package game;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import collision.CollisionDetector;
import display.LevelDisplay;
import entities.ActiveEntity;
import entities.Entity;
import entities.Player;
import entities.Pufferfish;
import entities.Shark;
import entities.Starfish;
import files.FileSystem;
import guicontrol.Frame;
import javafx.geometry.BoundingBox;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import java.awt.event.ActionEvent;

/**
 * The model component of the Game's
 * MVC structure.
 * @author maegan & andrew
 *
 */
public class Game {
	private FileSystem fs;
	private int xRes = 1300;
	private int yRes = 700;
	private int framesPerSecond;
	boolean gameHasStarted;
	Player player;
	Level currentLevel;
	LevelDisplay currentDisplay;
	private Frame frame;
	int key = -1;
	Timer timer;

	public static void main(String[] args) {
		//to try it out
		new Game();
	}

	/**
	 * Constructor used for loading saved games.
	 *
	 * @author andrew
	 *
	 * @param xRes the pixel width of the game
	 * @param yRes the pixel height of the game
	 * @param fps  the frame rate of the game
	 * @param gHS  whether or not the game has begun (gameHasStarted)
	 * @param p    Player object to be used
	 * @param l	   Level object to be used
	 */
	public Game(int xRes, int yRes, int fps, boolean gHS, Player p, Level l) {
		fs = new FileSystem(this);
		gameHasStarted = gHS;
		this.xRes = xRes;
		this.yRes = yRes;
		this.framesPerSecond = fps;
		this.player = p;
		this.currentLevel = l;
	}

	/**
	 * Constructor used for creating a new game from scratch
	 *
	 * @author maegan and andrew
	 */
	public Game() {
		fs = new FileSystem(this);
		gameHasStarted = false;
		player = new Player(new Point(50, 50), new Point(3, 3), new BoundingBox(0, 0, xRes, yRes),
				new CollisionDetector(this), this);
		currentLevel = new LevelOne();
		setFrame(new Frame(this));
		currentDisplay = getFrame().getLevelDisplay();
		framesPerSecond = 60;
		timer = new Timer((1000 / framesPerSecond), this::timerGoneOff);
	}

	/**
	 * This function replaces the internal game-state fields of this game
	 * with those from the argument 'savedGame'
	 * This allows a saved-game to be loaded into the already running instance
	 * of Game (e.g. prevents having to open a new window to display the saved game in)
	 *
	 * @author andrew
	 * @param savedGame	A game object that has been loaded from a saved game file
	 */
	public void transitionToSavedGame(Game savedGame) {
		fs = new FileSystem(this);
		xRes = savedGame.getXResolution();
		yRes = savedGame.getYResolution();
		framesPerSecond = savedGame.getFPS();
		gameHasStarted = true;
		player = savedGame.getPlayer();
		savedGame.getPlayer().setGame(this);
		currentLevel = savedGame.getLevel();
		for(Entity e : getEntityList()) {
			if(e instanceof Shark) {
				Shark s = (Shark) e;
				s.feedPlayer(getPlayer());
			}
		}
		timer = new Timer((1000 / framesPerSecond), this::timerGoneOff);
		getFrame().setWidth(xRes);
		getFrame().setHeight(yRes);
		getFrame().transitionToSavedGame(new LevelDisplay(this));

		start();
	}

	/**
	 * This function returns the game object to it's default state
	 * (e.g. the start of level one)
	 * Used when the 'new game' button is pressed
	 *
	 * @author andrew
	 */
	public void reset() {
		fs = new FileSystem(this);
		player = new Player(new Point(50, 50), new Point(3, 3), new BoundingBox(0, 0, xRes, yRes),
				new CollisionDetector(this), this);
		currentLevel = new LevelOne();
		currentDisplay = getFrame().getLevelDisplay();
		framesPerSecond = 60;
		timer = new Timer((1000 / framesPerSecond), this::timerGoneOff);

	}

	/**
	 * This function is called by the game's main swing timer.
	 * It is responsible for forwarding keyboard input,
	 * checking the health & fuel status of the player,
	 * ensuring active entities are moving,
	 * and repainting the display.
	 *
	 * This method is called 'frame-rate' times per second while
	 * the game is being played.
	 *
	 * @author maegan and andrew
	 * @param e
	 */
	private void timerGoneOff(ActionEvent e) {
		if (key != -1) {
			runKeyOperations();
		}
		stepActiveEntities();
		player.stepPlayer();
		getFrame().repaint();
		checkPlayerVitals();
	}

	/**
	 * This method is called when the player successfully completes a level.
	 * It progresses the game to the next level, or sets the game as 'won'.
	 *
	 * @author amy and andrew
	 */
	public void updateLevel() {
		if (currentLevel instanceof LevelOne) {
			currentLevel = new LevelTwo();
		}
		else if(currentLevel instanceof LevelTwo){
			currentLevel = new LevelThree();
		}
		else if(currentLevel instanceof LevelThree){
			winGame();
		}

	}

