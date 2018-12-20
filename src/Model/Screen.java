package Model;

import javax.swing.*;

import Controller.KeyHandler;
import View.Frame;

import static org.junit.Assert.fail;

import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*;
import java.util.Random;

/**
 * @author Felix
 * @since 2015/11/10
 * 
 * The actual content in the window, has drawn images and components
 * works with the frame. Implements runnable to make it a thread
 */
public class Screen extends JPanel implements Runnable, Serializable{

	/**
	 * Creating a new thread of the current class. USes the run function.
	 */
	public Thread thread;

	public boolean isRunning;
	
	/**
	 * the height and width of the screen, screen has its own size instead of following the window
	 */
	public static int sWidth, sHeight;
	
	/**
	 * Current amount of enemies defeated
	 */
	public static int killed;
	
	/**
	 * Kills needed to advance to next level or win
	 */
	public static int killsToWin;
	
	/**
	 * Current level of game
	 */
	public static int level;
	
	/**
	 * Maximum number of levels 
	 */
	public static int maxLevel;
	
	/**
	 * Time is takes to win
	 */
	public static int winTime;
	
	/**
	 * Counter for the wintime
	 */
	public static int winFrame;
	
	/**
	 * the grass tile and air tile(transparent) images
	 */
	public static Image[] grassTile = new Image[10];
	/**
	 * Transparent air tiles that will be on top of the grass tiles
	 * Includes defense, enemy and resources
	 */
	public static Image[] airTile = new Image[10];
	
	public static Image[] WaterArray = new Image[6];
	
	/**
	 * An array or images which will contain and health and money icon  and tile layouts
	 * do not need all 100 images
	 */
	public static Image[] tileset_res = new Image[10];
	
	/**
	 * An images of the enemies, i.e. dumpers overfishers...
	 */
	public static Image[] enemyUnit = new Image[10];
	//public static Image[] unitToPlace = new Image[100];
	/**
	 * Images for all the defense and resource units
	 */
	public static Image[] defResUnit = new Image[10];
	

	
	/**
	 * The amount of coins game starts with
	 * Money lost when unit is bought and gained when enemy is defeated or fisherman is on screen
	 */
	public static int coinage;
	/**
	 * The amount of health the game starts with
	 * Health lost when enemy reaches estuary and regained by researcher
	 */
	public static int coinageCap = 300;
	
	public static int health;
	/**
	 * The maximum health that the estuary can reach
	 */
	public static int healthCap = 300;
	//Could also be called money
	
	
	/**
	 * Boolean that will tell if this is the first time the program is running
	 */
	public static boolean isFirst;
	/**
	 * Used to test the block fight method
	 * Makes the rectangle radius visible
	 */
	public static boolean isDebug = false;
	
	/**
	 * If the player has won
	 */
	public static boolean isWin;
	
	
	/**
	 * The x and y location point of the mouse 
	 */
	public static Point mse = new Point(0, 0);

	/**
	 * Contains a 2d array of blocks for the games map
	 */
	public static Room room;

	/**
	 * the save file that will create the estuary path
	 */
	public static Save save;

	/**
	 *  the shop that all units can be purchased from
	 */
	public static Store store;

	/**
	 * sets number of enemys that get spawned
	 * The array of mobs, only want 10 for now
	 */
	public static Enemy[] enemys = new Enemy[10];
	
	/**
	 * The probability of each enemy at index i spawning
	 */
	public static double[] enemyProb = new double[3];

	public Frame frame;
	
	/**
	 * Constructor that creates what is going to be on the screen
	 * Takes in a frame object so that the handlers can be used
	 */
	public Screen(Frame frame, int startCash, int startHealth){
		
		thread = new Thread(this);
		killed = 0;
		killsToWin = 0;
		level = 1;
		maxLevel = 3;
		winTime = 4000;
		winFrame = 0;
		isFirst = true;
		isWin = false;
		isRunning = true;
		
		this.frame = frame;
		
		//Since the keyhandler extends the other we can use them
		frame.addMouseListener(new KeyHandler());
		frame.addMouseMotionListener(new KeyHandler());

		coinage = startCash;
		health = startHealth;
		
		thread.start();
	}

	//Draws all the components, takes in a graphics object and will be called by default by jpanel
	public void paintComponent(Graphics g){
		//if isfirst is true then set the screen size of the game and define everything
		if(isFirst){
			//defines the width of the screen as size of the grame
			sWidth = getWidth();
			//defines the height of the screen
			sHeight = getHeight();
			//defines everything beside the thread
			define();
		}
		isFirst = false;

		//Setting the backgound color R,G,B for wher the HUD is places
		//g.setColor(new Color(255,255,255));
		//Colored backgrounds cause issues seeing the money and health values
		
		
		//creates a blank space for the screen
		g.fillRect(0, 0, getWidth(), getHeight());

		//drawing the room
		room.draw(g);

		//For all the enemies we create in array
		for(int i=0;i<enemys.length;i++){
			//if they are in game then draw it
			if(enemys[i].inGame){
				enemys[i].draw(g);
			}
		}

		//drawing the store
		store.draw(g);
		
		//if health of estuary is emptyy then game over
		if (health < 1) {
			g.fillRect(0, 0, sWidth, sHeight);
			Image img = new ImageIcon("resources/badE.png").getImage();
			g.drawImage(img,0,0,getWidth(), getHeight(),null);
		}
		
		//if the game is won
		if(isWin) {
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(new Color(0, 0, 0));
			g.setFont(new Font("Courier New", Font.BOLD, 14));
			if (level == maxLevel) {
				g.fillRect(0, 0, sWidth, sHeight);
				Image img = new ImageIcon("resources/win.png").getImage();
				g.drawImage(img,0,0,getWidth(), getHeight(),null);
			}
			else {
				g.fillRect(0, 0, sWidth, sHeight);
				Image img = new ImageIcon("resources/next.png").getImage();
				g.drawImage(img,0,0,getWidth(), getHeight(),null);
			}
		}
	}

