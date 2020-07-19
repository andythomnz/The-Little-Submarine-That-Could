package entities;

/**
 * Entity Abstract class
 * Author @Jannat Khan
 * This library is used for the Entity Component design, used for the NPC logic.
 */
import java.awt.Point;

import javafx.geometry.BoundingBox;

public abstract class ActiveEntity extends Entity{

	BoundingBox bounds;
	Point movement;
	int step;

	public ActiveEntity(Point location, Point movement, BoundingBox bounds) {
		super(location);
		this.movement = movement;
		this.bounds = bounds;
	}


	/**
	 * ActiveEntity constructor: used by all components extending from
	 *
	 * @param: Location
	 *             determining the 2D position of the object on the pane
	 * @param: damage,
	 *             the points taken off per collision.
	 * @param: fuelLoss,
	 *             the amount of fuel lost
	 * @param: bounds,
	 * 			   the bounding box for declared object
	 * @param :movement
	 * 				Point coordinate of how much a moving object will move.
	 */
	//Constructor used when loading a saved game
		public ActiveEntity(Point location, double damage, double fuelLoss, BoundingBox bounds, Point movement) {
			super(location, damage, fuelLoss);
			this.bounds = bounds;
			this.movement = movement;
		}

	/**
	 * Abstract decleration of move method.
	 * Individually implemented in concrete classes.
	 * This means every active entity must have a move method.
	 */
	public abstract void move();


	/**
	 * returns the location of the entity 
	 * @return
	 */
	public Point getLocation(){
		return location;
	}
	/**
	 * returns how the entity moves, aka speed in x and y directions
	 * @author Maegan
	 * @return
	 */
	public Point getMovement() {
		return movement;
	}

	/**
	 * returns the bounding box that determines where the entity can move on the screen
	 * @author Maegan
	 * @return
	 */
	public BoundingBox getBounds() {
		return bounds;
	}


	@Override
	/**
	 * Export method for files libraray.Exporting active entities requires a little more detail, hence the method decleration.
	 */
	public String export() {
		String entityPart = super.export();

		String activeEntityPart = "," +
								this.getMovement().getX() + "," +
								this.getMovement().getY();

		String bounds = ",null,null,null,null";

		if(this.getBounds() != null) {
			bounds = "," +
					this.getBounds().getMinX() + "," +
					this.getBounds().getMinY() + "," +
					this.getBounds().getWidth() + "," +
					this.getBounds().getHeight();
		}

		return entityPart + activeEntityPart + bounds;
	}

	/**
	 * Returns true when two active entities are equal, false when not.
	 * Used for testing.
	 */
	@Override
	public boolean equals(Object o){
		if(!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
			return false;	//if objects are not of the same type, return false;
		}

		ActiveEntity e = (ActiveEntity) o;
		Boolean sameLocation = this.getLocation().equals(e.getLocation());
		Boolean sameDamage = this.getDamage() == e.getDamage();
		Boolean sameFuelLoss = this.getFuelLoss() == e.getFuelLoss();

		Boolean sameMovement = this.getMovement().equals(e.getMovement());
		Boolean sameBounds = this.getBounds().equals(e.getBounds());


		return sameLocation && sameDamage && sameFuelLoss && sameMovement && sameBounds;
	}

}
