package org.ssu.ml.base;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXPanel;
import org.ssu.ml.presentation.CustomCellRenderer1;
import org.ssu.ml.presentation.FigCustomNode;
import org.ssu.ml.presentation.JNodeInfoTableModel;
import org.ssu.ml.presentation.MultiLineCellRenderer;
import org.tigris.gef.base.CmdReorder;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.presentation.Fig;

public class GeneFunctionSet extends JFrame implements Runnable {
	
	static final Color COLOR_STANDBY = new Color(209, 209, 209);
	static final Color COLOR_EMPTY = new Color(247, 247, 247);
	static final Color COLOR_FINISH = new Color(130, 184, 255);
	static final Color COLOR_PROCESSING = new Color(255, 96, 43);
	
	static final int VALUE_STANDBY = 1;
	static final int VALUE_EMPTY = 2;
	static final int VALUE_FINISH = 3;
	static final int VALUE_PROCESSING = 4;
	
	BigInteger[] binomA = null;
	BigInteger[] binomB = null;
	BigInteger[] binomC = null;
	
	Double[] logMap = null;
	
	
	
	List<String> geneIds = new Vector<String>();
	HashMap<String, HashMap<Integer, List<String>>> functionUniverse = null;
	
	int maxGridX = 0;
	int maxGridY = 0;
	HashMap<Point, HashMap<Integer, List<String>>> gridFuncData = null;
	HashMap<Point, List<String>> gridGeneSetData = null;
	
	List<Fig> nodes = null;
	int interval_space = 0;
    int xOffset = 0;
    int yOffset = 0;
    
	JXPanel mainPanel = null;
	
	
	JXPanel resultPanel = null;
	JScrollPane scrollPane = null;
	JTextField filterText = null;
	private JComboBox filterColumn;
	private TableRowSorter<JNodeInfoTableModel> sorter;
	LinkedHashMap<String, Double>[][] resultMap = null;
	JTable nodeTable = null;
	
	
	int[][] process = null;
	JXPanel[][] processPanel = null;
	
	public GeneFunctionSet(HashMap<String, HashMap<Integer, List<String>>> functionUniverse){
		this.functionUniverse = functionUniverse;
		
		Set<String> keys = functionUniverse.keySet();
		for(String id : keys){
			geneIds.add(id);
		}
		
		int N = functionUniverse.keySet().size();
		preLogCal(N);
		
		
		setSize(new Dimension(500, 500));
		
	}
	
	public void paint(Graphics g){
		System.out.println("paint, size: "+this.getSize());
		super.paint(g);
		mainPanel.removeAll();
		//System.out.println("P: "+process.length+", "+process[0].length);
		for(int i = 0 ; i <= maxGridY ; i++){
			for(int j = 0 ; j <= maxGridX ; j++){
				//JXPanel panel = processPanel[i][j];
				
				if(process[i][j] == VALUE_FINISH)
					processPanel[i][j].setBackground(COLOR_FINISH);
				else if(process[i][j] == VALUE_STANDBY)
					processPanel[i][j].setBackground(COLOR_STANDBY);
				else if(process[i][j] == VALUE_EMPTY)
					processPanel[i][j].setBackground(COLOR_EMPTY);
				else if(process[i][j] == VALUE_PROCESSING)
					processPanel[i][j].setBackground(COLOR_PROCESSING);
				
				processPanel[i][j].setPreferredSize(new Dimension(this.getSize().width/maxGridX, this.getSize().height/maxGridY));
    			//System.out.println("add.. i: "+i+", j: "+j);
    			if(j+1 > maxGridX )
    				mainPanel.add(processPanel[i][j], "wrap, width 1::, height 1::");
    			else
    				mainPanel.add(processPanel[i][j], "width 1::, height 1::");
			}
		}
	}
	
