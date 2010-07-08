package org.ssu.ml.base;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.JXPanel;
import org.ssu.ml.presentation.FigCustomNode;
import org.ssu.ml.presentation.JNodeInfoPanel;
import org.ssu.ml.ui.CNodeData;
import org.ssu.ml.ui.NodeRenderManager;
import org.tigris.gef.base.Globals;
import org.tigris.gef.presentation.Fig;

public class UiGlobals extends Globals{
	public static String isExample = "N";
	public static String exampleType = "1";
	
	private static Font normalFont = new Font("Lucida Grande", Font.PLAIN, 10);
	private static Font titleFont = new Font("Lucida Grande", Font.BOLD, 25);
	private static ColorPool constantColor = new ColorPool();
	private static Color searchMarkColor = null;
	
	private static Vector<double[]> gridDatas = new Vector<double[]>();
	private static Vector<CGridState> gridStes = new Vector<CGridState>();
	private static Vector<String> gridCategories = new Vector<String>();
	
	private static Vector<String> annotationHeader = new Vector<String>();
	private static HashMap<String, HashMap<Integer, String>> annotationContent = new HashMap<String, HashMap<Integer, String>>();
	private static GeneFunctionSet functionUniverse = null;
	
	private static String fileName = "";
	private static String annotationFileName = "";
	private static int default_grid_spacing = 20;
	private static int drawingSizeX;
	private static int drawingSizeY;
	private static int pre_scaled = 4;
	
	private static int grid_spacing =  default_grid_spacing;
	private static int grid_scale = pre_scaled; 
	
	private static JFrame distFrame;
	
	private static JTextField propertySearchField;
	private static JComboBox propertySearchCombo;
	private static JButton propertySearchButton;
	private static JButton propertyResetButton;
	
	private static JXMultiSplitPane msp = null;
	private static JNodeInfoPanel nodeInfoPanel = null;
	private static JXFrame nodeInfoFrame = null;
	
	private static JPanel coordBottomPanel = null;
	
	private static String searchType = "New";
	private static boolean isShowOnlyFound = false;
	
	private static JComboBox showLayerCombo;
	private static HashMap<String, Color> layerColor = new HashMap<String, Color>();
	private static HashMap<String, List<Fig>> layerData = new HashMap<String, List<Fig>>();
	
	private static HashMap<String, FigCustomNode> nodeHash = null;
	private static Vector<FigCustomNode> infoMarkedNode = new Vector<FigCustomNode>();
	
	private static JPanel graphPane = null;
	private static JPanel mainPane = null;
	
	private static CNodeData cNodeData = null;
	
	private static HashMap<String, HashMap<String, Integer>> preCalFunctionData = null;
	
	
	public static HashMap<String, HashMap<String, Integer>> getPreCalFunctionData() {
		return preCalFunctionData;
	}

	public static void setPreCalFunctionData(
			HashMap<String, HashMap<String, Integer>> preCalFunctionData) {
		UiGlobals.preCalFunctionData = preCalFunctionData;
	}

	/**
	 * @return the functionUniverse
	 */
	public static GeneFunctionSet getFunctionUniverse() {
		return functionUniverse;
	}

	/**
	 * @param functionUniverse the functionUniverse to set
	 */
	public static void setFunctionUniverse(GeneFunctionSet functionUniverse) {
		UiGlobals.functionUniverse = functionUniverse;
	}

	/**
	 * @return the cNodeData
	 */
	public static CNodeData getcNodeData() {
		return cNodeData;
	}

	/**
	 * @param cNodeData the cNodeData to set
	 */
	public static void setcNodeData(CNodeData cNodeData) {
		UiGlobals.cNodeData = cNodeData;
	}

	/**
	 * @return the mainPane
	 */
	public static JPanel getMainPane() {
		return mainPane;
	}

	/**
	 * @param mainPane the mainPane to set
	 */
	public static void setMainPane(JPanel mainPane) {
		UiGlobals.mainPane = mainPane;
	}

	/**
	 * @return the graphPane
	 */
	public static JPanel getGraphPane() {
		return graphPane;
	}

	/**
	 * @param graphPane the graphPane to set
	 */
	public static void setGraphPane(JPanel graphPane) {
		UiGlobals.graphPane = graphPane;
	}

	/**
	 * @return the infoMarkedNode
	 */
	public static Vector<FigCustomNode> getInfoMarkedNode() {
		return infoMarkedNode;
	}

	/**
	 * @param infoMarkedNode the infoMarkedNode to set
	 */
	public static void setInfoMarkedNode(Vector<FigCustomNode> infoMarkedNode) {
		UiGlobals.infoMarkedNode = infoMarkedNode;
	}

	/**
	 * @return the nodeHash
	 */
	public static HashMap<String, FigCustomNode> getNodeHash() {
		return nodeHash;
	}

	/**
	 * @param nodeHash the nodeHash to set
	 */
	public static void setNodeHash(HashMap<String, FigCustomNode> nodeHash) {
		UiGlobals.nodeHash = nodeHash;
	}

	/**
	 * @return the layerColor
	 */
	public static HashMap<String, Color> getLayerColor() {
		return layerColor;
	}

	/**
	 * @param layerColor the layerColor to set
	 */
	public static void setLayerColor(HashMap<String, Color> layerColor) {
		UiGlobals.layerColor = layerColor;
	}

	/**
	 * @return the layerData
	 */
	public static HashMap<String, List<Fig>> getLayerData() {
		return layerData;
	}

	/**
	 * @param layerData the layerData to set
	 */
	public static void setLayerData(HashMap<String, List<Fig>> layerData) {
		UiGlobals.layerData = layerData;
	}


