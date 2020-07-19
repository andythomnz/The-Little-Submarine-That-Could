/**
 * @author khannuru
 */
package entities;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class JannatsJUnitTests {

	@Test
	public void test() {
		fail("Implemented");
	}

	/**
	 * Player is not null, even in a negative location, player is not null.
	 */
	@Test
	public void PlayerTest1(){
		Point location = new Point(-5,-5);
		Point movement = new Point(5,5);
		Entity e = new Player(location, movement, null, null, null );
		assertNotNull(e);
	}

	/**
	 * Overlapping entities, use rock and trash for this for this.
	 */
	@Test
	public void EntitiesTest1(){
		Point location = new Point(10,10);
		Entity a = new Rocks(location);
		Entity b = new Trash(location);
		a.equals(b);
		fail("Trash equals to Rock");

	}

	/**
	 * Check to see if the Player actually moved
	 */
	@Test
	public void PlayerTest2(){
		Point location = new Point(10,10);
		Point movement = new Point(5,5);
		Player p = new Player(location, movement, null, null, null);
		p.move();
		assert(p.location == new Point(15,15));
	}

	/**
	 * Check that load game constructor in entity works
	 */
	@Test
	public void EntitiesTest2(){
		Point location = new Point(20,20);
		Entity e = new Spanner (location,50,0);
		System.out.println(e.location + " "+ e.damage + " " + e.fuelLoss);
		fail("Load game doesn't work");

	}

	/**
	 * Check to see if two players are the same or not.
	 */
	@Test
	public void PlayerTest3() {
		Point location = new Point(10,10);
		Point movement = new Point(5,5);
		Player a = new Player(movement, movement, null, null, null);
		Player b = new Player(movement, movement, null, null, null);
		a.equals(b);
		fail("Two players are the same");
	}

}
