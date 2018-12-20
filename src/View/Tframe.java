package View;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Controller.KeyHandler;

public class Tframe extends JFrame {
	public static String title = "WasteWatchers";
	//public static Dimension size = new Dimension(900, 725);
	public static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
	public static Image[] demoTile = new Image[20];
	/**
	 * Counter for the demo pictures
	 */
	public int i = 0;
	
	public Tframe(){
		setTitle(title);
		//sets the size of window to be the dimension
		setSize(size);
		//the window is resizable
		setResizable(false);
		//makes the window centered on the screen
		setLocationRelativeTo(null);
		setUndecorated(true);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		for (int i =0; i< demoTile.length; i++) {
			//imported the image in our resource folder
			demoTile[i] = new ImageIcon("resources/tutNAMES.png").getImage();
			//creating a new image from image with filtered image, the we crop the image 
			demoTile[i] = createImage(new FilteredImageSource(demoTile[i].getSource(), new CropImageFilter(0, 658*i, 1189, 658)));
		}
		addMouseListener(new KeyHandler());
		addMouseMotionListener(new KeyHandler());
		init(i);
	}

	private void init(int x) {
		setLayout(new BorderLayout());
		
		
		/*for (int i =0; i< demoTile.length; i++) {
			ImageIcon BackgroundIcon; 
			Image tempBackgroundImage = demoTile[i];
			Image newTempBackgroundImg = tempBackgroundImage.getScaledInstance(
					(int)(getWidth()*0.7), 
					(int)(getHeight()*0.7),  
					java.awt.Image.SCALE_SMOOTH);  
			BackgroundIcon = new ImageIcon(newTempBackgroundImg);  
			 
			setContentPane(new JLabel(BackgroundIcon));
			setLayout(new BorderLayout());
		}*/
		
		ImageIcon BackgroundIcon; 
		Image tempBackgroundImage = demoTile[x];
		Image newTempBackgroundImg = tempBackgroundImage.getScaledInstance(
				(int)(getWidth()*.9), 
				(int)(getHeight()*.9),  
				java.awt.Image.SCALE_SMOOTH);  
		BackgroundIcon = new ImageIcon(newTempBackgroundImg);  
		 
		setContentPane(new JLabel(BackgroundIcon));
		setLayout(new BorderLayout());
		
		/*ImageIcon BackgroundIcon = new ImageIcon("resources/TutorialFinal.png"); 
		Image tempBackgroundImage = BackgroundIcon.getImage();
		Image newTempBackgroundImg = tempBackgroundImage.getScaledInstance(
				(int)(getWidth()*0.7), 
				(int)(getHeight()*0.7),  
				java.awt.Image.SCALE_SMOOTH);  
		BackgroundIcon = new ImageIcon(newTempBackgroundImg);  
		 
		setContentPane(new JLabel(BackgroundIcon));
		setLayout(new BorderLayout());*/
		JLabel lblPlayHard = new JLabel("");
		lblPlayHard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				back();
				
			}
		});
		lblPlayHard.setHorizontalAlignment(SwingConstants.SOUTH_EAST);
		//ImageIcon playHardIcon = new ImageIcon("resources/back.png"); 
		//lblPlayHard.setIcon(playHardIcon);
		getContentPane().add(lblPlayHard, BorderLayout.CENTER);
		setVisible(true);
		
	}
	public void back(){
		//dispose();
		if (i < 18) {
			i++;	
			init(i);
		}
		else {
			dispose();
		}	
		
		
	}
	public static void main(String args[]){
		Tframe frame = new Tframe();
	}
}
