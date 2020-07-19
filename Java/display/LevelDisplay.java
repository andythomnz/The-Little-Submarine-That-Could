package display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entities.ActiveEntity;
import entities.Entity;
import game.*;
import javafx.geometry.BoundingBox;


/**
 * Level Display Class
 * Author @Amy Wilson
 * This library is used to draw the game scene, including the background and the entities.
 */
public class LevelDisplay extends JPanel {

	private Game game;

	public LevelDisplay(Game game) {
		this.game = game;
		this.setSize(game.getXResolution(), game.getYResolution());
		this.setBackground(new Color(0, 0, 1));
		this.setVisible(true);
	}

	public void paintComponent(Graphics _g) {
		super.paintComponent(_g);
		Graphics2D g = (Graphics2D) _g;
		drawEntities(g);
	}

	private void drawEntities(Graphics2D g) {
		g.drawImage(game.getLevel().background, 0, 0, game.getXResolution(), game.getYResolution(), null);
		g.drawImage(EntityImages.getImage(game.getPlayer()), game.getPlayer().getLocation().x, game.getPlayer().getLocation().y,
				EntityImages.getImage(game.getPlayer()).getWidth(), EntityImages.getImage(game.getPlayer()).getHeight(), null);
		for (Entity e : game.getLevel().getLevelEntities()) {
			g.drawImage(EntityImages.getImage(e), e.getLocation().x, e.getLocation().y, EntityImages.getImage(e).getWidth(),
					EntityImages.getImage(e).getHeight(), null);
			
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(game.getXResolution(), game.getYResolution());
	}

	public JPanel getLevelDisplay() {
		return this;
	}

	public Game getGame() {
		return game;
	}
}
