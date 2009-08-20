/**
 * Chart2D, a java library for drawing two dimensional charts.
 * Copyright (C) 2001 Jason J. Simas
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * The author of this library may be contacted at:
 * E-mail:  jjsimas@users.sourceforge.net
 * Street Address:  J J Simas, 887 Tico Road, Ojai, CA 93023-3555 USA
 */



import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import net.sourceforge.chart2d.Chart2D;
import net.sourceforge.chart2d.Chart2DProperties;
import net.sourceforge.chart2d.Dataset;
import net.sourceforge.chart2d.GraphChart2DProperties;
import net.sourceforge.chart2d.GraphProperties;
import net.sourceforge.chart2d.LBChart2D;
import net.sourceforge.chart2d.LegendProperties;
import net.sourceforge.chart2d.MultiColorsProperties;
import net.sourceforge.chart2d.Object2DProperties;
import net.sourceforge.chart2d.WarningRegionProperties;

import java.awt.Color;
import java.util.Random;


/**
 * A Chart2D demo demonstrating the LBChart2D object.
 * Container Class: JFrame<br>
 * Program Types:  Applet or Application<br>
 */
public class LBChart2DFrameDemo extends JApplet {


  private JFrame frame = null;
  private static boolean isApplet = true;


  /**
   * For running as an application.
   * Calls init() and start().
   * @param args An unused parameter.
   */
  public static void main (String[] args) {

    isApplet = false;
    LBChart2DFrameDemo demo = new LBChart2DFrameDemo();
    demo.init();
    demo.start();
    //exit on frame close event
  }


  /**
   * Configure the chart and frame, and open the frame.
   */
  public void init() {

    //Start configuring a JFrame GUI with a JTabbedPane for multiple chart panes
    JTabbedPane panes = new JTabbedPane();

    panes.addTab ("Bar", getChart2DDemoA());
    

    //JTabbedPane specific GUI code
    //Chart2D by default magnifies itself on user resize, the magnification
    //raio is based on each chart's preferred size.  So that all the charts
    //have the same magnification ratio, we must make all the charts have the
    //same preferred size.  You can calculate each chart's preferred size
    //dynamically (slower), OR you can pick a size that is at least a big as
    //its dynamic preferred size and use this statically -- permanently.
    //I recommend using dynamic calc while writing your code, then for
    //production make sure to use the static code for the performance increase.
    //Add a System.out.println (size) with the dynamic code, and use that size
    //for your static code size.
    //Also, setting the panes preferred size with a static size pushes
    //calculation of charts in non-visible panes off, until they are visible.
    //This means that start up time with a static panes size is the same as if
    //you had only one Chart2D object.
    boolean dynamicSizeCalc = false;
    if (dynamicSizeCalc) {
      int maxWidth = 0;
      int maxHeight = 0;
      for (int i = 0; i < panes.getTabCount(); ++i) {
        Chart2D chart2D = (Chart2D)panes.getComponentAt (i);
        chart2D.pack();
        Dimension size = chart2D.getSize();
        maxWidth = maxWidth > size.width ? maxWidth : size.width;
        maxHeight = maxHeight > size.height ? maxHeight : size.height;
      }
      Dimension maxSize = new Dimension (maxWidth, maxHeight);
      System.out.println (maxSize);
      for (int i = 0; i < panes.getTabCount(); ++i) {
        Chart2D chart2D = (Chart2D)panes.getComponentAt (i);
        chart2D.setSize (maxSize);
        chart2D.setPreferredSize (maxSize);
      }
      System.out.println (panes.getPreferredSize());
    }
    else {
      Dimension maxSize = new Dimension (561, 214);
      for (int i = 0; i < panes.getTabCount(); ++i) {
        Chart2D chart2D = (Chart2D)panes.getComponentAt (i);
        chart2D.setSize (maxSize);
        chart2D.setPreferredSize (maxSize);
      }
      panes.setPreferredSize (new Dimension (566 + 5, 280 + 5)); //+ 5 slop
    }

    frame = new JFrame();
    frame.getContentPane().add (panes);
    frame.setTitle ("LBChart2DFrameDemo");
    frame.addWindowListener (
      new WindowAdapter() {
        public void windowClosing (WindowEvent e) {
          destroy();
    } } );
    frame.pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    frame.setLocation (
      (screenSize.width - frame.getSize().width) / 2,
      (screenSize.height - frame.getSize().height) / 2);
  }


  /**
   * Shows the JFrame GUI.
   */
  public void start() {
    frame.show();
  }


  /**
   * Ends the application or applet.
   */
  public void destroy() {

    if (frame != null) frame.dispose();
    if (!isApplet) System.exit (0);
  }


  /**
   * Builds the demo chart.
   * @return The demo chart.
   */
  private Chart2D getChart2DDemoA() {

    //<-- Begin Chart2D configuration -->

    //Configure object properties
    Object2DProperties object2DProps = new Object2DProperties();
    object2DProps.setObjectTitleText ("Amperage Used Per Appliance");

    //Configure chart properties
    Chart2DProperties chart2DProps = new Chart2DProperties();
    chart2DProps.setChartDataLabelsPrecision (-1);

    //Configure legend properties
    LegendProperties legendProps = new LegendProperties();
    String[] legendLabels = {"2002", "2001", "2000"};
    legendProps.setLegendLabelsTexts (legendLabels);

    //Configure graph chart properties
    GraphChart2DProperties graphChart2DProps = new GraphChart2DProperties();
    String[] labelsAxisLabels = {"Computer", "Monitor", "AC", "Lighting", "Refrigerator"};
    graphChart2DProps.setLabelsAxisLabelsTexts (labelsAxisLabels);
    graphChart2DProps.setLabelsAxisTitleText ("Appliances");
    graphChart2DProps.setNumbersAxisTitleText ("Amps on 120 Volt Line");

    //Configure graph properties
    GraphProperties graphProps = new GraphProperties();

    //Configure dataset
    Dataset dataset = new Dataset (3, 5, 1);
    dataset.set (0, 0, 0, 3.5f);
    dataset.set (0, 1, 0, 2.5f);
    dataset.set (0, 2, 0, 10.0f);
    dataset.set (0, 3, 0, 0.5f);
    dataset.set (0, 4, 0, 2.0f);
    dataset.set (1, 0, 0, 3.0f);
    dataset.set (1, 1, 0, 2.0f);
    dataset.set (1, 2, 0, 10.0f);
    dataset.set (1, 3, 0, 1.0f);
    dataset.set (1, 4, 0, 2.0f);
    dataset.set (2, 0, 0, 2.5f);
    dataset.set (2, 1, 0, 2.0f);
    dataset.set (2, 2, 0, 10.5f);
    dataset.set (2, 3, 0, 1.0f);
    dataset.set (2, 4, 0, 2.5f);

    //Configure graph component colors
    MultiColorsProperties multiColorsProps = new MultiColorsProperties();

    //Configure chart
    LBChart2D chart2D = new LBChart2D();
    chart2D.setObject2DProperties (object2DProps);
    chart2D.setChart2DProperties (chart2DProps);
    chart2D.setLegendProperties (legendProps);
    chart2D.setGraphChart2DProperties (graphChart2DProps);
    chart2D.addGraphProperties (graphProps);
    chart2D.addDataset (dataset);
    chart2D.addMultiColorsProperties (multiColorsProps);

    //Optional validation:  Prints debug messages if invalid only.
    if (!chart2D.validate (false)) chart2D.validate (true);

    //<-- End Chart2D configuration -->

    return chart2D;
  }


  /**
   * Builds the demo chart.
   * @return The demo chart.
   */
  
}