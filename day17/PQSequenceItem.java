/**
 * Adds fields and methods to the sequence item to allow it to be used 
 * in a priority queue.
 *  
 * @author Scot Drysdale 
 * @see SequenceItem
 */

public class PQSequenceItem extends SequenceItem implements Comparable<PQSequenceItem> {
	private int key;		// Holds the priority - low priority is best
	
	/**
	 * row and column are position in maze, previous the previous
	 * item in the path, key is the priority value.
	 * 
	 * @param row row of item
	 * @param col column of item
	 * @param previous the previous item
	 * @param theKey the priority key
	 */
	public PQSequenceItem (int row, int col, PQSequenceItem previous, int theKey) {
		super(row, col, previous);
		key = theKey;
	}
	
	/**
	 *  Compares key values
	 *  
	 *  @param other object to compare to key
	 *  @return comparison of keys
	 */
	public int compareTo(PQSequenceItem other) {
		return key -  other.key;
	}
}
