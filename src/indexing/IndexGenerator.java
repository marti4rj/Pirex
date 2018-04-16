package indexing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import datastore.Paragraph;
import datastore.Posting;

/**
 * Generate indexes from a paragraph.
 * 
 * @author shimpjn
 *
 */
public class IndexGenerator
{
  private Paragraph text;
  private Set<String> stopList;
  private List<String> termsList;
  private Set<String> terms;
  
  /**
   * Construct index generator.
   * @param paragraph text to index
   */
  public IndexGenerator(Paragraph paragraph)
  {
    this.text = paragraph;
    this.stopList = IndexGenerator.readStopList();
 
    this.getIndexableTerms();
  }
  
  /**
   * Construct index generator.
   * @param paragraph text to index
   * @param stopListSet unique list of stop words
   */
  public IndexGenerator(Paragraph paragraph, Set<String> stopListSet)
  {
    this.text = paragraph;
    this.stopList = stopListSet;
    
    this.getIndexableTerms();
  }

  /**
   * 
   * @return indexable words
   */
  public List<Index> getIndices()
  {
    ArrayList<Index> indices = new ArrayList<Index>();
    
    for (String term : terms)
    {
      int frequency   = Collections.frequency(termsList, term);
      Posting posting = new Posting(text.getBookId(), text.getId(), frequency);
      
      indices.add(new Index(term, posting));
    }
    
    return indices;
  }

  /**
   * Get the droplist from the filesystem.
   * @return unique set of dropped keywords
   */
  public static Set<String> readStopList()
  {
    BufferedReader stopListBuffer;
    String word;
    HashSet<String> stopWords = new HashSet<String>();
    
    try
    {
      stopListBuffer = new BufferedReader(new FileReader("resources/stopList.txt"));
      
      while ((word = stopListBuffer.readLine()) != null)
        stopWords.add(word);
      
      stopListBuffer.close();
      
      return stopWords; 
    }
    catch (IOException e)
    {
      System.out.println("Couldn't open drop list.");
      return new HashSet<String>();
    }
  }

  /**
   * @return the stopList
   */
  public Set<String> getStopList()
  {
    return stopList;
  }

  /**
   * @param stopList the stopList to set
   */
  public void setStopList(Set<String> stopList)
  {
    this.stopList = stopList;
  }
  
  /**
   * Sanitize and return a copy of the input.
   * @return sanitized string
   */
  private String sanitize()
  {
    String sanitized = new String(text.getText());  
    
    sanitized = sanitized.toLowerCase();
    
    sanitized = sanitized.replaceAll("(--+)|(-\\s)|(\\s-)", " ");
    sanitized = sanitized.replaceAll("[’‘]", "'");
    sanitized = sanitized.replaceAll("[\"“”()]", " ");
    sanitized = sanitized.replaceAll("'($|\\n)", " ");
    sanitized = sanitized.replaceAll("('\\s)|(\\s')", " "); 
    sanitized = sanitized.replaceAll("[~`!@#\\$%^&*_+=,;:?\\/|<>\\[\\]{}]", " ");
    sanitized = sanitized.replaceAll("(\\.($|\\n))|(\\s\\.)|(\\.\\s)", " ");
    sanitized = sanitized.replaceAll("'($|\\n)", " ");
    sanitized = sanitized.replaceAll("('\\s)|(\\s')", " ");
    sanitized = sanitized.replaceAll("(^\\s+)", "");
    
    return sanitized;
  }
  
  /**
   * Parse text for terms.
   */
  private void getIndexableTerms()
  {
    String sanitizedText = sanitize();
    List<String> sanitizedSplitText = Arrays.asList(sanitizedText.split("\\s+"));
    Set<String> indexableTerms = 
        new HashSet<String>(sanitizedSplitText);
        
    if (!stopList.isEmpty())
      indexableTerms.removeAll(stopList);
    
    this.termsList = sanitizedSplitText;
    this.terms = indexableTerms;
  }
}
