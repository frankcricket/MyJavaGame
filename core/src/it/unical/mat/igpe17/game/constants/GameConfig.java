package it.unical.mat.igpe17.game.constants;

import com.badlogic.gdx.math.Vector2;

public class GameConfig {

	public static int BACKGROUND_MOVE_SPEED = 50; // pixels per second.

	public static Vector2 GRAVITY = new Vector2(2f, 0);

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
	public static Vector2 FALLING_POS_VELOCITY = new Vector2(0, +9.5f);
	public static Vector2 FALLING_NEG_VELOCITY = new Vector2(0, -9.5f);
	
	/*
	 * Velocità salto player
	 */
	public static final Vector2 POSITIVE_JUMP_VELOCITY = new Vector2(-14f, 9f);
	public static final Vector2 NEGATIVE_JUMP_VELOCITY = new Vector2(+14f, 9f);
	public static final Vector2 GRAVITY_JUMP = new Vector2(1f,0.05f);

	public static final int SIZE_PLAYER_X = 1;

	public static final int SIZE_PLAYER_Y = 2; 

	public static final int SIZE_OBSTALCE_Y = 1;

	public static final int SIZE_OBSTALCE_X = 1;

	public static final int SIZE_GROUND_X = 1;

	public static final int SIZE_GROUND_Y = 1;

	public static final int SIZE_ENEMY_X = 1;

	public static final int SIZE_ENEMY_Y = 2;
	
	public static final int SIZE_MOVE_ENEMY = 5;
	


}
