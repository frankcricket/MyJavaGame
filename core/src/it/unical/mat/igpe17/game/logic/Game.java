package it.unical.mat.igpe17.game.logic;

import java.util.List;
import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;
import it.unical.mat.igpe17.game.player.PlayerState;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {

	// set di default
	private static String LEVEL = "levels/firstLevel.tmx";

	private Player player;
	private List<Ground> groundObjects;

	private int row;
	private int column;
	private float camera;

	boolean setStartPosition = true;
	Vector2 startPosition = new Vector2();

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
		if (checkGroundCollision((int) x, (int) y + 1)) {
			Vector2 tmp = new Vector2();
			tmp.x = GameConfig.PLAYER_NEG_VELOCITY.x;
			tmp.y = GameConfig.PLAYER_NEG_VELOCITY.y;
			player.movePlayer(tmp, dt);

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
		 * Se il player può andare a destra, viene settata la nuova posizione
		 */
		if (checkGroundCollision((int) ++x, (int) ++y)){
			Vector2 tmp = new Vector2();
			tmp.x = GameConfig.PLAYER_POS_VELOCITY.x;
			tmp.y = GameConfig.PLAYER_POS_VELOCITY.y;
			
			player.movePlayer(tmp, dt);
		}
		else{
			Vector2 tmp = new Vector2();
			tmp.x = GameConfig.PLAYER_POS_VELOCITY.y;
			tmp.y = GameConfig.PLAYER_POS_VELOCITY.y;
			
			player.movePlayer(tmp, dt);
			
		}
	}


	public static boolean jumping = false;

	public void makePlayerJump(float delta) {
		int xp = (int) player.getPosition().x;
		int yp = (int) player.getPosition().y;
		
		//memorizzo la posizione del player prima del salto
		if (setStartPosition) {
			startPosition.x = player.getPosition().x;
			startPosition.y = player.getPosition().y;
			setStartPosition = false;
		}
		
		//salto solo in verticale
		if(player.VERTICAL_JUMP){
			if((xp < startPosition.x - 3) || playerIsCollidingWithGround(xp,yp)){
				player.setForce(GameConfig.JUMP_POS_FORCE);
			}
			player.verticalJump(delta);
		}else {
			if((xp < startPosition.x - 3) || playerIsCollidingWithGround(xp,yp))
				player.swap();
			
			player.jump(delta);
		}
		
		//il player deve rimanere dentro la camera
		checkPlayerBounds();

		// verifico che ritorni sul terreno e si fermi
		if (checkGroundCollision((int) (player.getPosition().x) + 1, Math.round((player.getPosition().y)))) {
			player.setPosition(new Vector2((int)player.getPosition().x,player.getPosition().y));
			setStartPosition = true;
			jumping = false;
			player.VERTICAL_JUMP = false;
			player.setForce(GameConfig.JUMP_NEG_FORCE);
			player.setState(PlayerState.IDLING);
			player.reset();
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
	
	
	private boolean playerIsCollidingWithGround(int x, int y){
		
		int tmpX = x - GameConfig.SIZE_PLAYER_X;
		int tmpY = y;
		
		/*
		 * Collisione con oggetti che si trovano sopra il player
		 */
		for(Ground g : groundObjects){
			if(g.getPosition().x == tmpX && g.getPosition().y == tmpY)
				return true;
		}
		
		/*
		 * Collisione a sinistra del player
		 */
		tmpX = x;
		tmpY = y ;
		if(player.getDirection() == 'l'){
			for(int i = 0; i < GameConfig.SIZE_PLAYER_X; i++){
				for(Ground g : groundObjects){
					if(g.getPosition().x == tmpX && g.getPosition().y == tmpY){
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
		if(player.getDirection() == 'r'){
			for(int i = 0; i < GameConfig.SIZE_PLAYER_X; i++){
				for(Ground g : groundObjects){
					if(g.getPosition().x == tmpX && g.getPosition().y == tmpY){
						return true;
					}
				}
				x--;
			}
		}
		
		
		return false;
	}
	
	private void checkPlayerBounds(){
		
		float x = player.getPosition().x;
		float y = player.getPosition().y;
		
		/*
		 * Posizione del player dentro la camera: lato sinistro
		 */
		
		if(y < camera)
		{
			player.setPosition(new Vector2(x,camera));
		}
		
		/*
		 * Posizione del player dentro la camera: lato superiore
		 */
		if(x - GameConfig.SIZE_PLAYER_Y <= 0 ){
			player.setPosition(new Vector2(x - GameConfig.SIZE_PLAYER_Y, y));
		}
		
		/*
		 * Posizione del player dentro la camera: lato destro
		 */
		if(y + GameConfig.SIZE_PLAYER_X > column ){
			player.setPosition(new Vector2(x,column - 1));
		}

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
