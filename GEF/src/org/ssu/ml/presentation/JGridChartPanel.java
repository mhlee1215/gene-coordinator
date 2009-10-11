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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXRootPane;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.border.DropShadowBorder;
import org.jdesktop.swingx.painter.AbstractPainter;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;
import org.jdesktop.swingx.painter.PinstripePainter;
import org.jdesktop.swingx.painter.RectanglePainter;
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

import etc.Colors;
import etc.RoundedBorder;

/**
 * A window that displays a toolbar, a connected graph editing pane, and a
 * status bar.
 */

public class JGridChartPanel extends JGridPanel implements IStatusBar, Cloneable, ChangeListener, MouseListener{
	
	Border selectedBorder = new RoundedBorder(Color.red, true);//BorderFactory.createMatteBorder(5, 5, 5, 5, Color.red);
	Border unselectedBorder = new RoundedBorder(Color.white, false);//BorderFactory.createMatteBorder(5, 5, 5, 5, Color.white);
	Border overedBorder = new RoundedBorder(Color.LIGHT_GRAY, false);//BorderFactory.createMatteBorder(5, 5, 5, 5, Color.LIGHT_GRAY);
	JPanel overedPanel = null;
	JPanel selectedPanel = null;
	
	
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
	
	JPanel chartPanel;
	
	ToolBar leftSideToolbar;
	
	int totalWidth = 0;
	int totalHeight = 0;
	int chartWidth = 0;
	int chartHeight = 0;

    private static final long serialVersionUID = -8167010467922210977L;
    
    

    DefaultCategoryDataset categoryDataset = new DefaultCategoryDataset();
    Dimension drawingSize = null;
    /**
     * Contruct a new JGraphFrame with the title "untitled" and a new
     * DefaultGraphModel.
     */
	public JGridChartPanel(String title, int totalWidth, int toalHeight) {
		super();
		setUpSideToolbar();
		this.totalWidth = totalWidth;
		this.totalHeight = toalHeight;
    }

    public void addData(double[] data, String category){
    	datas.add(data);
    	categories.add(category);
    }
    
    public Object clone() {
        return null; // needs-more-work
    }
    
    public void setUpSideToolbar()
    {
    	leftSideToolbar = new ToolBar();
    	
		maxCurValue = 50;
		minCurValue = 10;
		leftSideToolbar.setLayout(new GridLayout(2, 1));
		
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
	        leftSideToolbar.add(maxRanger);
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
	        
			leftSideToolbar.add(minRanger);
			
		}
    	
		mainPanel.add(leftSideToolbar, BorderLayout.WEST);
    }


    // //////////////////////////////////////////////////////////////
    // IStatusListener implementation

    
    public void drawChart()
    {
    	chartPanel = createPanel();
    	//_histogram.setPreferredSize(new Dimension(700, 270));
    	mainPanel.add(chartPanel, BorderLayout.CENTER);
        //this.remove
    }
    
	
//	private CategoryDataset addDataset(double data[], String categoryName){
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		return addDataset(dataset, data, categoryName);
//	}
	
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
	
	
	
