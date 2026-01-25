
package habitpanel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class ScreensaverManager {
    // General Screensaver Variables
    GUI_Window mainGUI = null;
    private int savedScreensaver = 1; // Current Screensaver: 1 -> Skyline Background  ------------> ALSO A VARIABLE THAT CAN BE SAVED IN VARIABLE FILE
    
    // Skyline Screensaver extras 
    Timer screensaverTimer = null;
    private int skylineMinLocation;
    private int skylinePanelWidth;
    
    
    
     // CONSTRUCTOR : --------------------------------------------------------------------------------------------------------------------------------------
    public void setUp(GUI_Window mainGUIinput, JPanel backgroundInput){
        // Setting up main gui window
        mainGUI = mainGUIinput;
        
        // Setting up skyline variablaes
        skylineMinLocation = -mainGUI.skylinePanel1.getWidth() - 10;
        skylinePanelWidth = mainGUI.skylinePanel1.getWidth();
        
    }
    
    // -------------------------------------------------------------------------------------------------------------------------------------------------------
    
    
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
        stopClocks();
        mainGUI.screensaverTimeText.setVisible(false);
        mainGUI.screensaverDateText.setVisible(false);
        
        mainGUI.skylinePanel1.setVisible(false);
        mainGUI.skylinePanel2.setVisible(false);
        mainGUI.skylinePanel3.setVisible(false);
        
        mainGUI.screensaverFishTankBackground.setVisible(false);
        
        mainGUI.screensaverTodaysProgress.setVisible(false);
        
        mainGUI.screensaverOverallProgress.setVisible(false);
        
        
        switch (savedScreensaver) {
            // Skyline Screensaver
            case 1 -> {
                mainGUI.skylinePanel1.setVisible(true);
                mainGUI.skylinePanel2.setVisible(true);
                mainGUI.skylinePanel3.setVisible(true);
                mainGUI.screensaverTimeText.setVisible(true);
                mainGUI.screensaverDateText.setVisible(true);
                startSkylineClock();
            }
            
            // Fish tank screensaver
            case 2 -> {
                mainGUI.screensaverFishTankBackground.setVisible(true);
            }
            
            // Blank Black Screensaver
            case 3 -> {
                
            }
            
            // Todays Progress Screensaver
            case 4 -> {
                setUpTodaysProgess();
                mainGUI.screensaverTodaysProgress.setVisible(true);
            }
            
            // Overall Progress Screensaver
            case 5 -> {
                mainGUI.screensaverOverallProgress.setVisible(true);
                setUpOverallProgessPanel();
            }
            
            // Simple Clock and Date Screensaver
            case 6 -> {
                // Centering the time text and then centering the date text under the time text
                mainGUI.screensaverTimeText.setLocation(mainGUI.screensaverPanel.getWidth()/2 - mainGUI.screensaverTimeText.getWidth()/2, 
                                                        mainGUI.screensaverPanel.getHeight()/2-mainGUI.screensaverTimeText.getHeight());
                mainGUI.screensaverDateText.setLocation(mainGUI.screensaverTimeText.getX() + (mainGUI.screensaverTimeText.getWidth() - mainGUI.screensaverDateText.getWidth())/2, 
                                                        mainGUI.screensaverTimeText.getY() + mainGUI.screensaverTimeText.getHeight());
                mainGUI.screensaverTimeText.setVisible(true);
                mainGUI.screensaverDateText.setVisible(true);
            }
            
            
        }
    }
    
    // Safely stopping the clock
    public void stopClocks(){
        if(screensaverTimer != null){        // Making sure there is an object here
            if(screensaverTimer.isRunning()) // If its running -> stop
                screensaverTimer.stop();
            screensaverTimer = null;         
        }
        
        ((StreaksBarGraph)mainGUI.overallProgressStreakBarGraph).stopClock();
    }
    
    
    
    
    // SKYLINE SCREENSAVER ------------------------------------------------------------------------------------------------------------------------------
    // Set up and start the clock
    private void startSkylineClock(){
        // Moving panels to position 
        mainGUI.skylinePanel1.setLocation(0,0);
        mainGUI.skylinePanel2.setLocation(1040,0);
        mainGUI.skylinePanel3.setLocation(2060,0);
        mainGUI.screensaverTimeText.setLocation(320,60);
        mainGUI.screensaverDateText.setLocation(320,120);
        
        screensaverTimer = new Timer(30, e->{
            // Move panels to their new location
            mainGUI.skylinePanel1.setLocation(mainGUI.skylinePanel1.getX()-1, 0);
            mainGUI.skylinePanel2.setLocation(mainGUI.skylinePanel2.getX()-1, 0);
            mainGUI.skylinePanel3.setLocation(mainGUI.skylinePanel3.getX()-1, 0);
            
            // Checks if any panel has reached max left location (there should only be one that reaches this, so no need to check all three
            if(mainGUI.skylinePanel1.getX() <= skylineMinLocation){
                mainGUI.skylinePanel1.setLocation(mainGUI.skylinePanel3.getX()+skylinePanelWidth,0);
            }
            else{
                if(mainGUI.skylinePanel2.getX() <= skylineMinLocation){
                    mainGUI.skylinePanel2.setLocation(mainGUI.skylinePanel1.getX()+skylinePanelWidth,0);
                }
                else{
                    if(mainGUI.skylinePanel3.getX() <= skylineMinLocation){
                        mainGUI.skylinePanel3.setLocation(mainGUI.skylinePanel2.getX()+skylinePanelWidth,0);
                    }
                }
            }
        });
        
        screensaverTimer.start();
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------

    
    
    
    
    // TODAYS PROGRESS SCREENSAVER ------------------------------------------------------------------------------------------------------------------------------
    
    // Helper function: adds a habit name to the todaysProgressDisplay
    private void addHabitByName(String addingHabitName){
        int textSize = 17;
        JPanel newHabitPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                
                int stroke = 2;
                int dotD = 15;
                int dotX = getWidth()/2 - 175;
                int dotY = getHeight()/2-dotD/2;
                int lineXStart = dotX + 50;
                int lineLenght = 250;
                int lineY = getHeight()/2 + textSize;
                
                // Fill Dot
                g2.setColor(mainGUI.screensaverTodaysProgress.getForeground()); 
                g2.fillOval(dotX, dotY, dotD, dotD);
                
                // Dot Border (3D)
                g2.setColor(Color.WHITE);
                g2.drawOval(dotX, dotY, dotD, dotD); // first upper draw for white
                
                
                g2.setColor(Color.GRAY);             // second lower draw for gray
                g2.setStroke(new BasicStroke(stroke));
                g2.drawArc(dotX, dotY, dotD, dotD, 180, 180);
                
                g2.setColor(Color.LIGHT_GRAY);       // third middle draw for light gray
                g2.setStroke(new BasicStroke(stroke-1));
                g2.drawArc(dotX, dotY, dotD, dotD, 170, 20);
                g2.drawArc(dotX, dotY, dotD, dotD, 350, 20);
                
                
                // Line  --> essetianlly the fill of the rectangle
                g2.setStroke(new BasicStroke(stroke));
                g2.setColor(mainGUI.screensaverTodaysProgress.getForeground()); 
                g2.drawLine(lineXStart, lineY, lineXStart+lineLenght, lineY);
                
                // 3D Effect of line
                g2.setStroke(new BasicStroke(stroke-1));
                g2.setColor(Color.WHITE);                                       // first draw upper section
                g2.drawLine(lineXStart, lineY-stroke-1, lineXStart+lineLenght-stroke, lineY-stroke-1);
                
                g2.setColor(Color.GRAY);                                        // second draw lower section
                g2.drawLine(lineXStart+stroke, lineY+stroke-1, lineXStart+lineLenght, lineY+stroke-1);
                
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(lineXStart, lineY-stroke-1,lineXStart+stroke, lineY+stroke-1);
                g2.drawLine(lineXStart+lineLenght-stroke, lineY-stroke-1, lineXStart+lineLenght, lineY+stroke-1);
                
                
                
                g2.dispose();
            }
        };

        newHabitPanel.setOpaque(false);
        newHabitPanel.setLayout(new BorderLayout());
        
        
        JLabel newHabitText = new JLabel(addingHabitName, SwingConstants.CENTER);
        newHabitText.setFont(new Font("Segoe UI", Font.BOLD, textSize));
        newHabitText.setForeground(mainGUI.screensaverTodaysProgressDisplay.getForeground());

        newHabitPanel.add(newHabitText, BorderLayout.CENTER);
        mainGUI.screensaverTodaysProgressDisplay.add(newHabitPanel);
        mainGUI.screensaverTodaysProgressDisplay.revalidate();
        mainGUI.screensaverTodaysProgressDisplay.repaint();
    }
    
    // Set up todays progress panel
    private void setUpTodaysProgess(){
        // Removing previous habits
        mainGUI.screensaverTodaysProgressDisplay.removeAll();
        
        // Getting information on todays day
        String todaysWeekday = mainGUI.getWeekday();
        LocalDate todayDate = LocalDate.now();
        
        int itemCount = 0;
        // Loading habits that are for today and have not been completed yet
        for(HabitCard_Quantity currCard : mainGUI.allQuantityCards){
            if(currCard.isForToday(todaysWeekday) && !currCard.getCompleted(todayDate) && itemCount <= 14){ 
                addHabitByName(currCard.getHabitName()); 
                itemCount++;
            }
        }
            
        for(HabitCard_YesNo currCard : mainGUI.allYesNoCards){
            if(currCard.isForToday(todaysWeekday) && !currCard.getCompleted(todayDate) && itemCount <= 14){ 
                addHabitByName(currCard.getHabitName()); 
                itemCount++;
            }
        }
        
        if(mainGUI.allQuantityCards.size() + mainGUI.allYesNoCards.size() == 0){
            JLabel completedHabitsText = new JLabel("There Are No Habits Added");
            completedHabitsText.setFont(new Font("Segoe UI",1,18));
            completedHabitsText.setForeground(mainGUI.TEXT_COLOR);
            completedHabitsText.setHorizontalAlignment(SwingConstants.CENTER);
            mainGUI.screensaverTodaysProgressDisplay.add(completedHabitsText);
            
            // We can now continue because the item count will be 1, not zero!
            itemCount = 1;
        }
        
        if(itemCount == 0){
            JLabel completedHabitsText = new JLabel("CONGRATULATIONS! All Habits Have Been Completed!");
            completedHabitsText.setFont(new Font("Segoe UI",1,18));
            completedHabitsText.setForeground(mainGUI.TEXT_COLOR);
            completedHabitsText.setHorizontalAlignment(SwingConstants.CENTER);
            mainGUI.screensaverTodaysProgressDisplay.add(completedHabitsText);
            
            // We can now continue because the item count will be 1, not zero!
            itemCount = 1;
        }
        
        // Resizing all the habit names after we know how many there are
        int itemHeight = mainGUI.screensaverTodaysProgressDisplay.getHeight() / itemCount - 3;
        int newWidth = (mainGUI.screensaverTodaysProgressDisplay.getWidth() - 10) / (itemCount > 7 ? 2 : 1); // If we have more than 7 habits, then we need to make it into two columns by dividing witdh by 2 
        int newHeight = itemHeight * (itemCount > 7 ? 2 : 1);                             // If we have more than 7 habits, then we need to multiply the height by 2 to acomodate for the two columns
        for(int i = 0; i < mainGUI.screensaverTodaysProgressDisplay.getComponentCount(); i++){
            mainGUI.screensaverTodaysProgressDisplay.getComponent(i).setPreferredSize(new Dimension(newWidth, newHeight));   
        }
    }
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------

    
    
    
    
    // OVERALL PROGRESS SCREENSAVER ------------------------------------------------------------------------------------------------------------------------------
    // Sets up overall progress panel
    private void setUpOverallProgessPanel(){
        // Catch when we do not have any habits and SHOW/HIDE panels depedning on that as well
        boolean hasHabits = (mainGUI.allQuantityCards.size() + mainGUI.allYesNoCards.size()) != 0;
        mainGUI.overallProgressDayCircle.setVisible(hasHabits);
        mainGUI.overallProgressWeekCircle.setVisible(hasHabits);
        mainGUI.overallProgressMonthCircle.setVisible(hasHabits);
        mainGUI.overallProgressYearBarGraph.setVisible(hasHabits);
        mainGUI.overallProgressStreakBarGraph.setVisible(hasHabits);
        mainGUI.overallProgressNoHabitsText.setVisible(!hasHabits);
        if(!hasHabits)
            return;
        
        int dayHabitCount = 0;
        int weekHabitCount = 0;
        int monthHabitCount = 0;
        int dayReachedCount = 0;
        int weekReachedCount = 0;
        int monthReachedCount = 0;
        
        

        LocalDate today = LocalDate.now();
        
        
        

        // -- DATA FOR THE GAUGES (DAY/WEEK/MONTH TRACKER) **AND** THE YEAR BAR GRAPH DATA --
        // Week range (Sun -> Today)
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

        // Month range (1 - > Today)
        YearMonth ym = YearMonth.from(today);
        LocalDate startOfMonth = ym.atDay(1);

        // Setting up the year bar graph data (init to -1 at first)
        int dayCount = ym.lengthOfMonth();
        double[] monthProgressArray = new double[dayCount];  
        for (int i = 0; i < dayCount; i++) 
            monthProgressArray[i] = -1;
        
        // Loop through all valid days this month (no future days though)
        for (LocalDate date = startOfMonth; !date.isAfter(today); date = date.plusDays(1)) {
            boolean isToday = date.equals(today);
            boolean isThisWeek = !date.isBefore(startOfWeek);
            int totalHabitsForDay = 0;
            int completedHabitsForDay = 0;

            // ----- Quantity Habits -----
            for (HabitCard_Quantity currCard : mainGUI.allQuantityCards) {
                if (!currCard.hasDateEntry(date)) 
                    continue;

                // MONTH
                monthHabitCount++;
                if (currCard.getDateEntry(date).getCompleted()) 
                    monthReachedCount++;

                // WEEK
                if (isThisWeek) {
                    weekHabitCount++;
                    if (currCard.getDateEntry(date).getCompleted()) {
                        weekReachedCount++;
                    }
                }

                // DAY
                if (isToday) {
                    dayHabitCount++;
                    if (currCard.getDateEntry(date).getCompleted()) {
                        dayReachedCount++;
                    }
                }
                
                // -- DATA FOR YEAR BAR GRAPH -- 
                totalHabitsForDay++;
                if (currCard.getDateEntry(date).getCompleted()) 
                    completedHabitsForDay++;
            }

            // ----- Yes / No Habits -----
            for (HabitCard_YesNo currCard : mainGUI.allYesNoCards) {
                if (!currCard.hasDateEntry(date)) 
                    continue;

                // MONTH
                monthHabitCount++;
                if (currCard.getCompleted(date)) 
                    monthReachedCount++;
                
                // WEEK
                if (isThisWeek) {
                    weekHabitCount++;
                    if (currCard.getCompleted(date)) {
                        weekReachedCount++;
                    }
                }
                // DAY
                if (isToday) {
                    dayHabitCount++;
                    if (currCard.getCompleted(date)) {
                        dayReachedCount++;
                    }
                }
                
                 // -- DATA FOR YEAR BAR GRAPH -- 
                totalHabitsForDay++;
                if (currCard.getCompleted(date)) 
                    completedHabitsForDay++;
            }
            
            // -- DATA FOR YEAR BAR GRAPH --
            // Finding out index and saving into month progress array
            int dayIndex = date.getDayOfMonth() - 1;
            if (totalHabitsForDay == 0) 
                monthProgressArray[dayIndex] = -1; // gray full bar (will be triggered by the -1)
            else 
                monthProgressArray[dayIndex] = (double) completedHabitsForDay / totalHabitsForDay;
        }
        
        
        
        // -- DATA FOR THE STREAKS --
        LocalDate firstOfMonth = today.withDayOfMonth(1);
        Map<String, Integer> habitStreaks = new HashMap<>();      // <---- Saving this outside of this function so that the timer can use it if needed
        for (HabitCard_Quantity currCard : mainGUI.allQuantityCards) {
            int streak = 0;
            for (LocalDate d = today; !d.isBefore(firstOfMonth); d = d.minusDays(1)) {
                if (currCard.hasDateEntry(d)){
                    if (!currCard.getDateEntry(d).getCompleted())
                        break;
                    streak++; // else, then add to the streak
                }
            }
            if(streak > 0)
                habitStreaks.put(currCard.getHabitName(), streak);
        }
        
        
        for (HabitCard_YesNo currCard : mainGUI.allYesNoCards) {
            int streak = 0;
            for (LocalDate d = today; !d.isBefore(firstOfMonth);  d = d.minusDays(1)) {
                if (currCard.hasDateEntry(d)){
                    if (!currCard.getCompleted(d))
                        break;    
                    streak++;  // else, then add to the streak
                }
                    
            }
            if(streak > 0)
                habitStreaks.put(currCard.getHabitName(), streak);
        }
        
        
        // Sending information into the ProgressCircle Panel
        ((ProgressCircle)mainGUI.overallProgressDayCircle).setReached(dayReachedCount);
        ((ProgressCircle)mainGUI.overallProgressDayCircle).setMax(dayHabitCount);
        ((ProgressCircle)mainGUI.overallProgressWeekCircle).setReached(weekReachedCount);
        ((ProgressCircle)mainGUI.overallProgressWeekCircle).setMax(weekHabitCount);
        ((ProgressCircle)mainGUI.overallProgressMonthCircle).setReached(monthReachedCount);
        ((ProgressCircle)mainGUI.overallProgressMonthCircle).setMax(monthHabitCount);
        
        // Sending information to YearBarGraph panel 
        ((YearBarGraph) mainGUI.overallProgressYearBarGraph).updateData(monthProgressArray);
        
        // Sending information to SreakBarGraph panel (if there is any)
        if(!habitStreaks.isEmpty()){
            mainGUI.overallProgressStreakBarGraph.setVisible(true);
            mainGUI.overallProgressNoStreaksText.setVisible(false);
            List<Map.Entry<String, Integer>> entries = new ArrayList<>(habitStreaks.entrySet()); // Making list of this map
            entries.sort((a, b) -> Integer.compare(b.getValue(), a.getValue()));                 // Sorting the list 
            ((StreaksBarGraph) mainGUI.overallProgressStreakBarGraph).setStreaks(entries);       // Setting the data for the panel to repaint itself
        }
        else{
            mainGUI.overallProgressStreakBarGraph.setVisible(false);
            mainGUI.overallProgressNoStreaksText.setVisible(true);
        }
    }
    
    // -----------------------------------------------------------------------------------------------------------------------------------------------------

}