	public void run(){
		init();
		setVisible(true);
		invalidate();
		
		long start = System.currentTimeMillis();
		int n = this.getContainAttributeGeneSize(1, "transport");
		//int qSize = data.size();
		/*for(int i = 0 ; i < 50 ; i++){
			System.out.println("itor["+i+"]");
			getAdjPValue(data, qSize, 1, "transport", n);
		}*/
		
		genGeneFunctionByGrid(1);
		getGeneSetByGrid();
		int columnIndex = 1;
		int itorCnt = 0;
		
		System.out.println("maxGridX: "+maxGridX);
		System.out.println("maxGridY: "+maxGridY);
		
		int filledGridCnt = 0;
		for(int i = 0 ; i <= maxGridY ; i++){
         	for(int j = 0 ; j <= maxGridX ; j++){
         		List<String> curGeneSet = gridGeneSetData.get(new Point(j, i));
         		HashMap<Integer, List<String>> curFuncSet = gridFuncData.get(new Point(j, i));
         		if(curGeneSet != null && curFuncSet != null)
         		{
         			List<String> funcList = curFuncSet.get(columnIndex);
         			if(funcList != null){
         				filledGridCnt++;
         				
         			}else{
         			}
         		}else{
         		}
         	}
         }
		
		int calculatedGridCnt = 0;
		for(int i = 0 ; i <= maxGridY ; i++){
         	for(int j = 0 ; j <= maxGridX ; j++){
         		//System.out.println("GRID: "+i+", "+j);
         		List<String> curGeneSet = gridGeneSetData.get(new Point(j, i));
         		HashMap<Integer, List<String>> curFuncSet = gridFuncData.get(new Point(j, i));
         		if(curGeneSet != null && curFuncSet != null)
         		{
         			List<String> funcList = curFuncSet.get(columnIndex);
         			if(funcList != null){
         				System.out.print("O");
         				
         				process[i][j] = VALUE_PROCESSING;
         				processPanel[i][j].setBackground(COLOR_PROCESSING);
         				//this.repaint();
         				//mainPanel.getComponent(i*(maxGridX+1)+j).setBackground(Color.blue);
         				calculatedGridCnt++;
         				for(int k = 0 ; k < funcList.size() ; k++){
         					double pvalue = getAdjPValue(curGeneSet, curGeneSet.size(), 1, funcList.get(k), n);
         					resultMap[i][j].put(funcList.get(k), Math.exp(pvalue));
         					itorCnt++;
         					
         				}
         				process[i][j] = VALUE_FINISH;
         				processPanel[i][j].setBackground(COLOR_FINISH);
         			}else{
         				System.out.print("X");
         				process[i][j] = VALUE_EMPTY;
         				//mainPanel.getComponent(i*maxGridX+1+j).setBackground(Color.white);
         				processPanel[i][j].setBackground(COLOR_EMPTY);
         			}
         		}else{
         			System.out.print("X");
         			process[i][j] = VALUE_EMPTY;
         			//mainPanel.getComponent(i*maxGridX+1+j).setBackground(Color.white);
         			processPanel[i][j].setBackground(COLOR_EMPTY);
         		}
         	}
         	System.out.println(" validCnt: "+calculatedGridCnt+"/"+filledGridCnt+", itorCnt: "+itorCnt);
         }
		
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		start = System.currentTimeMillis();
		float elapsedTimeSec = elapsedTimeMillis/1000F;
		System.out.println("time: "+elapsedTimeSec+", itorCnt: "+itorCnt);
		showList();
	}
	
	public void init(){
		
		System.out.println("init");
		
		maxGridX = 0;
		maxGridY = 0;
		
		mainPanel = new JXPanel();
		resultPanel = new JXPanel();
		this.setContentPane(mainPanel);
		mainPanel.setBackground(Color.white);
		this.setContentPane(mainPanel);
		mainPanel.setPreferredSize(getSize());
		mainPanel.setSize(getSize());
		mainPanel.invalidate();
		System.out.println("size: "+mainPanel.getSize());
		
		MigLayout layout = new MigLayout(new LC().insets("5").gridGapX("1").gridGapY("1"));
		mainPanel.setLayout(layout);
		
		Editor editor = UiGlobals.curEditor();
		
		//그리드 정보 가져옴
		LayerGrid grid = (LayerGrid) editor.getLayerManager().findLayerNamed("Grid");
        HashMap map = grid.getParameters();
        this.interval_space = (Integer)map.get("spacing");
        this.xOffset = (Integer)map.get("xOffset");
        this.yOffset = (Integer)map.get("yOffset");
        System.out.println("xOffset:"+xOffset+", yOffset: "+yOffset);
        //노드 정보 가져옴
    	this.nodes = editor.getLayerManager().getActiveLayer().getContents();
    	//노드 정보를 통해 그리드 정보 갱신
    	for(int count = 0 ; count < nodes.size() ; count++)
        {
        	Fig node = nodes.get(count);
        	int gridX = (node.getLocation().x+Math.abs(xOffset))/interval_space;
        	int gridY = (node.getLocation().y+Math.abs(yOffset))/interval_space;
        	
        	maxGridX = Math.max(gridX, maxGridX);
        	maxGridY = Math.max(gridY, maxGridY);
        }
    	//그리드별 작업 진행여부 저장 빈
    	System.out.println("maxGridX: "+maxGridX);
		System.out.println("maxGridY: "+maxGridY);
    	process = new int[maxGridY+1][maxGridX+1];
    	processPanel = new JXPanel[maxGridY+1][maxGridX+1];
    	resultMap = new LinkedHashMap[maxGridY+1][maxGridX+1];
    	
    	for(int i = 0 ; i <= maxGridY ; i++)
    		for(int j = 0 ; j <= maxGridX ; j++){
    			process[i][j] = VALUE_STANDBY;
    			processPanel[i][j] = new JXPanel();
    			processPanel[i][j].setBackground(COLOR_STANDBY);
    			resultMap[i][j] = new LinkedHashMap<String, Double>();
    		}
    	
    	invalidate();
    	mainPanel.revalidate();
	}
	
