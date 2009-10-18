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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
import org.tigris.gef.ui.ToolBar;

/**
 * A Palette that defines buttons to create lines, rectangles, rounded
 * rectangles, circles, and text. Also a select button is provided to switch
 * back to ModeSelect.
 * 
 * Needs-more-work: sticky mode buttons are not supported right now. They should
 * be in the next release.
 * 
 * @see ModeSelect
 * @see ModeCreateFigLine
 * @see ModeCreateFigRect
 * @see ModeCreateFigRRect
 * @see ModeCreateFigCircle
 * @see ModeCreateFigText
 * @see ModeCreateFigPoly
 */

public class ResizerPaletteFig extends ToolBar implements ChangeListener, ActionListener{

	/**
     * 
     */
	private static final long serialVersionUID = 304194274216578087L;
	
	private int gridCurValue = 0;
	private int scaleCurValue = 0;
	
	JSpinner gridSpinner = null;
	JSlider gridResizer = null;
	JSlider scaleResizer = null;
	
	
	JMenu scaleMenu;
	String scalePrefix = "Scale x";
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
		this.setBackground(Color.white);
		this.setForeground(Color.white);
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
		
		
		
		
		int gridMax = 200;
		int gridMin = 1;
		gridCurValue = UiGlobals.getDefault_grid_spacing();
		gridResizer = new JSlider(JSlider.VERTICAL,
				gridMin, gridMax, gridCurValue);
		gridResizer.setName("gridResizer");
		gridResizer.setBackground(Color.white);
		//Font font = new Font("Dialog.plain", 0, 10);
		
		JLabel minLabel = new JLabel("m");
		//minLabel.setFont(font);
		JLabel maxLabel = new JLabel("M");
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
		JButton buttonUp = new JButton("^");
		buttonUp.setActionCommand("gridUp");
		buttonUp.addActionListener(this);
		buttonUp.setPreferredSize(new Dimension(20, 20));
		buttonUp.setMargin(new Insets(0, 0, 0, 0));
		buttonUp.setBounds(16, 0, 20, 20);
		locControlPanel.add(buttonUp);
		
		JButton buttonDown = new JButton("v");
		buttonDown.setActionCommand("gridDown");
		buttonDown.addActionListener(this);
		buttonDown.setPreferredSize(new Dimension(20, 20));
		buttonDown.setMargin(new Insets(0, 0, 0, 0));
		buttonDown.setBounds(16, 40, 20, 20);
		locControlPanel.add(buttonDown);
		
		JButton buttonLeft = new JButton("<");
		buttonLeft.setActionCommand("gridLeft");
		buttonLeft.addActionListener(this);
		buttonLeft.setPreferredSize(new Dimension(28, 20));
		buttonLeft.setMargin(new Insets(0, 0, 0, 0));
		buttonLeft.setBounds(0, 20, 20, 20);
		locControlPanel.add(buttonLeft);
		
		JButton buttonRight = new JButton(">");
		buttonRight.setActionCommand("gridRight");
		buttonRight.addActionListener(this);
		buttonRight.setPreferredSize(new Dimension(28, 20));
		buttonRight.setMargin(new Insets(0, 0, 0, 0));
		buttonRight.setBounds(32, 20, 20, 20);
		locControlPanel.add(buttonRight);
		
		
		
		
		
		
		
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
		
		
//		setLayout(new GridBagLayout());
//		GridBagConstraints c = new GridBagConstraints();
//		
//		//c.fill = GridBagConstraints.HORIZONTAL;
//		c.gridx = 0;
//		c.gridy = 0;
//		add(scaleMenuBar, c);
//		
//		//c.fill = GridBagConstraints.HORIZONTAL;
//		c.gridx = 0;
//		c.gridy = 1;
//		add(gridResizer, c);
//		
//		//c.fill = GridBagConstraints.HORIZONTAL;
//		c.gridx = 0;
//		c.gridy = 2;
//		add(gridSpinner, c);
//		
//		//c.fill = GridBagConstraints.HORIZONTAL;
//		c.gridx = 0;
//		c.gridy = 3;
//		add(locControlPanel, c);
		
		
		//boolean shouldFill = true;
		//JButton button;
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		//if (shouldFill) {
				//natural height, maximum width
		//		c.fill = GridBagConstraints.HORIZONTAL;
		//}

		//button = new JButton("Button 1");
		
		int leftToolbarWidth = 50;
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.PAGE_START;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.ipady = 20;
		scaleMenuBar.setPreferredSize(new Dimension(leftToolbarWidth, 30));
		add(scaleMenuBar, c);

		//button = new JButton("Button 2");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 200;      //make this component tall
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 1;
		//c.insets = new Insets(20, 0, 20, 0);
		//c.anchor = GridBagConstraints.PAGE_END;
		scaleMenuBar.setPreferredSize(new Dimension(leftToolbarWidth, 150));
		
		JPanel resizerPanel = new JPanel();
		
		resizerPanel.setLayout(new GridBagLayout());
		GridBagConstraints cResizer = new GridBagConstraints();
		//cResizer.insets = new Insets(10, 0, 10, 0);
		resizerPanel.setBorder(BorderFactory.createTitledBorder("Grid"));
		resizerPanel.add(gridResizer);
		
		add(resizerPanel, c);

		//button = new JButton("Button 3");
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx = 0.5;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 2;
		gridSpinner.setPreferredSize(new Dimension(leftToolbarWidth, 30));
		add(gridSpinner, c);

		//button = new JButton("Long-Named Button 4");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 60;      //make this component tall
		c.weightx = 0.0;
		//c.gridwidth = 3;
		c.gridx = 0;
		c.gridy = 3;
		add(locControlPanel, c);

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
		double defaultSpace = (int) Math.pow(2, 3);
		map.put("spacing_include_stamp", (int) (space)*UiGlobals.getPre_scaled());
		
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
					UiGlobals.setPre_scaled(count);
					manager.drawNodes(true);
					
				}
			}
		}
	}
} /* end class PaletteFig */
