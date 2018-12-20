package Model;
import java.awt.*;

/**
 * @author Felix
 * @since 2015/11/10
 * 
 * Loads up all the different levels and helps with saving and loading
 * all the blocks on the ground. 
 */
public class Room {
	/**
	 * Length of the first dim of block array
	 * Screen will show this number of columns of block
	 */
	public int worldWidth = 12;
	/**
	 * The height of second dimension of the block array
	 * Screen will show this number of rows of block
	 */
	public int worldHeight = 8;
	/**
	 * How big a block will be on screen, i.e 32x32
	 */
	//public int blockSize = 52;
	public int blockSize = (int)(Screen.sWidth / 12);
	public int blockHeight = (int)(Screen.sHeight / 9.5);

	/**
	 * 2D array of block that will be defined later
	 */
	public Block[][] block;

	/**
	 * Constructor that calls the define method which will initialize block
	 */
	public Room(){
		define();

	}
	
	/**
	 * Initializes the block as a 2d array, block is now world
	 */
	private void define() {
		//Instatiate the 2d block to be set as the world
		block = new Block[worldHeight][worldWidth];
		
		//for 0 to height of the block
		for(int i = 0; i < block.length; i++){
			//from 0 to width of the block
			for(int j = 0; j < block[0].length; j++){
				//Defining the block as (x coor, ycoor, widsize, heigtsize, groundid, airid)
				block[i][j] = new Block((Screen.sWidth/2) - ((worldWidth * blockSize)/2) + (j * blockSize), 
						i * blockHeight, blockSize, blockHeight, Value.groundGrass, Value.airAir);
			}
		}
	
}
	/**
	 * Called from the screen with the screen current graphics object
	 * Able to draw on the screen
	 * 
	 * @param g			Screens graphic
	 */
	public void draw(Graphics g){
		for(int i = 0; i < block.length; i++){
			for(int j = 0; j < block[0].length; j++){
				//draws each block individually
				block[i][j].draw(g);
			}
		}
		//Is not in the previous loop because that draws row by row and overlaps the rows ahead
		//so bottom on the box disapears
		for(int i = 0; i < block.length; i++){
			for(int j = 0; j < block[0].length; j++){
				//draws the radius of each block individually
				block[i][j].fight(g);
			}
		}
	}

	/**
	 * physics of the room, basically the physics for each individual block
	 * 
	 */
	public void physic() {
		for(int y =0; y < block.length;y++) {
			for(int x=0;x<block[0].length;x++) {
				block[y][x].physic();
			}
		}
		
	}
	
}
