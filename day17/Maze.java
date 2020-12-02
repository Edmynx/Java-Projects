//import javax.swing.JOptionPane;
//
///**
// * Represents and solves a maze. <p>
// * The input maze is assumed to be a 2-D character array, using
// * the constants shown below for the characters.
// *
// * @author Scot Drysdale
// */
//
//public class Maze {
//	public static final char EMPTY = ' ';    // Empty space in the maze
//	public static final char WALL = '*';     // Represents a wall
//	public static final char START = 'S';    // Start position in the maze (only one)
//	public static final char TARGET = 'T';   // Target (goal) position in the maze.
//	public static final char EXPLORED = '.'; // Has been explored.
//	public static final char ON_PATH = 'x';  // Lies on the path found
//
//	private char[][] theMaze;           // The maze to be solved
//	private Sequencer seq;	            // The sequencer that specifies the
//	                                    // order to explore the locations
//	private boolean isDone;	            // true when maze search is completed
//
//
//	/**
//	 * Constructor makes a copy of the maze presented in maze, after verifying
//	 * its accuracy (single start and target, all characters valid).
//	 *
//	 * @param maze the maze to be solved that must be valid
//	 * @param s a sequencer that determines the method of solving
//	 */
//	public Maze(char [][] maze, Sequencer s) {
//		int startCount = 0;           // Have found no start locations.
//		int targetCount = 0;          // Have found no target locations
//		boolean errorFound = false;   // Have we found an error in the input maze?
//
//		seq = s;
//		isDone = false;
//
//		// Create a copy of the maze, after checking it for errors.
//		theMaze = new char [maze.length][maze[0].length];
//
//		for (int row = 0; row < maze.length; row++)
//		  for (int col = 0; col < maze[row].length; col++) {
//		  	char symbol = maze[row] [col];
//		    theMaze[row][col] = symbol;
//
//		  	if (symbol == START) {
//		  		startCount++;
//		  		seq.add(row, col, null);  // start has no predecessor
//		  	}
//		  	else if (symbol == TARGET) {
//		  	  targetCount++;
//					seq.setTarget(row, col);
//		  	}
//		  	else if (symbol != EMPTY & symbol != WALL) {
//		  		if (!errorFound) {  // Only want to report this error once
//	  				JOptionPane.showMessageDialog(null, "Invalid symbol '" + symbol +
//	  						"' found in maze.",
//	          		"Error", JOptionPane.ERROR_MESSAGE);
//	  			  errorFound = true;
//	  		  }
//		  	}
//		  }
//
//	  if (startCount > 1) {
//	  	JOptionPane.showMessageDialog(null, "Multiple start symbols in maze.",
//      		"Error", JOptionPane.ERROR_MESSAGE);
//	    errorFound = true;
//    }
//	  else if(startCount == 0) {
//  		JOptionPane.showMessageDialog(null, "No start symbol in maze.",
//      		"Error", JOptionPane.ERROR_MESSAGE);
//	    errorFound = true;
//    }
//
//	  if (targetCount > 1) {
//		  JOptionPane.showMessageDialog(null, "Multiple target symbols in maze.",
//      		"Error", JOptionPane.ERROR_MESSAGE);
//	    errorFound = true;
//    }
//	  else if(targetCount == 0) {
//		  JOptionPane.showMessageDialog(null, "No target symbol in maze.",
//      		"Error", JOptionPane.ERROR_MESSAGE);
//	    errorFound = true;
//    }
//
//		if (errorFound)
//			theMaze = null;    // Don't have a valid maze, so can't solve
//	}
//
//	/**
//	 * Returns true if this maze object has a valid theMaze.
//	 * If error found in constructor it will not.
//	 *
//	 * @return true if the maze is valid false otherwise
//	 */
//	public boolean isValid() {
//		return theMaze != null;
//	}
//
//	/**
//	 * Step the maze, assuming exists and is not done
//	 */
//	public void stepMaze() {
//		if (!isDone && theMaze != null)
//			if (!seq.hasNext())
//				isDone = true;
//			else {
//				SequenceItem current = seq.next();
//				int row = current.getRow();
//				int col = current.getCol();
//				if (theMaze[row][col] == TARGET) {
//					isDone = true;
//				  tracePath(current.getPrevious());
//				}
//				else if (theMaze[row][col] == EMPTY || theMaze[row][col] == START) {
//				  addIfValid(row-1, col, current);
//				  addIfValid(row+1, col, current);
//				  addIfValid(row, col-1, current);
//				  addIfValid(row, col+1, current);
//				  if (theMaze[row][col] == EMPTY)    // Don't change START
//				  		theMaze[row][col] = EXPLORED;
//				}
//			}
//	}
//
//	/**
//	 * Add to the sequencer seq if the row and column are in
//	 * the maze and the position is EMPTY or TARGET
//	 *
//	 * @param row the x coordinate of a viable new position
//	 * @param col the y coordinate of a viable new position
//	 * @param current the current state
//	 */
//	private void addIfValid(int row, int col, SequenceItem current) {
//		if((0 <= row && row < numRows() && 0 <= col && col < numCols())
//				&& (theMaze[row][col] == EMPTY || theMaze[row][col] == TARGET))
//			seq.add(row, col, current);
//	}
//
//	/**
//	 * Get the number of rows
//	 * @return the number of rows in the maze
//	 */
//	public int numRows()  {
//		return theMaze.length;
//	}
//
//	/**
//	 * Get the number of columns
//	 * @return the number of columns in the maze
//	 */
//	public int numCols()  {
//		return theMaze[0].length;
//	}
//
//	/**
//	 * Get the value of a square in the maze
//	 * @param row the x coordinate of the position you want to check
//	 * @param col the y coordinate of the position you want to check
//	 * @return Return the value of this maze square
//	 */
//	public char get(int row, int col)  {
//		return theMaze[row][col];
//	}
//
//	/**
//	 * Updates the maze to trace the path found from where we are to the
//	 * start location.  Assumes that last is not null.
//	 *
//	 * @param last the last position we were on
//	 */
//	private void tracePath(SequenceItem last) {
//		for (SequenceItem cur = last;
//		     cur.getPrevious() != null;
//		     cur = cur.getPrevious())
//			theMaze[cur.getRow()][cur.getCol()] = ON_PATH;
//	}
//
//	/**
//	 * Is the maze search finished?
//	 * @return true if the maze search is finished.
//	 */
//	public boolean searchDone() {
//		return isDone;
//	}
//
//	/**
//	 * Prints the maze
//	 */
//	public void printMaze() {
//		MazeIO.writeMaze(theMaze);
//	}
//
//	/**
//	 *  Test program
//	 */
//	public static void main(String[] args) {
//		Maze maze = new Maze(
//				MazeIO.readMaze("data"),
//				new AStarSequencer());
//
//		while (!maze.searchDone()) {
//			maze.stepMaze();
//			maze.printMaze();
//		}
//	}
//}
