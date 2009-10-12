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

import org.jdesktop.swingx.JXPanel;
import org.jfree.chart.JFreeChart;
import org.ssu.ml.base.CmdSaveChart;
import org.ssu.ml.ui.GridPaletteFig;
import org.tigris.gef.ui.ToolBar;



public class JGridPanel extends JXPanel {
	JFreeChart chart;
	ToolBar toolbar = new ToolBar();
	protected JLabel statusbar = new JLabel(" ");
	protected JPanel mainPanel = new JPanel(new BorderLayout());
    
    public JGridPanel(){
        setLayout(new BorderLayout());
    	add(mainPanel, BorderLayout.CENTER);
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
