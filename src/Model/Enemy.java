package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;


/**
 * @author Felix, jollyswagman
 * @since 2015/11/17
 * 
 * The enemy units that uses all the rectangle methods
 * Different enmies will have different speed
 */
public class Enemy extends Rectangle{

	/**
	 * The xcoordinate and the ycorrdinate of the enemy
	 */
	public int xC, yC;

	/**
	 * Health of the enemy. Will be the width of the enemy
	 */
	public int health;

	/**
	 * Space of health from enemy 
	 */
	public int healthSpace = 3;

	/**
	 * Height of the health bar
	 */
	public int healthHeight =6;
	/**
	 * Size of the enemy, same as the block size
	 */
	//public int enemySize = 52;
	public int enemySize = (int)(Screen.sWidth / 12);
	public int enemyHeight = (int)(Screen.sHeight / 9.5);
	/**
	 * the enemy id for this specific enemy
	 */
	public int enemyID;
	//public int enemyID = Value.EnemyGarbageDropper;
	//public int enemyID = Value.EnemyMittenCrab;
	//public int enemyID = Value.EnemyOverFisher;
	
	/**
	 * If the enemy is in the game
	 */
	public boolean inGame = false;

	/**
	 * also known as mobWalk
	 * Will increment each time the enemy moves. Used to check if enemy is transitioning to a new block
	 */
	public int enemyMove = 0;

	/**
	 * direction that corresponds to int
	 */
	public int up = 0, down = 1, right = 2, left = 3;

	// also known as upward, downward, and right
	/**
	 * Default walking direction
	 */
	public int direction = right;

	/**
	 * Tells if the enemy has to go up
	 */
	public boolean hasUP = false;
	/**
	 * If it has to go down
	 */
	public boolean hasDOWN = false;
	/**
	 * If it has to go left
	 */
	public boolean hasLEFT = false; 
	/**
	 * If it has to go right
	 */
	public boolean hasRIGHT = false;
	
	
	/**
	 * This number will be used to run the random movements
	 */
	public long randomMoveCount = 0;

	/**
	 * sets speed of enemies
	 * also known as walkFrame and WalkSpeed
	 */
	public int rowFrame = 0;
	
	/**
	 * Speed it takes 
	 */
	public int rowSpeed;


	/**
	 * Constructor
	 */
	public Enemy(){

	}
	
	public Enemy(int id, int health, boolean inGame, int dir, int move){
		enemyID = id;
		this.health = health;
		this.inGame = inGame;
		this.direction = dir;
		this.enemyMove = move;
	}

	/**
	 * Controls how the enemy is spawning
	 * 
	 * @param enemyID			the ID the enemy should have
	 */
	public void spawnEnemy(int enemyID){
		//goes through all the blocks verticle blocks in first column
		for(int y = 0; y<Screen.room.block.length;y++){
			//Checks to see if those block contains the water
			if(Screen.room.block[y][0].groundID == Value.groundWater){
				//sets the bound of the rectangle
				setBounds(Screen.room.block[y][0].x ,Screen.room.block[y][0].y,enemySize,enemyHeight);
				//the xc and yc should always be y at the start
				xC=0;
				yC=y;

			}//Spawns the enemy units
		}
		//setBounds(10,10,100,100);

		//
		this.enemyID = enemyID;
		//health is now enemy size
		this.health = enemySize;

		//when an enemy is spawned the ingame should be true
		inGame = true;

		//checking to see if enemy are spawning
		//System.out.print("better work");
		
		//serial();
	}


	/**
	 * Physic of the game like walking and movement
	 * Basically enemy increments its xcoor until it is out of grid box. then once it is out it determins how to move
	 * based on next box
	 */
	public void physic(){
		double randomInt = Math.random() * 100;
		if(randomInt < 50){
			physic1();
		}
		else 
			physic2();


	}

