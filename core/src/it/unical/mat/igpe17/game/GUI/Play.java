package it.unical.mat.igpe17.game.GUI;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.JumpListener;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.constants.MyAnimation;
import it.unical.mat.igpe17.game.logic.Bullet;
import it.unical.mat.igpe17.game.logic.Game;
import it.unical.mat.igpe17.game.objects.Obstacle;
import it.unical.mat.igpe17.game.objects.StaticObject;

public class Play implements Screen {

	private Game game;
	private Background background;
	private Player player;
	public static int player_type = 1;

	private List<Enemy> enemies;
	private List<Obstacle> coins;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private MyAnimation animations;
	private SpriteBatch batch;

	// Camera bounds
	private int mapLeft = 0;
	private int mapRight;

	float elapsedTime;

	private String level;
	private int x1 = 10;

	private Thread jump_player;

	public Play(String level) {
		this.level = level;
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
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		elapsedTime += delta;

		batch.setProjectionMatrix(camera.combined);

		background.update(delta);
		
		updateLives();

		updatePlayer(delta);
		renderPlayer();
/*
		updateEnemy(delta);
		renderEnemy();
		

		renderCoins();
*/
		updateBullets(delta);
		renderBullets();
		camera.update();
		renderer.setView(camera);

		renderer.render();

	}
	
	private void updateBullets(float dt){
		game.updateBullets(dt);
	}
	private void renderBullets(){
		Texture bullet = new Texture(Asset.BULLET);
		batch.begin();
		if(!game.getBullets().isEmpty()){
			for(Bullet b: game.getBullets()){
				int xB = (int) ((b.getPosition().y) * Asset.TILE);
				int yB = (int) (((Asset.HEIGHT / Asset.TILE) - b.getPosition().x - 1) * Asset.TILE);

				batch.draw(bullet,xB ,yB );	
			}
		}
		batch.end();
	}
	


	private void updateLives() {
		batch.begin();

		// caricamento img del cuore
		Texture texture = new Texture(Asset.lives);
		
		int width = texture.getWidth() + 10;

		//per far rimanere fermi i cuori
		float cameraHalfWidth = camera.viewportWidth * .5f;
		x1 = (int) ((int) camera.position.x - cameraHalfWidth);
		
		int x2 = x1 + width;	
		int x3 = x2 + width;

		int y = 664;		

		//swicho a secondi dei cuori del player
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
	
	private boolean shot = false;
	private void updatePlayer(final float delta) {

		if (!game.isOver()) {

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

			if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
				if (player.getGun()) {
					player.setGun(false);
				} else {
					player.setGun(true);
				}
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.S)){

				float c_x = player.getPosition().x;
				float c_y = player.getPosition().y;
				
				if(player.getDirection() == 'r'){
					c_x -= 0.8f;
					c_y += GameConfig.SIZE_PLAYER_X;
					
					game.addBullet(c_x,c_y,'r');
				}
				else{
					c_x -= 0.8f;					
					game.addBullet(c_x,c_y,'l');
				}
				shot = true;
			}

			// System.out.println(player.getState());
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

			/*
			 * Update della camera fisica e logica
			 */
			if (((camera.position.x + camera.viewportWidth / 2) < mapRight
					&& (player.getPosition().y) * Asset.TILE >= game.getEndCamer() * 0.04)) {

				if (player.getState() == PlayerState.JUMPING && player.getDirection() == 'r') {
					camera.position.x += GameConfig.POSITIVE_JUMP_VELOCITY.y * 0.5;
				} else {
					camera.position.x += GameConfig.PLAYER_POS_VELOCITY.y * 1.2;
				}
				game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);

				updateCamera();

				game.setEndCamera(game.getEndCamer() + Asset.TILE * 2);
			}

			game.handleScores();
		}
	}

	private void updateCamera() {

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

	/*
	 * Visualizzazione delle animazioni in base allo stato del player
	 */
	private void renderPlayer() {

		if (player_type == 1) {

			if (player.getGun()) {
				switch (player.getDirection()) {
				case 'r': {
					if(player.getState() == PlayerState.IDLING && shot){
						shot = false;
						drawAnimation(player, "player_m_shot_right");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_m_idle_with_gun_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_m_run_with_gun_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_jump_right");
					}

					break;
				} // end of case 'r'
				case 'l': {
					if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
						drawAnimation(player, "player_m_idle_with_gun_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_m_run_with_gun_left");

					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_jump_left");
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

	private void renderCoins() {
		for (Obstacle o : coins) {
			drawAnimation(o, "coins");
		}
	}

	// stampa qualsiasi tipo di animazione
	private void drawAnimation(StaticObject obj, String name) {

		if (obj instanceof Player)
			obj = (Player) obj;
		else if (obj instanceof Enemy)
			obj = (Enemy) obj;
		else if (obj instanceof Obstacle)
			obj = (Obstacle) obj;

		int xP = (int) ((obj.getPosition().y) * Asset.TILE);
		int yP = (int) (((Asset.HEIGHT / Asset.TILE) - obj.getPosition().x - 1) * Asset.TILE);

		Animation<TextureRegion> a = animations.getAnimation(name);
		batch.begin();
		batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
		batch.end();

	}

	// private void drawAnimationEnemy(String name, float x, float y) {
	// int xP = (int) ((y) * Asset.TILE);
	// int yP = (int) (((Asset.HEIGHT / Asset.TILE) - x - 1) * Asset.TILE);
	//
	// Animation<TextureRegion> a = animations.getAnimation(name);
	// batch.begin();
	// batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
	// batch.end();
	// }

	private void renderEnemy() {
		for (Enemy e : enemies) {

			switch (e.getType()) {
			case "31": {
				if (e.getDirection() == 'l') {
					drawAnimation(e, "enemy2_m_run_left");
				} else {
					// System.out.println("31");
					drawAnimation(e, "enemy2_m_run_right");
				}

				break;
			}
			case "32": {
				if (e.getDirection() == 'l') {
					drawAnimation(e, "enemy_run_left");
				} else {
					drawAnimation(e, "enemy_run_right");
				}
				break;

			}
			case "33": {
				if (e.getDirection() == 'l') {
					drawAnimation(e, "enemy1_w_run_left");
				} else {

					drawAnimation(e, "enemy1_w_run_right");
				}

				break;

			}

			default:
				break;
			}

		}
	}

	// rimuove i nemici dalla creazione dall'editor (i nemici sono sempre
	// presenti e si muovono nel gioco)
	private void removeObjectsFromMap() {

		/*
		 * Rimozione dei nemici dalla mappa
		 */
		for (Enemy e : enemies) {
			int layer = game.getRow() - 1;
			int x = (int) e.getPosition().x;
			int y = (int) e.getPosition().y;

			layer = layer - x;
			TiledMapTileLayer tile = (TiledMapTileLayer) map.getLayers().get(0);
			tile.getCell(y, layer).setTile(null);
		}

		/*
		 * Rimozione dei coins dalla mappa
		 */
		for (Obstacle e : coins) {
			int layer = game.getRow() - 1;
			int x = (int) e.getPosition().x;
			int y = (int) e.getPosition().y;

			layer = layer - x;
			TiledMapTileLayer tile = (TiledMapTileLayer) map.getLayers().get(0);
			tile.getCell(y, layer).setTile(null);
		}

	}

	private void updateEnemy(float delta) {
		game.moveEnemy(delta);
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
	public void resume() {
	}
}
