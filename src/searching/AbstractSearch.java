package searching;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import datastore.Datastore;
import datastore.Posting;

/**
 * Abstract search strategy--contains methods used across all strategies.
 * 
 * @author RJ Martinez
 * @version 11/15/2017
 */
public abstract class AbstractSearch implements SearchStrategy
{
  /**
   * Gets an array of search terms used by all searching strategies.
   * 
   * @param searchString
   *          string to get terms from
   * @param searchWeight
   *          weight of terms being extracted
   * @return array of search terms
   */
  public ArrayList<SearchTerm> getSearchTerms(String searchString, int searchWeight)
  {
    // get stop words
    ArrayList<String> stopWords = new ArrayList<String>();
    try
    {
      FileReader fileReader = new FileReader("resources/stopList.txt");
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      String term;
      while ((term = bufferedReader.readLine()) != null)
      {
        stopWords.add(term);
      }

      bufferedReader.close();
      fileReader.close();
    }
    catch (IOException e)
    {
      System.out.println("Couldn't open file.");
      e.printStackTrace();
      return new ArrayList<SearchTerm>();
    }

    // create search term list
    String[] words = searchString.split("\\s+");
    ArrayList<SearchTerm> searchTerms = new ArrayList<SearchTerm>();

    // add words not in stop list to array of search terms
    for (int i = 0; i < words.length; i++)
    {
      boolean isStopWord = false;

      for (String s : stopWords)
      {
        if (s.equals(words[i]))
        {
          isStopWord = true;
          break;
        }
      }

      if (!isStopWord)
      {
        searchTerms.add(new SearchTerm(words[i].toLowerCase(), searchWeight));
      }

    }

    return searchTerms;
  }

  /**
   * Protected helper class used by both or and and searching.
   * 
   * @param store
   *          Store to search
   * @param terms
   *          Search terms to use
   * @return An array list of SearchResults
   * 
   */
  protected ArrayList<SearchResult> searchHelper(Datastore store, ArrayList<SearchTerm> terms)
  {
    ArrayList<SearchResult> results = new ArrayList<SearchResult>();
    ArrayList<Posting> postings = new ArrayList<Posting>();

    for (SearchTerm term : terms)
    {
      postings = store.search(term.term());
      if(postings == null)
      {
        return new ArrayList<SearchResult>();
      }
      for (Posting post : postings)
      {
        boolean notFound = true;
        for (SearchResult result : results)
        {
          if (post.equalBookandParagraph(result.testPosting()))
          {
            notFound = false;
            result.addTerm(term);
            break;
          }
        }
        if (notFound)
        {
          SearchResult temp = new SearchResult(post.getBookID(), post.getParagraphID());
          temp.addTerm(term);
          results.add(temp);
        }
      }
    }

    return results;
  }

  /**
   * Calculates the total weight of each search result and returns a new ordered list of the same
   * results.
   * 
   * @param oldresults
   *          Search results
   * @param store
   *          Data store
   * @return New, ordered list of search results in descending order
   */
  protected ArrayList<SearchResult> calculateTotalWeight(ArrayList<SearchResult> oldresults,
      Datastore store)
  {
    ArrayList<SearchResult> results = oldresults;
    ArrayList<Posting> postings = new ArrayList<Posting>();
    for (SearchResult result : results)
    {
      // wt • log(n/nt) • (log(fpt) + 1)
      double totalWeight = 0;
      int n = store.getBook(result.getBookID()).getNumParagraphs();
      for (SearchTerm term : result.getTerms())
      {
        int wt = term.weight();
        double nt = 0;
        double fpt = 0;
        postings = store.search(term.term());
        for (Posting post : postings)
        {
          if (post.getBookID() == result.getBookID())
          {
            nt += 1;
            if (post.getParagraphID() == result.getParagraphID())
            {
              fpt = post.getFrequency();
            }
          }
          
        }
        totalWeight += (wt * Math.log(n / nt) * (Math.log(fpt) + 1));
      }
      result.setTotalWeight(totalWeight);
    }
    Collections.sort(results);
    Collections.reverse(results);
    return results;
  }
  
}
