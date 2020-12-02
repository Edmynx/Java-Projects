import java.util.*;

/**
 * Uses a stack to sequence the squares of the maze as explored. 
 *
 * @author Scot Drysdale
 * @see Sequencer
 */
public class StackSequencer implements Sequencer {
	private Stack<SequenceItem> theStack;
	
	/**
	 * Constructor that initializes a stack
	 */
	public StackSequencer() {
		theStack = new Stack<SequenceItem>();
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public void add(int row, int col, SequenceItem prev) {
		theStack.push(new SequenceItem(row, col, prev));
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public SequenceItem next() {
		return theStack.pop();
	}
	
	/**
	 * <p>{@inheritDoc}
	 */
	public boolean hasNext() {
		return !theStack.isEmpty();
	}
	
	/**
	 * Not needed for stack sequencer.
	 * <p>{@inheritDoc}
	 */
	public void setTarget(int row, int col) {}

}
