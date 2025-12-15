package habitpanel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.Color;
import java.awt.geom.Arc2D;



public class ScrollCornerButton extends JPanel {
    private final int middleHeigth= 30;
    private final int middleWidth = 250;
    private final int middleXLocation = 350;
    private final int sideArcRadius = 20;
    private final int edgeWidth = 10;
    private final int arrowWidth = 70;
    private final int arrowHeight = 20;
    private final int arrowSpacing = 10;
    
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
        int mw = middleWidth;
        int mh = middleHeigth;
        int mx = middleXLocation;
        int sr = sideArcRadius;
        int ew = edgeWidth;
        int aw = arrowWidth;
        int as = arrowSpacing;
        int ah = arrowHeight;
        int middle = w/2;
        

        
        // Avoid weird values when the panel is too small
        if (mw <= 0 || mx <= 0 || mh <= 0 || w <= 0 || h <= 0) 
            return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D shape = new Path2D.Double();

        // If the scroll button is one on top, then draw it like this
        if (type == Type.UP) {
            // --- FILL SHAPE ---
            shape.moveTo(0, h);              // START: bottom left corner
            shape.lineTo(0, mh);             // TO: upper left corner
            shape.append(new Arc2D.Double(0, shape.getCurrentPoint().getY(), 2*sr, 2*sr, 180, -90, Arc2D.OPEN), true); // Upper left corner arc
            shape.lineTo(mx-mh, mh);         // TO: middle bottom left BEFORE arc
            shape.append(new Arc2D.Double(mx-mh-mh, -mh, 2*mh, 2*mh, 270, 90, Arc2D.OPEN), true);  // TO: middle top left AFTER arc (only to middle point)    
            shape.lineTo(mx+mw, 0);          // TO: middle top right BEFORE arc
            shape.append(new Arc2D.Double(mx+mw, -mh, 2*mh, 2*mh, 180, 90, Arc2D.OPEN), true);     // TO: middle bottom right AFTER arc
            shape.lineTo(w-sr, mh);             // TO: upper right corner
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX()-sr, shape.getCurrentPoint().getY(), 2*sr, 2*sr, 90, -90, Arc2D.OPEN), true);     // upper right arc
            shape.lineTo(w, h);              // TO: lower right corner
            shape.lineTo(w-ew, h);           // TO: lower right edge-left corner BEFORE arc
            shape.append(new Arc2D.Double(w-ew-(2*sr), h-(2*sr), 2*sr, 2*sr, 0, 90, Arc2D.OPEN), true); // lower section inside right arc
            shape.lineTo(ew+sr, shape.getCurrentPoint().getY());   // TO: lower left section BEFORE arc  
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX()-(sr), shape.getCurrentPoint().getY(), 2*sr, 2*sr, 90, 90, Arc2D.OPEN), true);// lower section inside left arc
            shape.lineTo(shape.getCurrentPoint().getX(), h);
            shape.lineTo(0, h);              // TO: bottom left corner
            
            g2.setColor(getBackground());
            g2.fill(shape);

            
            // --- DRAW BORDER ---
            g2.setStroke(new BasicStroke(1));
            
            
            // Top Side Sections for 3d effect
            g2.setColor(Color.WHITE);
            g2.draw(new Arc2D.Double(mx-mh-mh, -mh, 2*mh, 2*mh, 270, 90, Arc2D.OPEN));   // top left arc
            g2.draw(new Arc2D.Double(0, mh, 2*sr, 2*sr, 180, -90, Arc2D.OPEN)); // mid left arc
            
            g2.drawLine(0, h, 0, mh+sr);  // left side vertical
            g2.drawLine(sr, mh, mx-mh, mh); // left side top horz
            g2.drawLine(mx, 0, mx+mw, 0); // middle top horz
            g2.drawLine(mx+mw+mh, mh, w-sr, mh); // right side top horz
            g2.drawLine(w-ew, h-1, w-ew, h-sr); // right side left horz
            
            
            
            
            // Bottom Side Sections for 3d effect
            g2.setColor(Color.DARK_GRAY);
            g2.draw(new Arc2D.Double(mx+mw, -mh, 2*mh, 2*mh, 180, 90, Arc2D.OPEN)); // top right arc
            g2.draw(new Arc2D.Double(w-ew-(2*sr), h-(2*sr)-1, 2*sr, 2*sr, 0, 90, Arc2D.OPEN)); // bottom right arc
            g2.draw(new Arc2D.Double(w-sr*2, mh, 2*sr, 2*sr, 90, -90, Arc2D.OPEN)); // mid right arc
            g2.draw(new Arc2D.Double(ew, h-sr-sr-1, 2*sr, 2*sr, 90, 90, Arc2D.OPEN)); // bottom left arc
            
            g2.drawLine(w-1, mh+sr-2, w-1, h); // right side vertical
            g2.drawLine(w-1, h-1, w-ew, h-1); // right side edge horz
            g2.drawLine(w-ew-sr, h-sr-sr-1, ew+sr, h-sr-sr-1); // long bottom section
            g2.drawLine(ew,h-sr, ew, h); // left edge right side 
            g2.drawLine(0, h-1, ew, h-1); // left edge bottom side
            
            
            
            // --- DRAW ARROW SHAPE ---
            shape.reset();
            shape.moveTo(middle,as);
            shape.lineTo(middle+aw/2, as+ah);
            shape.lineTo(middle-aw/2, as + ah);
            shape.lineTo(middle, as);
            g2.setColor(getForeground());
            g2.fill(shape);
            
            // --- DRAW ARROW BORDER --- 
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(middle, as, middle+aw/2, as+ah);
            g2.drawLine(middle+aw/2, as+ah, middle-aw/2 , as+ah);
            g2.drawLine(middle-aw/2, as+ah, middle, as);
        }
        
        // If the scroll button is the bottom, then draw it like this 
        else {
            
            // --- FILL SHAPE ---
            shape.moveTo(0, 0); // START: upper let corner
            shape.lineTo(0, h-mh-sr); // TO: bottom left before arc
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX(), shape.getCurrentPoint().getY()-sr, 2*sr, 2*sr, 180, 90, Arc2D.OPEN), true);
            shape.lineTo(mx-mh, h-mh); // TO: middle section from left side before arc
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX()-mh, shape.getCurrentPoint().getY(), 2*mh, 2*mh, 90, -90, Arc2D.OPEN), true);
            shape.lineTo(mx+mw,h);
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX(), shape.getCurrentPoint().getY()-mh, 2*mh, 2*mh, 180, -90, Arc2D.OPEN), true);
            shape.lineTo(w-sr,h-mh);
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX()-sr, shape.getCurrentPoint().getY()-sr-sr, 2*sr, 2*sr, 270, 90, Arc2D.OPEN), true);
            shape.lineTo(w,0);
            shape.lineTo(w-ew,0);
            shape.lineTo(w-ew,sr);
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX()-sr-sr, shape.getCurrentPoint().getY()-sr, 2*sr, 2*sr, 0, -90, Arc2D.OPEN), true);
            shape.lineTo(ew+sr,sr+sr);
            shape.append(new Arc2D.Double(shape.getCurrentPoint().getX()-sr, shape.getCurrentPoint().getY()-sr-sr, 2*sr, 2*sr, 270, -90, Arc2D.OPEN), true);
            shape.lineTo(ew,0);
            shape.lineTo(0,0);
            
            g2.setColor(getBackground());
            g2.fill(shape);

            
            // --- DRAW BORDER EXCEPT TOP EDGE ---
            g2.setStroke(new BasicStroke(1));
            
            // Top Side Sections for 3d effect
            g2.setColor(Color.WHITE);
            g2.draw(new Arc2D.Double(0, h-mh-sr-sr, 2*sr, 2*sr, 180, 90, Arc2D.OPEN));  // middle left arc
            g2.draw(new Arc2D.Double(mx-mh-mh, h-mh, 2*mh, 2*mh, 90, -90, Arc2D.OPEN)); // bottom left arc
            g2.draw(new Arc2D.Double(w-ew-sr-sr, 0, 2*sr, 2*sr, 0, -90, Arc2D.OPEN)); // top right arc
            
            
            g2.drawLine(0, 0, 0, h-mh-sr); // left side vertical
            g2.drawLine(w-1,0,w-ew,0); // right edge top horz
            g2.drawLine(w-ew,0,w-ew,sr); // right edge left side horz
            g2.drawLine(w-ew-sr,sr+sr,ew+sr,sr+sr); // long top side
            g2.drawLine(ew,0,0,0); // left edge top side horz
            
            
            // Bottom Side Sections for 3d effect
            g2.setColor(Color.DARK_GRAY);
            g2.draw(new Arc2D.Double(w-sr-sr, h-mh-sr-sr, 2*sr, 2*sr, 270, 90, Arc2D.OPEN)); // middle right arc
            g2.draw(new Arc2D.Double(mx+mw, h-mh, 2*mh, 2*mh, 180, -90, Arc2D.OPEN)); // bottom right arc
            g2.draw(new Arc2D.Double(ew, 0, 2*sr, 2*sr, 270, -90, Arc2D.OPEN)); // top left arc
            
            g2.drawLine(ew+2, h-mh, mx-mh, h-mh); // bottom left side horz
            g2.drawLine(mx,h-1,mx+mw,h-1); // full bottom horz
            g2.drawLine(mx+mw+mh,h-mh,w-ew-2,h-mh); // bottom right side horz
            g2.drawLine(w-1,h-mh-sr+2,w-1,0); // right side vertical
            g2.drawLine(ew,sr,ew,0); // left edge right side vertical
            
            
            // --- DRAW ARROW SHAPE ---
            shape.reset();
            shape.moveTo(middle,h-as);
            shape.lineTo(middle+aw/2, h-as-ah);
            shape.lineTo(middle-aw/2, h-as-ah);
            shape.lineTo(middle, h-as);
            g2.setColor(getForeground());
            g2.fill(shape);
            
            // --- DRAW ARROW BORDER --- 
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(1));
            g2.drawLine(middle, h-as, middle+aw/2, h-as-ah);
            g2.drawLine(middle+aw/2, h-as-ah, middle-aw/2, h-as-ah);
            g2.drawLine(middle-aw/2, h-as-ah, middle, h-as);
            
            
        }
        

        g2.dispose();
    }
}