// Copyright (c) 1996-99 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.ssu.ml.presentation;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.RefineryUtilities;
import org.ssu.ml.base.CGridHistogramData;
import org.ssu.ml.base.HistogramDescriptor;
import org.ssu.ml.base.UiGlobals;
import org.ssu.ml.ui.NodeRenderManager;
import org.tigris.gef.base.*;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.ui.*;
import org.tigris.gef.undo.RedoAction;
import org.tigris.gef.undo.UndoAction;
import org.tigris.gef.event.*;
import org.tigris.gef.graph.*;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.util.*;

/**
 * A window that displays a toolbar, a connected graph editing pane, and a
 * status bar.
 */

public class JGridHistogramFrame extends JFrame implements IStatusBar, Cloneable, ChangeListener{
	
	double data[];            
	int precise = 10;
	int beanCurValue = 10;
	JPanel histogram;

    private static final long serialVersionUID = -8167010467922210977L;
    /** The toolbar (shown at top of window). */
    private ToolBar _toolbar = new PaletteFig();
    /** The graph pane (shown in middle of window). */
    private JGraph _graph;
    /** A statusbar (shown at bottom ow window). */
    private JLabel _statusbar = new JLabel(" ");

    private JPanel _mainPanel = new JPanel(new BorderLayout());
    private JPanel _graphPanel = new JPanel(new BorderLayout());
    private JMenuBar _menubar = new JMenuBar();

    Dimension drawingSize = null;
    /**
     * Contruct a new JGraphFrame with the title "untitled" and a new
     * DefaultGraphModel.
     */
    public JGridHistogramFrame(String title) {
        super(title);
        setContentPane(_mainPanel);
        //add(_mainPanel, BorderLayout.CENTER);
        setUpToolbar();
       
    }

    public JGridHistogramFrame(String title, double[] data) {
        this(title);
        this.data = data;
    }
    
    public int getPrecise() {
		return precise;
	}

	public void setPrecise(int precise) {
		this.precise = precise;
	}


    // //////////////////////////////////////////////////////////////
    // Cloneable implementation

    public Object clone() {
        return null; // needs-more-work
    }
    


    public ToolBar getToolBar() {
        return _toolbar;
    }

    public void setToolBar(ToolBar tb) {
        _toolbar = tb;
        _mainPanel.add(_toolbar, BorderLayout.NORTH);
    }


    public void setUpToolbar()
    {
    	_toolbar = new ToolBar();
    	
    	int beanMax = 100;
		int beanMin = 1;
		beanCurValue = 10;
		JSlider beanResizer = new JSlider(JSlider.VERTICAL,
				beanMin, beanMax, beanCurValue);
		beanResizer.setName("beanResizer");
		beanResizer.setBackground(Color.white);
		Font font = new Font("Dialog.plain", 0, 10);
		
		JLabel minLabel = new JLabel("1");
		minLabel.setFont(font);
		JLabel maxLabel = new JLabel("100");
		maxLabel.setFont(font);
		Hashtable<Integer, JLabel> labelTable = 
            new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( beanMin ),
				minLabel );
		labelTable.put(new Integer( beanMax ),
				maxLabel );
		beanResizer.setLabelTable(labelTable);
		beanResizer.setPaintLabels(false);
		//gridResizer.setFont(font);
        beanResizer.setPaintLabels(true);
        beanResizer.setMajorTickSpacing(10);
        beanResizer.addChangeListener(this);
       // beanResizer.setPaintTicks(true);
        beanResizer.setMinorTickSpacing(5);
        BorderFactory a;
        
        
        //beanResizer.setBorder(
                //BorderFactory.createEmptyBorder(0,0,0,0)
                //BorderFactory.createLineBorder(Color.black, 1)
        		
        //        BorderFactory.createTitledBorder("Number of Bean")
        //        );
		_toolbar.add(beanResizer);
    	
    	 add(_toolbar, BorderLayout.WEST);
    }

    // //////////////////////////////////////////////////////////////
    // IStatusListener implementation

    /** Show a message in the statusbar. */
    public void showStatus(String msg) {
        if (_statusbar != null)
            _statusbar.setText(msg);
    }
    
    public void drawHistogram()
    {
    	histogram = createPanel();
    	histogram.setPreferredSize(new Dimension(700, 270));
        _mainPanel.add(histogram);
        //this.remove
    }
    
    private static IntervalXYDataset createDataset(double data[])
    {
    	return createDataset(data, 10);
    }
	private static IntervalXYDataset createDataset(double data[], int precise) {
		HistogramDataset histogramdataset = new HistogramDataset();
		histogramdataset.addSeries("Grid Density", data, precise);
		return histogramdataset;
	}
	
	private static JFreeChart createChart(IntervalXYDataset intervalxydataset)
	{
	     JFreeChart jfreechart = ChartFactory.createHistogram("Grid Density", "Density", "Frequency", intervalxydataset, PlotOrientation.VERTICAL, true, true, false);
	     XYPlot xyplot = (XYPlot)jfreechart.getPlot();
	     xyplot.setForegroundAlpha(0.85F);

	     return jfreechart;
	}
	
	public JPanel createPanel()
	{
		System.out.println("create Panel, precise : "+precise);
	     JFreeChart jfreechart = createChart(createDataset(data, precise));
	     return new ChartPanel(jfreechart);
	}
	

    public static void main(String[] argv)
    {
    	double data[] = {1, 2, 3, 2, 1, 4, 5, 3, 1, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	JGridHistogramFrame _jgf = new JGridHistogramFrame("title", data);
        _jgf.drawHistogram();
        _jgf.pack();
        RefineryUtilities.centerFrameOnScreen(_jgf);
        _jgf.setVisible(true);
        
    }

	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		if(source instanceof JSlider)
		{
			JSlider slider = (JSlider)source;
			String sliderName = slider.getName();
			//System.out.println(sliderName);
			if(sliderName != null){
				if (sliderName.equals("beanResizer")) {

					if (beanCurValue != slider.getValue()) {
						beanCurValue = slider.getValue();
						
						precise = beanCurValue;
						
						_mainPanel.remove(histogram);
						histogram = createPanel();
				    	histogram.setPreferredSize(new Dimension(700, 270));
				        _mainPanel.add(histogram);
				        
				        
						//this.repaint();
						histogram.repaint();
						
					}
				}
			}
		}
		
	}
} /* end class JGraphFrame */
