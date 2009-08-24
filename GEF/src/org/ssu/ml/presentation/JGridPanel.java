package org.ssu.ml.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToolBar;

import org.jfree.chart.JFreeChart;
import org.ssu.ml.base.CmdSaveChart;
import org.ssu.ml.ui.GridPaletteFig;
import org.tigris.gef.ui.ToolBar;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class JGridPanel extends JPanel {
	JFreeChart chart;
	ToolBar toolbar = new ToolBar();
	protected JLabel statusbar = new JLabel(" ");
	protected JPanel mainPanel = new JPanel(new BorderLayout());
    
    public JGridPanel(){
    	add(mainPanel);
		setUpToolbar();
		setStatusBar();
    }
	
    public JFreeChart getChart() {
		return chart;
	}

	public void setUpToolbar()
    {
    	toolbar.setBackground(Color.white);
    	toolbar.setForeground(Color.white);
    	toolbar.add(new CmdSaveChart(this, "aa"), "Save to JPEG", "Save1");
    	mainPanel.add(toolbar, BorderLayout.NORTH);
    }
    
    public void setStatusBar(){
    	mainPanel.add(statusbar, BorderLayout.SOUTH);
    }
    
    public void showStatus(String msg) {
        if (statusbar != null)
            statusbar.setText(msg);
    }	 
}
