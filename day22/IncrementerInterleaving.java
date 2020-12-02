/** 
 * Shows the interleaving of operations in incrementing.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012, inspired by an example by Scot Drysdale
 */
public class IncrementerInterleaving extends Thread {
	private static int total = 0;  						// a variable shared by all incrementers
	private static final int times = 5;					// how many times to increment total, in each thread
	private String name;								// for display purposes

	public IncrementerInterleaving(String name) {
		this.name = name;
	}

	/**
	 * Increments total the specified number of times
	 */
	public void run() {
		for (int i = 0; i < times; i++) {
			int temp = total;
			System.out.println(name + " gets " + temp);
			temp = temp + 1;
			total = temp;
			System.out.println(name + " puts " + temp);
		}
	}

	public static void main(String [] args) throws Exception {
		IncrementerInterleaving inc1 = new IncrementerInterleaving("one");
		IncrementerInterleaving inc2 = new IncrementerInterleaving("two");

		// Fire off threads and wait for them to complete
		inc1.start();
		inc2.start();
		inc1.join();
		inc2.join();

		System.out.println("total at end = " + total);
	}
}
