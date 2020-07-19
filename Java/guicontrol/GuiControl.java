package guicontrol;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import display.LevelDisplay;
import display.StatsBarDisplay;
import entities.Rocks;
import game.Game;

public class GuiControl implements java.awt.event.KeyListener{ 
	
	Frame frame;
	
	
	public GuiControl(Frame f){
		this.frame = f;
	}
	
	//Action listener for NEW GAME
	public ActionListener newGameButton(){
		ActionListener listener = new ActionListener() { 
			//
			public void actionPerformed(ActionEvent e) { 
				newGameButtonAction();
			} 
		};
		return listener;
	}
	
	public void newGameButtonAction(){
		frame.remove(frame.menuPanel);
		frame.setInMenu(false);
		frame.getGame().reset();
		frame.openCountdownScreen();
	}
	
	//Action listener for LOAD GAME
	public ActionListener loadGameButton(){
		ActionListener listener = new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				try {
					frame.getGame().loadGame();
				} catch (Exception e1) {
				}
			} 
		};
		return listener;
	}

	
	//Action listener for SAVE GAME
	public ActionListener saveGameButton(){
		ActionListener listener = new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				try {
					frame.getGame().saveGame();
				} catch (Exception e1) {
				}
			} 
		};
		return listener;
	}
	
	
	//Action listener for HELP
	public ActionListener helpButton(){
		ActionListener listener =new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				frame.openHelpScreen();
			} 
		};
		return listener;
	}
		
	//Action listener for QUIT GAME
	public ActionListener quitGameButton(){
		ActionListener listener =new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				//Exit the game
				System.exit(0);
			} 
		};
		return listener;
	}
		
	//Action listener for RESUME GAME
	public ActionListener resumeGameButton(){
		ActionListener listener = new ActionListener() { 
			public void actionPerformed(ActionEvent e) { 
				resumeGameButtonAction();
			} 
		};
				
		return listener;
	}
	
	public void resumeGameButtonAction(){
		frame.setInMenu(false);
		frame.remove(frame.menuPanel);
		frame.add(frame.gamePanel);
		frame.add(frame.statusPanel);
		frame.setVisible(true);
		frame.setFocusable(true);
		frame.getGame().start();
		frame.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		frame.getGame().keyPress(e);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {
		frame.getGame().keyRelease(e);
	}
}
