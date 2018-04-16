package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import datastore.Book;
import datastore.Datastore;
import datastore.Paragraph;
import searching.AndStrategy;
import searching.OrStrategy;
import searching.SearchResult;
import searching.SearchStrategy;
import searching.SearchTerm;
import searching.AbstractSearch;


/**
 * Search Panel UI class.
 * 
 * @author willi4rj
 */

public class SearchPanel extends PirexPanel
{
  private static final long serialVersionUID = 1L;

  private JRadioButton or;
  private JRadioButton and;
  private ButtonGroup group;
  private JButton clearAll;
  private JTextField hiWeight;
  private JTextField loWeight;
  private JList<String> occurrences;
  private JScrollPane occScroll;
  private JTextArea longFormView;
  private JScrollPane longFormScroll;
  private JPanel terms;
  private JPanel hTerms;
  private JPanel lTerms;
  private JLabel combine;
  private JLabel highWeight;
  private JLabel lowWeight;
  private JLabel numParagraphs;

  /**
   * Search Panel constructor.
   * 
   * Creates the panel used for searching
   */
  public SearchPanel()
  {
    // create radio buttons and put them into a group
    this.or = new JRadioButton("Or");
    or.setSelected(true);
    or.setBackground(Color.white);
    or.addActionListener(new InputTermListener());
    
    this.and = new JRadioButton("And");
    and.setBackground(Color.white);
    and.addActionListener(new InputTermListener());
    
    this.group = new ButtonGroup();
    group.add(or);
    group.add(and);

    this.clearAll = new JButton("Clear All Terms");
    clearAll.addActionListener(new ClearTermsListener());

    // create text fields and their desired sizes
    this.hiWeight = new JTextField();
    hiWeight.setMaximumSize(new Dimension(Integer.MAX_VALUE, hiWeight.getPreferredSize().height));
    hiWeight.addKeyListener(new InputTermListener());
    
    this.loWeight = new JTextField();
    loWeight.setMaximumSize(new Dimension(Integer.MAX_VALUE, loWeight.getPreferredSize().height));
    loWeight.addKeyListener(new InputTermListener());

    // text area for paragraphs
    this.longFormView = new JTextArea();
    this.longFormView.setEditable(false);
    
    this.longFormScroll = new JScrollPane(longFormView);
    
    // create list for returned findings
    this.occurrences = new JList<String>();
    this.occurrences.addMouseListener(new LongFormListener());
    this.occurrences.addKeyListener(new LongFormListener());
    this.occScroll = new JScrollPane(occurrences);
    

    // panels to be put into main panel
    this.terms = new JPanel();
    this.hTerms = new JPanel();
    this.lTerms = new JPanel();

    // create JLabels for text in GUI
    this.combine = new JLabel("Combine Terms Using:");
    this.highWeight = new JLabel("High Weight Search Terms:");
    this.lowWeight = new JLabel("Low Weight Search Terms: ");

    this.numParagraphs = new JLabel(" ");
    
    // Initialize all panels
    terms.setLayout(new BoxLayout(terms, BoxLayout.X_AXIS));
    terms.setBackground(Color.white);
    terms.add(combine);
    terms.add(Box.createRigidArea(new Dimension(10, 0)));
    terms.add(or);
    terms.add(Box.createRigidArea(new Dimension(10, 0)));
    terms.add(and);
    terms.add(Box.createHorizontalGlue());
    terms.add(clearAll);
    terms.setAlignmentX(Component.LEFT_ALIGNMENT);

    hTerms.setLayout(new BoxLayout(hTerms, BoxLayout.X_AXIS));
    hTerms.setBackground(Color.white);
    hTerms.add(highWeight);
    hTerms.add(Box.createRigidArea(new Dimension(10, 0)));
    hTerms.add(hiWeight);
    hTerms.setAlignmentX(Component.LEFT_ALIGNMENT);

    lTerms.setLayout(new BoxLayout(lTerms, BoxLayout.X_AXIS));
    lTerms.setBackground(Color.white);
    lTerms.add(lowWeight);
    lTerms.add(Box.createRigidArea(new Dimension(10, 0)));
    lTerms.add(loWeight);
    lTerms.setAlignmentX(Component.LEFT_ALIGNMENT);

    
    longFormView.setBackground(Color.white);
    //longFormView.setBorder(BorderFactory.createLineBorder(Color.black));

    // add panels to main panel
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.setBackground(Color.white);
    this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    this.add(terms);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(hTerms);
    this.add(Box.createRigidArea(new Dimension(0, 10)));
    this.add(lTerms);
    this.add(Box.createRigidArea(new Dimension(0, 20)));
    //this.add(list);
    this.add(occScroll);
    this.add(Box.createRigidArea(new Dimension(0, 5)));    
    this.add(numParagraphs);
    this.add(Box.createRigidArea(new Dimension(0, 5)));    
    this.add(longFormScroll);
    //this.add(paragraphs);
  }
  
