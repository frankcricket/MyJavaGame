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
		player = null;
		matrix = null;
		groundObjects = null;		
	}
	
	public void loadLevel(){
		Reader reader = new Reader(LEVEL);
		reader.parse();
		matrix = reader.getMatrix();
		groundObjects = reader.getGround();
		
		player = reader.getPlayer();
	}
	
	public void movePlayer(char dir, float dt){
		
		int xtmp = (int) player.getPosition().x ;
		int ytmp = (int) player.getPosition().y;
		
		System.out.println(player.getPosition());
		
		switch(dir){
		case 'l':{
			ytmp -= 1;
			if(!(ytmp < 0)){
				if(matrix[xtmp][ytmp] == ' '){
					matrix[xtmp][ytmp+1] = ' ';
					matrix[xtmp][ytmp] = 'p';
					player.move(dir, -dt);
					
				}
			}			
			break;
		}
		case 'r':{
			
			ytmp += 1;
			if(ytmp < matrix[xtmp].length){
				if(matrix[xtmp][ytmp] == ' '){
					matrix[xtmp][ytmp-1] = ' ';
					matrix[xtmp][ytmp] = 'p';
					player.move(dir, dt);
				}
				
			}
			
			break;
		}
		default: break;
		}
	}
	
	public void setLevel(String level){
		LEVEL = level;
	}
	public final List<Ground> getGround(){
		return groundObjects;
	}
	
	public final Player getPlayer(){
		return player;
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
	
	public static void main(String[] args){
		
		Game g = new Game();
		g.loadLevel();
		for(int i = 0; i < 10; i++){
			g.movePlayer('r', (float)1);
			g.print();
		}
	}

}
