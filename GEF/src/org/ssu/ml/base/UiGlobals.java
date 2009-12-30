package org.ssu.ml.base;

import java.awt.Font;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

import org.ssu.ml.ui.NodeRenderManager;
import org.tigris.gef.base.Globals;

public class UiGlobals extends Globals{
	public static String isExample = "N";
	public static String exampleType = "1";
	
	private static Font normalFont = new Font("Lucida Grande", Font.PLAIN, 10);
	private static Font titleFont = new Font("Lucida Grande", Font.BOLD, 25);
	private static ColorPool constantColor = new ColorPool();
	
	private static Vector<double[]> gridDatas = new Vector<double[]>();
	private static Vector<CGridState> gridStes = new Vector<CGridState>();
	private static Vector<String> gridCategories = new Vector<String>();
	
	private static String fileName = "";
	private static int default_grid_spacing = 20;
	private static int drawingSizeX;
	private static int drawingSizeY;
	private static int pre_scaled = 4;
	
	private static int grid_spacing =  default_grid_spacing;
	private static int grid_scale = pre_scaled; 
	
	private static JFrame distFrame;
	
	public static void init(){
	    gridDatas.clear();
	    gridStes.clear();
	    gridCategories.clear();
	}
	
	
	
	public static String getIsExample() {
		return isExample;
	}



	public static void setIsExample(String isExample) {
		UiGlobals.isExample = isExample;
	}



	public static String getExampleType() {
		return exampleType;
	}



	public static void setExampleType(String exampleType) {
		UiGlobals.exampleType = exampleType;
	}



	public static JFrame getDistFrame() {
		return distFrame;
	}

	public static void setDistFrame(JFrame distFrame) {
		UiGlobals.distFrame = distFrame;
	}

	public static int getGrid_scale() {
		return grid_scale;
	}

	public static void setGrid_scale(int gridScale) {
		grid_scale = gridScale;
	}

	public static int getGrid_spacing() {
		return grid_spacing;
	}

	public static void setGrid_spacing(int gridSpacing) {
		grid_spacing = gridSpacing;
	}

	public static Vector<CGridState> getGridStes() {
		return gridStes;
	}

	public static void setGridStes(Vector<CGridState> gridStes) {
		UiGlobals.gridStes = gridStes;
	}

	public static Font getTitleFont() {
		return titleFont;
	}

	public static void setTitleFont(Font titleFont) {
		UiGlobals.titleFont = titleFont;
	}
	
	public static Font getNormalFont() {
		return normalFont;
	}

	public static void setNormalFont(Font normalFont) {
		UiGlobals.normalFont = normalFont;
	}

	
	
	public static ColorPool getConstantColor() {
		return constantColor;
	}

	public static void setConstantColor(ColorPool constantColor) {
		UiGlobals.constantColor = constantColor;
	}
	
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
