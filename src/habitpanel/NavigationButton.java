package habitpanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.BasicStroke;


public class NavigationButton extends JPanel{
    public enum Type { SETTINGS, ADD, HOME, EDIT, PROGRESS }
    private final Type type;
    
    
    NavigationButton(Type typeInput, int x, int y, int w, int h){
        type = typeInput;
        setOpaque(false); 
        setLayout(null);
        setBounds(x,y,w,h);
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        int h = getHeight();
        int w = getWidth();
        int boxX = 15;
        int boxY = (h-w)/2 + 15;
        int boxW = w-boxX*2;
        int boxH = h-boxY*2;
        int lineThickness = 5;
        
        g2.setColor(getForeground());
        g2.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        
        if(boxX*2 + boxW > w ||
           boxY + boxH > h){
            System.out.println("Error: Drawing box is out of boudns");
            g2.dispose();
            return;
        }
        
                    
        if(null != type)
            switch (type) {
                case SETTINGS -> {
                    // -- Straight Up/Down/Left/Right Gears --
                    g2.drawLine(boxX+(boxW/2), boxY, boxX+(boxW/2), boxY+boxH);
                    g2.drawLine(boxX, boxY+(boxH/2), boxX+boxW, boxY+(boxH/2)); 
                    // -- Diagonal Gears --
                    g2.drawLine(boxX, boxY, boxX+boxH, boxY+boxW);
                    g2.drawLine(boxX, boxY+boxH, boxX+boxW, boxY);
                    // -- Center Bigger Foreground Circle --
                    int biggerRadius = 22;
                    g2.fillOval(boxX+(boxW/2)-biggerRadius/2, boxY+(boxH/2)-biggerRadius/2, biggerRadius, biggerRadius);
                    // -- Center Smaller Background Circle --
                    g2.setColor(getBackground());
                    g2.setStroke(new BasicStroke(lineThickness+2));
                    int smallRadius = 12;
                    g2.fillOval(boxX+(boxW/2)-smallRadius/2, boxY+(boxH/2)-smallRadius/2, smallRadius, smallRadius);
                    // -- Cutting Circle Around in Background Color --
                    g2.drawOval(boxX-lineThickness, boxY-lineThickness, boxH+lineThickness*2, boxW+lineThickness*2);
                    
                }
                case ADD -> {
                    // -- Vertical Line --
                    g2.drawLine(boxX+(boxW/2), boxY, boxX+(boxW/2), boxY+boxH);
                    // -- Horizontal Line --
                    g2.drawLine(boxX, boxY+(boxH/2), boxX+boxW, boxY+(boxH/2)); 
                }
                case HOME -> {
                    boxX -= 5;
                    boxW += 10;
                    // -- Rectangle --
                    g2.drawRoundRect(boxX, boxY , boxW, boxH, 5, 5);
                    // -- Dots inside --
                    int spacing = 5;
                    int dotD = 2;
                    g2.drawOval(w/2-spacing*2, h/2-spacing, dotD, dotD);
                    g2.drawOval(w/2+spacing*2, h/2-spacing, dotD, dotD);
                    g2.drawOval(w/2-spacing*2, h/2+spacing, dotD, dotD);
                    g2.drawOval(w/2+spacing*2, h/2+spacing, dotD, dotD);
                    g2.drawOval(w/2, h/2-spacing, dotD, dotD);
                    g2.drawOval(w/2, h/2+spacing, dotD, dotD);
                    
                    
                }
                case EDIT -> {
                    Path2D shape = new Path2D.Double();
                }
                case PROGRESS -> {
                    // -- Outside Box -- 
                    g2.drawRoundRect(boxX, boxY, boxW, boxH, 5, 5);
                    // -- Top Rectangle --
                    g2.drawRect(boxX, boxY, boxW, 10);
                    // -- Date Secition --
                    int spacingH = 3;
                    int spacingV = 5;
                    int currentY = boxY+spacingH*2+10;
                    int currentX = boxX+spacingH*2+2;
                    g2.setStroke(new BasicStroke(1));
                    for(int i = 0; i < 4; i++, currentY+=spacingH, currentX+=spacingV){
                        g2.drawLine(boxX, currentY, boxX+boxW, currentY);
                        g2.drawLine(currentX, boxY+10, currentX, boxY+boxH);
                    }
                    
                    
                }
            }
        
        
        g2.dispose();
    }
    
}
