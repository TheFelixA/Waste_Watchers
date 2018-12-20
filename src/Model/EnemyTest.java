package Model;
import java.awt.*;
import java.io.*;
import static org.junit.Assert.*;

import org.junit.*;

import org.junit.Test;

import View.Frame;
//import Model.Screen;

/**
 * @author Felix
 * @since 2015-11-29
 * 
 * Testing the methods in the enemy class
 */
public class EnemyTest {
	
	@Test
	public void testMovementRandomizer() {
	//	fail("Not yet implemented");
	}

	/**
	 * Testing that  delete enemy changes boolean to false and dir to right/0, 
	 * movement to 0 to reset it back on map, and the increases the games money
	 * Everything else stays the same
	 */
	@Test
	public void testDeleteEnemy() {
		Frame gameWindow = new Frame(false);	
		Screen scrn = new Screen(gameWindow, 150, 100);
		scrn.define();
		
		Enemy dumper = new Enemy(0, 100, true, 0, 20);
		Enemy fisher = new Enemy(1, 0, false, 0, 0);
		Enemy crab = new Enemy(2, 0, false, 2, 40);
		
		scrn.enemys[0] = dumper;
		scrn.enemys[1] = fisher;
		scrn.enemys[2] = crab;
		
		dumper.deleteEnemy();
		assertFalse(dumper.inGame);
		assertEquals(2, dumper.direction);
		assertEquals(0, dumper.enemyMove);
		assertEquals(155, scrn.coinage);
		
		fisher.deleteEnemy();
		assertFalse(fisher.inGame);
		assertEquals(2, fisher.direction);
		assertEquals(0, fisher.enemyMove);
		assertEquals(165, scrn.coinage);
		
		crab.deleteEnemy();
		//should fail
		//assertTrue(crab.inGame);
		assertFalse(crab.inGame);
		assertEquals(2, crab.direction);
		assertEquals(0, crab.enemyMove);
		//should fail
		//assertEquals(165, scrn.coinage);
		assertEquals(170, scrn.coinage);
		
	}

	/**
	 * Test whether it returns false when enemy is in game
	 */
	@Test
	public void testIsDead() {
		Enemy dumper = new Enemy(0, 100, true, 0, 20);
		Enemy fisher = new Enemy(1, 0, false, 0, 0);
		Enemy crab = new Enemy(2, 0, false, 2, 40);
		
		assertFalse(dumper.isDead());
		assertTrue(fisher.isDead());
		assertTrue(crab.isDead());
	}
	
	@Test
	public void testEnemyLoseHealth() {
		Enemy testFoe = new Enemy();
		testFoe.health = 100;
		testFoe.loseHealth(50);
		Assert.assertEquals(testFoe.health, 50);
		
		testFoe.enemyID = 0;
		Screen testScreen = new Screen(new Frame(false),100,100);
		testFoe.loseHealth();
		Assert.assertEquals(Screen.health, 90);
		testScreen.endGame();
	}
	
	@Test
	public void testEnemyCheckDeath() {
		Enemy testFoe = new Enemy();
		testFoe.health = 100;
		testFoe.enemyID = 0;
		
		Screen testScreen = new Screen(new Frame(false),100,100);
		testScreen.define();
		
		testFoe.loseHealth(101);
		Assert.assertEquals(Screen.killed, 1);
		Assert.assertTrue(!testFoe.inGame);

	}

}
