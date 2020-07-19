package entities;

import java.awt.Point;


public class TreasureChest extends Entity{

	public TreasureChest(Point location) {
		super(location);
		damage = 0;
		fuelLoss = 0;
	}
	
	//Constructor used when loading a saved game
	public TreasureChest(Point location, double damage, double fuelLoss) {
		super(location,damage,fuelLoss);
	}

}
