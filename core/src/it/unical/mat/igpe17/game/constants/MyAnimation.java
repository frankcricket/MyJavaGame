package it.unical.mat.igpe17.game.constants;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {

	private Map<String, Animation<TextureRegion>> animations = new HashMap<String, Animation<TextureRegion>>();

	private Animation<TextureRegion> aTmp;

	public MyAnimation() {

		/*
		 * Player male animation: running
		 */
		aTmp = createAnimation(8, new Texture("animations/player_run_right.png"), 66, 128, .10f);
		animations.put("player_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_run_left.png"), 66, 128, .10f);
		animations.put("player_run_left", aTmp);
		
		/*
		 * Player male animation: running with gun
		 */
		aTmp = createAnimation(8, new Texture("animations/player_m_run_with_gun_right.png"), 64, 128, .10f);
		animations.put("player_m_run_with_gun_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_m_run_with_gun_left.png"), 64, 128, .10f);
		animations.put("player_m_run_with_gun_left", aTmp);

		/*
		 * Player male animation: jump
		 */

		aTmp = createAnimation(8, new Texture("animations/player_jump_right.png"), 64, 128, .08f);
		animations.put("player_jump_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_jump_left.png"), 64, 128, .08f);
		animations.put("player_jump_left", aTmp);

		/*
		 * Player male animation: idle
		 */
		aTmp = createAnimation(8, new Texture("animations/player_idle_right.png"), 64, 128, .11f);
		animations.put("player_idle_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_idle_left.png"), 64, 128, .11f);
		animations.put("player_idle_left", aTmp);
		
		/*
		 * Player male animation: idle with gun
		 */
		aTmp = createAnimation(8, new Texture("animations/player_m_idle_with_gun_right.png"), 64, 128, .30f);
		animations.put("player_m_idle_with_gun_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_m_idle_with_gun_left.png"), 64, 128, .30f);
		animations.put("player_m_idle_with_gun_left", aTmp);

		/*
		 * Player female: running
		 */

		aTmp = createAnimation(8, new Texture("animations/player_w_run_right.png"), 64, 128, .10f);
		animations.put("player_w_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_w_run_left.png"), 64, 128, .10f);
		animations.put("player_w_run_left", aTmp);
		
		/*
		 * Player female animation: running with gun
		 */
		aTmp = createAnimation(8, new Texture("animations/player_f_run_with_gun_right.png"), 64, 128, .10f);
		animations.put("player_f_run_with_gun_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_f_run_with_gun_left.png"), 64, 128, .10f);
		animations.put("player_f_run_with_gun_left", aTmp);

		/*
		 * Player female: idle
		 */

		aTmp = createAnimation(8, new Texture("animations/player_w_idle_right.png"), 64, 128, .11f);
		animations.put("player_w_idle_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_w_idle_left.png"), 64, 128, .11f);
		animations.put("player_w_idle_left", aTmp);

		/*
		 * Player female: jump
		 */

		aTmp = createAnimation(8, new Texture("animations/player_w_jump_right.png"), 64, 128, .08f);
		animations.put("player_w_jump_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_w_jump_left.png"), 64, 128, .08f);
		animations.put("player_w_jump_left", aTmp);

		/*
		 * Enemy1 animation: running
		 */

		aTmp = createAnimation(8, new Texture("animations/enemy_run_left.png"), 64, 128, .11f);
		animations.put("enemy_run_left", aTmp);
		aTmp = createAnimation(8, new Texture("animations/enemy_run_right.png"), 64, 128, .11f);
		animations.put("enemy_run_right", aTmp);

		/*
		 * Enemy2 animation: running
		 */

		aTmp = createAnimation(8, new Texture("animations/enemy2_m_run_right.png"), 64, 128, .11f);
		animations.put("enemy2_m_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/enemy2_m_run_left.png"), 64, 128, .11f);
		animations.put("enemy2_m_run_left", aTmp);

		/*
		 * Enemy3 female animation: running
		 */

		aTmp = createAnimation(8, new Texture("animations/enemy1_w_run_right.png"), 64, 128, .11f);
		animations.put("enemy1_w_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/enemy1_w_run_left.png"), 64, 128, .11f);
		animations.put("enemy1_w_run_left", aTmp);

		
		/*
		 * Loading animation
		 */
		aTmp = createAnimation(15, new Texture("animations/loading.gif"), 256, 64, .11f);
		animations.put("loading", aTmp);
		
		
		
		/*
		 * Coins animation
		 */
		aTmp = createAnimation(4, new Texture("animations/coins.png"),64, 64, .16f);
		animations.put("coins", aTmp);
		
		
	}

	private Animation<TextureRegion> createAnimation(int elements, Texture image, int width, int height, float speed) {
		TextureRegion[] regionFrame = new TextureRegion[elements];
		TextureRegion[][] tmpFrames = TextureRegion.split(image, width, height);
		for (int i = 0; i < elements; i++) {
			regionFrame[i] = tmpFrames[0][i];
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
