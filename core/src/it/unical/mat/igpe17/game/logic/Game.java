package it.unical.mat.igpe17.game.logic;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

import it.unical.mat.igpe17.game.constants.Asset;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;
import it.unical.mat.igpe17.game.utility.Reader;

public class Game {

	private Player player;
	private char[][] matrix;
	private int row;
	private int column;

	private int cameraStart;
	private int cameraEnd;
	private List<Ground> groundObjects;

	int point_x;
	int point_y1;
	int point_y2;
	

	// set di default
	private static String LEVEL = "levels/firstLevel.tmx";

	private boolean gameOver;

	public Game() {
		player = null;
		matrix = null;
		groundObjects = null;
	}

	public void loadLevel() {
		Reader reader = new Reader(LEVEL);
		reader.parse();
		matrix = reader.getMatrix();
		row = reader.getRow();
		column = reader.getColumn();
		groundObjects = reader.getGround();

		player = reader.getPlayer();

		cameraStart = 0;
		cameraEnd = Asset.WIDTH / Asset.TILE;

		point_x = (int) player.getPosition().x;
		point_y1 = -1; 
		point_y2 = -1;
	}

	public void movePlayer(char dir, float dt) {

		// posizione attuale del player sulla matrice
		float x_player = player.getPosition().x;
		float y_player = player.getPosition().y;

		switch (dir) {
		case 'l': {
			if ((int)y_player > cameraStart) {
				if (matrix[(int) x_player][(int) y_player - 1] == ' ') {
					if (y_player <= point_y1) {
						matrix[(int) x_player][(int) y_player + 1] = ' ';
						matrix[(int) x_player][(int) y_player] = 'p';
						point_y1 = (int) y_player - 1;
						point_y2 = (int) y_player + 1;
						print();
					} else
						player.movePlayer(new Vector2(0, -5.0f), dt);
				}
			}

			break;
		}
		case 'r': {
			if ((int) y_player < row) {
				if (matrix[(int) x_player][(int) y_player + 1] == ' ') {
					if (y_player >= point_y2) {
						matrix[(int) x_player][(int) y_player] = ' ';
						matrix[(int) x_player][(int) y_player+1] = 'p';
						point_y2 = (int) y_player + 1;
						point_y1 = (int) y_player - 1;
						
						
						print();

					} else
						player.movePlayer(new Vector2(0, +15.0f), dt);
				}
				

			}

			break;

		}
		case 's': {
			player.jump(dt);

			break;
		}
		default:
			break;
		}
	}

	public void setLevel(String level) {
		LEVEL = level;
	}

	public final List<Ground> getGround() {
		return groundObjects;
	}

	public final Player getPlayer() {
		return player;
	}

	public final void print() {
		for (int i = 0; i < matrix.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.print(" |");
			System.out.println();
		}
	}

	public final int getRow() {
		return row;
	}

	public final int getColumn() {
		return column;
	}

	public void updatePhysicCamera() {
		cameraStart++;
		cameraEnd++;
	}

	public static void main(String[] args) {

		Game g = new Game();
		g.loadLevel();
		g.print();
		/*
		 * for(int i = 0; i < 10; i++){ g.movePlayer('r', (float)1); g.print();
		 * }
		 */
	}

}
