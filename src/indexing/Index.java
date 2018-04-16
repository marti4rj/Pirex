package indexing;

import datastore.Posting;

/**
 * @author shimpjn
 *
 */
public class Index
{
  private String term;
  private Posting posting;
  
  /**
   * Construct an index.
   * 
   * @param indexTerm index term
   * @param indexPosting associated posting
   */
  public Index(String indexTerm, Posting indexPosting)
  {
    this.setTerm(indexTerm);
    this.setPosting(indexPosting);
  }

  /**
   * @return the term
   */
  public String getTerm()
  {
    return term;
  }

  /**
   * @param term the term to set
   */
  public void setTerm(String term)
  {
    this.term = term;
  }

  /**
   * @return the posting
   */
  public Posting getPosting()
  {
    return posting;
  }

  /**
   * @param posting the posting to set
   */
  public void setPosting(Posting posting)
  {
    this.posting = posting;
  }
}
