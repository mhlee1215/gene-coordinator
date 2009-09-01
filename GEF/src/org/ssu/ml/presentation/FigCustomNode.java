// $Id: FigCircle.java 1218 2009-01-12 22:16:24Z bobtarling $
// Copyright (c) 1996-2009 The Regents of the University of California. All
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

package org.ssu.ml.presentation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.ssu.ml.base.CmdGetNodes;
import org.ssu.ml.base.NodeDescriptor;
import org.ssu.ml.base.UiGlobals;
import org.tigris.gef.base.CmdReorder;
import org.tigris.gef.base.Editor;
import org.tigris.gef.presentation.Fig;
import org.tigris.gef.presentation.FigRect;
import org.tigris.gef.util.Localizer;

/**
 * Primitive Fig for displaying circles and ovals.
 * @author ics125
 */
public class FigCustomNode extends FigRect {

    public FigCustomNode(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}
    
    public FigCustomNode(int x, int y, int w, int h, Object owner) {
		super(x, y, w, h);
		setOwner(owner);
		// TODO Auto-generated constructor stub
	}
	/**
     * 
     */
    private static final long serialVersionUID = 7376986113799307733L;
    /**
     * Used as a percentage tolerance for making it easier for the user to
     * select a hollow circle with the mouse. Needs-More-Work: This is bad
     * design that needs to be changed. Should use just GRIP_FACTOR.
     */
    
    public Vector getPopUpActions(MouseEvent me) {
        Vector popUpActions = new Vector();
        
        Editor editor = UiGlobals.curEditor();
        List<Fig> list = editor.getSelectionManager().getSelectedFigs();
        String nodeStr = "";
        String prefix = "<html><body style=\"background-color: #ffffdd\"><h3><font color=#000000><span >";
        String postfix = "</span></font></h3></body></html>";
     
        int nodeCount = 0;
        
        for(int count = 0 ; count < list.size() ; count++)
        {
        	Fig node = list.get(count);

        	Object desc = node.getOwner();
        	if(desc instanceof NodeDescriptor)
        	{
        		
        		NodeDescriptor nodeDesc = (NodeDescriptor)desc;
        		System.out.println("name : "+nodeDesc.getName()+", "+node.getLocation());
        		if(count == 0)
        			nodeStr = nodeDesc.getName();
        		else if(count < 6)
        			nodeStr += "<br>&nbsp;"+nodeDesc.getName();
        		else {
        			nodeStr += "<br>&nbsp;...<br>&nbsp;...<br>&nbsp;Total "+list.size()+" nodes";
        			break;
        		}
        		nodeCount++;
        	}
        }
        
        
        
        NodeDescriptor desc = (NodeDescriptor)this.getOwner();
        JLabel name = new JLabel(prefix+nodeStr+postfix);
        if(nodeCount > 5)
        	name.setPreferredSize(new Dimension(120, (nodeCount+2)*24));
        else
        	name.setPreferredSize(new Dimension(120, (nodeCount)*24));
        name.setToolTipText(desc.getName());
        name.setOpaque(true);
        name.setBackground(new Color(255, 255, 221));
        name.setFocusable(false);
        
        //name.set
        
        JMenu getMenu = new JMenu(Localizer.localize("PresentationGef",
        "Get selected Node"));
        
        getMenu.add(new CmdGetNodes());

        
        
        
        //popUpActions.addElement(orderMenu);
        popUpActions.addElement(name);
        popUpActions.addElement(new JSeparator());
        popUpActions.addElement(getMenu);
        //popUpActions.addElement(getClustering);

        return popUpActions;
    }
    
    /** Paint this FigRect */
    public void paint(Graphics g) {
    	//g.draw
       // drawRect(g, isFilled(), getFillColor(), getLineWidth(), getLineColor(), getX(), getY(), getWidth(),
        //        getHeight(), getDashed(), _dashes, _dashPeriod);
    	
    	
    	Color old = g.getColor();
    	g.setColor(getLineColor());
    	g.fillOval(getX(), getY(), getWidth(), getHeight());
    	g.setColor(old);
    }
    
    
    
    
} /* end class FigCircle */