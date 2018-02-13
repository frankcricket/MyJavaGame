package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Enemy extends DynamicObject {
	
	private EnemyState state;

	//mosse che fa automaticamente 
	private int moves = GameConfig.SIZE_MOVE_ENEMY;		
	private boolean justOnce = true;
	private float  startingPos;	
	private boolean selectedYet = false;	
	//stringa che per il tipo di nemico che abbiamo letto dal livello
	private String type;	
	private boolean isAlive = true; // il nostro personaggio è vivo o morto?
	
	private float life_status;

	public Enemy(Vector2 position, Vector2 size, char direction, String type,float life_status,EnemyState state) {
		super(position, size, direction);
		this.type = type;
		this.state = state;
		this.life_status = life_status;
	}
	
	public void setState(EnemyState state){
		this.state = state;
	}
	
	public final EnemyState getState(){
		return state;
	}
	
	public boolean getJustOnce() {
		return justOnce;
	}

	public void setJustOnce(boolean justOnce) {
		this.justOnce = justOnce;
	}

	public float getStartingPos() {
		return startingPos;
	}

	public void setStartingPos(float startingPos) {
		this.startingPos = startingPos;
	}

	public boolean getSelectedYet() {
		return selectedYet;
	}

	public void setSelectedYet(boolean selectedYet) {
		this.selectedYet = selectedYet;
	}
	
	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public final float getLifeStatus() {
		return life_status;
	}
	
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param life_bar 
	 * 				quantita' di vita da sottrarre a quella attuale
	 */
	public void decreaseLifeStatus(float life_bar) {
		this.life_status -= life_bar;
	}
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	

}
