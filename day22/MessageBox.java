/**
 * Coordinates sending and receiving a message for ProducerConsumer
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out helper classes
 *
 */
public class MessageBox {
	private String message = null;

	/**
	 * Put m as message once it's okay to do so (current message has been taken)
	 */
	public synchronized void put(String m) throws InterruptedException {
		//check to see if message is not null, might have been woken by put() notifyAll
		while (message != null) { 
			wait();
		}
		message = m;
		notifyAll(); //wakes producers AND consumers
	}

	/**
	 * Takes message once it's there, leaving empty message
	 */
	public synchronized String take() throws InterruptedException {
		//check to see if message is null, might have been woken by take() notifyAll
		while (message == null) {
			wait();
		}
		String m = message;
		message = null;
		notifyAll();  //wakes producers AND consumers
		return m;
	}
}