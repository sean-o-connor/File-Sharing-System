/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Files;
import ProjectInterface.Changes;
import ProjectInterface.FoldermonitorImpl;
import ProjectInterface.LocalFiles;

/**
 *
 * @author Sean O Connor
 */
public class Navigation {
    
       public Changes getDirectory(String directory){
      if(directory== null){
         return null;
      }		
      if(directory.equalsIgnoreCase("User")){
         return new LocalFiles();
         
      } else if(directory.equalsIgnoreCase("Shared")){
         return new FoldermonitorImpl();
         
      }
      return null;
   }
    
}
