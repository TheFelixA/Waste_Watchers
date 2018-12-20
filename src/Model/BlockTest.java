package Model;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import View.Frame;

/**
 * @author Felix
 *
 * Test Methods in the block class
 */
public class BlockTest {

	@Test
	public void testGetMoney() {

		Screen testScreen = new Screen(new Frame(false),100,100);
		Block testBlock = new Block(5, 5, 10, 10, 1, -1);
		
		Assert.assertEquals(100, testScreen.coinage);
		
		testBlock.getMoney(0);
		
		Assert.assertEquals(105, testScreen.coinage);
		
	}

}
