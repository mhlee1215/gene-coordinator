import org.jdesktop.swingx.*;
import org.jdesktop.swingx.icon.*;
import org.jdesktop.swingx.painter.*;


import etc.Colors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
/**
 * BusyLabelExample
 *
 * @author Nazmul Idris
 * @version 1.0
 * @since Feb 2, 2008, 2:43:30 PM
 */
public class BusyLabelExample {
/** simple main driver for this class */
public static void main(String[] args) {
  SwingUtilities.invokeLater(new Runnable() {
    public void run() {
      new BusyLabelExample();
    }
  });
}
/** creates a JFrame and calls {@link #doInit} to create a JXPanel and adds the panel to this frame. */
public BusyLabelExample() {
  JFrame frame = new JFrame("JXBusyLabel Example");
  // add the panel to this frame
  frame.add(doInit());
  // when you close the frame, the app exits
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  // center the frame and show it
  frame.setLocationRelativeTo(null);
  frame.pack();
  frame.setVisible(true);
}

 
/** creates a JXLabel and attaches a painter to it. */
private Component doInit() {
  JXPanel panel = new JXPanel();
  panel.setLayout(new BorderLayout());
  // create a busylabel
  final JXBusyLabel busylabel1 = createSimpleBusyLabel();
  final JXBusyLabel busylabel2 = createComplexBusyLabel();
  busylabel1.setEnabled(false);
  busylabel2.setEnabled(false);
  // create a label
  final JXLabel label = createLabel();
  // create a button
  JButton button = new JButton("start/stop");
  button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
      if (!busylabel1.isEnabled()){
        busylabel1.setEnabled(true);
        busylabel2.setEnabled(true);
      }
      if (busylabel1.isBusy()) {
        label.setText("BusyLabel stopped");
        busylabel1.setBusy(false);
        busylabel2.setBusy(false);
      }
      else {
        label.setText("BusyLabel started");
        busylabel1.setBusy(true);
        busylabel2.setBusy(true);
      }
    }
  });
  // set the transparency of the JXPanel to 50% transparent
  panel.setAlpha(0.7f);
  // add the label, busylables, and button to the panel
  panel.add(label, BorderLayout.NORTH);
  JXPanel busylabels = new JXPanel(new FlowLayout(FlowLayout.CENTER, 40, 5));
  busylabels.add(busylabel1);
  busylabels.add(busylabel2);
  panel.add(busylabels, BorderLayout.CENTER);
  panel.add(button, BorderLayout.SOUTH);
  panel.setPreferredSize(new Dimension(250, 125));
  return panel;
}
public JXBusyLabel createSimpleBusyLabel(){
  JXBusyLabel label = new JXBusyLabel();
  label.setToolTipText("simple busy label");
  return label;
}
public JXBusyLabel createComplexBusyLabel() {
  // this will not work in the 0.9.1 release of SwingX (need later builds)
  JXBusyLabel label = new JXBusyLabel(new Dimension(38, 38));
  BusyPainter painter = new BusyPainter(
      new java.awt.geom.Ellipse2D.Float(0, 0, 8.0f, 8.0f),
      new Ellipse2D.Float(5.5f, 5.5f, 27.0f, 27.0f));
  painter.setTrailLength(4);
  painter.setPoints(8);
  painter.setFrame(-1);
  painter.setBaseColor(Colors.LightBlue.color());
  painter.setHighlightColor(Colors.Orange.color());
  label.setPreferredSize(new Dimension(38, 38));
  label.setIcon(new EmptyIcon(38, 38));
  label.setBusyPainter(painter);
  label.setToolTipText("complex busy label");
  return label;
}
public JXLabel createLabel() {
  JXLabel label = new JXLabel();
  label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
  label.setFont(new Font("Segoe UI", Font.BOLD, 14));
  label.setText("<html>BusyLabel Example...<br>click start/stop button</html>");
 // label.setIcon(Images.NetworkDisconnected.getIcon(40, 40));
  label.setHorizontalAlignment(JXLabel.LEFT);
  label.setBackgroundPainter(getPainter());
  return label;
}
/** this painter draws a gradient fill */
public Painter getPainter() {
  int width = 100;
  int height = 100;
  Color color1 = Colors.White.color(0.5f);
  Color color2 = Colors.Gray.color(0.5f);
  LinearGradientPaint gradientPaint =
      new LinearGradientPaint(0.0f, 0.0f, width, height,
                              new float[]{0.0f, 1.0f},
                              new Color[]{color1, color2});
  MattePainter mattePainter = new MattePainter(gradientPaint);
  return mattePainter;
}
}//end class BusyLabelExample