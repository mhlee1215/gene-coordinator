// File: CmdZoom.java
// Classes: CmdZoom
// Original Author: lawley@dstc.edu.au
// $Id: CmdZoom.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.ssu.ml.base;

import java.util.HashMap;
import java.util.List;

import org.jfree.ui.RefineryUtilities;
import org.ssu.ml.base.UiGlobals;
import org.ssu.ml.presentation.JGridHistogramFrame;
import org.tigris.gef.base.Cmd;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.graph.presentation.JGraphFrame;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.util.Localizer;


public class CmdGridHistogram extends Cmd {
    private static final long serialVersionUID = 8472508088519383941L;
    protected double _magnitude;

    // //////////////////////////////////////////////////////////////
    // constructor

    /** Default behaviour is to restore scaling to 1.0 (1 to 1) */
    public CmdGridHistogram() {
        super("Show Grid Histogram");
    }

    /**
     * Each time <code>doIt()</code> is invoked, adjust scaling by a factor of
     * <code>magnitude</code>.
     * 
     * @param magnitude
     *                the factor by which to adjust the Editor's scaling. Must
     *                be greater than or equal to zero. If zero, resets the
     *                Editor's scale factor to 1.
     */


    /** Convert the zoom magnitude to an English description. */
   

    /** Adjust the scale factor of the current editor. */
    public void doIt() {
    	
        
        Editor editor = UiGlobals.curEditor();
        LayerGrid grid = (LayerGrid) editor.getLayerManager().findLayerNamed("Grid");
        HashMap map = grid.getParameters();
        int interval_space = (Integer)map.get("spacing");
        System.out.println("Hi! I'm a GridHistogram Swich!, space : "+interval_space);
        
        List<Fig> nodes = editor.getLayerManager().getActiveLayer().getContents();
        int drawingSizeX = UiGlobals.getDrawingSizeX();
        int drawingSizeY = UiGlobals.getDrawingSizeY();
        
        CGridHistogramData histoData = new CGridHistogramData(drawingSizeX, drawingSizeY, interval_space);
        
        for(int count = 0 ; count < nodes.size() ; count++)
        {
        	Fig node = nodes.get(count);
        	//System.out.println(node.getLocation().x+", "+node.getLocation().y+", "+node.getId());
        	histoData.addData(node.getLocation().x, node.getLocation().y);
//        	Object desc = node.getOwner();
//        	if(desc instanceof NodeDescriptor)
//        	{
//        		NodeDescriptor nodeDesc = (NodeDescriptor)desc;
//        		System.out.println("name : "+nodeDesc.getName());
//        	}
        }
        Double[] result = histoData.generateHistoData();
        double[] result_1 = new double[result.length];
        for(int count = 0 ; count < result.length ; count++)
        	result_1[count] = (double)result[count];
        
        if(result_1.length > 0){
	        JGridHistogramFrame histoFrame = new JGridHistogramFrame("title", result_1);
	        histoFrame.setPrecise(100);
	        histoFrame.drawHistogram();
	        histoFrame.pack();
	        
	        RefineryUtilities.centerFrameOnScreen(histoFrame);
	        histoFrame.setVisible(true);
        }
        else
        	System.out.println("No Data Error!");
        
    }

    /**
     * Undo the zoom. Does not yet work for magnitude of 0 (a reset), and is
     * subject to skew due to precision errors since for floats we cannot assume
     * <code>(x * f / f) == x</code>
     */
    public void undoIt() {
       
    }
} /* end class CmdZoom */