package it.unical.mat.igpe17.game.logic;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {

	private Player player;
	private char[][] matrix;
	private int row;
	private int column;

	private float camera;
	private List<Ground> groundObjects;

	int point_x;
	int point_y1;
	int point_y2;

	// set di default
	private static String LEVEL = "levels/firstLevel.tmx";

	private boolean gameOver;

	public Game() {
		player = null;
		matrix = null;
		groundObjects = null;
	}

	public void loadLevel() {
		Reader reader = new Reader(LEVEL);
		reader.parse();
		matrix = reader.getMatrix();
		row = reader.getRow();
		column = reader.getColumn();
		groundObjects = reader.getGround();

		player = reader.getPlayer();

		camera = 0f;

		point_x = (int) player.getPosition().x;
		point_y1 = -1;
		point_y2 = -1;
	}

	public void movePlayer(char dir, float dt) {
		if (dir == 'r') {
			movePlayerToRight(dt);
		}
		else if(dir == 'l'){
			movePlayerToLeft(dt);
		}
		else if(dir == 's'){
//			player.jump(dt);
			makePlayerJump(dt);
		}
	}


	private void movePlayerToLeft(float dt) {
		float x = player.getPosition().x;		
		float y = player.getPosition().y;
		
		x++;
		y--;
		if(y < camera - 0.95f){
			return;
		}
		
		if(checkCollisionGround((int)x, (int)y))
			player.movePlayer(new Vector2(0, -9.0f), dt);
		
	}

	private void movePlayerToRight(float dt) {
		float x = player.getPosition().x;		
		float y = player.getPosition().y;
		
		if(y + 1 >= row)
			return;
		if(checkCollisionGround((int)++x,(int) ++y))
			player.movePlayer(new Vector2(0, +9.0f), dt);
	}
	
	float posX = 0;
	float posY = 0;
	
	float velocityX = .005f;
	float velocityY = .005f;
	
	float gravity = 0.5f;
	
	boolean left = true;
	
	float initialPos = 0;
	boolean justOnce = true;
	
	
	private void makePlayerJump(float dt){
		
		if(player.IS_JUMPING){
			
			if(justOnce){
				initialPos = player.getPosition().x;
				justOnce = false;
			}
			
			posX = player.getPosition().x;
			posY = player.getPosition().y;
			
			if(posX + 2 <= 0)
				return;
			
			if(posX > (initialPos - 2) && left){
				posX -= velocityX *dt;
				posY += velocityY *dt;
				velocityX += gravity;		
				velocityY += gravity;	
			}
			else{
				left = false;
			}
			
			if(!left){
				posX += velocityX *dt;
				posY += velocityY *dt;
				velocityX += gravity;		
				velocityY += gravity;
			}
	
			player.setPosition(new Vector2(posX,posY));
	
			if(checkCollisionGround((int)player.getPosition().x, (int)player.getPosition().y))
			{
				player.setPosition(new Vector2(player.getPosition().x - 2,player.getPosition().y));
				reset();
				left = true;
				justOnce = true;
				player.IS_JUMPING = false;
				return;
			}
			
		}
		
	}
	
	private void reset() {
		velocityX = .005f;
		velocityY = .005f;
		
		gravity = 0.5f;
	}
	
	private final boolean checkCollisionGround(int x, int y){
		System.out.println(x + "  " + y);
		for(Ground g: groundObjects){
			if(g.getPosition().x == x){
				if(g.getPosition().y == y){
					if((g.getType().equals("1") || g.getType().equals("2") || g.getType().equals("3") || g.getType().equals("7") || g.getType().equals("11")
							|| g.getType().equals("14") || g.getType().equals("15") || g.getType().equals("16"))){
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

	public final void print() {
		for (int i = 0; i < matrix.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print(" |");
			System.out.println();
		}
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

	public static void main(String[] args) {

		Game g = new Game();
		g.loadLevel();
		g.print();
		/*
		 * for(int i = 0; i < 10; i++){ g.movePlayer('r', (float)1); g.print();
		 * }
		 */
	}

}
