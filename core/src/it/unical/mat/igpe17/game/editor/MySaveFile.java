package it.unical.mat.igpe17.game.editor;

import java.awt.Dimension;
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import it.unical.mat.igpe17.game.constants.Asset;

public class MySaveFile {

	private int dimension;
	private Vector<Sprite> points;
	private Vector<Sprite> pointsTmp = new Vector<Sprite>();
	
	
	
/*
 * 
	public void save() {
		try {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save in...");

			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				FileWriter fw = new FileWriter(fc.getSelectedFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("" + MyPanel.row + " " + (Asset.WIDTH / Asset.TILE)); // TODO  fixare
				bw.newLine();
				for (int i = 0; i < MyPanel.pointsTmp.size(); i++) {
					bw.write(MyPanel.pointsTmp.get(i).getName() + MyPanel.toString(MyPanel.pointsTmp.get(i).getPoint()));
					bw.newLine();
				}
				bw.write("/");
				bw.newLine();
				bw.write("" + Asset.WIDTH);
				bw.close();

				JOptionPane.showMessageDialog(null, "Operation Completed!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
*/
	public void saveNewFile() {
		try {
			JFileChooser fc = new JFileChooser();
			File dir = new File(System.getProperty("user.dir")+"\\levels");
			String path = dir.getAbsolutePath();
			fc.setCurrentDirectory(dir);
			fc.setDialogTitle("Save in...");

			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				FileWriter fw = new FileWriter(fc.getSelectedFile() + ".tmx");
				BufferedWriter bw = new BufferedWriter(fw);

				int width = MyPanel.row;
//				int height = (Asset.WIDTH / Asset.TILE);

				points = MyPanel.getPoint();
				copyPoints();
				int height = findDimension();

				/*
				 * viene impostata la nuova posizione dei nemici
				 */
				for (int i = 0; i < pointsTmp.size(); i++){
					if (pointsTmp.get(i).getName().equals("31")
							|| pointsTmp.get(i).getName().equals("32")
							|| pointsTmp.get(i).getName().equals("33")) {
						int xtmp = pointsTmp.get(i).getPoint().x;
						int ytmp= pointsTmp.get(i).getPoint().y;
						
						String name = pointsTmp.get(i).getName();
						
						ytmp ++;
						
						pointsTmp.get(i).setPoint(new Point(xtmp, ytmp));
					}
				}
				
				startWrite(bw, width, height);
				for (int i = 0; i < width; i++) {
					writePoints(bw, i, width, height);
				}
				endWrite(bw);
				
				bw.close();
				fw.close();
				

				JOptionPane.showMessageDialog(null, "Operation Completed!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void startWrite(BufferedWriter bw, int width, int height) throws IOException {
		bw.write("<?xml version= " + "\"" + "1.0" + "\"" + " encoding=" + "\"" + "UTF-8" + "\"" + "?>");
		bw.newLine();
		bw.write("<map version=" + "\"" + "1.0" + "\"" + " tiledversion=" + "\"" + "2017.11.22" + "\"" + " orientation="
				+ "\"" + "orthogonal" + "\"" + " renderorder=" + "\"" + "right-down" + "\"" + " width=" + "\"" + height
				+ "\"" + " height=" + "\"" + width + "\"" + " tilewidth=" + "\"" + Asset.TILE + "\"" + " tileheight="
				+ "\"" + Asset.TILE + "\"" + " infinite=" + "\"" + "0" + "\"" + " nextobjectid=" + "\"" + "1" + "\""
				+ ">");
		bw.newLine();
		/*
		 * Incusione file ground: 1-16
		 */
		for (int i = 1; i <= 16; i++) {
			bw.write(" <tileset firstgid=" + "\"" + i + "\"" + " source=" + "\"" + "tileset/Ground" + i + ".tsx" + "\""
					+ "/>");
			bw.newLine();
		}
		/*
		 * Inclusione ostacoli: 17-30
		 */
		for (int i = 17; i <= 30; i++) {
			bw.write(" <tileset firstgid=" + "\"" + i + "\"" + " source=" + "\"" + "tileset/obstacle" + i + ".tsx"
					+ "\"" + "/>");
			bw.newLine();
		}
		
		/*
		 * Inclusione nemici: 31-33
		 */
		
		for (int i = 31; i <= 33; i++){
			bw.write(" <tileset firstgid=" + "\"" + i + "\"" + " source=" + "\"" + "tileset/enemy" + i + ".tsx"
					+ "\"" + "/>");
			bw.newLine();
		}
		
		
		
		bw.write(" <layer name=" + "\"" + "Livello tile 1" + "\"" + " width=" + "\"" + height + "\"" + " height=" + "\""
				+ width + "\"" + ">");
		bw.newLine();
		bw.write("  <data encoding=" + "\"" + "csv" + "\"" + ">");
		bw.newLine();
	}

	private void writePoints(BufferedWriter bw, int i, int width, int height) throws IOException {
		for (int j = 0; j < height; j++) {
			Sprite p = checkPoint(i, j);
			if (p == null) {
				if (i == width - 1 && j == height - 1)
					bw.write("0");
				else
					bw.write("0,");
			} else if (i == width - 1 && j == height - 1)
				bw.write(p.getName());
			else
				bw.write(p.getName() + ",");
		}
		bw.newLine();

	}

	
	private final Sprite checkPoint(int i, int j) {
		
		for (Sprite s : pointsTmp) {
			if (s.getPoint().x == j && s.getPoint().y == i)
				return s;
		}
		return null;
	}

	private void endWrite(BufferedWriter bw) throws IOException {
		bw.write("</data>");
		bw.newLine();
		bw.write(" </layer>");
		bw.newLine();
		bw.write("</map>");
	}

	public void open(String fileName) {
		try {

			FileReader fr = new FileReader(fileName);
			try {

				MyPanel.points.clear();
				Scanner input = new Scanner(fr);
				/*
				 * Ignoro le righe del file fin quando non trovo la mappa
				 */
				while (input.hasNextLine()) {
					if (input.nextLine().startsWith("  <data")) {
						break;
					}
				}
				/*
				 * Parsing delle righe e colonne
				 */
				int count = 0;
				while (input.hasNextLine()) {
					String tmp = input.nextLine();
					if (tmp.equals("</data>"))
						break;
					addObjects(tmp, count);
					count++;
				}

				/*
				 * Aumento la dimensione del pannello in base all'estensione della mappa
				 */
				Asset.WIDTH = dimension * Asset.TILE;
				MyFrame.drawing.setPreferredSize(new Dimension(Asset.WIDTH, Asset.HEIGHT));
				MyFrame.drawing.revalidate();
				input.close();

			} catch (Exception e) {
				e.printStackTrace();
			}

			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void addObjects(String line, int row) {
		String[] split = line.split(",");
		dimension = split.length;
		for (int i = 0; i < split.length; i++) {
			if (!(split[i].equals("0"))) {
				if(split[i].equals("31") || split[i].equals("32") || split[i].equals("33")){
					Point p = new Point(i, row-1);// coordinate invertite
					Sprite sprite = new Sprite(p, split[i]);
					MyPanel.points.add(sprite);
				} else{
					Point p = new Point(i, row);// coordinate invertite
					Sprite sprite = new Sprite(p, split[i]);
					MyPanel.points.add(sprite);
				}
			}
		}
	}
	
	public void addTile(String s) {
		String[] tmp = s.split(";");
		String type = tmp[0];
		int x = Integer.parseInt(tmp[1]);
		int y = Integer.parseInt(tmp[2]);

		Point p = new Point(y, x);
		Sprite sprite = new Sprite(p, type);
		MyPanel.points.add(sprite);
	}
	
	private void copyPoints(){
		for (int i = 0; i < points.size(); i++){
			String string = points.get(i).getName();
			int xtmp = points.get(i).getPoint().x;
			int ytmp = points.get(i).getPoint().y;
			Sprite spriteTmp = new Sprite(new Point(xtmp, ytmp), string);
			pointsTmp.add(spriteTmp);
		}
	}
	
	private int findDimension(){
		int xMax = 0;
		for(Sprite s : points){
		
			if(s.getPoint().x > xMax)
				xMax = s.getPoint().x;
		}
		return ++xMax;
	}
}
