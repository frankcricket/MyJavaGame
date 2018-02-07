package it.unical.mat.igpe17.game.GUI;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.EnemyState;
import it.unical.mat.igpe17.game.actors.JumpListener;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.constants.MyAnimation;
import it.unical.mat.igpe17.game.constants.Textures;
import it.unical.mat.igpe17.game.logic.Bullet;
import it.unical.mat.igpe17.game.logic.Game;
import it.unical.mat.igpe17.game.objects.Obstacle;
import it.unical.mat.igpe17.game.objects.StaticObject;
import it.unical.mat.igpe17.game.screens.HandleGameOver;

public class Play implements Screen {

	private Game game;
	private Background background;
	private Player player;
	public static int player_type = 1;

	private List<StaticObject> enemies;
	private List<StaticObject> coins;
	
	private StaticObject toDrawObj;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private MyAnimation animations;
	protected SpriteBatch batch;

	// Camera bounds
	private int mapLeft = 0;
	private int mapRight;

	float elapsedTime;
	// gestione tempo di gioco
	private final static int nDigits = 4;
	private int[] digit = new int[nDigits];

	private String level;

	private Thread jump_player;
	
	
	

	public Play(String level) {
		this.level = level;
	}
	public Play(int player_type) {
		this.player_type = player_type;
	}


	@Override
	public void show() {

		if (level != null)
			game.LEVEL = level;
		game = new Game();
		game.loadLevel();

		mapRight = game.getColumn() * Asset.TILE;

		background = new Background();
		player = game.getPlayer();
		enemies = game.getEnemy();
		coins = game.getCoins();

		/*
		 * mappa e animazioni
		 */
		map = new TmxMapLoader().load(game.LEVEL);

		batch = new SpriteBatch();
		animations = new MyAnimation();

		/*
		 * camera
		 */
		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Asset.WIDTH, Asset.HEIGHT);
		camera.update();

		/*
		 * Faccio partire il Thread che si occuperà del salto del player
		 */
		jump_player = new Thread(new JumpListener(game));
		jump_player.start();
		removeObjectsFromMap();

		initTimer();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		elapsedTime += delta;

		batch.setProjectionMatrix(camera.combined);

		background.update(delta);
		
		camera.update();
		renderer.setView(camera);
		renderer.render();
		
		updateLives();
		updateTimer();
		updateScores();
		
		updatePlayer(delta);
		renderPlayer();
		
		updateEnemy(delta);
		renderEnemy();
		
		renderCoins();
		
		updateBullets(delta);
		renderBullets();
		renderGenericAnimations();
		
