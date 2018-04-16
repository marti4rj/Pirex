package searching.tests;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import datastore.Book;
import datastore.Datastore;
import datastore.GutenbergStrategy;
import searching.OrStrategy;
import searching.SearchResult;
import searching.SearchTerm;

/**
 * Test cases for the or strategy.
 */
public class OrStrategyTest
{
  
  /**
   * Tests searching.
   */
  @Test
  public void testSearch()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    Book book;
    try
    {
      book = Book.loadBook("resources/test_book.txt", new GutenbergStrategy());
      store.add(book);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    OrStrategy strategy = new OrStrategy();
    String query = "one two three";
    ArrayList<SearchTerm> st = strategy.getSearchTerms(query, 3);
    ArrayList<SearchResult> results = strategy.search(store, st);
    Assert.assertTrue(results.size() == 3);
  }

}
