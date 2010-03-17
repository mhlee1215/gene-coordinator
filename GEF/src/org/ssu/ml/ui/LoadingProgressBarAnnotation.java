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
import org.ssu.ml.base.CmdGridChart;
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
import java.util.Vector;

import org.ssu.ml.presentation.FigCustomNode;
import org.ssu.ml.ui.LoadingProgressBarNode.NodeTask;


public class LoadingProgressBarAnnotation extends JPanel
                              implements ActionListener, 
                                         PropertyChangeListener {
	Logger logger = Logger.getLogger(LoadingProgressBarAnnotation.class);
	
	private AnnotationTask task;
	String filename;

    class AnnotationTask extends SwingWorker<Void, Void> {
    	boolean progressFlag = true;
    	String filename = "";

    	public AnnotationTask(String filename)
    	{
    		this.filename = filename;
    	}
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
            
         
            //Initialize progress property.
            setProgress(0);
            
            String AnnotationFileName = filename;
            System.out.println("Annotation file name: "+filename);
    		Vector<String> headerColumn = new Vector<String>();
    		HashMap<String, HashMap<Integer, String>> annotationContent = new HashMap<String, HashMap<Integer, String>>();
    	
    		try {
    			BufferedReader br = Utils.getInputReader(AnnotationFileName);
    			
    			String strTmp = "";
    			
    			int count = 0;
    			
    			while((strTmp=br.readLine()) != null)
    			{
    				
    				if(!strTmp.startsWith("#"))
    				{
    					
    					if(count == 0){
    						//Read head.
    						String[] strs = strTmp.split(",");
    						for(int headCnt = 0 ; headCnt < strs.length ; headCnt++)
    						{
    							headerColumn.add(strs[headCnt]);
    						}
    					}else{
    						String[] strs = strTmp.split(",");
    						//Target ID
    						
    						
    						
    						HashMap<Integer, String> contentMap = new HashMap<Integer, String>();
    						String proveId = strs[0].replace("\"", "").trim();
    						for(int strCnt = 0 ; strCnt < strs.length ; strCnt++)
    						{
    							
    							String contentTmp = strs[strCnt].replace("\"", "").trim();
    							contentMap.put(strCnt, contentTmp);
    						}
    						annotationContent.put(proveId, contentMap);
    						
    						if(count%100 == 0){
    							//System.out.println(strTmp);
    							String output = String.format("%.0f", ((double)count*100)/UiGlobals.getNodeCount())+"% An annotation file is being loaded..";
    							UiGlobals.getPropertySearchField().setText(output);
    							UiGlobals.setStatusbarText(output);
    							//System.out.println("["+count+"] :: "+proveId);
    						}
    						
    					}
    					count++;
    				}
    			}		
    			UiGlobals.setAnnotationHeader(headerColumn);
    			UiGlobals.setAnnotationContent(annotationContent);
    			br.close();
    			
    			Editor editor = UiGlobals.curEditor();
    			java.util.List<Fig> nodes = editor.getLayerManager().getActiveLayer().getContents();
    			String prefix = "<html><body style=\"background-color: #ffffdd\"><h3><font color=#000000><span >";
	            String postfix = "</span></font></h3></body></html>";
    			for(int count1 = 0 ; count1 < nodes.size() ; count1++)
    	        {
    	        	Fig node = nodes.get(count1);
    	        	FigCustomNode nodeCustom = (FigCustomNode)node;
    	        	NodeDescriptor desc = (NodeDescriptor)nodeCustom.getOwner();
    	        	String resultToolTipContent = "";
    	        	
    	        	
    	        	resultToolTipContent = prefix;
    	        	resultToolTipContent += postfix;
    	        	
    	        	
    	        	
    	            
    	        }
    		}catch(Exception e){
    			e.printStackTrace();
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
        	UiGlobals.getPropertySearchButton().setEnabled(true);
        	UiGlobals.getPropertySearchCombo().setEnabled(true);
        	UiGlobals.getPropertySearchCombo().setPreferredSize(new Dimension(200, 30));
        	String[] propertyItem = new String[UiGlobals.getAnnotationHeader().size()];
        	UiGlobals.getAnnotationHeader().toArray(propertyItem);
        	
        	for(int i = 0 ; i < propertyItem.length ; i++)
        		UiGlobals.getPropertySearchCombo().addItem(propertyItem[i]);
        	UiGlobals.getPropertySearchField().setEnabled(true);
        	UiGlobals.getPropertySearchField().setPreferredSize(new Dimension(150, 30));
        	UiGlobals.getPropertySearchField().setText("");
        	UiGlobals.getPropertyResetButton().setEnabled(true);
        }
    }
    
    public LoadingProgressBarAnnotation(String filename) {
        super(new BorderLayout());
        
        init();

        
        
                
        //Instances of javax.swing.SwingWorker are not reusuable, so
        //we create new instances as needed.
        task = new AnnotationTask(filename);
        //task.addPropertyChangeListener(this);
        //status = STATUS_STARTED;
        task.execute();
    }

    /**
     * Invoked when the user presses the start button.
     */
    public void actionPerformed(ActionEvent evt) {
    	Object s = evt.getSource();
    	
    }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {

    }
    
    public void changeProgress(){
    	//progressBar.setIndeterminate(false);
    	//System.out.println("progress : "+(float)cur_work*100/max_work);
    	//progressBar.setValue((int)((float)cur_work*100/max_work));
        //progressBar.setString(cur_work+"/"+max_work);
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
		}
		return size;
	}
    
    public void init()
    {
    	logger.debug("public void init");
    	logger.debug("====[S]======================");
    }
}