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

import org.ssu.ml.base.NodeDescriptor;
import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.base.Editor;
import org.tigris.gef.demo.SampleNode;
import org.tigris.gef.graph.presentation.DefaultGraphModel;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigCircle;
import org.tigris.gef.presentation.FigNode;
import org.tigris.gef.presentation.FigRect;

import java.beans.*;
import java.util.Random;

import org.ssu.ml.presentation.FigCustomNode;


public class NodeLoadingProgressBar extends JPanel
                              implements ActionListener, 
                                         PropertyChangeListener {

    private JProgressBar progressBar;
    private JButton startButton;
    private JButton stopButton;
    private JButton cancelButton;
    private JTextArea taskOutput;
    private Task task;
    
    private int max_work = 50000;
    private int cur_work = 0;
    
    private JFrame frame = null;
    private JGraph _graph = null;
    private CNodeData data;

    class Task extends SwingWorker<Void, Void> {
    	boolean progressFlag = true;
    	CNodeData data = null;
    	float minLocx = 0;
    	float minLocy = 0;
    	float maxLocx = 0;
    	float maxLocy = 0;
    	
    	JGraph _graph = null;
    	JFrame frame = null; 
    	

    	public Task(CNodeData data, JFrame frame, JGraph graph)
    	{
    		this.data = data;
    		this.frame = frame;
    		this._graph = graph;
    		
    		minLocx = Utils.minValue(data.getLocxArry());
        	minLocy = Utils.minValue(data.getLocyArry());
        	maxLocx = Utils.maxValue(data.getLocxArry());
        	maxLocy = Utils.maxValue(data.getLocyArry());
    	}
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            //Initialize progress property.
            setProgress(0);
            //Sleep for at least one second to simulate "startup".
         

        	//System.out.println("max_work : "+max_work);
            DefaultGraphModel dgm = (DefaultGraphModel) _graph.getGraphModel();
            Editor editor = _graph.getEditor();
            
        	for(int count = cur_work ; count < max_work ; count++){
            	int locx = (int)((data.getLocxArry()[count]+Math.abs(minLocx))*data.getPre_scale()) + data.getPadding()/2;
            	int locy = (int)((data.getLocyArry()[count]+Math.abs(minLocy))*data.getPre_scale()) + data.getPadding()/2;
            	
            	//System.out.println(locx+", "+locy);
            	//FigCircle rect = new FigCircle(locx, locy, 1, 1);
            	NodeDescriptor desc = new NodeDescriptor();
            	desc.setName(data.getPointerName(count));
            	desc.setGroup(data.getGroup(count));
            	FigCustomNode rect = new FigCustomNode(locx, locy, 1, 1, desc);
            	
            	rect.setLineColor(data.getColor(count));
            	//CustomNode rect = new CustomNode();
            	
            	//rect.setLocation(locx, locy);
            	//rect.setSize(1, 1);
            	//rect.setResizable(false);
            	//rect.setMovable(false);
            	rect.setLocked(true);
            	//FigText rect = new FigText(locx, locy, 1, 1);
            	//rect.setSize(10, 10);
            	//rect.setText("count "+count);
            	//editor.getGraphModel();
            	//rect.initialize(null);
            	//dgm.addNode(rect);
            	editor.add(rect);
            	//_p.advance();
            	cur_work++;
            	if(cur_work%1000 == 0) UiGlobals.setStatusbarText(" Node Rendering... "+cur_work+"/"+max_work);//System.out.println("cur_work! : "+cur_work);
            	
            	if(cur_work%100 == 0 ) changeProgress();
            	
                if(cur_work == max_work) break;
                
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
            if(cur_work == max_work)
            	frame.setVisible(false);
            
            UiGlobals.setStatusbarText(" Node render is completed.");
            
        }
    }

    public NodeLoadingProgressBar(CNodeData data, JGraph editor) {
        super(new BorderLayout());

        this._graph = editor;
        this.max_work = data.getPointCount();
        this.data = data;
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
        task = new Task(data, frame, this._graph);
        //task.addPropertyChangeListener(this);
        task.execute();
    }

    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
    	Object s = evt.getSource();
    	
    	if(s == startButton){
    		task = new Task(data, frame, this._graph);
    		task.execute();
    		System.out.println("execute?");
    	}
    	else if(s == stopButton){
    		//progressBar.setIndeterminate(false);
    		task.stop();

    	}
    	else if(s == cancelButton){
    		//progressBar.setIndeterminate(false);
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
    	progressBar.setValue((int)((float)cur_work*100/max_work));
        progressBar.setString(cur_work+"/"+max_work);
        //taskOutput.append(String.format(
        //            "%s\n", data.toString(cur_work)));
    }
}