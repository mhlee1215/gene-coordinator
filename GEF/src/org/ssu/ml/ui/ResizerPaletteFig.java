// Copyright (c) 1996-99 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

// File: PaletteFig.java
// Classes: PaletteFig
// Original Author: ics125 spring 1996
// $Id: PaletteFig.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.ssu.ml.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

import org.jdesktop.swingx.JXTaskPane;
import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.base.CmdSetMode; 
import org.tigris.gef.base.CmdZoom;
import org.tigris.gef.base.Editor;
import org.tigris.gef.base.Layer;
import org.tigris.gef.base.LayerGrid;
import org.tigris.gef.base.ModeBroom;
import org.tigris.gef.base.ModeCreateFigCircle;
import org.tigris.gef.base.ModeCreateFigInk;
import org.tigris.gef.base.ModeCreateFigLine;
import org.tigris.gef.base.ModeCreateFigPoly;
import org.tigris.gef.base.ModeCreateFigRRect;
import org.tigris.gef.base.ModeCreateFigRect;
import org.tigris.gef.base.ModeCreateFigSpline;
import org.tigris.gef.base.ModeCreateFigText;
import org.tigris.gef.base.ModeSelect;
import org.tigris.gef.util.ResourceLoader;


public class ResizerPaletteFig extends WestToolBar implements ChangeListener, ActionListener{

	/**
     * 
     */
	private static final long serialVersionUID = 304194274216578087L;
	
	private int gridCurValue = 0;
	private int scaleCurValue = 0;
	
	JComboBox scaleCombo = null;
	JSpinner gridSpinner = null;
	JSlider gridResizer = null;
	JSlider scaleResizer = null;
	
	
	JMenu scaleMenu;
	String scalePrefix = "x";
	int scaleMin = 1;
	int initScale = 4;
	int scaleMax = 9;

	public ResizerPaletteFig() {
		defineButtons();
	}

	/**
	 * Defined the buttons in this palette. Each of these buttons is associated
	 * with an CmdSetMode, and that Cmd sets the next global Mode to somethign
	 * appropriate. All the buttons can stick except 'select'. If the user
	 * unclicks the sticky checkbox, the 'select' button is automatically
	 * pressed.
	 */
	public void defineButtons() {
		//this.setLayout(new GridLayout(4, 1));
		//this.setLayout(new FlowLayout());
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// add(new CmdSetMode(ModeCreateFigLine.class, "Line"));
		// add(new CmdSetMode(ModeCreateFigText.class, "Text")	);

		// add(image1, "Image1", "Image1");
		//add(new CmdZoom(2), "Zoom in", "zoom_in");
		//add(new CmdZoom(0.5), "Zoom out", "zoom_out");
		scaleMenu = new JMenu(scalePrefix+initScale);
		scaleMenu.setToolTipText("<html><h3>Manupulation of scale</h3><br>The Scale means that total resolution of white plane. <br>If scale value become higher, then total size of white <br>plane become larger. However, when you chanege scale, <br>the plane you will see is same as before. <br>This is because the white plane is fit to your monitor.</html>");
		ButtonGroup group = new ButtonGroup();
		for(int count = scaleMin ; count < scaleMax ; count++){
			JRadioButtonMenuItem item = new JRadioButtonMenuItem(scalePrefix+count);
			item.setName(scalePrefix+count);
			item.addActionListener(this);
			group.add(item);
			scaleMenu.add(item);
			if(count == initScale)
				item.setSelected(true);
		}
		JMenuBar scaleMenuBar = new JMenuBar();
		//scaleMenuBar.setComponentOrientation()
		scaleMenuBar.add(scaleMenu);
		//scaleMenuBar.setPreferredSize(new Dimension(50, 50));
		
		String[] strScaleItems = new String[scaleMax - scaleMin];
		for(int count = scaleMin ; count < scaleMax ; count++){
		    strScaleItems[count-scaleMin] = scalePrefix+count;
		}
		scaleCombo = new JComboBox(strScaleItems);
		scaleCombo.setSelectedIndex(initScale-1);
		scaleCombo.addActionListener(this);
		
		
		int gridMax = 200;
		int gridMin = 1;
		gridCurValue = UiGlobals.getDefault_grid_spacing();
		gridResizer = new JSlider(JSlider.VERTICAL,
				gridMin, gridMax, gridCurValue);
		gridResizer.setName("gridResizer");
		//gridResizer.setBackground(Color.white);
		//Font font = new Font("Dialog.plain", 0, 10);
		
		JLabel minLabel = new JLabel("▼");
		//minLabel.setFont(font);
		JLabel maxLabel = new JLabel("▲");
		//maxLabel.setFont(font);
		Hashtable<Integer, JLabel> labelTable = 
            new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( gridMin ),
				minLabel );
		labelTable.put(new Integer( gridMax ),
				maxLabel );
		gridResizer.setLabelTable(labelTable);
		
