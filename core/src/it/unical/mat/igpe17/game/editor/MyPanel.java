package it.unical.mat.igpe17.game.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JPanel;




public class MyPanel extends JPanel implements MouseListener, MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	public static String IMAGE_NAME = null;
	protected  Image image;
	protected static Vector<Sprite> points = new Vector<Sprite>(300);
	private Sprite sprite = new Sprite();

	public MyPanel() 
	{
		super();
		sprite.setName(Asset.BACKGROUND);
		image = sprite.getImageByName();
		image.getScaledInstance(100, 80, Image.SCALE_DEFAULT);
		
	}
	
	public static String toString(Point p){
	
		return ";"+(int)p.getY()+";" + (int)p.getX();
	}

	public void paintComponent(Graphics g) {
		//this.setBackground(Color.WHITE);
		super.paintComponent(g);
		if(image == null) return;
	    g.drawImage(image,0,0, null); 
	    
	    for(int i = 0; i < image.getWidth(this); i += 64){
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(i, 0, 1, image.getHeight(this));
			g.fillRect(0, i, image.getWidth(this), 1);
		} 
	    
	    for(int i = 0; i < points.size() && points.get(i) != null; i++){
			Sprite tmp = points.get(i);
			g.drawImage(tmp.getImageByName(),tmp.getPoint().x*64, tmp.getPoint().y*64, null);
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
		Point point = clickToGrid(x,y);		
		Sprite sp = new Sprite(point,IMAGE_NAME);
		
		//System.out.println("   " + sp.getName());	
		//removeDuplicate(point);
		boolean checked = true;
		for(int i = 0; i < points.size() && points.get(i) != null;i++){
			if(!points.isEmpty() && sp.getPoint().equals(points.get(i).getPoint())){
				checked = false;
				break;
			}
		}
		if(checked){
			points.add(sp);		
			repaint();
		}
	
	}
	private Point clickToGrid(int x, int y){
		int px = x ;
		int py = y ;
		px = px / 64;
		py = py / 64;
		return new Point(px,py);
	}
/*	
	private void removeDuplicate(Point p){
		for(int i = 0; i < points.size();i++){
			Sprite tmp = points.get(i);
			if (tmp.getPoint().equals(p)){
				points.remove(i);
				return;
			}
		}
	}

*/
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
