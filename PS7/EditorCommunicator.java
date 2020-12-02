import java.io.*;
import java.net.Socket;


/**
 * Handles communication to/from the server for the editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Chris Bailey-Kellogg; overall structure substantially revised Winter 2014
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 */
public class EditorCommunicator extends Thread {
	private PrintWriter out;		// to server
	private BufferedReader in;		// from server
	protected Editor editor;		// handling communication for

	/**
	 * Establishes connection and in/out pair
	 */
	public EditorCommunicator(String serverIP, Editor editor) {
		this.editor = editor;
		System.out.println("connecting to " + serverIP + "...");
		try {
			Socket sock = new Socket(serverIP, 4242);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			System.out.println("...connected");
		}
		catch (IOException e) {
			System.err.println("couldn't connect");
			System.exit(-1);
		}
	}

	/**
	 * Sends message to the server
	 */
	public void send(String msg) {
		System.out.println("\nEDITOR COMMUNICATOR at work");
		System.out.println("\tHas sent mesage (" + msg + ") to SERVER COMMUNICATOR");
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {
			// Handle messages
			// TODO: YOUR CODE HERE
			System.out.println("\nEDITOR COMMUNICATOR has started");
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("\nEDITOR COMMUNICATOR at work:\n\t Just received a message from the server:");
				System.out.println("\t\tMessage: " + line);
				String[] tokens = StringInterpreter.unpackString(line);
				System.out.println("\t\tMessage broken into pieces: ");
				int i = 1;
				for (String s: tokens) {
					System.out.println("\t\t\tPiece " + i +  ": " + s);
					i++;
				}
				if (tokens[0].equals("draw") || tokens[0].equals("move") || tokens[0].equals("recolor") || tokens[0].equals("delete")) {
					System.out.println("\n\t\tServer has requested to make changes to an EXISTING shape in EDITOR's sketch");
					StringInterpreter.performAction(tokens, editor.getSketch().getShapeMap());
				}

				else {
					System.out.println("\n\t\tServer has requested to add NEW shape to EDITOR's sketch");
					editor.getSketch().addShape(StringInterpreter.createShape(tokens));

				}

				editor.repaint();
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		finally {
			System.out.println("\n\t\tServer hung up");
		}
	}
}
