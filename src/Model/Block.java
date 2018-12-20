package Model;
import java.awt.*;


/**
 * @author Felix
 * @since 2015/11/10
 * 
 * Extends rectangle to hold the x and y values, which will be used in methods
 */
/**
 * @author Felix
 *
 */
public class Block extends Rectangle{
	
	
	/**
	 * Differentiates the kind of block it is, whether grass block or water block
	 */
	public int groundID;
	
	/**
	 * Determines if there is a defense in the location 
	 */
	public int airID;
	
	/**
	 * A rectangle that essentially is the radius of the attack of the defensive unit
	 */
	public Rectangle towerSquare;
	
	/**
	 * Size of the tower square radius
	 */
	public int towerSquareSize = Screen.sWidth/9;
	
	/**
	 * Each time a dumper passes it will check all enemies inside radius and it will shoot one
	 * of them. It is the exact index of the enemy that was shot
	 */
	public int shotDumper = -1;
	
	/**
	 * Tells if the the dumper is currently attacking an enemy 
	 */
	public boolean isShooting = false;
	
    /**
     * Time for all health of enemy to disappear
     */
    public int loseTime = 100;
    
    /**
     * Counter for lose time
     */
    public int loseFrame = 0;
	
    
    /**
     * Time it takes for researcher and fisherman to increase health/money
     */
    public int resourceTime = 1500;
    /**
     * The ticks for resourcetime 
     */
    public int resourceFrame = 0;
     
    /**
     * How many times researcher will heal estuary before it disappears
     */
    public int researchTime = 10;
    /**
     * Number of times fisherman generates money before it disappears
     */
    public int fisherTime = 16;
    /**
     * How many times the researcher hs generated health
     */
    public int researchCount = 0;
    /**
     * How many times the fisherman has generated money
     */
    public int fisherCount = 0;
    
    /**
     * sets the speed of the water animation(Higher is slower)
     */
    public int animationSpeed = 20;
	
	/**
	 * sets which picture that gets selected for the water animation
	 */
	public int animationFrame = 0; 

	/**
	 * used in conjunciton with animationFrame
	 */
	public int animationTime = 60 * animationSpeed;

	/**
	 * Used in the watertile animation, Ig true continue to change to next watertile. Else go to
	 * previous water tile
	 */
	public boolean increment = true;
	
	/**
	 * Constructor for the block. sets t	 * 
	 * 
	 * @param x				horizontal position 
	 * @param y				vertical position
	 * @param width			width of the block
	 * @param height		height of the block
	 * @param groundID		determines if the block is water or ground
	 * @param airID			determines if the location is occupied by defense, enemy or neither
	 */
	public Block(int x, int y, int width, int height, int groundID, int airID){
		//the bounds with x, y the upper left corner of rectangle
		//NOT THE X,Y FROM RECTANGLE SUPERCLASS
		setBounds(x, y, width, height);
		//creates a rectangle to denote the size of the radius of the defense
		towerSquare = new Rectangle(x - (towerSquareSize/2), y - (towerSquareSize/2), width + (towerSquareSize), height + (towerSquareSize));
		//sets the ground and air id
		this.groundID = groundID;
		this.airID = airID;

	}

	/**
	 * draws the block that is constructed on the screen
	 * 
	 * @param g			the graphics to draw on
	 */
	public void draw(Graphics g){

		if(groundID == Value.groundWater){
			Screen.grassTile[1] = Screen.WaterArray[0];
			if(increment == true){
				if(animationFrame <= animationTime){
					animationFrame+=1;
					if(animationFrame >= 50*animationSpeed){
						Screen.grassTile[1] = Screen.WaterArray[5];
						g.drawImage(Screen.grassTile[1], x, y, width, height, null);
					}
					else if(animationFrame >= 40*animationSpeed){
						Screen.grassTile[1] = Screen.WaterArray[4];
						g.drawImage(Screen.grassTile[1], x, y, width, height, null);
					}
					else if(animationFrame >= 30*animationSpeed){
						Screen.grassTile[1] = Screen.WaterArray[3];
						g.drawImage(Screen.grassTile[1], x, y, width, height, null);
					}
					else if(animationFrame >= 20*animationSpeed){
						Screen.grassTile[1] = Screen.WaterArray[2];
						g.drawImage(Screen.grassTile[1], x, y, width, height, null);
					}
					else if(animationFrame >= 10*animationSpeed){
						Screen.grassTile[1] = Screen.WaterArray[1];
						g.drawImage(Screen.grassTile[1], x, y, width, height, null);
					}
					else if(animationFrame >= 1*animationSpeed){
						Screen.grassTile[1] = Screen.WaterArray[0];
						g.drawImage(Screen.grassTile[1], x, y, width, height, null);
					}
					if(animationFrame >60*animationSpeed){increment = false;}
				}
			}
			else{
				animationFrame-=1;
				if(animationFrame >= 50*animationSpeed){
					Screen.grassTile[1] = Screen.WaterArray[5];
					g.drawImage(Screen.grassTile[1], x, y, width, height, null);
				}
				else if(animationFrame >= 40*animationSpeed){
					Screen.grassTile[1] = Screen.WaterArray[4];
					g.drawImage(Screen.grassTile[1], x, y, width, height, null);
				}
				else if(animationFrame >= 30*animationSpeed){
					Screen.grassTile[1] = Screen.WaterArray[3];
					g.drawImage(Screen.grassTile[1], x, y, width, height, null);
				}
				else if(animationFrame >= 20*animationSpeed){
					Screen.grassTile[1] = Screen.WaterArray[2];
					g.drawImage(Screen.grassTile[1], x, y, width, height, null);
				}
				else if(animationFrame >= 10*animationSpeed){
					Screen.grassTile[1] = Screen.WaterArray[1];
					g.drawImage(Screen.grassTile[1], x, y, width, height, null);
				}
				else if(animationFrame >= 1*animationSpeed){
					Screen.grassTile[1] = Screen.WaterArray[0];
					g.drawImage(Screen.grassTile[1], x, y, width, height, null);
				}
			}
			if(animationFrame<=0){increment = true;}

		}

		//drawing the respective image for the ground
		g.drawImage(Screen.grassTile[groundID], x, y, width, height, null);

		//if the airid is not an actual air value will draw this
		//This draws the defenses on screen
		if((airID != Value.airAir) && (airID !=Value.Finish)) {
			//g.drawImage(Screen.tileset_res[airID], x, y, width, height, null);
			g.drawImage(Screen.defResUnit[airID], x, y, width, height, null);

			//draws the rectangle radius of the the cleaner for testing
			/*if(airID == Value.trashCleaner) {
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}*/
		}
		
	}
	
