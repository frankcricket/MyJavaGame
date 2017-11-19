package it.unical.mat.igpe17.game.guiTest;

import java.util.List;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import it.unical.mat.igpe17.game.constants.MyTexture;
import it.unical.mat.igpe17.game.logic.Game;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;

public class GameTest implements ApplicationListener{
	
	private Background background;	
	
	private Game game;
	private List<Ground> groundObjects;
	private Player player;
	
	private SpriteBatch batch;
	private MyTexture texture;
	
	
	public GameTest() {
		
		game = new Game();
		game.loadLevel();
		groundObjects = game.getGround();
		player = game.getPlayer();

	}


	@Override
	public void create() {		
		background = new Background();
		batch = new SpriteBatch();
		texture = new MyTexture();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void render() {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		background.update(Gdx.graphics.getDeltaTime());
		batch.begin();
		
		for(Ground g : groundObjects){
//			System.out.println(g.getPosition().x + "/" +g.getPosition().y + " " + ((7 - g.getPosition().x)) + "/" + (g.getPosition().y) );
			batch.draw(texture.getTexture(g.getType()), (g.getPosition().y) * 64, ((7 - g.getPosition().x) * 64));
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			game.movePlayer('r', 1);
		}
		else
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			game.movePlayer('l', 1);
		}
		
		batch.draw(texture.getTexture("100"), (player.getPosition().y) * 64, ((7 - player.getPosition().x) * 64));
		batch.end();
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
	public void dispose() {
		background.dispose();
		batch.dispose();

	}

}
