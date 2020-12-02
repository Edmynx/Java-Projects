/**
 * Animated blob, defined by a position and size.
 * Version 0: just core instance variables and a main method.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016, based on animated agents from previous terms
 * @author Tim Pierson, Darmouth CS 10, Spring 2018, added setX method
 */

public class Blob01 {
	protected double x, y;		// x,y position on screen
	protected double r;			// radius

	/**
	 * Sets the x instance variable to value passed in.  Take a look at the 'this' keyword.  Why is it there?  
	 * Also demonstrate the Javadoc feature by mousing over bob.setX(500) on line 26 below.
	 * 
	 * @param x  value that instance variable x will set to 
	 */
	public void setX(double x) {
		this.x = x;
	}

	public static void main(String[] args) {
		Blob01 bob = new Blob01();
		System.out.println("bob starts at ("+bob.x+","+bob.y+")");
		bob.setX(500);
		System.out.println("bob is now at ("+bob.x+","+bob.y+")");
	}
}
