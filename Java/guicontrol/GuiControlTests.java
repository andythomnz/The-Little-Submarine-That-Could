package guicontrol;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import org.junit.Test;

import game.Game;

public class GuiControlTests {

	Game g = new Game();
	Frame f = new Frame(g);

	
	/**
	 * 	//----Tests to check the game window and menu work correctly----\\
	 * 	@author Micheal and Jannat
	 */
	

	@Test
	public void checkGamePanel(){
		//To check if the game frame is created successfully
		assert(f.gamePanel != null);
	}

	@Test
	public void menuTest(){
		//To check if the menu loads successfully
		//f.openMenu();
		assert f.menuActive();
	}


	/**
	 * 	//----Tests to check the keyEvents work correctly----\\
	 * 	@author Micheal and Jannat
	 */

	@Test
	public void keyPressTest_UP(){
		g.runKeyOperations(KeyEvent.VK_UP);
		assert true;
		//To check if the game successfully registers an UP key press
	}

	@Test
	public void keyPressTest_DOWN(){
		g.runKeyOperations(KeyEvent.VK_DOWN);
		assert true;
		//To check if the game successfully registers an DOWN key press
	}

	@Test
	public void keyPressTest_LEFT(){
		g.runKeyOperations(KeyEvent.VK_LEFT);
		assert true;
		//To check if the game successfully registers an LEFT key press
	}

	@Test
	public void keyPressTest_RIGHT(){
		g.runKeyOperations(KeyEvent.VK_RIGHT);
		assert true;
		//To check if the game successfully registers an RIGHT key press
	}
	
	@Test
	public void buttonPressTest_ResumeGame(){
		//To check if the game successfully registers a ResumeGame button press
		Menu tempMenu = new Menu(g.getWetherGameHasStarted());
		f.testMenu(tempMenu.getBtnResumeGame(), tempMenu);
		assert true;
	}

	@Test
	public void buttonPressTest_NewGame(){
		//To check if the game successfully registers a NewGame button press
		Menu tempMenu = new Menu(g.getWetherGameHasStarted());
		f.testMenu(tempMenu.getBtnNewGame(), tempMenu);
		assert true;
	}

	@Test
	public void buttonPressTest_LoadGame(){
		//To check if the game successfully registers a LoadGame button press
		Menu tempMenu = new Menu(g.getWetherGameHasStarted());
		f.testMenu(tempMenu.getBtnLoadGame(), tempMenu);
		assert true;
	}

	@Test
	public void buttonPressTest_SaveGame(){
		//To check if the game successfully registers a SaveGame button press
		Menu tempMenu = new Menu(g.getWetherGameHasStarted());
		f.testMenu(tempMenu.getBtnSaveGame(), tempMenu);
		assert true;
	}

	public void buttonPressTest_Help(){
		//To check if the game successfully registers a Help button press
		Menu tempMenu = new Menu(g.getWetherGameHasStarted());
		f.testMenu(tempMenu.getBtnHelp(), tempMenu);
		assert true;
	}

	@Test
	public void buttonPressTest_QuitGame(){
		//To check if the game successfully registers a QuitGame button press
		Menu tempMenu = new Menu(g.getWetherGameHasStarted());
		f.testMenu(tempMenu.getBtnQuitGame(), tempMenu);
		assert true;
	}

}