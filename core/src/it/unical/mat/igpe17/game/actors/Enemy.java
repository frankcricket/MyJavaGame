package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Enemy extends DynamicObject {

	private int lives = 3; // quante vite ha il nostro nemico
	
	//mosse che fa automaticamente 
	private int moves = GameConfig.SIZE_MOVE_ENEMY;		
	private boolean justOnce = true;
	private int  startingPos;	
	private boolean selectedYet = false;	
	//stringa che per il tipo di nemico che abbiamo letto dal livello
	private String type;	
	private boolean isAlive = true; // il nostro personaggio è vivo o morto?
	
	

	public Enemy(Vector2 position, Vector2 size, char direction, String type) {
		super(position, size, direction);
		this.type = type;
	}
	
	public boolean getJustOnce() {
		return justOnce;
	}

	public void setJustOnce(boolean justOnce) {
		this.justOnce = justOnce;
	}

	public int getStartingPos() {
		return startingPos;
	}

	public void setStartingPos(int startingPos) {
		this.startingPos = startingPos;
	}

	public boolean getSelectedYet() {
		return selectedYet;
	}

	public void setSelectedYet(boolean selectedYet) {
		this.selectedYet = selectedYet;
	}
	
	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getLives() {
		return lives;
	}
	
	public String getType() {
		return type;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	

}
