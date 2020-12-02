//import java.awt.*;
//import javax.swing.*;
//
///**
// * The drawing surface for a maze.  <p>
// * Modeled on ColonyPanel.java from CS 5 Life lab.
// *
// * @author Scot Drysdale
// */
//
//public class MazePanel extends JPanel {
//	private static final long serialVersionUID = 1L;
//
//	private Maze maze;       // The current maze to be drawn.
//
//	// Colors for various types of maze cells
//	private static final Color BACKGROUND_COLOR = Color.yellow;
//	private static final Color EMPTY_COLOR = Color.white;
//	private static final Color EXPLORED_COLOR = Color.gray;
//	private static final Color WALL_COLOR = Color.black;
//	private static final Color ON_PATH_COLOR = Color.cyan;
//	private static final Color START_COLOR = Color.green;
//	private static final Color TARGET_COLOR = Color.red;
//	private static final Color DEFAULT_COLOR = Color.magenta;
//
//	/**
//	 * Construct a panel with no maze displayed
//	 */
//	public MazePanel()	{
//		maze = null;           // Will be set later via setMaze
//		setBackground(BACKGROUND_COLOR);
//	}
//
//	/**
//	 * Used to update the maze to be displayed
//	 * @param aMaze a maze that you want to be displayed
//	 */
//	public void setMaze(Maze aMaze) {
//		maze = aMaze;
//	}
//
//	/**
//	 * Draws the whole panel
//	 */
//	public void paintComponent(Graphics page)
//	{
//		super.paintComponent(page);
//		display(page);
//	}
//
//	/**
//	 * Does the actual work of drawing the maze
//	 * @param page the page to which you want to draw
//	 */
//	public void display(Graphics page)
//	{
//		if (maze == null) return;    // Nothing to paint if no maze
//
//		// Get dimensions of panel and maze
//		int panelWidth = getWidth();
//    int panelHeight = getHeight();
//    int numRows = maze.numRows();
//    int numCols = maze.numCols();
//
//    // Based on current panel size, figure maximum square size and
//    // where the upper left corner of the maze should be to center it
//    int squareSize = Math.min(panelHeight/numRows, panelWidth/numCols);
//    int upperLeftX = (panelWidth - numCols*squareSize)/2;
//    int upperLeftY = (panelHeight - numRows*squareSize)/2;
//
//    // Draw the maze
//		for (int row = 0; row < numRows; row++)
//			for (int col = 0; col < numCols; col++)
//			{
//				Color cellColor;   // Color for this cell
//
//				switch (maze.get(row, col)) {
//
//				case Maze.EMPTY:
//					cellColor = EMPTY_COLOR;
//			    break;
//				case Maze.WALL:
//					cellColor = WALL_COLOR;
//			    break;
//				case Maze.EXPLORED:
//					cellColor = EXPLORED_COLOR;
//			    break;
//				case Maze.ON_PATH:
//					cellColor = ON_PATH_COLOR;
//			    break;
//				case Maze.TARGET:
//					cellColor = TARGET_COLOR;
//					break;
//				case Maze.START:
//					cellColor = START_COLOR;
//			    break;
//				default:  // Should never occur
//					cellColor = DEFAULT_COLOR;
//				}
//
//				page.setColor(cellColor);
//				page.fillRect(upperLeftX + col*squareSize,
//						upperLeftY + row*squareSize, squareSize, squareSize);
//			}
//	}
//}
