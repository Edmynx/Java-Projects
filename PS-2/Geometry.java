/**
 * Geometry helper methods
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Fall 2016, separated from quadtree, instrumented to count calls
 * 
 */
public class Geometry {
	private static int numInCircleTests = 0;				// keeps track of how many times pointInCircle has been called
	private static int numCircleRectangleTests = 0;			// keeps track of how many times circleIntersectsRectangle has been called
	private static int numInRectangleTests = 0;				// keeps track of how many times pointRectangle has been called
	private static int numRectangleRectangleTests = 0;		// keeps track of how many times rectangleIntersectsRectangle has been called

	public static int getNumInCircleTests() {
		return numInCircleTests;
	}

	public static void resetNumInCircleTests() {
		numInCircleTests = 0;
	}

	public static int getNumCircleRectangleTests() {
		return numCircleRectangleTests;
	}

	public static void resetNumCircleRectangleTests() {
		numCircleRectangleTests = 0;
	}

	public static int getNumInRectangleTestsTests() {
		return numInRectangleTests;
	}

	public static void resetNumRectangleTests() {
		numInRectangleTests = 0;
	}

	public static int getNumRectangleRectangleTests() {
		return numRectangleRectangleTests;
	}

	public static void resetNumRectangleRectangleTests() {
		numRectangleRectangleTests = 0;
	}

	/**
	 * Returns whether or not the point is within the circle
	 * @param px		point x coord
	 * @param py		point y coord
	 * @param cx		circle center x
	 * @param cy		circle center y
	 * @param cr		circle radius
	 */
	public static boolean pointInCircle(double px, double py, double cx, double cy, double cr) {
		numInCircleTests++;
		return (px-cx)*(px-cx) + (py-cy)*(py-cy) <= cr*cr;
	}

	/**
	 * Returns whether or not the point is within the rectangle
	 * @param px		point x coord
	 * @param py		point y coord
	 * @param x1		rectangle top left x
	 * @param y1		rectangle top left y
	 * @param x2		rectangle bottom right x
	 * @param y2		rectangle bottom right y
	 */
	public static boolean pointInRectangle(double px, double py, double x1, double y1, double x2, double y2) {
		numInRectangleTests++;
		return (px >= x1 && px <= x2 && py >= y1 && py <= y2);
	}

	/**
	 * Returns whether or not the circle intersects the rectangle
	 * Based on discussion at http://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
	 * @param cx	circle center x
	 * @param cy	circle center y
	 * @param cr	circle radius
	 * @param x1 	rectangle min x
	 * @param y1  	rectangle min y
	 * @param x2  	rectangle max x
	 * @param y2  	rectangle max y
	 */
	public static boolean circleIntersectsRectangle(double cx, double cy, double cr, double x1, double y1, double x2, double y2) {
		numCircleRectangleTests++;
		double closestX = Math.min(Math.max(cx, x1), x2);
		double closestY = Math.min(Math.max(cy, y1), y2);
		return (cx-closestX)*(cx-closestX) + (cy-closestY)*(cy-closestY) <= cr*cr;
	}

	/**
	 * Returns whether or not rectangle 1 circle intersects rectangle 2
	 * @param px1	rectangle 1 top left x
	 * @param py1	rectangle 1 top lef y
	 * @param px2	rectangle 1 bottom right x
	 * @param py2   rectangle 1 bottom right y
	 * @param x1 	rectangle 2 top left x
	 * @param y1  	rectangle 2 top left y
	 * @param x2  	rectangle 2 bottom right x
	 * @param y2  	rectangle 2 bottom right y
	 */
	public static boolean rectangleIntersectsRectangle(double px1, double py1, double px2, double py2, double x1, double y1, double x2, double y2) {
		numRectangleRectangleTests++;
		if (px2 < x1 || x2 < px1) return false;
		if (py1 < y2 || y1 < py2) return  false;
		return true;
	}
}
