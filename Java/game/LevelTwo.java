package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import display.EntityImages;
import entities.FuelTopup;
import entities.Chain;
import entities.Entity;
import entities.Mine;
import entities.Trash;
import javafx.geometry.BoundingBox;
import entities.FuelTopup;
import entities.Jellyfish;
import entities.Pufferfish;
import entities.Rocks;
import entities.Spanner;
import entities.Starfish;

/**
 * @author amy
 *
 */
public class LevelTwo extends Level{

	public LevelTwo(){
		ArrayList<Entity> entities = new ArrayList<Entity>();
		background = EntityImages.Images.BG_2.img;
		entities.add(new Mine(new Point(450, 200)));
		entities.add(new Chain(new Point(475, 250)));
		entities.add(new Mine(new Point(150, 500)));
		entities.add(new Chain(new Point(175, 550)));
		entities.add(new Mine(new Point(800, 350)));
		entities.add(new Chain(new Point(825, 400)));
		entities.add(new Mine(new Point(1000, 300)));
		entities.add(new Chain(new Point(1025, 350)));
		entities.add(new Jellyfish(new Point(250, 200), new Point(0,4), new BoundingBox(250, 200, 50, 400)));
		entities.add(new Jellyfish(new Point(1100, 100), new Point(4,0), new BoundingBox(925, 100, 350, 80)));
		entities.add(new Jellyfish(new Point(1125, 200), new Point(4,0), new BoundingBox(975, 200, 300, 80)));
		entities.add(new Pufferfish(new Point(350, 125), new Point(0,0), new BoundingBox(225, 50, 250, 150)));
		entities.add(new Pufferfish(new Point(725, 100), new Point(0,0), new BoundingBox(600, 25, 250, 150)));
		entities.add(new FuelTopup(new Point(75, 575)));
		entities.add(new FuelTopup(new Point(900, 600)));
		entities.add(new Trash(new Point(775, 225)));
		entities.add(new Trash(new Point(200, 60)));
		entities.add(new Spanner(new Point(300, 625)));
		entities.add(new Spanner(new Point(600, 615)));
		entities.add(new Rocks(new Point(0,0),2,"left"));
		entities.add(new Rocks(new Point(-8,635),2,"bottom"));
		this.levelEntities = entities;
		
	}
	
	public LevelTwo(List<Entity> levelEntities) {
		this.levelEntities = (ArrayList<Entity>) levelEntities;
		background = EntityImages.Images.BG_2.img;
	}
}
