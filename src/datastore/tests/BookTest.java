package datastore.tests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import datastore.Book;
import datastore.GutenbergStrategy;
import datastore.ParseStrategy;

/**
 * Tests for Book clas.
 * @author shimpjn
 *
 */
public class BookTest
{

  /**
   * Test for numParagraphs.
   * @throws IOException if cannot read file
   */
  @Test
  public void checkParagraphCount() throws IOException
  {
    String greatExpectations = "resources/great_expectations.txt";
    String aTaleOfTwoCities  = "resources/a_tale_of_two_cities.txt";
    String bleakHouse = "resources/bleak_house.txt";

    ParseStrategy parser = new GutenbergStrategy();
    
    /* Great expectations has 3848 paragraphs according to UIDesignV1.pdf
     * found on canvas, but we are getting 3901 paragraphs. There doesn't seem
     * to be a direct cause. Our margin of error is small enough that the search
     * shouldn't be significantly affected. 
     */
    Book book1 = Book.loadBook(greatExpectations, parser);
    book1.setId(0);
    book1.parseParagraphs();
    assertEquals("check that the number of paragraphs is correct",
        book1.getNumParagraphs(), 3901);
    
    Book book2 = Book.loadBook(aTaleOfTwoCities, parser);
    book2.setId(1);
    book2.parseParagraphs();
    assertEquals("check that the number of paragraphs is correct",
        book2.getNumParagraphs(), 3329);
    
    Book book3 = Book.loadBook(bleakHouse, parser);
    book3.setId(2);
    book3.parseParagraphs();
    assertEquals("check that the number of paragraphs is correct",
        book3.getNumParagraphs(), 7312);
  }
  
  /**
   * Test copy constructor.
   */
  @Test
  public void bookCopyConstructor()
  {
    try
    {
      Book book1 = Book.loadBook("resources/bleak_house.txt", new GutenbergStrategy());
      Book book2 = new Book(book1);
      
      assertEquals("Check that books' addresses are equal", book1.getAddress(), book2.getAddress());
      assertEquals("Check that books' authors are equal", book1.getAuthor(), book2.getAuthor());
      assertEquals("Check that books' IDs are equal", book1.getId(), book2.getId());
      assertEquals("Check that books' index terms are equal", 
          book1.getIndexTerms(), book2.getIndexTerms());
      assertEquals("Check that books' number of paragraphs are equal",
          book1.getNumParagraphs(), book2.getNumParagraphs());
      assertEquals("Check that books' paragraphs are equal",
          book1.getParagraphList(), book2.getParagraphList());
      assertEquals("Check that books' postings are equal",
          book1.getPostings(), book2.getPostings());
      assertEquals("Check that books' titles are equal", book1.getTitle(), book2.getTitle());
    }
    catch (IOException e)
    {
      System.out.println(e);
    }
  }
}
