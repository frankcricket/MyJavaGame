package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.ZombieGame;

import it.unical.mat.igpe17.game.editor.MyFrame;

public class MainMenu implements Screen{
	
	//Element that we need for our menu
		
	private Stage stage;
	private Skin skin; //aspetto di tutto ciò che faremo
	private Table table;
	private TextureAtlas atlas;
	private BitmapFont white;
	private TextButton singlePlayer, multiplayer, editorLivelli, impostazioni, exit;
	private Label heading;
	
	
	
	
	
	@Override
	public void show() {
		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		
		//creazione font 
		white = new BitmapFont(Gdx.files.internal("asset/font/white.fnt"), false);
		
		atlas = new TextureAtlas("asset/ui/atlas.pack");
		skin = new Skin(atlas);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//creazione bottoni
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.getDrawable("button.up"); //pulsante su
		textButtonStyle.down = skin.getDrawable("button.down");  //pulsante giu
		textButtonStyle.pressedOffsetX = 1; //muoviamo sull'asse delle x di 1
		textButtonStyle.pressedOffsetY = -1; // muoviamo sull'asse delle y di meno 1
		textButtonStyle.font = white;
		textButtonStyle.fontColor = Color.BLACK;
		
		singlePlayer = new TextButton("SINGLE PLAYER", textButtonStyle);
		singlePlayer.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
//				((Game) Gdx.app.getApplicationListener()).setScreen(new Levels());
				
				new MyFrame("Editor");
				
				
			}
			
		});
		
		
		exit = new TextButton("EXIT", textButtonStyle );
		exit.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) { // se clicchiamo sul pulsante exit
				Gdx.app.exit(); // esce dall'applicazione
				
				
			}
		});
		exit.pad(20);
		
		//creazine heading
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		heading = new Label(ZombieGame.NAME, headingStyle);
		
		heading.setFontScale(2);
		
		
		
		
		
		//mettiamo tutto insieme
		table.add(heading);
		table.row();  //aggiungiamo una riga
		
		table.getCell(heading).space(50); //aggiungere spazio tra un elemento ed un altro (in px)

		table.add(singlePlayer);
		table.row();  //aggiungiamo una riga

	//	table.getCell(heading).space(50); //aggiungere spazio tra un elemento ed un altro (in px)

		table.add(exit); // aggiungiamo il bottone alla table
		table.debug(); 
		stage.addActor(table); // aggiungiamo la table allo stage
		
	}

	@Override
	public void render(float delta) {
		
		//puisco tutto lo schermo
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Table.drawDebug(stage);
		
		
		stage.act(delta); //aggiorna qualsiasi cosa in esso
		
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
		atlas.dispose();
		skin.dispose();
		white.dispose();
		
		
		
	}

}
