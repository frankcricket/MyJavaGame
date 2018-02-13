package it.unical.mat.igpe17.game.editor;

import java.awt.event.*;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import it.unical.mat.igpe17.game.screens.MainMenu;

import javax.swing.JMenuBar;

public class MyMenu extends JMenuBar {


	private static final long serialVersionUID = 1L;
	JMenu file;	
	JMenuItem save;
	JMenuItem exit;
	JMenuItem open;
	
	JMenuItem load_level;

	public MyMenu(){	
		super();
		
		file = new JMenu("File");
		save = new JMenuItem("Save");
		exit = new JMenuItem("Exit");
		open = new JMenuItem("Open");

		load_level = new JMenuItem("Load Level");

		this.add(file);
		file.add(open);
		file.add(save);
		file.add(load_level);
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
//				mysavefile.save();
				mysavefile.saveNewFile();
			}
		});
		
		load_level.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();	
				File dir = new File(System.getProperty("user.dir")+"\\levels\\default_levels");
				chooser.setCurrentDirectory(dir);
				if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
					String path = chooser.getSelectedFile().getAbsolutePath();
					File f = new File(path);
					String result = f.getName();
					result = "levels/default_levels/" + result;
					MainMenu.LEVEL = result;					
					JOptionPane.showMessageDialog(null, "Operation Completed!");
				}
			}
		});
		

	}


}	





