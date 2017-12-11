package it.unical.mat.igpe17.game.player;

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
			if(game.getPlayer().getState() == PlayerState.JUMPING){
				try {
					float delta = Gdx.graphics.getDeltaTime();
					game.makePlayerJump(delta);
					Thread.sleep(27);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else {
				break;
			}
		}
		
	}

}
