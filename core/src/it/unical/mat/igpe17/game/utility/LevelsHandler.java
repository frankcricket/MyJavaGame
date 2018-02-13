package it.unical.mat.igpe17.game.utility;

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class LevelsHandler {
	
	private final String DIR = "levels/default_levels"; 
	
	private static LinkedList<String> allPaths;
	
	public static LevelsHandler handler = null;
	
	private LevelsHandler() {
		allPaths = new LinkedList<>();
		loadCurrentLevels();
	}
	
	public static LevelsHandler getInstance(){
		if(handler == null){
			handler = new LevelsHandler();
		}
		return handler;
	}
	/*
	 * Return path del primo livello. Deve essere presente nella cartella!!
	 */
	public static final String first(){
		if(allPaths.size() == 1) return null;// uguale a 1 perchè viene anche letta la cartella tileset
		Iterator<String> it = allPaths.iterator();
		if(it.hasNext()){
			String level = it.next();
			return level;
		}
		return null;
	}
	
	public static final String next(){
		Iterator<String> it = allPaths.iterator();
		int count = -1;
		while(it.hasNext()){
			if(count < 0){
				it.next();
				count = 0;
				continue;
			}
			if(allPaths.size() == 2) return null;
			String level = it.next();
			it.remove();
			return level;
		}
		return null;
	}
	
	private void loadCurrentLevels(){
		FileHandle listFiles = Gdx.files.internal(DIR);
		for (FileHandle entry: listFiles.list()) {
		   allPaths.add(entry.path());
		}	
	}
	
	/**
	 * Questa funzione crea un nuovo riferimento; avviene quando il player è morto, quindi vengono ricaricati tutti i livelli
	 */
	public static void reload(){
		handler = new LevelsHandler();
	}


}
