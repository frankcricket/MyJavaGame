package it.unical.mat.igpe17.game.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import it.unical.mat.igpe17.game.GUI.Play;
import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.constants.Textures;
import it.unical.mat.igpe17.game.utility.LevelsHandler;

public class HandleGameOver implements Screen {

	private Stage stage;
	private Skin skin; 
	private Table table;
	private SpriteBatch batch;
	
	
	private ArrayList<Integer> c_score;
	private ArrayList<Integer> b_score;
	private int best_score;
	private int current_score;
	
	public static boolean GAME_OVER_INSTANCE;
	
	public static HandleGameOver gameOver;
	
	public static HandleGameOver getInstance(){
		if(gameOver == null){
			gameOver = new HandleGameOver();
		}
		return gameOver;
	}
	
	
	private HandleGameOver() {
		best_score = 0;
		
		GAME_OVER_INSTANCE = false;
	}


	@Override
	public void show() {
		stage = new Stage();
		batch = new SpriteBatch();

		Gdx.input.setInputProcessor(stage);
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());	

		// background image
 		Image backgroundMenu = new Image(new Texture(Asset.GAME_OVER));
 		current_score = GameConfig.BEST_SCORE;
 		handleScore();
 		initArrays();


		// bottone restart
		Image restart_on = new Image(new Texture("asset/menu_img/restart.png"));
		Image restart_off = new Image(new Texture("asset/menu_img/restart1.png"));
		ImageButton restart = new ImageButton(restart_on.getDrawable(), restart_off.getDrawable());
		restart.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				LevelsHandler.reload();
				GAME_OVER_INSTANCE = false;
				GameConfig.NUM_LIVES = 3;
				((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(MainMenu.getMainMenu());
				/*
				 *  	OPPURE 
				 * ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new Play());
				 */
			}

		});

		// bottone settings
		Image setting_on = new Image(new Texture("asset/menu_img/settings.png"));
		Image setting_off = new Image(new Texture("asset/menu_img/settings_1.png"));
		ImageButton setting = new ImageButton(setting_on.getDrawable(), setting_off.getDrawable());
		setting.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				GAME_OVER_INSTANCE = true;
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
		
		restart.setPosition(x, y);
		x += 226;
		setting.setPosition(x, y);
		x += 226;
		exit.setPosition(x, y);
		table.setBackground(backgroundMenu.getDrawable());

		table.addActor(restart);
		table.addActor(setting);
		table.addActor(exit);
		stage.addActor(table);

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
 		drawScore();

	}
	
	protected void handleScore(){
		
		FileHandle file = Gdx.files.internal(Asset.BEST_SCORE_FILE);
		if(!file.exists()){
			throw new RuntimeException("Il file contenente il punteggio non esiste!");
		}
		String score = file.readString();
		/* Se non è presente un punteggio nel file, scrivo il punteggio corrente */
		if(score.equals("")){
			FileHandle w_file = Gdx.files.local(Asset.BEST_SCORE_FILE);
			w_file.writeString(""+current_score,false);
		}
		/*Se nel file è presente un punteggio, devo verificare che sia il migliore*/
		else{
			best_score = Integer.parseInt(score);
			if(current_score > best_score){
				FileHandle w_file = Gdx.files.local(Asset.BEST_SCORE_FILE);
				w_file.writeString(""+current_score,false);
				best_score = current_score;
			}
		}	
	}
	
	private void drawScore(){
		
		batch.begin();
		
		int pos_x =	675;
		int pos_y = 461;		
		for(int i = c_score.size()-1; i>=0; i--){
			Texture t = Textures.get("D"+c_score.get(i));
			batch.draw(t,pos_x,pos_y);
			pos_x += 20;
		}
		pos_x = 675;
		pos_y = 321;
		for(int i = b_score.size()-1; i>=0; i--){
			Texture t = Textures.get("D"+b_score.get(i));
			batch.draw(t,pos_x,pos_y);
			pos_x += 20;
		}
		
		batch.end();
	}
	
	private void initArrays(){
		//punteggio corrente
		c_score = Play.handleDigits(current_score);
		//punteggio migliore
		b_score = Play.handleDigits(best_score);
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
