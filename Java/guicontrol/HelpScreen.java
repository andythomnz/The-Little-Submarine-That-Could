package guicontrol;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpScreen {

	JPanel panel;
	JButton back;
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setSize(1300, 760);
		HelpScreen hs = new HelpScreen();
		f.getContentPane().add(hs.getPanel());
		f.setVisible(true);
	}
	
	public HelpScreen() {
		panel = new JPanel();
		JLabel image = new JLabel();
		try {
			image = new JLabel(new ImageIcon(this.getClass().getResource("helpScreenResources/help-screen.png")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		back = new JButton();
		try {
			back.setIcon(new ImageIcon(this.getClass().getResource("helpScreenResources/back.png")));
			back.setRolloverIcon(new ImageIcon(this.getClass().getResource("helpScreenResources/back-pressed.png")));
			back.setBorderPainted(false); 
			back.setContentAreaFilled(false); 
			back.setFocusPainted(false); 
			back.setOpaque(false);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		image.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		c.ipady = 40;
		c.weighty = 0.2;
		c.anchor = GridBagConstraints.PAGE_END;
		
		image.add(back, c);
		

		panel.setSize(1300,760);
		panel.add(image);
		panel.setVisible(true);
	}
	
	public JButton getBackBtn() {
		return this.back;
	}
	
	public JPanel getPanel() {
		return this.panel;
	}
}
