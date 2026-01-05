package habitpanel;
import java.awt.*;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


class QuantityEntry {
    private boolean completed;
    private double reached;
    private double goal;

    public QuantityEntry(double reached, double goal) {
        this.completed = (reached >= goal);
        this.reached = reached;
        this.goal = goal;
    }

    // Getters
    public boolean getCompleted() { return completed; }
    public double getReached() { return reached; }
    public double getGoal() { return goal; }

    // Setters (if you want to update)
    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setReached(double reached) { this.reached = reached; completed = (reached >= goal); }
    public void setGoal(double goal) { this.goal = goal; }
}




public class HabitCard_Quantity extends javax.swing.JPanel {
    // Private Variables: ================================================================================
    private final int MAX_LENGTH = 17;
    private double quantity = 0; // This is what this card takes care of (not a boolean)
    private double goal = 0;
    private double increment = 0;
    private GUI_Window mainGUI = null;
    private Color habitColor = null;
    private String week = "0000000";
    private Color ringBackColor = new Color(102,102,102);
    private Color shadowColor = new Color(70,70,70);
    private Color greenColor = new Color(20,255,20);
    private int progessThickness = 10; 
    private int effectThickness = 10; 
    private int pressOffset = 0;          // current visual offset
    private int targetPressOffset = 0;    // where we want to go
    private final int MAX_PRESS = effectThickness-1;
    private Map<LocalDate, QuantityEntry> completionMap = new HashMap<>();
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
        g2.fillOval(outerX, outerY+effectThickness-1, outerDiameter, outerDiameter);
        g2.setColor(Color.BLACK);
        g2.drawArc(outerX, outerY+effectThickness-1, outerDiameter, outerDiameter, 0,360);
        
        
        // ------------------------------
        // OUTER RING BACKGROUND COLOR
        // ------------------------------
        g2.setColor(ringBackColor);  // ring color
        g2.fillOval(outerX, outerY+pressOffset, outerDiameter, outerDiameter);
        
        
        // ------------------------------
        // PROGRESS ARC (clockwise fill)
        // ------------------------------
        double progress = (quantity>=goal ? 100 : (quantity/goal)*100); 
        Color brimPaintingColor = progressToGreen(progress);
        
        g2.setStroke(new BasicStroke(progessThickness));
        g2.setColor(brimPaintingColor);

        g2.drawArc(outerX+progessThickness/2, outerY+progessThickness/2+pressOffset, outerDiameter-progessThickness, outerDiameter-progessThickness, 90, - (int)(progress * 3.6));
        
