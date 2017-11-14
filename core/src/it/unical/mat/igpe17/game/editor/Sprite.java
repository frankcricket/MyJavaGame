package it.unical.mat.igpe17.game.editor;


import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;


public class Sprite {

	private Point point;
	private String name;

	
	public Sprite() {
		
	}
	public Sprite(Point point, String name)
	{
		this.point = point;
		this.name = name;
	}
	public Image getImageByName(){
//		FileUtils fu = new FileUtils();
//		Image g = fu.getImage(name);
//		return g;
		Image image = new ImageIcon(name).getImage();
		return image;		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;		
	}	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}	
	
}
