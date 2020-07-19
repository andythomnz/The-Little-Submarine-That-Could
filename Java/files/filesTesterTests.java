package files;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import game.Game; 

/**
 * @author maegan
 *
 */
public class filesTesterTests {
	
	@Test
	public void T01_CheckEntityEquals(){
		Game g = new Game();
		FileSystem fs = g.getFS();
		ArrayList<Entity> e1;
		fs.setFileByPathString("src/resources/tester-file-system-testfiles/T01.sub");
		try{
			fs.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			fs.readFromFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void T02_BlankGameCheck(){
		Game g = new Game();
		FileSystem fs = g.getFS();
		List<String> lines;
		List<String> lines2;
		fs.setFileByPathString("src/resources/tester-file-system-testfiles/T02.sub");
		try{
			fs.writeToFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			lines = fs.readFromFile();
			lines2 = fs.parseOut(g);
			for(int i = 0; i<lines.size(); i++){
				String line = lines.get(i);
				String line2 = lines2.get(i+1);
				assertTrue("lines are not equal fileLine:" + line + " parsedLine:" + line2, line.equals(line2));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
