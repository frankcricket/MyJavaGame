package it.unical.mat.igpe17.game.network;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import it.unical.mat.igpe17.game.GUI.Background;
import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.EnemyState;
import it.unical.mat.igpe17.game.actors.JumpListener;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.Audio;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.constants.MyAnimation;
import it.unical.mat.igpe17.game.constants.Textures;
import it.unical.mat.igpe17.game.logic.Bullet;
import it.unical.mat.igpe17.game.logic.Game;
import it.unical.mat.igpe17.game.objects.Obstacle;
import it.unical.mat.igpe17.game.objects.StaticObject;
import it.unical.mat.igpe17.game.screens.HandleGameOver;
import it.unical.mat.igpe17.game.screens.LevelUp;
import it.unical.mat.igpe17.game.screens.Settings;

public class MultiplayerGameMain implements Screen {

	private Game game;
	private Background background;
	private Player player;
	public static int player_type = 1;

	private List<StaticObject> enemies;
	private List<StaticObject> coins;
	private List<StaticObject> keyAndDoor;

	private StaticObject toDrawObj;

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;

	private static MyAnimation animations;
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

	private boolean ready_for_next_level = false;

	private Table table;
	private Stage stage;

	public static boolean PAUSE = false;

	public MultiplayerGameMain(String level) {
		this.level = level;
		this.player_type = Asset.PLAYER_TYPE;

		createWorldObjects();
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);
		Image pause_on = new Image(new Texture(Asset.PAUSE_ON));
		Image pause_off = new Image(new Texture(Asset.PAUSE_OFF));
		ImageButton pause = new ImageButton(pause_on.getDrawable(), pause_off.getDrawable());
		pause.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				PAUSE = true;
				((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(Settings.getSettings());
			}

		});

		pause.setPosition(1220, GameConfig.HP);
		table.addActor(pause);

		// Controllers.addListener(this);

	}

	private void createWorldObjects() {

		game = new Game();
		game.LEVEL = level;
		game.loadLevel();

		mapRight = game.getColumn() * Asset.TILE;

		background = new Background();
		player = game.getPlayer();
		enemies = game.getEnemy();
		coins = game.getCoins();
		keyAndDoor = game.getUtility();

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
		initButton();

	}

	private float dt_prec = -1f;

	@Override
	public void render(float delta) {

		if (!PAUSE) {

			if (dt_prec < 0) {
				delta = 0.01f;
				dt_prec = Float.MAX_VALUE;
			}

			if (delta > 2 * dt_prec) {
				delta = 2 * dt_prec;
			}
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			elapsedTime += delta;

			batch.setProjectionMatrix(camera.combined);

			background.update(delta);

			if (ready_for_next_level)
				renderDoor();

			camera.update();
			renderer.setView(camera);
			renderer.render();

			renderLives(delta);
			renderTimer(delta);
			renderScore(delta);

			if (!(player.getState() == PlayerState.WAITING)) {
				updatePlayer(delta);
			}
			renderPlayer();
			if (!game.RESUME)
				updateEnemy(delta);
			renderEnemy();

			renderCoins();

			updateBullets(delta);
			renderBullets();
			renderGenericAnimations();

			resumePlayer();

			dt_prec = delta;
		}

	}

	private boolean shot = false;

	private void updatePlayer(final float delta) {

		if (!game.isOver() && !game.RESUME) {
			if ((Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
					&& !(player.getState() == PlayerState.JUMPING)/*
																	 * && !game.
																	 * PLAYER_IS_FALLING
																	 */) {
				player.setDirection('r');
				player.setState(PlayerState.RUNNING);
				game.resumeCondition();
				if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
					player.setState(PlayerState.JUMPING);
					game.resumeCondition();
				}

			} else if ((Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
					&& !(player.getState() == PlayerState.JUMPING) /*
																	 * && !game.
																	 * PLAYER_IS_FALLING
																	 */) {
				player.setDirection('l');
				player.setState(PlayerState.RUNNING);
				game.resumeCondition();
				if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
					player.setState(PlayerState.JUMPING);
					game.resumeCondition();
				}

			} else if (Gdx.input.isKeyJustPressed(Input.Keys.W) && !(player.getState() == PlayerState.JUMPING)
					&& !game.PLAYER_IS_FALLING) {
				player.setState(PlayerState.JUMPING);
				player.VERTICAL_JUMP = true;
				game.resumeCondition();

			}
			if (player.VERTICAL_JUMP
					&& (Gdx.input.isKeyJustPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))) {
				player.setDirection('r');
				game.moveWhileJumping = true;
			} else if (player.VERTICAL_JUMP && (Gdx.input.isKeyJustPressed(Input.Keys.A))
					|| Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				player.setDirection('l');
				game.moveWhileJumping = true;
			}

			// input selezione pistola
			if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
				if (player.getGun()) {
					player.setGun(false);
				} else {
					player.setGun(true);
				}
			}
			// input sparo
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				// se il player ha la pistola, allora può sparare
				if (player.getGun()) {
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
					Audio.playShot();
					shot = true;
				}
			}

			/**
			 * Se il personaggio è fermo, viene impostato il suo stato come
			 * 
			 * @param state
			 *            IDLING
			 */
			if (!(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT))
					&& !(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT))
					&& !(player.getState() == PlayerState.JUMPING)) {
				if (!game.PLAYER_IS_FALLING)
					player.setState(PlayerState.IDLING);
			}

			if (game.PLAYER_COLLISION) {
				player.setState(PlayerState.HIT);
				player.decreaseLives();
				game.RESUME = true;
				game.PLAYER_COLLISION = false;
			}

			updateCamera();
			if (game.handleScores()) {
				Audio.playCoin();
			}

			if (!ready_for_next_level && game.findMagicKey()) {
				ready_for_next_level = true;
				Audio.playMagicKey();
				for (StaticObject so : keyAndDoor) {
					if (((Obstacle) so).getType().equals("70")) {
						door_position = so.getPosition();
					}
					resetMapCell(so.getPosition());
				}
				keyAndDoor = null;

			}
		}

	}

	private void updateCamera() {
		float y_pos = (player.getPosition().y) * Asset.TILE;
		/* (y_pos >= game.getEndCamera() * 0.045) && */
		if (Math.abs(y_pos - (camera.position.x - 640)) > Asset.TILE * 3 && !player.VERTICAL_JUMP) {

			if (player.getState() == PlayerState.JUMPING) {
				camera.position.x += GameConfig.POSITIVE_JUMP_VELOCITY.y * 0.9;
			} else {
				camera.position.x += GameConfig.PLAYER_POS_VELOCITY.y * 1.2;
			}

			if (game.PLAYER_IS_FALLING && (camera.position.x - 640) > (player.getPosition().y * Asset.TILE) - 128) {
				camera.position.x -= GameConfig.PLAYER_POS_VELOCITY.y * 1.2;
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

		if (player_type == 1) {

			if (player.getGun()) {
				switch (player.getDirection()) {
				case 'r': {
					if (shot && !(player.getState() == PlayerState.RUNNING)) {
						if (e_time > 0.3f) {
							shot = false;
							e_time = 0;
						}
						drawAnimation(player, "player_m_shot_right");
						e_time += Gdx.graphics.getDeltaTime();
					} else if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_m_dizzy_right");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_m_idle_with_gun_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_m_run_with_gun_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_m_jump_with_gun_right");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_m_flash_idle_right");
					}

					break;
				} // end of case 'r'
				case 'l': {
					if (shot && !(player.getState() == PlayerState.RUNNING)) {
						if (e_time > 0.3f) {
							shot = false;
							e_time = 0;
						}
						drawAnimation(player, "player_m_shot_left");
						e_time += Gdx.graphics.getDeltaTime();
					} else if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_m_dizzy_right");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_m_idle_with_gun_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_m_run_with_gun_left");

					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_m_jump_with_gun_left");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_m_flash_idle_left");
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
					if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_m_dizzy_right");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_idle_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_run_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_jump_right");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_m_flash_idle_right");
					}

					break;
				} // end of case 'r'
				case 'l': {
					if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_m_dizzy_left");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_idle_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_run_left");

					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_jump_left");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_m_flash_idle_left");
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
			if (player.getGun()) {
				switch (player.getDirection()) {
				case 'r': {
					if (shot && !(player.getState() == PlayerState.RUNNING)) {
						if (e_time > 0.3f) {
							shot = false;
							e_time = 0;
						}
						drawAnimation(player, "player_w_shot_right");
						e_time += Gdx.graphics.getDeltaTime();
					} else if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_w_dizzy_right");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_w_idle_with_gun_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_w_run_with_gun_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_w_jump_with_gun_right");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_w_flash_idle_right");
					}

					break;
				} // end of case 'r'
				case 'l': {
					if (shot && !(player.getState() == PlayerState.RUNNING)) {
						if (e_time > 0.3f) {
							shot = false;
							e_time = 0;
						}
						drawAnimation(player, "player_w_shot_left");
						e_time += Gdx.graphics.getDeltaTime();
					} else if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_w_dizzy_right");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_w_idle_with_gun_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_w_run_with_gun_left");

					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_w_jump_with_gun_left");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_w_flash_idle_left");
					}

					break;
				}
				default:
					break;
				}// end of switch

			} else { // PLAYER SENZA PISTOLA

				switch (player.getDirection()) {
				case 'r': {
					if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_w_dizzy_right");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_w_idle_right");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_w_run_right");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_w_jump_right");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_w_flash_idle_right");
					}

					break;
				} // end of case 'r'
				case 'l': {
					if (player.getState() == PlayerState.HIT) {
						drawAnimation(player, "player_w_dizzy_left");
					} else if (player.getState() == PlayerState.IDLING || game.PLAYER_IS_FALLING
							|| player.getState() == PlayerState.WAITING) {
						drawAnimation(player, "player_w_idle_left");
					} else if (player.getState() == PlayerState.RUNNING) {
						drawAnimation(player, "player_w_run_left");
					} else if (player.getState() == PlayerState.JUMPING) {
						drawAnimation(player, "player_w_jump_left");
					} else if (player.getState() == PlayerState.FLASH) {
						drawAnimation(player, "player_w_flash_idle_left");
					}

					break;
				}
				default:
					break;
				}// end of switch}

			} // end else without gun
		} // end else player woman
	}

	/**
	 * Variabili per la stampa della vita del singolo nemico
	 */
	private void renderLifeBar(Enemy e) {

		float w;
		float h = GameConfig.BAR_HEIGHT;

		// posizione del nemico
		float xP = ((e.getPosition().y) * Asset.TILE);
		float yP = ((Asset.HEIGHT / Asset.TILE) - e.getPosition().x - 1) * Asset.TILE;

		// posiziono la barra sopra il nemico
		xP -= 11;
		yP += 138;

		batch.begin();

		batch.draw(Textures.LIFE_BAR_BACKGROUND, xP, yP);

		w = e.getLifeStatus();
		if (w >= 0) {
			batch.draw(Textures.LIFE_BAR, xP, yP, w, h);
		}

		batch.end();

	}

	private void updateEnemy(float delta) {
		game.moveEnemy(delta);
	}

	private void renderEnemy() {
		for (StaticObject obj : enemies) {
			Enemy e = (Enemy) obj;
			renderLifeBar(e);
			if (e.getState() == EnemyState.RUNNING) {
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
			} else if (e.getState() == EnemyState.FOLLOWING_PLAYER) {
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

	private Vector2 door_position;

	private void renderDoor() {
		float x = (door_position.y) * Asset.TILE;
		float y = ((Asset.HEIGHT / Asset.TILE) - door_position.x - 1) * Asset.TILE;

		batch.begin();
		batch.draw(Textures.DOOR, x, y);
		batch.end();
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
			if (player.getDirection() == 'l') {
				sprite.flip(true, false);
			}
			for (Bullet b : game.getBullets()) {
				float xB = (b.getPosition().y) * Asset.TILE;
				float yB = ((Asset.HEIGHT / Asset.TILE) - b.getPosition().x - 1) * Asset.TILE;

				batch.draw(sprite, xB, yB);
			}
		}
		batch.end();
	}

	int clock = 0;
	float deltaTime = 0;

	private void updateTimer(float delta) {

		deltaTime += Gdx.graphics.getDeltaTime();
		if ((int) deltaTime > clock) {
			clock = (int) deltaTime;
			timer();
		}

		/*
		 * batch.begin(); Texture clock = new Texture(Asset.CLOCK); Texture d1 =
		 * Textures.get("D" + digit[i--]); Texture d2 = Textures.get("D" +
		 * digit[i--]); Texture d3 = Textures.get("D" + digit[i--]); Texture d4
		 * = Textures.get("D" + digit[i]);
		 * 
		 * Texture dp = Textures.get("DP");
		 * 
		 * int width = 15 + 10; float cameraHalfWidth = camera.viewportWidth *
		 * .5f; float pos_x = (camera.position.x - cameraHalfWidth) + 545; int
		 * pos_y = GameConfig.HP+4;
		 * 
		 * //batch.draw(clock,pos_x,pos_y-2);
		 * 
		 * pos_x += width+25; batch.draw(d4, pos_x, pos_y); pos_x += width;
		 * batch.draw(d3, pos_x, pos_y); pos_x += width - 5; batch.draw(dp,
		 * pos_x, pos_y); pos_x += width - 15; batch.draw(d2, pos_x, pos_y);
		 * pos_x += width; batch.draw(d1, pos_x, pos_y);
		 * 
		 * batch.end();
		 */

	}

	protected boolean times_end = false;

	private void timer() {

		int index = nDigits - 1;
		if (digit[index] != 0) { // settaggio dei secondi
			digit[index]--;
		} else {
			if (digit[index - 1] != 0) {// settaggio dei secondi: 59 a
										// decremento
				digit[index - 1]--;
				digit[index] = 9;
			} else {
				if (digit[index - 2] != 0) { // settaggio dei minuti
					digit[index - 2]--;
					digit[index - 1] = 5;
					digit[index] = 9;
				} else {
					if (digit[index - 3] != 0) {// settaggio dei minuti e
												// secondi: (--)9:59, dove(--) è
												// il minuto decrem.
						digit[index - 3]--;
						digit[index - 2] = 9;
						digit[index - 1] = 5;
						digit[index] = 9;
					} else {
						times_end = true;
						if (!(player.getState() == PlayerState.WAITING))
							player.setState(PlayerState.DEAD);
					}
				}
			}
		}

	}

	private void turnTimeInMoney() {
		timer();
		player.score(20);

	}

	private void updateLives() {
		// Texture vita
		Texture texture = Textures.LIFE;
		Texture heart = Textures.HEART;
		Texture syb_x = Textures.SYMBOL_X;

		int width = heart.getWidth() + 15;

		float cameraHalfWidth = camera.viewportWidth * .5f;
		float x1 = (camera.position.x - cameraHalfWidth) + 10;

		int y = GameConfig.HP;

		batch.begin();

		// batch.draw(heart, x1, y-2); //stampa riconoscitore cuore

		x1 += width;
		// batch.draw(texture, x1, y); //stampa di un cuore
		x1 += 50;
		y += 6;
		// batch.draw(syb_x, x1, y); //stampa della x ( il simbolo che precede
		// il numero di vite)
		int lives = player.getLives();
		if (lives < 10) {
			Texture c1 = Textures.get("D" + lives);
			x1 += 40;
			batch.draw(c1, x1, y);
		} else {
			int tmp = lives / 10;
			Texture c1 = Textures.get("D" + tmp);
			tmp = lives % 10;
			Texture c2 = Textures.get("D" + tmp);
			x1 += 40;
			batch.draw(c1, x1, y);
			x1 += 17;
			batch.draw(c2, x1, y);
		}

		batch.end();
	}

	private void updateScores() {
		int coins = game.getCoinsCount();
		Texture coin = Textures.COIN;
		Texture score = Textures.SCORE;

		float cameraHalfWidth = camera.viewportWidth * .5f;
		float pos_x_coin = (camera.position.x - cameraHalfWidth) + 320;
		float pos_x_score = (camera.position.x - cameraHalfWidth) + 320;

		batch.begin();

		// sprite numero di monete
		// batch.draw(coin,pos_x_coin,GameConfig.HP,39,40);
		int y = GameConfig.HP + 4;
		if (coins < 10) {
			Texture c1 = Textures.get("D" + coins);
			pos_x_coin += 55;
			batch.draw(c1, pos_x_coin, y);
		} else {
			int tmp = coins / 10;
			Texture c1 = Textures.get("D" + tmp);
			tmp = coins % 10;
			Texture c2 = Textures.get("D" + tmp);
			pos_x_coin += 55;
			batch.draw(c1, pos_x_coin, y);
			pos_x_coin += 17;
			batch.draw(c2, pos_x_coin, y);
		}

		pos_x_score += 550;
		// sprite punteggio
		// batch.draw(score,pos_x_score,GameConfig.HP);
		pos_x_score += 50;
		ArrayList<Integer> digits = handleDigits(game.getScore());
		for (int i = digits.size() - 1; i >= 0; i--) {
			Texture t = Textures.get("D" + digits.get(i));
			batch.draw(t, pos_x_score, y);
			pos_x_score += 20;
		}

		digits.clear();

		batch.end();
	}

	private float drawingTime = 0.7f;
	private float timer_limit = 1.2f;
	private int step_counter = 0;

	private void renderGenericAnimations() {
		toDrawObj = game.toDraw;
		if (toDrawObj == null) {
			return;
		}

		if (drawingTime < timer_limit) {
			if (toDrawObj instanceof Obstacle) {
				if ((int) drawingTime == 0) {
					resetMapCell(toDrawObj.getPosition());
				}
				Obstacle tmp = (Obstacle) toDrawObj;
				if (tmp.getType().equals("19") || tmp.getType().equals("20")) {
					drawAnimation(tmp, "explosion");
				} else {

					drawAnimation(tmp.getPosition().x - 0.01f, tmp.getPosition().y + 0.5f, "little_coin");
					timer_limit = 1.5f;
				}
			} else if (toDrawObj instanceof Enemy) {
				Enemy e = (Enemy) toDrawObj;
				switch (e.getType()) {
				case "31": {
					if (e.getDirection() == 'r') {
						drawAnimation(e, "enemy3_die_right");
					} else {
						drawAnimation(e, "enemy3_die_left");
					}
					break;

				}
				case "32": {
					if (e.getDirection() == 'r') {
						drawAnimation(e, "enemy1_die_right");
					} else {
						drawAnimation(e, "enemy1_die_left");
					}
					break;
				}
				case "33": {
					if (e.getDirection() == 'r') {
						drawAnimation(e, "enemy2_die_right");
					} else {
						drawAnimation(e, "enemy2_die_left");
					}
					break;
				}
				default:
					break;
				}
			}

		} else {
			toDrawObj = null;
			game.toDraw = null;
			drawingTime = 0.7f;
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

		x -= step_counter * Gdx.graphics.getDeltaTime();
		if (!game.animationCollision(x, y)) {
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

	private void resetMapCell(Vector2 pos) {
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

	private void initButton() {

		table = new Table();
		stage = new Stage();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.input.setInputProcessor(stage);

		Image heart = new Image(Textures.HEART);
		ImageButton heartB = new ImageButton(heart.getDrawable());

		Image life = new Image(Textures.LIFE);
		ImageButton lifeB = new ImageButton(life.getDrawable());

		Image symbol_x = new Image(Textures.SYMBOL_X);
		ImageButton symbol_xB = new ImageButton(symbol_x.getDrawable());

		Image coin = new Image(Textures.COIN);
		ImageButton coinB = new ImageButton(coin.getDrawable());

		Image score = new Image(Textures.SCORE);
		ImageButton scoreB = new ImageButton(score.getDrawable());

		Image clock = new Image(Textures.CLOCK);
		ImageButton clockB = new ImageButton(clock.getDrawable());

		// immagine decorativa per le vite
		int x = 10, y = GameConfig.HP;
		y -= 2;
		heartB.setPosition(x, y);

		// immagine vita
		x += 55;
		y += 2;
		lifeB.setPosition(x, y);

		// immagine "x" separatore vita da numero
		x += 50;
		y += 6;
		symbol_xB.setPosition(x, y);

		// immagine rappresentante il numero di coin
		x = 320;
		y -= 6;
		coinB.setPosition(x, y);

		// immagine rappresentante il punteggio
		x += 550;
		scoreB.setPosition(x, y);

		// immagine rappresentante il timer
		x = 545;
		y += 2;
		clockB.setPosition(x, y);

		table.addActor(heartB);
		table.addActor(lifeB);
		table.addActor(symbol_xB);
		table.addActor(coinB);
		table.addActor(scoreB);
		table.addActor(clockB);
		stage.addActor(table);
	}

	/**
	 * UPDATE VITE
	 */
	private void renderLives(float delta) {

		int x = 155;
		int y = GameConfig.HP + 6;
		int lives = player.getLives();

		printDigits(lives, x, y, 21, delta);
	}

	/**
	 * UPDATE GAME TIMER
	 * 
	 */
	private void renderTimer(float delta) {
		deltaTime += Gdx.graphics.getDeltaTime();
		if ((int) deltaTime > clock) {
			clock = (int) deltaTime;
			timer();
		}

		int i = nDigits - 1;
		Image d1 = new Image(Textures.get("D" + digit[i--]));
		ImageButton d1B = new ImageButton(d1.getDrawable());

		Image d2 = new Image(Textures.get("D" + digit[i--]));
		ImageButton d2B = new ImageButton(d2.getDrawable());

		Image d3 = new Image(Textures.get("D" + digit[i--]));
		ImageButton d3B = new ImageButton(d3.getDrawable());

		Image d4 = new Image(Textures.get("D" + digit[i]));
		ImageButton d4B = new ImageButton(d4.getDrawable());

		Image dP = new Image(Textures.get("DP"));
		ImageButton dpB = new ImageButton(dP.getDrawable());

		int x = 605;
		int y = GameConfig.HP + 4;
		int width = 15 + 10;

		d4B.setPosition(x, y);
		x += width;
		d3B.setPosition(x, y);
		x += width - 5;
		dpB.setPosition(x, y);
		x += width - 15;
		d2B.setPosition(x, y);
		x += width;
		d1B.setPosition(x, y);

		table.addActor(d4B);
		table.addActor(d3B);
		table.addActor(dpB);
		table.addActor(d2B);
		table.addActor(d1B);

		stage.act(delta);
		stage.draw();
		table.removeActor(d4B);
		table.removeActor(d3B);
		table.removeActor(dpB);
		table.removeActor(d2B);
		table.removeActor(d1B);

	}

	/**
	 * UPDATE COINS & SCORE
	 */
	private void renderScore(float delta) {

		int coins = game.getCoinsCount();

		// monete
		int pos_x_coin = 375;
		int y = GameConfig.HP + 4;
		printDigits(coins, pos_x_coin, y, 17, delta);

		// punteggio
		float pos_x_score = 920;
		ArrayList<Integer> digits = handleDigits(game.getScore());
		for (int i = digits.size() - 1; i >= 0; i--) {
			Image img = new Image(Textures.get("D" + digits.get(i)));
			ImageButton imgB = new ImageButton(img.getDrawable());
			imgB.setPosition(pos_x_score, y);
			pos_x_score += 20;
			table.addActor(imgB);
			stage.act(delta);
			stage.draw();
			table.removeActor(imgB);

		}

		digits.clear();

	}

	private void printDigits(int number_to_check, int x, int y, int space, float delta) {

		if (number_to_check < 10) {
			Image c = new Image(Textures.get("D" + number_to_check));
			ImageButton c1 = new ImageButton(c.getDrawable());
			c1.setPosition(x, y);
			table.addActor(c1);

			stage.act(delta);
			stage.draw();
			table.removeActor(c1);
		} else {
			int tmp = number_to_check / 10;
			Image c = new Image(Textures.get("D" + tmp));
			ImageButton c1 = new ImageButton(c.getDrawable());

			tmp = number_to_check % 10;

			Image c2 = new Image(Textures.get("D" + tmp));
			ImageButton c2B = new ImageButton(c2.getDrawable());

			c1.setPosition(x, y);
			table.addActor(c1);

			x += space;
			c2B.setPosition(x, y);
			table.addActor(c2B);

			stage.act(delta);
			stage.draw();
			table.removeActor(c1);
			table.removeActor(c2B);
		}

	}

	public static ArrayList<Integer> handleDigits(int score) {

		ArrayList<Integer> digits = new ArrayList<>();
		if (score < 10) {
			digits.add(score);
		} else {
			int elem = score;
			while (elem != 0) {
				elem = score / 10;
				score = score % 10;
				digits.add(score);
				score = elem;
			}

		}

		return digits;
	}

	private float waiting_time;
	private boolean stepOver = false;

	@SuppressWarnings("deprecation")
	private void resumePlayer() {
		/*
		 * Il player è riuscito ad aprire la porta. Passa al livello successivo.
		 */
		if (ready_for_next_level) {
			if (game.findCollision(player,
					new StaticObject(new Vector2(door_position.x, door_position.y + 1), new Vector2()))) {
				jump_player.stop();
				player.setState(PlayerState.WAITING);
				turnTimeInMoney();
			}
		}
		if (times_end && player.getState() == PlayerState.WAITING) {
			GameConfig.BEST_SCORE = player.getScore();
			GameConfig.NUM_LIVES = player.getLives();
			if (game.getCoinsCount() == game.getMaxCoinsCount()) {
				GameConfig.NUM_LIVES += 2;
			}
			Audio.GAME_MUSIC = false;
			Audio.BACKGROUND_MUSIC = true;
			Audio.game_music.pause();
			Audio.playGameMenuMusic();
			((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(LevelUp.getInstance());
		} else if (player.getLives() == 0 || (player.getState() == PlayerState.DEAD)) {
			player.setState(PlayerState.DEAD);
			game.RESUME = false;
			GameConfig.BEST_SCORE = player.getScore();
			jump_player.stop();

			Audio.GAME_MUSIC = false;
			Audio.BACKGROUND_MUSIC = true;
			Audio.game_music.pause();
			Audio.playGameMenuMusic();
			((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(HandleGameOver.getInstance());

		}

		if (game.RESUME) {
			/*
			 * Tempo di attesa prima che il player ritorni sulla mappa
			 */
			if (waiting_time > 1.3f && !stepOver) {
				game.findNewPlayerPosition(player.getPosition());
				stepOver = true;
				waiting_time = 6;

			} else {
				// reset della camera
				camera.position.x -= 120 * Gdx.graphics.getDeltaTime();
				if ((camera.position.x - (camera.viewportWidth * .5f)) <= mapLeft)
					camera.position.x = mapLeft + (camera.viewportWidth * .5f);
				game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);
				game.setEndCamera(game.getEndCamera() + Asset.TILE * 1.5f);
				player.setState(PlayerState.HIT);
			}

			if ((camera.position.x - 640) > player.getPosition().y * Asset.TILE) {
				camera.position.x -= 120 * Gdx.graphics.getDeltaTime();
				if ((camera.position.x - (camera.viewportWidth * .5f)) <= mapLeft)
					camera.position.x = mapLeft + (camera.viewportWidth * .5f);
				game.setCamera((camera.position.x - camera.viewportWidth / 2) / Asset.TILE);
				game.setEndCamera(game.getEndCamera() + Asset.TILE * 1.5f);
				return;
			}
			/*
			 * Tempo di attesa stampa animazione
			 */
			if (stepOver) {
				player.setState(PlayerState.FLASH);
				if (waiting_time > 9.8f) {

					// reset del timer
					initTimer();

					game.RESUME = false;
					stepOver = false;
					waiting_time = 0;
				}
			}

			waiting_time += Gdx.graphics.getDeltaTime();
		}
	}
	
	public int getPlayer1Type(){
		return player_type;
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

}
