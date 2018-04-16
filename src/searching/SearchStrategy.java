package searching;

import java.util.ArrayList;
import datastore.Datastore;


/**
 * Strategy interface for searching.
 * 
 * @author James Mitchell
 * @version 11/15/2017
 */
public interface SearchStrategy
{
  // search weights
  public static final int HEIGH_WEIGHT = 3;
  public static final int LOW_WEIGHT = 1;
  
  /**
   * Searches for paragraphs.
   * @param store Datastore to search
   * @param terms terms to search for in store
   * @return paragraphs with matching terms
   */
  ArrayList<SearchResult> search(Datastore store, ArrayList<SearchTerm> terms);
}
