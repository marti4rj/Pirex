package datastore;

/**
 * @author Ryan/shimpjn
 * Paragraph class.
 */
public class Paragraph implements java.io.Serializable
{
  private static final long serialVersionUID = 1L;
	
  private int id;
  private int bookId;
  private String text;

  
  /**
   * Instantiate a paragraph with an associated book.
   * @param identifier associated book
   * @param bookIdentifier ordinal id for the book
   * @param text raw paragraph
   */
  public Paragraph(int identifier, int bookIdentifier, String text)
  {
    this.id     = identifier;
    this.bookId = bookIdentifier;
    this.text   = text;
  }

  /**
   * @return returns text of paragraph.
   */
  public String getText()
  {
    return text;
  }
  
  /**
   * Getter for book.
   * @return associated book
   */
  public int getBookId()
  {
    return bookId;
  }
  
  /**
   * Getter for identifier.
   * @return the ordinal id
   */
  public int getId()
  {
    return id;
  }
  
  /**
   * String representation.
   * 
   */
  @Override
  public String toString()
  {
    return text;
  }
}  
