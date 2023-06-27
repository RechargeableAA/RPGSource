package com.rech.rpg;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner; // Import the Scanner class to read text files
import static java.util.Map.entry;

import com.rech.rpg.entity.Player;

public class FileManager{

	public class SaveFile{
		
		ArrayList<SaveObject> saveObjects = new ArrayList<SaveObject>();
		
		public SaveFile(Player player) {
			//player save object
			saveObjects.add(new SaveObject(new HashMap<String, Object>()) {{Map.ofEntries(
					entry("PlayerName", Main.getPlayer().getName())
					
			);}});
		}
		
		public boolean save(String fileName) {
			try {
				FileWriter save = new FileWriter(fileName+".txt");
				for(SaveObject obj : saveObjects) {
					for(Map.Entry<String, Object> entry : obj.values.entrySet()) {
						save.write(entry.getKey() + ":" + entry.getValue());
					}
				}
				System.out.println("Save Complete.");	
				save.close();
				return true;
			}catch(IOException ioe) {
				System.err.println("Save failed.");
			}
			return false;
		}
		
		public boolean load(String fileName) {
			try {
			File saveGame = new File(fileName+".txt");
				if (saveGame.exists()) {
					
					Scanner save = new Scanner(saveGame);
					
					while (save.hasNextLine()) {
						for(SaveObject obj : saveObjects) {
							for(Map.Entry<String, Object> entry : obj.values.entrySet()) {
								String currentLine = save.nextLine();
								System.out.println(currentLine.split(":")[0] + " " + currentLine.split(":")[1]);
								entry = entry(currentLine.split(":")[0], currentLine.split(":")[1]);
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
				
				
				
		public class SaveObject{
			private HashMap<String, Object> values;
			
			public SaveObject(HashMap<String, Object> values) {
				this.values = values;
			}
			
			public HashMap<String, Object> getValues(){
				return values;
			}
		}
	}
}
