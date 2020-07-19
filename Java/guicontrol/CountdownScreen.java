package guicontrol;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple display showing a countdown
 * timer before the game begins. Ensuring
 * a "clear start" to the game. 
 * @author andrew
 *
 */
public class CountdownScreen {
	
	JPanel panel;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(1300, 760);
		CountdownScreen cs = new CountdownScreen();
		f.getContentPane().add(cs.getPanel());
		f.setVisible(true);
	}
	
	public CountdownScreen() {
		panel = new JPanel();
		
		JLabel image = new JLabel();
		try {
			image = new JLabel(new ImageIcon(this.getClass().getResource("countdownScreenResources/countdown.gif")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		panel.setSize(1300,760);
		panel.add(image);
		panel.setVisible(true);
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
	

}
