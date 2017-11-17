package it.unical.mat.igpe17.game.utility;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;

public class Builder {
	
	private int dimX;
	private int dimY;
	private char[][] matrix;
	
	private List<Ground> groundObjects = new LinkedList<Ground>();
	
	protected void convertDimension(String dim){
		
		String[] tmp = dim.split(" ");
		dimX = Integer.parseInt(tmp[0]);
		dimY = Integer.parseInt(tmp[1]);
	}
	protected void createMatrix(){
		matrix = new char[dimX][dimY];
		
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				matrix[i][j] = ' ';
			}
		}
	}
	
	protected void convertGround(String line){
		
		String[] tmp = line.split(";");
		String type = tmp[0];
		int xpos = Integer.parseInt(tmp[1]);
		int ypos = Integer.parseInt(tmp[2]);
		
		Ground ground = new Ground(new Vector2(xpos,ypos),
									new Vector2(GameConfig.SIZE_GROUND_X,GameConfig.SIZE_GROUND_Y),
									type);
		if(checkGround(type))
			matrix[xpos][ypos] = 'g';
		else{
			matrix[xpos][ypos] = '-';
		}
		groundObjects.add(ground);
									
	}
	
	private boolean checkGround(String tmp){
		
		if(tmp.equals("1") ||
				tmp.equals("2") ||
				tmp.equals("3") ||
				tmp.equals("7") ||
				tmp.equals("11") ||
				tmp.equals("14") ||
				tmp.equals("15") ||
				tmp.equals("16")
			){
			
			return true;
		}
		return false;
		
	}
	
	protected final char[][] getMatrix(){
		return matrix;
	}
	
	protected final List<Ground> getGround(){
		return groundObjects;
	}
	
	protected final Player getPlayer(){
		Player p = null;
		boolean stop = false;
		for(int i = 0; i < matrix[i].length; i++){
			for(int j = 0; j < matrix.length; j++){
				if(matrix[j][i] == 'g'){
					p = new Player(new Vector2(j-2,i),
									new Vector2(GameConfig.SIZE_PLAYER_X, GameConfig.SIZE_PLAYER_Y),
										'r');
					matrix[j-2][i] = 'p';
					stop = true;
					break;
				}
			}
			if(stop)
				break;
		}
		return p;
	}
	

}