  /**
   * 
   * @author shimpjn
   *
   */
  private class ClearTermsListener implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
      SearchPanel.this.occurrences.setListData(new String[1]);
      SearchPanel.this.hiWeight.setText("");
      SearchPanel.this.loWeight.setText("");
      SearchPanel.this.numParagraphs.setText(" ");
      SearchPanel.this.longFormView.setText("");
    }
  }
  
  /**
   * 
   * @author shimpjn
   *
   */
  private class InputTermListener implements KeyListener, ActionListener
  {
    /**
     * Search operation.
     */
    private void doSearch()
    {
      AbstractSearch searchStrategy;
      ArrayList<SearchTerm> searchTerms = new ArrayList<SearchTerm>();
      Datastore store = SearchPanel.this.getDatastore();
      
      if (SearchPanel.this.or.isSelected())
        searchStrategy = new OrStrategy();
      else
        searchStrategy = new AndStrategy();
      
      String lowWeightTerms = SearchPanel.this.loWeight.getText();
      String highWeightTerms = SearchPanel.this.hiWeight.getText();
      
      if (!lowWeightTerms.equals(""))
        searchTerms.addAll(searchStrategy.getSearchTerms(lowWeightTerms, 
            SearchStrategy.LOW_WEIGHT));
      
      if (!highWeightTerms.equals(""))
        searchTerms.addAll(searchStrategy.getSearchTerms(highWeightTerms, 
            SearchStrategy.HEIGH_WEIGHT));
      
      if (!searchTerms.isEmpty())
      {
        ArrayList<SearchResult> searchResults = searchStrategy.search(store, searchTerms);
        String[] searchList = new String[searchResults.size()];
        
        for (int i = 0; i < searchResults.size(); i++)
        {
          Book book = store.getBook(searchResults.get(i).getBookID());
          int paragraphID = searchResults.get(i).getParagraphID();
          
          Paragraph paragraph = book.getParagraph(paragraphID);
          String[] paragraphLines = paragraph.getText().split("\n");
          
          String strResult = book.getAuthor() + "  " + book.getTitle()
                            + "  " + paragraph.getId() + "  " + paragraphLines[0];
          
          searchList[i] = strResult;
        }
        
        SearchPanel.this.longFormView.setText("");
        SearchPanel.this.occurrences.setListData(searchList);
        SearchPanel.this.numParagraphs.setText("Retrieved " + searchList.length + " paragraphs");
      }
    }
  
    @Override
    public void keyPressed(KeyEvent keyEvent)
    {
      if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER)
        doSearch();
    }

    @Override
    public void keyReleased(KeyEvent arg0)
    {      
    }

    @Override
    public void keyTyped(KeyEvent arg0)
    { 
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      doSearch();
    } 
  }
  
  /**
   * Long Form input action listener.
   * @author shimpjn
   *
   */
  private class LongFormListener implements MouseListener, KeyListener
  {
    /**
     * Update the longform view with the selected posting.
     * @param locationIndex location of paragraph index
     */
    private void updateLongForm(int locationIndex)
    {
      Datastore store = SearchPanel.this.getDatastore();
      ArrayList<Book> books = store.getBooks();
      
      if (occurrences.getModel().getElementAt(locationIndex) != null 
          && occurrences.getModel().getElementAt(locationIndex) instanceof String)
      {
        String currentElement = occurrences.getModel().getElementAt(locationIndex);
        String[] contents = currentElement.split("  ");
        
        for (int i = 0; i < books.size(); i++)
        {
          if (books.get(i).getTitle().equals(contents[1]))
          {
            int id = Integer.parseInt(contents[2]);
            longFormView.setText(books.get(i).getParagraph(id).getText()); 
          }
        }
      }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
      int keyCode = e.getKeyCode();
      if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN)
        updateLongForm(occurrences.getSelectedIndex());
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
      updateLongForm(occurrences.locationToIndex(e.getPoint()));
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
    }

    @Override
    public void mouseExited(MouseEvent e)
    {      
    }

    @Override
    public void mousePressed(MouseEvent e)
    {      
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {      
    }
    
  }
}
