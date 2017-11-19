package it.unical.mat.igpe17.game.testGui;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import it.unical.mat.igpe17.game.guiTest.GameTest;

public class Main {
	
	public static final int INTERNAL_WIDTH = 1280;
	public static final int INTERNAL_HEIGHT = 512;
	
	public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Zombie Game";
        cfg.width = INTERNAL_WIDTH;
        cfg.height = INTERNAL_HEIGHT;
        new LwjglApplication(new GameTest(), cfg);
    }

}
