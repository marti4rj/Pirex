/**
 * 
 */
package datastore;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author shimpjn
 *
 */
public interface ParseStrategy
{
  /**
   * Book parser placeholder.
   * @param buffer buffer to parse
   * @throws IOException if a buffer error occurs
   * @return parsed Book
   */
  public Book parse(BufferedReader buffer) throws IOException;
}
