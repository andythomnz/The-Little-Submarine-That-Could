package entities;

import java.awt.Point;

import display.EntityImages;
import javafx.geometry.BoundingBox;

public class Shark extends ActiveEntity{
	
	boolean following = false;
	Player player;
	double step = 2;
	boolean goingLeft = false;

	public Shark(Point location, Point movement, BoundingBox bounds) {
		super(location, movement, bounds);
		damage = -0.5;
		fuelLoss = 0;
		}
	
	//Constructor used when loading a saved game
	public Shark(Point location, double damage, double fuelLoss, BoundingBox bounds, Point movement) {
		super(location,damage,fuelLoss,bounds,movement);
	}
	
	@Override
	public void move() {
		following = bounds.contains(player.location.getX(), player.location.getY());
		
		if(!following){
			if(movement.getY()!=0){
				movement.setLocation(step, 0);
			}
		}
		else{
			//distance x and y between shark and player
			Point difference = new Point(player.getLocation().x-this.location.x, player.getLocation().y-this.location.y);
			//if the shark is further away from the player on the x axis than on the y
			if(Math.abs(difference.x)>Math.abs(difference.y)){
				if(difference.x<0){
					//go left
					movement.setLocation(-step, 0);
				}
				else{
					//go right
					movement.setLocation(step, 0);
				}
			}
			else if(Math.abs(difference.x)<Math.abs(difference.y)){
				if(difference.y<0){
					//go up
					movement.setLocation(0, -step);
				}
				else{
					//go down
					movement.setLocation(0, step);
				}
			}
		}
		int x = movement.x + location.x;
		int y = movement.y + location.y;
		BoundingBox potentialNewLocation = new BoundingBox(x, y, EntityImages.getImage(this).getWidth(), EntityImages.getImage(this).getHeight());
		if (!bounds.contains(potentialNewLocation)) {
			movement.setLocation(-movement.getX(),-movement.getY());
			
		}
		goingLeft = movement.getX()<0;
		location.setLocation(x, y);
	}

	/**
	 * feeds the shark the player every time it needs to use it. 
	 * called in game class.
	 * @param p
	 */
	public void feedPlayer(Player p){
		this.player = p;
	}
	
	/**
	 * returns boolean that determines whether it is going left or not. 
	 * used during image generation for entity
	 * @author maegan
	 * @return
	 */
	public boolean isGoingLeft(){
		return goingLeft;
	}

}
