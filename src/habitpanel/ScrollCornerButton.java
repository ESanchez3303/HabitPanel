package habitpanel;
import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.geom.Arc2D;



public class ScrollCornerButton extends JPanel {
    private final int cutOutLenght = 80;
    private final int textBoxW = 50;
    private final int textBoxH = 45;
    private final int tailLength = 160;
    private final Font textFont = new Font("Segoe UI", Font.BOLD, 30);
    
    public enum Type { UP, DOWN }
    private final Type type;
    
    public ScrollCornerButton(Type typeInput) {
        setOpaque(false); 
        type = typeInput;
        setLayout(null);
        JLabel directionText = new JLabel();
        directionText.setHorizontalAlignment(SwingConstants.CENTER);
        
        if(type == Type.UP){
            setBounds(860, 30, 100, 265);
            directionText.setBounds(getWidth()-textBoxW,0,textBoxW, textBoxH);
            directionText.setFont(textFont);
            directionText.setText("▲"); 
        }
        else{
            setBounds(860, 310, 100, 265);
            directionText.setBounds(getWidth()-textBoxW,getHeight()-textBoxH,textBoxW, textBoxH);
            directionText.setFont(textFont);
            directionText.setText("▼");
        }
        
        add(directionText);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();
        int c = cutOutLenght;

        
        // Avoid weird values when the panel is too small
        if (c <= 0 || w <= 0 || h <= 0) 
            return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D shape = new Path2D.Double();

        // If the scroll button is one on top, then draw it like this
        if (type == Type.UP) {
            // --- FILL SHAPE ---
            shape.moveTo(0,0); // STARTING: Top left 
            shape.lineTo(w,0); // Top left -> top right
            shape.lineTo(w,h); // Top right -> bottom right
            shape.lineTo(c,h); // Bottom right -> bottom left of lower edge
            shape.lineTo(c, h-tailLength); // Bottom left of lower edge -> starting curve bottom right section
            shape.append(new Arc2D.Double(-c, h-tailLength-c, 2*c, 2*c, 0, 90, Arc2D.OPEN), true); // starting curve bottom right section -> ending curve upper left section
            shape.lineTo(0, 0); // Ending curve upper left section -> top left
            
            
            g2.setColor(getBackground());
            g2.fill(shape);

            
            // --- DRAW BORDER ---
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);
            
            g2.drawLine(0, 0, w, 0);                   // Top border
            g2.drawLine(c, h, c, h - tailLength);      // Left side before arc
            g2.draw(new Arc2D.Double(-c, h-tailLength-c, 2*c, 2*c, 0, 90, Arc2D.OPEN)); // Draw the arc edge
            g2.drawLine(0, h-tailLength-c, 0, 0);      // Left side after arc
            
            // Glitch fix: these for some reason needs 2px
            g2.setStroke(new BasicStroke(2));
            g2.drawLine(w, 0, w, h);      // Right border
            g2.drawLine(w, h, c, h);      // Bottom small edge
            
        }
        
        // If the scroll button is the bottom, then draw it like this 
        else {
            // --- FILL SHAPE ---
            shape.moveTo(0, h);           // START: :ower left
            shape.lineTo(0, tailLength+c);// TO top of left small edge
            shape.append(new Arc2D.Double(-c, tailLength-c, 2*c, 2*c, 270, 90, Arc2D.OPEN), true);  // arc
            shape.lineTo(c, 0);           // TO top of left larger edge
            shape.lineTo(w,0);            // TO top right
            shape.lineTo(w, h);           // TO bottom right
            shape.lineTo(0,h);            // TO bottom left
            
            g2.setColor(getBackground());
            g2.fill(shape);

            
            // --- DRAW BORDER EXCEPT TOP EDGE ---
            g2.setStroke(new BasicStroke(1));
            g2.setColor(Color.BLACK);

            g2.drawLine(0,h,0,tailLength+c);  // Left small edge
            g2.draw(new Arc2D.Double(-c, tailLength-c, 2*c, 2*c, 270, 90, Arc2D.OPEN)); // Draw the arc edge
            g2.drawLine(c, tailLength, c, 0); // Left longer edge
            g2.drawLine(c, 0, w, 0);          // Top line
            
            
           // Glitch fix: these for some reason needs 2px
            g2.setStroke(new BasicStroke(2)); 
            g2.drawLine(w, 0, w, h); // Right side 
            g2.drawLine(w, h, 0, h); // Bottom Side
            
        }
        

        g2.dispose();
    }
}