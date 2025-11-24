package habitpanel;
import javax.swing.*;
import java.awt.Color;
// NOTE TO SELF: 1040 x 600 is the screen size






public class GUI_Window extends javax.swing.JFrame {
    
    // MANIPULATABLE VARIABLES: ===========================================
    int AWAY_FROM_SCREEN_TIME = 60; // In seconds (1 minute)
    Color PRIMARY_COLOR = new Color(156,183,133);   // =.
    Color SECONDARY_COLOR = new Color(204,204,204); //  | Color variables that can change
    Color BUTTON_COLOR = new Color(128,161,98);     //  | when reading from the variable file
    Color TEXT_COLOR = new Color(255,255,255);      // ='
    
    // ====================================================================
    
    // HOLDING VARIABLES: =================================================
    int awayFromScreenCounter = 0;    // Keeps count from 0- AWAY_FROM_SCREEN_TIME
    JPanel progress_again = null;     // Holds the settings panel for foward refrence
    JTextField keyboardTarget = null; // Holds where we are typing into 
    String customName = "";
    // ====================================================================
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI_Window.class.getName());

    // Constructor of the frame
    public GUI_Window() {
        initComponents();
        
        // Loading information from files
        loadVariables();           // Loading in the variables from files
        loadHabits();              // Loading habits from files
        
        // Main Frame Code:
        keyboard.getParent().setLayout(null);  // Setting the layout to null, so that we can use the set location without issues
        
        // Settings Panel Code:
        progress_again = progress; // Saving the progress again into another location because of foward refrence in timer
        s_customName.setText(customName.isEmpty() ? "Not Set" : customName);                        // Setting the custom name
        h_welcomeMessage.setText(customName.isEmpty() ? "Welcome back!" : "Welcome, " + customName);  // Setting the custom name
        s_awayFromScreen.setText(Integer.toString(AWAY_FROM_SCREEN_TIME/60) + " Minute(s)");          // Setting the away from screen time
        s_awayFromScreenInput.setModel(new SpinnerNumberModel(1, 1, 9999, 1));                        // Setting up the spinner so it can have max and mins
        ((JSpinner.DefaultEditor) s_awayFromScreenInput.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER); // Centering the text in input
        
        
        // Keyboard Panel Code:
        keyboard.getParent().setComponentZOrder(keyboard,0);  // Moving up the keyboard because it needs to be on top of everything
        keyboard.setLocation(0,0);                            // Moving keyboard to safe space
        keyboard.setVisible(false);                           // Hiding keyboard
        
        // Painting the program
        paintColors();         
        
        // Switching to start in home frame
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
        h_scrollPane = new javax.swing.JScrollPane();
        h_habitPanel = new javax.swing.JPanel();
        settings = new javax.swing.JPanel();
        s_topPanel = new javax.swing.JPanel();
        s_settingsTitle = new javax.swing.JLabel();
        s_backButton = new javax.swing.JButton();
        s_colorsPanel = new javax.swing.JPanel();
        s_colorsTitle = new javax.swing.JLabel();
        s_primaryColorButton = new javax.swing.JButton();
        s_primaryColor = new javax.swing.JPanel();
        s_secondaryColor = new javax.swing.JPanel();
        s_secondaryColorButton = new javax.swing.JButton();
        s_textColorButton = new javax.swing.JButton();
        s_textColor = new javax.swing.JPanel();
        s_buttonColorButton = new javax.swing.JButton();
        s_buttonColor = new javax.swing.JPanel();
        s_customNamePanel = new javax.swing.JPanel();
        s_customNameTitle = new javax.swing.JLabel();
        s_customNameButton = new javax.swing.JButton();
        s_customNameTitle2 = new javax.swing.JLabel();
        s_customName = new javax.swing.JLabel();
        s_customNameInput = new javax.swing.JTextField();
        s_saveCustomNameButton = new javax.swing.JButton();
        s_awayFromScreenPanel = new javax.swing.JPanel();
        s_awayFromScreenTitle = new javax.swing.JLabel();
        s_awayFromScreenTitle2 = new javax.swing.JLabel();
        s_awayFromScreen = new javax.swing.JLabel();
        s_awayFromScreenInput = new javax.swing.JSpinner();
        s_awayFromScreenButton = new javax.swing.JButton();
        s_connectionPanel = new javax.swing.JPanel();
        progress = new javax.swing.JPanel();
        p_topPanel = new javax.swing.JPanel();
        p_progressTitle = new javax.swing.JLabel();
        p_backButton = new javax.swing.JButton();
        keyboard = new javax.swing.JPanel();
        key1 = new javax.swing.JButton();
        key2 = new javax.swing.JButton();
        key3 = new javax.swing.JButton();
        key4 = new javax.swing.JButton();
        key5 = new javax.swing.JButton();
        key6 = new javax.swing.JButton();
        key7 = new javax.swing.JButton();
        key8 = new javax.swing.JButton();
        key9 = new javax.swing.JButton();
        key10 = new javax.swing.JButton();
        key11 = new javax.swing.JButton();
        key12 = new javax.swing.JButton();
        key13 = new javax.swing.JButton();
        key14 = new javax.swing.JButton();
        key15 = new javax.swing.JButton();
        key16 = new javax.swing.JButton();
        key17 = new javax.swing.JButton();
        key18 = new javax.swing.JButton();
        key19 = new javax.swing.JButton();
        key20 = new javax.swing.JButton();
        key21 = new javax.swing.JButton();
        key22 = new javax.swing.JButton();
        key23 = new javax.swing.JButton();
        key24 = new javax.swing.JButton();
        key25 = new javax.swing.JButton();
        key26 = new javax.swing.JButton();
        key27 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HabitPanel");
        setMaximumSize(new java.awt.Dimension(1040, 600));
        setMinimumSize(new java.awt.Dimension(1040, 600));
        setResizable(false);
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

        h_scrollPane.setBackground(new java.awt.Color(204, 204, 204));

        h_habitPanel.setBackground(new java.awt.Color(204, 204, 204));
        h_habitPanel.setLayout(new java.awt.GridLayout(0, 4, 10, 10));
        h_scrollPane.setViewportView(h_habitPanel);

        home.add(h_scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 1000, 460));

        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        settings.setBackground(new java.awt.Color(204, 204, 204));
        settings.setMaximumSize(new java.awt.Dimension(1040, 600));
        settings.setMinimumSize(new java.awt.Dimension(1040, 600));
        settings.setName(""); // NOI18N
        settings.setPreferredSize(new java.awt.Dimension(1040, 600));
        settings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_topPanel.setBackground(new java.awt.Color(156, 183, 133));
        s_topPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_settingsTitle.setBackground(new java.awt.Color(128, 161, 98));
        s_settingsTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        s_settingsTitle.setForeground(java.awt.Color.white);
        s_settingsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_settingsTitle.setText("Settings");
        s_topPanel.add(s_settingsTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 5, 780, 40));

        s_backButton.setBackground(new java.awt.Color(128, 161, 98));
        s_backButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_backButton.setForeground(java.awt.Color.white);
        s_backButton.setText("Back");
        s_backButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_backButtonMouseClicked(evt);
            }
        });
        s_topPanel.add(s_backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 110, 36));

        settings.add(s_topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 50));

        s_colorsPanel.setBackground(new java.awt.Color(156, 183, 133));
        s_colorsPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_colorsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_colorsTitle.setBackground(new java.awt.Color(156, 183, 133));
        s_colorsTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_colorsTitle.setForeground(java.awt.Color.white);
        s_colorsTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_colorsTitle.setText("Colors:");
        s_colorsPanel.add(s_colorsTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, -1));

        s_primaryColorButton.setBackground(new java.awt.Color(128, 161, 98));
        s_primaryColorButton.setForeground(java.awt.Color.white);
        s_primaryColorButton.setText("Primary Color");
        s_primaryColorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_primaryColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_changeColorButtonClicked(evt);
            }
        });
        s_colorsPanel.add(s_primaryColorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 270, 30));

        s_primaryColor.setBackground(java.awt.Color.white);
        s_primaryColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout s_primaryColorLayout = new javax.swing.GroupLayout(s_primaryColor);
        s_primaryColor.setLayout(s_primaryColorLayout);
        s_primaryColorLayout.setHorizontalGroup(
            s_primaryColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );
        s_primaryColorLayout.setVerticalGroup(
            s_primaryColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        s_colorsPanel.add(s_primaryColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 50, 50, 30));

        s_secondaryColor.setBackground(java.awt.Color.white);
        s_secondaryColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout s_secondaryColorLayout = new javax.swing.GroupLayout(s_secondaryColor);
        s_secondaryColor.setLayout(s_secondaryColorLayout);
        s_secondaryColorLayout.setHorizontalGroup(
            s_secondaryColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );
        s_secondaryColorLayout.setVerticalGroup(
            s_secondaryColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        s_colorsPanel.add(s_secondaryColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 90, 50, 30));

        s_secondaryColorButton.setBackground(new java.awt.Color(128, 161, 98));
        s_secondaryColorButton.setForeground(java.awt.Color.white);
        s_secondaryColorButton.setText("Secondary Color");
        s_secondaryColorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_secondaryColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_changeColorButtonClicked(evt);
            }
        });
        s_colorsPanel.add(s_secondaryColorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 270, 30));

        s_textColorButton.setBackground(new java.awt.Color(128, 161, 98));
        s_textColorButton.setForeground(java.awt.Color.white);
        s_textColorButton.setText("Text Color");
        s_textColorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_textColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_changeColorButtonClicked(evt);
            }
        });
        s_colorsPanel.add(s_textColorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 270, 30));

        s_textColor.setBackground(java.awt.Color.white);
        s_textColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout s_textColorLayout = new javax.swing.GroupLayout(s_textColor);
        s_textColor.setLayout(s_textColorLayout);
        s_textColorLayout.setHorizontalGroup(
            s_textColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );
        s_textColorLayout.setVerticalGroup(
            s_textColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        s_colorsPanel.add(s_textColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 50, 30));

        s_buttonColorButton.setBackground(new java.awt.Color(128, 161, 98));
        s_buttonColorButton.setForeground(java.awt.Color.white);
        s_buttonColorButton.setText("Button Color");
        s_buttonColorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_buttonColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_changeColorButtonClicked(evt);
            }
        });
        s_colorsPanel.add(s_buttonColorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 270, 30));

        s_buttonColor.setBackground(java.awt.Color.white);
        s_buttonColor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout s_buttonColorLayout = new javax.swing.GroupLayout(s_buttonColor);
        s_buttonColor.setLayout(s_buttonColorLayout);
        s_buttonColorLayout.setHorizontalGroup(
            s_buttonColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 48, Short.MAX_VALUE)
        );
        s_buttonColorLayout.setVerticalGroup(
            s_buttonColorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 28, Short.MAX_VALUE)
        );

        s_colorsPanel.add(s_buttonColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 170, 50, 30));

        settings.add(s_colorsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 380, 220));

        s_customNamePanel.setBackground(new java.awt.Color(156, 183, 133));
        s_customNamePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_customNamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_customNameTitle.setBackground(new java.awt.Color(156, 183, 133));
        s_customNameTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_customNameTitle.setForeground(java.awt.Color.white);
        s_customNameTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_customNameTitle.setText("Set Up Custom Name:");
        s_customNamePanel.add(s_customNameTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 560, -1));

        s_customNameButton.setBackground(new java.awt.Color(128, 161, 98));
        s_customNameButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        s_customNameButton.setForeground(java.awt.Color.white);
        s_customNameButton.setText("Change Name");
        s_customNameButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_customNameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_customNameButtonMouseClicked(evt);
            }
        });
        s_customNamePanel.add(s_customNameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 530, 40));

        s_customNameTitle2.setBackground(new java.awt.Color(156, 183, 133));
        s_customNameTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_customNameTitle2.setForeground(java.awt.Color.white);
        s_customNameTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_customNameTitle2.setText("Current Saved:");
        s_customNamePanel.add(s_customNameTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 260, 30));

        s_customName.setBackground(new java.awt.Color(156, 183, 133));
        s_customName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_customName.setForeground(java.awt.Color.white);
        s_customNamePanel.add(s_customName, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 260, 30));

        settings.add(s_customNamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 80, 580, 140));

        s_customNameInput.setBackground(new java.awt.Color(156, 183, 133));
        s_customNameInput.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        s_customNameInput.setForeground(java.awt.Color.white);
        s_customNameInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        s_customNameInput.setText("customnamehere");
        settings.add(s_customNameInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, 520, 61));

        s_saveCustomNameButton.setBackground(new java.awt.Color(128, 161, 98));
        s_saveCustomNameButton.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        s_saveCustomNameButton.setForeground(java.awt.Color.white);
        s_saveCustomNameButton.setText("Save Custom Name");
        s_saveCustomNameButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_saveCustomNameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_saveCustomNameButtonMouseClicked(evt);
            }
        });
        settings.add(s_saveCustomNameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 730, 40));

        s_awayFromScreenPanel.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreenPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_awayFromScreenPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_awayFromScreenTitle.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreenTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreenTitle.setForeground(java.awt.Color.white);
        s_awayFromScreenTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenTitle.setText("Away From Screen Timeout");
        s_awayFromScreenPanel.add(s_awayFromScreenTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, -1));

        s_awayFromScreenTitle2.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreenTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreenTitle2.setForeground(java.awt.Color.white);
        s_awayFromScreenTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenTitle2.setText("Current Saved:");
        s_awayFromScreenPanel.add(s_awayFromScreenTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 140, -1));

        s_awayFromScreen.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreen.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreen.setForeground(java.awt.Color.white);
        s_awayFromScreen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreen.setText("1 Minute");
        s_awayFromScreenPanel.add(s_awayFromScreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 60, 140, -1));

        s_awayFromScreenInput.setEditor(new javax.swing.JSpinner.NumberEditor(s_awayFromScreenInput, ""));
        s_awayFromScreenInput.setOpaque(true);
        s_awayFromScreenPanel.add(s_awayFromScreenInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 300, 40));

        s_awayFromScreenButton.setBackground(new java.awt.Color(128, 161, 98));
        s_awayFromScreenButton.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        s_awayFromScreenButton.setForeground(java.awt.Color.white);
        s_awayFromScreenButton.setText("Update Time");
        s_awayFromScreenButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_awayFromScreenButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_awayFromScreenButtonMouseClicked(evt);
            }
        });
        s_awayFromScreenPanel.add(s_awayFromScreenButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 340, 30));

        settings.add(s_awayFromScreenPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 380, 220));

        s_connectionPanel.setBackground(new java.awt.Color(156, 183, 133));
        s_connectionPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout s_connectionPanelLayout = new javax.swing.GroupLayout(s_connectionPanel);
        s_connectionPanel.setLayout(s_connectionPanelLayout);
        s_connectionPanelLayout.setHorizontalGroup(
            s_connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
        );
        s_connectionPanelLayout.setVerticalGroup(
            s_connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 294, Short.MAX_VALUE)
        );

        settings.add(s_connectionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 260, 580, 300));

        getContentPane().add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        progress.setBackground(new java.awt.Color(204, 204, 204));
        progress.setMaximumSize(new java.awt.Dimension(1040, 600));
        progress.setMinimumSize(new java.awt.Dimension(1040, 600));
        progress.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_topPanel.setBackground(new java.awt.Color(156, 183, 133));
        p_topPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_progressTitle.setBackground(new java.awt.Color(128, 161, 98));
        p_progressTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        p_progressTitle.setForeground(java.awt.Color.white);
        p_progressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_progressTitle.setText("Progress Center");
        p_topPanel.add(p_progressTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 5, 780, 40));

        p_backButton.setBackground(new java.awt.Color(128, 161, 98));
        p_backButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        p_backButton.setForeground(java.awt.Color.white);
        p_backButton.setText("Back");
        p_backButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p_backButtonMouseClicked(evt);
            }
        });
        p_topPanel.add(p_backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 8, 110, 36));

        progress.add(p_topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 50));

        getContentPane().add(progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        keyboard.setBackground(new java.awt.Color(156, 183, 133));
        keyboard.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        keyboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        key1.setBackground(new java.awt.Color(128, 161, 98));
        key1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key1.setForeground(java.awt.Color.white);
        key1.setText("Q");
        key1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 60, 60));

        key2.setBackground(new java.awt.Color(128, 161, 98));
        key2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key2.setForeground(java.awt.Color.white);
        key2.setText("W");
        key2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 60, 60));

        key3.setBackground(new java.awt.Color(128, 161, 98));
        key3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key3.setForeground(java.awt.Color.white);
        key3.setText("E");
        key3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 60, 60));

        key4.setBackground(new java.awt.Color(128, 161, 98));
        key4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key4.setForeground(java.awt.Color.white);
        key4.setText("R");
        key4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 60, 60));

        key5.setBackground(new java.awt.Color(128, 161, 98));
        key5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key5.setForeground(java.awt.Color.white);
        key5.setText("T");
        key5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key5, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 60, 60));

        key6.setBackground(new java.awt.Color(128, 161, 98));
        key6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key6.setForeground(java.awt.Color.white);
        key6.setText("Y");
        key6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 10, 60, 60));

        key7.setBackground(new java.awt.Color(128, 161, 98));
        key7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key7.setForeground(java.awt.Color.white);
        key7.setText("U");
        key7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 60, 60));

        key8.setBackground(new java.awt.Color(128, 161, 98));
        key8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key8.setForeground(java.awt.Color.white);
        key8.setText("I");
        key8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, 60, 60));

        key9.setBackground(new java.awt.Color(128, 161, 98));
        key9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key9.setForeground(java.awt.Color.white);
        key9.setText("O");
        key9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key9, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 10, 60, 60));

        key10.setBackground(new java.awt.Color(128, 161, 98));
        key10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key10.setForeground(java.awt.Color.white);
        key10.setText("P");
        key10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key10, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 10, 60, 60));

        key11.setBackground(new java.awt.Color(128, 161, 98));
        key11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key11.setForeground(java.awt.Color.white);
        key11.setText("A");
        key11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 60, 60));

        key12.setBackground(new java.awt.Color(128, 161, 98));
        key12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key12.setForeground(java.awt.Color.white);
        key12.setText("S");
        key12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 80, 60, 60));

        key13.setBackground(new java.awt.Color(128, 161, 98));
        key13.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key13.setForeground(java.awt.Color.white);
        key13.setText("D");
        key13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 60, 60));

        key14.setBackground(new java.awt.Color(128, 161, 98));
        key14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key14.setForeground(java.awt.Color.white);
        key14.setText("F");
        key14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 60, 60));

        key15.setBackground(new java.awt.Color(128, 161, 98));
        key15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key15.setForeground(java.awt.Color.white);
        key15.setText("G");
        key15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key15, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 60, 60));

        key16.setBackground(new java.awt.Color(128, 161, 98));
        key16.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key16.setForeground(java.awt.Color.white);
        key16.setText("H");
        key16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 60, 60));

        key17.setBackground(new java.awt.Color(128, 161, 98));
        key17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key17.setForeground(java.awt.Color.white);
        key17.setText("J");
        key17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key17, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 80, 60, 60));

        key18.setBackground(new java.awt.Color(128, 161, 98));
        key18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key18.setForeground(java.awt.Color.white);
        key18.setText("K");
        key18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key18, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 60, 60));

        key19.setBackground(new java.awt.Color(128, 161, 98));
        key19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key19.setForeground(java.awt.Color.white);
        key19.setText("L");
        key19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key19, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 80, 60, 60));

        key20.setBackground(new java.awt.Color(128, 161, 98));
        key20.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key20.setForeground(java.awt.Color.white);
        key20.setText("Z");
        key20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 60, 60));

        key21.setBackground(new java.awt.Color(128, 161, 98));
        key21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key21.setForeground(java.awt.Color.white);
        key21.setText("X");
        key21.setToolTipText("");
        key21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key21, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 150, 60, 60));

        key22.setBackground(new java.awt.Color(128, 161, 98));
        key22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key22.setForeground(java.awt.Color.white);
        key22.setText("C");
        key22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key22, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 150, 60, 60));

        key23.setBackground(new java.awt.Color(128, 161, 98));
        key23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key23.setForeground(java.awt.Color.white);
        key23.setText("V");
        key23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key23, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 150, 60, 60));

        key24.setBackground(new java.awt.Color(128, 161, 98));
        key24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key24.setForeground(java.awt.Color.white);
        key24.setText("B");
        key24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key24, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 150, 60, 60));

        key25.setBackground(new java.awt.Color(128, 161, 98));
        key25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key25.setForeground(java.awt.Color.white);
        key25.setText("N");
        key25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key25, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 60, 60));

        key26.setBackground(new java.awt.Color(128, 161, 98));
        key26.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key26.setForeground(java.awt.Color.white);
        key26.setText("M");
        key26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key26, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, 60, 60));

        key27.setBackground(new java.awt.Color(128, 161, 98));
        key27.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key27.setForeground(java.awt.Color.white);
        key27.setText("<-");
        key27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key27, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 150, 100, 60));

        getContentPane().add(keyboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 230));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        // SWITCH FRAME FUNCTION: ==========================================================================================================
    private void switchFrame(JPanel target){
        // Hidding all other frames (and keyboard)
        home.setVisible(false);
        settings.setVisible(false);
        progress.setVisible(false);
        keyboard.setVisible(false);
        
        // Stoping timer to reset since we are moving screens
        awayFromScreenCounter = 0;     // Reseting counter
        if(awayFromScreen.isRunning()) // Stopping timer if its running
            awayFromScreen.stop();
        
        // Frames specific instructions for setting up
        if(target == home){
            awayFromScreen.start();    // Starting the timer again if we went to home screen
        }
        else if(target == settings){
            s_customNameInput.setVisible(false);      // Hiding the edit custom name panel
            s_saveCustomNameButton.setVisible(false); // Hiding the save name button
            s_colorsPanel.setVisible(true);           // Showing the color panel
            s_customNamePanel.setVisible(true);       // Showing the custom name panel
            s_awayFromScreenPanel.setVisible(true);   // Showing the away from screen panel
            s_connectionPanel.setVisible(true);       // Showing the connection panel
        }
        else if(target == progress){
            
        }
        
        
        // Showing target frame after everything has been set
        target.setVisible(true);
    }
    // =================================================================================================================================

    // AWAY FROM SCREEN TIMER: =========================================================================================================
    Timer awayFromScreen = new Timer(1000, e->{
        awayFromScreenCounter++;           // Increase counter
        if(awayFromScreenCounter >= AWAY_FROM_SCREEN_TIME){  // IF: checking if we reached the max time
            ((Timer)e.getSource()).stop(); // Stop Timer
            switchFrame(progress_again);   // Take back to progress
            awayFromScreenCounter = 0;     // Reset counter
            // ---------------------------------------------------------------->  Make brightness lower [ADD LATER]
        }
    });
    // =================================================================================================================================
    
  
    
    // LOADING FUNCTIONS: ==============================================================================================================
    
    // LOADS VARIABLES FROM FILES:
    private void loadVariables(){
        // load colors
        // load custom name
    }
    
    // LOADS HABITS FROM FILES
    private void loadHabits(){
        // load habits and add them into the list 
        // save if we have some quantity habits to see if we need to upate view
        h_habitPanel.add(new HabitCard("Drink Water"));
    }
    
    // PAINTS ALL THE PANELS
    private void paintColors(){
        
        // COLLECTING ALL PANELS, BUTTONS, AND LABELS
        JPanel primaryColored[] = {
            // HOME SCREEN:
            h_topPanel, h_bottomPanel,
            // SETTINGS SCREEN:
            s_topPanel, s_colorsPanel, s_customNamePanel, s_awayFromScreenPanel,
            // PROGRESS SCREEN:
            p_topPanel,
            // KEYBOARD:
            keyboard,
        };
        JPanel secondaryColored[] = {
            // HOME SCREEN:
            home,
            // SETTINGS SCREEN:
            settings, 
            // PROGRESS SCREEN:
            progress,
        
        };
        JButton buttonColored[] = {
            // HOME SCREEN:
            h_settingsButton, h_addButton, h_editButton, h_progressButton,
            // SETTINGS SCREEN:
            s_backButton, s_primaryColorButton, s_secondaryColorButton, s_buttonColorButton, s_textColorButton, s_customNameButton,
            s_saveCustomNameButton, s_awayFromScreenButton,
            // PROGRESS SCREEN:
            p_backButton,
            // KEYBOARD:
            key1,key2,key3,key4,key5,key6,key7,key8,key9,key10,
            key11,key12,key13,key14,key15,key16,key17,key18,key19,key20,
            key21,key22,key23,key24,key25,key26,key27,
        };
        
        JLabel textColored[] = {
            // HOME SCREEN:
            h_welcomeMessage, h_dateTitle, h_date,
            // SETTINGS SCREEN:
            s_settingsTitle, s_colorsTitle, s_customNameTitle, s_customNameTitle2, s_customName, s_awayFromScreenTitle, 
            s_awayFromScreenTitle2, s_awayFromScreen,
            // PROGRESS SCREEN:
            p_progressTitle,
        };
       
        
        // PAINTING ALL TO THEIR COLORS
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
        
        // MISC. PAINTING CALLS
        // Painting color displays in the settings frame
        s_primaryColor.setBackground(PRIMARY_COLOR);
        s_secondaryColor.setBackground(SECONDARY_COLOR);
        s_textColor.setBackground(TEXT_COLOR);
        s_buttonColor.setBackground(BUTTON_COLOR);
        
        // Painting settings custom name input
        s_customNameInput.setBackground(PRIMARY_COLOR);
        s_customNameInput.setForeground(TEXT_COLOR);
        
        // Painting away from screentime input
        JFormattedTextField txt = ((JSpinner.DefaultEditor) s_awayFromScreenInput.getEditor()).getTextField();
        txt.setBackground(PRIMARY_COLOR);
        txt.setForeground(TEXT_COLOR);
        
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
    // ================== [ ADD SCREEN FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
    // =================================================================================================================================
    // ================== [ EDIT SCREEN FUNCTIONS ] ====================================================================================
    // =================================================================================================================================
    
    
    
    
    // =================================================================================================================================
    // ================== [ SETTINGS SCREEN FUNCTIONS ] ================================================================================
    // =================================================================================================================================
    private void s_backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_backButtonMouseClicked
        switchFrame(home);
    }//GEN-LAST:event_s_backButtonMouseClicked

    private void s_changeColorButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_changeColorButtonClicked
        // Finding out what color we to display as the starting color in the color chooser
        JButton chosenButton = (JButton) evt.getSource();
        Color startingColor = null;
        String colorCategory = "";
        if(chosenButton == s_primaryColorButton) { startingColor = PRIMARY_COLOR; colorCategory = "Pimary"; }
        else if(chosenButton == s_secondaryColorButton) { startingColor = SECONDARY_COLOR;  colorCategory = "Secondary"; }
        else if(chosenButton == s_textColorButton) { startingColor = TEXT_COLOR; colorCategory = "Text"; }
        else if(chosenButton == s_buttonColorButton) { startingColor = BUTTON_COLOR; colorCategory = "Button"; }
        
        // Showing the color chooser 
        Color selectedColor = JColorChooser.showDialog(this, "Choose color for: " + colorCategory + " Color", startingColor);

        // If user chose valid color (in case something goes wrong in choosing)
        if (selectedColor != null) {
            // Saving the selected color to the variable
            if(chosenButton == s_primaryColorButton) PRIMARY_COLOR = selectedColor;
            else if(chosenButton == s_secondaryColorButton) SECONDARY_COLOR  = selectedColor;
            else if(chosenButton == s_textColorButton) TEXT_COLOR = selectedColor;
            else if(chosenButton == s_buttonColorButton) BUTTON_COLOR = selectedColor;
            
            // Repainting program
            paintColors();
        }
    }//GEN-LAST:event_s_changeColorButtonClicked

    
    private void s_customNameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_customNameButtonMouseClicked
        keyboardTarget = s_customNameInput;    // Setting up target for the keyboard
        s_customNameInput.setText(customName); // Setting the input to the current name
        
        keyboard.setLocation(140,290); // Move keyboard to location
        
        s_colorsPanel.setVisible(false);          // Hiding the colors panel
        s_customNamePanel.setVisible(false);      // Hiding the custom name panel
        s_awayFromScreenPanel.setVisible(false);  // Hiding the away from screen panel
        s_connectionPanel.setVisible(false);      // Hiding the connection panel
        s_saveCustomNameButton.setVisible(true);  // Showing the save name button
        s_customNameInput.setVisible(true);       // Showing the custom name input
        keyboard.setVisible(true);                // Showing the keyboard
    }//GEN-LAST:event_s_customNameButtonMouseClicked

    private void s_saveCustomNameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_saveCustomNameButtonMouseClicked
        customName = s_customNameInput.getText(); // Saving the custom name made

        // Fixing the name to make first letter upper case and the reset to lowercase
        if (!customName.isEmpty()) {
            customName = customName.substring(0, 1).toUpperCase() + customName.substring(1).toLowerCase();
        }

        // Setting the settings page with new name if its not empty AND the home page welcome message as well
        s_customName.setText(customName.isEmpty() ? "Not Set" : customName);
        h_welcomeMessage.setText(customName.isEmpty() ? "Welcome back!" : "Welcome, " + customName);

        switchFrame(settings);                    // Switching to settings which resets everything
    }//GEN-LAST:event_s_saveCustomNameButtonMouseClicked

    private void s_awayFromScreenButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_awayFromScreenButtonMouseClicked
    // If the value is set to 0 or less, we wont accept it
    int newValue = ((Integer) s_awayFromScreenInput.getValue());
    if(newValue <= 0)
        return;
    
    // Saving value and updating the visual showing the current saved
    AWAY_FROM_SCREEN_TIME = newValue * 60;
    s_awayFromScreen.setText(Integer.toString(AWAY_FROM_SCREEN_TIME/60) + " Minute(s)");
    }//GEN-LAST:event_s_awayFromScreenButtonMouseClicked

    
    
    // =================================================================================================================================
    // ================== [ PROGRESS SCREEN FUNCTIONS ] ================================================================================
    // =================================================================================================================================
    
    private void p_backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_backButtonMouseClicked
        switchFrame(home);
    }//GEN-LAST:event_p_backButtonMouseClicked

    
    // =================================================================================================================================
    // ================== [ KEYBOARD FUNCTIONS ] =======================================================================================
    // =================================================================================================================================
    
    
    private void keyboardKeyClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keyboardKeyClicked
        String current = keyboardTarget.getText();           // Getting the current text from the target input
        String input = ((JButton)evt.getSource()).getText(); // Getting the button text that was pressed
        
        // Specific checks for returning instead of typing
        if(keyboardTarget == s_customNameInput && current.length() > 25 && !input.equals("<-"))
            return;      
        
        // Editing the string 
        if(input.equals("<-")){                              // If it was a backspace, then remove one letter from the back
            if(!current.isEmpty())
                current = current.substring(0,current.length()-1);
        }
        else{                                                // Append to the end of the current string the new input
            current = current + input;
        }
        
        
        keyboardTarget.setText(current);                     // Set the target text to the edited text now
    }//GEN-LAST:event_keyboardKeyClicked







    

    
    
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
    private javax.swing.JPanel h_habitPanel;
    private javax.swing.JButton h_progressButton;
    private javax.swing.JScrollPane h_scrollPane;
    private javax.swing.JButton h_settingsButton;
    private javax.swing.JPanel h_topPanel;
    private javax.swing.JLabel h_welcomeMessage;
    private javax.swing.JPanel home;
    private javax.swing.JButton key1;
    private javax.swing.JButton key10;
    private javax.swing.JButton key11;
    private javax.swing.JButton key12;
    private javax.swing.JButton key13;
    private javax.swing.JButton key14;
    private javax.swing.JButton key15;
    private javax.swing.JButton key16;
    private javax.swing.JButton key17;
    private javax.swing.JButton key18;
    private javax.swing.JButton key19;
    private javax.swing.JButton key2;
    private javax.swing.JButton key20;
    private javax.swing.JButton key21;
    private javax.swing.JButton key22;
    private javax.swing.JButton key23;
    private javax.swing.JButton key24;
    private javax.swing.JButton key25;
    private javax.swing.JButton key26;
    private javax.swing.JButton key27;
    private javax.swing.JButton key3;
    private javax.swing.JButton key4;
    private javax.swing.JButton key5;
    private javax.swing.JButton key6;
    private javax.swing.JButton key7;
    private javax.swing.JButton key8;
    private javax.swing.JButton key9;
    private javax.swing.JPanel keyboard;
    private javax.swing.JButton p_backButton;
    private javax.swing.JLabel p_progressTitle;
    private javax.swing.JPanel p_topPanel;
    private javax.swing.JPanel progress;
    private javax.swing.JLabel s_awayFromScreen;
    private javax.swing.JButton s_awayFromScreenButton;
    private javax.swing.JSpinner s_awayFromScreenInput;
    private javax.swing.JPanel s_awayFromScreenPanel;
    private javax.swing.JLabel s_awayFromScreenTitle;
    private javax.swing.JLabel s_awayFromScreenTitle2;
    private javax.swing.JButton s_backButton;
    private javax.swing.JPanel s_buttonColor;
    private javax.swing.JButton s_buttonColorButton;
    private javax.swing.JPanel s_colorsPanel;
    private javax.swing.JLabel s_colorsTitle;
    private javax.swing.JPanel s_connectionPanel;
    private javax.swing.JLabel s_customName;
    private javax.swing.JButton s_customNameButton;
    private javax.swing.JTextField s_customNameInput;
    private javax.swing.JPanel s_customNamePanel;
    private javax.swing.JLabel s_customNameTitle;
    private javax.swing.JLabel s_customNameTitle2;
    private javax.swing.JPanel s_primaryColor;
    private javax.swing.JButton s_primaryColorButton;
    private javax.swing.JButton s_saveCustomNameButton;
    private javax.swing.JPanel s_secondaryColor;
    private javax.swing.JButton s_secondaryColorButton;
    private javax.swing.JLabel s_settingsTitle;
    private javax.swing.JPanel s_textColor;
    private javax.swing.JButton s_textColorButton;
    private javax.swing.JPanel s_topPanel;
    private javax.swing.JPanel settings;
    // End of variables declaration//GEN-END:variables
}
