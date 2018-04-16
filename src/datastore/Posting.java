package datastore;

import java.io.Serializable;

/**
 * @author willi4rj Posting class
 */
public class Posting implements Serializable
{
  private static final long serialVersionUID = 1L;

  private int bookID;
  private int paragraphID;
  private int frequency;

  /**
   * Posting constructor.
   * 
   * @param bookID
   *          book index
   * @param paragraphID
   *          paragraph index
   * @param frequency
   *          occurrences within paragraph
   */
  public Posting(int bookID, int paragraphID, int frequency)
  {
    this.bookID = bookID;
    this.paragraphID = paragraphID;
    this.frequency = frequency;
  }

  /**
   * @return returns book's ID for occurrence
   */
  public int getBookID()
  {
    return bookID;
  }

  /**
   * @param newBookID
   *          set book index for occurrence
   */
  public void setBookID(int newBookID)
  {
    this.bookID = newBookID;
  }

  /**
   * @return returns paragraph's ID for occurrence
   */
  public int getParagraphID()
  {
    return paragraphID;
  }

  /**
   * @param newParagraphID
   *          set paragraph index for occurrence
   */
  public void setParagraphID(int newParagraphID)
  {
    this.paragraphID = newParagraphID;
  }

  /**
   * @return returns number of occurrences within paragraph
   */
  public int getFrequency()
  {
    return frequency;
  }

  /**
   * @param newFrequency
   *          set amount of occurrences
   */
  public void setFrequency(int newFrequency)
  {
    this.frequency = newFrequency;
  }

  /**
   * Equals method.
   * 
   * @param posting
   *          Posting to be tested against
   * @return true if equal, false if not
   */
  public boolean equalBookandParagraph(Posting posting)
  {
    boolean booksAreEqual      = bookID == posting.getBookID();
    boolean paragraphsAreEqual = paragraphID == posting.getParagraphID();
    
    return booksAreEqual && paragraphsAreEqual;
  }
}
