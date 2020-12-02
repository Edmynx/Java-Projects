import java.io.*;
import java.net.Socket;

/**
 * Handles communication between the server and one client, for SketchServer
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; revised Winter 2014 to separate SketchServerCommunicator
 */
public class SketchServerCommunicator extends Thread {
	private Socket sock;					// to talk with client
	private BufferedReader in;				// from client
	private PrintWriter out;				// to client
	private SketchServer server;			// handling communication for

	public SketchServerCommunicator(Socket sock, SketchServer server) {
		this.sock = sock;
		this.server = server;
	}

	/**
	 * Sends a message to the client
	 * @param msg
	 */
	public void send(String msg) {
		out.println(msg);
	}
	
	/**
	 * Keeps listening for and handling (your code) messages from the client
	 */
	public void run() {
		System.out.println("\nSERVER COMMUNICATOR has started");
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE
			System.out.println("Server's sketch: " + server.getSketch().getShapeMap());
			for (int id: server.getSketch().getShapeMap().navigableKeySet()){
				//if (shapeString.split("[], ] +")[0].equals("polyline"))
				if (server.getSketch().getShapeMap().get(id) instanceof Polyline) {
					int segmentCount = 1;
					for (Segment segment : ((Polyline) server.getSketch().getShapeMap().get(id)).getSegments()) {
						if (segmentCount > 1 ) {
							send("draw " + id + " polyline" + segment.toString());
						}

						else {
							send("add polyline" +segment.toString());
						}

						segmentCount++;
					}

					System.out.println("Has added polyline to EDITOR's (newbie's) sketch from SERVER's sketch");

				}

				else {
					String shapeString = server.getSketch().getShapeMap().get(id).toString();
					send("add " + shapeString);
					System.out.println("Has added " + shapeString.split(" ")[0] + " to EDITOR's (newbie's) sketch from SERVER's sketch");
				}
			}

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			String line;;
			while ((line = in.readLine()) != null) { // coming from comm in editor
				System.out.println("\nSERVER COMMUNICATOR at work:\n\t Just received a message from the editor:");
				System.out.println("\t\tMessage: " + line);
				String[] tokens = StringInterpreter.unpackString(line);
				System.out.println("\t\tMessage broken into pieces: ");
				int i = 1;
				for (String s: tokens) {
					System.out.println("\t\t\tPiece " + i +  ": " + s);
					i++;
				}

				System.out.println("\t\tRequest SERVER to broadcast message to EDITORS (clients)");
				server.broadcast(line);

				if (tokens[0].equals("draw") || tokens[0].equals("move") || tokens[0].equals("recolor") || tokens[0].equals("delete")) {
					System.out.println("\n\t\tAn EDITOR has requested to make changes to an EXISTING shape in SERVER's sketch");
					StringInterpreter.performAction(tokens, server.getSketch().getShapeMap());
				}

				else {
					System.out.println("\n\t\tAn EDITOR has requested to add NEW shape to SERVER's sketch");
					server.getSketch().addShape(StringInterpreter.createShape(tokens));
				}

				//String string = "";
				//string += tokens[0].charAt(tokens.length - 1) + tokens[0].charAt(tokens.length - 2) + tokens[0].charAt(tokens.length - 3);
				//if (string.equals("add")) server.getSketch().addShape(StringInterpreter.createShape(tokens));
			}


				//else {
					//System.out.println(line);
				///	server.getSketch().getShapeMap().put(server.getSketch().generateID(), StringInterpreter.unpackString(line));
				//	server.broadcast(line);
			//	}


			// if input == "Please send me an integer!"
			// server.getSketch().generateID()

			// Clean up -- note that also remove self from server's list so it doesn't broadcast here
			server.removeCommunicator(this);
			out.close();
			in.close();
			sock.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
