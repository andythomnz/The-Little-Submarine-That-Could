package entities;

import java.awt.Point;

import display.EntityImages;
import javafx.geometry.BoundingBox;

public class Starfish extends ActiveEntity{

	Player player;
	private boolean stuck;
	int count = 0;
	
	public Starfish(Point location, Point movement, BoundingBox bounds) {
		super(location, movement, bounds);
		stuck = false;
		damage = -0.1;
		fuelLoss = -0.3;
	}
	
	//Constructor used when loading a saved game
	public Starfish(Point location, double damage, double fuelLoss, BoundingBox bounds, Point movement, Boolean stuck, int count) {
		super(location,damage,fuelLoss,bounds,movement);
		this.stuck = stuck;
		this.count = count;
	}
	
	/**
	 * Move method for starfish. 
	 */
	@Override
	public void move() {
		//when we want the starfish to be stuck to the submarine
		if(stuck&&count<100){
			int x = player.getLocation().x + EntityImages.getImage(player).getWidth()/2;
			int y = player.getLocation().y + EntityImages.getImage(player).getHeight()/2;
			location = new Point(x,y);
			//count basically determines how long the starfish is stuck to the player
			count++;
		}
		else{
			int x = movement.x + location.x;
			int y = movement.y + location.y;
			BoundingBox potentialNewLocation = new BoundingBox(x, y, EntityImages.getImage(this).getWidth(), EntityImages.getImage(this).getHeight());
			//if the starfish is about to go out of bounds(hits the ground)
			if (!bounds.contains(potentialNewLocation)) {
				movement.setLocation(0,0);
				return;
			}
			/**
			 * if it hasnt been stuck to the submarine yet
			 */
			if(!stuck&&count<100){
				//waits til the submarine is within 100px horizontally of it before starting to drop
				if(this.location.x < player.getLocation().getX()+200 && this.location.x>player.getLocation().getX()-200){
					location = new Point(x,y);
				}
			}
			/**
			 * if it has been stuck to the submarine and has timed out and is falling off
			 */
			else if(stuck&&count>=100){
				location = new Point(x,y);
			}
		}
	}
	
	/*
	 * setter method for indicating starfish is sticking to submarine
	 * @author Maegan
	 */
	public void stickToSub(boolean stuck){
		if(count == 0){
			this.stuck = stuck;
		}
	}
	
	/**
	 * indicates whether starfish is stuck to the submarine
	 * @author Maegan
	 * @return
	 */
	public boolean isStuck(){
		return stuck;
	}
	
	/**
	 * feeds the starfish the player everytime it needs to be used
	 * @param p
	 */
	public void feedPlayer(Player p){
		this.player = p;
	}

	@Override
	public String export() {
		String entityAndActiveEntityParts = super.export();
		
		String starfishPart = "," + this.stuck + "," + this.count;
		
		return entityAndActiveEntityParts + starfishPart;
	}
	
	/**
	 * Returns true when two Starfish entities are equal, false when not.
	 * Used for testing.
	 */
	@Override
	public boolean equals(Object o){
		if(!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {
			return false;	//if objects are not of the same type, return false;
		}
		
		Starfish s = (Starfish) o;
		Boolean sameLocation = this.getLocation().equals(s.getLocation());
		Boolean sameDamage = this.getDamage() == s.getDamage();
		Boolean sameFuelLoss = this.getFuelLoss() == s.getFuelLoss();
		
		Boolean sameMovement = this.getMovement().equals(s.getMovement());
		Boolean sameBounds = this.getBounds().equals(s.getBounds());
		
		Boolean sameStuck = this.isStuck() == s.isStuck();
		
		
		return sameLocation && sameDamage && sameFuelLoss && sameMovement && sameBounds && sameStuck;
	}

}
