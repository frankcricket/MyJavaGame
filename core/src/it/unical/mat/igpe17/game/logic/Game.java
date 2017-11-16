package it.unical.mat.igpe17.game.logic;

import java.util.List;

import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {
	
	
	private Player player;
	private char[][] matrix;
	private List<Ground> groundObjects;
	
	
	//set di default
	private static String LEVEL = "levels/first.txt";
	
	private boolean gameOver;
	
	
	public Game() {
		
		Reader reader = new Reader(LEVEL);
		reader.parse();
		matrix = reader.getMatrix();
		groundObjects = reader.getGround();
		
		player = reader.getPlayer();
		
		
	}
	
	public final void print(){
		for(int i = 0; i < matrix.length; i++){
			System.out.print("| ");
			for(int j = 0; j < matrix[i].length; j++){
				System.out.print(matrix[i][j]+" ");
			}
			System.out.print(" |");
			System.out.println();
		}
	}
	
	public void setLevel(String level){
		LEVEL = level;
	}
	
	public static void main(String[] args){
		
		Game g = new Game();
		g.print();
	}

}
