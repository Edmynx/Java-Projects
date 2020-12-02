/**
 * Animated blob, defined by a position and size.
 * Version 0: just core instance variables and a main method.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016, based on animated agents from previous terms
 * @author Tim Pierson, Darmouth CS 10, Spring 2018, highlight constructor overloading
 */

public class Blob02 {
	protected double x, y, r=5;				// position
	
	public Blob02() {
		// Do nothing; everything has its default value
		// This constructor is implicit unless you provide an alternative
	}
	/**
	 * @param x		initial x coordinate
	 * @param y		initial y coordinate
	 */
	public Blob02(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public static void main(String[] args) {
		Blob02 bob = new Blob02(); //calls first constructor, x=0, y=0, r=5
		Blob02 alice = new Blob02(10,7); //calls second constructor, x=10, y=7, r=5
		System.out.println("bob starts at ("+bob.x+","+bob.y+")");
		System.out.println("alice starts at ("+alice.x+","+alice.y+")");
	}
}
