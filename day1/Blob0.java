/**
 * Animated blob, defined by a position and size.
 * Version 0: just core instance variables and a main method.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016, based on animated agents from previous terms
 */

public class Blob0 {
	double x, y;		// x,y position on screen
	double r;			// radius

	public static void main(String[] args) {
		Blob0 bob = new Blob0();
		System.out.println("bob starts at ("+bob.x+","+bob.y+")");
		bob.x = 100;
		bob.y = 50;
		System.out.println("bob is now at ("+bob.x+","+bob.y+")");
	}
}