	//TEST
	/**
	 * Check to see if we won the game
	 * If it is won icrease level and reset killcounter
	 */
	public static void hasWon() {
		if(killed >= killsToWin) {
			isWin = true;
			killed = 0;
			//Keep if you want money to follow to next level
			//level +=1;
			
			//To remove money
			if(coinage < 50){ coinage = 50;};
			if(coinage > 150){ coinage = 150;};
		}
		Store.unitCount = 0;
	}
	
	/**
	 * Defines most of the static attributes such as the room and save to make it easier to organize
	 */
	public void define() {
		room = new Room();
		//save doesnt have a constructor but it doesnt not need one because we are not
		//initializing attributes or anything
		save = new Save();
		store = new Store();

		//defines the grass tiles and water ground tiles
		//for (int i =0; i< grassTile.length; i++) {
			//imported the image in our resource folder
			//grassTile[i] = new ImageIcon("resources/grassTile.png").getImage();
			//creating a new image from image with iltered image, the we crop the image 
			//grassTile[i] = createImage(new FilteredImageSource(grassTile[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		//}
		grassTile[0] = new ImageIcon("resources/flowers.png").getImage();//sets the grass
		grassTile[1] = new ImageIcon("resources/waterTilePlusHighlights.png").getImage();//sets the water
		
		WaterArray[0] = createImage(new FilteredImageSource(grassTile[1].getSource(), new CropImageFilter(0, 0, 26, 26)));
		WaterArray[2] = createImage(new FilteredImageSource(grassTile[1].getSource(), new CropImageFilter(26, 0, 26, 26)));
		WaterArray[3] = createImage(new FilteredImageSource(grassTile[1].getSource(), new CropImageFilter(52, 0, 26, 26)));
		WaterArray[4] = createImage(new FilteredImageSource(grassTile[1].getSource(), new CropImageFilter(78, 0, 26, 26)));
		WaterArray[5] = createImage(new FilteredImageSource(grassTile[1].getSource(), new CropImageFilter(104, 0, 26, 26)));
		WaterArray[1] = createImage(new FilteredImageSource(grassTile[1].getSource(), new CropImageFilter(130, 0, 26, 26)));//sets water for animation
		
		//defines the airtiles, blank at first but will have (defenses, enemies and resources)
		for (int i =0; i< airTile.length; i++) {
			//imported the image in our resource folder
			airTile[i] = new ImageIcon("resources/airTile.png").getImage();
			//creating a new image from image with filtered image, the we crop the image 
			airTile[i] = createImage(new FilteredImageSource(airTile[i].getSource(), new CropImageFilter(0, 26*i, 26, 26)));
		}

		//define the garbage picker tile
		//MIGHT BE DELETED
		/*for(int i =0; i<defResUnit.length; i++){
			defResUnit[i] = new ImageIcon("resources/DefenseResource2.png").getImage();
			defResUnit[i] = createImage(new FilteredImageSource(defResUnit[i].getSource(), new CropImageFilter(0,52*i,52,52)));
		}*/
		
		//Define all resource and defense units
		defResUnit[0] = new ImageIcon("resources/GarbagePickerUpper.png").getImage();
		defResUnit[1] = new ImageIcon("resources/police.png").getImage();
		defResUnit[2] = new ImageIcon("resources/Spr_FRLG_Fisherman.png").getImage();
		defResUnit[3] = new ImageIcon("resources/Researcher.png").getImage();
		defResUnit[4] = new ImageIcon("resources/plant1.png").getImage();
		defResUnit[5] = new ImageIcon("resources/plant3.png").getImage();
		defResUnit[6] = new ImageIcon("resources/Floppy_Disk.png").getImage();
		defResUnit[9] = new ImageIcon("resources/Recycle_Bin_Empty.png").getImage();
		
		//set up image for enemies
		//imported the image in our resource folder
		for(int i = 3; i < 7; i++) {
			enemyUnit[i] = new ImageIcon("resources/GarbageDumperDeath.png").getImage();
			//creating a new image from image with iltered image, the we crop the image 
			enemyUnit[i] = createImage(new FilteredImageSource(enemyUnit[i].getSource(), new CropImageFilter(56*(i-3), 0, 56, 56)));
		}
		//enemyUnit[0] = new ImageIcon("resources/GarbageDumperUnit.png").getImage();//makes enemyUnit repersented with image
		enemyUnit[1] = new ImageIcon("resources/overfisher2.png").getImage();
		enemyUnit[2] = new ImageIcon("resources/mittencrab.png").getImage();
		
		// Loads up all the accessory things in HUD
		// Such as health and coins
		tileset_res[0] = new ImageIcon("resources/GarbagePickerUpper.png").getImage();
		
		// The image that will be printed next to the health (later to be replaced by bar)
		tileset_res[1] = new ImageIcon("resources/TestHeart.jpg").getImage();
		
		// the image that will be printed next the money
		tileset_res[2] = new ImageIcon("resources/moneyImage.jpg").getImage();
		
		// fill in the store with bull
		//WILL BE DELETED
		//unitToPlace[3] = new ImageIcon("resorces/GarbagePickerUpper.png").getImage();
		
		
		
		//loading the save file we created
		//WILL BE USED TO LOAD ADDITIONAL LEVELS
		save.loadSave(new File("save/mission" + level + ".waste"), false);
		
		

		//defines the new enemys
		for(int i=0;i<enemys.length;i++){
			//defines the mobs
			enemys[i] = new Enemy();
			//enemys[i].spawnEnemy(0);
			//enemys[i] = new ImageIcon("resources/GarbageDumberUnit.png").getImage();
		}

	}