	/**
	 * @return the showLayerCombo
	 */
	public static JComboBox getShowLayerCombo() {
		return showLayerCombo;
	}


	/**
	 * @param showLayerCombo the showLayerCombo to set
	 */
	public static void setShowLayerCombo(JComboBox showLayerCombo) {
		UiGlobals.showLayerCombo = showLayerCombo;
	}


	/**
	 * @return the isShowOnlyFound
	 */
	public static boolean isShowOnlyFound() {
		return isShowOnlyFound;
	}


	/**
	 * @param isShowOnlyFound the isShowOnlyFound to set
	 */
	public static void setShowOnlyFound(boolean isShowOnlyFound) {
		UiGlobals.isShowOnlyFound = isShowOnlyFound;
	}


	/**
	 * @return the searchType
	 */
	public static String getSearchType() {
		return searchType;
	}


	/**
	 * @param searchType the searchType to set
	 */
	public static void setSearchType(String searchType) {
		UiGlobals.searchType = searchType;
	}


	/**
	 * @return the searchMarkColor
	 */
	public static Color getSearchMarkColor() {
		return searchMarkColor;
	}


	/**
	 * @param searchMarkColor the searchMarkColor to set
	 */
	public static void setSearchMarkColor(Color searchMarkColor) {
		UiGlobals.searchMarkColor = searchMarkColor;
	}


	public static void showNodeInfoList(List<Fig> selectedFigList){
		if(selectedFigList == null){
			if(UiGlobals.getNodeInfoPanel() == null || UiGlobals.getNodeInfoPanel().getFigList() == null)
				return;
		}
		
		if(UiGlobals.getNodeInfoFrame() == null){
    		UiGlobals.setNodeInfoPanel(new JNodeInfoPanel());
    		UiGlobals.getNodeInfoPanel().removeAll();
        	
        	
        	//UiGlobals.getMsp().getMultiSplitLayout().displayNode("left.middle", true);
    		UiGlobals.setNodeInfoFrame(new JXFrame("Selected node(s) info."));
    	}
    	UiGlobals.getNodeInfoPanel().setColumnData(UiGlobals.getAnnotationHeader());
    	UiGlobals.getNodeInfoPanel().showList(selectedFigList);
    	
    	UiGlobals.getNodeInfoFrame().add(UiGlobals.getNodeInfoPanel());
    	UiGlobals.getNodeInfoFrame().invalidate();
    	UiGlobals.getNodeInfoFrame().setVisible(true);
	}
	
	
	/**
	 * @return the coordBottomPanel
	 */
	public static JPanel getCoordBottomPanel() {
		return coordBottomPanel;
	}


	/**
	 * @param coordBottomPanel the coordBottomPanel to set
	 */
	public static void setCoordBottomPanel(JPanel coordBottomPanel) {
		UiGlobals.coordBottomPanel = coordBottomPanel;
	}


	/**
	 * @return the nodeInfoFrame
	 */
	public static JXFrame getNodeInfoFrame() {
		return nodeInfoFrame;
	}
	/**
	 * @param nodeInfoFrame the nodeInfoFrame to set
	 */
	public static void setNodeInfoFrame(JXFrame nodeInfoFrame) {
		UiGlobals.nodeInfoFrame = nodeInfoFrame;
		UiGlobals.nodeInfoFrame.setSize(500, 300);
	}
	public static JXMultiSplitPane getMsp() {
		return msp;
	}
	public static void setMsp(JXMultiSplitPane msp) {
		UiGlobals.msp = msp;
	}
	public static JNodeInfoPanel getNodeInfoPanel() {
		return nodeInfoPanel;
	}
	public static void setNodeInfoPanel(JNodeInfoPanel nodeInfoPanel) {
		UiGlobals.nodeInfoPanel = nodeInfoPanel;
	}

	private static int nodeCount = 0;
	
	public static int getNodeCount() {
		return nodeCount;
	}
	public static void setNodeCount(int nodeCount) {
		UiGlobals.nodeCount = nodeCount;
	}
	public static JTextField getPropertySearchField() {
		return propertySearchField;
	}
	public static void setPropertySearchField(JTextField propertySearchField) {
		UiGlobals.propertySearchField = propertySearchField;
	}
	public static JComboBox getPropertySearchCombo() {
		return propertySearchCombo;
	}
	public static JButton getPropertyResetButton() {
		return propertyResetButton;
	}
	public static void setPropertyResetButton(JButton propertyResetButton) {
		UiGlobals.propertyResetButton = propertyResetButton;
	}
	public static void setPropertySearchCombo(JComboBox propertySearchCombo) {
		UiGlobals.propertySearchCombo = propertySearchCombo;
	}
	public static JButton getPropertySearchButton() {
		return propertySearchButton;
	}
	public static void setPropertySearchButton(JButton propertySearchButton) {
		UiGlobals.propertySearchButton = propertySearchButton;
	}



	public static void init(){
	    gridDatas.clear();
	    gridStes.clear();
	    gridCategories.clear();
	}
	
	
	
	public static Vector<String> getAnnotationHeader() {
		return annotationHeader;
	}

	public static void setAnnotationHeader(Vector<String> annotationHeader) {
		UiGlobals.annotationHeader = annotationHeader;
	}

	

	public static HashMap<String, HashMap<Integer, String>> getAnnotationContent() {
		return annotationContent;
	}
	public static void setAnnotationContent(
			HashMap<String, HashMap<Integer, String>> annotationContent) {
		UiGlobals.annotationContent = annotationContent;
	}
	public static String getAnnotationFileName() {
		return annotationFileName;
	}

	public static void setAnnotationFileName(String annotationFileName) {
		UiGlobals.annotationFileName = annotationFileName;
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
