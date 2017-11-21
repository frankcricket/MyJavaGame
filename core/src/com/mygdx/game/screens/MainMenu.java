package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import it.unical.mat.igpe17.game.editor.MyFrame;

public class MainMenu implements Screen {

	// Element that we need for our menu

	private Stage stage;
	private Skin skin; // aspetto di tutto ciò che faremo
	private Table table;
	
	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		// creazione font
		// white = new BitmapFont(Gdx.files.internal("asset/font/white.fnt"),
		// false);

		// atlas = new TextureAtlas("asset/ui/atlas.pack");
		// skin = new Skin(atlas);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// creazione bottoni
		/*
		 * TextButtonStyle textButtonStyle = new TextButtonStyle();
		 * textButtonStyle.up = skin.getDrawable("button.up"); //pulsante su
		 * textButtonStyle.down = skin.getDrawable("button.down"); //pulsante
		 * giu textButtonStyle.pressedOffsetX = 1; //muoviamo sull'asse delle x
		 * di 1 textButtonStyle.pressedOffsetY = -1; // muoviamo sull'asse delle
		 * y di meno 1 textButtonStyle.font = white; textButtonStyle.fontColor =
		 * Color.BLACK;
		 * 
		 * singlePlayer = new TextButton("SINGLE PLAYER", textButtonStyle);
		 * singlePlayer.addListener(new ClickListener(){
		 * 
		 * @Override public void clicked(InputEvent event, float x, float y) {
		 * 
		 * // ((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
		 * 
		 * new MyFrame("Editor");
		 * 
		 * 
		 * }
		 * 
		 * });
		 * 
		 * 
		 * 
		 * 
		 * //button play
		 * 
		 * Image bagImage = new Image(new
		 * Texture("asset/buttons/menuButtons/play.png")); Image bagImage2 = new
		 * Image(new Texture("asset/buttons/menuButtons/play_pressed.png"));
		 * ImageButton bagButton = new ImageButton(bagImage.getDrawable(),
		 * bagImage2.getDrawable()); bagButton.setSize(125, 125);
		 * bagButton.addListener(new ClickListener() { public void
		 * clicked(InputEvent event, float x, float y) {
		 * 
		 * }
		 * 
		 * });
		 * 
		 * 
		 * 
		 * 
		 * exit = new TextButton("EXIT", textButtonStyle ); exit.addListener(new
		 * ClickListener(){
		 * 
		 * @Override public void clicked(InputEvent event, float x, float y) {
		 * // se clicchiamo sul pulsante exit Gdx.app.exit(); // esce
		 * dall'applicazione
		 * 
		 * 
		 * } }); exit.pad(20);
		 * 
		 * //creazine heading heading = new Label(ZombieGame.NAME, new
		 * LabelStyle(white, Color.WHITE));
		 * 
		 * heading.setFontScale(2);
		 * 
		 * 
		 * 
		 * 
		 * 
		 * //mettiamo tutto insieme table.add(heading); table.row();
		 * //aggiungiamo una riga
		 * 
		 * table.getCell(heading).space(50); //aggiungere spazio tra un elemento
		 * ed un altro (in px)
		 * 
		 * table.add(singlePlayer); table.row(); //aggiungiamo una riga
		 * 
		 * // table.getCell(heading).space(50); //aggiungere spazio tra un
		 * elemento ed un altro (in px) //table.add(bagButton); table.add(exit);
		 * // aggiungiamo il bottone alla table table.debug();
		 * stage.addActor(table); // aggiungiamo la table allo stage
		 */

		// img background
		Image backgroundMenu = new Image(new Texture("asset/menu_img/background_2.png"));
		
		// bottone  single player
		Image singlePlayer = new Image(new Texture("asset/menu_img/single_player.png"));
		Image singlePlayer_2 = new Image(new Texture("asset/menu_img/single_player_2.png"));
		ImageButton singlePlayerB = new ImageButton(singlePlayer.getDrawable(), singlePlayer_2.getDrawable());
		singlePlayerB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new ChooseCharacter());
			}

		});
		
		
		// bottone  multiplayer
		Image multiplayer = new Image(new Texture("asset/menu_img/multiplayer.png"));
		Image multiplayer_2 = new Image(new Texture("asset/menu_img/multiplayer_2.png"));
		ImageButton multiplayerB = new ImageButton(multiplayer.getDrawable(), multiplayer_2.getDrawable());
		multiplayerB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {

			}

		});
		
		
		//bottone editor
		
		Image editor = new Image(new Texture("asset/menu_img/editor.png"));
		Image editor_2 = new Image(new Texture("asset/menu_img/editor_2.png"));
		ImageButton editorB = new ImageButton(editor.getDrawable(), editor_2.getDrawable());
		editorB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				new MyFrame("Editor - Zombie Shooter");
			}

		});
		
		
		//bottone settings
		
		Image settings = new Image(new Texture("asset/menu_img/settings.png"));
		Image settings_2 = new Image(new Texture("asset/menu_img/settings_2.png"));
		ImageButton settingsB = new ImageButton(settings.getDrawable(), settings_2.getDrawable());
		settingsB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
			}

		});
		
		
		
		//bottone exit
		
		Image exit = new Image(new Texture("asset/menu_img/exit.png"));
		Image exit_2 = new Image(new Texture("asset/menu_img/exit_2.png"));
		ImageButton exitB = new ImageButton(exit.getDrawable(), exit_2.getDrawable());
		exitB.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}

		});
		
		
		
		
		
		//aggiungiamo tutto
		table.setBackground(backgroundMenu.getDrawable());	
		
		
		table.add((singlePlayerB)).height(80f).row();
		
		table.add((multiplayerB)).height(80f).row();	
		
		table.add((editorB)).height(80f).row();

		table.add((settingsB)).height(80f).row();

		table.add((exitB)).height(80f).row();

		

		
		
		//table.add(singlePlayerB).size(200, 200);
		
		//table.row();

//		table.add(multiplayerB).size(200, 200);
//		
//		table.row();
//		
//		table.add(editorB).size(200, 200);

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
		// stage.getViewport().update(screenWidth, screenHeight, centerCamera);

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
