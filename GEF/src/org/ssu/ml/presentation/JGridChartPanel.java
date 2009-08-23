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
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.RefineryUtilities;
import org.tigris.gef.ui.*;

/**
 * A window that displays a toolbar, a connected graph editing pane, and a
 * status bar.
 */

public class JGridChartPanel extends JGridPanel implements IStatusBar, Cloneable, ChangeListener{
	
	private static final String SERIES_GOOD = "Good";
	private static final String SERIES_BIG = "Big";
	private static final String SERIES_SMALL = "Small";
    
	Vector<double[]> datas = new Vector<double[]>();
	Vector<String> categories = new Vector<String>();
	double data[];            
	//int precise = 10;
	
	JSlider maxRanger;
	JSlider minRanger;
	int rangeMax = 100;
	int rangeMin = 0;
	int maxCurValue;
	int minCurValue;
	int minimum_interval = 5;
	
	JPanel _chart;

    private static final long serialVersionUID = -8167010467922210977L;
    /** The toolbar (shown at top of window). */
    private ToolBar _toolbar = new PaletteFig();
    
    /** A statusbar (shown at bottom ow window). */
    private JLabel _statusbar = new JLabel(" ");
    private JPanel _mainPanel = new JPanel(new BorderLayout());
    

    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    Dimension drawingSize = null;
    /**
     * Contruct a new JGraphFrame with the title "untitled" and a new
     * DefaultGraphModel.
     */
	public JGridChartPanel(String title) {
		super();
		add(_mainPanel);
		// add(_mainPanel, BorderLayout.CENTER);
		setUpToolbar();
		setStatusBar();

		System.out.println("hi!");


    }

    public JGridChartPanel(String title, double[] data, String category) {
        this(title);
        //this.data = data;
        datas.add(data);
        categories.add(category);
    }
    
