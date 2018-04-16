package datastore.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import datastore.Paragraph;

/**
 * 
 * @author shimpjn
 *
 */
public class ParagraphTest
{

  /**
   * Test constructor.
   */
  @Test
  public void testParagraph()
  {
    Paragraph test = new Paragraph(0, 0, "Test");
    
    assertTrue("Check that paragraph was instantiated", test != null);
  }

  /**
   * Check paragraph text.
   */
  @Test
  public void testGetTextAndToString()
  {
    Paragraph test = new Paragraph(0, 0, "Test");
    
    assertEquals("Check paragraph text", "Test", test.getText());
    assertEquals("Check paragraph text", "Test", test.toString());
  }

  /**
   * Test book id.
   */
  @Test
  public void testGetBookId()
  {
    Paragraph test = new Paragraph(0, 0, "Test");

    assertEquals("Check book id", test.getBookId(), 0);
  }

  /**
   * Test paragraph id.
   */
  @Test
  public void testGetId()
  {
    Paragraph test = new Paragraph(0, 0, "Test");

    assertEquals("Check paragraph id", test.getId(), 0);
  }
}
