package datastore;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author shimpjn
 * Parsing strategy for Gutenberg files.
 */
public class GutenbergStrategy implements ParseStrategy
{

  @Override
  public Book parse(BufferedReader buffer) throws IOException
  {
    StringBuilder sb   = new StringBuilder();
    String line        = buffer.readLine();
    String author      = null;
    String title       = null;
    boolean error      = false;
    long errorLocation = 0;
    String errorType   = null;

    // proceeds the buffered reader without adding string to string builder, until ebook starts.
    try
    {
      while (!line.contains("*** START") && !line.contains("***START"))
      {
        line = buffer.readLine();
        errorLocation++;
        if (line.contains("Author: "))
        {
          String[] authorArr = line.split(": ");
          author = authorArr[1];
        } 
        else if (line.contains("Title: "))
        {
          String[] titleArr = line.split(": ");
          title = titleArr[1];
        }
      }
      
      line = buffer.readLine();
      errorLocation++;
      
      while (!line.contains("*** END") && !line.contains("**END"))
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
