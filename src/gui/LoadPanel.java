package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import datastore.Book;
import datastore.Datastore;
import datastore.GutenbergStrategy;
import datastore.ParseStrategy;
import datastore.PlainTextStrategy;

/**
 * LoadPanel for admins.
 * 
 * @author mitchejp
 */
public class LoadPanel extends PirexPanel
{
  private static final long serialVersionUID = 1L;
  
  private final String gutenberg = "Project Gutenberg File";
  private final String plainText = "Plain Text File";

  private JPanel loadTextFilePanel;
  private JPanel selectFileTypePanel;
  private JPanel titleAuthorPanel;
  private JPanel processPanel;
  private JPanel summaryPanel;

  private JLabel textFileLabel;
  private JLabel textFileTypeLabel;
  private JLabel titleLabel;
  private JLabel authorLabel;

  private JTextField fileField;
  private JTextField authorField;
  private JTextField titleField;

  private JComboBox<String> fileTypeComboBox;

  private JButton browseButton;
  private JButton processButton;

  private JTextArea processSummary;

  private final JFileChooser fileChooser = new JFileChooser();

  /**
   * Instantiates LoadPanel.
   */
  public LoadPanel()
  {
    // set layout
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    // create labels
    textFileLabel = new JLabel("Text File:");
    textFileTypeLabel = new JLabel("Text File Type:");
    titleLabel = new JLabel("Title:");
    authorLabel = new JLabel("Author Label:");

    // create text fields
    titleField = new JTextField();
    titleField
        .setMaximumSize(new Dimension(Integer.MAX_VALUE, titleField.getPreferredSize().height));
    authorField = new JTextField();
    authorField
        .setMaximumSize(new Dimension(Integer.MAX_VALUE, authorField.getPreferredSize().height));
    fileField = new JTextField();
    fileField.setMaximumSize(new Dimension(Integer.MAX_VALUE, fileField.getPreferredSize().height));
    fileField.addActionListener(new Browse());
    fileField.setText("");

    // create combo box
    fileTypeComboBox = new JComboBox<String>();
    fileTypeComboBox.setMaximumSize(
        new Dimension(Integer.MAX_VALUE, fileTypeComboBox.getPreferredSize().height));
    fileTypeComboBox.setBackground(Color.white);
    fileTypeComboBox.addItem(gutenberg);
    fileTypeComboBox.addItem(plainText);
    fileTypeComboBox.setSelectedIndex(-1);  // Make original selection empty.
    fileTypeComboBox.addActionListener(new FileTypeSelection());
    
    // create buttons
    browseButton = new JButton("Browse");
    browseButton.addActionListener(new Browse());
    processButton = new JButton("Process");
    processButton.addActionListener(new Process());
    processButton.setEnabled(false);

    // create summary
    processSummary = new JTextArea();
    processSummary.setEditable(false);

    // assemble the layout

    // load texts file panel
    loadTextFilePanel = new JPanel();
    loadTextFilePanel.setBackground(Color.white);
    loadTextFilePanel.setLayout(new BoxLayout(loadTextFilePanel, BoxLayout.X_AXIS));
    loadTextFilePanel.add(textFileLabel);
    loadTextFilePanel.add(Box.createRigidArea(new Dimension(10, 0)));
    loadTextFilePanel.add(fileField);
    loadTextFilePanel.add(Box.createRigidArea(new Dimension(10, 0)));
    loadTextFilePanel.add(browseButton);

    // select file type panel
    selectFileTypePanel = new JPanel();
    selectFileTypePanel.setBackground(Color.white);
    selectFileTypePanel.setLayout(new BoxLayout(selectFileTypePanel, BoxLayout.X_AXIS));
    selectFileTypePanel.add(textFileTypeLabel);
    selectFileTypePanel.add(Box.createRigidArea(new Dimension(10, 0)));
    selectFileTypePanel.add(fileTypeComboBox);

    // edit title/author panel
    titleAuthorPanel = new JPanel();
    titleAuthorPanel.setBackground(Color.white);
    titleAuthorPanel.setLayout(new BoxLayout(titleAuthorPanel, BoxLayout.X_AXIS));
    titleAuthorPanel.add(titleLabel);
    titleAuthorPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    titleAuthorPanel.add(titleField);
    titleAuthorPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    titleAuthorPanel.add(authorLabel);
    titleAuthorPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    titleAuthorPanel.add(authorField);

    // process panel
    processPanel = new JPanel();
    processPanel.setBackground(Color.white);
    processPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
    processPanel.add(processButton);
    summaryPanel = new JPanel();
    summaryPanel.setLayout(new BorderLayout());
    summaryPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    summaryPanel.setBackground(Color.white);
    summaryPanel.add(processSummary);

    // add everything in
    this.setBackground(Color.white);
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.add(loadTextFilePanel);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(selectFileTypePanel);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(titleAuthorPanel);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(processPanel);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(summaryPanel);
  }

