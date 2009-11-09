import java.awt.Color;
import java.awt.event.*;

import javax.swing.*;

public class test {
  public static void main(String[] args) throws Exception {
    JFrame frame = new JFrame("Parent");
    
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(800, 600);
    frame.setVisible(true);
    frame.setBackground(Color.blue);
    frame.setForeground(Color.blue);
    //frame.setOpaque(true);

    final JDialog dialog = new JDialog(frame, "Child", true);
    dialog.setBackground(Color.green);
    dialog.setForeground(Color.green);
    dialog.setSize(300, 200);
    dialog.setLocationRelativeTo(frame);
    JButton button = new JButton("Button");
    button.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dialog.dispose();
      }
    });
    //dialog.add(button);
    JPanel panel = new JPanel();
    panel.setBackground(Color.green);
    JLabel label = new JLabel("hi");
    panel.add(label);
    dialog.add(panel);
    dialog.setUndecorated(true);
    dialog.setVisible(true);
  }
}