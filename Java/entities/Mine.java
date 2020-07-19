package entities;

import java.awt.Image;
import java.awt.Point;

import display.EntityImages;


public class Mine extends Entity {

	boolean exploded;
	
	public Mine(Point location) {
		super(location);
		damage = -100;
		fuelLoss = 0;
		exploded = false;
	}
	
	//Constructor used when loading a saved game
	public Mine(Point location, double damage, double fuelLoss, boolean exploded) {
		super(location,damage,fuelLoss);
		this.exploded = exploded;
	}
	
	/**
	 * explodes the mine
	 * @author maegan
	 */
	public void explode(){
		if(!exploded){
			exploded = true;
			location.x = location.x - EntityImages.getImage(this).getWidth()/2;
			location.y = location.y - EntityImages.getImage(this).getHeight()/2;
		}
	}
	
	/**
	 * determines whether the mine has exploded or not
	 * @author maegan
	 * @return
	 */
	public boolean hasExploded(){
		return exploded;
	}
	
	@Override
	public String export() {
		String entityPart = super.export();
		
		String minePart = "," + this.exploded;
		
		return entityPart + minePart;
	}

}