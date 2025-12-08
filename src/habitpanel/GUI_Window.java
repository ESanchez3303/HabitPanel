package habitpanel;
import javax.swing.*;
import java.awt.*;
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
    boolean isAway = false;
    // ====================================================================
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GUI_Window.class.getName());

    // Constructor of the frame
    public GUI_Window() {
        initComponents();
        
        // Loading information from files
        loadVariables();           // Loading in the variables from files
        loadHabits();              // Loading habits from files
        
        // Main Frame Code:
        this.setLayout(null);  // Setting the layout to null, so that we can use the set location without issues
        home.setComponentZOrder(h_addHabitPanel, 0);  // Moving these two panels to the top
        home.setComponentZOrder(h_editHabitPanel, 0); // Moving these two panels to the top
        
        // Settings Panel Code:
        progress_again = progress; // Saving the progress again into another location because of foward refrence in timer
        s_customName.setText(customName.isEmpty() ? "Not Set" : customName);                          // Setting the custom name
        h_welcomeMessage.setText(customName.isEmpty() ? "Welcome back!" : "Welcome, " + customName);  // Setting the custom name
        s_awayFromScreen.setText(Integer.toString(AWAY_FROM_SCREEN_TIME/60) + " Minute(s)");          // Setting the away from screen time
        s_awayFromScreenInput.setModel(new SpinnerNumberModel(1, 1, 9999, 1));                        // Setting up the spinner so it can have max and mins
        ((JSpinner.DefaultEditor) s_awayFromScreenInput.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER); // Centering the text in input
        
        // Keyboard Panel Code:
        keyboard.getParent().setComponentZOrder(keyboard,0);  // Moving up the keyboard because it needs to be on top of everything
        keyboard.setLocation(0,0);                            // Moving keyboard to safe space
        keyboard.setVisible(false);                           // Hiding keyboard
        
        // Setting up home page z order (moving up the add and edit panels because during production we will be moving them around)
        home.setComponentZOrder(h_addHabitPanel, 0);
        home.setComponentZOrder(h_editHabitPanel, 0);
        
        // Setting up add habit panel (button grouping)
        ButtonGroup tempButtonGroup = new ButtonGroup();
        tempButtonGroup.add(h_addHabitIncrementPointOne);
        tempButtonGroup.add(h_addHabitIncrementPointFive);
        tempButtonGroup.add(h_addHabitIncrementOne);
        
        
        // Painting the program
        paintColors();         
        
        // Switching to start in home frame
        switchFrame(home);         // Switching to the starting frame (home)
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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
        home = new javax.swing.JPanel();
        h_addHabitPanel = new javax.swing.JPanel();
        h_addHabitTitle = new javax.swing.JLabel();
        h_addHabitTopPanel = new javax.swing.JPanel();
        h_addHabitQuantityPanel = new javax.swing.JPanel();
        h_addHabitQuantityPanelText = new javax.swing.JLabel();
        h_addHabitQuantityPanelText2 = new javax.swing.JLabel();
        h_addHabitQuantityIncrementPanel = new javax.swing.JPanel();
        h_addHabitIncrementPointOne = new javax.swing.JToggleButton();
        h_addHabitIncrementPointFive = new javax.swing.JToggleButton();
        h_addHabitIncrementOne = new javax.swing.JToggleButton();
        h_addHabitQuantitySaveButton = new javax.swing.JButton();
        h_addHabitQuantityGoal = new javax.swing.JLabel();
        h_addHabitQuantityDecrease = new javax.swing.JButton();
        h_addHabitQuantityIncrease = new javax.swing.JButton();
        h_addHabitTopPanelText = new javax.swing.JLabel();
        h_addHabitName = new javax.swing.JTextField();
        h_addHabitTopPanelText1 = new javax.swing.JLabel();
        h_addHabitYesButton = new javax.swing.JLabel();
        h_addHabitNoButton = new javax.swing.JLabel();
        h_addHabitCover = new javax.swing.JLabel();
        h_addHabitChooseColorButton = new javax.swing.JButton();
        h_addHabitColorDisplay = new javax.swing.JPanel();
        h_addHabitResetButton = new javax.swing.JButton();
        h_addHabitCardPanel = new javax.swing.JPanel();
        h_addHabitDisplay = new javax.swing.JPanel();
        h_addHabitSaveButton = new javax.swing.JLabel();
        h_addHabitResetBigButton = new javax.swing.JLabel();
        h_addHabitDaysPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        h_addHabitSaveDaysButton = new javax.swing.JButton();
        h_addHabitSunday = new javax.swing.JToggleButton();
        h_addHabitMonday = new javax.swing.JToggleButton();
        h_addHabitTuesday = new javax.swing.JToggleButton();
        h_addHabitWednesday = new javax.swing.JToggleButton();
        h_addHabitThursday = new javax.swing.JToggleButton();
        h_addHabitFriday = new javax.swing.JToggleButton();
        h_addHabitSaturday = new javax.swing.JToggleButton();
        h_editHabitPanel = new javax.swing.JPanel();
        h_editHabitTitle = new javax.swing.JLabel();
        h_scrollUpButton = new javax.swing.JButton();
        h_scrollDownButton = new javax.swing.JButton();
        h_topPanel = new javax.swing.JPanel();
        h_welcomeMessage = new javax.swing.JLabel();
        h_dateTitle = new javax.swing.JLabel();
        h_date = new javax.swing.JLabel();
        h_settingsButton = new javax.swing.JButton();
        h_bottomPanel = new javax.swing.JPanel();
        h_addHabitButton = new javax.swing.JButton();
        h_editHistoryButton = new javax.swing.JButton();
        h_progressButton = new javax.swing.JButton();
        h_editHabitButton = new javax.swing.JButton();
        h_scrollPane = new javax.swing.JScrollPane();
        h_habitPanel = new javax.swing.JPanel();
        progress = new javax.swing.JPanel();
        p_topPanel = new javax.swing.JPanel();
        p_progressTitle = new javax.swing.JLabel();
        p_backButton = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HabitPanel");
        setMaximumSize(new java.awt.Dimension(1040, 600));
        setMinimumSize(new java.awt.Dimension(1040, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1040, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        getContentPane().add(keyboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(-200, -300, 730, 230));

        home.setBackground(new java.awt.Color(204, 204, 204));
        home.setMaximumSize(new java.awt.Dimension(1040, 600));
        home.setMinimumSize(new java.awt.Dimension(1040, 600));
        home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_addHabitPanel.setBackground(new java.awt.Color(181, 181, 181));
        h_addHabitPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_addHabitTitle.setBackground(new java.awt.Color(156, 183, 133));
        h_addHabitTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        h_addHabitTitle.setForeground(java.awt.Color.white);
        h_addHabitTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitTitle.setText("Add New Habit");
        h_addHabitTitle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitTitle.setOpaque(true);
        h_addHabitPanel.add(h_addHabitTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1010, 50));

        h_addHabitTopPanel.setBackground(new java.awt.Color(156, 183, 133));
        h_addHabitTopPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitTopPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_addHabitQuantityPanel.setBackground(new java.awt.Color(156, 183, 133));
        h_addHabitQuantityPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        h_addHabitQuantityPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_addHabitQuantityPanelText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitQuantityPanelText.setForeground(java.awt.Color.white);
        h_addHabitQuantityPanelText.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        h_addHabitQuantityPanelText.setText("Goal To Reach:");
        h_addHabitQuantityPanel.add(h_addHabitQuantityPanelText, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 140, 40));

        h_addHabitQuantityPanelText2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitQuantityPanelText2.setForeground(java.awt.Color.white);
        h_addHabitQuantityPanelText2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        h_addHabitQuantityPanelText2.setText("Increment:");
        h_addHabitQuantityPanelText2.setToolTipText("");
        h_addHabitQuantityPanel.add(h_addHabitQuantityPanelText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 140, 40));

        h_addHabitQuantityIncrementPanel.setOpaque(false);
        h_addHabitQuantityIncrementPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_addHabitIncrementPointOne.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitIncrementPointOne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        h_addHabitIncrementPointOne.setForeground(java.awt.Color.white);
        h_addHabitIncrementPointOne.setText("+0.1");
        h_addHabitIncrementPointOne.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitQuantityIncrementPanel.add(h_addHabitIncrementPointOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 0, 80, 40));

        h_addHabitIncrementPointFive.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitIncrementPointFive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        h_addHabitIncrementPointFive.setForeground(java.awt.Color.white);
        h_addHabitIncrementPointFive.setText("+0.5");
        h_addHabitIncrementPointFive.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitQuantityIncrementPanel.add(h_addHabitIncrementPointFive, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 80, 40));

        h_addHabitIncrementOne.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitIncrementOne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        h_addHabitIncrementOne.setForeground(java.awt.Color.white);
        h_addHabitIncrementOne.setText("+1");
        h_addHabitIncrementOne.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitQuantityIncrementPanel.add(h_addHabitIncrementOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 0, 80, 40));

        h_addHabitQuantityPanel.add(h_addHabitQuantityIncrementPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 280, 40));

        h_addHabitQuantitySaveButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitQuantitySaveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitQuantitySaveButton.setForeground(java.awt.Color.white);
        h_addHabitQuantitySaveButton.setText("Save Settings");
        h_addHabitQuantitySaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitQuantitySaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitQuantitySaveButtonMouseClicked(evt);
            }
        });
        h_addHabitQuantityPanel.add(h_addHabitQuantitySaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 500, 30));

        h_addHabitQuantityGoal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitQuantityGoal.setForeground(java.awt.Color.white);
        h_addHabitQuantityGoal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitQuantityGoal.setText("9999");
        h_addHabitQuantityPanel.add(h_addHabitQuantityGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 80, 40));

        h_addHabitQuantityDecrease.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitQuantityDecrease.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addHabitQuantityDecrease.setForeground(java.awt.Color.white);
        h_addHabitQuantityDecrease.setText("-");
        h_addHabitQuantityDecrease.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitQuantityDecrease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitQuantityDecreaseMouseClicked(evt);
            }
        });
        h_addHabitQuantityPanel.add(h_addHabitQuantityDecrease, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 60, 50, 40));

        h_addHabitQuantityIncrease.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitQuantityIncrease.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addHabitQuantityIncrease.setForeground(java.awt.Color.white);
        h_addHabitQuantityIncrease.setText("+");
        h_addHabitQuantityIncrease.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitQuantityIncrease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitQuantityDecreaseMouseClicked(evt);
            }
        });
        h_addHabitQuantityPanel.add(h_addHabitQuantityIncrease, new org.netbeans.lib.awtextra.AbsoluteConstraints(415, 60, 50, 40));

        h_addHabitTopPanel.add(h_addHabitQuantityPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 570, 150));

        h_addHabitTopPanelText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitTopPanelText.setForeground(java.awt.Color.white);
        h_addHabitTopPanelText.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        h_addHabitTopPanelText.setText("Habit Name:");
        h_addHabitTopPanel.add(h_addHabitTopPanelText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 100, 30));

        h_addHabitName.setBackground(java.awt.Color.white);
        h_addHabitName.setForeground(java.awt.Color.black);
        h_addHabitName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        h_addHabitTopPanel.add(h_addHabitName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 220, 30));

        h_addHabitTopPanelText1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitTopPanelText1.setForeground(java.awt.Color.white);
        h_addHabitTopPanelText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitTopPanelText1.setText("Will This Habit Have a Quantity?");
        h_addHabitTopPanel.add(h_addHabitTopPanelText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 360, 20));

        h_addHabitYesButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitYesButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addHabitYesButton.setForeground(java.awt.Color.white);
        h_addHabitYesButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitYesButton.setText("Yes");
        h_addHabitYesButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitYesButton.setOpaque(true);
        h_addHabitYesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitYesButtonMouseClicked(evt);
            }
        });
        h_addHabitTopPanel.add(h_addHabitYesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 150, 30));

        h_addHabitNoButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitNoButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addHabitNoButton.setForeground(java.awt.Color.white);
        h_addHabitNoButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitNoButton.setText("No");
        h_addHabitNoButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitNoButton.setOpaque(true);
        h_addHabitNoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitNoButtonMouseClicked(evt);
            }
        });
        h_addHabitTopPanel.add(h_addHabitNoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 150, 30));

        h_addHabitCover.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        h_addHabitCover.setForeground(java.awt.Color.white);
        h_addHabitCover.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitCover.setText("To Start Choose Finish Options On Left");
        h_addHabitCover.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        h_addHabitTopPanel.add(h_addHabitCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 10, 570, 150));

        h_addHabitChooseColorButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitChooseColorButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitChooseColorButton.setForeground(java.awt.Color.white);
        h_addHabitChooseColorButton.setText("Choose a Color");
        h_addHabitChooseColorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitChooseColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitChooseColorButtonMouseClicked(evt);
            }
        });
        h_addHabitTopPanel.add(h_addHabitChooseColorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 190, 30));

        h_addHabitColorDisplay.setBackground(java.awt.Color.white);

        javax.swing.GroupLayout h_addHabitColorDisplayLayout = new javax.swing.GroupLayout(h_addHabitColorDisplay);
        h_addHabitColorDisplay.setLayout(h_addHabitColorDisplayLayout);
        h_addHabitColorDisplayLayout.setHorizontalGroup(
            h_addHabitColorDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        h_addHabitColorDisplayLayout.setVerticalGroup(
            h_addHabitColorDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        h_addHabitTopPanel.add(h_addHabitColorDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 140, 30));

        h_addHabitResetButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitResetButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitResetButton.setForeground(java.awt.Color.white);
        h_addHabitResetButton.setText("Restart Habit Card");
        h_addHabitResetButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitResetButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitResetButtonMouseClicked(evt);
            }
        });
        h_addHabitTopPanel.add(h_addHabitResetButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 340, 30));

        h_addHabitPanel.add(h_addHabitTopPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 1010, 170));

        h_addHabitCardPanel.setBackground(new java.awt.Color(156, 183, 133));
        h_addHabitCardPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitCardPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_addHabitDisplay.setBackground(new java.awt.Color(156, 183, 133));
        h_addHabitDisplay.setOpaque(false);
        h_addHabitDisplay.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        h_addHabitCardPanel.add(h_addHabitDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 200));

        h_addHabitSaveButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitSaveButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addHabitSaveButton.setForeground(java.awt.Color.white);
        h_addHabitSaveButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitSaveButton.setText("Save Habit");
        h_addHabitSaveButton.setToolTipText("");
        h_addHabitSaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitSaveButton.setOpaque(true);
        h_addHabitCardPanel.add(h_addHabitSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, 190, 90));

        h_addHabitResetBigButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitResetBigButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addHabitResetBigButton.setForeground(java.awt.Color.white);
        h_addHabitResetBigButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_addHabitResetBigButton.setText("Restart");
        h_addHabitResetBigButton.setToolTipText("");
        h_addHabitResetBigButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitResetBigButton.setOpaque(true);
        h_addHabitResetBigButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitResetButtonMouseClicked(evt);
            }
        });
        h_addHabitCardPanel.add(h_addHabitResetBigButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 190, 100));

        h_addHabitPanel.add(h_addHabitCardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 255, 450, 220));

        h_addHabitDaysPanel.setBackground(new java.awt.Color(156, 183, 133));
        h_addHabitDaysPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Choose The Days For This Habit");
        h_addHabitDaysPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 40));

        h_addHabitSaveDaysButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitSaveDaysButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitSaveDaysButton.setForeground(java.awt.Color.white);
        h_addHabitSaveDaysButton.setText("Save Days");
        h_addHabitSaveDaysButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitSaveDaysButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitSaveDaysButtonMouseClicked(evt);
            }
        });
        h_addHabitDaysPanel.add(h_addHabitSaveDaysButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 400, 30));

        h_addHabitSunday.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitSunday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitSunday.setForeground(java.awt.Color.white);
        h_addHabitSunday.setText("Sunday");
        h_addHabitSunday.setToolTipText("");
        h_addHabitSunday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.add(h_addHabitSunday, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 90, 50));

        h_addHabitMonday.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitMonday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitMonday.setForeground(java.awt.Color.white);
        h_addHabitMonday.setText("Monday");
        h_addHabitMonday.setToolTipText("");
        h_addHabitMonday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.add(h_addHabitMonday, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 90, 50));

        h_addHabitTuesday.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitTuesday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitTuesday.setForeground(java.awt.Color.white);
        h_addHabitTuesday.setText("Tuesday");
        h_addHabitTuesday.setToolTipText("");
        h_addHabitTuesday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.add(h_addHabitTuesday, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 90, 50));

        h_addHabitWednesday.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitWednesday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitWednesday.setForeground(java.awt.Color.white);
        h_addHabitWednesday.setText("Wednesday");
        h_addHabitWednesday.setToolTipText("");
        h_addHabitWednesday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.add(h_addHabitWednesday, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 90, 50));

        h_addHabitThursday.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitThursday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitThursday.setForeground(java.awt.Color.white);
        h_addHabitThursday.setText("Thursday");
        h_addHabitThursday.setToolTipText("");
        h_addHabitThursday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.add(h_addHabitThursday, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 90, 50));

        h_addHabitFriday.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitFriday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitFriday.setForeground(java.awt.Color.white);
        h_addHabitFriday.setText("Friday");
        h_addHabitFriday.setToolTipText("");
        h_addHabitFriday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.add(h_addHabitFriday, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 90, 50));

        h_addHabitSaturday.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitSaturday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        h_addHabitSaturday.setForeground(java.awt.Color.white);
        h_addHabitSaturday.setText("Saturday");
        h_addHabitSaturday.setToolTipText("");
        h_addHabitSaturday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitDaysPanel.add(h_addHabitSaturday, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 90, 50));

        h_addHabitPanel.add(h_addHabitDaysPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 255, 450, 220));

        home.add(h_addHabitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 55, 1030, 490));

        h_editHabitPanel.setBackground(new java.awt.Color(181, 181, 181));
        h_editHabitPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_editHabitPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_editHabitTitle.setBackground(new java.awt.Color(156, 183, 133));
        h_editHabitTitle.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        h_editHabitTitle.setForeground(java.awt.Color.white);
        h_editHabitTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_editHabitTitle.setText("Edit Habit");
        h_editHabitTitle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_editHabitTitle.setOpaque(true);
        h_editHabitPanel.add(h_editHabitTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1000, 50));

        home.add(h_editHabitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 55, 1030, 490));

        h_scrollUpButton.setBackground(new java.awt.Color(169, 198, 144));
        h_scrollUpButton.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        h_scrollUpButton.setForeground(java.awt.Color.white);
        h_scrollUpButton.setText("Scroll Up");
        h_scrollUpButton.setToolTipText("");
        h_scrollUpButton.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        h_scrollUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_scrollButtonMouseClicked(evt);
            }
        });
        home.add(h_scrollUpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 55, 600, 25));

        h_scrollDownButton.setBackground(new java.awt.Color(169, 198, 144));
        h_scrollDownButton.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        h_scrollDownButton.setForeground(java.awt.Color.white);
        h_scrollDownButton.setText("Scroll Down");
        h_scrollDownButton.setToolTipText("");
        h_scrollDownButton.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        h_scrollDownButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_scrollButtonMouseClicked(evt);
            }
        });
        home.add(h_scrollDownButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 520, 600, 25));

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

        h_addHabitButton.setBackground(new java.awt.Color(128, 161, 98));
        h_addHabitButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_addHabitButton.setForeground(java.awt.Color.white);
        h_addHabitButton.setText("+ Add Habit");
        h_addHabitButton.setToolTipText("");
        h_addHabitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_addHabitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_addHabitButtonMouseClicked(evt);
            }
        });
        h_bottomPanel.add(h_addHabitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 8, 155, 36));

        h_editHistoryButton.setBackground(new java.awt.Color(128, 161, 98));
        h_editHistoryButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_editHistoryButton.setForeground(java.awt.Color.white);
        h_editHistoryButton.setText("Edit History");
        h_editHistoryButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_editHistoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_editHistoryButtonMouseClicked(evt);
            }
        });
        h_bottomPanel.add(h_editHistoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 8, 320, 36));

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

        h_editHabitButton.setBackground(new java.awt.Color(128, 161, 98));
        h_editHabitButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        h_editHabitButton.setForeground(java.awt.Color.white);
        h_editHabitButton.setText("Edit Habit");
        h_editHabitButton.setToolTipText("");
        h_editHabitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_editHabitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_editHabitButtonMouseClicked(evt);
            }
        });
        h_bottomPanel.add(h_editHabitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 8, 160, 36));

        home.add(h_bottomPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 550, 1040, 50));

        h_scrollPane.setBackground(new java.awt.Color(204, 204, 204));
        h_scrollPane.setBorder(null);
        h_scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        h_scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        h_scrollPane.setMaximumSize(new java.awt.Dimension(1000, 1000));
        h_scrollPane.setMinimumSize(new java.awt.Dimension(1000, 1000));
        h_scrollPane.setPreferredSize(new java.awt.Dimension(1000, 1000));

        h_habitPanel.setBackground(new java.awt.Color(204, 204, 204));
        h_habitPanel.setMaximumSize(new java.awt.Dimension(1000, 1000));
        h_habitPanel.setMinimumSize(new java.awt.Dimension(1000, 1000));
        h_habitPanel.setPreferredSize(new java.awt.Dimension(1000, 1000));
        h_habitPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 40, 10));
        h_scrollPane.setViewportView(h_habitPanel);

        home.add(h_scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 85, 1000, 430));

        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        progress.setBackground(new java.awt.Color(204, 204, 204));
        progress.setMaximumSize(new java.awt.Dimension(1040, 600));
        progress.setMinimumSize(new java.awt.Dimension(1040, 600));
        progress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayAndClicked(evt);
            }
        });
        progress.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_topPanel.setBackground(new java.awt.Color(156, 183, 133));
        p_topPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_topPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayAndClicked(evt);
            }
        });
        p_topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_progressTitle.setBackground(new java.awt.Color(128, 161, 98));
        p_progressTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        p_progressTitle.setForeground(java.awt.Color.white);
        p_progressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_progressTitle.setText("Progress Center");
        p_progressTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayAndClicked(evt);
            }
        });
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
            h_addHabitPanel.setVisible(false);       // Hiding the add habit panel until user enters there 
            h_editHabitPanel.setVisible(false);      // Hiding the edit habit panel until user enters there 
            h_habitPanel.setVisible(true);           // Showing the habit panel since this is the starting state
            h_addHabitButton.setText("+ Add Habit"); // Reset button text
            h_editHabitButton.setText("Edit Habit"); // Reset button text
            h_scrollUpButton.setVisible(true);       // Show the scroll button again
            h_scrollDownButton.setVisible(true);     // Show the scroll button again
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
    private final Timer awayFromScreen = new Timer(1000, e->{
        awayFromScreenCounter++;           // Increase counter
        if(awayFromScreenCounter >= AWAY_FROM_SCREEN_TIME){  // IF: checking if we reached the max time
            ((Timer)e.getSource()).stop(); // Stop Timer
            switchFrame(progress_again);   // Take back to progress
            awayFromScreenCounter = 0;     // Reset counter
            isAway = true;                 // Setting up the isAway to true so clicks can send back into menu
            // ---------------------------------------------------------------->  Make brightness lower [ADD LATER]
        }
    });
    
    public void resetAway(){
        awayFromScreenCounter = 0; // This should alone "reset" the timer to start from the beginning of the counting
    }
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
        Color temp = new Color(156,183,133);
        Color temp1 = new Color(153,153,255);
        Color temp2 = new Color(0,204,255);
        h_habitPanel.add(new HabitCard_quantity(this, "Drink Water", temp2, 0, 8, 1));
        h_habitPanel.add(new HabitCard_quantity(this, "Drink Milk", temp2, 0, 3, 0.5));
        h_habitPanel.add(new HabitCard_YesNo(this, "Dont eat big", temp, true));
        h_habitPanel.add(new HabitCard_YesNo(this, "Run 3 Miles", temp1, false));
        h_habitPanel.add(new HabitCard_YesNo(this, "Run 3 Miles", temp1, false));
        h_habitPanel.add(new HabitCard_YesNo(this, "Run 3 Miles", temp1, false));
        h_habitPanel.add(new HabitCard_YesNo(this, "Run 3 Miles", temp1, false));
        h_habitPanel.add(new HabitCard_YesNo(this, "Wake up at 7am", temp, false));
        h_habitPanel.add(new HabitCard_YesNo(this, "Take out trash", temp, false));
        h_habitPanel.add(new HabitCard_YesNo(this, "LAST ONE", temp1, false));
        
        // Updating the viewport for the scroll pane after adding the habits
        updateScrollPaneSize();
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
            h_settingsButton, h_addHabitButton, h_editHistoryButton, h_progressButton,
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

    private void h_addHabitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitButtonMouseClicked
        // CODE TO CLOSE THE ADD HABIT SCREEN
        if(h_addHabitPanel.isVisible()){                 // If the habit panel is currently visible
            h_addHabitButton.setText("+ Add Habit");     // Reset the text
            h_addHabitPanel.setVisible(false);           // Hide the panel
            
            keyboard.setVisible(false); // Making invisible again
        }
        
        // ACTUAL CODE TO RUN TO SHOW THE ADD HABIT SCREEN
        else{
            h_editHabitPanel.setVisible(false);          // Hiding edit habit in case 
            h_editHabitButton.setText("Edit Habtit");    // Reseting the text just in case
            h_addHabitPanel.setVisible(true);            // Showing the add habit panel
            h_addHabitButton.setText("Close Add Panel"); // Setting the text to close this panel
            
            h_addHabitResetButtonMouseClicked(null); // Reseting everything in the panel 
        }
        
        // If any panel is visbile, hide the buttons, otherwise show them 
        if(h_addHabitPanel.isVisible() || h_editHabitPanel.isVisible()){
            h_scrollUpButton.setVisible(false);           // Hide buttons so that they do not show up when hover
            h_scrollDownButton.setVisible(false);         // Hide buttons so that they do not show up when hover
            h_habitPanel.setVisible(false);               // Hiding the habit panel so the buttons wont bleed through
            
            // Stopping the timer while we are in here
            if(awayFromScreen.isRunning()){
                awayFromScreenCounter = 0;
                awayFromScreen.stop();
            }
        }
        else{
            h_scrollUpButton.setVisible(true);           // Show buttons again
            h_scrollDownButton.setVisible(true);         // Show buttons again
            h_habitPanel.setVisible(true);               // Showing the habit panel
            switchFrame(home);
        }
    }//GEN-LAST:event_h_addHabitButtonMouseClicked

    private void h_editHabitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_editHabitButtonMouseClicked
        // CODE TO CLOSE THE EDIT HABIT SCREEN
        if(h_editHabitPanel.isVisible()){                  // If edit panel is currently visible
            h_editHabitButton.setText("Edit Habit");       // Reset the text
            h_editHabitPanel.setVisible(false);            // Hide the panel
        }
        
        // ACTUAL CODE TO RUN TO SHOW THE EDIT HABIT SCREEN
        else{
            h_addHabitPanel.setVisible(false);             // Hiding edit habit in case 
            h_addHabitButton.setText("+ Add Habit");       // Resetting the text just in case
            h_editHabitPanel.setVisible(true);             // Showing the add habit panel
            h_editHabitButton.setText("Close Edit Panel"); // Setting the text to close this panel
            
            
            // Stopping the timer while we are in here
            if(awayFromScreen.isRunning()){
                awayFromScreenCounter = 0;
                awayFromScreen.stop();
            }
        }
        
        // If any panel is visbile, hide the buttons, otherwise show them 
        if(h_addHabitPanel.isVisible() || h_editHabitPanel.isVisible()){
            h_scrollUpButton.setVisible(false);           // Hide buttons so that they do not show up when hover
            h_scrollDownButton.setVisible(false);         // Hide buttons so that they do not show up when hover
            h_habitPanel.setVisible(false);               // Hiding so buttons dont bleed through
            
            // Stopping the timer while we are in here
            if(awayFromScreen.isRunning()){
                awayFromScreenCounter = 0;
                awayFromScreen.stop();
            }
        }
        else{
            h_scrollUpButton.setVisible(true);           // Show buttons again
            h_scrollDownButton.setVisible(true);         // Show buttons again
            h_habitPanel.setVisible(true);               // Bringint back the habit panel
            switchFrame(home);
        }
    }//GEN-LAST:event_h_editHabitButtonMouseClicked

    private void h_editHistoryButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_editHistoryButtonMouseClicked
        // show the edit history panel
    }//GEN-LAST:event_h_editHistoryButtonMouseClicked

    private void h_progressButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_progressButtonMouseClicked
        switchFrame(progress); // Swtich to the progress frame
    }//GEN-LAST:event_h_progressButtonMouseClicked

    private void h_scrollButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_scrollButtonMouseClicked
        JButton buttonClicked = (JButton) evt.getSource();
        if(buttonClicked == null) return; // Just in case return this case
        
        // Getting value of the current scroll position
        JScrollBar vBar = h_scrollPane.getVerticalScrollBar();
        
        // Scrolling up or down depending on which button was clicked
        if(buttonClicked == h_scrollUpButton)
            vBar.setValue(vBar.getValue() - 210);   // scroll up 200 + 20 px (card size + gap size)
        else if(buttonClicked == h_scrollDownButton)
            vBar.setValue(vBar.getValue() + 210);   // scroll down 200 + 20 px (card size + gap size)
        
    }//GEN-LAST:event_h_scrollButtonMouseClicked

    
    private void updateScrollPaneSize(){
        int count = h_habitPanel.getComponentCount();   // number of habit cards
        int rows = (int)Math.ceil(count / 4.0);         // Each row has 4 cards
        int contentHeight = rows * (200 + 10) + 10;     // Setting the content height using card height and gap in between

        int contentWidth = h_habitPanel.getWidth();
        h_habitPanel.setPreferredSize(new Dimension(contentWidth, contentHeight));
        h_habitPanel.revalidate();
        h_habitPanel.repaint();
    }
    

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
        // Custom return code for limiting the size of the name for the habit
        if(keyboardTarget == h_addHabitName && h_addHabitName.getText().length() >= 17)
            return;
        
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

    
    
    
    
    // HELPER FUNCTION (SEND EVERYTHING FROM PROGRESS HERE) ============================================================================
    private void awayAndClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_awayAndClicked
        if(isAway){
            isAway = false;
            switchFrame(home);
        }
    }//GEN-LAST:event_awayAndClicked
    // =================================================================================================================================
    
    
    
    
    // =================================================================================================================================
    // ================== [ ADD SCREEN FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
    // User said no, MAKE ONLY YES/NO habit card
    private void h_addHabitNoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitNoButtonMouseClicked
        // Making sure we have a color and a name first and that we have not locked yet
        if(h_addHabitName.getBackground() == Color.RED || h_addHabitChooseColorButton.getBackground() == Color.RED)
            return;
        
        if(h_addHabitName.getText().isEmpty() && h_addHabitName.getBackground() != Color.RED){
            flashTextInput(h_addHabitName);
            return;
        }
        if(h_addHabitColorDisplay.getBackground() == Color.WHITE && h_addHabitChooseColorButton.getBackground() != Color.RED){
            flashButton(h_addHabitChooseColorButton);
            return; 
        }
        if(h_addHabitResetButton.isVisible()) // Do nothing if we are already reset (button should be invis so we shouldnt get here)
            return;

        // Lock the information in and show reset button
        h_addHabitYesButton.setVisible(false);
        h_addHabitNoButton.setVisible(false);
        h_addHabitResetButton.setVisible(true);
        h_addHabitName.setEditable(false);   
        h_addHabitTopPanelText1.setText("Will This Habit Have a Quantity? : No");
        
        
        // Reset YES option (hide the quantity extra info card)
        h_addHabitCover.setVisible(true); // Showing because we do not need any more inputs from use
        h_addHabitDisplay.removeAll();    // Removing any previous things we had here
        
        // Make YESNO card and add it to the addHabitDisplay we have
        h_addHabitDisplay.add(new HabitCard_YesNo(this, h_addHabitName.getText(), h_addHabitColorDisplay.getBackground(), false));
        h_addHabitDisplay.repaint();
        h_addHabitDisplay.revalidate();
        
        // Chaning text on cover to say that everything is finished setting up
        h_addHabitCover.setText("No Information Needed Here");
        
        // Hide keyboard and show habit card
        keyboard.setVisible(false);
        h_addHabitDaysPanel.setVisible(true); // Moving onto next step to set up the days
    }//GEN-LAST:event_h_addHabitNoButtonMouseClicked

    
    // User said yes, MAKE QUANTITY CARD
    private void h_addHabitYesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitYesButtonMouseClicked
        // Making sure that we have a color and a name first and that we have not locked yet
        if(h_addHabitName.getBackground() == Color.RED || h_addHabitChooseColorButton.getBackground() == Color.RED)
            return;
        
        if(h_addHabitName.getText().isEmpty()){
            flashTextInput(h_addHabitName);
            return;
        }
        if(h_addHabitColorDisplay.getBackground() == Color.WHITE){
            flashButton(h_addHabitChooseColorButton);
            return; 
        }
        
        if(h_addHabitResetButton.isVisible())
            return;
        
        // Lock the information in and show reset button
        h_addHabitYesButton.setVisible(false);
        h_addHabitNoButton.setVisible(false);
        h_addHabitResetButton.setVisible(true);
        h_addHabitName.setEditable(false);   
        h_addHabitTopPanelText1.setText("Will This Habit Have a Quantity? : Yes");
        
        // Show the options section and clear inputs there
        h_addHabitCover.setVisible(false);
        h_addHabitQuantityPanel.setVisible(true);
        
        // Hiding the keyboard and showing the number keyboard
        keyboard.setVisible(false);
        // keypad.setVisible(false);
    }//GEN-LAST:event_h_addHabitYesButtonMouseClicked

    private void h_addHabitChooseColorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitChooseColorButtonMouseClicked
        if(h_addHabitResetButton.isVisible()) // If we have locked, do nothing
            return;
        
        // Open the menu to choose a color
        Color chosenColor = JColorChooser.showDialog(null, "Choose a color:", h_addHabitColorDisplay.getBackground());
        
        // If color is valid, make the panel this color
        if(chosenColor != null){
            h_addHabitColorDisplay.setBackground(chosenColor);
        }
    }//GEN-LAST:event_h_addHabitChooseColorButtonMouseClicked

    private void h_addHabitResetButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitResetButtonMouseClicked
        // Reseting the screen as if we are coming here for the first time
        keyboard.setVisible(true);      
        keyboard.setLocation(155,305);  
        keyboard.getParent().repaint();
        keyboard.getParent().revalidate();
        keyboardTarget = h_addHabitName;
        
        h_addHabitName.setText("");
        h_addHabitColorDisplay.setBackground(Color.WHITE);
        h_addHabitName.setEditable(true);   
        h_addHabitTopPanelText1.setText("Will This Habit Have a Quantity?");
        h_addHabitYesButton.setVisible(true);
        h_addHabitNoButton.setVisible(true);
        h_addHabitResetButton.setVisible(false);
        
        h_addHabitCover.setVisible(true);
        h_addHabitCover.setText("To Start Finish Choosing Options On The Left");
        h_addHabitQuantityGoal.setText("0.0");
        h_addHabitIncrementPointOne.setSelected(false);
        h_addHabitIncrementPointFive.setSelected(false);
        h_addHabitIncrementOne.setSelected(false);
        h_addHabitQuantityPanel.setVisible(false);
        
        h_addHabitDaysPanel.setVisible(false);
        h_addHabitMonday.setSelected(false);
        h_addHabitTuesday.setSelected(false);
        h_addHabitWednesday.setSelected(false);
        h_addHabitThursday.setSelected(false);
        h_addHabitFriday.setSelected(false);
        h_addHabitSaturday.setSelected(false);
        h_addHabitSunday.setSelected(false);
        
        h_addHabitCardPanel.setVisible(false);
        h_addHabitDisplay.removeAll();
        h_addHabitDisplay.revalidate();
        h_addHabitDisplay.repaint();
    }//GEN-LAST:event_h_addHabitResetButtonMouseClicked

    private void h_addHabitSaveDaysButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitSaveDaysButtonMouseClicked
        // Making sure at least one day is checked 
        if(!h_addHabitMonday.isSelected() && !h_addHabitTuesday.isSelected() && !h_addHabitWednesday.isSelected() && !h_addHabitThursday.isSelected() && 
           !h_addHabitFriday.isSelected() && !h_addHabitSaturday.isSelected() && !h_addHabitSunday.isSelected()){
            flashToggleButton(h_addHabitMonday);
            flashToggleButton(h_addHabitTuesday);
            flashToggleButton(h_addHabitWednesday);
            flashToggleButton(h_addHabitThursday); 
            flashToggleButton(h_addHabitFriday);
            flashToggleButton(h_addHabitSaturday); 
            flashToggleButton(h_addHabitSunday);
            return;
        }
        
        // Moving on to next step
        h_addHabitCardPanel.setVisible(true);
    }//GEN-LAST:event_h_addHabitSaveDaysButtonMouseClicked

    private void h_addHabitQuantityDecreaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitQuantityDecreaseMouseClicked
        if(h_addHabitIncrementPointOne.getBackground() == Color.RED || h_addHabitIncrementPointFive.getBackground() == Color.RED 
           || h_addHabitIncrementOne.getBackground() == Color.RED)
            return;
        
        if(!h_addHabitIncrementPointOne.isSelected() && !h_addHabitIncrementPointFive.isSelected() && !h_addHabitIncrementOne.isSelected()){
            flashToggleButton(h_addHabitIncrementPointOne);
            flashToggleButton(h_addHabitIncrementPointFive);
            flashToggleButton(h_addHabitIncrementOne);
            return;
        }
        
        // Incrementing and setting the text
        double increment = (h_addHabitIncrementPointOne.isSelected() ? 0.1 : (h_addHabitIncrementPointFive.isSelected() ? 0.5 : 1)); // Complicated but just finds out which increment is chosen
        boolean isIncrease = ((JButton)evt.getSource()).getText().equals("+"); // Finding out if we are increase or decreasing
        if(!isIncrease) // Set to negative if we are decreasing
            increment *= -1;
        
            
        double newValue = Double.parseDouble(h_addHabitQuantityGoal.getText()) + increment;
        newValue = Math.round(newValue * 10.0) / 10.0;
        if(newValue < 0) // Making sure we do not go negative
            newValue = 0;
        h_addHabitQuantityGoal.setText(Double.toString(newValue));
    }//GEN-LAST:event_h_addHabitQuantityDecreaseMouseClicked

    private void h_addHabitQuantitySaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_addHabitQuantitySaveButtonMouseClicked
        if(h_addHabitQuantityDecrease.getBackground() == Color.RED || h_addHabitQuantityIncrease.getBackground() == Color.RED)
           return;
           
        if(Double.parseDouble(h_addHabitQuantityGoal.getText()) == 0){
           flashButton(h_addHabitQuantityDecrease); 
           flashButton(h_addHabitQuantityIncrease); 
           return;
        }
        
        
        h_addHabitCover.setText("Goal To Reach: " + h_addHabitQuantityGoal.getText());
        h_addHabitCover.setVisible(true);
        h_addHabitQuantityPanel.setVisible(false);
        h_addHabitDaysPanel.setVisible(true);
    }//GEN-LAST:event_h_addHabitQuantitySaveButtonMouseClicked
    // =================================================================================================================================



    // =================================================================================================================================
    // ================== [ OTHER HELPER FUNCTIONS ] ===================================================================================
    // =================================================================================================================================
    private void flashButton(JButton target){
        Color previousColor = target.getBackground();
        target.setBackground(Color.RED);
        Timer flashTimer = new Timer(1000,e->{
            target.setBackground(previousColor);
            ((Timer)e.getSource()).stop();
        });
        flashTimer.start();
    }
    private void flashTextInput(JTextField target){
        Color previousColor = target.getBackground();
        target.setBackground(Color.RED);
        Timer flashTimer = new Timer(1000,e->{
            target.setBackground(previousColor);
            ((Timer)e.getSource()).stop();
        });
        flashTimer.start();
    }
    
    private void flashToggleButton(JToggleButton target){
        Color previousColor = target.getBackground();
        target.setBackground(Color.RED);
        Timer flashTimer = new Timer(1000,e->{
            target.setBackground(previousColor);
            ((Timer)e.getSource()).stop();
        });
        flashTimer.start();
    }
    
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
    private javax.swing.JButton h_addHabitButton;
    private javax.swing.JPanel h_addHabitCardPanel;
    private javax.swing.JButton h_addHabitChooseColorButton;
    private javax.swing.JPanel h_addHabitColorDisplay;
    private javax.swing.JLabel h_addHabitCover;
    private javax.swing.JPanel h_addHabitDaysPanel;
    private javax.swing.JPanel h_addHabitDisplay;
    private javax.swing.JToggleButton h_addHabitFriday;
    private javax.swing.JToggleButton h_addHabitIncrementOne;
    private javax.swing.JToggleButton h_addHabitIncrementPointFive;
    private javax.swing.JToggleButton h_addHabitIncrementPointOne;
    private javax.swing.JToggleButton h_addHabitMonday;
    private javax.swing.JTextField h_addHabitName;
    private javax.swing.JLabel h_addHabitNoButton;
    private javax.swing.JPanel h_addHabitPanel;
    private javax.swing.JButton h_addHabitQuantityDecrease;
    private javax.swing.JLabel h_addHabitQuantityGoal;
    private javax.swing.JButton h_addHabitQuantityIncrease;
    private javax.swing.JPanel h_addHabitQuantityIncrementPanel;
    private javax.swing.JPanel h_addHabitQuantityPanel;
    private javax.swing.JLabel h_addHabitQuantityPanelText;
    private javax.swing.JLabel h_addHabitQuantityPanelText2;
    private javax.swing.JButton h_addHabitQuantitySaveButton;
    private javax.swing.JLabel h_addHabitResetBigButton;
    private javax.swing.JButton h_addHabitResetButton;
    private javax.swing.JToggleButton h_addHabitSaturday;
    private javax.swing.JLabel h_addHabitSaveButton;
    private javax.swing.JButton h_addHabitSaveDaysButton;
    private javax.swing.JToggleButton h_addHabitSunday;
    private javax.swing.JToggleButton h_addHabitThursday;
    private javax.swing.JLabel h_addHabitTitle;
    private javax.swing.JPanel h_addHabitTopPanel;
    private javax.swing.JLabel h_addHabitTopPanelText;
    private javax.swing.JLabel h_addHabitTopPanelText1;
    private javax.swing.JToggleButton h_addHabitTuesday;
    private javax.swing.JToggleButton h_addHabitWednesday;
    private javax.swing.JLabel h_addHabitYesButton;
    private javax.swing.JPanel h_bottomPanel;
    private javax.swing.JLabel h_date;
    private javax.swing.JLabel h_dateTitle;
    private javax.swing.JButton h_editHabitButton;
    private javax.swing.JPanel h_editHabitPanel;
    private javax.swing.JLabel h_editHabitTitle;
    private javax.swing.JButton h_editHistoryButton;
    private javax.swing.JPanel h_habitPanel;
    private javax.swing.JButton h_progressButton;
    private javax.swing.JButton h_scrollDownButton;
    private javax.swing.JScrollPane h_scrollPane;
    private javax.swing.JButton h_scrollUpButton;
    private javax.swing.JButton h_settingsButton;
    private javax.swing.JPanel h_topPanel;
    private javax.swing.JLabel h_welcomeMessage;
    private javax.swing.JPanel home;
    private javax.swing.JLabel jLabel1;
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
