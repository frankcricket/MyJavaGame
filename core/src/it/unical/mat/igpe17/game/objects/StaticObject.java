package it.unical.mat.igpe17.game.objects;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IObject;

public class StaticObject implements IObject, ICollidable {

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
	public final Vector2 getSize() {
		return size;
	}

	@Override
	public final Vector2 getPosition() {
		return position;
	}

	@Override
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	@Override
	public void setSize(Vector2 size) {
		this.size = size;
	}

}
