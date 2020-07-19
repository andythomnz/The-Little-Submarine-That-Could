package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import game.LevelOne;
import game.LevelThree;
import game.LevelTwo;


/**
 * Status Display Class
 * Author @Andrew Thompson
 * This library is used to draw the status panel at the bottom of the screen.
 */
public class StatsBarDisplay extends JPanel {
	private Game game;
	
	public StatsBarDisplay(Game g) {
		this.game = g;
		super.setBackground(new Color(0, 0, 1));
		this.setVisible(true);
		JPanel panel = new JPanel();
		panel.setSize(game.getXResolution(), 60);
		panel.setBackground(new Color(0, 0, 1));
		panel.setVisible(true);
	}
	
	/* Draws a black panel with white text showing the health, fuel and number of deaths of the player*/
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.white);
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 2.0F);
		g.setFont(newFont);
		g.drawString("Health: " + (int) game.getPlayerHealth() + "%", 100, 35);
		g.drawString("Fuel: " + (int) game.getPlayerFuel() + "%", 600, 35);
		g.drawString("Deaths: " + game.getPlayer().getDeaths(), 1050, 35);
	}
	
	@Override
	public Dimension getPreferredSize() {
		int width = this.game.getXResolution();
		int height = 60;
		return new Dimension(width,height);
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
