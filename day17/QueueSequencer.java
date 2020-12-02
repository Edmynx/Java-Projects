import java.util.LinkedList;
import java.util.Queue;

/**
 * Uses a queue to sequence the squares of the maze as explored.
 * 
 * @author Scot Drysdale 
 * @see Sequencer
 */
public class QueueSequencer implements Sequencer {
	private Queue<SequenceItem> theQueue;
	
	/**
	 * Constructor that initializes a queue
	 */
	public QueueSequencer() {
		theQueue = new LinkedList<SequenceItem>();
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public void add(int row, int col, SequenceItem prev) {
		theQueue.add(new SequenceItem(row, col, prev));
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public SequenceItem next() {
		return theQueue.remove();
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public boolean hasNext() {
		return !theQueue.isEmpty();
	}
	
	/**
	 * Not needed for queue sequencer.
	 * <p>{@inheritDoc}
	 */
	public void setTarget(int row, int col) {}

}
