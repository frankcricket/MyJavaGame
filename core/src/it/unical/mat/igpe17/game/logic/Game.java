package it.unical.mat.igpe17.game.logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.EnemyState;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.Audio;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.objects.Obstacle;
import it.unical.mat.igpe17.game.objects.StaticObject;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {

	// set di default
	//public static String LEVEL = "levels/firstLevel.tmx";
	public static String LEVEL = "levels/completeLevel.tmx";
//	public static String LEVEL = "levels/t1.tmx";
//	public static String LEVEL = "levels/default_levels/test_door.tmx";

	private Player player;
	private List<StaticObject> groundObjects;
	private List<StaticObject> obstacleObjects;
	private List<StaticObject> coins;
	private List<Bullet> bullets;
	private List<StaticObject> enemy;
	
	private List<StaticObject> utility;
	
	public StaticObject toDraw;

	private int row;
	private int column;

	/*
	 * Inizio e fine della camera
	 */
	private float camera;
	private float end_camera;

	/*
	 * Velocità per la caduta del player
	 */
	private float pos_fall;
	private float neg_fall;
	public static boolean PLAYER_IS_FALLING;
	public static boolean PLAYER_COLLISION;

	public static boolean RESUME;

	private int current_coin_count;

	private Lock lock;
	private Condition condition;

	public Game() {
		player = null;
		groundObjects = null;
		enemy = null;
		coins = null;
		bullets = null;
		toDraw = null;
		utility = null;
		
		initStaticVariable();

		pos_fall = GameConfig.FALLING_POS_VELOCITY.y;
		neg_fall = GameConfig.FALLING_NEG_VELOCITY.y;

		lock = new ReentrantLock();
		condition = lock.newCondition();
	}

	/*
	 * Creo il livello, il primo è impostato da default
	 */
	public void loadLevel() {

		Reader reader = new Reader(LEVEL);
		reader.parse();
		row = reader.getColumn();
		column = reader.getRow();
		groundObjects = reader.getGround();
		obstacleObjects = reader.getObstacle();
		coins = reader.getCoins();
		enemy = reader.getEnemy();
		player = reader.getPlayer();
		
		utility = reader.getUtility();

		bullets = new LinkedList<>();
		
		toDraw = null;

		camera = 0f;
		end_camera = Asset.WIDTH;

		current_coin_count = 0;
	}
	
	private void initStaticVariable(){
		PLAYER_IS_FALLING = false;
		PLAYER_COLLISION = false;
		RESUME = false;
	}

	public void movePlayer(float dt) {
		switch (player.getState()) {
		case RUNNING: {
			if (player.getDirection() == 'r')
				movePlayerToRight(dt);
			else
				movePlayerToLeft(dt);
			break;
		}
		case JUMPING: {
			makePlayerJump(dt);
			break;
		}
		default:
			break;
		}
	}

	// queste due variabili vengono utilizzate per capire se il player sta
	// cadendo
	private boolean check_g1_l;
	private boolean check_g2_l;

	private void movePlayerToLeft(float dt) {
		lock.lock();

		float y = player.getPosition().y;

		check_g1_l = false;
		check_g2_l = true;

		try {
			while (player.getState() != PlayerState.RUNNING) {
				condition.await();
			}

			/*
			 * Se l'ascissa del player supera a sinistra il limite della camera,
			 * return della funzione
			 */
			if (y - 1 < (camera - 0.95f)) {
				return;
			}
			if (player.getPosition().x >= row + GameConfig.SIZE_PLAYER_X) {
				neg_fall = GameConfig.FALLING_NEG_VELOCITY.y;

				// gestione perdita vita
				player.decreaseLives();
				RESUME = true;
				PLAYER_IS_FALLING = false;
				player.setState(PlayerState.IDLING);

				check_g1_l = false;
				check_g2_l = false;
				return;
			}

			/*
			 * Se il player può andare a sinistra, viene settata la nuova
			 * posizione
			 */
			if (validatePosition()) {
				check_g1_l = true;
				player.setPosition(new Vector2((int) player.getPosition().x, player.getPosition().y));
				if (!isObstaclesCollision()) {
					Vector2 tmp = new Vector2();
					tmp.x = GameConfig.PLAYER_NEG_VELOCITY.x;
					tmp.y = GameConfig.PLAYER_NEG_VELOCITY.y;
					player.move(tmp, dt);
					PLAYER_IS_FALLING = false;
					neg_fall = GameConfig.FALLING_NEG_VELOCITY.y;
					check_g2_l = true;
				}
			}
			if (!check_g1_l && check_g2_l) {
				if (neg_fall >= -4.0f) {
					neg_fall = 0;
				}
				Vector2 tmp = new Vector2();
				tmp.x = GameConfig.GRAVITY.x;
				tmp.y = neg_fall;
				player.move(tmp, dt);
				PLAYER_IS_FALLING = true;
				neg_fall += 1.7f;
			}

		} catch (InterruptedException e) {

		} finally {
			lock.unlock();
		}

	}

	// queste due variabili vengono utilizzate per capire se il player sta
	// cadendo
	private boolean check_g1_r;
	private boolean check_g2_r;

	private void movePlayerToRight(float dt) {
		lock.lock();

		float y = player.getPosition().y;

		check_g1_r = false;
		check_g2_r = true;

		try {
			while (player.getState() != PlayerState.RUNNING) {
				condition.await();
			}

			/*
			 * Se l'ascissa del player supera a destra il limite della camera,
			 * return della funzione
			 */
			if (y + 1 >= column)
				return;


			if (player.getPosition().x >= row + GameConfig.SIZE_PLAYER_Y) {
				pos_fall = GameConfig.FALLING_POS_VELOCITY.y;

				// gestione perdita vita
				player.decreaseLives();
				RESUME = true;
				PLAYER_IS_FALLING = false;
				player.setState(PlayerState.IDLING);

				check_g1_r = false;
				check_g2_r = false;
				return;
			}

			if (validatePosition()) {
				check_g1_r = true;
				player.setPosition(new Vector2((int) player.getPosition().x, player.getPosition().y));
				if (!isObstaclesCollision()) {
					Vector2 tmp = new Vector2();
					tmp.x = GameConfig.PLAYER_POS_VELOCITY.x;
					tmp.y = GameConfig.PLAYER_POS_VELOCITY.y;
					player.move(tmp, dt);
					PLAYER_IS_FALLING = false;
					pos_fall = GameConfig.FALLING_POS_VELOCITY.y;
					check_g2_r = true;
				}
			}
			/*
			 * Il player sta cadendo..
			 */
			if (!check_g1_r && check_g2_r) {
				if (pos_fall <= 4.0f) {
					pos_fall = 0;
				}
				Vector2 tmp = new Vector2();
				tmp.x = GameConfig.GRAVITY.x;
				tmp.y = pos_fall;
				player.move(tmp, dt);
				pos_fall -= 1.7f;
				PLAYER_IS_FALLING = true;
			}

		} catch (InterruptedException e) {

		} finally {
			lock.unlock();
		}

	}

	private boolean obstacleFound = false;
	public boolean moveWhileJumping = false;
	private boolean setStartPosition = true;
	private Vector2 startPosition = new Vector2();

	/**
	 * @param delta
	 */
	public void makePlayerJump(float delta) {
		lock.lock();
		try {
			while (player.getState() != PlayerState.JUMPING) {
				condition.await();

			}
			float xp = player.getPosition().x;
			float yp = player.getPosition().y;

			/*
			 * Verifica posizione player > dimensione schermo : BOTTOM
			 */
			if (player.getPosition().x >= row + GameConfig.SIZE_PLAYER_Y) {
				// gestione perdita vita
				player.decreaseLives();
				RESUME = true;
				player.setState(PlayerState.IDLING);
				PLAYER_IS_FALLING = false;
				return;
			}

			/*
			 * Memorizzazione posizione del player prima del salto
			 */
			if (setStartPosition) {
				startPosition.x = player.getPosition().x;
				startPosition.y = player.getPosition().y;
				setStartPosition = false;
			}

			if ((isObstaclesCollision() || checkPlayerBounds())){
				obstacleFound = true;
				
			}

			if (obstacleFound) {
				player.setForce(GameConfig.JUMP_POS_FORCE);
				player.verticalJump(delta);
			} else {

				if (player.VERTICAL_JUMP) {
					if ((int) xp < startPosition.x - 3) {
						player.setForce(GameConfig.JUMP_POS_FORCE);
					}
					player.verticalJump(delta);
					if (moveWhileJumping)
						if (player.getDirection() == 'r') {
							Vector2 tmp = new Vector2();
							tmp.x = 0;
							tmp.y = +2.3f;
							player.move(tmp, delta);
						} else {
							Vector2 tmp = new Vector2();
							tmp.x = 0;
							tmp.y = -2.3f;
							player.move(tmp, delta);
						}
				} else {
					if (((int) xp < startPosition.x - 4.1)) {
						player.swap();
					}
					player.jump(delta);
				}
			}

			xp = player.getPosition().x;
			yp = player.getPosition().y;

			int ytmp = Math.round(yp);
			if (yp - ytmp < 0.499f || ytmp - yp < 0.499f) {
				if (player.getDirection() == 'r')
					yp -= 0.3f;
				else
					yp += 0.3f;
			}

			if (validatePosition()) {
				player.setPosition(new Vector2((int) player.getPosition().x, player.getPosition().y));

				setStartPosition = true;
				player.VERTICAL_JUMP = false;
				PLAYER_IS_FALLING = false;
				obstacleFound = false;

				moveWhileJumping = false;

				player.setForce(GameConfig.JUMP_NEG_FORCE);
				player.setState(PlayerState.IDLING);
				player.reset();
			}

		} catch (InterruptedException e) {
		} finally {
			lock.unlock();
		}

	}
	
	private boolean isObstaclesCollision() {

		//TODO VERIFICARE BUG QUANDO RISORGE DOPO ESPLOSIONE BOMBA
		float left = player.getPosition().y;
		float bottom = player.getPosition().x;
		float top = (bottom - GameConfig.SIZE_PLAYER_Y);
		float right = (left + GameConfig.SIZE_PLAYER_X);

		/*
		 * Collisione con terreno
		 */
		for (StaticObject g : groundObjects) {
			float b = g.getPosition().x;
			float l = g.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;

			if ((l > left && l <= right && b > top && b < bottom)// top right
					|| (l > left && l < right && t > top && t < bottom)// top
																		// left
					|| (r > left && r < right && t > top && t < bottom)// bottom
																		// right
					|| (r >= left && r < right && b > top && b < bottom)// bottom
																		// left
			) {

				// Il player salta in verticale e collide con un terreno
				if (player.VERTICAL_JUMP) {
					if (top > t && top < b) {
						player.setPosition(new Vector2(bottom + 0.5f, left));
						return true;
					}
				}

				/*
				 * Il player salta (salto classico) OPPURE salta in verticale e
				 * si muove
				 */
				if (moveWhileJumping || !player.VERTICAL_JUMP) {
					// imposto la posizione del player quando collide con un
					// oggetto che si trova sopra di lui
					if (player.getState() == PlayerState.JUMPING) {
						if (top > t && top <= b) {
							player.setPosition(new Vector2(b + GameConfig.SIZE_ENEMY_Y, left));
							return true;
						}

					}

					if (right >= l && right < r) {
						player.setPosition(new Vector2(bottom, (int) left - 0.001f));
						return true;
					} else if (left > l && left <= r) {
						player.setPosition(new Vector2(bottom, r + 0.001f));
						return true;
					} else if (top > t && top < b) {
						player.setPosition(new Vector2((int) bottom, left));
						return true;
					}

				}

			}
		}
/*
 *  ------------------------------- COLLISIONE CON GLI OSTACOLI------------------------
 */
		Iterator<StaticObject> iterator = obstacleObjects.iterator();
		while(iterator.hasNext()) {
			Obstacle o = (Obstacle)iterator.next();
			float b = o.getPosition().x;
			float l = o.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;
			if(!checkTypeObj(o.getType()) && ! PLAYER_COLLISION){
				if ((l > left && l <= right && b > top && b < bottom)// top right
						|| (l > left && l < right && t > top && t < bottom)// bottom right
						|| (r > left && r < right && t > top && t < bottom)// bottom left
						|| (r >= left && r < right && b > top && b < bottom)// top left
				) {			
					
					/*
					 * Il player salta (salto classico) OPPURE salta in verticale e
					 * si muove
					 */
					if(o.getType().equals("19") || o.getType().equals("20")){
						toDraw = o;
						iterator.remove();
						PLAYER_COLLISION = true;
						return true;
					}
					
					if (moveWhileJumping || !player.VERTICAL_JUMP) {
						// imposto la posizione del player quando collide con un
						// oggetto che si trova sopra di lui
						if (player.getState() == PlayerState.JUMPING) {
							if (top > t && top <= b) {
								player.setPosition(new Vector2(b + GameConfig.SIZE_ENEMY_Y, left));
								if(!o.getType().equals("25")){
									PLAYER_COLLISION = true;
								}
								return true;
							}
	
						}
	
						if (right >= l && right < r) {
							player.setPosition(new Vector2(bottom,left - 0.001f));
							if(!o.getType().equals("25")){
								PLAYER_COLLISION = true;
							}
							return true;
						} else if (left > l && left <= r) {
							player.setPosition(new Vector2(bottom,r+0.001f));
							if(!o.getType().equals("25")){
								PLAYER_COLLISION = true;
							}
							return true;
						} else if (top > t && top < b) {
							if(player.getDirection() == 'r'){
								player.setPosition(new Vector2((int) bottom, right+0.1f));
							}
							else{
								player.setPosition(new Vector2((int) bottom, left-0.1f));
							}
							if(!o.getType().equals("25")){
								PLAYER_COLLISION = true;
							}
							return true;
						}
					}
					
	
				}
			}
		}

		return false;
	}

	private boolean checkPlayerBounds() {

		float x = player.getPosition().x;
		float y = player.getPosition().y;

		/*
		 * Posizione del player dentro la camera: lato sinistro
		 */
		boolean b = y - 1 < (camera - 0.75f);
		if ((b && moveWhileJumping) || (b && !player.VERTICAL_JUMP)) {
			return true;
		}

		/*
		 * Posizione del player dentro la camera: lato superiore
		 */
		if (((int) x - GameConfig.SIZE_PLAYER_Y) + 1 < 0) {
			return true;
		}

		/*
		 * Posizione del player dentro la camera: lato destro
		 */
		boolean b1 = y + GameConfig.SIZE_PLAYER_X + 0.25f > column;
		if ((b1 && moveWhileJumping) || (b1 && !player.VERTICAL_JUMP)) {
			return true;
		}

		return false;

	}	
	
	public void moveEnemy(float dt){

		for (StaticObject obj : enemy) {
			Enemy e = (Enemy) obj;
			//posizione iniziale del nemico
			if (e.getJustOnce()) {
				e.setStartingPos(e.getPosition().y);
				e.setJustOnce(false);
			}

			float x = e.getPosition().x;
			float y = e.getPosition().y;

			switch (e.getDirection()) {

			case 'l': {
				if ((e.getStartingPos() - y) >= e.getMoves()) {
					e.setDirection('r');
					if (!e.getSelectedYet()) {
						e.setSelectedYet(true);
						e.setMoves(e.getMoves() * 2);
					}
					e.setStartingPos((int) e.getPosition().y);
					break;
				}
				if(enemyCollision(x,y-0.25f)){
					Vector2 tmp = new Vector2();
					tmp.x = GameConfig.PLAYER_NEG_VELOCITY.x;
					tmp.y = GameConfig.PLAYER_NEG_VELOCITY.y;
					e.move(tmp, dt);				
			}else{
				e.setDirection('r');
			}
				break;
			}
			case 'r': {
				if ((y - e.getStartingPos()) >= e.getMoves()) {
					e.setDirection('l');
					e.setStartingPos(e.getPosition().y);
					break;
				}	
				if(enemyCollision(x,y+GameConfig.SIZE_ENEMY_X)){
					Vector2 tmp = new Vector2();
					tmp.x = GameConfig.PLAYER_POS_VELOCITY.x;
					tmp.y = GameConfig.PLAYER_POS_VELOCITY.y;
					e.move(tmp, dt);
					
				}
				else{
					e.setDirection('l');
				}
				
				break;
			}
			default:
				break;
			}
			
		}

		isEnemyFollowingPlayer();
		
		
	}
	
	private boolean enemyCollision(float x, float y){
		float left = y;
		float bottom = x;
		float top = (bottom - GameConfig.SIZE_ENEMY_Y);
		float right = (left + GameConfig.SIZE_ENEMY_X);
		
		/*
		 * Collisione con ostacoli
		 */
		for (StaticObject g : obstacleObjects) {
			float b = g.getPosition().x;
			float l = g.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;

			if ((r >= left && r < right && t > top && t < bottom)// bottom right
					|| (r > left && r < right && b > top && b < bottom)// bottom left
			) {
				return false;
			}
		}
		
		for (StaticObject obj : groundObjects) {
			Ground g = (Ground)obj;
			float b = g.getPosition().x;
			float l = g.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;
			
			if ((l > left && l <= right && b > top && b < bottom)// top right
					|| (r >= left && r < right && b > top && b < bottom)// top left
				) {
					return false;
				}
			
			
		}

		/*
		 * Collisione con terreno
		 */
		for (StaticObject obj : groundObjects) {
			Ground g = (Ground)obj;
			float b = g.getPosition().x;
			float l = g.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;
			
			
			if ((r > left && r < right && t > top && t <= bottom)// bottom right
					|| (r >= left && r < right && b >= top && b < bottom)// bottom left
			) {

				if(checkType(g.getType())){
					return true;
				}
			}
			
		}
		
		return false;
	}
	
	

	// ritorna true se il player entra nel raggio del nemico
	private boolean isPlayerInEmenyZone(Enemy e) {
		int x_player = (int)player.getPosition().x;
		int x_enemy =  (int)e.getPosition().x;
		return (Math.abs(e.getPosition().y - player.getPosition().y) <= GameConfig.SIZE_MOVE_ENEMY
				&& x_player == x_enemy);
	}


	// quando il player entra nella zona del nemico a seconda, se viene da sx o
	// da dx, cambia direzione verso il player e lo insegue
	private void isEnemyFollowingPlayer() {
		for (StaticObject obj : enemy) {
			Enemy e = (Enemy) obj;
			if (isPlayerInEmenyZone(e)) {
				e.setState(EnemyState.FOLLOWING_PLAYER);
				// se a sx del nemico lo faccio muovere verso di lui
				if (player.getPosition().y < e.getPosition().y) {
					e.setDirection('l');
				}

				// se a dx del nemico lo faccio muovere verso di lui
				else
					e.setDirection('r');

			}else{
				e.setState(EnemyState.RUNNING);
			}
		}
	}

	private final boolean validatePosition() {

		float left = player.getPosition().y;
		float bottom = player.getPosition().x;
		float top = (bottom - GameConfig.SIZE_PLAYER_Y);
		float right = (left + GameConfig.SIZE_PLAYER_X);

		/*
		 * Collisione con terreno
		 */
		for (StaticObject obj : groundObjects) {
			Ground g = (Ground) obj;
			if (checkType(g.getType())) {
				float b = g.getPosition().x;
				float l = g.getPosition().y;
				float t = b - GameConfig.SIZE_GROUND_X;
				float r = l + GameConfig.SIZE_GROUND_Y;

				if ((l > left && l < right - 0.45f && t > top && t <= bottom)// bottom right
						|| (r > left + 0.45f && r < right && t > top && t <= bottom)// bottom left
						|| (left >= l && left <= r - 0.45f && bottom >= t && bottom < b)
				) {
					return true;
				}
			}

		} // end for

		for (StaticObject o : obstacleObjects) {
			if (((Obstacle) o).getType().equals("25")) {
				float b = o.getPosition().x;
				float l = o.getPosition().y;
				float t = b - GameConfig.SIZE_GROUND_X;
				float r = l + GameConfig.SIZE_GROUND_Y;

				if ((l > left && l < right - 0.45f && t > top && t <= bottom)// bottom right
						|| (r > left + 0.45f && r < right && t > top && t <= bottom)// bottom left
				) {
					return true;
				}
			}
		}

		return false;
	}


	/*
	 * Verifica collisione tra monete e player
	 */
	public boolean handleScores() {
		for (Iterator<StaticObject> iter = coins.iterator(); iter.hasNext();) {
			Obstacle coin = (Obstacle) iter.next();
			if (findCollision(player, coin)) {
				iter.remove();
				current_coin_count++;
				toDraw = coin;
				player.score(100);
				return true;
			}
		}
		return false;
	}

	private boolean handleBullets(Bullet tmp) {
		if (findCollision(tmp, enemy)) {
			player.score(155);
			return true;
		} else if (tmp.getPosition().x > row || findCollision(tmp, obstacleObjects)	|| findCollision(tmp, groundObjects)) {
			return true;
		}
		return false;
	}

	public void addBullet(float x, float y, char dir) {
		bullets.add(new Bullet(new Vector2(x, y), 
								new Vector2(GameConfig.SIZE_BULLET_X, GameConfig.SIZE_BULLET_Y),
								dir));
	}

	public void updateBullets(float dt) {
		if (!bullets.isEmpty()) {
			Iterator<Bullet> it = bullets.iterator();
			while (it.hasNext()) {
				Bullet b = it.next();
				b.move(dt);
				if (handleBullets(b)) {
					it.remove();
				}

			}
		}

	}

	// gestisce la collisione dati 2 oggetti
	public boolean findCollision(StaticObject obj1, StaticObject obj2) {

		float left = obj1.getPosition().y;
		float bottom = obj1.getPosition().x;
		float top = bottom - GameConfig.SIZE_GROUND_X;
		float right = left + GameConfig.SIZE_GROUND_Y;
		if (obj1 instanceof Player) {
			top = bottom - GameConfig.SIZE_PLAYER_Y;
			right = left + GameConfig.SIZE_PLAYER_X;
		}
		float b = obj2.getPosition().x;
		float l = obj2.getPosition().y;
		float t = b - GameConfig.SIZE_GROUND_X;
		float r = l + GameConfig.SIZE_GROUND_Y;
		if ((l > left && l <= right && b > top && b < bottom)// top right
				|| (l > left && l < right && t > top && t < bottom)// bottom right
				|| (r > left && r < right && t > top && t < bottom)// bottom left
				|| (r >= left && r < right && b > top && b < bottom)// top left
		) {
			return true;
		}

		return false;

	}

	// gestisce la collisione dati un oggetto e una lista di ostacoli ( tra
	// oggetti e nemici)
	private boolean findCollision(StaticObject obj1, List<StaticObject> list) {
		float left = obj1.getPosition().y;
		float bottom = obj1.getPosition().x;
		float top = bottom - obj1.getSize().x;
		float right = left + obj1.getSize().y;

		Iterator<StaticObject> it = list.iterator();
		while (it.hasNext()) {
			StaticObject o = it.next();
			float b = o.getPosition().x;
			float l = o.getPosition().y;
			float t = b - o.getSize().y;
			float r = l + o.getSize().x;

			if ((l >= left && l <= right && b >= top && b <= bottom)// top right
					|| (l >= left && l <= right && t >= top && t <= bottom)// bottom right
					|| (r >= left && r <= right && t >= top && t <= bottom)// bottom left
					|| (r >= left && r <= right && b >= top && b <= bottom)// top left
					|| (left >= l && left <= r && t <= top && b >= bottom) // il proiettile incontra il nemico a dx
					|| (right >= l && right <= r && t <= top && b >= bottom)//il proiettile incontra il nemico a sx
			) {
				if (o instanceof Enemy) {
					it.remove();
				}
				else if(o instanceof Obstacle){
					if(checkTypeObj(((Obstacle) o).getType())){
						return false;
					}
				}
				return true;
			}
		} // fine while

		return false;
	}

	public void findNewPlayerPosition(Vector2 player_pos) {
		float x_p = player_pos.x;
		float y_p = player_pos.y;

		float current_x = 0;
		float current_y = 0;
		float current_distance = 100;

		for (StaticObject obj : groundObjects) {
			Ground g = (Ground)obj;
			if(checkType(g.getType())){
				float g_x = g.getPosition().x;
				float g_y = g.getPosition().y;
				
				/* calcolo della distanza tra la posizione attuale del player e la posizione del terreno.
				 * viene presa la posizione con distanza minore tra player e terreno 
				 */
				float dist = (float) Math.sqrt((Math.pow((g_x - x_p), 2)+(Math.pow((g_y - y_p), 2))));
				if(dist < current_distance){
					current_distance = dist;
					current_x = g_x;
					current_y = g_y;
				}
			}			
		}
		
		boolean ground = false;
		boolean obstacles = false;
		int count = 0;
		
		do{
			ground = findGroundPosition(current_x, current_y);
			obstacles = findObstaclePosition(current_x - 1, current_y);
			
			if(ground && !obstacles){
				current_x -= GameConfig.SIZE_GROUND_X; 
				player.setPosition(new Vector2(current_x, current_y-0.1f));
				break;
			}
			
			if(count < 4){
				current_y --;
			}else{
				current_y ++;
			}
			count ++;
			
		} while (!ground || obstacles); 
	}

	/**
	 *  @return 
	 *  		true se la stringa in input corrisponde al tipo di ground tra quelli nella funzione. 
	 */
	public final boolean checkType(String type) {
		return (type.equals("1") || type.equals("2") || type.equals("3") || type.equals("7") || type.equals("11")
				|| type.equals("14") || type.equals("15") || type.equals("16"));
	}
	private final boolean checkTypeObj(String type) {
		return (type.equals("18") || type.equals("22") || type.equals("26") || type.equals("28") || type.equals("29"));
	}
	
	
	/**
	 * 
	 * @return true se è presente un oggetto ground che abbia quelle coordinate.
	 */
	public final boolean findGroundPosition(float x, float y){
		for (StaticObject obj : groundObjects) {
			Ground g = (Ground)obj;
			if(checkType(g.getType())){
				if(g.getPosition().x == x && g.getPosition().y == y){
					return true;
				}
			}
		}
		return false;
	}
	
	public final boolean findObstaclePosition(float x, float y){
		for (StaticObject obj : obstacleObjects) {
			if(obj.getPosition().x == x && obj.getPosition().y == y) return true;
		}
		return false;
	}
	
	/*
	 * Gestione collisione tra l'animazione del coin e il terreno
	 */
	public boolean animationCollision(float x,float y){
		float bottom = x;
		float left = y;
		float top = bottom - 0.7f;
		float right = left + 0.7f;

		for (StaticObject g : groundObjects) {
			float b = g.getPosition().x;
			float l = g.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;

			if ((l > left && l < right && b > top && b < bottom)// top right
					|| (l > left && l < right && t > top && t < bottom)// top left
			) {
				
				return true;
			}
		}
		return false;
		
	}
	
	/**
	 * 
	 * @return true se il player ha trovato la chiave per aprire la porta, false altrimenti
	 */
	public boolean findMagicKey(){
		float bottom = player.getPosition().x;
		float left = player.getPosition().y;
		float top = bottom - GameConfig.SIZE_BULLET_Y;
		float right = left + GameConfig.SIZE_BULLET_X;

		for (StaticObject g : utility) {
			float b = g.getPosition().x;
			float l = g.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;

			//71 = identificativo della chiave
			if(((Obstacle)g).getType().equals("71")){
				
				if ((l >= left && l <= right && b >= top && b <= bottom)// top right
						|| (l >= left && l <= right && t >= top && t <= bottom)// bottom right
						|| (r >= left && r <= right && t >= top && t <= bottom)// bottom left
						|| (r >= left && r <= right && b >= top && b <= bottom)// top left
						) {
					player.score(1000);
					return true;
				}
			}
		}
		return false;
	}

	public final List<Bullet> getBullets() {
		return bullets;
	}

	public void setLevel(String level) {
		LEVEL = level;
	}

	public final List<StaticObject> getGround() {
		return groundObjects;
	}

	public final Player getPlayer() {
		return player;
	}

	public final List<StaticObject> getEnemy() {
		return enemy;
	}

	public final List<StaticObject> getCoins() {
		return coins;
	}
	
	public final List<StaticObject> getUtility() {
		return utility;
	}
	
	public final List<StaticObject> getObstacles() {
		return obstacleObjects;
	}

	public final int getRow() {
		return row;
	}

	public final int getColumn() {
		return column;
	}

	public float getCamera() {
		return camera;
	}

	public void setCamera(float position) {
		camera = position;
	}

	public float getEndCamera() {
		return end_camera;
	}

	public void setEndCamera(float position) {
		end_camera = position;
	}

	public void resumeCondition() {
		lock.lock();
		condition.signalAll();
		lock.unlock();
	}

	public final boolean isOver() {
		if (player.getState() == PlayerState.DEAD)
			return true;
		return false;
	}

	public final int getCoinsCount() {
		return current_coin_count;
	}

	public final int getScore() {
		return player.getScore();
	}

}
