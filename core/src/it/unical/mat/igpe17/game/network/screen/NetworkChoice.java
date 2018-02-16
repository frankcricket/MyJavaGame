package it.unical.mat.igpe17.game.network.screen;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import it.unical.mat.igpe17.game.screens.MainMenu;


public class NetworkChoice implements Screen{
	
	private Stage stage;
	private Skin skin;
	private TextField text_ip = null;
	private TextField text_port = null;
	
	private boolean clicked = false;


	@Override
	public void show() {
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		skin = new Skin();

		// img background
		Image menu = new Image(new Texture("asset/menu_img/network_menu.png"));
		ImageButton backgroundMenu = new ImageButton(menu.getDrawable());

		// bottone server
		Image server_0 = new Image(new Texture("asset/menu_img/server_0.png"));
		Image server_1 = new Image(new Texture("asset/menu_img/server_1.png"));
		ImageButton server = new ImageButton(server_0.getDrawable(), server_1.getDrawable());
		server.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if(!clicked){
					serverEvent();
					clicked = true;
				}
			}

		});

		// bottone login
		Image login_0 = new Image(new Texture("asset/menu_img/login_0.png"));
		Image login_1 = new Image(new Texture("asset/menu_img/login_1.png"));
		ImageButton login = new ImageButton(login_0.getDrawable(), login_1.getDrawable());
		login.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				if(!clicked){
					loginEvent();
					clicked = true;
				}
			}

		});
		
		// bottone next
		Image next_on = new Image(new Texture("asset/menu_img/next.png"));
		Image next_off = new Image(new Texture("asset/menu_img/next_1.png"));
		ImageButton next = new ImageButton(next_on.getDrawable(), next_off.getDrawable());
		next.addListener(new ClickListener() {
			public void clicked(InputEvent event, float x, float y) {
				checkFieldValues();
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

		
		//tasto back
		goToBackB.setPosition(5, 621);
		
		int pos_x = 415; 
		int pos_y = 435;
		server.setPosition(pos_x, pos_y);
		pos_x += 244;
		login.setPosition(pos_x, pos_y);
		
		next.setPosition(769, 123);
		
		stage.addActor(backgroundMenu);
		stage.addActor(goToBackB);
		stage.addActor(server);
		stage.addActor(login);
		stage.addActor(next);

	}
	
	private void serverEvent(){
		
		Image image = new Image(new Texture("asset/menu_img/port.png"));
		ImageButton port = new ImageButton(image.getDrawable());
		
		text_port = new TextField("", new Skin(Gdx.files.internal("asset/ui/shade/skin/terra-mother-ui.json")));
		
		port.setPosition(423, 334);
		
		text_port.setPosition(571, 333);
		text_port.setSize(100, 50);
		text_port.setMaxLength(4);
		
		stage.addActor(port);
		stage.addActor(text_port);
		
	}
	private void loginEvent(){
		
		Image image = new Image(new Texture("asset/menu_img/ip_port.png"));
		ImageButton ip_port = new ImageButton(image.getDrawable());
		
		text_ip = new TextField("", new Skin(Gdx.files.internal("asset/ui/shade/skin/terra-mother-ui.json")));
		text_port = new TextField("", new Skin(Gdx.files.internal("asset/ui/shade/skin/terra-mother-ui.json")));
		
		ip_port.setPosition(423, 288);
		
		text_ip.setPosition(571, 332);
		text_port.setPosition(571, 287);
		text_ip.setSize(200, 50);
		text_port.setSize(100, 50);
		text_ip.setMaxLength(18);
		text_port.setMaxLength(4);
		
		stage.addActor(ip_port);
		stage.addActor(text_ip);
		stage.addActor(text_port);
		
	}
	
	private void checkFieldValues(){
		if(text_ip == null){
			if(text_port != null){
				String port = text_port.getText();
				if(!(port.equals(""))){
					//try connection
					int i_port = Integer.parseInt(port);
					((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new Connection(i_port));
				}
				
			}
		}else{
			if(text_port != null){
				String ip = text_ip.getText();
				String port = text_port.getText();
				if(!(port.equals("")) && !(ip.equals(""))){
					//try connection
					int i_port = Integer.parseInt(port);
					((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new Connection(ip,i_port));
				}
				
			}
		}

	}
	

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
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
