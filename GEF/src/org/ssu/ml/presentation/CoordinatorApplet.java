package org.ssu.ml.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTitledPanel;
import org.jdesktop.swingx.MultiSplitLayout;
import org.ssu.ml.base.UiGlobals;
import org.ssu.ml.ui.NodePaletteFig;
import org.ssu.ml.ui.NodeRenderManager;
import org.ssu.ml.ui.ResizerPaletteFig;
import org.ssu.ml.ui.Utils;
import org.ssu.ml.ui.WestToolBar;
import org.swingX.util.Stacker;
import org.tigris.gef.base.AlignAction;
import org.tigris.gef.base.CmdAdjustGrid;
import org.tigris.gef.base.CmdAdjustGuide;
import org.tigris.gef.base.CmdAdjustPageBreaks;
import org.tigris.gef.base.CmdCopy;
import org.tigris.gef.base.CmdExit;
import org.tigris.gef.base.CmdGroup;
import org.tigris.gef.base.CmdOpen;
import org.tigris.gef.base.CmdOpenWindow;
import org.tigris.gef.base.CmdPaste;
import org.tigris.gef.base.CmdPrint;
import org.tigris.gef.base.CmdPrintPageSetup;
import org.tigris.gef.base.CmdRemoveFromGraph;
import org.tigris.gef.base.CmdReorder;
import org.tigris.gef.base.CmdSave;
import org.tigris.gef.base.CmdSavePGML;
import org.tigris.gef.base.CmdSaveSVG;
import org.tigris.gef.base.CmdSelectAll;
import org.tigris.gef.base.CmdSelectInvert;
import org.tigris.gef.base.CmdSelectNext;
import org.tigris.gef.base.CmdShowProperties;
import org.tigris.gef.base.CmdSpawn;
import org.tigris.gef.base.CmdUngroup;
import org.tigris.gef.base.CmdUseReshape;
import org.tigris.gef.base.CmdUseResize;
import org.tigris.gef.base.CmdUseRotate;
import org.tigris.gef.base.DistributeAction;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.base.NudgeAction;
import org.tigris.gef.event.ModeChangeEvent;
import org.tigris.gef.event.ModeChangeListener;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.undo.RedoAction;
import org.tigris.gef.undo.UndoAction;
import org.tigris.gef.util.Localizer;
import org.tigris.gef.util.ResourceLoader;


public class CoordinatorApplet extends JApplet implements ModeChangeListener {

	public static final int _PADDING = 100;

	int _width = 2000;
	int _height = 2000;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String id = null;
	String coordFileName = "";
	String edgeFileName = "";
	int pre_scaled = 1;

	
	
	
	Editor editor = null;

	//private WestToolBar _toolbar = null;
	//private WestToolBar _westToolbar = null;
	/** The graph pane (shown in middle of window). */
	private JGraph _graph;
	/** A statusbar (shown at bottom ow window). */
	private JLabel _statusbar = new JLabel(" ");
	//private JXPanel jxpanel;
	private JXPanel _mainPanel = new JXPanel(new BorderLayout());
	private JXPanel _graphPanel = new JXPanel(new BorderLayout());
	private JMenuBar _menubar = new JMenuBar();
	private JXTitledPanel _nodeInfoPanel = new JXTitledPanel();
	
	JXMultiSplitPane msp = new JXMultiSplitPane();
	
	Stacker dataPanel;
	JLabel credits;

	public CoordinatorApplet() throws Exception {
		Localizer.addResource("GefBase",
				"org.tigris.gef.base.BaseResourceBundle");
		Localizer.addResource("GefPres",
				"org.tigris.gef.presentation.PresentationResourceBundle");
		Localizer.addLocale(Locale.getDefault());
		Localizer.switchCurrentLocale(Locale.getDefault());
		ResourceLoader.addResourceExtension("gif");
		ResourceLoader.addResourceExtension("png");
		ResourceLoader.addResourceExtension("jpg");
		ResourceLoader.addResourceLocation("/org/tigris/gef/Images");
		
		UiGlobals.init();
		
		try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException e) {
        	e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
			  UIManager.setLookAndFeel(
			    UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e1) {
			}
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

		//UiGlobals.set_curApplet(this);
		UiGlobals.setApplet(this);

	}

