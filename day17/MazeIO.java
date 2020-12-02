/** 
 * Functions to read and write mazes. <p>
 * Input : maze is represented as a list of strings, converted to a
 *         2-D array of characters. <p>
 * Output: prints a 2-D array of characters <p>
 * 
 * Modeled on IntIO.java
 * 
 * @author Scot Drysdale
 * @see MazePanel
 */

import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import javax.swing.*;

public class MazeIO {
	
	/**
   * Read a representation of a maze from a file.  Assumes the 
	 * file contains a number of lines, and that each line has the 
	 * same number of characters.  Stores the characters in a 2-D 
	 * array and returns it.
	 *   
	 * @param directory a String giving the directory that we our file search from start from
	 * @return the maze, or null if cancel button selected in file chooser or if an error occurs
	 */
	public static char [][] readMaze(String directory) {
    ArrayList<String> lines;     // To hold the lines of the maze.
    Scanner input = null;
    
    // Put up a file chooser window to select a file to read.
    JFileChooser chooser = new JFileChooser(directory);
    int status = chooser.showOpenDialog(null);
    
    boolean fileOpened = true;    // OK unless proven otherwise
    
    do {
      // Get a file from the file chooser.  If the user hits the Cancel button,
      // end the program.
      while (status != JFileChooser.APPROVE_OPTION) {
        if (status == JFileChooser.CANCEL_OPTION)
          return null;
        else {
          JOptionPane.showMessageDialog(null, "No file chosen.  Try again.",
          		"Error", JOptionPane.ERROR_MESSAGE);
          status = chooser.showOpenDialog(null);
        }
      }
    
      // We have a file from the file chooser.  We should be able to create a
      // Scanner to read the file.
      File inputFile = chooser.getSelectedFile();
      try {
        input = new Scanner(inputFile);
      }
      catch (FileNotFoundException e) {
        fileOpened = false;
        JOptionPane.showMessageDialog(null, "Could not open file.  Try again.",
        		"Error", JOptionPane.ERROR_MESSAGE);
      }
    }
    while (!fileOpened);
    
    // Now the file has been opened.  Read the lines
    lines = new ArrayList<String>(); // Hold lines in an arraylist
    while (input.hasNext())
      lines.add(input.nextLine());
    
    if (lines.size() == 0) {
    	JOptionPane.showMessageDialog(null, "File was empty.",
      		"Error", JOptionPane.ERROR_MESSAGE);
    	return null;
    }
    else {
      // Create a 2-D array to hold the information
    	int lineLength = lines.get(0).length();
    	
    	if (lineLength == 0) {
      	JOptionPane.showMessageDialog(null, "First line of file was empty.",
        		"Error", JOptionPane.ERROR_MESSAGE);
      	return null;
    	}
    	
      char [][] maze = new char [lines.size()] [lines.get(0).length()];
      
      for (int row = 0; row < lines.size(); row++) {
      	String line = lines.get(row);
 
      	if (line.length() != lineLength) {
        	JOptionPane.showMessageDialog(null, "First line had " +
        			lineLength + " characters; found line with " + 
        		  line.length() + " character(s).",
          		"Error", JOptionPane.ERROR_MESSAGE);
        	return null;
      	}
      	else
      	  for (int col = 0; col < lineLength; col++)
      	  	maze[row][col] = line.charAt(col);
      }
      return maze;
    }
  }

  /**
   * Write the maze
   * 
   * @param maze the maze you wish to write
   */
  public static void writeMaze(char [][] maze) {

    System.out.println();
    for (char [] row : maze) {
    	for (char entry : row)
        System.out.print(entry);
    	System.out.println();
    }
    System.out.println();
  }
  
  /**
   *  test program
   */
  public static void main(String [] args) {
  	char [][] maze = readMaze("data/");
  	if (maze != null)
  	  writeMaze(maze);
  }
}
