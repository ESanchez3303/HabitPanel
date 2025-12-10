package habitpanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    String customName = "";           // Holds the custom name if there is any
    boolean isAway = false;           // Holds when the screen is currently away to single click back into home
    boolean awayIsOn = true;          // User can set this up through the settings to turn it off 
    ButtonGroup h_addHabitIncrementButtonGroup = null;
    
    
    ArrayList<HabitCard_Quantity> allQuantityCards = new ArrayList<>(); // Holds all the quantity cards that user has made
    ArrayList<HabitCard_YesNo> allYesNoCards = new ArrayList<>();       // Holds all the yes/no cards that user has made
    
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
        
        
        // Setting up add habit panel (button grouping)
        h_addHabitIncrementButtonGroup = new ButtonGroup();
        h_addHabitIncrementButtonGroup.add(ah_IncrementPointOne);
        h_addHabitIncrementButtonGroup.add(ah_IncrementPointFive);
        h_addHabitIncrementButtonGroup.add(ah_IncrementOne);
        
        
        
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
        topPanel = new javax.swing.JPanel();
        h_welcomeMessage = new javax.swing.JLabel();
        h_dateTitle = new javax.swing.JLabel();
        h_date = new javax.swing.JLabel();
        home = new javax.swing.JPanel();
        h_scrollPane = new javax.swing.JScrollPane();
        h_habitPanel = new javax.swing.JPanel();
        h_scrollUpButton = new javax.swing.JButton();
        h_scrollDownButton = new javax.swing.JButton();
        progress = new javax.swing.JPanel();
        p_progressTitle = new javax.swing.JLabel();
        settings = new javax.swing.JPanel();
        s_title = new javax.swing.JLabel();
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
        s_turnOffAwayFromScreenButton = new javax.swing.JButton();
        editHistory = new javax.swing.JPanel();
        eHist_title = new javax.swing.JLabel();
        addHabit = new javax.swing.JPanel();
        ah_title = new javax.swing.JLabel();
        ah_NamePanel = new javax.swing.JPanel();
        ah_TopPanelText = new javax.swing.JLabel();
        h_addHabitName = new javax.swing.JTextField();
        ah_TopPanelText1 = new javax.swing.JLabel();
        ah_YesButton = new javax.swing.JLabel();
        ah_NoButton = new javax.swing.JLabel();
        ah_ChooseColorButton = new javax.swing.JButton();
        ah_ColorDisplay = new javax.swing.JPanel();
        ah_DaysPanel = new javax.swing.JPanel();
        ah_DaysText = new javax.swing.JLabel();
        ah_SaveDaysButton = new javax.swing.JButton();
        ah_Sunday = new javax.swing.JToggleButton();
        ah_Monday = new javax.swing.JToggleButton();
        ah_Tuesday = new javax.swing.JToggleButton();
        ah_Wednesday = new javax.swing.JToggleButton();
        ah_Thursday = new javax.swing.JToggleButton();
        ah_Friday = new javax.swing.JToggleButton();
        ah_Saturday = new javax.swing.JToggleButton();
        ah_QuantityPanel = new javax.swing.JPanel();
        ah_QuantityPanelText = new javax.swing.JLabel();
        ah_QuantityPanelText2 = new javax.swing.JLabel();
        ah_QuantityIncrementPanel = new javax.swing.JPanel();
        ah_IncrementPointOne = new javax.swing.JToggleButton();
        ah_IncrementPointFive = new javax.swing.JToggleButton();
        ah_IncrementOne = new javax.swing.JToggleButton();
        ah_QuantitySaveButton = new javax.swing.JButton();
        ah_QuantityGoal = new javax.swing.JLabel();
        ah_QuantityDecrease = new javax.swing.JButton();
        ah_QuantityIncrease = new javax.swing.JButton();
        ah_ResetButton = new javax.swing.JButton();
        ah_SummaryPanel = new javax.swing.JPanel();
        ah_SummaryName = new javax.swing.JLabel();
        ah_SummaryText1 = new javax.swing.JLabel();
        ah_SummaryText3 = new javax.swing.JLabel();
        ah_SummaryQuantity = new javax.swing.JLabel();
        ah_SummaryDays = new javax.swing.JLabel();
        ah_SummaryText6 = new javax.swing.JLabel();
        ah_SummaryIncrement = new javax.swing.JLabel();
        ah_SummaryText4 = new javax.swing.JLabel();
        ah_SummaryText5 = new javax.swing.JLabel();
        ah_SummaryGoal = new javax.swing.JLabel();
        ah_SummaryText2 = new javax.swing.JLabel();
        ah_SummaryColor = new javax.swing.JLabel();
        ah_SaveButton = new javax.swing.JLabel();
        ah_CardPanel = new javax.swing.JPanel();
        editHabit = new javax.swing.JPanel();
        eh_title = new javax.swing.JLabel();
        navigationPanel = new javax.swing.JPanel();
        addHabitButton = new javax.swing.JButton();
        editHistoryButton = new javax.swing.JButton();
        progressButton = new javax.swing.JButton();
        editHabitButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        homeButton = new javax.swing.JButton();
        n_homeCover = new javax.swing.JPanel();
        n_settingsCover = new javax.swing.JPanel();
        n_addCover = new javax.swing.JPanel();
        n_editHabitCover = new javax.swing.JPanel();
        n_editHistoryCover = new javax.swing.JPanel();
        n_progressCover = new javax.swing.JPanel();

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

        getContentPane().add(keyboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, -300, 730, 220));

        topPanel.setBackground(new java.awt.Color(156, 183, 133));
        topPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        topPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_welcomeMessage.setBackground(new java.awt.Color(128, 161, 98));
        h_welcomeMessage.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        h_welcomeMessage.setForeground(java.awt.Color.white);
        h_welcomeMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_welcomeMessage.setText("Welcome Back!");
        topPanel.add(h_welcomeMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 5, 470, 40));

        h_dateTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        h_dateTitle.setForeground(java.awt.Color.white);
        h_dateTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_dateTitle.setText("Date:");
        topPanel.add(h_dateTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 5, 100, 40));

        h_date.setBackground(new java.awt.Color(156, 183, 133));
        h_date.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        h_date.setForeground(java.awt.Color.white);
        h_date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_date.setText("Saturday, Nov 22, 2025");
        h_date.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        topPanel.add(h_date, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 6, 300, 40));

        getContentPane().add(topPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, -2, 1042, 52));

        home.setBackground(new java.awt.Color(204, 204, 204));
        home.setMaximumSize(new java.awt.Dimension(1040, 600));
        home.setMinimumSize(new java.awt.Dimension(1040, 600));
        home.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        h_scrollPane.setBackground(new java.awt.Color(204, 204, 204));
        h_scrollPane.setBorder(null);
        h_scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        h_scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        h_scrollPane.setMaximumSize(new java.awt.Dimension(1000, 1000));
        h_scrollPane.setMinimumSize(new java.awt.Dimension(1000, 1000));
        h_scrollPane.setPreferredSize(new java.awt.Dimension(1000, 1000));

        h_habitPanel.setBackground(new java.awt.Color(204, 204, 204));
        h_habitPanel.setMaximumSize(new java.awt.Dimension(850, 1000));
        h_habitPanel.setMinimumSize(new java.awt.Dimension(850, 1000));
        h_habitPanel.setPreferredSize(new java.awt.Dimension(850, 1000));
        h_habitPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10));
        h_scrollPane.setViewportView(h_habitPanel);

        home.add(h_scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 60, 974, 430));

        h_scrollUpButton.setBackground(new java.awt.Color(181, 181, 181));
        h_scrollUpButton.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        h_scrollUpButton.setForeground(java.awt.Color.white);
        h_scrollUpButton.setText("Scroll Up");
        h_scrollUpButton.setToolTipText("");
        h_scrollUpButton.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        h_scrollUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_scrollButtonMouseClicked(evt);
            }
        });
        home.add(h_scrollUpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 910, 40));

        h_scrollDownButton.setBackground(new java.awt.Color(181, 181, 181));
        h_scrollDownButton.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        h_scrollDownButton.setForeground(java.awt.Color.white);
        h_scrollDownButton.setText("Scroll Down");
        h_scrollDownButton.setToolTipText("");
        h_scrollDownButton.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        h_scrollDownButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                h_scrollButtonMouseClicked(evt);
            }
        });
        home.add(h_scrollDownButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 500, 910, 40));

        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 970, 550));

        progress.setBackground(new java.awt.Color(204, 204, 204));
        progress.setMaximumSize(new java.awt.Dimension(1040, 600));
        progress.setMinimumSize(new java.awt.Dimension(1040, 600));
        progress.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayAndClicked(evt);
            }
        });
        progress.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_progressTitle.setBackground(new java.awt.Color(156, 183, 133));
        p_progressTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        p_progressTitle.setForeground(java.awt.Color.white);
        p_progressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_progressTitle.setText("Progress Center");
        p_progressTitle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_progressTitle.setOpaque(true);
        p_progressTitle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayAndClicked(evt);
            }
        });
        progress.add(p_progressTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

        getContentPane().add(progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 970, 550));

        settings.setBackground(new java.awt.Color(204, 204, 204));
        settings.setMaximumSize(new java.awt.Dimension(1040, 600));
        settings.setMinimumSize(new java.awt.Dimension(1040, 600));
        settings.setName(""); // NOI18N
        settings.setPreferredSize(new java.awt.Dimension(1040, 600));
        settings.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_title.setBackground(new java.awt.Color(156, 183, 133));
        s_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        s_title.setForeground(java.awt.Color.white);
        s_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_title.setText("Settings");
        s_title.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_title.setOpaque(true);
        settings.add(s_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

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

        settings.add(s_colorsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 380, 220));

        s_customNamePanel.setBackground(new java.awt.Color(156, 183, 133));
        s_customNamePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_customNamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_customNameTitle.setBackground(new java.awt.Color(156, 183, 133));
        s_customNameTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_customNameTitle.setForeground(java.awt.Color.white);
        s_customNameTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_customNameTitle.setText("Set Up Custom Name:");
        s_customNamePanel.add(s_customNameTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 560, -1));

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
        s_customNamePanel.add(s_customNameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 530, 40));

        s_customNameTitle2.setBackground(new java.awt.Color(156, 183, 133));
        s_customNameTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_customNameTitle2.setForeground(java.awt.Color.white);
        s_customNameTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_customNameTitle2.setText("Current Saved:");
        s_customNamePanel.add(s_customNameTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 45, 260, 30));

        s_customName.setBackground(new java.awt.Color(156, 183, 133));
        s_customName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_customName.setForeground(java.awt.Color.white);
        s_customNamePanel.add(s_customName, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 45, 260, 30));

        settings.add(s_customNamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 560, 140));

        s_customNameInput.setBackground(new java.awt.Color(156, 183, 133));
        s_customNameInput.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        s_customNameInput.setForeground(java.awt.Color.white);
        s_customNameInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        s_customNameInput.setText("customnamehere");
        settings.add(s_customNameInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 520, 61));

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
        settings.add(s_saveCustomNameButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 500, 730, 40));

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
        s_awayFromScreenPanel.add(s_awayFromScreenInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 300, 40));

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
        s_awayFromScreenPanel.add(s_awayFromScreenButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 340, 30));

        settings.add(s_awayFromScreenPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 380, 200));

        s_connectionPanel.setBackground(new java.awt.Color(156, 183, 133));
        s_connectionPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout s_connectionPanelLayout = new javax.swing.GroupLayout(s_connectionPanel);
        s_connectionPanel.setLayout(s_connectionPanelLayout);
        s_connectionPanelLayout.setHorizontalGroup(
            s_connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        s_connectionPanelLayout.setVerticalGroup(
            s_connectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        settings.add(s_connectionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 220, 560, 310));

        s_turnOffAwayFromScreenButton.setBackground(new java.awt.Color(128, 161, 98));
        s_turnOffAwayFromScreenButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        s_turnOffAwayFromScreenButton.setForeground(java.awt.Color.white);
        s_turnOffAwayFromScreenButton.setText("Turn Off Away From Screen");
        s_turnOffAwayFromScreenButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_turnOffAwayFromScreenButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_turnOffAwayFromScreenButtonMouseClicked(evt);
            }
        });
        settings.add(s_turnOffAwayFromScreenButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 380, 30));

        getContentPane().add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 970, 550));

        editHistory.setBackground(new java.awt.Color(204, 204, 204));
        editHistory.setMaximumSize(new java.awt.Dimension(1040, 600));
        editHistory.setMinimumSize(new java.awt.Dimension(1040, 600));
        editHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editHistoryawayAndClicked(evt);
            }
        });
        editHistory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eHist_title.setBackground(new java.awt.Color(156, 183, 133));
        eHist_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eHist_title.setForeground(java.awt.Color.white);
        eHist_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eHist_title.setText("Edit Past Entries");
        eHist_title.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eHist_title.setOpaque(true);
        eHist_title.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eHist_titleawayAndClicked(evt);
            }
        });
        editHistory.add(eHist_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

        getContentPane().add(editHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 970, 550));

        addHabit.setBackground(new java.awt.Color(181, 181, 181));
        addHabit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_title.setBackground(new java.awt.Color(156, 183, 133));
        ah_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ah_title.setForeground(java.awt.Color.white);
        ah_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_title.setText("Add New Habit");
        ah_title.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_title.setOpaque(true);
        addHabit.add(ah_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

        ah_NamePanel.setBackground(new java.awt.Color(156, 183, 133));
        ah_NamePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_NamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_TopPanelText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_TopPanelText.setForeground(java.awt.Color.white);
        ah_TopPanelText.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ah_TopPanelText.setText("Habit Name:");
        ah_NamePanel.add(ah_TopPanelText, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 100, 30));

        h_addHabitName.setBackground(java.awt.Color.white);
        h_addHabitName.setForeground(java.awt.Color.black);
        h_addHabitName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        ah_NamePanel.add(h_addHabitName, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 20, 220, 30));

        ah_TopPanelText1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_TopPanelText1.setForeground(java.awt.Color.white);
        ah_TopPanelText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_TopPanelText1.setText("Will This Habit Have a Quantity?");
        ah_NamePanel.add(ah_TopPanelText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 360, 20));

        ah_YesButton.setBackground(new java.awt.Color(128, 161, 98));
        ah_YesButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ah_YesButton.setForeground(java.awt.Color.white);
        ah_YesButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_YesButton.setText("Yes");
        ah_YesButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_YesButton.setOpaque(true);
        ah_YesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_YesButtonMouseClicked(evt);
            }
        });
        ah_NamePanel.add(ah_YesButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 150, 30));

        ah_NoButton.setBackground(new java.awt.Color(128, 161, 98));
        ah_NoButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ah_NoButton.setForeground(java.awt.Color.white);
        ah_NoButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_NoButton.setText("No");
        ah_NoButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_NoButton.setOpaque(true);
        ah_NoButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_NoButtonMouseClicked(evt);
            }
        });
        ah_NamePanel.add(ah_NoButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 140, 150, 30));

        ah_ChooseColorButton.setBackground(new java.awt.Color(128, 161, 98));
        ah_ChooseColorButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_ChooseColorButton.setForeground(java.awt.Color.white);
        ah_ChooseColorButton.setText("Choose a Color");
        ah_ChooseColorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_ChooseColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_ChooseColorButtonMouseClicked(evt);
            }
        });
        ah_NamePanel.add(ah_ChooseColorButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 190, 30));

        ah_ColorDisplay.setBackground(java.awt.Color.white);
        ah_ColorDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        javax.swing.GroupLayout ah_ColorDisplayLayout = new javax.swing.GroupLayout(ah_ColorDisplay);
        ah_ColorDisplay.setLayout(ah_ColorDisplayLayout);
        ah_ColorDisplayLayout.setHorizontalGroup(
            ah_ColorDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        ah_ColorDisplayLayout.setVerticalGroup(
            ah_ColorDisplayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        ah_NamePanel.add(ah_ColorDisplay, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 140, 30));

        addHabit.add(ah_NamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 85, 390, 190));

        ah_DaysPanel.setBackground(new java.awt.Color(156, 183, 133));
        ah_DaysPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_DaysText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_DaysText.setForeground(java.awt.Color.white);
        ah_DaysText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_DaysText.setText("Choose The Days For This Habit");
        ah_DaysPanel.add(ah_DaysText, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 40));

        ah_SaveDaysButton.setBackground(new java.awt.Color(128, 161, 98));
        ah_SaveDaysButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SaveDaysButton.setForeground(java.awt.Color.white);
        ah_SaveDaysButton.setText("Save Days");
        ah_SaveDaysButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_SaveDaysButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_SaveDaysButtonMouseClicked(evt);
            }
        });
        ah_DaysPanel.add(ah_SaveDaysButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 400, 30));

        ah_Sunday.setBackground(new java.awt.Color(128, 161, 98));
        ah_Sunday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_Sunday.setForeground(java.awt.Color.white);
        ah_Sunday.setText("Sunday");
        ah_Sunday.setToolTipText("");
        ah_Sunday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.add(ah_Sunday, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 90, 50));

        ah_Monday.setBackground(new java.awt.Color(128, 161, 98));
        ah_Monday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_Monday.setForeground(java.awt.Color.white);
        ah_Monday.setText("Monday");
        ah_Monday.setToolTipText("");
        ah_Monday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.add(ah_Monday, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 90, 50));

        ah_Tuesday.setBackground(new java.awt.Color(128, 161, 98));
        ah_Tuesday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_Tuesday.setForeground(java.awt.Color.white);
        ah_Tuesday.setText("Tuesday");
        ah_Tuesday.setToolTipText("");
        ah_Tuesday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.add(ah_Tuesday, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 90, 50));

        ah_Wednesday.setBackground(new java.awt.Color(128, 161, 98));
        ah_Wednesday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_Wednesday.setForeground(java.awt.Color.white);
        ah_Wednesday.setText("Wednesday");
        ah_Wednesday.setToolTipText("");
        ah_Wednesday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.add(ah_Wednesday, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 40, 90, 50));

        ah_Thursday.setBackground(new java.awt.Color(128, 161, 98));
        ah_Thursday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_Thursday.setForeground(java.awt.Color.white);
        ah_Thursday.setText("Thursday");
        ah_Thursday.setToolTipText("");
        ah_Thursday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.add(ah_Thursday, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 40, 90, 50));

        ah_Friday.setBackground(new java.awt.Color(128, 161, 98));
        ah_Friday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_Friday.setForeground(java.awt.Color.white);
        ah_Friday.setText("Friday");
        ah_Friday.setToolTipText("");
        ah_Friday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.add(ah_Friday, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 90, 50));

        ah_Saturday.setBackground(new java.awt.Color(128, 161, 98));
        ah_Saturday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_Saturday.setForeground(java.awt.Color.white);
        ah_Saturday.setText("Saturday");
        ah_Saturday.setToolTipText("");
        ah_Saturday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_DaysPanel.add(ah_Saturday, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 90, 50));

        addHabit.add(ah_DaysPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 450, 210));

        ah_QuantityPanel.setBackground(new java.awt.Color(156, 183, 133));
        ah_QuantityPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_QuantityPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_QuantityPanelText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_QuantityPanelText.setForeground(java.awt.Color.white);
        ah_QuantityPanelText.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ah_QuantityPanelText.setText("Goal To Reach:");
        ah_QuantityPanel.add(ah_QuantityPanelText, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 140, 40));

        ah_QuantityPanelText2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_QuantityPanelText2.setForeground(java.awt.Color.white);
        ah_QuantityPanelText2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ah_QuantityPanelText2.setText("Increment:");
        ah_QuantityPanelText2.setToolTipText("");
        ah_QuantityPanel.add(ah_QuantityPanelText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 140, 40));

        ah_QuantityIncrementPanel.setOpaque(false);
        ah_QuantityIncrementPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_IncrementPointOne.setBackground(new java.awt.Color(128, 161, 98));
        ah_IncrementPointOne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ah_IncrementPointOne.setForeground(java.awt.Color.white);
        ah_IncrementPointOne.setText("+0.1");
        ah_IncrementPointOne.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_QuantityIncrementPanel.add(ah_IncrementPointOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 0, 80, 40));

        ah_IncrementPointFive.setBackground(new java.awt.Color(128, 161, 98));
        ah_IncrementPointFive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ah_IncrementPointFive.setForeground(java.awt.Color.white);
        ah_IncrementPointFive.setText("+0.5");
        ah_IncrementPointFive.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_QuantityIncrementPanel.add(ah_IncrementPointFive, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 80, 40));

        ah_IncrementOne.setBackground(new java.awt.Color(128, 161, 98));
        ah_IncrementOne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        ah_IncrementOne.setForeground(java.awt.Color.white);
        ah_IncrementOne.setText("+1");
        ah_IncrementOne.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_QuantityIncrementPanel.add(ah_IncrementOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(185, 0, 80, 40));

        ah_QuantityPanel.add(ah_QuantityIncrementPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 280, 40));

        ah_QuantitySaveButton.setBackground(new java.awt.Color(128, 161, 98));
        ah_QuantitySaveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_QuantitySaveButton.setForeground(java.awt.Color.white);
        ah_QuantitySaveButton.setText("Save Settings");
        ah_QuantitySaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_QuantitySaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_QuantitySaveButtonMouseClicked(evt);
            }
        });
        ah_QuantityPanel.add(ah_QuantitySaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 440, 50));

        ah_QuantityGoal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_QuantityGoal.setForeground(java.awt.Color.white);
        ah_QuantityGoal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_QuantityGoal.setText("9999");
        ah_QuantityPanel.add(ah_QuantityGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 80, 80, 40));

        ah_QuantityDecrease.setBackground(new java.awt.Color(128, 161, 98));
        ah_QuantityDecrease.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ah_QuantityDecrease.setForeground(java.awt.Color.white);
        ah_QuantityDecrease.setText("-");
        ah_QuantityDecrease.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_QuantityDecrease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_QuantityDecreaseMouseClicked(evt);
            }
        });
        ah_QuantityPanel.add(ah_QuantityDecrease, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 80, 50, 40));

        ah_QuantityIncrease.setBackground(new java.awt.Color(128, 161, 98));
        ah_QuantityIncrease.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ah_QuantityIncrease.setForeground(java.awt.Color.white);
        ah_QuantityIncrease.setText("+");
        ah_QuantityIncrease.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_QuantityIncrease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_QuantityDecreaseMouseClicked(evt);
            }
        });
        ah_QuantityPanel.add(ah_QuantityIncrease, new org.netbeans.lib.awtextra.AbsoluteConstraints(355, 80, 50, 40));

        addHabit.add(ah_QuantityPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, 460, 210));

        ah_ResetButton.setBackground(new java.awt.Color(128, 161, 98));
        ah_ResetButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_ResetButton.setForeground(java.awt.Color.white);
        ah_ResetButton.setText("Reset Habit");
        ah_ResetButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_ResetButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_ResetButtonMouseClicked(evt);
            }
        });
        addHabit.add(ah_ResetButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 270, 40));

        ah_SummaryPanel.setBackground(new java.awt.Color(156, 183, 133));
        ah_SummaryPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_SummaryPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_SummaryName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryName.setForeground(java.awt.Color.white);
        ah_SummaryName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryName.setText("ppppppppppppppppk");
        ah_SummaryPanel.add(ah_SummaryName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 175, 30));

        ah_SummaryText1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText1.setForeground(java.awt.Color.white);
        ah_SummaryText1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText1.setText("Name:");
        ah_SummaryPanel.add(ah_SummaryText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 75, 30));

        ah_SummaryText3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText3.setForeground(java.awt.Color.white);
        ah_SummaryText3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText3.setText("Quantity:");
        ah_SummaryPanel.add(ah_SummaryText3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 75, 75, 30));

        ah_SummaryQuantity.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryQuantity.setForeground(java.awt.Color.white);
        ah_SummaryQuantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryQuantity.setText("Yes/No");
        ah_SummaryPanel.add(ah_SummaryQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 75, 175, 30));

        ah_SummaryDays.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryDays.setForeground(java.awt.Color.white);
        ah_SummaryDays.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryDays.setText("M/T/W/Th/F/S/Su");
        ah_SummaryPanel.add(ah_SummaryDays, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 165, 175, 30));

        ah_SummaryText6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText6.setForeground(java.awt.Color.white);
        ah_SummaryText6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText6.setText("Days:");
        ah_SummaryPanel.add(ah_SummaryText6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 165, 75, 30));

        ah_SummaryIncrement.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryIncrement.setForeground(java.awt.Color.white);
        ah_SummaryIncrement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryIncrement.setText("+1");
        ah_SummaryPanel.add(ah_SummaryIncrement, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 105, 175, 30));

        ah_SummaryText4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText4.setForeground(java.awt.Color.white);
        ah_SummaryText4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText4.setText("Increment:");
        ah_SummaryPanel.add(ah_SummaryText4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 105, 75, 30));

        ah_SummaryText5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText5.setForeground(java.awt.Color.white);
        ah_SummaryText5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText5.setText("Goal:");
        ah_SummaryPanel.add(ah_SummaryText5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 75, 30));

        ah_SummaryGoal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryGoal.setForeground(java.awt.Color.white);
        ah_SummaryGoal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryGoal.setText("9999");
        ah_SummaryPanel.add(ah_SummaryGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 135, 175, 30));

        ah_SummaryText2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText2.setForeground(java.awt.Color.white);
        ah_SummaryText2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText2.setText("Color:");
        ah_SummaryPanel.add(ah_SummaryText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 45, 75, 30));

        ah_SummaryColor.setBackground(java.awt.Color.white);
        ah_SummaryColor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryColor.setForeground(java.awt.Color.white);
        ah_SummaryColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryColor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_SummaryColor.setOpaque(true);
        ah_SummaryPanel.add(ah_SummaryColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 45, 175, 30));

        addHabit.add(ah_SummaryPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 270, 210));

        ah_SaveButton.setBackground(new java.awt.Color(128, 161, 98));
        ah_SaveButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        ah_SaveButton.setForeground(java.awt.Color.white);
        ah_SaveButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SaveButton.setText("Save Habit");
        ah_SaveButton.setToolTipText("");
        ah_SaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_SaveButton.setOpaque(true);
        ah_SaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ah_SaveButtonMouseClicked(evt);
            }
        });
        addHabit.add(ah_SaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 450, 40));

        ah_CardPanel.setBackground(new java.awt.Color(156, 183, 133));
        ah_CardPanel.setOpaque(false);
        ah_CardPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
        addHabit.add(ah_CardPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, 200, 200));

        getContentPane().add(addHabit, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 970, 550));

        editHabit.setBackground(new java.awt.Color(181, 181, 181));
        editHabit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_title.setBackground(new java.awt.Color(156, 183, 133));
        eh_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_title.setForeground(java.awt.Color.white);
        eh_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_title.setText("Edit Habit");
        eh_title.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_title.setOpaque(true);
        editHabit.add(eh_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

        getContentPane().add(editHabit, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 50, 970, 550));

        navigationPanel.setBackground(new java.awt.Color(156, 183, 133));
        navigationPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        navigationPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addHabitButton.setBackground(new java.awt.Color(156, 183, 133));
        addHabitButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        addHabitButton.setForeground(java.awt.Color.white);
        addHabitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/addIcon.png"))); // NOI18N
        addHabitButton.setToolTipText("");
        addHabitButton.setBorder(null);
        addHabitButton.setContentAreaFilled(false);
        addHabitButton.setFocusPainted(false);
        addHabitButton.setFocusable(false);
        addHabitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addHabitButtonMouseClicked(evt);
            }
        });
        navigationPanel.add(addHabitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 195, 60, 60));

        editHistoryButton.setBackground(new java.awt.Color(156, 183, 133));
        editHistoryButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        editHistoryButton.setForeground(java.awt.Color.white);
        editHistoryButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/historyIcon.png"))); // NOI18N
        editHistoryButton.setToolTipText("");
        editHistoryButton.setBorder(null);
        editHistoryButton.setContentAreaFilled(false);
        editHistoryButton.setFocusPainted(false);
        editHistoryButton.setFocusable(false);
        editHistoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editHistoryButtonMouseClicked(evt);
            }
        });
        navigationPanel.add(editHistoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 375, 60, 60));

        progressButton.setBackground(new java.awt.Color(156, 183, 133));
        progressButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        progressButton.setForeground(java.awt.Color.white);
        progressButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/progressIcon.png"))); // NOI18N
        progressButton.setToolTipText("");
        progressButton.setBorder(null);
        progressButton.setContentAreaFilled(false);
        progressButton.setFocusPainted(false);
        progressButton.setFocusable(false);
        progressButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                progressButtonMouseClicked(evt);
            }
        });
        navigationPanel.add(progressButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 465, 60, 60));

        editHabitButton.setBackground(new java.awt.Color(156, 183, 133));
        editHabitButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        editHabitButton.setForeground(java.awt.Color.white);
        editHabitButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/editIcon.png"))); // NOI18N
        editHabitButton.setToolTipText("");
        editHabitButton.setBorder(null);
        editHabitButton.setContentAreaFilled(false);
        editHabitButton.setFocusPainted(false);
        editHabitButton.setFocusable(false);
        editHabitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editHabitButtonMouseClicked(evt);
            }
        });
        navigationPanel.add(editHabitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 285, 60, 60));

        settingsButton.setBackground(new java.awt.Color(156, 183, 133));
        settingsButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        settingsButton.setForeground(java.awt.Color.white);
        settingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/settingsIcon.png"))); // NOI18N
        settingsButton.setBorder(null);
        settingsButton.setContentAreaFilled(false);
        settingsButton.setFocusPainted(false);
        settingsButton.setFocusable(false);
        settingsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                settingsButtonMouseClicked(evt);
            }
        });
        navigationPanel.add(settingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 105, 60, 60));

        homeButton.setBackground(new java.awt.Color(156, 183, 133));
        homeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/homeIcon.png"))); // NOI18N
        homeButton.setBorder(null);
        homeButton.setContentAreaFilled(false);
        homeButton.setFocusPainted(false);
        homeButton.setFocusable(false);
        homeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeButtonMouseClicked(evt);
            }
        });
        navigationPanel.add(homeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 15, 60, 60));

        n_homeCover.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout n_homeCoverLayout = new javax.swing.GroupLayout(n_homeCover);
        n_homeCover.setLayout(n_homeCoverLayout);
        n_homeCoverLayout.setHorizontalGroup(
            n_homeCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        n_homeCoverLayout.setVerticalGroup(
            n_homeCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        navigationPanel.add(n_homeCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 30, 32, 32));

        n_settingsCover.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout n_settingsCoverLayout = new javax.swing.GroupLayout(n_settingsCover);
        n_settingsCover.setLayout(n_settingsCoverLayout);
        n_settingsCoverLayout.setHorizontalGroup(
            n_settingsCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        n_settingsCoverLayout.setVerticalGroup(
            n_settingsCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        navigationPanel.add(n_settingsCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 120, 32, 32));

        n_addCover.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout n_addCoverLayout = new javax.swing.GroupLayout(n_addCover);
        n_addCover.setLayout(n_addCoverLayout);
        n_addCoverLayout.setHorizontalGroup(
            n_addCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        n_addCoverLayout.setVerticalGroup(
            n_addCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        navigationPanel.add(n_addCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 210, 32, 32));

        n_editHabitCover.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout n_editHabitCoverLayout = new javax.swing.GroupLayout(n_editHabitCover);
        n_editHabitCover.setLayout(n_editHabitCoverLayout);
        n_editHabitCoverLayout.setHorizontalGroup(
            n_editHabitCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        n_editHabitCoverLayout.setVerticalGroup(
            n_editHabitCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        navigationPanel.add(n_editHabitCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 300, 32, 32));

        n_editHistoryCover.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout n_editHistoryCoverLayout = new javax.swing.GroupLayout(n_editHistoryCover);
        n_editHistoryCover.setLayout(n_editHistoryCoverLayout);
        n_editHistoryCoverLayout.setHorizontalGroup(
            n_editHistoryCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );
        n_editHistoryCoverLayout.setVerticalGroup(
            n_editHistoryCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 32, Short.MAX_VALUE)
        );

        navigationPanel.add(n_editHistoryCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 390, 32, 32));

        n_progressCover.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout n_progressCoverLayout = new javax.swing.GroupLayout(n_progressCover);
        n_progressCover.setLayout(n_progressCoverLayout);
        n_progressCoverLayout.setHorizontalGroup(
            n_progressCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );
        n_progressCoverLayout.setVerticalGroup(
            n_progressCoverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        navigationPanel.add(n_progressCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 480, 40, 38));

        getContentPane().add(navigationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 48, 72, 554));

        pack();
    }// </editor-fold>//GEN-END:initComponents

        // SWITCH FRAME FUNCTION: ==========================================================================================================
    private void switchFrame(JPanel target){
        // Hidding all other frames (and keyboard)
        home.setVisible(false);
        settings.setVisible(false);
        progress.setVisible(false);
        keyboard.setVisible(false);
        addHabit.setVisible(false);
        editHabit.setVisible(false);
        editHistory.setVisible(false);
        
        n_homeCover.setVisible(false);
        n_settingsCover.setVisible(false);
        n_addCover.setVisible(false);
        n_editHabitCover.setVisible(false);
        n_editHistoryCover.setVisible(false);
        n_progressCover.setVisible(false);
        
        // Stoping timer to reset since we are moving screens
        awayFromScreenCounter = 0;     // Reseting counter
        isAway = false;                // If we switched frames, then user just used the panel
        if(awayFromScreen.isRunning()) // Stopping timer if its running
            awayFromScreen.stop();
        
        // Frames specific instructions for setting up
        if(target == home){
            n_homeCover.setVisible(true);
            h_habitPanel.setVisible(true);       // Showing the habit panel since this is the starting state
            addHabit.setVisible(false);          // Hiding the add habit panel until user enters there 
            editHabit.setVisible(false);         // Hiding the edit habit panel until user enters there 
            refreshHomeData();                   // Reloads data of habit cards using the two arrays we have of them
            if(awayIsOn)                         // Only turn on timer if this is on
                awayFromScreen.start();          // Starting the timer again if we went to home screen
            h_scrollPane.getVerticalScrollBar().setValue(0); // Moving the scroll value to zero
            if(h_habitPanel.getComponentCount() > 8){
                h_scrollUpButton.setVisible(true);   // Show the scroll button again IF we need it 
                h_scrollDownButton.setVisible(true); // Show the scroll button again IF we need it 
            }
            else{
                h_scrollUpButton.setVisible(false);   // Hide the scroll button when we dont need it 
                h_scrollDownButton.setVisible(false); // Hide the scroll button when we dont need it 
            }
        }
        else if(target == settings){
            n_settingsCover.setVisible(true);
            s_customNameInput.setVisible(false);      // Hiding the edit custom name panel
            s_saveCustomNameButton.setVisible(false); // Hiding the save name button
            s_colorsPanel.setVisible(true);           // Showing the color panel
            s_customNamePanel.setVisible(true);       // Showing the custom name panel
            s_connectionPanel.setVisible(true);       // Showing the connection panel
            if(s_turnOffAwayFromScreenButton.getText().equals("Turn Off Away From Screen"))  // If state right now is on, then show panel below
                s_awayFromScreenPanel.setVisible(true);   // Showing the away from screen panel
            else
                s_awayFromScreenPanel.setVisible(false);  // Hiding when state shows that it is off
        }
        else if(target == progress){
            n_progressCover.setVisible(true);
        }
        else if(target == addHabit){
            n_addCover.setVisible(true);
            ah_ResetButtonMouseClicked(null); // Reset the addhabit panel
        }
        else if(target == editHabit){
            n_editHabitCover.setVisible(true);
        }
        else if(target == editHistory){
            n_editHistoryCover.setVisible(true);
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

    // HELPER FUNCTION (SEND EVERYTHING FROM PROGRESS HERE) ============================================================================
    private void awayAndClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_awayAndClicked
        if(isAway){
            isAway = false;
            switchFrame(home);
        }
    }//GEN-LAST:event_awayAndClicked

    

    // Away from screen button is pressed, cycle between turning it on and off
    private void s_turnOffAwayFromScreenButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_turnOffAwayFromScreenButtonMouseClicked
        // State is currently (ON) we are turning (OFF)
        if(s_turnOffAwayFromScreenButton.getText().equals("Turn Off Away From Screen")){
            s_turnOffAwayFromScreenButton.setText("Turn On Away From Screen");
            s_awayFromScreenPanel.setVisible(false);
            awayIsOn = false;
        }
        // State is currently (OFF) we are turning (ON)
        else{
            s_turnOffAwayFromScreenButton.setText("Turn Off Away From Screen");
            s_awayFromScreenPanel.setVisible(true);
            awayIsOn = true;
        }
    }//GEN-LAST:event_s_turnOffAwayFromScreenButtonMouseClicked
    
    
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
        Color temp1 = new Color(153,153,255);
        Color temp2 = new Color(0,204,255);
        String week = "1111111";
        
        // We should only add them to array in here and then later using the refresh screen for the home, we should check the week
        allQuantityCards.add(new HabitCard_Quantity(this, "Drink Water", temp2, 0, 8, 1, week));
        allQuantityCards.add(new HabitCard_Quantity(this, "Drink Milk", temp2, 0, 3, 0.5, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Dont eat big", temp1, true,week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Run 3 Miles", temp1, false,week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Seomthing", temp1, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Again", temp1, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Again1", temp1, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Againfs1", temp1, false, week));
        
        
        // Refreshing happens later in constructor when we call switchFrame(home);
    }
    
    // PAINTS ALL THE PANELS
    private void paintColors(){
        
        // COLLECTING ALL PANELS, BUTTONS, AND LABELS
        JPanel primaryColored[] = {
            // HOME SCREEN:
            topPanel, navigationPanel, 
            // SETTINGS SCREEN:
            s_colorsPanel, s_customNamePanel, s_awayFromScreenPanel, s_connectionPanel,
            // PROGRESS SCREEN:
            
            // KEYBOARD:
            keyboard,
            // EDIT HISTORY
            
            // ADD HABIT
            ah_NamePanel, ah_SummaryPanel, ah_DaysPanel, ah_QuantityPanel,
            // EDIT HABIT
        };
        JPanel secondaryColored[] = {
            // HOME SCREEN:
            home, h_habitPanel,
            // SETTINGS SCREEN:
            settings, 
            // PROGRESS SCREEN:
            progress,
            // EDIT HISTORY
            editHistory,
            // ADD HABIT
            addHabit, 
            // EDIT HABIT
            editHabit, 
        };
        JButton buttonColored[] = {
            // HOME SCREEN:
            
            // SETTINGS SCREEN:
            s_primaryColorButton, s_secondaryColorButton, s_buttonColorButton, s_textColorButton, s_customNameButton,
            s_saveCustomNameButton, s_awayFromScreenButton, s_turnOffAwayFromScreenButton,
            // PROGRESS SCREEN:
            
            // KEYBOARD:
            key1,key2,key3,key4,key5,key6,key7,key8,key9,key10,
            key11,key12,key13,key14,key15,key16,key17,key18,key19,key20,
            key21,key22,key23,key24,key25,key26,key27,
            // EDIT HISTORY
            
            // ADD HABIT
            ah_ResetButton, ah_ChooseColorButton, ah_QuantityDecrease, ah_QuantityIncrease, 
            ah_QuantitySaveButton, ah_SaveDaysButton,
            // EDIT HABIT
        };
        
        JLabel textColored[] = {
            // HOME SCREEN:
            h_welcomeMessage, h_dateTitle, h_date, 
            // SETTINGS SCREEN:
            s_title, s_colorsTitle, s_customNameTitle, s_customNameTitle2, s_customName, s_awayFromScreenTitle, 
            s_awayFromScreenTitle2, s_awayFromScreen, s_title,
            // PROGRESS SCREEN:
            p_progressTitle,
            
            // EDIT HISTORY
            eHist_title, 
            // ADD HABIT
            ah_title,ah_title, ah_TopPanelText, ah_TopPanelText1, ah_SummaryText1,
            ah_SummaryText2,ah_SummaryText3, ah_SummaryText4, ah_SummaryText5, ah_SummaryText6, ah_SummaryName, 
            ah_SummaryQuantity, ah_SummaryIncrement, ah_SummaryGoal, ah_SummaryDays, ah_DaysText, ah_QuantityPanelText2,
            ah_QuantityPanelText, ah_QuantityGoal,
            // EDIT HABIT
            eh_title,
        };
        
        JToggleButton toggleButtonColored[] = {
            ah_Monday, ah_Tuesday, ah_Wednesday, ah_Thursday, ah_Friday, ah_Saturday, ah_Sunday,  
            ah_IncrementPointOne, ah_IncrementPointFive, ah_IncrementOne,
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
            curr.setBackground(PRIMARY_COLOR);
        }
        for(JToggleButton curr : toggleButtonColored){
            curr.setBackground(BUTTON_COLOR);
            curr.setForeground(TEXT_COLOR);
        }
        
        // MISC. PAINTING CALLS : ==============================================================================
        // NAVIGATION PANEL: Making the "cover" for the buttons when selected the secondary color but brighter
            
        n_homeCover.setBackground(lightenColor(SECONDARY_COLOR));
        n_settingsCover.setBackground(lightenColor(SECONDARY_COLOR));
        n_addCover.setBackground(lightenColor(SECONDARY_COLOR));
        n_editHabitCover.setBackground(lightenColor(SECONDARY_COLOR));
        n_editHistoryCover.setBackground(lightenColor(SECONDARY_COLOR));
        n_progressCover.setBackground(lightenColor(SECONDARY_COLOR));
        
        
        // HOME: Painting the backgrounds of the navigation buttons to the same primary color INSTEAD of the button color
        settingsButton.setBackground(PRIMARY_COLOR);
        addHabitButton.setBackground(PRIMARY_COLOR);
        editHabitButton.setBackground(PRIMARY_COLOR);
        editHistoryButton.setBackground(PRIMARY_COLOR);
        progressButton.setBackground(PRIMARY_COLOR);
        
        // HOME: Painting the scroll buttons 
        h_scrollUpButton.setBackground(PRIMARY_COLOR);
        h_scrollDownButton.setBackground(PRIMARY_COLOR);
        h_scrollUpButton.setForeground(TEXT_COLOR);
        h_scrollDownButton.setForeground(TEXT_COLOR);
        
        // HOME-ADD: Painting the input for the name of the new habit
        h_addHabitName.setBackground(PRIMARY_COLOR);
        h_addHabitName.setForeground(TEXT_COLOR);
        
        // HOME-ADD: Painting the title of the panel
        ah_title.setBackground(PRIMARY_COLOR);
        
        // HOME-ADD: Painting labels that were actually suppose to be buttons
        ah_YesButton.setBackground(BUTTON_COLOR);
        ah_YesButton.setForeground(TEXT_COLOR);
        ah_NoButton.setBackground(BUTTON_COLOR);
        ah_NoButton.setForeground(TEXT_COLOR);
        ah_SaveButton.setBackground(BUTTON_COLOR);
        ah_SaveButton.setForeground(TEXT_COLOR);
        
        // SETTINGS: Painting color displays 
        s_primaryColor.setBackground(PRIMARY_COLOR);
        s_secondaryColor.setBackground(SECONDARY_COLOR);
        s_textColor.setBackground(TEXT_COLOR);
        s_buttonColor.setBackground(BUTTON_COLOR);
        
        // SETTINGS: Painting custom name input
        s_customNameInput.setBackground(PRIMARY_COLOR);
        s_customNameInput.setForeground(TEXT_COLOR);
        
        // SETTINGS: Painting away from screentime input 
        JFormattedTextField txt = ((JSpinner.DefaultEditor) s_awayFromScreenInput.getEditor()).getTextField();
        txt.setBackground(PRIMARY_COLOR);
        txt.setForeground(TEXT_COLOR);
        
        
        // Making sure everything gets repainted
        this.repaint();
        this.revalidate();
    }
    // =================================================================================================================================
    
    
    // =================================================================================================================================
    // ================== [ NAVIGATION PANEL FUNCTIONS ] ===============================================================================
    // =================================================================================================================================
    
    private void homeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_homeButtonMouseClicked
        switchFrame(home);
    }//GEN-LAST:event_homeButtonMouseClicked

    
    private void settingsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settingsButtonMouseClicked
        switchFrame(settings); // Swtich to the settings frame
    }//GEN-LAST:event_settingsButtonMouseClicked

    private void addHabitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addHabitButtonMouseClicked
        switchFrame(addHabit);
    }//GEN-LAST:event_addHabitButtonMouseClicked

    private void editHabitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editHabitButtonMouseClicked
        switchFrame(editHabit);
    }//GEN-LAST:event_editHabitButtonMouseClicked

    private void editHistoryButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editHistoryButtonMouseClicked
        switchFrame(editHistory);
    }//GEN-LAST:event_editHistoryButtonMouseClicked

    private void progressButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_progressButtonMouseClicked
        switchFrame(progress); // Swtich to the progress frame
    }//GEN-LAST:event_progressButtonMouseClicked

    
    
    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ HOME SCREEN FUNCTIONS ] ====================================================================================
    // =================================================================================================================================
    
    
    private void h_scrollButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_h_scrollButtonMouseClicked
        JButton buttonClicked = (JButton) evt.getSource();
        if(buttonClicked == null) return; // Just in case return this case
        
        // Getting value of the current scroll position
        JScrollBar vBar = h_scrollPane.getVerticalScrollBar();
        
        // Scrolling up or down depending on which button was clicked
        if(buttonClicked == h_scrollUpButton)
            vBar.setValue(vBar.getValue() - 220);   // scroll up 200 + 20 px (card size + gap size x 2[for up and down gaps])
        else if(buttonClicked == h_scrollDownButton)
            vBar.setValue(vBar.getValue() + 220);   // scroll down 200 + 20 px (card size + gap size)
        
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
    
    private void updateDate(){
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy");
        String formattedDate = today.format(formatter);
        h_date.setText(formattedDate);
    }
    
    private void refreshHomeData(){
        // Setting up the date 
        updateDate();
        String todaysWeekDay = h_date.getText().substring(0,h_date.getText().indexOf(','));
        
        
        // Adding first the quantity cards
        for(HabitCard_Quantity currCard : allQuantityCards){ // Looking through all quantity cards
            if(currCard.isForToday(todaysWeekDay))           // If this card is a habit for today
                h_habitPanel.add(currCard);
        }
        
        // Adding second the yes/no cards
        for(HabitCard_YesNo currCard : allYesNoCards){ // Looking through all yesno cards
            if(currCard.isForToday(todaysWeekDay))     // If this card is a habit for today
                h_habitPanel.add(currCard);
        }
        
        
        // Changes the size of the scroll after we added the cards
        updateScrollPaneSize();
    }

    
    
    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ SETTINGS SCREEN FUNCTIONS ] ====================================================================================
    // =================================================================================================================================
    

    
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
        
        keyboard.setLocation(190,290);            // Move keyboard to location
        keyboard.setVisible(true);                // Showing the keyboard
        
        s_colorsPanel.setVisible(false);          // Hiding the colors panel
        s_customNamePanel.setVisible(false);      // Hiding the custom name panel
        s_awayFromScreenPanel.setVisible(false);  // Hiding the away from screen panel
        s_connectionPanel.setVisible(false);      // Hiding the connection panel
        s_turnOffAwayFromScreenButton.setVisible(false); // Hiding the button to fix bugs
        s_saveCustomNameButton.setVisible(true);  // Showing the save name button
        s_customNameInput.setVisible(true);       // Showing the custom name input
        
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

    
    
    
    
    
    
    

    
    
    
    // =================================================================================================================================
    // ================== [ ADD SCREEN FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
    // User said no, MAKE ONLY YES/NO habit card
    private void ah_NoButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_NoButtonMouseClicked
        // Making sure we have a color and a name first and that we have not locked yet
        if(h_addHabitName.getBackground() == Color.RED || ah_ChooseColorButton.getBackground() == Color.RED)
            return;
        
        if(h_addHabitName.getText().isEmpty() && h_addHabitName.getBackground() != Color.RED){
            flashTextInput(h_addHabitName);
            return;
        }
        if(ah_ColorDisplay.getBackground() == Color.WHITE && ah_ChooseColorButton.getBackground() != Color.RED){
            flashButton(ah_ChooseColorButton);
            return; 
        }
        
        // Making sure that the name is unique
        boolean foundDuplicate = false;
        for(HabitCard_YesNo currCard : allYesNoCards){
            if(currCard.getHabitName().equalsIgnoreCase(h_addHabitName.getText())){
                foundDuplicate = true;
                break;
            }
        }
        // If we found a duplicate, then flash 
        if(foundDuplicate && h_addHabitName.getBackground() != Color.RED){
            flashTextInput(h_addHabitName);
            return;
        }
        
        // Hide panel so information wont be changed and hide keyboard too
        keyboard.setVisible(false);
        ah_NamePanel.setVisible(false);
        
        
        // Show Information in the summary panel
        ah_SummaryName.setText(h_addHabitName.getText());
        ah_SummaryColor.setBackground(ah_ColorDisplay.getBackground());
        ah_SummaryQuantity.setText("No");
        ah_SummaryIncrement.setText("N/A");
        ah_SummaryGoal.setText("N/A");
       
        // Moving onto next step to set up the days
        ah_DaysPanel.setVisible(true); 
        ah_SummaryPanel.setVisible(true);
        ah_ResetButton.setVisible(true);
    }//GEN-LAST:event_ah_NoButtonMouseClicked

    // User said yes, MAKE QUANTITY CARD
    private void ah_YesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_YesButtonMouseClicked
        // Making sure that we have a color and a name first and that we have not locked yet
        if(h_addHabitName.getBackground() == Color.RED || ah_ChooseColorButton.getBackground() == Color.RED)
            return;
        
        if(h_addHabitName.getText().isEmpty()){
            flashTextInput(h_addHabitName);
            return;
        }
        if(ah_ColorDisplay.getBackground() == Color.WHITE){
            flashButton(ah_ChooseColorButton);
            return; 
        }
        
        
        
        // Making sure that the name is unique
        boolean foundDuplicate = false;
        for(HabitCard_Quantity currCard : allQuantityCards){
            if(currCard.getHabitName().equalsIgnoreCase(h_addHabitName.getText())){
                foundDuplicate = true;
                break;
            }
        }
        // If we found a duplicate, then flash 
        if(foundDuplicate && h_addHabitName.getBackground() != Color.RED){
            flashTextInput(h_addHabitName);
            return;
        }
        
        // Hide Panel so user cant change the information
        ah_NamePanel.setVisible(false);
        keyboard.setVisible(false);
        
        // Show Information in the summary panel
        ah_SummaryName.setText(h_addHabitName.getText());
        ah_SummaryColor.setBackground(ah_ColorDisplay.getBackground());
        ah_SummaryQuantity.setText("Yes");
        
        
        // Moving to the next panel
        ah_QuantityPanel.setVisible(true);
        ah_SummaryPanel.setVisible(true);
        ah_ResetButton.setVisible(true);
    }//GEN-LAST:event_ah_YesButtonMouseClicked

    // User chooses color, show the color diagram
    private void ah_ChooseColorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_ChooseColorButtonMouseClicked
        // Open the menu to choose a color
        Color chosenColor = JColorChooser.showDialog(null, "Choose a color:", ah_ColorDisplay.getBackground());
        
        // If color is valid, make the panel this color
        if(chosenColor != null){
            ah_ColorDisplay.setBackground(chosenColor);
        }
    }//GEN-LAST:event_ah_ChooseColorButtonMouseClicked

    // User choose some reset button, reset the addHabitScreen
    private void ah_ResetButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_ResetButtonMouseClicked
        // Reseting the screen as if we are coming here for the first time
        keyboard.setVisible(true);      
        keyboard.setLocation(200,350);  
        keyboard.getParent().repaint();
        keyboard.getParent().revalidate();
        keyboardTarget = h_addHabitName;
        
        // Summary panel
        ah_SummaryPanel.setVisible(false);
        ah_ResetButton.setVisible(false);
        ah_SummaryName.setText("---");
        ah_SummaryColor.setBackground(Color.WHITE);
        ah_SummaryQuantity.setText("---");
        ah_SummaryIncrement.setText("---");
        ah_SummaryGoal.setText("---");
        ah_SummaryDays.setText("---");
        
        // Name and Color Panel Reset
        ah_NamePanel.setVisible(true);
        h_addHabitName.setText("");
        ah_ColorDisplay.setBackground(Color.WHITE);
        
        // Quantity goal and increment panel
        ah_QuantityPanel.setVisible(false);
        h_addHabitIncrementButtonGroup.clearSelection();
        ah_QuantityGoal.setText("0.0");
        
        // Days Panel
        ah_DaysPanel.setVisible(false);
        ah_Monday.setSelected(false);
        ah_Tuesday.setSelected(false);
        ah_Wednesday.setSelected(false);
        ah_Thursday.setSelected(false);
        ah_Friday.setSelected(false);
        ah_Saturday.setSelected(false);
        ah_Sunday.setSelected(false);
        
        // Card Presentation Panel
        ah_CardPanel.setVisible(false);
        ah_CardPanel.removeAll();
        ah_CardPanel.revalidate();
        ah_CardPanel.repaint();
        ah_SaveButton.setVisible(false); 
    }//GEN-LAST:event_ah_ResetButtonMouseClicked

    // User chose the save days button, move to show final habit card
    private void ah_SaveDaysButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_SaveDaysButtonMouseClicked
        // Making sure at least one day is checked 
        if(!ah_Monday.isSelected() && !ah_Tuesday.isSelected() && !ah_Wednesday.isSelected() && !ah_Thursday.isSelected() && 
           !ah_Friday.isSelected() && !ah_Saturday.isSelected() && !ah_Sunday.isSelected()){
            flashToggleButton(ah_Monday);
            flashToggleButton(ah_Tuesday);
            flashToggleButton(ah_Wednesday);
            flashToggleButton(ah_Thursday); 
            flashToggleButton(ah_Friday);
            flashToggleButton(ah_Saturday); 
            flashToggleButton(ah_Sunday);
            return;
        }
        
        
        
        // Making string for the week
        String newWeekString = "";
        newWeekString += (ah_Monday.isSelected() ? "1" : "0");
        newWeekString += (ah_Tuesday.isSelected() ? "1" : "0");
        newWeekString += (ah_Wednesday.isSelected() ? "1" : "0");
        newWeekString += (ah_Thursday.isSelected() ? "1" : "0");
        newWeekString += (ah_Friday.isSelected() ? "1" : "0");
        newWeekString += (ah_Saturday.isSelected() ? "1" : "0");
        newWeekString += (ah_Sunday.isSelected() ? "1" : "0");
        
        
        
        
        // If the chosen card is a YES/NO card, create and show and YES/NO CARD
        if(ah_SummaryQuantity.getText().equals("No"))
            ah_CardPanel.add(new HabitCard_YesNo(this, h_addHabitName.getText(), ah_ColorDisplay.getBackground(), false, newWeekString));
        
        // If chosen card is a QUANTITY card, create and show the QUANTITY card
        else{
            // Finding out what is the increment
            double foundIncrement = (ah_IncrementPointOne.isSelected() ? 0.1 : (ah_IncrementPointFive.isSelected() ? 0.5 : 1));
            
            // Making object and adding to card panel
            ah_CardPanel.add(new HabitCard_Quantity(this, h_addHabitName.getText(), ah_ColorDisplay.getBackground(), 
                    0, Double.parseDouble(ah_QuantityGoal.getText()), foundIncrement, newWeekString));
        }
        
        // Locking the days panel (building string to show and cover the panel up)
        newWeekString = ""; //Mon Tue Wed Thu Fri Sat Sun
        newWeekString += (ah_Monday.isSelected() ? "Mon" : "");
        newWeekString += (ah_Tuesday.isSelected() ? " Tue" : "");
        newWeekString += (ah_Wednesday.isSelected() ? " Wed" : "");
        newWeekString += (ah_Thursday.isSelected() ? " Thu" : "");
        newWeekString += (ah_Friday.isSelected() ? " Fri" : "");
        newWeekString += (ah_Saturday.isSelected() ? " Sat" : "");
        newWeekString += (ah_Sunday.isSelected() ? " Sun" : "");
        
        
        // Hiding the days panel so user doesnt change information
        ah_DaysPanel.setVisible(false);
        
        
        // Updating summary panel
        ah_SummaryDays.setText(newWeekString);
        
        // Showing the card once we are dont building
        ah_CardPanel.repaint();
        ah_CardPanel.revalidate();
        ah_CardPanel.setVisible(true);
        ah_SaveButton.setVisible(true);
    }//GEN-LAST:event_ah_SaveDaysButtonMouseClicked

    // User chose a -/+ button to set the goal
    private void ah_QuantityDecreaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_QuantityDecreaseMouseClicked
        if(ah_IncrementPointOne.getBackground() == Color.RED || ah_IncrementPointFive.getBackground() == Color.RED 
           || ah_IncrementOne.getBackground() == Color.RED)
            return;
        
        if(!ah_IncrementPointOne.isSelected() && !ah_IncrementPointFive.isSelected() && !ah_IncrementOne.isSelected()){
            flashToggleButton(ah_IncrementPointOne);
            flashToggleButton(ah_IncrementPointFive);
            flashToggleButton(ah_IncrementOne);
            return;
        }
        
        // Incrementing and setting the text
        double increment = (ah_IncrementPointOne.isSelected() ? 0.1 : (ah_IncrementPointFive.isSelected() ? 0.5 : 1)); // Complicated but just finds out which increment is chosen
        boolean isIncrease = ((JButton)evt.getSource()).getText().equals("+"); // Finding out if we are increase or decreasing
        if(!isIncrease) // Set to negative if we are decreasing
            increment *= -1;
        
            
        double newValue = Double.parseDouble(ah_QuantityGoal.getText()) + increment;
        newValue = Math.round(newValue * 10.0) / 10.0;
        if(newValue < 0) // Making sure we do not go negative
            newValue = 0;
        ah_QuantityGoal.setText(Double.toString(newValue));
    }//GEN-LAST:event_ah_QuantityDecreaseMouseClicked

    // User chose teh save quantity, show the days panel
    private void ah_QuantitySaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_QuantitySaveButtonMouseClicked
        if(ah_QuantityDecrease.getBackground() == Color.RED || ah_QuantityIncrease.getBackground() == Color.RED)
           return;
           
        if(Double.parseDouble(ah_QuantityGoal.getText()) == 0){
           flashButton(ah_QuantityDecrease); 
           flashButton(ah_QuantityIncrease); 
           return;
        }
        
        String increment = (ah_IncrementPointOne.isSelected() ? "0.1" : (ah_IncrementPointFive.isSelected() ? "0.5" : "1")); 
        
        // Hiding the quantity panel so user doesnt change information
        ah_QuantityPanel.setVisible(false);
        
        
        // Updating the summary panel
        ah_SummaryGoal.setText(ah_QuantityGoal.getText());
        ah_SummaryIncrement.setText(increment);
        
        
        // Moving on to next panel
        ah_DaysPanel.setVisible(true);
    }//GEN-LAST:event_ah_QuantitySaveButtonMouseClicked

    // User is approving the card, now we can add to array and send back to home
    private void ah_SaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ah_SaveButtonMouseClicked
        // Everything should be approved up to here and locked in, so we can add the example panel to the list and refrsh home
        
        // The card is a YESNO card, then grab the card
        if(ah_SummaryQuantity.getText().equals("No")){
            HabitCard_YesNo newCard = (HabitCard_YesNo) ah_CardPanel.getComponent(0);
            if(newCard != null){
                newCard.setComplete(false); // Resetting the card
                allYesNoCards.add(newCard); // Adding to the array
            }
            
           // Handling error JUST in case
            else
                JOptionPane.showMessageDialog(this, "ERROR: Could not create card");
        }
        
        // The card is a QUANTITY card
        else{
            HabitCard_Quantity newCard = (HabitCard_Quantity) ah_CardPanel.getComponent(0);
            if(newCard != null){
                newCard.setQuantity(0.0);      // Reseting card value before sending to array
                allQuantityCards.add(newCard); // Adding to the array
            }
            
            // Handling error JUST in case
            else
                JOptionPane.showMessageDialog(this, "ERROR: Could not create card");
        }
        
        switchFrame(home);
    }//GEN-LAST:event_ah_SaveButtonMouseClicked

    // =================================================================================================================================





    
    
    
    
    

    private void eHist_titleawayAndClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eHist_titleawayAndClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_eHist_titleawayAndClicked

    private void editHistoryawayAndClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editHistoryawayAndClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_editHistoryawayAndClicked

    
    
    
    
    
    
    


    // =================================================================================================================================
    // ================== [ OTHER HELPER FUNCTIONS ] ===================================================================================
    // =================================================================================================================================
    private void flashButton(JButton target){
        if(target.getBackground() == Color.RED)
            return;
        Color previousColor = target.getBackground();
        target.setBackground(Color.RED);
        Timer flashTimer = new Timer(1000,e->{
            target.setBackground(previousColor);
            ((Timer)e.getSource()).stop();
        });
        flashTimer.start();
    }
    
    private void flashTextInput(JTextField target){
        if(target.getBackground() == Color.RED)
            return;
        Color previousColor = target.getBackground();
        target.setBackground(Color.RED);
        Timer flashTimer = new Timer(1000,e->{
            target.setBackground(previousColor);
            ((Timer)e.getSource()).stop();
        });
        flashTimer.start();
    }
    
    private void flashToggleButton(JToggleButton target){
        if(target.getBackground() == Color.RED)
            return;
        Color previousColor = target.getBackground();
        target.setBackground(Color.RED);
        Timer flashTimer = new Timer(1000,e->{
            target.setBackground(previousColor);
            ((Timer)e.getSource()).stop();
        });
        flashTimer.start();
    }
    
    private Color lightenColor(Color color) {
        double factor = 0.45; // 0 = no change, 1 = fully white (lighter)

        int r = (int)(color.getRed()   + (255 - color.getRed())   * factor);
        int g = (int)(color.getGreen() + (255 - color.getGreen()) * factor);
        int b = (int)(color.getBlue()  + (255 - color.getBlue())  * factor);

        // ensure stays in valid range
        r = Math.min(r, 255);
        g = Math.min(g, 255);
        b = Math.min(b, 255);

        return new Color(r, g, b);
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
    private javax.swing.JPanel addHabit;
    private javax.swing.JButton addHabitButton;
    private javax.swing.JPanel ah_CardPanel;
    private javax.swing.JButton ah_ChooseColorButton;
    private javax.swing.JPanel ah_ColorDisplay;
    private javax.swing.JPanel ah_DaysPanel;
    private javax.swing.JLabel ah_DaysText;
    private javax.swing.JToggleButton ah_Friday;
    private javax.swing.JToggleButton ah_IncrementOne;
    private javax.swing.JToggleButton ah_IncrementPointFive;
    private javax.swing.JToggleButton ah_IncrementPointOne;
    private javax.swing.JToggleButton ah_Monday;
    private javax.swing.JPanel ah_NamePanel;
    private javax.swing.JLabel ah_NoButton;
    private javax.swing.JButton ah_QuantityDecrease;
    private javax.swing.JLabel ah_QuantityGoal;
    private javax.swing.JButton ah_QuantityIncrease;
    private javax.swing.JPanel ah_QuantityIncrementPanel;
    private javax.swing.JPanel ah_QuantityPanel;
    private javax.swing.JLabel ah_QuantityPanelText;
    private javax.swing.JLabel ah_QuantityPanelText2;
    private javax.swing.JButton ah_QuantitySaveButton;
    private javax.swing.JButton ah_ResetButton;
    private javax.swing.JToggleButton ah_Saturday;
    private javax.swing.JLabel ah_SaveButton;
    private javax.swing.JButton ah_SaveDaysButton;
    private javax.swing.JLabel ah_SummaryColor;
    private javax.swing.JLabel ah_SummaryDays;
    private javax.swing.JLabel ah_SummaryGoal;
    private javax.swing.JLabel ah_SummaryIncrement;
    private javax.swing.JLabel ah_SummaryName;
    private javax.swing.JPanel ah_SummaryPanel;
    private javax.swing.JLabel ah_SummaryQuantity;
    private javax.swing.JLabel ah_SummaryText1;
    private javax.swing.JLabel ah_SummaryText2;
    private javax.swing.JLabel ah_SummaryText3;
    private javax.swing.JLabel ah_SummaryText4;
    private javax.swing.JLabel ah_SummaryText5;
    private javax.swing.JLabel ah_SummaryText6;
    private javax.swing.JToggleButton ah_Sunday;
    private javax.swing.JToggleButton ah_Thursday;
    private javax.swing.JLabel ah_TopPanelText;
    private javax.swing.JLabel ah_TopPanelText1;
    private javax.swing.JToggleButton ah_Tuesday;
    private javax.swing.JToggleButton ah_Wednesday;
    private javax.swing.JLabel ah_YesButton;
    private javax.swing.JLabel ah_title;
    private javax.swing.JLabel eHist_title;
    private javax.swing.JPanel editHabit;
    private javax.swing.JButton editHabitButton;
    private javax.swing.JPanel editHistory;
    private javax.swing.JButton editHistoryButton;
    private javax.swing.JLabel eh_title;
    private javax.swing.JTextField h_addHabitName;
    private javax.swing.JLabel h_date;
    private javax.swing.JLabel h_dateTitle;
    private javax.swing.JPanel h_habitPanel;
    private javax.swing.JButton h_scrollDownButton;
    private javax.swing.JScrollPane h_scrollPane;
    private javax.swing.JButton h_scrollUpButton;
    private javax.swing.JLabel h_welcomeMessage;
    private javax.swing.JPanel home;
    private javax.swing.JButton homeButton;
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
    private javax.swing.JPanel n_addCover;
    private javax.swing.JPanel n_editHabitCover;
    private javax.swing.JPanel n_editHistoryCover;
    private javax.swing.JPanel n_homeCover;
    private javax.swing.JPanel n_progressCover;
    private javax.swing.JPanel n_settingsCover;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JLabel p_progressTitle;
    private javax.swing.JPanel progress;
    private javax.swing.JButton progressButton;
    private javax.swing.JLabel s_awayFromScreen;
    private javax.swing.JButton s_awayFromScreenButton;
    private javax.swing.JSpinner s_awayFromScreenInput;
    private javax.swing.JPanel s_awayFromScreenPanel;
    private javax.swing.JLabel s_awayFromScreenTitle;
    private javax.swing.JLabel s_awayFromScreenTitle2;
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
    private javax.swing.JPanel s_textColor;
    private javax.swing.JButton s_textColorButton;
    private javax.swing.JLabel s_title;
    private javax.swing.JButton s_turnOffAwayFromScreenButton;
    private javax.swing.JPanel settings;
    private javax.swing.JButton settingsButton;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}