		//gridResizer.setPreferredSize(new Dimension(50, 500));
        gridResizer.setPaintLabels(true);
        gridResizer.setMajorTickSpacing(1);
        gridResizer.addChangeListener(this);
        //gridResizer.setPaintTicks(true);
        //BorderFactory a;
        
        
//        gridResizer.setBorder(
//                //BorderFactory.createEmptyBorder(0,0,0,0)
//                //BorderFactory.createLineBorder(Color.black, 1)
//        		
//                BorderFactory.createTitledBorder("Grid")
//                );
        
		
		UiGlobals.set_gridSlider(gridResizer);
		
		
		SpinnerModel model =
	        new SpinnerNumberModel(gridCurValue, 	//initial value
	        						gridMin , 		//min
	                                gridMax , 		//max
	                                1);       		//step

		gridSpinner = new JSpinner();
		gridSpinner.setName("gridSpinner");
		
		gridSpinner.setModel(model);
		
		gridSpinner.addChangeListener(this);
		
		
		
		JPanel locControlPanel = new JPanel();
		//locControlPanel.setPreferredSize(new Dimension(50, 80));
		locControlPanel.setLayout(null);
		
		int btnWidth = 25;
		int btnHeight = 25;
		
		JButton buttonUp = new JButton("");
		Icon upIcon = ResourceLoader.lookupIconResource("directionUp", "direction_up");
		buttonUp.setIcon(upIcon);
		buttonUp.setActionCommand("gridUp");
		buttonUp.addActionListener(this);
		buttonUp.setPreferredSize(new Dimension(btnWidth, btnHeight));
		buttonUp.setMargin(new Insets(0, 0, 0, 0));
		buttonUp.setBounds(btnWidth, 0, btnWidth, btnHeight);
		buttonUp.setBackground(Color.white);
		locControlPanel.add(buttonUp);
		
		JButton buttonDown = new JButton("");
		Icon downIcon = ResourceLoader.lookupIconResource("directionDown", "direction_up");
		buttonDown.setIcon(downIcon);
		buttonDown.setActionCommand("gridDown");
		buttonDown.addActionListener(this);
		buttonDown.setPreferredSize(new Dimension(btnWidth, btnHeight));
		buttonDown.setMargin(new Insets(0, 0, 0, 0));
		buttonDown.setBounds(btnWidth, btnWidth, btnWidth, btnHeight);
		locControlPanel.add(buttonDown);
		
		JButton buttonLeft = new JButton("");
		Icon leftIcon = ResourceLoader.lookupIconResource("directionLeft", "direction_up");
		buttonLeft.setIcon(leftIcon);
		buttonLeft.setActionCommand("gridLeft");
		buttonLeft.addActionListener(this);
		buttonLeft.setPreferredSize(new Dimension(btnWidth, btnHeight));
		buttonLeft.setMargin(new Insets(0, 0, 0, 0));
		buttonLeft.setBounds(0, btnWidth/2, btnWidth, btnHeight);
		locControlPanel.add(buttonLeft);
		
		JButton buttonRight = new JButton("");
		Icon rightIcon = ResourceLoader.lookupIconResource("directionRight", "direction_up");
		buttonRight.setIcon(rightIcon);
		buttonRight.setActionCommand("gridRight");
		buttonRight.addActionListener(this);
		buttonRight.setPreferredSize(new Dimension(btnWidth, btnHeight));
		buttonRight.setMargin(new Insets(0, 0, 0, 0));
		buttonRight.setBounds(btnWidth*2, btnWidth/2, btnWidth, btnHeight);
		locControlPanel.add(buttonRight);
		//locControlPanel.setBackground(Color.white);
		
		
		
		
		
		
		int scaleMin = UiGlobals.getPre_scaled();
		int scaleMax = 12;
		scaleCurValue = scaleMin;
		scaleResizer = new JSlider(JSlider.VERTICAL,
				scaleMin, scaleMax, scaleCurValue);
		scaleResizer.setName("scaleResizer");
		scaleResizer.setBackground(Color.white);
		Hashtable<Integer, JLabel> scaleLableTable = 
            new Hashtable<Integer, JLabel>();
		scaleLableTable.put(new Integer( scaleMin ),
				minLabel );
		scaleLableTable.put(new Integer( scaleMax ),
				maxLabel );
		scaleResizer.setLabelTable(scaleLableTable);
        scaleResizer.setPaintLabels(true);
        scaleResizer.addChangeListener(this);
        //scaleResizer.setPreferredSize(new Dimension(50, 500));
        scaleResizer.setBorder(
                //BorderFactory.createEmptyBorder(0,0,0,0)
                //BorderFactory.createLineBorder(Color.black, 1)
                BorderFactory.createTitledBorder("Scale")
                );
		//add(scaleResizer);
		scaleResizer.setEnabled(false);
		
		
		//Search Option Content Start
		
