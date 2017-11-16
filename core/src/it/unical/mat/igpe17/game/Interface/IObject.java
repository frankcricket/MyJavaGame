package it.unical.mat.igpe17.game.Interface;

import com.badlogic.gdx.math.Vector2;

public interface IObject {

	//return the size in pixels of the object
	public Vector2 getSize();
	//returns the coordinate of the upper left corner 
	public Vector2 getPosition();
	//return the force which is applied to the object
	public Vector2 getForce();
	//returns the velocity of the object
	public Vector2 getVelocity();

	public void setPosition(Vector2 position);
	public void setVelocity(Vector2 velocity);
	public void setForce(Vector2 force);
	public void setSize(Vector2 size);

}
