package files;

import java.awt.EventQueue;
import java.awt.Point;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.lang.Math;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import entities.Chain;
import entities.Entity;
import entities.FuelTopup;
import entities.Jellyfish;
import entities.Mine;
import entities.Player;
import entities.Pufferfish;
import entities.Rocks;
import entities.Shark;
import entities.Spanner;
import entities.Starfish;
import entities.Trash;
import entities.TreasureChest;
import game.Game;
import game.Level;
import game.LevelOne;
import game.LevelThree;
import game.LevelTwo;
import javafx.geometry.BoundingBox;

/**
 * Manages the reading and writing of saved games via files on the user's hard drive.
 * @author Andrew Thompson
 *
 */
public class FileSystem {
	private File currentFile = null;
	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat ("'['dd/MM/yyyy' @ 'HH:mm']'");
	private Game game = null;
	private Game loadedGame = null;

	/**
	 * Constructs a new FileSystem object attached to a Game.
	 * @param g Game which the FileSystem will save / load to and from.
	 */
	public FileSystem(Game g) {
		this.game = g;
	}

	/**
	 * Presents the user with a file-chooser dialog, and saves the
	 * current game to a file on their local file system, as Submarine Game
	 * type (extension .sub)
	 * @throws Exception
	 */
	public void saveGame() throws Exception {
		guiChooseFile("save");
	}

	/**
	 * Presents the user with a file-chooser dialog, and loads the 
	 * chosen saved-game file into the current game window and resumes
	 * game play.
	 * @throws Exception
	 */
	public void loadGame() throws Exception {
		guiChooseFile("load");
		this.game.transitionToSavedGame(this.loadedGame);

	}

	/**
	 * Parses out the state of the game into a list of Strings ready to be written to file.
	 * @return	List<String> representation of the game state
	 */
	List<String> parseOut(Game g){
		final long startTime = System.nanoTime();

		List<String> lines = new ArrayList<>();

		//Setting up the file header
		lines.add("<<SUB>>");

		//Adding meta-data
		lines.add("<META>");
		lines.add("[NAME]The Little Submarine That Could[/NAME]");
		lines.add("[TYPE]SAVED GAME[/TYPE]");
		lines.add("[INFO]"+"File Created On: " + dateTimeFormat.format(new Date()) + "[/INFO]");
		lines.add("</META>");

		//Extracting state of Game object
		lines.add("<GAME>");
		lines.add(bracket("open",g) + g.export() + bracket("close",g));
		lines.add("</GAME>");

		//Extracting state of Player object
		Player p = g.getPlayer();
		lines.add("<PLAYER>");
		lines.add(bracket("open",p) + p.export() + bracket("close",p));
		lines.add("</PLAYER>");

		//Extracting state of Level object
		Level l = g.getLevel();
		lines.add("<LEVEL>");
		lines.add(bracket("open",l) + l.getLevelEntities().size() + bracket("close",l));
		lines.add("</LEVEL>");

		//Extracting state of Entity objects
		List<Entity> entities = g.getEntityList();
		lines.add("<ENTITIES>");
		for(Entity e : entities) {
			lines.add(bracket("open",e) + e.export() + bracket("close",e));
		}
		lines.add("</ENTITIES>");

		//Setting up footer
		final long duration = System.nanoTime() - startTime;
		lines.add("<META>");
		lines.add("[INFO] "+"Game export completed in " + (TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS)) + " seconds.[/INFO]" );

