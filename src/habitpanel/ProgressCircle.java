package habitpanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BasicStroke;


public class ProgressCircle extends JPanel{
    private final int thickness = 20;
    private int max = 10;
    private int reached = 5;

    public ProgressCircle(String nameInput, int x, int y, int w, int h) {
        setOpaque(false); 
        setLayout(null);
        setBounds(x,y,w,h);
    }    
    
    public void setMax(int maxInput){
        max = maxInput;
    }
    public void setReached(int reachedInput){
        reached = reachedInput;
    }

   
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D shape = new Path2D.Double();
        
        int w = getWidth();
        int h = getHeight();
        int startingDegree = 225;
        int delta = 270;
        double fill = reached / (double) max;
        
        // -- DRAWING THE BACKGROUND --
        g2.setStroke(new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
        g2.setColor(Color.LIGHT_GRAY);
        g2.draw(new Arc2D.Double(thickness/2.0, thickness/2.0, w-thickness, h-thickness, startingDegree, -270, Arc2D.OPEN));
        
        
        // -- DRAWING THE DATA FILL --
        int angleReached = (int) Math.round(270 * fill);
        g2.setColor(getBackground());
        g2.draw(new Arc2D.Double(thickness/2.0, thickness/2.0, w-thickness, h-thickness, startingDegree, -angleReached, Arc2D.OPEN));
        
        
        // -- DRAWING BORDER FOR GUAGE SHAPE --
        // Making outside and inside arcs
        Arc2D outerArc = new Arc2D.Double(1, 1, w - 2, h - 2, startingDegree, -delta, Arc2D.OPEN);
        Arc2D innerArc = new Arc2D.Double(thickness, thickness, w - thickness * 2, h - thickness * 2, startingDegree - delta, delta, Arc2D.OPEN);
        
        // Saving needing variables for easier reading
        var oStart = outerArc.getStartPoint();
        var iStart = innerArc.getStartPoint();
        var iEnd   = innerArc.getEndPoint();
        
        // Making end caps for border
        Arc2D rightCap = new Arc2D.Double(iStart.getX()-2, iStart.getY()-4, thickness-1, thickness-1, 315, -180, Arc2D.OPEN);
        Arc2D leftCap = new Arc2D.Double(iEnd.getX()-thickness+3, iEnd.getY()-4, thickness-1, thickness-1, 45, -180, Arc2D.OPEN);
        
        // Building Shape
        shape.moveTo(oStart.getX(), oStart.getY());// Moving to starting position
        shape.append(outerArc, true); // Draw outer arc first going left->right
        shape.append(rightCap, true); // Draw right side cap
        shape.append(innerArc, true); // Draw insider arc going right->left
        shape.append(leftCap, true);  // Draw left side cap
        shape.closePath();
        
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        g2.draw(shape); // Draw the border
        
        g2.dispose();
    }
}
