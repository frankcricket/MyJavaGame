package it.unical.mat.igpe17.game.editor;



import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;


public class FileUtils {


	private File directory;
	private Image image;
	private ArrayList<Object> resultList = new ArrayList<Object>(256);
	
	//costruttore
	public FileUtils() {
		image = null;
		//directory della cartella in cui si trovano le sprite
		//directory = new File("C:/Users/Cenzo WM/Documents/GitHub/ProgIGPE/ProgIGPE/asset");
		directory = new File("asset");
		
		File[] f = directory.listFiles();
		for (File file : f) {
			if (file != null && file.getName().toLowerCase().endsWith(".png") ) {
				try {
					String str=file.getCanonicalPath();
					image = new ImageIcon(str).getImage();
					resultList.add(image);
					str = str.substring(str.lastIndexOf('\\')+1,str.length()-4);
					resultList.add(str);                	

				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			

		}//fine forEach 
		
	}

	
	
	public Image getImage(String name){
		Image img = null;
		for(int i = 1; i < resultList.size(); i+=2){
			if(resultList.get(i).equals(name)){
				img = (Image)resultList.get(i-1);
				break;
			}
		}
		return img;
	}
	
/*	
	

	public void print()
	{
		for(int i = 0; i < resultList.size(); i++){
			if(resultList.get(i) instanceof Image )
				System.out.println("image");
			else
			if(resultList.get(i) instanceof String)
			//String s = (String)tmp.get( i + 1);
			System.out.println(resultList.get(i));
		}
	}


	public static void main(String[] args){
		FileUtils fu = new FileUtils();
		Image ii = fu.getImage("grid");
		fu.print();


	}


*/

}

