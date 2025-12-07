package habitpanel;

import java.awt.*;
import javax.swing.JButton;


public class RoundButton extends JButton {
    private final int diameter;

    public RoundButton(String text, int diameter) {
        super(text);
        this.diameter = diameter;
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new RoundedBorder(Color.BLACK, 1, diameter));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Amount to shrink when pressed
        int shrink = getModel().isPressed() ? 4 : 0; // shrink by 4 pixels on each side

        // Paint circular background with shrink offset
        g2.setColor(getBackground());
        g2.fillOval(shrink, shrink, getWidth() - 2*shrink, getHeight() - 2*shrink);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(diameter, diameter);
    }
}
