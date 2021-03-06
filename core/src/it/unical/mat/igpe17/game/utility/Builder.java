package it.unical.mat.igpe17.game.utility;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.EnemyState;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.actors.PlayerState;
import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.objects.Obstacle;
import it.unical.mat.igpe17.game.objects.StaticObject;

public class Builder {

	protected int dimX;
	protected int dimY;

	private List<StaticObject> groundObjects = new LinkedList<StaticObject>();
	private List<StaticObject> obstacleObjects = new LinkedList<StaticObject>();
	private List<StaticObject> coins = new LinkedList<StaticObject>();
	private List<StaticObject> utility = new LinkedList<StaticObject>();


	private List<StaticObject> enemiesObjects = new LinkedList<StaticObject>();
	

	protected void convertDimension(String dim) {
		dim = dim.substring(1);
		String[] split = dim.split(" ");

		String width = split[4];
		String height = split[5];

		width = width.substring(7, width.length() - 1);
		height = height.substring(8, height.length() - 2);

		dimY = Integer.parseInt(width);
		dimX = Integer.parseInt(height);

	}

	protected void convertWorldObjects(String line, int row) {

		/*
		 * In input una linea contenente gli oggetti da visualizzare, tra cui terreno e ostacoli.
		 * Se l'elemento split[i] � un numero compreso tra 1 e 16, allora memorizzo il terreno corrispondente
		 * altrimenti memorizzo un ostacolo
		 */
		String[] split = line.split(",");
		for (int i = 0; i < split.length; i++) {
			if (!(split[i].equals("0"))) {
				if (Integer.parseInt(split[i]) >= 1 && Integer.parseInt(split[i]) <= 16) {
					Ground ground = new Ground(new Vector2(row, i),
												new Vector2(GameConfig.SIZE_GROUND_X, GameConfig.SIZE_GROUND_Y),
												split[i]);

					groundObjects.add(ground);
				} else if(Integer.parseInt(split[i]) >= 17 && Integer.parseInt(split[i]) <= 30) {
					Obstacle obs = new Obstacle(new Vector2(row,i),  new Vector2(GameConfig.SIZE_OBSTALCE_X, GameConfig.SIZE_OBSTALCE_Y), split[i]);
					
					obstacleObjects.add(obs);
				}else if(Integer.parseInt(split[i]) >= 31 && Integer.parseInt(split[i]) <= 33){
					Enemy enemy = new Enemy(new Vector2(row, i),
							new Vector2(GameConfig.SIZE_ENEMY_X, GameConfig.SIZE_ENEMY_Y),
							'l',
							split[i],
							GameConfig.LIFE_STATUS_ENEMY,
							EnemyState.RUNNING);
					
					enemiesObjects.add(enemy);
				} else if(Integer.parseInt(split[i]) == 50){
					Obstacle obs = new Obstacle(new Vector2(row,i),  new Vector2(GameConfig.SIZE_OBSTALCE_X, GameConfig.SIZE_OBSTALCE_Y), split[i]);
					coins.add(obs);
				}else if(Integer.parseInt(split[i]) == 70
						|| Integer.parseInt(split[i]) == 71){
					Obstacle obs = new Obstacle(new Vector2(row,i),  new Vector2(GameConfig.SIZE_OBSTALCE_X, GameConfig.SIZE_OBSTALCE_Y), split[i]);
					utility.add(obs); 
				}
				
			}
		}

	}
	
	protected final List<StaticObject> getGround() {
		return groundObjects;
	}
	
	protected final List<StaticObject> getObstacle() {
		return obstacleObjects;
	}
	protected final List<StaticObject> getCoins() {
		return coins;
	}
	protected final List<StaticObject> getUtility() {
		return utility;
	}
	
	public List<StaticObject> getEnemiesObjects() {
		return enemiesObjects;
	}

	protected final Player getPlayer() {
		Player p = null;

		Ground tmp_ground = new Ground(new Vector2(5, 100), null, null);

		for (StaticObject obj : groundObjects) {
			Ground g = (Ground)obj;
			if (checkType(g.getType()))
				if (g.getPosition().x > tmp_ground.getPosition().x && g.getPosition().y < tmp_ground.getPosition().y)
					tmp_ground = g;
		}

		p = new Player(new Vector2(tmp_ground.getPosition().x - 1, (tmp_ground.getPosition().y + 0.15f)),
				new Vector2(GameConfig.SIZE_PLAYER_X, GameConfig.SIZE_PLAYER_Y), 'r', PlayerState.IDLING);

		return p;
	}
	
	private final boolean checkType(String type) {
		return (type.equals("1") || type.equals("2") || type.equals("3") || type.equals("7") || type.equals("11")
				|| type.equals("14") || type.equals("15") || type.equals("16"));
	}

}
