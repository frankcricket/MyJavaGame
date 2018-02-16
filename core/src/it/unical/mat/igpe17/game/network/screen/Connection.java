package it.unical.mat.igpe17.game.network.screen;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.unical.mat.igpe17.game.GUI.Play;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.Audio;
import it.unical.mat.igpe17.game.network.ClientGame;
import it.unical.mat.igpe17.game.network.MultiplayerGameMain;
import it.unical.mat.igpe17.game.network.ServerHandler;
import it.unical.mat.igpe17.game.utility.LevelsHandler;

public class Connection implements Screen{
	
	private String ip;
	private int port;
	
	private SpriteBatch batch;
	private Texture background;
	private MultiplayerGameMain mGame;
	
	private boolean isServer = false;
	
	public Connection(int port) {
		this.port = port;
		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Current ip address: " + ip);
		} catch (UnknownHostException e) {
			ip = "127.0.0.1";
		}
		
		//avvio il server
		ServerHandler sh = new ServerHandler(port);
		sh.start();
		
		System.out.println("ho lanciato i thread");
		
		isServer = true;
	}
	
	public Connection(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	

	@Override
	public void show() {
		
		MultiplayerGameMain.PAUSE = true;
		
		String level = LevelsHandler.getInstance().first();			
		/*
		 * Verifica presenza livelli nella cartella
		 */
		if(level == null){
			JOptionPane.showMessageDialog(null, "La cartella di configurazione dei livelli non può essere vuota!");
			Gdx.app.exit();
			return;
		}
		if(!isServer){
			Asset.PLAYER_TYPE = 2;
		}
		
		mGame = new MultiplayerGameMain(level);
		ClientGame c = new ClientGame(mGame,ip, port);
		c.start();
		batch = new SpriteBatch();
		background = new Texture("asset/connecting.png");
	}
	
	public static int received = 1;

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(received != 0){
			batch.begin();
			
			batch.draw(background, 0, 0);
			
			batch.end();
		}
		else{
					
			Audio.BACKGROUND_MUSIC = false;
			Audio.game_menu_music.pause();
			Audio.GAME_MUSIC = true;
			Audio.reloadGameMusic();
			mGame.PAUSE = false;
			((Game) Gdx.app.getApplicationListener()).setScreen(mGame);
		}
		
		
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