        if(quantity > goal){
            double savedQuantity = quantity;
            Color savedColor = greenColor;
            double savedProgress;
            
            while(savedQuantity > 0 && savedQuantity > goal){
                savedQuantity -= goal;                // Subtracting the amount left
                savedColor = increaseGreenColor(savedColor); // Darkening even more the color
                savedProgress = (savedQuantity>goal ? 100 : (savedQuantity/goal)*100); // Making new progress bar
                g2.setColor(savedColor);              // Changing the brush color
                g2.drawArc(                           // Drawing arc
                    outerX+progessThickness/2,
                    outerY+progessThickness/2+pressOffset,
                    outerDiameter-progessThickness,
                    outerDiameter-progessThickness,
                    90,
                    - (int)(savedProgress * 3.6)
                );
            }
        }

        
        
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
    
    
    public HabitCard_Quantity(GUI_Window guiInput, String habitNameInput, Color habitColorInput, 
        double quantityInput, double goalInput, double incrementInput, String weekInput) {
        initComponents();
        
        
        
        // Double checking that name is not too long
        if(habitNameInput.length() > MAX_LENGTH){
            habitName.setText("ERROR: TOO LONG");
            return;
        }
        
        // Setting up the variables:
        mainGUI = guiInput;
        habitColor = habitColorInput;
        quantity = quantityInput;
        goal = goalInput;
        increment = incrementInput;
        week = weekInput;
        habitName.setText(habitNameInput); // Setting the habit name
        
        // Setting text displays
        quantityText.setText(Double.toString(quantityInput));
        goalText.setText("Goal: " + goal);
        
        // Setting up "this" : ---------------------------------------------
        this.setOpaque(false);
        // ----------------------------------------------------------------
        
        
        // Setting up "insidePanel" : -------------------------------------
        insidePanel.setOpaque(false);
        insidePanel.setBackground(habitColor);
        // ----------------------------------------------------------------
        
        
        
        // Setting up buttons : ---------------------------------------------------------------------------------------------------
        insidePanel.remove(minusButton);    // Removing temp GUI buttons
        insidePanel.remove(plusButton);     // Removing temp GUI buttons
        
        plusButton = new RoundButton("+", 50);  // Adding dynamic button using round features
        minusButton = new RoundButton("-", 50); // Adding dynamic button using round features

        plusButton.setBackground(darkenColor(habitColorInput));  // Setting the background of the button using the given habitColor
        minusButton.setBackground(darkenColor(habitColorInput)); // Setting the background of the button using the given habitColor
        plusButton.setForeground(Color.BLACK);                   // Setting the text color of the button
        minusButton.setForeground(Color.BLACK);                  // Setting the text color of the button
        
        plusButton.setOpaque(false);
        minusButton.setOpaque(false);
        
        minusButton.addActionListener(e->minusClicked());    // Adding listeners for button click
        plusButton.addActionListener(e->plusClicked());      // Adding listeners for button click
        
        minusButton.setBounds(20, 110, 35, 35);
        plusButton.setBounds(115, 110, 35, 35);
        
        insidePanel.add(minusButton);  // Adding back once we are finished
        insidePanel.add(plusButton);   // Adding back once we are finished
        
        // --------------------------------------------------------------------------------------------------------------------------

        
        // Setting up complete goal state (actually the color around depends on goal and quantity which we already set soooo. wow that was easier lmao)
        if(quantity >= goal){
            completeText.setVisible(true);
        }
        else{
            completeText.setVisible(false);
        }
        
        // Repainting Everything
        this.repaint();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        insidePanel = new javax.swing.JPanel();
        minusButton = new javax.swing.JButton();
        plusButton = new javax.swing.JButton();
        quantityText = new javax.swing.JLabel();
        goalText = new javax.swing.JLabel();
        habitName = new javax.swing.JLabel();
        completeText = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(200, 200));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(200, 200));
        setLayout(null);

        insidePanel.setBackground(new java.awt.Color(255, 255, 0));
        insidePanel.setLayout(null);

        minusButton.setBackground(new java.awt.Color(153, 153, 153));
        minusButton.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        minusButton.setForeground(java.awt.Color.white);
        minusButton.setText("-");
        minusButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        insidePanel.add(minusButton);
        minusButton.setBounds(20, 110, 35, 35);

        plusButton.setBackground(new java.awt.Color(153, 153, 153));
        plusButton.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        plusButton.setForeground(java.awt.Color.white);
        plusButton.setText("+");
        plusButton.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        insidePanel.add(plusButton);
        plusButton.setBounds(115, 110, 35, 35);

        quantityText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        quantityText.setForeground(java.awt.Color.black);
        quantityText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        quantityText.setText("99999");
        insidePanel.add(quantityText);
        quantityText.setBounds(55, 100, 60, 50);

        goalText.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        goalText.setForeground(java.awt.Color.black);
        goalText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        goalText.setText("Goal:");
        insidePanel.add(goalText);
        goalText.setBounds(0, 90, 170, 16);

        habitName.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        habitName.setForeground(java.awt.Color.black);
        habitName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        habitName.setText("kkkkkkkkkkkkkkkkk");
        insidePanel.add(habitName);
        habitName.setBounds(0, 60, 170, 30);

        add(insidePanel);
        insidePanel.setBounds(15, 10, 170, 170);

        completeText.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        completeText.setForeground(new java.awt.Color(181, 230, 29));
        completeText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        completeText.setText("COMPLETE");
        add(completeText);
        completeText.setBounds(25, 40, 150, 30);
    }// </editor-fold>//GEN-END:initComponents

    
    
    // CLICKS OF THE BUTTONS: =======================================================================
    
    public void minusClicked() {
        // Resetting the away from screen timer
        mainGUI.reseAwaytAway(); 
        
        // Incremening the quantity and showing value
        quantity -= increment;
        quantity = Math.round(quantity * 10.0) / 10.0;  // Rounding to show nice numbers when working with doubles
        if(quantity < 0.0)                              // Bounding to never go negative
            quantity = 0.0; 
        quantityText.setText(String.valueOf(quantity)); // Showing new value
        
        
        // Checking if this puts quantity under goal to take away completion 
        if(quantity<goal){
            completeText.setVisible(false);
        }
        
         // Changing the completion status in the map
        completionMap.put(LocalDate.now(), new QuantityEntry(quantity, goal)); 
        
        animatePress(false); // do the animation, inside the animation it will handle if it will bounce or stay down
        
        // This does the repainting of the brim
        this.repaint();
    }

    public void plusClicked() {
        // Resetting the away from screen timer
        mainGUI.reseAwaytAway();  
        
        // Incremening the quantity and showing value
        quantity += increment;
        quantity = Math.round(quantity * 10.0) / 10.0;  // Rounding to show nice numbers when working with doubles
        quantityText.setText(String.valueOf(quantity)); // Show new value
        
        // Checking if this puts quantity over goal to give completion message
        if(quantity>=goal){
            completeText.setVisible(true);   
        }
        
        // Changing the completion status in the map -> setting to complete or false depending on if complete text is visible
        completionMap.put(LocalDate.now(), new QuantityEntry(quantity, goal));  
        
        animatePress(false); // do the animation, inside the animation it will handle if it will bounce or stay down
        
        // This does the repainting of the brim
        this.repaint();
    }
    
    // ===================================================================================================
    
    
    
    
    
    private Timer pressTimer;
    private void animatePress(boolean comingFromSetQuantity) {
        if (pressTimer != null && pressTimer.isRunning()) 
            return;
        
        // Do nothing if button is already pressed in, this will still do it for when the quantity is exactly the goal
        if(pressOffset == MAX_PRESS && quantity >= goal)
            return;
        
        
        
        if(quantity < goal)
            targetPressOffset = MAX_PRESS/2; // If this was a pressed in and we are still under the goal, then we want to only bounce half way
        else 
            targetPressOffset = quantity>=goal ? MAX_PRESS : 0;  // If this is not a bounce, do maxpress if quantity>=goal | do 0 if not
        
        // Overiding the bounce if we are coming from the setQuantity function
        if(comingFromSetQuantity){
            targetPressOffset = quantity>=goal ? MAX_PRESS : 0;
        }
        
        pressTimer = new Timer(10, e -> {
            if (pressOffset < targetPressOffset){
                habitName.setLocation(habitName.getX(),habitName.getY()+1);
                completeText.setLocation(completeText.getX(), completeText.getY()+1);
                minusButton.setLocation(minusButton.getX(), minusButton.getY()+1);
                plusButton.setLocation(plusButton.getX(), plusButton.getY()+1);
                quantityText.setLocation(quantityText.getX(), quantityText.getY()+1);
                goalText.setLocation(goalText.getX(), goalText.getY()+1);
                pressOffset++; // Changes the value that the repaint uses
            }
            else if (pressOffset > targetPressOffset) {
                habitName.setLocation(habitName.getX(),habitName.getY()-1);
                completeText.setLocation(completeText.getX(), completeText.getY()-1);
                minusButton.setLocation(minusButton.getX(), minusButton.getY()-1);
                plusButton.setLocation(plusButton.getX(), plusButton.getY()-1);
                quantityText.setLocation(quantityText.getX(), quantityText.getY()-1);
                goalText.setLocation(goalText.getX(), goalText.getY()-1);
                pressOffset--; // Changes the value that the repaint uses
            }
            else{
                // If we are doing a bounce, then we want to flip the targetpressoffset
                if(quantity < goal && targetPressOffset != 0){
                    targetPressOffset = 0;
                }
                else
                    ((Timer) e.getSource()).stop();
            }
            
            repaint();
        });
        
        pressTimer.start();
    }
    
    
    
    
    
    
    // Helper Functions: =============================================================================
    private Color darkenColor(Color color) {
        double factor = 0.7; // USE THIS FACTOR 0=darker | 1=lighter
        
        int r = Math.max((int)(color.getRed() * factor), 0);
        int g = Math.max((int)(color.getGreen() * factor), 0);
        int b = Math.max((int)(color.getBlue() * factor), 0);
        
        return new Color(r, g, b);
    }
    
    
    private Color increaseGreenColor(Color color) {
        float[] hsb = Color.RGBtoHSB(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                null
        );

        hsb[0] = Math.min(hsb[0] + 0.20f, 0.66f);  // shift hue
        hsb[1] = Math.min(hsb[1] + 0.00f, 1f);     // saturation
        hsb[2] = Math.max(hsb[2] - 0.01f, 0.20f);  // brightness

        return Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
    }
    

    private Color progressToGreen(double progress) {
        // Changing and clamping to 0-1
        progress = Math.max(0.0, Math.min(1.0, progress/100));

        // Linear math to make look good through all progress
        int r = (int) Math.round(255 + progress * (greenColor.getRed()   - 255));
        int g = (int) Math.round(51  + progress * (greenColor.getGreen() - 51));
        int b = (int) Math.round(51  + progress * (greenColor.getBlue()  - 51));

        // Clamp to valid 0-255 range 
        r = Math.max(0, Math.min(255, r));
        g = Math.max(0, Math.min(255, g));
        b = Math.max(0, Math.min(255, b));

        return new Color(r, g, b);
    }
    
    
    // ===================================================================================================
    
    // Public Functions : ============================================================================
    

    // -- GET FUNCTIONS -- 
    public String getHabitName(){
        return habitName.getText();
    }
    
    public double getQuantity(){
        return quantity;
    }
    
    public double getGoal(){
        return goal;
    }
    
    public double getIncrement(){
        return increment;
    }
    
    public Color getHabitColor(){
        return habitColor;
    }
    
    public String getHabitColorString(){
        return Integer.toString(habitColor.getRed()) + "," + Integer.toString(habitColor.getGreen()) + "," + Integer.toString(habitColor.getBlue());
    }
    
    public String getWeek(){
        return week;
    }
    
    public boolean getCompleted(LocalDate targetDay){
        return completionMap.get(targetDay).getCompleted();
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
   
    public Map<LocalDate, QuantityEntry> getCompletionMap() {
        return completionMap;
    }
    
    
    
    // -- SET FUNCTIONS --
    public void setHabitName(String newHabitName){
        habitName.setText(newHabitName);
    }
    
    public void setHabitColor(Color newColor){
        habitColor = newColor;
        insidePanel.setBackground(newColor);
        plusButton.setBackground(darkenColor(newColor));
        minusButton.setBackground(darkenColor(newColor));
    }
    
    public void setWeek(String newWeek){
        week = newWeek;
    }
    
    public void setIncrement(Double newIncrement){
        increment = newIncrement;
    }
    
    public void setGoal(Double newGoal){
        goal = newGoal;
    }
    
    public void setQuantity(double quantityInput){
        quantity = quantityInput;                       // Updating memory
        quantityText.setText(String.valueOf(quantity)); // Showing new value
        completeText.setVisible(quantity >= goal);      // Showing the "compelted" message if needed
        
        // Moving button to where its suppose to be with this new quantity
        animatePress(true);
        
        this.repaint();                                 // Repaint so that it can get painted the way its suppose to be with new quantity
    }
    
    
    public void addDateEntry(LocalDate date, double quantityReached, double targetGoal){
        completionMap.put(date, new QuantityEntry(quantityReached, targetGoal));
    }
    
    public boolean hasDateEntry(LocalDate targetDate){
        return completionMap.containsKey(targetDate);
    }
    
    public int changeDateEntry(LocalDate targetDate, double newQuantity){
        if(!hasDateEntry(targetDate))
            return -1;
        
        completionMap.get(targetDate).setReached(newQuantity);

        return completionMap.get(targetDate).getCompleted() ? 1 : 0;
    }
    
    public QuantityEntry getDateEntry(LocalDate targetDate){
        return completionMap.get(targetDate);
    }
    
    public int getDateEntryCount(){
        return completionMap.size();
    }
    
    
    
    
    
    
    // ===================================================================================================

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel completeText;
    private javax.swing.JLabel goalText;
    private javax.swing.JLabel habitName;
    private javax.swing.JPanel insidePanel;
    private javax.swing.JButton minusButton;
    private javax.swing.JButton plusButton;
    private javax.swing.JLabel quantityText;
    // End of variables declaration//GEN-END:variables
}
