package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ZombieGame;

public class Main {
	private final static int WIDTH = 1920;
	private final static int HEIGHT = 1024;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Zombie game";
		config.vSyncEnabled = true;
		config.useGL30 = true;
		config.width = WIDTH;
		config.height = HEIGHT;
		
		new LwjglApplication(new ZombieGame(), config);
	}
}


