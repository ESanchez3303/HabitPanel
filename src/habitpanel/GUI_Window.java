package habitpanel;
import javax.swing.*;
import java.awt.Color;
// NOTE TO SELF: 1040 x 600 is the screen size






public class GUI_Window extends javax.swing.JFrame {
    
    // MANIPULATABLE VARIABLES: ===========================================
    int AWAY_FROM_SCREEN_TIME = 60000; // In miliseconds (1 minute)
    Color PRIMARY_COLOR = new Color(156,183,133);   // =.
    Color SECONDARY_COLOR = new Color(204,204,204); //  | Color variables that can change
    Color BUTTON_COLOR = new Color(128,161,98);     //  | when reading from the variable file
    Color TEXT_COLOR = new Color(255,255,255);      // ='
    
    // ====================================================================
    
    // HOLDING VARIABLES: =================================================
    int awayFromScreenCounter = 0; // Keeps count from 0- AWAY_FROM_SCREEN_TIME
    JPanel settings_again = null;
    // ====================================================================
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI_Window.class.getName());

    // Constructor of the frame
    public GUI_Window() {
        initComponents();
        
        // ONE TIME CALLS:
        loadVariables();           // Loading in the variables from files
        loadHabits();              // Loading habits from files
        paintColors();             // Paint all the panels to the colors that we have set
        settings_again = settings; // Saving the settings again into another location because of foward refrence in timer
        switchFrame(home);         // Switching to the starting frame (home)
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        home = new javax.swing.JPanel();
        h_topPanel = new javax.swing.JPanel();
        h_welcomeMessage = new javax.swing.JLabel();
        h_dateTitle = new javax.swing.JLabel();
        h_date = new javax.swing.JLabel();
        h_settingsButton = new javax.swing.JButton();
        h_bottomPanel = new javax.swing.JPanel();
        h_addButton = new javax.swing.JButton();
        h_editButton = new javax.swing.JButton();
        h_progressButton = new javax.swing.JButton();
        settings = new javax.swing.JPanel();
        progress = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1040, 600));
        setMinimumSize(new java.awt.Dimension(1040, 600));
        setSize(new java.awt.Dimension(1040, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        home.setBackground(new java.awt.Color(204, 204, 204));
        home.setMaximumSize(new java.awt.Dimension(1040, 600));
        home.setMinimumSize(new java.awt.Dimension(1040, 600));
        home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_topPanel.setBackground(new java.awt.Color(156, 183, 133));
        h_topPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_welcomeMessage.setBackground(new java.awt.Color(128, 161, 98));
        h_welcomeMessage.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        h_welcomeMessage.setForeground(java.awt.Color.white);
        h_welcomeMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_welcomeMessage.setText("Welcome Back!");
        h_topPanel.add(h_welcomeMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 5, 470, 40));

        h_dateTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        h_dateTitle.setForeground(java.awt.Color.white);
        h_dateTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_dateTitle.setText("Date:");
        h_topPanel.add(h_dateTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 5, 100, 40));

        h_date.setBackground(new java.awt.Color(156, 183, 133));
        h_date.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        h_date.setForeground(java.awt.Color.white);
        h_date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_date.setText("Saturday, Nov 22, 2025");
        h_date.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        h_date.setOpaque(true);
        h_topPanel.add(h_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 6, 300, 40));

        h_settingsButton.setBackground(new java.awt.Color(128, 161, 98));
        h_settingsButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_settingsButton.setForeground(java.awt.Color.white);
        h_settingsButton.setText("Settings");
        h_settingsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_settingsButtonMouseClicked(evt);
            }
        });
        h_topPanel.add(h_settingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 110, 36));

        home.add(h_topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 50));

        h_bottomPanel.setBackground(new java.awt.Color(156, 183, 133));
        h_bottomPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_bottomPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_addButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addButton.setForeground(java.awt.Color.white);
        h_addButton.setText("+ Add New");
        h_addButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addButtonMouseClicked(evt);
            }
        });
        h_bottomPanel.add(h_addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 8, 320, 36));

        h_editButton.setBackground(new java.awt.Color(128, 161, 98));
        h_editButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_editButton.setForeground(java.awt.Color.white);
        h_editButton.setText("Edit History");
        h_editButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_editButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_editButtonMouseClicked(evt);
            }
        });
        h_bottomPanel.add(h_editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 8, 320, 36));

        h_progressButton.setBackground(new java.awt.Color(128, 161, 98));
        h_progressButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_progressButton.setForeground(java.awt.Color.white);
        h_progressButton.setText("View Progress");
        h_progressButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_progressButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_progressButtonMouseClicked(evt);
            }
        });
        h_bottomPanel.add(h_progressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 8, 320, 36));

        home.add(h_bottomPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 1040, 50));

        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        settings.setBackground(java.awt.Color.red);
        settings.setMaximumSize(new java.awt.Dimension(1040, 600));
        settings.setMinimumSize(new java.awt.Dimension(1040, 600));
        settings.setName(""); // NOI18N
        settings.setOpaque(false);

        javax.swing.GroupLayout settingsLayout = new javax.swing.GroupLayout(settings);
        settings.setLayout(settingsLayout);
        settingsLayout.setHorizontalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        settingsLayout.setVerticalGroup(
            settingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        getContentPane().add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        progress.setBackground(java.awt.Color.red);
        progress.setMaximumSize(new java.awt.Dimension(1040, 600));
        progress.setMinimumSize(new java.awt.Dimension(1040, 600));

        javax.swing.GroupLayout progressLayout = new javax.swing.GroupLayout(progress);
        progress.setLayout(progressLayout);
        progressLayout.setHorizontalGroup(
            progressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        progressLayout.setVerticalGroup(
            progressLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        getContentPane().add(progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        // SWITCH FRAME FUNCTION: ==========================================================================================================
    private void switchFrame(JPanel target){
        // Hidding all other frames 
        home.setVisible(false);
        settings.setVisible(false);
        progress.setVisible(false);
        
        // Stoping timer to reset since we are moving screens
        awayFromScreenCounter = 0;     // Reseting counter
        if(awayFromScreen.isRunning()) // Stopping timer if its running
            awayFromScreen.stop();
        
        // Frames specific instructions for setting up
        if(target == home){
            awayFromScreen.start();    // Starting the timer again if we went to home screen
        }
        else if(target == settings){
            
        }
        else if(target == progress){
            
        }
        
        
        // Showing target frame
        target.setVisible(true);
    }
    // =================================================================================================================================

    // AWAY FROM SCREEN TIMER: =========================================================================================================
    Timer awayFromScreen = new Timer(1000, e->{
        awayFromScreenCounter++;           // Increase counter
        if(awayFromScreenCounter >= AWAY_FROM_SCREEN_TIME){  // IF: checking if we reached the max time
            ((Timer)e.getSource()).stop(); // Stop Timer
            switchFrame(settings_again);   // Take back to progress
            awayFromScreenCounter = 0;     // Reset counter
            // ---------------------------------------------------------------->  Make brightness lower [ADD LATER]
        }
    });
    // =================================================================================================================================
    
  
    
    // LOADING FUNCTIONS: ==============================================================================================================
    
    // LOADS VARIABLES FROM FILES:
    private void loadVariables(){
        
    }
    
    // LOADS HABITS FROM FILES
    private void loadHabits(){
        
    }
    
    // PAINTS ALL THE PANELS
    private void paintColors(){
        JPanel primaryColored[] = {
            // HOME SCREEN:
            h_topPanel, h_bottomPanel,
            // SETTINGS SCREEN:
            // PROGRESS SCREEN:
        };
        JPanel secondaryColored[] = {
            // HOME SCREEN:
            // SETTINGS SCREEN:
            // PROGRESS SCREEN:
        
        };
        JButton buttonColored[] = {
            // HOME SCREEN:
            h_settingsButton, h_addButton, h_editButton, h_progressButton,
            // SETTINGS SCREEN:
            // PROGRESS SCREEN:
        };
        
        JLabel textColored[] = {
            // HOME SCREEN:
            h_welcomeMessage, h_dateTitle, h_date,
            // SETTINGS SCREEN:
            // PROGRESS SCREEN:
        };
        
        for(JPanel curr : primaryColored){
            curr.setBackground(PRIMARY_COLOR);
        }
        for(JPanel curr : secondaryColored){
            curr.setBackground(SECONDARY_COLOR);
        }
        for(JButton curr : buttonColored){
            curr.setBackground(BUTTON_COLOR);
            curr.setForeground(TEXT_COLOR);
        }
        for(JLabel curr : textColored){
            curr.setForeground(TEXT_COLOR);
        }
    }
    // =================================================================================================================================
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ HOME SCREEN FUNCTIONS ] ====================================================================================
    // =================================================================================================================================
    private void h_settingsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_settingsButtonMouseClicked
        switchFrame(settings); // Swtich to the settings frame
    }//GEN-LAST:event_h_settingsButtonMouseClicked

    private void h_addButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addButtonMouseClicked
        // show the add habit panel
    }//GEN-LAST:event_h_addButtonMouseClicked

    private void h_editButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_editButtonMouseClicked
        // show the edit history panel
    }//GEN-LAST:event_h_editButtonMouseClicked

    private void h_progressButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_progressButtonMouseClicked
        switchFrame(progress); // Swtich to the progress frame
    }//GEN-LAST:event_h_progressButtonMouseClicked

    // =================================================================================================================================
    // ================== [ SETTINGS SCREEN FUNCTIONS ] ================================================================================
    // =================================================================================================================================
    
    // =================================================================================================================================
    // ================== [ ADD SCREEN FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
    // =================================================================================================================================
    // ================== [ EDIT SCREEN FUNCTIONS ] ====================================================================================
    // =================================================================================================================================
    
    // =================================================================================================================================
    // ================== [ PROGRESS SCREEN FUNCTIONS ] ================================================================================
    // =================================================================================================================================
    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new GUI_Window().setVisible(true));
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton h_addButton;
    private javax.swing.JPanel h_bottomPanel;
    private javax.swing.JLabel h_date;
    private javax.swing.JLabel h_dateTitle;
    private javax.swing.JButton h_editButton;
    private javax.swing.JButton h_progressButton;
    private javax.swing.JButton h_settingsButton;
    private javax.swing.JPanel h_topPanel;
    private javax.swing.JLabel h_welcomeMessage;
    private javax.swing.JPanel home;
    private javax.swing.JPanel progress;
    private javax.swing.JPanel settings;
    // End of variables declaration//GEN-END:variables
}
