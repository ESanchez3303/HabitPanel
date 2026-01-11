package habitpanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;


public class YearBarGraph extends JPanel {
    
    // CONSTRUCTOR: ===================================
    public YearBarGraph() {
        
    }
    // ================================================
    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        
        g2.dispose();
    }
    
}
