package etc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

import javax.swing.border.AbstractBorder;

import org.jdesktop.swingx.graphics.GraphicsUtilities;
import org.jdesktop.swingx.graphics.ShadowRenderer;

public class RoundedBorder extends AbstractBorder {
	private Color FILL = new Color(135, 135, 135);
	private Color shadowColor;
	private final Stroke STROKE = new BasicStroke(20f);
	int arc = 10;
	int offset = 30;
	boolean drawShadow;
	
	public RoundedBorder(Color borderColor, boolean drawShadow, Color shadowColor){
		FILL = borderColor;
		this.drawShadow = drawShadow;
		this.shadowColor = shadowColor;
	}
	@Override 
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setRenderingHint(
		RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(FILL);
		g2.setStroke(STROKE);
		
		
		
	    int shadowSize = 6;
	    
		BufferedImage shadow = GraphicsUtilities.createCompatibleTranslucentImage(w, h);
		
		Graphics2D g3 = shadow.createGraphics();
	    g3.setColor(Color.WHITE);
	    g3.fillRoundRect(0, 0, w-13, h-13, arc, arc);
	    g3.dispose();

	    ShadowRenderer renderer = new ShadowRenderer(shadowSize, 0.5f, shadowColor);
	    shadow = renderer.createShadow(shadow);
	    
	    int xOffset = (shadow.getWidth()  - w) / 2;
	    int yOffset = (shadow.getHeight() - h) / 2;
		if(this.drawShadow){
		    g2.drawImage(shadow, 0, 0, null);
		}
	    
	    Paint bg =
			new GradientPaint(0, 0, FILL,
			w/2, h/2, Color.white,
			true);
		
		g2.setPaint(bg);	
		//g2.drawRoundRect(offset, offset, w-offset*2, h-offset*2, 5, 5);
		
		
		g2.setColor(FILL);
	    g2.fillRoundRect(10, 10, w-20, h-20, arc+5, arc);
//
	    g2.setStroke(new BasicStroke(3f));
	    g2.setColor(Color.WHITE);
	    g2.drawRoundRect(10, 10, w-20, h-20, arc+5, arc); 
	    
	    
	    g2.dispose();
	}
	
	
}