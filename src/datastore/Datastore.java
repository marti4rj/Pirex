/**
 * 
 */
package datastore;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import gui.ShowPirexError;
import indexing.Index;
import indexing.IndexGenerator;

/**
 * @author shimpjn
 *
 */
public class Datastore implements Serializable
{
  private static final long serialVersionUID = 1L;

  private HashMap<String, ArrayList<Posting>> store;
  private String storePath;
  private ArrayList<Book> books;
  private int totalIndexTerms;
  private int totalPostings;

  /**
   * Construct a new/blank datastore.
   * 
   * @param storeLocation location of the datastore on the filesystem
   */
  public Datastore(String storeLocation) 
  {
    this.totalIndexTerms = 0;
    this.totalPostings   = 0;
    this.storePath       = storeLocation;
    this.books           = new ArrayList<Book>();
    this.store           = new HashMap<String, ArrayList<Posting>>();
  }
  
  /**
   * Getter for bookCount.
   * @return total number of books in the datastore
   */
  public int getNumBooks()
  {
    return books.size();
  }
  
  /**
   * Getter for books list.
   * @return list of books
   */
  public ArrayList<Book> getBooks()
  {
    return this.books;
  }
  
  /**
   * Getter for totalIndexTerms.
   * @return the total index terms
   */
  public int getTotalIndexTerms()
  {
    return this.totalIndexTerms;
  }
  
  /**
   * Getter for totalPostings.
   * @return the total number of postings
   */
  public int getTotalPostings()
  {
    return this.totalPostings;
  }
  
  /**
   * Add a book to the datastore.
   * @param book Book to add
   */
  public void add(Book book)
  {
    int newIndexTerms = 0;
    int newPostings   = 0;
    
    book.setId(getNumBooks());
    book.parseParagraphs();
    
    Set<String> stopList = IndexGenerator.readStopList();
    
    for (Paragraph paragraph : book.getParagraphList())
    { 
      IndexGenerator indexGen = new IndexGenerator(paragraph, stopList);
      
      for (Index index : indexGen.getIndices())
      {
        String term = index.getTerm();
        ArrayList<Posting> instancesOfWord = store.get(term);
        
        if (instancesOfWord == null)
        {
          instancesOfWord = new ArrayList<Posting>();
          instancesOfWord.add(index.getPosting());

          store.put(term, instancesOfWord);
          
          newIndexTerms++;
        }
        else
        {
          instancesOfWord.add(index.getPosting());
          //store.put(term, instancesOfWord);
        }
        
        newPostings++;
      }
    }
    
    book.setIndexTerms(newIndexTerms);
    book.setPostings(newPostings); 
    
    totalIndexTerms += newIndexTerms;
    totalPostings   += newPostings;
    books.add(book); 
    
    this.saveData();
  }
  
  /**
   * Search datastore.
   * 
   * @param term search term
   * @return List of postings or null
   */
  public ArrayList<Posting> search(String term)
  {
    return store.get(term.toLowerCase());
  }
  
  /**
   * Get book by ID.
   * @param id Book id
   * @return book with specified id
   */
  public Book getBook(int id)
  {
    return books.get(id);
  }
  
  /**
   * Save datastore.
   * @return success / fail.
   */
  public boolean saveData()
  {
    return saveData(this.storePath);
  }
  
  /**
   * @param fileName location of file.
   * @return success / fail.
   */
  public boolean saveData(String fileName)
  {
    try
    { 
      FileOutputStream fileOut = new FileOutputStream(fileName);
      ObjectOutputStream out   = new ObjectOutputStream(fileOut);
      
      out.writeObject(this);
      
      out.close();
      fileOut.close();
      
      return true;
    }
    catch (IOException e)
    {
      ShowPirexError.showError("Datastore Save Error", "error occured while "
          + "saving datastore", e, 2);
    }
    return false;
  }
  
  
  /**
   * @param fileName location of file.
   * @return success / fail.
   */
  public static Datastore loadSavedData(String fileName)
  {
    try
    {
      FileInputStream fileIn = new FileInputStream(fileName);
      ObjectInputStream in   = new ObjectInputStream(fileIn);
      Datastore savedData    = (Datastore) in.readObject();
      
      in.close();
      fileIn.close();
      
      return savedData;
    }
    catch (EOFException e)
    {
      System.out.println("Datastore is empty");
      return new Datastore(fileName);
    }
    catch (IOException e)
    {
      ShowPirexError.showError("Datastore Load Error", "Error occured while "
          + "loading datastore", e, 3);
    }
    catch (ClassNotFoundException e)
    {
      System.out.println(e);
      return null;
    }
    return null;
  }
}
