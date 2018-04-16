package searching.tests;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import datastore.Book;
import datastore.Datastore;
import datastore.GutenbergStrategy;
import searching.AndStrategy;
import searching.SearchResult;
import searching.SearchTerm;

/**
 * Tests for the AndStrategy.
 * 
 * @author James Mitchell, RJ Martinez
 * @version 11/15/2017
 */
public class AndStrategyTest
{

  /**
   * Tests stop world removal with a list of only stop words.
   * 
   * @throws IOException
   *           thrown when IO fails
   */
  @Test
  public void getSearchTermsRemovesStopWords() throws IOException
  {
    AndStrategy strategy = new AndStrategy();
    String terms = "";
    FileReader fr = new FileReader("resources/stopList.txt");
    BufferedReader br = new BufferedReader(fr);

    String term;
    while ((term = br.readLine()) != null)
    {
      terms += term + " ";
    }

    ArrayList<SearchTerm> searchTerms = strategy.getSearchTerms(terms, 3);

    for (SearchTerm s : searchTerms)
    {
      System.out.println(s.term());
    }

    assertEquals(0, searchTerms.size());

    br.close();
    fr.close();
  }

  /**
   * Tests that words are inserted correctly into the list of search terms.
   */
  @Test
  public void testGetSearchTermsNormalTerms()
  {
  }

  {
    AndStrategy strategy = new AndStrategy();
    String[] terms = {"frankenstein", "dracula", "great", "expectations"};
    String query = "frankenstein dracula great expectations";
    ArrayList<SearchTerm> st = strategy.getSearchTerms(query, 3);

    assertEquals(4, st.size());
    for (int i = 0; i < terms.length; i++)
    {
      assertEquals(terms[i], st.get(i).term());
      assertEquals(3, st.get(i).weight());
    }
  }

  /**
   * Tests creating search term list with stop words.
   */
  @Test
  public void tesgGetSearchTermsWithStopTerms()
  {
    AndStrategy strategy = new AndStrategy();
    String[] terms = {"frankenstein", "dracula", "great", "expectations"};
    String query = "frankenstein a dracula great yourself expectations";
    ArrayList<SearchTerm> st = strategy.getSearchTerms(query, 3);

    assertEquals(4, st.size());
    for (int i = 0; i < terms.length; i++)
    {
      assertEquals(terms[i], st.get(i).term());
      assertEquals(3, st.get(i).weight());
    }
  }

  /**
   * Tests searching.
   */
  @Test
  public void testSearch()
  {
    Datastore store = new Datastore("/tmp/pirex_" + (System.currentTimeMillis() / 1000) + ".dat");
    Book book;
    try
    {
      book = Book.loadBook("resources/great_expectations.txt", new GutenbergStrategy());
      store.add(book);
      book = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());
      store.add(book);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    AndStrategy strategy = new AndStrategy();
    String query = "mud water";
    ArrayList<SearchTerm> st = strategy.getSearchTerms(query, 3);
    ArrayList<SearchResult> results = strategy.search(store, st);
    Assert.assertTrue(results.size() == 4);
  }

  /**
   * Tests weight calculation.
   */
  @Test
  public void testCalculateTotalWeight()
  {
    Datastore store = new Datastore("/tmp/pirex_" + (System.currentTimeMillis() / 1000) + ".dat");
    Book book;
    try
    {
      book = Book.loadBook("resources/testudo.txt", new GutenbergStrategy());
      store.add(book);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    AndStrategy strategy = new AndStrategy();
    String query = "testudo";
    ArrayList<SearchTerm> st = strategy.getSearchTerms(query, 3);
    ArrayList<SearchResult> results = strategy.search(store, st);
    
    for(SearchResult result : results)
    {
      System.out.println(result.getTotalWeight());
    }
    
    Assert.assertTrue("weight: " + results.get(0).getTotalWeight(),
        results.get(0).getTotalWeight() > results.get(1).getTotalWeight());
  }
}
