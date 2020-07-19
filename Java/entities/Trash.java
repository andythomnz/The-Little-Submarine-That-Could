package entities;

import java.awt.Image;
/**
 * Trash class
 * Author @Jannat Khan
 * Static Entity- Trash
 */
import java.awt.Point;


public class Trash extends Entity {
	
	public Trash(Point location) {
		super(location);
		damage = 0;
		fuelLoss = 0;
	}
	
	//Constructor used when loading a saved game
	public Trash(Point location, double damage, double fuelLoss) {
		super(location,damage,fuelLoss);
	}

	

}
