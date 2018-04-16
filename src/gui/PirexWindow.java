package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import datastore.Datastore;

/**
 * Top level window class.
 * 
 * @author shimpjn
 *
 */
public class PirexWindow extends JFrame
{
  private static final long serialVersionUID = 1L;
  
  private Datastore datastore;
  
  private JTabbedPane tabbedPane;
  private SearchPanel searchPanel;
  private LoadPanel loadPanel;
  private SummarizePanel summarizePanel;
  
  /**
   * Constructor.
   * 
   * @param data Datastore to use
   * Creates a tabbed interface for the main controls.
   */
  public PirexWindow(Datastore data)
  {
    this.datastore      = data;
    this.tabbedPane     = new JTabbedPane();
    this.searchPanel    = new SearchPanel();
    this.loadPanel      = new LoadPanel();
    this.summarizePanel = new SummarizePanel();
    
    tabbedPane.addTab("Search for Paragraphs", this.searchPanel);
    tabbedPane.addTab("Load Books", this.loadPanel);
    tabbedPane.addTab("Summarize Datastore", this.summarizePanel);
    
    this.setLayout(new BorderLayout());
    this.add(tabbedPane, BorderLayout.CENTER);
    
    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    this.setTitle("Pirex by group C");
    this.setPreferredSize(new Dimension(960, 540));
    this.pack();
    centerWindow();
    this.setVisible(true);
    
    summarizePanel.summarize();
  }
  
  /**
   * Getter for datastore.
   * @return shared datastore
   */
  public Datastore getDatastore()
  {
    return this.datastore;
  }
  
  /**
   * Setter for datastore.
   * @param data shared datastore
   */
  public  void setDatastore(Datastore data)
  {
    this.datastore = data;
  }
  
  /**
   * Updates the Summary panel when a new book is added.
   */
  public void updateSummary()
  {
    this.summarizePanel.summarize();
  }
  /**
   * Centers the JFrame.
   */
  private void centerWindow() 
  {
    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
    int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
    this.setLocation(x, y);
  }
}
