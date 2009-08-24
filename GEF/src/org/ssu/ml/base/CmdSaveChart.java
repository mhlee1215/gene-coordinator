// File: CmdZoom.java
// Classes: CmdZoom
// Original Author: lawley@dstc.edu.au
// $Id: CmdZoom.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.ssu.ml.base;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;

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
			Utils.saveToFile(gridPanel.getChart(),"/home/mhlee/public/data/"+filename+".jpg",500,300,100);
			System.out.println("file saved at : /home/mhlee/public/data/"+filename+".jpg");
    	} catch (Exception e) {
    		System.out.println("Download method can be execute only on the web!");
    		e.printStackTrace();
		} 
        
    }

    public void undoIt() {
       
    }
} /* end class CmdZoom */