	public void physic1(){

		if(enemyID == Value.EnemyGarbageDropper){
			rowSpeed = 15;
		}
		if(enemyID == Value.EnemyOverFisher){
			rowSpeed = 20;
		}
		if(enemyID == Value.EnemyMittenCrab){
			rowSpeed = 10;
		}
		
		if(rowFrame >= rowSpeed){
			//if direction is right it goes right


			if(direction == right){
				x += 1;
			}

			else if(direction == up){
				y -= 1;
			}

			//walks up



			//should never walk left
			else if(direction == left){
				x -= 1;
			}

			else if(direction == down){
				y += 1;
			}
			//increments the move
			enemyMove +=1;

			//checks to see if mob has walked to the block size
			if(((direction == left || direction == right) && enemyMove == Screen.room.blockSize)
					||(direction == up || direction == down) && enemyMove == Screen.room.blockHeight){
				//incrementing or decrmenting the coordinates
				if(direction == right){
					xC += 1;
					hasRIGHT = true;
				}
				else if(direction == up){
					yC -= 1;
					hasUP = true;
				}
				else if (direction == left){
					xC -= 1;
					hasLEFT = true;
				}

				else if(direction == down){
					yC+=1;
					hasDOWN = true;
				}


				//If the enemy was able to move up



				if(!hasLEFT){
					try{
						if(Screen.room.block[yC][xC+1].groundID == Value.groundWater){
							direction = right;
						}
					}catch(Exception e){};
				}


				if(!hasDOWN){
					try{
						if(Screen.room.block[yC-1][xC].groundID == Value.groundWater){
							direction = up;
						}
					}catch(Exception e){};
				}

				/*
				if(!hasUP){
					try{
						if(Screen.room.block[yC+1][xC].groundID == Value.groundWater){
							direction = down;
						}
					}catch(Exception e){};
				}

				if(!hasRIGHT){
					try{
						if(Screen.room.block[yC][xC-1].groundID == Value.groundWater){
							direction = left;
						}
					}catch(Exception e){};
				}

				 */



				//checks to see if enemy has reached the last block
				if(Screen.room.block[yC][xC].airID == Value.Finish){
					//if it does then reduce the health
					deleteEnemy();
					loseHealth();
				}

				//resets all the directions
				hasUP = false;
				hasDOWN = false;
				hasRIGHT = false;
				hasLEFT = false;
				enemyMove = 0;

			}

			rowFrame = 0;//resets all of the motions 
		}
		else{
			//increment the walk frame
			rowFrame +=1;
		}
	}



	public void physic2(){
		
		if(enemyID == Value.EnemyGarbageDropper){
			rowSpeed = 15;
		}
		if(enemyID == Value.EnemyOverFisher){
			rowSpeed = 20;
		}
		if(enemyID == Value.EnemyMittenCrab){
			rowSpeed = 10;
		}
		
		if(rowFrame >= rowSpeed){
			//if direction is right it goes right




			if(direction == right){
				x += 1;
			}

			else if(direction == up){
				y -= 1;
			}

			//walks up



			//should never walk left
			else if(direction == left){
				x -= 1;
			}

			else if(direction == down){
				y += 1;
			}
			//increments the move
			enemyMove +=1;

			//checks to see if mob has walked to the block size
			if(((direction == left || direction == right) && enemyMove == Screen.room.blockSize)
					||(direction == up || direction == down) && enemyMove == Screen.room.blockHeight){
				//incrementing or decrementing the coordinates
				if(direction == right){
					xC += 1;
					hasRIGHT = true;
				}
				else if(direction == up){
					yC -= 1;
					hasUP = true;
				}
				else if (direction == left){
					xC -= 1;
					hasLEFT = true;
				}

				else if(direction == down){
					yC+=1;
					hasDOWN = true;
				}


				//If the enemy was able to move up



				if(!hasDOWN){
					try{
						if(Screen.room.block[yC-1][xC].groundID == Value.groundWater){
							direction = up;
						}
					}catch(Exception e){};
				}


				if(!hasLEFT){
					try{
						if(Screen.room.block[yC][xC+1].groundID == Value.groundWater){
							direction = right;
						}
					}catch(Exception e){};
				}


				/*
				if(!hasUP){
					try{
						if(Screen.room.block[yC+1][xC].groundID == Value.groundWater){
							direction = down;
						}
					}catch(Exception e){};
				}

				if(!hasRIGHT){
					try{
						if(Screen.room.block[yC][xC-1].groundID == Value.groundWater){
							direction = left;
						}
					}catch(Exception e){};
				}

				 */



				//checks to see if enemy has reached the last block
				if(Screen.room.block[yC][xC].airID == Value.Finish){
					//if it does then reduce the health
					deleteEnemy();
					loseHealth();
				}

				//resets all the directions
				hasUP = false;
				hasDOWN = false;
				hasRIGHT = false;
				hasLEFT = false;
				enemyMove = 0;

			}

			rowFrame = 0;//resets all of the motions 
		}
		else{
			//increment the walk frame
			rowFrame +=1;
		}
	}

