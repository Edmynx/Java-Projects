/**
 * A resource consumer that needs to acquire particular resources simultaneously (for MonitoredDiningPhilosophers)
 * 
 *  @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out MonitoredPhilsopher from MonitoredDiningPhilosophers
 */
public class MonitoredPhilosopher extends Thread {
	private MonitoredDiningPhilosophers monitor;	// controlling fork acquisition
	private int num;								// for message printout
	private MonitoredFork left, right;				// the resources

	public MonitoredPhilosopher(MonitoredDiningPhilosophers monitor, int num, MonitoredFork left, MonitoredFork right) {
		this.monitor = monitor;
		this.num = num;
		this.left = left;
		this.right = right;
	}

	/**
	 * Waits a bit -- 1 to 5 seconds
	 */
	private void randPause() throws InterruptedException {
		sleep(1000 + (int)(Math.random()*4000));
	}

	/**
	 * Start the rounds of resource acquisition
	 */
	public void run () {
		for (int meal = 0; meal < 3; meal++) {
			eat();
			System.out.println(num + " finished meal " + meal);
		}
		System.out.println(num + " all done");
	}

	/**
	 * One round
	 */
	public void eat() {
		try {
			System.out.println(num + " contemplating the universe, working up an appetite");
			randPause();
			System.out.println(num + " hungry; going for forks");
			monitor.acquire(left, right);
			System.out.println(num + " got forks; chowing down");
			randPause();
			System.out.println(num + " finished eating; dropping forks");
			monitor.release(left, right);
		}
		catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}

