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
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.RefineryUtilities;
import org.tigris.gef.ui.*;

/**
 * A window that displays a toolbar, a connected graph editing pane, and a
 * status bar.
 */

public class JGridHistogramPanel extends JPanel implements IStatusBar, Cloneable, ChangeListener{
	
	Vector<double[]> datas = new Vector<double[]>();
	double data[];            
	int precise = 10;
	int beanCurValue = 10;
	JPanel _histogram;

    private static final long serialVersionUID = -8167010467922210977L;
    /** The toolbar (shown at top of window). */
    private ToolBar _toolbar = new PaletteFig();
    
    /** A statusbar (shown at bottom ow window). */
    private JLabel _statusbar = new JLabel(" ");
    private JPanel _mainPanel = new JPanel(new BorderLayout());
    

    HistogramDataset histogramdataset = new HistogramDataset();
    Dimension drawingSize = null;
    /**
     * Contruct a new JGraphFrame with the title "untitled" and a new
     * DefaultGraphModel.
     */
	public JGridHistogramPanel(String title) {
		super();
		add(_mainPanel);
		// add(_mainPanel, BorderLayout.CENTER);
		setUpToolbar();

		System.out.println("hi!");


    }

    public JGridHistogramPanel(String title, double[] data) {
        this(title);
        //this.data = data;
        datas.add(data);
    }
    
    public void addData(double[] data){
    	datas.add(data);
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
		_toolbar.setLayout(new GridLayout(2, 1));
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
        
        
        //BorderFactory a;
        //beanResizer.setBorder(
                //BorderFactory.createEmptyBorder(0,0,0,0)
                //BorderFactory.createLineBorder(Color.black, 1)
        		
        //        BorderFactory.createTitledBorder("Number of Bean")
        //        );
		_toolbar.add(beanResizer);
		
		JButton saveBtn = new JButton();
		saveBtn.setText("save");
		_toolbar.add(saveBtn);
    	
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
    	_histogram = createPanel();
    	//_histogram.setPreferredSize(new Dimension(700, 270));
        _mainPanel.add(_histogram, BorderLayout.CENTER);
        //this.remove
    }
    
    private static IntervalXYDataset createDataset(double data[])
    {
    	return createDataset(data, 10);
    }
    
    private static IntervalXYDataset createDataset(double data[], int precise){
    	HistogramDataset histogramdataset = new HistogramDataset();
    	return addDataset(histogramdataset, data, precise);
    }
    
	private static IntervalXYDataset addDataset(HistogramDataset histogramdataset, double data[], int precise) {	
		System.out.println(data);
		histogramdataset.addSeries("Grid Density", data, precise);
		return histogramdataset;
	}
	
	private static JFreeChart createChart(IntervalXYDataset intervalxydataset)
	{
	     JFreeChart jfreechart = ChartFactory.createHistogram("Grid Density", "Density", "Frequency", intervalxydataset, PlotOrientation.VERTICAL, true, true, false);
	     
	     XYPlot xyplot = (XYPlot)jfreechart.getPlot();
	     xyplot.setForegroundAlpha(0.3F);

	     return jfreechart;
	}
	
	public JPanel createPanel() {
		System.out.println("create Panel, precise : " + precise);
		for(int count = 0 ; count < datas.size() ; count++)
		{
			histogramdataset = (HistogramDataset) addDataset(histogramdataset, datas.get(count), precise);	
		}
		
		JFreeChart jfreechart = createChart(histogramdataset);
		XYPlot plot = jfreechart.getXYPlot();
		//jfreechart.get
		//XYItemRenderer renderer = plot.getRenderer();
		//renderer.set
		//BarRenderer renderer = (BarRenderer)plot.getRenderer();
		//renderer.set
		return new ChartPanel(jfreechart);
	}

	public void clean()
	{
		histogramdataset = new HistogramDataset();
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
				if (sliderName.equals("beanResizer")) {

					if (beanCurValue != slider.getValue()) {
						beanCurValue = slider.getValue();
						
						precise = beanCurValue;
						clean();
						_mainPanel.remove(_histogram);
						_histogram = createPanel();
				        _mainPanel.add(_histogram);
				        _histogram.revalidate();
					}
				}
			}
		}
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		System.out.println("Iam a repaint");
		
	}
} /* end class JGraphFrame */
