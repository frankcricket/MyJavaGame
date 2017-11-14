package it.unical.mat.igpe17.game.editor;

import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

public class MyMenu extends JMenuBar {


	private static final long serialVersionUID = 1L;
	JMenu file;	
	JMenuItem save;
	JMenuItem exit;
	JMenuItem open;

	public MyMenu(){	
		super();
		file = new JMenu("File");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		open = new JMenuItem("Open");

		this.add(file);
		file.add(open);
		file.add(save);
		file.add(exit);
		
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit performed");
				// MenuAcceleratorKeyStroke.this.dispose();
				System.exit(0);
			}
		});		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MySaveFile mysavefile = new MySaveFile();
				mysavefile.save();
			}
		});
		

	}


}	