		resumePlayer();
		
	}

	private boolean shot = false;

	private void updatePlayer(final float delta) {

		if (!game.isOver() && !game.RESUME) {
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !(player.getState() == PlayerState.JUMPING)
					&& !game.PLAYER_IS_FALLING) {
				player.setDirection('r');
				player.setState(PlayerState.RUNNING);
				game.resumeCondition();
				if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
					player.setState(PlayerState.JUMPING);
					game.resumeCondition();
				}

			} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && !(player.getState() == PlayerState.JUMPING)
					&& !game.PLAYER_IS_FALLING) {
				player.setDirection('l');
				player.setState(PlayerState.RUNNING);
				game.resumeCondition();
				if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
					player.setState(PlayerState.JUMPING);
					game.resumeCondition();
				}

			} else if (Gdx.input.isKeyJustPressed(Input.Keys.X) && !(player.getState() == PlayerState.JUMPING)
					&& !game.PLAYER_IS_FALLING) {
				player.setState(PlayerState.JUMPING);
				player.VERTICAL_JUMP = true;
				game.resumeCondition();

			}
			if (player.VERTICAL_JUMP && Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
				player.setDirection('r');
				game.moveWhileJumping = true;
			} else if (player.VERTICAL_JUMP && Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
				player.setDirection('l');
				game.moveWhileJumping = true;
			}

			//input selezione pistola
			if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
				if (player.getGun()) {
					player.setGun(false);
				} else {
					player.setGun(true);
				}
			}
			//input sparo
			if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
				//se il player ha la pistola, allora può sparare
				if(player.getGun()){
					float c_x = player.getPosition().x;
					float c_y = player.getPosition().y;
	
					if (player.getDirection() == 'r') {
						c_x -= 0.57f;
						c_y += GameConfig.SIZE_PLAYER_X;
	
						game.addBullet(c_x, c_y, 'r');
					} else {
						c_x -= 0.57f;
						game.addBullet(c_x, c_y, 'l');
					}
					shot = true;
				}
			}

			/**
			 * Se il personaggio è fermo, viene impostato il suo stato come
			 * 
			 * @param state
			 *            IDLING
			 */
			if (!(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) && !(Gdx.input.isKeyPressed(Input.Keys.LEFT))
					&& !(player.getState() == PlayerState.JUMPING)) {
				if (!game.PLAYER_IS_FALLING)
					player.setState(PlayerState.IDLING);
			}
			if(game.PLAYER_COLLISION){
				player.setState(PlayerState.HIT);
				player.decreaseLives();
				game.RESUME = true;
				game.PLAYER_COLLISION = false;
			}


			updateCamera();
			game.handleScores();
		}
		
	}

	private void updateCamera() {
		float y_pos = (player.getPosition().y) * Asset.TILE;
		/*(y_pos >= game.getEndCamera() * 0.045) && */
		if (Math.abs(y_pos -(camera.position.x-640)) > Asset.TILE*3
			 && !player.VERTICAL_JUMP) {

			if (player.getState() == PlayerState.JUMPING) {
				camera.position.x += GameConfig.POSITIVE_JUMP_VELOCITY.y * 0.9;
			} else {
				camera.position.x += GameConfig.PLAYER_POS_VELOCITY.y * 1.2;
			}
			game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);

			game.setEndCamera(game.getEndCamera() + Asset.TILE * 1.5f);
		
			float cameraHalfWidth = camera.viewportWidth * .5f;

			float cameraLeft = camera.position.x - cameraHalfWidth;
			float cameraRight = camera.position.x + cameraHalfWidth;

			if (camera.viewportWidth > mapRight) {
				camera.position.x = mapRight / 2;
			} else if (cameraLeft <= mapLeft) {
				camera.position.x = mapLeft + cameraHalfWidth;
			} else if (cameraRight >= mapRight) {
				camera.position.x = mapRight - cameraHalfWidth;
			}
			
		}

	}

	/*
	 * Visualizzazione delle animazioni in base allo stato del player
	 */
	private float e_time = 0;
	private void renderPlayer() {
		if(!game.RESUME){

		if (player_type == 1) {

			if (player.getGun()) {
				switch (player.getDirection()) {
				case 'r': {
					if (/*player.getState() == PlayerState.IDLING && */shot) {
						if(e_time > 0.3f){
							shot = false;
							e_time = 0;
						}
						drawAnimation(player, "player_m_shot_right");
						e_time += Gdx.graphics.getDeltaTime();
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_m_idle_with_gun_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_m_run_with_gun_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_m_jump_with_gun_right");
					}

					break;
				} // end of case 'r'
				case 'l': {
					if (/*player.getState() == PlayerState.IDLING && */shot) {
						if(e_time > 0.3f){
							shot = false;
							e_time = 0;
						}
						drawAnimation(player, "player_m_shot_left");
						e_time += Gdx.graphics.getDeltaTime();
					} 
					else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_m_idle_with_gun_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_m_run_with_gun_left");

					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_m_jump_with_gun_left");
					}

					break;
				}
				default:
					break;
				}// end of switch
			} // end if check gun
			else {

				switch (player.getDirection()) {
				case 'r': {
					if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_idle_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_run_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_jump_right");
					}

					break;
				} // end of case 'r'
				case 'l': {
					if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_idle_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_run_left");

					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_jump_left");
					}

					break;
				}
				default:
					break;
				}// end of switch
			}
			} else {
				/*
				 * Animazioni del player femmina
				 */
				switch (player.getDirection()) {
				case 'r': {
					if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_w_idle_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_w_run_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_w_jump_right");
					}
		
					break;
				} // end of case 'r'
				case 'l': {
					if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_w_idle_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_w_run_left");
		
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_w_jump_left");
					}
		
					break;
				}
				default:
					break;
				}// end of switch
		
			}
		}
		else{
			if(player.getDirection() == 'r'){
				drawAnimation(player,"player_m_flash_idle_right");
			}
			else{
				drawAnimation(player,"player_m_flash_idle_left");
			}
		}
		
		//TODO aggiungere le animazione del player donna , sparo players, animazione collisione con oggetti

	}
	
	private void updateEnemy(float delta) {
		game.moveEnemy(delta);
	}
	
	private void renderEnemy() {
		for (StaticObject obj : enemies) {
			Enemy e = (Enemy)obj;
			if(e.getState() == EnemyState.RUNNING){
				switch (e.getType()) {
				case "31": {
					if (e.getDirection() == 'l') {
						drawAnimation(e, "enemy3_m_run_left");
					} else {
						drawAnimation(e, "enemy3_m_run_right");
					}
					break;
				}
				case "32": {
					if (e.getDirection() == 'l') {
						drawAnimation(e, "enemy1_run_left");
					} else {
						drawAnimation(e, "enemy1_run_right");
					}
					break;
				}
				case "33": {
					if (e.getDirection() == 'l') {
						drawAnimation(e, "enemy2_w_run_left");
					} else {
						drawAnimation(e, "enemy2_w_run_right");
					}
					break;
				}
				default:
					break;
				}
			}else if(e.getState() == EnemyState.FOLLOWING_PLAYER){
				switch (e.getType()) {
				case "31": {
					if (e.getDirection() == 'l') {
						drawAnimation(e, "enemy3_attack_left");
					} else {
						drawAnimation(e, "enemy3_attack_right");
					}
					break;
				}
				case "32": {
					if (e.getDirection() == 'l') {
						drawAnimation(e, "enemy1_attack_left");
					} else {
						drawAnimation(e, "enemy1_attack_right");
					}
					break;
				}
				case "33": {
					if (e.getDirection() == 'l') {
						drawAnimation(e, "enemy2_attack_left");
					} else {
						drawAnimation(e, "enemy2_attack_right");
					}
					break;
				}
				default:
					break;
				}
			}

		}
	}

	private void renderCoins() {
		for (StaticObject o : coins) {
			drawAnimation(o, "coins");
		}
	}
	
	private void updateBullets(float dt) {
		game.updateBullets(dt);
	}

	private void renderBullets() {
		batch.begin();
		if (!game.getBullets().isEmpty()) {
			Texture bullet = Textures.BULLET;
			Sprite sprite = new Sprite(bullet);
			if(player.getDirection() == 'l'){
				sprite.flip(true, false);
			}
			for (Bullet b : game.getBullets()) {
				int xB = (int) ((b.getPosition().y) * Asset.TILE);
				int yB = (int) (((Asset.HEIGHT / Asset.TILE) - b.getPosition().x - 1) * Asset.TILE);

				batch.draw(sprite, xB, yB);
			}
		}
		batch.end();
	}
	
	int clock = 0;
	float deltaTime = 0;

	private void updateTimer() {

		deltaTime += Gdx.graphics.getDeltaTime();
		if ((int) deltaTime > clock) {
			clock = (int) deltaTime;
			timer();
		}

		batch.begin();
		int i = nDigits - 1;
		Texture clock = new Texture(Asset.CLOCK);
		Texture d1 = Textures.get("D" + digit[i--]);
		Texture d2 = Textures.get("D" + digit[i--]);
		Texture d3 = Textures.get("D" + digit[i--]);
		Texture d4 = Textures.get("D" + digit[i]);

		Texture dp = Textures.get("DP");
		
		int width = 15 + 10;
		float cameraHalfWidth = camera.viewportWidth * .5f;
		float pos_x = (camera.position.x - cameraHalfWidth) + 545;
		int pos_y = GameConfig.HP+4;
		
		batch.draw(clock,pos_x,pos_y-2);

		pos_x += width+25;		
		batch.draw(d4, pos_x, pos_y);
		pos_x += width;
		batch.draw(d3, pos_x, pos_y);
		pos_x += width - 5;
		batch.draw(dp, pos_x, pos_y);
		pos_x += width - 15;
		batch.draw(d2, pos_x, pos_y);
		pos_x += width;
		batch.draw(d1, pos_x, pos_y);

		batch.end();

	}

	private void timer() {

		int index = nDigits - 1;
		if (digit[index] != 0) { // settaggio dei secondi
			digit[index]--;
		} else {
			if (digit[index - 1] != 0) {// settaggio dei secondi: 59 a decremento
				digit[index - 1]--;
				digit[index] = 9;
			} else {
				if (digit[index - 2] != 0) { //settaggio dei minuti
					digit[index - 2]--;
					digit[index - 1] = 5;
					digit[index] = 9;
				} else {
					if (digit[index - 3] != 0) {// settaggio dei minuti e secondi: (--)9:59, dove(--) è il minuto decrem.
						digit[index - 3]--;
						digit[index - 2] = 9;
						digit[index - 1] = 5;
						digit[index] = 9;
					} else {
						player.setState(PlayerState.DEAD);
					}
				}
			}
		}
		/*
		 * for(int i = 0; i < nDigits; i++){ System.out.print(digit[i]+ " "); }
		 * System.out.println();
		 */

	}

	private void updateLives() {
		//Texture vita
		Texture texture = Textures.LIFE;
		Texture heart = Textures.HEART;
		
		int width = heart.getWidth() + 15;
		
		float cameraHalfWidth = camera.viewportWidth * .5f;
		float x1 = (camera.position.x - cameraHalfWidth) + 10;
		
		int y = GameConfig.HP;

		batch.begin();
		
		batch.draw(heart, x1, y-2);
		
		x1 += width;
		float x2 = x1 + width;
		float x3 = x2 + width;

		switch (player.getLives()) {

		case 1: {
			batch.draw(texture, x1, y);
			break;
		}

		case 2: {
			batch.draw(texture, x1, y);
			batch.draw(texture, x2, y);
			break;
		}

		case 3: {
			batch.draw(texture, x1, y);
			batch.draw(texture, x2, y);
			batch.draw(texture, x3, y);
			break;
		}

		default:
			break;

		}

		batch.end();
	}
	
	private void updateScores(){
		int coins = game.getCoinsCount();
		Texture coin = Textures.COIN;
		Texture score = Textures.SCORE;
		
		
		float cameraHalfWidth = camera.viewportWidth * .5f;
		float pos_x_coin = (camera.position.x - cameraHalfWidth) + 320;
		float pos_x_score = (camera.position.x - cameraHalfWidth) + 320;
		
		batch.begin();
		
		//sprite numero di monete
		batch.draw(coin,pos_x_coin,GameConfig.HP,39,40);
		int y = GameConfig.HP + 4;
		if(coins < 10){
			Texture c1 = Textures.get("D"+coins);
			pos_x_coin += 55;
			batch.draw(c1,pos_x_coin,y);
		}else{
			int tmp = coins/10;
			Texture c1 = Textures.get("D"+tmp);
			tmp = coins%10;
			Texture c2 = Textures.get("D"+tmp);
			pos_x_coin += 55;
			batch.draw(c1,pos_x_coin,y);
			pos_x_coin += 17;
			batch.draw(c2,pos_x_coin,y);
		}
		
		
		pos_x_score += 650; 
		//sprite punteggio
		batch.draw(score,pos_x_score,GameConfig.HP);
		pos_x_score += 50;
		ArrayList<Integer> digits = handleDigits(game.getScore());
		for(int i = digits.size()-1; i>=0; i--){
			Texture t = Textures.get("D"+digits.get(i));
			batch.draw(t,pos_x_score,y);
			pos_x_score += 20;
		}
		
		digits.clear();
		
		batch.end();
	}
	
	private float drawingTime = 0.9f;
	private float timer_limit = 1.2f;
	private int step_counter = 0;
	private void renderGenericAnimations(){
		toDrawObj = game.toDraw;
		if(toDrawObj == null){
			return;
		}
		if((int)drawingTime == 0){
			resetMapCell(toDrawObj.getPosition());
		}

		if(drawingTime < timer_limit){
			if(toDrawObj instanceof Obstacle){
				Obstacle tmp = (Obstacle)toDrawObj;
				if(tmp.getType().equals("19") || tmp.getType().equals("20")){
					drawAnimation(tmp, "explosion");
				} else {
					
					drawAnimation(tmp.getPosition().x -0.01f, tmp.getPosition().y+0.5f, "little_coin");
					timer_limit = 1.5f;
				}
			}
			
		}
		else{
			toDrawObj = null;
			game.toDraw = null;
			drawingTime = 0.9f;
			step_counter = 0;
			return;
		}
		drawingTime += Gdx.graphics.getDeltaTime();
	}
	

	// stampa qualsiasi tipo di animazione
	private void drawAnimation(StaticObject obj, String name) {

		int xP = (int) ((obj.getPosition().y) * Asset.TILE);
		int yP = (int) (((Asset.HEIGHT / Asset.TILE) - obj.getPosition().x - 1) * Asset.TILE);

		Animation<TextureRegion> a = animations.getAnimation(name);
		batch.begin();
		batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
		batch.end();

	}
	
	private void drawAnimation(float x, float y, String name) {
		
		x -= step_counter *Gdx.graphics.getDeltaTime();
		//TODO VIFICARE FUNZIONAMENTO COLLISIONE
		if(!game.animationCollision(x, y)){
			step_counter += 10;
			float xP = y * Asset.TILE;
			float yP = (((Asset.HEIGHT / Asset.TILE) - x - 1) * Asset.TILE);
	
			Animation<TextureRegion> a = animations.getAnimation(name);
			batch.begin();
			batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
			batch.end();
		}
	}

	

	// rimuove i nemici dalla creazione dall'editor (i nemici sono sempre
	// presenti e si muovono nel gioco)
	private void removeObjectsFromMap() {

		/*
		 * Rimozione dei nemici dalla mappa
		 */
		for (StaticObject obj : enemies) {
			resetMapCell(obj.getPosition());
		}

		/*
		 * Rimozione dei coins dalla mappa
		 */
		for (StaticObject obj : coins) {
			resetMapCell(obj.getPosition());
		}
	}
	
	private void resetMapCell(Vector2 pos){
		int layer = game.getRow() - 1;
		int x = (int) pos.x;
		int y = (int) pos.y;

		layer = layer - x;
		TiledMapTileLayer tile = (TiledMapTileLayer) map.getLayers().get(0);
		tile.getCell(y, layer).setTile(null);
	}

	private void initTimer() {

		int i = nDigits - 1;
		digit[i--] = GameConfig.DIGIT_4;
		digit[i--] = GameConfig.DIGIT_3;
		digit[i--] = GameConfig.DIGIT_2;
		digit[i] = GameConfig.DIGIT_1;

	}
	
	public static ArrayList<Integer> handleDigits(int score){
	
		ArrayList<Integer> digits = new ArrayList<>();
		if(score < 10){
			digits.add(score);
		}else{
			int elem = score;
			while(elem != 0){
				elem = score / 10;
				score = score%10;
				digits.add(score);
				score = elem;
			}

		}
		
		return digits;
	}
	
	
	private float waiting_time;
	private boolean stepOver = false;
	private void resumePlayer(){
		if(player.getLives() == 0 || (player.getState() == PlayerState.DEAD)){
			player.setState(PlayerState.DEAD);
			game.RESUME = false;
			GameConfig.BEST_SCORE = player.getScore();
			((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(HandleGameOver.getInstance());
			
		}
		if(game.RESUME){
			
			
			/*
			 * Tempo di attesa prima che ritorni sulla mappa
			 */
			if(waiting_time > 1.2f && ! stepOver){
				game.findNewPlayerPosition(player.getPosition());
				stepOver = true;
				waiting_time = 6;
				
				//reset del timer
				initTimer();
		
				//player state
				player.setState(PlayerState.IDLING);
			}
			else{
				//reset della camera
				camera.position.x -= 40 *Gdx.graphics.getDeltaTime();
				if((camera.position.x  - (camera.viewportWidth * .5f)) <= mapLeft)
					camera.position.x = mapLeft + (camera.viewportWidth * .5f);
				game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);
				game.setEndCamera(game.getEndCamera() + Asset.TILE * 1.5f);
			}
			/*
			 * Tempo di attesa stampa animazione
			 */
			if(stepOver){
				if(waiting_time > 9.8f){
					game.RESUME = false;
					stepOver = false;
					waiting_time = 0;
				}
			}
			
			waiting_time += Gdx.graphics.getDeltaTime();
//			camera.position.x -= 512;
		}
	}
	

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		animations.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {}
}