	/**
	 * Attribute가 속한 유전자 수
	 * @param columnIndex
	 * @param functionName
	 * @return
	 */
	public int getContainAttributeGeneSize(int columnIndex, String functionName){
		Set<String> keys = functionUniverse.keySet();
		int resultCnt = 0;
		
		for(String key : keys){
			HashMap<Integer, List<String>> functionById = functionUniverse.get(key);
			
			if(functionById != null){
				
				List<String> functionByColumn = functionById.get(columnIndex);
				if(functionByColumn!= null)
					if(functionByColumn.contains(functionName)) 
						resultCnt++;	
			}
		}
		//System.out.println("getContainAttributeGeneSize resultCnt: "+resultCnt);
		return resultCnt;
	}
	
	/**
	 * 쿼리 셋 내에 Attribute 를 포함한 유전자셋 수
	 * @param querySet
	 * @param columnIndex
	 * @param functionName
	 * @return
	 */
	public int getContainAttributeGeneSizeInQuery(List<String> querySet, int columnIndex, String functionName){
		int resultCnt = 0;
		
		for(String key : querySet){
			//System.out.println("keySize: "+functionUniverse.keySet().size());
			HashMap<Integer, List<String>> functionById = functionUniverse.get(key);
			//System.out.println("re: "+functionById+", key: "+key);
			if(functionById != null){
				List<String> functionByColumn = functionById.get(columnIndex);
				//System.out.println("B...");
				//for(Integer key11 : functionById.keySet())
				//	System.out.println("11: "+key);
				if(functionByColumn!= null)
					if(functionByColumn.contains(functionName)) 
						resultCnt++;	
			}
		}
		//System.out.println("getContainAttributeGeneSizeInQuery resultCnt: "+resultCnt);
		return resultCnt;
	}
	
	/**
	 * @param querySet
	 * @param columnIndex
	 * @param functionName
	 * @return
	 */
	public double getPValue(List<String> querySet, int q, int columnIndex, String functionName, int n){
		/**
		 * The number of genes in the Query Set
		 */
		//int q = querySet.size();
		/**
		 * The total number of genes having attribute A
		 */
		//int n = this.getContainAttributeGeneSize(columnIndex, functionName);
		/**
		 * The number of genes in Q having attribute A
		 */
		int m = this.getContainAttributeGeneSizeInQuery(querySet, columnIndex, functionName);
		/**
		 * The total number of genes in the gene universe.
		 */
		int N = functionUniverse.keySet().size();
		
		
		double overPresentPValue = 0.0;
		double devider = (binom(N, n));
		//System.out.println("q: "+q+", n: "+n+", m: "+m+", N: "+N);
		for(int a = m ; a <= Math.min(q, n) ; a++){
			//System.out.println("overPresentPValue: "+overPresentPValue+", (binom(1, q, a): "+binom(1, q, a)+", binom(2, N-q, n-a): "+binom(2, N-q, n-a)+", binom(3, N, n): "+binom(3, N, n));
			//overPresentPValue += (binom(1, q, a).multiply(binom(2, N-q, n-a))).divide(binom(3, N, n)).doubleValue();
			overPresentPValue += (binom(q, a)*binom(N-q, n-a))/devider;
		}
		//System.out.println("overPresentPValue: "+overPresentPValue);
		double underPresentPValue = 0.0;
		for(int a = 0 ; a <= m ; a++){
			underPresentPValue += (binom(q, a)*binom(N-a, n-a))/devider;
		}
		
		return Math.min(overPresentPValue, underPresentPValue);
	}
	
