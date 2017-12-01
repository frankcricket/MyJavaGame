package it.unical.mat.igpe17.game.constants;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {
	
	private Map<String,Animation<TextureRegion>> animations = new HashMap<String, Animation<TextureRegion>>();
	
	private Animation<TextureRegion> aTmp;
	
	public MyAnimation() {

		/*
		 * Player animation: running
		 */
		aTmp = createAnimation(8, new Texture("animations/player_run_right.png"), 66, 128,.10f);
		animations.put("player_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_run_left.png"), 66, 128,.10f);
		animations.put("player_run_left", aTmp);
		
		/*
		 * Player animation: jump
		 */
		
		aTmp = createAnimation(8, new Texture("animations/player_jump.png"), 66, 128,.10f);
		animations.put("player_jump", aTmp);
		
		/*
		 * 
		 */
	}

	private Animation<TextureRegion> createAnimation(int elements, Texture image, int width, int height, float speed) {
		TextureRegion[] regionFrame = new TextureRegion[elements];
		TextureRegion[][] tmpFrames = TextureRegion.split(image, width, height);
		int index = 0;
		for (int i = 0; i < elements; i++) {
			regionFrame[index++] = tmpFrames[0][i];
		}

		return new Animation<TextureRegion>(speed, regionFrame);

	}

	public final Animation<TextureRegion> getAnimation(String name) {
		return animations.get(name);
	}

	public void dispose() {
		animations.clear();
	}

}
