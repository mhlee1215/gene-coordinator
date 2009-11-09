/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Sun Microsystems nor the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

package org.ssu.ml.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.apache.log4j.Logger;
import org.ssu.ml.base.DoublePair;
import org.ssu.ml.base.NodeDescriptor;
import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.demo.SampleNode;
import org.tigris.gef.graph.presentation.DefaultGraphModel;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigLine;
import org.tigris.gef.presentation.FigNode;
import org.tigris.gef.presentation.FigRect;

import java.beans.*;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Random;

import org.ssu.ml.presentation.FigCustomNode;


public class NodeLoadingProgressBar extends JPanel
                              implements ActionListener, 
                                         PropertyChangeListener {
	Logger logger = Logger.getLogger(NodeLoadingProgressBar.class);
	
	private final static int STATUS_STARTED = 0;
	private final static int STATUS_STOPPED = 1;
	private final static int STATUS_CANCELED = 2;
	
	private int status;
	private int pre_scaled;
	
	private CNodeData nodeData = null;
	
    private JProgressBar progressBar;
    private JButton startButton;
    private JButton stopButton;
    private JButton cancelButton;
    private JTextArea taskOutput;
    private NodeTask task;
    
    private int max_work = 50000;
    private int cur_work = 0;
    
    private JFrame frame = null;
    private JGraph graph = null;
  

    class NodeTask extends SwingWorker<Void, Void> {
    	boolean progressFlag = true;
    	CNodeData nodeData = null;
    	float minLocx = 0;
    	float minLocy = 0;
    	float maxLocx = 0;
    	float maxLocy = 0;
    	
    	JGraph _graph = null;
    	JFrame frame = null; 
    	

    	public NodeTask(CNodeData nodeData, JFrame frame, JGraph graph)
    	{
    		this.nodeData = nodeData;
    		this.frame = frame;
    		this._graph = graph;
    		
    		minLocx = nodeData.getMinXValue();
        	minLocy = nodeData.getMinYValue();
        	maxLocx = nodeData.getMaxXValue();
        	maxLocy = nodeData.getMaxYValue();
    	}
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            
         
            //Initialize progress property.
            setProgress(0);
            
         

        	
           
            Editor editor = _graph.getEditor();
            
            float[] locxArry = nodeData.getLocxArry();
            float[] locyArry = nodeData.getLocyArry();
            
            max_work = nodeData.size();
        	for(int count = cur_work ; count < max_work ; count++){
            	int locx = (int)((locxArry[count]+Math.abs(minLocx))*nodeData.getPre_scale()) + nodeData.getPadding()/2;
            	int locy = (int)((locyArry[count]+Math.abs(minLocy))*nodeData.getPre_scale()) + nodeData.getPadding()/2;
            	

            	NodeDescriptor desc = new NodeDescriptor();
            	desc.setName(nodeData.getPointerName(count));
            	desc.setGroup(nodeData.getGroup(count));
            	FigCustomNode rect = new FigCustomNode(locx, locy, 7, 7, desc);
            	
            	rect.setLineColor(nodeData.getColor(count));

            	rect.setLocked(true);

            	editor.add(rect);

            	cur_work++;
            	if(cur_work%1000 == 0) UiGlobals.setStatusbarText(" Node Rendering... "+cur_work+"/"+max_work);//System.out.println("cur_work! : "+cur_work);
            	
            	if(cur_work%100 == 0 ) changeProgress();
            	
                //if(cur_work == max_work) break;
                
                if(!progressFlag) break;
                
            }
        	
        	
        	
            return null;
        }
        public void stop(){
        	progressFlag = false;
        }
        /*
         * Executed in event dispatch thread
         */
        public void done() {
        	if(UiGlobals.get_scaleSlider()!= null)
        		UiGlobals.get_scaleSlider().setEnabled(true);
        	
            //Toolkit.getDefaultToolkit().beep();
            startButton.setEnabled(true);
            //taskOutput.append("Node Rendering Finish!\n");
            
            System.out.println("status : "+status);
            if(status == STATUS_CANCELED || status == STATUS_STARTED)
            	frame.setVisible(false);
            
            UiGlobals.setStatusbarText(" Node render is completed.");
            
        }
    }
    
    public NodeLoadingProgressBar(JGraph graph) {
        super(new BorderLayout());
        nodeData = new CNodeData();
        
        this.graph = graph;
        this.pre_scaled = UiGlobals.getPre_scaled();
      
        readCoordData();
        init();

        
        
                
        //Create the demo's UI.
        startButton = new JButton("Start");
        startButton.setActionCommand("start");
        startButton.addActionListener(this);
        
        stopButton = new JButton("Stop");
        stopButton.setActionCommand("Stop");
        stopButton.addActionListener(this);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        cancelButton.addActionListener(this);

        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        //Call setStringPainted now so that the progress bar height
        //stays the same whether or not the string is shown.
        progressBar.setStringPainted(true); 

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);

        JPanel panel = new JPanel();
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(cancelButton);
        //panel.add(progressBar);
        

        add(panel, BorderLayout.PAGE_START);
        add(progressBar, BorderLayout.CENTER);
        //add(new JScrollPane(taskOutput), BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        frame = new JFrame("Node Rendering...");
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        //JComponent newContentPane = new NodeLoadingProgressBar(frame);
        setOpaque(true); //content panes must be opaque
        frame.setContentPane(this);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
        
        //progressBar.setIndeterminate(true);
        startButton.setEnabled(false);
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new NodeTask(nodeData, frame, this.graph);
        //task.addPropertyChangeListener(this);
        status = STATUS_STARTED;
        task.execute();
    }

    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
    	Object s = evt.getSource();
    	
    	if(s == startButton){
    		task = new NodeTask(nodeData, frame, this.graph);
    		status = STATUS_STARTED;
    		task.execute();
    		System.out.println("execute?");
    	}
    	else if(s == stopButton){
    		//progressBar.setIndeterminate(false);
    		status = STATUS_STOPPED;
    		task.stop();

    	}
    	else if(s == cancelButton){
    		//progressBar.setIndeterminate(false);
    		status = STATUS_CANCELED;
    		task.stop();
    		frame.setVisible(false);

    	}
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {

    }
    
    public void changeProgress(){
    	//progressBar.setIndeterminate(false);
    	//System.out.println("progress : "+(float)cur_work*100/max_work);
    	progressBar.setValue((int)((float)cur_work*100/max_work));
        progressBar.setString(cur_work+"/"+max_work);
        //taskOutput.append(String.format(
        //            "%s\n", data.toString(cur_work)));
    }
    
    
    
    public int readCoordData() {
		//int totalCount = getFileLineCount(filename);
		//if(totalCount <= 0) return -1;
		
		String filename = UiGlobals.getFileName()+".coord";

		int lineCount = 0;
		try {
			BufferedReader br = null;
			if(UiGlobals.getFileName()!=null){
				br = Utils.getInputReader(filename);	
			}
			
			if(br == null){
				makeRandomData(50, 300, 300);
				return 0;
			}
			
			String str = null;
			String sep = "\0";
			String[] seps = { "\t", ",", ".", " " };
			while ((str = br.readLine()) != null) {
				if (lineCount == 0) {
					// Find Separator
					for (int count = 0; count < seps.length; count++) {
						if (str.contains(seps[count])) {
							sep = seps[count];
							break;
						}
					}
				}

				String[] subStrs = str.split(sep);

				nodeData.insertItem(subStrs[0], Float.parseFloat(subStrs[1]), Float
						.parseFloat(subStrs[2]));

				lineCount++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lineCount;
	}
    
    public int makeRandomData(int size, int maxWidth, int maxHeight) {
		// data.
		Random random = new Random();

		for (int count = 0; count < size; count++) {
			nodeData.insertItem("random_" + count, random.nextInt(maxWidth), random
					.nextInt(maxHeight));
		}
		return size;
	}
    
    public void init()
    {
    	logger.debug("public void init");
    	logger.debug("====[S]======================");
    	Editor editor = graph.getEditor();
    	
    	float minLocx = Utils.minValue(nodeData.getLocxArry());
		float minLocy = Utils.minValue(nodeData.getLocyArry());
		float maxLocx = Utils.maxValue(nodeData.getLocxArry());
		float maxLocy = Utils.maxValue(nodeData.getLocyArry());

		int width, height;
		
		width = (int) maxLocx - (int) minLocx + NodeRenderManager._PADDING;
		height = (int) maxLocy - (int) minLocy + NodeRenderManager._PADDING;
		

		double scale = UiGlobals.getGrid_scale();//Math.pow(2, pre_scaled - 1);

		
		logger.debug("pre_scaled : " + pre_scaled + ", real scale : "+ scale);
		

		logger.debug("edtor.setScale("+1.0 / scale+")");
		editor.setScale(1.0 / scale);

		nodeData.setPre_scale(scale);
		
		
		int drawingSizeX = (int)(width*scale);
		int drawingSizeY = (int)(height*scale);
		logger.debug("drawingSizeX : "+drawingSizeX);
		logger.debug("drawingSizeY : "+drawingSizeY);
		graph.setDrawingSize(drawingSizeX, drawingSizeY);
		UiGlobals.setDrawingSizeX(drawingSizeX);
		UiGlobals.setDrawingSizeY(drawingSizeY);

		LayerGrid grid = (LayerGrid) editor.getLayerManager().findLayerNamed(
				"Grid");
		HashMap<String, Object> map = new HashMap<String, Object>();
		logger.debug("spacing_include_stamp : "+(UiGlobals.getGrid_spacing()*scale));
		map.put("spacing_include_stamp", (int)(UiGlobals.getGrid_spacing()*scale));
		logger.debug("thick : "+scale);
		map.put("thick", (int) scale);
		
		grid.adjust(map);
		
		UiGlobals.setStatusbarText(" resolution : x "+scale);
		logger.debug("====[E]======================");
    }
}