import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

public class AddingToScrollPane {

  public static void main(String args[]) {
    JFrame frame = new JFrame("Tabbed Pane Sample");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JLabel label = new JLabel("Label");
    label.setPreferredSize(new Dimension(1000, 1000));
    JPanel testPanel = new JPanel();
    JScrollPane jScrollPane = new JScrollPane(label);
    testPanel.setLayout(new BorderLayout());
    testPanel.add(jScrollPane, BorderLayout.CENTER);
    JButton jButton1 = new JButton();

    jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane.setViewportBorder(new LineBorder(Color.RED));
    //jScrollPane.getViewport().add(jButton1, null);

    frame.add(testPanel, BorderLayout.CENTER);
    frame.setSize(400, 150);
    frame.setVisible(true);
  }
}