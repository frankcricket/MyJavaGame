package it.unical.mat.igpe17.game.utility;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.GameConfig;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;
import it.unical.mat.igpe17.game.player.PlayerState;

public class Builder {

	protected int dimX;
	protected int dimY;
	private char[][] matrix;

	private List<Ground> groundObjects = new LinkedList<Ground>();

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

	protected void createMatrix() {
		matrix = new char[dimX][dimY];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = ' ';
			}
		}
	}

	protected void convertWorldObjects(String line, int row) {

		/*
		 * In input una linea contenente gli oggetti da visualizzare, tra cui terreno e ostacoli.
		 * Se l'elemento split[i] è un numero compreso tra 1 e 16, allora memorizzo il terreno corrispondente
		 * altrimenti memorizzo un ostacolo
		 */
		String[] split = line.split(",");
		for (int i = 0; i < split.length; i++) {
			if (!(split[i].equals("0"))) {
				if (Integer.parseInt(split[i]) >= 1 && Integer.parseInt(split[i]) <= 16) {
					Ground ground = new Ground(new Vector2(row, i),
							new Vector2(GameConfig.SIZE_GROUND_X, GameConfig.SIZE_GROUND_Y), split[i]);

					groundObjects.add(ground);
				}//TODO aggiungere gli ostacoli
			}
		}

	}

	protected void initMatrix(String line, int row) {
		String[] split = line.split(",");

		for (int i = 0; i < split.length; i++) {
			if (!(split[i].equals("0"))) {
				if (checkGround(split[i]))
					matrix[row][i] = 'g';
				else {
					matrix[row][i] = '-';
				}
			}
		}
	}

	private boolean checkGround(String tmp) {

		if (tmp.equals("1") || tmp.equals("2") || tmp.equals("3") || tmp.equals("7") || tmp.equals("11")
				|| tmp.equals("14") || tmp.equals("15") || tmp.equals("16")) {

			return true;
		}
		return false;

	}

	protected final char[][] getMatrix() {
		return matrix;
	}

	protected final List<Ground> getGround() {
		return groundObjects;
	}

	// protected final Player getPlayer() {
	// Player p = null;
	// boolean stop = false;
	// for (int i = 0; i < matrix[i].length; i++) {
	// for (int j = 0; j < matrix.length; j++) {
	// if (matrix[j][i] == 'g') {
	// p = new Player(new Vector2(j - 1, i),
	// new Vector2(GameConfig.SIZE_PLAYER_X, GameConfig.SIZE_PLAYER_Y), 'r');
	// matrix[j - 1][i] = 'p';
	// stop = true;
	// break;
	// }
	// }
	// if (stop)
	// break;
	// }
	// return p;
	// }

	protected final Player getPlayer() {
		Player p = null;

		Ground tmp_ground = new Ground(new Vector2(11, 0), null, null);

		for (Ground g : groundObjects) {
			if (g.getType().equals("1") || g.getType().equals("14"))
				if (g.getPosition().x > tmp_ground.getPosition().x && g.getPosition().y < tmp_ground.getPosition().y)
					tmp_ground = g;
		}

		p = new Player(new Vector2(tmp_ground.getPosition().x - 1, tmp_ground.getPosition().y),
				new Vector2(GameConfig.SIZE_PLAYER_X, GameConfig.SIZE_PLAYER_Y), 'r', PlayerState.IDLING);

		return p;
	}

}