	private void jbInit() throws Exception {

		try{
			System.out.println("this.getCodeBase() : "+this.getCodeBase());
		}catch(Exception e){
			System.out.println("Executed in local!");
		}
		// paths.setCodebase(this.getCodeBase().toString());
		// this.

		if (getParameter("prescaled") == null)
			pre_scaled = 1;
		else
			pre_scaled = Integer.parseInt(getParameter("prescaled"));
		
		
		
		
		
		//UiGlobals.setPre_scaled(pre_scaled);
		UiGlobals.setFileName(this.getParameter("fileName"));
		UiGlobals.setAnnotationFileName(this.getParameter("annotationFileName"));
		
		String isExample = this.getParameter("isExample");
		if(isExample == null) isExample = "N";
		UiGlobals.setIsExample(isExample);
		UiGlobals.setExampleType(this.getParameter("type"));
		System.out.println("isExample : "+UiGlobals.getIsExample());
		System.out.println("exampleType : "+UiGlobals.getExampleType());
		
		//Start to read annotation file
		//initAnnotation(UiGlobals.getAnnotationFileName());
		
		//Start to node rendering
		NodeRenderManager nodeRenderManager = new NodeRenderManager(_graph);
		nodeRenderManager.init(_width, _height);
		nodeRenderManager.drawNodes(true);
		UiGlobals.setNodeRenderManager(nodeRenderManager);
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	public void init() {
		init(new JGraph());
      
	}

	public void init(JGraph jg) {
		//Set default Font
		setDefaultFont(UiGlobals.getNormalFont());
		
		//Set toolbar
		//this.setToolBar(new NodePaletteFig()); // needs-more-work
		//Set western tool bar
		//this.setWestToolBar(new ResizerPaletteFig());
		
		
		_graph = jg;
		editor = _graph.getEditor();

		//Editor
		////Grid Setting
		LayerGrid grid = (LayerGrid) editor.getLayerManager().findLayerNamed("Grid");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Color", Color.LIGHT_GRAY);
		map.put("bgColor", Color.white);
		map.put("spacing_include_stamp", (int)UiGlobals.getDefault_grid_spacing()+50);
		map.put("paintLines", true);
		map.put("paintDots", false);
		grid.adjust(map);

		
		
		//content.add(_menubar, BorderLayout.NORTH);
		//_graphPanel.add(_graph, BorderLayout.CENTER);
		//_graphPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		
		//Loading Mask
		Container mask = this.getContentPane();
		JXPanel maskPanel = new JXPanel();
		JXBusyLabel label = new JXBusyLabel(new Dimension(100, 100));
        label.setName("busyLabel");
        label.getBusyPainter().setHighlightColor(new Color(44, 61, 146).darker().darker());
        label.getBusyPainter().setBaseColor(new Color(168, 204, 241).brighter());
        label.setBusy(true);
        label.getBusyPainter().setPoints(50);
        label.getBusyPainter().setTrailLength(25);
        //maskPanel.setAlpha(0.5f);
        //maskPanel.setPreferredSize(this.getToolkit().getScreenSize());
        maskPanel.setSize(this.getToolkit().getScreenSize());
        //maskPanel.setBackground(Color.white);
        //maskPanel.add(label, BorderLayout.CENTER);
        //mask.add(maskPanel, BorderLayout.CENTER);
        
        
        
        
        
        //content.setVisible(false);
        Container content = this.getLayeredPane();
		content.setLayout(new BorderLayout());
		JXPanel contentPanel = new JXPanel();
		contentPanel.setLayout(new BorderLayout());
		
		contentPanel.add(new NodePaletteFig(), BorderLayout.NORTH);
		
		
		
		
		//_mainPanel.add(new ResizerPaletteFig() , BorderLayout.WEST);
		//_mainPanel.add(_graph, BorderLayout.CENTER);
		//_mainPanel.add(_statusbar, BorderLayout.SOUTH);
		
		UiGlobals.setMsp(msp);
		contentPanel.add(msp, BorderLayout.CENTER);
		
		
		String layoutDef = "" + "(COLUMN " + "	(ROW weight=0.8 "
				+ "		(LEAF name=left.top) "
				+ "			(LEAF name=editor weight=0.8)"
				+ "			(LEAF name=left.middle weight=0.05)" + "		) "
				+ "	 (LEAF name=bottom weight=0.2))";

		MultiSplitLayout.Node modelRoot = MultiSplitLayout
				.parseModel(layoutDef);
		msp.getMultiSplitLayout().setModel(modelRoot);
		msp.setDividerSize(2);
		msp.add(new ResizerPaletteFig(), "left.top");
		
		UiGlobals.setNodeInfoPanel(_nodeInfoPanel);
		_nodeInfoPanel.setTitle("Node Info");
		msp.add( _nodeInfoPanel, "left.middle" );
		//msp.getMultiSplitLayout().displayNode("left.middle", false);
		
		msp.add(_graph, "editor");
		msp.add(_statusbar, "bottom");
		// ADDING A BORDER TO THE MULTISPLITPANE CAUSES ALL SORTS OF ISSUES
		msp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
  
		
		content.add(contentPanel, BorderLayout.CENTER);
		UiGlobals.set_statusBar(_statusbar);
		setSize(870, 600);
		setVisible(true);

		_graph.addModeChangeListener(this);

		
		credits = new JLabel(); 
        credits.setName("credits");
        credits.setText("Welcome to COEX");
        credits.setFont(UIManager.getFont("Table.font").deriveFont(24f));
        credits.setHorizontalAlignment(JLabel.CENTER);
        credits.setBorder(new CompoundBorder(new TitledBorder(""),
                new EmptyBorder(20,20,20,20)));

        
        dataPanel = new Stacker(contentPanel);
        
        
        content.add(dataPanel);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	dataPanel.showMessageLayer(credits, 1f);
            	
            	
            	//dataPanel.hideMessageLayer();
            }
        });
        
		try {
			jbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void modeChange(ModeChangeEvent mce) {
		// TODO Auto-generated method stub

	}

	public void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}
	
	/**
	 * Set default font
	 * @param font
	 */
	public void setDefaultFont(Font font)
	{
		
			String[] applyList = {
				"RadioButtonMenuItem.font",
				"CheckBoxMenuItem.font",
				"RadioButton.font",
				"ToolBar.font",
				"ProgressBar.font",
				"Menu.font",
				"Button.font",
				"TitledBorder.font",
				"ComboBox.font",
				"ToggleButton.font",
				"TabbedPane.font",
				"List.font",
				"MenuBar.font",
				"MenuItem.font",
				"CheckBox.font",
				"Label.font",
			};
			
			int nSize = applyList.length;
			for( int i=0; i < nSize; i++ ){
				UIManager.put(applyList[i], font);
			}
			
	}

	
	
	

	
	
	

}
