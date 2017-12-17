package it.unical.mat.igpe17.game.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.DynamicObject;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.objects.Obstacle;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {

	// set di default
	private static String LEVEL = "levels/firstLevel.tmx";

	private Player player;
	private List<Ground> groundObjects;
	private List<Obstacle> obstacleObjects;

	private final int NUM_ENEMY = 10;
	private List<Enemy> enemy;

	private int row;
	private int column;
	private float camera;

	boolean setStartPosition = true;
	Vector2 startPosition = new Vector2();

	private Lock lock;
	private Condition condition;

	public Game() {
		player = null;
		groundObjects = null;
		enemy = new LinkedList<Enemy>();

		lock = new ReentrantLock();
		condition = lock.newCondition();
	}

	/*
	 * Creo il livello, il primo � impostato da default
	 */
	public void loadLevel() {

		Reader reader = new Reader(LEVEL);
		reader.parse();
		row = reader.getColumn();
		column = reader.getRow();
		groundObjects = reader.getGround();
		obstacleObjects = reader.getObstacle();
		enemy = reader.getEnemy();
		player = reader.getPlayer();

		// generaNemici();

		camera = 0f;
	}

	public void movePlayer(char dir, float dt) {

		switch (dir) {
		case 'r': {
			movePlayerToRight(dt);
			break;
		}
		case 'l': {
			movePlayerToLeft(dt);
			break;
		}
		case 'x': {
			startPosition = player.getPosition();
			makePlayerJump(dt);
			break;
		}
		default:
			break;
		}

	}

	private void movePlayerToLeft(float dt) {
		float x = player.getPosition().x;
		float y = player.getPosition().y;

		/*
		 * Se l'ascissa del player supera a sinistra il limite della camera,
		 * return della funzione
		 */
		if (y - 1 < (camera - 0.95f)) {
			return;
		}

		/*
		 * Se il player pu� andare a sinistra, viene settata la nuova posizione
		 */
		if (checkGroundCollision(((int) x) + 1, (int) y) && checkObstaclesCollision((int) x, (int) y, false)) {
			Vector2 tmp = new Vector2();
			tmp.x = GameConfig.PLAYER_NEG_VELOCITY.x;
			tmp.y = GameConfig.PLAYER_NEG_VELOCITY.y;
			player.move(tmp, dt);

		}

	}

	private void movePlayerToRight(float dt) {

		float x = player.getPosition().x;
		float y = player.getPosition().y;

		/*
		 * Se l'ascissa del player supera a destra il limite della camera,
		 * return della funzione
		 */
		if (y + 1 >= column)
			return;
		/*
		 * Se il player pu� andare a destra, viene settata la nuova posizione
		 */
		if (checkGroundCollision(((int) x) + 1, ((int) y) + 1)
				&& checkObstaclesCollision((int) x, ((int) y) + 1, false)) {
			Vector2 tmp = new Vector2();
			tmp.x = GameConfig.PLAYER_POS_VELOCITY.x;
			tmp.y = GameConfig.PLAYER_POS_VELOCITY.y;

			player.move(tmp, dt);
		}
	}

	public static boolean jumping = false;

	public void makePlayerJump(float delta) {
		lock.lock();
		try {
			while (player.getState() != PlayerState.JUMPING) {
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			int xp = (int) player.getPosition().x;
			int yp = (int) player.getPosition().y;

			// memorizzo la posizione del player prima del salto
			if (setStartPosition) {
				startPosition.x = player.getPosition().x;
				startPosition.y = player.getPosition().y;
				setStartPosition = false;
			}

			// salto solo in verticale
			if (player.VERTICAL_JUMP) {
				if ((xp < startPosition.x - 3) || playerIsCollidingWithGround(xp, yp)) {
					player.setForce(GameConfig.JUMP_POS_FORCE);
				}
				player.verticalJump(delta);
			} else {
				if ((xp < startPosition.x - 3) || playerIsCollidingWithGround(xp, yp))
					player.swap();

				player.jump(delta);
			}

			// il player deve rimanere dentro la camera
			checkPlayerBounds();

			// verifico che ritorni sul terreno e si fermi
			if (checkGroundCollision(((int) player.getPosition().x) + 1, Math.round((player.getPosition().y)))
					|| checkObstaclesCollision(((int) player.getPosition().x) + 1, Math.round((player.getPosition().y)),
							true)) {
				player.setPosition(new Vector2((int) player.getPosition().x, player.getPosition().y));
				setStartPosition = true;
				jumping = false;
				player.VERTICAL_JUMP = false;
				player.setForce(GameConfig.JUMP_NEG_FORCE);
				player.setState(PlayerState.IDLING);
				player.reset();
				return;
			}
		} finally {
			lock.unlock();
		}

	}

	private final boolean checkGroundCollision(int x, int y) {
		for (Ground g : groundObjects) {
			if (g.getPosition().x == x) {
				if (g.getPosition().y == y) {
					if ((g.getType().equals("1") || g.getType().equals("2") || g.getType().equals("3")
							|| g.getType().equals("7") || g.getType().equals("11") || g.getType().equals("14")
							|| g.getType().equals("15") || g.getType().equals("16"))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * @return true se non c'� collisione con l'ostacolo
	 */
	private final boolean checkObstaclesCollision(int x, int y, boolean top) {
		if (top) {
			for (Obstacle o : obstacleObjects) {
				if (o.getPosition().x == x && o.getPosition().y == y) {
					if (o.getType().equals("25"))
						return true;
				}
			}
			return false;
		} else {
			for (Obstacle o : obstacleObjects) {
				if (o.getPosition().x == x && o.getPosition().y == y) {
					return false;
				}

			}
		}

		return true;
	}

	private final boolean checkFinalGroundCollision(int x, int y) {
		for (Ground g : groundObjects) {
			if (g.getPosition().x == x) {
				if (g.getPosition().y == y) {
					if ((g.getType().equals("1") || g.getType().equals("3") || g.getType().equals("14")
							|| g.getType().equals("16"))) {
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean playerIsCollidingWithGround(int x, int y) {

		int tmpX = x - GameConfig.SIZE_PLAYER_X;
		int tmpY = y;

		/*
		 * Collisione con oggetti che si trovano sopra il player
		 */
		for (Ground g : groundObjects) {
			if (g.getPosition().x == tmpX && g.getPosition().y == tmpY)
				return true;
		}

		/*
		 * Collisione a sinistra del player
		 */
		tmpX = x;
		tmpY = y;
		if (player.getDirection() == 'l') {
			for (int i = 0; i < GameConfig.SIZE_PLAYER_X; i++) {
				for (Ground g : groundObjects) {
					if (g.getPosition().x == tmpX && g.getPosition().y == tmpY) {
						return true;
					}
				}
				x--;
			}
		}

		/*
		 * Collisione a destra del player
		 */
		tmpX = x;
		tmpY = y;
		if (player.getDirection() == 'r') {
			for (int i = 0; i < GameConfig.SIZE_PLAYER_X; i++) {
				for (Ground g : groundObjects) {
					if (g.getPosition().x == tmpX && g.getPosition().y == tmpY) {
						return true;
					}
				}
				x--;
			}
		}

		return false;
	}

	private void checkPlayerBounds() {

		float x = player.getPosition().x;
		float y = player.getPosition().y;

		/*
		 * Posizione del player dentro la camera: lato sinistro
		 */

		if (y < camera) {
			player.setPosition(new Vector2(x, camera));
		}

		/*
		 * Posizione del player dentro la camera: lato superiore
		 */
		if (x - GameConfig.SIZE_PLAYER_Y <= 0) {
			player.setPosition(new Vector2(x - GameConfig.SIZE_PLAYER_Y, y));
		}

		/*
		 * Posizione del player dentro la camera: lato destro
		 */
		if (y + GameConfig.SIZE_PLAYER_X > column) {
			player.setPosition(new Vector2(x, column - 1));
		}

	}
	
	// private boolean generaPosEnemy() {
	// // due numeri random compresi tra i margini del livello
	// // aggiunti alla lista di nemici
	// Random random = new Random();
	//
	// int n = getRow();
	// int n2 = getColumn();
	//
	// int x = random.nextInt(n);
	// int y = random.nextInt(n2);
	// // controllare che le posizioni che ho randomizzato si trovino su un
	// // oggetto terreno
	//
	// if (checkGroundCollision(x + 1, y)) {
	//
	// Enemy tmp = new Enemy(new Vector2(x, y), new
	// Vector2(GameConfig.SIZE_ENEMY_X, GameConfig.SIZE_ENEMY_Y),
	// 'l');
	//
	// enemy.add(tmp);
	// return true;
	// }
	//
	// return false;
	//
	// }

	public void moveEnemy(float dt) {
		for (Enemy e : enemy) {

			// prendiamo le coordinate di un nemico per volta

			float x = e.getPosition().x;
			float y = e.getPosition().y;


			switch (e.getDirection()) {
			
			case 'l': {
				if (e.getMovesLeft() == GameConfig.SIZE_MOVE_ENEMY) {
					e.setDirection('r');
					e.setMovesLeft(0);
					System.out.println("l");
					break;

				}
				
				if (checkGroundCollision((int) ++x, (int) y)) {

					Vector2 tmp = new Vector2();
					tmp.x = GameConfig.PLAYER_NEG_VELOCITY.x;
					tmp.y = GameConfig.PLAYER_NEG_VELOCITY.y;
					e.move(tmp, dt);
					e.setMovesLeft(e.getMovesLeft()+1);
//					System.out.println(movesLeft +" ")
					
				
					
					int x1 = (int) (e.getPosition().x);
					int y1 = (int) (e.getPosition().y);
					
					
					
					if (checkFinalGroundCollision(++x1, y1)) {
						e.setDirection('r');
					}
				}
				break;
			}
			case 'r': {
				
				if (e.getMovesRight() == GameConfig.SIZE_MOVE_ENEMY ) {
					e.setDirection('l');
					e.setMovesRight(0);
					System.out.println("r");
					break;
				}
				
				if (checkGroundCollision((int) ++x, (int) ++y)) {

					Vector2 tmp = new Vector2();
					tmp.x = GameConfig.PLAYER_POS_VELOCITY.x;
					tmp.y = GameConfig.PLAYER_POS_VELOCITY.y;
					e.move(tmp, dt);
					e.setMovesRight(e.getMovesRight()+1);
					int x1 = (int) (e.getPosition().x);
					int y1 = (int) (e.getPosition().y);

					if (checkFinalGroundCollision(++x1, y1)) {
						e.setDirection('l');
					}
				}

				break;
			}
			default:
				break;
			}
		}
	
	}

	public void setLevel(String level) {
		LEVEL = level;
	}

	public int getNumEnemy() {
		return NUM_ENEMY;
	}

	public final List<Ground> getGround() {
		return groundObjects;
	}

	public final Player getPlayer() {
		return player;
	}

	public final List<Enemy> getEnemy() {
		return enemy;
	}

	public final int getRow() {
		return row;
	}

	public final int getColumn() {
		return column;
	}

	public void setCamera(float position) {
		camera = position;
	}

	public void resumeJumpPlayer() {
		lock.lock();
		player.setState(PlayerState.JUMPING);
		condition.signal();
		lock.unlock();
	}

}
