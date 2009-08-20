import java.awt.Color;
import java.io.FileOutputStream;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class BarExample2{
	public static void main(String arg[]){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(2, "Marks", "Rahul");
		dataset.setValue(7, "Marks", "Vinod");
		dataset.setValue(4, "Marks", "Deepak");
		dataset.setValue(9, "Marks", "Prashant");
		dataset.setValue(6, "Marks", "Chandan");
		JFreeChart chart = ChartFactory.createBarChart(
		  "BarChart using JFreeChart","Student", "Marks", dataset, PlotOrientation.VERTICAL, false,true, false);
		chart.setBackgroundPaint(Color.yellow);
		chart.getTitle().setPaint(Color.blue); 
		CategoryPlot p = chart.getCategoryPlot(); 
		p.setRangeGridlinePaint(Color.red); 

		try{
			/*������ ��Ʈ�� �̹��� ���Ϸ� �����ϴ� ���� �������, 
			��½�Ʈ���� ServletOutputStream ����
			���������ν� �������� ����� ���� �ִ�.*/

			FileOutputStream fo = new FileOutputStream("BarChart.png");
			ChartUtilities.writeChartAsPNG(fo, chart, 400, 400);
			fo.close();
			System.out.println("Chart created.");
		}catch(Exception e){
			System.err.println(e);
		}
	}
}