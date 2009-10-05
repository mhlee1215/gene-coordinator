// File: CmdZoom.java
// Classes: CmdZoom
// Original Author: lawley@dstc.edu.au
// $Id: CmdZoom.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.ssu.ml.base;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.RefineryUtilities;
import org.ssu.ml.base.UiGlobals;
import org.ssu.ml.presentation.JGridChartPanel;
import org.ssu.ml.presentation.JGridTabbedFrame;
import org.ssu.ml.presentation.JGridHistogramPanel;
import org.ssu.ml.presentation.JGridPanel;
import org.ssu.ml.ui.Utils;
import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.graph.presentation.JGraphFrame;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.util.Localizer;


public class CmdSaveChart extends Cmd {
    private static final long serialVersionUID = 8472508088519383941L;
    protected double _magnitude;
    JGridPanel gridPanel;
    String filename;

    // //////////////////////////////////////////////////////////////
    // constructor

    /** Default behaviour is to restore scaling to 1.0 (1 to 1) */
    public CmdSaveChart(JGridPanel gridPanel, String filename) {
        super("Save chart");
        this.gridPanel = gridPanel;
        this.filename = filename;
    }

    public void doIt() {
    	
        

    	try {
    		//URL path = UiGlobals.getApplet().getCodeBase();
			saveToFile(gridPanel.getChart(),"/home/mhlee/public/data/"+filename+".jpg",800,600,100);
    	} catch (Exception e) {
    		System.out.println("Download method can be execute only on the web!");
    		e.printStackTrace();
		} 
        
    }

    public void undoIt() {
       
    }
    
    public void saveToFile(JFreeChart chart, String aFileName,
			int width, int height, double quality)
			throws Exception {
		BufferedImage img = draw(chart, width, height);
		
		SendImageToJsp(img, aFileName, width, height);
	}
	
	protected BufferedImage draw(JFreeChart chart, int width, int height)
    {
        BufferedImage img =
        new BufferedImage(width , height,
        BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = img.createGraphics();
                       
        chart.draw(g2, new Rectangle2D.Double(0, 0, width, height));
 
        g2.dispose();
        return img;
    }
	
	public void SendImageToJsp(BufferedImage img, String filename, int width, int height) throws Exception
	{
		
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
		
		ImageIO.write(img,"jpeg", bas);
		byte[] data = bas.toByteArray();
		
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		System.out.println(data);
		
		
		
		String url = UiGlobals.getApplet().getCodeBase().toString() + "coordinator/writeImage.jsp";
		HttpClient httpClient = new HttpClient();
		System.out.println("code base to Write : "+url);
		PostMethod postMethod = new PostMethod(url);
		
		//Set Inputstream as entity
		System.out.println("send filename : "+filename);
		NameValuePair[] requestBody = {
				new NameValuePair("filename", filename)
		};
		postMethod.setRequestBody(requestBody);
		postMethod.setRequestEntity(new InputStreamRequestEntity(bis));
		
		try{
			//Execute
			httpClient.executeMethod(postMethod);
			
			System.out.println(postMethod.getResponseBody());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
} /* end class CmdZoom */