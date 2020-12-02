/**
 * Holds a sequence item for an A* sequencer.
 * 
 * @author Scot Drysdale
 */
public class AStarSequenceItem extends SequenceItem {
  private int pathLength;  // The length of the path back to START
  
	/**
	 * row and column are position in maze, previous the previous
	 * item in the path, key is the priority value.
	 * 
	 * @param row row of item
	 * @param col column of item
	 * @param previous the previous item
	 * @param length the length of the path so far
	 */
	public AStarSequenceItem (int row, int col, AStarSequenceItem previous, int length) {
		super(row, col, previous);
		pathLength = length;
	}
	
	/**
	 * What is the length of the path so far?
	 * @returns the length of the path back to START.
	 */
	public int getPathLength() {
		return pathLength;
	}
}
