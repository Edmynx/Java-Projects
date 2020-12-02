import java.awt.*;
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
		try {
			System.out.println("someone connected");
			
			// Communication channel
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new PrintWriter(sock.getOutputStream(), true);

			// Tell the client the current state of the world
			// TODO: YOUR CODE HERE
			for (int id: server.getSketch().getShapeMap().navigableKeySet()){
				String shapestring = server.getSketch().getShapeMap().get(id).toString();
				System.out.println("These are the things already in our Map!" + shapestring);
				send(shapestring);
			}

			out.print(this.server.getSketch()); // theoretically prints out, to the client, the sketch object?

			// Keep getting and handling messages from the client
			// TODO: YOUR CODE HERE
			String line;
			while ((line = in.readLine()) != null) { // coming from comm in editor

				System.out.println("This was just communicated to the server:");
				System.out.println(line);

				String[] splitLine = line.split(" ");

				if (splitLine[0].equals("Delete")){ // Delete movingID
					server.getSketch().getShapeMap().remove(Integer.parseInt(splitLine[1]));
					System.out.println("Broadcasting: " + line);
					server.broadcast(line);

				}

				else if (splitLine[0].equals("Recolor")){ // Recolor MovingID color
					Color color = new Color(Integer.parseInt(splitLine[2]));
					server.getSketch().getShapeMap().get(Integer.parseInt(splitLine[1])).setColor(color);
					server.broadcast(line);
				}

				else if (splitLine[0].equals("Move")){ // Move MovingID x y
					server.getSketch().getShapeMap().get(Integer.parseInt(splitLine[1])).moveBy(Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]));
					server.broadcast(line);
				}

				else if (splitLine[0].equals("SetCornersR")){
					((Rectangle)(server.getSketch().getShapeMap().get(Integer.parseInt(splitLine[1])))).setCorners(Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), Integer.parseInt(splitLine[5]));
					server.broadcast(line);
				}

				else if (splitLine[0].equals("SetCornersE")){
					System.out.println(server.getSketch().getShapeMap().keySet());
					((Ellipse)(server.getSketch().getShapeMap().get(Integer.parseInt(splitLine[1])))).setCorners(Integer.parseInt(splitLine[2]), Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[4]), Integer.parseInt(splitLine[5]));
					server.broadcast(line);
				}

				else{

					System.out.println("Server just made a new shape b/c of this command: \n" + line);
					int id = server.getSketch().generateID();
					System.out.println("That shape has ID:  " + id + "\n\n");
					server.getSketch().getShapeMap().put(id, StringDecomposer.unpackString(line));
					System.out.println("This is that shape:  " + StringDecomposer.unpackString(line));
					server.broadcast(line + " " + id); // you're not sending the ID along with the object
				}

			}
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
