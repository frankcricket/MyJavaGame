package it.unical.mat.igpe17.game.Main;

import com.badlogic.gdx.Game;

import it.unical.mat.igpe17.game.GUI.Play;
import it.unical.mat.igpe17.game.screens.HandleGameOver;
import it.unical.mat.igpe17.game.screens.LevelUp;

public class GameTest extends Game {

	@Override
	public void create() {
		setScreen(Play.getPlay(null));
		//setScreen(HandleGameOver.getInstance());
		//setScreen(LevelUp.getInstance());
		//setScreen(LevelsHandler.getInstance());
	}
	
	

}
