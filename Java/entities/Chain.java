package entities;

import java.awt.Point;

public class Chain extends Entity {

	public Chain(Point location) {
		super(location);
		// TODO Auto-generated constructor stub
		damage = 0;
		fuelLoss = 0;
	}
	
	//Constructor used when loading a saved game
	public Chain(Point location, double damage, double fuelLoss) {
		super(location,damage,fuelLoss);
	}

}