package it.unical.mat.igpe17.game.editor;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 1280;
	private static final int HEIGHT = 780;


	static MyPanel panel_img = new MyPanel();
	JScrollPane scrollFrame = new JScrollPane(panel_img);
	JPanel panelNorth = new JPanel();
	JPanel panelEast = new JPanel();	
	JPanel panelSouth = new Panel();

	JPanel southP = new JPanel();
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
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
				}
			}

		});
		
	}

	private void createFrame() { 			
		
		deleteall.setIcon(new ImageIcon (Asset.BIN));
		undo.setIcon(new ImageIcon (Asset.UNDO));

		//crea un borderLayout sul frame
		this.setLayout(new BorderLayout());

		//GESTIONE PANNELLO IMMAGINE
		panel_img.setPreferredSize(new Dimension(panel_img.image.getWidth(this),panel_img.image.getHeight(this)));
		panel_img.addListenerToPanel();
		panel_img.setAutoscrolls(true);

		//DEFINIZIONE DIMENSIONI PANNELLI
		panelNorth.setPreferredSize(new Dimension(1000,80));
		panelSouth.setPreferredSize(new Dimension(100,200));	
		westP.setPreferredSize(new Dimension(80, 100));
		eastP.setPreferredSize(new Dimension(50, 100));

		centerPanel.setLayout(new BorderLayout());
		//INSERIMENTO DEL JTree
		centerPanel.add(new MyTree(),"Center");

		//pannello combobox del BACKGROUND
//		String[] shapes = {"-----"}; //, "background1" , "background2"
//		JComboBox combobox = new JComboBox(shapes);
		JPanel pComboBbackground = new JPanel();
		JLabel label = new JLabel("Background");
		label.setFont(new Font("Serif", Font.PLAIN, 18));
//		combobox.setFont(new Font("Serif", Font.PLAIN, 18));
		pComboBbackground.add(label);
//		pComboBbackground.add(combobox);
		
		/* ComboBox Background */
//		combobox.addActionListener(new ActionListener() 
//		{			
//			@Override
//			public void actionPerformed(ActionEvent e) 
//			{				
////				String s = ((String)combobox.getItemAt(combobox.getSelectedIndex()));				
////				MySaveFile.IMAGE_NAME = s;
//				panel_img.image = new ImageIcon(Asset.BACKGROUND).getImage();
//				repaint();
//			
//			}
//		});

		//creo un borderLayout in cui vado a inserire i bottoni e il pannello PLAYER  
		southP.setPreferredSize(new Dimension(southP.getWidth(), 100));
		JPanel pPlayer = new JPanel();
		pPlayer.setLayout(new BorderLayout());
		southP.add(deleteall);
		southP.add(undo);
		pPlayer.add(southP,"North");		
		//pPlayer.add(panel2,"South");

		panelSouth.setLayout(new BorderLayout());			
		
		//panelSouth.add(playerLabel, "East");
		
		//aggiungo i pannelli al borderLayout centerPanel
		centerPanel.add(westP, "West");
		centerPanel.add(eastP, "East");
		centerPanel.add(pComboBbackground, "North");
		centerPanel.add(pPlayer, "South");
		
		
		//aggiungo i pannelli al frame
		this.setJMenuBar(mymenu);	
		add(panelSouth,"South");
		add(centerPanel,"East");
		add(scrollFrame,"Center");

		setVisible(true);

	}
	
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
	

	
}