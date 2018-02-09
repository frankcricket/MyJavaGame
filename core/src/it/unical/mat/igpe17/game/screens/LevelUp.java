package it.unical.mat.igpe17.game.screens;

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
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.utility.LevelsHandler;

public class LevelUp implements Screen{
	
	private Stage stage;
	private Skin skin; 
	private Table table;
	
	public static boolean LEVEL_UP_INSTANCE;
	
	public static LevelUp level_instance;
	
	
	private LevelUp() {
		LEVEL_UP_INSTANCE = false;
		stage = new Stage();
	}
	
	public static LevelUp getInstance(){
		if(level_instance == null){
			level_instance = new LevelUp();
		}
		return level_instance;
	}

	@Override
	public void show() {

		Gdx.input.setInputProcessor(stage);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	

		// background image
 		Image backgroundMenu = new Image(new Texture(Asset.LEVEL_UP));


		// bottone next
		Image next_on = new Image(new Texture("asset/menu_img/next.png"));
		Image next_off = new Image(new Texture("asset/menu_img/next_1.png"));
		ImageButton next = new ImageButton(next_on.getDrawable(), next_off.getDrawable());
		next.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				String level = LevelsHandler.next();
				if(level != null){
					((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new Play(level));
				}
				//TODO GESTIRE CASO IN CUI IL LIVELLO NON CI SIA
			}

		});

		// bottone settings
		Image setting_on = new Image(new Texture("asset/menu_img/settings.png"));
		Image setting_off = new Image(new Texture("asset/menu_img/settings_1.png"));
		ImageButton setting = new ImageButton(setting_on.getDrawable(), setting_off.getDrawable());
		setting.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				LEVEL_UP_INSTANCE = true;
				((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(Settings.getSettings());
			}

		});

		
		// bottone exit

		Image exit_on = new Image(new Texture("asset/menu_img/exit.png"));
		Image exit_off = new Image(new Texture("asset/menu_img/exit_1.png"));
		ImageButton exit = new ImageButton(exit_on.getDrawable(), exit_off.getDrawable());
		exit.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}

		});

		table.setFillParent(false);
		
		int x = 317;
		int y = 123;
		
		next.setPosition(x, y);
		x += 226;
		setting.setPosition(x, y);
		x += 226;
		exit.setPosition(x, y);
		table.setBackground(backgroundMenu.getDrawable());

		table.addActor(next);
		table.addActor(setting);
		table.addActor(exit);
		stage.addActor(table);

	}

	@Override
	public void render(float delta) {
	
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	
		stage.act(delta);
		stage.draw();}

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
