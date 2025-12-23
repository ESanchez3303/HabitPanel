
package habitpanel;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;

 

public class HabitCard_YesNo extends javax.swing.JPanel {
    
    // Private Variables: ================================================================================
    private final int MAX_LENGTH = 17;
    private boolean isComplete = false;
    private GUI_Window mainGUI = null;
    private String week = "0000000";
    private Color habitColor = null;
    private Color ringBackColor = new Color(120,120,120);
    private Color shadowColor = new Color(70,70,70);
    private Color greenColor = new Color(20,255,20);
    private int progessThickness = 10; 
    private int effectThickness = 10;
    private int pressOffset = 0;          // current visual offset
    private int targetPressOffset = 0;    // where we want to go
    private final int MAX_PRESS = effectThickness-1;
    // ===================================================================================================

    
    // Overriding the painting 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // keep children visible
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        
        int newWidth = getWidth() - effectThickness;
        int newHeight = getHeight() - effectThickness;
        int size = Math.min(newWidth, newHeight);
        int outerDiameter = size; 
        int innerDiameter = outerDiameter - progessThickness*2; // thickness of brim
        int outerX = effectThickness/2;
        int outerY = getWidth() - outerDiameter - effectThickness;
        
        // ------------------------------
        // 3D EFFECT
        // ------------------------------
        g2.setColor(shadowColor);
        g2.fillOval(outerX, outerY+effectThickness, outerDiameter, outerDiameter);
        g2.setColor(Color.BLACK);
        g2.drawArc(outerX, outerY+effectThickness-1, outerDiameter, outerDiameter, 0, 360);
        
        
        // ------------------------------
        // BIG RING FILL COLOR (Color if its not green)
        // ------------------------------
        g2.setColor(ringBackColor);  // ring color
        g2.fillOval(outerX, outerY+pressOffset, outerDiameter, outerDiameter);
        
        
        // ------------------------------
        // GREEN RING AROUND 
        // ------------------------------
        
        g2.setStroke(new BasicStroke(progessThickness));
        g2.setColor(greenColor);

        g2.drawArc(outerX+progessThickness/2, 
                outerY+progessThickness/2+pressOffset, 
                outerDiameter-progessThickness, 
                outerDiameter-progessThickness, 90, 
                (isComplete ? 360 : 0));                 // Depending on if isComplete is true/false, we make the circle fully or to "0 degrees"
        
        
        // ------------------------------
        // INNER CIRCLE COLOR BACKGROUND
        // ------------------------------
        g2.setColor(insidePanel.getBackground());
        g2.fillOval((effectThickness/2) + progessThickness, progessThickness+pressOffset, innerDiameter, innerDiameter);
        
        
        // ------------------------------
        // OUTER RING CIRCLE (BORDER)
        // ------------------------------
        //g2.setStroke(new BasicStroke(1));
        //g2.setColor(Color.BLACK);
        //g2.drawArc(outerX, outerY+pressOffset, outerDiameter, outerDiameter, 90, 360);
        
