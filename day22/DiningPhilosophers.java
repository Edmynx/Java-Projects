import java.util.*;

/**
 * Dining philosophers, deadlocking by independent fork acquisition
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out Fork and Philosopher
 */
public class DiningPhilosophers {
	private ArrayList<Philosopher> philosophers;

	/**
	 * Creates the forks and philosophers
	 */
	public DiningPhilosophers() {
		ArrayList<Fork> forks = new ArrayList<Fork>();
		for (int fork = 0; fork < 5; fork++) {
			forks.add(new Fork());
		}

		philosophers = new ArrayList<Philosopher>();
		for (int phil = 0; phil < 5; phil++) {
			philosophers.add(new Philosopher(phil, forks.get(phil), forks.get((phil+1)%5)));
		}
	}

	/**
	 * Gets each philosopher started at the table
	 */
	public void dine() {
		for (Philosopher phil : philosophers) {
			phil.start();
		}
	}

	public static void main(String[] args) {
		new DiningPhilosophers().dine();
	}
}