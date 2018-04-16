package gui;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import datastore.Book;
import datastore.Datastore;

/**
 * Summarize Panel.
 * 
 * @author RJ Martinez
 * @version 10/26/1017
 *
 */
public class SummarizePanel extends PirexPanel
{
  private static final long serialVersionUID = 1L;

  private JScrollPane pane;
  private JTextArea txt;

  /**
   * Constructor for the panel.
   */
  public SummarizePanel()
  {
    txt = new JTextArea();
    txt.setEditable(false);
    pane = new JScrollPane(txt);
    pane.setPreferredSize(new Dimension(940, 535));
    this.add(pane);
  }

  /**
   * Summarize books in datastore.
   */
  public void summarize()
  {
    Datastore temp = this.getDatastore();
    ArrayList<Book> books = temp.getBooks();
    String allBooks = "";
    for (int i = 0; i < books.size(); i++)
    {
      Book book = books.get(i);
      allBooks += "Book  " + i + ": " + book.getAuthor() + "   " + book.getTitle() + "    "
          + book.getNumParagraphs() + " paragraphs\n                " + book.getAddress() + "\n";
    }
    txt.setText(
        allBooks + "\nIndex terms: " + temp.getTotalIndexTerms()
                 + "\nPostings:      " + temp.getTotalPostings());
  }

}
