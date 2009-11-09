import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;


public class TestGridBackLayout {
	public static void main(String[] argv)
	{
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(500, 500));
		boolean shouldFill = true;
		boolean shouldWeightX = true;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		JButton button;
		
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//c.fill = GridBagConstraints.HORIZONTAL;

		button = new JButton("Button 1");
		c.weightx = 0.1;
		c.ipady = 40;      //make this component tall
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(button, c);

		button = new JButton("Button 2");
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 4;
		pane.add(button, c);

		button = new JButton("Button 3");
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 2;
		c.gridy = 0;
		pane.add(button, c);

		button = new JButton("Long-Named Button 4");
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(button, c);

		button = new JButton("5");
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;       //reset to default
		c.weighty = 0.2;   //request any extra vertical space
		//c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(10,0,0,0);  //top padding
		c.gridx = 1;       //aligned with button 2
		c.gridwidth = 2;   //2 columns wide
		c.gridy = 2;       //third row
		pane.add(button, c);
		
		//frame.pack();
		frame.setVisible(true);
	}
}
