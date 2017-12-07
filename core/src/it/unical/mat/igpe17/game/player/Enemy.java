package it.unical.mat.igpe17.game.player;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.IMovable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Enemy extends DynamicObject implements IPlayer, IMovable{

	public Enemy(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);
		
	}

	@Override
	public void move(char dir, float dt) {
		
		
	}

	@Override
	public void jump(float dt) {
		// TODO Auto-generated method stub
		
	}

}
