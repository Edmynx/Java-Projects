/**
 * Producer/consumer example
 * Producer sends messages to consumer by way of MessageBox semaphore with synchronized send/receive methods.
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate out helper classes
 * @author Tim Pierson, Dartmouth CS 10, Spring 2019, updated to pass Producer number of messages to send
 */
public class ProducerConsumer {
	public static final int numMessages = 5;	// how many messages to send from producer to consumer
	private Producer producer;
	private Consumer consumer;

	public ProducerConsumer() {
		MessageBox box = new MessageBox();	
		producer = new Producer(box,numMessages);
		consumer = new Consumer(box);
	}

	/**
	 * Just starts the producer and consumer running
	 */
	public void communicate() {
		producer.start();	
		consumer.start();
	}

	public static void main(String[] args) {
		new ProducerConsumer().communicate();
		System.out.println("Peace out! (threads are still running but I'm done)");
	}
}