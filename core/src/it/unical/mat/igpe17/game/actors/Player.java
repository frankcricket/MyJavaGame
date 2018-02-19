package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Player extends DynamicObject implements IPlayer {

	private PlayerState state;

	private Vector2 velocity;
	private Vector2 playerForce;
	
	/* variabile per il salto in verticale, senza allungamento */
	public boolean VERTICAL_JUMP;
	private float posX;
	private float posY;
	
	private int points;
	
	private int lives;
	
	private boolean hasGun;
	
	public int type;

	public Player(Vector2 position, Vector2 size, char direction, PlayerState state) {
		super(position, size, direction);

		this.state = state;

		velocity = new Vector2(0, 0);
		velocity.x =GameConfig.POSITIVE_JUMP_VELOCITY.x;
		velocity.y = GameConfig.POSITIVE_JUMP_VELOCITY.y;

		playerForce = new Vector2();
		playerForce.x = GameConfig.JUMP_NEG_FORCE.x;
		playerForce.y = GameConfig.JUMP_NEG_FORCE.y;

		VERTICAL_JUMP = false;
		
		posX = 0;
		posY = 0;
		
		points = 0;
		
		lives = GameConfig.NUM_LIVES;
		
		hasGun = false;
	
	}
	
	/**
	 * Gestione del salto in baso alla direzione del player
	 * 
	 * @param r il personaggio si muove a destra
	 * @param l il personaggio si muove a sinistra
	 */
	@Override
	public void jump(float dt) {

		if (getDirection() == 'r') {

			posX = getPosition().x;
			posY = getPosition().y;

			posX += velocity.x * dt;
			posY += velocity.y * dt;
			velocity.x += GameConfig.GRAVITY_JUMP.x;
			velocity.y += GameConfig.GRAVITY_JUMP.y;

			setPosition(new Vector2(posX, posY));

		} else {

			posX = getPosition().x;
			posY = getPosition().y;

			posX += velocity.x * dt;
			posY -= velocity.y * dt;
			velocity.x += GameConfig.GRAVITY_JUMP.x;
			velocity.y += GameConfig.GRAVITY_JUMP.y;

			setPosition(new Vector2(posX, posY));
		}
	}

	/*
	 * Gestione del salto lungo l'asse verticale, senza spostamento sull'asse x
	 */
	public void verticalJump(float dt) {
		Vector2 tmpPos = getPosition();
		Vector2 newPos = tmpPos.add(playerForce.x * dt, 0);
		setPosition(newPos);
		
	}

	
	/*
	 * Funzione di reset della velocità per il salto
	 */
	public void reset() {
		velocity.x = GameConfig.POSITIVE_JUMP_VELOCITY.x;
		velocity.y = GameConfig.POSITIVE_JUMP_VELOCITY.y;
	}

	
	/*
	 * Funzione di inversione della velocità per la discesa del salto
	 */
	public void swap() {
		velocity.x = GameConfig.NEGATIVE_JUMP_VELOCITY.x;
		velocity.y = GameConfig.NEGATIVE_JUMP_VELOCITY.y;
	}

	public void setForce(Vector2 force) {
		this.playerForce.x = force.x;
		this.playerForce.y = force.y;
	}

	public void score(final int _score) {
		points += _score;
		//System.out.println("Current score: " + points);
	}
	public final int getScore(){
		return points;
	}

	public final PlayerState getState() {
		return state;
	}

	public void setState(PlayerState state) {
		this.state = state;
		
	}
	
	public void decreaseLives(){
		lives --;
	}
	
	public final int getLives(){
		return lives;
	}
	
	public final boolean getGun(){
		return hasGun;
	}
	public void setGun(boolean hasGun){
		this.hasGun = hasGun;
	}
	public void setType(int type){
		this.type = type;
	}
	public final int getType(){
		return this.type;
	}

}
