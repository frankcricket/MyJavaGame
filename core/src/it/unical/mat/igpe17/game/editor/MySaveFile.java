package it.unical.mat.igpe17.game.editor;
import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import it.unical.mat.igpe17.game.constants.Asset;

public class MySaveFile {
	
	private BufferedWriter bw;
	private FileWriter fw;
	private JFileChooser fc = new JFileChooser();
	protected static String IMAGE_NAME = Asset.BACKGROUND;
	private Vector<Sprite> points;

	public void save(){
		try{
			fc.setDialogTitle("Save in...");
			
			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				fw = new FileWriter(fc.getSelectedFile());
				bw = new BufferedWriter(fw);
				bw.write("" + MyPanel.row + " " + (Asset.WIDTH / Asset.TILE));  //TODO fixare
				bw.newLine();
				for (int i = 0; i < MyPanel.points.size(); i++){
					bw.write(MyPanel.points.get(i).getName()+MyPanel.toString(MyPanel.points.get(i).getPoint()));
					bw.newLine();
				}
				bw.write("/");
				bw.newLine();
				bw.write(""+Asset.WIDTH);
				bw.close();
				
				JOptionPane.showMessageDialog(null, "Operation Completed!");
			}

		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void saveNewFile(){
		try{
			fc.setDialogTitle("Save in...");
			
			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
				fw = new FileWriter(fc.getSelectedFile() + ".tmx");
				bw = new BufferedWriter(fw);
				
				int width = MyPanel.row;
				int height = (Asset.WIDTH / Asset.TILE);

				points = MyPanel.getPoint();
				startWrite(bw,width,height);
				for (int i = 0; i < width; i++){
					writePoints(bw,i,width,height);
				}
				endWrite(bw);
				bw.close();
				
				JOptionPane.showMessageDialog(null, "Operation Completed!");
			}

		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	private void startWrite(BufferedWriter bw, int width, int height)throws IOException{
		bw.write("<?xml version= " + "\"" + "1.0" + "\"" + " encoding=" + "\"" + "UTF-8" + "\"" + "?>");
		bw.newLine();
		bw.write("<map version=" + "\"" +"1.0"+ "\"" +" tiledversion="+ "\"" +"2017.11.22"+ "\"" +" orientation="
										+ "\"" +"orthogonal"+ "\"" +" renderorder="+ "\"" 
									    +"right-down"+ "\"" +" width="+ "\"" +height+ "\"" +" height="+ "\"" +width+ "\"" 
										+" tilewidth="+ "\"" +Asset.TILE+ "\"" +" tileheight="+ "\"" +Asset.TILE+ "\"" 
									    +" infinite="+ "\"" +"0"+ "\"" +" nextobjectid="+ "\"" +"1"+ "\"" +">");
		bw.newLine();
		for(int i = 1; i <= 16; i++){
			bw.write(" <tileset firstgid="+ "\"" +i+ "\"" +" source="+ "\"" +"tileset/Ground"+i+".tsx"+ "\"" +"/>");
			bw.newLine();
		}
		bw.write(" <layer name="+ "\"" +"Livello tile 1"+ "\"" + " width="+ "\"" +height + "\"" + " height="+ "\"" +width + "\"" +">");
		bw.newLine();
		bw.write("  <data encoding="+ "\"" +"csv"+ "\"" +">");
		bw.newLine();
	}
	
	private void writePoints(BufferedWriter bw, int i,int width, int height) throws IOException{
		for(int j = 0; j < height; j++){
			Sprite p = checkPoint(i,j);
			if(p == null){
				if(i == width - 1 && j == height - 1)
					bw.write("0");
				else
					bw.write("0,");
			}
			else
			if(i == width - 1 && j == height - 1)
				bw.write(p.getName());
			else
				bw.write(p.getName() + ",");
		}
		bw.newLine();
		
		
		
	}
	private final Sprite checkPoint(int i, int j){
		for(Sprite s : points){
			if(s.getPoint().x == j && s.getPoint().y == i)
				return s;
		}
		return null;		
	}
	
	private void endWrite(BufferedWriter bw) throws IOException{
		bw.write("</data>");
		bw.newLine();
		bw.write(" </layer>");
		bw.newLine();
		bw.write("</map>");
	}


	public void open(String fileName) {
		FileReader fr = null;
		try {
			
			fr = new FileReader(fileName);
			try{
				
				MyPanel.points.clear();
				Scanner input = new Scanner(fr);
				input.nextLine(); //ignoro la dimensione della matrice
				while(input.hasNextLine())
				{
					String line = input.nextLine();	
					if(!line.equals("/"))
						addTile(line);
					else
						break;
				}
				
				String dimension = input.nextLine();
				int dim = Integer.parseInt(dimension);
				Asset.WIDTH = dim;
				MyFrame.drawing.setPreferredSize(new Dimension(Asset.WIDTH, Asset.HEIGHT));
				MyFrame.drawing.revalidate();
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

	
	/*
	
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

*/
	
	public void addTile(String s){
		String[] tmp = s.split(";");
		String type = tmp[0];
		int x = Integer.parseInt(tmp[1]);
		int y = Integer.parseInt(tmp[2]);
		
		Point p = new Point(y, x);
		Sprite sprite = new Sprite(p,type);
		MyPanel.points.add(sprite);
	}
}

