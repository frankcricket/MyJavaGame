package it.unical.mat.igpe17.game.constants;

import com.badlogic.gdx.math.Vector2;

public class GameConfig {

	public static int BACKGROUND_MOVE_SPEED = 50; // pixels per second.

	public static Vector2 GRAVITY = new Vector2(2.5f, 0);

	/*
	 * Salto del player solo in verticale
	 */
	public static Vector2 JUMP_POS_FORCE = new Vector2(+5.3f, 0f);
	public static Vector2 JUMP_NEG_FORCE = new Vector2(-3.3f, 0f);

	/*
	 * Movimento sull'asse delle ascisse
	 */
	public static Vector2 PLAYER_POS_VELOCITY = new Vector2(0, +3.0f);
	public static Vector2 PLAYER_NEG_VELOCITY = new Vector2(0, -3.0f);
	
	/*
	 * Velocità caduta del player
	 */
	public static Vector2 FALLING_POS_VELOCITY = new Vector2(0, +10.5f);
	public static Vector2 FALLING_NEG_VELOCITY = new Vector2(0, -10.5f);
	
	/*
	 * Velocità salto player
	 */
	public static final Vector2 POSITIVE_JUMP_VELOCITY = new Vector2(-11f, 6.5f);
	public static final Vector2 NEGATIVE_JUMP_VELOCITY = new Vector2(+12f, 6.5f);
	public static final Vector2 GRAVITY_JUMP = new Vector2(0.45f,0.075f);
	
	
	/*
	 * Posizione delle vite, tempo e punteggio
	 */
	public static final int HP = 700;
	
	// 5 minuti per finire il livello
	public static int DIGIT_1 = 0;
	public static int DIGIT_2 = 5;
	public static int DIGIT_3 = 0;
	public static int DIGIT_4 = 0;
	

	public static final int SIZE_PLAYER_X = 1;

	public static final int SIZE_PLAYER_Y = 2; 

	public static final int SIZE_OBSTALCE_Y = 1;

	public static final int SIZE_OBSTALCE_X = 1;

	public static final int SIZE_GROUND_X = 1;

	public static final int SIZE_GROUND_Y = 1;

	public static final int SIZE_ENEMY_X = 1;

	public static final int SIZE_ENEMY_Y = 2;
	
	public static final int SIZE_MOVE_ENEMY = 5;

	public static final float SIZE_BULLET_X = 0.5f;
	
	public static final float SIZE_BULLET_Y = 0.5f;
	
	
	public static int BEST_SCORE = 0;
	


}
