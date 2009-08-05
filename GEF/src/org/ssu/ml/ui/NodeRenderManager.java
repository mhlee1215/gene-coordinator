package org.ssu.ml.ui;

import java.util.HashMap;

import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.graph.presentation.JGraph;

public class NodeRenderManager {

	private JGraph graph = null;
	private CNodeData data = null;
	private int width = 0;
	private int height = 0;
	public static final int _PADDING = 50;
	
	public NodeRenderManager(JGraph graph)
	{
		this.graph = graph;
	}
	
	public void init(CNodeData data, int width, int height)
	{
		this.data = data;
		this.width = width;
		this.height = height;
	}
	
	public void drawNodes(int pre_scaled)
	{
		drawNodes(pre_scaled, true);
	}
	
	public void drawNodes(int pre_scaled, boolean removeExistedNodes)
	{

		Editor editor = graph.getEditor();
		
		if(removeExistedNodes)
			editor.getLayerManager().getActiveLayer().removeAll();
		
		float minLocx = Utils.minValue(data.getLocxArry());
		float minLocy = Utils.minValue(data.getLocyArry());
		float maxLocx = Utils.maxValue(data.getLocxArry());
		float maxLocy = Utils.maxValue(data.getLocyArry());

		width = (int) maxLocx - (int) minLocx + _PADDING;
		height = (int) maxLocy - (int) minLocy + _PADDING;
		

		double scale = Math.pow(2, pre_scaled - 1);

		
		System.out.println("pre_scaled : " + pre_scaled + ", real scale : "
				+ scale);
		

		
		// int pre_scaled = 2;
		editor.setScale(1.0 / scale);

		data.setPre_scale(scale);
		
		
		int drawingSizeX = (int)(width*scale);
		int drawingSizeY = (int)(height*scale);
		graph.setDrawingSize(drawingSizeX, drawingSizeY);
		UiGlobals.setDrawingSizeX(drawingSizeX);
		UiGlobals.setDrawingSizeY(drawingSizeY);

		LayerGrid grid = (LayerGrid) editor.getLayerManager().findLayerNamed(
				"Grid");
		HashMap<String, Object> map = new HashMap<String, Object>();

		double defaultSpace = (int) Math.pow(2, 5);
		map.put("spacing", (int) (scale * defaultSpace));
		map.put("thick", (int) scale);
		grid.adjust(map);

		//int maxNodeNum = data.getPointCount();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// createAndShowGUI();
				new NodeLoadingProgressBar(data, graph);
			}
		});
		
		UiGlobals.setStatusbarText(" resolution : x "+scale);
	}
}
