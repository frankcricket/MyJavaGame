package it.unical.mat.igpe17.game.objects;

import com.badlogic.gdx.math.Vector2;

public class Ground extends StaticObject{
	
	private String type;

	public Ground(Vector2 position, Vector2 size, String type ) {
		super(position, size);
		
		this.type = type;
	}
	
	public final String getType(){
		return this.type;
	}

}
