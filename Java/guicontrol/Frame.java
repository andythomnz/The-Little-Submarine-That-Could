package guicontrol;

import game.Game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import display.LevelDisplay;
import display.StatsBarDisplay;

public class Frame extends javax.swing.JFrame{

	private boolean inMenu = true;
	Menu mainMenu;
	JPanel menuPanel = new JPanel();
	JPanel gamePanel = new JPanel();
	JPanel statusPanel;
	JPanel winPanel;
	JPanel helpPanel;
	JPanel countdownPanel;
	Timer gameWonTimer;
	Timer countdownTimer;

	private int width;
	private int height;

	LevelDisplay currentDisplay;
	Game game;
	private GuiControl control = new GuiControl(this);

	public Frame(Game g) {
		width = g.getXResolution();
		height = g.getYResolution()+60;
		setTitle("The Little Submarine That Could");
		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setResizable(false);
		addKeyListener(getControl());
		game = g;
		generateMenu();
		add(menuPanel);
		setVisible(true);
		setFocusable(true);
	}

	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	public boolean menuActive(){
		return inMenu;
	}

	public void openMenu(){
		inMenu = true;
		game.stop();
		remove(gamePanel);
		remove(statusPanel);
		generateMenu();
		add(menuPanel);
		setVisible(true);
	}

	public void openHelpScreen() {
		remove(menuPanel);
		HelpScreen hs = new HelpScreen();
		hs.getBackBtn().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remove(helpPanel);
				generateMenu();
				add(menuPanel);
				setVisible(true);
			}
		} );
		helpPanel = hs.getPanel();
		add(helpPanel);
		setVisible(true);

	}

	public void openWinScreen() {
		remove(gamePanel);
		remove(statusPanel);
		WinScreen ws = new WinScreen();
		winPanel = ws.getPanel();
		add(winPanel);
		setVisible(true);
		gameWonTimer = new Timer(14000, this::closeWinScreen);
		gameWonTimer.start();
	}

	private void closeWinScreen(ActionEvent e) {
		remove(winPanel);
		game.setGameAsNotStarted();
		generateMenu();
		add(menuPanel);
		setVisible(true);
		gameWonTimer.stop();
	}

	public void openCountdownScreen() {
		remove(menuPanel);
		CountdownScreen cs = new CountdownScreen();
		countdownPanel = cs.getPanel();
		add(countdownPanel);
		setVisible(true);
		countdownTimer = new Timer(4000, this::closeCountdownScreen);
		countdownTimer.start();
	}

	private void closeCountdownScreen(ActionEvent e) {
		remove(countdownPanel);
		currentDisplay = new LevelDisplay(getGame());
		gamePanel = currentDisplay;
		statusPanel = new StatsBarDisplay(getGame());
		add(gamePanel);
		add(statusPanel);
		setVisible(true);
		setFocusable(true);
		getGame().setGameAsStarted(); // Next time generating Menu screen "resume" & "save" buttons will be displayed.
		countdownTimer.stop();
		getGame().start();
		repaint();

	}

	public void setLevelDisplay(LevelDisplay display){
		currentDisplay = display;
	}

	public void setGamePanel(JPanel game){
		gamePanel = game;
	}

	public LevelDisplay getLevelDisplay() {
		return this.currentDisplay;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Game getGame(){
		return game;
	}

	public void transitionToSavedGame(LevelDisplay currentDisplay) {
		remove(menuPanel);
		inMenu = false;
		this.currentDisplay = currentDisplay;
		gamePanel = currentDisplay;
		statusPanel = new StatsBarDisplay(game);
		add(gamePanel);
		add(statusPanel);
		addKeyListener(getControl());
		setFocusable(true);
		setVisible(true);
	}

	public void setInMenu(boolean state){
		inMenu = state;
	}

	public void generateMenu() {
		//Setup the menu
		mainMenu = new Menu(game.getWetherGameHasStarted()); //if there is a game in progress, will include 'resume' & 'save' buttons.

		//Action listener for RESUME GAME
		mainMenu.getBtnResumeGame().addActionListener(getControl().resumeGameButton());

		//Action listener for NEW GAME
		mainMenu.getBtnNewGame().addActionListener(getControl().newGameButton());

		//Action listener for LOAD GAME
		mainMenu.getBtnLoadGame().addActionListener(getControl().loadGameButton());

		//Action listener for SAVE GAME
		mainMenu.getBtnSaveGame().addActionListener(getControl().saveGameButton());

		//Action listener for HELP
		mainMenu.getBtnHelp().addActionListener(getControl().helpButton());

		//Action listener for QUIT GAME
		mainMenu.getBtnQuitGame().addActionListener(getControl().quitGameButton());

		menuPanel = mainMenu.getPanel();
	}

	public GuiControl getControl() {
		return control;
	}

	public void setControl(GuiControl control) {
		this.control = control;
	}
	
	public JPanel getMenuPanel() {
		return menuPanel;
	}
	
	public void testMenu(JButton button, Menu thisMenu) {
		//Setup the menu
		
		if(button == thisMenu.getBtnResumeGame()) {
			thisMenu.getBtnResumeGame().addActionListener(getControl().resumeGameButton());
		} 
		else if(button == thisMenu.getBtnNewGame()) {
			thisMenu.getBtnNewGame().addActionListener(getControl().newGameButton());
		} 
		else if(button == thisMenu.getBtnLoadGame()) {
			thisMenu.getBtnLoadGame().addActionListener(getControl().loadGameButton());
		} 
		else if(button == thisMenu.getBtnSaveGame()) {
			thisMenu.getBtnSaveGame().addActionListener(getControl().saveGameButton());
		} 
		else if(button == thisMenu.getBtnHelp()) {
			thisMenu.getBtnHelp().addActionListener(getControl().helpButton());
		} 
		else if(button == thisMenu.getBtnQuitGame()) {
			thisMenu.getBtnQuitGame().addActionListener(getControl().quitGameButton());
		}
		
		menuPanel = thisMenu.getPanel();
	}


}