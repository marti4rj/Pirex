package indexing.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import datastore.Paragraph;
import indexing.Index;
import indexing.IndexGenerator;

/**
 * @author shimpjn
 *
 */
public class IndexGeneratorTest
{

  /**
   * Test method for {@link indexing.IndexGenerator#IndexGenerator(datastore.Paragraph)}.
   */
  @Test
  public void testIndexGeneratorParagraph()
  {
    Paragraph p = new Paragraph(0, 0, "Test");
    IndexGenerator ig = new IndexGenerator(p);
    
    assertTrue("Check constructor", ig != null);
  }

  /**
   * Test method for {@link indexing.IndexGenerator#IndexGenerator(datastore.Paragraph,
   *  java.util.HashSet)}.
   */
  @Test
  public void testIndexGeneratorParagraphHashSetOfString()
  {
    Paragraph p = new Paragraph(0, 0, "Test");
    Set<String> sl = IndexGenerator.readStopList();
    IndexGenerator ig = new IndexGenerator(p, sl);
    
    assertTrue("Check constructor", ig != null);
  }

  /**
   * Test method for {@link indexing.IndexGenerator#getIndices()}.
   */
  @Test
  public void testGetIndices()
  {
    Paragraph p = new Paragraph(0, 0, "Test, this is a test.");
    Set<String> sl = IndexGenerator.readStopList();
    IndexGenerator ig = new IndexGenerator(p, sl);
    ArrayList<Index> indices = (ArrayList<Index>) ig.getIndices();
    
    assertEquals("Check that the list size is 1", 1, indices.size());
    assertEquals("Check that indices are correct",
        ig.getIndices().get(0).getTerm(), "test");
  }

  /**
   * Test method for {@link indexing.IndexGenerator#readStopList()}.
   */
  @Test
  public void testReadStopList()
  {
    Set<String> stopList = IndexGenerator.readStopList();
    
    assertFalse("Check if the stop was read", stopList.isEmpty());
  }

  /**
   * Test method for {@link indexing.IndexGenerator#getStopList()}.
   */
  @Test
  public void testGetStopList()
  {
    Paragraph p = new Paragraph(0, 0, "Test, this is a test.");
    Set<String> sl = IndexGenerator.readStopList();
    IndexGenerator ig = new IndexGenerator(p, sl);

    assertEquals("Check internal stop list", sl, ig.getStopList());
  }

  /**
   * Test method for {@link indexing.IndexGenerator#setStopList(java.util.HashSet)}.
   */
  @Test
  public void testSetStopList()
  {
    Paragraph p = new Paragraph(0, 0, "Test, this is a test.");
    Set<String> sl = IndexGenerator.readStopList();
    IndexGenerator ig = new IndexGenerator(p);
    
    ig.setStopList(sl);

    assertEquals("Check internal stop list", sl, ig.getStopList());
  }
  
  /**
   * Verify paragraph frequency.
   */
  @Test
  public void testParagraphFrequency()
  {
    Paragraph p = new Paragraph(0, 0,
        "The man, after looking at me for a moment, turned me upside down, and\n"
        + "emptied my pockets. There was nothing in them but a piece of bread. When\n"
        + "the church came to itself,--for he was so sudden and strong that he\n"
        + "made it go head over heels before me, and I saw the steeple under my\n"
        + "feet,--when the church came to itself, I say, I was seated on a high\n"
        + "tombstone, trembling while he ate the bread ravenously.");
    
    IndexGenerator ig = new IndexGenerator(p);
    List<Index> indicies = ig.getIndices();
    
    for (Index i : indicies)
      if (i.getTerm().equals("church"))
        assertEquals("check frequency", 2, i.getPosting().getFrequency());
  }
}
