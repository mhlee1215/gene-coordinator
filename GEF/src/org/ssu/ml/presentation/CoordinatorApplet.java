package org.ssu.ml.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import javax.swing.JApplet;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.ssu.ml.base.UiGlobals;
import org.ssu.ml.ui.CEdgeData;
import org.ssu.ml.ui.CNodeData;
import org.ssu.ml.ui.NodeLoadingProgressBar;
import org.ssu.ml.ui.NodePaletteFig;
import org.ssu.ml.ui.NodeRenderManager;
import org.ssu.ml.ui.ResizerPaletteFig;
import org.ssu.ml.ui.Utils;
import org.ssu.ml.ui.WestToolBar;
import org.tigris.gef.base.*;
import org.tigris.gef.event.ModeChangeEvent;
import org.tigris.gef.event.ModeChangeListener;
import org.tigris.gef.graph.presentation.JGraph;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.presentation.FigText;
import org.tigris.gef.ui.IStatusBar;
import org.tigris.gef.ui.PaletteFig;
import org.tigris.gef.ui.Progress;
import org.tigris.gef.ui.ToolBar;
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

	private WestToolBar _toolbar = null;
	private WestToolBar _westToolbar = null;
	/** The graph pane (shown in middle of window). */
	private JGraph _graph;
	/** A statusbar (shown at bottom ow window). */
	private JLabel _statusbar = new JLabel(" ");
	//private JXPanel jxpanel;
	private JPanel _mainPanel = new JPanel(new BorderLayout());
	private JPanel _graphPanel = new JPanel(new BorderLayout());
	private JMenuBar _menubar = new JMenuBar();

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
		
		System.out.println("constructur");

		setSize(756, 600);

//		try {
//			
//			//QuaquaManager.setProperty("Quaqua.tabLayoutPolicy", "wrap");
//			// configure the class loader of the UIManager.
//			//UIManager.put("ClassLoader", getClass().getClassLoader());
//			 UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			//UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
//			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
////			try {
////				  UIManager.setLookAndFeel(
////				    UIManager.getSystemLookAndFeelClassName());
////				} catch (Exception e) {
////				}	
//		
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			//e.printStackTrace();
//		}
		
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
		
		
		
	
		NodeRenderManager nodeRenderManager = new NodeRenderManager(_graph);
		nodeRenderManager.init(_width, _height);
		nodeRenderManager.drawNodes(true);
		
		UiGlobals.setNodeRenderManager(nodeRenderManager);
		//UiGlobals.setPre_scaled(pre_scaled);
		UiGlobals.setFileName(this.getParameter("fileName"));
		
	}
	
