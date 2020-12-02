/**
 * A resource that can be acquired and released (among DiningPhilosophers)
 * 
 *  @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out Fork from DiningPhilosophers
 */
public class Fork {
	private boolean available = true;

	public synchronized void acquire() throws InterruptedException {
		while (!available) {
			wait();
		}
		available = false;
	}

	public synchronized void release() {
		available = true;
		notifyAll();
	}
}

