package entities;

import java.awt.Image;
import java.awt.Point;


public class Spanner extends Entity {
	
	public Spanner(Point location) {
		super(location);
		// TODO Auto-generated constructor stub
		damage = 50;
		fuelLoss = 0;
	}
	
	//Constructor used when loading a saved game
	public Spanner(Point location, double damage, double fuelLoss) {
		super(location,damage,fuelLoss);
	}

}
