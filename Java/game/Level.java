package game;

import java.awt.Image;
import java.util.ArrayList;
import entities.Entity;

/**
 * @author Maegan
 *
 */
public abstract class Level {
	
	protected ArrayList<Entity> levelEntities = new ArrayList<Entity>();
	public Image background;
	
	public ArrayList<Entity> getLevelEntities(){
		return levelEntities;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!o.getClass().getSimpleName().equals(this.getClass().getSimpleName())) {return false;}
		Level l = (Level) o;
		return getLevelEntities().equals(l.getLevelEntities());
	}
}
