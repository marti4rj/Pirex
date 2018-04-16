package searching;

import java.util.ArrayList;

import datastore.Datastore;

/**
 * Searches for terms using the "or" strategy.
 * 
 * @author RJ Martinez
 * @version 11/15/2017
 */
public class OrStrategy extends AbstractSearch
{

  @Override
  public ArrayList<SearchResult> search(Datastore store, ArrayList<SearchTerm> terms)
  {
    ArrayList<SearchResult> results = calculateTotalWeight(searchHelper(store, terms), store);
    return results;
  }

}
