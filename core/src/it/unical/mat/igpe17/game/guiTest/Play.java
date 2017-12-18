package it.unical.mat.igpe17.game.guiTest;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.JumpListener;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.constants.MyAnimation;
import it.unical.mat.igpe17.game.logic.Game;

public class Play implements Screen {

	private Game game;
	private Background background;
	private Player player;
	public static int player_type = 1;

	private List<Enemy> enemies;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private MyAnimation animations;
	private SpriteBatch batch;

	// Camera bounds
	private int mapLeft = 0;
	private int mapRight;

	float elapsedTime;

	private Thread jump_player;

	@Override
	public void show() {
		game = new Game();
		game.loadLevel();

		mapRight = game.getColumn() * Asset.TILE;

		background = new Background();
		player = game.getPlayer();
		enemies = game.getEnemy();

		/*
		 * mappa e animazioni
		 */
		map = new TmxMapLoader().load(Asset.FIRST_LEVEL);
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
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		elapsedTime += delta;

		batch.setProjectionMatrix(camera.combined);

		background.update(delta);
		updatePlayer(delta);
		renderPlayer();

		updateEnemy(delta);
		renderEnemy();

		camera.update();
		renderer.setView(camera);

		renderer.render();

	}

	private void updatePlayer(final float delta) {
		
		if(!game.isOver()){

			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) 
					&& !(player.getState() == PlayerState.JUMPING)
					&& !game.PLAYER_IS_FALLING) {
				player.setDirection('r');
				player.setState(PlayerState.RUNNING);
				game.resumeCondition();
				if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
					player.setState(PlayerState.JUMPING);
					game.resumeCondition();
				}
	
			} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) 
					&& !(player.getState() == PlayerState.JUMPING)
					&& !game.PLAYER_IS_FALLING) {
				player.setDirection('l');
				player.setState(PlayerState.RUNNING);
				game.resumeCondition();
				if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
					player.setState(PlayerState.JUMPING);
					game.resumeCondition();
				}

			} else if(Gdx.input.isKeyJustPressed(Input.Keys.X) 
					&& !(player.getState() == PlayerState.JUMPING)
					&& !game.PLAYER_IS_FALLING){
				player.setState(PlayerState.JUMPING);
				player.VERTICAL_JUMP = true;
				game.resumeCondition();
				
			}
			
//			System.out.println(player.getState());
		/**
		 * Se il personaggio è fermo, viene impostato il suo stato come
		 * @param state IDLING
		 */
		if(!(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) 
				&& !(Gdx.input.isKeyPressed(Input.Keys.LEFT))
				&& !(player.getState() == PlayerState.JUMPING)){
			if(!game.PLAYER_IS_FALLING)
				player.setState(PlayerState.IDLING);
		}
		
		/*
		 * Update della camera fisica e logica
		 */
		if (((camera.position.x + camera.viewportWidth / 2) < mapRight	
				&& (player.getPosition().y)*Asset.TILE >= game.getEndCamer() * 0.04)) {

			if(player.getState() == PlayerState.JUMPING && player.getDirection() == 'r'){
				camera.position.x += GameConfig.POSITIVE_JUMP_VELOCITY.y *0.7;
			}
			else{
				camera.position.x += GameConfig.PLAYER_POS_VELOCITY.y*1.4;
			}
			game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);

			updateCamera();
			
			game.setEndCamera(game.getEndCamer() + Asset.TILE*2);
		}
		
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
		
		if(player_type == 1){

			switch (player.getDirection()) {
			case 'r': {
				if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING ) {
					drawAnimation("player_idle_right");
				} else if (player.getState() == PlayerState.RUNNING) {
					drawAnimation("player_run_right");
				} else if (player.getState() == PlayerState.JUMPING) {
					drawAnimation("player_jump_right");
				}
	
				break;
			} // end of case 'r'
			case 'l': {
				if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
					drawAnimation("player_idle_left");
				} else if (player.getState() == PlayerState.RUNNING) {
					drawAnimation("player_run_left");
	
				} else if (player.getState() == PlayerState.JUMPING) {
					drawAnimation("player_jump_left");
				}
	
				break;
			}
			default:
				break;
			}// end of switch
		} else {
					/*
					 * Animazioni del player femmina
					 */
			switch (player.getDirection()) {
			case 'r': {
				if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING ) {
					drawAnimation("player_w_idle_right");
				} else if (player.getState() == PlayerState.RUNNING) {
					drawAnimation("player_w_run_right");
				} else if (player.getState() == PlayerState.JUMPING) {
					drawAnimation("player_w_jump_right");
				}
	
				break;
			} // end of case 'r'
			case 'l': {
				if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING) {
					drawAnimation("player_w_idle_left");
				} else if (player.getState() == PlayerState.RUNNING) {
					drawAnimation("player_w_run_left");
	
				} else if (player.getState() == PlayerState.JUMPING) {
					drawAnimation("player_w_jump_left");
				}
	
				break;
			}
			default:
				break;
			}// end of switch
			
		}

	}

	// stampa qualsiasi tipo di animazione
	private void drawAnimation(String name) {
		int xP = (int) ((player.getPosition().y) * Asset.TILE);
		int yP = (int) (((Asset.HEIGHT / Asset.TILE) - player.getPosition().x - 1) * Asset.TILE);

		Animation<TextureRegion> a = animations.getAnimation(name);
		batch.begin();
		batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
		batch.end();
	}

	private void drawAnimationEnemy(String name, float x, float y) {
		int xP = (int) ((y) * Asset.TILE);
		int yP = (int) (((Asset.HEIGHT / Asset.TILE) - x - 1) * Asset.TILE);

		Animation<TextureRegion> a = animations.getAnimation(name);
		batch.begin();
		batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
		batch.end();
	}

	// TODO da mettere le animazioni per tutti i nemici
	private void renderEnemy() {
		for (Enemy e : enemies) {
			
			switch (e.getType()){
			
			case "32" :{
				if (e.getDirection() == 'l') {
					drawAnimationEnemy("enemy_run_left", e.getPosition().x, e.getPosition().y);
				} else {
					drawAnimationEnemy("enemy_run_right", e.getPosition().x, e.getPosition().y);
				}
			break;
				
			}
			
			case "31":{
				if (e.getDirection() == 'l') {
					drawAnimationEnemy("enemy2_m_run_left", e.getPosition().x, e.getPosition().y);
				} else {
					//System.out.println("31");
					drawAnimationEnemy("enemy2_m_run_right", e.getPosition().x, e.getPosition().y);
				}
				
				break;
			}
			
			case "33": {
				if (e.getDirection() == 'l') {
					drawAnimationEnemy("enemy1_w_run_left", e.getPosition().x, e.getPosition().y);
				} else {
					
					drawAnimationEnemy("enemy1_w_run_right", e.getPosition().x, e.getPosition().y);
				}
				
				break;
				
			}
			
			default:
				break;
			}

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
