package it.unical.mat.igpe17.game.screens;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import it.unical.mat.igpe17.game.GUI.Play;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.Audio;
import it.unical.mat.igpe17.game.constants.MyAnimation;
import it.unical.mat.igpe17.game.utility.LevelsHandler;

public class Loading implements Screen {

	private Stage stage;
	private Skin skin = new Skin(); // aspetto di tutto cio' che faremo
	private Table table;

	private MyAnimation animations = new MyAnimation();
	private SpriteBatch batch = new SpriteBatch();
	float elapsedTime;
	public static boolean swap = false;
	private int player_type;
	
	private String loadedLevel;
	
	private LevelsHandler l_handler;
	
	Animation<TextureRegion> a = animations.getAnimation("loading");
	
	public Loading(int type,String level) {
		player_type = type;
		l_handler = LevelsHandler.getInstance();
		loadedLevel = level;
	}

	@Override
	public void show() {

		stage = new Stage();

		Gdx.input.setInputProcessor(stage);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			// @Override
			public void run() {
				swap = true;
				

			}
		}, 3000);

		stage.addActor(table);

	}

	@Override
	public void render(float delta) {
		// puisco tutto lo schermo
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		elapsedTime += delta;
		// Table.drawDebug(stage);

		stage.act(delta); // aggiorna qualsiasi cosa in esso

		stage.draw(); // dove tutto diventa visibile

		batch.begin();
		batch.draw(a.getKeyFrame(elapsedTime, true), 512, 50);
		batch.end();
		
		if (swap){
			Asset.PLAYER_TYPE = player_type;
			String level = l_handler.first();			
			/*
			 * Verifica presenza livelli nella cartella
			 */
			if(level == null){
				JOptionPane.showMessageDialog(null, "La cartella di configurazione dei livelli non può essere vuota!");
				Gdx.app.exit();
				return;
			}
			
			Audio.BACKGROUND_MUSIC = false;
			Audio.game_menu_music.pause();
			Audio.GAME_MUSIC = true;
			Audio.reloadGameMusic();
			
			Play.PLAY_OBJECT = null;
			if(!(loadedLevel == null)){
				level = null;
				((Game) Gdx.app.getApplicationListener()).setScreen(Play.getPlay(loadedLevel));
			}
			((Game) Gdx.app.getApplicationListener()).setScreen(Play.getPlay(level));
			 swap = false;
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
	public void dispose() {
		stage.dispose();
		skin.dispose();

	}

}