	//TEST
	/**
	 * removes the enemy from the game and get the amount of money from
	 *only 1 attack
	 */
	public void deleteEnemy(){
		inGame = false;
		//might not be needed
		direction = right;
		enemyMove = 0;

		//Test to see if it counts kills once
		//Screen.killed +=1;
		Screen.room.block[0][0].getMoney(enemyID);
	}

	//TEST
	/**
	 * the amount of health to be decreased when it reaches estuary
	 */
	public void loseHealth(){
		Screen.health -= Value.enemyDamage[enemyID];
	}

	//TEST
	/**
	 * Method the deals with the enemy's health loss
	 * 
	 * @param amo			reduces the health of the enmey by this amount
	 */
	public void loseHealth(int amo) {
		health -= amo;

		checkDeath();
	}

	//TEST
	/**
	 * Checks if the enemy is dead, if so it removes it 
	 */
	public void checkDeath() {
		//if the health is 0
		if(health <= 0) {
			deleteEnemy();
			Screen.killed +=1;
		}
	}

	//TEST
	/**
	 * Tells if the enemy is in game
	 * 
	 * @return		true if the enemy is not in game
	 * 				false otherwise
	 */
	public boolean isDead() {
		if(inGame) {
			return false; 
		}
		else {
			return true;
		}
	}

	/**
	 * Adds the ability to get rid of mitten crabs through clicks
	 * @param mousebutton				The screens mouse
	 */
	public void click(int mousebutton){
		if((Screen.mse.getX() - this.x) < enemySize && ((Screen.mse.getX() - this.x) > 0)){
			if((Screen.mse.getY() - this.y) < enemyHeight && ((Screen.mse.getY() - this.y) > 0)){
				if(this.enemyID == Value.EnemyMittenCrab){
					this.loseHealth((int)(enemySize/5)+1);
				}
			}
		}
	}
	
	
	/**
	 * Draws the enemy in the game floating on top the grid
	 * 
	 * @param g			the graphic to draw the image on
	 */
	/**
	 * @param g
	 */
	public void draw(Graphics g){
		//if the enemy is in the game will be drawn on screen
		
		if (enemyID == Value.EnemyGarbageDropper) {
			if (health > .76 * enemySize && health <= enemySize){
				g.drawImage(Screen.enemyUnit[3],x,y,width,height,null);
			}
			else if(health > .51 * enemySize && health <= .76 *enemySize){
				g.drawImage(Screen.enemyUnit[4],x,y,width,height,null);
			}
			else if(health > .30 * enemySize && health <= .51 *enemySize) {
				g.drawImage(Screen.enemyUnit[5],x,y,width,height,null);
			}
			else {
				g.drawImage(Screen.enemyUnit[6],x,y,width,height,null);
			}
		}
		else {
			g.drawImage(Screen.enemyUnit[enemyID],x,y,width,height,null);
		}
		
		
		//The health bar
		g.setColor(new Color(100, 50, 50));
		g.fillRect(x, y - (healthSpace = healthHeight), width, healthHeight);

		g.setColor(new Color(50, 180, 50));
		g.fillRect(x, y - (healthSpace = healthHeight), health, healthHeight);

		//shading outline around health bar
		g.setColor(new Color(0,0,0));
		g.drawRect(x, y - (healthSpace = healthHeight), health - 1, healthHeight - 1);
	}

	/*public void serial() {
		try
        {
			Enemy dumper = new Enemy(0, 100, true, 0, 20);
			FileOutputStream fos = new FileOutputStream("tempdata.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(dumper);
            oos.close();
        }
        catch (Exception ex)
        {
            fail("Exception thrown during test: " + ex.toString());
        }
        
        try
        {
            FileInputStream fis = new FileInputStream("tempdata.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Enemy dumper2 = (Enemy) ois.readObject();
            ois.close();

            // Clean up the file
            //new File("tempdata.ser").delete();
        }
        catch (Exception ex)
        {
            fail("Exception thrown during test: " + ex.toString());
        }
	}*/
	
}


