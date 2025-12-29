package habitpanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// NOTE TO SELF: 1040 x 600 is the screen size

class Screensaver {
    // General Screensaver Variables
    Timer screensaverClock = null;
    private JLabel timeText = null;
    private JLabel dateText = null;
    private int savedScreensaver = 1; // Current Screensaver: 1 -> Skyline Background 
    private JPanel background = null;
    
    // Skyline Screensaver
    private int skylineMinLocation;
    private int skylinePanelWidth;
    private final int skylineStep = 1;  // 1  -> Step for the skyline movement
    private final int skylineTick = 30; // 30 -> Speed til next step in (ms)
    private JLabel skylinePanel1 = null;
    private JLabel skylinePanel2 = null;
    private JLabel skylinePanel3 = null;
    
    // Todays Progress Screensaver
    private JPanel todaysProgressPanel = null;
    
    // Fish Tank Screensaver
    private JLabel fishTankBackground = null;
    
    
     // 400x70 | 400x50
    public void setUp(JPanel backgroundInput, JLabel timeInput, JLabel dateInput, JLabel sp1, JLabel sp2, JLabel sp3,
                      JPanel todaysProgressPanelInput, JLabel fishTankBackgroundInput){
        // Setting up the date and time labels
        dateText = dateInput;
        timeText = timeInput;
        background = backgroundInput;
        
        // Setting up skyline variablaes
        skylineMinLocation = -sp1.getWidth() - 10;
        skylinePanelWidth = sp1.getWidth();
        skylinePanel1 = sp1;
        skylinePanel2 = sp2;
        skylinePanel3 = sp3;
        
        // Setting up fishtank screensaver
        fishTankBackground = fishTankBackgroundInput;
        
        // Setting up Todays Progress Panel
        todaysProgressPanel = todaysProgressPanelInput;
        
    }
    
    // Call this function to set up a different screensaver
    public void setScreensaver(int input){
        savedScreensaver = input;
    }
    
    // Call to find out which screensaver we are using
    public int getScreensaver(){
        return savedScreensaver;
    }
    
    // Call this function to start up the screensaver we want to use
    public void startScreenSaver(){
        // Setting invisible panels of all screensavers to only later show the one we want to use
        stopClock();
        timeText.setVisible(false);
        dateText.setVisible(false);
        
        skylinePanel1.setVisible(false);
        skylinePanel2.setVisible(false);
        skylinePanel3.setVisible(false);
        
        fishTankBackground.setVisible(false);
        
        todaysProgressPanel.setVisible(false);
        
        
        switch (savedScreensaver) {
            // Skyline Screensaver
            case 1 -> {
                skylinePanel1.setVisible(true);
                skylinePanel2.setVisible(true);
                skylinePanel3.setVisible(true);
                timeText.setVisible(true);
                dateText.setVisible(true);
                startSkylineClock();
            }
            
            // Fish tank screensaver
            case 2 -> {
                fishTankBackground.setVisible(true);
            }
            
            // Blank Black Screensaver
            case 3 -> {
                
            }
            
            // Todays Progress Screensaver
            case 4 -> {
                todaysProgressPanel.setVisible(true);
            }
            
            // Overall Summary Screensaver
            case 5 -> {
                
            }
            
            // Simple Clock and Date Screensaver
            case 6 -> {
                // Centering the time text and then centering the date text under the time text
                timeText.setLocation(background.getWidth()/2 - timeText.getWidth()/2, background.getHeight()/2-timeText.getHeight());
                dateText.setLocation(timeText.getX() + (timeText.getWidth() - dateText.getWidth())/2, timeText.getY() + timeText.getHeight());
                timeText.setVisible(true);
                dateText.setVisible(true);
            }
            
            
        }
    }
    
    // SCREEN SAVER CLOCKS ------------------------------------------------------------------------------------------------------------------------------
    // Safely stopping the clock
    public void stopClock(){
        if(screensaverClock != null){        // Making sure there is an object here
            if(screensaverClock.isRunning()) // If its running -> stop
                screensaverClock.stop();
            screensaverClock = null;         // Removing it so memory can delete it when it can
        }
    }
    
    private void startSkylineClock(){
        // Moving panels to position 
        skylinePanel1.setLocation(0,0);
        skylinePanel2.setLocation(1040,0);
        skylinePanel3.setLocation(2060,0);
        timeText.setLocation(320,60);
        dateText.setLocation(320,120);
        
        screensaverClock = new Timer(skylineTick, e->{
            // Move panels to their new location
            skylinePanel1.setLocation(skylinePanel1.getX()-skylineStep, 0);
            skylinePanel2.setLocation(skylinePanel2.getX()-skylineStep, 0);
            skylinePanel3.setLocation(skylinePanel3.getX()-skylineStep, 0);
            
            // Checks if any panel has reached max left location (there should only be one that reaches this, so no need to check all three
            if(skylinePanel1.getX() <= skylineMinLocation){
                skylinePanel1.setLocation(skylinePanel3.getX()+skylinePanelWidth,0);
            }
            else{
                if(skylinePanel2.getX() <= skylineMinLocation){
                    skylinePanel2.setLocation(skylinePanel1.getX()+skylinePanelWidth,0);
                }
                else{
                    if(skylinePanel3.getX() <= skylineMinLocation){
                        skylinePanel3.setLocation(skylinePanel2.getX()+skylinePanelWidth,0);
                    }
                }
            }
        });
        
        screensaverClock.start();
    }
    // --------------------------------------------------------------------------------------------------------------------------------------------------
}




//============================================================================================================================================================================================================
//============================================================================================================================================================================================================
// [ MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS ] =============
//============================================================================================================================================================================================================
//============================================================================================================================================================================================================
public class GUI_Window extends javax.swing.JFrame {
    
    // MANIPULATABLE VARIABLES: ===========================================
    int AWAY_FROM_SCREEN_TIME = 3;                 // In seconds (1 minute)
    Color PRIMARY_COLOR = new Color(221,178,93);    // =.
    Color SECONDARY_COLOR = new Color(204,204,204); //  | Color variables that can change
    Color BUTTON_COLOR = new Color(193,144,69);     //  | when reading from the variable file
    Color TEXT_COLOR = new Color(255,255,255);      // ='
    
    // ====================================================================
    
    // CHANGING VARIABLES: =================================================
    JTextField keyboardTarget = null;    // Holds where we are typing into 
    JPanel screenSaver_again = null;     // Holds the settings panel for foward refrence
    Screensaver screensaver = new Screensaver();  // Holds the instance of the object that manages the screensaver
    int awayFromScreenCounter = 0;       // Keeps count from 0- AWAY_FROM_SCREEN_TIME
    boolean awayIsOn = true;             // User can set this up through the settings to turn it off 
    // ====================================================================
            
    
    // STATIC/HOLDING VARIABLES: =============================================
    ArrayList<HabitCard_Quantity> allQuantityCards = new ArrayList<>(); // Holds all the quantity cards that user has made
    ArrayList<HabitCard_YesNo> allYesNoCards = new ArrayList<>();       // Holds all the yes/no cards that user has made
    ButtonGroup ah_IncrementButtonGroup = null; // Holds the group button from the add menu
    ButtonGroup eh_IncrementButtonGroup = null; // Holds the group button from the edit habit menu
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
        screenSaver_again = screensaverPanel; // Saving the progress again into another location because of foward refrence in timer
        s_awayFromScreen.setText(Integer.toString(AWAY_FROM_SCREEN_TIME/60) + " Minute(s)");          // Setting the away from screen time
        s_awayFromScreenInput.setModel(new SpinnerNumberModel(1, 1, 9999, 1));                        // Setting up the spinner so it can have max and mins
        ((JSpinner.DefaultEditor) s_awayFromScreenInput.getEditor()).getTextField().setHorizontalAlignment(JTextField.CENTER); // Centering the text in input
        
        // Keyboard Panel Code:
        keyboard.getParent().setComponentZOrder(keyboard,0);  // Moving up the keyboard because it needs to be on top of everything
        keyboard.setLocation(0,0);                            // Moving keyboard to safe space
        keyboard.setVisible(false);                           // Hiding keyboard
        
        
        // Setting up add habit panel (button grouping)
        ah_IncrementButtonGroup = new ButtonGroup();
        ah_IncrementButtonGroup.add(ah_IncrementPointOne);
        ah_IncrementButtonGroup.add(ah_IncrementPointFive);
        ah_IncrementButtonGroup.add(ah_IncrementOne);
        
        // Setting up edit habit panel (button grouping);
        eh_IncrementButtonGroup = new ButtonGroup();
        eh_IncrementButtonGroup.add(eh_IncrementPointOne);
        eh_IncrementButtonGroup.add(eh_IncrementPointFive);
        eh_IncrementButtonGroup.add(eh_IncrementOne);
        
        // Setting up edit habit up and down scroll panel
        eh_chooseHabitPanel.remove(eh_scrollUpPanel);
        eh_chooseHabitPanel.remove(eh_scrollDownPanel);
        eh_scrollUpPanel = new ScrollButton(ScrollButton.Type.UP, ScrollButton.Panel.EDIT_HABIT);
        eh_scrollDownPanel = new ScrollButton(ScrollButton.Type.DOWN, ScrollButton.Panel.EDIT_HABIT);
        eh_scrollUpPanel.addMouseListener(new MouseAdapter() {                                 
            @Override public void mouseClicked(MouseEvent e) {eh_scrollButtonClicked(e);}
        });
        eh_scrollDownPanel.addMouseListener(new MouseAdapter() {                                 
            @Override public void mouseClicked(MouseEvent e) {eh_scrollButtonClicked(e);}
        });
        eh_chooseHabitPanel.add(eh_scrollUpPanel);
        eh_chooseHabitPanel.add(eh_scrollDownPanel);
        
        
        
        // Setting up the screensaver
        screensaver.setUp(screensaverPanel, screensaverTimeText, screensaverDateText, skylinePanel1, skylinePanel2, skylinePanel3,
                          screensaverTodaysProgress, screensaverFishTankBackground); 
        
        // Setting up the scroll buttons in the home panel (removing, making new object, adding, setting z level to 0)
        home.remove(h_scrollUpPanel);
        home.remove(h_scrollDownPanel);
        h_scrollUpPanel = new ScrollButton(ScrollButton.Type.UP, ScrollButton.Panel.HOME);
        h_scrollDownPanel = new ScrollButton(ScrollButton.Type.DOWN, ScrollButton.Panel.HOME);
        h_scrollUpPanel.addMouseListener(new MouseAdapter() {                                  // Connecting the function since we removed 
            @Override public void mouseClicked(MouseEvent e) {scrollButtonClicked(e);}
        });  
        h_scrollDownPanel.addMouseListener(new MouseAdapter() {                               // Connecting the function since we removed 
            @Override public void mouseClicked(MouseEvent e) {scrollButtonClicked(e);}
        });
        home.add(h_scrollUpPanel);
        home.add(h_scrollDownPanel);
        home.setComponentZOrder(h_scrollUpPanel, 0);
        home.setComponentZOrder(h_scrollDownPanel, 0);
        
        
        
        // Painting the program
        paintColors();         
        
