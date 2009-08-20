import java.io.File;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;


public class test {

	public static void main(String[] argv)
	{
		try {
			 DefaultPieDataset dataset2 = new DefaultPieDataset();

			 dataset2.setValue("1월", 5); 
			 dataset2.setValue("2월", 16); 
			 dataset2.setValue("3월", 15); 
			 dataset2.setValue("4월", 14);
			 dataset2.setValue("5월", 30);
			 dataset2.setValue("6월", 15);  

			 JFreeChart chart2 = ChartFactory.createPieChart("Pie Chart", dataset2, true, true, false);

			 chart2.setBackgroundPaint(java.awt.Color.white);
			 chart2.setTitle("월별 DVD 대여량");

			 ChartRenderingInfo info2 = new ChartRenderingInfo(new StandardEntityCollection());
			 String fileName2 = "C:/" + "K0002.jpeg";
			 ChartUtilities.saveChartAsJPEG(new File(fileName2),chart2,600,300,info2);
			} catch (Exception e) {

			 System.out.println("error!!");
			}


	}
}	
