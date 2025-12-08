package habitpanel;
import java.awt.*;
import javax.swing.Timer;

public class HabitCard_quantity extends javax.swing.JPanel {

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
    private double quantity = 0; // This is what this card takes care of (not a boolean)
    private double goal = 0;
    private double increment = 0;
    private GUI_Window mainGUI = null;
    private final int COMPLETE_COVER_STEP = 10;
    private Color habitColor = null;
    private Color plusMinusCompleteCover = new Color(51,255,51);
    // ===================================================================================================
    
    
    public HabitCard_quantity(GUI_Window guiInput, String habitNameInput, Color habitColorInput, double quantityInput, double goalInput, double incrementInput) {
        initComponents();
        
        // Saving the parent gui to use its methods later
        mainGUI = guiInput;
        
        // Double checking that name is not too long
        if(habitNameInput.length() > MAX_LENGTH){
            habitName.setText("ERROR: TOO LONG");
            return;
        }
        
        // Setting up the variables:
        habitColor = habitColorInput;
        quantity = quantityInput;
        goal = goalInput;
        increment = incrementInput;
        
        
        // Setting text displays
        quantityText.setText(Double.toString(quantityInput));
        goalText.setText("Goal: " + goal);
        
        // Setting up the card itself (this panel)
        this.setBorder(new RoundedBorder(Color.BLACK, 1, 20));
        this.setOpaque(false);
        this.setBackground(habitColor); // Color that user chose for this habit
        
        // Setting up habit name
        habitName.setText(habitNameInput); // Setting the habit name
        
        // Setting up buttons
        remove(minusButton);    // Removing temp GUI buttons
        remove(plusButton);     // Removing temp GUI buttons
        
        plusButton = new RoundButton("+", 50);  // Adding dynamic button using round features
        minusButton = new RoundButton("-", 50); // Adding dynamic button using round features

        plusButton.setBackground(darkenColor(habitColor));  // Setting the background of the button using the given habitColor
        minusButton.setBackground(darkenColor(habitColor)); // Setting the background of the button using the given habitColor
        plusButton.setForeground(Color.BLACK);               // Setting the text color of the button
        minusButton.setForeground(Color.BLACK);              // Setting the text color of the button
        
        minusButton.addActionListener(e->minusClicked());    // Adding listeners for button click
        plusButton.addActionListener(e->plusClicked());      // Adding listeners for button click
        
        minusButton.setBounds(10, 125, 50, 50);
        plusButton.setBounds(140, 125, 50, 50);
        

        add(minusButton);
        add(plusButton);
        
        // Setting up the cover panel
        if(quantity >= goal){
            completeCover.setLocation(0,0);
            habitName.setForeground(Color.WHITE);
            goalText.setForeground(Color.WHITE);
            plusButton.setBackground(plusMinusCompleteCover);
            minusButton.setBackground(plusMinusCompleteCover);
        }
        else{
            completeCover.setLocation(0,200);
            habitName.setForeground(Color.BLACK);
            goalText.setForeground(Color.BLACK);
            plusButton.setBackground(darkenColor(habitColor));
            minusButton.setBackground(darkenColor(habitColor));
        }
        this.setComponentZOrder(completeCover, 5);
        
        // Repainting Everything
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        minusButton = new javax.swing.JButton();
        plusButton = new javax.swing.JButton();
        habitName = new javax.swing.JLabel();
        quantityText = new javax.swing.JLabel();
        goalText = new javax.swing.JLabel();
        completeCover = new javax.swing.JPanel();
        completeCoverText = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(200, 200));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(200, 200));
        setLayout(null);

        minusButton.setBackground(new java.awt.Color(153, 153, 153));
        minusButton.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        minusButton.setForeground(java.awt.Color.white);
        minusButton.setText("-");
        minusButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(minusButton);
        minusButton.setBounds(5, 125, 50, 50);

        plusButton.setBackground(new java.awt.Color(153, 153, 153));
        plusButton.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        plusButton.setForeground(java.awt.Color.white);
        plusButton.setText("+");
        plusButton.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        add(plusButton);
        plusButton.setBounds(145, 125, 50, 50);

        habitName.setBackground(new java.awt.Color(0, 204, 255));
        habitName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        habitName.setForeground(java.awt.Color.black);
        habitName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        habitName.setText("kkkkkkkkkkkkkkkkk");
        add(habitName);
        habitName.setBounds(5, 10, 190, 110);

        quantityText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quantityText.setForeground(java.awt.Color.black);
        quantityText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quantityText.setText("99999");
        add(quantityText);
        quantityText.setBounds(60, 120, 80, 60);

        goalText.setForeground(java.awt.Color.black);
        goalText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goalText.setText("Goal:");
        add(goalText);
        goalText.setBounds(50, 100, 100, 16);

        completeCover.setBackground(new java.awt.Color(0, 153, 0));
        completeCover.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        completeCover.setPreferredSize(new java.awt.Dimension(200, 200));
        completeCover.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        completeCoverText.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        completeCoverText.setForeground(new java.awt.Color(51, 255, 51));
        completeCoverText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        completeCoverText.setText("COMPLETE!");
        completeCover.add(completeCoverText, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 50));

        add(completeCover);
        completeCover.setBounds(0, 200, 200, 200);
    }// </editor-fold>//GEN-END:initComponents

    
    
    // CLICKS OF THE BUTTONS: =======================================================================
    
    private void minusClicked() {
        // Making sure we dont click while we are in animation
        if(completeCover.getY() != 200 && completeCover.getY() != 0)
            return;
        
        mainGUI.resetAway(); // Resetting the away from screen timer
        quantity -= increment;
        if(quantity < 0)       // Bounding to never go negative
            quantity = 0; 
        quantityText.setText(String.valueOf(quantity)); // Showing new value
        
        // Checking if this value puts under the goal
        if(quantity < goal && completeCover.getY() != 200){
            moveCompleteCover("not completed");
        }
    }

    private void plusClicked() {
        // Making sure we dont click while we are in animation
        if(completeCover.getY() != 200 && completeCover.getY() != 0)
            return;
        
        mainGUI.resetAway();  // Reseting the away from screen timer
        quantity += increment;
        quantityText.setText(String.valueOf(quantity));
        
        // Checking if this value puts over or equal to goal
        if(quantity >= goal && completeCover.getY() != 0){
            moveCompleteCover("completed");
        }
    }
    
    // ===================================================================================================
    
    
    // Helper Functions: =============================================================================
    private Color darkenColor(Color color) {
        double factor = 0.8; // USE THIS FACTOR 0=darker | 1=lighter
        
        int r = Math.max((int)(color.getRed() * factor), 0);
        int g = Math.max((int)(color.getGreen() * factor), 0);
        int b = Math.max((int)(color.getBlue() * factor), 0);
        
        return new Color(r, g, b);
    }
    
    public void moveCompleteCover(String state){
        Timer animation = null;
        if(state.equals("completed")){ // Currently is not completed -> show animation to make complete
            animation = new Timer(20,e->{
                completeCover.setLocation(0,completeCover.getY() - COMPLETE_COVER_STEP);
                
                // Changing color of habit name when we pass it
                if(completeCover.getY() <= (habitName.getY()+50) && habitName.getForeground() != Color.WHITE){
                    habitName.setForeground(Color.WHITE);
                }
                
                // Changing color of the quantityText when we pass it
                if(completeCover.getY() <= (quantityText.getY()+10) && quantityText.getForeground() != Color.WHITE){
                    quantityText.setForeground(Color.WHITE);
                }
                
                // Changing color of the buttons when we pass it
                if(completeCover.getY() <= (plusButton.getY()-10) && completeCover.getBackground() != plusMinusCompleteCover){
                    plusButton.setBackground(plusMinusCompleteCover);
                    minusButton.setBackground(plusMinusCompleteCover);
                }
                
                // Changing the color of the goal text
                if(completeCover.getY() <= (goalText.getY()) && goalText.getForeground() != Color.WHITE){
                    goalText.setForeground(Color.WHITE);
                }
                
                
                // Catching when to stop
                if(completeCover.getY() <= 0){
                    ((Timer)e.getSource()).stop();       // Stopping timer
                    completeCover.setLocation(0,0);      // Just in case, we are hard setting it to correct location
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
                
                // Changing color of the quantityText when we pass it
                if(completeCover.getY() >= (quantityText.getY()) && quantityText.getForeground() != Color.BLACK){
                    quantityText.setForeground(Color.BLACK);
                }
                
                // Changing color of the buttons when we pass it
                if(completeCover.getY() >= (plusButton.getY()) && completeCover.getBackground() != darkenColor(habitColor)){
                    plusButton.setBackground(darkenColor(habitColor));
                    minusButton.setBackground(darkenColor(habitColor));
                }
                
                // Changing the color of the goal text
                if(completeCover.getY() >= (goalText.getY()) && goalText.getForeground() != Color.BLACK){
                    goalText.setForeground(Color.BLACK);
                }
                
                
                // Catching when to stop
                if(completeCover.getY() >= 200){
                    ((Timer)e.getSource()).stop();        // Stopping timer
                    completeCover.setLocation(0,200);     // Just in case, we are hard setting it to correct location
                }
            });
        }
        
        if(animation != null)
            animation.start();
    }
    
    
    
    // ===================================================================================================
    
    // Public Functions : ============================================================================
    // Getting is quantity
    public double getQuantity(){
        return quantity;
    }
    
    public boolean isComplete(){
        return quantity >= goal;
    }
    
    
    // Geting Name
    public String getHabitName(){
        return habitName.getText();
    }
    
    
    // ===================================================================================================

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel completeCover;
    private javax.swing.JLabel completeCoverText;
    private javax.swing.JLabel goalText;
    private javax.swing.JLabel habitName;
    private javax.swing.JButton minusButton;
    private javax.swing.JButton plusButton;
    private javax.swing.JLabel quantityText;
    // End of variables declaration//GEN-END:variables
}
