package game;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import entities.Entity;

/**
 * @author maegan & andrew
 *
 */
public class gameAuthorTests {
	
	@Test
	public void test01() { //Verifying correct game resolution
		Game g = new Game();
		assertEquals(1300,g.getXResolution());
		assertEquals(700,g.getYResolution());
	}
	
	@Test
	public void test02() {	//Verifying correct frame rate
		Game g = new Game();
		assertEquals(60,g.getFPS());
	}
	
	@Test
	public void test03() {	//Verifying game has not started
		Game g = new Game();
		assertFalse(g.getWetherGameHasStarted());
	}
	
	@Test
	public void test04() {	//Verifying player is not null
		Game g = new Game();
		assertNotNull(g.getPlayer());
	}
	
	@Test
	public void test05() {	//Verifying level is not null
		Game g = new Game();
		assertNotNull(g.getLevel());
	}
	
	@Test
	public void test06() {	//Verifying level is instance of level one
		Game g = new Game();
		assertTrue(g.getLevel() instanceof LevelOne);
	}
	
	@Test
	public void test07() {	//Verifying entity list not null
		Game g = new Game();
		assertNotNull(g.getEntityList());
	}
	
	@Test
	public void test08() {	//Verifying entity list is not empty
		Game g = new Game();
		assertFalse(g.getEntityList().isEmpty());
	}
	
	@Test
	public void test09() {	//Verifying game progresses to level 2
		Game g = new Game();
		g.updateLevel();
		assertTrue(g.getLevel() instanceof LevelTwo);
	}
	
	@Test
	public void test10() {	//Verifying game progresses to level 3
		Game g = new Game();
		g.updateLevel();
		g.updateLevel();
		assertTrue(g.getLevel() instanceof LevelThree);
	}
	
	@Test
	public void test11() {	//Verifying resetting level creates new instance of same level
		Game g = new Game();
		Level before = g.getLevel();
		g.resetLevel();
		Level after = g.getLevel();
		
		assertTrue(before.getClass().getSimpleName().equals(after.getClass().getSimpleName()) && !(before==after));
	}
	
	

}
