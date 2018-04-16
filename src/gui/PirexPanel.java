package gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import datastore.Datastore;

/**
 * Required methods for all panels in the pirex application.
 * 
 * @author shimpjn
 */
public abstract class PirexPanel extends JPanel
{
  private static final long serialVersionUID = 1L;

  /**
   * Return the application's datastore.
   * @return shared datastore
   */
  public Datastore getDatastore()
  {
    PirexWindow mainWindow = (PirexWindow) SwingUtilities.getRoot(this);
    
    return mainWindow.getDatastore();
  }
  
  /**
   * Updates the summary panel.
   */
  public void updateSummary()
  {
    PirexWindow mainWindow = (PirexWindow) SwingUtilities.getRoot(this);
    
    mainWindow.updateSummary();
  }
}
