package it.unical.mat.igpe17.game.logic;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.objects.Obstacle;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {

	// set di default
	public static String LEVEL = "levels/firstLevel.tmx";

	private Player player;
	private List<Ground> groundObjects;
	private List<Obstacle> obstacleObjects;
	private List<Obstacle> coins;

	private final int NUM_ENEMY = 10;
	private List<Enemy> enemy;

	private int row;
	private int column;
	private float camera;
	private float end_camera = Asset.WIDTH;

	boolean setStartPosition = true;
	Vector2 startPosition = new Vector2();

	private float pos_fall = GameConfig.FALLING_POS_VELOCITY.y;
	private float neg_fall = GameConfig.FALLING_NEG_VELOCITY.y;
	public static boolean PLAYER_IS_FALLING = false;

	private Lock lock;
	private Condition condition;

	public Game() {
		player = null;
		groundObjects = null;
		enemy = null;
		coins = null;

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

		// for(Ground g : groundObjects)
		// System.out.println(g.getPosition());
		//
		// System.out.println("Obstacles");
		// for(Obstacle g : obstacleObjects)
		// System.out.println(g.getPosition());

		// generaNemici();

		camera = 0f;
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

		float x = player.getPosition().x;
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
				player.setState(PlayerState.DEAD);
				neg_fall = GameConfig.FALLING_NEG_VELOCITY.y;
				check_g1_l = false;
				check_g2_l = false;
				return;
			}

			/*
			 * Se il player può andare a sinistra, viene settata la nuova
			 * posizione
			 */
//			if (checkGroundCollision(((int) x) + 1,	Math.round(y))) {
				if (validatePosition()) {
				check_g1_l = true;
				player.setPosition(new Vector2((int)player.getPosition().x, player.getPosition().y));
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
				neg_fall += 0.9f;
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

		float x = player.getPosition().x;
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

			/*
			 * Se la posizione x del player supera la dimensione della riga,
			 * viene posto il suo stato come DEAD
			 */
			if (player.getPosition().x >= row + GameConfig.SIZE_PLAYER_Y) {
				player.setState(PlayerState.DEAD);
				pos_fall = GameConfig.FALLING_POS_VELOCITY.y;

				check_g1_r = false;
				check_g2_r = false;
				return;
			}

//			if (checkGroundCollision(((int) x) + 1, Math.round(y))) {
			if (validatePosition()) {
				check_g1_r = true;
				player.setPosition(new Vector2((int)player.getPosition().x, player.getPosition().y));
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
				pos_fall -= 0.9f;
				PLAYER_IS_FALLING = true;
			}

		} catch (InterruptedException e) {

		} finally {
			lock.unlock();
		}

	}
	private boolean obstacleFound = false;
	public boolean moveWhileJumping = false;

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
				player.setState(PlayerState.DEAD);
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

			if ((isObstaclesCollision() || checkPlayerBounds()))
				obstacleFound = true;
			
			if(obstacleFound){
				player.setForce(GameConfig.JUMP_POS_FORCE);
				player.verticalJump(delta);
			}
			else{

				if (player.VERTICAL_JUMP) {
					if ((int) xp < startPosition.x - 3) {
						player.setForce(GameConfig.JUMP_POS_FORCE);
					}
					player.verticalJump(delta);
					if(moveWhileJumping)
						if(player.getDirection() == 'r'){
							Vector2 tmp = new Vector2();
							tmp.x = 0;
							tmp.y = +2.3f;
							player.move(tmp, delta);
						}
						else{
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

//			if (checkGroundCollision(((int) player.getPosition().x) + 1, Math.round((yp)))) {
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

	private final boolean checkObstaclesCollisionEnemy(int x, int y) {
		for (Obstacle o : obstacleObjects) {
			if (o.getPosition().x == x && o.getPosition().y == y) {
				return true;
			}
		}
		return false;

	}

	private boolean enemyIsObstaclesCollision(Enemy e) {

		int posX = ((int) e.getPosition().x) - GameConfig.SIZE_ENEMY_Y + GameConfig.SIZE_GROUND_X;
		int posY = Math.round(e.getPosition().y);

		/*
		 * Collisione sopra il personaggio con oggetti di tipo ground
		 */
		for (Ground g : groundObjects) {
			if (g.getPosition().x == posX && g.getPosition().y == posY)
				return true;
		}

		if (e.getDirection() == 'r') {
			posX = (int) e.getPosition().x;
			posX--;
			posY = (int) (e.getPosition().y) + GameConfig.SIZE_ENEMY_X;

			for (int i = 0; i < GameConfig.SIZE_ENEMY_Y; i++) {
				for (Ground g : groundObjects) {
					if (g.getPosition().x == posX && g.getPosition().y == posY) {
						return true;
					}
				}
				posX++;
			}

		} else {
			posX = (int) e.getPosition().x;
			posY = (int) (e.getPosition().y);
			for (int i = 0; i < GameConfig.SIZE_ENEMY_Y; i++) {
				for (Ground g : groundObjects) {
					if (g.getPosition().x == posX && (g.getPosition().y) == posY) {
						return true;
					}
				}
				posX--;
			}
		}

		return false;

	}

	private boolean isObstaclesCollision() {

		float left = player.getPosition().y;
		float bottom = player.getPosition().x;
		float top = (bottom - GameConfig.SIZE_PLAYER_Y);
		float right = (left + GameConfig.SIZE_PLAYER_X);

		/*
		 * Collisione con terreno
		 */
		for (Ground g : groundObjects) {
			float b = g.getPosition().x;
			float l = g.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;
			
			
			if((l > left && l <= right && b > top && b < bottom)//top right
				|| 
				(l > left && l < right && t > top && t < bottom)//top left
				|| 
				(r > left && r < right && t > top && t < bottom)//bottom right
				|| 
				(r >= left && r < right && b > top && b < bottom)//bottom left
					){
				
				//Il player salta in verticale e collide con un terreno
				if(player.VERTICAL_JUMP){
					 if(top > t && top < b){
						player.setPosition(new Vector2(bottom + 0.5f,left));
						return true;
					 }
//					 else if(bottom >= t && bottom < b && right >= l && right < r){
//						 player.setPosition(new Vector2(t-0.01f,left));
//						 return true;
//					 }

				}
				
				/*
				 * Il player salta (salto classico) OPPURE salta in verticale e si muove
				 */
				if(moveWhileJumping	|| !player.VERTICAL_JUMP){
					//imposto la posizione del player quando collide con un oggetto che si trova sopra di lui
					if(player.getState() == PlayerState.JUMPING){
						if(top > t && top <= b){
							player.setPosition(new Vector2(b + GameConfig.SIZE_ENEMY_Y,left));
							return true;
						}
						
					}
					
					if(right >= l && right < r){
						player.setPosition(new Vector2(bottom,(int)left - 0.001f));
						return true;
					}
					else if(left > l && left <= r){
						player.setPosition(new Vector2(bottom,r + 0.001f));
						return true;
					}
					else if(top > t && top < b){
						player.setPosition(new Vector2((int)bottom,left));
						return true;
					}
					
				}
				
			}
		}
		
		
		for (Obstacle o : obstacleObjects) {
			float b = o.getPosition().x;
			float l = o.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;
			
			
			if((l > left && l <= right && b > top && b < bottom)//top right
				|| 
				(l > left && l < right && t > top && t < bottom)//bottom right
				|| 
				(r > left && r < right && t > top && t < bottom)//bottom left
				|| 
				(r >= left && r < right && b > top && b < bottom)//top left
					){
				
				
				
				/*
				 * Il player salta (salto classico) OPPURE salta in verticale e si muove
				 */
				if(moveWhileJumping	|| !player.VERTICAL_JUMP){
					//imposto la posizione del player quando collide con un oggetto che si trova sopra di lui
					if(player.getState() == PlayerState.JUMPING){
						if(top > t && top <= b){
							player.setPosition(new Vector2(b + GameConfig.SIZE_ENEMY_Y,left));
							return true;
						}
						
					}
					
					if(right >= l && right < r){
						player.setPosition(new Vector2(bottom,(int)left - 0.001f));
						return true;
					}
					else if(left > l && left <= r){
						player.setPosition(new Vector2(bottom,r + 0.001f));
						return true;
					}
					else if(top > t && top < b){
						player.setPosition(new Vector2((int)bottom,left));
						return true;
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
		if ((b && moveWhileJumping)	|| (b && !player.VERTICAL_JUMP)) {
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
		boolean b1 = y + GameConfig.SIZE_PLAYER_X  + 0.25f > column;
		if ((b1 && moveWhileJumping) || (b1 && !player.VERTICAL_JUMP)) {
			return true;
		}

		return false;

	}

	public void moveEnemy(float dt) {
		for (Enemy e : enemy) {

			if (e.getJustOnce()) {
				e.setStartingPos((int) e.getPosition().y);
				e.setJustOnce(false);
			}

			// prendiamo le coordinate di un nemico per volta

			int x = (int) e.getPosition().x;
			int y = (int) e.getPosition().y;

			switch (e.getDirection()) {

			case 'l': {

				if (checkObstaclesCollisionEnemy(x, y) || enemyIsObstaclesCollision(e)) {
					e.setDirection('r');
					break;
				} else {

					if ((e.getStartingPos() - y) == e.getMoves()) {
						e.setDirection('r');
						if (!e.getSelectedYet()) {
							e.setSelectedYet(true);
							e.setMoves(e.getMoves() * 2);
						}
						e.setStartingPos((int) e.getPosition().y);
						break;
					}

					if (checkGroundCollision((int) ++x, (int) y)) {

						Vector2 tmp = new Vector2();
						tmp.x = GameConfig.PLAYER_NEG_VELOCITY.x;
						tmp.y = GameConfig.PLAYER_NEG_VELOCITY.y;
						e.move(tmp, dt);
					} else {
						e.setDirection('r');
					}
				}

				break;
			}
			case 'r': {
				if (checkObstaclesCollisionEnemy(x, ++y) || enemyIsObstaclesCollision(e)) {
					e.setDirection('l');
					break;
				} else {

					if ((y - e.getStartingPos()) == e.getMoves()) {
						e.setDirection('l');
						e.setStartingPos((int) e.getPosition().y);
						break;
					}

					if (checkGroundCollision((int) ++x, (int) ++y)) {

						Vector2 tmp = new Vector2();
						tmp.x = GameConfig.PLAYER_POS_VELOCITY.x;
						tmp.y = GameConfig.PLAYER_POS_VELOCITY.y;
						e.move(tmp, dt);
					} else {
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
	
	private final boolean validatePosition(){
		
		float left = player.getPosition().y;
		float bottom = player.getPosition().x;
		float top = (bottom - GameConfig.SIZE_PLAYER_Y);
		float right = (left + GameConfig.SIZE_PLAYER_X);

		/*
		 * Collisione con terreno
		 */
		for (Ground g : groundObjects) {
			if ((g.getType().equals("1") || g.getType().equals("2") || g.getType().equals("3")
					|| g.getType().equals("7") || g.getType().equals("11") || g.getType().equals("14")
					|| g.getType().equals("15") || g.getType().equals("16"))){
				float b = g.getPosition().x;
				float l = g.getPosition().y;
				float t = b - GameConfig.SIZE_GROUND_X;
				float r = l + GameConfig.SIZE_GROUND_Y;
				
				if((l > left && l < right - 0.45f && t > top && t <= bottom)//bottom right
					|| 
					(r > left + 0.45f && r < right && t > top && t <= bottom)//bottom left
					){
					return true;
				}
				
			}
			
		}//end for
		
		for(Obstacle o : obstacleObjects){
			if(o.getType().equals("25")){
				float b = o.getPosition().x;
				float l = o.getPosition().y;
				float t = b - GameConfig.SIZE_GROUND_X;
				float r = l + GameConfig.SIZE_GROUND_Y;
				
				if((l > left && l < right - 0.45f && t > top && t <= bottom)//bottom right
					|| 
					(r > left + 0.45f && r < right && t > top && t <= bottom)//bottom left
					){
					return true;
				}
			}
		}
		
		return false;
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
		
		float left = player.getPosition().y;
		float bottom = player.getPosition().x;
		float top = (bottom - GameConfig.SIZE_PLAYER_Y);
		float right = (left + GameConfig.SIZE_PLAYER_X);
		
		for(Obstacle o : obstacleObjects){
			if(o.getType().equals("25")){
				float b = o.getPosition().x;
				float l = o.getPosition().y;
				float t = b - GameConfig.SIZE_GROUND_X;
				float r = l + GameConfig.SIZE_GROUND_Y;
				if((r > left && r < right && t > top && t < bottom)//bottom right
					|| 
					(r >= left && r < right && b > top && b < bottom)//bottom left
				  ){
					return true;
				}
			}
		}
		return false;
	}
	
	public void handleScores(){
		float left = player.getPosition().y;
		float bottom = player.getPosition().x;
		float top = (bottom - GameConfig.SIZE_PLAYER_Y);
		float right = (left + GameConfig.SIZE_PLAYER_X);
		
		
		for(Iterator<Obstacle> iter = coins.iterator(); iter.hasNext();) {
		    Obstacle coin = iter.next();
		    float b = coin.getPosition().x;
			float l = coin.getPosition().y;
			float t = b - GameConfig.SIZE_GROUND_X;
			float r = l + GameConfig.SIZE_GROUND_Y;
		    if((l > left && l <= right && b > top && b < bottom)//top right
				|| 
				(l > left && l < right && t > top && t < bottom)//bottom right
				|| 
				(r > left && r < right && t > top && t < bottom)//bottom left
				|| 
				(r >= left && r < right && b > top && b < bottom)//top left
					){
		    		iter.remove();
		    		player.score(100);
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
	public final List<Obstacle> getCoins() {
		return coins;
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

	public float getEndCamer() {
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

}
