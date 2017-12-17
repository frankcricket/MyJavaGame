package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.Gdx;

import it.unical.mat.igpe17.game.logic.Game;

public class JumpListener implements Runnable{
	private Game game;
	
	public JumpListener(Game game) {
		this.game = game;
	}	
	
	@Override
	public void run() {
		
		while(true){
				
			try {
				float delta = Gdx.graphics.getDeltaTime();
				game.movePlayer(delta);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	

}
