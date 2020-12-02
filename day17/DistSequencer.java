import java.util.PriorityQueue;

/**
 * Uses a priority queue to sequence the squares of the maze as explored.
 * Priority is L1 distance to the target.
 * 
 * @author Scot Drysdale
 * @see Sequencer
 */
public class DistSequencer implements Sequencer {
	private PriorityQueue<PQSequenceItem> pq;
	private int targetRow, targetCol;     // row and column of the target
	
	/**
	 *  Constructor.  targetX and targetY will be initialized by setTarget
	 */
	public DistSequencer() {
		pq = new PriorityQueue<PQSequenceItem>();
	}
	
	/**
	 *  Add entry for this row and column to the Sequencer.
	 *
	 * prev must be a PQSequenceItem, although the interface for add
	 *   requires a SequenceItem. <p>
	 * Uses L1 distance to target as the key.
	 * <p>{@inheritDoc}
	 */
	public void add(int row, int col, SequenceItem prev) {
		int dist = Math.abs(row-targetRow) + Math.abs(col-targetCol);
		pq.add(new PQSequenceItem(row, col, (PQSequenceItem) prev,dist));
	}
	
	/**
	 * To allow subclasses to add items to the priority queue.
	 * @param item item to be inserted
	 */
	protected void add(PQSequenceItem item) {
		pq.add(item);
	}
	
	/**
	 * @return the next obj from the sequencer and remove it 
	 */
	public SequenceItem next() {
		return (SequenceItem) pq.remove();
	}
	
	/**
	 *  @return true iff this sequencer is has more items
	 */
	public boolean hasNext() {
		return !pq.isEmpty();
	}
	
	/**
	 *  Save the target location
	 *  <p>{@inheritDoc}
	 */
	public void setTarget(int row, int col) {
		targetRow = row;
		targetCol = col;
	}
	
	/**
	 * Get the target row
	 * @return the target row
	 */
	public int getTargetRow() {
		return targetRow;
	}
	
	/**
	 * Get the target column
	 * @return the target column 
	 */
	public int getTargetCol() {
		return targetCol;
	}
}
