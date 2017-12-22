package it.unical.mat.igpe17.game.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import it.unical.mat.igpe17.game.Main.GameTest;
import it.unical.mat.igpe17.game.constants.Asset;

public class Main {
	
	
	public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Zombie Game";
        cfg.width = Asset.WIDTH;
        cfg.height = Asset.HEIGHT;
        new LwjglApplication(new GameTest(), cfg);
    }

}	


