import org.jfree.chart.*;
import org.jfree.data.category.*;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.*;
import org.jfree.data.*;
import org.jfree.chart.renderer.category.*;
import org.jfree.chart.plot.*;
import java.awt.*;

public class BarExample1{
	public static void main(String arg[]){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(2, "Marks", "Rahul");
		dataset.setValue(7, "Marks", "Vinod");
		dataset.setValue(4, "Marks", "Deepak");
		dataset.setValue(9, "Marks", "Prashant");
		dataset.setValue(6, "Marks", "Chandan");
		JFreeChart chart = ChartFactory.createBarChart	(
			"BarChart using JFreeChart","Student", "Marks", dataset, 
			PlotOrientation.VERTICAL, false,true, false);
		chart.setBackgroundPaint(Color.yellow);
		chart.getTitle().setPaint(Color.blue); 
		CategoryPlot p = chart.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 
		ChartFrame frame1=new ChartFrame("Bar Chart",chart);
		frame1.setSize(400,350);  
		frame1.setVisible(true);
	}
}