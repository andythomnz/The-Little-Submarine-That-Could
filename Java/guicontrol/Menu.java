package guicontrol;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * Dynamically generates the main menu of our game.
 * Only shows some buttons when appropriate
 * E.g. 'Resume Game' is only shown when there is a game in progress.
 * @author andrew
 *
 */
public class Menu {
	Boolean resumeBtnAvailable;
	
	JButton btnResumeGame;
	JButton btnNewGame;
	JButton btnLoadGame;
	JButton btnSaveGame;
	JButton btnHelp;
	JButton btnQuitGame;

	JPanel menu;

	public Menu(Boolean resumeBtnAvailable) {
		this.resumeBtnAvailable = resumeBtnAvailable;
		menu = new JPanel();
		
		//Create the backdrop
		JLabel backdrop = new JLabel();
		try {
			backdrop = new JLabel(new ImageIcon(this.getClass().getResource("menuResources/backdrop-stretch.gif")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		backdrop.setLayout(new GridBagLayout());

		//Create the RESUME GAME button
		btnResumeGame = new JButton();
		try {
			btnResumeGame.setIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/resume.png")));
			btnResumeGame.setRolloverIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/resume_hover.png")));
			btnResumeGame.setBorderPainted(false); 
			btnResumeGame.setContentAreaFilled(false); 
			btnResumeGame.setFocusPainted(false); 
			btnResumeGame.setOpaque(false);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//Create the NEW GAME button
		btnNewGame = new JButton();
		try {
			btnNewGame.setIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/new.png")));
			btnNewGame.setRolloverIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/new_hover.png")));
			btnNewGame.setBorderPainted(false); 
			btnNewGame.setContentAreaFilled(false); 
			btnNewGame.setFocusPainted(false); 
			btnNewGame.setOpaque(false);
		}catch (Exception e) {
			e.printStackTrace();
		}

		//Create the LOAD GAME button
		btnLoadGame = new JButton();
		try {
			btnLoadGame.setIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/load.png")));
			btnLoadGame.setRolloverIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/load_hover.png")));
			btnLoadGame.setBorderPainted(false); 
			btnLoadGame.setContentAreaFilled(false); 
			btnLoadGame.setFocusPainted(false); 
			btnLoadGame.setOpaque(false);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//Create the SAVE GAME button
		btnSaveGame = new JButton();
		try {
			btnSaveGame.setIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/save.png")));
			btnSaveGame.setRolloverIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/save_hover.png")));
			btnSaveGame.setBorderPainted(false); 
			btnSaveGame.setContentAreaFilled(false); 
			btnSaveGame.setFocusPainted(false); 
			btnSaveGame.setOpaque(false);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//Create the HELP button
		btnHelp = new JButton();

		try {
			btnHelp.setIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/help.png")));
			btnHelp.setRolloverIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/help_hover.png")));
			btnHelp.setBorderPainted(false); 
			btnHelp.setContentAreaFilled(false); 
			btnHelp.setFocusPainted(false); 
			btnHelp.setOpaque(false);
		}catch (Exception e) {
			e.printStackTrace();
		}

		//Create the QUIT button
		btnQuitGame = new JButton();

		try {
			btnQuitGame.setIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/quit.png")));
			btnQuitGame.setRolloverIcon(new ImageIcon(this.getClass().getResource("menuResources/buttons/quit_hover.png")));
			btnQuitGame.setBorderPainted(false); 
			btnQuitGame.setContentAreaFilled(false); 
			btnQuitGame.setFocusPainted(false); 
			btnQuitGame.setOpaque(false);
		}catch (Exception e) {
			e.printStackTrace();
		}

		//Add the buttons to the backdrop
		GridBagConstraints c = new GridBagConstraints();

		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.ipady = 0;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.PAGE_END;

		if(resumeBtnAvailable) {
			backdrop.add(btnResumeGame, c);
			c.gridy++;
			c.weighty = 0;
		}
		
		backdrop.add(btnNewGame, c);
		c.gridy++;
		c.weighty = 0;
		
		backdrop.add(btnLoadGame, c);
		c.gridy++;
		
		if(resumeBtnAvailable) {	//Saving of game should also only be allowed if game has begun.
			backdrop.add(btnSaveGame, c);
			c.gridy++;
		}
		
		backdrop.add(btnHelp, c);
		c.gridy++;
		
		c.ipady = 50;
		backdrop.add(btnQuitGame, c);
		c.gridy++;

		//Add the contents to the menuPanel

		menu.setSize(1300,700);
		menu.add(backdrop);
		menu.setVisible(true);
	}
	
	
	
	public JPanel getPanel() {
		return this.menu;
	}

	public JButton getBtnResumeGame() {
		return btnResumeGame;
	}
	
	public JButton getBtnNewGame() {
		return btnNewGame;
	}

	public JButton getBtnLoadGame() {
		return btnLoadGame;
	}
	
	public JButton getBtnSaveGame() {
		return btnSaveGame;
	}
	
	public JButton getBtnHelp() {
		return btnHelp;
	}

	public JButton getBtnQuitGame() {
		return btnQuitGame;
	}

}
