package it.unical.mat.igpe17.game.constants;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class Textures {
	
	private static HashMap<String, Texture> map = new HashMap<>();
	
	/*
	 * BULLET
	 */
	public static final Texture BULLET = new Texture(Asset.BULLET);
	
	/*
	 * CLOCK
	 */
	public static final Texture CLOCK = new Texture(Asset.CLOCK);
	
	/*
	 * DIGITS
	 */
	public static final Texture D0 = new Texture(Asset.getPath("d0"));
	public static final Texture D1 = new Texture(Asset.getPath("d1"));
	public static final Texture D2 = new Texture(Asset.getPath("d2"));
	public static final Texture D3 = new Texture(Asset.getPath("d3"));
	public static final Texture D4 = new Texture(Asset.getPath("d4"));
	public static final Texture D5 = new Texture(Asset.getPath("d5"));
	public static final Texture D6 = new Texture(Asset.getPath("d6"));
	public static final Texture D7 = new Texture(Asset.getPath("d7"));
	public static final Texture D8 = new Texture(Asset.getPath("d8"));
	public static final Texture D9 = new Texture(Asset.getPath("d9"));

	/*
	 * DUE-PUNTI (:)
	 */
	public static final Texture DP = new Texture(Asset.getPath("DP"));
	/*
	 * X
	 */
	public static final Texture SYMBOL_X = new Texture(Asset.SYMBOL_X);
	
	/*
	 * VITA
	 */
	public static final Texture LIFE = new Texture(Asset.LIFE);
	public static final Texture HEART = new Texture(Asset.HEART);
	
	/*
	 * PUNTEGGIO: COIN & SCORE
	 */
	public static final Texture COIN = new Texture(Asset.COINS);
	public static final Texture SCORE = new Texture(Asset.SCORE);
	
	/*
	 * DOOR -> EXIT GAME
	 */
	public static final Texture DOOR = new Texture(Asset.OPENED_DOOR);
	
	/*
	 * VITA DEI NEMICI
	 */
	public static final Texture LIFE_BAR_BACKGROUND = new Texture(Asset.LIFE_BAR_BACKGROUND);
	public static final Texture LIFE_BAR = new Texture(Asset.LIFE_BAR);
	
	
	public static final Texture LABEL_M = new Texture(Asset.LABEL_M);
	public static final Texture LABEL_F = new Texture(Asset.LABEL_F);
	
	static{
		map.put("D0", D0);
		map.put("D1", D1);
		map.put("D2", D2);
		map.put("D3", D3);
		map.put("D4", D4);
		map.put("D5", D5);
		map.put("D6", D6);
		map.put("D7", D7);
		map.put("D8", D8);
		map.put("D9", D9);
		
		map.put("DP", DP);
	}
	
	public static final Texture get(String name){
		return map.get(name);
	}
	
	

}
