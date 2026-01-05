package habitpanel;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.Color;
import java.awt.geom.Arc2D;
import java.awt.geom.AffineTransform;
import java.awt.Shape;
import java.awt.geom.*;
import javax.swing.Timer;



public class ScrollButton extends JPanel {
    private final Color shadowColor = new Color(70,70,70);
    int shadowThickness = 8;
    private int arrowH = 0;
    private int arrowW = 0;
    private int pressOffset = 0;          // current visual offset
    private int targetPressOffset = 0;    // where we want to go
    private final int MAX_PRESS = shadowThickness - 1;
    
    
    public enum Type { UP, DOWN, LEFT, RIGHT }
    public enum Form {PILL, CIRCLE}
    private final Type type;
    private final Form form;
    
    public ScrollButton(Type typeInput, Form formInput, int x, int y, int w, int h) {
        type = typeInput;
        form = formInput;
        setOpaque(false); 
        setLayout(null);
        setBounds(x,y,w,h);
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        int thickness = getHeight() - shadowThickness;
        int yArrow = (thickness/2-arrowH/2);
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D shape = new Path2D.Double();
        
        if(form == Form.PILL){
            if(h*2 > w){
                System.out.println("ERROR: PILL is not rectangle enough");
                return;
            }
            // Setting arrow size for the pill shape
            arrowH = getHeight()/3;
            arrowW = arrowH;
            
            // --- FILL SHAPE FOR SHADOW ---
            shape.moveTo(thickness/2, shadowThickness);
            shape.append(new Arc2D.Double(thickness/2, shadowThickness, thickness, thickness, 90, 180, Arc2D.OPEN), true);
            shape.lineTo(w-thickness/2, thickness+shadowThickness);
            shape.append(new Arc2D.Double(w-thickness, shadowThickness, thickness, thickness, 270, 180, Arc2D.OPEN), true);
            shape.closePath();
            g2.setColor(shadowColor);
            g2.fill(shape);


            // --- FILL SHAPE FOR PILL SHAPE ---
            AffineTransform shadowTx = AffineTransform.getTranslateInstance(0, -shadowThickness+pressOffset);
            Shape shadow = shadowTx.createTransformedShape(shape);
            g2.setColor(getBackground());
            g2.fill(shadow);
        }
        else if(form == Form.CIRCLE){
            if(w != h){ // Makes sure we are using a square
                System.out.println("ERROR: CIRCLE is not a squate");
                return;
            }
            
            arrowH = arrowW = w/2;
            
            // -- FILL FOR SHAPE FOR SHADOW --
            g2.setColor(shadowColor);
            g2.fillOval(0, 0, w, h);
            
            // -- FILL SHAPE FOR CIRCLE SHAPE --
            g2.setColor(getBackground());
            g2.fillOval(0, 0, w, h);
        }
        
        // --- POINTING ARROW ---
        shape.reset();
        shape.moveTo(w/2, yArrow+pressOffset);
        shape.lineTo(w/2-arrowW/2, yArrow+arrowH+pressOffset);
        shape.lineTo(w/2+arrowW/2, yArrow+arrowH+pressOffset);
        shape.closePath();
        
        
        double angle = 0;
        Rectangle2D bounds = shape.getBounds2D();
        double cx = bounds.getCenterX();
        double cy = bounds.getCenterY();
        switch (type) {
            case UP -> angle = 0;
            case RIGHT -> angle = Math.PI / 2;
            case DOWN -> angle = Math.PI;
            case LEFT -> angle = -Math.PI / 2;
        }
        g2.setColor(getBackground() == Color.GRAY ? Color.DARK_GRAY : getForeground());
        AffineTransform rotate = AffineTransform.getRotateInstance(angle, cx, cy);
        Shape rotated = rotate.createTransformedShape(shape);
        g2.fill(rotated);
        
        g2.dispose();
    }
    
    
    private Timer pressTimer;
    public void animatePress() {
        targetPressOffset = MAX_PRESS;

        if (pressTimer != null && pressTimer.isRunning()) 
            return;

        pressTimer = new Timer(10, e -> {
            if (pressOffset < targetPressOffset){
                pressOffset++;
            }
            else if (pressOffset > targetPressOffset) {
                // If we have reached back to zero then go ahead and stop the timer 
                if(pressOffset == 0)
                    ((Timer)e.getSource()).stop();
                pressOffset--;
                
            }
            else {
                targetPressOffset = 0;
            }
            repaint();
        });
        
        pressTimer.start();
    }
}