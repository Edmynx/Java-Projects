/**
 * Provides methods to add things to be sequenced and to get (and remove)
 * the next square to be explored.  Generalized queue.
 * Also keeps track of previous sequence item, so can trace path back. 
 * 
 * @author Scot Drysdale 
 */
public interface Sequencer {
	
	/**
	 * Add entry for this row and column to the Sequencer.
	 *
	 * @param row given row
	 * @param col given column
	 * @param prev the previous sequence item, the one that generated this one
	 */
	public void add(int row, int col, SequenceItem prev);
	
	/**
	 * Remove and return the next obj from the sequencer
	 * 
	 * @return the next item in the sequencer
	 */
	public SequenceItem next();
	
	/**
	 * Is there a next item?
	 * 
	 * @return true iff this sequencer has a next item
	 */
	public boolean hasNext();

	/**
	 * Tells the sequencer where the target is, for use in priority
	 * queue applications.
	 * 
	 * @param row row of target
	 * @param col column of target
	 */
	public void setTarget(int row, int col);

}
