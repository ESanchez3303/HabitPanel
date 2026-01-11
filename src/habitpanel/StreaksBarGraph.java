package habitpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

public class StreaksBarGraph extends JPanel{
    
    // Holding variables
    Map<String, Integer> data = null;
    
    public StreaksBarGraph(int x, int y, int w, int h) {
        setOpaque(false);
        setLayout(null);
        setBounds(x,y,w,h);
    }
    
    public void setStreaks(Map<String, Integer> dataInput){
        data = dataInput;
        repaint();
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty())
            return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        // ----- Constants -----
        int padding = 15;
        int rowHeight = 30;
        int barHeight = 16;

        int nameWidth = 140;
        int valueWidth = 50;

        int barStartX = padding + nameWidth;
        int barEndX = getWidth() - padding - valueWidth;
        int maxBarWidth = barEndX - barStartX;

        // ----- Sort data (descending) -----
        List<Map.Entry<String, Integer>> entries =
                new ArrayList<>(data.entrySet());

        entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));

        int maxStreak = entries.get(0).getValue();

        // ----- Fonts -----
        Font nameFont = new Font("Segoe UI", Font.BOLD, 13);
        Font valueFont = new Font("Segoe UI", Font.BOLD, 13);
        
        // Setting the spacing to move to the middle, and the max rows 
        int maxRows = (getHeight() - padding * 2) / rowHeight;
        
        int numberOfBars = Math.min(entries.size(), maxRows);
        int totalBarsHeight = numberOfBars * rowHeight;
        int topOffset = (getHeight() - totalBarsHeight) / 2;
        int y = topOffset;
        
        
        
        for (int i = 0; i < entries.size() && i < maxRows; i++) {
            Map.Entry<String, Integer> entry = entries.get(i);
            
            String habit = entry.getKey();
            int streak = entry.getValue();

            // Vertical centering
            int centerY = y + rowHeight / 2;

            // ----- Habit name -----
            g2.setFont(nameFont);
            g2.setColor(getBackground());
            g2.drawString(habit, padding, centerY + 5);

            // ----- Bar -----
            double percent = streak / (double) maxStreak;
            int barWidth = (int) (maxBarWidth * percent);

            g2.setColor(getBackground());
            g2.fillRoundRect(
                    barStartX,
                    centerY - barHeight / 2,
                    barWidth,
                    barHeight,
                    barHeight,
                    barHeight
            );

            // ----- Count text -----
            g2.setFont(valueFont);
            g2.setColor(getBackground());
            g2.drawString(
                    "x" + streak,
                    barEndX + 10,
                    centerY + 5
            );

            y += rowHeight;
        }

        g2.dispose();
    }
}
