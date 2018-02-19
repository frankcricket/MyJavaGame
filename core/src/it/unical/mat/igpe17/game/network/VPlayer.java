package it.unical.mat.igpe17.game.network;

import java.util.concurrent.locks.Lock;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.GameConfig;

public class VPlayer extends Player{
	
	protected float pos_fall;
	protected float neg_fall;
	protected boolean player_is_falling;
	protected boolean player_collision;
	
	protected boolean obstacleFound;
	protected boolean moveWhileJumping;
	protected boolean setStartPosition;
	protected Vector2 startPosition;
	
	protected int current_coin_count;
	
	private Lock lock;

	public VPlayer(Vector2 position, Vector2 size, char direction, PlayerState state,Lock lock) {
		super(position, size, direction, state);
		
		pos_fall = GameConfig.FALLING_POS_VELOCITY.y;
		neg_fall = GameConfig.FALLING_NEG_VELOCITY.y;
		
		player_is_falling = false;
		player_collision = false;
		
		obstacleFound = false;
		moveWhileJumping = false;
		setStartPosition = true;
		startPosition = new Vector2();
		
		current_coin_count = 0;
		
		this.lock = lock;
	}
	
	@Override
	public void setState(PlayerState state) {
		lock.lock();
		super.setState(state);
		lock.unlock();
	}


}
