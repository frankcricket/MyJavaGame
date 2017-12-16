package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Enemy extends DynamicObject {

	private Vector2 velocity;

	private int lives = 3; // quante vite ha il nostro nemico

	private int range = 5;
	
	private int movesLeft = 0;
	public int getMovesLeft() {
		return movesLeft;
	}

	public void setMovesLeft(int movesLeft) {
		this.movesLeft = movesLeft;
	}

	public int getMovesRight() {
		return movesRight;
	}

	public void setMovesRight(int movesRight) {
		this.movesRight = movesRight;
	}

	private int movesRight = 0;


	public int getLives() {
		return lives;
	}
	
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
