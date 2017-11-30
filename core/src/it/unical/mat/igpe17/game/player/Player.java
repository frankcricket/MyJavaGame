package it.unical.mat.igpe17.game.player;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IMovable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Player extends DynamicObject implements IPlayer , IMovable{
	
	private int points;
	private Vector2 jumpVelocity;
	

	public Player(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);
		
		jumpVelocity = new Vector2(2,2);
		points = 0;
	}

	public void movePlayer(Vector2 velocity, float dt) {
		

//		super.setPosition(new Vector2(getPosition().x,getPosition().y));
//		float py = getPosition().y;
//		py = py + dt;
//		Vector2 tmpPos = new Vector2(getPosition().x,py);
//		super.setPosition(tmpPos);
		
		setPosition(getPosition().add(velocity.scl(dt)));
				
	}

	@Override
	public void jump(float dt) {
		
		Vector2 currentPos = this.getPosition();
		
		Vector2 tmpPos = currentPos;
		
//		do{
			
			currentPos = currentPos.sub(jumpVelocity.scl(dt));
			
			jumpVelocity = jumpVelocity.add(this.getForce().scl(dt));
			
			this.setPosition(currentPos);
			
			checkBounds(tmpPos);
			System.out.println(getPosition());
//		}while(this.getPosition().y != tmpPos.y);
	}
	
	private void checkBounds(Vector2 pos) {
		
		if(this.getPosition().y + this.getSize().y < Asset.HEIGHT){
			if(this.getPosition().y + this.getSize().y >= pos.y + 256){
				jumpVelocity.set(new Vector2(2,-2));
			}
		}
		
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

	@Override
	public void move(char dir, float dt) {
		// TODO Auto-generated method stub
		
	}
	

}
