package entities;

/**
 * Entity Abstract class
 * Author @Jannat Khan
 * This library is used for the Entity Component design, used for the NPC logic.
 */

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.*;

import display.EntityImages;
import javafx.geometry.BoundingBox;

public abstract class Entity {

	Point location;
	double damage;
	double fuelLoss;

	public Entity(Point location) {
		super();
		this.location = location;
	}

	/**
	 * Entity constructor: used by all components.
	 *
	 * @param: Location
	 *             determining the 2D position of the object on the pane
	 * @param: damage,
	 *             the points taken off per collision.
	 * @param: fuelLoss,
	 *             the amount of fuel lost
	 */
	//Constructor used when loading a saved game
	public Entity(Point location, double damage, double fuelLoss) {
		super();
		this.location = location;
		this.damage = damage;
		this.fuelLoss = fuelLoss;
	}

	//gets the damage
	public double getDamage(){
		return damage;
	}

	//gets change in fuel
	public double getFuelLoss(){
		return fuelLoss;
	}

	//gets the location
	public Point getLocation(){
		return location;
	}

	/** 
	 * generates a bounding box for the collision detection
	 * @author maegan
	 * @return
	 */
	public BoundingBox getBoundingBox() {
		int height = EntityImages.getImage(this).getHeight();
		int width = EntityImages.getImage(this).getWidth();
		return new BoundingBox(location.getX(), location.getY(), width, height);
	}

	//The following sections of code will be used for game loading and saving. Andrew's part.

	/**
	 * Export method to be used for Andrew's saving aspect of the game design.
	 * doesn't return all fields, only the ones stores in the abstract class so
	 * where it is, how much damage it holds Andrew, please let me know of your
	 * thoughts and feel free to change this method and the following
	 * constructor
	 *
	 * @return location, damage, fuelLoss.
	 */
	public String export() {
		return this.getClass().getSimpleName() + "," +
				this.getLocation().getX() + "," +
				this.getLocation().getY() + "," +
				this.damage + "," +
				this.fuelLoss;
	}

	/**
	 * Returns true when two entities are equal, false when not.
	 * Used for testing.
	 */
	@Override
	public boolean equals(Object o){
		if(!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
			return false;	//if objects are not of the same type, return false;
		}

		Entity e = (Entity) o;
		Boolean sameLocation = this.getLocation().equals(e.getLocation());
		Boolean sameDamage = this.getDamage() == e.getDamage();
		Boolean sameFuelLoss = this.getFuelLoss() == e.getFuelLoss();

		return sameLocation && sameDamage && sameFuelLoss;
	}
}
