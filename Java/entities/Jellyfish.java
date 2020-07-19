package entities;

import java.awt.Image;
import java.awt.Point;

import display.EntityImages;
import javafx.geometry.BoundingBox;

public class Jellyfish extends ActiveEntity {

	public Jellyfish(Point location, Point movement, BoundingBox bounds) {
		super(location, movement, bounds);
		this.damage = -0.7;
		this.fuelLoss = 0;

	}
	
	//Constructor used when loading a saved game
	public Jellyfish(Point location, double damage, double fuelLoss, BoundingBox bounds, Point movement) {
		super(location,damage,fuelLoss,bounds,movement);
	}

	/**
	 * jellyfish move method. basically just moves back and forward within it's bounding box
	 * @author maegan
	 */
	@Override
	public void move() {
		int x = movement.x + location.x;
		int y = movement.y + location.y;
		BoundingBox potentialNewLocation = new BoundingBox(x, y, EntityImages.getImage(this).getWidth(), EntityImages.getImage(this).getHeight());
		if (!bounds.contains(potentialNewLocation)) {
			movement.setLocation(-movement.getX(),-movement.getY());

		}
		location.setLocation(x, y);
	}
}