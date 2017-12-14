package it.unical.mat.igpe17.game.actors;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.Interface.ICollidable;
import it.unical.mat.igpe17.game.Interface.IPlayer;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.DynamicObject;

public class Player extends DynamicObject implements IPlayer {

	private PlayerState state;

	private Vector2 velocity;
	private Vector2 playerForce;
	
	/* variabile per il salto in verticale, senza allungamento */
	public static boolean VERTICAL_JUMP;
	private float posX;
	private float posY;
	
	private int points;

	public Player(Vector2 position, Vector2 size, char direction, PlayerState state) {
		super(position, size, direction);

		this.state = state;

		velocity = new Vector2(0, 0);
		velocity.x = (-1) * GameConfig.PLAYER_JUMP_POS_VELOCITY.x;
		velocity.y = GameConfig.PLAYER_JUMP_POS_VELOCITY.y;

		playerForce = new Vector2();
		playerForce.x = GameConfig.JUMP_NEG_FORCE.x;
		playerForce.y = GameConfig.JUMP_NEG_FORCE.y;

		VERTICAL_JUMP = false;
		
		posX = 0;
		posY = 0;
		
		points = 0;
	
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
			velocity.x += GameConfig.GRAVITY.x;
			velocity.y += GameConfig.GRAVITY.x;

			setPosition(new Vector2(posX, posY));

		} else {

			posX = getPosition().x;
			posY = getPosition().y;

			posX += velocity.x * dt;
			posY -= velocity.y * dt;
			velocity.x += GameConfig.GRAVITY.x;
			velocity.y += GameConfig.GRAVITY.x;

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
		velocity.x = (-1) * GameConfig.PLAYER_JUMP_POS_VELOCITY.x;
		velocity.y = GameConfig.PLAYER_JUMP_POS_VELOCITY.y;
	}

	
	/*
	 * Funzione di inversione della velocità per la discesa del salto
	 */
	public void swap() {
		velocity.x = GameConfig.PLAYER_JUMP_POS_VELOCITY.x;
		velocity.y = GameConfig.PLAYER_JUMP_POS_VELOCITY.y;
	}

	@Override
	public boolean collide(ICollidable object) {
		if (object instanceof DynamicObject)
			return this.collide(((DynamicObject) object));
		return false;
	}

	

	public void setForce(Vector2 force) {
		this.playerForce.x = force.x;
		this.playerForce.y = force.y;
	}

	/**
	 * @return the points
	 */
	public final int getPoints() {
		return points;
	}
	public void score(final int _score) {
		points += _score;
	}

	public final PlayerState getState() {
		return state;
	}

	public void setState(PlayerState state) {
		this.state = state;
		
	}

}
