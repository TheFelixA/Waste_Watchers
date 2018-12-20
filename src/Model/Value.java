package Model;

/**
 * @author Felix
 * @since 2015/11/10
 * 
 * Holds the values for all different elements in the game
 * Look on mission save file
 * i.e the grass,air enemies
 */
public class Value {

	/**
	 * ID of blocks to determine if it is grass or water
	 * Permanent vale for the grass 
	 */
	public static int groundGrass = 0;
	/**
	 * ID of blocks to determine if it is grass or water
	 * the estuary is the path of the enemy 
	 */
	public static int groundWater = 1;
	
	
	
	/**
	 * On the block and shows that there is no defense occupying the spot
	 * Permanent value for the air. Where the towers are location 
	 */
	public static int airAir = -1;
	/**
	 * On the block and shows that it is place where enemies stop
	 * the finish point where enemies start causing damage to estuaries
	 */
	public static int Finish = -2;			
	/**
	 * ID of the cleaner unit
	 * Can onlly attack dumper
	 */
	public static int trashCleaner = 0;
	/**
	 * ID for EPA agent unit
	 * Can attack overfishers and garbage dumpers
	 */
	public static int epaAgent = 1;
	/**
	 * ID of the fisherman unit
	 * Increases money gradually
	 */
	public static int fisherMan = 2;
	/**
	 * The ID of the researcher Unit 
	 * Increases health of estuary gradually
	 */
	public static int researchUnit = 3;
	/**
	 * ID for plant to protect from runoff 
	 * 	 * Decreases the amount of damage the estuary takes when enemy reaches estuary
	 */
	public static int plant1 = 4;
	/**
	 * ID fot plant to protect from runoff
	 * Decreases the amount of damage the estuary takes when enemy reaches estuary
	 */
	public static int plant2 = 5;
	/**
	 * name needs to be changed
	 */
	public static int AirTrashCan = 9;
	
	public static int save = 6;
	
	
	/**
	 * Coin earned from defeating each enemy
	 */
	public static int[] deathReward = {5,10,5};
	
	/**
	 * The damage enemy gives to estuary when it reaches finish
	 * Can be reduced by placing plants to prevent runoff and othe things
	 */
	public static double[] enemyDamage = {10, 15, 5};
	
	/**
	 * The path where the enemies are able to spawn
	 * look on mission save
	 * NOTNEEDED
	 */
	//public static int EnemyAir= -1;
	
	
	
	/**
	 * A value that displays what kind of enemy it is
	 * the distinct id of the garbage dropper
	 */
	public static int EnemyGarbageDropper = 0;
	/**
	 * Distinct ID for the overfisher
	 * Can be only attacked by EPA agent
	 */
	public static int EnemyOverFisher = 1;
	/**
	 * Distinct Id for Mitten Crab, Nodefense can attack it
	 */
	public static int EnemyMittenCrab = 2;
	
}
