package org.ssu.ml.base;

import java.applet.Applet;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JSlider;

import org.ssu.ml.ui.NodeRenderManager;
import org.tigris.gef.base.Globals;

public class UiGlobals extends Globals{
	private static Vector<double[]> gridDatas = new Vector<double[]>();
	
	private static Applet _curApplet = null;
	
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
	
	static int drawingSizeX;
	static int drawingSizeY;

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
