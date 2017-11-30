package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Settings implements Screen {

	private static Settings settings = null;

	public static Settings getSettings() {
		if (settings == null)
			settings = new Settings();
		return settings;
	}

	public Settings() {
	}

	private Stage stage;
	private Skin skin; // aspetto di tutto ciò che faremo
	private Table table;

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Image goToBack = new Image(new Texture("asset/menu_img/undo.png"));
		Image goToBack_1 = new Image(new Texture("asset/menu_img/pressed_undo.png"));
		ImageButton goToBackB = new ImageButton(goToBack.getDrawable(), goToBack_1.getDrawable());
		goToBackB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(MainMenu.getMainMenu());

			}

		});

		
		// image music
		
		final Image MusicButtonOff = new Image(new Texture("asset/menu_img/off.png"));

		final Image MusicButtonOn = new Image(new Texture("asset/menu_img/on.png"));
		
		MusicButtonOn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MusicButtonOff.setVisible(true);
				MusicButtonOn.setVisible(false);
			}
		});

		MusicButtonOff.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				MusicButtonOn.setVisible(true);
				MusicButtonOff.setVisible(false);
			}

		});

		MusicButtonOff.setVisible(false);
		
		
		//image sound 
		
		final Image SoundOff = new Image(new Texture("asset/menu_img/off.png"));

		final Image SoundOn = new Image(new Texture("asset/menu_img/on.png"));
		
		SoundOn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundOff.setVisible(true);
				SoundOn.setVisible(false);
			}
		});

		SoundOff.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				SoundOn.setVisible(true);
				SoundOff.setVisible(false);
			}

		});

		SoundOff.setVisible(false);
		
		//image vibration
		
		final Image VibrationOff = new Image(new Texture("asset/menu_img/off.png"));

		final Image VibrationOn = new Image(new Texture("asset/menu_img/on.png"));
		
		VibrationOn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				VibrationOff.setVisible(true);
				VibrationOn.setVisible(false);
			}
		});

		VibrationOff.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				VibrationOn.setVisible(true);
				VibrationOff.setVisible(false);
			}

		});

		VibrationOff.setVisible(false);
		
		
		
		
		
		
		//background
		Image background = new Image(new Texture("asset/menu_img/background_settings.png"));

		table.setBackground(background.getDrawable());

		table.setFillParent(false);
		table.bottom();

		MusicButtonOff.setPosition(740, 430);
		MusicButtonOn.setPosition(740, 430);
		table.addActor(MusicButtonOff);
		table.addActor(MusicButtonOn);
		
		SoundOff.setPosition(740, 349);
		SoundOn.setPosition(740, 349);
		table.addActor(SoundOff);
		table.addActor(SoundOn);
		
		VibrationOff.setPosition(740, 265);
		VibrationOn.setPosition(740, 265);
		table.addActor(VibrationOff);
		table.addActor(VibrationOn);
		
		
		

		goToBackB.setPosition(0 + 5, 720 - 109);
		table.addActor(goToBackB);

		table.debug();
		stage.addActor(table);
	}

	@Override
	public void render(float delta) {
		// puisco tutto lo schermo
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Table.drawDebug(stage);

		stage.act(delta); // aggiorna qualsiasi cosa in esso

		stage.draw(); // dove tutto diventa visibile

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
