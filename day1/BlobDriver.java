/**
 * Test case for the Blob class
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 */
public class BlobDriver {
	public static void main(String[] args) {
		// Create a couple of blobs
		Blob bob = new Blob(10,20);  //constructors with x and y coordinates
		Blob alice = new Blob(30,40); 
		
		// Set some of their parameters
		bob.setR(2 * alice.getR()); //make bob's radius 2X alice's (alice 5 by default)
		alice.setVelocity(3, 4); //cause alice to move right 3 pixels, and down 4 pixels each step
		bob.setGrowth(10); //make bob's radius increase by 10 each step
		
		// Move/grow them
		alice.step();
		bob.step();
		
		// See where they are and how big
		System.out.println("bob: at "+bob.getX()+","+bob.getY()+"; sized "+bob.getR());
		System.out.println("alice: at "+alice.getX()+","+alice.getY()+"; sized "+alice.getR());
		
	}
}
