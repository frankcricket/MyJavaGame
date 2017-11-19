package it.unical.mat.igpe17.game.player;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IMovable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Player extends DynamicObject implements IPlayer , IMovable{
	
	private int points;
	

	public Player(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);
		
		points = 0;
	}
	
	@Override
	public void move(char dir, float dt) {
		float py = getPosition().y;
		py = py + dt;
		System.out.println(py);
		Vector2 tmpPos = new Vector2(getPosition().x,py);
		super.setPosition(tmpPos);
		
		
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
