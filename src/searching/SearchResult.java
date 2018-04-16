package searching;

import java.util.HashSet;

import datastore.Posting;

/**
 * Search result object that holds the total weight of all the search terms found in its associated
 * paragraph/book.
 * 
 * @author RJ Martinez
 * @version 12/7/17
 *
 */
public class SearchResult implements Comparable<SearchResult>
{
  private int book;
  private int paragraph;
  private HashSet<SearchTerm> terms;
  private double totalWeight;

  /**
   * Constructor for the SearchTerm.
   * 
   * @param bookID
   *          Book the paragraph is in
   * @param paragraphID
   *          Paragraph
   */
  public SearchResult(int bookID, int paragraphID)
  {
    terms = new HashSet<SearchTerm>();
    book = bookID;
    paragraph = paragraphID;
    setTotalWeight(0);
  }

  /**
   * Getter for book ID.
   * 
   * @return book id
   */
  public int getBookID()
  {
    return book;
  }

  /**
   * Getter for paragraph ID.
   * 
   * @return paragraph id
   */
  public int getParagraphID()
  {
    return paragraph;
  }

  /**
   * Adds weight to the search result.
   * 
   * @param term search term
   */
  public void addTerm(SearchTerm term)
  {
    terms.add(term);
  }

  /**
   * Tests posting.
   * 
   * @return returns a blank posting.
   */
  public Posting testPosting()
  {
    return new Posting(this.book, this.paragraph, 0);
  }

  /**
   * Gets the number of terms.
   * 
   * @return number of terms.
   */
  public int getNumTerms()
  {
    return this.terms.size();
  }

  /**
   * Gets the set of search terms.
   * 
   * @return set of terms
   */
  public HashSet<SearchTerm> getTerms()
  {
    return this.terms;
  }

  /**
   * @return the totalWeight
   */
  public double getTotalWeight()
  {
    return totalWeight;
  }

  /**
   * @param totalWeight the totalWeight to set
   */
  public void setTotalWeight(double totalWeight)
  {
    this.totalWeight = totalWeight;
  }
  
  @Override
  public int compareTo(SearchResult arg0)
  {
    int ans = 0;
    if(this.getTotalWeight() < arg0.getTotalWeight()) 
    {
      ans = -1;
    }
    else if(this.getTotalWeight() > arg0.getTotalWeight()) 
    {
      ans = 1;
    }

    return ans;
  }
}