	public double getAdjPValue(List<String> querySet, int querySetSize, int columnIndex, String functionName, int n){
		
		//double pValue = getPValue(querySet, columnIndex, functionName);
		//int querySetSize = querySet.size();
		int iterSize = 50;
		
		/**
		 * The number of genes in the Query Set
		 */
		//int q = querySetSize;
		/**
		 * The total number of genes having attribute A
		 */
		//int n = this.getContainAttributeGeneSize(columnIndex, functionName);
		/**
		 * The number of genes in Q having attribute A
		 */
		//int m = this.getContainAttributeGeneSizeInQuery(querySet, columnIndex, functionName);
		/**
		 * The total number of genes in the gene universe.
		 */
		//int N = functionUniverse.keySet().size();
		
		//binomA = new BigInteger [ q + 1];
		//binomB = new BigInteger [ N - q + 1];
		//binomC = new BigInteger [ N + 1];
		
		
		//preCal(binomA, q);
		//System.out.println("A: "+binomA);
		//preCal(binomB, N-q);
		//System.out.println("B: "+binomB);
		//preCal(binomC, N);		
		//System.out.println("C: "+binomC);
		double oriPValue = getPValue(querySet, querySetSize, columnIndex, functionName, n);
		//System.out.println("oriPValue: "+oriPValue);
		/*for(int i = 0 ; i < iterSize ; i++){
			//System.out.println(i+" .. try.."+querySetSize+", "+functionUniverse.keySet().size());
			List<String> randSelection = makeRandGeneSelection(querySetSize, functionUniverse.keySet().size());
			//System.out.println(randSelection);
			double itorPValue = getPValue(randSelection, querySetSize, columnIndex, functionName, n);
			//System.out.println("itorPValue: "+itorPValue);
		}*/
		
		return oriPValue;
	}
	
	public void preLogCal(int size){
		logMap = new Double[size+1];
		for(int i = 0 ; i <= size ; i++){
			logMap[i] = Math.log(i);
		}
	}
	
	public double getLog(int val){
		//if(logMap.get(val) == null)
		//	return Math.log(val);
		return logMap[val];//.get(val);
	}
	
	public void preCal(BigInteger[] data, int n){
		//data = new double [ n + 1];
		data [0] = new BigInteger("1");
    	for(int i = 1 ; i <= n ; i++)
    	{
    		//System.out.println("i: "+i);
    		data[i] = new BigInteger("1");
    		for(int j = i - 1 ; j > 0 ; --j){
    			data[j] = data[j].add( data[j - 1] );
    		}
    	}
    	
	}
	
    public BigInteger binom (int type, int n, int m)
    {
    	
    	if(type == 1)
    		return binomA[m];
    	if(type == 2)
    		return binomB[m];
    	if(type == 3)
    		return binomC[m];
    	return new BigInteger("0");
    	
    	//double result = 0;
    	//return factorial(n)/(factorial(m)*factorial(n-m));
    }
    
    public double binom (int N, int n)
    {
    	
    	double result = 0.0;
    	for(int i = N ; i >= N - n + 1 && i >= 1; i--){
    		result += getLog(i);//Math.log(i);
    	}
    	for(int i = n-1 ; i >= 1 ; i--){
    		result -= getLog(i);//Math.log(i);
    	}
    	
    	return result;
    	
    	//double result = 0;
    	//return factorial(n)/(factorial(m)*factorial(n-m));
    }
    
    public static int factorial(int n){
    	if(n == 1) return 1;
    	else return n * factorial(n-1);
    }
	
