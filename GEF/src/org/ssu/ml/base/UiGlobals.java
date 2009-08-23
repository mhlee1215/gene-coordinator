package org.ssu.ml.base;

import java.applet.Applet;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JSlider;

import org.ssu.ml.ui.NodeRenderManager;
import org.tigris.gef.base.Globals;

public class UiGlobals extends Globals{
	private static Vector<double[]> gridDatas = new Vector<double[]>();
	private static Vector<String> gridCategories = new Vector<String>();
	private static Applet _curApplet = null;
	private static String fileName = "";
	private static int default_grid_spacing = 20;
	private static int drawingSizeX;
	private static int drawingSizeY;
	private static int pre_scaled;
	
	public static Vector<String> getGridCategories() {
		return gridCategories;
	}

	public static void setGridCategories(Vector<String> gridCategories) {
		UiGlobals.gridCategories = gridCategories;
	}
	
	public static int getPre_scaled() {
		return pre_scaled;
	}

	public static void setPre_scaled(int preScaled) {
		pre_scaled = preScaled;
	}

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		UiGlobals.fileName = fileName;
	}
	
	public static int getDefault_grid_spacing() {
		return default_grid_spacing;
	}

	public static void setDefault_grid_spacing(int defaultGridSpacing) {
		default_grid_spacing = defaultGridSpacing;
	}

	public static Vector<double[]> getGridDatas() {
		return gridDatas;
	}

	public static void setGridDatas(Vector<double[]> gridDatas) {
		UiGlobals.gridDatas = gridDatas;
	}

	private static JSlider _gridSlider = null;

	public static Applet get_curApplet() {
		return _curApplet;
	}

	public static void set_curApplet(Applet curApplet) {
		_curApplet = curApplet;
	}

	public static JSlider get_gridSlider() {
		return _gridSlider;
	}

	public static void set_gridSlider(JSlider gridSlider) {
		_gridSlider = gridSlider;
	}
	
	private static JSlider _scaleSlider = null;

	public static JSlider get_scaleSlider() {
		return _scaleSlider;
	}

	public static void set_scaleSlider(JSlider scaleSlider) {
		_scaleSlider = scaleSlider;
	}
	
	private static JLabel _statusBar = null;

	public static JLabel get_statusBar() {
		return _statusBar;
	}

	public static void set_statusBar(JLabel statusBar) {
		_statusBar = statusBar;
	}
	
	public static void setStatusbarText(String text)
	{
		if(_statusBar != null)
			_statusBar.setText(text);
	}
	
	public static NodeRenderManager nodeRenderManager = null;

	public static NodeRenderManager getNodeRenderManager() {
		return nodeRenderManager;
	}

	public static void setNodeRenderManager(NodeRenderManager nodeRenderManager) {
		UiGlobals.nodeRenderManager = nodeRenderManager;
	}
	


	public static int getDrawingSizeX() {
		return drawingSizeX;
	}

	public static void setDrawingSizeX(int drawingSizeX) {
		UiGlobals.drawingSizeX = drawingSizeX;
	}

	public static int getDrawingSizeY() {
		return drawingSizeY;
	}

	public static void setDrawingSizeY(int drawingSizeY) {
		UiGlobals.drawingSizeY = drawingSizeY;
	}
	
	
	
	
	
	
	
}
