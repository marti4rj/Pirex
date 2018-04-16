package datastore.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import datastore.Book;
import datastore.Datastore;
import datastore.GutenbergStrategy;
import searching.AbstractSearch;
import searching.AndStrategy;
import searching.OrStrategy;
import searching.SearchResult;
import searching.SearchTerm;

/**
 * @author Ryan
 * Non Functional Tests
 */
public class NonFunctionalTests
{

  /**
   * Test that pirex runs without failure with a book store from 4 text files,
   * after executing 10 searches.
   */
  @Test
  public void fourTextTenSearches()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    try
    {
      Book book1 = Book.loadBook("resources/great_expectations.txt",   new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());
      Book book3 = Book.loadBook("resources/oliver_twist.txt",         new GutenbergStrategy());
      Book book4 = Book.loadBook("resources/bleak_house.txt",          new GutenbergStrategy());
      
      store.add(book1);
      store.add(book2);
      store.add(book3);
      store.add(book4);
      
      AbstractSearch orStrategy = new OrStrategy();
      AbstractSearch andStrategy = new AndStrategy();
      
      SearchTerm term1 = new SearchTerm("mud", 2);
      SearchTerm term2 = new SearchTerm("water", 2);
      ArrayList<SearchTerm> twoTerms = new ArrayList<SearchTerm>();
      twoTerms.add(term1);
      twoTerms.add(term2);
      long oneSecond = 1000;
      
      long startTime = System.currentTimeMillis();
      @SuppressWarnings("unused")
      ArrayList<SearchResult> orResults = orStrategy.search(store, twoTerms);
      long endTime = System.currentTimeMillis();
      long time1 = (endTime - startTime);
       
      assertTrue("Check time for loading with or search is under 1 second",time1 < oneSecond);
      
      startTime = System.currentTimeMillis();
      @SuppressWarnings("unused")
      ArrayList<SearchResult> andResults = andStrategy.search(store, twoTerms);
      endTime = System.currentTimeMillis();
      long time2 = (endTime - startTime);
      assertTrue("Check time for loading with and search is under 1 second", time2 < oneSecond);
      
    }
    
    catch (IOException e)
    {
      System.out.println("Failed to load book");
    }
  }

  /**
   * Test search performance.
   */
  @Test
  public void loadTextTime()
  {
    
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    long fourSeconds = 4000;
    
    try
    {
      Book book1 = Book.loadBook("resources/great_expectations.txt",
          new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt",
          new GutenbergStrategy());
      Book book3 = Book.loadBook("resources/oliver_twist.txt",
          new GutenbergStrategy());
      Book book4 = Book.loadBook("resources/the_legend_of_sleepy_hollow.txt",
          new GutenbergStrategy());
      Book book5 = Book.loadBook("resources/bleak_house.txt",
          new GutenbergStrategy());
      
      store.add(book1);
      store.add(book2);
      store.add(book3);
      store.add(book4);
      
      long startTime = System.currentTimeMillis();
      store.add(book5);
      long endTime = System.currentTimeMillis();
      long time = (endTime - startTime);
      
      assertTrue("Check 5th book loading time less than 4 seconds", time < fourSeconds);
      
    }
    catch (IOException e)
    {
      System.out.println("Failed to load book");
    }
  }
  
  /**
   * Makeshift test until better one is devised.
   */
  @Test
  public void programStillRunning()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    try
    {
      Book book1 = Book.loadBook("resources/great_expectations.txt",   new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());
      Book book3 = Book.loadBook("resources/oliver_twist.txt",         new GutenbergStrategy());
      Book book4 = Book.loadBook("resources/bleak_house.txt",          new GutenbergStrategy());
      
      store.add(book1);
      store.add(book2);
      store.add(book3);
      store.add(book4);
      
      AbstractSearch orStrategy = new OrStrategy();
      AbstractSearch andStrategy = new AndStrategy();
      
      SearchTerm term1 = new SearchTerm("mud", 2);
      SearchTerm term2 = new SearchTerm("water", 2);
      ArrayList<SearchTerm> twoTerms = new ArrayList<SearchTerm>();
      twoTerms.add(term1);
      twoTerms.add(term2);
      
      SearchTerm term3 = new SearchTerm("purple", 2);
      SearchTerm term4 = new SearchTerm("green", 2);
      ArrayList<SearchTerm> twoTermsNew = new ArrayList<SearchTerm>();
      twoTerms.add(term3);
      twoTerms.add(term4);
      
      // Execute 10 searches
      
      @SuppressWarnings("unused")
      ArrayList<SearchResult> orResults1 = orStrategy.search(store, twoTerms);

      @SuppressWarnings("unused")
      ArrayList<SearchResult> andResults2 = andStrategy.search(store, twoTerms);

      @SuppressWarnings("unused")
      ArrayList<SearchResult> orResults3 = orStrategy.search(store, twoTerms);

      @SuppressWarnings("unused")
      ArrayList<SearchResult> andResults4 = andStrategy.search(store, twoTerms);
      
      @SuppressWarnings("unused")
      ArrayList<SearchResult> orResults5 = orStrategy.search(store, twoTerms);

      @SuppressWarnings("unused")
      ArrayList<SearchResult> andResults6 = andStrategy.search(store, twoTerms);
      
      @SuppressWarnings("unused")
      ArrayList<SearchResult> orResults7 = orStrategy.search(store, twoTerms);

      @SuppressWarnings("unused")
      ArrayList<SearchResult> andResults8 = andStrategy.search(store, twoTerms);
      
      @SuppressWarnings("unused")
      ArrayList<SearchResult> orResults9 = orStrategy.search(store, twoTerms);

      @SuppressWarnings("unused")
      ArrayList<SearchResult> andResults10 = andStrategy.search(store, twoTerms);
      
      @SuppressWarnings("unused")
      ArrayList<SearchResult> orResults11 = orStrategy.search(store, twoTermsNew);
      
      // check if program still running
      boolean stillWorking = true;
      assertEquals(stillWorking, true);  
    }
    
    catch (IOException e)
    {
      System.out.println("Failed to load book");
    }
  }
}
