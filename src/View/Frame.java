package View;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import Model.Screen;


/**
 * @author Felix
 * @since 2015/11/10
 * 
 * Deals with the properties of the window such as title, and size
 * needs the Jpanel to be added on from screen
 */
public class Frame extends JFrame{

	/**
	 * Title of the game, static variable so visible throughout class
	 */
	public static String title = "WasteWatchers";
	
	/**
	 * Dimension(width and height) of the jframe
	 */
	//public static Dimension size = new Dimension(750, 650);
	//public static Dimension size = new Dimension(1300, 1000);
	//public static Dimension size = new Dimension(900, 725);
	//Sets the size of the window.  The one below makes it fullscreen.
	public static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

	/**
	 * Constructor for the frame. sets the title and size, cant resize window 
	 */
	public Frame(boolean isFirst){
		setTitle(title);
		//sets the size of window to be the dimension
		setSize(size);
		//the window is not resizable.  It kept breaking when it was.
		setResizable(false);
		//makes the window centered on the screen
		setLocationRelativeTo(null);
		//Makes it borderless and look nice.  
		//WARNING:  This means there is no exit button.  Alt-Tab or Alt-F4 to close it when enabled.
		//setUndecorated(true);
		//Makes it close when you close it
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//I have no idea.  Seems superfluous.
		//setExtendedState(getExtendedState() |JFrame.MAXIMIZED_BOTH);
		if(isFirst){
			init();
		}
	}
	
	/**
	 *  Initializes by making a grid layout as the screen, basically one big block
	 *  
	 */
	public void init(){
		setLayout(new BorderLayout());
		ImageIcon BackgroundIcon = new ImageIcon("resources/Background.png"); // Load the image to a imageIcon
		Image tempBackgroundImage = BackgroundIcon.getImage(); // Transform it into a scalable form 
		Image newTempBackgroundImg = tempBackgroundImage.getScaledInstance(
				(int)(getWidth()), //Scales based on width and height of the Frame
				(int)(getHeight()),  
				java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way  
		BackgroundIcon = new ImageIcon(newTempBackgroundImg);  // Transform it back
		setContentPane(new JLabel(BackgroundIcon));
		setLayout(new BorderLayout());
		
		/*JLabel lblTitle = new JLabel("");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon titleIcon = new ImageIcon("resources/Title.png"); // Load the image to a imageIcon
		Image tempImage = titleIcon.getImage(); // Transform it into a scalable form 
		Image newTempImg = tempImage.getScaledInstance(
				(int)(getWidth()*0.9), //Scales based on width and height of the Frame
				(int)(getHeight()*0.4),  
				java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way  
		titleIcon = new ImageIcon(newTempImg);  // Transform it back
		lblTitle.setIcon(titleIcon);
		getContentPane().add(lblTitle, BorderLayout.NORTH);*/
		
		JLabel lblPlayEasy = new JLabel("");
		lblPlayEasy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Play!");
				playGame(150,100);
			}
		});
		lblPlayEasy.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon playEasyIcon = new ImageIcon("resources/PlayEasy.png"); // Load the image to a imageIcon
		Image tempPlayEasyImage = playEasyIcon.getImage(); // Transform it into a scalable form 
		Image newTempPlayEasyImg = tempPlayEasyImage.getScaledInstance(
				(int)(getWidth()*0.3), //Scales based on width and height of the Frame
				(int)(getHeight()*0.5),  
				java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way  
		playEasyIcon = new ImageIcon(newTempPlayEasyImg);  // Transform it back
		lblPlayEasy.setIcon(playEasyIcon);
		getContentPane().add(lblPlayEasy, BorderLayout.WEST);
		
		JLabel lblPlayHard = new JLabel("");
		lblPlayHard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("Play Hard Mode!");
				playGame(50,100);
			}
		});
		lblPlayHard.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon playHardIcon = new ImageIcon("resources/PlayHard.png"); // Load the image to a imageIcon
		Image tempPlayHardImage = playHardIcon.getImage(); // Transform it into a scalable form 
		Image newTempPlayHardImg = tempPlayHardImage.getScaledInstance(
				(int)(getWidth()*0.3), //Scales based on width and height of the Frame
				(int)(getHeight()*0.5),  
				java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way  
		playHardIcon = new ImageIcon(newTempPlayHardImg);  // Transform it back
		lblPlayHard.setIcon(playHardIcon);
		getContentPane().add(lblPlayHard, BorderLayout.EAST);
		
		JLabel lblTutorial = new JLabel("");
		lblTutorial.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//System.out.println("CLICK THINGS OR WHATEVER");
				tutorial();
				//Run the Tutorial
			}
		});
		lblTutorial.setHorizontalAlignment(SwingConstants.CENTER);
		ImageIcon tutIcon = new ImageIcon("resources/Tutorial.png"); // Load the image to a imageIcon
		Image tempTutImage = tutIcon.getImage(); // Transform it into a scalable form 
		Image newTempTutImg = tempTutImage.getScaledInstance(
				(int)(getWidth()*0.3), //Scales based on width and height of the Frame
				(int)(getHeight()*0.5),  
				java.awt.Image.SCALE_SMOOTH); // Scale it the smooth way  
		tutIcon = new ImageIcon(newTempTutImg);  // Transform it back
		lblTutorial.setIcon(tutIcon);
		getContentPane().add(lblTutorial, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public void tutorial(){
		Tframe frameEnd = new Tframe();
		
		setVisible(true);
	}
	
	public void endGame(){
		dispose();
	}
	
	public void playGame(int startCash, int startHealth){
		//makes it one big window in order to add object
		//getContentPane().removeAll();  //Removes Title Screen
		
		Frame gameWindow = new Frame(false);
		
		gameWindow.setAlwaysOnTop(true);
		
		Screen screen = new Screen(gameWindow,startCash,startHealth);
		gameWindow.getContentPane().add(screen);
		gameWindow.setVisible(true);
	}
	
	public static void main(String args[]){
		Frame frame = new Frame(true);
	}
}
