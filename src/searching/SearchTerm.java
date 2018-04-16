package searching;
/**
 * Search.
 * 
 * @author James Mitchell
 * @version 11/15/2017
 */
public class SearchTerm
{
  private final String term;
  private final int weight;
  
  /**
   * Default constructor for empty search terms.
   */
  public SearchTerm( ) 
  {
    term = null;
    weight = 0;
  }
  
  /**
   * Creates a SearchTerm object.
   * @param term search term
   * @param weight weight of searh term
   */
  public SearchTerm(String term, int weight) 
  {
    this.term = term;
    this.weight = weight;
  }
  
  /**
   * Returns search term string.
   * @return search term string
   */
  public String term() 
  {
    return term;
  }
  
  /**
   * Returns search term weight.
   * @return search term weight;
   */
  public int weight() 
  {
    return weight;
  }
}
