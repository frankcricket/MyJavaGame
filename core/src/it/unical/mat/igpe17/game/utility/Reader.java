package it.unical.mat.igpe17.game.utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import it.unical.mat.igpe17.game.actors.Enemy;
import it.unical.mat.igpe17.game.actors.Player;
import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.objects.Obstacle;

public class Reader {

	private String path;
	private Builder builder;

	public Reader(String path) {
		this.path = path;
	}

	public void parse() {
		try {

			FileReader fr = new FileReader(path);
			builder = new Builder();
			Scanner input = new Scanner(fr);
			try {
				/*
				 * Parsing della dimensione della matrice
				 */
				while (input.hasNextLine()) {
					String tmp = input.nextLine();
					if (tmp.startsWith(" <layer")) {
						builder.convertDimension(tmp);
						tmp = input.nextLine();
						break;
					}
				}
				/*
				 * Parsing delle righe e colonne
				 */
				int count = 0;
				while(input.hasNextLine()){
					String tmp = input.nextLine();
					if(tmp.equals("</data>"))
						break;
					//builder.initMatrix(tmp,count);
					builder.convertWorldObjects(tmp,count);
					count++;
				}

				input.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}// end of function

	public void setPath(String path) {
		this.path = path;
	}

	public final List<Ground> getGround() {
		return builder.getGround();
	}
	
	public final List<Obstacle> getObstacle() {
		return builder.getObstacle();
	}
	public final List<Enemy> getEnemy() {
		return builder.getEnemiesObjects();
	}

	public final Player getPlayer() {
		return builder.getPlayer();
	}
	public final int getRow(){
		return builder.dimY;
	}
	public final int getColumn(){
		return builder.dimX;
	}

}
