package it.unical.mat.igpe17.game.objects;

import com.badlogic.gdx.math.Vector2;

public class DynamicObject extends StaticObject {

	private char direction;

	public DynamicObject(Vector2 position, Vector2 size, char direction) {
		super(position, size);
		this.direction = direction;
	}

	public void setDirection(char dir) {
		this.direction = dir;
	}
	public final char getDirection() {
		return this.direction;
	}
	
	public void move(Vector2 velocity, float dt) {

		setPosition(getPosition().add(velocity.scl(dt)));
		if (getPosition().y < 0.0f)
			setPosition(new Vector2(getPosition().x, 0f));
	}

}
