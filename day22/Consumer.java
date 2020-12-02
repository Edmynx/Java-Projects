/**
 * Takes messages from the box for ProducerConsumer
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out helper classes
 */
public class Consumer extends Thread {
	private MessageBox box;

	public Consumer(MessageBox box) {
		this.box = box;
	}

	/**
	 * Takes messages from the box and prints them, until receiving EOF
	 */
	public void run() {
		try {
			String message;
			while (!(message = box.take()).equals("EOF")) {
				System.out.println(message);
			}
		}
		catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}