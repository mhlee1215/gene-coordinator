package org.ssu.ml.ui;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.JFreeChart;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;



public class Utils {
	
	public static int requestIntParameter(HttpServletRequest request, String name, int initVal)
	{
		if(request.getParameter(name) == null)
			return initVal;
		else
			return Integer.parseInt(request.getParameter(name));
	}
	
	public static String requestStringParameter(HttpServletRequest request, String name, String initVal)
	{
		if(request.getParameter(name) == null)
			return initVal;
		else
			return (String)request.getParameter(name);
	}
	
	public static int maxIndex(float[] args)
	{
		float maxValue = -1000000;
		int maxCount = -1;
		Class type;
		
		for(int count = 0 ; count < args.length ; count++)
		{
			if(maxValue < args[count])
			{
				maxValue = args[count];
				maxCount = count;
			}
		}
		return maxCount;
	}
	
	public static int minIndex(float[] args)
	{
		float minValue = 1000000;
		int minCount = -1;
		Class type;
		
		for(int count = 0 ; count < args.length ; count++)
		{
			if(minValue > args[count])
			{
				minValue = args[count];
				minCount = count;
			}
		}
		return minCount;
	}
	
	public static float minValue(float[] args)
	{
		return args[minIndex(args)];
	}
	
	public static float maxValue(float[] args)
	{
		return args[maxIndex(args)];
	}
	
	public static void main(String[] argv)
	{
		Object a = (float)0.01;
		if(a instanceof Float)
		{
			System.out.println("??");
		}
	}
	
	public static BufferedReader getInputReader(String filename) {
		BufferedReader br = null;
		
		try {
			URL testServlet = new URL(filename);
			HttpURLConnection servletConnection = (HttpURLConnection) testServlet
					.openConnection();

			InputStream is = new BufferedInputStream(servletConnection
					.getInputStream());
			
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return br;
	}
	
	public static void saveToFile(JFreeChart chart, String aFileName,
			int width, int height, double quality)
			throws FileNotFoundException, IOException {
		BufferedImage img = draw(chart, width, height);
		FileOutputStream fos = new FileOutputStream(aFileName);
		JPEGImageEncoder encoder2 = JPEGCodec.createJPEGEncoder(fos);
		JPEGEncodeParam param2 = encoder2.getDefaultJPEGEncodeParam(img);
		param2.setQuality((float) quality, true);
		encoder2.encode(img, param2);
		fos.close();
	}
	
	protected static BufferedImage draw(JFreeChart chart, int width, int height)
    {
        BufferedImage img =
        new BufferedImage(width , height,
        BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
                       
        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
 
        g2.dispose();
        return img;
    }
	
}
