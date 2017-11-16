package it.unical.mat.igpe17.game.constants;

import java.util.HashMap;
import java.util.Map;

public class Asset {
	
	public static int WIDTH = 1920;
	public static int HEIGHT = 512;
	
	public static final int TILE = 64;
	
	
	public static Map<String, String> map = new HashMap<String,String>();
	
	public static String BACKGROUND = "assets/Background.png";
	
	public static String GROUND1 = "assets/resized/Ground1.png";
	public static String GROUND2 = "assets/resized/Ground2.png";
	public static String GROUND3 = "assets/resized/Ground3.png";
	public static String GROUND4 = "assets/resized/Ground4.png";
	public static String GROUND5 = "assets/resized/Ground5.png";
	public static String GROUND6 = "assets/resized/Ground6.png";
	public static String GROUND7 = "assets/resized/Ground7.png";
	public static String GROUND8 = "assets/resized/Ground8.png";
	public static String GROUND9 = "assets/resized/Ground9.png";
	public static String GROUND10 = "assets/resized/Ground10.png";
	public static String GROUND11 = "assets/resized/Ground11.png";
	public static String GROUND12 = "assets/resized/Ground12.png";
	public static String GROUND13 = "assets/resized/Ground13.png";
	public static String GROUND14 = "assets/resized/Ground14.png";
	public static String GROUND15 = "assets/resized/Ground15.png";
	public static String GROUND16 = "assets/resized/Ground16.png";
	
	static{
		map.put("1", GROUND1);
		map.put("2", GROUND2);
		map.put("3", GROUND3);
		map.put("4", GROUND4);
		map.put("5", GROUND5);
		map.put("6", GROUND6);
		map.put("7", GROUND7);
		map.put("8", GROUND8);
		map.put("9", GROUND9);
		map.put("10", GROUND10);
		map.put("11", GROUND11);
		map.put("12", GROUND12);
		map.put("13", GROUND13);
		map.put("14", GROUND14);
		map.put("15", GROUND15);
		map.put("16", GROUND16);
	}
	
	public static String getPath(String s){
		return map.get(s);
	}
	
	
	public static String BIN = "assets/buttons/Bin.png";
	public static String UNDO = "assets/buttons/Undo.png";

}