        // Switching to start in home frame
        switchFrame(home);         // Switching to the starting frame (home)
        dateTimeClock.start();     // Start the clock timer that will update the date and time
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navigationPanel = new javax.swing.JPanel();
        navSelector = new javax.swing.JPanel();
        addHabitButton = new javax.swing.JButton();
        editHistoryButton = new javax.swing.JButton();
        progressButton = new javax.swing.JButton();
        editHabitButton = new javax.swing.JButton();
        settingsButton = new javax.swing.JButton();
        homeButton = new javax.swing.JButton();
        editHabit = new javax.swing.JPanel();
        eh_title = new javax.swing.JLabel();
        eh_editHabitPanel = new javax.swing.JPanel();
        eh_editHabitText1 = new javax.swing.JLabel();
        eh_editNamePanel = new javax.swing.JPanel();
        eh_editNameCancelButton = new javax.swing.JButton();
        eh_editNameSaveButton = new javax.swing.JButton();
        eh_nameInput = new javax.swing.JTextField();
        eh_editHabitSummaryPanel = new javax.swing.JPanel();
        eh_editHabitText2 = new javax.swing.JLabel();
        eh_editHabitText3 = new javax.swing.JLabel();
        eh_editHabitText4 = new javax.swing.JLabel();
        eh_editHabitText5 = new javax.swing.JLabel();
        eh_editHabitText6 = new javax.swing.JLabel();
        eh_name = new javax.swing.JLabel();
        eh_color = new javax.swing.JLabel();
        eh_goal = new javax.swing.JLabel();
        eh_increment = new javax.swing.JLabel();
        eh_days = new javax.swing.JLabel();
        eh_editNameButton = new javax.swing.JButton();
        eh_editColorButton = new javax.swing.JButton();
        eh_editIncrementAndGoalButton = new javax.swing.JButton();
        eh_editDaysButton = new javax.swing.JButton();
        eh_bottomButtonsPanel = new javax.swing.JPanel();
        eh_cancelChangesButton = new javax.swing.JButton();
        eh_saveHabitButton = new javax.swing.JButton();
        eh_deleteHabitButton = new javax.swing.JButton();
        eh_deletePanel = new javax.swing.JPanel();
        eh_deleteConfirmButton = new javax.swing.JButton();
        eh_deleteCancelButton = new javax.swing.JButton();
        eh_deleteText1 = new javax.swing.JLabel();
        eh_editIncrementAndGoalPanel = new javax.swing.JPanel();
        eh_IncrementPointOne = new javax.swing.JToggleButton();
        eh_IncrementPointFive = new javax.swing.JToggleButton();
        eh_IncrementOne = new javax.swing.JToggleButton();
        eh_euantityIncrease = new javax.swing.JButton();
        eh_editIncrementAndGoalSaveButton = new javax.swing.JButton();
        eh_editIncrementAndGoalText1 = new javax.swing.JLabel();
        eh_editIncrementAndGoalGoal = new javax.swing.JLabel();
        eh_editIncrementAndGoalText2 = new javax.swing.JLabel();
        eh_quantityDecrease = new javax.swing.JButton();
        eh_editIncrementAndGoalCancelButton = new javax.swing.JButton();
        eh_editDaysPanel = new javax.swing.JPanel();
        eh_Monday = new javax.swing.JToggleButton();
        eh_Tuesday = new javax.swing.JToggleButton();
        eh_Wednesday = new javax.swing.JToggleButton();
        eh_Thursday = new javax.swing.JToggleButton();
        eh_Friday = new javax.swing.JToggleButton();
        eh_Saturday = new javax.swing.JToggleButton();
        eh_Sunday = new javax.swing.JToggleButton();
        eh_editDaysSaveButton = new javax.swing.JButton();
        eh_editDaysCancelButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        eh_chooseHabitPanel = new javax.swing.JPanel();
        eh_scrollDownPanel = new javax.swing.JPanel();
        eh_scrollUpPanel = new javax.swing.JPanel();
        eh_scrollPane = new javax.swing.JScrollPane();
        eh_chooseHabitDisplay = new javax.swing.JPanel();
        home = new javax.swing.JPanel();
        h_scrollUpPanel = new javax.swing.JPanel();
        h_scrollDownPanel = new javax.swing.JPanel();
        h_scrollPane = new javax.swing.JScrollPane();
        h_habitPanel = new javax.swing.JPanel();
        h_date = new javax.swing.JLabel();
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
        progress = new javax.swing.JPanel();
        p_progressTitle = new javax.swing.JLabel();
        editHistory = new javax.swing.JPanel();
        eHist_title = new javax.swing.JLabel();
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
        s_customScreensaverPanel = new javax.swing.JPanel();
        s_awayFromScreenTitle1 = new javax.swing.JLabel();
        s_awayFromScreenSelectedText = new javax.swing.JLabel();
        s_awayFromScreenOption1 = new javax.swing.JLabel();
        s_awayFromScreenOption2 = new javax.swing.JLabel();
        s_awayFromScreenOption3 = new javax.swing.JLabel();
        s_awayFromScreenOption4 = new javax.swing.JLabel();
        s_awayFromScreenOption5 = new javax.swing.JLabel();
        s_awayFromScreenOption6 = new javax.swing.JLabel();
        s_awayFromScreenPanel = new javax.swing.JPanel();
        s_awayFromScreenTitle = new javax.swing.JLabel();
        s_awayFromScreenTitle2 = new javax.swing.JLabel();
        s_awayFromScreen = new javax.swing.JLabel();
        s_awayFromScreenInput = new javax.swing.JSpinner();
        s_awayFromScreenButton = new javax.swing.JButton();
        s_connectionPanel = new javax.swing.JPanel();
        s_turnOffAwayFromScreenButton = new javax.swing.JButton();
        screensaverPanel = new javax.swing.JPanel();
        screensaverFishTankBackground = new javax.swing.JLabel();
        screensaverTodaysProgress = new javax.swing.JPanel();
        screensaverTodaysProgressTitle = new javax.swing.JLabel();
        screensaverTodaysProgressDisplay = new javax.swing.JPanel();
        screensaverTimeText = new javax.swing.JLabel();
        screensaverDateText = new javax.swing.JLabel();
        skylinePanel1 = new javax.swing.JLabel();
        skylinePanel2 = new javax.swing.JLabel();
        skylinePanel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HabitPanel");
        setMaximumSize(new java.awt.Dimension(1040, 600));
        setMinimumSize(new java.awt.Dimension(1040, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1040, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navigationPanel.setBackground(new java.awt.Color(156, 183, 133));
        navigationPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        navigationPanel.setLayout(null);

        navSelector.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        navSelector.setOpaque(false);

        javax.swing.GroupLayout navSelectorLayout = new javax.swing.GroupLayout(navSelector);
        navSelector.setLayout(navSelectorLayout);
        navSelectorLayout.setHorizontalGroup(
            navSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 59, Short.MAX_VALUE)
        );
        navSelectorLayout.setVerticalGroup(
            navSelectorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 96, Short.MAX_VALUE)
        );

        navigationPanel.add(navSelector);
        navSelector.setBounds(3, 3, 63, 100);

        addHabitButton.setBackground(new java.awt.Color(193, 144, 69));
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
        navigationPanel.add(addHabitButton);
        addHabitButton.setBounds(0, 200, 68, 100);

        editHistoryButton.setBackground(new java.awt.Color(193, 144, 69));
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
        navigationPanel.add(editHistoryButton);
        editHistoryButton.setBounds(0, 400, 68, 100);

        progressButton.setBackground(new java.awt.Color(193, 144, 69));
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
        navigationPanel.add(progressButton);
        progressButton.setBounds(0, 500, 68, 100);

        editHabitButton.setBackground(new java.awt.Color(193, 144, 69));
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
        navigationPanel.add(editHabitButton);
        editHabitButton.setBounds(0, 300, 68, 100);

        settingsButton.setBackground(new java.awt.Color(193, 144, 69));
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
        navigationPanel.add(settingsButton);
        settingsButton.setBounds(0, 100, 68, 100);

        homeButton.setBackground(new java.awt.Color(193, 144, 69));
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
        navigationPanel.add(homeButton);
        homeButton.setBounds(0, 0, 68, 100);

