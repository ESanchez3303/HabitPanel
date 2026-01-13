package habitpanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import javax.swing.JLabel;


public class ProgressCircle extends JPanel{
    private final int thickness = 20;
    private int max = 10;
    private int reached = 10;
    JLabel nameTag = null;
    JLabel progressData = null;

    public ProgressCircle(String nameInput, int x, int y, int w, int h) {
        setOpaque(false); 
        setLayout(null);
        setBounds(x,y,w,h);
        
        // Making the label for the progress circle (day, week, month)
        nameTag = new JLabel(nameInput);
        nameTag.setHorizontalAlignment(JLabel.CENTER);
        nameTag.setBounds(0, getHeight()-40, getWidth(), 40);
        nameTag.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nameTag.setForeground(getBackground());
        this.add(nameTag);
        
        // Making the label for the progress display (9/10) etc
        progressData = new JLabel("");
        progressData.setHorizontalAlignment(JLabel.CENTER);
        progressData.setBounds(0, 0, getWidth(), getHeight());
        progressData.setFont(new Font("Segoe UI", Font.BOLD, 15));
        progressData.setForeground(getBackground());
        this.add(progressData);
        
    }    
    
    public void setMax(int maxInput){
        max = maxInput;
        progressData.setText(reached + "/" + max);
        repaint();
    }
    public void setReached(int reachedInput){
        reached = reachedInput;
        progressData.setText(reached + "/" + max);
        repaint();
    }

   
    @Override
    protected void paintComponent(Graphics g) {
        // Catch when we have invalid data
        if(reached > max || max <= 0 || reached < 0)
            return;
        
        
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
        if(reached > 0){
            g2.draw(new Arc2D.Double(thickness/2.0, thickness/2.0, w-thickness, h-thickness, startingDegree, -angleReached, Arc2D.OPEN));
        }
        // If we are 0/x completed, then just draw a small dot
        else{
            g2.draw(new Arc2D.Double(thickness/2.0, thickness/2.0, w-thickness, h-thickness, startingDegree, -1, Arc2D.OPEN));
        }
        
        
        
        // -- DRAWING BORDER FOR GUAGE SHAPE --
        /*
        // Making outside and inside arcs
        Arc2D outerArc = new Arc2D.Double(1, 1, w - 2, h - 2, startingDegree, -delta, Arc2D.OPEN);
        Arc2D innerArc = new Arc2D.Double(thickness, thickness, w - thickness * 2, h - thickness * 2, startingDegree - delta, delta, Arc2D.OPEN);
        
        // Saving needing variables for easier reading
        var oStart = outerArc.getStartPoint();
        var iStart = innerArc.getStartPoint();
        var iEnd   = innerArc.getEndPoint();
        
        // Making end caps for border
        Arc2D rightCap = new Arc2D.Double(iStart.getX()-3, iStart.getY()-3, thickness-1, thickness-1, 315, -180, Arc2D.OPEN);
        Arc2D leftCap = new Arc2D.Double(iEnd.getX()-thickness+4, iEnd.getY()-3, thickness-1, thickness-1, 45, -180, Arc2D.OPEN);
        
        // Building Shape for border
        shape.moveTo(oStart.getX(), oStart.getY());// Moving to starting position
        shape.append(outerArc, true); // Draw outer arc first going left->right
        shape.append(rightCap, true); // Draw right side cap
        shape.append(innerArc, true); // Draw insider arc going right->left
        shape.append(leftCap, true);  // Draw left side cap
        shape.closePath();
        
        g2.setStroke(new BasicStroke(1));
        g2.setColor(Color.BLACK);
        g2.draw(shape); // Draw the border
        */

        g2.dispose();
    }
}
