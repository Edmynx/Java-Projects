import java.awt.*;
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
		out.println(msg);
	}

	/**
	 * Keeps listening for and handling (your code) messages from the server
	 */
	public void run() {
		try {

			Shape shape;
			System.out.println("EditorCommunicator is online!");
			// Handle messages
			// TODO: YOUR CODE HERE
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println("This was just communicated to the Editor: \n");
				System.out.println(line);
				System.out.println("\n\n");

				String[] lineSplit = line.split(" ");

				System.out.println(lineSplit[0]);

				if (lineSplit[0].equals("Delete")){ // Delete MovingID
					int targetID = Integer.parseInt(lineSplit[1]);
					System.out.println("We're about to try to delete shape " + targetID + " from our Map \n" + editor.getSketch().getShapeMap());
					editor.getSketch().getShapeMap().remove(targetID);
					System.out.println("\n\n\nThis is our map after that removal.");
					System.out.println(editor.getSketch().getShapeMap());
					editor.clearCurr();
				}

				else if (lineSplit[0].equals("Move")){ // Move MovingID x y
					editor.getSketch().getShapeMap().get(Integer.parseInt(lineSplit[1])).moveBy(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]));
					send(line);
				}

				else if (lineSplit[0].equals("Recolor")){
					Color color = new Color(Integer.parseInt(lineSplit[2]));
					editor.getSketch().getShapeMap().get(Integer.parseInt(lineSplit[1])).setColor(color);
					send(line);
				}

				else if (lineSplit[0].equals("SetCornersR")){
					((Rectangle)(editor.getSketch().getShapeMap().get(Integer.parseInt(lineSplit[1])))).setCorners(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]), Integer.parseInt(lineSplit[5]));

				}

				else if (lineSplit[0].equals("SetEndsS")){
					((Segment)(editor.getSketch().getShapeMap().get(Integer.parseInt(lineSplit[1])))).setEnd(Integer.parseInt(lineSplit[4]), Integer.parseInt(lineSplit[5]));

				}

				else if (lineSplit[0].equals("SetEndsP")){
					((Polyline)(editor.getSketch().getShapeMap().get(Integer.parseInt(lineSplit[1])))).addSegment(new Segment(Integer.parseInt(lineSplit[4]), Integer.parseInt(lineSplit[5]), new Color(Integer.parseInt(lineSplit[6]))));

				}


				else if (lineSplit[0].equals("SetCornersE")){
					((Ellipse)(editor.getSketch().getShapeMap().get(Integer.parseInt(lineSplit[1])))).setCorners(Integer.parseInt(lineSplit[2]), Integer.parseInt(lineSplit[3]), Integer.parseInt(lineSplit[4]), Integer.parseInt(lineSplit[5]));

				}

				else {
					shape = StringDecomposer.unpackString(line);
					if (shape!= null){
						int ID = Integer.parseInt(line.split(" ")[line.split(" ").length - 1]);
						System.out.println("The editor is putting this ID into its sketch object.");
						System.out.println(ID);
						editor.getSketch().getShapeMap().put(ID, shape);
						System.out.println("This is what the client map now looks like:");
						System.out.println(editor.getSketch().getShapeMap());
					}

				}

				editor.repaint();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			System.out.println("server hung up");
		}
	}	

	// Send editor requests to the server
	// TODO: YOUR CODE HERE
	
}
