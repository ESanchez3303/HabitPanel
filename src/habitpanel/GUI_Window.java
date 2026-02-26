package habitpanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.time.format.TextStyle;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.DateTimeException;
import java.time.format.DateTimeParseException;

// NOTE TO SELF: 1040 x 600 is the screen size




//============================================================================================================================================================================================================
//============================================================================================================================================================================================================
// [ MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS --- MAIN GUI CLASS ] =============
//============================================================================================================================================================================================================
//============================================================================================================================================================================================================
public class GUI_Window extends javax.swing.JFrame {
    // CHANGABLE BEFORE COMPILE VARIABLES: =================================
    int MAX_HABIT_NAME_LENGTH = 15;
    // =====================================================================
    
    // FILE NAMES: ========================================================
    private final String configFileName = "config.txt";
    private final String programFolderName = "PROGRAM FILES";
    private final String habitsFolderName = "HABITS";
    
    // FILE VARIABLES: ============================================
    private int AWAY_FROM_SCREEN_TIME = 60;                 // In seconds (1 minute)
    public Color PRIMARY_COLOR = new Color(221,178,93);    // =.
    public Color SECONDARY_COLOR = new Color(255,204,153); //  | Color variables that can change
    public Color BUTTON_COLOR = new Color(193,144,69);     //  | when reading from the variable file
    public Color TEXT_COLOR = new Color(255,255,255);      // ='
    // ============================================================
    
    
    // CHANGING VARIABLES: ========================================
    private ScreensaverManager screensaver = new ScreensaverManager();  // Holds the instance of the object that manages the screensaver
    private JTextField keyboardTarget = null;    // Holds where we are typing into 
    private JPanel screenSaver_again = null;     // Holds the settings panel for foward refrence
    private JPanel navSelectorAgain = null;      // Holds the navigation panel for foward refrence
    private int awayFromScreenCounter = 0;       // Keeps count from 0- AWAY_FROM_SCREEN_TIME
    private boolean awayIsOn = true;             // User can set this up through the settings to turn it off 
    private LocalDate todaysDate;                // Saving todays date so that we know when it changed
    // ====================================================================
            
    
    // HOLDING VARIABLES: =============================================
    public ArrayList<HabitCard_Quantity> allQuantityCards = new ArrayList<>(); // Holds all the quantity cards that user has made
    public ArrayList<HabitCard_YesNo> allYesNoCards = new ArrayList<>();       // Holds all the yes/no cards that user has made
    private ButtonGroup ah_IncrementButtonGroup = null; // Holds the group button from the add menu
    private ButtonGroup eh_IncrementButtonGroup = null; // Holds the group button from the edit habit menu
    private ButtonGroup ehist_completedButtonGroup = null; // Holds the group button from the edit history panel
    private ButtonGroup ehist_addPanelCompletedButtonGroup = null; // Holds the group button from the edit history panel (in the add panel)
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
        
        
        // Setting up navigation panel code:
        navigationPanel.remove(settingsButton);
        navigationPanel.remove(addHabitButton);
        navigationPanel.remove(homeButton);
        navigationPanel.remove(editHabitButton);
        navigationPanel.remove(progressButton);
        settingsButton = new NavigationButton(NavigationButton.Type.SETTINGS, settingsButton.getX(), settingsButton.getY(), settingsButton.getWidth(), settingsButton.getHeight());
        addHabitButton = new NavigationButton(NavigationButton.Type.ADD, addHabitButton.getX(), addHabitButton.getY(), addHabitButton.getWidth(), addHabitButton.getHeight());
        homeButton = new NavigationButton(NavigationButton.Type.HOME, homeButton.getX(), homeButton.getY(), homeButton.getWidth(), homeButton.getHeight());
        editHabitButton = new NavigationButton(NavigationButton.Type.EDIT, editHabitButton.getX(), editHabitButton.getY(), editHabitButton.getWidth(), editHabitButton.getHeight());
        progressButton = new NavigationButton(NavigationButton.Type.PROGRESS, progressButton.getX(), progressButton.getY(), progressButton.getWidth(), progressButton.getHeight());
        settingsButton.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent e) {settingsButtonClicked();}});
        addHabitButton.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent e) {addHabitButtonClicked();}});
        homeButton.addMouseListener(new MouseAdapter()     { @Override public void mouseClicked(MouseEvent e) {homeButtonClicked();}});
        editHabitButton.addMouseListener(new MouseAdapter(){ @Override public void mouseClicked(MouseEvent e) {editHabitButtonClicked();}});
        progressButton.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent e) {progressButtonClicked();}});
        navigationPanel.add(settingsButton);
        navigationPanel.add(addHabitButton);
        navigationPanel.add(homeButton);
        navigationPanel.add(editHabitButton);
        navigationPanel.add(progressButton);
        
        
        
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
        
        // Setting up edit history panel (button grouping):
        ehist_completedButtonGroup = new ButtonGroup();
        ehist_completedButtonGroup.add(eh_editHistoryCompleteButton);
        ehist_completedButtonGroup.add(eh_editHistoryNotCompleteButton);
        
        // Setting up edit history panel (button grouping) (THE ADD PANEL):
        ehist_addPanelCompletedButtonGroup = new ButtonGroup();
        ehist_completedButtonGroup.add(eh_editHistoryAddCompleteButton);
        ehist_completedButtonGroup.add(eh_editHistoryAddNotCompleteButton);
        
        
        // Setting up edit habit up and down scroll panel
        eh_chooseHabitPanel.remove(eh_scrollUpPanel);
        eh_chooseHabitPanel.remove(eh_scrollDownPanel);
        eh_scrollUpPanel = new ScrollButton(ScrollButton.Type.UP,  ScrollButton.Form.PILL, eh_scrollUpPanel.getX(), eh_scrollUpPanel.getY(), eh_scrollUpPanel.getWidth(), eh_scrollUpPanel.getHeight());
        eh_scrollDownPanel = new ScrollButton(ScrollButton.Type.DOWN, ScrollButton.Form.PILL, eh_scrollDownPanel.getX(), eh_scrollDownPanel.getY(), eh_scrollDownPanel.getWidth(), eh_scrollDownPanel.getHeight());
        eh_scrollUpPanel.addMouseListener(new MouseAdapter() {                                 
            @Override public void mouseClicked(MouseEvent e) {eh_scrollButtonClicked(e);}
        });
        eh_scrollDownPanel.addMouseListener(new MouseAdapter() {                                 
            @Override public void mouseClicked(MouseEvent e) {eh_scrollButtonClicked(e);}
        });
        eh_chooseHabitPanel.add(eh_scrollUpPanel);
        eh_chooseHabitPanel.add(eh_scrollDownPanel);
        
        
        // Setting up the screensaver
        screensaver.setUp(this, screensaverPanel); 
        
        // Setting up the scroll buttons in the home panel (removing, making new object, adding, setting z level to 0)
        home.remove(h_scrollUpPanel);
        home.remove(h_scrollDownPanel);
        h_scrollUpPanel = new ScrollButton(ScrollButton.Type.UP, ScrollButton.Form.PILL,h_scrollUpPanel.getX(), h_scrollUpPanel.getY(), h_scrollUpPanel.getWidth(), h_scrollUpPanel.getHeight());
        h_scrollDownPanel = new ScrollButton(ScrollButton.Type.DOWN, ScrollButton.Form.PILL, h_scrollDownPanel.getX(), h_scrollDownPanel.getY(), h_scrollDownPanel.getWidth(), h_scrollDownPanel.getHeight());
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
        
        
        // Setting up the progress screensaver
        screensaverOverallProgress.remove(overallProgressDayCircle);
        screensaverOverallProgress.remove(overallProgressWeekCircle);
        screensaverOverallProgress.remove(overallProgressMonthCircle);
        screensaverOverallProgress.remove(overallProgressStreakBarGraph);
        screensaverOverallProgress.remove(overallProgressYearBarGraph);
        overallProgressDayCircle = new ProgressCircle("Day", overallProgressDayCircle.getX(), overallProgressDayCircle.getY(), overallProgressDayCircle.getWidth(), overallProgressDayCircle.getHeight());
        overallProgressWeekCircle = new ProgressCircle("Week", overallProgressWeekCircle.getX(), overallProgressWeekCircle.getY(), overallProgressWeekCircle.getWidth(), overallProgressWeekCircle.getHeight());
        overallProgressMonthCircle = new ProgressCircle("Month", overallProgressMonthCircle.getX(), overallProgressMonthCircle.getY(), overallProgressMonthCircle.getWidth(), overallProgressMonthCircle.getHeight());
        overallProgressStreakBarGraph = new StreaksBarGraph(overallProgressStreakBarGraph.getX(), overallProgressStreakBarGraph.getY(), overallProgressStreakBarGraph.getWidth(), overallProgressStreakBarGraph.getHeight());;
        overallProgressYearBarGraph = new YearBarGraph(overallProgressYearBarGraph.getX(), overallProgressYearBarGraph.getY(), overallProgressYearBarGraph.getWidth(), overallProgressYearBarGraph.getHeight());;
        screensaverOverallProgress.add(overallProgressDayCircle);
        screensaverOverallProgress.add(overallProgressWeekCircle);
        screensaverOverallProgress.add(overallProgressMonthCircle);
        screensaverOverallProgress.add(overallProgressStreakBarGraph);
        screensaverOverallProgress.add(overallProgressYearBarGraph);
        
        
        
        // Setting up the scroll button for the progress panel
        progress.remove(p_tableLeftScrollButton);
        progress.remove(p_tableRightScrollButton);
        progress.remove(p_habitLeftScrollButton);
        progress.remove(p_habitRightScrollButton);
        p_tableLeftScrollButton = new ScrollButton(ScrollButton.Type.LEFT, ScrollButton.Form.CIRCLE, p_tableLeftScrollButton.getX(), p_tableLeftScrollButton.getY(), p_tableLeftScrollButton.getWidth(), p_tableLeftScrollButton.getHeight());
        p_tableRightScrollButton = new ScrollButton(ScrollButton.Type.RIGHT,ScrollButton.Form.CIRCLE, p_tableRightScrollButton.getX(), p_tableRightScrollButton.getY(), p_tableRightScrollButton.getWidth(), p_tableRightScrollButton.getHeight());
        p_habitLeftScrollButton = new ScrollButton(ScrollButton.Type.LEFT, ScrollButton.Form.CIRCLE,p_habitLeftScrollButton.getX(), p_habitLeftScrollButton.getY(), p_habitLeftScrollButton.getWidth(), p_habitLeftScrollButton.getHeight());
        p_habitRightScrollButton = new ScrollButton(ScrollButton.Type.RIGHT, ScrollButton.Form.CIRCLE,p_habitRightScrollButton.getX(), p_habitRightScrollButton.getY(), p_habitRightScrollButton.getWidth(), p_habitRightScrollButton.getHeight());
        p_tableLeftScrollButton.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent e) {p_tableScrollButtonClicked(e);}});
        p_tableRightScrollButton.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent e) {p_tableScrollButtonClicked(e);}});
        p_habitLeftScrollButton.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent e) {p_habitScrollButtonClicked(e);}});
        p_habitRightScrollButton.addMouseListener(new MouseAdapter() { @Override public void mouseClicked(MouseEvent e) {p_habitScrollButtonClicked(e);}});
        progress.add(p_tableLeftScrollButton);
        progress.add(p_tableRightScrollButton);
        progress.add(p_habitLeftScrollButton);
        progress.add(p_habitRightScrollButton); 
        
        
        // Setting up the spinners in the edithabit -> edithistory -> add section
        // Set the model of the spinners in this section
        eh_editHistoryAddYear.setModel(new SpinnerNumberModel(LocalDate.now().getYear(), 1900, 2100, 1));
        eh_editHistoryAddMonth.setModel(new SpinnerNumberModel(1, 1, 12, 1));
        eh_editHistoryAddDay.setModel(new SpinnerNumberModel(1, 1, 31, 1));
        
        // Custom for the year, we dont want to show the commas in the years
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(eh_editHistoryAddYear, "####");
        editor.getFormat().setGroupingUsed(false);
        eh_editHistoryAddYear.setEditor(editor);
        
        
        // Setting up the listener for the jtable in the edit history panel
        eh_historyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Only respond when the selection is finalized
                ehist_rowChanged();
            }
        });
        
        // Setting up nav selector again for the "illegal foward refrence"
        navSelectorAgain = navSelector;
        
        // DBEUG:
        
        // Setting up todays date for the first time
        //todaysDate = LocalDate.now();
        todaysDate = LocalDateTime.now().toLocalDate();
        
        
        // Painting the program
        paintColors();      
        
        // Move the navigation selector to the correct position
        moveNavSelector(homeButton);
        
        // Switching to start in home frame
        switchFrame(home);         // Switching to the starting frame (home)
        dateTimeClock.start();     // Start the clock timer that will update the date and time
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        navigationPanel = new javax.swing.JPanel();
        settingsButton = new javax.swing.JPanel();
        addHabitButton = new javax.swing.JPanel();
        homeButton = new javax.swing.JPanel();
        editHabitButton = new javax.swing.JPanel();
        progressButton = new javax.swing.JPanel();
        navSelector = new javax.swing.JPanel();
        editHabit = new javax.swing.JPanel();
        eh_title = new javax.swing.JLabel();
        eh_noHabitsPanel = new javax.swing.JLabel();
        eh_editHistoryPanel = new javax.swing.JPanel();
        eh_editHistoryAddPanel = new javax.swing.JPanel();
        eh_editHistoryAddSaveButton = new javax.swing.JButton();
        eh_editHistoryAddCancelButton = new javax.swing.JButton();
        eh_editHistoryAddYear = new javax.swing.JSpinner();
        eh_editHistoryAddDay = new javax.swing.JSpinner();
        eh_editHistoryAddMonth = new javax.swing.JSpinner();
        eh_editHistoryAddText1 = new javax.swing.JLabel();
        eh_editHistoryAddYesNoPanel = new javax.swing.JPanel();
        eh_editHistoryAddText2 = new javax.swing.JLabel();
        eh_editHistoryAddCompleteButton = new javax.swing.JToggleButton();
        eh_editHistoryAddNotCompleteButton = new javax.swing.JToggleButton();
        eh_editHistoryAddQuantityPanel = new javax.swing.JPanel();
        eh_editHistoryAddText3 = new javax.swing.JLabel();
        eh_editHistoryAddText4 = new javax.swing.JLabel();
        eh_editHistoryAddCurrentGoal = new javax.swing.JLabel();
        eh_editHistoryAddReached = new javax.swing.JLabel();
        eh_editHistoryAddGoal = new javax.swing.JLabel();
        eh_editHistoryAddGoalIncreaseButton = new javax.swing.JButton();
        eh_editHistoryAddReachedDecreaseButton = new javax.swing.JButton();
        eh_editHistoryAddGoalDecreaseButton = new javax.swing.JButton();
        eh_editHistoryAddReachedIncreaseButton = new javax.swing.JButton();
        eh_editHistoryAddIncrement = new javax.swing.JLabel();
        eh_editHistoryAddDateEntryLabels = new javax.swing.JLabel();
        eh_editHistoryAddDateWarning = new javax.swing.JLabel();
        ehist_historyScrollPane = new javax.swing.JScrollPane();
        eh_historyTable = new javax.swing.JTable();
        eh_editHistoryDeletePanel = new javax.swing.JPanel();
        eh_editHistoryDeleteConfirmButton = new javax.swing.JButton();
        eh_editHistoryDeleteCanceButton = new javax.swing.JButton();
        eh_editHistoryDeleteDate = new javax.swing.JLabel();
        eh_editHistoryDeleteText1 = new javax.swing.JLabel();
        eh_editHistoryYesNoPanel = new javax.swing.JPanel();
        eh_editHistoryNotCompleteButton = new javax.swing.JToggleButton();
        eh_editHistoryCompleteButton = new javax.swing.JToggleButton();
        eh_editHistoryQuantityPanel = new javax.swing.JPanel();
        eh_editHIstoryIncreaseButton = new javax.swing.JButton();
        eh_editHistorydDecreaseButton = new javax.swing.JButton();
        eh_editHistoryQuantity = new javax.swing.JLabel();
        eh_text3 = new javax.swing.JLabel();
        eh_editHistoryHabitName = new javax.swing.JLabel();
        eh_editHistoryText1 = new javax.swing.JLabel();
        eh_editHistoryScrollUpButton = new javax.swing.JButton();
        eh_editHistoryScrollDownButton = new javax.swing.JButton();
        eh_editHistoryBackButton = new javax.swing.JButton();
        eh_editHistoryDeleteButton = new javax.swing.JButton();
        eh_editHistoryAddButton = new javax.swing.JButton();
        eh_editHabitPanel = new javax.swing.JPanel();
        eh_editHabitText1 = new javax.swing.JLabel();
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
        eh_editIncrementAndGoalPanel = new javax.swing.JPanel();
        eh_IncrementPointOne = new javax.swing.JToggleButton();
        eh_IncrementPointFive = new javax.swing.JToggleButton();
        eh_IncrementOne = new javax.swing.JToggleButton();
        eh_quantityIncrease = new javax.swing.JButton();
        eh_quantityDecrease = new javax.swing.JButton();
        eh_editIncrementAndGoalSaveButton = new javax.swing.JButton();
        eh_editIncrementAndGoalText1 = new javax.swing.JLabel();
        eh_editIncrementAndGoalGoal = new javax.swing.JLabel();
        eh_editIncrementAndGoalText2 = new javax.swing.JLabel();
        eh_editIncrementAndGoalCancelButton = new javax.swing.JButton();
        eh_editNamePanel = new javax.swing.JPanel();
        eh_editNameCancelButton = new javax.swing.JButton();
        eh_editNameSaveButton = new javax.swing.JButton();
        eh_nameInput = new javax.swing.JTextField();
        eh_bottomButtonsPanel = new javax.swing.JPanel();
        eh_backButton = new javax.swing.JButton();
        eh_deleteHabitButton = new javax.swing.JButton();
        eh_editHistoryButton = new javax.swing.JButton();
        eh_deletePanel = new javax.swing.JPanel();
        eh_deleteConfirmButton = new javax.swing.JButton();
        eh_deleteCancelButton = new javax.swing.JButton();
        eh_deleteText1 = new javax.swing.JLabel();
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
        settings = new javax.swing.JPanel();
        s_title = new javax.swing.JLabel();
        s_colorsPanel = new javax.swing.JPanel();
        s_colorsTitle = new javax.swing.JLabel();
        s_theme1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        s_theme3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        s_theme2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        s_theme4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
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
        progress = new javax.swing.JPanel();
        p_progressTitle = new javax.swing.JLabel();
        p_showAllHabitsText = new javax.swing.JLabel();
        p_tableScrollPane = new javax.swing.JScrollPane();
        p_table = new javax.swing.JTable();
        p_tableRightScrollButton = new javax.swing.JPanel();
        p_habitRightScrollButton = new javax.swing.JPanel();
        p_habitName = new javax.swing.JLabel();
        p_tableLeftScrollButton = new javax.swing.JPanel();
        p_habitLeftScrollButton = new javax.swing.JPanel();
        p_noHabitsPanel = new javax.swing.JLabel();
        p_monthAndYear = new javax.swing.JLabel();
        p_resetMonthButton = new javax.swing.JButton();
        p_showAllHabitsButton = new javax.swing.JButton();
        screensaverPanel = new javax.swing.JPanel();
        screensaverOverallProgress = new javax.swing.JPanel();
        overallProgressStreakBarGraph = new javax.swing.JPanel();
        overallProgressYearBarGraph = new javax.swing.JPanel();
        overallProgressWeekCircle = new javax.swing.JPanel();
        overallProgressDayCircle = new javax.swing.JPanel();
        overallProgressMonthCircle = new javax.swing.JPanel();
        overallProgressNoHabitsText = new javax.swing.JLabel();
        overallProgressNoStreaksText = new javax.swing.JLabel();
        overallProgressText1 = new javax.swing.JLabel();
        overallProgressText2 = new javax.swing.JLabel();
        overallProgressText3 = new javax.swing.JLabel();
        overallProgressText4 = new javax.swing.JLabel();
        screensaverTodaysProgress = new javax.swing.JPanel();
        screensaverTodaysProgressTitle = new javax.swing.JLabel();
        screensaverTodaysProgressDisplay = new javax.swing.JPanel();
        screensaverFishTankBackground = new javax.swing.JLabel();
        screensaverTimeText = new javax.swing.JLabel();
        screensaverDateText = new javax.swing.JLabel();
        skylinePanel1 = new javax.swing.JLabel();
        skylinePanel2 = new javax.swing.JLabel();
        skylinePanel3 = new javax.swing.JLabel();
        home = new javax.swing.JPanel();
        h_scrollUpPanel = new javax.swing.JPanel();
        h_scrollDownPanel = new javax.swing.JPanel();
        h_scrollPane = new javax.swing.JScrollPane();
        h_habitPanel = new javax.swing.JPanel();
        h_savingFilesText = new javax.swing.JLabel();
        h_date = new javax.swing.JLabel();
        h_noHabitsPanel = new javax.swing.JLabel();
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
        key28 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HabitPanel");
        setMinimumSize(new java.awt.Dimension(1040, 600));
        setResizable(false);
        setSize(new java.awt.Dimension(1040, 600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navigationPanel.setBackground(new java.awt.Color(156, 183, 133));
        navigationPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED)));
        navigationPanel.setLayout(null);

        settingsButton.setBackground(new java.awt.Color(156, 183, 133));

        javax.swing.GroupLayout settingsButtonLayout = new javax.swing.GroupLayout(settingsButton);
        settingsButton.setLayout(settingsButtonLayout);
        settingsButtonLayout.setHorizontalGroup(
            settingsButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        settingsButtonLayout.setVerticalGroup(
            settingsButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        navigationPanel.add(settingsButton);
        settingsButton.setBounds(4, 0, 60, 120);

        addHabitButton.setBackground(new java.awt.Color(156, 183, 133));

        javax.swing.GroupLayout addHabitButtonLayout = new javax.swing.GroupLayout(addHabitButton);
        addHabitButton.setLayout(addHabitButtonLayout);
        addHabitButtonLayout.setHorizontalGroup(
            addHabitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        addHabitButtonLayout.setVerticalGroup(
            addHabitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        navigationPanel.add(addHabitButton);
        addHabitButton.setBounds(4, 120, 60, 120);

        homeButton.setBackground(new java.awt.Color(156, 183, 133));

        javax.swing.GroupLayout homeButtonLayout = new javax.swing.GroupLayout(homeButton);
        homeButton.setLayout(homeButtonLayout);
        homeButtonLayout.setHorizontalGroup(
            homeButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        homeButtonLayout.setVerticalGroup(
            homeButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        navigationPanel.add(homeButton);
        homeButton.setBounds(4, 240, 60, 120);

        editHabitButton.setBackground(new java.awt.Color(156, 183, 133));

        javax.swing.GroupLayout editHabitButtonLayout = new javax.swing.GroupLayout(editHabitButton);
        editHabitButton.setLayout(editHabitButtonLayout);
        editHabitButtonLayout.setHorizontalGroup(
            editHabitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        editHabitButtonLayout.setVerticalGroup(
            editHabitButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        navigationPanel.add(editHabitButton);
        editHabitButton.setBounds(4, 360, 60, 120);

        progressButton.setBackground(new java.awt.Color(156, 183, 133));

        javax.swing.GroupLayout progressButtonLayout = new javax.swing.GroupLayout(progressButton);
        progressButton.setLayout(progressButtonLayout);
        progressButtonLayout.setHorizontalGroup(
            progressButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );
        progressButtonLayout.setVerticalGroup(
            progressButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        navigationPanel.add(progressButton);
        progressButton.setBounds(4, 480, 60, 120);

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
            .addGap(0, 111, Short.MAX_VALUE)
        );

        navigationPanel.add(navSelector);
        navSelector.setBounds(3, 3, 63, 115);

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

        eh_noHabitsPanel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_noHabitsPanel.setText("Add Habits To Unlock This Section");
        eh_noHabitsPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_noHabitsPanel.setOpaque(true);
        editHabit.add(eh_noHabitsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(285, 200, 400, 200));

        eh_editHistoryPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editHistoryAddPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editHistoryAddSaveButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddSaveButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddSaveButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddSaveButton.setText("Save");
        eh_editHistoryAddSaveButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddSaveButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryAddSaveButtonMouseClicked(evt);
            }
        });
        eh_editHistoryAddPanel.add(eh_editHistoryAddSaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 250, 70));

        eh_editHistoryAddCancelButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddCancelButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddCancelButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddCancelButton.setText("Cancel");
        eh_editHistoryAddCancelButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddCancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryAddCancelButtonMouseClicked(evt);
            }
        });
        eh_editHistoryAddPanel.add(eh_editHistoryAddCancelButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 250, 70));

        eh_editHistoryAddYear.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddPanel.add(eh_editHistoryAddYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 110, 40));

        eh_editHistoryAddDay.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddPanel.add(eh_editHistoryAddDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 80, 40));

        eh_editHistoryAddMonth.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddPanel.add(eh_editHistoryAddMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 30, 80, 40));

        eh_editHistoryAddText1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddText1.setForeground(java.awt.Color.white);
        eh_editHistoryAddText1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eh_editHistoryAddText1.setText("Date Of Entry:");
        eh_editHistoryAddPanel.add(eh_editHistoryAddText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 140, 40));

        eh_editHistoryAddYesNoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editHistoryAddText2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddText2.setForeground(java.awt.Color.white);
        eh_editHistoryAddText2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        eh_editHistoryAddText2.setText("Completed:");
        eh_editHistoryAddYesNoPanel.add(eh_editHistoryAddText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 140, 40));

        eh_editHistoryAddCompleteButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddCompleteButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_editHistoryAddCompleteButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddCompleteButton.setText("Completed");
        eh_editHistoryAddCompleteButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddYesNoPanel.add(eh_editHistoryAddCompleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 140, 40));

        eh_editHistoryAddNotCompleteButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddNotCompleteButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        eh_editHistoryAddNotCompleteButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddNotCompleteButton.setText("Not Completed");
        eh_editHistoryAddNotCompleteButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddYesNoPanel.add(eh_editHistoryAddNotCompleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 140, 40));

        eh_editHistoryAddPanel.add(eh_editHistoryAddYesNoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 80, 620, 110));

        eh_editHistoryAddQuantityPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editHistoryAddText3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddText3.setForeground(java.awt.Color.white);
        eh_editHistoryAddText3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryAddText3.setText("Reached:");
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddText3, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 180, 30));

        eh_editHistoryAddText4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddText4.setForeground(java.awt.Color.white);
        eh_editHistoryAddText4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryAddText4.setText("Goal:");
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddText4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, 180, 30));

        eh_editHistoryAddCurrentGoal.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        eh_editHistoryAddCurrentGoal.setForeground(java.awt.Color.white);
        eh_editHistoryAddCurrentGoal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryAddCurrentGoal.setText("Current: <number>");
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddCurrentGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, 180, 30));

        eh_editHistoryAddReached.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddReached.setForeground(java.awt.Color.white);
        eh_editHistoryAddReached.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryAddReached.setText("<999>");
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddReached, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 35, 80, 40));

        eh_editHistoryAddGoal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddGoal.setForeground(java.awt.Color.white);
        eh_editHistoryAddGoal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryAddGoal.setText("<999>");
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 35, 80, 40));

        eh_editHistoryAddGoalIncreaseButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddGoalIncreaseButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryAddGoalIncreaseButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddGoalIncreaseButton.setText("+");
        eh_editHistoryAddGoalIncreaseButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddGoalIncreaseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryAddIncreaseDecreaseButtonClicked(evt);
            }
        });
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddGoalIncreaseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 35, 50, 40));

        eh_editHistoryAddReachedDecreaseButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddReachedDecreaseButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddReachedDecreaseButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddReachedDecreaseButton.setText("-");
        eh_editHistoryAddReachedDecreaseButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddReachedDecreaseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryAddIncreaseDecreaseButtonClicked(evt);
            }
        });
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddReachedDecreaseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 35, 50, 40));

        eh_editHistoryAddGoalDecreaseButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddGoalDecreaseButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddGoalDecreaseButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddGoalDecreaseButton.setText("-");
        eh_editHistoryAddGoalDecreaseButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddGoalDecreaseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryAddIncreaseDecreaseButtonClicked(evt);
            }
        });
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddGoalDecreaseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 35, 50, 40));

        eh_editHistoryAddReachedIncreaseButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddReachedIncreaseButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryAddReachedIncreaseButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddReachedIncreaseButton.setText("+");
        eh_editHistoryAddReachedIncreaseButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddReachedIncreaseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryAddIncreaseDecreaseButtonClicked(evt);
            }
        });
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddReachedIncreaseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 35, 50, 40));

        eh_editHistoryAddIncrement.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        eh_editHistoryAddIncrement.setForeground(java.awt.Color.white);
        eh_editHistoryAddIncrement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryAddIncrement.setText("Increment: <+1.0>");
        eh_editHistoryAddQuantityPanel.add(eh_editHistoryAddIncrement, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 180, 30));

        eh_editHistoryAddPanel.add(eh_editHistoryAddQuantityPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 80, 620, 110));

        eh_editHistoryAddDateEntryLabels.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddDateEntryLabels.setForeground(java.awt.Color.white);
        eh_editHistoryAddDateEntryLabels.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        eh_editHistoryAddDateEntryLabels.setText("    Month               Day                   Year        ");
        eh_editHistoryAddPanel.add(eh_editHistoryAddDateEntryLabels, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 290, 20));

        eh_editHistoryAddDateWarning.setForeground(java.awt.Color.white);
        eh_editHistoryAddDateWarning.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryAddDateWarning.setText("Entry cannot be: For today, for a future day, or an already entered date");
        eh_editHistoryAddPanel.add(eh_editHistoryAddDateWarning, new org.netbeans.lib.awtextra.AbsoluteConstraints(5, 273, 620, 25));

        eh_editHistoryPanel.add(eh_editHistoryAddPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 630, 300));

        ehist_historyScrollPane.setBackground(new java.awt.Color(156, 183, 133));
        ehist_historyScrollPane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ehist_historyScrollPane.setForeground(new java.awt.Color(156, 183, 133));

        eh_historyTable.setBackground(new java.awt.Color(156, 183, 133));
        eh_historyTable.setForeground(java.awt.Color.white);
        eh_historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Date", "Title 2", "Title 3", "Title 4"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        eh_historyTable.setFocusable(false);
        ehist_historyScrollPane.setViewportView(eh_historyTable);
        if (eh_historyTable.getColumnModel().getColumnCount() > 0) {
            eh_historyTable.getColumnModel().getColumn(0).setResizable(false);
            eh_historyTable.getColumnModel().getColumn(1).setResizable(false);
            eh_historyTable.getColumnModel().getColumn(2).setResizable(false);
            eh_historyTable.getColumnModel().getColumn(3).setResizable(false);
        }

        eh_editHistoryPanel.add(ehist_historyScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 630, 350));

        eh_editHistoryDeletePanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryDeletePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryDeletePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editHistoryDeleteConfirmButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryDeleteConfirmButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryDeleteConfirmButton.setForeground(java.awt.Color.white);
        eh_editHistoryDeleteConfirmButton.setText("Confirm Delete");
        eh_editHistoryDeleteConfirmButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryDeleteConfirmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryDeleteConfirmButtonMouseClicked(evt);
            }
        });
        eh_editHistoryDeletePanel.add(eh_editHistoryDeleteConfirmButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 190, 250, 70));

        eh_editHistoryDeleteCanceButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryDeleteCanceButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryDeleteCanceButton.setForeground(java.awt.Color.white);
        eh_editHistoryDeleteCanceButton.setText("Cancel");
        eh_editHistoryDeleteCanceButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryDeleteCanceButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryDeleteCanceButtonMouseClicked(evt);
            }
        });
        eh_editHistoryDeletePanel.add(eh_editHistoryDeleteCanceButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 250, 70));

        eh_editHistoryDeleteDate.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editHistoryDeleteDate.setForeground(java.awt.Color.white);
        eh_editHistoryDeleteDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryDeleteDate.setText("<date>");
        eh_editHistoryDeletePanel.add(eh_editHistoryDeleteDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 630, 70));

        eh_editHistoryDeleteText1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editHistoryDeleteText1.setForeground(java.awt.Color.white);
        eh_editHistoryDeleteText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryDeleteText1.setText("Are you sure you want to delete this entry?");
        eh_editHistoryDeletePanel.add(eh_editHistoryDeleteText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 630, 60));

        eh_editHistoryPanel.add(eh_editHistoryDeletePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 630, 300));

        eh_editHistoryYesNoPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryYesNoPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryYesNoPanel.setForeground(java.awt.Color.white);
        eh_editHistoryYesNoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editHistoryNotCompleteButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryNotCompleteButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryNotCompleteButton.setForeground(java.awt.Color.white);
        eh_editHistoryNotCompleteButton.setText("Not Completed");
        eh_editHistoryNotCompleteButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryNotCompleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ehist_completedNotCompletedButtonClicked(evt);
            }
        });
        eh_editHistoryYesNoPanel.add(eh_editHistoryNotCompleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, 310, 80));

        eh_editHistoryCompleteButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryCompleteButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryCompleteButton.setForeground(java.awt.Color.white);
        eh_editHistoryCompleteButton.setText("Completed");
        eh_editHistoryCompleteButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryCompleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ehist_completedNotCompletedButtonClicked(evt);
            }
        });
        eh_editHistoryYesNoPanel.add(eh_editHistoryCompleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 300, 80));

        eh_editHistoryPanel.add(eh_editHistoryYesNoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, 660, 110));

        eh_editHistoryQuantityPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryQuantityPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryQuantityPanel.setForeground(java.awt.Color.white);
        eh_editHistoryQuantityPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_editHIstoryIncreaseButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHIstoryIncreaseButton.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editHIstoryIncreaseButton.setForeground(java.awt.Color.white);
        eh_editHIstoryIncreaseButton.setText("+");
        eh_editHIstoryIncreaseButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHIstoryIncreaseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ehist_increaseDecreaseButtonClicked(evt);
            }
        });
        eh_editHistoryQuantityPanel.add(eh_editHIstoryIncreaseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, 150, 70));

        eh_editHistorydDecreaseButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistorydDecreaseButton.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editHistorydDecreaseButton.setForeground(java.awt.Color.white);
        eh_editHistorydDecreaseButton.setText("-");
        eh_editHistorydDecreaseButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistorydDecreaseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ehist_increaseDecreaseButtonClicked(evt);
            }
        });
        eh_editHistoryQuantityPanel.add(eh_editHistorydDecreaseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 150, 70));

        eh_editHistoryQuantity.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        eh_editHistoryQuantity.setForeground(java.awt.Color.white);
        eh_editHistoryQuantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_editHistoryQuantity.setText("9999");
        eh_editHistoryQuantityPanel.add(eh_editHistoryQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 40, 220, 60));

        eh_text3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_text3.setForeground(java.awt.Color.white);
        eh_text3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_text3.setText("Quantity Reached");
        eh_editHistoryQuantityPanel.add(eh_text3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 220, -1));

        eh_editHistoryPanel.add(eh_editHistoryQuantityPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 400, 660, 110));

        eh_editHistoryHabitName.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryHabitName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryHabitName.setForeground(java.awt.Color.white);
        eh_editHistoryHabitName.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        eh_editHistoryHabitName.setText("<habit name>");
        eh_editHistoryPanel.add(eh_editHistoryHabitName, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 270, 50));

        eh_editHistoryText1.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryText1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryText1.setForeground(java.awt.Color.white);
        eh_editHistoryText1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        eh_editHistoryText1.setText("                                   Loaded Entries for:");
        eh_editHistoryText1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryText1.setOpaque(true);
        eh_editHistoryPanel.add(eh_editHistoryText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 630, 50));

        eh_editHistoryScrollUpButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryScrollUpButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryScrollUpButton.setForeground(java.awt.Color.white);
        eh_editHistoryScrollUpButton.setText("^");
        eh_editHistoryScrollUpButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryScrollUpButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryScrollButtonClicked(evt);
            }
        });
        eh_editHistoryPanel.add(eh_editHistoryScrollUpButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 0, 30, 200));

        eh_editHistoryScrollDownButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryScrollDownButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryScrollDownButton.setForeground(java.awt.Color.white);
        eh_editHistoryScrollDownButton.setText("V");
        eh_editHistoryScrollDownButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryScrollDownButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryScrollButtonClicked(evt);
            }
        });
        eh_editHistoryPanel.add(eh_editHistoryScrollDownButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 200, 30, 200));

        eh_editHistoryBackButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryBackButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryBackButton.setForeground(java.awt.Color.white);
        eh_editHistoryBackButton.setText("Back");
        eh_editHistoryBackButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryBackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryBackButtonMouseClicked(evt);
            }
        });
        eh_editHistoryPanel.add(eh_editHistoryBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 100, 60));

        eh_editHistoryDeleteButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryDeleteButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryDeleteButton.setForeground(java.awt.Color.white);
        eh_editHistoryDeleteButton.setText("Delete Entry");
        eh_editHistoryDeleteButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryDeleteButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryDeleteButtonMouseClicked(evt);
            }
        });
        eh_editHistoryPanel.add(eh_editHistoryDeleteButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 100, 60));

        eh_editHistoryAddButton.setBackground(new java.awt.Color(156, 183, 133));
        eh_editHistoryAddButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        eh_editHistoryAddButton.setForeground(java.awt.Color.white);
        eh_editHistoryAddButton.setText("Add Entry");
        eh_editHistoryAddButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryAddButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryAddButtonMouseClicked(evt);
            }
        });
        eh_editHistoryPanel.add(eh_editHistoryAddButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 100, 60));

        editHabit.add(eh_editHistoryPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 950, 520));

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
        eh_name.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
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
        eh_goal.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        eh_goal.setForeground(java.awt.Color.white);
        eh_goal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_goal.setText("< target goal >");
        eh_editHabitSummaryPanel.add(eh_goal);
        eh_goal.setBounds(170, 220, 240, 40);

        eh_increment.setBackground(java.awt.Color.black);
        eh_increment.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        eh_increment.setForeground(java.awt.Color.white);
        eh_increment.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eh_increment.setText("< target increment >");
        eh_editHabitSummaryPanel.add(eh_increment);
        eh_increment.setBounds(170, 170, 240, 40);

        eh_days.setBackground(java.awt.Color.black);
        eh_days.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
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

        eh_quantityIncrease.setBackground(new java.awt.Color(128, 161, 98));
        eh_quantityIncrease.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_quantityIncrease.setForeground(java.awt.Color.white);
        eh_quantityIncrease.setText("+");
        eh_quantityIncrease.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_quantityIncrease.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_quantityDecreasedIncreasedButtonClicked(evt);
            }
        });
        eh_editIncrementAndGoalPanel.add(eh_quantityIncrease, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 180, 80, 60));

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

        eh_bottomButtonsPanel.setBackground(new java.awt.Color(156, 183, 133));
        eh_bottomButtonsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        eh_backButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_backButton.setForeground(java.awt.Color.white);
        eh_backButton.setText("Back");
        eh_backButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_backButtonMouseClicked(evt);
            }
        });
        eh_bottomButtonsPanel.add(eh_backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 180, 70));

        eh_deleteHabitButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_deleteHabitButton.setForeground(java.awt.Color.white);
        eh_deleteHabitButton.setText("Delete Habit");
        eh_deleteHabitButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_deleteHabitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_deleteHabitButtonMouseClicked(evt);
            }
        });
        eh_bottomButtonsPanel.add(eh_deleteHabitButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 180, 70));

        eh_editHistoryButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        eh_editHistoryButton.setForeground(java.awt.Color.white);
        eh_editHistoryButton.setText("Edit History");
        eh_editHistoryButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        eh_editHistoryButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eh_editHistoryButtonMouseClicked(evt);
            }
        });
        eh_bottomButtonsPanel.add(eh_editHistoryButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 180, 70));

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
            .addGap(0, 40, Short.MAX_VALUE)
        );

        eh_chooseHabitPanel.add(eh_scrollDownPanel);
        eh_scrollDownPanel.setBounds(40, 430, 850, 40);

        javax.swing.GroupLayout eh_scrollUpPanelLayout = new javax.swing.GroupLayout(eh_scrollUpPanel);
        eh_scrollUpPanel.setLayout(eh_scrollUpPanelLayout);
        eh_scrollUpPanelLayout.setHorizontalGroup(
            eh_scrollUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );
        eh_scrollUpPanelLayout.setVerticalGroup(
            eh_scrollUpPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        eh_chooseHabitPanel.add(eh_scrollUpPanel);
        eh_scrollUpPanel.setBounds(40, 20, 850, 40);

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
        ah_NamePanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createCompoundBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED)), new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED)));
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
        ah_DaysPanel.setBorder(ah_NamePanel.getBorder());
        ah_DaysPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_DaysText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_DaysText.setForeground(java.awt.Color.white);
        ah_DaysText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_DaysText.setText("Choose The Days For This Habit");
        ah_DaysPanel.add(ah_DaysText, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 3, 450, 35));

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
        ah_DaysPanel.add(ah_SaveDaysButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 163, 400, 30));

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
        ah_QuantityPanel.setBorder(ah_NamePanel.getBorder());
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
        ah_QuantityPanel.add(ah_QuantitySaveButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 140, 430, 50));

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
        ah_SummaryPanel.setBorder(ah_NamePanel.getBorder());
        ah_SummaryPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ah_SummaryName.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryName.setForeground(java.awt.Color.white);
        ah_SummaryName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryName.setText("ppppppppppppppppk");
        ah_SummaryPanel.add(ah_SummaryName, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 170, 30));

        ah_SummaryText1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText1.setForeground(java.awt.Color.white);
        ah_SummaryText1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText1.setText("Name:");
        ah_SummaryPanel.add(ah_SummaryText1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 10, 73, 30));

        ah_SummaryText3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText3.setForeground(java.awt.Color.white);
        ah_SummaryText3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText3.setText("Quantity:");
        ah_SummaryPanel.add(ah_SummaryText3, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 75, 73, 30));

        ah_SummaryQuantity.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryQuantity.setForeground(java.awt.Color.white);
        ah_SummaryQuantity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryQuantity.setText("Yes/No");
        ah_SummaryPanel.add(ah_SummaryQuantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 75, 170, 30));

        ah_SummaryDays.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryDays.setForeground(java.awt.Color.white);
        ah_SummaryDays.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryDays.setText("M/T/W/Th/F/S/Su");
        ah_SummaryPanel.add(ah_SummaryDays, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 165, 170, 30));

        ah_SummaryText6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText6.setForeground(java.awt.Color.white);
        ah_SummaryText6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText6.setText("Days:");
        ah_SummaryPanel.add(ah_SummaryText6, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 165, 73, 30));

        ah_SummaryIncrement.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryIncrement.setForeground(java.awt.Color.white);
        ah_SummaryIncrement.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryIncrement.setText("+1");
        ah_SummaryPanel.add(ah_SummaryIncrement, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 105, 170, 30));

        ah_SummaryText4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText4.setForeground(java.awt.Color.white);
        ah_SummaryText4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText4.setText("Increment:");
        ah_SummaryPanel.add(ah_SummaryText4, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 105, 73, 30));

        ah_SummaryText5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText5.setForeground(java.awt.Color.white);
        ah_SummaryText5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText5.setText("Goal:");
        ah_SummaryPanel.add(ah_SummaryText5, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 135, 73, 30));

        ah_SummaryGoal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryGoal.setForeground(java.awt.Color.white);
        ah_SummaryGoal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryGoal.setText("9999");
        ah_SummaryPanel.add(ah_SummaryGoal, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 135, 170, 30));

        ah_SummaryText2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryText2.setForeground(java.awt.Color.white);
        ah_SummaryText2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ah_SummaryText2.setText("Color:");
        ah_SummaryPanel.add(ah_SummaryText2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 45, 73, 30));

        ah_SummaryColor.setBackground(java.awt.Color.white);
        ah_SummaryColor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ah_SummaryColor.setForeground(java.awt.Color.white);
        ah_SummaryColor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ah_SummaryColor.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        ah_SummaryColor.setOpaque(true);
        ah_SummaryPanel.add(ah_SummaryColor, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 45, 170, 30));

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
        s_colorsTitle.setText("Theme");
        s_colorsPanel.add(s_colorsTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 360, -1));

        s_theme1.setBackground(new java.awt.Color(191, 172, 129));
        s_theme1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_theme1.setForeground(new java.awt.Color(191, 172, 129));
        s_theme1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme1MouseClicked(evt);
            }
        });
        s_theme1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setBackground(new java.awt.Color(199, 179, 116));
        jLabel1.setForeground(java.awt.Color.white);
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("AAA");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme1MouseClicked(evt);
            }
        });
        s_theme1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, 135, -1));

        jLabel2.setBackground(new java.awt.Color(216, 216, 216));
        jLabel2.setOpaque(true);
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme1MouseClicked(evt);
            }
        });
        s_theme1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 35, 171, 33));

        s_colorsPanel.add(s_theme1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 175, 70));

        s_theme3.setBackground(new java.awt.Color(221, 178, 93));
        s_theme3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_theme3.setForeground(new java.awt.Color(191, 172, 129));
        s_theme3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme3MouseClicked(evt);
            }
        });
        s_theme3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(193, 144, 69));
        jLabel3.setForeground(java.awt.Color.white);
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("AAA");
        jLabel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel3.setOpaque(true);
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme3MouseClicked(evt);
            }
        });
        s_theme3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, 135, -1));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setOpaque(true);
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme3MouseClicked(evt);
            }
        });
        s_theme3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 35, 171, 33));

        s_colorsPanel.add(s_theme3, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 50, 175, 70));

        s_theme2.setBackground(new java.awt.Color(105, 134, 191));
        s_theme2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_theme2.setForeground(new java.awt.Color(191, 172, 129));
        s_theme2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme2MouseClicked(evt);
            }
        });
        s_theme2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setBackground(new java.awt.Color(117, 147, 169));
        jLabel5.setForeground(java.awt.Color.white);
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("AAA");
        jLabel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel5.setOpaque(true);
        jLabel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme2MouseClicked(evt);
            }
        });
        s_theme2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, 135, -1));

        jLabel6.setBackground(new java.awt.Color(209, 230, 246));
        jLabel6.setOpaque(true);
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme2MouseClicked(evt);
            }
        });
        s_theme2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 35, 171, 33));

        s_colorsPanel.add(s_theme2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 175, 70));

        s_theme4.setBackground(new java.awt.Color(175, 125, 93));
        s_theme4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        s_theme4.setForeground(new java.awt.Color(191, 172, 129));
        s_theme4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme4MouseClicked(evt);
            }
        });
        s_theme4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setBackground(new java.awt.Color(202, 171, 125));
        jLabel7.setForeground(java.awt.Color.white);
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("AAA");
        jLabel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel7.setOpaque(true);
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme4MouseClicked(evt);
            }
        });
        s_theme4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 24, 135, -1));

        jLabel8.setBackground(new java.awt.Color(201, 208, 188));
        jLabel8.setOpaque(true);
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                s_theme4MouseClicked(evt);
            }
        });
        s_theme4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 35, 171, 33));

        s_colorsPanel.add(s_theme4, new org.netbeans.lib.awtextra.AbsoluteConstraints(195, 130, 175, 70));

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
        s_awayFromScreenOption4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/todaysProgressScreensaver.PNG"))); // NOI18N
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
        s_awayFromScreenOption5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/overallProgressScreensaver.png"))); // NOI18N
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
        s_awayFromScreenButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
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
            .addGap(0, 214, Short.MAX_VALUE)
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

        progress.setBackground(new java.awt.Color(202, 202, 202));
        progress.setMaximumSize(new java.awt.Dimension(1040, 600));
        progress.setMinimumSize(new java.awt.Dimension(1040, 600));
        progress.setLayout(null);

        p_progressTitle.setBackground(new java.awt.Color(156, 183, 133));
        p_progressTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        p_progressTitle.setForeground(java.awt.Color.white);
        p_progressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_progressTitle.setText("Progress Center");
        p_progressTitle.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        p_progressTitle.setOpaque(true);
        progress.add(p_progressTitle);
        p_progressTitle.setBounds(10, 10, 950, 50);

        p_showAllHabitsText.setBackground(new java.awt.Color(156, 183, 133));
        p_showAllHabitsText.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        p_showAllHabitsText.setForeground(java.awt.Color.white);
        p_showAllHabitsText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_showAllHabitsText.setText("Showing All Habits");
        p_showAllHabitsText.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_showAllHabitsText.setOpaque(true);
        progress.add(p_showAllHabitsText);
        p_showAllHabitsText.setBounds(225, 70, 520, 50);

        p_tableScrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        p_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        p_tableScrollPane.setViewportView(p_table);

        progress.add(p_tableScrollPane);
        p_tableScrollPane.setBounds(85, 170, 800, 410);

        p_tableRightScrollButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p_tableScrollButtonClicked(evt);
            }
        });

        javax.swing.GroupLayout p_tableRightScrollButtonLayout = new javax.swing.GroupLayout(p_tableRightScrollButton);
        p_tableRightScrollButton.setLayout(p_tableRightScrollButtonLayout);
        p_tableRightScrollButtonLayout.setHorizontalGroup(
            p_tableRightScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        p_tableRightScrollButtonLayout.setVerticalGroup(
            p_tableRightScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        progress.add(p_tableRightScrollButton);
        p_tableRightScrollButton.setBounds(890, 318, 75, 75);

        javax.swing.GroupLayout p_habitRightScrollButtonLayout = new javax.swing.GroupLayout(p_habitRightScrollButton);
        p_habitRightScrollButton.setLayout(p_habitRightScrollButtonLayout);
        p_habitRightScrollButtonLayout.setHorizontalGroup(
            p_habitRightScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        p_habitRightScrollButtonLayout.setVerticalGroup(
            p_habitRightScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        progress.add(p_habitRightScrollButton);
        p_habitRightScrollButton.setBounds(660, 70, 50, 50);

        p_habitName.setBackground(new java.awt.Color(156, 183, 133));
        p_habitName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        p_habitName.setForeground(java.awt.Color.white);
        p_habitName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_habitName.setText("<habit name>");
        p_habitName.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_habitName.setOpaque(true);
        progress.add(p_habitName);
        p_habitName.setBounds(320, 75, 330, 40);

        p_tableLeftScrollButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p_tableScrollButtonClicked(evt);
            }
        });

        javax.swing.GroupLayout p_tableLeftScrollButtonLayout = new javax.swing.GroupLayout(p_tableLeftScrollButton);
        p_tableLeftScrollButton.setLayout(p_tableLeftScrollButtonLayout);
        p_tableLeftScrollButtonLayout.setHorizontalGroup(
            p_tableLeftScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        p_tableLeftScrollButtonLayout.setVerticalGroup(
            p_tableLeftScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );

        progress.add(p_tableLeftScrollButton);
        p_tableLeftScrollButton.setBounds(5, 318, 75, 75);

        p_habitLeftScrollButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p_habitScrollButtonClicked(evt);
            }
        });

        javax.swing.GroupLayout p_habitLeftScrollButtonLayout = new javax.swing.GroupLayout(p_habitLeftScrollButton);
        p_habitLeftScrollButton.setLayout(p_habitLeftScrollButtonLayout);
        p_habitLeftScrollButtonLayout.setHorizontalGroup(
            p_habitLeftScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        p_habitLeftScrollButtonLayout.setVerticalGroup(
            p_habitLeftScrollButtonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        progress.add(p_habitLeftScrollButton);
        p_habitLeftScrollButton.setBounds(260, 70, 50, 50);

        p_noHabitsPanel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_noHabitsPanel.setText("Add Habits To Unlock This Section");
        p_noHabitsPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_noHabitsPanel.setOpaque(true);
        progress.add(p_noHabitsPanel);
        p_noHabitsPanel.setBounds(285, 200, 400, 200);

        p_monthAndYear.setBackground(new java.awt.Color(156, 183, 133));
        p_monthAndYear.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        p_monthAndYear.setForeground(java.awt.Color.white);
        p_monthAndYear.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p_monthAndYear.setText("<MONTH AND YEAR>");
        p_monthAndYear.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        p_monthAndYear.setOpaque(true);
        progress.add(p_monthAndYear);
        p_monthAndYear.setBounds(85, 132, 800, 40);

        p_resetMonthButton.setBackground(new java.awt.Color(156, 183, 133));
        p_resetMonthButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        p_resetMonthButton.setForeground(java.awt.Color.white);
        p_resetMonthButton.setText("This Month");
        p_resetMonthButton.setToolTipText("");
        p_resetMonthButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_resetMonthButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p_resetMonthButtonMouseClicked(evt);
            }
        });
        progress.add(p_resetMonthButton);
        p_resetMonthButton.setBounds(760, 75, 130, 40);

        p_showAllHabitsButton.setBackground(new java.awt.Color(156, 183, 133));
        p_showAllHabitsButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        p_showAllHabitsButton.setForeground(java.awt.Color.white);
        p_showAllHabitsButton.setText("Show By Habit");
        p_showAllHabitsButton.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        p_showAllHabitsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p_showAllHabitsButtonMouseClicked(evt);
            }
        });
        progress.add(p_showAllHabitsButton);
        p_showAllHabitsButton.setBounds(80, 75, 130, 40);

        getContentPane().add(progress, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

        screensaverPanel.setBackground(java.awt.Color.black);
        screensaverPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                screensaverPanelMouseClicked(evt);
            }
        });
        screensaverPanel.setLayout(null);

        screensaverOverallProgress.setBackground(new java.awt.Color(156, 183, 133));
        screensaverOverallProgress.setLayout(null);

        javax.swing.GroupLayout overallProgressStreakBarGraphLayout = new javax.swing.GroupLayout(overallProgressStreakBarGraph);
        overallProgressStreakBarGraph.setLayout(overallProgressStreakBarGraphLayout);
        overallProgressStreakBarGraphLayout.setHorizontalGroup(
            overallProgressStreakBarGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );
        overallProgressStreakBarGraphLayout.setVerticalGroup(
            overallProgressStreakBarGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 220, Short.MAX_VALUE)
        );

        screensaverOverallProgress.add(overallProgressStreakBarGraph);
        overallProgressStreakBarGraph.setBounds(30, 350, 490, 220);

        javax.swing.GroupLayout overallProgressYearBarGraphLayout = new javax.swing.GroupLayout(overallProgressYearBarGraph);
        overallProgressYearBarGraph.setLayout(overallProgressYearBarGraphLayout);
        overallProgressYearBarGraphLayout.setHorizontalGroup(
            overallProgressYearBarGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 916, Short.MAX_VALUE)
        );
        overallProgressYearBarGraphLayout.setVerticalGroup(
            overallProgressYearBarGraphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 255, Short.MAX_VALUE)
        );

        screensaverOverallProgress.add(overallProgressYearBarGraph);
        overallProgressYearBarGraph.setBounds(62, 30, 916, 255);

        javax.swing.GroupLayout overallProgressWeekCircleLayout = new javax.swing.GroupLayout(overallProgressWeekCircle);
        overallProgressWeekCircle.setLayout(overallProgressWeekCircleLayout);
        overallProgressWeekCircleLayout.setHorizontalGroup(
            overallProgressWeekCircleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        overallProgressWeekCircleLayout.setVerticalGroup(
            overallProgressWeekCircleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        screensaverOverallProgress.add(overallProgressWeekCircle);
        overallProgressWeekCircle.setBounds(695, 385, 150, 150);

        javax.swing.GroupLayout overallProgressDayCircleLayout = new javax.swing.GroupLayout(overallProgressDayCircle);
        overallProgressDayCircle.setLayout(overallProgressDayCircleLayout);
        overallProgressDayCircleLayout.setHorizontalGroup(
            overallProgressDayCircleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        overallProgressDayCircleLayout.setVerticalGroup(
            overallProgressDayCircleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        screensaverOverallProgress.add(overallProgressDayCircle);
        overallProgressDayCircle.setBounds(540, 385, 150, 150);

        javax.swing.GroupLayout overallProgressMonthCircleLayout = new javax.swing.GroupLayout(overallProgressMonthCircle);
        overallProgressMonthCircle.setLayout(overallProgressMonthCircleLayout);
        overallProgressMonthCircleLayout.setHorizontalGroup(
            overallProgressMonthCircleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );
        overallProgressMonthCircleLayout.setVerticalGroup(
            overallProgressMonthCircleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
        );

        screensaverOverallProgress.add(overallProgressMonthCircle);
        overallProgressMonthCircle.setBounds(850, 385, 150, 150);

        overallProgressNoHabitsText.setBackground(new java.awt.Color(156, 183, 133));
        overallProgressNoHabitsText.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        overallProgressNoHabitsText.setForeground(java.awt.Color.white);
        overallProgressNoHabitsText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overallProgressNoHabitsText.setText("Add Habits To Unlock Statistics");
        screensaverOverallProgress.add(overallProgressNoHabitsText);
        overallProgressNoHabitsText.setBounds(320, 200, 400, 200);

        overallProgressNoStreaksText.setBackground(new java.awt.Color(156, 183, 133));
        overallProgressNoStreaksText.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        overallProgressNoStreaksText.setForeground(java.awt.Color.white);
        overallProgressNoStreaksText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overallProgressNoStreaksText.setText("There Are No Streaks Set Yet");
        overallProgressNoStreaksText.setToolTipText("");
        overallProgressNoStreaksText.setOpaque(true);
        screensaverOverallProgress.add(overallProgressNoStreaksText);
        overallProgressNoStreaksText.setBounds(75, 345, 400, 200);

        overallProgressText1.setBackground(new java.awt.Color(156, 183, 133));
        overallProgressText1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        overallProgressText1.setForeground(java.awt.Color.white);
        overallProgressText1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overallProgressText1.setText("This Month's Streaks");
        screensaverOverallProgress.add(overallProgressText1);
        overallProgressText1.setBounds(30, 320, 490, 30);

        overallProgressText2.setBackground(new java.awt.Color(156, 183, 133));
        overallProgressText2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        overallProgressText2.setForeground(java.awt.Color.white);
        overallProgressText2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        overallProgressText2.setText("Completion Tracker");
        screensaverOverallProgress.add(overallProgressText2);
        overallProgressText2.setBounds(540, 320, 460, 30);

        overallProgressText3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        overallProgressText3.setForeground(java.awt.Color.white);
        overallProgressText3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        overallProgressText3.setText("100%");
        screensaverOverallProgress.add(overallProgressText3);
        overallProgressText3.setBounds(30, 65, 32, 20);

        overallProgressText4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        overallProgressText4.setForeground(java.awt.Color.white);
        overallProgressText4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        overallProgressText4.setText("0%");
        screensaverOverallProgress.add(overallProgressText4);
        overallProgressText4.setBounds(30, 235, 32, 20);

        screensaverPanel.add(screensaverOverallProgress);
        screensaverOverallProgress.setBounds(0, 0, 1040, 600);

        screensaverTodaysProgress.setBackground(new java.awt.Color(255, 204, 51));
        screensaverTodaysProgress.setLayout(null);

        screensaverTodaysProgressTitle.setBackground(new java.awt.Color(255, 204, 51));
        screensaverTodaysProgressTitle.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        screensaverTodaysProgressTitle.setForeground(new java.awt.Color(255, 255, 255));
        screensaverTodaysProgressTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        screensaverTodaysProgressTitle.setText("Left To Do Today");
        screensaverTodaysProgressTitle.setOpaque(true);
        screensaverTodaysProgress.add(screensaverTodaysProgressTitle);
        screensaverTodaysProgressTitle.setBounds(10, 10, 820, 40);

        screensaverTodaysProgressDisplay.setBackground(new java.awt.Color(255, 204, 51));
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 1, 1);
        flowLayout2.setAlignOnBaseline(true);
        screensaverTodaysProgressDisplay.setLayout(flowLayout2);
        screensaverTodaysProgress.add(screensaverTodaysProgressDisplay);
        screensaverTodaysProgressDisplay.setBounds(10, 60, 820, 330);

        screensaverPanel.add(screensaverTodaysProgress);
        screensaverTodaysProgress.setBounds(120, 100, 840, 400);

        screensaverFishTankBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/habitpanel/screensaverFishBackground.png"))); // NOI18N
        screensaverFishTankBackground.setOpaque(true);
        screensaverPanel.add(screensaverFishTankBackground);
        screensaverFishTankBackground.setBounds(0, 0, 1040, 600);

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
        h_scrollUpPanel.setBounds(135, 70, 700, 40);

        h_scrollDownPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                scrollButtonClicked(evt);
            }
        });

        javax.swing.GroupLayout h_scrollDownPanelLayout = new javax.swing.GroupLayout(h_scrollDownPanel);
        h_scrollDownPanel.setLayout(h_scrollDownPanelLayout);
        h_scrollDownPanelLayout.setHorizontalGroup(
            h_scrollDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 700, Short.MAX_VALUE)
        );
        h_scrollDownPanelLayout.setVerticalGroup(
            h_scrollDownPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        home.add(h_scrollDownPanel);
        h_scrollDownPanel.setBounds(135, 546, 700, 40);

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

        h_savingFilesText.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        h_savingFilesText.setForeground(java.awt.Color.white);
        h_savingFilesText.setText("Saving Files...");
        home.add(h_savingFilesText);
        h_savingFilesText.setBounds(840, 20, 120, 30);

        h_date.setBackground(new java.awt.Color(193, 144, 69));
        h_date.setFont(new java.awt.Font("Segoe UI", 1, 22)); // NOI18N
        h_date.setForeground(new java.awt.Color(255, 255, 255));
        h_date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_date.setText("Saturday, Nov 22, 2025");
        h_date.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));
        h_date.setOpaque(true);
        home.add(h_date);
        h_date.setBounds(10, 10, 950, 50);

        h_noHabitsPanel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h_noHabitsPanel.setText("Add Habits To Unlock This Section");
        h_noHabitsPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        h_noHabitsPanel.setOpaque(true);
        home.add(h_noHabitsPanel);
        h_noHabitsPanel.setBounds(285, 200, 400, 200);

        getContentPane().add(home, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 0, 970, 600));

        keyboard.setBackground(new java.awt.Color(156, 183, 133));
        keyboard.setBorder(ah_NamePanel.getBorder());
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
        keyboard.add(key1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 15, 60, 60));

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
        keyboard.add(key2, new org.netbeans.lib.awtextra.AbsoluteConstraints(85, 15, 60, 60));

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
        keyboard.add(key3, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 15, 60, 60));

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
        keyboard.add(key4, new org.netbeans.lib.awtextra.AbsoluteConstraints(225, 15, 60, 60));

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
        keyboard.add(key5, new org.netbeans.lib.awtextra.AbsoluteConstraints(295, 15, 60, 60));

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
        keyboard.add(key6, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 15, 60, 60));

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
        keyboard.add(key7, new org.netbeans.lib.awtextra.AbsoluteConstraints(435, 15, 60, 60));

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
        keyboard.add(key8, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 15, 60, 60));

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
        keyboard.add(key9, new org.netbeans.lib.awtextra.AbsoluteConstraints(575, 15, 60, 60));

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
        keyboard.add(key10, new org.netbeans.lib.awtextra.AbsoluteConstraints(645, 15, 60, 60));

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
        keyboard.add(key11, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 85, 60, 60));

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
        keyboard.add(key12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 85, 60, 60));

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
        keyboard.add(key13, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 85, 60, 60));

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
        keyboard.add(key14, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 85, 60, 60));

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
        keyboard.add(key15, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 85, 60, 60));

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
        keyboard.add(key16, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 85, 60, 60));

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
        keyboard.add(key17, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 85, 60, 60));

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
        keyboard.add(key18, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 85, 60, 60));

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
        keyboard.add(key19, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 85, 60, 60));

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
        keyboard.add(key20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 155, 60, 60));

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
        keyboard.add(key21, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 155, 60, 60));

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
        keyboard.add(key22, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 155, 60, 60));

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
        keyboard.add(key23, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 155, 60, 60));

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
        keyboard.add(key24, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 155, 60, 60));

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
        keyboard.add(key25, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 155, 60, 60));

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
        keyboard.add(key26, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 155, 60, 60));

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
        keyboard.add(key27, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 155, 100, 60));

        key28.setBackground(new java.awt.Color(128, 161, 98));
        key28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        key28.setForeground(java.awt.Color.white);
        key28.setText("SPACE");
        key28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        key28.setFocusPainted(false);
        key28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                keyboardKeyClicked(evt);
            }
        });
        keyboard.add(key28, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 155, 95, 60));

        getContentPane().add(keyboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, -330, 725, 230));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [SWITCH FRAMES FUNCTION ] ===============================================================================
    // =================================================================================================================================
    // Gets called everytime we need to switch frames:
    //  -> Hides all tabs that are able to be shown
    //  -> Handles timing and other functions that are done when switching from specific frames
    //  -> Calls specific-to-frame functions to set up those functions
    //  -> Shows the frame that was targeted
    private void switchFrame(JPanel target){
        // Hidding all other frames (and keyboard)
        keyboard.setVisible(false);
        screensaverPanel.setVisible(false);
        home.setVisible(false);
        settings.setVisible(false);
        progress.setVisible(false);
        addHabit.setVisible(false);
        editHabit.setVisible(false);
        navigationPanel.setVisible(true);
        
        
        
        // Stoping timer to reset since we are moving screens
        awayFromScreenCounter = 0;     // Reseting counter
        if(awayFromScreen.isRunning()) // Stopping timer if its running
            awayFromScreen.stop();
        
        // Frames specific instructions for setting up
        if(target == home){
            refreshHomeData();                   // Reloads data of habit cards using the two arrays we have of them
            
            h_scrollPane.getVerticalScrollBar().setValue(0); // Moving the scroll value to zero
            scrollButtonClicked(null);                       // Simulating that the scroll up was clicked so it gets grayed out
            if(h_habitPanel.getComponentCount() > 8){
                h_scrollUpPanel.setVisible(true);   // Show the scroll button again IF we need it 
                h_scrollDownPanel.setVisible(true); // Show the scroll button again IF we need it 
                h_scrollDownPanel.setBackground(PRIMARY_COLOR);
            }
            else{
                h_scrollUpPanel.setVisible(false);   // Hide the scroll button when we dont need it 
                h_scrollDownPanel.setVisible(false); // Hide the scroll button when we dont need it  
            }
            
            if(awayIsOn)                         // Only turn on timer if this is on
                awayFromScreen.start();          // Starting the timer again if we went to home screen
            
            // Check if we need to change the colors
            
            h_savingFilesText.setVisible(false);
        }
        else if(target == settings){
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
            setUpProgressScreen();
        }
        else if(target == addHabit){
            ah_ResetButtonMouseClicked(null); // Reset the addhabit panel
        }
        else if(target == editHabit){
            eh_resetPanel();
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
            switchFrame(screenSaver_again);   // Send to screensaver
            // ---------------------------------------------------------------->  Make brightness lower [ADD LATER]
        }
    });
    
    // "Public" reset-away-from-screen time function that can be called from inside the habitcard classes
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
    
    int saveHabitsCounter = 6;
    // Function for time update
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
        
        
        // Checking if the date has changed, if so lock in data for yesterday and refresh home screen
        LocalDate newTodaysDate = now.toLocalDate();
        if(!newTodaysDate.equals(todaysDate)){
            saveAllHabits();   // Saving one more time all data
            refreshHomeData(); // Refreshing home screen
            todaysDate = newTodaysDate; // Pushing foward to actual today
        }
        
        // Checking if we are ready to save the habits to the disk (every 60 seconds) (no reason to update while screensaver is active)
        if(!screensaverPanel.isVisible()){
                saveHabitsCounter--;
            if(saveHabitsCounter <= 0){
                saveAllHabits();
                saveHabitsCounter = 6;
            }   
        }
    }
    
    
  
    
    // =================================================================================================================================
    // ================== [ LOADING FUNCTIONS ] ========================================================================================
    // =================================================================================================================================
    
    // HELPER FUNCTION : Checks if it is a number
    private boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // LOADS VARIABLES FROM FILES: (helper function isNumber makes sure what is sent in is a number duh)
    private void loadVariables(){
        Path filePath = Path.of(programFolderName, configFileName); 
        
        // Make path if it does not exist yet
        try{
            Files.createDirectories(filePath.getParent());
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(this, "Could not make program files folder", "ERROR", JOptionPane.ERROR);
            return;
        }
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toFile()))) {
            String line;
            int lineCount = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trimming lines of extra spaces
                if (line.isEmpty()) // Skipping empty lines
                    continue; 
                
                lineCount++;
                String[] parts = line.split(","); // Splitting into parts 

                // Determine what we expect based on the first part
                switch (parts[0]) {
                    case "away_time", "away_screen" -> {
                        if (parts.length != 2) {                  // Make new file when there is no second part
                            throw new IllegalArgumentException(); 
                        }
                        
                        if (!isNumber(parts[1])) {                // Make new file when second part is not a number
                            throw new IllegalArgumentException(); 
                        }
                        
                        // If we are reading the away_screen line, then set screensaver
                        if(parts[0].equals("away_screen")){
                            if(Integer.parseInt(parts[1]) > 6 || Integer.parseInt(parts[1]) < 1)    // Make new file when screensaver is not valid (1-6)
                                throw new IllegalArgumentException();
                            screensaver.setScreensaver(Integer.parseInt(parts[1]));   
                        }
                        // If we are reading the away_time, then set AWAY_FROM_SCREEN_TIME
                        else
                            AWAY_FROM_SCREEN_TIME = Integer.parseInt(parts[1]);
                    }
                    case "primary", "secondary", "button", "text" -> {
                        if (parts.length != 4) {                                                     // Make new file when there are not 4 parts
                            throw new IllegalArgumentException();
                        }
                        
                        for (int i = 1; i <= 3; i++) {                                               // Check that all 3 values are numbers
                            if (!isNumber(parts[i])) {
                                throw new IllegalArgumentException();
                            }
                            if(Integer.parseInt(parts[i]) > 255 || Integer.parseInt(parts[i]) < 0)
                                throw new IllegalArgumentException();
                        }
                        
                        // Assinging the colors!! FINALLY!
                        switch (parts[0]) {
                            case "primary" -> PRIMARY_COLOR = new Color(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
                            case "secondary" -> SECONDARY_COLOR = new Color(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
                            case "button" -> BUTTON_COLOR = new Color(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
                            case "text" -> TEXT_COLOR = new Color(Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
                        }
                    }

                    default -> throw new IllegalArgumentException(); // If the first part is none of the ones we were looking for, then make new file
                }
            }
            
            // Checking that we have read 6 lines, if not then we remake file
            if(lineCount != 6)
                throw new IllegalArgumentException();
        } 
        
        
        // REMOVE AND REMAKE FILE CATCH (also gets thrown here when the file is not found)
        catch (IOException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Config.txt file was corrupted. Creating new file.", "Corrupted File.", JOptionPane.ERROR_MESSAGE);
            // -- Remove File --
            try {
                // Delete file if it exists (jsut in case idk why)
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }

                // Create new file and STATIC LINES (honestly doesnt matter, user will customize)
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                    writer.write("away_time,60");
                    writer.newLine();
                    writer.write("away_screen,1");
                    writer.newLine();
                    writer.write("primary,221,178,93");
                    writer.newLine();
                    writer.write("secondary,204,204,204");
                    writer.newLine();
                    writer.write("button,193,144,69");
                    writer.newLine();
                    writer.write("text,255,255,255");
                }
            } catch (IOException ex) { ex.printStackTrace(); } // Shouldn't ever since we are recyling the function 
            
            loadVariables(); // Rerun the function so that we can use newly made files
        }
    }
    
    // LOADS HABITS FROM FILES
    private void loadHabits(){
        // Make sure that the folder exits!!
        File folder = new File(programFolderName + "/" + habitsFolderName);
        
        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                JOptionPane.showMessageDialog(this, "Failed to create folder: " + folder.getAbsolutePath(), "ERROR", JOptionPane.ERROR_MESSAGE);
                return; 
            }
        }

        File[] habitFiles = folder.listFiles((dir, name) -> name.endsWith(".txt")); // only .txt files

        if (habitFiles == null) return;
        

        for (File file : habitFiles) {
            
            // Making holding variables
            String habitName = "";
            Color habitColor = Color.GRAY;
            String habitDays = "0000000";
            String habitType = "";

            double increment = 0;
            double goal = 0;
            HabitCard_Quantity loadingQuantityCard = null;
            HabitCard_YesNo loadingYesNoCard = null;
            LocalDateTime lastModifiedTemp = LocalDateTime.now();
            
            
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean readingDates = false;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    
                    // If the line is empty then we are going to be moving on to read the date entries, so first we have to make the actual objects
                    if (line.isEmpty() && !readingDates) {
                        if(habitType.equalsIgnoreCase("quantity")){
                            loadingQuantityCard = new HabitCard_Quantity(this, habitName, habitColor, 0, goal, increment, habitDays);
                            allQuantityCards.add(loadingQuantityCard); 
                        }   
                        else if(habitType.equalsIgnoreCase("yesno")){
                            loadingYesNoCard = new HabitCard_YesNo(this, habitName, habitColor, false, habitDays);
                            allYesNoCards.add(loadingYesNoCard); 
                        }
                        
                        // Triggering flag so that next iter we can start loading in the date entries
                        readingDates = true; // blank line separates metadata from entr
                        continue;
                    }
                    

                    // READING BASIC INFORMATION FIRST
                    if (!readingDates) {
                        String[] parts = line.split("=", 2);
                        if (parts.length != 2) 
                            continue;

                        String key = parts[0].trim();
                        String value = parts[1].trim();

                        switch (key) {
                            case "name" -> habitName = value;
                            
                            case "color" -> {
                                try{
                                    String[] colorParts = value.split(",",3);
                                    habitColor = new Color(
                                            Integer.parseInt(colorParts[0]), 
                                            Integer.parseInt(colorParts[1]), 
                                            Integer.parseInt(colorParts[2])
                                    );
                                }
                                catch(NumberFormatException e){
                                    habitColor = Color.GRAY;
                                }
                                
                            }
                            
                            case "type" -> habitType = value;
                            
                            case "days" -> {
                                if (value.matches("[01]{7}")) 
                                    habitDays = value;
                                else
                                    habitDays = "0000000";
                            }
                            
                            case "increment" -> {
                                try { increment = Double.parseDouble(value); } 
                                catch (NumberFormatException e) { increment = 1; }
                            }
                            
                            case "goal" -> {
                                try { goal = Double.parseDouble(value); } 
                                catch (NumberFormatException e) { goal = 1; }
                            }
                            
                            case "lastmodified" -> {
                                try {
                                    LocalDateTime parsed = LocalDateTime.parse(value);
                                    if (habitType.equalsIgnoreCase("quantity") && loadingQuantityCard != null) {
                                        loadingQuantityCard.setLastModified(parsed);
                                    } else if (habitType.equalsIgnoreCase("yesno") && loadingYesNoCard != null) {
                                        loadingYesNoCard.setLastModified(parsed);
                                    } else {
                                        // Store temporarily to set after object creation
                                        lastModifiedTemp = parsed;
                                    }
                                } catch (DateTimeParseException e) {
                                    // Keep default (now)
                                }
                            }
                        }

                    } else {
                        // Store the date entries:
                        
                        // If we are loading a quantity card, add the entries here
                        if(loadingQuantityCard != null){
                            String[] parts = line.split("=", 2);
                            if (parts.length != 2) continue;
                            
                            String dateStr = parts[0].trim();
                            String[] values = parts[1].split(",", 2);
                            if(values.length != 2) continue;

                            try {
                                LocalDate date = LocalDate.parse(dateStr);
                                double quantityReached = Double.parseDouble(values[0].trim());
                                double goalTarget = Double.parseDouble(values[1].trim());

                                loadingQuantityCard.addDateEntry(date,quantityReached, goalTarget);
                            } 
                            catch(Exception e) { 
                                JOptionPane.showMessageDialog(this, "Error date entry: " + line, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        
                        // If we are loading a yesno card, add the entires here
                        else if(loadingYesNoCard != null){ 
                            String[] parts = line.split("=", 2);
                            if (parts.length != 2) continue; // Just in case

                            try{
                                LocalDate date = LocalDate.parse(parts[0]);
                                boolean completed = Boolean.parseBoolean(parts[1]);
                                loadingYesNoCard.addDateEntry(date, completed);
                            }
                            catch(Exception e) { 
                                JOptionPane.showMessageDialog(this, "Error date entry: " + line, "ERROR", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                    
                }
                
                // In case there were not dates (no empty lines, which there should be but just in case) we need to still make the card
                if(!readingDates){
                    if (habitType.equalsIgnoreCase("quantity")) {
                        loadingQuantityCard = new HabitCard_Quantity( this, habitName, habitColor, 0, goal, increment, habitDays);
                        loadingQuantityCard.setLastModified(lastModifiedTemp);
                        allQuantityCards.add(loadingQuantityCard);
                    } else if(habitType.equalsIgnoreCase("yesno")){
                        loadingYesNoCard = new HabitCard_YesNo(this, habitName, habitColor, false, habitDays);
                        loadingYesNoCard.setLastModified(lastModifiedTemp);
                        allYesNoCards.add(loadingYesNoCard);
                    }
                }
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + file.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // PAINTS ALL THE PANELS
    private void paintColors(){
        
        // COLLECTING ALL PANELS, BUTTONS, AND LABELS
        JPanel primaryColored[] = {
            // SETTINGS SCREEN:
            s_colorsPanel, s_customScreensaverPanel, s_awayFromScreenPanel, s_connectionPanel,
            // PROGRESS SCREEN:
            
            // KEYBOARD:
            keyboard,
            
            // ADD HABIT:
            ah_NamePanel, ah_SummaryPanel, ah_DaysPanel, ah_QuantityPanel, 
            
            // EDIT HABIT:
            eh_editHabitSummaryPanel, eh_editNamePanel,
            eh_editDaysPanel, eh_editIncrementAndGoalPanel, eh_deletePanel, eh_editHistoryAddPanel, eh_editHistoryDeletePanel,
            eh_editHistoryAddYesNoPanel, eh_editHistoryAddQuantityPanel, eh_editHistoryAddYesNoPanel, eh_editHistoryAddQuantityPanel, 
            
            // PROGRESS PANEL:
            p_tableLeftScrollButton, p_tableRightScrollButton, p_habitLeftScrollButton, p_habitRightScrollButton,
        };
        JPanel secondaryColored[] = {
            // HOME SCREEN:
            home, h_habitPanel,
            // SETTINGS SCREEN:
            settings, 
            
            // PROGRESS SCREEN:
            progress,
            
            // ADD HABIT 
            addHabit, 
            
            // EDIT HABIT
            eh_chooseHabitPanel, editHabit, eh_chooseHabitDisplay, eh_editHabitPanel, eh_bottomButtonsPanel, eh_editHistoryPanel
        };
        JButton buttonColored[] = {
            // HOME SCREEN:
            
            // SETTINGS SCREEN:
            s_awayFromScreenButton, 
            s_turnOffAwayFromScreenButton,
            
            // PROGRESS SCREEN:
            
            // KEYBOARD:
            key1,key2,key3,key4,key5,key6,key7,key8,key9,key10,
            key11,key12,key13,key14,key15,key16,key17,key18,key19,key20,
            key21,key22,key23,key24,key25,key26,key27, key28,
            
            // ADD HABIT
            ah_ResetButton, ah_ChooseColorButton, ah_QuantityDecrease, ah_QuantityIncrease, 
            ah_QuantitySaveButton, ah_SaveDaysButton, eh_editHistorydDecreaseButton, eh_editHIstoryIncreaseButton, 
            
            // EDIT HABIT
            eh_editNameButton, eh_editColorButton, eh_editIncrementAndGoalButton,
            eh_editDaysButton, eh_editNameCancelButton, eh_editNameSaveButton, eh_deleteHabitButton, eh_backButton,
            eh_editDaysCancelButton, eh_editDaysSaveButton, eh_quantityDecrease, eh_quantityIncrease, eh_editIncrementAndGoalCancelButton, eh_editIncrementAndGoalSaveButton,
            eh_deleteConfirmButton, eh_deleteCancelButton, eh_editHistoryButton, eh_editHistoryBackButton, eh_editHistoryScrollDownButton, eh_editHistoryScrollUpButton, 
            eh_editHistoryDeleteConfirmButton, eh_editHistoryDeleteCanceButton, eh_editHistoryAddSaveButton, eh_editHistoryAddCancelButton, 
            eh_editHistoryAddButton, eh_editHistoryDeleteButton, eh_editHistoryAddReachedDecreaseButton, eh_editHistoryAddReachedIncreaseButton,
            eh_editHistoryAddGoalDecreaseButton, eh_editHistoryAddGoalIncreaseButton,

            // PROGRESS PANEL
            p_resetMonthButton, p_showAllHabitsButton, 
        };
        
        JLabel textColored[] = {
            // HOME SCREEN:
            h_noHabitsPanel,
            
            // SETTINGS SCREEN:
            s_title, s_colorsTitle, s_awayFromScreenTitle, 
            s_awayFromScreenTitle2, s_awayFromScreen, s_title,
            
            // PROGRESS SCREEN:
            p_progressTitle, p_habitName, p_monthAndYear, p_noHabitsPanel, p_showAllHabitsText,
            
            // ADD HABIT
            ah_title,ah_title, ah_TopPanelText, ah_TopPanelText1, ah_SummaryText1,
            ah_SummaryText2,ah_SummaryText3, ah_SummaryText4, ah_SummaryText5, ah_SummaryText6, ah_SummaryName, 
            ah_SummaryQuantity, ah_SummaryIncrement, ah_SummaryGoal, ah_SummaryDays, ah_DaysText, ah_QuantityPanelText2,
            ah_QuantityPanelText, ah_QuantityGoal, 
            
            // EDIT HABIT
            eh_title, eh_editHabitText1, eh_editHabitText2, eh_editHabitText3, eh_editHabitText4, eh_noHabitsPanel,
            eh_editHabitText5, eh_editHabitText6, eh_editIncrementAndGoalText1, eh_editIncrementAndGoalText2, eh_editIncrementAndGoalGoal,
            eh_deleteText1,eh_name, eh_days, eh_increment, eh_goal, eh_editHistoryHabitName, eh_text3, eh_editHistoryQuantity, eh_editHistoryText1,
            eh_editHistoryDeleteDate, eh_editHistoryDeleteText1, eh_editHistoryAddText1, eh_editHistoryAddText2, eh_editHistoryAddText3,
            eh_editHistoryAddText4, eh_editHistoryAddDateEntryLabels, eh_editHistoryAddDateWarning, 
            
            
            // SCREENSAVER PANEL
            screensaverTimeText, screensaverDateText,
        };
        
        JToggleButton toggleButtonColored[] = {
            ah_Monday, ah_Tuesday, ah_Wednesday, ah_Thursday, ah_Friday, ah_Saturday, ah_Sunday,  
            ah_IncrementPointOne, ah_IncrementPointFive, ah_IncrementOne,
            eh_Monday, eh_Tuesday, eh_Wednesday, eh_Thursday, eh_Friday, eh_Saturday, eh_Sunday,  
            eh_IncrementPointOne, eh_IncrementPointFive, eh_IncrementOne,
            eh_editHistoryCompleteButton, eh_editHistoryNotCompleteButton, eh_editHistoryAddCompleteButton, 
            eh_editHistoryAddNotCompleteButton,
        };
       
        
        // PAINTING ALL TO THEIR COLORS
        for(JPanel curr : primaryColored){
            curr.setBackground(PRIMARY_COLOR);
            curr.setForeground(TEXT_COLOR);
        }
        for(JPanel curr : secondaryColored){
            curr.setBackground(SECONDARY_COLOR);
        }
        for(JButton curr : buttonColored){
            curr.setBackground(BUTTON_COLOR);
            curr.setForeground(TEXT_COLOR);
            curr.setFocusable(false);
        }
        for(JLabel curr : textColored){
            curr.setForeground(TEXT_COLOR);
            curr.setBackground(PRIMARY_COLOR);
        }
        for(JToggleButton curr : toggleButtonColored){
            curr.setBackground(BUTTON_COLOR);
            curr.setForeground(TEXT_COLOR);
            curr.setFocusable(false);
        }
        
        // MISC. PAINTING CALLS : ==============================================================================
        // NAVIGATION:  Painting the navSelectorPanel so that it can be a different color
        navSelector.setBackground(PRIMARY_COLOR);
        navigationPanel.setBackground(PRIMARY_COLOR);
        
        settingsButton.setBackground(PRIMARY_COLOR);
        addHabitButton.setBackground(darkenColor(PRIMARY_COLOR));
        homeButton.setBackground(darkenColor(PRIMARY_COLOR));
        editHabitButton.setBackground(darkenColor(PRIMARY_COLOR));
        progressButton.setBackground(darkenColor(PRIMARY_COLOR));
        
        settingsButton.setForeground(darkenColor(TEXT_COLOR));
        addHabitButton.setForeground(darkenColor(TEXT_COLOR));
        homeButton.setForeground(darkenColor(TEXT_COLOR));
        editHabitButton.setForeground(darkenColor(TEXT_COLOR));
        progressButton.setForeground(darkenColor(TEXT_COLOR));

        // HOME: Painting the backgrounds of the navigation buttons to the same primary color INSTEAD of the button color
        settingsButton.setBackground(PRIMARY_COLOR);
        addHabitButton.setBackground(PRIMARY_COLOR);
        editHabitButton.setBackground(PRIMARY_COLOR);
        progressButton.setBackground(PRIMARY_COLOR);
        
        // HOME: Painting the scroll buttons/panels
        h_scrollUpPanel.setBackground(PRIMARY_COLOR);
        h_scrollUpPanel.setForeground(TEXT_COLOR);
        h_scrollDownPanel.setBackground(PRIMARY_COLOR);
        h_scrollDownPanel.setForeground(TEXT_COLOR);
        
        // HOME: Painting the date
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
        
        // EDIT HISTORY: Painting the edit habits entries 
        eh_historyTable.setBackground(PRIMARY_COLOR);
        eh_historyTable.setForeground(TEXT_COLOR);
        ehist_historyScrollPane.setBackground(PRIMARY_COLOR);
        
        // EDIT HISTORY: Painting the two changing values panel
        eh_editHistoryQuantityPanel.setBackground(PRIMARY_COLOR);
        eh_editHistoryYesNoPanel.setBackground(PRIMARY_COLOR);
        
        
        
        // SCREENSAVER : If the screensavers is "Skyline" | "today progress" | "simple clock/date" ----> Then we need to repaint certain things
        switch(screensaver.getScreensaver()){
            case 1 -> {
                screensaverTimeText.setForeground(TEXT_COLOR);
                screensaverDateText.setForeground(TEXT_COLOR);
            }
            case 3 -> {
                screensaverPanel.setBackground(Color.BLACK);
            }
            case 4 -> {
                screensaverTodaysProgressTitle.setBackground(PRIMARY_COLOR);
                screensaverTodaysProgressTitle.setForeground(TEXT_COLOR);
                screensaverTodaysProgress.setBackground(PRIMARY_COLOR);
                screensaverTodaysProgress.setForeground(SECONDARY_COLOR);
                screensaverTodaysProgressDisplay.setBackground(PRIMARY_COLOR);
                screensaverTodaysProgressDisplay.setForeground(TEXT_COLOR);
                screensaverPanel.setBackground(PRIMARY_COLOR);
            }
            case 5 ->{
                screensaverOverallProgress.setBackground(PRIMARY_COLOR);
                overallProgressDayCircle.setBackground(TEXT_COLOR);
                overallProgressWeekCircle.setBackground(TEXT_COLOR);
                overallProgressMonthCircle.setBackground(TEXT_COLOR);
                overallProgressNoHabitsText.setBackground(PRIMARY_COLOR);
                overallProgressNoHabitsText.setForeground(TEXT_COLOR);
                overallProgressNoStreaksText.setBackground(PRIMARY_COLOR);
                overallProgressNoStreaksText.setForeground(TEXT_COLOR);
                overallProgressText1.setForeground(TEXT_COLOR);
                overallProgressText2.setForeground(TEXT_COLOR);
                overallProgressText3.setForeground(TEXT_COLOR);
                overallProgressText4.setForeground(TEXT_COLOR);
                overallProgressStreakBarGraph.setBackground(TEXT_COLOR);
                overallProgressYearBarGraph.setBackground(TEXT_COLOR);
                overallProgressYearBarGraph.setForeground(darkenColor(PRIMARY_COLOR));
            }
            case 6 -> {
                screensaverPanel.setBackground(PRIMARY_COLOR);
                screensaverTimeText.setForeground(TEXT_COLOR);
                screensaverDateText.setForeground(TEXT_COLOR);
            }
        }
        
        
        // Making sure everything gets repainted
        this.repaint();
        this.revalidate();
    }
    
    
    // =================================================================================================================================
    // ================== [ FILE FUNCTION ] ==========================================================================================
    // =================================================================================================================================
    
    // Creates a new quantity card file with the information from the card
    private void createQuantityHabitFile(HabitCard_Quantity target){
        // Making path 
        Path filePath = Path.of(programFolderName, habitsFolderName, target.getHabitName() + ".txt");
        
        // Creating the file and writting basic information into it
        try {
            Files.createDirectories(filePath.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(filePath,StandardOpenOption.CREATE_NEW)) {
                writer.write("name=" + target.getHabitName());
                writer.newLine();
                
                writer.write("color=" + target.getHabitColorString());
                writer.newLine();
                
                writer.write("days=" + target.getWeek());
                writer.newLine();
                
                writer.write("lastmodified=" + target.getLastModified().toString());
                writer.newLine();
                
                writer.write("type=quantity");
                writer.newLine();
                
                writer.write("increment="  + target.getIncrement());
                writer.newLine();
                
                writer.write("goal=" + target.getGoal());
                writer.newLine();
                
                
                // Blank line separates metadata from entries
                writer.newLine();

                // --- Write date entries ---
                for (Map.Entry<LocalDate, QuantityEntry> entry : target.getCompletionMap().entrySet()) {
                    LocalDate date = entry.getKey();
                    QuantityEntry qe = entry.getValue();
                    writer.write(date + "=" + qe.getReached() + "," + qe.getGoal());
                    writer.newLine();
                }
                
            }

        } 
        
        catch (FileAlreadyExistsException e) {
            JOptionPane.showMessageDialog(this, "Habit already exists: " + target.getHabitName(), "ERROR", JOptionPane.ERROR);
        } 
        
        catch (IOException e) { 
            JOptionPane.showMessageDialog(this, "Error creating habit file: " + e.getMessage(), "ERROR", JOptionPane.ERROR);
        }
    }
    
    // Creates a new yesno card file with the information from the card
    private void createYesNoHabitFile(HabitCard_YesNo target){
        // Making path 
        Path filePath = Path.of(programFolderName, habitsFolderName, target.getHabitName() + ".txt");

        
        // Creating the file and writting basic information into it
        try {
            Files.createDirectories(filePath.getParent());

            try (BufferedWriter writer = Files.newBufferedWriter(filePath,StandardOpenOption.CREATE_NEW)) {
                writer.write("name=" + target.getHabitName());
                writer.newLine();
                
                writer.write("color=" + target.getHabitColorString());
                writer.newLine();
                
                writer.write("days=" + target.getWeek());
                writer.newLine();
                
                writer.write("lastmodified=" + target.getLastModified().toString());
                writer.newLine();
                
                writer.write("type=yesno");
                writer.newLine();
                
                // Blank line separates metadata from entries
                writer.newLine();

                // --- Write date entries ---
                for (Map.Entry<LocalDate, Boolean> entry : target.getCompletionMap().entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }
            }

        } 
        
        catch (FileAlreadyExistsException e) {
            JOptionPane.showMessageDialog(this, "Habit already exists: " + target.getHabitName(), "ERROR", JOptionPane.ERROR);
        } 
        
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error creating habit file: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Saving Quantity cards into file
    private void saveQuantityHabitFile(HabitCard_Quantity target){
        File file = new File(programFolderName + "/" + habitsFolderName + "/" + target.getHabitName() + ".txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // --- Write metadata ---
            writer.write("name=" + target.getHabitName());
            writer.newLine();

            Color c = target.getHabitColor();
            writer.write("color=" + c.getRed() + "," + c.getGreen() + "," + c.getBlue());
            writer.newLine();
            
            writer.write("type=quantity");
            writer.newLine();

            writer.write("days=" + target.getWeek());
            writer.newLine();
            
            writer.write("lastmodified=" + target.getLastModified().toString());
            writer.newLine();

            writer.write("increment=" + target.getIncrement());
            writer.newLine();

            writer.write("goal=" + target.getGoal());
            writer.newLine();

            // Blank line separates metadata from entries
            writer.newLine();

            // --- Write date entries ---
            for (Map.Entry<LocalDate, QuantityEntry> entry : target.getCompletionMap().entrySet()) {
                LocalDate date = entry.getKey();
                QuantityEntry qe = entry.getValue();
                writer.write(date + "=" + qe.getReached() + "," + qe.getGoal());
                writer.newLine();
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + file.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Saving YesNo cards into file
    private void saveYesNoHabitFile(HabitCard_YesNo target){
        File file = new File(programFolderName + "/" + habitsFolderName + "/" + target.getHabitName() + ".txt");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                // --- Write metadata ---
                writer.write("name=" + target.getHabitName());
                writer.newLine();

                Color c = target.getHabitColor();
                writer.write("color=" + c.getRed() + "," + c.getGreen() + "," + c.getBlue());
                writer.newLine();

                writer.write("type=yesno");
                writer.newLine();

                writer.write("days=" + target.getWeek());
                writer.newLine();
                
                writer.write("lastmodified=" + target.getLastModified().toString());
                writer.newLine();

                // Blank line separates metadata from entries
                writer.newLine();

                // --- Write date entries ---
                for (Map.Entry<LocalDate, Boolean> entry : target.getCompletionMap().entrySet()) {
                    writer.write(entry.getKey() + "=" + entry.getValue());
                    writer.newLine();
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + file.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    // Saves all habits, this is called every 60 seconds
    private void saveAllHabits(){
        // WHAT WE FOR SURE KNOW:
        // -- At this time, the folders will already be made
        // -- At this time, the habits that we have in memory will already be created as well
        
        h_savingFilesText.setVisible(true); // Simple visual showing that we are currently saving files
        
        for(HabitCard_Quantity currCard : allQuantityCards){
            saveQuantityHabitFile(currCard);
        }

        // Save all yes/no habits
        for(HabitCard_YesNo currCard : allYesNoCards){
            saveYesNoHabitFile(currCard);
        }
        
        h_savingFilesText.setVisible(false);
    }
    
    // Takes in a name of a habit that we are going to rename and changes only the name of the file 
    private void renameHabitFile(String oldName, String newName){
        String oldFilePath = programFolderName + "/" + habitsFolderName + "/" + oldName + ".txt";
        String newFilePath = programFolderName + "/" + habitsFolderName + "/" + newName + ".txt";

        File oldFile = new File(oldFilePath);
        File newFile = new File(newFilePath);

        if (oldFile.exists()) {
            boolean success = oldFile.renameTo(newFile);
            if (!success) {
                JOptionPane.showMessageDialog(this, "Failed to rename habit file: " + oldFile.getName(), "ERROR", JOptionPane.ERROR_MESSAGE);
            } 
        } 
    }
    
    // Removes the file of target from the disk
    private void deleteHabitFile(String target){
        String filePath = programFolderName + "/" + habitsFolderName + "/" + target + ".txt";
        File file = new File(filePath);

        if (file.exists()) {
            boolean success = file.delete();
            if (!success) {
                JOptionPane.showMessageDialog(this, "Failed to delete habit file: " + target, "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } 
    }
    
    // Takes in something like "away_screen" and a value of "2" and then remakes the file updating only that line (does nothing if it does not find it)
    private void updateConfigFile(String target, String newValue){
        Path path = Paths.get(programFolderName, configFileName);
        List<String> lines = new ArrayList<>();

        String line;
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(target)) {
                    line = target + "," + newValue;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    
    
    // =================================================================================================================================
    // ================== [ NAVIGATIONS FUNCTIONS ] ====================================================================================
    // =================================================================================================================================
    int navTargetY = 3;
    int navSelectorStep = 20; // Make sure this is divisible by 100 (the size of each icon
    Timer navSelectorTimer = new Timer(10,e->{
            if(navSelectorAgain.getY() < navTargetY){
                navSelectorAgain.setLocation(navSelectorAgain.getX(),navSelectorAgain.getY()+navSelectorStep);
            }
            else if(navSelectorAgain.getY() > navTargetY){
                navSelectorAgain.setLocation(navSelectorAgain.getX(),navSelectorAgain.getY()-navSelectorStep);
            }
            else{
                ((Timer)e.getSource()).stop();
            }

    });
    
    // Function to call when a side navigation button is clicked to move the "navigation selector" down/up to the correct position
    private void moveNavSelector(JPanel target){
        if(navSelectorTimer.isRunning())
            navSelectorTimer.stop();
        
        navTargetY = target.getY() + 3; // sets the target for the nav selector to move to before starting the timer
        navSelectorTimer.start();
    }
    
    // Functions that get called when the navigation buttons are clicked -> Switches the frame & moves the navigation selector
    private void settingsButtonClicked() {switchFrame(settings);  moveNavSelector(settingsButton);}
    private void addHabitButtonClicked() {switchFrame(addHabit);  moveNavSelector(addHabitButton);}
    private void homeButtonClicked()     {switchFrame(home);      moveNavSelector(homeButton);}
    private void editHabitButtonClicked(){switchFrame(editHabit); moveNavSelector(editHabitButton);}
    private void progressButtonClicked() {switchFrame(progress);  moveNavSelector(progressButton);}
    
    
    
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
        
        // If the evt is null, then we are most likely going to be coming in from the switchframe(home)
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
    
    public String getWeekday(){
        return h_date.getText().substring(0,h_date.getText().indexOf(','));
    }
    
    private void refreshHomeData(){
        
        // Getting the weekday so that we can compare to the cards date later
        String todaysWeekDay = getWeekday();
        
        
        // Clean the screen first of all instances of the visuals of the objects
        h_habitPanel.removeAll();
        
        
        // Now adding the instances back, this way we get a clean updated version of the lists
        // Adding first the quantity cards
        for(HabitCard_Quantity currCard : allQuantityCards){  // Looking through all quantity cards
            if(currCard.isForToday(todaysWeekDay)){           // If this card is a habit for today
                h_habitPanel.add(currCard);
                if(!currCard.hasDateEntry(LocalDate.now())){   // Adding an entry for today if it does not already have one 
                    currCard.addDateEntry(LocalDate.now(), 0.0, currCard.getGoal()); // Adding date entry for today
                    currCard.setLastModified(LocalDateTime.now());                   // Updating that we have made a change
                    saveQuantityHabitFile(currCard);                                 // Saving file in the disk since we are creating a new one
                }
                else                                          // If there is already an entry for today, then we need to update the cache
                    currCard.setQuantity(currCard.getDateEntry(LocalDate.now()).getReached());
                
            }
        }
        
        // Adding second the yes/no cards
        for(HabitCard_YesNo currCard : allYesNoCards){        // Looking through all yesno cards
            if(currCard.isForToday(todaysWeekDay)){           // If this card is a habit for today
                h_habitPanel.add(currCard);
                if(!currCard.hasDateEntry(LocalDate.now())){   // Adding an entry for today if it does not already have one 
                    currCard.addDateEntry(LocalDate.now(), false); // Adding date entry for today
                    currCard.setLastModified(LocalDateTime.now()); // Updating that we have made a change
                    saveYesNoHabitFile(currCard);                  // Saving file in the disk since we are creating a new one
                }
                else                                          // If there is already an entry for today, then we need to update the cache for it
                    currCard.setComplete(currCard.getDateEntry(LocalDate.now()));
            }
        }
        
        // Changes the size of the scroll after we added the cards
        updateScrollPaneSize();
        
        
        // If there are no habits in the panel, then we want to show the no habits panel
        if(h_habitPanel.getComponentCount() == 0){
            h_noHabitsPanel.setVisible(true);
            h_scrollPane.setVisible(false);
        }
        else{
            h_noHabitsPanel.setVisible(false);
            h_scrollPane.setVisible(true);
        }
    }

    
    
    
    
    
    // =================================================================================================================================
    // ================== [ SETTINGS  FUNCTIONS ] =======================================================================================
    // =================================================================================================================================
    
    private void s_awayFromScreenButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_awayFromScreenButtonMouseClicked
        // If the value is set to 0 or less, we wont accept it
        int newValue = ((Integer) s_awayFromScreenInput.getValue());
        if(newValue <= 0)
            return;

        // Saving value and updating the visual showing the current saved
        AWAY_FROM_SCREEN_TIME = newValue * 60;
        s_awayFromScreen.setText(Integer.toString(AWAY_FROM_SCREEN_TIME/60) + " Minute(s)");
        updateConfigFile("away_time", Integer.toString(AWAY_FROM_SCREEN_TIME));
    }//GEN-LAST:event_s_awayFromScreenButtonMouseClicked
   
    private void awayFromScreenOptionClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_awayFromScreenOptionClicked
        // Moving the selected text to position of the button clicked
        JLabel buttonClicked = (JLabel) evt.getSource();
        s_awayFromScreenSelectedText.setLocation(buttonClicked.getX(), buttonClicked.getY());
        
        // --- SKYLINE SCREENSAVER ----------------------
        if(buttonClicked == s_awayFromScreenOption1){
            screensaver.setScreensaver(1);
        } // --------------------------------------------
       
        // --- FISH TANK SCREENSAVER ---------------------
        else if(buttonClicked == s_awayFromScreenOption2){
            screensaver.setScreensaver(2);
        } // ---------------------------------------------
        
        //  --- BLANK BLACK SCREENSAVER --------------- 
        else if(buttonClicked == s_awayFromScreenOption3){
            screensaver.setScreensaver(3);
        } // ----------------------------------------------
        
        //  --- TODAYS PROGRESS SCREENSAVER ----------------- 
        else if(buttonClicked == s_awayFromScreenOption4){
            screensaver.setScreensaver(4);
        } // -------------------------------------------------

        //  --- OVERALL PROGRESS SCREENSAVER --------------- 
        else if(buttonClicked == s_awayFromScreenOption5){
            screensaver.setScreensaver(5);
        } // -----------------------------------------------
        
        //  --- SIMPLE CLOCK AND DATE SCREENSAVER --------- 
        else if(buttonClicked == s_awayFromScreenOption6){
            screensaver.setScreensaver(6);
        } // ------------------------------------------- 
        
        // Using the painting from the paint color section
        paintColors();
        
        // || FINISHED || ---> Saving to file the background chosen:
        updateConfigFile("away_screen", Integer.toString(screensaver.getScreensaver()));
    }//GEN-LAST:event_awayFromScreenOptionClicked
        
    private void s_theme1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_theme1MouseClicked
        // Changing colors back to theme colors
        PRIMARY_COLOR = new Color(191,172,129);
        SECONDARY_COLOR = new Color(216,216,216);
        BUTTON_COLOR = new Color(199,179,116);
        TEXT_COLOR = new Color(255,255,255);

        // Painting program again
        paintColors();

        // Saving file
        updateConfigFile("primary", "191,172,129");
        updateConfigFile("secondary", "216,216,216");
        updateConfigFile("button", "199,179,116");
        updateConfigFile("text", "255,255,255");
    }//GEN-LAST:event_s_theme1MouseClicked

    private void s_theme2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_theme2MouseClicked
        // Changing colors back to default colors
        PRIMARY_COLOR = new Color(105,134,191);
        SECONDARY_COLOR = new Color(209,230,246);
        BUTTON_COLOR = new Color(117,147,169);
        TEXT_COLOR = new Color(255,255,255);

        // Painting program again
        paintColors();

        // Saving file
        updateConfigFile("primary", "105,134,191");
        updateConfigFile("secondary", "209,230,246");
        updateConfigFile("button", "117,147,169");
        updateConfigFile("text", "255,255,255");
    }//GEN-LAST:event_s_theme2MouseClicked

    private void s_theme3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_theme3MouseClicked
        // Changing colors back to default colors
        PRIMARY_COLOR = new Color(221,178,93);
        SECONDARY_COLOR = new Color(204,204,204);
        BUTTON_COLOR = new Color(193,144,69);
        TEXT_COLOR = new Color(255,255,255);

        // Painting program again
        paintColors();

        // Saving file
        updateConfigFile("primary", "221,178,93");
        updateConfigFile("secondary", "204,204,204");
        updateConfigFile("button", "193,144,69");
        updateConfigFile("text", "255,255,255");
    }//GEN-LAST:event_s_theme3MouseClicked

    private void s_theme4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_s_theme4MouseClicked
        // Changing colors back to default colors
        PRIMARY_COLOR = new Color(175,125,93);
        SECONDARY_COLOR = new Color(201,208,188);
        BUTTON_COLOR = new Color(202,171,125);
        TEXT_COLOR = new Color(255,255,255);

        // Painting program again
        paintColors();

        // Saving file
        updateConfigFile("primary", "175,125,93");
        updateConfigFile("secondary", "201,208,188");
        updateConfigFile("button", "202,171,125");
        updateConfigFile("text", "255,255,255");
    }//GEN-LAST:event_s_theme4MouseClicked



    
    
    
    
    // =================================================================================================================================
    // ================== [ SCREEN SAVER FUNCTIONS ] ===================================================================================
    // =================================================================================================================================

    private void screensaverPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_screensaverPanelMouseClicked
        screensaver.stopClocks(); // Stopping the clock
        switchFrame(home);
    }//GEN-LAST:event_screensaverPanelMouseClicked

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ KEYBOARD FUNCTIONS ] =======================================================================================
    // =================================================================================================================================
    
    
    private void keyboardKeyClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_keyboardKeyClicked
        String current = keyboardTarget.getText();           // Getting the current text from the target input
        String input = ((JButton)evt.getSource()).getText(); // Getting the button text that was pressed
        
        // Custom return code for limiting the size of the name for the habit (added: allows backspace to go through this)
        if((keyboardTarget == h_addHabitName || keyboardTarget == eh_nameInput)
           && keyboardTarget.getText().length() >= MAX_HABIT_NAME_LENGTH && !input.equals("<-"))
            return;
        
        // Editing the string 
        if(input.equals("<-")){                              // If it was a backspace, then remove one letter from the back
            if(!current.isEmpty())
                current = current.substring(0,current.length()-1);
        }
        else if(input.equals("SPACE")){
            current = current + " ";                         // Appending a space if that is the key that we clicked
        }
        else{                                                // Append to the end of the current string the new input
            current = current + input;
        }
        
        
        keyboardTarget.setText(current);                     // Set the target text to the edited text now
    }//GEN-LAST:event_keyboardKeyClicked

    
    
    
    
    
    
    

    
    
    
    // =================================================================================================================================
    // ================== [ ADD SCREEN FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
    private String fixName(String inputName){
        // trim leading/trailing spaces, then replace multiple internal spaces with a single space
        return inputName.trim().replaceAll("\\s+", " ");
    }
    
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
        if(!foundDuplicate){
            for(HabitCard_Quantity currCard : allQuantityCards){
                if(currCard.getHabitName().equalsIgnoreCase(h_addHabitName.getText())){
                    foundDuplicate = true;
                    break;
                }
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
        ah_SummaryName.setText(fixName(h_addHabitName.getText()));
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
        if(!foundDuplicate){
            for(HabitCard_YesNo currCard : allYesNoCards){
                if(currCard.getHabitName().equalsIgnoreCase(h_addHabitName.getText())){
                    foundDuplicate = true;
                    break;
                }
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
        ah_SummaryName.setText(fixName(h_addHabitName.getText()));
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
            ah_CardPanel.add(new HabitCard_YesNo(this, ah_SummaryName.getText(), ah_SummaryColor.getBackground(), false, newWeekString));
        
        // If chosen card is a QUANTITY card, create and show the QUANTITY card
        else{
            // Finding out what is the increment
            double foundIncrement = (ah_IncrementPointOne.isSelected() ? 0.1 : (ah_IncrementPointFive.isSelected() ? 0.5 : 1));
            
            // Making object and adding to card panel
            ah_CardPanel.add(new HabitCard_Quantity(this, ah_SummaryName.getText(), ah_SummaryColor.getBackground(), 
                    0, Double.parseDouble(ah_SummaryGoal.getText()), foundIncrement, newWeekString));
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
                newCard.setComplete(false);    // Resetting the card
                allYesNoCards.add(newCard);    // Adding to the array
                
                // If this card is suppose to make an entry today, then go ahead and make it as false for now
                if(newCard.isForToday(getWeekday()))
                    newCard.addDateEntry(LocalDate.now(), false);
                
                createYesNoHabitFile(newCard); // Creating file in disk
            }
            
           // Handling error JUST in case
            else
                JOptionPane.showMessageDialog(this, "ERROR: Could not create card");
        }
        
        // The card is a QUANTITY card
        else{
            HabitCard_Quantity newCard = (HabitCard_Quantity) ah_CardPanel.getComponent(0);
            if(newCard != null){
                newCard.setQuantity(0.0);         // Reseting card value before sending to array
                //newCard.minusClicked();           // After reseting to 0, we are simulating a minus clicked so that it can go back to incompleted
                allQuantityCards.add(newCard);    // Adding to the array
                
                // If this card is suppose to make an entry today, then go ahead and make it as false for now
                if(newCard.isForToday(getWeekday()))
                    newCard.addDateEntry(LocalDate.now(), 0.0, newCard.getGoal());
                
                createQuantityHabitFile(newCard); // Creating file in disk
            }
            
            // Handling error JUST in case
            else
                JOptionPane.showMessageDialog(this, "ERROR: Could not create card");
        }
        
        homeButtonClicked(); // Switching frame to home and moving navigation bar (Calling this is only needed when changing views out too)
    }//GEN-LAST:event_ah_SaveButtonMouseClicked

    // =================================================================================================================================





    
    
    
    
    

    
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ EDIT HABIT FUNCTIONS ] =====================================================================================
    // =================================================================================================================================
    
    private HabitCard_Quantity targetQuantityCard = null;
    private HabitCard_YesNo targetYesNoCard = null;
    private String savedWeek = "";
    private String savedName = "";
    
    // === Functions for before the habit is chosen (setting up the view) ===
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
        
        // Changing Properties of each
        hnText.setForeground(TEXT_COLOR);
        hnLabel.setForeground(TEXT_COLOR);
        cLabel.setForeground(TEXT_COLOR);
        cBox.setOpaque(false);
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
            
            goalLabel.setForeground(TEXT_COLOR);
            goalText.setForeground(TEXT_COLOR);
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
        eh_editHistoryPanel.setVisible(false);
        eh_editHistoryAddPanel.setVisible(false);
        eh_editHistoryDeletePanel.setVisible(false);
        ehist_historyScrollPane.setVisible(true);
        eh_editHistoryBackButton.setVisible(true);
        eh_editHistoryAddButton.setVisible(true);
        eh_editHistoryDeleteButton.setVisible(true);
        eh_editHistoryScrollDownButton.setVisible(true);
        eh_editHistoryScrollUpButton.setVisible(true);
        eh_editHistoryHabitName.setVisible(true);
        eh_editHistoryText1.setVisible(true);
        
        
        
        
        
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
        
        
        // Getting count and finding out if we need to stop here
        if(allQuantityCards.size() + allYesNoCards.size() == 0){
            eh_noHabitsPanel.setVisible(true);
        }
        else{
            eh_noHabitsPanel.setVisible(false);
        }
        
        
        
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
        
        if(count > 8){
            eh_scrollUpPanel.setVisible(true);
            eh_scrollDownPanel.setVisible(true);
        }
        else{
            eh_scrollUpPanel.setVisible(false);
            eh_scrollDownPanel.setVisible(false);
        }
        
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
            
            // Saving name to check later if they are different
            savedName = targetQuantityCard.getHabitName();
        }
        else{  // No need to do targetYesNoCard == null anymore since we made sure of that with earlier catch
            eh_name.setText(targetYesNoCard.getHabitName());
            eh_color.setBackground(targetYesNoCard.getHabitColor());
            weekFromCard = targetYesNoCard.getWeek();
            eh_editHabitSummaryPanel.setSize(630, 170);
            eh_editHabitSummaryPanel.setLocation(150, 135);
            
            // Saving name to check later if they are different
            savedName = targetYesNoCard.getHabitName();
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
        
        // Saving information that we might need later in other functions
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
        if(targetQuantityCard != null){
            allQuantityCards.remove(targetQuantityCard);
            deleteHabitFile(targetQuantityCard.getHabitName());
        }
        else{
            allYesNoCards.remove(targetYesNoCard);
            deleteHabitFile(targetYesNoCard.getHabitName());
        }
        
        
        // Reset the Panel -> takes back to the "starting page" of the edit panel
        eh_resetPanel();
    }//GEN-LAST:event_eh_deleteConfirmButtonMouseClicked
    
    private void eh_backButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_backButtonMouseClicked
        eh_resetPanel();
    }//GEN-LAST:event_eh_backButtonMouseClicked

    private void eh_editNameButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editNameButtonMouseClicked
        keyboard.setLocation(190,330);
        keyboard.setVisible(true);
        eh_editNamePanel.setVisible(true);
        eh_editHabitSummaryPanel.setVisible(false);
        keyboardTarget = eh_nameInput;
        eh_nameInput.setText(targetQuantityCard != null ? targetQuantityCard.getHabitName() : targetYesNoCard.getHabitName());
        eh_bottomButtonsPanel.setVisible(false);
    }//GEN-LAST:event_eh_editNameButtonMouseClicked

    private void eh_editNameCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editNameCancelButtonMouseClicked
        keyboard.setVisible(false);
        keyboard.setLocation(190,-330);
        keyboardTarget = null;
        eh_editNamePanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(true);
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
        eh_name.setText(fixName(eh_nameInput.getText()));
        
        // Saving into memory
        if(targetQuantityCard != null){
            targetQuantityCard.setHabitName(eh_name.getText());                 // Setting the text on the summary 
            if(!savedName.equals(targetQuantityCard.getHabitName())){           // If name does not equal to the saved name
                targetQuantityCard.setLastModified(LocalDateTime.now());        // Update last modified before saving
                renameHabitFile(savedName, targetQuantityCard.getHabitName());  // Renaming the file
                savedName = eh_name.getText();                                  // Updating saved name
            }
        }
        else{
            targetYesNoCard.setHabitName(eh_name.getText());                    // Setting the text on the summary
            if(!savedName.equals(targetYesNoCard.getHabitName())){
                targetYesNoCard.setLastModified(LocalDateTime.now());           // Update last modified before saving
                renameHabitFile(savedName, targetYesNoCard.getHabitName());     // Renaming file if the name changed
                savedName = eh_name.getText();                                  // Updating saved name
            }
            
        }
        
        // Switch back to the summary panel
        keyboard.setVisible(false);
        keyboard.setLocation(190,-330);
        keyboardTarget = null;
        eh_editNamePanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(true);
    }//GEN-LAST:event_eh_editNameSaveButtonMouseClicked

    private void eh_editColorButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editColorButtonMouseClicked
        // Open the menu to choose a color
        Color chosenColor = JColorChooser.showDialog(null, "Choose a color:", eh_color.getBackground());
        
        // If color is valid, make the panel this color
        if(chosenColor != null){
            // Setting the summary panel with this new color
            eh_color.setBackground(chosenColor);
            
            // Saving into memory and disk
            if(targetQuantityCard != null){
                targetQuantityCard.setHabitColor(chosenColor);
                targetQuantityCard.setLastModified(LocalDateTime.now()); // Update last modified before saving
                saveQuantityHabitFile(targetQuantityCard);
            }
            else{
                targetYesNoCard.setHabitColor(chosenColor);
                targetYesNoCard.setLastModified(LocalDateTime.now()); // Update last modified before saving
                saveYesNoHabitFile(targetYesNoCard);
            }
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
        
        

        // Building the new week string in binary to get ready to save (For memory)
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
        
        // Setting the summary visaul of the week
        eh_days.setText(newWeekString);
        
        // Saving into memory and disk
        if(targetQuantityCard != null){
            targetQuantityCard.setWeek(savedWeek); 
            targetQuantityCard.setLastModified(LocalDateTime.now()); // Update last modified before saving
            saveQuantityHabitFile(targetQuantityCard);
        }
        else{
            targetYesNoCard.setWeek(savedWeek);
            targetYesNoCard.setLastModified(LocalDateTime.now()); // Update last modified before saving
            saveYesNoHabitFile(targetYesNoCard);
        }
        
        
        // Switching back to summary panel
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
        Double newIncrement = eh_IncrementPointOne.isSelected() ? 0.1 : (eh_IncrementPointFive.isSelected() ? 0.5 : eh_IncrementOne.isSelected() ? 1.0 : -1);
        Double newGoal = Double.valueOf(eh_editIncrementAndGoalGoal.getText());
        

        // If the increment is for some reason not set, then deny the save
        if(newIncrement == -1){
            flashToggleButton(eh_IncrementPointOne);
            flashToggleButton(eh_IncrementPointFive);
            flashToggleButton(eh_IncrementOne);
            return;
        }
        
        // If the goal is set to 0, we deny the save
        if(newGoal <= 0){
            flashButton(eh_quantityIncrease);
            flashButton(eh_quantityDecrease);
            return;
        }
        
        
            
        
        // Setting up the summary we are going back to 
        eh_increment.setText(Double.toString(newIncrement));
        eh_goal.setText(Double.toString(newGoal));
        
        
        // Save into memory
        targetQuantityCard.setIncrement(newIncrement);
        targetQuantityCard.setGoal(newGoal);
        
        
        // Update last modified before saving
        targetQuantityCard.setLastModified(LocalDateTime.now());
        
        // Saving into disk
        saveQuantityHabitFile(targetQuantityCard);
        
        // Switching back to summary panel
        eh_editIncrementAndGoalPanel.setVisible(false);
        eh_editHabitSummaryPanel.setVisible(true);
        eh_bottomButtonsPanel.setVisible(true);
    }//GEN-LAST:event_eh_editIncrementAndGoalSaveButtonMouseClicked
    
    
    
    
    
    
    //  [ EDIT HISTORY HELPER FUNCTIONS ] ---------------------------------------------------------
    // SCROLL FUNCTION: When the scroll button is clicked (for both the selection box and history table)
    private void eh_editHistoryScrollButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryScrollButtonClicked
        JButton pressedButton = (JButton) evt.getSource();

        // Making sure that row count is greater than 1
        if(eh_historyTable.getRowCount() > 1){
            int row = Math.max(0, eh_historyTable.getSelectedRow()); // Making sure that the selected row is greater than 0 
            int rowCount = eh_historyTable.getRowCount();

            
            if (pressedButton == eh_editHistoryScrollUpButton)
                row--;
            else
                row++;

            if (row >= 0 && row < rowCount) {
                eh_historyTable.setRowSelectionInterval(row, row);
                eh_historyTable.scrollRectToVisible(eh_historyTable.getCellRect(row, 0, true));
            }
        }
    }//GEN-LAST:event_eh_editHistoryScrollButtonClicked

    
    // BACK BUTTON: go back to the page before (summary page of habit)
    private void eh_editHistoryBackButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryBackButtonMouseClicked
        eh_editHistoryPanel.setVisible(false); // Simple hide again the history panel
        eh_bottomButtonsPanel.setVisible(true);
        eh_editHabitPanel.setVisible(true);
    }//GEN-LAST:event_eh_editHistoryBackButtonMouseClicked

    
    
    
    //  -------------------------------------------------------------------------------------------------
    //  [ EDIT HISTORY FUNCTIONS ] ----------------------------------------------------------------------
    // LOAD THE HISTORY PANEL:  Edit History button is clicked in 
    private void eh_editHistoryButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryButtonMouseClicked
        
        // Hiding add/delete panels
        eh_editHistoryDeletePanel.setVisible(false);
        eh_editHistoryAddPanel.setVisible(false);
        
        // Show everything back in case it isnt visibe
        ehist_historyScrollPane.setVisible(true);
        eh_editHistoryBackButton.setVisible(true);
        eh_editHistoryAddButton.setVisible(true);
        eh_editHistoryDeleteButton.setVisible(true);
        eh_editHistoryScrollDownButton.setVisible(true);
        eh_editHistoryScrollUpButton.setVisible(true);
        eh_editHistoryHabitName.setVisible(true);
        eh_editHistoryText1.setVisible(true);

        // Setting up the name label
        eh_editHistoryHabitName.setText(savedName);

        // Loading in information into the table and showing the correct value change panel
        HabitCard_Quantity loadedQuantityCard = getQuantityCard(eh_editHistoryHabitName.getText());
        HabitCard_YesNo loadedYesNoCard = (loadedQuantityCard != null ? null : getYesNoCard(eh_editHistoryHabitName.getText()));
        // ^^^ To save time, if we already found the card in quantity  array, then just make loadedyesnocard null

        if(loadedQuantityCard != null){
            eh_editHistoryQuantityPanel.setVisible(true); // Making change value panel visible
            eh_editHistoryYesNoPanel.setVisible(false);


            DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Date", "Quantity", "Goal", "Completed"}, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
            };

            // Loading in entries
            Map<LocalDate, QuantityEntry> completionMap = loadedQuantityCard.getCompletionMap();
            completionMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(LocalDate.now())) // <- dont populate today's entry into here
                .sorted(Map.Entry.<LocalDate, QuantityEntry>comparingByKey().reversed())
                .forEach(entry -> {
                    LocalDate date = entry.getKey();
                    QuantityEntry qEntry = entry.getValue();

                    model.addRow(new Object[]{
                        date,
                        qEntry.getReached(),
                        qEntry.getGoal(),
                        qEntry.getCompleted()
                    });
                });

            eh_historyTable.setModel(model);

        }
        else if(loadedYesNoCard != null){
            eh_editHistoryYesNoPanel.setVisible(true);    // Making change value panel visible
            eh_editHistoryQuantityPanel.setVisible(false);


            // Create new model (also making cells uneditable)
            DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Date", "Completed"} , 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
            };

            // Loading in entries
            Map<LocalDate, Boolean> newCompletionMap = loadedYesNoCard.getCompletionMap();
            newCompletionMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(LocalDate.now())) // <- dont populate today's entry into here
                .sorted(Map.Entry.<LocalDate, Boolean>comparingByKey().reversed())
                .forEach(entry -> model.addRow(new Object[]{
                    entry.getKey(),
                    entry.getValue()
                }));

            // Setting the information into the table
            eh_historyTable.setModel(model);

        }
        else{
            JOptionPane.showMessageDialog(this, "Could not load past entries -> Did not find habit using name: " + savedName,"ERROR", JOptionPane.ERROR);
            return;
        }



        // Setting the font and height of the rows OF THE INFORMATION
        eh_historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        eh_historyTable.setRowHeight(24);

        // Setting the font and height of the rows OF THE HEADERS
        JTableHeader header = eh_historyTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        // Centering the date (for some reason the header is already center so no need to do so)
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < eh_historyTable.getColumnCount(); i++)
            eh_historyTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

        // Setting the selected row to the first row
        if(eh_historyTable.getRowCount() > 0){
            eh_historyTable.setRowSelectionInterval(0, 0);
            eh_historyTable.scrollRectToVisible(eh_historyTable.getCellRect(0, 0, true));
        }

        // After everything loads in, Switch the panels
        eh_editHistoryPanel.setVisible(true);
        eh_editHabitPanel.setVisible(false);
        eh_bottomButtonsPanel.setVisible(false);
        
        
        // Showing or hiding the bottom panels depending on if there is date in the history section
        if(targetQuantityCard != null){
            eh_editHistoryQuantityPanel.setVisible(targetQuantityCard.getDateEntryCount() > 1);
        }
        else{
            eh_editHistoryYesNoPanel.setVisible(targetYesNoCard.getDateEntryCount() > 1);
        }
    }//GEN-LAST:event_eh_editHistoryButtonMouseClicked

    
    // When the row changes in the history table
    private void ehist_rowChanged(){
        int row = eh_historyTable.getSelectedRow();
        
        // If for some reason the row changed to none selected, go ahead and set it back to the first row
        if (row == -1) {
            return;
        } 

        // If we have more than 2 columns, we are in a quantity habit
        if(eh_editHistoryQuantityPanel.isVisible()){
            Object temp = eh_historyTable.getValueAt(row, 1);
            eh_editHistoryQuantity.setText(String.valueOf(temp));
        }
        // If we only have 2 columns, we are in a yesno habit
        else{
            ehist_completedButtonGroup.clearSelection();
            boolean rowValue = (String.valueOf(eh_historyTable.getValueAt(row, 1)).equals("true"));
            eh_editHistoryCompleteButton.setSelected(rowValue);
            eh_editHistoryNotCompleteButton.setSelected(!rowValue);
        }
    }
    
     // Boolean to prevent changes while writting
    boolean criticalSection = false;    

    
    // When the complete/not complete button is pressed
    private void ehist_completedNotCompletedButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ehist_completedNotCompletedButtonClicked
        // Making sure that there is history in the table
        if(eh_historyTable.getRowCount() == 0)
            return;
        
        int row = eh_historyTable.getSelectedRow();                                             // Finding out what row is selected
        if(row == -1){                                                                             // Catching when for some reason a row is not selected
            JOptionPane.showMessageDialog(this, "No row selected.", "ERROR", JOptionPane.ERROR); 
            return;
        }
        
        // Catch when we are currently writting in something else
        if(criticalSection)
            return;
        criticalSection = true;
       
        LocalDate dateSelected = (LocalDate) eh_historyTable.getValueAt(row, 0);                // Getting the date that we are going to be looking for
        HabitCard_YesNo habitSelected = getYesNoCard(eh_editHistoryHabitName.getText());        // Getting the habit that we are suppose to be using 
        
        
        Boolean tableValue = (Boolean) eh_historyTable.getValueAt(row, 1);
        JToggleButton clickedButton = (JToggleButton) evt.getSource();

        if ((tableValue && clickedButton == eh_editHistoryCompleteButton) ||
            (!tableValue && clickedButton == eh_editHistoryNotCompleteButton)) {
            return;
        }
        
        int newValue = habitSelected.changeDateEntry(dateSelected);      // Change the value in the data array
         
        if(newValue == -1){                                                                        // Catch when the value was not foudn in the changeDateEntry()
            JOptionPane.showMessageDialog(this, "Could not change data.", "ERROR", JOptionPane.ERROR);
            return;
        }
        
        eh_historyTable.setValueAt((newValue == 1), row, 1);                                    // Change the value in the table
        criticalSection = false;
        
        // Updating that we have changed something
        habitSelected.setLastModified(LocalDateTime.now());
    }//GEN-LAST:event_ehist_completedNotCompletedButtonClicked

    
    // When the -/+ button is pressed
    private void ehist_increaseDecreaseButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ehist_increaseDecreaseButtonClicked
        // Making sure that there is history in the table
        if(eh_historyTable.getRowCount() == 0)
            return;
        
        int row = eh_historyTable.getSelectedRow();
        if(row == -1){
            JOptionPane.showMessageDialog(this, "No row selected.", "ERROR", JOptionPane.ERROR); 
            return;
        }
        
        if(criticalSection)
            return;
        criticalSection = true;
        
        // Getting current data from the table and saving the card to acces things in it temp.
        LocalDate dateSelected = (LocalDate) eh_historyTable.getValueAt(row, 0);
        double currentValue = Double.parseDouble(eh_editHistoryQuantity.getText());
        HabitCard_Quantity habitSelected = getQuantityCard(eh_editHistoryHabitName.getText());
        
        // Incresing or decreasing the value we got from
        if((JButton)evt.getSource() == eh_editHistorydDecreaseButton){
            currentValue -= habitSelected.getIncrement();
            if(currentValue < 0)
                currentValue = 0;
        }
        else{
            currentValue += habitSelected.getIncrement();
        }
        
        // Rounding to a nice number
        currentValue = Math.round(currentValue * 10.0) / 10.0;
        
        // Setting the value in the data array
        int newCompletedValue = habitSelected.changeDateEntry(dateSelected, currentValue);
        
        // Catching if there was no entry for this day, shouldn't ever happen though
        if(newCompletedValue == -1){
            JOptionPane.showMessageDialog(this, "Could not change data.", "ERROR", JOptionPane.ERROR);
            criticalSection = false;
            return;
        }
        
        // Setting the value in the bottom quantity showing value
        eh_editHistoryQuantity.setText(Double.toString(currentValue));
        
        // Setting the value in the table
        eh_historyTable.setValueAt(currentValue, row, 1);
        eh_historyTable.setValueAt((newCompletedValue == 1), row, 3);
        
        criticalSection = false;
        
        // Updating that we have changed something
        habitSelected.setLastModified(LocalDateTime.now());
    }//GEN-LAST:event_ehist_increaseDecreaseButtonClicked

    
    
    
    //  -------------------------------------------------------------------------------------------------
    //  [ DELETE/ADD HISTORY FUNCTIONS ] ----------------------------------------------------------------------

    // DELETE BUTTON CLICKED
    private void eh_editHistoryDeleteButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryDeleteButtonMouseClicked
        if(eh_historyTable.getRowCount() == 0)
            return;
        
        // Hide everything else 
        ehist_historyScrollPane.setVisible(false);
        eh_editHistoryBackButton.setVisible(false);
        eh_editHistoryAddButton.setVisible(false);
        eh_editHistoryDeleteButton.setVisible(false);
        eh_editHistoryScrollDownButton.setVisible(false);
        eh_editHistoryScrollUpButton.setVisible(false);
        eh_editHistoryHabitName.setVisible(false);
        eh_editHistoryText1.setVisible(false);
        eh_editHistoryQuantityPanel.setVisible(false);
        eh_editHistoryYesNoPanel.setVisible(false);
        
        // Set up the entry target date that user wants to delete
        eh_editHistoryDeleteDate.setText((String) eh_historyTable.getValueAt(eh_historyTable.getSelectedRow(), 0).toString());
        
        // Show the delete confirm panel
        eh_editHistoryDeletePanel.setVisible(true); 
    }//GEN-LAST:event_eh_editHistoryDeleteButtonMouseClicked

    // DELETE -> CANCEL BUTTON CLICKED
    private void eh_editHistoryDeleteCanceButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryDeleteCanceButtonMouseClicked
        eh_editHistoryButtonMouseClicked(null); // Reloads the history page
    }//GEN-LAST:event_eh_editHistoryDeleteCanceButtonMouseClicked

    // DELETE -> CONFIRM BUTTON CLICKED
    private void eh_editHistoryDeleteConfirmButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryDeleteConfirmButtonMouseClicked
        
        // If we are removing a quantity card
        if(targetQuantityCard != null){
            // -- REMOVE FROM MEMORY --
            targetQuantityCard.removeDateEntry((LocalDate) eh_historyTable.getValueAt(eh_historyTable.getSelectedRow(), 0));
            
            // Update last modified before saving
            targetQuantityCard.setLastModified(LocalDateTime.now());
            
            // -- REMOVE FROM FILES (SAVE FILE) -- 
            saveQuantityHabitFile(targetQuantityCard);
            
            // -- RELOADING THE HISTORY TABLE AND PAGE --
            eh_editHistoryButtonMouseClicked(null);
        }
        
        // If we are removing a yesno card
        else{
            // -- REMOVE FROM MEMORY --
            targetYesNoCard.removeDateEntry((LocalDate) eh_historyTable.getValueAt(eh_historyTable.getSelectedRow(), 0));
            
            // Update last modified before saving
            targetYesNoCard.setLastModified(LocalDateTime.now());
            
            // -- REMOVE FROM FILES (SAVE FILE) -- 
            saveYesNoHabitFile(targetYesNoCard);
            
            // -- RELOADING THE HISTORY TABLE AND PAGE --
            eh_editHistoryButtonMouseClicked(null);
        }    
    }//GEN-LAST:event_eh_editHistoryDeleteConfirmButtonMouseClicked

    // ADD BUTTON CLICKED
    private void eh_editHistoryAddButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryAddButtonMouseClicked
        
        // Hiding everything else 
        ehist_historyScrollPane.setVisible(false);
        eh_editHistoryBackButton.setVisible(false);
        eh_editHistoryAddButton.setVisible(false);
        eh_editHistoryDeleteButton.setVisible(false);
        eh_editHistoryScrollDownButton.setVisible(false);
        eh_editHistoryScrollUpButton.setVisible(false);
        eh_editHistoryHabitName.setVisible(false);
        eh_editHistoryText1.setVisible(false);
        eh_editHistoryQuantityPanel.setVisible(false);
        eh_editHistoryYesNoPanel.setVisible(false);
        
        // Set up the panel according to what card (quantity/yesno card)
        if(targetQuantityCard != null){
            eh_editHistoryAddQuantityPanel.setVisible(true);
            eh_editHistoryAddYesNoPanel.setVisible(false);
            eh_editHistoryAddCurrentGoal.setText("Current: " + targetQuantityCard.getGoal());
            eh_editHistoryAddIncrement.setText("Increment: " + targetQuantityCard.getIncrement());
            eh_editHistoryAddGoal.setText(Double.toString(targetQuantityCard.getGoal())); 
            eh_editHistoryAddReached.setText("0.0");
        }
        else{
            eh_editHistoryAddYesNoPanel.setVisible(true);
            eh_editHistoryAddQuantityPanel.setVisible(false);
            ehist_addPanelCompletedButtonGroup.clearSelection(); 
        }
        
        // Set up the target date that we are going to be adding
        eh_editHistoryAddDay.setValue(1);
        eh_editHistoryAddMonth.setValue(1);
        eh_editHistoryAddYear.setValue(LocalDate.now().getYear());
        
        eh_editHistoryAddPanel.setVisible(true); // Show the delete confirm panel
    }//GEN-LAST:event_eh_editHistoryAddButtonMouseClicked

    // ADD -> +/- BUTTONS CLICKED IN THE QUANTITY PANEL OF THE ADD PANEL
    private void eh_editHistoryAddIncreaseDecreaseButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryAddIncreaseDecreaseButtonClicked
        criticalSection = true;
        JButton buttonClicked = (JButton) evt.getSource();
        
        // -- REACHED NUMBER BEING CHANGED --
        if(buttonClicked == eh_editHistoryAddReachedDecreaseButton || buttonClicked == eh_editHistoryAddReachedIncreaseButton){
            double reachedNumber = Double.parseDouble(eh_editHistoryAddReached.getText());
            reachedNumber += buttonClicked == eh_editHistoryAddReachedDecreaseButton ? 
                    -targetQuantityCard.getIncrement() : targetQuantityCard.getIncrement();
            reachedNumber = Math.round(reachedNumber * 10.0) / 10.0;
            if(reachedNumber < 0)
                reachedNumber = 0;
            eh_editHistoryAddReached.setText(Double.toString(reachedNumber));
        }
        
        
        // -- GOAL NUMBER BEING CHANGED -- 
        else{
            double goalNumber = Double.parseDouble(eh_editHistoryAddGoal.getText());
            goalNumber += buttonClicked == eh_editHistoryAddGoalDecreaseButton ? 
                    -targetQuantityCard.getIncrement() : targetQuantityCard.getIncrement();
            goalNumber = Math.round(goalNumber * 10.0) / 10.0;
            if(goalNumber < 0)
                goalNumber = 0;
            eh_editHistoryAddGoal.setText(Double.toString(goalNumber));
        }
        
        criticalSection = false;
    }//GEN-LAST:event_eh_editHistoryAddIncreaseDecreaseButtonClicked

    // ADD -> CANCEL BUTTON CLICKED
    private void eh_editHistoryAddCancelButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryAddCancelButtonMouseClicked
         eh_editHistoryButtonMouseClicked(null); // Reloads the history page
    }//GEN-LAST:event_eh_editHistoryAddCancelButtonMouseClicked

    // ADD -> SAVE BUTTON CLICKED
    private void eh_editHistoryAddSaveButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eh_editHistoryAddSaveButtonMouseClicked
        
        // Validate that the date 
        LocalDate newDate;
        try {
            int newDay   = ((Number) eh_editHistoryAddDay.getValue()).intValue();
            int newMonth = ((Number) eh_editHistoryAddMonth.getValue()).intValue();
            int newYear  = ((Number) eh_editHistoryAddYear.getValue()).intValue();
            newDate = LocalDate.of(newYear, newMonth, newDay);
        } 
        catch (DateTimeException e) {
            flashLabel(eh_editHistoryAddDateEntryLabels);
            return;
        }
        
        
        // Validate that the new date is not today
        if(newDate.equals(LocalDate.now())){
            flashLabel(eh_editHistoryAddDateWarning);
            return;
        }
        
        // Validate that the new date is not for a future date
        if(newDate.isAfter(LocalDate.now())){
            flashLabel(eh_editHistoryAddDateWarning);
            return;
        }
        
        
        // If we are in a quantity card
        if(targetQuantityCard != null){
            // Validate that the card does not already have an entry for this day
            if(targetQuantityCard.hasDateEntry(newDate)){
                flashLabel(eh_editHistoryAddDateWarning);
                return;
            }
            
            double newGoal = Double.parseDouble(eh_editHistoryAddGoal.getText());
            double newReached = Double.parseDouble(eh_editHistoryAddReached.getText()); // Reached can be zero

            // Validate that the goal is not zero
            if(newGoal <= 0){
                flashLabel(eh_editHistoryAddGoal);
                return;
            }
            
            // -- SAVE TO MEMORY --
            targetQuantityCard.addDateEntry(newDate, newReached, newGoal);
            
            // Update last modified before saving
            targetQuantityCard.setLastModified(LocalDateTime.now());
            
            // -- SAVE TO DISK --
            saveQuantityHabitFile(targetQuantityCard);
        }
        
        // If we are in a yesno card
        else{
            // Validate that the card does not already have an entry for this day
            if(targetYesNoCard.hasDateEntry(newDate)){
                flashLabel(eh_editHistoryAddDateWarning);
                return;
            }
            
            // Validating that one of the buttons is completed
            if(!eh_editHistoryAddCompleteButton.isSelected() && !eh_editHistoryAddNotCompleteButton.isSelected()){
                flashToggleButton(eh_editHistoryAddCompleteButton);
                flashToggleButton(eh_editHistoryAddNotCompleteButton);
                return;
            }
            // -- SAVE TO MEMORY --
            boolean newIsCompleted = eh_editHistoryAddCompleteButton.isSelected();
            targetYesNoCard.addDateEntry(newDate, newIsCompleted);
            
            // Update last modified before saving
            targetYesNoCard.setLastModified(LocalDateTime.now());
            
            // -- SAVE TO DISK --
            saveYesNoHabitFile(targetYesNoCard);
        }
        
        // Reload history table
        eh_editHistoryButtonMouseClicked(null);
    }//GEN-LAST:event_eh_editHistoryAddSaveButtonMouseClicked
    
    
    
    
    
    // =================================================================================================================================
    // ================== [ PROGRESS SCREEN FUNCTIONS ] ================================================================================
    // =================================================================================================================================
    
    LocalDate showingMonth = LocalDate.now();
    int currentHabitIndex = 0;
    ArrayList<String> allHabitNames = new ArrayList<>();
    // SET UP PROGRESS PANEL 
    private void setUpProgressScreen(){
        
        // If there are currently no habits, then hide everything and show the no habits panel
        boolean hasHabits = allQuantityCards.size() + allYesNoCards.size() > 0;
        p_tableLeftScrollButton.setVisible(hasHabits);
        p_tableRightScrollButton.setVisible(hasHabits);
        p_habitLeftScrollButton.setVisible(hasHabits);
        p_habitRightScrollButton.setVisible(hasHabits);
        p_habitName.setVisible(hasHabits);
        p_tableScrollPane.setVisible(hasHabits);
        p_monthAndYear.setVisible(hasHabits);
        p_resetMonthButton.setVisible(hasHabits);
        p_showAllHabitsText.setVisible(hasHabits); 
        p_showAllHabitsButton.setText("Filter By Habit"); 
        
        // Showing the no habits panel if there are no habits avaiable
        p_noHabitsPanel.setVisible(!hasHabits);
        
        // Returning if there is nothing left to do
        if(!hasHabits)
            return;
        
        // Setting up saving variables
        currentHabitIndex = -1;           // WE ARE STARTING THE CURRENT HABIT TO BE AT -1 TO SHOW ALL HABITS FIRST
        showingMonth = LocalDate.now();
        
        // Loading all habits into the allHabitsName array
        allHabitNames.clear();
        for(HabitCard_Quantity currCard : allQuantityCards)
            allHabitNames.add(currCard.getHabitName());
        for(HabitCard_YesNo currCard : allYesNoCards)
            allHabitNames.add(currCard.getHabitName());
        
        // Painting the left scroll as gray since we are starting on the left of the array
        p_habitLeftScrollButton.setBackground(Color.GRAY);
        
        
        // Load the table for the current habit that we have selected
        loadTable(); // <-- uses the currentHabitIndex 
        
        p_tableLeftScrollButton.revalidate();
        p_tableRightScrollButton.revalidate();
        p_habitRightScrollButton.revalidate();
        p_habitLeftScrollButton.revalidate();
        p_tableLeftScrollButton.repaint();
        p_tableRightScrollButton.repaint();
        p_habitRightScrollButton.repaint();
        p_habitLeftScrollButton.repaint();
    }
    
    // HELPER FUNCTION FOR LOAD TABLE: Count the total amount of habits that are for a day AND the amount of habits that were completed that day
    private int[] countTodaysProgress(LocalDate targetDay){
        int totalHabits = 0;
        int completedToday = 0;
        for (String currentHabitName : allHabitNames) {
            HabitCard_Quantity tempQuantity = getQuantityCard(currentHabitName);
            if(tempQuantity != null){
                if(tempQuantity.hasDateEntry(targetDay)){
                    totalHabits++;
                    QuantityEntry entry = tempQuantity.getDateEntry(targetDay);
                    if(entry.getCompleted())
                        completedToday++;
                }
            }
            else{
                HabitCard_YesNo tempYesNo = getYesNoCard(currentHabitName);
                if(tempYesNo.hasDateEntry(targetDay)){
                    totalHabits++;
                    if(tempYesNo.getDateEntry(targetDay))
                        completedToday++;
                }
            }
        }
         return new int[]{completedToday, totalHabits};
    }
    
    // LOAD TABLE
    private void loadTable(){
        // -- Set month/year label --
        YearMonth yearMonth = YearMonth.from(showingMonth);
        String monthTitle = yearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()) + " " + yearMonth.getYear();
        p_monthAndYear.setText(monthTitle);
        
        
        // (ONLY IN THE CASE THAT WE ARE SHOWING A SPECIFIC HABIT)
        HabitCard_Quantity quantityCard = null;
        HabitCard_YesNo yesNoCard = null;
        boolean isQuantity = false;
        boolean isYesNo = false;
        if(currentHabitIndex >= 0){
            // -- SET UP NAME --
            String habitName = allHabitNames.get(currentHabitIndex);
            p_habitName.setText(habitName); 
            
            // -- DETECT HABIT TYPE --
            quantityCard = getQuantityCard(habitName);
            yesNoCard = getYesNoCard(habitName);
            isQuantity = (quantityCard != null);
            isYesNo = (yesNoCard != null);
        }
        

        // -- Set up Table -- 
        String[] columns = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        // Populate calendar with habit data
        LocalDate firstOfMonth = yearMonth.atDay(1);
        int daysInMonth = yearMonth.lengthOfMonth();

        
        int startColumn = firstOfMonth.getDayOfWeek().getValue() % 7;

        int day = 1;
        while (day <= daysInMonth) {
            Object[] row = new Object[7];

            for (int col = startColumn; col < 7 && day <= daysInMonth; col++) {
                LocalDate date = yearMonth.atDay(day);
                String cellText = String.valueOf(day);
                
                // -- IF WE ARE SHOWING A SPECIFIC HABIT --
                if(currentHabitIndex >= 0){
                    // Quantity habit
                    if (isQuantity && quantityCard.hasDateEntry(date)) {
                        QuantityEntry entry = quantityCard.getDateEntry(date);

                        cellText += "\n" + entry.getReached() + " / " + entry.getGoal();
                        cellText += entry.getCompleted() ? "\n@COMPLETE@" : "\n@INCOMPLETE@"; // Hidden marker (creative huh) so that we can catch later
                    } 

                    // Yes/No habit
                    else if (isYesNo && yesNoCard.hasDateEntry(date)) {
                        boolean completed = yesNoCard.getDateEntry(date);
                        cellText += completed ? "\n@COMPLETE@" : "\n@INCOMPLETE@"; // Hidden marker (creative huh) so that we can catch later
                    }
                }
                
                // -- IF WE ARE SHOWING ALL HABITS --
                else{
                    // If the date is before OR is today, then we want to show the track
                    if(!date.isAfter(LocalDate.now())){
                        int[] results = countTodaysProgress(date);
                        cellText += "\n" + Integer.toString(results[0]) + "/" + Integer.toString(results[1]);
                        if(results[1] != 0)
                            cellText += (results[0] == results[1]) ? "\n@COMPLETE@" : "\n@INCOMPLETE@";
                    }
                }
                

                row[col] = cellText;
                day++;
            }

            model.addRow(row);
            startColumn = 0; // only first row may start mid-week
        }

        p_table.setModel(model);

        // -- Adjust row height -- 
        int rowCount = p_table.getRowCount();
        if (rowCount > 0) {
            int tableHeight = p_table.getParent().getHeight();
            int rowHeight = tableHeight / rowCount;
            p_table.setRowHeight(rowHeight);
        }
        
        
        
        
        // -- MULTI LINE RENDERING --
        
        Color backgroundColor   = new Color(225, 225, 225);
        Color incompleteColor   = new Color(255, 182, 193);
        Color completeColor     = new Color(144, 238, 144);
        //Color highlightDayColor = new Color(30, 144, 255);
        Color highlightDayColor = new Color(100, 100, 100);
        
        
        p_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

            // Saving label and formating text
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (value != null) {
                String raw = value.toString();

                // Remove hidden markers and replace (code newline) -> (html newline)
                String visibleText = raw
                        .replace("\n@COMPLETE@", "")
                        .replace("\n@INCOMPLETE@", "")
                        .replace("\n", "<br>");

                label.setText(
                    "<html><div style='text-align:center;'>"
                    + visibleText
                    + "</div></html>"
                );
            } else {
                label.setText("");  // <--- does nothing, just in case
            }
            

            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.CENTER);
            
            
            
            // -- PAINTING COLORS ---
            // --- Coloring background depending on if its ocmplete or incomplete ---
            if (value != null) {
                String text = value.toString().toUpperCase();
                
                
                if (text.contains("@INCOMPLETE@")) 
                    label.setBackground(incompleteColor); 
                else if (text.contains("@COMPLETE@")) 
                    label.setBackground(completeColor);
                else 
                    label.setBackground(backgroundColor); 
                
            } 
            else 
                label.setBackground(backgroundColor); 
            
            label.setOpaque(true); 
            
            // ---- HIGHLIGHT TODAY ----
            // Calculating which day this cell represents
            int startCol = firstOfMonth.getDayOfWeek().getValue() % 7;
            int dayNumber = row * 7 + column - startCol + 1;

            boolean isToday =
                dayNumber >= 1 &&
                dayNumber <= yearMonth.lengthOfMonth() &&
                yearMonth.equals(YearMonth.from(LocalDate.now())) &&
                dayNumber == LocalDate.now().getDayOfMonth();

            if (isToday) 
                label.setBorder(BorderFactory.createLineBorder(highlightDayColor, 3));
            else 
                label.setBorder(null);
            
            return label;
            }
        });
    }
    
    // TABLE SCROLL BUTTONS
    private void p_tableScrollButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_tableScrollButtonClicked
        // NO LONGER CATCHING -1 FOR THE CURRENT HABIT BECAUSE IT WILL FLAG TO THE LOAD TABLE TO SHOW ALL INSTEAD
        
        // Saving the panel target
        ScrollButton scrollButtonClicked = (ScrollButton) evt.getSource();
        
        // Show animation from the scroll button
        scrollButtonClicked.animatePress();
        
        // Add or Subtract months from todays date
        if(scrollButtonClicked == p_tableLeftScrollButton)
            showingMonth = showingMonth.minusMonths(1);
        else 
            showingMonth = showingMonth.plusMonths(1);
        
        // Loading the table which will now use the new showing month
        loadTable();
    }//GEN-LAST:event_p_tableScrollButtonClicked

    // HABIT SCROLL BUTTONS
    private void p_habitScrollButtonClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_habitScrollButtonClicked
        // Catching in the case that we are showing all habits, no need for these buttons
        if(currentHabitIndex == -1)
            return;
        
        // Saving the panel target
        ScrollButton scrollButtonClicked = (ScrollButton) evt.getSource();
        
        // Show animation from the scroll button
        scrollButtonClicked.animatePress();
        
        // Adding or subtracting from the currentHabitIndex and limited to be under 0
        int previousIndex = currentHabitIndex;
        int newIncrement = (scrollButtonClicked == p_habitLeftScrollButton) ? -1 : 1;
        currentHabitIndex += newIncrement;
        if(currentHabitIndex < 0)
            currentHabitIndex = 0;
        if(currentHabitIndex >= allHabitNames.size())
            currentHabitIndex = allHabitNames.size()-1;
        
        // Setting the background color if we have reached a background
        p_habitLeftScrollButton.setBackground(currentHabitIndex == 0 ? Color.GRAY : PRIMARY_COLOR);
        p_habitRightScrollButton.setBackground(currentHabitIndex == (allHabitNames.size()-1) ? Color.GRAY : PRIMARY_COLOR);
        
        // If index did not change, then we can just return
        if(previousIndex == currentHabitIndex)
            return;
        
        // Loading the table with the new index
        loadTable();
    }//GEN-LAST:event_p_habitScrollButtonClicked

    // BRING CALENDAR BACK TO THIS MONTH
    private void p_resetMonthButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_resetMonthButtonMouseClicked
        showingMonth = LocalDate.now();
        loadTable();
    }//GEN-LAST:event_p_resetMonthButtonMouseClicked

    // SHOW ALL HABITS BUTTON
    private void p_showAllHabitsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p_showAllHabitsButtonMouseClicked
        String buttonText = ((JButton) evt.getSource()).getText();
        
        // NOW SHOWING SPECIFIC HABIT
        if(buttonText.equals("Filter By Habit")){
            p_showAllHabitsButton.setText("Show All Habits"); // Changing states to show a specific habit
            p_showAllHabitsText.setVisible(false);            // Showing the cover for the habit choose and buttons
            
            // Hiding other panels under the cover
            p_habitName.setVisible(true);                  
            p_habitLeftScrollButton.setVisible(true);
            p_habitRightScrollButton.setVisible(true); 
            
            // Setting the current habit index to the first one again
            currentHabitIndex = 0;
            
            // Setting the colors of the scroll buttons at the starting index (Depending on the amount of habits we have)
            p_habitLeftScrollButton.setBackground(Color.GRAY);
            p_habitRightScrollButton.setBackground(allHabitNames.size() > 1 ? PRIMARY_COLOR : Color.GRAY);
                
        }
        
        // NOW SHOWING ALL HABITS
        else{
            p_showAllHabitsButton.setText("Filter By Habit");       // Changing state to show all habits
            p_showAllHabitsText.setVisible(true);
            
            // Showing the other panels that were under
            p_habitName.setVisible(false);                  
            p_habitLeftScrollButton.setVisible(false);
            p_habitRightScrollButton.setVisible(false); 
            
            // Setting the current habit index to the flag that will get caught in the loadTable
            currentHabitIndex = -1;
        }
        
        // LOAD HABIT TABLE AGAIN 
        loadTable();
    }//GEN-LAST:event_p_showAllHabitsButtonMouseClicked



    
    
    
    




    

    
    
   
    
    
    


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
    
    private void flashLabel(JLabel target){
        if(target.getBackground() == Color.RED)
            return;
        Color previousColor = target.getForeground();
        target.setForeground(Color.RED);
        Timer flashTimer = new Timer(1000,e->{
            target.setForeground(previousColor);
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
        double factor = 0.95; // USE THIS FACTOR 0=darker | 1=lighter
        
        int r = Math.max((int)(color.getRed() * factor), 0);
        int g = Math.max((int)(color.getGreen() * factor), 0);
        int b = Math.max((int)(color.getBlue() * factor), 0);
        
        return new Color(r, g, b);
    }
    
    // Helper function to return a target quantity card
    private HabitCard_Quantity getQuantityCard(String target){
        for(HabitCard_Quantity currCard : allQuantityCards){
            if(currCard.getHabitName().equals(target))
                return currCard;
        }
        return null;
    }
    
    // Helper function to return a target yesno card
    private HabitCard_YesNo getYesNoCard(String target){
        for(HabitCard_YesNo currCard : allYesNoCards)
            if(currCard.getHabitName().equals(target))
                return currCard;
         return null;
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
    private javax.swing.JPanel addHabitButton;
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
    private javax.swing.JPanel editHabit;
    private javax.swing.JPanel editHabitButton;
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
    private javax.swing.JButton eh_backButton;
    private javax.swing.JPanel eh_bottomButtonsPanel;
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
    private javax.swing.JButton eh_editHIstoryIncreaseButton;
    private javax.swing.JPanel eh_editHabitPanel;
    private javax.swing.JPanel eh_editHabitSummaryPanel;
    private javax.swing.JLabel eh_editHabitText1;
    private javax.swing.JLabel eh_editHabitText2;
    private javax.swing.JLabel eh_editHabitText3;
    private javax.swing.JLabel eh_editHabitText4;
    private javax.swing.JLabel eh_editHabitText5;
    private javax.swing.JLabel eh_editHabitText6;
    private javax.swing.JButton eh_editHistoryAddButton;
    private javax.swing.JButton eh_editHistoryAddCancelButton;
    private javax.swing.JToggleButton eh_editHistoryAddCompleteButton;
    private javax.swing.JLabel eh_editHistoryAddCurrentGoal;
    private javax.swing.JLabel eh_editHistoryAddDateEntryLabels;
    private javax.swing.JLabel eh_editHistoryAddDateWarning;
    private javax.swing.JSpinner eh_editHistoryAddDay;
    private javax.swing.JLabel eh_editHistoryAddGoal;
    private javax.swing.JButton eh_editHistoryAddGoalDecreaseButton;
    private javax.swing.JButton eh_editHistoryAddGoalIncreaseButton;
    private javax.swing.JLabel eh_editHistoryAddIncrement;
    private javax.swing.JSpinner eh_editHistoryAddMonth;
    private javax.swing.JToggleButton eh_editHistoryAddNotCompleteButton;
    private javax.swing.JPanel eh_editHistoryAddPanel;
    private javax.swing.JPanel eh_editHistoryAddQuantityPanel;
    private javax.swing.JLabel eh_editHistoryAddReached;
    private javax.swing.JButton eh_editHistoryAddReachedDecreaseButton;
    private javax.swing.JButton eh_editHistoryAddReachedIncreaseButton;
    private javax.swing.JButton eh_editHistoryAddSaveButton;
    private javax.swing.JLabel eh_editHistoryAddText1;
    private javax.swing.JLabel eh_editHistoryAddText2;
    private javax.swing.JLabel eh_editHistoryAddText3;
    private javax.swing.JLabel eh_editHistoryAddText4;
    private javax.swing.JSpinner eh_editHistoryAddYear;
    private javax.swing.JPanel eh_editHistoryAddYesNoPanel;
    private javax.swing.JButton eh_editHistoryBackButton;
    private javax.swing.JButton eh_editHistoryButton;
    private javax.swing.JToggleButton eh_editHistoryCompleteButton;
    private javax.swing.JButton eh_editHistoryDeleteButton;
    private javax.swing.JButton eh_editHistoryDeleteCanceButton;
    private javax.swing.JButton eh_editHistoryDeleteConfirmButton;
    private javax.swing.JLabel eh_editHistoryDeleteDate;
    private javax.swing.JPanel eh_editHistoryDeletePanel;
    private javax.swing.JLabel eh_editHistoryDeleteText1;
    private javax.swing.JLabel eh_editHistoryHabitName;
    private javax.swing.JToggleButton eh_editHistoryNotCompleteButton;
    private javax.swing.JPanel eh_editHistoryPanel;
    private javax.swing.JLabel eh_editHistoryQuantity;
    private javax.swing.JPanel eh_editHistoryQuantityPanel;
    private javax.swing.JButton eh_editHistoryScrollDownButton;
    private javax.swing.JButton eh_editHistoryScrollUpButton;
    private javax.swing.JLabel eh_editHistoryText1;
    private javax.swing.JPanel eh_editHistoryYesNoPanel;
    private javax.swing.JButton eh_editHistorydDecreaseButton;
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
    private javax.swing.JLabel eh_goal;
    private javax.swing.JTable eh_historyTable;
    private javax.swing.JLabel eh_increment;
    private javax.swing.JLabel eh_name;
    private javax.swing.JTextField eh_nameInput;
    private javax.swing.JLabel eh_noHabitsPanel;
    private javax.swing.JButton eh_quantityDecrease;
    private javax.swing.JButton eh_quantityIncrease;
    private javax.swing.JPanel eh_scrollDownPanel;
    private javax.swing.JScrollPane eh_scrollPane;
    private javax.swing.JPanel eh_scrollUpPanel;
    private javax.swing.JLabel eh_text3;
    private javax.swing.JLabel eh_title;
    private javax.swing.JScrollPane ehist_historyScrollPane;
    private javax.swing.JTextField h_addHabitName;
    private javax.swing.JLabel h_date;
    private javax.swing.JPanel h_habitPanel;
    private javax.swing.JLabel h_noHabitsPanel;
    private javax.swing.JLabel h_savingFilesText;
    private javax.swing.JPanel h_scrollDownPanel;
    private javax.swing.JScrollPane h_scrollPane;
    private javax.swing.JPanel h_scrollUpPanel;
    private javax.swing.JPanel home;
    private javax.swing.JPanel homeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
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
    private javax.swing.JButton key28;
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
    public javax.swing.JPanel overallProgressDayCircle;
    public javax.swing.JPanel overallProgressMonthCircle;
    public javax.swing.JLabel overallProgressNoHabitsText;
    public javax.swing.JLabel overallProgressNoStreaksText;
    public javax.swing.JPanel overallProgressStreakBarGraph;
    public javax.swing.JLabel overallProgressText1;
    public javax.swing.JLabel overallProgressText2;
    private javax.swing.JLabel overallProgressText3;
    private javax.swing.JLabel overallProgressText4;
    public javax.swing.JPanel overallProgressWeekCircle;
    public javax.swing.JPanel overallProgressYearBarGraph;
    private javax.swing.JPanel p_habitLeftScrollButton;
    private javax.swing.JLabel p_habitName;
    private javax.swing.JPanel p_habitRightScrollButton;
    private javax.swing.JLabel p_monthAndYear;
    private javax.swing.JLabel p_noHabitsPanel;
    private javax.swing.JLabel p_progressTitle;
    private javax.swing.JButton p_resetMonthButton;
    private javax.swing.JButton p_showAllHabitsButton;
    private javax.swing.JLabel p_showAllHabitsText;
    private javax.swing.JTable p_table;
    private javax.swing.JPanel p_tableLeftScrollButton;
    private javax.swing.JPanel p_tableRightScrollButton;
    private javax.swing.JScrollPane p_tableScrollPane;
    private javax.swing.JPanel progress;
    private javax.swing.JPanel progressButton;
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
    private javax.swing.JPanel s_colorsPanel;
    private javax.swing.JLabel s_colorsTitle;
    private javax.swing.JPanel s_connectionPanel;
    private javax.swing.JPanel s_customScreensaverPanel;
    private javax.swing.JPanel s_theme1;
    private javax.swing.JPanel s_theme2;
    private javax.swing.JPanel s_theme3;
    private javax.swing.JPanel s_theme4;
    private javax.swing.JLabel s_title;
    private javax.swing.JButton s_turnOffAwayFromScreenButton;
    public javax.swing.JLabel screensaverDateText;
    public javax.swing.JLabel screensaverFishTankBackground;
    public javax.swing.JPanel screensaverOverallProgress;
    public javax.swing.JPanel screensaverPanel;
    public javax.swing.JLabel screensaverTimeText;
    public javax.swing.JPanel screensaverTodaysProgress;
    public javax.swing.JPanel screensaverTodaysProgressDisplay;
    private javax.swing.JLabel screensaverTodaysProgressTitle;
    private javax.swing.JPanel settings;
    private javax.swing.JPanel settingsButton;
    public javax.swing.JLabel skylinePanel1;
    public javax.swing.JLabel skylinePanel2;
    public javax.swing.JLabel skylinePanel3;
    // End of variables declaration//GEN-END:variables
}
