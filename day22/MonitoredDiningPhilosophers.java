import java.util.*;

/**
 * Dining philosophers, avoiding deadlock (but not necessarily starvation)
 * by coordinated acquisition
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out MonitoredFork and MonitoredPhilosopher
 */
public class MonitoredDiningPhilosophers {
	private ArrayList<MonitoredPhilosopher> philosophers;

	/**
	 * Creates the forks and philosophers
	 */
	public MonitoredDiningPhilosophers() {
		ArrayList<MonitoredFork> forks = new ArrayList<MonitoredFork>();
		for (int fork = 0; fork < 5; fork++) {
			forks.add(new MonitoredFork());
		}

		philosophers = new ArrayList<MonitoredPhilosopher>();
		for (int phil = 0; phil < 5; phil++) {
			philosophers.add(new MonitoredPhilosopher(this, phil, forks.get(phil), forks.get((phil+1)%5)));
		}
	}

	/**
	 * Gets each philosopher started at the table
	 */
	public void dine() {
		for (MonitoredPhilosopher phil : philosophers) {
			phil.start();
		}
	}

	/**
	 * Simultaneously acquires both resources
	 */
	public synchronized void acquire(MonitoredFork left, MonitoredFork right) throws InterruptedException {
		while (!left.available || !right.available) {
			wait();
		}
		left.available = false;
		right.available = false;
	}

	/**
	 * Releases both resources
	 */
	public synchronized void release(MonitoredFork left, MonitoredFork right) {
		left.available = true;
		right.available = true;
		notifyAll();
	}

	public static void main(String[] args) {
		new MonitoredDiningPhilosophers().dine();
	}
}
