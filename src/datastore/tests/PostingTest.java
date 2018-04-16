package datastore.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import datastore.Posting;

/**
 * Test cases for Posting.
 * @author shimpjn
 *
 */
public class PostingTest
{

  /**
   * Test constructor.
   */
  @Test
  public void testPosting()
  {
    Posting posting = new Posting(0, 0, 64);
    
    assertTrue("Check posting constructor", posting != null);
  }

  /**
   * Test book id getter.
   */
  @Test
  public void testGetBookID()
  {
    Posting posting = new Posting(0, 0, 64);
    assertEquals("Check book id", 0, posting.getBookID());
  }

  /**
   * Test book id setter.
   */
  @Test
  public void testSetBookID()
  {
    Posting posting = new Posting(0, 0, 64);
    assertEquals("Check book id", 0, posting.getBookID());
    
    posting.setBookID(1);
    assertEquals("Check new book id", 1, posting.getBookID());
  }

  /**
   * Test paragraph id getter.
   */
  @Test
  public void testGetParagraphID()
  {
    Posting posting = new Posting(0, 0, 64);
    assertEquals("Check book id", 0, posting.getParagraphID());
  }

  /**
   * Test paragraph id setter.
   */
  @Test
  public void testSetParagraphID()
  {
    Posting posting = new Posting(0, 0, 64);
    assertEquals("Check paragraph id", 0, posting.getParagraphID());
    
    posting.setParagraphID(1);
    assertEquals("Check new paragraph id", 1, posting.getParagraphID());
  }

  /**
   * Test frequency getter.
   */
  @Test
  public void testGetFrequency()
  {
    Posting posting = new Posting(0, 0, 64);
    assertEquals("Check frequency", 64, posting.getFrequency());
  }

  /**
   * Test frequency setter.
   */
  @Test
  public void testSetFrequency()
  {
    Posting posting = new Posting(0, 0, 64);
    assertEquals("Check frequency", 64, posting.getFrequency());
    
    posting.setFrequency(32);
    assertEquals("Check new frequency", 32, posting.getFrequency());
  }

  /**
   * Test book/paragraph id comparison.
   */
  @Test
  public void testEqualBookandParagraph()
  {
    Posting p1 = new Posting(0, 0, 8);
    Posting p2 = new Posting(0, 0, 8);
    
    assertTrue(p1.equalBookandParagraph(p2));
    
    p2.setBookID(1);
    
    assertFalse(p1.equalBookandParagraph(p2));
  }
}
