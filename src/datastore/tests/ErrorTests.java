package datastore.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import datastore.Book;
import datastore.Datastore;
import datastore.GutenbergStrategy;

/**
 * Test for Pirex Errors.
 * @author willi4rj
 *
 */
public class ErrorTests
{

  /**
   * Test file reading errors.
   */
  @Test
  public void readingFileError() 
  {
    @SuppressWarnings("unused")
    Datastore store = new Datastore("/tmp/pirex_" 
        + (System.currentTimeMillis() / 1000) + ".dat");
    
    try
    {
      Book book1 = Book.loadBook("resources/plain_text_test_file.txt",   new GutenbergStrategy());
      assertEquals(book1.isError(), true);
      assertEquals(book1.getErrorType(), "java.lang.NullPointerException");
      assertEquals(book1.getLineOfError(), 7);
    }
    catch (IOException e)
    {
      
    }
  }
}
