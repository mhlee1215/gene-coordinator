// File: CmdZoom.java
// Classes: CmdZoom
// Original Author: lawley@dstc.edu.au
// $Id: CmdZoom.java 1153 2008-11-30 16:14:45Z bobtarling $

package org.ssu.ml.base;

import org.tigris.gef.base.Cmd;


public class CmdShowFuncAssociate extends Cmd {
    private static final long serialVersionUID = 8472508088519383941L;
    private static Thread thread = null;
    
    // //////////////////////////////////////////////////////////////
    // constructor

    /** Default behaviour is to restore scaling to 1.0 (1 to 1) */
    public CmdShowFuncAssociate() {
        super("");
    }

    /**
     * Each time <code>doIt()</code> is invoked, adjust scaling by a factor of
     * <code>magnitude</code>.
     * 
     * @param magnitude
     *                the factor by which to adjust the Editor's scaling. Must
     *                be greater than or equal to zero. If zero, resets the
     *                Editor's scale factor to 1.
     */


    /** Convert the zoom magnitude to an English description. */
   

    /** Adjust the scale factor of the current editor. */
    public void doIt() {
    	if(thread != null)
    		thread.stop();
    	thread = new Thread(UiGlobals.getFunctionUniverse());
		thread.start();
    }

    /**
     * Undo the zoom. Does not yet work for magnitude of 0 (a reset), and is
     * subject to skew due to precision errors since for floats we cannot assume
     * <code>(x * f / f) == x</code>
     */
    public void undoIt() {
       
    }
} /* end class CmdZoom */