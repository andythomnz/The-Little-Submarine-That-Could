package entities;

import java.awt.Image;
import java.awt.Point;


public class Rocks extends Entity{
	int level;
	String side;
	String imgName;

	public Rocks(Point location) {
		super(location);
		//location = this.setStartingLocation(double x, double y);
		damage = 0;
		fuelLoss = 0;
	}
	
	public Rocks(Point location, int level, String side) {
		super(location);
		damage = 0;
		fuelLoss = 0;
		if(level > 0 && level < 4) {
			this.level = level;
			imgName = "";
			if(side.equalsIgnoreCase("left")) {
				imgName = "ROCKS_" + level + "_LEFT";
				this.side = "LEFT";
			}else {
				imgName = "ROCKS_" + level + "_BOTTOM";
				this.side = "BOTTOM";
			}
		}
	}
	
	//Constructor used when loading a saved game
	public Rocks(Point location, double damage, double fuelLoss, int level, String side) {
		super(location,damage,fuelLoss);
		if(level > 0 && level < 4) {
			this.level = level;
			imgName = "";
			if(side.equalsIgnoreCase("left")) {
				imgName = "ROCKS_" + level + "_LEFT";
				this.side = "LEFT";
			}else {
				imgName = "ROCKS_" + level + "_BOTTOM";
				this.side = "BOTTOM";
			}
		}
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public String getSide() {
		return this.side;
	}
	
	@Override
	public String export() {
		String entityPart = super.export();
		return entityPart + "," + this.level + "," + this.side;
	}
	
	public String getImgName() {
		return this.imgName;
	}
	
	/**
	 * Returns true when two Rocks entities are equal, false when not.
	 * Used for testing.
	 */
	@Override
	public boolean equals(Object o){
		if(!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
			return false;	//if objects are not of the same type, return false;
		}
		
		Rocks r = (Rocks) o;
		Boolean sameLocation = this.getLocation().equals(r.getLocation());
		Boolean sameDamage = this.getDamage() == r.getDamage();
		Boolean sameFuelLoss = this.getFuelLoss() == r.getFuelLoss();
		
		Boolean sameLevel = this.getLevel() == r.getLevel();
		Boolean sameSide = this.getSide().equalsIgnoreCase(r.getSide());
		
		return sameLocation && sameDamage && sameFuelLoss && sameLevel && sameSide;
	}

}
