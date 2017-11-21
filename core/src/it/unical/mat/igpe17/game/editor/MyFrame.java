package it.unical.mat.igpe17.game.editor;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import it.unical.mat.igpe17.game.constants.Asset;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	

	static MyPanel drawing = new MyPanel();
	JScrollPane scrollFrame = new JScrollPane(drawing);
	JPanel panelNorth = new JPanel();
	JPanel panelEast = new JPanel();	

	JPanel westP = new JPanel();
	JPanel centerPanel = new JPanel();
	JPanel eastP = new JPanel();
	MyMenu mymenu = new MyMenu();


	JButton deleteall = new JButton();
	JButton undo = new JButton();

	ImageIcon imagePlayer;
	JLabel playerLabel;

	JScrollPane  scroll = new JScrollPane();

	public MyFrame(String title){
		setTitle(title);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		//aggiunta icon alla finestra jframe
		ImageIcon img = new ImageIcon("asset/menu_img/game_icon.png");
		setIconImage(img.getImage());

		
		
		scrollFrame.setAlignmentX(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		createFrame();
		addListenerToFrame();	

	}



	private void addListenerToFrame() {

		deleteall.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MyPanel.points.clear();
				repaint();

			}
		});

		undo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int pos = MyPanel.points.size() - 1;
				if (pos >= 0){
					MyPanel.points.remove(pos);
					repaint();
				}

			}
		});
		mymenu.open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFileChooser chooser = new JFileChooser();				
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					String path = chooser.getSelectedFile().getAbsolutePath();					
					MySaveFile mysavefile = new MySaveFile();
					mysavefile.open(path);
					repaint();
					
					JOptionPane.showMessageDialog(null, "Operation Completed!");
				}
			}

		});

	}

	private void createFrame() { 			

		deleteall.setIcon(new ImageIcon (Asset.BIN));
		undo.setIcon(new ImageIcon (Asset.UNDO));

		//crea un borderLayout sul frame
		setLayout(new BorderLayout());

		drawing.setPreferredSize(new Dimension(Asset.WIDTH,Asset.HEIGHT));
		panelNorth.setPreferredSize(new Dimension(1000,80));
		westP.setPreferredSize(new Dimension(80, 100));
		eastP.setPreferredSize(new Dimension(50, 100));
		
		drawing.addListenerToPanel();
		drawing.setAutoscrolls(true);

		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(new MyTree(),"Center");
		
		JPanel southP = new JPanel();
		southP.setPreferredSize(new Dimension(southP.getWidth(), 100));
		JPanel buttonPanel = new JPanel();

		buttonPanel.setLayout(new BorderLayout());
		southP.add(deleteall);
		southP.add(undo);
		buttonPanel.add(southP,"North");		

		//aggiungo i pannelli al borderLayout centerPanel
		centerPanel.add(westP, "West");
		centerPanel.add(eastP, "East");
		centerPanel.add(buttonPanel, "South");

		//panel sotto l'area di disegno
		JPanel pp = new JPanel();
		pp.setPreferredSize(new Dimension(WIDTH, 131));

		//aggiungo i pannelli al frame
		setJMenuBar(mymenu);	
		add(centerPanel,"East");
		add(scrollFrame,"Center");
		add(pp,"South");
		
		setVisible(true);

	}
	/*
	class Panel extends JPanel
	{
		protected  Image image;
		private Sprite sprite = new Sprite();

		public Panel() 
		{
			super();
			sprite.setName(Asset.BACKGROUND);
			image = sprite.getImageByName();
			image.getScaledInstance(100, 80, Image.SCALE_DEFAULT);

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if(image == null) return;
		    g.drawImage(image,0,0, null); 

		}
	}
	 */


}