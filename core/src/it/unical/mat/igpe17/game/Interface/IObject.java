package it.unical.mat.igpe17.game.Interface;

import com.badlogic.gdx.math.Vector2;

public interface IObject {

	//return the size in pixels of the object
	public Vector2 getSize();
	//returns the coordinate of the upper left corner 
	public Vector2 getPosition();

	public void setPosition(Vector2 position);
	
	public void setSize(Vector2 size);

}
