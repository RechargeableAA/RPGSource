package com.rech.rpg;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * A standardized way to make menus. The primary purpose is to have the "You dont know what..." messages to appear below the options
 * @author Nolan DeMatteis
 *
 */
public class Menu {
	/**
	 * Text appearing at the very top of the menu, like options or questions.
	 */
	private ArrayList<Prompt> prompts;
	
	//Information important to menu options, like the location you're in
	private String menuInfo;
	private String menuTitle;
	
	private static final int defaultMLL = 50;
	private static int maxLineLength = defaultMLL;
	
	private String menuBorder = "|";
	private String titleDivider = "-";
	/**
	 * 		|			menuTitle
	 * 		--------------------------------
	 * 		|			menuInfo
	 * 		|
	 * 		|[prompt] - prompt description
	 * 		|[prompt] - prompt description	 
	 * 		|[prompt] - prompt description
	 *		|
	 * 		|message message message message
	 * 
	 * 
	 */
	
	
	
	/**
	 * Create an empty menu
	 * @param menuTitle - Name displayed above options
	 */
	public Menu(String menuTitle) {
		prompts = new ArrayList<Prompt>();
		this.menuTitle = menuTitle;
	}
	
	/**
	 * Print the menu name, its options, and any messages in that order
	 * @param isWrapped - 
	 */
	public void display(boolean isWrapped) {
		Main.clearScreen();
		
		//Menu title
		System.out.print(new String(new char[(maxLineLength/2)-(menuTitle.length()/2)-1]).replace("\0", " ")); // center menu title with spaces
		System.out.println(menuTitle);
		
		//Menu info
		
		if(menuInfo != null && menuInfo != ""){ 
			if (isWrapped == true) {
				for(String wrappedInfo : wordWrappedString(menuInfo, maxLineLength, 0, false)) {
					System.out.println(wrappedInfo);
				}
			}else{
				System.out.println(menuInfo);
			}
		}
			
		//Menu prompts
		if (isWrapped == true) {
			for(Prompt prompt : prompts) {
				System.out.print(prompt.title);
				for(String wrappedPrompt : wordWrappedString(prompt.description, maxLineLength, prompt.title.length(), false)) {
					System.out.println(wrappedPrompt);
				}
			}
		}else {
			for(Prompt prompt : prompts) {
				System.out.print(prompt.getTitle());
				System.out.println(prompt.getDescription());
			}
		}
		
	}
	
	/**
	 * Set information about the current menu
	 * @param menuInfo
	 */
	public void setMenuInfo(String menuInfo) {
		this.menuInfo = menuInfo;
	}
	
	/**
	 * Clear the menu's info
	 */
	public void clearMenuInfo() {
		menuInfo = "";
	}
	
	/**
	 * Clears menuinfo, prompts, and messages
	 */
	public void clearAll() {
		prompts.clear();
		menuInfo = "";
	}
	
	/**
	 * Add a prompt/option to the menu
	 * @param promptTitle
	 * @param promptDescription
	 */
	public void addPrompt(String promptTitle, String promptDescription) {
		prompts.add(new Prompt("["+promptTitle.toUpperCase()+"] - ", promptDescription));
	}
	public void addPrompt(String promptTitle) {
		prompts.add(new Prompt("["+promptTitle.toUpperCase()+"]", ""));
	}
	
	
	/**
	 * Remove all prompts from menu
	 */
	public void clearPrompts() {
		prompts.clear();
	}
	
	/**
	 * Messages that appear after menu, displayed instantly
	 * EX: "You payed 5 gp for that."
	 * @param message
	 */
	public void message(String message, Scanner input) {
		if(message != null && message != ""){ 
			for(String wrappedInfo : wordWrappedString(message, maxLineLength, 0, false)) {
				System.out.println(wrappedInfo);
			}
		}
		input.nextLine();
		maxLineLength = defaultMLL; //this is so you can set the MLL back to default so you can change it dynamically, and it will revert after the message.

	}
	
	/**
	 * Word wrap the menu by restricting its length to a value. Allows for word wrapping to be indented by a specific amount
	 * @param rawString - raw unwrapped string, should not contain \n
	 * @param maxLineLength - The bounds to wrap the text around
	 * @param firstLineBias - Give the first line a shorter max length to account for any text that might proceed it
	 * @param biasIndent - Give lines after the first line, the same indent
	 * @return
	 */
	private String[] wordWrappedString(String rawString, int maxLineLength, int firstLineBias, boolean biasIndent) {
		String stringSplit = rawString;
		ArrayList<String> wrappedStrings = new ArrayList<String>();
		int lastSpaceIndex = 0;
		for(int charc = 0; charc < stringSplit.length(); charc++) {
			if(stringSplit.charAt(charc) == ' ') {
				lastSpaceIndex = charc;
			}
			
			if(charc >= maxLineLength-firstLineBias) {
				if(wrappedStrings.size() == 0) {
					wrappedStrings.add(stringSplit.substring(0, lastSpaceIndex));
				}else {
					String indent = new String(new char[firstLineBias]).replace("\0", " ");
					wrappedStrings.add(indent + stringSplit.substring(0, lastSpaceIndex));
				}
				stringSplit = stringSplit.substring(lastSpaceIndex+1);
				charc = 0;
				if(!biasIndent) {
					firstLineBias = 0;
				}
			}
		}
		//add remainder of text
		if(wrappedStrings.size() > 0) {
			String indent = new String(new char[firstLineBias]).replace("\0", " ");
			wrappedStrings.add(indent + stringSplit);
		}else {
			wrappedStrings.add(stringSplit);
		}
		
		return wrappedStrings.toArray(new String[wrappedStrings.size()]);
	}
	
	
	
	
	/**
	 * Options contained in the menu.
	 * Consists of a title for the option and its description
	 * @author Nolan DeMatteis
	 *
	 */
	private class Prompt {
		private String title, 
					   description;
		protected Prompt(String title, String description) {
			this.title = title;
			this.description = description;
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getDescription() {
			return description;
		}
	}
}
