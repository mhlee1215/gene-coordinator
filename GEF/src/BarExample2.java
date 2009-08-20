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
			/*생성한 차트를 이미지 파일로 저장하는 예를 들었지만, 
			출력스트림을 ServletOutputStream 으로
			변경함으로써 브라우저로 출력할 수도 있다.*/

			FileOutputStream fo = new FileOutputStream("BarChart.png");
			ChartUtilities.writeChartAsPNG(fo, chart, 400, 400);
			fo.close();
			System.out.println("Chart created.");
		}catch(Exception e){
			System.err.println(e);
		}
	}
}