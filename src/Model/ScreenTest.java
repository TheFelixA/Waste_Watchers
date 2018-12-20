package Model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import View.Frame;

/**
 * @author Felix
 *
 * Test the Screen methods
 */
public class ScreenTest {

	/**
	 * Test for has won method 
	 */
	@Test
	public void testHasWon() {
		Frame testFrame = new Frame(true);
		Screen testScreen = new Screen(testFrame, 40, 100);
		
		Assert.assertFalse(testScreen.isWin);
		
	
		testScreen.killsToWin = 10;
		testScreen.killed = 9;
		
		testScreen.hasWon();
		
		Assert.assertFalse(testScreen.isWin);
		
		testScreen.killed = 10;
		testScreen.coinage = 200;
		testScreen.hasWon();
		
		Assert.assertTrue(testScreen.isWin);
		Assert.assertEquals(0, testScreen.killed);
		Assert.assertEquals(150, testScreen.coinage);
	}

	/**
	 * Enemy Spawner test 
	 */
	//@Test
	/*public void testEnemySpawner(){
		Frame testFrame = new Frame(true);
		Screen testScreen = new Screen(testFrame, 40, 100);
		
		
		
		
		double enemyProb[] = {.6, .3, .1};
		boolean garbageCheck = false;
		boolean overFisherCheck = false;
		boolean mittenCrabCheck = false;
		
		
		while(garbageCheck != true || overFisherCheck != true || mittenCrabCheck != true){
		
		
		testScreen.spawnFrame = testScreen.spawnTime;
		testScreen.enemySpawner();
		
		if(testScreen.randNum < enemyProb[0]){
			Assert.assertEquals(Value.EnemyGarbageDropper, testScreen.randEnemy);
			garbageCheck = true;
		}
			
		else if(testScreen.randNum >= enemyProb[0] && testScreen.randNum < enemyProb[0] + enemyProb[1]) {
			Assert.assertEquals(Value.EnemyOverFisher, testScreen.randEnemy );
			overFisherCheck = true;
		}
		
		else {
			Assert.assertEquals(Value.EnemyMittenCrab, testScreen.randEnemy);
			mittenCrabCheck = true;
			}
		
		}
	}*/
}
