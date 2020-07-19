package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import display.EntityImages;
import entities.Chain;
import entities.Entity;
import entities.FuelTopup;
import entities.Jellyfish;
import entities.Mine;
import entities.Pufferfish;
import entities.Rocks;
import entities.Shark;
import entities.Spanner;
import entities.TreasureChest;
import javafx.geometry.BoundingBox;

/**
 * @author andrew
 *
 */
public class LevelThree extends Level{
	
	public LevelThree(){
		ArrayList<Entity> entities = new ArrayList<Entity>();
		background = EntityImages.Images.BG_3.img;
		
		entities.add(new FuelTopup(new Point(70,575)));
		entities.add(new FuelTopup(new Point(250,500)));
		entities.add(new FuelTopup(new Point(830,350)));
		entities.add(new Mine(new Point(160,180)));
		entities.add(new Chain(new Point(185, 230)));
		entities.add(new Mine(new Point(330,300)));
		entities.add(new Chain(new Point(355, 350)));
		entities.add(new Mine(new Point(750,160)));
		entities.add(new Chain(new Point(775, 210)));
		entities.add(new Pufferfish(new Point(485,175), new Point(0,0), new BoundingBox(350, 100, 250, 150)));
		entities.add(new Pufferfish(new Point(1190,250), new Point(0,0), new BoundingBox(1030, 175, 250, 150)));
		entities.add(new Jellyfish(new Point(625,235), new Point(0,4), new BoundingBox(625, 235, 50, 400)));
		entities.add(new Jellyfish(new Point(825,50), new Point(4,0), new BoundingBox(825, 50, 450, 80)));
		entities.add(new Jellyfish(new Point(1200,150), new Point(4,0), new BoundingBox(900, 150, 350, 80)));
		entities.add(new TreasureChest(new Point(1200,600)));
		entities.add(new Shark(new Point(810,550),new Point(2,0), new BoundingBox(810, 300, 400, 300)));
		entities.add(new Spanner(new Point(485,600)));
		entities.add(new Rocks(new Point(0,0),3,"left"));
		entities.add(new Rocks(new Point(-8,635),3,"bottom"));
		this.levelEntities = entities;
		
	}
	
	public LevelThree(List<Entity> levelEntities) {
		this.levelEntities = (ArrayList<Entity>) levelEntities;
		background = EntityImages.Images.BG_3.img;
	}

}
