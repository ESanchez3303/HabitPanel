package habitpanel;

import java.awt.*;
import javax.swing.*;

public class CirclePanel extends JPanel {

    CirclePanel(){setOpaque(false);}
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillOval(x, y, diameter, diameter);

        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = Math.min(getWidth(), getHeight()) / 2;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= radius * radius;
    }
}