//	private static JFreeChart createChart(CategoryDataset categoryDataset)
//	{
//	    
//	     JFreeChart jfreechart = ChartFactory.createBarChart(
//	             "Block size counting for construction of gene sets",         // chart title
//	             "grid space",               // domain axis label
//	             "count",                  // range axis label
//	             categoryDataset,                  // data
//	             PlotOrientation.VERTICAL, // orientation
//	             true,                     // include legend
//	             true,                     // tooltips?
//	             false                     // URLs?
//	         );
//	   
//	     jfreechart.setBackgroundPaint(Color.WHITE);
//	     CategoryPlot plot = jfreechart.getCategoryPlot();  // íƮ�� Plot ��ü�� ���Ѵ�.
//	     plot.setBackgroundPaint(Color.white);     // íƮ�� Plot ����; lightGray�� �ٲ۴�.
//	     plot.setRangeGridlinePaint(Color.BLUE);       // ���� �׸�������� ��; BLUE�� �ٲ۴�.
//	     plot.setDomainGridlinesVisible(true);        // ���� �׸������; �Ⱥ��̰� �Ѵ�.
//	     plot.setDomainGridlinePaint(Color.blue);
//	     plot.setOutlinePaint(Color.blue);	//��Ʈ �ܰ�
//	     plot.setOutlineVisible(true);
//	     plot.setAnchorValue(10);
//	     
//	     
//	     // 6. ��; Ŀ���͸������ϱ�
//        BarRenderer renderer = (BarRenderer) plot.getRenderer();  // BarRenderer�� ���Ѵ�.
//        
//        renderer.setItemMargin(0.05);                 // �0� �;����� ����; d�Ѵ�.
//        renderer.setDrawBarOutline(true);            // ���� ��輱 ǥ�ø� ��d
//        //renderer.setOutlinePaint(Color.red);
//        BarPainter painter = new StandardBarPainter();
//        renderer.setBarPainter(painter);
//        Stroke stroke = new BasicStroke(3.0f);
//        //stroke.
//        
//        
//        renderer.setSeriesOutlinePaint(0, Color.black);
//        renderer.setSeriesOutlinePaint(1, Color.black);
//        renderer.setSeriesOutlinePaint(2, Color.black);
//        renderer.setSeriesOutlineStroke(0, stroke);
//        renderer.setSeriesOutlineStroke(1, stroke);
//        renderer.setSeriesOutlineStroke(2, stroke);
//        renderer.setShadowXOffset(10.0);
//        renderer.setShadowYOffset(10.0);
//        
//        StandardCategoryItemLabelGenerator labelGenerator = new StandardCategoryItemLabelGenerator();
//        renderer.setItemLabelFont(UiGlobals.getNormalFont());
//        renderer.setItemLabelGenerator(labelGenerator);
//        renderer.setItemLabelPaint(Color.black);
//        renderer.setItemLabelsVisible(true);
//        
//        NumberFormat format = NumberFormat.getInstance();
//        String labelFormat = "<html><body style=\"background-color: #ffffdd\"><h1>{0}</h1><br><h2>{1}</h2><br><h3>{2}</h3></body></html>";
//        StandardCategoryToolTipGenerator generator = new StandardCategoryToolTipGenerator(labelFormat, format);
//        //System.out.println(generator.getLabelFormat());
//        
//        renderer.setSeriesToolTipGenerator(0, generator);
//        renderer.setSeriesToolTipGenerator(1, generator);
//        renderer.setSeriesToolTipGenerator(2, generator);
//        
//        // 7.�;�� �ٲٱ�
//        
//        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, new Color(204, 255, 153),  0.0f, 0.0f, new Color(204, 255, 153));
//        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f, 0.0f, new Color(0, 64, 0));
//        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,   0.0f, 0.0f, new Color(64, 0, 0));
//        renderer.setSeriesPaint(0, UiGlobals.getConstantColor().getColor(0));
//        renderer.setSeriesPaint(1, UiGlobals.getConstantColor().getColor(1));
//        renderer.setSeriesPaint(2, UiGlobals.getConstantColor().getColor(2));
//        
//        
//	     //XYPlot xyplot = (XYPlot)jfreechart.getPlot();
//	     //xyplot.setForegroundAlpha(0.3F);
//
//	     return jfreechart;
//	}
	
	public CategoryPlot createCategoryPlot(double[] data, String categori){
		NumberAxis rangeAxis1 = new NumberAxis(categori);
		rangeAxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		//CategoryAxis categoryAxis = new CategoryAxis("a");
		
		BarRenderer renderer = new BarRenderer();
		renderer.setItemMargin(0.05);                 // 
        renderer.setDrawBarOutline(true);            // 
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
        
        // 7.�;�� �ٲٱ�
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
		JXPanel mainPanel = new JXPanel();
		mainPanel.setBackground(Color.white);
		GridBagConstraints gbcMain = new GridBagConstraints();  
		mainPanel.setLayout( new GridBagLayout() );   
		gbcMain.gridy = 0;  
		gbcMain.insets = new Insets( 0, 0, 0, 0 );
		
		NumberAxis valueAxis = new NumberAxis("Count");
		CombinedRangeCategoryPlot plot = new CombinedRangeCategoryPlot(valueAxis);
		//plot.setrange
		plot.setOrientation(PlotOrientation.VERTICAL);
		for(int count = 0 ; count < datas.size() ; count++)
		{
			categoryDataset = (DefaultCategoryDataset) addDataset(categoryDataset, datas.get(count), categories.get(count));
			CategoryPlot cplot = createCategoryPlot(datas.get(count), categories.get(count));
			JFreeChart plotChart = new JFreeChart(cplot);
			
			
			JXPanel subBorderPanel = new JXPanel();
			subBorderPanel.setBackground(Color.white);
			subBorderPanel.setBorder(this.unselectedBorder);
			
			JXTitledPanel subTitlePanel = new JXTitledPanel("title");
			
			
			GridBagConstraints gbcBorder = new GridBagConstraints();  
			subBorderPanel.setLayout( new GridBagLayout() );   
		    gbcBorder.insets = new Insets(5, 5, 5, 5);
		    subBorderPanel.add( subTitlePanel, gbcBorder );
			
		    
			
			
			
			int width = 100;
			int height = 300;
			Color color1 = Colors.White.color(0.5f);
			Color color2 = Colors.Gray.color(0.5f);
			 
			LinearGradientPaint gradientPaint =
			     new LinearGradientPaint(6.0f, 6.0f, width, height,
			                             new float[]{0.0f, 1.0f},
			                             new Color[]{color1, color2});
			MattePainter mattePainter = new MattePainter(gradientPaint);
			
			MattePainter mp = new MattePainter(Colors.LightBlue.alpha(0.8f));
			GlossPainter gp = new GlossPainter(Colors.White.alpha(0.8f),
			                                   GlossPainter.GlossPosition.TOP);
			PinstripePainter pp = new PinstripePainter(Colors.Gray.alpha(0.8f),
			                                           45d);
			//subChartPanel.set
			RectanglePainter roundRect = new RectanglePainter(
			        6, 6, 6, 6,   10,10,   true, 
				Color.GRAY, 3, Color.DARK_GRAY); 
			roundRect.setAntialiasing(true);
			subTitlePanel.setTitlePainter(roundRect);
			subTitlePanel.setBorder(null);
			RectanglePainter backgroundRoundRect = new RectanglePainter(
			        6, 6, 6, 6, 10,10,   true, 
				Color.GRAY, 3, Color.DARK_GRAY); 
			backgroundRoundRect.setAntialiasing(true);
			
			//subTitlePanel.setBackgroundPainter(backgroundRoundRect);
			
			
			//subChartPanel.set
			//subChartPanel.set
			//new GradientPainter();
			//subChartPanel.setTitle("title");
			subTitlePanel.setBackground(Color.white);
			subTitlePanel.setForeground(Color.white);
			//subChartPanel.set
			//subChartPanel.setLayout(new BorderLayout());
			plotChart.setBackgroundPaint(Color.gray);
			
			JPanel innerChartPanel = new ChartPanel(plotChart);
			System.out.println("popup size : "+innerChartPanel.getComponentPopupMenu());
			
			System.out.println("createPanel size : "+getSize());
			//innerChartPanel.set
			
			innerChartPanel.setPreferredSize(new Dimension(this.totalWidth/datas.size(), getSize().height-130));
			
			
			JRadioButton radio = new JRadioButton();
			radio.setText("radio");
			
			//subChartPanel.add(radio, BorderLayout.NORTH);
			
			GridBagConstraints gbc = new GridBagConstraints();  
			subTitlePanel.setLayout( new GridBagLayout() );   
		    gbc.gridy = 1;  
		    gbc.insets = new Insets( 0, 10, 10, 0 );  
		    subTitlePanel.add( innerChartPanel, gbc );  
		    //setSize( 500, 500 );  
			
			
		    subTitlePanel.addMouseListener(this);
		    innerChartPanel.addMouseListener(this);
			
			
			//subChartPanel.add(innerChartPanel, BorderLayout.CENTER);
			mainPanel.add(subBorderPanel, gbcMain);
			//mainPanel.add(new JXTitledPanel("title"));
			//new PlotPanel(cplot);
			//new ChartPanel(cplot);
			plot.add(cplot);
			//cplot.setOrientation(PlotOrientation.VERTICAL);
			
			
	        //cplot.setDomainCrosshairColumnKey(SERIES_GOOD);
	        //cplot.setDomainCrosshairVisible(true);
			//cplot.set
		}
		//plot.setOrientation(PlotOrientation.);
		//plot.setDomainAxisLocation(0, AxisLocation.BOTTOM_OR_LEFT);
		
		plot.setDomainAxis(new CategoryAxis("111"));
		//plot.setDrawSharedDomainAxis(false);
		//plot.setDomainCrosshairVisible(false);
		//plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		
		//plot.setdomain
		
		//JFreeChart jfreechart = createChart(categoryDataset);
//		JFreeChart jfreechart = new JFreeChart(
//	            "Block size counting for construction of gene sets",
//	            UiGlobals.getTitleFont(),
//	            plot,
//	            true
//	        );
		
		
//		LegendItemCollection legendCollection = new LegendItemCollection();
//		LegendItem item1 = new LegendItem(SERIES_GOOD, UiGlobals.getConstantColor().getColor(0));
//		//Paint paint = new Paint();
//		//Point paint = new BasicPaint();
//		//item1.set
//		LegendItem item2 = new LegendItem(SERIES_BIG, UiGlobals.getConstantColor().getColor(1));
//		LegendItem item3 = new LegendItem(SERIES_SMALL, UiGlobals.getConstantColor().getColor(2));
//		legendCollection.add(item1);
//		legendCollection.add(item2);
//		legendCollection.add(item3);
//		plot.setFixedLegendItems(legendCollection);
		
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
		//chart = jfreechart;
		//	XYPlot plot = jfreechart.getXYPlot();
		//jfreechart.get
		//XYItemRenderer renderer = plot.getRenderer();
		//renderer.set
		//BarRenderer renderer = (BarRenderer)plot.getRenderer();
		//renderer.set
		
		//return new ChartPanel(jfreechart);
		return mainPanel;
		//return new JXTitledPanel("title");
	}

	public void clean()
	{
		categoryDataset = new DefaultCategoryDataset();
	}
    public static void main(String[] argv)
    {
    	
    	double data[] = {1, 2, 3, 2, 1, 4, 5, 3, 1, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	double data1[] = {3, 3, 3, 1, 1, 2, 2, 3, 3, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	double data2[] = {3, 3, 3, 1, 1, 2, 2, 3, 3, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	double data3[] = {3, 3, 3, 1, 1, 2, 2, 3, 3, 2,3  ,1, 2,3, 1,2, 3, 12,3 };
    	JGridChartPanel char1 = new JGridChartPanel("title", 500, 500);
    	JXFrame frame = new JXFrame();
    	frame.setVisible(true);
    	frame.addComponentListener(new ComponentListener(){

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				JXFrame frame = (JXFrame)e.getComponent();
				
				Component[] c = frame.getContentPane().getComponents();
				if(c.length > 0){
					JGridChartPanel gcp  = (JGridChartPanel) c[0];
					gcp.reDraw();
				}
			}
			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}});
    	char1.setSize(700, 500);
    	//char1.setAlpha(.3f);
    	
    	
    	
    	char1.setBackground(Color.white);
    	char1.addData(data, "data1");
    	char1.addData(data1, "data2");
    	char1.addData(data2, "data3");
    	char1.addData(data3, "datar");
    	char1.drawChart();
    	frame.getContentPane().add(char1);
    	//frame.add(char1);
    	frame.pack();
//    	
//    	final JDialog dialog = new JDialog(frame, "Child", true);
//        dialog.setBackground(Color.green);
//        dialog.setForeground(Color.green);
//        dialog.setSize(300, 200);
//        dialog.setLocationRelativeTo(frame);
//        JXTitledPanel panel = new JXTitledPanel("loading...");
//        JLabel label = new JLabel("hi");
//        panel.add(label);
//        dialog.add(panel);
//        dialog.setUndecorated(true);
//        dialog.setVisible(true);
        System.out.println("load fin!");
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
				mainPanel.remove(chartPanel);
				chartPanel = createPanel();
				mainPanel.add(chartPanel);
				chartPanel.revalidate();

				this.showStatus("Good range : [" + maxCurValue + " to "
						+ minCurValue + "]");

			}
		}
		
	}
	
	public void reDraw(){
		clean();
		mainPanel.remove(chartPanel);
		chartPanel = createPanel();
		mainPanel.add(chartPanel);
		chartPanel.revalidate();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);	
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() instanceof JXTitledPanel){
			JXTitledPanel source = (JXTitledPanel)e.getSource();
			JXPanel shdowPanel = (JXPanel)source.getParent();
			System.out.println("src : "+source +", selected : "+selectedPanel);
			if(shdowPanel != selectedPanel){
				shdowPanel.setBorder(this.overedBorder);
				//source.setAlpha(.5f);
			}
		}else if(e.getSource() instanceof JPanel){
			JPanel source = (JPanel)e.getSource();
			e.setSource(source.getParent());
			mouseEntered(e);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() instanceof JXTitledPanel){
			JXTitledPanel source = (JXTitledPanel)e.getSource();
			JXPanel shdowPanel = (JXPanel)source.getParent();
			if(shdowPanel != selectedPanel){
				shdowPanel.setBorder(this.unselectedBorder);
				//source.setAlpha(1.0f);
			}
		}else if(e.getSource() instanceof JPanel){
			JPanel source = (JPanel)e.getSource();
			e.setSource(source.getParent());
			mouseExited(e);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if( e.getSource() instanceof JXTitledPanel){
			JPanel source = (JPanel)e.getSource();
			JXPanel shdowPanel = (JXPanel)source.getParent();
			if(selectedPanel != null){
				selectedPanel.setBorder(this.unselectedBorder);
			}
			shdowPanel.setBorder(this.selectedBorder);
			selectedPanel = shdowPanel;
			
		}else if(e.getSource() instanceof JPanel){
			JPanel source = (JPanel)e.getSource();
			e.setSource(source.getParent());
			mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
