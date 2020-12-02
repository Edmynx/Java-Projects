//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import net.datastructures.*;
//
///**
// * Uses A* search - key value is current path length plus L1 distance
// * to the target.
// *
// * Solves the given maze using a search that depends on cost estimates
// * for the final path length.  Returns the final maze SequenceItem, which
// * has links back to all intermediate SequenceItems.
// *
// * To guarantee optimality of the solution, the estimate of total cost
// * must be both admissible (never overestimate the true cost)
// * and monotone (cost estimate after a move is never less that the cost estimate
// * before the move). The monotone assumption is needed because this search
// * function uses a closed set.  The estimate above meets both criteria.
// *
// * @author Scot Drysdale
// * @see Sequencer
// */
//public class AStarSequencer implements Sequencer {
//	// Priority queue with replace-key capabilities.
//	private AdaptablePriorityQueue<Integer, AStarSequenceItem> pq;
//
//	/* The open and closed sets for A* search.
//	 * We have found the best path to the things in closed.
//	 * Items in open are in the priority queue, and are mapped to the
//	 *   priority queue entry in case it needs updating.
//	 */
//	private Set<AStarSequenceItem> closed;
//	private Map<AStarSequenceItem, Entry<Integer,AStarSequenceItem>> open;
//
//	private int targetRow, targetCol;     // row and column of the target
//
//	/**
//	 *  Constructor.  targetX and targetY will be initialized by setTarget
//	 */
//	public AStarSequencer() {
//		pq = new HeapAdaptablePriorityQueue<Integer, AStarSequenceItem>();
//		closed = new HashSet<AStarSequenceItem>();
//		open = new HashMap<AStarSequenceItem, Entry<Integer,AStarSequenceItem>>();
//	}
//
//
//	/**
//   * <p>{@inheritDoc} <p>
//   * Uses L1 distance to target plus current path length as key <p>
//   * previous must be a AStarSequenceItem, although the interface for add only
//   * requires a SequenceItem.
//	 */
//	public void add(int row, int col, SequenceItem previous) {
//		AStarSequenceItem prev = (AStarSequenceItem) previous;
//		int pathLength;          // Path length to this item
//		if (prev == null)
//			pathLength = 1;
//		else
//			pathLength = prev.getPathLength() + 1;   // One longer than pred.
//
//		// Get the A* total path length estimate
//		int distEst = pathLength + Math.abs(row-getTargetRow()) + Math.abs(col-getTargetCol());
//		AStarSequenceItem newItem = new AStarSequenceItem(row, col, prev, pathLength);
//
//		// If the maze item is in the closed set can ignore it.
//		if(!closed.contains(newItem)) {
//		  // Try to get an earlier version of this SequenceItem
//			Entry<Integer, AStarSequenceItem> oldEntry = open.get(newItem);
//		  if(oldEntry == null) {  // First time this maze position discovered?
//	  	  Entry<Integer, AStarSequenceItem> newEntry = pq.insert(distEst, newItem);
//	  	  open.put(newItem, newEntry);
//		  }
//		  else {  // Found this position before.  Have we found a better path to it?
//		  	AStarSequenceItem oldItem = oldEntry.getValue();
//
//		  	// Note that the maze position is the same, so distance to target is the same
//		  	if(pathLength < oldItem.getPathLength()) {
//          // Update the pq entry to reflect the new distance and value
//			  	pq.replaceKey(oldEntry, distEst);
//			  	pq.replaceValue(oldEntry, newItem);
//			  }
//		  }
//		}
//	}
//
//	/**
//	 * Gets the next item in the sequencer.  Updates open and closed sets.
//	 * @return the next obj from the sequencer and remove it
//	 */
//	public SequenceItem next() {
//    Entry<Integer, AStarSequenceItem> nextEntry = pq.removeMin();
//    AStarSequenceItem item = nextEntry.getValue();
//
//    // Move item from open set to closed set
//		open.remove(item);
//		closed.add(item);
//
//		return item;
//	}
//
//	/**
//	 * Does this sequencer have more items?
//	 * @return true iff this sequencer is has more items
//	 */
//	public boolean hasNext() {
//		return !pq.isEmpty();
//	}
//
//	/**
//	 *  Save the target location
//	 *  <p>{@inheritDoc}
//	 */
//	public void setTarget(int row, int col) {
//		targetRow = row;
//		targetCol = col;
//	}
//
//	/**
//	 * Get the target row
//	 * @return the target row
//	 */
//	public int getTargetRow() {
//		return targetRow;
//	}
//
//	/**
//	 * Get the target column
//	 * @return the target column
//	 */
//	public int getTargetCol() {
//		return targetCol;
//	}
//}
