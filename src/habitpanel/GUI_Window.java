package habitpanel;
import javax.swing.*;
// NOTE TO SELF: 1040 x 600 is the screen size






public class GUI_Window extends javax.swing.JFrame {
    
    // MANIPULATABLE VARIABLES: ===========================================
    int AWAY_FROM_SCREEN_TIME = 60000; // In miliseconds (1 minute)
    
    // ====================================================================
    
    // HOLDING VARIABLES: =================================================
    int awayFromScreenCounter = 0;
    JPanel settings_again = null;
    // ====================================================================
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI_Window.class.getName());

    // Constructor of the frame
    public GUI_Window() {
        initComponents();
        
        // ONE TIME CODE:
        loadVariables();
        settings_again = settings;
        switchFrame(home); // Switching to the starting frame (home)
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        home = new javax.swing.JPanel();
        settings = new javax.swing.JPanel();
        progress = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1040, 600));
        setMinimumSize(new java.awt.Dimension(1040, 600));
        setPreferredSize(new java.awt.Dimension(1040, 600));
        setSize(new java.awt.Dimension(1040, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        home.setBackground(java.awt.Color.white);
        home.setMaximumSize(new java.awt.Dimension(1040, 600));
        home.setMinimumSize(new java.awt.Dimension(1040, 600));

        javax.swing.GroupLayout homeLayout = new javax.swing.GroupLayout(home);
        home.setLayout(homeLayout);
        homeLayout.setHorizontalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1040, Short.MAX_VALUE)
        );
        homeLayout.setVerticalGroup(
            homeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        settings.setBackground(java.awt.Color.white);
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

        progress.setBackground(java.awt.Color.white);
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
    
    
    // SWITCH FRAME FUNCTION: ==========================================================================================================
    private void switchFrame(JPanel target){
        // Hidding all other frames 
        home.setVisible(false);
        settings.setVisible(false);
        progress.setVisible(false);
        
        // Stoping timer to reset since we are moving
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
                                           // Make brightness lower [ADD LATER]
            switchFrame(settings_again);   // Take back to progress
            awayFromScreenCounter = 0;     // Reset counter
        }
    });
    // =================================================================================================================================
    
  
    
    // LOADING FUNCTIONS: ==============================================================================================================
    private void loadVariables(){
        // Load the timer interval for away from screen
    }
    // =================================================================================================================================
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel home;
    private javax.swing.JPanel progress;
    private javax.swing.JPanel settings;
    // End of variables declaration//GEN-END:variables
}
