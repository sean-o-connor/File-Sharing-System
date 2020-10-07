/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectInterface;

/**
 *
 * @author R00104637
 */
public  interface FolderMonitor {
    
    boolean isEOF();  // have we have reached the end of the file being read?
	String[] getNames(String s);  // returns the names of all the files in folder
	boolean openFile(String name);  // opens a file called name
	byte getB();   // Gets a byte from the currently open file
	boolean closeFile(String name);  // closes the open file
	boolean isChange(boolean b);	// has a new file(s) has been added?

    
}
