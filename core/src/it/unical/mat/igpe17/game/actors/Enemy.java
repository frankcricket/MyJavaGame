package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Enemy extends DynamicObject {

	private Vector2 velocity;

	private int lives = 3; // quante vite ha il nostro nemico
	
	//mosse che fa automaticamente 
	private int moves = GameConfig.SIZE_MOVE_ENEMY;

	//nella classe enemy crei un booleano che chiami --> justOnce inizializzato a true;
	//e un intero che posizione iniziale --> startingPos
	// e una variabile bool che rappresenta il cambio delle mosse totali: bool selectedYet = false;
	//intero per il numero delle mosse totali: --> moves = GameConfig.NUMERO_MOSSE_TOTALI; 
	
	
	private boolean justOnce = true;
	
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

	private int  startingPos;
	
	private boolean selectedYet = false;
	
	
	
	
	
	
	
	
	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getLives() {
		return lives;
	}
	
	//stringa che ritorna il tipo di nemico che abbiamo letto dal livello
	private String type;

	public String getType() {
		return type;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	private boolean isAlive = true; // il nostro personaggio è vivo o morto?
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public Enemy(Vector2 position, Vector2 size, char direction, String type) {
		super(position, size, direction);
		velocity = new Vector2(0,0);
		this.type = type;
	}
	

}
