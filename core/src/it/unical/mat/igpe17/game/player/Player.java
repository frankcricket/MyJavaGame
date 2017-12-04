package it.unical.mat.igpe17.game.player;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IMovable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Player extends DynamicObject implements IPlayer, IMovable {

	private int points;
	private Vector2 jumpVelocity;
	
	public static boolean IS_JUMPING = false;

	public Player(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);

		jumpVelocity = new Vector2(-2, 2);
		points = 0;
	}

	public void movePlayer(Vector2 velocity, float dt) {

		setPosition(getPosition().add(velocity.scl(dt)));
		if (getPosition().y < 0.0f)
			setPosition(new Vector2(getPosition().x, 0f));
	}
	
	
	float posX = 0;
	float posY = 0;
	
	float velocityX = .05f;
	float velocityY = .05f;
	
	float gravity = 1.5f;
	
	boolean left = true;

	@Override
	public void jump(float dt) {
		
		posX = getPosition().x;
		posY = getPosition().y;
		
		if(posX > 8 && left){
			
			posX -= velocityX *dt;
			posY += velocityY *dt;
			velocityX += gravity;		
			velocityY += gravity;	
		}
		else{
			left = false;
		}
		
		if(!left){
			posX += velocityX *dt;
			posY += velocityY *dt;
			velocityX += gravity;		
			velocityY += gravity;
		}
		
		if(posX > 10) {
			setPosition(new Vector2(10,0));
			reset();
			left = true;
			return;
		}
		
		
		setPosition(new Vector2(posX,posY));



	}

	private void reset() {
		velocityX = .05f;
		velocityY = .05f;
		
		gravity = 1.5f;
	}

	@Override
	public boolean collide(ICollidable object) {
		if (object instanceof DynamicObject)
			return this.collide(((DynamicObject) object));
		return false;
	}

	public void score(final int _score) {
		points += _score;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	
	public void setJumpVelocity(Vector2 jumpVelocity){
		this.jumpVelocity = jumpVelocity;
	}

	@Override
	public void move(char dir, float dt) {
		// TODO Auto-generated method stub

	}

}
