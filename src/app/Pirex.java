package app;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import datastore.Datastore;
import gui.PirexWindow;
import gui.ShowPirexError;

/**
 * Main application class.
 * 
 * @author shimpjn
 *
 */
public class Pirex
{
  
  /**
   * Entry point.
   * @param args program arguments
   */
  public static void main(String[] args)
  {
    Path pirexData;
    Path pirexDataFile;
    String pirexStore;
    Datastore data = null;
    
    pirexStore = System.getenv("PIREX_STORE");
    
    if (pirexStore != null)
    {
      pirexData = Paths.get(pirexStore);
       
      if (Files.notExists(pirexData))
      {
        System.out.println("Cannot load datastore at " + pirexData);
        String currentWorkingDirectory = System.getProperty("user.dir");
        pirexData = Paths.get(currentWorkingDirectory, "pirexData");
      }
    }
    else
    {
      System.out.println("Environment variable PIREX_STORE is not set");
      String currentWorkingDirectory = System.getProperty("user.dir");
      pirexData = Paths.get(currentWorkingDirectory, "pirexData"); 
    }
    
    pirexDataFile = Paths.get(pirexData.toString(), "pirex.dat");
    
    if (Files.notExists(pirexData))
    {
      try
      {
        System.out.println(pirexData.toString());
        Files.createDirectory(pirexData);
        Files.createFile(pirexDataFile);

        
        data = new Datastore(pirexDataFile.toString());
      }
      catch (IOException e)
      {
        ShowPirexError.showError("Datstore Creation Error", "Could not create pirexData "
            + "directory in current working directory", e, 1);
      }
    }
    else
    {
      try
      {
        if (Files.notExists(pirexDataFile))
          Files.createFile(pirexDataFile);
        
        data = Datastore.loadSavedData(pirexDataFile.toString());
      }
      catch (IOException e)
      {
        System.out.println(e);
      }
      
    }
    
    if (data != null)
      new PirexWindow(data);
    else
      System.out.println("Could not load datastore");
  }
}