	/**
	 * Controls the garbage cleaner interaction with the garbage dumpers
	 * If the enemy is in the defenses range it attacks
	 * Controls the resource unit regeneration
	 */
	public void physic() {
		//If the the cleaner is not attacking anyone and an enemy comes near
		if(shotDumper != -1 && towerSquare.intersects(Screen.enemys[shotDumper])) {
			isShooting = true;
		}
		else {
			isShooting = false;
		}
		
		//If the defense is not attacking anyone
		if(!isShooting) {
			//if the block is a cleaner
			if(airID == Value.trashCleaner) {
				//checks enemy array
				for (int i = 0; i< Screen.enemys.length; i++) {
					//if the enemy is a garbage dumper
					if(Screen.enemys[i].enemyID == Value.EnemyGarbageDropper) {
						//Check to see if the enemy is in the game
						if(Screen.enemys[i].inGame) {
							//if the enemy is in game then check to see if it intersects the defense on the blocks location
							if(towerSquare.intersects(Screen.enemys[i])) {
								//if it does, make the cleaner attack the dumper at that spot
								isShooting = true;
								shotDumper = i;
							}
						}
					}					
				}
			}
			
			//If the block is an agents then it looks for droppers and overfishers and attacks them
			if(airID == Value.epaAgent) {
				//checks enemy array
				for (int i = 0; i< Screen.enemys.length; i++) {
					//if the enemy is a garbage dumper or overfisher
					if(Screen.enemys[i].enemyID == Value.EnemyGarbageDropper || Screen.enemys[i].enemyID == Value.EnemyOverFisher) {
						//Check to see if the enemy is in the game
						if(Screen.enemys[i].inGame) {
							//if the enemy is in game then check to see if it intersects the defense on the blocks location
							if(towerSquare.intersects(Screen.enemys[i])) {
								//if it does, make the agent attack the enemy at that spot
								isShooting = true;
								shotDumper = i;
							}
						}
					}					
				}
			}
			
		}
		
		//to change health
		if (isShooting) {
			//if the frame is greater than the time make the enemy loose health
			if(loseFrame >= loseTime) {
				//decrease dumpers health
				Screen.enemys[shotDumper].loseHealth(Screen.sWidth/400);
				loseFrame = 0;;
			}
			else {
				loseFrame +=1;
			}
			
			//After enemy is defeated make the defense available to attack a new unit
			//ALso checks to see if player has reached the win total
			if(Screen.enemys[shotDumper].isDead()) {	
				//Defense is now avaialble to find new target
				isShooting = false;
				shotDumper = -1;
				
				//Screen.killed +=1;
				//After checking if its dead we check if player has won
				Screen.hasWon();
			}
		}
		
		//If the block is occupied by a research unit then regenerate health
		if (airID == Value.researchUnit && Screen.health < Screen.healthCap) {
			if (resourceFrame % resourceTime  == 0 && researchCount < researchTime) {
				Screen.health +=5;
				resourceFrame++;
				researchCount++;
			}
			if(researchCount >= researchTime) {
				airID = Value.airAir;
				researchCount = 0;
				Store.unitCount--;
			}
			resourceFrame++;
		}
		
		//if the block is occupied by fisherman regenrate health
		if (airID == Value.fisherMan && Screen.coinage < Screen.coinageCap) {
			if (resourceFrame % resourceTime == 0 && fisherCount < fisherTime) {
				Screen.coinage += 5;
				resourceFrame++;
				fisherCount++;
			}
			if(fisherCount >= fisherTime) {
				airID = Value.airAir;
				fisherCount = 0;
				Store.unitCount--;
			}
			resourceFrame++;
		}
	}
	
	//TEST
	/**
	 * Determines how much money should be added for enemies death
	 * 
	 * @param enemyID			The unique id of the enemy
	 */
	public void getMoney(int enemyID) {
		Screen.coinage += Value.deathReward[enemyID];
	}
	
	
	/**
	 * Draws the defense and enemy unit interaction. Is a line fore now
	 * 
	 * @param g
	 */
	public void fight(Graphics g) {
		//if debug is true will draw the rectangle radius of defense unit
		if(Screen.isDebug){
			if(airID == Value.trashCleaner) {
				g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
			}
		}
		
		
		//if the cleaner is shooting enemy draw a laser
		/*if(isShooting) {
			g.setColor(new Color(255,255,0));
			g.drawLine(x + (width/2), y + (height/2), Screen.enemys[shotDumper].x + (Screen.enemys[shotDumper].width/2),
					Screen.enemys[shotDumper].y + (Screen.enemys[shotDumper].height/2));
		}*/
	}
}
