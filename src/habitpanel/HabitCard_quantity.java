package habitpanel;
import java.awt.*;

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
    private int quantity = 0; // This is what this card takes care of (not a boolean)
    private int increment = 0;
    private GUI_Window mainGUI = null;
    // ===================================================================================================
    
    
    public HabitCard_quantity(GUI_Window guiInput, String habitNameInput, Color habitColor, int quantityInput, int incrementInput) {
        initComponents();
        
        // Saving the parent gui to use its methods later
        mainGUI = guiInput;
        
        // Double checking that name is not too long
        if(habitNameInput.length() > MAX_LENGTH){
            habitName.setText("ERROR: TOO LONG");
            return;
        }
        
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
        
        add(minusButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 125, 50, 50));  // Re-adding the button
        add(plusButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 125, 50, 50)); // Re-adding the button
        
        // Setting the quantiy
        quantityText.setText(Integer.toString(quantityInput));
        quantity = quantityInput;
        increment = incrementInput;
        
        // Repainting Everything
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        habitName = new javax.swing.JLabel();
        minusButton = new javax.swing.JButton();
        plusButton = new javax.swing.JButton();
        quantityText = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(200, 200));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(200, 200));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        habitName.setBackground(new java.awt.Color(0, 204, 255));
        habitName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        habitName.setForeground(java.awt.Color.black);
        habitName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        habitName.setText("kkkkkkkkkkkkkkkkk");
        add(habitName, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 10, 190, 110));

        minusButton.setBackground(new java.awt.Color(153, 153, 153));
        minusButton.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        minusButton.setForeground(java.awt.Color.white);
        minusButton.setText("-");
        minusButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        add(minusButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 125, 50, 50));

        plusButton.setBackground(new java.awt.Color(153, 153, 153));
        plusButton.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        plusButton.setForeground(java.awt.Color.white);
        plusButton.setText("+");
        plusButton.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        add(plusButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(145, 125, 50, 50));

        quantityText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quantityText.setForeground(java.awt.Color.black);
        quantityText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quantityText.setText("99999");
        add(quantityText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 80, 60));
    }// </editor-fold>//GEN-END:initComponents

    
    
    // CLICKS OF THE BUTTONS: =======================================================================
    
    private void minusClicked() {
        mainGUI.resetAway(); // Resetting the away from screen timer
        quantity -= increment; // Decrementing the quantity by saved increment
        if(quantity < 0)       // Bounding to never go negative
            quantity = 0; 
        quantityText.setText(String.valueOf(quantity)); // Showing new value
    }

    private void plusClicked() {
        mainGUI.resetAway();  // Reseting the away from screen timer
        quantity += increment;
        quantityText.setText(String.valueOf(quantity));
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
    
    // ===================================================================================================
    
    // Public Functions : ============================================================================
    // Getting is quantity
    public int getQuantity(){
        return quantity;
    }
    
    // Getting increment
    public int getIncrement(){
        return increment;
    }
    
    // Geting Name
    public String getHabitName(){
        return habitName.getText();
    }
    
    
    // ===================================================================================================

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel habitName;
    private javax.swing.JButton minusButton;
    private javax.swing.JButton plusButton;
    private javax.swing.JLabel quantityText;
    // End of variables declaration//GEN-END:variables
}
