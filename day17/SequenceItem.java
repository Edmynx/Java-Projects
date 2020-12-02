/**
 * Holds an item in the Sequencer.  Keeps location a previous item,
 * so able to trace solution path back.
 * 
 * Note that two SequenceItems are "equals" if their row and column 
 * values are equal.
 * 
 * @author Scot Drysdale 
 * @see Sequencer
 */
public class SequenceItem {
	private int row, col;             // Position in the array
	private SequenceItem previous;    // Item from which this was discovered
	
	/**
	 * Construct a sequence item
	 * @param r row of the square
	 * @param c column of the square
	 * @param prev previous item (the one that lead to this item)
	 */
	public SequenceItem (int r, int c, SequenceItem prev) {
		row = r;
		col = c;
		previous = prev;
	}
	
	/**
	 * Accessor to get row.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Accessor to get column.
	 */	
	public int getCol() {
		return col;
	}
	
	/**
	 * Accessor previous thing in path.
	 */
	public SequenceItem getPrevious() {
		return previous;
	}
	
	/**
	 * Override equals to say that two SequenceItems are equal iff
	 * their row and column are equal
	 */
	public boolean equals(Object other) {
		if(!(other instanceof SequenceItem))
			throw new IllegalArgumentException("Argument to equals not a SequenceItem");
		
		SequenceItem otherItem = (SequenceItem) other;
		return row == otherItem.row && col == otherItem.col;
	}
	
	/**
	 * Override hashCode so equal items have equal hashCodes
	 */
	public int hashCode() {
		return 47*row + col;
	}
}