    public List<String> makeRandGeneSelection(int size, int maxSize){
		if(size > maxSize) return null;
		int[] sequence = new int[maxSize];
		
		//랜덤 큐 채우기 
		Random oRandom = new Random();
		for(int i = 0 ; i < size ; i++){
			int selection = oRandom.nextInt(maxSize);//(int)Math.random()/(maxSize-i);
			int swapA = sequence[selection];
			if(swapA == 0) swapA = selection;
			int swapB = sequence[i];
			if(swapB == 0) swapB = i+1;
			sequence[i] = swapA;
			sequence[selection] = swapB;
			
		}
		
		for(int i = 0 ; i < size ; i++)
			if(sequence[i] == 0) sequence[i] = i;
		
		List<String> resultList = new Vector<String>();
		
		//System.out.println("size: "+geneIds.size());
		
		for(int i = 0 ; i < size ; i++){
			//System.out.println("seq: "+sequence[i]);
			resultList.add(geneIds.get(sequence[i]));
		}
		
		return resultList;
	}
    
    public static void main(String[] argv){
    	int aa = 0;
    	JFrame a = new JFrame(){
    		public int gridX = 300;
    		public int gridY = 500;
    		JXPanel mainPanel = new JXPanel(); 
    		
    		{
    			setSize(new Dimension(500, 500));
    			mainPanel.setBackground(Color.white);
    			this.setContentPane(mainPanel);
    			
    			MigLayout layout = new MigLayout(new LC().insets("5").gridGapX("1").gridGapY("1"));
    			mainPanel.setLayout(layout);
    		}
    		
    		public void paint(Graphics g){
    			super.paint(g);
    			System.out.println("aa");
    			mainPanel.removeAll();
    			
    			for(int i = 0 ; i < gridY ; i++){
    				for(int j = 0 ; j < gridX ; j++){
    					JXPanel panel = new JXPanel();
    	    			panel.setBackground(Color.red);
    	    			panel.setPreferredSize(new Dimension(this.getSize().width/gridX, this.getSize().height/gridY));
    	    			if(j+1 == gridX )
    	    				mainPanel.add(panel, "wrap, width 1::, height 1::");
    	    			else
    	    				mainPanel.add(panel, "width 1::, height 1::");
    				}
    			}
    			
    			mainPanel.getComponent(2*gridX+5).setBackground(Color.blue);
    		}
    		
    	};
    	
    	a.setDefaultCloseOperation(EXIT_ON_CLOSE);
		a.setVisible(true);
		a.invalidate();
    }
    
    /**
     * 그리드별 속한 진셋을 구함
     */
    public void getGeneSetByGrid(){
    	gridGeneSetData = new HashMap<Point, List<String>>();
    	 for(int count = 0 ; count < nodes.size() ; count++)
         {
         	Fig node = nodes.get(count);
         	String name = "";
         	Object desc = node.getOwner();
         	if(desc instanceof NodeDescriptor)
         	{
         		
         		NodeDescriptor nodeDesc = (NodeDescriptor)desc;
        		name = nodeDesc.getName();
         	}
         	
         	int gridX = (node.getLocation().x+xOffset)/interval_space;
         	int gridY = (node.getLocation().y+yOffset)/interval_space;
         	
         	//maxGridX = Math.max(gridX, maxGridX);
         	//maxGridY = Math.max(gridY, maxGridY);
         	
         	List<String> curGeneSet = gridGeneSetData.get(new Point(gridX, gridY));
         	if(curGeneSet == null){
         		curGeneSet = new Vector<String>();
         		gridGeneSetData.put(new Point(gridX, gridY), curGeneSet);
         	}
         	curGeneSet.add(name);
         	
         }
    	 
    	 /*int filledGrid = 0;
         for(int i = 0 ; i <= maxGridX ; i++){
         	for(int j = 0 ; j <= maxGridY ; j++){
         		List<String> curGeneSet = gridGeneSetData.get(new Point(i, j));
         		if(curGeneSet != null)
         		{
         			System.out.println(i+", "+j+": "+curGeneSet);
         			filledGrid++;
         		}
         	}
         }
         System.out.println("filled: "+filledGrid);*/
    }
    
