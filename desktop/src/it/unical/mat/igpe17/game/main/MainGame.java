package it.unical.mat.igpe17.game.main;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import it.unical.mat.igpe17.game.Main.ZombieGame;
import it.unical.mat.igpe17.game.constants.Asset;

public class MainGame {
	private final static int WIDTH = Asset.WIDTH;
	private final static int HEIGHT = Asset.HEIGHT;
	
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Zombies game";
		config.vSyncEnabled = true;
		//config.useGL30 = true;
		config.width = WIDTH;
		config.height = HEIGHT;
		config.addIcon("asset/menu_img/game_icon.png", FileType.Internal);
		
		new LwjglApplication(new ZombieGame(), config);
	}
}