		return lines;
	}

	/**
	 * Generates opening or closing brackets to surround Object data, for use in FileSystem.parseOut()
	 * @param type	Either "open" or "close"
	 * @param e		The Object whose class name is to be used
	 * @return		[OBJECT-NAME] or [/OBJECT-NAME]
	 */
	private String bracket(String type, Object e) {
		String open = "[";
		if(type.equals("close")) {open = "[/";}
		String name = e.getClass().getSimpleName();
		name = name.toUpperCase();
		String close = "]";
		return open+name+close;
	}

	/**
	 * Retrieves a list of Strings which represent a past game state.
	 * Parses String content appropriately to reconstruct previous Game object
	 * into the 'loadedGame' field.
	 * @throws Exception
	 */
	public void parseIn() throws Exception {
		
		//Reading contents of file and verifying it is of acceptable format
		List<String> lines = readFromFile();
		if(!verifyFileIntegrity(lines)) {
			throw new Exception("The file is incompatible or corrupted");
		}

		//Parsing Game Data
		int xRes = Integer.parseInt(Arrays.asList(stripBrackets(lines.get(7)).split("\\s*,\\s*")).get(0));
		int yRes = Integer.parseInt(Arrays.asList(stripBrackets(lines.get(7)).split("\\s*,\\s*")).get(1));
		int framesPerSecond = Integer.parseInt(Arrays.asList(stripBrackets(lines.get(7)).split("\\s*,\\s*")).get(2));
		Boolean gameHasStarted = true;
		Player p = null;
		Level currentLevel = null;

		//Parsing Player Data
		List<String> playerArgs = Arrays.asList(stripBrackets(lines.get(10)).split("\\s*,\\s*"));
		double locationX = Double.parseDouble(playerArgs.get(1));
		double locationY = Double.parseDouble(playerArgs.get(2));
		Point location = new Point((int) locationX, (int) locationY);
		double damage = Double.parseDouble(playerArgs.get(3));
		double fuelLoss = Double.parseDouble(playerArgs.get(4));
		double movementX = Double.parseDouble(playerArgs.get(5));
		double movementY = Double.parseDouble(playerArgs.get(6));
		Point movement = new Point((int) movementX, (int) movementY);
		BoundingBox bounds = null;
		if(!playerArgs.get(7).equals("null")) {
			double minX = Double.parseDouble(playerArgs.get(7));
			double minY = Double.parseDouble(playerArgs.get(8));
			double width = Double.parseDouble(playerArgs.get(9));
			double height = Double.parseDouble(playerArgs.get(10));
			bounds = new BoundingBox(minX,minY,width,height);
		}
		double health = Double.parseDouble(playerArgs.get(11));
		double fuel = Double.parseDouble(playerArgs.get(12));
		double step = Double.parseDouble(playerArgs.get(13));
		int deaths = Integer.parseInt(playerArgs.get(14));

		p = new Player(location,damage,fuelLoss,bounds,movement,health,fuel,step,deaths);


		//Parsing Entity Data
		int numEntities = Integer.parseInt(stripBrackets(lines.get(13)));
		int firstEntity = 16;
		int lastEntity = firstEntity + numEntities;

		List<Entity> levelEntities = parseEntityData(lines.subList(firstEntity, lastEntity));


		//Parsing Level Data
		String levelString = getBracketName(lines.get(13));

		//Creating appropriate Level object using the previously generated list of Entities
		switch(levelString) {
		case "LEVELONE":		//Construct level one
			currentLevel = new LevelOne(levelEntities);
			break;
		case "LEVELTWO": 	//Construct level two
			currentLevel = new LevelTwo(levelEntities);
			break;
		case "LEVELTHREE":	//Construct level three
			currentLevel = new LevelThree(levelEntities);
			break;
		}

		//Constructing Game object
		this.loadedGame = new Game(xRes,yRes,framesPerSecond,gameHasStarted,p,currentLevel);
		
	}

	/**
	 * Helper method for 'parseIn()'
	 * Responsible for parsing a sub-list of a previous game state.
	 * The sub-list contains String representations of only the entities in the previous
	 * game state.
	 * @param lines A list of Strings representing only the entities in a previous game state
	 * @return	A list of Entity objects to be used when re-constructing a saved game
	 */
	public List<Entity> parseEntityData(List<String> lines){
		List<Entity> entityList = new ArrayList<>();

		for(String entityString : lines) {
			//For each entity, get its name and its arguments as a list of Strings.
			List<String> entityArgs = Arrays.asList(stripBrackets(entityString).split("\\s*,\\s*"));
			String type = entityArgs.get(0);

			//Entity Arguments (applicable to ALL entities)
			double locationX = Double.parseDouble(entityArgs.get(1));
			double locationY = Double.parseDouble(entityArgs.get(2));
			Point location = new Point((int) locationX, (int) locationY);
			double damage = Double.parseDouble(entityArgs.get(3));
			double fuelLoss = Double.parseDouble(entityArgs.get(4));

			//Active Entity Arguments (applicable only to Active Entities)
			double movementX, movementY;
			Point movement = null;
			BoundingBox bounds = null;
			double minX, minY, width, height;
			if(type.equalsIgnoreCase("Jellyfish") || type.equalsIgnoreCase("Shark") || type.equalsIgnoreCase("Starfish") || type.equalsIgnoreCase("Pufferfish")) {
				movementX = Double.parseDouble(entityArgs.get(5));
				movementY = Double.parseDouble(entityArgs.get(6));
				movement = new Point((int) movementX, (int) movementY);
				bounds = null;
				if(!entityArgs.get(7).equals("null")) {
					minX = Double.parseDouble(entityArgs.get(7));
					minY = Double.parseDouble(entityArgs.get(8));
					width = Double.parseDouble(entityArgs.get(9));
					height = Double.parseDouble(entityArgs.get(10));
					bounds = new BoundingBox(minX,minY,width,height);
				}
			}
			
			//Pufferfish Arguments (only applicable if the entity is a Pufferfish)
			Boolean puffed = false;
			if(type.equalsIgnoreCase("Pufferfish")) {
				if(entityArgs.get(11).equals("true")) {
					puffed = true;
				}
			}
			
			//Mine Arguments (only applicable if the entity is a Mine)
			Boolean exploded = false;
			if(type.equalsIgnoreCase("Mine")) {
				if(entityArgs.get(5).equals("true")) {
					exploded = true;
				}
			}
			

			//Starfish Arguments (only applicable if the entity is a Starfish)
			Boolean isStuck = false;
			int count = 0;
			if(type.equalsIgnoreCase("Starfish")) {
				if(entityArgs.get(11).equals("true")) {
					isStuck = true;
				}
				count = Integer.parseInt(entityArgs.get(12));
			}
			
			//Rocks Arguments (only applicable if the entity is a Rock)
			int level = 1;
			String side = "LEFT";
			if(type.equalsIgnoreCase("Rocks")) {
				level = Integer.parseInt(entityArgs.get(5));
				side = entityArgs.get(6);
			}
			

			//Construct the entity
			switch(type) {
			case "Starfish":		//construct starfish
				entityList.add(new Starfish(location,damage,fuelLoss,bounds,movement,isStuck,count));
				break;
			case "Shark":		//construct shark
				entityList.add(new Shark(location,damage,fuelLoss,bounds,movement));
				break;
			case "Jellyfish":	//construct Jellyfish
				entityList.add(new Jellyfish(location,damage,fuelLoss,bounds,movement));
				break;
			case "TreasureChest"://construct TreasureChest
				entityList.add(new TreasureChest(location,damage,fuelLoss));
				break;
			case "Trash":		//construct Trash
				entityList.add(new Trash(location,damage,fuelLoss));
				break;
			case "Spanner":		//construct Spanner
				entityList.add(new Spanner(location,damage,fuelLoss));
				break;
			case "Rocks":		//construct Rocks
				entityList.add(new Rocks(location,damage,fuelLoss,level,side));
				break;
			case "Pufferfish":	//construct Pufferfish
				entityList.add(new Pufferfish(location,damage,fuelLoss,bounds,movement,puffed));
				break;
			case "Mine":			//construct Mine
				entityList.add(new Mine(location,damage,fuelLoss,exploded));
				break;
			case "FuelTopup":	//construct FuelTopup
				entityList.add(new FuelTopup(location,damage,fuelLoss));
				break;
			case "Chain": //construct Chain
				entityList.add(new Chain(location,damage,fuelLoss));
				break;
			}
		}

		return entityList;
	}

	/**
	 * Helper method for 'parseIn()'
	 * Responsible for analyzing the contents of the saved game file, and determining whether or not
	 * the contents is valid. If the contents is not valid, the file cannot not be parsed. 
	 * @param lines A list of Strings that have been read from a saved game file
	 * @return	A boolean value whether the 'lines' argument has been verified as a correct
	 * representation of a saved game.
	 */
	public Boolean verifyFileIntegrity(List<String> lines) {
		Boolean openingTag = lines.get(0).equals("<<SUB>>");
		Boolean closingTag = lines.get(lines.size()-1).equals("<</SUB>>");
		Boolean openingMeta = lines.get(1).equals("<META>");	//The meta information expected near the top of a saved game file
		Boolean closingMeta = lines.get(5).equals("</META>");
		Boolean subGame = stripBrackets(lines.get(2)).equals("The Little Submarine That Could");
		Boolean type = stripBrackets(lines.get(3)).equals("SAVED GAME");
		Boolean openingGame = lines.get(6).equals("<GAME>");
		Boolean closingGame = lines.get(8).equals("</GAME>");
		Boolean gameHasThreeValues = Arrays.asList(stripBrackets(lines.get(7)).split("\\s*,\\s*")).size() == 3;
		Boolean openingPlayer = lines.get(9).equals("<PLAYER>");
		Boolean closingPlayer = lines.get(11).equals("</PLAYER>");
		Boolean playerHas15Values = Arrays.asList(stripBrackets(lines.get(10)).split("\\s*,\\s*")).size() == 15;
		Boolean openingLevel = lines.get(12).equals("<LEVEL>");
		Boolean closingLevel = lines.get(14).equals("</LEVEL>");
		String levelString = getBracketName(lines.get(13));
		Boolean validLevel = levelString.equalsIgnoreCase("LevelOne") ||
				levelString.equalsIgnoreCase("LevelTwo") ||
				levelString.equalsIgnoreCase("LevelThree");
		int numEntities = Integer.parseInt(stripBrackets(lines.get(13)));
		Boolean hasEntities = numEntities > 0;
		Boolean openingEntities = lines.get(15).equals("<ENTITIES>");
		Boolean closingEntities = lines.get(15+numEntities+1).equals("</ENTITIES>");
		int firstEntity = 16;
		int lastEntity = firstEntity + numEntities;
		Boolean entitiesAreValid = entitiesAreValid(lines.subList(firstEntity, lastEntity));
		Boolean openingMeta2 = lines.get(lastEntity+1).equals("<META>");	//The second area of meta information expected near the end
		Boolean closingMeta2 = lines.get(lastEntity+4).equals("</META>");	// of a saved game file.

		return openingTag && closingTag && openingMeta && closingMeta && subGame && type && openingGame &&
				closingGame && gameHasThreeValues && openingPlayer && closingPlayer && playerHas15Values &&
				openingLevel && closingLevel && validLevel && hasEntities && openingEntities && closingEntities &&
				entitiesAreValid && openingMeta2 && closingMeta2;
	}

	/**
	 * Helper method for 'verifyFileIntegrity()'
	 * Responsible for analysing the part of a saved game file which contains Entity data.
	 * Verifies whether entity data is valid, by checking entity names and no. of arguments.
	 * @param entities
	 * @return
	 */
	public Boolean entitiesAreValid(List<String> entities) {
		Boolean containsAnInvalidEntity = false;
		
		//Define the number of arguments expected for each type of Entity
		int entityArgs = 5;
		int mineArgs = 6;
		int activeEntityArgs = 11;
		int pufferfishArgs = 12;
		int starFishArgs = 13;
		int rockArgs = 7;
		
		//Define a map of Entity type to expected no. of arguments
		Map<String,Integer> validEntities = new HashMap<>();
		validEntities.put("fueltopup",entityArgs);
		validEntities.put("chain", entityArgs);
		validEntities.put("jellyfish",activeEntityArgs);
		validEntities.put("mine",mineArgs);
		validEntities.put("pufferfish",pufferfishArgs);
		validEntities.put("rocks",rockArgs);
		validEntities.put("shark",activeEntityArgs);
		validEntities.put("spanner",entityArgs);
		validEntities.put("starfish",starFishArgs);
		validEntities.put("trash",entityArgs);
		validEntities.put("treasurechest",entityArgs);
		
		//Check that each entity is of a type specified in the map
		//And that if it is a valid type, it has the correct no. of arguments
		//for that type.
		for(String entity : entities) {
			if(!(validEntities.containsKey(getBracketName(entity).toLowerCase()))) {
				containsAnInvalidEntity = true;
			}else if(Arrays.asList(stripBrackets(entity).split("\\s*,\\s*")).size() != validEntities.get(getBracketName(entity).toLowerCase())) {
				containsAnInvalidEntity = true;
			}
		}
		return !containsAnInvalidEntity;
	}

	/**
	 * Helper method for 'parseIn()'
	 * Given a String with contents enclosed by brackets generated by the above 
	 * 'bracket()' method, this function returns the String without the brackets. 
	 * @param s A line enclosed with brackets e.g. "[BRACKET]Hello World[/BRACKET]"
	 * @return	The line with the brackets removed e.g. "Hello World"
	 */
	public String stripBrackets(String s) {
		int last = s.lastIndexOf("[");
		int first = s.lastIndexOf("]", last);
		String stripped = s.substring(first+1, last);
		return stripped;
	}

	/**
	 * Helper method for 'parseIn()'
	 * Given a String with contents enclosed by brackets generated by the above
	 * 'bracket()' method, this function returns the String inside of the bracket,
	 * e.g. the bracket name.
	 * @param String representation of an Entity E.g. "[ROCKS]Some Rock Data Here[/ROCKS]"
	 * @return	Name contained within the brackets, e.g. "ROCKS"
	 */
	public String getBracketName(String line) {
		int lastOpen = line.lastIndexOf("[");
		int firstClose = line.lastIndexOf("]", lastOpen);
		String name = line.substring(1, firstClose);
		return name;
	}

	/**
	 * Retrieves a list of String which represents the current game state.
	 * Writes these strings to the File specified by field currentFile.
	 * @throws Exception
	 */
	 void writeToFile() throws Exception {
		 //Check that the file-chooser has first been used to select a save location
		if(currentFile == null) {
			throw new Exception("writeToFile() - currentFile has not been set");
		}
		//Generate the List of Strings to be written to the file
		List<String> lines = parseOut(game);
		final long startTime = System.nanoTime();
		Path targetFile = currentFile.toPath();
		//Write the list of Strings to the file specified in field 'currentFile'
		Files.write(targetFile, lines);
		final long duration = System.nanoTime() - startTime;
		List<String> footer = new ArrayList<>();
		footer.add("[INFO] " + "Writing to file completed in " + (TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS)) + " seconds.[/INFO]");
		footer.add("</META>");
		footer.add("<</SUB>>");
		Files.write(targetFile,footer, StandardOpenOption.APPEND);
	}

	/**
	 * Reads the contents of the File specified by currentFile field and copies each line
	 * into a List of String.
	 * @return	List containing each line in the File pointed to by currentFile field.
	 * @throws Exception
	 */
	public List<String> readFromFile() throws Exception {
		//Check the file chooser has been used to select a file first.
		if(currentFile == null) {
			throw new Exception("readFromFile() - currentFile has not been set");
		}
		final long startTime = System.nanoTime();
		Path sourceFile = currentFile.toPath();
		List<String> lines = Files.readAllLines(sourceFile);

		final long duration = System.nanoTime() - startTime;
		return lines;
	}

	/**
	 * Used for JUnit test purposes, so that the currentFile field can be set,
	 * without requiring a JFileChooser (and therefore user input).
	 * @param path
	 */
	public void setFileByPathString(String path) {
		//path variable should be in format of "resources/sub-folders-here/file.fileExtension"
		currentFile = new File(path);
	}

	/**
	 * @return	The current File object the FileSystem is working with
	 */
	public File getCurrentFile() {
		return currentFile; 
	}

	/**
	 * Creates and displays on the screen a graphical-user-interface
	 * file picker. Has 2 modes for either opening or saving a file.
	 * Restricted to only allow Submarine Game (.sub) file type.
	 * Only one file may be selected at a time.
	 * @param mode	"load" for loading a game file or "save" for saving a game file
	 * @throws Exception
	 */
	private void guiChooseFile(String mode) throws Exception {

		if(mode.equals("load")) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileExtensionFilter("sub","Submarine Game"));
			fc.setApproveButtonText("Load Game");
			fc.setDialogTitle("Load Saved Game");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY); //Prevent user from trying to load a folder
			fc.setMultiSelectionEnabled(false); //User may only choose ONE file
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				currentFile = fc.getSelectedFile();
				try {
					if(currentFile != null) {
						parseIn();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					JOptionPane.showMessageDialog(new JFrame(),
						    e.getMessage(),
						    "Error Loading Saved Game",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		}else if(mode.equals("save")) {
			JFileChooser fc = new JFileChooser();
			fc.setFileFilter(new FileExtensionFilter("sub","Submarine Game"));
			fc.setApproveButtonText("Save Game");
			fc.setDialogTitle("Save Game");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY); //Prevent user from trying to load a folder
			fc.setMultiSelectionEnabled(false); //User may only choose ONE file
			int returnVal = fc.showSaveDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File f = fc.getSelectedFile();
				String path = f.getAbsolutePath();
				if(!path.endsWith(".sub")) {
					path += ".sub";
				}
				currentFile = new File(path);
				if(currentFile != null) {
					try {
						writeToFile();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						JOptionPane.showMessageDialog(new JFrame(),
							    e.getMessage(),
							    "Error Saving Game",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		}else {
			try {
				throw new Exception("Incompatible guiChooseFile() argument: " + mode);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 


	}

}