		JPanel searchOptionPanel = new JPanel();
		searchOptionPanel.setLayout(new MigLayout("insets 0 0 0 0"));
		JComboBox viewType = new JComboBox();
		viewType.addItem("With marked");
		viewType.addItem("Only searched");
		searchOptionPanel.add(viewType, "wrap");
		
		JComboBox markType = new JComboBox();
		markType.addItem("Auto colored");
		markType.addItem("Custom colored");
		searchOptionPanel.add(markType, "wrap");
		
		JTextField markColorInput = new JTextField();
		searchOptionPanel.add(markColorInput, "wrap");
		
		
		//Search Option Content End
		
		
		
		setLayout(new GridLayout(1, 1));
		JPanel mainPanel = new JPanel();
		mainPanel.setBackground(Color.white);
		add(mainPanel);
		mainPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		int leftToolbarWidth = 30;
		int gridyIndex = 0;
		
		
		JXTaskPane scaleTask = new JXTaskPane();
		scaleTask.setLayout(new GridBagLayout());
		GridBagConstraints taskConstraints = new GridBagConstraints();
		taskConstraints.fill = GridBagConstraints.HORIZONTAL;
		taskConstraints.anchor = GridBagConstraints.PAGE_START;
		taskConstraints.weightx = 1;
		taskConstraints.insets = new Insets(-6,-8,-6,-8);  //top padding
		taskConstraints.gridx = 0;
		taskConstraints.gridy = 0;
        
		Icon scaleTaskIcon = ResourceLoader.lookupIconResource("scaleTask1", "scaleTask1");
		scaleTask.setTitle("Scale");
		scaleTask.setFocusable(false);
		scaleTask.setCollapsed(true);
		scaleTask.setIcon(scaleTaskIcon);
		
		scaleTask.add(scaleCombo, taskConstraints);
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = gridyIndex++;
		mainPanel.add(scaleTask, c);
		
		
//		c.fill = GridBagConstraints.HORIZONTAL;
//        c.anchor = GridBagConstraints.PAGE_START;
//        c.weightx = 0.5;
//        c.gridx = 0;
//        c.gridy = gridyIndex++;
//        //c.ipady = 20;
//        scaleMenuBar.setPreferredSize(new Dimension(leftToolbarWidth, 30));
//        mainPanel.add(scaleCombo, c);
        
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.anchor = GridBagConstraints.PAGE_START;
//		c.weightx = 0.5;
//		c.gridx = 0;
//		c.gridy = 0;
//		c.ipady = 20;
//		scaleMenuBar.setPreferredSize(new Dimension(leftToolbarWidth, 30));
		//add(scaleMenuBar, c);

		//button = new JButton("Button 2");
        Insets old = c.insets; 
        
        gridResizer.setPreferredSize(new Dimension(leftToolbarWidth, 100));
		JPanel resizerPanel = new JPanel();
		resizerPanel.setLayout(new GridBagLayout());
		//GridBagConstraints cResizer = new GridBagConstraints();
		//cResizer.insets = new Insets(1000, -5, 0, -50);
		resizerPanel.setBorder(BorderFactory.createTitledBorder(""));
		//resizerPanel.setBackground(Color.white);
		resizerPanel.add(gridResizer);
		
        JXTaskPane gridTask = new JXTaskPane();
        Icon gridTaskIcon = ResourceLoader.lookupIconResource("gridTask", "gridTask");
        //gridTask.setLayout(new GridBagLayout());
		GridBagConstraints gridTaskConstraints = new GridBagConstraints();
		gridTaskConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridTaskConstraints.anchor = GridBagConstraints.PAGE_START;
		gridTaskConstraints.weightx = 1;
		gridTaskConstraints.insets = new Insets(-6,-8,-6,-8);  //top padding
		gridTaskConstraints.gridx = 0;
		gridTaskConstraints.gridy = 0;
        gridTask.setTitle("Grid size");
        gridTask.setFocusable(false);
        gridTask.setCollapsed(true);
        gridTask.setIcon(gridTaskIcon);
        
