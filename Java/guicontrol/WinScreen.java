package guicontrol;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Simple display showing a 'you won'
 * animation to the player in order to 
 * create a "clear ending" to the game.
 * @author andrew
 *
 */
public class WinScreen {

	JPanel panel;

	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(1300, 760);
		WinScreen ws = new WinScreen();
		f.getContentPane().add(ws.getPanel());
		f.setVisible(true);
		
	}
	
	public WinScreen() {
		panel = new JPanel();
		JLabel image = new JLabel();
		try {
			image = new JLabel(new ImageIcon(this.getClass().getResource("winScreenResources/win-screen-final.gif")));
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
