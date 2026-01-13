package habitpanel;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StreaksBarGraph extends JPanel{
    // CONSTANTS:
    int padding = 15;
    int rowHeight = 30;
    int barHeight = 16;
    int nameWidth = 140;
    int valueWidth = 50;
    
    // Holding variables
    Timer cycleTimer = null;
    List<Map.Entry<String, Integer>> entries = null;
    List<Map.Entry<String, Integer>> entriesCopy = null;
    private int nextShowingIndex = 0;
    private int maxStreak = 0;
    private int maxRows = 7; 
    
    public StreaksBarGraph(int x, int y, int w, int h) {
        setOpaque(false);
        setLayout(null);
        setBounds(x,y,w,h);
    }
    
    public void stopClock(){
        if(cycleTimer != null){
            if(cycleTimer.isRunning())
                cycleTimer.stop();
        }
        cycleTimer = null;
    }
    
    public void startCycleTimer(){
        // Settting up to show the frist (7) entries
        nextShowingIndex = 0;
        entriesCopy = new ArrayList<>(entries);
        
        // Doing the first timer tick one time to populate information quickly instead of waiting the timer tick amount
        entries.clear();
        
        
        int end = Math.min(nextShowingIndex + maxRows, entriesCopy.size());
        for(int currentIndex = nextShowingIndex; currentIndex < end; currentIndex++){
            entries.add(entriesCopy.get(currentIndex)); // Adding this entriy to the entry set that will be used in painting
        }
        // Finding out the next starting index
        nextShowingIndex = end;
        if (nextShowingIndex >= entriesCopy.size()) 
            nextShowingIndex = 0;
        
        repaint(); // Repaints which is whats going to paint the new entries list
        
        cycleTimer = new Timer(1000,e->{
            entries.clear();
            
            // Adding the entries from the copy to teh entries 
            int end1 = Math.min(nextShowingIndex + maxRows, entriesCopy.size());
            for(int currentIndex = nextShowingIndex; currentIndex < end1; currentIndex++){
                entries.add(entriesCopy.get(currentIndex)); // Adding this entriy to the entry set that will be used in painting
            }
            
            // Finding out the next starting index
            nextShowingIndex = end1;
            if (nextShowingIndex >= entriesCopy.size()) 
                nextShowingIndex = 0;
            
            repaint(); // Repaints which is whats going to paint the new entries list
        });
        
        cycleTimer.start();
    }
    
    public void setStreaks(List<Map.Entry<String, Integer>>  entriesInput){
        // Reset Variables
        stopClock();
        entriesCopy = new ArrayList<>(entriesInput);
        
        
        // Making entries set and sorting and finding out the maxStreak
        maxStreak = entries.get(0).getValue();             // Saving the max streak so that we can use it later
        maxRows = (getHeight() - padding * 2) / rowHeight; // Setting the spacing to move to the middle, and the max rows 
        
        
        // If we have move habits than will be able to fit, we need to cycle the painting
        if(entries.size() > maxRows){
            startCycleTimer();
        }
        else{
            repaint();
        }
    }
    
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (entries == null || entries.isEmpty())
            return;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        
        // ----- Variables -----
        int barStartX = padding + nameWidth;
        int barEndX = getWidth() - padding - valueWidth;
        int maxBarWidth = barEndX - barStartX;


        // ----- Fonts -----
        Font nameFont = new Font("Segoe UI", Font.BOLD, 13);
        Font valueFont = new Font("Segoe UI", Font.BOLD, 13);
        
        
        
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
