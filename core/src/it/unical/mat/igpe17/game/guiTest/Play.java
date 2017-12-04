package it.unical.mat.igpe17.game.guiTest;

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
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.MyAnimation;
import it.unical.mat.igpe17.game.constants.MyTexture;
import it.unical.mat.igpe17.game.logic.Game;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;

public class Play implements Screen {

	private Game game;
	private Background background;
	private Player player;

	private List<Ground> groundObjects;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private MyAnimation animations;
	private SpriteBatch batch;
	private Sprite sprite_player;

	// Camera bounds
	private int mapLeft = 0;
	private int mapRight;
	private int mapBottom = 0;
	private int mapTop;

	float elapsedTime;
	private int boundEndX = Asset.WIDTH;
	private float camera_pos;

	@Override
	public void show() {
		game = new Game();
		game.loadLevel();

		mapRight = game.getRow() * Asset.TILE;
		mapTop = Asset.HEIGHT;

		player = game.getPlayer();
		groundObjects = game.getGround();

		background = new Background();

		map = new TmxMapLoader().load(Asset.FIRST_LEVEL);

		batch = new SpriteBatch();
		sprite_player = new Sprite(MyTexture.getTexture("Player"));
		animations = new MyAnimation();

		renderer = new OrthogonalTiledMapRenderer(map);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Asset.WIDTH, Asset.HEIGHT);
		camera.update();
		camera_pos = camera.position.x;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		elapsedTime += delta;

		batch.setProjectionMatrix(camera.combined);

		background.update(delta);
		updatePlayer(delta);

		camera.update();
		renderer.setView(camera);

		renderer.render();

		// for (Ground g : groundObjects) {
		// System.out.println(g.getPosition().x + "/" + g.getPosition().y + " "
		// + ((7 - g.getPosition().x)) + "/"
		// + (g.getPosition().y));
		// batch.draw(texture.getTexture(g.getType()), (g.getPosition().y) * 64,
		// ((7 - g.getPosition().x) * 64));
		// }

	}

	private void updatePlayer(float delta) {

		boolean running = false;
		int xP, yP;

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			game.movePlayer('r', delta);

			running = true;

			xP = (int) ((player.getPosition().y) * Asset.TILE);
			yP = (int) (((Asset.HEIGHT / Asset.TILE) - player.getPosition().x - 1) * Asset.TILE);

			Animation<TextureRegion> a = animations.getAnimation("player_run_right");
			batch.begin();
			batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
			batch.end();
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			game.movePlayer('l', delta);
			running = true;
			xP = (int) ((player.getPosition().y) * Asset.TILE);
			yP = (int) (((Asset.HEIGHT / Asset.TILE) - player.getPosition().x - 1) * Asset.TILE);
			Animation<TextureRegion> a = animations.getAnimation("player_run_left");
			batch.begin();
			batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
			batch.end();
		} else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			player.IS_JUMPING = true;
			running = true;
			game.movePlayer('s', delta);
			xP = (int) ((player.getPosition().y) * Asset.TILE);
			yP = (int) (((Asset.HEIGHT / Asset.TILE) - player.getPosition().x - 1) * Asset.TILE);
			Animation<TextureRegion> a = animations.getAnimation("player_jump");
			batch.begin();
			batch.draw(a.getKeyFrame(elapsedTime, true), xP, yP);
			batch.end();
		}
	

		/*
		 * Update della camera fisica e logica
		 */

		if ((player.getPosition().y >= (boundEndX / Asset.TILE) * 0.3)
				&& (camera.position.x + camera.viewportWidth / 2) < mapRight) {

			camera.position.x += camera.position.x * delta;
			game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);

			updateCamera();

			boundEndX += Asset.TILE * 2;

		}

		camera.update();
		renderer.setView(camera);

		renderer.render();
		if (!running) {
			xP = (int) ((player.getPosition().y) * Asset.TILE);
			yP = (int) (((Asset.HEIGHT / Asset.TILE) - player.getPosition().x - 1) * Asset.TILE);
			sprite_player.setPosition(xP, yP);
			batch.begin();
			sprite_player.draw(batch);
			batch.end();
		}

	}

	private void updateCamera() {

		float cameraHalfWidth = camera.viewportWidth * .5f;
		float cameraHalfHeight = camera.viewportHeight * .5f;

		float cameraLeft = camera.position.x - cameraHalfWidth;
		float cameraRight = camera.position.x + cameraHalfWidth;
		float cameraBottom = camera.position.y - cameraHalfHeight;
		float cameraTop = camera.position.y + cameraHalfHeight;

		if (camera.viewportWidth > mapRight) {
			camera.position.x = mapRight / 2;
		} else if (cameraLeft <= mapLeft) {
			camera.position.x = mapLeft + cameraHalfWidth;
		} else if (cameraRight >= mapRight) {
			camera.position.x = mapRight - cameraHalfWidth;
		}

		if (camera.viewportHeight > mapTop) {
			camera.position.y = mapTop / 2;
		} else if (cameraBottom <= mapBottom) {
			camera.position.y = mapBottom + cameraHalfHeight;
		} else if (cameraTop >= mapTop) {
			camera.position.y = mapTop - cameraHalfHeight;
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
	public void resume() {
	}
}
