package entities;

import java.awt.Image;
import java.awt.Point;
/**
 * Fuel topup class
 * Only increases fuel status.
 * @author Jannat
 *
 */
public class FuelTopup extends Entity{

	public FuelTopup(Point location) {
		super(location);
		damage = 0;
		fuelLoss = +50;
	}
	
	//Constructor used when loading a saved game
	public FuelTopup(Point location, double damage, double fuelLoss) {
		super(location,damage,fuelLoss);
	}

}
