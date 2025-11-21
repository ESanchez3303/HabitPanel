
package habitpanel;
public class HabitPanel {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new GUI_Window().setVisible(true);
        }
    });
    }
    
}
