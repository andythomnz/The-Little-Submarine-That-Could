package entities;

import java.awt.Image;
import java.awt.Point;

import javafx.geometry.BoundingBox;


public class Pufferfish extends ActiveEntity {

	boolean puffed;
	Player player;
	
	public Pufferfish(Point location, Point movement, BoundingBox bounds) {
		super(location, movement, bounds);
		damage = -0.1;
		fuelLoss = 0 ;
		puffed = false;
	}
	
	//Constructor used when loading a saved game
	public Pufferfish(Point location, double damage, double fuelLoss, BoundingBox bounds, Point movement, boolean puffed) {
		super(location, damage,fuelLoss, bounds, movement);
		this.puffed = puffed;
	}
	
	/**
	 * flags that the puffer fish is puffed up. 
	 * called when the player gets within a certain distance of pufferfish
	 * @author michael
	 */
	
	@Override
	public void move(){
		puffed = bounds.contains(player.location.getX(), player.location.getY());
	}
	
	/**
	 * checks whether the fish is puffed or not.
	 * used during image generation
	 * 
	 * @author maegan
	 * @return
	 */
	public boolean isPuffed(){
		return puffed;
	}
	
	@Override
	public String export() {
		String activeEntityPart = super.export();
		return activeEntityPart + "," + this.puffed;
	}
	
	/**
	 * feeds the starfish the player everytime it needs to be used
	 * @param p
	 */
	public void feedPlayer(Player p){
		this.player = p;
	}

}
