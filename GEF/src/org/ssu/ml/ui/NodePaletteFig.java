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

import javax.swing.JTextField;

import org.ssu.ml.base.CmdGridChart;
import org.ssu.ml.base.CmdShowAbout;
import org.ssu.ml.base.CmdZoom;
import org.tigris.gef.base.ModeCreateFigCircle;
import org.tigris.gef.base.ModeCreateFigLine;
import org.tigris.gef.base.ModeCreateFigPoly;
import org.tigris.gef.base.ModeCreateFigRRect;
import org.tigris.gef.base.ModeCreateFigRect;
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

public class NodePaletteFig extends WestToolBar {

    /**
     * 
     */
    private static final long serialVersionUID = 304194274216578087L;

    public NodePaletteFig() {
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
        this.setBackground(Color.black);
        this.setForeground(Color.black);
        
        //add(new CmdSetMode(ModeCreateFigLine.class, "Line"));
        //add(new CmdSetMode(ModeCreateFigText.class, "Text"));
        
        //add(image1, "Image1", "Image1");
        add(new CmdZoom(1.25), "Zoom in", "zoomIn", ToolBar.BUTTON_TYPE_NO_TEXT);
        add(new CmdZoom(1/1.25), "Zoom out", "zoomOut", ToolBar.BUTTON_TYPE_NO_TEXT);
        //this.addSeparator();
        add(new CmdGridChart(), "Grid Histo", "siGraph", ToolBar.BUTTON_TYPE_NO_TEXT);
        //add(new CmdShowAbout(), "Show About", "about1", ToolBar.BUTTON_TYPE_NO_TEXT);
        
        JTextField searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(200, 30));
        this.setBackground(Color.black);
        this.setForeground(Color.black);
        
        System.out.println("back!!!!!!!!!!!!!"+this.getBackground());
        System.out.println("back!!!!!!!!!!!!!"+this.getForeground());
        //add(searchField);
    }
} /* end class PaletteFig */
