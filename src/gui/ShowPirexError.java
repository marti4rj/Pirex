package gui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Ryan
 * Handles un-Recoverable Errors
 */
public class ShowPirexError
{
  /**
   * @param title title of error pop up
   * @param errorMessage Error message within pop up
   * @param e Exception causing un-Recoverable error
   * @param exitValue value to exit with
   */
  public static void showError(String title, String errorMessage, Exception e,
      int exitValue)
  {
    // Create Pop up window for error
    JFrame frame = new JFrame();
    JOptionPane.showMessageDialog(frame, errorMessage,
        title, JOptionPane.WARNING_MESSAGE);
    
    // Dump information about Error
    e.printStackTrace();
    
    System.exit(exitValue);
  }
}
