package it.unical.mat.igpe17.game.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

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
import javax.swing.tree.TreeSelectionModel;

public class MyTree extends JPanel
{

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private final Font currentFont;
	private final Font bigFont; 

	//Constructor
	public MyTree()
	{
		super(new GridLayout(1,0));

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Editor Tree");
		addGround(root);
		addObjects(root);

		tree = new JTree(root);
		tree.setCellRenderer(new Render());
		currentFont = tree.getFont();
		//tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		bigFont = new Font(currentFont.getName(), currentFont.getStyle(), currentFont.getSize() + 15);
		tree.setFont(bigFont);

		

		JScrollPane treeView = new JScrollPane(tree);
		Border blackline = BorderFactory.createLineBorder(Color.BLUE);
		tree.setBorder(blackline);
		treeView.setPreferredSize(new Dimension(201, 500));

		add(treeView);

		selectionListener();

	}

	private void addGround(DefaultMutableTreeNode root){

		DefaultMutableTreeNode ground = new DefaultMutableTreeNode("Ground");
		
		ImageIcon icon = new ImageIcon("asset/Ground1");
		DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
		render.setLeafIcon(icon);


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
		
		DefaultMutableTreeNode obj = new DefaultMutableTreeNode("Objects");
		
		obj.add(new DefaultMutableTreeNode());
		
		
		root.add(obj);
		
	}


	public void selectionListener(){

		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				
				String s = e.getPath().getLastPathComponent().toString();
				MyPanel.IMAGE_NAME = Asset.getPath(s);
				
			}
		});
	}


	class Render implements TreeCellRenderer 
	{
		
		private JLabel label;

		Render() 
		{
			label = new JLabel();
		}

		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean ex,boolean l, int r, boolean foc)
		{
			
			Object o = ((DefaultMutableTreeNode) value).getUserObject();		
			ImageIcon img = new ImageIcon(Asset.getPath((String) o));
			if (img != null) 
			{
				label.setIcon(img);
			}
			label.setText((String) o);
				
			return label;
		}
	}

}


