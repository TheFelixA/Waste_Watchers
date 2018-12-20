package Model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Felix
 * @since 2015/11/10
 * 
 * Is a list of rectangles that contain the defense units that players will buy
 * Also has health and money icon and all HUD related items
 */
public class Store {
	/**
	 * How many units are in the store
	 */
	public static int shopWidth = 8;

	/**
	 * has the pixel size of each individual button, i.e. 32x32 
	 */
	//public static int buttonSize = 52;
	public static int buttonSize = Screen.sWidth / 12;
	public static int buttonHeight = Screen.sHeight / 10;
	
	//public static int buttonSize = Screen.sWidth / 20;

	/**
	 * filling the store up with the units and trashcan to dump it at the end
	 * Not actual objects just their reference value
	 */
	public static int[] buttonID = {Value.trashCleaner, Value.epaAgent, Value.fisherMan, Value.researchUnit, Value.plant1,
			Value.plant2, Value.AirTrashCan, Value.save}; 
	
	/**
	 * distance between the bottom and the HUD 
	 */
	public static int itemIn = 4; 

	/**
	 * Size of the icons for the health and coins
	 */
	//public static int iconSize = 20;
	public static int iconSize = Screen.sWidth /40;
	/**
	 * Squares that represent different units or resources available to be bought
	 */
	public Rectangle[] button = new Rectangle[shopWidth];
	
	/**
	 * Rectangle for the heatlh
	 */
	public Rectangle buttonHealth;
	
	/**
	 * Rectangle for the coins
	 */
	public Rectangle buttonCoins;
	/**
	 * the distance of the buttons 
	 */
	public static int gapSpace = 2;
	//also known as cellSpace

	/**
	 * Distance away from game frame, seperates hud from game screen 
	 */
	//public static int distanceFromRoom = 29;
	public static int distanceFromRoom = Screen.sHeight / 30;
	//also known as awayFromRoom

	/**
	 * Distance between the health and coin icon 
	 */
	public static int iconSpace = 3;

	/**
	 * Distances added to center the health and coin number on HUD 
	 */
	public static int iconTextY = 15;

	/**
	 *  Changes the id to the unit that was clicked in the HUD
	 *  Is air on default
	 */
	public static int heldID = Value.airAir;

	/**
	 * changes value to the HUD array button index when mouse is clicked
	 * Used to determine the price of item clicked using ButtonPrice
	 */
	public static int realID = Value.airAir;
	/**
	 * Boolean for if the mouse house an item
	 */
	public boolean holdsItem = false;
	
	/**
	 * cost of each item in the hud 
	 */
	public static int[] buttonPrice = {20, 40, 50, 50, 5, 5, 0, 0};
	
	/**
	* THe max number of defense and res units that can appear on screen
	*/
	public int maxDefResUnits = 10;
	/**
	* COunts the number of units on screen
	*/
	public static int unitCount = 0;
	
	/**
	 * If the player has enough coins to buy unit
	 */
	public boolean isEnough = true;
	
	
	public boolean isSave = false;
	/**
	 * Constructor that will call define to implement everything
	 */
	public Store() {
		define();
	}