    /**
     * 그리드별 펑션 리스트 구함
     * @param columnIndex : annotation 파일의 컬럼 번호를 의미함 , 각각 다른 속성을 가지고 있으므로..
     */
    public void genGeneFunctionByGrid(int columnIndex){
    	
        gridFuncData = new HashMap<Point, HashMap<Integer, List<String>>>();
        
        for(int count = 0 ; count < nodes.size() ; count++)
        {
        	Fig node = nodes.get(count);
        	String name = "";
        	Object desc = node.getOwner();
        	if(desc instanceof NodeDescriptor)
        	{
        		
        		NodeDescriptor nodeDesc = (NodeDescriptor)desc;
       			name = nodeDesc.getName();
        	}
        	
        	int gridX = (node.getLocation().x+xOffset)/interval_space;
        	int gridY = (node.getLocation().y+yOffset)/interval_space;
        	
        	maxGridX = Math.max(gridX, maxGridX);
        	maxGridY = Math.max(gridY, maxGridY);
        	
        	HashMap<Integer, List<String>> curGridFunc = gridFuncData.get(new Point(gridX, gridY));
        	if(curGridFunc == null){
        		curGridFunc = new HashMap<Integer, List<String>>();
        		gridFuncData.put(new Point(gridX, gridY), curGridFunc);
        	}
        	
        	List<String> funcList = curGridFunc.get(columnIndex);
        	if(funcList == null){
        		funcList = new Vector<String>();
        		curGridFunc.put(columnIndex, funcList);
        	}
        	
        	//System.out.println(functionUniverse.get(name).get(columnIndex));
        	for(String funcName : functionUniverse.get(name).get(columnIndex)){
        		if(!funcList.contains(funcName))
        			funcList.add(funcName);
        	}
        }
    	
    }
    
