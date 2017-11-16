package it.unical.mat.igpe17.game.utility;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import it.unical.mat.igpe17.game.objects.Ground;
import it.unical.mat.igpe17.game.player.Player;

public class Reader {
	
	private String path;
	private Builder builder;
	
	public Reader(String path) {
		this.path = path;
	}
	
	public void parse(){	
		try {
			
			FileReader fr = new FileReader(path);
			builder = new Builder();
			try{
				Scanner input = new Scanner(fr);
				builder.convertDimension(input.nextLine());
				builder.createMatrix();
				while(input.hasNextLine())
				{
					String line = input.nextLine();	
					if(!line.equals("/")){
						builder.convertGround(line);
					}
					else
						break;
				}
				
				input.close();
				
			}catch (Exception e){
				e.printStackTrace();
			}

			fr.close();

		}catch (IOException e){
			e.printStackTrace();
		}
	}// end of function
	
	public void setPath(String path){
		this.path = path;
	}
	
	
	public final char[][] getMatrix(){
		return builder.getMatrix();
	}
	
	public final List<Ground> getGround(){
		return builder.getGround();
	}
	
	public final Player getPlayer(){
		return builder.getPlayer();
	}

}
