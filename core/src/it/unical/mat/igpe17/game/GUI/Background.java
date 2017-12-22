package it.unical.mat.igpe17.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;

public class Background {
	
	private static int BACKGROUND_SPEED = GameConfig.BACKGROUND_MOVE_SPEED;

	private SpriteBatch batch;
	private Texture texture_1;
	private Texture texture_2;
	
	private int widthMax;
	private float xTexture_1;
	private float xTexture_2;
	
	public Background() {
		
		batch = new SpriteBatch();
		texture_1 = new Texture(Gdx.files.internal(Asset.BACKGROUND));
		texture_2 = texture_1;
		
		widthMax = texture_1.getWidth();
		xTexture_1 = 0;
		xTexture_2 = widthMax;
	
	}
	
	protected void update(final float dt){
		
		xTexture_1 -= BACKGROUND_SPEED * dt;
		xTexture_2 -= BACKGROUND_SPEED * dt;
		if (xTexture_1 <= widthMax *(-1)) {
			xTexture_1 = 0; 
			xTexture_2 = widthMax;
		}
		
		batch.begin();
		batch.draw(texture_1,xTexture_1,0);
		batch.draw(texture_2, xTexture_2,0);
		batch.end();
	}
	
	protected void dispose(){
		batch.dispose();
		texture_1.dispose();
	}

}