//	public void drawNodes(int prescale)
//	{
//		float minLocx = Utils.minValue(data.getLocxArry());
//		float minLocy = Utils.minValue(data.getLocyArry());
//		float maxLocx = Utils.maxValue(data.getLocxArry());
//		float maxLocy = Utils.maxValue(data.getLocyArry());
//
//		_width = (int) maxLocx - (int) minLocx + _PADDING;
//		_height = (int) maxLocy - (int) minLocy + _PADDING;
//		this.setSize(_width, _height);
//
//		double scale = Math.pow(2, pre_scaled - 1);
//
//		id = this.getParameter("id");
//		System.out.println("id : " + id);
//		System.out.println("pre_scaled : " + pre_scaled + ", real scale : "
//				+ scale);
//		System.out.println("codebase = " + this.getCodeBase().toString());
//
//		// int pre_scaled = 2;
//		editor.setScale(1.0 / scale);
//		
//		data.setPre_scale(scale);
//		_width *= scale;
//		_height *= scale;
//		_graph.setDrawingSize(_width, _height);
//
//		LayerGrid grid = (LayerGrid) editor.getLayerManager().findLayerNamed(
//				"Grid");
//		HashMap<String, Object> map = new HashMap<String, Object>();
//
//		double defaultSpace = (int) Math.pow(2, 5);
//		map.put("spacing", (int) (scale * defaultSpace));
//		map.put("thick", (int) scale);
//		grid.adjust(map);
//
//		//int maxNodeNum = data.getPointCount();
//		javax.swing.SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				// createAndShowGUI();
//				new NodeLoadingProgressBar(data, _graph);
//			}
//		});
//	}

	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	public void init() {
		init(new JGraph());
	}

	public void init(JGraph jg) {
		setDefaultFont(UiGlobals.getNormalFont());
		this.setToolBar(new NodePaletteFig()); // needs-more-work
		this.setWestToolBar(new ResizerPaletteFig());
		_graph = jg;

		editor = _graph.getEditor();
		// _graph.setBackground(Color.white);
		// _graph.setBounds(0, 0, _width, _height);

		LayerGrid grid = (LayerGrid) editor.getLayerManager().findLayerNamed(
				"Grid");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("Color", Color.LIGHT_GRAY);
		map.put("bgColor", Color.white);
		map.put("spacing_include_stamp", (int)UiGlobals.getDefault_grid_spacing()+50);
		map.put("paintLines", true);
		map.put("paintDots", false);

		grid.adjust(map);

		Container content = getContentPane();
		setUpMenus();
		content.setLayout(new BorderLayout());
		//content.add(_menubar, BorderLayout.NORTH);
		_graphPanel.add(_graph, BorderLayout.CENTER);
		_graphPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

		_mainPanel.add(_graphPanel, BorderLayout.CENTER);
		content.add(_mainPanel, BorderLayout.CENTER);
		content.add(_statusbar, BorderLayout.SOUTH);
		UiGlobals.set_statusBar(_statusbar);
		setSize(500, 500);
		setVisible(true);

		_graph.addModeChangeListener(this);

		try {
			jbInit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void setUpMenus() {
		JMenuItem openItem, saveItem, printItem, exitItem;
		JMenuItem deleteItem, copyItem, pasteItem;
		JMenuItem groupItem, ungroupItem;
		JMenuItem toBackItem, backwardItem, toFrontItem, forwardItem;

		JMenu file = new JMenu(Localizer.localize("GefBase", "File"));
		file.setMnemonic('F');
		_menubar.add(file);
		// file.add(new CmdNew());
		openItem = file.add(new CmdOpen());
		saveItem = file.add(new CmdSave());
		file.add(new CmdSavePGML());
		file.add(new CmdSaveSVG());
		CmdPrint cmdPrint = new CmdPrint();
		printItem = file.add(cmdPrint);
		file.add(new CmdPrintPageSetup(cmdPrint));
		file.add(new CmdOpenWindow("org.tigris.gef.base.PrefsEditor",
				"Preferences..."));
		// file.add(new CmdClose());
		exitItem = file.add(new CmdExit());

		JMenu edit = new JMenu(Localizer.localize("GefBase", "Edit"));
		edit.setMnemonic('E');
		_menubar.add(edit);

		JMenuItem undoItem = edit.add(new UndoAction(Localizer.localize(
				"GefBase", "Undo")));
		undoItem.setMnemonic(Localizer.localize("GefBase", "UndoMnemonic")
				.charAt(0));
		JMenuItem redoItem = edit.add(new RedoAction(Localizer.localize(
				"GefBase", "Redo")));
		redoItem.setMnemonic(Localizer.localize("GefBase", "RedoMnemonic")
				.charAt(0));

		JMenu select = new JMenu(Localizer.localize("GefBase", "Select"));
		edit.add(select);
		select.add(new CmdSelectAll());
		select.add(new CmdSelectNext(false));
		select.add(new CmdSelectNext(true));
		select.add(new CmdSelectInvert());

		edit.addSeparator();

		copyItem = edit.add(new CmdCopy());
		copyItem.setMnemonic('C');
		pasteItem = edit.add(new CmdPaste());
		pasteItem.setMnemonic('P');

		deleteItem = edit.add(new CmdRemoveFromGraph());
		edit.addSeparator();
		edit.add(new CmdUseReshape());
		edit.add(new CmdUseResize());
		edit.add(new CmdUseRotate());

		JMenu view = new JMenu(Localizer.localize("GefBase", "View"));
		_menubar.add(view);
		view.setMnemonic('V');
		view.add(new CmdSpawn());
		view.add(new CmdShowProperties());
		// view.addSeparator();
		// view.add(new CmdZoomIn());
		// view.add(new CmdZoomOut());
		// view.add(new CmdZoomNormal());
		view.addSeparator();
		view.add(new CmdAdjustGrid());
		view.add(new CmdAdjustGuide());
		view.add(new CmdAdjustPageBreaks());

		JMenu arrange = new JMenu(Localizer.localize("GefBase", "Arrange"));
		_menubar.add(arrange);
		arrange.setMnemonic('A');
		groupItem = arrange.add(new CmdGroup());
		groupItem.setMnemonic('G');
		ungroupItem = arrange.add(new CmdUngroup());
		ungroupItem.setMnemonic('U');

		JMenu align = new JMenu(Localizer.localize("GefBase", "Align"));
		arrange.add(align);
		align.add(new AlignAction(AlignAction.ALIGN_TOPS));
		align.add(new AlignAction(AlignAction.ALIGN_BOTTOMS));
		align.add(new AlignAction(AlignAction.ALIGN_LEFTS));
		align.add(new AlignAction(AlignAction.ALIGN_RIGHTS));
		align.add(new AlignAction(AlignAction.ALIGN_H_CENTERS));
		align.add(new AlignAction(AlignAction.ALIGN_V_CENTERS));
		align.add(new AlignAction(AlignAction.ALIGN_TO_GRID));

		JMenu distribute = new JMenu(Localizer
				.localize("GefBase", "Distribute"));
		arrange.add(distribute);
		distribute.add(new DistributeAction(DistributeAction.H_SPACING));
		distribute.add(new DistributeAction(DistributeAction.H_CENTERS));
		distribute.add(new DistributeAction(DistributeAction.V_SPACING));
		distribute.add(new DistributeAction(DistributeAction.V_CENTERS));

		JMenu reorder = new JMenu(Localizer.localize("GefBase", "Reorder"));
		arrange.add(reorder);
		toBackItem = reorder.add(new CmdReorder(CmdReorder.SEND_TO_BACK));
		toFrontItem = reorder.add(new CmdReorder(CmdReorder.BRING_TO_FRONT));
		backwardItem = reorder.add(new CmdReorder(CmdReorder.SEND_BACKWARD));
		forwardItem = reorder.add(new CmdReorder(CmdReorder.BRING_FORWARD));

		JMenu nudge = new JMenu(Localizer.localize("GefBase", "Nudge"));
		arrange.add(nudge);
		nudge.add(new NudgeAction(NudgeAction.LEFT));
		nudge.add(new NudgeAction(NudgeAction.RIGHT));
		nudge.add(new NudgeAction(NudgeAction.UP));
		nudge.add(new NudgeAction(NudgeAction.DOWN));

		KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlP = KeyStroke.getKeyStroke(KeyEvent.VK_P,
				KeyEvent.CTRL_MASK);
		KeyStroke altF4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4,
				KeyEvent.ALT_MASK);

		KeyStroke delKey = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		KeyStroke ctrlZ = KeyStroke.getKeyStroke(KeyEvent.VK_Z,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlY = KeyStroke.getKeyStroke(KeyEvent.VK_Y,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlC = KeyStroke.getKeyStroke(KeyEvent.VK_C,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlV = KeyStroke.getKeyStroke(KeyEvent.VK_V,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlG = KeyStroke.getKeyStroke(KeyEvent.VK_G,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlU = KeyStroke.getKeyStroke(KeyEvent.VK_U,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B,
				KeyEvent.CTRL_MASK);
		KeyStroke ctrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F,
				KeyEvent.CTRL_MASK);
		KeyStroke sCtrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B,
				KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);
		KeyStroke sCtrlF = KeyStroke.getKeyStroke(KeyEvent.VK_F,
				KeyEvent.CTRL_MASK | KeyEvent.SHIFT_MASK);

		openItem.setAccelerator(ctrlO);
		saveItem.setAccelerator(ctrlS);
		printItem.setAccelerator(ctrlP);
		exitItem.setAccelerator(altF4);

		deleteItem.setAccelerator(delKey);
		undoItem.setAccelerator(ctrlZ);
		redoItem.setAccelerator(ctrlY);
		copyItem.setAccelerator(ctrlC);
		pasteItem.setAccelerator(ctrlV);

		groupItem.setAccelerator(ctrlG);
		ungroupItem.setAccelerator(ctrlU);

		toBackItem.setAccelerator(sCtrlB);
		toFrontItem.setAccelerator(sCtrlF);
		backwardItem.setAccelerator(ctrlB);
		forwardItem.setAccelerator(ctrlF);

	}

	@Override
	public void modeChange(ModeChangeEvent mce) {
		// TODO Auto-generated method stub

	}

	public void setToolBar(WestToolBar tb) {
		_toolbar = tb;
		_mainPanel.add(_toolbar, BorderLayout.NORTH);
	}

	public void setWestToolBar(WestToolBar tb) {
		_westToolbar = tb;
		_mainPanel.add(_westToolbar, BorderLayout.WEST);
	}

	public void start() {
		// TODO Auto-generated method stub
		super.start();
	}

	public void stop() {
		// TODO Auto-generated method stub
		super.stop();
	}
	
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
