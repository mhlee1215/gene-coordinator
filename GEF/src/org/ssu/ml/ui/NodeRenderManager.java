package org.ssu.ml.ui;

import java.util.HashMap;

import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.graph.presentation.JGraph;

public class NodeRenderManager {

	private JGraph graph = null;
	private int width = 0;
	private int height = 0;
	public static final int _PADDING = 50;
	
	public NodeRenderManager(JGraph graph)
	{
		this.graph = graph;
	}
	
	public void init(int width, int height)
	{
		this.width = width;
		this.height = height;
	}
	
	
	public void drawNodes(boolean removeExistedNodes)
	{
		Editor editor = graph.getEditor();
		
		if(removeExistedNodes)
			editor.getLayerManager().getActiveLayer().removeAll();
		
		

		
		//int maxNodeNum = data.getPointCount();
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// createAndShowGUI();
				new LoadingProgressBarNode(graph);
			}
		});
		
		/*
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// createAndShowGUI();
				new EdgeLoadingProgressBar(nodeData, edgeData, graph);
			}
		});
		*/
		
	}
}
