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
    int paddingX = 0;
    int paddingY = 0;
    
    
    NavigationButton(Type typeInput, int x, int y, int w, int h){
        type = typeInput;
        setOpaque(false); 
        setLayout(null);
        setBounds(x,y,w,h);
        paddingX = (h-w)/2;
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        Path2D shape = new Path2D.Double();
        
        int h = getHeight();
        int w = getWidth();
        int boxW = w;
        int boxH = w;
        paddingX = 0;
        paddingY = (h-w)/2;
        int lineThickness = 5;
        Color lineColor = Color.WHITE;
        g2.setColor(lineColor);
        
        if(paddingX + boxW > w ||
           paddingY + boxH > h){
            System.out.println("Error: Drawing box is out of boudns");
            g2.dispose();
            return;
        }
        
                    
        if(null != type)
            switch (type) {
                case SETTINGS -> {

                }
                case ADD -> {
                    int linePadding = 10;
                    g2.setStroke(new BasicStroke(lineThickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                    // -- Vertical Line --
                    g2.drawLine(paddingX+(boxW/2), paddingY+linePadding, paddingX+(boxW/2), paddingY+boxH-linePadding);
                    // -- Horizontal Line --
                    g2.drawLine(paddingX+linePadding, (boxH/2)+paddingY, paddingX+boxW-linePadding, (boxH/2)+paddingY);
                }
                case HOME -> {

                }
                case EDIT -> {

                }
                case PROGRESS -> {

                }
            }
        
        
        g2.dispose();
    }
    
}
