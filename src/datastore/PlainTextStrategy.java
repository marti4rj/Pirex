package datastore;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author willi4rj
 * Parsing strategy for Plain Text files.
 */
public class PlainTextStrategy implements ParseStrategy
{

  @Override
  public Book parse(BufferedReader buffer) throws IOException
  {
    StringBuilder sb   = new StringBuilder();  
    String author      = "Anonymous";
    String title       = "None"; 
    String line        = buffer.readLine();
    boolean error      = false;
    long errorLocation = 0;
    String errorType   = null;
    
    try
    {
      while (line != null)
      {
        line = line.trim();
        
        if (!line.equals("\n") || !line.isEmpty())
        {
          sb.append(line);
          sb.append("\n");
        }
        
        line = buffer.readLine();
        errorLocation++;
      }
    }
    catch (IOException e)
    {
      error = true;
      errorType = e.toString();
    }
    catch (NullPointerException e)
    {
      error = true;
      errorType = e.toString();
    }
    
    String s = sb.toString();
    
    Book parsedBook = new Book(s);
    parsedBook.setAuthor(author);
    parsedBook.setTitle(title);
    if (error)
    {
      parsedBook.setError(error);
      parsedBook.setLineOfError(errorLocation);
      parsedBook.setErrorType(errorType);
    }
    
    return parsedBook;
  }
}
