package it.unical.mat.igpe17.game.objects;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.IMovable;

public class DynamicObject extends StaticObject {

	private char direction;
	
	public DynamicObject(Vector2 position, Vector2 size, char direction) {
		super(position,size);
		this.direction = direction;
	}	
	
	public void setDirection(char dir){
		this.direction = dir;
	}

}
