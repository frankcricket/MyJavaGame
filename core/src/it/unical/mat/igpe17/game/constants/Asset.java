package it.unical.mat.igpe17.game.constants;

import java.util.HashMap;
import java.util.Map;

public class Asset {
	
	public static int WIDTH = 1280;
	public static int HEIGHT = 768;
	
	public static final int TILE = 64;
	
	
	public static Map<String, String> map = new HashMap<String,String>();
	
	public static final String FIRST_LEVEL = "levels/firstLevel.tmx";
	
	public static String BACKGROUND = "asset/Background.png";
	public static String PLAYER = "asset/Player.png";
	
	public static String GROUND1 = "asset/resized/Ground1.png";
	public static String GROUND2 = "asset/resized/Ground2.png";
	public static String GROUND3 = "asset/resized/Ground3.png";
	public static String GROUND4 = "asset/resized/Ground4.png";
	public static String GROUND5 = "asset/resized/Ground5.png";
	public static String GROUND6 = "asset/resized/Ground6.png";
	public static String GROUND7 = "asset/resized/Ground7.png";
	public static String GROUND8 = "asset/resized/Ground8.png";
	public static String GROUND9 = "asset/resized/Ground9.png";
	public static String GROUND10 = "asset/resized/Ground10.png";
	public static String GROUND11 = "asset/resized/Ground11.png";
	public static String GROUND12 = "asset/resized/Ground12.png";
	public static String GROUND13 = "asset/resized/Ground13.png";
	public static String GROUND14 = "asset/resized/Ground14.png";
	public static String GROUND15 = "asset/resized/Ground15.png";
	public static String GROUND16 = "asset/resized/Ground16.png";
	
	
	public static String OBSTACLE1 = "asset/Obstacles/obstacle17.png";
	public static String OBSTACLE2 = "asset/Obstacles/obstacle18.png";
	public static String OBSTACLE3 = "asset/Obstacles/obstacle19.png";
	public static String OBSTACLE4 = "asset/Obstacles/obstacle20.png";
	public static String OBSTACLE5 = "asset/Obstacles/obstacle21.png";
	public static String OBSTACLE6 = "asset/Obstacles/obstacle22.png";
	public static String OBSTACLE7 = "asset/Obstacles/obstacle23.png";
	public static String OBSTACLE8 = "asset/Obstacles/obstacle24.png";
	public static String OBSTACLE9 = "asset/Obstacles/obstacle25.png";
	public static String OBSTACLE10 = "asset/Obstacles/obstacle26.png";
	public static String OBSTACLE11 = "asset/Obstacles/obstacle27.png";
	public static String OBSTACLE12 = "asset/Obstacles/obstacle28.png";
	public static String OBSTACLE13 = "asset/Obstacles/obstacle29.png";
	public static String OBSTACLE14 = "asset/Obstacles/obstacle30.png";
	
	
	static{
		
		//ground 
		
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
		
		
		//obstacles
		map.put("17", OBSTACLE1);
		map.put("18", OBSTACLE2);
		map.put("19", OBSTACLE3);
		map.put("20", OBSTACLE4);
		map.put("21", OBSTACLE5);
		map.put("22", OBSTACLE6);
		map.put("23", OBSTACLE7);
		map.put("24", OBSTACLE8);
		map.put("25", OBSTACLE9);
		map.put("26", OBSTACLE10);
		map.put("27", OBSTACLE11);
		map.put("28", OBSTACLE12);
		map.put("29", OBSTACLE13);
		map.put("30", OBSTACLE14);
		
		
		
	}
	
	public static String getPath(String s){
		return map.get(s);
	}
	
	
	public static String BIN = "asset/buttons/Bin.png";
	public static String UNDO = "asset/buttons/Undo.png";

}
