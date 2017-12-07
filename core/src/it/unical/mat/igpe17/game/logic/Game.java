package it.unical.mat.igpe17.game.logic;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {

	private Player player;
	private int row;
	private int column;

	private float camera;
	private List<Ground> groundObjects;

	int point_x;
	int point_y1;
	int point_y2;
	
	Vector2 startPosition;

	// set di default
	private static String LEVEL = "levels/firstLevel.tmx";

	public Game() {
		player = null;
		groundObjects = null;
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

		player = reader.getPlayer();

		camera = 0f;

		point_x = (int) player.getPosition().x;
		point_y1 = -1;
		point_y2 = -1;
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

		x++;
		y--;
		/*
		 * Se l'ascissa del player supera a sinistra il limite della camera,
		 * return della funzione
		 */
		if (y < (camera - 0.95f)) {
			return;
		}

		/*
		 * Se il player può andare a sinistra, viene settata la nuova posizione
		 */
		if (checkGroundCollision((int) x, (int) y + 1))
			player.movePlayer(new Vector2(0, -5.0f), dt);

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
		 * Se il player può andare a destra, viene settata la nuova posizione
		 */
		if (checkGroundCollision((int) ++x, (int) ++y))
			player.movePlayer(new Vector2(0, +5.0f), dt);
	}

//	float posX = 0;
//	float posY = 0;
//
//	float velocityX = .005f;
//	float velocityY = .005f;
//
//	float gravity = 0.5f;
//
//	boolean left = true;
//
//	float initialPos = 0;
//	boolean justOnce = true;

//	private void makePlayerJump(float dt) {
//
//		if (player.IS_JUMPING) {
//
//			if (justOnce) {
//				initialPos = player.getPosition().x;
//				justOnce = false;
//			}
//
//			posX = player.getPosition().x;
//			posY = player.getPosition().y;
//
//			/*
//			 * Upper bound
//			 */
//
//			if (posX + 2 <= 0)
//				return;
//
//			if (posX > (initialPos - 2) && left) {
//				posX -= velocityX * dt;
//				posY += velocityY * dt;
//				velocityX += gravity;
//				velocityY += gravity;
//			} else {
//				left = false;
//			}
//
//			if (!left) {
//				posX += velocityX * dt;
//				posY += velocityY * dt;
//				velocityX += gravity;
//				velocityY += gravity;
//			}
//
//			player.setPosition(new Vector2(posX, posY));
//
//			if (checkCollisionGround((int) player.getPosition().x, (int) player.getPosition().y)) {
//				player.setPosition(new Vector2((int) player.getPosition().x - 1, player.getPosition().y));
//				reset();
//				left = true;
//				justOnce = true;
//				player.IS_JUMPING = false;
//				return;
//			}
//
//		}
//
//		player.jump(dt);
//
//	}
	//
	// private void reset() {
	// velocityX = .005f;
	// velocityY = .005f;
	//
	// gravity = 0.5f;
	// }
	
	
	
	public void makePlayerJump(float delta){
		//limite superiore -> altezza del salto
		if(player.getPosition().x - 2 < 6){
			player.setForce(0.01f);
//			player.JUMPING = false;
//			return;
		}
		
		player.jump(delta);
		
		//verifico che ritorni sul terreno e si fermi
		if(checkGroundCollision((int)(player.getPosition().x)+1, (int)(player.getPosition().y))){
			player.setPosition(startPosition);
			player.JUMPING = false;
			return;
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

	public void setLevel(String level) {
		LEVEL = level;
	}

	public final List<Ground> getGround() {
		return groundObjects;
	}

	public final Player getPlayer() {
		return player;
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

}
