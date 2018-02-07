package it.unical.mat.igpe17.game.screens;

import java.util.Timer;
import java.util.TimerTask;

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
import it.unical.mat.igpe17.game.constants.MyAnimation;

public class Loading implements Screen {

	private Stage stage;
	private Skin skin = new Skin(); // aspetto di tutto cio' che faremo
	private Table table;

	private MyAnimation animations = new MyAnimation();
	private SpriteBatch batch = new SpriteBatch();
	float elapsedTime;
	public static boolean swap = false;
	private int player_type;
	
	Animation<TextureRegion> a = animations.getAnimation("loading");
	
	public Loading(int type) {
		player_type = type;
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
			 ((Game) Gdx.app.getApplicationListener()).setScreen(new Play(player_type));
			 swap = false;
		}
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();

	}

}