        gridTaskConstraints.insets = new Insets(-6,-8,-6,-8);  //top padding
		gridTaskConstraints.gridx = 0;
		gridTaskConstraints.gridy = 0;
        gridTask.add(gridSpinner);
		
        gridTaskConstraints.insets = new Insets(8,-8,-6,-8);  //top padding
		gridTaskConstraints.gridx = 0;
		gridTaskConstraints.gridy = 1;
		gridTask.add(resizerPanel);
		
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        c.insets = new Insets(5,0,0,0);  //top padding
        c.gridy = gridyIndex++;
		mainPanel.add(gridTask, c);
		c.insets = old;
		

		//button = new JButton("Button 3");
//		c.fill = GridBagConstraints.HORIZONTAL;
//		//c.weightx = 0.5;
//		c.ipady = 0;
//		c.gridx = 0;
//		c.gridy =  gridyIndex++;
//		gridSpinner.setPreferredSize(new Dimension(leftToolbarWidth, 30));
//		mainPanel.add(gridSpinner, c);

		JXTaskPane locCtrlTask = new JXTaskPane();
		locCtrlTask.setLayout(new GridBagLayout());
        Icon locCtrlTaskIcon = ResourceLoader.lookupIconResource("direction_up", "direction_up");
        locCtrlTask.setTitle("Grid move");
        locCtrlTask.setCollapsed(true);
        locCtrlTask.setFocusable(false);
        locControlPanel.setBorder(BorderFactory.createTitledBorder(""));
        locCtrlTask.setIcon(locCtrlTaskIcon);
        
        GridBagConstraints locTaskConstraints = new GridBagConstraints();
        //locTaskConstraints.fill = GridBagConstraints.HORIZONTAL;
        locTaskConstraints.anchor = GridBagConstraints.PAGE_START;
        locTaskConstraints.weightx = 1;
        locTaskConstraints.insets = new Insets(-4,-8,-6,-8);  //top padding
        locTaskConstraints.gridx = 0;
        locTaskConstraints.gridy = 0;
        locCtrlTask.add(locControlPanel, locTaskConstraints);
        locControlPanel.setPreferredSize(new Dimension(75, 55));
		//JLabel locControlLabel = new JLabel(" Grid Ctrl");
        //gridLabel.set
		c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        old = c.insets; 
        c.insets = new Insets(5,0,0,0);  //top padding
        c.gridy = gridyIndex++;
		mainPanel.add(locCtrlTask, c);
		c.insets = old;
		//button = new JButton("Long-Named Button 4");
		
		
		JXTaskPane searchOptionTask = new JXTaskPane();
		searchOptionTask.setLayout(new GridBagLayout());
        Icon searchOptionTaskIcon = ResourceLoader.lookupIconResource("direction_up", "direction_up");
        searchOptionTask.setTitle("Search Option");
        searchOptionTask.setCollapsed(true);
        searchOptionTask.setFocusable(false);
        searchOptionTask.setIcon(searchOptionTaskIcon);
        
        GridBagConstraints searchOptionConstraints = new GridBagConstraints();
        //locTaskConstraints.fill = GridBagConstraints.HORIZONTAL;
        locTaskConstraints.anchor = GridBagConstraints.PAGE_START;
        locTaskConstraints.weightx = 1;
        locTaskConstraints.insets = new Insets(-4,-8,-6,-8);  //top padding
        locTaskConstraints.gridx = 0;
        locTaskConstraints.gridy = 0;
        searchOptionTask.add(searchOptionPanel, searchOptionConstraints);
        locControlPanel.setPreferredSize(new Dimension(75, 55));
		//JLabel locControlLabel = new JLabel(" Grid Ctrl");
        //gridLabel.set
		c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_START;
        c.weightx = 0.5;
        c.gridx = 0;
        old = c.insets; 
        c.insets = new Insets(5,0,0,0);  //top padding
        c.gridy = gridyIndex++;
		mainPanel.add(searchOptionTask, c);
		c.insets = old;
		
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.ipady = 60;      //make this component tall
//		c.weightx = 0.0;
//		//c.gridwidth = 3;
//		c.gridx = 0;
//		c.gridy = gridyIndex++;
//		mainPanel.add(locControlPanel, c);
		
