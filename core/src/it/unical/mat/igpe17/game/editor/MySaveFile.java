package it.unical.mat.igpe17.game.editor;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class MySaveFile {
	
	private BufferedWriter bw;
	private FileWriter fw;
	private JFileChooser fc = new JFileChooser();
	protected static String IMAGE_NAME = Asset.BACKGROUND;

	public void save(){
		try{
			fc.setDialogTitle("Save in...");
			
			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				fw = new FileWriter(fc.getSelectedFile());
				bw = new BufferedWriter(fw);
				bw.write(""+MyPanel.dimX+" "+(Asset.WIDTH/Asset.TILE));
				bw.newLine();
				for (int i = 0; i < MyPanel.points.size(); i++){
					bw.write(MyPanel.points.get(i).getName()+MyPanel.toString(MyPanel.points.get(i).getPoint())+".");
					bw.newLine();
				}
				bw.write("/");
				bw.newLine();
				bw.write(""+Asset.WIDTH);
				bw.close();
			}

		} catch (IOException e){
			e.printStackTrace();
		}
	}


	public void open(String fileName) {
		FileReader fr = null;
		try {
			fr = new FileReader(fileName);
			MyPanel.points.clear();
			try{
				
				MyPanel.points.clear();
				Scanner input = new Scanner(fr);
				
				while(input.hasNextLine())
				{
					String line = input.nextLine();					
					addTile(line);
				}
				
				input.close();
				
			}catch (Exception e){
				e.printStackTrace();
			}

			fr.close();


		}catch (IOException e){
			e.printStackTrace();
		}

	}

//	private void print() {
//		for(int i = 0; i < MyPanel.points.size(); i++){
//			System.out.println(MyPanel.points.get(i).getPoint());
//		}
//	}

	public void addTile(String s)
	{
		String word = new String();
		for(int i = 0; i < s.length(); i++){
			if(!(s.charAt(i) == ';'))
				word = word + s.charAt(i);
			else
				break;    			
		}

		int index = word.length() + 1 ;
		int numberX = 0,numberY = 0;
		
		for (; s.charAt(index) != ';'; index++){
			numberX = numberX * 10 + Character.getNumericValue(s.charAt(index));
		}
		index ++;
		for (; s.charAt(index) != '.'; index++){
			numberY = numberY * 10 + Character.getNumericValue(s.charAt(index));
		}
		//System.out.println(word+" "+numberX+" "+numberY); //okk
		
		//coordinate INVERTITE!!
		Point p = new Point(numberY, numberX);
		Sprite sprite = new Sprite(p,word);
		MyPanel.points.add(sprite);
		
		
	}


}

