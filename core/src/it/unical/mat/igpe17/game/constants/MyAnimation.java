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
		 * Player male animation: idle
		 */
		aTmp = createAnimation(8, new Texture("animations/player_idle_right.png"), 64, 128, .11f);
//		aTmp = createAnimation(10, new Texture("animations/enemy3_die.png"), 64, 128, .11f);
		animations.put("player_idle_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_idle_left.png"), 64, 128, .11f);
		animations.put("player_idle_left", aTmp);

		/*
		 * Player male animation: running
		 */
		aTmp = createAnimation(8, new Texture("animations/player_run_right.png"), 66, 128, .08f);
		animations.put("player_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_run_left.png"), 66, 128, .08f);
		animations.put("player_run_left", aTmp);

		/*
		 * Player male animation: jump
		 */

		aTmp = createAnimation(6, new Texture("animations/player_jump_right.png"), 64, 128, .18f);
		animations.put("player_jump_right", aTmp);
		aTmp = createAnimation(6, new Texture("animations/player_jump_left.png"), 64, 128, .18f);
		animations.put("player_jump_left", aTmp);	
		
		/*
		 * Player male animation: dizzy
		 */

		aTmp = createAnimation(2, new Texture("animations/player_m_dizzy_right.png"), 69, 90, .10f);
		animations.put("player_m_dizzy_right", aTmp);
		aTmp = createAnimation(2, new Texture("animations/player_m_dizzy_left.png"), 69, 90, .10f);
		animations.put("player_m_dizzy_left", aTmp);	
		
		/*
		 * Player male animation: flash idle
		 */
		aTmp = createAnimation(8, new Texture("animations/player_m_flash_idle_right.png"), 64, 128, .11f);
		animations.put("player_m_flash_idle_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_m_flash_idle_left.png"), 64, 128, .11f);
		animations.put("player_m_flash_idle_left", aTmp);
		
		//----------------------------------------------  GUN  ---------------------------------------------
 		
		/*
		 * Player male animation: idle with gun
		 */
		aTmp = createAnimation(8, new Texture("animations/player_m_idle_with_gun_right.png"), 64, 128, .30f);
		animations.put("player_m_idle_with_gun_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_m_idle_with_gun_left.png"), 64, 128, .30f);
		animations.put("player_m_idle_with_gun_left", aTmp);

		/*
		 * Player male animation: running with gun
		 */
		aTmp = createAnimation(8, new Texture("animations/player_m_run_with_gun_right.png"), 64, 128, .10f);
		animations.put("player_m_run_with_gun_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_m_run_with_gun_left.png"), 64, 128, .10f);
		animations.put("player_m_run_with_gun_left", aTmp);
		
		/*
		 * Player male animation: jump with run
		 */

		aTmp = createAnimation(6, new Texture("animations/player_m_jump_with_gun_right.png"), 64, 128, .18f);
		animations.put("player_m_jump_with_gun_right", aTmp);
		aTmp = createAnimation(6, new Texture("animations/player_m_jump_with_gun_left.png"), 64, 128, .18f);
		animations.put("player_m_jump_with_gun_left", aTmp);
		
		
		/*
		 * Player male animation: shot with gun
		 */
		aTmp = createAnimation(4, new Texture("animations/player_m_shot_right.png"), 64, 128, 0.11f);
		animations.put("player_m_shot_right", aTmp);
		aTmp = createAnimation(4, new Texture("animations/player_m_shot_left.png"), 64, 128, 0.11f);
		animations.put("player_m_shot_left", aTmp);
		
		/*
		 * ---------------------------------------------------------------------------------------------------------- 
		 * 										FEMALE
		 * ---------------------------------------------------------------------------------------------------------- 
		 */
		
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

		aTmp = createAnimation(6, new Texture("animations/player_w_jump_right.png"), 64, 128, .08f);
		animations.put("player_w_jump_right", aTmp);
		aTmp = createAnimation(6, new Texture("animations/player_w_jump_left.png"), 64, 128, .08f);
		animations.put("player_w_jump_left", aTmp);
		
		/*
		 * Player female animation: dizzy
		 */

		aTmp = createAnimation(2, new Texture("animations/player_w_dizzy_right.png"), 69, 90, .10f);
		animations.put("player_w_dizzy_right", aTmp);
		aTmp = createAnimation(2, new Texture("animations/player_w_dizzy_left.png"), 69, 90, .10f);
		animations.put("player_w_dizzy_left", aTmp);	
		
		/*
		 * Player female animation: flash idle
		 */
		aTmp = createAnimation(8, new Texture("animations/player_w_flash_idle_right.png"), 64, 128, .11f);
		animations.put("player_w_flash_idle_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/player_w_flash_idle_left.png"), 64, 128, .11f);
		animations.put("player_w_flash_idle_left", aTmp);
		
		
		//----------------------------------------------  GUN  ---------------------------------------------
 		
				/*
				 * Player female animation: idle with gun
				 */
				aTmp = createAnimation(8, new Texture("animations/player_w_idle_with_gun_right.png"), 64, 128, .30f);
				animations.put("player_w_idle_with_gun_right", aTmp);
				aTmp = createAnimation(8, new Texture("animations/player_w_idle_with_gun_left.png"), 64, 128, .30f);
				animations.put("player_w_idle_with_gun_left", aTmp);

				/*
				 * Player female animation: running with gun
				 */
				aTmp = createAnimation(8, new Texture("animations/player_w_run_with_gun_right.png"), 64, 128, .10f);
				animations.put("player_w_run_with_gun_right", aTmp);
				aTmp = createAnimation(8, new Texture("animations/player_w_run_with_gun_left.png"), 64, 128, .10f);
				animations.put("player_w_run_with_gun_left", aTmp);
				
				/*
				 * Player female animation: jump with run
				 */

				aTmp = createAnimation(6, new Texture("animations/player_w_jump_with_gun_right.png"), 64, 128, .18f);
				animations.put("player_w_jump_with_gun_right", aTmp);
				aTmp = createAnimation(6, new Texture("animations/player_w_jump_with_gun_left.png"), 64, 128, .18f);
				animations.put("player_w_jump_with_gun_left", aTmp);
				
				
				/*
				 * Player female animation: shot with gun
				 */
				aTmp = createAnimation(4, new Texture("animations/player_w_shot_right.png"), 64, 128, 0.11f);
				animations.put("player_w_shot_right", aTmp);
				aTmp = createAnimation(4, new Texture("animations/player_w_shot_left.png"), 64, 128, 0.11f);
				animations.put("player_w_shot_left", aTmp);
		
		//---------------------------------------------------------------------------------------------------------
		//---------------------------------  ENEMIES ANIMATIONS     -----------------------------------------------

		/*
		 * Enemy1 animation: running
		 */
		aTmp = createAnimation(8, new Texture("animations/enemy1_run_left.png"), 64, 128, .11f);
		animations.put("enemy1_run_left", aTmp);
		aTmp = createAnimation(8, new Texture("animations/enemy1_run_right.png"), 64, 128, .11f);
		animations.put("enemy1_run_right", aTmp);
		/*
		 * Enemy1 animation: attack
		 */
		aTmp = createAnimation(6, new Texture("animations/enemy1_attack_left.png"), 64, 128, .11f);
		animations.put("enemy1_attack_left", aTmp);
		aTmp = createAnimation(6, new Texture("animations/enemy1_attack_right.png"), 64, 128, .11f);
		animations.put("enemy1_attack_right", aTmp);
		/*
		 * Enemy1 animation: die
		 */
		aTmp = createAnimation(10, new Texture("animations/enemy1_die_right.png"), 64, 128, .10f);
		animations.put("enemy1_die_right", aTmp);
		aTmp = createAnimation(10, new Texture("animations/enemy1_die_left.png"), 64, 128, .10f);
		animations.put("enemy1_die_left", aTmp);

		/*
		 * Enemy2 female animation: running
		 */
		aTmp = createAnimation(8, new Texture("animations/enemy2_w_run_right.png"), 64, 128, .11f);
		animations.put("enemy2_w_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/enemy2_w_run_left.png"), 64, 128, .11f);
		animations.put("enemy2_w_run_left", aTmp);
		/*
		 * Enemy2 female animation: attack
		 */
		aTmp = createAnimation(6, new Texture("animations/enemy2_attack_right.png"), 64, 128, .11f);
		animations.put("enemy2_attack_right", aTmp);
		aTmp = createAnimation(6, new Texture("animations/enemy2_attack_left.png"), 64, 128, .11f);
		animations.put("enemy2_attack_left", aTmp);
		/*
		 * Enemy2 animation: die
		 */
		aTmp = createAnimation(10, new Texture("animations/enemy2_die_right.png"), 64, 128, .10f);
		animations.put("enemy2_die_right", aTmp);
		aTmp = createAnimation(10, new Texture("animations/enemy2_die_left.png"), 64, 128, .10f);
		animations.put("enemy2_die_left", aTmp);
		
		
		/*
		 * Enemy3 animation: running
		 */
		aTmp = createAnimation(8, new Texture("animations/enemy3_m_run_right.png"), 64, 128, .11f);
		animations.put("enemy3_m_run_right", aTmp);
		aTmp = createAnimation(8, new Texture("animations/enemy3_m_run_left.png"), 64, 128, .11f);
		animations.put("enemy3_m_run_left", aTmp);
		/*
		 * Enemy3 animation: attack
		 */
		aTmp = createAnimation(6, new Texture("animations/enemy3_attack_right.png"), 64, 128, .11f);
		animations.put("enemy3_attack_right", aTmp);
		aTmp = createAnimation(6, new Texture("animations/enemy3_attack_left.png"), 64, 128, .11f);
		animations.put("enemy3_attack_left", aTmp);
		/*
		 * Enemy3 animation: die
		 */
		aTmp = createAnimation(10, new Texture("animations/enemy3_die_right.png"), 64, 128, .10f);
		animations.put("enemy3_die_right", aTmp);
		aTmp = createAnimation(10, new Texture("animations/enemy3_die_left.png"), 64, 128, .10f);
		animations.put("enemy3_die_left", aTmp);

		
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
		/*
		 * little_coin animation
		 */
		aTmp = createAnimation(9, new Texture("animations/little_coin.png"),50, 50, .09f);
		animations.put("little_coin", aTmp);
		
		/*
		 * Explosion animation
		 */
		aTmp = createAnimation(3, new Texture("animations/explosion.png"),70, 70, 0.3f);
		animations.put("explosion", aTmp);
		
		
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
