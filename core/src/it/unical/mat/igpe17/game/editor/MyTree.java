package it.unical.mat.igpe17.game.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

import it.unical.mat.igpe17.game.constants.Asset;

public class MyTree extends JPanel
{

	private static final long serialVersionUID = 1L;
	private JTree tree;
	
	public MyTree()
	{
		super(new GridLayout(1,0));

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Editor Tree");
		addGround(root);
		addObjects(root);
		addEnemies(root);
		addUtility(root);

		tree = new JTree(root);
		tree.setCellRenderer(new Render());		

		JScrollPane treeView = new JScrollPane(tree);
		Border blackline = BorderFactory.createLineBorder(Color.BLUE);
		tree.setBorder(blackline);
		treeView.setPreferredSize(new Dimension(201, 500));

		add(treeView);

		selectionListener();
		
	}

	private void addGround(DefaultMutableTreeNode root){

		DefaultMutableTreeNode ground = new DefaultMutableTreeNode("Ground");
//		
//		ImageIcon icon = new ImageIcon("asset/Ground1");
//		DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
//		render.setLeafIcon(icon);


		DefaultMutableTreeNode g1 = new DefaultMutableTreeNode("1");
		
		DefaultMutableTreeNode g2 = new DefaultMutableTreeNode("2");
		DefaultMutableTreeNode g3 = new DefaultMutableTreeNode("3");
		DefaultMutableTreeNode g4 = new DefaultMutableTreeNode("4");
		DefaultMutableTreeNode g5 = new DefaultMutableTreeNode("5");
		DefaultMutableTreeNode g6 = new DefaultMutableTreeNode("6");
		DefaultMutableTreeNode g7 = new DefaultMutableTreeNode("7");
		DefaultMutableTreeNode g8 = new DefaultMutableTreeNode("8");
		DefaultMutableTreeNode g9 = new DefaultMutableTreeNode("9");
		DefaultMutableTreeNode g10 = new DefaultMutableTreeNode("10");
		DefaultMutableTreeNode g11 = new DefaultMutableTreeNode("11");
		DefaultMutableTreeNode g12 = new DefaultMutableTreeNode("12");
		DefaultMutableTreeNode g13 = new DefaultMutableTreeNode("13");
		DefaultMutableTreeNode g14 = new DefaultMutableTreeNode("14");
		DefaultMutableTreeNode g15 = new DefaultMutableTreeNode("15");
		DefaultMutableTreeNode g16 = new DefaultMutableTreeNode("16");

		ground.add(g1);
		ground.add(g2);
		ground.add(g3);
		ground.add(g4);
		ground.add(g5);
		ground.add(g6);
		ground.add(g7);
		ground.add(g8);
		ground.add(g9);
		ground.add(g10);
		ground.add(g11);
		ground.add(g12);
		ground.add(g13);
		ground.add(g14);
		ground.add(g15);
		ground.add(g16);
		root.add(ground); 

	}
	
	private void addObjects(DefaultMutableTreeNode root){
		
		DefaultMutableTreeNode obj = new DefaultMutableTreeNode("Obstacles");
		
		//obj.add(new DefaultMutableTreeNode());
		
		DefaultMutableTreeNode ob1 = new DefaultMutableTreeNode("17");
		DefaultMutableTreeNode ob2 = new DefaultMutableTreeNode("18");
		DefaultMutableTreeNode ob3 = new DefaultMutableTreeNode("19");
		DefaultMutableTreeNode ob4 = new DefaultMutableTreeNode("20");
		DefaultMutableTreeNode ob5 = new DefaultMutableTreeNode("21");
		DefaultMutableTreeNode ob6 = new DefaultMutableTreeNode("22");
		DefaultMutableTreeNode ob7 = new DefaultMutableTreeNode("23");
		DefaultMutableTreeNode ob8 = new DefaultMutableTreeNode("24");
		DefaultMutableTreeNode ob9 = new DefaultMutableTreeNode("25");
		DefaultMutableTreeNode ob10 = new DefaultMutableTreeNode("26");
		DefaultMutableTreeNode ob11 = new DefaultMutableTreeNode("27");
		DefaultMutableTreeNode ob12 = new DefaultMutableTreeNode("28");
		DefaultMutableTreeNode ob13 = new DefaultMutableTreeNode("29");
		DefaultMutableTreeNode ob14 = new DefaultMutableTreeNode("30");
		
		DefaultMutableTreeNode coin = new DefaultMutableTreeNode("50");
		
		obj.add(ob1);
		obj.add(ob2);
		obj.add(ob3);
		obj.add(ob4);
		obj.add(ob5);
		obj.add(ob6);
		obj.add(ob7);
		obj.add(ob8);
		obj.add(ob9);
		obj.add(ob10);
		obj.add(ob11);
		obj.add(ob12);
		obj.add(ob13);
		obj.add(ob14);
		obj.add(coin);
		
		
		
		
		root.add(obj);
		
	}
	
	private void addEnemies(DefaultMutableTreeNode root){
		
		DefaultMutableTreeNode enem = new DefaultMutableTreeNode("Enemies");
		
		//obj.add(new DefaultMutableTreeNode());
		
		DefaultMutableTreeNode enem1 = new DefaultMutableTreeNode("31");
		DefaultMutableTreeNode enem2 = new DefaultMutableTreeNode("32");
		DefaultMutableTreeNode enem3 = new DefaultMutableTreeNode("33");
		
		
		enem.add(enem1);
		enem.add(enem2);
		enem.add(enem3);
		
		root.add(enem);
	}
	
	private void addUtility(DefaultMutableTreeNode root){
		DefaultMutableTreeNode util = new DefaultMutableTreeNode("Utility");
		
		DefaultMutableTreeNode door = new DefaultMutableTreeNode("70");
		DefaultMutableTreeNode key = new DefaultMutableTreeNode("71");
		
		util.add(door);
		util.add(key);
		
		root.add(util);
	}



	public void selectionListener(){

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				
				String s = e.getPath().getLastPathComponent().toString();
				MyPanel.IMAGE_NAME = s;
				
			}
		});
	}


	class Render implements TreeCellRenderer 
	{
		
		private JLabel label;
		private Border border;
		private Font font; 
		

		Render() 
		{
			label = new JLabel();
			border = BorderFactory.createEmptyBorder ( 5, 2, 7, 2 );
			font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
			
		}

		/*
		 * Imposto l'icona ad ogni nodo del JTree 
		 */
		 
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean ex,boolean l, int r, boolean foc)
		{
			Object o = ((DefaultMutableTreeNode) value).getUserObject();
			ImageIcon img = new ImageIcon(Asset.getPath((String) o));
			if (img != null) 
			{
				label.setIcon(img);
			}
			label.setText((String) o);
			label.setBorder(border);
			label.setFont(font);
				
			return label;
		}
	}

}


