package it.unical.mat.igpe17.game.player;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.IMovable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Enemy extends DynamicObject {

	private Vector2 velocity;

	private int lives = 3 // quante vite ha il nostro nemico

	public int getLives() {
		return lives;
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

	public Enemy(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);
		velocity = new Vector2(0,0);
	}

	@Override
	public void move(char dir, float dt) {
		setPosition(getPosition().add(velocity.scl(dt)));
		if (getPosition().y < 0.0f)
			setPosition(new Vector2(getPosition().x, 0f));
	}

}