  /**
   * Private class that populates the author and title fields if a gutenburg 
   * book is selected. 
   */
  private void populateAuthorAndTitle()
  {
    processButton.setEnabled(true);
    processSummary.setText("");
    try
    {
      ParseStrategy strategy;
      String selectedFileType = (String) fileTypeComboBox.getSelectedItem();
      
      if (selectedFileType.equals(gutenberg))
        strategy = new GutenbergStrategy();
      else if (selectedFileType.equals(plainText))
        strategy = new PlainTextStrategy();
      else
        strategy = null; // should throw exception instead of this.
        // should fix that instead of doing this.
        
      Book tempBook = Book.loadBook(fileField.getText(), strategy);
      authorField.setText(tempBook.getAuthor());
      titleField.setText(tempBook.getTitle());
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Action Listener for browse button.
   * 
   * @author shimpjn
   */
  private class Browse implements ActionListener
  {
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
      authorField.setText("");
      titleField.setText("");
      
      SwitchableActionListener listener = 
          (SwitchableActionListener) fileTypeComboBox.getActionListeners()[0];
      
      listener.setActive(false);
      fileTypeComboBox.setSelectedIndex(-1); // No selection.
      listener.setActive(true);
      
      fileChooser.showOpenDialog(null);
      if (fileChooser.getSelectedFile() != null)
      {
        fileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
      }
    }
  }
  
  /**
   * Action Listener for file type selection.
   * 
   * @author shimpjn
   */
  private class FileTypeSelection extends SwitchableActionListener
  {    
    @Override
    public void respondToEvent(ActionEvent event)
    {
      populateAuthorAndTitle();
    }
  }
  
  /**
   * Action Listener for process button.
   * 
   * @author mitchejp/shimpjn
   */
  private class Process implements ActionListener
  {

    @Override
    public void actionPerformed(ActionEvent event)
    {
      try
      {
        ParseStrategy strategy;
        
        Datastore store = LoadPanel.this.getDatastore();
        String selectedFileType = (String) fileTypeComboBox.getSelectedItem();

        if (selectedFileType.equals(gutenberg))
          strategy = new GutenbergStrategy();
        else if (selectedFileType.equals(plainText))
          strategy = new PlainTextStrategy();
        else
          strategy = null; // should throw exception instead of this.
          // should fix that instead of doing this.
        
        Book book = Book.loadBook(fileField.getText(), strategy);
        book.setAuthor(authorField.getText());
        book.setTitle(titleField.getText());
        store.add(book);
        
        processSummary.setText("Book: " + book.getAddress() + "\n"
            + "Title: " + book.getTitle() + "\n" + "Author: " + book.getAuthor() + "\n"
            + "Book Size: " + book.getNumParagraphs() + "\n"
            + "Book Number: " + book.getId() + "\n"
            + "New Index Terms: " + book.getIndexTerms() + "\n"
            + "New Postings: " + book.getPostings() + "\n"
            + "Total Index Terms: " + store.getTotalIndexTerms() + "\n"
            + "Total Postings: " + store.getTotalPostings());
        
        if(book.isError())
          processSummary.setText(processSummary.getText() + "\n Error occured during "
              + "reading at Line : " + book.getLineOfError() + "\n Error of type : " 
              + book.getErrorType());
      }
      catch (IOException e)
      {
        // TODO Auto-generated catch block
        ShowPirexError.showError("Loading book error", "Error loading " + "the book", e, 4);
        e.printStackTrace();
      }
      LoadPanel.this.updateSummary();
    }

  }
}
