package it.unical.mat.igpe17.game.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import java.awt.Point;

import javax.swing.JPanel;

import it.unical.mat.igpe17.game.constants.Asset;


public class MyPanel extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;

	protected static int row = Asset.HEIGHT / Asset.TILE;
	protected static int column;

	public static String IMAGE_NAME = null;
	protected static Vector<Sprite> points = new Vector<Sprite>(300);


	public void paintComponent(Graphics g) {
		super.paintComponent(g);	    
		for(int i = 0; i < Asset.WIDTH; i += Asset.TILE){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(i, 0, 1, Asset.HEIGHT);
			g.fillRect(0, i, Asset.WIDTH, 1);
		} 

		for(int i = 0; i < points.size(); i++){
			Sprite tmp = points.get(i);
			g.drawImage(tmp.getImageByName(),
					tmp.getPoint().x*Asset.TILE,
					tmp.getPoint().y*Asset.TILE,
					null);
		}

	}

	public void addListenerToPanel(){
		addMouseListener(this); 
		addMouseMotionListener(this);		
	}


	@Override
	public void mouseDragged(MouseEvent e) {
		mousePressed(e);
	}

	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		Point point = null;
		if(clickToGrid(x,y))
		{
			point = new Point(x/Asset.TILE, y/Asset.TILE);
			column = x;
			Sprite sp = new Sprite(point,IMAGE_NAME);

			boolean checked = true;
			for(int i = 0; i < points.size();i++){
				if(!points.isEmpty() && sp.getPoint().equals(points.get(i).getPoint())){
					checked = false;
					break;
				}
			}
			if(checked){
				points.add(sp);	
				repaint();
				if(column >= Asset.WIDTH * 0.9){
					Asset.WIDTH += (int) (Asset.WIDTH * .2); 
					MyFrame.drawing.setPreferredSize(new Dimension(Asset.WIDTH, Asset.HEIGHT));
					MyFrame.drawing.revalidate();
					repaint();
				}
			}
		}
	}
	private boolean clickToGrid(int x, int y){
		return ((x >=0 && x < Asset.WIDTH) 
				&& (y >=0 && y < Asset.HEIGHT));
	}

	public static String toString(Point p){

		return ";"+(int)p.getY()+";" + (int)p.getX();
	}
	
	public static final Vector<Sprite> getPoint(){
		return points;
	}


	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {		
	}
	@Override
	public void mouseExited(MouseEvent e) {	
	}
	@Override
	public void mouseReleased(MouseEvent e) {			
	}
}
