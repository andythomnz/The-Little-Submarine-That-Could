package files;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import game.Game;

/**
 * @author andrew
 *
 */
public class filesAuthorTests {
	
	@Test
	public void test01_check_file_name() {
		FileSystem fs = new FileSystem(null);
		fs.setFileByPathString("src/resources/file-system-test-files/Test01.sub");
		assertEquals("Test01.sub",fs.getCurrentFile().getName());
	}
	
	@Test
	public void test02_check_file_path() {
		FileSystem fs = new FileSystem(null);
		fs.setFileByPathString("src/resources/file-system-test-files/Test01.sub");
		assertEquals("src/resources/file-system-test-files/Test01.sub",fs.getCurrentFile().getPath());
	}
	
	@Test
	public void test03_check_parsed_out_game_not_null() {
		FileSystem fs = new FileSystem(null);
		fs.setFileByPathString("src/resources/file-system-test-files/Test01.sub");
		List<String> lines = null;
		try {
			lines = fs.readFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(lines==null);
	}
	
	@Test
	public void test04_check_parsed_out_game_not_empty() {
		FileSystem fs = new FileSystem(null);
		fs.setFileByPathString("src/resources/file-system-test-files/Test01.sub");
		List<String> lines = null;
		try {
			lines = fs.readFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse(lines.isEmpty());
	}
	
	@Test
	public void test05_check_parsed_out_game_size() {
		FileSystem fs = new FileSystem(null);
		fs.setFileByPathString("src/resources/file-system-test-files/Test03.sub");
		List<String> lines = null;
		try {
			lines = fs.readFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(40,lines.size());
	}
	
	@Test
	public void test06_check_parsed_out_game_contents() {
		FileSystem fs = new FileSystem(null);
		fs.setFileByPathString("src/resources/file-system-test-files/Test03.sub");
		List<String> lines = null;
		try {
			lines = fs.readFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		List<String> lines2 = new ArrayList<>();
		lines2.add("<<SUB>>");
		lines2.add("<META>");
		lines2.add("[NAME]The Little Submarine That Could[/NAME]");
		lines2.add("[TYPE]SAVED GAME[/TYPE]");
		lines2.add("[INFO]File Created On: [10/10/2017 @ 11:50][/INFO]");
		lines2.add("</META>");
		lines2.add("<GAME>");
		lines2.add("[GAME]1300,700,60[/GAME]");
		lines2.add("</GAME>");
		lines2.add("<PLAYER>");
		lines2.add("[PLAYER]Player,50.0,50.0,0,0,3.0,3.0,null,null,null,null,6750,200[/PLAYER]");
		lines2.add("</PLAYER>");
		lines2.add("<LEVEL>");
		lines2.add("[LEVELONE]18[/LEVELONE]");
		lines2.add("</LEVEL>");
		lines2.add("<ENTITIES>");
		lines2.add("[MINE]Mine,500.0,200.0,100,0[/MINE]");
		lines2.add("[MINE]Mine,250.0,500.0,100,0[/MINE]");
		lines2.add("[MINE]Mine,925.0,500.0,100,0[/MINE]");
		lines2.add("[MINE]Mine,1100.0,400.0,100,0[/MINE]");
		lines2.add("[STARFISH]Starfish,100.0,10.0,0,0,0.0,700.0,null,null,null,null,false[/STARFISH]");
		lines2.add("[STARFISH]Starfish,650.0,10.0,0,0,800.0,700.0,null,null,null,null,false[/STARFISH]");
		lines2.add("[JELLYFISH]Jellyfish,450.0,400.0,0,0,4.0,4.0,null,null,null,null[/JELLYFISH]");
		lines2.add("[JELLYFISH]Jellyfish,1200.0,350.0,0,0,4.0,4.0,null,null,null,null[/JELLYFISH]");
		lines2.add("[TRASH]Trash,275.0,60.0,0,-5[/TRASH]");
		lines2.add("[TRASH]Trash,295.0,70.0,0,-5[/TRASH]");
		lines2.add("[TRASH]Trash,250.0,90.0,0,-5[/TRASH]");
		lines2.add("[TRASH]Trash,350.0,500.0,0,-5[/TRASH]");
		lines2.add("[TRASH]Trash,850.0,120.0,0,-5[/TRASH]");
		lines2.add("[TRASH]Trash,900.0,100.0,0,-5[/TRASH]");
		lines2.add("[FUELTOPUP]FuelTopup,350.0,600.0,0,50[/FUELTOPUP]");
		lines2.add("[FUELTOPUP]FuelTopup,1025.0,600.0,0,50[/FUELTOPUP]");
		lines2.add("[ROCKS]Rocks,0.0,0.0,0,0,1,LEFT[/ROCKS]");
		lines2.add("[ROCKS]Rocks,-8.0,635.0,0,0,1,BOTTOM[/ROCKS]");
		lines2.add("</ENTITIES>");
		lines2.add("<META>");
		lines2.add("[INFO] Game export completed in 0 seconds.[/INFO]");
		lines2.add("[INFO] Writing to file completed in 0 seconds.[/INFO]");
		lines2.add("</META>");
		lines2.add("<</SUB>>");
		
		for(int i=0; i<lines2.size(); i++) {
			assertEquals(lines2.get(i),lines.get(i));
		}
		
	}
	
	@Test
	public void test07_check_parsed_in_game_not_null() {
		Game g = new Game();
		FileSystem fs = g.getFS();
		List<String> lines1 = fs.parseOut(g);
		fs.setFileByPathString("src/resources/file-system-test-files/xyz.sub");
		try {
			fs.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> lines2 = null;
		try {
			lines2 = fs.readFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		assertTrue(lines1!=null);
		assertTrue(lines2!=null);
		
	}
	
	@Test
	public void test08_check_parsed_in_game_not_empty() {
		Game g = new Game();
		FileSystem fs = g.getFS();
		List<String> lines1 = fs.parseOut(g);
		fs.setFileByPathString("src/resources/file-system-test-files/xyz.sub");
		try {
			fs.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> lines2 = null;
		try {
			lines2 = fs.readFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		assertFalse(lines1.isEmpty());
		assertFalse(lines2.isEmpty());
		
	}
	
	@Test
	public void test09_check_parsed_in_contents_equals_parsed_out_contents() {
		Game g = new Game();
		FileSystem fs = g.getFS();
		List<String> lines1 = fs.parseOut(g);
		fs.setFileByPathString("src/resources/file-system-test-files/xyz.sub");
		try {
			fs.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> lines2 = null;
		try {
			lines2 = fs.readFromFile();
			lines2.remove(lines2.size()-1);
			lines2.remove(lines2.size()-1);
			lines2.remove(lines2.size()-1);
		} catch (Exception e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		assertEquals(lines1,lines2);
	}
	
	@Test
	public void test10_check_loaded_game_has_same_resolution() {
		Game g1 = new Game();
		FileSystem fs1 = g1.getFS();
		fs1.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs1.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game g2 = new Game();
		FileSystem fs2 = g2.getFS();
		fs2.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs2.parseIn();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(g1.getXResolution(),g2.getXResolution());
		assertEquals(g1.getYResolution(),g2.getYResolution());
		
	}
	
	@Test
	public void test11_check_loaded_game_has_same_frame_rate() {
		Game g1 = new Game();
		FileSystem fs1 = g1.getFS();
		fs1.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs1.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game g2 = new Game();
		FileSystem fs2 = g2.getFS();
		fs2.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs2.parseIn();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(g1.getFPS(),g2.getFPS());
		
	}
	
	@Test
	public void test12_check_loaded_game_has_same_state() {
		Game g1 = new Game();
		FileSystem fs1 = g1.getFS();
		fs1.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs1.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game g2 = new Game();
		FileSystem fs2 = g2.getFS();
		fs2.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs2.parseIn();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(g1.getWetherGameHasStarted(),g2.getWetherGameHasStarted());
		
	}
	
	@Test
	public void test13_check_loaded_game_has_same_player() {
		Game g1 = new Game();
		FileSystem fs1 = g1.getFS();
		fs1.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs1.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game g2 = new Game();
		FileSystem fs2 = g2.getFS();
		fs2.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs2.parseIn();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(g1.getPlayer(),g2.getPlayer());
		
	}
	
	@Test
	public void test14_check_loaded_game_has_same_entities() {
		Game g1 = new Game();
		FileSystem fs1 = g1.getFS();
		fs1.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs1.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game g2 = new Game();
		FileSystem fs2 = g2.getFS();
		fs2.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs2.parseIn();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(g1.getEntityList(),g2.getEntityList());
		
	}
	
	@Test
	public void test15_check_loaded_game_has_same_level() {
		Game g1 = new Game();
		FileSystem fs1 = g1.getFS();
		fs1.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs1.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game g2 = new Game();
		FileSystem fs2 = g2.getFS();
		fs2.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs2.parseIn();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(g1.getLevel(),g2.getLevel());
		
	}
	
	@Test
	public void test16_check_loaded_game_is_same_as_saved_game() {
		Game g1 = new Game();
		FileSystem fs1 = g1.getFS();
		fs1.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs1.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Game g2 = new Game();
		FileSystem fs2 = g2.getFS();
		fs2.setFileByPathString("src/resources/file-system-test-files/test10.sub");
		try {
			fs2.parseIn();
		} catch (Exception e) { 
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(g1,g2);
		
	}
	
	
	
	
	
	
	

	
	

}
