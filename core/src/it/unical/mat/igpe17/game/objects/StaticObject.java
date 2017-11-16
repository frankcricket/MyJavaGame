package it.unical.mat.igpe17.game.objects;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IObject;

public class StaticObject implements IObject, ICollidable{
	
	Vector2 position;
	Vector2 size;
	
	public StaticObject(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
	}
	

	@Override
	public boolean collide(ICollidable object) {
		
		
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public Vector2 getSize() {
		return size;
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public Vector2 getForce() {
		return null;
	}

	@Override
	public Vector2 getVelocity() {
		return null;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	@Override
	public void setVelocity(Vector2 velocity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setForce(Vector2 force) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setSize(Vector2 size) {
		this.size = size;
	}

}
