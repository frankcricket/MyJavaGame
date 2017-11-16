package it.unical.mat.igpe17.game.editor;


import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import it.unical.mat.igpe17.game.constants.Asset;


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
		Image image = new ImageIcon(Asset.getPath(name)).getImage();
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

	
}