		c.ipady = 0;       //reset to default
		c.weighty = 1.0;   //request any extra vertical space
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
		c.insets = new Insets(0,0,0,0);  //top padding
		c.gridx = 0;       //aligned with button 2
		//c.gridwidth = 2;   //2 columns wide
		c.gridy = gridyIndex++;       //third row
		mainPanel.add(new JPanel(), c);

//		button = new JButton("5");
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.ipady = 0;       //reset to default
//		c.weighty = 1.0;   //request any extra vertical space
//		c.anchor = GridBagConstraints.PAGE_END; //bottom of space
//		c.insets = new Insets(10,0,0,0);  //top padding
//		c.gridx = 0;       //aligned with button 2
//		//c.gridwidth = 2;   //2 columns wide
//		c.gridy = 4;       //third row
//		add(button, c);
		
        
		UiGlobals.set_scaleSlider(scaleResizer);
	}

	@Override
	public void stateChanged(ChangeEvent e) {

		Object source = e.getSource();
		if(source instanceof JSlider)
		{
			JSlider slider = (JSlider)source;
			String sliderName = slider.getName();
			System.out.println(sliderName);
			if(sliderName != null){
				if (sliderName.equals("gridResizer")) {

					if (gridCurValue != slider.getValue()) {
						gridCurValue = slider.getValue();
						int space = slider.getValue();
						gridSpinner.setValue(space);
						gridResize(space);
						
					}
				}
				else if(sliderName.equals("scaleResizer"))
				{
					if(scaleCurValue != slider.getValue()){
						System.out.println("scale Value : "+slider.getValue());
						scaleCurValue = slider.getValue();
						
						UiGlobals.get_scaleSlider().setEnabled(false);
						NodeRenderManager manager = UiGlobals.getNodeRenderManager();
						UiGlobals.setPre_scaled(scaleCurValue);
						manager.drawNodes(true);
					}
				}
			}
		}
		else if(source instanceof JSpinner)
		{
			JSpinner spinner = (JSpinner)source;
			String spinnerName = spinner.getName();
			System.out.println("gridSpinner");
			if(spinnerName.equals("gridSpinner")){
				if (gridCurValue != (Integer)spinner.getValue()) {
					gridCurValue = (Integer)spinner.getValue();
					int scale = (Integer)spinner.getValue();
					gridResizer.setValue(scale);
					gridResize(scale);
					
				}
			}
			
		}
		
		// TODO Auto-generated method stub
		
	}
	
	public void gridResize(int space)
	{
		Editor editor = UiGlobals.curEditor();
		LayerGrid grid = (LayerGrid) editor.getLayerManager()
				.findLayerNamed("Grid");
		HashMap map = new HashMap();
		UiGlobals.setGrid_spacing(space);
		map.put("spacing_include_stamp", (int) (space)*UiGlobals.getGrid_scale());
		
		grid.adjust(map);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object s = e.getSource();
		if(s instanceof JButton){
			JButton button = (JButton)s;
			
			Editor editor = UiGlobals.curEditor();
			LayerGrid grid = (LayerGrid) editor.getLayerManager()
					.findLayerNamed("Grid");
			HashMap params = grid.getParameters();
			int spacing = (Integer)params.get("spacing");
			
			double moveSize = .1; 
			int distence = (int)(spacing*moveSize);
			if(distence == 0) distence = 1;
	
			
			HashMap map = new HashMap();	
			if(button.getActionCommand().equals("gridUp")){
				map.put("yOffset", -distence);
			}else if(button.getActionCommand().equals("gridDown")){
				map.put("yOffset", distence);
			}else if(button.getActionCommand().equals("gridLeft")){
				map.put("xOffset", -distence);
			}else if(button.getActionCommand().equals("gridRight")){
				map.put("xOffset", +distence);
			}
			
			grid.adjust(map);
		}
		else if(s instanceof JRadioButtonMenuItem){
			JRadioButtonMenuItem item = (JRadioButtonMenuItem)s;
			for(int count = scaleMin ; count < scaleMax ; count++){
				if(item.getName().equals(scalePrefix+count)){
					scaleMenu.setText(scalePrefix+count);
					item.setSelected(true);
					
					//UiGlobals.get_scaleSlider().setEnabled(false);
					NodeRenderManager manager = UiGlobals.getNodeRenderManager();
					UiGlobals.setGrid_scale(count);
					manager.drawNodes(true);
					
				}
			}
		}
		else if(s instanceof JComboBox){
		    JComboBox cb = (JComboBox)s;
		    String scaleName = (String)cb.getSelectedItem();
		    String[] scaleNamePart = scaleName.split(scalePrefix);
		    System.out.println("Scale changed to : "+scaleNamePart[1]);
		    NodeRenderManager manager = UiGlobals.getNodeRenderManager();
            UiGlobals.setGrid_scale(Integer.parseInt(scaleNamePart[1]));
            manager.drawNodes(true);
		    
		}
	}
} /* end class PaletteFig */
