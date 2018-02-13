package it.unical.mat.igpe17.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import it.unical.mat.igpe17.game.editor.MyFrame;
import it.unical.mat.igpe17.game.editor.MyMenu;

public class MainMenu implements Screen {

	// Element that we need for our menu

	private static MainMenu mainMenu = null;
	public static String LEVEL = null;
	private Stage stage;
	private Skin skin; // aspetto di tutto cio' che faremo
	private Table table;
	
	public static MainMenu getMainMenu(){
		if (mainMenu == null)
			mainMenu = new MainMenu();
		return mainMenu;
	}
	
	

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// img background
		Image backgroundMenu = new Image(new Texture("asset/menu_img/main_menu.png"));

		// bottone single player
		Image singlePlayer = new Image(new Texture("asset/menu_img/single_player.png"));
		Image singlePlayer_2 = new Image(new Texture("asset/menu_img/single_player_1.png"));
		ImageButton singlePlayerB = new ImageButton(singlePlayer.getDrawable(), singlePlayer_2.getDrawable());
		singlePlayerB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(ChooseCharacter.getChooseCharacter(LEVEL));
			}

		});

		// bottone multiplayer
		Image multiplayer = new Image(new Texture("asset/menu_img/multiplayer.png"));
		Image multiplayer_2 = new Image(new Texture("asset/menu_img/multiplayer_1.png"));
		ImageButton multiplayerB = new ImageButton(multiplayer.getDrawable(), multiplayer_2.getDrawable());
		multiplayerB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {

			}

		});

		// bottone editor

		Image editor = new Image(new Texture("asset/menu_img/editor.png"));
		Image editor_2 = new Image(new Texture("asset/menu_img/editor_1.png"));
		ImageButton editorB = new ImageButton(editor.getDrawable(), editor_2.getDrawable());
		editorB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				new MyFrame("Editor - Zombies Shooter");
			}

		});

		// bottone settings

		Image settings = new Image(new Texture("asset/menu_img/settings.png"));
		Image settings_2 = new Image(new Texture("asset/menu_img/settings_1.png"));
		ImageButton settingsB = new ImageButton(settings.getDrawable(), settings_2.getDrawable());
		settingsB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(Settings.getSettings());
			}

		});

		// bottone exit

		Image exit = new Image(new Texture("asset/menu_img/exit.png"));
		Image exit_2 = new Image(new Texture("asset/menu_img/exit_1.png"));
		ImageButton exitB = new ImageButton(exit.getDrawable(), exit_2.getDrawable());
		exitB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}

		});

		table.setFillParent(false);
		

		// aggiungiamo tutto
		table.setBackground(backgroundMenu.getDrawable());

		/*
		table.add((singlePlayerB)).height(80f).row();
		// table.addActor(singlePlayerB);

		table.add((multiplayerB)).height(80f).row();

		table.add((editorB)).height(80f).row();

		table.add((settingsB)).height(80f).row();

		table.add((exitB)).height(80f);
		
		*/
		
		int pos_x = 415; 
		int pos_y = 435;
		singlePlayerB.setPosition(pos_x, pos_y);
		pos_x += 244;
		multiplayerB.setPosition(pos_x, pos_y);
		pos_x -= 120;
		pos_y -= 103;
		editorB.setPosition(pos_x, pos_y);
		pos_x -= 124;
		pos_y -= 102;
		settingsB.setPosition(pos_x, pos_y);
		pos_x += 244;
		exitB.setPosition(pos_x, pos_y);
		
		table.addActor(singlePlayerB);
		table.addActor(multiplayerB);
		table.addActor(editorB);
		table.addActor(settingsB);
		table.addActor(exitB);
		
		stage.addActor(table);

	}
	
	public static final String getLevel(){
		return LEVEL;
	}

	@Override
	public void render(float delta) {

		// puisco tutto lo schermo
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta); // aggiorna qualsiasi cosa in esso

		stage.draw(); // dove tutto diventa visibile

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