    public void addData(double[] data, String category){
    	datas.add(data);
    	categories.add(category);
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
    
    public void setStatusBar(){
    	_mainPanel.add(_statusbar, BorderLayout.SOUTH);
    }


    public void setUpToolbar()
    {
    	_toolbar = new ToolBar();
    	
    	
		maxCurValue = 50;
		minCurValue = 10;
		_toolbar.setLayout(new GridLayout(2, 1));
		
		{
			maxRanger = new JSlider(JSlider.VERTICAL,
					rangeMin+minimum_interval, rangeMax, maxCurValue);
			maxRanger.setName("maxRanger");
			maxRanger.setBackground(Color.white);
			JLabel minLabel = new JLabel(Integer.toString(rangeMin+minimum_interval));
			JLabel maxLabel = new JLabel(Integer.toString(rangeMax));
			Hashtable<Integer, JLabel> labelTable = 
	            new Hashtable<Integer, JLabel>();
			labelTable.put(new Integer( rangeMin+minimum_interval ),
					minLabel );
			labelTable.put(new Integer( rangeMax ),
					maxLabel );
			maxRanger.setLabelTable(labelTable);
			maxRanger.setPaintLabels(false);
			//gridResizer.setFont(font);
	        maxRanger.setPaintLabels(true);
	        maxRanger.setMajorTickSpacing(10);
	        maxRanger.setMinorTickSpacing(5);
	        maxRanger.addChangeListener(this);
	        maxRanger.setPaintTicks(true);
	        maxRanger.setMinorTickSpacing(5);
	        _toolbar.add(maxRanger);
		}
        
		{
	        minRanger = new JSlider(JSlider.VERTICAL,
					rangeMin, rangeMax-minimum_interval, minCurValue);
			minRanger.setName("minRanger");
			minRanger.setBackground(Color.white);
			
			JLabel minLabel = new JLabel(Integer.toString(rangeMin));
			JLabel maxLabel = new JLabel(Integer.toString(rangeMax-minimum_interval));
			Hashtable<Integer, JLabel> labelTable = 
	            new Hashtable<Integer, JLabel>();
			labelTable.put(new Integer( rangeMin ),
					minLabel );
			labelTable.put(new Integer( rangeMax-minimum_interval ),
					maxLabel );
			minRanger.setLabelTable(labelTable);
			minRanger.setPaintLabels(false);
			//gridResizer.setFont(font);
	        minRanger.setPaintLabels(true);
	        minRanger.setMajorTickSpacing(10);
	        minRanger.setMinorTickSpacing(5);
	        minRanger.addChangeListener(this);
	        
	        minRanger.setPaintTicks(true);
	        minRanger.setMinorTickSpacing(5);
	        
			_toolbar.add(minRanger);
			
		}
    	
		_mainPanel.add(_toolbar, BorderLayout.WEST);
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
    	_chart = createPanel();
    	//_histogram.setPreferredSize(new Dimension(700, 270));
        _mainPanel.add(_chart, BorderLayout.CENTER);
        //this.remove
    }
    
	
	private CategoryDataset addDataset(double data[], String categoryName){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		return addDataset(dataset, data, categoryName);
	}
	
	private CategoryDataset addDataset(DefaultCategoryDataset categoryDataset, double data[], String categoryName){
		int overCount = 0;
		int goodCount = 0;
		int lowerCount = 0;
        
		for(int count = 0 ; count < data.length ; count++)
		{
			if(data[count] > maxCurValue)
				overCount++;
			else if(data[count] < minCurValue)
				lowerCount++;
			else
				goodCount++;
		}
		System.out.println("data.length : "+data.length);
		System.out.println("goodCount : "+goodCount+", overCount  :"+overCount+", : lowerCount"+lowerCount);
		categoryDataset.addValue(goodCount, SERIES_GOOD, categoryName);
		categoryDataset.addValue(overCount, SERIES_BIG, categoryName);
		categoryDataset.addValue(lowerCount, SERIES_SMALL, categoryName);
		return categoryDataset;
	}
	
	
	
	private static JFreeChart createChart(CategoryDataset categoryDataset)
	{
	    
	     JFreeChart jfreechart = ChartFactory.createBarChart(
	             "Block size counting for construction of gene sets",         // chart title
	             "grid space",               // domain axis label
	             "count",                  // range axis label
	             categoryDataset,                  // data
	             PlotOrientation.VERTICAL, // orientation
	             true,                     // include legend
	             true,                     // tooltips?
	             false                     // URLs?
	         );
	     
	     //XYPlot xyplot = (XYPlot)jfreechart.getPlot();
	     //xyplot.setForegroundAlpha(0.3F);

	     return jfreechart;
	}
	
	public JPanel createPanel() {
		//System.out.println("create Panel, precise : " + precise);
		for(int count = 0 ; count < datas.size() ; count++)
		{
			categoryDataset = (DefaultCategoryDataset) addDataset(categoryDataset, datas.get(count), categories.get(count));	
		}
		
		JFreeChart jfreechart = createChart(categoryDataset);
		//XYPlot plot = jfreechart.getXYPlot();
		//jfreechart.get
		//XYItemRenderer renderer = plot.getRenderer();
		//renderer.set
		//BarRenderer renderer = (BarRenderer)plot.getRenderer();
		//renderer.set
		return new ChartPanel(jfreechart);
	}

	public void clean()
	{
		categoryDataset = new DefaultCategoryDataset();
	}
    public static void main(String[] argv)
    {
    	/*
    	double data[] = {1, 2, 3, 2, 1, 4, 5, 3, 1, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	double data1[] = {3, 3, 3, 1, 1, 2, 2, 3, 3, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	JGridHistogramFrame _jgf = new JGridHistogramFrame("title", data);
    	_jgf.addData(data1);
        _jgf.drawHistogram();
        _jgf.pack();
        RefineryUtilities.centerFrameOnScreen(_jgf);
        _jgf.setVisible(true);
        */
    }

	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		if(source instanceof JSlider)
		{
			JSlider slider = (JSlider)source;
			String sliderName = slider.getName();
			if(sliderName != null){
				if (sliderName.equals("maxRanger")) {

					if (maxCurValue != slider.getValue()) {
						maxCurValue = slider.getValue();
						
						
						if(maxCurValue - minimum_interval < minCurValue){
							minCurValue = maxCurValue - minimum_interval;
							minRanger.setValue(minCurValue);
						}
					}
				}else if (sliderName.equals("minRanger")) {

					if (minCurValue != slider.getValue()) {
						minCurValue = slider.getValue();
						
						
						if(maxCurValue < minCurValue + minimum_interval){
							maxCurValue = minCurValue + minimum_interval;
							maxRanger.setValue(maxCurValue);
						}
					}
				}
				clean();
				_mainPanel.remove(_chart);
				_chart = createPanel();
		        _mainPanel.add(_chart);
		        _chart.revalidate();
		        
		        this.showStatus("Range : ["+maxCurValue+" to "+minCurValue+"]");
					
			}
		}
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		System.out.println("Iam a repaint");
		
	}
} /* end class JGraphFrame */
