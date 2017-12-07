package it.unical.mat.igpe17.game.objects;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;

public class DynamicObject extends StaticObject {

	private char direction;
	private Vector2 velocity;
	protected Vector2 force;

	public DynamicObject(Vector2 position, Vector2 size, char direction) {
		super(position, size);
		this.direction = direction;
		velocity = new Vector2(0f, 100f);
		force = GameConfig.GRAVITY;
	}
//
//	public void move(final float dt) {
//		
//		
//		applyGravity(dt);
//
//		velocity.set(velocity.add(force.scl(dt)));
//
//		if ((int) (position.y + size.y) >= Asset.HEIGHT && velocity.y > 0) {
//			Vector2 nv = new Vector2(velocity.x, 0);
//			velocity.set(nv);
//		}
//
//		setPosition(getPosition().add(velocity.scl(dt)));
//		if (getPosition().y + getSize().y >= Asset.HEIGHT)
//			getPosition().y = ((int) Asset.HEIGHT - getSize().y);
//
//	}

	void applyGravity(final double dt) {
		if (force.y < GameConfig.GRAVITY.y)
			force.set(force.add(GameConfig.GRAVITY));

		if (position.y + size.y < Asset.HEIGHT)
			force.set(force.add(GameConfig.GRAVITY));
		else if ((int) (position.y + size.y + 1) >= Asset.HEIGHT) {
			Vector2 f = force;
			f.y = 0f;
			force.set(f);
			Vector2 nv = new Vector2(velocity.x, 0);
			velocity.set(nv);
		}
	}

//	private Vector2 computeNewVelocity(Vector2 vel, Vector2 force, float dt) {
//		return vel.add(force.scl(dt));
//	}

	public void setDirection(char dir) {
		this.direction = dir;
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public Vector2 getForce(){
		return force;
	}

}
