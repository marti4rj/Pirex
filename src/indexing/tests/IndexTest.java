package indexing.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import datastore.Posting;
import indexing.Index;

/**
 * @author shimpjn
 *
 */
public class IndexTest
{

  /**
   * Test method for {@link indexing.Index#Index(java.lang.String, datastore.Posting)}.
   */
  @Test
  public void testIndex()
  {
    Posting posting = new Posting(0, 0, 4);
    Index index = new Index("Test", posting);
    
    assertTrue("Check constructor", index != null);
  }

  /**
   * Test method for {@link indexing.Index#getTerm()}.
   */
  @Test
  public void testGetTerm()
  {
    Posting posting = new Posting(0, 0, 4);
    Index index = new Index("Test", posting);
    
    assertEquals("Check term getter", "Test", index.getTerm());
  }

  /**
   * Test method for {@link indexing.Index#setTerm(java.lang.String)}.
   */
  @Test
  public void testSetTerm()
  {
    Posting posting = new Posting(0, 0, 4);
    Index index = new Index("Test", posting);
    
    index.setTerm("McTestFace");
    
    assertEquals("Check term setter", "McTestFace", index.getTerm());
  }

  /**
   * Test method for {@link indexing.Index#getPosting()}.
   */
  @Test
  public void testGetPosting()
  {
    Posting posting = new Posting(0, 0, 4);
    Index index = new Index("Test", posting);

    assertEquals("check posting getter", posting, index.getPosting());
  }

  /**
   * Test method for {@link indexing.Index#setPosting(datastore.Posting)}.
   */
  @Test
  public void testSetPosting()
  {
    Posting p1 = new Posting(0, 0, 4);
    Posting p2 = new Posting(0, 1, 16);
    Index index = new Index("Test", p1);
    
    index.setPosting(p2);
    
    assertEquals("Check posting setter", p2, index.getPosting());
  }
}
