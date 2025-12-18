package habitpanel;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Path2D;
import javax.swing.border.LineBorder;

public class RoundedBorderSunken extends LineBorder {
    
    Color backgroundColor = null;
    public RoundedBorderSunken(Color backgroundColorInput) {
        super(Color.BLACK, 1, true);
        backgroundColor = backgroundColorInput;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D shape = new Path2D.Double();

        int xPadding = 0;
        int cornerArc = 30;
        
        
        // ------------------------ FILL INSIDE BORDER -------------------------
        g2.setColor(backgroundColor);
        shape.moveTo(xPadding+cornerArc/2,0);
        shape.append(new Arc2D.Double(xPadding, 0, cornerArc, cornerArc, 90, 90, Arc2D.OPEN), true); 
        shape.lineTo(xPadding, height-cornerArc/2);
        shape.append(new Arc2D.Double(xPadding, shape.getCurrentPoint().getY(), cornerArc, cornerArc, 180, 90, Arc2D.OPEN), true); 
        shape.lineTo(width-xPadding-cornerArc/2, height-1);
        g2.fill(shape);
        
        // ----------------------- LINE BORDER AROUND --------------------------
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(xPadding+cornerArc/2, 0, width-xPadding-cornerArc/2, 0);
        g2.drawArc(xPadding, 0, cornerArc, cornerArc, 90, 90);
        g2.drawLine(xPadding, cornerArc/2, xPadding, height-cornerArc/2);
        g2.drawArc(xPadding, height-cornerArc-1, cornerArc, cornerArc, 180, 90);
        g2.drawLine(xPadding+cornerArc/2, height-1, width-xPadding-cornerArc/2, height-1);
        g2.drawArc(width-xPadding-cornerArc, height-cornerArc-1, cornerArc, cornerArc, 270, 90);
        g2.drawLine(width-xPadding, height-1-cornerArc/2, width-xPadding, cornerArc/2);
        g2.drawArc(width-xPadding-cornerArc, 0, cornerArc, cornerArc, 0, 90);
        
        
        
        // --- 3D EFFECT TOP SIDE ---

        
        
        g2.dispose();
    }
}