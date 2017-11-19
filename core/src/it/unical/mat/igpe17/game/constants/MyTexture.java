package it.unical.mat.igpe17.game.constants;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class MyTexture {
	
	public Map<String,Texture> map = new HashMap<String,Texture>();
	
	//TODO rendere flyweight questa classe
	public MyTexture() {
		
		map.put("1", new Texture(Gdx.files.internal(Asset.GROUND1)));
		map.put("2", new Texture(Gdx.files.internal(Asset.GROUND2)));
		map.put("3", new Texture(Gdx.files.internal(Asset.GROUND3)));
		map.put("4", new Texture(Gdx.files.internal(Asset.GROUND4)));
		map.put("5", new Texture(Gdx.files.internal(Asset.GROUND5)));
		map.put("6", new Texture(Gdx.files.internal(Asset.GROUND6)));
		map.put("7", new Texture(Gdx.files.internal(Asset.GROUND7)));
		map.put("8", new Texture(Gdx.files.internal(Asset.GROUND8)));
		map.put("9", new Texture(Gdx.files.internal(Asset.GROUND9)));
		map.put("10", new Texture(Gdx.files.internal(Asset.GROUND10)));
		map.put("11", new Texture(Gdx.files.internal(Asset.GROUND11)));
		map.put("12", new Texture(Gdx.files.internal(Asset.GROUND12)));
		map.put("13", new Texture(Gdx.files.internal(Asset.GROUND13)));
		map.put("14", new Texture(Gdx.files.internal(Asset.GROUND14)));
		map.put("15", new Texture(Gdx.files.internal(Asset.GROUND15)));
		map.put("16", new Texture(Gdx.files.internal(Asset.GROUND16)));
		
		map.put("100",new Texture(Gdx.files.internal(Asset.PLAYER)));
		
	}
	
	
	
	public final Texture getTexture(String key){
		return map.get(key);
	}

}
