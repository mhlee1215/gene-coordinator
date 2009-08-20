import java.awt.Dimension;
import javax.swing.JApplet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
public class BarChartDemo_t01 extends JApplet{
 public BarChartDemo_t01(){
  // row keys...
        final String series1 = "First";
        final String series2 = "Second";
        final String series3 = "Third";
        // column keys...
        final String category1 = "Category 1";
        final String category2 = "Category 2";
        final String category3 = "Category 3";
        final String category4 = "Category 4";
        final String category5 = "Category 5";
        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);
        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);
        dataset.addValue(-4.0, series3, category1);
        dataset.addValue(-3.0, series3, category2);
        dataset.addValue(-2.0, series3, category3);
        dataset.addValue(-3.0, series3, category4);
        dataset.addValue(-6.0, series3, category5);
        
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "Bar Chart Demo test01",  // chart title
            "Category",               // domain axis label
            "Value",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );
        
        final ChartPanel chartPanel = new ChartPanel(chart);
 
        // 여기서부터 다듬기 시작

        setContentPane(chartPanel);
 }
}