	/**
	 * This method is called when it is determined the player has won the game.
	 * The method stops the game-play and actives a game-won screen
	 *
	 * @author andrew
	 */
	public void winGame() {
		stop();
		frame.openWinScreen();
	}

	/**
	 * This method is called when the player dies during a level.
	 * It recreates a new instance of the SAME level the player is on,
	 * so they can try that level again.
	 *
	 * Not to be confused with 'reset' which always resets to level one.
	 *
	 * @author amy and maegan
	 */
	void resetLevel() {
		if (currentLevel instanceof LevelOne) {
			currentLevel = new LevelOne();
		} else if (currentLevel instanceof LevelTwo) {
			currentLevel = new LevelTwo();
		} else {
			currentLevel = new LevelThree();
		}
		player.getLocation().setLocation(new Point(50, 50));
		player.resetVitals();
		player.changeDirection("STOPPED");
	}

	/**
	 * This method is a helper method for timerGoneOff()
	 * It finds any active entities (the type of entities which move autonomously),
	 * and moves them one step in the correct direction.
	 *
	 * @author maegan and andrew
	 */
	public void stepActiveEntities() {
		for (Entity e : currentLevel.levelEntities) {
			if (e instanceof ActiveEntity) {
				ActiveEntity a = (ActiveEntity) e;
				if(a instanceof Shark){
					Shark s = (Shark) e;
					s.feedPlayer(player);
				}
				if(a instanceof Starfish){
					Starfish s = (Starfish) e;
					s.feedPlayer(player);
				}if(a instanceof Pufferfish){
					Pufferfish p = (Pufferfish) e;
					p.feedPlayer(player);
				}
				a.move();
			}
		}
	}

	/**
	 * This method is called by the control when the user requests to open
	 * a saved game.
	 * It commands the game's FileSystem to begin the process of loading a game.
	 *
	 * @author andrew
	 * @throws Exception
	 */
	public void loadGame() throws Exception {
		fs = new FileSystem(this);
		fs.loadGame();
	}

	/**
	 * This method is called by the control when the user requests to save
	 * the game to file.
	 * It commands the game's FileSystem to begin the process of saving the game.
	 * @author andrew
	 * @throws Exception
	 */
	public void saveGame() throws Exception {
		fs = new FileSystem(this);
		fs.saveGame();
	}

	/**
	 * This method begins the game in motion by starting the timer.
	 * @author andrew
	 */
	public void start() {
		timer.start();
	}

	/**
	 * This method stops (pauses) the game's motion.
	 * Used often when opening the menu, for example.
	 * @author andrew
	 */
	public void stop() {
		timer.stop();
	}

	/**
	 * Returns the List of Entity objects in the game
	 * @author maegan
	 */
	public ArrayList<Entity> getEntityList() {
		return currentLevel.getLevelEntities();
	}

	/**
	 * Returns the Player object in the game
	 * @author maegan
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * Returns the current Level object in the game
	 * @author maegan
	 */
	public Level getLevel() {
		return this.currentLevel;
	}

	/**
	 * Helper method of 'timerGoneOff()'
	 * Determines whether the Player should remain alive.
	 * If the player is decided to be dead, the game is halted and a
	 * dialog box is presented to the player.
	 * @author andrew and maegan
	 */
	public void checkPlayerVitals() {
		Boolean	isAlive = this.player.isAlive();
		Boolean hasFuel = this.player.hasFuel();
		String message = null;
		if (!isAlive && !hasFuel) {
			message = "You lost the game because you ran out of health and fuel.";
		} else if (!isAlive) {
			message = "You lost the game because you ran out of health.";
		} else if (!hasFuel) {
			message = "You lost the game because you ran out of fuel.";
		}
		if (message != null) {
			key=-1;
			Object[] options = { "Restart Level", "Menu", "Exit Game" };
			int n = JOptionPane.showOptionDialog(frame, message, "You Lost!", JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[2]);
			if (n == 0) {
				resetLevel();
				player.die();
			}
			if (n == 1) {
				frame.openMenu();
			}
			if (n == 2) {
				System.exit(0);
			}
		}
	}


	/**
	 * Responsible for processing key events which are
	 * forwarded here from the Control.
	 * @author andrew and maegan
	 * @param k
	 */
	public void keyPress(KeyEvent k) {
		Boolean keyPressed = k.getID()==KeyEvent.KEY_PRESSED;
		Boolean onGamePlayScreen = !getFrame().menuActive();
		if (keyPressed && onGamePlayScreen) {
			this.key = k.getKeyCode();
		}
	}

