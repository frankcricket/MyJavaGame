package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.Gdx;

import it.unical.mat.igpe17.game.network.Game;


public class JumpListener implements Runnable{
	private Game game;
	private boolean isLocalPlayer;
	
	public JumpListener(Game game, boolean isLocalPlayer) {
		this.game = game;
		this.isLocalPlayer = isLocalPlayer;
	}	
	
	@Override
	public void run() {
		
		while(true){
				
			try {
				if(isLocalPlayer){
					float delta = Gdx.graphics.getDeltaTime();
					game.movePlayer(delta);
					Thread.sleep(10);
					if(game.getPlayer().getState() == PlayerState.JUMPING
							&& !(game.getPlayer().VERTICAL_JUMP)){
						Thread.sleep(5);
					}
				}
				else{
					float delta = Gdx.graphics.getDeltaTime();
					game.moveVirtualPlayer(delta);
					Thread.sleep(10);
					if(game.getVirtualPlayer().getState() == PlayerState.JUMPING
							&& !(game.getVirtualPlayer().VERTICAL_JUMP)){
						Thread.sleep(5);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	

}
