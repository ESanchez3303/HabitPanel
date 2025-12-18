package habitpanel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.geom.*;



public class ScrollCornerButton extends JPanel {
    private final Color shadowColor = new Color(70,70,70);
    private final int arrowW = 50;
    private final int arrowH = 15;
    
    public enum Type { UP, DOWN }
    private final Type type;
    
    public ScrollCornerButton(Type typeInput) {
        type = typeInput;
        setOpaque(false); 
        setLayout(null);
        
        if(type == Type.UP)
            setBounds(10 ,45, 950, 90);
        else
            setBounds(10, 500, 950, 90);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        
        // PILL SHAPED
        
        int thickness = 30;
        int shadowThickness = 10;
        int xPadding = 100;
        int yPill = (type == Type.UP ? 0 : h-thickness-shadowThickness);
        int yArrow = yPill + (thickness/2-arrowH/2);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D shape = new Path2D.Double();
            
        // --- FILL SHAPE FOR SHADOW ---
        shape.moveTo(xPadding+thickness/2, yPill+shadowThickness);
        shape.append(new Arc2D.Double(xPadding+thickness/2, yPill+shadowThickness, thickness, thickness, 90, 180, Arc2D.OPEN), true);
        shape.lineTo(w-xPadding-thickness/2, thickness+yPill+shadowThickness);
        shape.append(new Arc2D.Double(w-xPadding-thickness, yPill+shadowThickness, thickness, thickness, 270, 180, Arc2D.OPEN), true);
        shape.closePath();
        g2.setColor(shadowColor);
        g2.fill(shape);


        // --- FILL SHAPE FOR PILL SHAPE ---
        AffineTransform shadowTx = AffineTransform.getTranslateInstance(0, -shadowThickness);
        Shape shadow = shadowTx.createTransformedShape(shape);
        g2.setColor(getBackground());
        g2.fill(shadow);

        // --- BORDER ---
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(xPadding+thickness, yPill, w-xPadding-thickness/2, yPill);
        g2.drawLine(xPadding+thickness, yPill+thickness, w-xPadding-thickness/2, yPill+thickness);
        g2.draw(new Arc2D.Double(xPadding+thickness/2, yPill, thickness, thickness, 90, 180, Arc2D.OPEN));
        g2.draw(new Arc2D.Double(w-xPadding-thickness, yPill, thickness, thickness, 270, 180, Arc2D.OPEN));
        
        // --- POINTING ARROW ---
        shape.reset();
        shape.moveTo(w/2, yArrow);
        shape.lineTo(w/2-arrowW/2, yArrow+arrowH);
        shape.lineTo(w/2+arrowW/2, yArrow+arrowH);
        shape.closePath();
        
        g2.setColor(getBackground() == Color.GRAY ? Color.DARK_GRAY : getForeground());
        
        if(type == Type.UP)
            g2.fill(shape);
        else{
            Rectangle2D b = shape.getBounds2D();
            AffineTransform rotate = AffineTransform.getRotateInstance(Math.PI, b.getCenterX(), b.getCenterY());
            Shape newArrow = rotate.createTransformedShape(shape);
            g2.fill(newArrow);
        }
       
        
        g2.dispose();
    }
}