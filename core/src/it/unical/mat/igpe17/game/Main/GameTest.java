package it.unical.mat.igpe17.game.Main;

import com.badlogic.gdx.Game;

import it.unical.mat.igpe17.game.GUI.Play;

public class GameTest extends Game {

	@Override
	public void create() {
		setScreen(new Play(null));
	}
	
	

}
