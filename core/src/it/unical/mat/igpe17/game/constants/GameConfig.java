package it.unical.mat.igpe17.game.constants;

import com.badlogic.gdx.math.Vector2;

public class GameConfig {

	public static int BACKGROUND_MOVE_SPEED = 50; // pixels per second.

	public static Vector2 GRAVITY = new Vector2(0.3f, 0);

	public static Vector2 JUMP_POS_FORCE = new Vector2(+10.0f, 0f);
	public static Vector2 JUMP_NEG_FORCE = new Vector2(-10.0f, 0f);

	/*
	 * Movimento sull'asse delle ascisse
	 */
	public static Vector2 PLAYER_POS_VELOCITY = new Vector2(0, +3.0f);
	public static Vector2 PLAYER_NEG_VELOCITY = new Vector2(0, -3.0f);
	
	/*
	 * Velocità salto player
	 */
	public static Vector2 PLAYER_JUMP_POS_VELOCITY = new Vector2(18f, 10f);

	public static final int SIZE_PLAYER_X = 1;

	public static final int SIZE_PLAYER_Y = 2; // TODO sistemare le size

	public static final float SIZE_OBSTALCE_Y = 4;

	public static final float SIZE_OBSTALCE_X = 24;

	public static final float SIZE_GROUND_X = 128;

	public static final float SIZE_GROUND_Y = 128;

}
