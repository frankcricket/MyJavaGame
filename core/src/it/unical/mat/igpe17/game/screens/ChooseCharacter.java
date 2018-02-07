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

import it.unical.mat.igpe17.game.GUI.Play;
 
public class ChooseCharacter implements Screen {

	private static ChooseCharacter chooseCharacter = null;
	
	public static ChooseCharacter getChooseCharacter(){
		if (chooseCharacter == null){
			chooseCharacter = new ChooseCharacter();
		}
		return chooseCharacter;
	}

	public int Character = 0;

	private Stage stage;
	private Skin skin; // aspetto di tutto cio' che faremo
	private Table table;

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Image backgroundMenu = new Image(new Texture("asset/menu_img/characters_menu.png"));

		Image character1 = new Image(new Texture("asset/menu_img/character1.png"));
		Image character1_2 = new Image(new Texture("asset/menu_img/character1_2.png"));
		ImageButton character1B = new ImageButton(character1.getDrawable(), character1_2.getDrawable());
		character1B.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Loading(1));
			}

		});

		Image character2 = new Image(new Texture("asset/menu_img/character2.png"));
		Image character2_2 = new Image(new Texture("asset/menu_img/character2_2.png"));
		ImageButton character2B = new ImageButton(character2.getDrawable(), character2_2.getDrawable());
		character2B.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new Loading(2));
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

		goToBackB.setPosition(5, 621);
		table.addActor(goToBackB);

		int x = 380;
		int y = 190;
		character1B.setPosition(x,y);
		x += 320;
		character2B.setPosition(x,y);
		
		table.addActor(character1B);
		table.addActor(character2B);
		
		// aggiungiamo tutto

		table.setBackground(backgroundMenu.getDrawable());

		table.debug();
		stage.addActor(table);
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