	/**
	 * set re-spawnTime, which changes how often enEmies will spawn
	 */
	public int spawnTime = 3000, spawnFrame = 0;
	/**
	 *  Random enemyID
	 */
	public int randEnemy;
	/**
	 * random decimal from 0-1 and based on enemy prob it will create enemies with some porbability
	 */
	double randNum;
	
	/**
	 * Spawns the enemy, keeps incrementing spawnframe until it reaches spawn time
	 * then it will spawn a new enemy
	 */
	public void enemySpawner(){
		
		//if spawnframe reaches spawn time
		if(spawnFrame >= spawnTime){
			randNum = Math.random();
			//System.out.println(randNum);
			if(randNum > 0 && randNum < enemyProb[0]) {
				randEnemy = Value.EnemyGarbageDropper;
			}
			else if(randNum >= enemyProb[0] && randNum < (enemyProb[0] + enemyProb[1])) {
				randEnemy = Value.EnemyOverFisher;
			}
			else {
				randEnemy = Value.EnemyMittenCrab;
			}
			for(int i = 0;i< enemys.length;i++){
				//if enemy is not in the game
				if(!enemys[i].inGame){
					// enemy should be in the game so it spawns one
					enemys[i].spawnEnemy(randEnemy);
					//enemys[i].spawnEnemy(Value.EnemyGarbageDropper);
				break;
				}
			}
			//resets the spawntimer
			spawnFrame = 0;
		}
		//if not incrementing
		else{spawnFrame += 1;}
		

	}
	
	public void endGame(){
		
		isRunning = false;
		frame.endGame();
		
	}
	
	//needed because it is runnable, so the thread is need
	public void run() {
		
		//the game loop, controls how fast characters run etc
		//tells what will run at every millisec
		while(isRunning){
			//Thread only works when it is not the first time the program is running
			//if it is not first 
			if(!isFirst && health > 0 && !isWin){
				room.physic();
				//the spawner which spawns the enemies
				enemySpawner();
				//controls the physics for each mob
				for(int i = 0 ;i<enemys.length;i++){
					//if the mob is in game call the physics of the mob
					if(enemys[i].inGame){
						enemys[i].physic();
					}
				}
			}
			else {
				//This is so money does not follow you to next level
				if(isWin) {
					if(winFrame >= winTime) {
						if (level == maxLevel) {
							//closes whole game if max level has been reached
							//System.exit(0);
							endGame();
							Store.unitCount = 0;
						}
						else {
							level +=1;
							//redefine everything to reset enemy physics to new map
							define();
							//loading the save file we created
							save.loadSave(new File("save/mission" + level + ".waste"), false);
							isWin = false;
							
							Store.unitCount = 0;
						}
						
						winFrame = 0;
					}
					else {
						winFrame += 1;
					}
				}
			}
			
			//repaint the paintcomponent each time
			repaint();

			try {
				Thread.sleep(1);
			} 
			catch(Exception e){ 

			}
		}
	}
	
	/*public void saveScreen() {
		try
        {
			Enemy[] elist = this.enemys;
			//Screen state = this;
			FileOutputStream fos = new FileOutputStream("tempdata.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(elist);
            oos.close();
        }
        catch (Exception ex)
        {
            fail("Exception thrown during test: " + ex.toString());
        }
	}
	
	public void click(int mouseButton) {
		System.out.println("TESting");
		if(mouseButton == 1){
			for(int i = 0; i < store.button.length; i++){
				//if a box in the hud contains the mouse location once it is clicked
				if(store.button[i].contains(Screen.mse)){
					//if the button actually contains a unit
					if(store.buttonID[i] == Value.airAir) {
						saveScreen();
						System.out.println("Click registered");
					}
				}
			}
		}
	}*/

}
