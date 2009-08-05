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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JSlider;
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

public class ResizerPaletteFig extends ToolBar implements ChangeListener{

	/**
     * 
     */
	private static final long serialVersionUID = 304194274216578087L;
	
	private int gridCurValue = 0;
	private int scaleCurValue = 0;

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
		this.setLayout(new GridLayout(4, 1));
		//this.setLayout(new FlowLayout());
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// add(new CmdSetMode(ModeCreateFigLine.class, "Line"));
		// add(new CmdSetMode(ModeCreateFigText.class, "Text")	);

		// add(image1, "Image1", "Image1");
		//add(new CmdZoom(2), "Zoom in", "zoom_in");
		//add(new CmdZoom(0.5), "Zoom out", "zoom_out");
		int gridMax = 10;
		int gridMin = 1;
		gridCurValue = 5;
		JSlider gridResizer = new JSlider(JSlider.VERTICAL,
				gridMin, gridMax, gridCurValue);
		gridResizer.setName("gridResizer");
		gridResizer.setBackground(Color.white);
		Font font = new Font("Dialog.plain", 0, 10);
		
		JLabel minLabel = new JLabel("Min");
		minLabel.setFont(font);
		JLabel maxLabel = new JLabel("Max");
		maxLabel.setFont(font);
		Hashtable<Integer, JLabel> labelTable = 
            new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer( gridMin ),
				minLabel );
		labelTable.put(new Integer( gridMax ),
				maxLabel );
		gridResizer.setLabelTable(labelTable);
		
		//gridResizer.setFont(font);
        gridResizer.setPaintLabels(true);
        gridResizer.setMajorTickSpacing(1);
        gridResizer.addChangeListener(this);
        //gridResizer.setPaintTicks(true);
        BorderFactory a;
        
        
        gridResizer.setBorder(
                //BorderFactory.createEmptyBorder(0,0,0,0)
                //BorderFactory.createLineBorder(Color.black, 1)
        		
                BorderFactory.createTitledBorder("Grid")
                );
		add(gridResizer);
		UiGlobals.set_gridSlider(gridResizer);
		
		int scaleMin = 1;
		int scaleMax = 4;
		scaleCurValue = scaleMin;
		JSlider scaleResizer = new JSlider(JSlider.VERTICAL,
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
        
        scaleResizer.setBorder(
                //BorderFactory.createEmptyBorder(0,0,0,0)
                //BorderFactory.createLineBorder(Color.black, 1)
                BorderFactory.createTitledBorder("Scale")
                );
		add(scaleResizer);
		scaleResizer.setEnabled(false);

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
						int scale = (int)Math.pow(2, slider.getValue());
						
						Editor editor = UiGlobals.curEditor();
						LayerGrid grid = (LayerGrid) editor.getLayerManager()
								.findLayerNamed("Grid");
						HashMap map = new HashMap();
						double defaultSpace = (int) Math.pow(2, 3);
						map.put("spacing", (int) (scale));
						grid.adjust(map);
					}
				}
				else if(sliderName.equals("scaleResizer"))
				{
					if(scaleCurValue != slider.getValue()){
						System.out.println("scale Value : "+slider.getValue());
						scaleCurValue = slider.getValue();
						
						UiGlobals.get_scaleSlider().setEnabled(false);
						NodeRenderManager manager = UiGlobals.getNodeRenderManager();
						manager.drawNodes(scaleCurValue);
					}
				}
			}
		}
		// TODO Auto-generated method stub
		
	}
} /* end class PaletteFig */