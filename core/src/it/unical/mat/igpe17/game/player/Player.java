package it.unical.mat.igpe17.game.player;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Player extends DynamicObject implements IPlayer{
	
	private int points;
	

	public Player(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);
		
		points = 0;
	}
	
	@Override
	public void move(float dt) {
		super.move(dt);
	}

	@Override
	public void jump() {
		
		
		//TODO
		
		
	}
	
	@Override
	public boolean collide(ICollidable object) {
		if(object instanceof DynamicObject)
			return this.collide(((DynamicObject)object));
		return false;
	}
	
	public void score(final int _score){
		points += _score;
	}
	
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

}
