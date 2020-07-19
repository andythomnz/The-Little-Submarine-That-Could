/**
 * @author Maegan
 */

package collision;

import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.awt.Point;

import entities.Entity;
import entities.Player;
import game.Game;

public class CollisionDetector {
	
	public Game game;
	
	public CollisionDetector(Game g){
		this.game = g;
	}
	
	/**
	 * Determines whether a bounding box is going to intersect with another entities bounding box
	 * if it does, that entity is returned.
	 * @param potentialNewLocation
	 * @return
	 */
	public Entity collisionWithOtherEntity(BoundingBox potentialNewLocation){
		for(Entity other : game.getEntityList()){
			BoundingBox otherBox = other.getBoundingBox();
			if (otherBox.intersects(potentialNewLocation)){
				//collision
				return other;
			}
			//no collision, loop to next entity
		}
		//not colliding w anything currently
		return null;
	}
	
	/**
	 * Determines whether a bounding box is going to intersect with another 
	 * bounding box that surrounds the end of the level.
	 * if it does, the method returns true.
	 * @param potentialLocation
	 * @return
	 */
	public boolean collisionWithEndOfLevel(BoundingBox potentialLocation){
		BoundingBox endBounds = new BoundingBox(game.getXResolution()-100, game.getYResolution()-70, 100, 50);
		if(endBounds.intersects(potentialLocation)){
			return true;
		}
		return false;
	}
}