        g2.dispose();
    }
    
    
    //  CONSTRUCTOR:       ===============================================================================
    public HabitCard_YesNo(GUI_Window guiInput, String habitNameInput, Color habitColorInput, boolean completed, String weekInput) {
        initComponents();
        
        // Setting the parent gui so that we can use its methods later
        mainGUI = guiInput;
        habitColor = habitColorInput;
        
        // Setting up the week
        week = weekInput;
        
        //Setting up habit name
        habitName.setText(habitNameInput);
        
        // Double checking that name is not too long
        if(habitNameInput.length() > MAX_LENGTH){
            habitName.setText("ERROR: TOO LONG");
            return;
        }
        
        // Setting up the card itself (border and color)
        this.setOpaque(false);
        
        // Setting up inside panel
        insidePanel.setOpaque(false);
        insidePanel.setBackground(habitColor);
        
        
        
        // Setting up brim using given COMPLETED state
        if(completed){
            habitNameMouseClicked(null);
        }
        else{
            pressToMarkText.setVisible(true);
            checkmarkImage.setVisible(false);
            completeText.setVisible(false);
            isComplete = completed;
        }
      
        
        // Repainting Everything (specifically for calling the paintComponent overrided above)
        this.repaint();
    }

    
    // ===================================================================================================
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        insidePanel = new javax.swing.JPanel();
        habitName = new javax.swing.JLabel();
        pressToMarkText = new javax.swing.JLabel();
        checkmarkImage = new javax.swing.JLabel();
        completeText = new javax.swing.JLabel();

        setBackground(new java.awt.Color(102, 102, 102));
        setMaximumSize(new java.awt.Dimension(200, 200));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setPreferredSize(new java.awt.Dimension(200, 200));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        setLayout(null);

        insidePanel.setBackground(new java.awt.Color(153, 255, 153));
        insidePanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        insidePanel.setLayout(null);

        habitName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        habitName.setForeground(java.awt.Color.black);
        habitName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        habitName.setText("kkkkkkkkkkkkkkkkk");
        habitName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        insidePanel.add(habitName);
        habitName.setBounds(0, 60, 170, 30);

        pressToMarkText.setBackground(new java.awt.Color(153, 153, 255));
        pressToMarkText.setFont(new java.awt.Font("Segoe UI", 3, 10)); // NOI18N
        pressToMarkText.setForeground(java.awt.Color.black);
        pressToMarkText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pressToMarkText.setText("Press To Mark Completed");
        pressToMarkText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        insidePanel.add(pressToMarkText);
        pressToMarkText.setBounds(0, 95, 170, 20);

        add(insidePanel);
        insidePanel.setBounds(15, 10, 170, 170);

        checkmarkImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/checkmarkIcon.png"))); // NOI18N
        add(checkmarkImage);
        checkmarkImage.setBounds(70, 110, 60, 60);

        completeText.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        completeText.setForeground(new java.awt.Color(181, 230, 29));
        completeText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        completeText.setText("COMPLETE");
        add(completeText);
        completeText.setBounds(25, 40, 150, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void habitNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_habitNameMouseClicked
        // Current state is "COMPLTED" -> moving to "NOT COMPLETED"
        if(!pressToMarkText.isVisible()){
            // If the press timer is current running, wait until its finished
            if(pressTimer != null && pressTimer.isRunning())
                return;
            animatePress(false);
            
            // Reseting the away timer
            mainGUI.reseAwaytAway();
            
            // Chaning visuasl
            pressToMarkText.setVisible(true);
            checkmarkImage.setVisible(false);
            completeText.setVisible(false);
            
            // Changing variables
            isComplete = false;
        }
        
        // Current state is "NOT COMPLETED" -> moving to "COMPLETED"
        else{
            // If the press timer is current running, wait until its finished
            if(pressTimer != null && pressTimer.isRunning())
                return;
            animatePress(true);
            
            // Resting the away timer
            mainGUI.reseAwaytAway();
            
            // Changing visuals
            pressToMarkText.setVisible(false);
            checkmarkImage.setVisible(true);
            completeText.setVisible(true);
            
            // Changing variables
            isComplete = true;
        }
        
        // Repaiting the whole panel which should call the paintComponent, i hopeeeee
        this.repaint();
    }//GEN-LAST:event_habitNameMouseClicked
    

    // ===================================================================================================
    private Timer pressTimer;
    private void animatePress(boolean pressIn) {
        targetPressOffset = pressIn ? MAX_PRESS : 0;

        if (pressTimer != null && pressTimer.isRunning()) 
            return;

        pressTimer = new Timer(10, e -> {
            if (pressOffset < targetPressOffset){
                habitName.setLocation(habitName.getX(),habitName.getY()+1);
                pressToMarkText.setLocation(pressToMarkText.getX(),pressToMarkText.getY()+1);
                checkmarkImage.setLocation(checkmarkImage.getX(), checkmarkImage.getY()+1);
                completeText.setLocation(completeText.getX(), completeText.getY()+1);
                pressOffset++;
            }
            else if (pressOffset > targetPressOffset) {
                habitName.setLocation(habitName.getX(),habitName.getY()-1);
                pressToMarkText.setLocation(pressToMarkText.getX(),pressToMarkText.getY()-1);
                checkmarkImage.setLocation(checkmarkImage.getX(), checkmarkImage.getY()-1);
                completeText.setLocation(completeText.getX(), completeText.getY()-1);
                pressOffset--;
            }
            else 
                ((Timer) e.getSource()).stop();
            repaint();
        });
        
        pressTimer.start();
    }
    
    
    
    
    // ===================================================================================================
    
    // Public Functions : ============================================================================
    // Getting is Complete bool
    public boolean isComplete(){
        return isComplete;
    }
    
    // Geting Name
    public String getHabitName(){
        return habitName.getText();
    }
    
    public String getWeek(){
        return week;
    }
    
    public boolean isForToday(String day){
        if(day.equals("Monday"))    return (week.charAt(0) == '1');
        if(day.equals("Tuesday"))   return (week.charAt(1) == '1');
        if(day.equals("Wednesday")) return (week.charAt(2) == '1');
        if(day.equals("Thursday"))  return (week.charAt(3) == '1');
        if(day.equals("Friday"))    return (week.charAt(4) == '1');
        if(day.equals("Saturday"))  return (week.charAt(5) == '1');
        if(day.equals("Sunday"))    return (week.charAt(6) == '1');
        
        JOptionPane.showMessageDialog(mainGUI, "ERROR: " + habitName + " was not able to generate in isForToday() function.");
        return true;
    }
    
    
    public void setComplete(boolean state){
        isComplete = state;
    }
    
    public Color getColor(){
        return habitColor;
    }
    // ===================================================================================================
    
    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel checkmarkImage;
    private javax.swing.JLabel completeText;
    private javax.swing.JLabel habitName;
    private javax.swing.JPanel insidePanel;
    private javax.swing.JLabel pressToMarkText;
    // End of variables declaration//GEN-END:variables
}
