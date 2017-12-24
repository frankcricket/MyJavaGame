package it.unical.mat.igpe17.game.objects;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.IObject;

public class StaticObject implements IObject {

	Vector2 position;
	Vector2 size;

	public StaticObject(Vector2 position, Vector2 size) {
		this.position = position;
		this.size = size;
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
