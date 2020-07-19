package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import display.EntityImages;
import display.EntityImages.Images;
import entities.Chain;
import entities.Entity;
import entities.Mine;
import entities.Trash;
import javafx.geometry.BoundingBox;
import entities.FuelTopup;
import entities.Jellyfish;
import entities.Pufferfish;
import entities.Spanner;
import entities.Rocks;
import entities.Starfish;

/**
 * @author amy
 *
 */
public class LevelOne extends Level {

	public LevelOne() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		background = EntityImages.Images.BG_1.img;
		entities.add(new Spanner(new Point(700, 625)));
		entities.add(new Chain(new Point(525, 250)));
		entities.add(new Mine(new Point(500, 200)));
		entities.add(new Chain(new Point(275, 550)));
		entities.add(new Mine(new Point(250, 500)));
		entities.add(new Chain(new Point(950, 550)));
		entities.add(new Mine(new Point(925, 500)));
		entities.add(new Chain(new Point(1125, 450)));
		entities.add(new Mine(new Point(1100, 400)));
		entities.add(new Jellyfish(new Point(450, 200), new Point(0,4), new BoundingBox(450, 200, Images.JELLYFISH.img.getWidth(), 450)));
		entities.add(new Jellyfish(new Point(1200, 350), new Point(0,4), new BoundingBox(1200, 0, Images.JELLYFISH.img.getWidth(), 700)));
		entities.add(new Trash(new Point(275, 60)));
		entities.add(new Trash(new Point(295, 70)));
		entities.add(new Trash(new Point(250, 90)));
		entities.add(new Trash(new Point(350, 500)));
		entities.add(new Trash(new Point(850, 120)));
		entities.add(new Trash(new Point(900, 100)));
		entities.add(new FuelTopup(new Point(350, 600)));
		entities.add(new FuelTopup(new Point(1025, 600)));
		entities.add(new FuelTopup(new Point(900, 600)));
		entities.add(new Rocks(new Point(0,0),1,"left"));
		entities.add(new Rocks(new Point(-8,635),1,"bottom"));
		entities.add(new Starfish(new Point(100, 10), new Point(0,2), new BoundingBox(50, 10, 1200, 650)));
		entities.add(new Starfish(new Point(650, 10), new Point(0,2), new BoundingBox(50, 10, 1200, 650)));
		this.levelEntities = entities;

	}
	
	//Constructor for use when loading saved game
	public LevelOne(List<Entity> levelEntities) {
		this.levelEntities = (ArrayList<Entity>) levelEntities;
		background = EntityImages.Images.BG_1.img;
	}

}
