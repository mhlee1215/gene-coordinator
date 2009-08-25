package org.ssu.ml.ui;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jfree.chart.JFreeChart;
import org.ssu.ml.base.UiGlobals;

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
			throws FileNotFoundException, IOException, InterruptedException {
		BufferedImage img = draw(chart, width, height);
		int[] pixel = getArrayFromImage(img, width, height);
		SendImageToJsp(pixel, aFileName, width, height);
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
	
	public static void SendImageToJsp(int[] pixels, String filename, int width, int height)
	{
		//String url = UiGlobals.getApplet().getCodeBase().toString();
		//HttpClient httpClient = new DefaultHttpClient();
		//HttpGet httpGet = new HttpGet(url+"writeImage.jsp");
		
		try{
			System.out.println(pixels.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	public static String Array2String(int[] array, String delimeter){
		String result = "";
		for(int count = 0 ; count < array.length ; count++)
		{
			if(count == 0)
				result = ""+array[count];
			else
				result += (delimeter + array[count]);
		}
		return result;
	}
	
	public static int[] String2Array(String string, String delimeter){
		String[] parts = string.split(delimeter);
		int[] result = new int[parts.length];
		
		for(int count = 0 ; count < parts.length ; count++)
				result[count] = Integer.parseInt(parts[count]);
		return result;
	}
	
	protected static int[] getArrayFromImage(Image img, int width, int height) throws
	InterruptedException {
		int[] pixels = new int[width * height];
		PixelGrabber pg = new PixelGrabber(img, 0, 0, width, height, pixels, 0, width);
		pg.grabPixels();
		return pixels;
	}  //  private int[] getArrayFromImage()
	
	private Image getImageFromArray(int[] pixels, int width, int height) {
		MemoryImageSource mis = new MemoryImageSource(width, height, pixels, 0, width);
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.createImage(mis);
	}  //  private Image getImageFromArray()
	
	
	
	
}
