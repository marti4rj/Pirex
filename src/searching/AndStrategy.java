package searching;

import java.util.ArrayList;

import datastore.Datastore;

/**
 * Searches for terms using the "and" strategy.
 * 
 * @author RJ Martinez
 * @version 11/15/2017
 *
 */
public class AndStrategy extends AbstractSearch
{

  @Override
  public ArrayList<SearchResult> search(Datastore store,
      ArrayList<SearchTerm> terms)
  {
    ArrayList<SearchResult> tempresults = searchHelper(store, terms);
    ArrayList<SearchResult> results = new ArrayList<SearchResult>();
    for(SearchResult result : tempresults) 
    {
      if(result.getNumTerms() == terms.size()) 
      {
        results.add(result);
      }
    }
    results = calculateTotalWeight(results, store);
    return results;
  }
}
