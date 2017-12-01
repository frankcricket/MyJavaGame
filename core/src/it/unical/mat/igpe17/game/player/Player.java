package it.unical.mat.igpe17.game.player;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IMovable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Player extends DynamicObject implements IPlayer, IMovable {

	private int points;
	private Vector2 jumpVelocity;

	public Player(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);

		jumpVelocity = new Vector2(-2, 2);
		points = 0;
	}

	public void movePlayer(Vector2 velocity, float dt) {

		setPosition(getPosition().add(velocity.scl(dt)));
		if (getPosition().y < 0.0f)
			setPosition(new Vector2(getPosition().x, 0f));
	}

	@Override
	public void jump(float dt) {
		
		Vector2 oldPos = getPosition();
		System.out.println("Old pos: " + oldPos);
		Vector2 newPos = getPosition().add(jumpVelocity).scl(dt);
		oldPos.add(newPos);
		setPosition(oldPos);
		System.out.println("New pos: " + getPosition());
		Vector2 vel = jumpVelocity;
		vel.add(jumpVelocity.add(getForce().scl(dt)));
		setJumpVelocity(vel);
		checkBounds();


	}

	private void checkBounds() {

		if ((this.getPosition().y + this.getSize().y) > Asset.HEIGHT/2) {
				jumpVelocity.set(new Vector2(2, -2));

		}


	}

	@Override
	public boolean collide(ICollidable object) {
		if (object instanceof DynamicObject)
			return this.collide(((DynamicObject) object));
		return false;
	}

	public void score(final int _score) {
		points += _score;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	
	public void setJumpVelocity(Vector2 jumpVelocity){
		this.jumpVelocity = jumpVelocity;
	}

	@Override
	public void move(char dir, float dt) {
		// TODO Auto-generated method stub

	}

}
