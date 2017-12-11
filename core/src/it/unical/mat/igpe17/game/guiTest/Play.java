package it.unical.mat.igpe17.game.guiTest;

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

import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.constants.MyAnimation;
import it.unical.mat.igpe17.game.logic.Game;
import it.unical.mat.igpe17.game.player.JumpListener;
import it.unical.mat.igpe17.game.player.Player;
import it.unical.mat.igpe17.game.player.PlayerState;

public class Play implements Screen {

	private Game game;
	private Background background;
	private Player player;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private MyAnimation animations;
	private SpriteBatch batch;

	// Camera bounds
	private int mapLeft = 0;
	private int mapRight;

	float elapsedTime;
	private int boundEndX = Asset.WIDTH;

	@Override
	public void show() {
		game = new Game();
		game.loadLevel();

		mapRight = game.getColumn() * Asset.TILE;

		player = game.getPlayer();

		background = new Background();

		map = new TmxMapLoader().load(Asset.FIRST_LEVEL);

		batch = new SpriteBatch();
		animations = new MyAnimation();

		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Asset.WIDTH, Asset.HEIGHT);
		camera.update();
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
		
		camera.update();
		renderer.setView(camera);

		renderer.render();

	}


	
	private void updatePlayer(final float delta) {

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && ! (player.getState() == PlayerState.JUMPING)) {
			player.setDirection('r');
			player.setState(PlayerState.RUNNING);
			game.movePlayer('r', delta);
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
				player.setState(PlayerState.JUMPING);
				Thread t = new Thread(new JumpListener(game));
				t.start();
			}

		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && ! (player.getState() == PlayerState.JUMPING)) {
			player.setDirection('l');
			player.setState(PlayerState.RUNNING);
			game.movePlayer('l', delta);
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
				player.setState(PlayerState.JUMPING);
				Thread t = new Thread(new JumpListener(game));
				t.start();
			}
			
		} else if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			player.setState(PlayerState.JUMPING);
			player.VERTICAL_JUMP = true;
			Thread t = new Thread(new JumpListener(game));
			t.start();
		}
		
		/**
		 * Se il personaggio è fermo, viene impostato il suo stato come
		 * @param state IDLING
		 */
		if(!(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
				&& !(Gdx.input.isKeyPressed(Input.Keys.LEFT))
				&& !(player.getState() == PlayerState.JUMPING)){
			player.setState(PlayerState.IDLING);
		}
		
		/*
		 * Update della camera fisica e logica
		 */
		if (((camera.position.x + camera.viewportWidth / 2) < mapRight	
				&& (player.getPosition().y)*Asset.TILE >= boundEndX * 0.03)) {

			camera.position.x += GameConfig.PLAYER_POS_VELOCITY.y;
			game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);

			updateCamera();
			
			boundEndX += Asset.TILE*2;
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
	private void renderPlayer(){
		
		switch(player.getDirection()){
		case 'r':{
			if(player.getState() == PlayerState.IDLING){
				drawAnimation("player_idle_right");
			}else if (player.getState() == PlayerState.RUNNING){
				drawAnimation("player_run_right");
			}else if(player.getState() == PlayerState.JUMPING){
				drawAnimation("player_jump_right");
			}
			
			break;
		}//end of case 'r'
		case 'l':{
			if(player.getState() == PlayerState.IDLING){
				drawAnimation("player_idle_left");
			}else if (player.getState() == PlayerState.RUNNING){
				drawAnimation("player_run_left");

			}else if(player.getState() == PlayerState.JUMPING){
				drawAnimation("player_jump_left");
			}
		
			break;
		}
		default:
			break;
		}//end of switch
		
	}
	
	private void drawAnimation(String name){
		int xP = (int) ((player.getPosition().y) * Asset.TILE);
		int yP = (int) (((Asset.HEIGHT / Asset.TILE) - player.getPosition().x - 1) * Asset.TILE);

		Animation<TextureRegion> a = animations.getAnimation(name);
		batch.begin();
		batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
		batch.end();
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
