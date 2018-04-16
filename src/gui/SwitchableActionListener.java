package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Switchable Action Listener.
 * 
 * @author shimpjn
 */
public abstract class SwitchableActionListener implements ActionListener
{
  private boolean active = true;

  /**
   * Set switch status.
   * @param status switch on/off
   */
  public void setActive(boolean status)
  {
    active = status;
  }
  
  @Override
  public void actionPerformed(ActionEvent event)
  {
    if (active)
      this.respondToEvent(event);
  }
  
  /**
   * The action to be performed with the event is triggered.
   * @param event action event object
   */
  public abstract void respondToEvent(ActionEvent event);
}