	/**
	 * Responsible for processing key events which are
	 * forwarded here from the Control.
	 * @author maegan
	 * @param k
	 */
	public void keyRelease(KeyEvent k) {
		Boolean keyReleased = k.getID() == KeyEvent.KEY_RELEASED;
		if (keyReleased) {
			if (this.key==k.getKeyCode()){
				player.changeDirection("STOPPED");
				key = -1;
			}
		}
	}

	/**
	 * Sets the direction in which the player should be moving.
	 * Checks for 'ESC' key-press and opens menu as required.
	 * @Author maegan & andrew
	 */
	public void runKeyOperations(){
		switch(key) {
			case KeyEvent.VK_UP:	player.changeDirection("UP");
									break;
			case KeyEvent.VK_DOWN:	player.changeDirection("DOWN");
									break;
			case KeyEvent.VK_LEFT:	player.changeDirection("LEFT");
									break;
			case KeyEvent.VK_RIGHT:	player.changeDirection("RIGHT");
									break;
			case KeyEvent.VK_ESCAPE:	frame.openMenu();
									break;
		}

	}


	/**
	 * Returns the numeric value of the player's health.
	 * @return
	 */
	public double getPlayerHealth() {
		return this.player.getPlayerHealthStatus();
	}

	/**
	 * Returns the numeric value of the fuel remaining in the player's submarine.
	 * @return
	 */
	public double getPlayerFuel() {
		return this.player.getPlayerFuelStatus();
	}

	/**
	 * Returns the pixel width of the game.
	 * @return
	 */
	public int getXResolution() {
		return this.xRes;
	}

	/**
	 * Returns the pixel height of the game.
	 * @return
	 */
	public int getYResolution() {
		return this.yRes;
	}

	/**
	 * Returns the frame-rate at which the game is running.
	 * @return
	 */
	public int getFPS() {
		return this.framesPerSecond;
	}

	/**
	 * Returns the FileSystem being used by the game.
	 * @return
	 */
	public FileSystem getFS() {
		return this.fs;
	}

	/**
	 * Flags the game as being in progress.
	 * Used when generating menu screen to determine
	 * which buttons should be displayed.
	 *
	 * @author andrew
	 */
	public void setGameAsStarted() {
		this.gameHasStarted = true;
	}

	/**
	 * Flags the game as not having started yet.
	 * Used when generating menu screen to determine
	 * which buttons should be displayed.
	 *
	 * @author andrew
	 */
	public void setGameAsNotStarted() {
		this.gameHasStarted = false;
	}

	/**
	 * Returns wether the game has begun yet.
	 * Used when generating menu screen to determine
	 * which buttons should be displayed.
	 * @return
	 */
	public Boolean getWetherGameHasStarted() {
		return this.gameHasStarted;
	}


	/**
	 * Used for saving the game to a file.
	 * Returns a String representation of the Game's internal fields.
	 * @author andrew
	 * @return
	 */
	public String export() {
		return xRes + "," + yRes + "," + framesPerSecond;
	}

	/**
	 * Returns the Frame object in use by the Game.
	 * @return
	 */
	public Frame getFrame() {
		return frame;
	}

	/**
	 * Sets the Frame object being used by the Game.
	 * @param frame
	 */
	public void setFrame(Frame frame) {
		this.frame = frame;
	}

	/**
	 * Sets the level object being used by the Game.
	 * @param level
	 */
	public void setLevel(Level level) {
		this.currentLevel = level;
	}

	/**
	 * @author andrew
	 */
	@Override
	public boolean equals(Object o) {
		if(!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {return false;}
		Game g = (Game) o;
		Boolean sameXRes = this.getXResolution() == g.getXResolution();
		Boolean sameYRes = this.getYResolution() == g.getYResolution();
		Boolean sameFPS = this.getFPS() == g.getFPS();
		Boolean sameGameState = this.getWetherGameHasStarted() == g.getWetherGameHasStarted();
		Boolean samePlayer = this.getPlayer().equals(g.getPlayer());
		Boolean sameEntities = this.getEntityList().equals(g.getEntityList());
		Boolean sameLevel = this.getLevel().equals(g.getLevel());

		return sameXRes && sameYRes && sameFPS && sameGameState && samePlayer && sameEntities && sameLevel;
	}


	/**
	 * Method used for external testing of GUIControl.
	 * Same as runKeyOperations
	 * @author jannat
	 * @param key
	 */
	public void runKeyOperations(int key) {
		// TODO Auto-generated method stub
		switch(key) {
		case KeyEvent.VK_UP:	player.changeDirection("UP");
								break;
		case KeyEvent.VK_DOWN:	player.changeDirection("DOWN");
								break;
		case KeyEvent.VK_LEFT:	player.changeDirection("LEFT");
								break;
		case KeyEvent.VK_RIGHT:	player.changeDirection("RIGHT");
								break;
		case KeyEvent.VK_ESCAPE:	frame.openMenu();
								break;
	}

	}

}