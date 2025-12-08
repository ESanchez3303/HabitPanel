
package habitpanel;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;

 

public class HabitCard_YesNo extends javax.swing.JPanel {

    // Overriding the paint so that we only paint inside the border
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint rounded background using the panel's background color
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g2.dispose();

        super.paintComponent(g);
    }
    
    // Private Variables: ================================================================================
    private final int MAX_LENGTH = 17;
    private final int COMPLETE_COVER_STEP = 10;
    private boolean isComplete = false;
    private GUI_Window mainGUI = null;
    private String week = "0000000";
    // ===================================================================================================
    
    //  CONSTRUCTOR:       ===============================================================================
    public HabitCard_YesNo(GUI_Window guiInput, String habitNameInput, Color habitColor, boolean completed, String weekInput) {
        initComponents();
        
        // Setting the parent gui so that we can use its methods later
        mainGUI = guiInput;
        
        // Setting up the week
        week = weekInput;
        setWeekText();
        
        // Double checking that name is not too long
        if(habitNameInput.length() > MAX_LENGTH){
            habitName.setText("ERROR: TOO LONG");
            return;
        }
        
        
        // Setting up the card itself (border and color)
        this.setBorder(new RoundedBorder(Color.BLACK, 1, 20));
        this.setOpaque(false);
        this.setBackground(habitColor); // Color that user chose for this habit
        
        
        // Setting up habit name
        habitName.setText(habitNameInput);
        
        
        // Setting up complete cover
        if(completed){
            completeCover.setLocation(0,0);   // Move to the top to show
            habitName.setForeground(Color.WHITE);
            weekText.setForeground(Color.WHITE);
        }else{
            completeCover.setLocation(0,200); // Move to the bottom to hide
            habitName.setForeground(Color.BLACK);
            weekText.setForeground(Color.BLACK);
        }
        isComplete = completed;        
        
        // Repainting Everything
        this.repaint();
    }

    
    // ===================================================================================================
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        habitName = new javax.swing.JLabel();
        weekText = new javax.swing.JLabel();
        completeCover = new javax.swing.JPanel();
        completeCoverText = new javax.swing.JLabel();
        pressToMarkText = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 204));
        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(200, 200));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setPreferredSize(new java.awt.Dimension(200, 200));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        setLayout(null);

        habitName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        habitName.setForeground(java.awt.Color.black);
        habitName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        habitName.setText("kkkkkkkkkkkkkkkkk");
        habitName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        add(habitName);
        habitName.setBounds(5, 10, 190, 110);

        weekText.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        weekText.setForeground(java.awt.Color.black);
        weekText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        weekText.setText("M / T / W / Th / F / S / Su");
        add(weekText);
        weekText.setBounds(0, 175, 200, 20);

        completeCover.setBackground(new java.awt.Color(0, 153, 0));
        completeCover.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        completeCover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        completeCover.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        completeCoverText.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        completeCoverText.setForeground(new java.awt.Color(51, 255, 51));
        completeCoverText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        completeCoverText.setText("COMPLETE!");
        completeCover.add(completeCoverText, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 50));

        add(completeCover);
        completeCover.setBounds(0, 200, 200, 200);

        pressToMarkText.setBackground(new java.awt.Color(153, 153, 255));
        pressToMarkText.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        pressToMarkText.setForeground(java.awt.Color.black);
        pressToMarkText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        pressToMarkText.setText("Press To Mark Completed");
        pressToMarkText.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                habitNameMouseClicked(evt);
            }
        });
        add(pressToMarkText);
        pressToMarkText.setBounds(20, 90, 160, 90);
    }// </editor-fold>//GEN-END:initComponents

    private void habitNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_habitNameMouseClicked
        if(completeCover.getY() == 0){
            mainGUI.resetAway();
            completeCoverAnimation("not completed");
            isComplete = false;
        }
        else if(completeCover.getY() == 200){
            mainGUI.resetAway();
            completeCoverAnimation("completed");
            isComplete = true;
        }
        
        pressToMarkText.repaint();
    }//GEN-LAST:event_habitNameMouseClicked
    
    private void setWeekText(){
        StringBuilder newWeekString = new StringBuilder();
        String[] weekDays = {"M", "T", "W", "Th", "F", "S", "Su"};

        for (int i = 0; i < week.length(); i++) {
            if (week.charAt(i) == '1') {
                newWeekString.append(weekDays[i]).append("/");
            }
        }

        // Remove trailing slash if there is one
        if (newWeekString.length() > 0 && newWeekString.charAt(newWeekString.length() - 1) == '/') {
            newWeekString.deleteCharAt(newWeekString.length() - 1);
        }

        if(newWeekString.length() == 0)
            weekText.setText("ERROR: STRING L=0");
        else
            weekText.setText(newWeekString.toString());
    }

    // ===================================================================================================
    
    
    // ANIMATIONS: ========================================================================================
    private void completeCoverAnimation(String state){
        Timer animation = null;
        if(state.equals("completed")){ // Currently is not completed -> show animation to make complete
            animation = new Timer(20,e->{
                completeCover.setLocation(0,completeCover.getY() - COMPLETE_COVER_STEP);
                
                // Changing color of habit name when we pass it
                if(completeCover.getY() <= (habitName.getY()+50) && habitName.getForeground() != Color.WHITE){
                    habitName.setForeground(Color.WHITE);
                }
                
                // Chaning color of week schedule when we pass it
                if(completeCover.getY() <= weekText.getY() && weekText.getBackground() != Color.WHITE){
                    weekText.setForeground(Color.WHITE);;
                }
                
                // Catch when we reach there 
                if(completeCover.getY() <= 0){
                    ((Timer)e.getSource()).stop();  // Stopping timer
                    completeCover.setLocation(0,0); // Just in case, we are hard setting it to correct location
                }
            });
        }
        else if(state.equals("not completed")){ // Currently is completed -> show animation to make not completed
            animation = new Timer(20,e->{
                completeCover.setLocation(0,completeCover.getY() + COMPLETE_COVER_STEP);
                
                // Chaning color of habit name when we pass it
                if(completeCover.getY() >= (habitName.getY()+50) && habitName.getForeground() != Color.BLACK){
                    habitName.setForeground(Color.BLACK);
                }
                
                // Chaning color of week schedule when we pass it
                if(completeCover.getY() >= weekText.getY() && weekText.getBackground() != Color.BLACK){
                    weekText.setForeground(Color.BLACK);;
                }
                
                // Catch when we reach there 
                if(completeCover.getY() >= 200){
                    ((Timer)e.getSource()).stop();    // Stopping timer
                    completeCover.setLocation(0,200); // Just in case, we are hard setting it to correct location
                }
            });
        }
        
        if(animation != null)
            animation.start();
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
    // ===================================================================================================
    
    

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel completeCover;
    private javax.swing.JLabel completeCoverText;
    private javax.swing.JLabel habitName;
    private javax.swing.JLabel pressToMarkText;
    private javax.swing.JLabel weekText;
    // End of variables declaration//GEN-END:variables
}