	/**
	 * will define everything in button array
	 * Creates buttons in the shop, and determines their location.
	 */
	public void define() {
		for (int i = 0; i < button.length; i++) {
			//each button will be a new rectangle at a position of size button size
			button[i] = new Rectangle((Screen.sWidth/2) - ((shopWidth * (buttonSize + gapSpace))/2) + ((buttonSize + gapSpace) * i), 
					(Screen.room.block[Screen.room.worldHeight -1][0].y) + Screen.room.blockHeight +  distanceFromRoom, buttonSize, buttonHeight);
		}

		//instantiate button health as a rectangle
		buttonHealth = new Rectangle(Screen.room.block[0][0].x - 1, button[0].y, iconSize, iconSize); 
		//places the money incon below the health icon
		buttonCoins =  new Rectangle(Screen.room.block[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize, iconSize); 
	}
	
	/**
	 * checks for mouse clicks to place units
	 * 
	 * @param mouseButton			the mouse button (left, right or wheel)
	 */
	public void click(int mouseButton){
		//System.out.println(Screen.enemyProb[0] + " " + Screen.enemyProb[1] + " " + Screen.enemyProb[2]);
		//the left mouse button is 1
		//If a mouse is clicked then it can be clicking something in HUD or placing item on screen
		if(mouseButton == 1){
			for(int i = 0; i < button.length; i++){
				//if a box in the hud contains the mouse location once it is clicked
				if(button[i].contains(Screen.mse)){
					//if the button actually contains a unit
					if(buttonID[i] != Value.save) {
						//if the trashcan is clicked remove the previously clicked item
						if(buttonID[i] == Value.AirTrashCan){
							holdsItem = false;
						}
						else{
							//Gives the item that the mouse is holding
							heldID = buttonID[i];
							//changes to the index of the item
							realID = i;
							holdsItem = true;
						}
					}
					else {
						 if(!isSave) {
							 try
								{
									//Screen state = this;
									FileOutputStream fos = new FileOutputStream("tempdata.ser");
						            ObjectOutputStream oos = new ObjectOutputStream(fos);
						            oos.writeObject(Screen.enemys);
						            oos.close();
						            
						            Save.writeSave();
						            System.out.print("Working");
						        }
						        catch (Exception ex)
						        {
						            fail("Exception thrown during test: " + ex.toString());
						        }
							 isSave = true;
						 }
						 else {
							 try
						        {
						            FileInputStream fis = new FileInputStream("tempdata.ser");
						            ObjectInputStream ois = new ObjectInputStream(fis);
						            Enemy[] elist = (Enemy[]) ois.readObject();
						            ois.close();
						            
						            Save.loadSave(new File("dump.txt"), true);
						            
						            Screen.enemys = elist; 
						       
						            // Clean up the file
						            //new File("tempdata.ser").delete();
						        }
						        catch (Exception ex)
						        {
						            fail("Exception thrown during test: " + ex.toString());
						        }
						 }
						//Screen.saveScreen();
						
						
					}
				}
			}//problly needs to change to for each loop
			//If one of the units is clicked in HUD
			if(holdsItem) {
				//if the amount of coins we have is greater than the price of the unit
				if(Screen.coinage >= buttonPrice[realID] && unitCount < maxDefResUnits) {
					isEnough = true;
					for(int y = 0; y<Screen.room.block.length; y++) {
						for(int x = 0; x<Screen.room.block[0].length; x++) {
							//if the mouse clicks a location in the game
							if (Screen.room.block[y][x].contains(Screen.mse)) {
								//If the location clicked is not the water and it doesnt contain a defense already
								if(Screen.room.block[y][x].groundID != Value.groundWater && 
										Screen.room.block[y][x].airID == Value.airAir) {
									//place the unit on the block location
									Screen.room.block[y][x].airID = heldID;
									//System.out.println(Screen.room.block[y][x].airID);
									//remove cost from price
									Screen.coinage -= buttonPrice[realID];
									//If the plants are on screen reduce the damage enemy gives to the estuary
									if(heldID == Value.plant1) {
										for(int i = 0; i < Value.enemyDamage.length; i++) {
											Value.enemyDamage[i] *=.93;
											//System.out.println("Damage is " + Value.enemyDamage[i]);
										}
									}
									else if(heldID == Value.plant2) {
										for(int i = 0; i < 4; i++) {
											buttonPrice[i] *= .97;
										}
									}
									//increases the count of number of units on screen
									unitCount++;
								}
							}
						}
						
					}
				}
				else {
					isEnough = false;
				}
			}
		}
	}

	/**
	 * Draws the store
	 * 
	 * @param g			The graphics to draw the store on
	 */
	public void draw(Graphics g) {

		for (int i = 0; i < button.length; i++) {
			//Makes the rectangle light up when the mouse hovers it
			//If the buttton contains the mouse
			if(button[i].contains(Screen.mse)){
				//fill the rectangle to have a solid full rectangle on screen
				//Changes the rectangle it to a new transparent color
				g.setColor(new Color(255, 255, 255, 100));
				g.fillRect(button[i].x, button[i].y, button[i].width, button[i].height);
			}
			//Should draw transparent squares
			//g.drawImage(Screen.tileset_res[0], button[i].x, button[0].y, button[i].width, button[i].height, null);
			//g.drawRect(button[i].x, button[0].y, button[i].width, button[i].height);
			//g.drawImage(Screen.defResUnit[0], button[i].x + itemIn, 
				//	button[i].y+ itemIn, button[i].width - (itemIn*2),button[i].height - (itemIn*2), null); //not sure the namings
		
			//Fills the Hud if they are not air values with appropiate units
			if(buttonID[i]!= Value.airAir) {
				//drawing the garbage picker into the tile
				g.drawImage(Screen.defResUnit[buttonID[i]], button[i].x + itemIn, 
						button[i].y+ itemIn, button[i].width - (itemIn*2),button[i].height - (itemIn*2), null); //not sure the namings
			}
			
				
			if(buttonPrice[i] > 0){
				g.setColor(new Color(255, 255, 255));
				g.setFont(new Font("Courier New", Font.BOLD, 20));
				g.drawString("$" + buttonPrice[i], button[i].x + itemIn, button[i].y + itemIn);

			}
		}
		
		
		//the health icon
		g.drawImage(Screen.tileset_res[1], buttonHealth.x, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);
		//the money icon that is imported
		g.drawImage(Screen.tileset_res[2], buttonCoins.x, buttonCoins.y, buttonCoins.width, buttonCoins.height, null);

		//Changes the font of the number for health and coins
		g.setFont(new Font("Courier New", Font.BOLD, 20));
		//Color of the health and coin numbers
		g.setColor(new Color(255,255,255));
		if(!isEnough) {
			g.setColor(new Color(255,0,0));
			g.drawString("" + Screen.coinage, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);
		}
		else {
			g.drawString("" + Screen.coinage, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);
		}
		//Displays the amount of health and coins
		g.drawString("" + Screen.health, buttonHealth.x + buttonHealth.width + iconSpace, buttonHealth.y + iconTextY);
		//g.drawString("" + Screen.coinage, buttonCoins.x + buttonCoins.width + iconSpace, buttonCoins.y + iconTextY);
	
		//Remove for better touch screen experience
		//If the mouse is holding a unit then draw it 
		if(holdsItem){
			g.drawImage(Screen.defResUnit[heldID], Screen.mse.x - ((button[0].width - (itemIn*2))/2)+ itemIn, 
					Screen.mse.y -((button[0].width - (itemIn*2))/2)+ itemIn  , button[0].width - (itemIn*2), button[0].height - (itemIn*2), null);
		}
	}
}