        getContentPane().add(navigationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 70, 600));

        editHabit.setBackground(new java.awt.Color(181, 181, 181));
        editHabit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_title.setBackground(new java.awt.Color(156, 183, 133));
        eh_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_title.setForeground(java.awt.Color.white);
        eh_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_title.setText("Edit Habit");
        eh_title.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        eh_title.setOpaque(true);
        editHabit.add(eh_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

        eh_editHabitPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHabitPanel.setLayout(null);

        eh_editHabitText1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editHabitText1.setForeground(java.awt.Color.white);
        eh_editHabitText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHabitText1.setText("Saved Details");
        eh_editHabitText1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        eh_editHabitText1.setOpaque(true);
        eh_editHabitPanel.add(eh_editHabitText1);
        eh_editHabitText1.setBounds(150, 30, 630, 40);

        eh_editNamePanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editNamePanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        eh_editNamePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editNameCancelButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editNameCancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editNameCancelButton.setForeground(java.awt.Color.white);
        eh_editNameCancelButton.setText("Cancel/Reset");
        eh_editNameCancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editNameCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editNameCancelButtonMouseClicked(evt);
            }
        });
        eh_editNamePanel.add(eh_editNameCancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 270, 70));

        eh_editNameSaveButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editNameSaveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editNameSaveButton.setForeground(java.awt.Color.white);
        eh_editNameSaveButton.setText("Save");
        eh_editNameSaveButton.setToolTipText("");
        eh_editNameSaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editNameSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editNameSaveButtonMouseClicked(evt);
            }
        });
        eh_editNamePanel.add(eh_editNameSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 90, 270, 70));

        eh_nameInput.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_nameInput.setForeground(new java.awt.Color(255, 255, 255));
        eh_nameInput.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eh_nameInput.setText("< Enter Name Section >");
        eh_editNamePanel.add(eh_nameInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 440, 50));

        eh_editHabitPanel.add(eh_editNamePanel);
        eh_editNamePanel.setBounds(150, 80, 630, 230);

        eh_editHabitSummaryPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHabitSummaryPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        eh_editHabitSummaryPanel.setLayout(null);

        eh_editHabitText2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHabitText2.setForeground(java.awt.Color.white);
        eh_editHabitText2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eh_editHabitText2.setText("Name:");
        eh_editHabitSummaryPanel.add(eh_editHabitText2);
        eh_editHabitText2.setBounds(40, 20, 120, 40);

        eh_editHabitText3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHabitText3.setForeground(java.awt.Color.white);
        eh_editHabitText3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eh_editHabitText3.setText("Color:");
        eh_editHabitSummaryPanel.add(eh_editHabitText3);
        eh_editHabitText3.setBounds(40, 70, 120, 40);

        eh_editHabitText4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHabitText4.setForeground(java.awt.Color.white);
        eh_editHabitText4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eh_editHabitText4.setText("Goal:");
        eh_editHabitSummaryPanel.add(eh_editHabitText4);
        eh_editHabitText4.setBounds(40, 220, 120, 40);

        eh_editHabitText5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHabitText5.setForeground(java.awt.Color.white);
        eh_editHabitText5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eh_editHabitText5.setText("Increment:");
        eh_editHabitSummaryPanel.add(eh_editHabitText5);
        eh_editHabitText5.setBounds(40, 170, 120, 40);

        eh_editHabitText6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHabitText6.setForeground(java.awt.Color.white);
        eh_editHabitText6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eh_editHabitText6.setText("Days:");
        eh_editHabitSummaryPanel.add(eh_editHabitText6);
        eh_editHabitText6.setBounds(40, 120, 120, 40);

        eh_name.setBackground(java.awt.Color.black);
        eh_name.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        eh_name.setForeground(java.awt.Color.white);
        eh_name.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_name.setText("< target habit name >");
        eh_editHabitSummaryPanel.add(eh_name);
        eh_name.setBounds(170, 20, 240, 40);

        eh_color.setBackground(new java.awt.Color(255, 153, 102));
        eh_color.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        eh_color.setForeground(java.awt.Color.white);
        eh_color.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_color.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eh_color.setOpaque(true);
        eh_editHabitSummaryPanel.add(eh_color);
        eh_color.setBounds(190, 75, 200, 30);

        eh_goal.setBackground(java.awt.Color.black);
        eh_goal.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        eh_goal.setForeground(java.awt.Color.white);
        eh_goal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_goal.setText("< target goal >");
        eh_editHabitSummaryPanel.add(eh_goal);
        eh_goal.setBounds(170, 220, 240, 40);

        eh_increment.setBackground(java.awt.Color.black);
        eh_increment.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        eh_increment.setForeground(java.awt.Color.white);
        eh_increment.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_increment.setText("< target increment >");
        eh_editHabitSummaryPanel.add(eh_increment);
        eh_increment.setBounds(170, 170, 240, 40);

        eh_days.setBackground(java.awt.Color.black);
        eh_days.setFont(new java.awt.Font("Segoe UI", 2, 17)); // NOI18N
        eh_days.setForeground(java.awt.Color.white);
        eh_days.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_days.setText("< target days >");
        eh_editHabitSummaryPanel.add(eh_days);
        eh_days.setBounds(170, 120, 240, 40);

        eh_editNameButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_editNameButton.setText("Edit");
        eh_editNameButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editNameButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editNameButtonMouseClicked(evt);
            }
        });
        eh_editHabitSummaryPanel.add(eh_editNameButton);
        eh_editNameButton.setBounds(410, 25, 170, 30);

        eh_editColorButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_editColorButton.setText("Edit");
        eh_editColorButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editColorButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editColorButtonMouseClicked(evt);
            }
        });
        eh_editHabitSummaryPanel.add(eh_editColorButton);
        eh_editColorButton.setBounds(410, 75, 170, 30);

        eh_editIncrementAndGoalButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_editIncrementAndGoalButton.setText("Edit");
        eh_editIncrementAndGoalButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editIncrementAndGoalButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editIncrementAndGoalButtonMouseClicked(evt);
            }
        });
        eh_editHabitSummaryPanel.add(eh_editIncrementAndGoalButton);
        eh_editIncrementAndGoalButton.setBounds(410, 175, 170, 80);

        eh_editDaysButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_editDaysButton.setText("Edit");
        eh_editDaysButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editDaysButtonMouseClicked(evt);
            }
        });
        eh_editHabitSummaryPanel.add(eh_editDaysButton);
        eh_editDaysButton.setBounds(410, 125, 170, 30);

        eh_editHabitPanel.add(eh_editHabitSummaryPanel);
        eh_editHabitSummaryPanel.setBounds(150, 80, 630, 280);

        eh_bottomButtonsPanel.setBackground(new java.awt.Color(156, 183, 133));

        eh_cancelChangesButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_cancelChangesButton.setText("Cancel / Go Back");
        eh_cancelChangesButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_cancelChangesButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_cancelChangesButtonMouseClicked(evt);
            }
        });

        eh_saveHabitButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_saveHabitButton.setText("Save Habit");
        eh_saveHabitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_saveHabitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_saveHabitButtonMouseClicked(evt);
            }
        });

        eh_deleteHabitButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_deleteHabitButton.setText("Delete Habit");
        eh_deleteHabitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_deleteHabitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_deleteHabitButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout eh_bottomButtonsPanelLayout = new javax.swing.GroupLayout(eh_bottomButtonsPanel);
        eh_bottomButtonsPanel.setLayout(eh_bottomButtonsPanelLayout);
        eh_bottomButtonsPanelLayout.setHorizontalGroup(
            eh_bottomButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 630, Short.MAX_VALUE)
            .addGroup(eh_bottomButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(eh_bottomButtonsPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(eh_deleteHabitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addComponent(eh_cancelChangesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addComponent(eh_saveHabitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        eh_bottomButtonsPanelLayout.setVerticalGroup(
            eh_bottomButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
            .addGroup(eh_bottomButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(eh_bottomButtonsPanelLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addGroup(eh_bottomButtonsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(eh_deleteHabitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(eh_cancelChangesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(eh_saveHabitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        eh_editHabitPanel.add(eh_bottomButtonsPanel);
        eh_bottomButtonsPanel.setBounds(150, 370, 630, 90);

        eh_deletePanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_deletePanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        eh_deletePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_deleteConfirmButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_deleteConfirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_deleteConfirmButton.setForeground(new java.awt.Color(255, 255, 255));
        eh_deleteConfirmButton.setText("Confirm Delete");
        eh_deleteConfirmButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_deleteConfirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_deleteConfirmButtonMouseClicked(evt);
            }
        });
        eh_deletePanel.add(eh_deleteConfirmButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 300, 70));

        eh_deleteCancelButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_deleteCancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_deleteCancelButton.setForeground(new java.awt.Color(255, 255, 255));
        eh_deleteCancelButton.setText("Cancel");
        eh_deleteCancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_deleteCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_deleteCancelButtonMouseClicked(evt);
            }
        });
        eh_deletePanel.add(eh_deleteCancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, 300, 70));

        eh_deleteText1.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        eh_deleteText1.setForeground(java.awt.Color.white);
        eh_deleteText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_deleteText1.setText("Are you sure you want to delete this habit?");
        eh_deletePanel.add(eh_deleteText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 630, 270));

        eh_editHabitPanel.add(eh_deletePanel);
        eh_deletePanel.setBounds(150, 80, 630, 380);

        eh_editIncrementAndGoalPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editIncrementAndGoalPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        eh_editIncrementAndGoalPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_IncrementPointOne.setBackground(new java.awt.Color(128, 161, 98));
        eh_IncrementPointOne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_IncrementPointOne.setForeground(java.awt.Color.white);
        eh_IncrementPointOne.setText("+0.1");
        eh_IncrementPointOne.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editIncrementAndGoalPanel.add(eh_IncrementPointOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 120, 40));

        eh_IncrementPointFive.setBackground(new java.awt.Color(128, 161, 98));
        eh_IncrementPointFive.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_IncrementPointFive.setForeground(java.awt.Color.white);
        eh_IncrementPointFive.setText("+0.5");
        eh_IncrementPointFive.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editIncrementAndGoalPanel.add(eh_IncrementPointFive, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 120, 40));

        eh_IncrementOne.setBackground(new java.awt.Color(128, 161, 98));
        eh_IncrementOne.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_IncrementOne.setForeground(java.awt.Color.white);
        eh_IncrementOne.setText("+1");
        eh_IncrementOne.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editIncrementAndGoalPanel.add(eh_IncrementOne, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 70, 120, 40));

        eh_euantityIncrease.setBackground(new java.awt.Color(128, 161, 98));
        eh_euantityIncrease.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_euantityIncrease.setForeground(java.awt.Color.white);
        eh_euantityIncrease.setText("+");
        eh_euantityIncrease.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_euantityIncrease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_quantityDecreasedIncreasedButtonClicked(evt);
            }
        });
        eh_editIncrementAndGoalPanel.add(eh_euantityIncrease, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 80, 60));

        eh_editIncrementAndGoalSaveButton.setBackground(new java.awt.Color(128, 161, 98));
        eh_editIncrementAndGoalSaveButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editIncrementAndGoalSaveButton.setForeground(java.awt.Color.white);
        eh_editIncrementAndGoalSaveButton.setText("Save");
        eh_editIncrementAndGoalSaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editIncrementAndGoalSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editIncrementAndGoalSaveButtonMouseClicked(evt);
            }
        });
        eh_editIncrementAndGoalPanel.add(eh_editIncrementAndGoalSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 270, 80));

        eh_editIncrementAndGoalText1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editIncrementAndGoalText1.setForeground(java.awt.Color.white);
        eh_editIncrementAndGoalText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editIncrementAndGoalText1.setText("Increment:");
        eh_editIncrementAndGoalPanel.add(eh_editIncrementAndGoalText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 400, 60));

        eh_editIncrementAndGoalGoal.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editIncrementAndGoalGoal.setForeground(java.awt.Color.white);
        eh_editIncrementAndGoalGoal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editIncrementAndGoalGoal.setText("0.0");
        eh_editIncrementAndGoalPanel.add(eh_editIncrementAndGoalGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, 120, 60));

        eh_editIncrementAndGoalText2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editIncrementAndGoalText2.setForeground(java.awt.Color.white);
        eh_editIncrementAndGoalText2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editIncrementAndGoalText2.setText("Goal:");
        eh_editIncrementAndGoalPanel.add(eh_editIncrementAndGoalText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 400, 60));

        eh_quantityDecrease.setBackground(new java.awt.Color(128, 161, 98));
        eh_quantityDecrease.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_quantityDecrease.setForeground(java.awt.Color.white);
        eh_quantityDecrease.setText("-");
        eh_quantityDecrease.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_quantityDecrease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_quantityDecreasedIncreasedButtonClicked(evt);
            }
        });
        eh_editIncrementAndGoalPanel.add(eh_quantityDecrease, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 80, 60));

        eh_editIncrementAndGoalCancelButton.setBackground(new java.awt.Color(128, 161, 98));
        eh_editIncrementAndGoalCancelButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editIncrementAndGoalCancelButton.setForeground(java.awt.Color.white);
        eh_editIncrementAndGoalCancelButton.setText("Cancel/Reset");
        eh_editIncrementAndGoalCancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editIncrementAndGoalCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editIncrementAndGoalCancelButtonMouseClicked(evt);
            }
        });
        eh_editIncrementAndGoalPanel.add(eh_editIncrementAndGoalCancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 270, 80));

        eh_editHabitPanel.add(eh_editIncrementAndGoalPanel);
        eh_editIncrementAndGoalPanel.setBounds(150, 80, 630, 380);

        eh_editDaysPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editDaysPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        eh_editDaysPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_Monday.setBackground(new java.awt.Color(128, 161, 98));
        eh_Monday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_Monday.setForeground(java.awt.Color.white);
        eh_Monday.setText("Monday");
        eh_Monday.setToolTipText("");
        eh_Monday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysPanel.add(eh_Monday, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 140, 70));

        eh_Tuesday.setBackground(new java.awt.Color(128, 161, 98));
        eh_Tuesday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_Tuesday.setForeground(java.awt.Color.white);
        eh_Tuesday.setText("Tuesday");
        eh_Tuesday.setToolTipText("");
        eh_Tuesday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysPanel.add(eh_Tuesday, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 140, 70));

        eh_Wednesday.setBackground(new java.awt.Color(128, 161, 98));
        eh_Wednesday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_Wednesday.setForeground(java.awt.Color.white);
        eh_Wednesday.setText("Wednesday");
        eh_Wednesday.setToolTipText("");
        eh_Wednesday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysPanel.add(eh_Wednesday, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 140, 70));

        eh_Thursday.setBackground(new java.awt.Color(128, 161, 98));
        eh_Thursday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_Thursday.setForeground(java.awt.Color.white);
        eh_Thursday.setText("Thursday");
        eh_Thursday.setToolTipText("");
        eh_Thursday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysPanel.add(eh_Thursday, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 30, 140, 70));

        eh_Friday.setBackground(new java.awt.Color(128, 161, 98));
        eh_Friday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_Friday.setForeground(java.awt.Color.white);
        eh_Friday.setText("Friday");
        eh_Friday.setToolTipText("");
        eh_Friday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysPanel.add(eh_Friday, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 140, 70));

        eh_Saturday.setBackground(new java.awt.Color(128, 161, 98));
        eh_Saturday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_Saturday.setForeground(java.awt.Color.white);
        eh_Saturday.setText("Saturday");
        eh_Saturday.setToolTipText("");
        eh_Saturday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysPanel.add(eh_Saturday, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, 140, 70));

        eh_Sunday.setBackground(new java.awt.Color(128, 161, 98));
        eh_Sunday.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_Sunday.setForeground(java.awt.Color.white);
        eh_Sunday.setText("Sunday");
        eh_Sunday.setToolTipText("");
        eh_Sunday.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysPanel.add(eh_Sunday, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 140, 70));

        eh_editDaysSaveButton.setBackground(new java.awt.Color(128, 161, 98));
        eh_editDaysSaveButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editDaysSaveButton.setForeground(java.awt.Color.white);
        eh_editDaysSaveButton.setText("Save");
        eh_editDaysSaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editDaysSaveButtonMouseClicked(evt);
            }
        });
        eh_editDaysPanel.add(eh_editDaysSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 270, 80));

        eh_editDaysCancelButton.setBackground(new java.awt.Color(128, 161, 98));
        eh_editDaysCancelButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editDaysCancelButton.setForeground(java.awt.Color.white);
        eh_editDaysCancelButton.setText("Cancel/Reset");
        eh_editDaysCancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editDaysCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editDaysCancelButtonMouseClicked(evt);
            }
        });
        eh_editDaysPanel.add(eh_editDaysCancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 270, 80));
        eh_editDaysPanel.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 570, 20));

        eh_editHabitPanel.add(eh_editDaysPanel);
        eh_editDaysPanel.setBounds(150, 80, 630, 380);

        editHabit.add(eh_editHabitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 950, 490));

        eh_chooseHabitPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_chooseHabitPanel.setLayout(null);

        javax.swing.GroupLayout eh_scrollDownPanelLayout = new javax.swing.GroupLayout(eh_scrollDownPanel);
        eh_scrollDownPanel.setLayout(eh_scrollDownPanelLayout);
        eh_scrollDownPanelLayout.setHorizontalGroup(
            eh_scrollDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        eh_scrollDownPanelLayout.setVerticalGroup(
            eh_scrollDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        eh_chooseHabitPanel.add(eh_scrollDownPanel);
        eh_scrollDownPanel.setBounds(40, 430, 850, 50);

        javax.swing.GroupLayout eh_scrollUpPanelLayout = new javax.swing.GroupLayout(eh_scrollUpPanel);
        eh_scrollUpPanel.setLayout(eh_scrollUpPanelLayout);
        eh_scrollUpPanelLayout.setHorizontalGroup(
            eh_scrollUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        eh_scrollUpPanelLayout.setVerticalGroup(
            eh_scrollUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        eh_chooseHabitPanel.add(eh_scrollUpPanel);
        eh_scrollUpPanel.setBounds(40, 10, 850, 50);

        eh_scrollPane.setBackground(new java.awt.Color(156, 183, 133));
        eh_scrollPane.setBorder(null);
        eh_scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        eh_scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        eh_scrollPane.setWheelScrollingEnabled(false);

        eh_chooseHabitDisplay.setBackground(new java.awt.Color(156, 183, 133));
        eh_chooseHabitDisplay.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10));
        eh_scrollPane.setViewportView(eh_chooseHabitDisplay);

        eh_chooseHabitPanel.add(eh_scrollPane);
        eh_scrollPane.setBounds(40, 80, 850, 330);

        editHabit.add(eh_chooseHabitPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 910, 490));

        getContentPane().add(editHabit, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

        home.setBackground(new java.awt.Color(204, 204, 204));
        home.setMaximumSize(new java.awt.Dimension(1040, 600));
        home.setMinimumSize(new java.awt.Dimension(1040, 600));
        home.setLayout(null);

        h_scrollUpPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollButtonClicked(evt);
            }
        });
        h_scrollUpPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        home.add(h_scrollUpPanel);
        h_scrollUpPanel.setBounds(10, 60, 950, 90);

        h_scrollDownPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollButtonClicked(evt);
            }
        });

        javax.swing.GroupLayout h_scrollDownPanelLayout = new javax.swing.GroupLayout(h_scrollDownPanel);
        h_scrollDownPanel.setLayout(h_scrollDownPanelLayout);
        h_scrollDownPanelLayout.setHorizontalGroup(
            h_scrollDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        h_scrollDownPanelLayout.setVerticalGroup(
            h_scrollDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 90, Short.MAX_VALUE)
        );

        home.add(h_scrollDownPanel);
        h_scrollDownPanel.setBounds(10, 500, 950, 90);

        h_scrollPane.setBackground(new java.awt.Color(204, 204, 204));
        h_scrollPane.setBorder(null);
        h_scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        h_scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        h_scrollPane.setMaximumSize(new java.awt.Dimension(900, 430));
        h_scrollPane.setMinimumSize(new java.awt.Dimension(900, 430));
        h_scrollPane.setOpaque(false);
        h_scrollPane.setPreferredSize(new java.awt.Dimension(900, 430));
        h_scrollPane.setWheelScrollingEnabled(false);

        h_habitPanel.setBackground(new java.awt.Color(204, 204, 204));
        h_habitPanel.setMaximumSize(new java.awt.Dimension(99999, 999999));
        h_habitPanel.setMinimumSize(new java.awt.Dimension(900, 430));
        h_habitPanel.setName(""); // NOI18N
        h_habitPanel.setPreferredSize(new java.awt.Dimension(900, 440));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 10);
        flowLayout1.setAlignOnBaseline(true);
        h_habitPanel.setLayout(flowLayout1);
        h_scrollPane.setViewportView(h_habitPanel);

        home.add(h_scrollPane);
        h_scrollPane.setBounds(35, 113, 900, 430);

        h_date.setBackground(new java.awt.Color(193, 144, 69));
        h_date.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        h_date.setForeground(new java.awt.Color(255, 255, 255));
        h_date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_date.setText("Saturday, Nov 22, 2025");
        h_date.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        h_date.setOpaque(true);
        home.add(h_date);
        h_date.setBounds(10, 10, 950, 50);

        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

        addHabit.setBackground(new java.awt.Color(181, 181, 181));
        addHabit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_title.setBackground(new java.awt.Color(156, 183, 133));
        ah_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        ah_title.setForeground(java.awt.Color.white);
        ah_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_title.setText("Add New Habit");
        ah_title.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
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

        addHabit.add(ah_NamePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 110, 390, 190));

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

        getContentPane().add(addHabit, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

        keyboard.setBackground(new java.awt.Color(156, 183, 133));
        keyboard.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        keyboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        key1.setBackground(new java.awt.Color(128, 161, 98));
        key1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key1.setForeground(java.awt.Color.white);
        key1.setText("Q");
        key1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key1.setFocusPainted(false);
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
        key2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key2.setFocusPainted(false);
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
        key3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key3.setFocusPainted(false);
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
        key4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key4.setFocusPainted(false);
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
        key5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key5.setFocusPainted(false);
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
        key6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key6.setFocusPainted(false);
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
        key7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key7.setFocusPainted(false);
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
        key8.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key8.setFocusPainted(false);
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
        key9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key9.setFocusPainted(false);
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
        key10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key10.setFocusPainted(false);
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
        key11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key11.setFocusPainted(false);
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
        key12.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key12.setFocusPainted(false);
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
        key13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key13.setFocusPainted(false);
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
        key14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key14.setFocusPainted(false);
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
        key15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key15.setFocusPainted(false);
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
        key16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key16.setFocusPainted(false);
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
        key17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key17.setFocusPainted(false);
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
        key18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key18.setFocusPainted(false);
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
        key19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key19.setFocusPainted(false);
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
        key20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key20.setFocusPainted(false);
        key20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key20, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 60, 60));

        key21.setBackground(new java.awt.Color(128, 161, 98));
        key21.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key21.setForeground(java.awt.Color.white);
        key21.setText("X");
        key21.setToolTipText("");
        key21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key21.setFocusPainted(false);
        key21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key21, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 60, 60));

        key22.setBackground(new java.awt.Color(128, 161, 98));
        key22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key22.setForeground(java.awt.Color.white);
        key22.setText("C");
        key22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key22.setFocusPainted(false);
        key22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key22, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 150, 60, 60));

        key23.setBackground(new java.awt.Color(128, 161, 98));
        key23.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key23.setForeground(java.awt.Color.white);
        key23.setText("V");
        key23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key23.setFocusPainted(false);
        key23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key23, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 150, 60, 60));

        key24.setBackground(new java.awt.Color(128, 161, 98));
        key24.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key24.setForeground(java.awt.Color.white);
        key24.setText("B");
        key24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key24.setFocusPainted(false);
        key24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key24, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 150, 60, 60));

        key25.setBackground(new java.awt.Color(128, 161, 98));
        key25.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key25.setForeground(java.awt.Color.white);
        key25.setText("N");
        key25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key25.setFocusPainted(false);
        key25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key25, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 150, 60, 60));

        key26.setBackground(new java.awt.Color(128, 161, 98));
        key26.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key26.setForeground(java.awt.Color.white);
        key26.setText("M");
        key26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key26.setFocusPainted(false);
        key26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key26, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 60, 60));

        key27.setBackground(new java.awt.Color(128, 161, 98));
        key27.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        key27.setForeground(java.awt.Color.white);
        key27.setText("<-");
        key27.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key27.setFocusPainted(false);
        key27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key27, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 150, 100, 60));

        getContentPane().add(keyboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, -330, 730, 220));

        progress.setBackground(new java.awt.Color(204, 204, 204));
        progress.setMaximumSize(new java.awt.Dimension(1040, 600));
        progress.setMinimumSize(new java.awt.Dimension(1040, 600));
        progress.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        p_progressTitle.setBackground(new java.awt.Color(156, 183, 133));
        p_progressTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        p_progressTitle.setForeground(java.awt.Color.white);
        p_progressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_progressTitle.setText("Progress Center");
        p_progressTitle.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        p_progressTitle.setOpaque(true);
        progress.add(p_progressTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

        getContentPane().add(progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

        editHistory.setBackground(new java.awt.Color(204, 204, 204));
        editHistory.setMaximumSize(new java.awt.Dimension(1040, 600));
        editHistory.setMinimumSize(new java.awt.Dimension(1040, 600));
        editHistory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eHist_title.setBackground(new java.awt.Color(156, 183, 133));
        eHist_title.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eHist_title.setForeground(java.awt.Color.white);
        eHist_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eHist_title.setText("Edit Past Entries");
        eHist_title.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        eHist_title.setOpaque(true);
        editHistory.add(eHist_title, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 950, 50));

        getContentPane().add(editHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

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
        s_title.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
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

        settings.add(s_colorsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 380, 220));

        s_customScreensaverPanel.setBackground(new java.awt.Color(156, 183, 133));
        s_customScreensaverPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_customScreensaverPanel.setLayout(null);

        s_awayFromScreenTitle1.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreenTitle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreenTitle1.setForeground(java.awt.Color.white);
        s_awayFromScreenTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenTitle1.setText("   Away From Screen Options");
        s_customScreensaverPanel.add(s_awayFromScreenTitle1);
        s_awayFromScreenTitle1.setBounds(10, 5, 480, 25);

        s_awayFromScreenSelectedText.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreenSelectedText.setForeground(java.awt.Color.white);
        s_awayFromScreenSelectedText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenSelectedText.setText("SELECTED");
        s_awayFromScreenSelectedText.setToolTipText("");
        s_customScreensaverPanel.add(s_awayFromScreenSelectedText);
        s_awayFromScreenSelectedText.setBounds(20, 40, 140, 81);

        s_awayFromScreenOption1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/skylineScreensaver.png"))); // NOI18N
        s_awayFromScreenOption1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_awayFromScreenOption1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayFromScreenOptionClicked(evt);
            }
        });
        s_customScreensaverPanel.add(s_awayFromScreenOption1);
        s_awayFromScreenOption1.setBounds(20, 40, 140, 81);

        s_awayFromScreenOption2.setForeground(java.awt.Color.white);
        s_awayFromScreenOption2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenOption2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/fishBackgroundScreensaver.png"))); // NOI18N
        s_awayFromScreenOption2.setToolTipText("");
        s_awayFromScreenOption2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        s_awayFromScreenOption2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_awayFromScreenOption2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayFromScreenOptionClicked(evt);
            }
        });
        s_customScreensaverPanel.add(s_awayFromScreenOption2);
        s_awayFromScreenOption2.setBounds(180, 40, 140, 81);

        s_awayFromScreenOption3.setBackground(java.awt.Color.black);
        s_awayFromScreenOption3.setForeground(java.awt.Color.white);
        s_awayFromScreenOption3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenOption3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        s_awayFromScreenOption3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_awayFromScreenOption3.setOpaque(true);
        s_awayFromScreenOption3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayFromScreenOptionClicked(evt);
            }
        });
        s_customScreensaverPanel.add(s_awayFromScreenOption3);
        s_awayFromScreenOption3.setBounds(340, 40, 140, 81);

        s_awayFromScreenOption4.setForeground(java.awt.Color.white);
        s_awayFromScreenOption4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenOption4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/todaysProgressScreensaver.jpg"))); // NOI18N
        s_awayFromScreenOption4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        s_awayFromScreenOption4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_awayFromScreenOption4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayFromScreenOptionClicked(evt);
            }
        });
        s_customScreensaverPanel.add(s_awayFromScreenOption4);
        s_awayFromScreenOption4.setBounds(20, 130, 140, 81);

        s_awayFromScreenOption5.setForeground(java.awt.Color.white);
        s_awayFromScreenOption5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenOption5.setText("<PROGRES PANEL>");
        s_awayFromScreenOption5.setToolTipText("");
        s_awayFromScreenOption5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        s_awayFromScreenOption5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_awayFromScreenOption5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayFromScreenOptionClicked(evt);
            }
        });
        s_customScreensaverPanel.add(s_awayFromScreenOption5);
        s_awayFromScreenOption5.setBounds(180, 130, 140, 81);

        s_awayFromScreenOption6.setBackground(java.awt.Color.black);
        s_awayFromScreenOption6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/clockScreensaver.png"))); // NOI18N
        s_awayFromScreenOption6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_awayFromScreenOption6.setOpaque(true);
        s_awayFromScreenOption6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awayFromScreenOptionClicked(evt);
            }
        });
        s_customScreensaverPanel.add(s_awayFromScreenOption6);
        s_awayFromScreenOption6.setBounds(340, 130, 140, 81);

        settings.add(s_customScreensaverPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 360, 500, 220));

        s_awayFromScreenPanel.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreenPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        s_awayFromScreenPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        s_awayFromScreenTitle.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreenTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreenTitle.setForeground(java.awt.Color.white);
        s_awayFromScreenTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenTitle.setText("Away From Screen Timeout");
        s_awayFromScreenPanel.add(s_awayFromScreenTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 5, 450, -1));

        s_awayFromScreenTitle2.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreenTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreenTitle2.setForeground(java.awt.Color.white);
        s_awayFromScreenTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreenTitle2.setText("Current Saved:");
        s_awayFromScreenPanel.add(s_awayFromScreenTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 140, -1));

        s_awayFromScreen.setBackground(new java.awt.Color(156, 183, 133));
        s_awayFromScreen.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        s_awayFromScreen.setForeground(java.awt.Color.white);
        s_awayFromScreen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        s_awayFromScreen.setText("1 Minute");
        s_awayFromScreenPanel.add(s_awayFromScreen, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 140, -1));

        s_awayFromScreenInput.setEditor(new javax.swing.JSpinner.NumberEditor(s_awayFromScreenInput, ""));
        s_awayFromScreenInput.setOpaque(true);
        s_awayFromScreenPanel.add(s_awayFromScreenInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 300, 40));

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
        s_awayFromScreenPanel.add(s_awayFromScreenButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 380, 60));

        settings.add(s_awayFromScreenPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 450, 220));

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

        settings.add(s_connectionPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 80, 560, 220));

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
        settings.add(s_turnOffAwayFromScreenButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, 950, 50));

        getContentPane().add(settings, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

        screensaverPanel.setBackground(java.awt.Color.black);
        screensaverPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                screensaverPanelMouseClicked(evt);
            }
        });
        screensaverPanel.setLayout(null);

        screensaverFishTankBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/screensaverFishBackground.png"))); // NOI18N
        screensaverFishTankBackground.setOpaque(true);
        screensaverPanel.add(screensaverFishTankBackground);
        screensaverFishTankBackground.setBounds(0, 0, 1040, 600);

        screensaverTodaysProgress.setBackground(new java.awt.Color(255, 204, 51));
        screensaverTodaysProgress.setBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        screensaverTodaysProgress.setLayout(null);

        screensaverTodaysProgressTitle.setBackground(new java.awt.Color(255, 204, 51));
        screensaverTodaysProgressTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        screensaverTodaysProgressTitle.setForeground(new java.awt.Color(255, 255, 255));
        screensaverTodaysProgressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        screensaverTodaysProgressTitle.setText("Left To Do Today");
        screensaverTodaysProgressTitle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        screensaverTodaysProgressTitle.setOpaque(true);
        screensaverTodaysProgress.add(screensaverTodaysProgressTitle);
        screensaverTodaysProgressTitle.setBounds(10, 10, 820, 40);

        screensaverTodaysProgressDisplay.setBackground(new java.awt.Color(255, 204, 51));
        screensaverTodaysProgressDisplay.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        screensaverTodaysProgress.add(screensaverTodaysProgressDisplay);
        screensaverTodaysProgressDisplay.setBounds(10, 60, 820, 330);

        screensaverPanel.add(screensaverTodaysProgress);
        screensaverTodaysProgress.setBounds(100, 100, 840, 400);

        screensaverTimeText.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        screensaverTimeText.setForeground(new java.awt.Color(255, 255, 255));
        screensaverTimeText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        screensaverTimeText.setText("7:20 PM");
        screensaverPanel.add(screensaverTimeText);
        screensaverTimeText.setBounds(320, 60, 400, 70);

        screensaverDateText.setFont(new java.awt.Font("Segoe UI Black", 1, 24)); // NOI18N
        screensaverDateText.setForeground(new java.awt.Color(255, 255, 255));
        screensaverDateText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        screensaverDateText.setText("Thursday, December 11, 2025");
        screensaverDateText.setToolTipText("");
        screensaverPanel.add(screensaverDateText);
        screensaverDateText.setBounds(320, 120, 400, 50);

        skylinePanel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/screensaverSkylineP1.png"))); // NOI18N
        screensaverPanel.add(skylinePanel1);
        skylinePanel1.setBounds(0, 0, 1040, 600);

        skylinePanel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/screensaverSkylineP2.png"))); // NOI18N
        screensaverPanel.add(skylinePanel2);
        skylinePanel2.setBounds(0, 0, 1040, 600);

        skylinePanel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/screensaverSkylineP3.png"))); // NOI18N
        screensaverPanel.add(skylinePanel3);
        skylinePanel3.setBounds(0, 0, 1040, 600);

        getContentPane().add(screensaverPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1040, 600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [SWITCH FRAMES FUNCTION ] ===============================================================================
    // =================================================================================================================================
    private void switchFrame(JPanel target){
        // Hidding all other frames (and keyboard)
        keyboard.setVisible(false);
        screensaverPanel.setVisible(false);
        home.setVisible(false);
        settings.setVisible(false);
        progress.setVisible(false);
        addHabit.setVisible(false);
        editHabit.setVisible(false);
        editHistory.setVisible(false);
        navigationPanel.setVisible(true);
        
        
        
        // Stoping timer to reset since we are moving screens
        awayFromScreenCounter = 0;     // Reseting counter
        if(awayFromScreen.isRunning()) // Stopping timer if its running
            awayFromScreen.stop();
        
        // Frames specific instructions for setting up
        if(target == home){
            moveNavSelector(homeButton); // Moving the navSelector to the correct position
            refreshHomeData();                   // Reloads data of habit cards using the two arrays we have of them
            
            h_scrollPane.getVerticalScrollBar().setValue(0); // Moving the scroll value to zero
            scrollButtonClicked(null);                       // Simulating that the scroll up was clicked so it gets grayed out
            if(h_habitPanel.getComponentCount() > 8){
                h_scrollUpPanel.setVisible(true);   // Show the scroll button again IF we need it 
                h_scrollDownPanel.setVisible(true); // Show the scroll button again IF we need it 
            }
            else{
                h_scrollUpPanel.setVisible(false);   // Hide the scroll button when we dont need it 
                h_scrollDownPanel.setVisible(false); // Hide the scroll button when we dont need it  
            }
            
            if(awayIsOn)                         // Only turn on timer if this is on
                awayFromScreen.start();          // Starting the timer again if we went to home screen
        }
        else if(target == settings){
            moveNavSelector(settingsButton); // Moving the navSelector to the correct position
            s_colorsPanel.setVisible(true);                // Showing the color panel
            s_customScreensaverPanel.setVisible(true);     // Showing the custom name panel
            s_connectionPanel.setVisible(true);            // Showing the connection panel
            if(s_turnOffAwayFromScreenButton.getText().equals("Turn Off Away From Screen")){  // If state right now is on, then show panel below
                s_awayFromScreenPanel.setVisible(true);    // Showing the away from screen panel
                s_customScreensaverPanel.setVisible(true); // SHowing the screensaver customizer panel
            }
            else{
                s_awayFromScreenPanel.setVisible(false);   // Hiding when state shows that it is off
                s_customScreensaverPanel.setVisible(true); /// Hiding wehn state shows that its off
            }
            
            // Moving the chosen screensaver cover to the correct panel
            switch(screensaver.getScreensaver()){
                case 1 -> s_awayFromScreenSelectedText.setLocation(s_awayFromScreenOption1.getX(), s_awayFromScreenOption1.getY());
                case 2 -> s_awayFromScreenSelectedText.setLocation(s_awayFromScreenOption2.getX(), s_awayFromScreenOption2.getY());
                case 3 -> s_awayFromScreenSelectedText.setLocation(s_awayFromScreenOption3.getX(), s_awayFromScreenOption3.getY());
                case 4 -> s_awayFromScreenSelectedText.setLocation(s_awayFromScreenOption4.getX(), s_awayFromScreenOption4.getY());
                case 5 -> s_awayFromScreenSelectedText.setLocation(s_awayFromScreenOption5.getX(), s_awayFromScreenOption5.getY());
                case 6 -> s_awayFromScreenSelectedText.setLocation(s_awayFromScreenOption6.getX(), s_awayFromScreenOption6.getY());
            }
        }
        else if(target == progress){
            moveNavSelector(progressButton); // Moving the navSelector to the correct position
        }
        else if(target == addHabit){
            moveNavSelector(addHabitButton); // Moving the navSelector to the correct position
            ah_ResetButtonMouseClicked(null); // Reset the addhabit panel
        }
        else if(target == editHabit){
            moveNavSelector(editHabitButton); // Moving the navSelector to the correct position
            eh_resetPanel();
        }
        else if(target == editHistory){
            moveNavSelector(editHistoryButton); // Moving the navSelector to the correct position
        }
        else if(target == screensaverPanel){
            navigationPanel.setVisible(false);
        }
        
        
        
        
        // Showing target frame after everything has been set
        target.setVisible(true);
    }
    
    
    
    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ TIME TIMERS ] ==============================================================================================
    // =================================================================================================================================
    private final Timer awayFromScreen = new Timer(1000, e->{
        awayFromScreenCounter++;              // Increase counter
        if(awayFromScreenCounter >= AWAY_FROM_SCREEN_TIME){  // IF: checking if we reached the max time
            ((Timer)e.getSource()).stop();    // Stop Timer
            awayFromScreenCounter = 0;        // Reset counter
            screensaver.startScreenSaver();   // Starting the screensaver
            switchFrame(screenSaver_again);   // Take back to progress
            // ---------------------------------------------------------------->  Make brightness lower [ADD LATER]
        }
    });
    
    public void reseAwaytAway(){
        awayFromScreenCounter = 0; // This should alone "reset" the timer to start from the beginning of the counting
    }

    // Away from screen button is pressed, cycle between turning it on and off
    private void s_turnOffAwayFromScreenButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_turnOffAwayFromScreenButtonMouseClicked
        // State is currently (ON) we are turning (OFF)
        if(s_turnOffAwayFromScreenButton.getText().equals("Turn Off Away From Screen")){
            s_turnOffAwayFromScreenButton.setText("Turn On Away From Screen");
            s_awayFromScreenPanel.setVisible(false);
            s_customScreensaverPanel.setVisible(false);
            awayIsOn = false;
        }
        // State is currently (OFF) we are turning (ON)
        else{
            s_turnOffAwayFromScreenButton.setText("Turn Off Away From Screen");
            s_awayFromScreenPanel.setVisible(true);
            s_customScreensaverPanel.setVisible(true);
            awayIsOn = true;
        }
    }//GEN-LAST:event_s_turnOffAwayFromScreenButtonMouseClicked
    
    // Checking every 5 seconds for a time update
    private final Timer dateTimeClock = new Timer(5000,e->{
        updateDateAndTime();
    });
    
    public void updateDateAndTime(){
        // Grabbing date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        
        // Updating home date
        h_date.setText(now.format(dateFormatter));
        
        // Updating skyline screensave date
        screensaverDateText.setText(now.format(dateFormatter));
        screensaverTimeText.setText(now.format(timeFormatter));
    }
    
    
  
    
    // =================================================================================================================================
    // ================== [ LOADING    FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
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
        Color temp2 = new Color(100,204,255);
        Color temp3 = new Color(90,50,30);
        Color temp4 = new Color(30,204,204);
        Color temp5 = new Color(50,90,255);
        Color temp6 = new Color(100,153,255);
        Color temp7 = new Color(20,204,255);
        Color temp8 = new Color(20,50,200);
        String week = "1111111";
        
        // We should only add them to array in here and then later using the refresh screen for the home, we should check the week
        allQuantityCards.add(new HabitCard_Quantity(this, "Drink Water", temp1, 0, 8, 1, week));
        allQuantityCards.add(new HabitCard_Quantity(this, "Drink Milk", temp2, 0, 3, 0.5, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Dont eat big", temp2, true,week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Run 3 Miles", temp3, false,week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Seomthing", temp3, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Again", temp5, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Againfds", temp6, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Againfsd", temp7, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Agains", temp8, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Agaisdfn", temp8, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "asd", temp8, false, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "Agaifsdsdfn", temp8, true, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "asfdwsd", temp8, true, week));
        allYesNoCards.add(new HabitCard_YesNo(this, "ffws", temp8, true, week));
        
        // Refreshing happens later in constructor when we call switchFrame(home);
    }
    
    // PAINTS ALL THE PANELS
    private void paintColors(){
        
        // COLLECTING ALL PANELS, BUTTONS, AND LABELS
        JPanel primaryColored[] = {
            // HOME SCREEN:
            navigationPanel,
            
            // SETTINGS SCREEN:
            s_colorsPanel, s_customScreensaverPanel, s_awayFromScreenPanel, s_connectionPanel,
            // PROGRESS SCREEN:
            
            // KEYBOARD:
            keyboard,
            // EDIT HISTORY
            
            // ADD HABIT:
            ah_NamePanel, ah_SummaryPanel, ah_DaysPanel, ah_QuantityPanel,
            
            // EDIT HABIT:
            eh_editHabitSummaryPanel, eh_editNamePanel,
            eh_editDaysPanel, eh_editIncrementAndGoalPanel, eh_deletePanel, 
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
            eh_chooseHabitPanel, editHabit, eh_chooseHabitDisplay, eh_editHabitPanel, eh_bottomButtonsPanel
        };
        JButton buttonColored[] = {
            // HOME SCREEN:
            
            // SETTINGS SCREEN:
            s_primaryColorButton, s_secondaryColorButton, s_buttonColorButton, s_textColorButton, s_awayFromScreenButton, 
            s_turnOffAwayFromScreenButton,
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
            eh_editNameButton, eh_editColorButton, eh_editIncrementAndGoalButton,
            eh_editDaysButton, eh_editNameCancelButton, eh_editNameSaveButton, eh_deleteHabitButton, eh_cancelChangesButton, eh_saveHabitButton,
            eh_editDaysCancelButton, eh_editDaysSaveButton, eh_quantityDecrease, eh_euantityIncrease, eh_editIncrementAndGoalCancelButton, eh_editIncrementAndGoalSaveButton,
            eh_deleteConfirmButton, eh_deleteCancelButton,
        };
        
        JLabel textColored[] = {
            // HOME SCREEN:
            
            // SETTINGS SCREEN:
            s_title, s_colorsTitle, s_awayFromScreenTitle, 
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
            eh_title, eh_editHabitText1, eh_editHabitText2, eh_editHabitText3, eh_editHabitText4, 
            eh_editHabitText5, eh_editHabitText6, eh_editIncrementAndGoalText1, eh_editIncrementAndGoalText2, eh_editIncrementAndGoalGoal,
            eh_deleteText1,
            
            // SCREENSAVER PANEL
            screensaverTimeText, screensaverDateText,
        };
        
        JToggleButton toggleButtonColored[] = {
            ah_Monday, ah_Tuesday, ah_Wednesday, ah_Thursday, ah_Friday, ah_Saturday, ah_Sunday,  
            ah_IncrementPointOne, ah_IncrementPointFive, ah_IncrementOne,
            eh_Monday, eh_Tuesday, eh_Wednesday, eh_Thursday, eh_Friday, eh_Saturday, eh_Sunday,  
            eh_IncrementPointOne, eh_IncrementPointFive, eh_IncrementOne,
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
        // HOME: Painting the backgrounds of the navigation buttons to the same primary color INSTEAD of the button color
        settingsButton.setBackground(PRIMARY_COLOR);
        addHabitButton.setBackground(PRIMARY_COLOR);
        editHabitButton.setBackground(PRIMARY_COLOR);
        editHistoryButton.setBackground(PRIMARY_COLOR);
        progressButton.setBackground(PRIMARY_COLOR);
        
        // HOME: Painting the scroll buttons/panels
        h_scrollUpPanel.setBackground(PRIMARY_COLOR);
        h_scrollUpPanel.setForeground(TEXT_COLOR);
        h_scrollDownPanel.setBackground(PRIMARY_COLOR);
        h_scrollDownPanel.setForeground(TEXT_COLOR);
        
        // HOME (TESTING) : Painting the date
        h_date.setBackground(PRIMARY_COLOR);
        h_date.setForeground(TEXT_COLOR);
        
        
        // ADD: Painting the input for the name of the new habit
        h_addHabitName.setBackground(PRIMARY_COLOR);
        h_addHabitName.setForeground(TEXT_COLOR);
        
        // ADD: Painting the title of the panel
        ah_title.setBackground(PRIMARY_COLOR);
        
        // ADD: Painting labels that were actually suppose to be buttons
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
        
        
        // SETTINGS: Painting away from screentime input 
        JFormattedTextField txt = ((JSpinner.DefaultEditor) s_awayFromScreenInput.getEditor()).getTextField();
        txt.setBackground(PRIMARY_COLOR);
        txt.setForeground(TEXT_COLOR);
        
        
        // EDIT HABIT: Painting the text field in the edit name section
        eh_nameInput.setBackground(PRIMARY_COLOR);
        eh_nameInput.setForeground(TEXT_COLOR);
        
        
        // EDIT HABIT: Painting the scroll buttons on the edit habit panel
        eh_scrollUpPanel.setBackground(darkenColor(PRIMARY_COLOR));
        eh_scrollUpPanel.setForeground(TEXT_COLOR);
        eh_scrollDownPanel.setBackground(darkenColor(PRIMARY_COLOR));
        eh_scrollDownPanel.setForeground(TEXT_COLOR);
        
        
        // SCREENSAVER : If the screensavers is "Skyline" | "today progress" | "simple clock/date" ----> Then we need to repaint certain things
        switch(screensaver.getScreensaver()){
            case 1 -> {
                screensaverTimeText.setForeground(TEXT_COLOR);
                screensaverDateText.setForeground(TEXT_COLOR);
            }
            case 4 -> {
                screensaverTodaysProgressTitle.setBackground(PRIMARY_COLOR);
                screensaverTodaysProgressTitle.setForeground(TEXT_COLOR);
                screensaverTodaysProgress.setBackground(SECONDARY_COLOR);
                screensaverTodaysProgressDisplay.setBackground(PRIMARY_COLOR);
                screensaverPanel.setBackground(darkenColor(PRIMARY_COLOR));
            }
            case 6 -> {
                screensaverTimeText.setForeground(TEXT_COLOR);
                screensaverDateText.setForeground(TEXT_COLOR);
                screensaverPanel.setBackground(PRIMARY_COLOR);
            }
        }
        
        
        // Making sure everything gets repainted
        this.repaint();
        this.revalidate();
    }
    
    
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

    int navTargetY = 3;
    Timer navSelectorTimer = new Timer(20,e->{
        // finish this section here to move the selector up and down 
        
    });
    private void moveNavSelector(JButton target){
        if(navSelectorTimer.isRunning())
            navSelectorTimer.stop();
        
        navTargetY = target.getY() + 3; // sets the target for the nav selector to move to before starting the timer
        navSelectorTimer.start();
    }
    
    
    
    
    // =================================================================================================================================
    // ================== [ HOME SCREEN FUNCTIONS ] ====================================================================================
    // =================================================================================================================================
    private Timer scrollAnimation = null;
    private int homeTargetY = 0;
    private final int scrollStep = 10;
    private void scrollButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_scrollButtonClicked
        // Making sure button was not null, if it was sent from switchframe, this step will be skipped and we will simply paint as follows in the next section
        if(evt != null){        
            // Don't scroll if we are currently inside an animation
            if(scrollAnimation != null && scrollAnimation.isRunning()){
                return;
            }
            
            // Activating the animation for the scroll buttons
            JPanel buttonClicked = (JPanel) evt.getSource();
            ((ScrollButton)buttonClicked).animatePress();
            
            // Return if the button is currently disabled
            if(buttonClicked.getBackground() == Color.GRAY)
                return;
            
            
            // Setting the target to where to scroll to (+/- 220) 200 for size of card + 2 padding of 10px, the top and bottom instances
            if(buttonClicked == h_scrollDownPanel){
                homeTargetY = h_scrollPane.getVerticalScrollBar().getValue() + 210;
                if(homeTargetY > (h_scrollPane.getVerticalScrollBar().getMaximum() - h_scrollPane.getVerticalScrollBar().getVisibleAmount()))
                    homeTargetY = h_scrollPane.getVerticalScrollBar().getMaximum() - h_scrollPane.getVerticalScrollBar().getVisibleAmount();
            }
            else{
                homeTargetY = h_scrollPane.getVerticalScrollBar().getValue() - 210;
                if(homeTargetY < 0)
                    homeTargetY = 0;
            }
            
            
            // If the button clicked was a scroll down button, then we are going to be moving down 
            if(buttonClicked == h_scrollDownPanel){
                scrollAnimation = new Timer(10,e->{
                    int currentValue = h_scrollPane.getVerticalScrollBar().getValue();
                    currentValue += scrollStep;
                    
                    if(currentValue >= homeTargetY){
                        currentValue = homeTargetY; // Locking the current value to targetY so that we can make sure no weird jumps are made

                        // Getting state of both colors
                        Color upButtonColor = (currentValue <= 0 ? Color.GRAY : PRIMARY_COLOR);
                        Color downButtonColor = (currentValue >= (h_scrollPane.getVerticalScrollBar().getMaximum() - h_scrollPane.getVerticalScrollBar().getVisibleAmount()) ? Color.GRAY : PRIMARY_COLOR);

                        // Paintings both buttons
                        h_scrollUpPanel.setBackground(upButtonColor);
                        h_scrollDownPanel.setBackground(downButtonColor);
                        ((Timer)e.getSource()).stop();
                    }
                    
                    h_scrollPane.getVerticalScrollBar().setValue(currentValue);
                });
            }
            else{
                scrollAnimation = new Timer(10,e->{
                    int currentValue = h_scrollPane.getVerticalScrollBar().getValue();
                    currentValue -= scrollStep;
                    
                    if(currentValue <= homeTargetY){
                        currentValue = homeTargetY; // Locking the current value to targetY so that we can make sure no weird jumps are made

                        // Getting state of both colors
                        Color upButtonColor = (currentValue <= 0 ? Color.GRAY : PRIMARY_COLOR);
                        Color downButtonColor = (currentValue >= (h_scrollPane.getVerticalScrollBar().getMaximum() - h_scrollPane.getVerticalScrollBar().getVisibleAmount()) ? Color.GRAY : PRIMARY_COLOR);

                        // Paintings both buttons
                        h_scrollUpPanel.setBackground(upButtonColor);
                        h_scrollDownPanel.setBackground(downButtonColor);
                        ((Timer)e.getSource()).stop();
                    }
                    
                    h_scrollPane.getVerticalScrollBar().setValue(currentValue);
                });
            }
            
            // Start the animation timer
            scrollAnimation.start();
        }
        
        else{
            int currentValue = h_scrollPane.getVerticalScrollBar().getValue();
            Color upButtonColor = (currentValue <= 0 ? Color.GRAY : PRIMARY_COLOR);
            Color downButtonColor = (currentValue >= (h_scrollPane.getVerticalScrollBar().getMaximum() - h_scrollPane.getVerticalScrollBar().getVisibleAmount()) ? Color.GRAY : PRIMARY_COLOR);

            // Paintings both buttons
            h_scrollUpPanel.setBackground(upButtonColor);
            h_scrollDownPanel.setBackground(downButtonColor);
        }
    }//GEN-LAST:event_scrollButtonClicked
    
    private void updateScrollPaneSize(){
        int count = h_habitPanel.getComponentCount();   // number of habit cards
        int rows = (int)Math.ceil(count / 4.0);         // Each row has 4 cards
        int contentHeight = 10 + (rows * (200 + 10));   // Setting the content height using card height and gap in between

        int contentWidth = h_habitPanel.getWidth();
        h_habitPanel.setPreferredSize(new Dimension(contentWidth, contentHeight));
        h_habitPanel.revalidate();
        h_habitPanel.repaint();
    }
    
    private void refreshHomeData(){
        // Updating date and time so that we can see if the date changed and what to show on the main screen for the habits
        updateDateAndTime();
        String todaysWeekDay = h_date.getText().substring(0,h_date.getText().indexOf(','));
        
        
        // Clean the screen first of all instances of the visuals of the objects
        h_habitPanel.removeAll();
        
        
        // Now adding the instances back, this way we get a clean updated version of the lists
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
        if((keyboardTarget == h_addHabitName || keyboardTarget == eh_nameInput)
           && keyboardTarget.getText().length() >= 17)
            return;
        
        String current = keyboardTarget.getText();           // Getting the current text from the target input
        String input = ((JButton)evt.getSource()).getText(); // Getting the button text that was pressed
        
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
        ah_IncrementButtonGroup.clearSelection();
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





    
    
    
    
    // =================================================================================================================================
    // ================== [ SCREEN SAVER FUNCTIONS ] ===================================================================================
    // =================================================================================================================================

    private void screensaverPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_screensaverPanelMouseClicked
        screensaver.stopClock(); // Stopping the clock
        switchFrame(home);
    }//GEN-LAST:event_screensaverPanelMouseClicked

    // MOVE TO -> SETTINGS
    private void awayFromScreenOptionClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_awayFromScreenOptionClicked
        // Moving the selected text to position of the button clicked
        JLabel buttonClicked = (JLabel) evt.getSource();
        s_awayFromScreenSelectedText.setLocation(buttonClicked.getX(), buttonClicked.getY());
        
        // --- SKYLINE SCREENSAVER ----------------------
        if(buttonClicked == s_awayFromScreenOption1){
            screensaverTimeText.setForeground(TEXT_COLOR);
            screensaverDateText.setForeground(TEXT_COLOR);
            screensaver.setScreensaver(1);
        } // --------------------------------------------
       
        
        
        
        // --- FISH TANK SCREENSAVER ---------------------
        else if(buttonClicked == s_awayFromScreenOption2){
            screensaver.setScreensaver(2);
        } // ---------------------------------------------
        
        
        
        
        //  --- BLANK BLACK SCREENSAVER --------------- 
        else if(buttonClicked == s_awayFromScreenOption3){
            // Background 
            screensaverPanel.setBackground(Color.BLACK);
            screensaver.setScreensaver(3);
        } // ----------------------------------------------
        
        
        
        
        //  --- TODAYS PROGRESS SCREENSAVER ----------------- 
        else if(buttonClicked == s_awayFromScreenOption4){
            // Title:
            screensaverTodaysProgressTitle.setBackground(PRIMARY_COLOR);
            screensaverTodaysProgressTitle.setForeground(TEXT_COLOR);
            // Center Piece:
            screensaverTodaysProgress.setBackground(SECONDARY_COLOR);
            screensaverTodaysProgressDisplay.setBackground(PRIMARY_COLOR);
            // Background 
            screensaverPanel.setBackground(darkenColor(PRIMARY_COLOR));
            screensaver.setScreensaver(4);
        } // -------------------------------------------------

        

        //  --- OVERALL PROGRESS SCREENSAVER --------------- 
        else if(buttonClicked == s_awayFromScreenOption5){
            screensaver.setScreensaver(5);
        } // -----------------------------------------------
        
        
        
        
        //  --- SIMPLE CLOCK AND DATE SCREENSAVER --------- 
        else if(buttonClicked == s_awayFromScreenOption6){
            // Background 
            screensaverPanel.setBackground(PRIMARY_COLOR);
            screensaverTimeText.setForeground(TEXT_COLOR);
            screensaverDateText.setForeground(TEXT_COLOR);
            screensaver.setScreensaver(6);
        } // ------------------------------------------- 
    }//GEN-LAST:event_awayFromScreenOptionClicked

    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ EDIT HABIT FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
    private HabitCard_Quantity targetQuantityCard = null;
    private HabitCard_YesNo targetYesNoCard = null;
    private String savedWeek = "";
    
    
    private void makeEditHabitCard(String habitName, Color habitColor, int buttonW, int buttonH, int roundness, boolean isQuantityCard, double goal){
        // Making new button with custom painting
        JPanel newPanel = new JPanel(){                              
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(PRIMARY_COLOR);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, roundness, roundness);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, roundness, roundness);

                g2.dispose();
            }
        };           

        // Setting Settings for the panel views
        newPanel.setPreferredSize(new Dimension(buttonW, buttonH)); // Setting the size of the button
        newPanel.setOpaque(false);
        newPanel.setLayout(null);

        
        // Connecting this button to the function below 
        newPanel.addMouseListener(new MouseAdapter(){               
            @Override public void mouseClicked(MouseEvent e){ eh_habitChosen(e); }
        });

        
        // Making the texts for this panel and adding to the new panel before adding to the chooseHabitDisplay
        JLabel hnText = new JLabel(habitName); 
        JLabel hnLabel = new JLabel("Name: ");
        JLabel cLabel = new JLabel("Color:");
        JLabel cBox = new JLabel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(habitColor);
                g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, roundness/2, roundness/2);
                g2.setColor(Color.BLACK);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, roundness/2, roundness/2);

                g2.dispose();
            }
        };
        cBox.setOpaque(false);
        
        
        hnText.setForeground(Color.BLACK);
        hnLabel.setForeground(Color.BLACK);
        cLabel.setForeground(Color.BLACK);
        cBox.setBackground(habitColor);
        
        int leftTextW = 50;
        int leftTextH = 20;
        int paddingX = 15;
        int paddingY = (isQuantityCard ? 40 : 50); // Use a different padding if we are drawing a quantity card (to keep everything centered)
        int spacing = 5;
        int colorBoxW = 125;
        hnLabel.setBounds(paddingX, paddingY, leftTextW, leftTextH);
        hnText.setBounds(hnLabel.getX()+hnLabel.getWidth(), hnLabel.getY(), colorBoxW, leftTextH);
        cLabel.setBounds(paddingX, paddingY+leftTextH+spacing, leftTextW, leftTextH);
        cBox.setBounds(cLabel.getX() + cLabel.getWidth(), cLabel.getY(), colorBoxW, leftTextH);
        
        
        hnText.setHorizontalAlignment(SwingConstants.CENTER);
        newPanel.add(hnText);   
        newPanel.add(hnLabel);
        newPanel.add(cLabel);
        newPanel.add(cBox);
        
        
        if(isQuantityCard){
            JLabel goalLabel = new JLabel("Goal:");
            JLabel goalText = new JLabel(Double.toString(goal));
            goalLabel.setForeground(Color.BLACK);
            goalText.setForeground(Color.BLACK);
            goalLabel.setBounds(paddingX, paddingY+(leftTextH+spacing)*2, leftTextW, leftTextH);
            goalText.setBounds(cBox.getX(), goalLabel.getY(), cBox.getWidth(), leftTextH);
            
            goalText.setHorizontalAlignment(SwingConstants.CENTER);
            newPanel.add(goalLabel);
            newPanel.add(goalText);
        }

        // Adding the new button to the display        
        eh_chooseHabitDisplay.add(newPanel);      
    }
    
    private void eh_resetPanel(){
        // Resetting everything
        eh_chooseHabitDisplay.removeAll(); // Removes all previous buttons from the display 
        targetQuantityCard = null;
        targetYesNoCard = null;
        keyboard.setVisible(false);
        keyboard.setLocation(190,-330);
        eh_IncrementButtonGroup.clearSelection();
        eh_bottomButtonsPanel.setVisible(true);
        eh_editHabitPanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_editNamePanel.setVisible(false);
        eh_editDaysPanel.setVisible(false);
        eh_editIncrementAndGoalPanel.setVisible(false);
        eh_deletePanel.setVisible(false);
        
        
        
        
        // Information for easier view, DO NOT CHANGE!
        int buttonW = 200; 
        int buttonH = 150; 
        int gap = 10;
        int roundness = 50;
        
        
        
        // Adding the current saved habits into the eh_chooseHabitDisplay (JPanel)
        for(HabitCard_Quantity currCard : allQuantityCards)
            makeEditHabitCard(currCard.getHabitName(), currCard.getHabitColor(), buttonW, buttonH, roundness, true, currCard.getGoal());
        for(HabitCard_YesNo currCard : allYesNoCards)
            makeEditHabitCard(currCard.getHabitName(), currCard.getHabitColor(), buttonW, buttonH, roundness, false, 0);
        
        
        
        // Setting the preffered size of the display
        int count = allQuantityCards.size() + allYesNoCards.size();
        int rows = (int)Math.ceil(count / 4.0);         
        int contentHeight = gap + (rows * (buttonH + gap));  
        int contentWidth = eh_chooseHabitDisplay.getWidth();
        eh_chooseHabitDisplay.setPreferredSize(new Dimension(contentWidth, contentHeight));
        eh_chooseHabitDisplay.revalidate();
        eh_chooseHabitDisplay.repaint();
        
        
        // Resetting and painting the scroll bars 
        eh_scrollPane.getVerticalScrollBar().setValue(0); // Resetting the scroll pane
        eh_scrollUpPanel.setBackground(Color.GRAY);                   // Repatining
        eh_scrollDownPanel.setBackground(count <= 8 ? Color.GRAY : darkenColor(PRIMARY_COLOR)); // Repainting
        
        
        // Making first steps visible
        eh_chooseHabitPanel.setVisible(true); // Main Choosing panel can be made visible to start there
    }
    
    private void eh_scrollButtonClicked(java.awt.event.MouseEvent evt){
        ((ScrollButton) evt.getSource()).animatePress();

        int pos = eh_scrollPane.getVerticalScrollBar().getValue();

        // Finding out which scroll button was clicked and incremting that amount to the current position
        if(((JPanel)evt.getSource()) == eh_scrollDownPanel){
            pos += 150 + 10;
        }
        else{
            pos -= 150 + 10;
            if(pos < 0)
                pos = 0;
        }

        
        
        Color upButtonColor = (pos <= 0 ? Color.GRAY : darkenColor(PRIMARY_COLOR));
        Color downButtonColor = (pos >= (eh_scrollPane.getVerticalScrollBar().getMaximum() - eh_scrollPane.getVerticalScrollBar().getVisibleAmount()) ?
                                Color.GRAY : darkenColor(PRIMARY_COLOR));

        // Paintings both buttons
        eh_scrollUpPanel.setBackground(upButtonColor);
        eh_scrollDownPanel.setBackground(downButtonColor);
        
        eh_scrollPane.getVerticalScrollBar().setValue(pos);
    }
    
    private void eh_habitChosen(java.awt.event.MouseEvent evt){
        // Saving the card that we are going to be editing in one of the two variables above
        String targetHabitName = ((JLabel)((JPanel)evt.getSource()).getComponent(0)).getText();
        for(HabitCard_Quantity currCard : allQuantityCards){
            if(currCard.getHabitName().equals(targetHabitName)){
                targetQuantityCard = currCard;
                break;
            }
        }
        if(targetQuantityCard == null){ // If we didnt find anything in quantity, then we can search here too
            for(HabitCard_YesNo currCard : allYesNoCards){
                if(currCard.getHabitName().equals(targetHabitName)){
                    targetYesNoCard = currCard;
                    break;
                }
            }
        }
        
        // If we did not find a match for some reason, return to no cause errors
        if(targetQuantityCard == null && targetYesNoCard == null)
            return;
        
        

        // Loading the information into the edit habit panel behind the scenes before we change the visuals
        String weekFromCard;
        if(targetQuantityCard != null){
            eh_name.setText(targetQuantityCard.getHabitName());
            eh_color.setBackground(targetQuantityCard.getHabitColor());
            eh_goal.setText(Double.toString(targetQuantityCard.getGoal()));
            eh_increment.setText(Double.toString(targetQuantityCard.getIncrement()));
            weekFromCard = targetQuantityCard.getWeek();
            eh_editHabitSummaryPanel.setSize(630, 280);
            eh_editHabitSummaryPanel.setLocation(150, 80);
        }
        else{  // No need to do targetYesNoCard == null anymore since we made sure of that with earlier catch
            eh_name.setText(targetYesNoCard.getHabitName());
            eh_color.setBackground(targetYesNoCard.getHabitColor());
            weekFromCard = targetYesNoCard.getWeek();
            eh_editHabitSummaryPanel.setSize(630, 170);
            eh_editHabitSummaryPanel.setLocation(150, 135);
        }
        eh_editHabitSummaryPanel.getParent().repaint();
        eh_editHabitSummaryPanel.repaint();
        
        
        
        // Making sure that the week has 7 chracters, if not then we just return
        if(weekFromCard.length() != 7)
            return;
        
        // Building the String using the week format that we got (110000) -> (Mon Tue "" "" "" "" "")
        String newWeekString = ""; 
        newWeekString += (weekFromCard.charAt(0) == '1' ? "Mon" : "");
        newWeekString += (weekFromCard.charAt(1) == '1' ? " Tue" : "");
        newWeekString += (weekFromCard.charAt(2) == '1' ? " Wed" : "");
        newWeekString += (weekFromCard.charAt(3) == '1' ? " Thu" : "");
        newWeekString += (weekFromCard.charAt(4) == '1' ? " Fri" : "");
        newWeekString += (weekFromCard.charAt(5) == '1' ? " Sat" : "");
        newWeekString += (weekFromCard.charAt(6) == '1' ? " Sun" : "");
        eh_days.setText(newWeekString);
        
        savedWeek = weekFromCard;
        
        
        
        // Switching Panels
        eh_chooseHabitPanel.setVisible(false);
        eh_editHabitPanel.setVisible(true);
    }
    
    
    // == ONCE A HABIT IS CHOSEN, THESE ARE THE FUNCTIONS WE ARE USING ==
    private void eh_deleteHabitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_deleteHabitButtonMouseClicked
        
        // Switch frame into the confirm panel before removing the habit
        eh_deletePanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(false);
    }//GEN-LAST:event_eh_deleteHabitButtonMouseClicked

    private void eh_deleteCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_deleteCancelButtonMouseClicked
        // Switch frame into the confirm panel before removing the habit
        eh_deletePanel.setVisible(false);
        eh_bottomButtonsPanel.setVisible(true);
        eh_editHabitSummaryPanel.setVisible(true);
    }//GEN-LAST:event_eh_deleteCancelButtonMouseClicked

    private void eh_deleteConfirmButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_deleteConfirmButtonMouseClicked
        // Remove the habit from the saved arrays
        if(targetQuantityCard != null)
            allQuantityCards.remove(targetQuantityCard);
        else
            allYesNoCards.remove(targetYesNoCard);
        
        // Update the list writing into disk
        
        
        // Reset the Panel -> takes back to the "starting page" of the edit panel
        eh_resetPanel();
    }//GEN-LAST:event_eh_deleteConfirmButtonMouseClicked
    
    private void eh_cancelChangesButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_cancelChangesButtonMouseClicked
        eh_resetPanel();
    }//GEN-LAST:event_eh_cancelChangesButtonMouseClicked

    private void eh_saveHabitButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_saveHabitButtonMouseClicked
        
    }//GEN-LAST:event_eh_saveHabitButtonMouseClicked

    private void eh_editNameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editNameButtonMouseClicked
        keyboard.setLocation(190,330);
        keyboard.setVisible(true);
        eh_editNamePanel.setVisible(true);
        eh_editHabitSummaryPanel.setVisible(false);
        keyboardTarget = eh_nameInput;
        eh_nameInput.setText(targetQuantityCard != null ? targetQuantityCard.getHabitName() : targetYesNoCard.getHabitName());
    }//GEN-LAST:event_eh_editNameButtonMouseClicked

    private void eh_editNameCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editNameCancelButtonMouseClicked
        keyboard.setVisible(false);
        keyboard.setLocation(190,-330);
        keyboardTarget = null;
        eh_editNamePanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
    }//GEN-LAST:event_eh_editNameCancelButtonMouseClicked

    private void eh_editNameSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editNameSaveButtonMouseClicked
        // Check if name is unique
        String oldName = (targetQuantityCard != null ? targetQuantityCard.getHabitName() : targetYesNoCard.getHabitName());
        boolean duplicate = false;
        for(HabitCard_Quantity currCard : allQuantityCards){
            if(!eh_nameInput.getText().equalsIgnoreCase(oldName) && eh_nameInput.getText().equalsIgnoreCase(currCard.getHabitName())){
                duplicate = true;
                break;
            }
        }
        
        if(!duplicate){
            for(HabitCard_YesNo currCard : allYesNoCards){
                if(!eh_nameInput.getText().equalsIgnoreCase(oldName) && eh_nameInput.getText().equalsIgnoreCase(currCard.getHabitName())){
                    duplicate = true;
                    break;
                }
            }
        }
        
        // If we found a duplicate, do nothing 
        if(duplicate){
            flashTextInput(eh_nameInput);
            return;
        }
        
        // Change the name in the summary panel
        eh_name.setText(eh_nameInput.getText());
        
        // Switch back to the summary panel
        keyboard.setVisible(false);
        keyboard.setLocation(190,-330);
        keyboardTarget = null;
        eh_editNamePanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
    }//GEN-LAST:event_eh_editNameSaveButtonMouseClicked

    private void eh_editColorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editColorButtonMouseClicked
        // Open the menu to choose a color
        Color chosenColor = JColorChooser.showDialog(null, "Choose a color:", eh_color.getBackground());
        
        // If color is valid, make the panel this color
        if(chosenColor != null){
            eh_color.setBackground(chosenColor);
        }
    }//GEN-LAST:event_eh_editColorButtonMouseClicked

    private void eh_editDaysButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editDaysButtonMouseClicked
        // Setting up the days that should be ticked, and unticking the ones that should not
        String week;
        if(targetQuantityCard != null)
            week = targetQuantityCard.getWeek();
        else 
            week = targetYesNoCard.getWeek();
        
        
        // If for some reason the week lenght is not 7, we do nothing
        if(week.length() != 7)
            return;
        
        eh_Monday.setSelected(savedWeek.charAt(0) == '1');
        eh_Tuesday.setSelected(savedWeek.charAt(1) == '1');
        eh_Wednesday.setSelected(savedWeek.charAt(2) == '1');
        eh_Thursday.setSelected(savedWeek.charAt(3) == '1');
        eh_Friday.setSelected(savedWeek.charAt(4) == '1');
        eh_Saturday.setSelected(savedWeek.charAt(5) == '1');
        eh_Sunday.setSelected(savedWeek.charAt(6) == '1');
        
        eh_editDaysPanel.setVisible(true);
        eh_editHabitSummaryPanel.setVisible(false);
        eh_bottomButtonsPanel.setVisible(false);
    }//GEN-LAST:event_eh_editDaysButtonMouseClicked

    private void eh_editDaysCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editDaysCancelButtonMouseClicked
        eh_editDaysPanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(true);
    }//GEN-LAST:event_eh_editDaysCancelButtonMouseClicked

    private void eh_editDaysSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editDaysSaveButtonMouseClicked
        // Making sure that at least one of the days are selected
        if(!eh_Monday.isSelected() && !eh_Tuesday.isSelected() && !eh_Wednesday.isSelected() &&
           !eh_Thursday.isSelected() && !eh_Friday.isSelected() && !eh_Saturday.isSelected() && !eh_Sunday.isSelected()){
            flashToggleButton(eh_Monday);
            flashToggleButton(eh_Tuesday);
            flashToggleButton(eh_Wednesday);
            flashToggleButton(eh_Thursday);
            flashToggleButton(eh_Friday);
            flashToggleButton(eh_Saturday);
            flashToggleButton(eh_Sunday);
            return;
        }
        
        

        // Building the new week string in binary to get ready to save
        savedWeek = "";
        savedWeek += (eh_Monday.isSelected() ? "1" : "0");
        savedWeek += (eh_Tuesday.isSelected() ? "1" : "0");
        savedWeek += (eh_Wednesday.isSelected() ? "1" : "0");
        savedWeek += (eh_Thursday.isSelected() ? "1" : "0");
        savedWeek += (eh_Friday.isSelected() ? "1" : "0");
        savedWeek += (eh_Saturday.isSelected() ? "1" : "0");
        savedWeek += (eh_Sunday.isSelected() ? "1" : "0");
        
        // Making the string week to show in summary
        String newWeekString = ""; 
        newWeekString += (savedWeek.charAt(0) == '1' ? "Mon" : "");
        newWeekString += (savedWeek.charAt(1) == '1' ? " Tue" : "");
        newWeekString += (savedWeek.charAt(2) == '1' ? " Wed" : "");
        newWeekString += (savedWeek.charAt(3) == '1' ? " Thu" : "");
        newWeekString += (savedWeek.charAt(4) == '1' ? " Fri" : "");
        newWeekString += (savedWeek.charAt(5) == '1' ? " Sat" : "");
        newWeekString += (savedWeek.charAt(6) == '1' ? " Sun" : "");
        eh_days.setText(newWeekString);
        
        
        
        eh_editDaysPanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(true);
    }//GEN-LAST:event_eh_editDaysSaveButtonMouseClicked

    private void eh_editIncrementAndGoalButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editIncrementAndGoalButtonMouseClicked
        // Setting the increment 
        switch (eh_increment.getText()) {
            case "0.1" -> eh_IncrementPointOne.setSelected(true);
            case "0.5" -> eh_IncrementPointFive.setSelected(true);
            case "1.0" -> eh_IncrementOne.setSelected(true);
            default -> {return;}
        }
        
        // Setting the saved quantity
        eh_editIncrementAndGoalGoal.setText(eh_goal.getText());
        
        eh_editIncrementAndGoalPanel.setVisible(true);
        eh_editHabitSummaryPanel.setVisible(false);
        eh_bottomButtonsPanel.setVisible(false);
    }//GEN-LAST:event_eh_editIncrementAndGoalButtonMouseClicked

    private void eh_editIncrementAndGoalCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editIncrementAndGoalCancelButtonMouseClicked
        eh_editIncrementAndGoalPanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(true);
    }//GEN-LAST:event_eh_editIncrementAndGoalCancelButtonMouseClicked

    private void eh_quantityDecreasedIncreasedButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_quantityDecreasedIncreasedButtonClicked
        // Grabbing data first
        double incrementChosen = (eh_IncrementPointOne.isSelected() ? 0.1 : (eh_IncrementPointFive.isSelected() ? 0.5 : 1));
        double currentGoal = Double.parseDouble(eh_editIncrementAndGoalGoal.getText());
        
        // Setting to minus increment or plus increment
        incrementChosen *= ((JButton)evt.getSource()).getText().equals("-") ? -1 : 1;
        
        // Incrementing 
        currentGoal += incrementChosen;
        currentGoal = Math.round(currentGoal * 10.0) / 10.0; // Rounding to get better numbers
        if(currentGoal < 0) currentGoal = 0; // Making sure we dont go negative
        
        // Setting the text 
        eh_editIncrementAndGoalGoal.setText(Double.toString(currentGoal));
    }//GEN-LAST:event_eh_quantityDecreasedIncreasedButtonClicked

    private void eh_editIncrementAndGoalSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editIncrementAndGoalSaveButtonMouseClicked
        eh_goal.setText(eh_editIncrementAndGoalGoal.getText());
        eh_increment.setText(eh_IncrementPointOne.isSelected() ? "0.1" : (eh_IncrementPointFive.isSelected() ? "0.5" : "1.0"));
        
        // Switching back to summary panel
        eh_editIncrementAndGoalPanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(true);
    }//GEN-LAST:event_eh_editIncrementAndGoalSaveButtonMouseClicked



    
   
    
    
    


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
    
    private Color darkenColor(Color color) {
        double factor = 0.9; // USE THIS FACTOR 0=darker | 1=lighter
        
        int r = Math.max((int)(color.getRed() * factor), 0);
        int g = Math.max((int)(color.getGreen() * factor), 0);
        int b = Math.max((int)(color.getBlue() * factor), 0);
        
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
    private javax.swing.JToggleButton eh_Friday;
    private javax.swing.JToggleButton eh_IncrementOne;
    private javax.swing.JToggleButton eh_IncrementPointFive;
    private javax.swing.JToggleButton eh_IncrementPointOne;
    private javax.swing.JToggleButton eh_Monday;
    private javax.swing.JToggleButton eh_Saturday;
    private javax.swing.JToggleButton eh_Sunday;
    private javax.swing.JToggleButton eh_Thursday;
    private javax.swing.JToggleButton eh_Tuesday;
    private javax.swing.JToggleButton eh_Wednesday;
    private javax.swing.JPanel eh_bottomButtonsPanel;
    private javax.swing.JButton eh_cancelChangesButton;
    private javax.swing.JPanel eh_chooseHabitDisplay;
    private javax.swing.JPanel eh_chooseHabitPanel;
    private javax.swing.JLabel eh_color;
    private javax.swing.JLabel eh_days;
    private javax.swing.JButton eh_deleteCancelButton;
    private javax.swing.JButton eh_deleteConfirmButton;
    private javax.swing.JButton eh_deleteHabitButton;
    private javax.swing.JPanel eh_deletePanel;
    private javax.swing.JLabel eh_deleteText1;
    private javax.swing.JButton eh_editColorButton;
    private javax.swing.JButton eh_editDaysButton;
    private javax.swing.JButton eh_editDaysCancelButton;
    private javax.swing.JPanel eh_editDaysPanel;
    private javax.swing.JButton eh_editDaysSaveButton;
    private javax.swing.JPanel eh_editHabitPanel;
    private javax.swing.JPanel eh_editHabitSummaryPanel;
    private javax.swing.JLabel eh_editHabitText1;
    private javax.swing.JLabel eh_editHabitText2;
    private javax.swing.JLabel eh_editHabitText3;
    private javax.swing.JLabel eh_editHabitText4;
    private javax.swing.JLabel eh_editHabitText5;
    private javax.swing.JLabel eh_editHabitText6;
    private javax.swing.JButton eh_editIncrementAndGoalButton;
    private javax.swing.JButton eh_editIncrementAndGoalCancelButton;
    private javax.swing.JLabel eh_editIncrementAndGoalGoal;
    private javax.swing.JPanel eh_editIncrementAndGoalPanel;
    private javax.swing.JButton eh_editIncrementAndGoalSaveButton;
    private javax.swing.JLabel eh_editIncrementAndGoalText1;
    private javax.swing.JLabel eh_editIncrementAndGoalText2;
    private javax.swing.JButton eh_editNameButton;
    private javax.swing.JButton eh_editNameCancelButton;
    private javax.swing.JPanel eh_editNamePanel;
    private javax.swing.JButton eh_editNameSaveButton;
    private javax.swing.JButton eh_euantityIncrease;
    private javax.swing.JLabel eh_goal;
    private javax.swing.JLabel eh_increment;
    private javax.swing.JLabel eh_name;
    private javax.swing.JTextField eh_nameInput;
    private javax.swing.JButton eh_quantityDecrease;
    private javax.swing.JButton eh_saveHabitButton;
    private javax.swing.JPanel eh_scrollDownPanel;
    private javax.swing.JScrollPane eh_scrollPane;
    private javax.swing.JPanel eh_scrollUpPanel;
    private javax.swing.JLabel eh_title;
    private javax.swing.JTextField h_addHabitName;
    private javax.swing.JLabel h_date;
    private javax.swing.JPanel h_habitPanel;
    private javax.swing.JPanel h_scrollDownPanel;
    private javax.swing.JScrollPane h_scrollPane;
    private javax.swing.JPanel h_scrollUpPanel;
    private javax.swing.JPanel home;
    private javax.swing.JButton homeButton;
    private javax.swing.JSeparator jSeparator1;
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
    private javax.swing.JPanel navSelector;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JLabel p_progressTitle;
    private javax.swing.JPanel progress;
    private javax.swing.JButton progressButton;
    private javax.swing.JLabel s_awayFromScreen;
    private javax.swing.JButton s_awayFromScreenButton;
    private javax.swing.JSpinner s_awayFromScreenInput;
    private javax.swing.JLabel s_awayFromScreenOption1;
    private javax.swing.JLabel s_awayFromScreenOption2;
    private javax.swing.JLabel s_awayFromScreenOption3;
    private javax.swing.JLabel s_awayFromScreenOption4;
    private javax.swing.JLabel s_awayFromScreenOption5;
    private javax.swing.JLabel s_awayFromScreenOption6;
    private javax.swing.JPanel s_awayFromScreenPanel;
    private javax.swing.JLabel s_awayFromScreenSelectedText;
    private javax.swing.JLabel s_awayFromScreenTitle;
    private javax.swing.JLabel s_awayFromScreenTitle1;
    private javax.swing.JLabel s_awayFromScreenTitle2;
    private javax.swing.JPanel s_buttonColor;
    private javax.swing.JButton s_buttonColorButton;
    private javax.swing.JPanel s_colorsPanel;
    private javax.swing.JLabel s_colorsTitle;
    private javax.swing.JPanel s_connectionPanel;
    private javax.swing.JPanel s_customScreensaverPanel;
    private javax.swing.JPanel s_primaryColor;
    private javax.swing.JButton s_primaryColorButton;
    private javax.swing.JPanel s_secondaryColor;
    private javax.swing.JButton s_secondaryColorButton;
    private javax.swing.JPanel s_textColor;
    private javax.swing.JButton s_textColorButton;
    private javax.swing.JLabel s_title;
    private javax.swing.JButton s_turnOffAwayFromScreenButton;
    private javax.swing.JLabel screensaverDateText;
    private javax.swing.JLabel screensaverFishTankBackground;
    private javax.swing.JPanel screensaverPanel;
    private javax.swing.JLabel screensaverTimeText;
    private javax.swing.JPanel screensaverTodaysProgress;
    private javax.swing.JPanel screensaverTodaysProgressDisplay;
    private javax.swing.JLabel screensaverTodaysProgressTitle;
    private javax.swing.JPanel settings;
    private javax.swing.JButton settingsButton;
    private javax.swing.JLabel skylinePanel1;
    private javax.swing.JLabel skylinePanel2;
    private javax.swing.JLabel skylinePanel3;
    // End of variables declaration//GEN-END:variables
}
