/**
 * Helper for IncrementerSync
 * Packages up the total in an object with a synchronized increment method
 */
public class IncrementerTotal {
	int total = 0;
	public synchronized void inc() {
		total++;
	}
}