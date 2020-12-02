import java.util.ArrayList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position, 
 * with children at the subdivided quadrants
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, explicit rectangle
 * @author CBK, Fall 2016, generic with Point2D interface
 * 
 */
public class PointQuadtree<E extends Point2D> {
	private E point;							// the point anchoring this node
	private int x1, y1;							// upper-left corner of the region
	private int x2, y2;							// bottom-right corner of the region
	private PointQuadtree<E> c1, c2, c3, c4;	// children
	private static final int MAXNUMBER = 4;	// number of children this PointQuadtree can have

	/**
	 * Initializes a leaf quadtree, holding the point in the rectangle
	 */
	public PointQuadtree(E point, int x1, int y1, int x2, int y2) {
		this.point = point;
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
	}

	// Getters
	
	public E getPoint() {
		return point;
	}

	public int getX1() {
		return x1;
	}

	public int getY1() {
		return y1;
	}

	public int getX2() {
		return x2;
	}

	public int getY2() {
		return y2;
	}

	/**
	 * Returns the child (if any) at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public PointQuadtree<E> getChild(int quadrant) {
		if (quadrant==1) return c1;
		if (quadrant==2) return c2;
		if (quadrant==3) return c3;
		if (quadrant==4) return c4;
		return null;
	}

	/**
	 * Returns whether or not there is a child at the given quadrant, 1-4
	 * @param quadrant	1 through 4
	 */
	public boolean hasChild(int quadrant) {
		return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
	}

	/**
	 * Inserts the point into the tree
	 */
	public void insert(E p2) {
		// TODO: YOUR CODE HERE
		// If p2's y is between point's y and region's top y, p2 is in either quadrant 1 or 2
		if (p2.getY() >= getY1() && p2.getY() <= point.getY()){

			// If p2's x is between point's x and region's right x, p2 is in quadrant 1
			if (p2.getX() >= point.getX() && p2.getX() <= getX2()){
				if (hasChild(1)){
					// Recursively call insert on child if there is one in place
					getChild(1).insert(p2);
				}

				// Otherwise, make p2 the child
				else{
					c1 = new PointQuadtree<>(p2, (int) point.getX(), getY1(), getX2(), (int) point.getY());
				}
			}

			//Otherwise, p2 is in quadrant 2
			else if (p2.getX() >= getX1() && p2.getX() <= point.getX()){
				if (hasChild(2)) {
					// Recursively call insert on child if there is one in place
					getChild(2).insert(p2);
				}

				// Otherwise, make p2 the child
				else{
					c2 = new PointQuadtree<>(p2, getX1(), getY1(), (int) point.getX(), (int) point.getY());
				}
			}
		}

		// Otherwise, p2 is in either quadrant 3 or 4
		else if (p2.getY() >= point.getY() && p2.getY() <= getY2()){

			// If p2's x is between point's x and region's left x, p2 is in quadrant 3
			if (p2.getX() >= getX1() && p2.getX() <= point.getX()){
				// Recursively call insert on child if there is one in place
				if (hasChild(3)){
					getChild(3).insert(p2);
				}

				// Otherwise, make p2 the child
				else{
					c3 = new PointQuadtree<>(p2, getX1(), (int) point.getY(), (int) point.getX(), getY2());
				}
			}

			// Otherwise, p2 is in quadrant 4
			else if (p2.getX() >= point.getX() && p2.getX() <= getX2()){
				// Recursively call insert on child if there is one in place
				if (hasChild(4)){
					getChild(4).insert(p2);
				}

				// Otherwise, make p2 the child
				else{
					c4 = new PointQuadtree<>(p2, (int) point.getX(), (int) point.getY(), getX2(), getY2());
				}
			}
		}
	}
	
	/**
	 * Finds the number of points in the quadtree (including its descendants)
	 */
	public int size() {
		// TODO: YOUR CODE HERE
		int num = 1;
		for (int i = 1; i <= MAXNUMBER; i++){
			if (hasChild(i)){
				num += getChild(i).size();
			}
		}
		return  num;
	}
	
	/**
	 * Builds a list of all the points in the quadtree (including its descendants)
	 */
	public List<E> allPoints() {
		// TODO: YOUR CODE HERE
		List<E> list = new ArrayList<>();
		allPointsHelper(list);
		return list;
	}	

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param cx	circle center x
	 * @param cy  	circle center y
	 * @param cr  	circle radius
	 * @return    	the points in the circle (and the qt's rectangle)
	 */
	public List<E> findInCircle(double cx, double cy, double cr) {
		// TODO: YOUR CODE HERE
		List<E> list = new ArrayList<E>();
		findInCircleHelper(cx, cy, cr, list);
		return list;
	}

	/**
	 * Uses the quadtree to find all points within the circle
	 * @param px1	top left x
	 * @param px2  	bottom right x
	 * @param py1  	top left y
	 * @param py2   bottom right y
	 * @return    	the points in the rectangle (and the qt's rectangle)
	 */
	public List<E> findInRectangle(double px1, double py1, double px2, double py2){
		List<E> list = new ArrayList<E>();
		findInRectangleHelper(px1, py1, px2, py2, list);
		return list;
	}

	// TODO: YOUR CODE HERE for any helper methods
	// Helper for allPoints
	public void allPointsHelper(List<E> list){
		list.add(point);
		for (int i = 1; i <= MAXNUMBER; i++){
			if (hasChild(i)){
				getChild(i).allPointsHelper(list);
			}
		}
	}

	// Helper for findInCircle
	public void findInCircleHelper(double cx, double cy, double cr, List<E> list){

		// Test if point's region intersects given circle
		if (Geometry.circleIntersectsRectangle(cx, cy, cr, getX1(), getY1(), getX2(), getY2())){

			// Test if point is in region
			if (Geometry.pointInCircle(point.getX(), point.getY(), cx, cy, cr)){
				list.add(point);
			}

			// Recursively call findInCircleHelper on all the children of that region
			for (int i = 1; i <= MAXNUMBER; i++){
				if (hasChild(i)){
					getChild(i).findInCircleHelper(cx, cy, cr, list);
				}
			}
		}
	}

	// Helper for findInCircle
	public void findInRectangleHelper(double px1, double py1, double px2, double py2, List<E> list){
		// Test if point's region intersects given rectangle
		if (Geometry.rectangleIntersectsRectangle(px1, py1, px2, py2, getX1(), getY1(), getX2(), getY2())){
			if (Geometry.pointInRectangle(point.getX(), point.getY(), px1, py1, px2, py2)) {
				list.add(point);
			}
			for (int i = 1; i <= MAXNUMBER; i++) {
				if (hasChild(i)) {
					getChild(i).findInRectangleHelper(px1, py1, px2, py2, list);
				}
			}
		}

	}
}
