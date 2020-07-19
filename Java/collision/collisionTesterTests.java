package collision;

import static org.junit.Assert.*;

import org.junit.Test;

import game.Game;
import javafx.geometry.BoundingBox;

/**
 * @author andrew
 *
 */
public class collisionTesterTests {
	
	@Test
	public void test01() { //Expecting there to be no collisions at the player's starting position
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertNull(cd.collisionWithOtherEntity(new BoundingBox(50,50,1,1)));
	}
	
	@Test
	public void test02() {	//At a known position of Trash expecting collision detector to report Trash
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertEquals("Trash",cd.collisionWithOtherEntity(new BoundingBox(250,60,50,50)).getClass().getSimpleName());
	}
	
	@Test
	public void test03() {	//At a known position of Starfish expecting collision detector to report Starfish
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertEquals("Starfish",cd.collisionWithOtherEntity(new BoundingBox(100,10,20,20)).getClass().getSimpleName());
	}
	
	@Test
	public void test04() {	//At a known position of Mine expecting collision detector to report Starfish
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertEquals("Mine",cd.collisionWithOtherEntity(new BoundingBox(250,500,20,20)).getClass().getSimpleName());
	}
	
	@Test
	public void test05() {	//At a known position of Chain expecting collision detector to report Chain
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertEquals("Chain",cd.collisionWithOtherEntity(new BoundingBox(250,500,50,50)).getClass().getSimpleName());
	}
	
	@Test
	public void test06() {	//At a known position of FuelTopup expecting collision detector to report FuelTopup
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertEquals("FuelTopup",cd.collisionWithOtherEntity(new BoundingBox(350,600,50,50)).getClass().getSimpleName());
	}
	
	@Test
	public void test07() {	//At a known position of Jellyfish expecting collision detector to report Jellyfish
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertEquals("Jellyfish",cd.collisionWithOtherEntity(new BoundingBox(445,200,50,50)).getClass().getSimpleName());
	}
	
	@Test
	public void test08() {	//At a known position of Spanner expecting collision detector to report Spanner
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertEquals("Spanner",cd.collisionWithOtherEntity(new BoundingBox(700,625,50,50)).getClass().getSimpleName());
	}
	
	@Test
	public void test09() {	//At the end of level position, expect collision detector to report true for colliding with end of level
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertTrue(cd.collisionWithEndOfLevel(new BoundingBox(g.getXResolution()-100, g.getYResolution()-100, 100, 50)));
	}
	
	@Test
	public void test10() {	//Check that collision detector does not report the player colliding with itself
		Game g = new Game();
		CollisionDetector cd = g.getPlayer().getCD();
		assertNull(cd.collisionWithOtherEntity(new BoundingBox(g.getPlayer().getLocation().x,g.getPlayer().getLocation().y,10,10)));
	}
}
