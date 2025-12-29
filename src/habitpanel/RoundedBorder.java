package habitpanel;
import java.awt.*;
import javax.swing.border.LineBorder;


public class RoundedBorder extends LineBorder{
    public RoundedBorder(Color color, int thickness, int radius) {
        super(color, thickness, true);
        this.arc = radius;
    }

    private final int arc;

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int r = arc;
        int t = thickness;

        g2.setColor(lineColor);
        g2.setStroke(new BasicStroke(t));
        g2.drawRoundRect(x + t/2, y + t/2, width - t, height - t, r, r);
        
        g2.dispose();
    }
}
