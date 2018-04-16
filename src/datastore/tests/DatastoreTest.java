/**
 * 
 */
package datastore.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Test;

import datastore.Book;
import datastore.Datastore;
import datastore.GutenbergStrategy;
import datastore.Paragraph;
import datastore.PlainTextStrategy;
import datastore.Posting;

/**
 * @author shimpjn
 *
 */
public class DatastoreTest
{
  
  /**
   * Test empty data store.
   */
  @Test
  public void createEmptyDatastore()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    assertTrue("check that there are no books", store.getBooks().isEmpty());
    
    assertEquals("Datastore is empty", store.getNumBooks(), 0);
  }
  
  /**
   * Test adding book to data store.
   */
  @Test
  public void addBookToDataStore()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");

    try
    {
      Book book = Book.loadBook("resources/bleak_house.txt", new GutenbergStrategy());
      store.add(book);
      
      assertEquals("The book was added", store.getNumBooks(), 1);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Test adding several books.
   */
  @Test
  public void addMultipleBooksToDataStore()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");

    try
    {
      Book book1 = Book.loadBook("resources/bleak_house.txt", new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());

      store.add(book1);
      store.add(book2);
      
      assertEquals("Check to make sure id is 0", book1.getId(), 0);
      assertEquals("Check to make sure id is 1", book2.getId(), 1);

      assertEquals("The book was added", store.getNumBooks(), 2);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Test saving/loading.
   */
  @Test
  public void saveAndLoadDataStore()
  {
    String tmpFile = "/tmp/pirex_" + (System.currentTimeMillis() / 1000) + ".dat";
    Datastore store1 = new Datastore(tmpFile);

    try
    {
      Book book1 = Book.loadBook("resources/bleak_house.txt",          new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());
      
      store1.add(book1);
      store1.add(book2);
      
      boolean saved = store1.saveData();
      assertTrue("Check that the datastore was saved", saved);
      
      Datastore store2 = Datastore.loadSavedData(tmpFile);
      
      assertTrue("Store was loaded", store2 != null);
      assertEquals("Both stores have the same number of books",
          store1.getNumBooks(), store2.getNumBooks());
      
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Test saving/loading errors.
   */
  @Test
  public void saveAndLoadDataStoreErrors()
  {
    String tmpFile = "/tmp/pirex_" + System.currentTimeMillis() + ".dat";

    try
    {
      Files.createFile(Paths.get(tmpFile));
      
      Datastore store = Datastore.loadSavedData(tmpFile);
      
      assertTrue("check that store was created", store != null);
      assertEquals("check that new datastore was created with bad save file",
          store.getNumBooks(), 0);
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  }
  
  /**
   * Test that the datastore returns the correct list of books. 
   */
  @Test
  public void testAddedBooksAreStored()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    try
    {
      Book book1 = Book.loadBook("resources/bleak_house.txt",          new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());

      store.add(book1);
      store.add(book2);
      
      ArrayList<Book> books = store.getBooks();
      
      assertEquals("Check that the first book is the same", book1, books.get(0));
      assertEquals("Check that the second book is the same", book2, books.get(1));
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Ensure that index and postings statistics are correct.
   * Also tests PlainTextStrategy as an aside.
   */
  @Test
  public void testTotalIndicesAndPostings()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    try
    {
      Book book = Book.loadBook("resources/plain_text_test_file.txt", new PlainTextStrategy());
      
      store.add(book);
      
      assertEquals("Check total indices", store.getTotalIndexTerms(), 8);
      assertEquals("Check total postings", store.getTotalPostings(), 10);
    }
    catch (IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /**
   * Test search.
   */
  @Test
  public void testSearch()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    try
    {
      Book book = Book.loadBook("resources/test_book.txt", new GutenbergStrategy());
      
      store.add(book);
      
      ArrayList<Posting> postings1 = store.search("paragraph");
      assertEquals("check number of postings", postings1.size(), 3);
      
      ArrayList<Posting> postings2 = store.search("PARAGRAPH");
      assertEquals("check number of postings with uppercase query", postings2.size(), 3);
    }
    catch (IOException e)
    {
      System.out.println("Failed to load book");
    }
  }
  
  /**
   * Test getting a book by id.
   */
  @Test
  public void getBooksById()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    
    try
    {
      Book book1 = Book.loadBook("resources/great_expectations.txt",   new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());
      Book book3 = Book.loadBook("resources/bleak_house.txt",          new GutenbergStrategy());
      
      store.add(book1);
      store.add(book2);
      store.add(book3);

      assertEquals("Check that book with id 0 is book1", store.getBook(0), book1);
      assertEquals("Check that book with id 1 is book2", store.getBook(1), book2);
      assertEquals("Check that book with id 2 is book3", store.getBook(2), book3);
    }
    catch (IOException e)
    {
      System.out.println("Failed to load book");
    }
  }
  
  /**
   * Test search.
   */
  @Test
  public void checkThatSearchResultsContainSearchTerms()
  {
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    String searchTerm = "mud";

    try
    {
      Book book1 = Book.loadBook("resources/great_expectations.txt",   new GutenbergStrategy());
      Book book2 = Book.loadBook("resources/a_tale_of_two_cities.txt", new GutenbergStrategy());
      Book book3 = Book.loadBook("resources/bleak_house.txt",          new GutenbergStrategy());
      
      store.add(book1);
      store.add(book2);
      store.add(book3);

      ArrayList<Posting> searchResults = store.search(searchTerm);
      
      for (Posting result : searchResults)
      {
        Book tmpBook = store.getBook(result.getBookID());
        Paragraph tmpParagraph = tmpBook.getParagraph(result.getParagraphID());
        
        assertTrue("check that search result contains search term",  
            tmpParagraph.getText().contains(searchTerm));
      }
    }
    catch (IOException e)
    {
      System.out.println("Failed to load book");
    }
    
  }
}
