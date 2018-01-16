package it.unical.mat.igpe17.game.logic;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Bullet extends DynamicObject{
	
	public Bullet(Vector2 position, Vector2 size, char direction) {
		super(position, size, direction);
	}
	
	Vector2 vel = new Vector2(0f,0.2f);
	Vector2 grav = new Vector2(3,2);
	
	public void move(float dt) {
		
		float currentx = getPosition().x;
		float currenty = getPosition().y;
		
		if(getDirection() == 'r'){			
			float newx = currentx + (vel.x + grav.x * dt) - 0.07f;
			float newy = currenty + (vel.y + grav.y * dt) - 0.01f;
			
			float newGrav_x = grav.x + grav.x * dt;
			float newGrav_y = grav.y;
			grav = new Vector2(newGrav_x,newGrav_y);
			setPosition(new Vector2(newx,newy));
		}
		else{
			float newx = currentx + (vel.x + grav.x * dt) - 0.07f;
			float newy = currenty - (vel.y + grav.y * dt) + 0.01f;
			
			float newGrav_x = grav.x + grav.x * dt;
			float newGrav_y = grav.y;
			grav = new Vector2(newGrav_x,newGrav_y);
			setPosition(new Vector2(newx,newy));
		}
	}
	


}
