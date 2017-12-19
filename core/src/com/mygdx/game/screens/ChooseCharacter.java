package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import it.unical.mat.igpe17.game.guiTest.GameTest;
import it.unical.mat.igpe17.game.guiTest.Play;
 
public class ChooseCharacter implements Screen {
	// private mainmenu

	private static ChooseCharacter chooseCharacter = null;
	private static Play play;
	
	public static ChooseCharacter getChooseCharacter(){
		if (chooseCharacter == null)
			chooseCharacter = new ChooseCharacter();
		return chooseCharacter;
	}
	
	public ChooseCharacter() {
		play = new Play();
	}

	public int Character = 0;

	private Stage stage;
	private Skin skin; // aspetto di tutto ciò che faremo
	private Table table;

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Image backgroundMenu = new Image(new Texture("asset/menu_img/backgroud_choose_character.png"));

		Image character1 = new Image(new Texture("asset/menu_img/character1.png"));
		Image character1_2 = new Image(new Texture("asset/menu_img/character1_2.png"));
		ImageButton character1B = new ImageButton(character1.getDrawable(), character1_2.getDrawable());
		character1B.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				play.player_type = 1;
				((Game) Gdx.app.getApplicationListener()).setScreen(new Loading());
			}

		});

		Image character2 = new Image(new Texture("asset/menu_img/character2.png"));
		Image character2_2 = new Image(new Texture("asset/menu_img/character2_2.png"));
		ImageButton character2B = new ImageButton(character2.getDrawable(), character2_2.getDrawable());
		character2B.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				play.player_type = 2;
				((Game) Gdx.app.getApplicationListener()).setScreen(new Loading());
			}

		});

		Image goToBack = new Image(new Texture("asset/menu_img/undo.png"));
		Image goToBack_1 = new Image(new Texture("asset/menu_img/pressed_undo.png"));
		ImageButton goToBackB = new ImageButton(goToBack.getDrawable(), goToBack_1.getDrawable());
		goToBackB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(MainMenu.getMainMenu());

			}

		});

		table.setFillParent(false);
		table.bottom();

		goToBackB.setPosition(0 + 5, 720 - 109);
		table.addActor(goToBackB);

		character1B.setPosition((380), Math.abs(530-720));
		table.addActor(character1B);
		
		character2B.setPosition(700, Math.abs(530-720));
		table.addActor(character2B);
		
		// aggiungiamo tutto

		table.setBackground(backgroundMenu.getDrawable());

		table.debug();
		stage.addActor(table);
	}
	
	public static Play getPlay(){
		return play;
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
