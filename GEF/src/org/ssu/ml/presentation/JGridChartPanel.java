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
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.CombinedDomainCategoryPlot;
import org.jfree.chart.plot.CombinedRangeCategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarPainter;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.RefineryUtilities;
import org.ssu.ml.base.UiGlobals;
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
	
	ToolBar sideToolbar;

    private static final long serialVersionUID = -8167010467922210977L;
    
    

    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    Dimension drawingSize = null;
    /**
     * Contruct a new JGraphFrame with the title "untitled" and a new
     * DefaultGraphModel.
     */
	public JGridChartPanel(String title) {
		super();
		setUpSideToolbar();
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
    


    

    public void setUpSideToolbar()
    {
    	sideToolbar = new ToolBar();
    	
    	
		maxCurValue = 50;
		minCurValue = 10;
		sideToolbar.setLayout(new GridLayout(2, 1));
		
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
	        sideToolbar.add(maxRanger);
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
	        
			sideToolbar.add(minRanger);
			
		}
    	
		mainPanel.add(sideToolbar, BorderLayout.WEST);
    }


    // //////////////////////////////////////////////////////////////
    // IStatusListener implementation

    
    public void drawHistogram()
    {
    	_chart = createPanel();
    	//_histogram.setPreferredSize(new Dimension(700, 270));
    	mainPanel.add(_chart, BorderLayout.CENTER);
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
	   
	     jfreechart.setBackgroundPaint(Color.WHITE);
	     CategoryPlot plot = jfreechart.getCategoryPlot();  // 챠트의 Plot 객체를 구한다.
	     plot.setBackgroundPaint(Color.white);     // 챠트의 Plot 배경색을 lightGray로 바꾼다.
	     plot.setRangeGridlinePaint(Color.BLUE);       // 수평 그리드라인의 색을 BLUE로 바꾼다.
	     plot.setDomainGridlinesVisible(true);        // 수직 그리드라인을 안보이게 한다.
	     plot.setDomainGridlinePaint(Color.blue);
	     plot.setOutlinePaint(Color.blue);	//차트 외곽선
	     plot.setOutlineVisible(true);
	     plot.setAnchorValue(10);
	     
	     
	     // 6. 봉을 커스터마이즈하기
        BarRenderer renderer = (BarRenderer) plot.getRenderer();  // BarRenderer를 구한다.
        
        renderer.setItemMargin(0.05);                 // 봉과 봉사이의 여백을 정한다.
        renderer.setDrawBarOutline(true);            // 봉의 경계선 표시를 설정
        //renderer.setOutlinePaint(Color.red);
        BarPainter painter = new StandardBarPainter();
        renderer.setBarPainter(painter);
        Stroke stroke = new BasicStroke(3.0f);
        //stroke.
        
        
        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setSeriesOutlinePaint(1, Color.black);
        renderer.setSeriesOutlinePaint(2, Color.black);
        renderer.setSeriesOutlineStroke(0, stroke);
        renderer.setSeriesOutlineStroke(1, stroke);
        renderer.setSeriesOutlineStroke(2, stroke);
        renderer.setShadowXOffset(10.0);
        renderer.setShadowYOffset(10.0);
        
        StandardCategoryItemLabelGenerator labelGenerator = new StandardCategoryItemLabelGenerator();
        renderer.setItemLabelFont(UiGlobals.getNormalFont());
        renderer.setItemLabelGenerator(labelGenerator);
        renderer.setItemLabelPaint(Color.black);
        renderer.setItemLabelsVisible(true);
        
        NumberFormat format = NumberFormat.getInstance();
        String labelFormat = "<html><body style=\"background-color: #ffffdd\"><h1>{0}</h1><br><h2>{1}</h2><br><h3>{2}</h3></body></html>";
        StandardCategoryToolTipGenerator generator = new StandardCategoryToolTipGenerator(labelFormat, format);
        //System.out.println(generator.getLabelFormat());
        
        renderer.setSeriesToolTipGenerator(0, generator);
        renderer.setSeriesToolTipGenerator(1, generator);
        renderer.setSeriesToolTipGenerator(2, generator);
        
        // 7.봉색깔 바꾸기
        
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(204, 255, 153),  0.0f, 0.0f, new Color(204, 255, 153));
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0, 64, 0));
        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,   0.0f, 0.0f, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, UiGlobals.getConstantColor().getColor(0));
        renderer.setSeriesPaint(1, UiGlobals.getConstantColor().getColor(1));
        renderer.setSeriesPaint(2, UiGlobals.getConstantColor().getColor(2));
        
        
	     //XYPlot xyplot = (XYPlot)jfreechart.getPlot();
	     //xyplot.setForegroundAlpha(0.3F);

	     return jfreechart;
	}
	
	public CategoryPlot createCategoryPlot(double[] data, String categori){
		NumberAxis rangeAxis1 = new NumberAxis(categori);
		rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		CategoryAxis categoryAxis = new CategoryAxis("a");
		
		BarRenderer renderer = new BarRenderer();
		renderer.setItemMargin(0.05);                 // 봉과 봉사이의 여백을 정한다.
        renderer.setDrawBarOutline(true);            // 봉의 경계선 표시를 설정
        //renderer.setOutlinePaint(Color.red);
        BarPainter painter = new StandardBarPainter();
        renderer.setBarPainter(painter);
        Stroke stroke = new BasicStroke(3.0f);
        //stroke.
        
        
        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setSeriesOutlinePaint(1, Color.black);
        renderer.setSeriesOutlinePaint(2, Color.black);
        renderer.setSeriesOutlineStroke(0, stroke);
        renderer.setSeriesOutlineStroke(1, stroke);
        renderer.setSeriesOutlineStroke(2, stroke);
        renderer.setShadowXOffset(10.0);
        renderer.setShadowYOffset(10.0);
        
        StandardCategoryItemLabelGenerator labelGenerator = new StandardCategoryItemLabelGenerator();
        renderer.setItemLabelFont(UiGlobals.getNormalFont());
        renderer.setItemLabelGenerator(labelGenerator);
        renderer.setItemLabelPaint(Color.black);
        renderer.setItemLabelsVisible(true);
        
        NumberFormat format = NumberFormat.getInstance();
        String labelFormat = "<html><body style=\"background-color: #ffffdd\"><h1>{0}</h1><br><h2>{1}</h2><br><h3>{2}</h3></body></html>";
        StandardCategoryToolTipGenerator generator = new StandardCategoryToolTipGenerator(labelFormat, format);
        //System.out.println(generator.getLabelFormat());
        
        renderer.setSeriesToolTipGenerator(0, generator);
        renderer.setSeriesToolTipGenerator(1, generator);
        renderer.setSeriesToolTipGenerator(2, generator);
        
        // 7.봉색깔 바꾸기
        renderer.setSeriesPaint(0, UiGlobals.getConstantColor().getColor(0));
        renderer.setSeriesPaint(1, UiGlobals.getConstantColor().getColor(1));
        renderer.setSeriesPaint(2, UiGlobals.getConstantColor().getColor(2));
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
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
		System.out.println("data.length : "+data.length+", category : "+categori);
		System.out.println("goodCount : "+goodCount+", overCount  :"+overCount+", : lowerCount"+lowerCount);
		//dataset.addV
		dataset.addValue(goodCount, SERIES_GOOD, categori);
		dataset.addValue(overCount, SERIES_BIG, categori);
		dataset.addValue(lowerCount, SERIES_SMALL, categori);
        
        CategoryPlot categoryPlot = new CategoryPlot(dataset, null, rangeAxis1, renderer);
		//categoryPlot.setOrientation(PlotOrientation.VERTICAL);
		//categoryPlot.
        categoryPlot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);
        
        
		
		return categoryPlot;
		
	}
	
	public JPanel createPanel() {
		//System.out.println("create Panel, precise : " + precise);
		NumberAxis valueAxis = new NumberAxis("Count");
		CombinedRangeCategoryPlot plot = new CombinedRangeCategoryPlot(valueAxis);
		//plot.setrange
		plot.setOrientation(PlotOrientation.VERTICAL);
		for(int count = 0 ; count < datas.size() ; count++)
		{
			categoryDataset = (DefaultCategoryDataset) addDataset(categoryDataset, datas.get(count), categories.get(count));
			CategoryPlot cplot = createCategoryPlot(datas.get(count), categories.get(count));
			plot.add(cplot);
			//cplot.setOrientation(PlotOrientation.VERTICAL);
			
			
	        //cplot.setDomainCrosshairColumnKey(SERIES_GOOD);
	        cplot.setDomainCrosshairVisible(true);
			
		}
		//plot.setOrientation(PlotOrientation.);
		//plot.setDomainAxisLocation(0, AxisLocation.BOTTOM_OR_LEFT);
		
		plot.setDomainAxis(new CategoryAxis("111"));
		//plot.setDrawSharedDomainAxis(false);
		//plot.setDomainCrosshairVisible(false);
		//plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
		//plot.setdomain
		
		//JFreeChart jfreechart = createChart(categoryDataset);
		JFreeChart jfreechart = new JFreeChart(
	            "Block size counting for construction of gene sets",
	            UiGlobals.getTitleFont(),
	            plot,
	            true
	        );
		
		
		LegendItemCollection legendCollection = new LegendItemCollection();
		LegendItem item1 = new LegendItem(SERIES_GOOD, UiGlobals.getConstantColor().getColor(0));
		//Paint paint = new Paint();
		//Point paint = new BasicPaint();
		//item1.set
		LegendItem item2 = new LegendItem(SERIES_BIG, UiGlobals.getConstantColor().getColor(1));
		LegendItem item3 = new LegendItem(SERIES_SMALL, UiGlobals.getConstantColor().getColor(2));
		legendCollection.add(item1);
		legendCollection.add(item2);
		legendCollection.add(item3);
		plot.setFixedLegendItems(legendCollection);
		
		//jfreechart.removeLegend();
		//jfreechart.addLegend(new LegendTitle("aa"));
		//LegendTitle lt = jfreechart.getLegend();
		//lt.
		
		/*
		JFreeChart jfreechart = ChartFactory.createBarChart(
	             "Block size counting for construction of gene sets",         // chart title
	             "grid space",               // domain axis label
	             "count",                  // range axis label
	             plot,                  // data
	             PlotOrientation.VERTICAL, // orientation
	             true,                     // include legend
	             true,                     // tooltips?
	             false                     // URLs?
	         );
	         */
		chart = jfreechart;
		//	XYPlot plot = jfreechart.getXYPlot();
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
    	
    	double data[] = {1, 2, 3, 2, 1, 4, 5, 3, 1, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	double data1[] = {3, 3, 3, 1, 1, 2, 2, 3, 3, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	JGridChartPanel char1 = new JGridChartPanel("title");
    	char1.addData(data, "data1");
    	char1.addData(data1, "data2");
    	char1.drawHistogram();
    	JFrame frame = new JFrame();
    	frame.add(char1);
    	frame.pack();
    	frame.setVisible(true);
    }

	@Override
	public void stateChanged(ChangeEvent e) {
		Object source = e.getSource();
		if (source instanceof JSlider) {
			JSlider slider = (JSlider) source;
			String sliderName = slider.getName();
			if (sliderName != null) {
				if (sliderName.equals("maxRanger")) {

					if (maxCurValue != slider.getValue()) {
						maxCurValue = slider.getValue();

						if (maxCurValue - minimum_interval < minCurValue) {
							minCurValue = maxCurValue - minimum_interval;
							minRanger.setValue(minCurValue);
						}
					}
				} else if (sliderName.equals("minRanger")) {

					if (minCurValue != slider.getValue()) {
						minCurValue = slider.getValue();

						if (maxCurValue < minCurValue + minimum_interval) {
							maxCurValue = minCurValue + minimum_interval;
							maxRanger.setValue(maxCurValue);
						}
					}
				}
				clean();
				mainPanel.remove(_chart);
				_chart = createPanel();
				mainPanel.add(_chart);
				_chart.revalidate();

				this.showStatus("Good range : [" + maxCurValue + " to "
						+ minCurValue + "]");

			}
		}
		
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		System.out.println("Iam a repaint");
		
	}
} /* end class JGraphFrame */
