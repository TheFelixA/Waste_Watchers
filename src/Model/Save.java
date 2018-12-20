package Model;
import java.io.*;
//needed to import the file
import java.util.*;

/**
 * @author Felix
 * @since 2015/11/10
 * 
 * Will load up and save the game files
 */
public class Save {

	/**
	 * loads up different save files to the game
	 * 
	 * @param loadPath			the path of the save file
	 */
	public static void loadSave(File loadPath, boolean load) {
		try {
		//needed to load a file, scans through the files while there is a next
			Scanner loadScanner = new Scanner(loadPath);
			
			while (loadScanner.hasNext()) {
				//saves the number of kills to win
				Screen.killsToWin =loadScanner.nextInt();
				if(load){
					Screen.health = loadScanner.nextInt();
					Screen.coinage = loadScanner.nextInt();
					Store.unitCount = loadScanner.nextInt();
				}
									
				
				for (int i = 0; i < 3; i++) {
					Screen.enemyProb[i] = loadScanner.nextDouble();
				}
				
				//loads the block ground and water layout
				for (int y = 0; y <Screen.room.block.length; y++) {
					for (int x = 0; x <Screen.room.block[0].length; x++) {
						Screen.room.block[y][x].groundID = loadScanner.nextInt();
					}
				}
				
				//loads the places where defenses will be set and estuary damage point
				for (int y = 0; y <Screen.room.block.length; y++) {
					for (int x = 0; x <Screen.room.block[0].length; x++) {
						Screen.room.block[y][x].airID = loadScanner.nextInt();
					}
				}
			}	
			
			loadScanner.close();
		} 
		
		catch(Exception e) {
			
		}
	}
	
	public static void writeSave() {
		try {
			//needed to write to a file
			PrintWriter writer = new PrintWriter(new File("dump.txt"));
			
			//saves the number of kills to win
			writer.print(Screen.killsToWin+"\n");
			writer.print("\n");

			writer.print(Screen.health+" ");
			writer.print(Screen.coinage+" ");
			writer.print(Store.unitCount);
			
			writer.print("\n");
			writer.print("\n");

			for (int i = 0; i < 3; i++) {
				writer.print(Screen.enemyProb[i]+" ");
			}
			writer.print("\n");
			writer.print("\n");

			//saves the block ground and water layout
			for (int y = 0; y <Screen.room.block.length; y++) {
				for (int x = 0; x <Screen.room.block[0].length; x++) {
					writer.print(Screen.room.block[y][x].groundID+" ");
				}
				writer.print("\n");
			}
			
			writer.print("\n");

			//saves the places where defenses will be set and estuary damage point
			for (int y = 0; y <Screen.room.block.length; y++) {
				for (int x = 0; x <Screen.room.block[0].length; x++) {
					writer.print(Screen.room.block[y][x].airID+" ");
				}
				writer.print("\n");
			}
			
			writer.close();
		} 
		
		catch(Exception e) {
			
		}
	}
	
}
