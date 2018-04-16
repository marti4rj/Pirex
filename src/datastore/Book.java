/**
 * 
 */
package datastore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author shimpjn/will4rj
 *
 */
public class Book implements Serializable
{
  private static final long serialVersionUID = 1L;
  
  private ArrayList<Paragraph> paragraphList;
  private String address;
  private String author;
  private String title;
  private int id;
  private int indexTerms;
  private int postings;
  private String text;
  private boolean error;
  private long lineOfError;
  private String errorType;

  /**
   * Blank constructor.
   * @param rawText raw text of the book
   */
  public Book(String rawText)
  {
    this.title         = "None";
    this.author        = "Anonymous";
    this.paragraphList = new ArrayList<Paragraph>();
    this.indexTerms    = 0;
    this.postings      = 0;
    this.text          = rawText;
    this.error = false;
    this.lineOfError = 0;
  }
  
  /**
   * Book clone constructor.
   * @param oldBook book to be cloned.
   */
  public Book(Book oldBook)
  {
    this.id            = oldBook.getId();
    this.address       = oldBook.getAddress();
    this.author        = oldBook.getAuthor();
    this.title         = oldBook.getTitle();
    this.paragraphList = oldBook.getParagraphList();
    this.indexTerms    = oldBook.getIndexTerms();
    this.postings      = oldBook.getPostings();
    this.error = false;
    this.lineOfError = 0;
  }

  /**
   * Getter for identifier.
   * @return identifier
   */
  public int getId()
  {
    return id;
  }

  /**
   * Setter for identifier.
   * @param identifier book identifier
   */
  public void setId(int identifier)
  {
    this.id = identifier;
  }

  /**
   * Getter for index terms.
   * @return the index terms
   */
  public int getIndexTerms()
  {
    return indexTerms;
  }

  /**
   * Setter for indexTerms.
   * @param terms index terms
   */
  public void setIndexTerms(int terms)
  {
    this.indexTerms = terms;
  }

  /**
   * Getter for postings.
   * @return postings
   */
  public int getPostings()
  {
    return postings;
  }

  /**
   * Setter for postings.
   * @param postings book postings
   */
  public void setPostings(int postings)
  {
    this.postings = postings;
  }

  /**
   * Get paragraph by id.
   * @param paragraphID paragraph id
   * @return paragraph with specified id
   */
  public Paragraph getParagraph(int paragraphID)
  {
    return paragraphList.get(paragraphID);
  }
  
  /**
   * @return returns list of paragraphs.
   */
  public ArrayList<Paragraph> getParagraphList()
  {
    return paragraphList;
  }

  /**
   * @return returns the address of the original file.
   */
  public String getAddress()
  {
    return address;
  } 

  /**
   * @param address sets the address of the original file.
   */
  public void setAddress(String address)
  {
    this.address = address;
  }

  /**
   * @return returns author of document.
   */
  public String getAuthor() 
  {
    return author;
  }

  /**
   * @param author sets the author of document.
   */
  public void setAuthor(String author) 
  {
    this.author = author;
  }

  /**
   * @return returns title of document.
   */
  public String getTitle() 
  {
    return title;
  }

  /**
   * @param title sets title of document.
   */
  public void setTitle(String title) 
  {
    this.title = title;
  }
  
  /**
   * Get number of paragraphs.
   * @return number of paragraphs in the book
   */
  public int getNumParagraphs()
  {
    return this.paragraphList.size();
  }

  /**
   * Load and parse a book from the filesystem.
   * @param fileAddress file location
   * @param parser parsing strategy
   * @return string representative of the book.
   * @throws IOException if there is an error with file selection.
   */
  public static Book loadBook(String fileAddress, ParseStrategy parser) throws IOException
  { 
    BufferedReader bookBuffer = new BufferedReader(new FileReader(fileAddress));
   
    Book parsedBook = parser.parse(bookBuffer);
    parsedBook.setAddress(fileAddress);
    
    bookBuffer.close();

    return parsedBook;
  }
  
  /**
   * Parse the paragraphs and store them in a list.
   */
  public void parseParagraphs()
  {
    /* Split by two or more newlines */
    String[] paragraphs = text.trim().split("\\n{2,}");
    
    for (int i = 0; i < paragraphs.length; i++)
      paragraphList.add(new Paragraph(i, this.getId(), paragraphs[i]));
  }

  /**
   * @return returns if Book had an error when reading in
   */
  public boolean isError()
  {
    return error;
  }

  /**
   * @param error sets the books error if error occurs
   */
  public void setError(boolean error)
  {
    this.error = error;
  }

  /**
   * @return returns line at which error occurred
   */
  public long getLineOfError()
  {
    return lineOfError;
  }

  /**
   * @param lineOfError sets line at which error occurred
   */
  public void setLineOfError(long lineOfError)
  {
    this.lineOfError = lineOfError;
  }
  
  /**
   * @return gets type of error
   */
  public String getErrorType()
  {
    return errorType;
  }

  /**
   * @param errorType sets type of error
   */
  public void setErrorType(String errorType)
  {
    this.errorType = errorType;
  }
  
}
