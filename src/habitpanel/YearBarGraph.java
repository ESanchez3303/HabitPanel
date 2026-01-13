package habitpanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;



public class YearBarGraph extends JPanel {
    // Variables:
    JLabel monthLabel = null;
    LocalDate currentDate = LocalDate.now();
    private double[] monthProgress = null;
    
    // CONSTRUCTOR: ===================================
    public YearBarGraph(int x, int y, int w, int h) {
        setLayout(null);
        setOpaque(false);
        setBounds(x, y, w, h);
        

        // Month title
        monthLabel = new JLabel("<Month>", JLabel.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 25));
        monthLabel.setBounds(0, 0, w, 32);
        monthLabel.setForeground(getBackground());

        add(monthLabel);
    }
    // ================================================
    
    // Takes in an array that will already be built with the amount of days that there are in this month 
    public void updateData(double[] newData){
        
        // Load Data
        monthProgress = newData;
                
        // Setting month title text
        currentDate = LocalDate.now();
        String monthText = currentDate.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
        monthLabel.setText(monthText + " Progress");
        
        revalidate();
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if(monthProgress == null || monthProgress.length < 28 || currentDate == null){
            return;
        }
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Constants:
        int dayCount = monthProgress.length;
        int padding = 5;
        int h = getHeight();
        int w = getWidth();
        int dateTextW = (w-padding*2)/dayCount;
        int dateTextH = 20;
        
        
        // -- DRAW DAY NUMBERS --
        g2.setColor(getBackground());
        FontMetrics fm = g2.getFontMetrics(); // weiiiiiird but useful to know 
        Font originalFont = g2.getFont();
        Font boldFont = originalFont.deriveFont(Font.BOLD);
        int today = currentDate.getDayOfMonth();
        
        for (int i = 1; i <= dayCount; i++) {
            String dayText = Integer.toString(i);
            int textWidth = fm.stringWidth(dayText);
            int slotX = padding + (i - 1) * dateTextW;
            int textX = slotX + (dateTextW - textWidth) / 2;
            int textY = h - padding; 
            
            // Drawing date and BOLD-ing the current date
            if (i == today) {
                g2.setFont(boldFont);
                g2.drawString(dayText, textX, textY);

                // draw underline
                g2.drawLine(textX, textY + 2, textX + textWidth, textY + 2);
            } else {
                g2.setFont(originalFont);
                g2.drawString(dayText, textX, textY);
            }
        }
        
        
        
        // -- DRAW BAR GRAPHS --
        int barPadding = 3; // horizontal padding inside each day slot (to not overlap goof)
        int titleHeight = monthLabel.getHeight();
        int barTopMargin = 5;
        int maxBarHeight = h - padding - dateTextH - 5 - titleHeight - barTopMargin;

        for (int i = 0; i < currentDate.getDayOfMonth(); i++) {
            double dayValue = monthProgress[i];

            int barX = padding + i * dateTextW + barPadding; // starting within the day slot that is saved for this day
            int barWidth = dateTextW - 2 * barPadding;       // width of the bar
            int barBottomY = h - padding - dateTextH;        // y of bottom line

            if (dayValue == -1) {
                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(barX, barBottomY - maxBarHeight, barWidth, maxBarHeight);
            } 
            else {
                g2.setColor(getBackground());
                int barHeight = (int)(dayValue * maxBarHeight);
                g2.fillRect(barX, barBottomY - barHeight, barWidth, barHeight);
            }
        }
        
        // -- DRAW BOTTOM LINE --
        g2.setColor(getBackground());
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(padding, h-padding-dateTextH, w-padding, h-padding-dateTextH);
        
        
        // -- DRAW MAP FOR THE BAR GRAPH --
        int mapTextW = 200;
        
        // Grayed out box for no habit days
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(w-mapTextW-padding*2, 0, titleHeight, titleHeight/2);
        
        // Background color box for progressed days
        g2.setColor(getBackground());
        g2.fillRect(w-mapTextW-padding*2, titleHeight/2+padding/2, titleHeight, titleHeight/2);
        
        // Borders around both 
        /*
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(w-mapTextW-padding*2, 0, titleHeight, titleHeight/2);
        g2.drawRect(w-mapTextW-padding*2, titleHeight/2+padding/2, titleHeight, titleHeight/2);
        */
        
        // Labels for both
        int labelsX = w-mapTextW-padding*2 + titleHeight + padding*2;
        g2.setColor(getBackground());
        g2.setFont(new Font("Segoe UI", 1, 12));
        g2.drawString("No Habits Scheduled", labelsX, titleHeight/2-padding/2);
        g2.drawString("Habit Completion", labelsX, titleHeight);
        
        g2.dispose();
    }
    
}
