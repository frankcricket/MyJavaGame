package com.mygdx.game.screens;

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

public class ChooseCharacter implements Screen{
//	private mainmenu
	
	public ChooseCharacter(){}
	
	public int Character = 0;
	
	private Stage stage;
	private Skin skin; // aspetto di tutto ci√≤ che faremo
	private Table table;
	
	private int choice = 0; // in questa variabile ci salviamo il tipo di personaggio che verr‡ scelto dall'utente
	//sar‡ modificata nell'evento click del personaggio selezionato
	
	

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		Image backgroundMenu = new Image(new Texture("asset/menu_img/backgroud_choose_character.png"));

		
		Image character1 = new Image (new Texture("asset/menu_img/character1.png"));
		Image character1_2 = new Image (new Texture("asset/menu_img/character1_2.png"));
		ImageButton character1B = new ImageButton(character1.getDrawable(), character1_2.getDrawable());
		character1B.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				choice = 1;
			}

		});
		
		Image character2 = new Image (new Texture("asset/menu_img/character2.png"));
		Image character2_2 = new Image (new Texture("asset/menu_img/character2_2.png"));
		ImageButton character2B = new ImageButton(character2.getDrawable(), character2_2.getDrawable());
		character2B.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				choice = 2;
			}

		});
		

		
		table.add((character1B)).width(350f);
		table.add((character2B)).width(350f);
		
		
		
		
		
		
		
		
		
		//aggiungiamo tutto
		
		table.setBackground(backgroundMenu.getDrawable());	

		
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
