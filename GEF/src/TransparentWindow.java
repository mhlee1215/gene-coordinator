import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
 
public class TransparentWindow extends JWindow
{
 
    Robot robot;
    BufferedImage screen;
    Shape shape;
    BufferedImage buffer;
 
    public TransparentWindow(Shape shape) throws AWTException
    {
        this.shape = shape;
        robot = new Robot(getGraphicsConfiguration().getDevice());
        requestFocus();
        setSize(shape.getBounds().getSize());
        buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        updateScreen();
 
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK|AWTEvent.FOCUS_EVENT_MASK);
    }
 
    protected void updateScreen()
    {
        screen = robot.createScreenCapture(new Rectangle(new Point(0,0), Toolkit.getDefaultToolkit().getScreenSize()));
    }
 
    protected void processFocusEvent(FocusEvent e)
    {
        super.processFocusEvent(e);
        if(e.getID() == FocusEvent.FOCUS_GAINED)
        {
            updateScreen();
            repaint();
        }
    }
 
    protected void processMouseMotionEvent(MouseEvent e)
    {
        super.processMouseMotionEvent(e);
        if(e.getID() == MouseEvent.MOUSE_DRAGGED)
        {
            Point p = e.getPoint();
            SwingUtilities.convertPointToScreen(p, (Component)e.getSource());
            setLocation(p.x, p.y);
            repaint();
        }
    }
 
    public void paint(Graphics _g)
    {
        Graphics2D g = buffer.createGraphics();
        if(screen != null)
        {
            Point location = getLocationOnScreen();
            g.drawImage(screen, -location.x, -location.y, this);
        }
 
        g.setColor(Color.gray);
        g.fill(shape);
 
        _g.drawImage(buffer, 0, 0, this);
    }
 
    public static void main(String[] args)
    {
        GeneralPath p = new java.awt.geom.GeneralPath();
        p.moveTo(0,0);
        p.lineTo(100, 0);
        p.lineTo(50, 50);
        p.lineTo(100, 100);
        p.lineTo(0, 100);
        p.lineTo(0,0);
        p.closePath();
 
        try
        {
            new TransparentWindow(p).show();
        } catch (AWTException e)
        {
            e.printStackTrace();
        }
    }
 
}