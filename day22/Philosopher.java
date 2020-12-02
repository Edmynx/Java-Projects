/**
 * A resource consumer that needs to acquire particular resources simultaneously (for DiningPhilosophers)
 *
 *  @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out Philsopher from DiningPhilosophers
 */
public class Philosopher extends Thread {
	private int num;						// for message printout
	private Fork left, right;		// the resources

	public Philosopher(int num, Fork left, Fork right) {
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
			System.out.println(num + " hungry; going for left fork");
			left.acquire();
			System.out.println(num + " got left fork");
			randPause();
			System.out.println(num + " going for right fork");
			right.acquire();
			System.out.println(num + " got right fork; chowing down");
			randPause();
			System.out.println(num + " finished eating; dropping forks");
			right.release();
			left.release();
		}
		catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}