    public void showList(){
		scrollPane = null;
		
		if(resultPanel != null){
			resultPanel.removeAll();
			this.remove(resultPanel);
		}
		
		String[] column = null;
		Vector<String> columnData = new Vector<String>();
		columnData.add("X");
		columnData.add("Y");
		columnData.add("Gene Ontology Biological Process");
		
		Object[][] data = null;
		Integer[] dataHeight = null;
		
		try{
			if(column == null && (columnData == null || columnData.size() == 0)){
				//JOptionPane.showMessageDialog(UiGlobals.getNodeInfoFrame(),
				//	    "Eggs are not supposed to be green.", 
				//	    "Message",
				//	    JOptionPane.WARNING_MESSAGE);
			}else{
				int total = (maxGridX+1)*(maxGridY+1);
				this.setTitle("Total "+total+" Grids.");
				
				if(column == null){
					column = new String[columnData.size()];
					columnData.toArray(column);
				}
				
				
				data = new Object[total][columnData.size()];
				dataHeight = new Integer[total];
				
				HashMap<String, HashMap<Integer, String>> annotationContent = UiGlobals.getAnnotationContent();
				
				int filledCount = 0;
				
//				for(int i = 0 ; i <= maxGridY ; i++){
//		    		for(int j = 0 ; j <= maxGridX ; j++){
//		    			System.out.println("i: "+i+", j: "+j+".."+resultMap[i][j].size());
//		    		}
//				}
				for(int i = 0 ; i <= maxGridY ; i++){
		    		for(int j = 0 ; j <= maxGridX ; j++){
		    			data[filledCount][0] = i;
		    			data[filledCount][1] = j;
		    			dataHeight[filledCount] = 0;
		    			LinkedHashMap function = resultMap[i][j];
		    			if(function.size() == 0) continue;
		    			
		    			Set<String> keys = function.keySet();
		    			//System.out.println("i: "+i+", j: "+j+", "+function.size());
		    			
		    			int keyCnt = 0;
		    			for(String key : keys){
		    				dataHeight[filledCount]++;
		    				if(keyCnt == 0)
		    					data[filledCount][2] = key+": "+function.get(key);
		    				else
		    					data[filledCount][2] = data[filledCount][2].toString()+"\n"+key+": "+function.get(key);
		    				
		    				keyCnt++;
		    			}
						filledCount++;
		    		}
				}
				
//				for(Fig fig : selectedFig){
//					if( fig.getOwner() instanceof NodeDescriptor){
//						NodeDescriptor des = (NodeDescriptor)fig.getOwner();
//						HashMap<Integer, String> property = annotationContent.get(des.getName());
//						
//						Set<Integer> keys = property.keySet();
//						for(int key = 0 ; key < columnData.size() ; key++){
//							
//							data[count][key] = property.get(key);
//						}
//					}
//					count++;
//				}
				
				
				if(scrollPane != null)
					this.remove(scrollPane);
				//revalidate();
				
				System.out.println("tableCreated");
				
//				for(String col : column)
//					System.out.println("column: "+col);
//				for(Object d : data)
//					System.out.println("data: "+d.toString());
				
				Object[][] dataFilled = new Object[filledCount][3];
				
				for(int i = 0 ; i < filledCount ; i++){
					dataFilled[i][0] = data[i][0];
					dataFilled[i][1] = data[i][1];
					dataFilled[i][2] = data[i][2];
				}
				
				JNodeInfoTableModel model = new JNodeInfoTableModel(column, dataFilled);
				sorter = new TableRowSorter<JNodeInfoTableModel>(model);
				nodeTable = new JTable(model);
				//nodeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				//nodeTable.setDefaultRenderer(String.class, new CustomCellRenderer1());
				
				nodeTable.getColumnModel().getColumn(0).setMaxWidth(30);
				nodeTable.getColumnModel().getColumn(1).setMaxWidth(30);
				
				MultiLineCellRenderer cellRenderer = new MultiLineCellRenderer();
			    //cellRenderer.setLineWrap(true);
			    //cellRenderer.setWrapStyleWord(true);
			      
				nodeTable.getColumnModel().getColumn(2).setCellRenderer(cellRenderer);
				
				//System.out.println("cs: "+nodeTable.getColumnModel().getColumnCount());
				//nodeTable.setRowMargin(10);
				//nodeTable.setRowHeight(100);
//				nodeTable.setRowHeight(1, 100);
//				nodeTable.setRowHeight(2, 100);
//				nodeTable.setRowHeight(3, 100);
				//for(int i = 0 ; i < filledCount ; i++)
				//	if(dataHeight[i] > 0)
				//		nodeTable.setRowHeight(i, 30 * dataHeight[i]);
				nodeTable.setRowHeight(nodeTable.getRowHeight() * 3);
				nodeTable.setRowSorter(sorter);
				nodeTable.setEnabled(true);
				//nodeTable.setAutoCreateRowSorter(true);
				nodeTable.setPreferredScrollableViewportSize(new Dimension(5000, 5000));
				//nodeTable.setFillsViewportHeight(true);
				//nodeTable.getSelectionModel().addListSelectionListener(new RowListener());
				scrollPane = new JScrollPane(nodeTable);
				
				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			
		}
		
		
		MigLayout layout = new MigLayout(new LC().fillX().insets("0 0 0 0"),
				 new AC().align("left").gap("rel").grow(1f).fill(),
				 new AC().gap("0"));
		resultPanel = new JXPanel();
		resultPanel.setLayout(layout);
		
		if(scrollPane == null){ 
			MigLayout layout1 = new MigLayout(new LC().fillX().fillY().insets("0 0 0 0"),
					 new AC().align("center").gap("rel").grow(1f).fill(),
					 new AC().gap("0"));
			JPanel empty = new JPanel();
			empty.setLayout(layout1);
			empty.add(new JLabel("After annotation file is loaded, then this windows will be activated."));
			scrollPane = new JScrollPane(empty);
		}else{
			JLabel filterLabel = new JLabel("Filter column: ");
			resultPanel.add(filterLabel);
			filterColumn = new JComboBox();
			for(String columSr : column)
				filterColumn.addItem(columSr);
			resultPanel.add(filterColumn, "wrap");
			JLabel filterTextLabel = new JLabel("Filter value: ");
			resultPanel.add(filterTextLabel);
			filterText = new JTextField();
			
			filterText.getDocument().addDocumentListener(
	                new DocumentListener() {
	                    public void changedUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                    public void insertUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                    public void removeUpdate(DocumentEvent e) {
	                        newFilter();
	                    }
	                });
			resultPanel.add(filterText, "wrap");
		}
		
		CC cc = new CC();
		resultPanel.add(scrollPane, cc.wrap().spanX(2));
		
		//this.add(main);
		this.setContentPane(resultPanel);
		
	}
    
    private void newFilter() {
        RowFilter<JNodeInfoTableModel, Object> rf = null;
        //If current expression doesn't parse, don't update.
        try {
            rf = RowFilter.regexFilter(filterText.getText(), filterColumn.getSelectedIndex());
        } catch (java.util.regex.PatternSyntaxException e) {
        	e.printStackTrace();
            return;
        }
        sorter.setRowFilter(rf);
    }
    
    private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
        }
    }
    

}