import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Client-server graphical editor
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012; loosely based on CS 5 code by Tom Cormen
 * @author CBK, winter 2014, overall structure substantially revised
 * @author Travis Peters, Dartmouth CS 10, Winter 2015; remove EditorCommunicatorStandalone (use echo server for testing)
 * @author CBK, spring 2016 and Fall 2016, restructured Shape and some of the GUI
 */

public class Editor extends JFrame {	
	private static String serverIP = "localhost";			// IP address of sketch server
	// "localhost" for your own machine;
	// or ask a friend for their IP address

	private static final int width = 800, height = 800;		// canvas size

	// Current settings on GUI
	public enum Mode {
		DRAW, MOVE, RECOLOR, DELETE
	}
	private Mode mode = Mode.DRAW;				// drawing/moving/recoloring/deleting objects
	private String shapeType = "ellipse";		// type of object to add
	private Color color = Color.black;			// current drawing color

	// Drawing state
	// these are remnants of my implementation; take them as possible suggestions or ignore them
	private Shape curr = null;					// current shape (if any) being drawn
	private Sketch sketch;						// holds and handles all the completed objects
	private int movingId = -1;					// current shape id (if any; else -1) being moved
	private Point drawFrom = null;				// where the drawing started
	private Point moveFrom = null;				// where object is as it's being dragged


	// Communication
	private EditorCommunicator comm;			// communication with the sketch server

	public Editor() {
		super("Graphical Editor");

		sketch = new Sketch();

		// Connect to server
		comm = new EditorCommunicator(serverIP, this);
		comm.start();

		// Helpers to create the canvas and GUI (buttons, etc.)
		JComponent canvas = setupCanvas();
		JComponent gui = setupGUI();

		// Put the buttons and canvas together into the window
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(canvas, BorderLayout.CENTER);
		cp.add(gui, BorderLayout.NORTH);

		// Usual initialization
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	/**
	 * Creates a component to draw into
	 */
	private JComponent setupCanvas() {
		JComponent canvas = new JComponent() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawSketch(g);
			}
		};
		
		canvas.setPreferredSize(new Dimension(width, height));

		canvas.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent event) {
				handlePress(event.getPoint());
			}

			public void mouseReleased(MouseEvent event) {
				handleRelease();
			}
		});		

		canvas.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent event) {
				handleDrag(event.getPoint());
			}
		});
		
		return canvas;
	}

	/**
	 * Creates a panel with all the buttons
	 */
	private JComponent setupGUI() {
		// Select type of shape
		String[] shapes = {"ellipse", "freehand", "rectangle", "segment"};
		JComboBox<String> shapeB = new JComboBox<String>(shapes);
		shapeB.addActionListener(e -> shapeType = (String)((JComboBox<String>)e.getSource()).getSelectedItem());

		// Select drawing/recoloring color
		// Following Oracle example
		JButton chooseColorB = new JButton("choose color");
		JColorChooser colorChooser = new JColorChooser();
		JLabel colorL = new JLabel();
		colorL.setBackground(Color.black);
		colorL.setOpaque(true);
		colorL.setBorder(BorderFactory.createLineBorder(Color.black));
		colorL.setPreferredSize(new Dimension(25, 25));
		JDialog colorDialog = JColorChooser.createDialog(chooseColorB,
				"Pick a Color",
				true,  //modal
				colorChooser,
				e -> { color = colorChooser.getColor(); colorL.setBackground(color); },  // OK button
				null); // no CANCEL button handler
		chooseColorB.addActionListener(e -> colorDialog.setVisible(true));

		// Mode: draw, move, recolor, or delete
		JRadioButton drawB = new JRadioButton("draw");
		drawB.addActionListener(e -> mode = Mode.DRAW);
		drawB.setSelected(true);
		JRadioButton moveB = new JRadioButton("move");
		moveB.addActionListener(e -> mode = Mode.MOVE);
		JRadioButton recolorB = new JRadioButton("recolor");
		recolorB.addActionListener(e -> mode = Mode.RECOLOR);
		JRadioButton deleteB = new JRadioButton("delete");
		deleteB.addActionListener(e -> mode = Mode.DELETE);
		ButtonGroup modes = new ButtonGroup(); // make them act as radios -- only one selected
		modes.add(drawB);
		modes.add(moveB);
		modes.add(recolorB);
		modes.add(deleteB);
		JPanel modesP = new JPanel(new GridLayout(1, 0)); // group them on the GUI
		modesP.add(drawB);
		modesP.add(moveB);
		modesP.add(recolorB);
		modesP.add(deleteB);

		// Put all the stuff into a panel
		JComponent gui = new JPanel();
		gui.setLayout(new FlowLayout());
		gui.add(shapeB);
		gui.add(chooseColorB);
		gui.add(colorL);
		gui.add(modesP);
		return gui;
	}

	/**
	 * Getter for the sketch instance variable
	 */
	public Sketch getSketch() {
		return sketch;
	}

	/**
	 * Draws all the shapes in the sketch,
	 * along with the object currently being drawn in this editor (not yet part of the sketch)
	 */
	public void drawSketch(Graphics g) {
		// TODO: YOUR CODE HERE
		if (!sketch.getShapeMap().isEmpty()) {
			for (int id : sketch.getShapeMap().navigableKeySet()) { //iterates through shapes from high to low ID
				sketch.getShapeMap().get(id).draw(g);
				System.out.println("Drawing this shape: " + sketch.getShapeMap().get(id));

				if (curr != null) {
					curr.draw(g);
				}
			}
		}
	}

	// Helpers for event handlers
	
	/**
	 * Helper method for press at point
	 * In drawing mode, start a new object;
	 * in moving mode, (request to) start dragging if clicked in a shape;
	 * in recoloring mode, (request to) change clicked shape's color
	 * in deleting mode, (request to) delete clicked shape
	 */
	private void handlePress(Point p) {
		// TODO: YOUR CODE HERE

		System.out.println("Click registered!");

		if (this.mode == Mode.DRAW) {
			if (this.shapeType.equals("ellipse")) {
				this.curr = new Ellipse(p.x, p.y, this.color);
				drawFrom = p;

			}
			if (this.shapeType.equals("rectangle")) {
				this.curr = new Rectangle(p.x, p.y, p.x, p.y);
			}
			if (this.shapeType.equals("polyline")) {
				Segment start = new Segment(p.x, p.y, this.color);
				this.curr = new Polyline(start);
			}
			System.out.println("The editor has just made this shape: \n");
			System.out.println(curr);
			System.out.println("We are now sending this shape through comm to SSC");
			comm.send(curr.toString());


			//this.sketch.addShape(curr); // this is the local sketch, we want to send this to tbe global sketch as well
			//int currID = comm.send("Send me an id!");
			// comm.send("Just made a new shape: [shape] with ID [currID]
			// send shape to server --> write a new method here
			// you want a method that reads "Send me an integer" and asks sketch server communicator to .getsketch().generateID()
			// so on SSC, we want a method called getserver that returns a reference to our master server

		}

		if (curr != null) {
			if (this.mode == Mode.MOVE) {
				// request to!

				for (int ID : sketch.getShapeMap().descendingKeySet()) {
					if (sketch.getShapeMap().get(ID).contains(p.x, p.y)) {
						curr = sketch.getShapeMap().get(ID);
						moveFrom = p;
						movingId = ID;
						break;
					}
				}

			}

//			for (int id : sketch.getShapeMap().keySet()){
//				if (shapeCompare(sketch.getShapeMap().get(id), curr)) {
//					this.movingId = id;
//					break;
//				}
//			}

			if (this.mode == Mode.RECOLOR) {
				// request to!

				if (this.curr.contains(p.x, p.y)) {
					this.curr.setColor(this.color);
				}

				// delete curr from the server's copy
				comm.send("Recolor " + movingId + " " + color); // make sure this has appropriate spaces
			}

		}
			if (this.mode == Mode.DELETE) {

				for (int ID : sketch.getShapeMap().descendingKeySet()){
					if (sketch.getShapeMap().get(ID).contains(p.x, p.y)) {
						this.curr = null;
						movingId = ID;
						comm.send("Delete " + movingId);
						}
					}
			}

		repaint();
	}


	private boolean shapeCompare(Shape shape1, Shape shape2){
		boolean res = true;
		String str1 = shape1.toString();
		String str2 = shape2.toString();
		String[] spl1 = str1.split(" ");
		String[] spl2 = str2.split(" ");

		if (spl1.length != spl2.length){
			return false;
		}

		for (int i=0; i<spl1.length - 1; i++){
			if (!spl1[i].equals(spl2[i])){
				res = false;
			}
		}


		double ratio = (Double.parseDouble(spl1[spl1.length - 1]) / (Double.parseDouble(spl2[spl2.length - 1])));

		if (ratio > 1.1 | ratio < 0.9){
			res = false;
		}

		return res;
	}

	/**
	 * Helper method for drag to new point
	 * In drawing mode, update the other corner of the object;
	 * in moving mode, (request to) drag the object
	 */
	private void handleDrag(Point p) {
		// TODO: YOUR CODE HERE
		// In drawing mode, revise the shape as it is stretched out
		// In moving mode, shift the object and keep track of where next step is from
		// Be sure to refresh the canvas (repaint) if the appearance has changed

		if (this.sketch.getShapeMap().isEmpty()){
			System.out.println("The map for the client is currently empty.");
		}

		//System.out.println("MovingID is currently: " + movingId);

		for (int id : sketch.getShapeMap().keySet()){
			//System.out.println("This is an ID in your client's ShapeMap: " + id);
			//System.out.println("This is what that ID corresponds to: " + sketch.getShapeMap().get(id));
			//System.out.println("We are testing if that is equal to this: " + curr);
			if (shapeCompare(sketch.getShapeMap().get(id), curr)) {
				this.movingId = id;
				System.out.println("This is the ID of the shape we found:");
				System.out.println(id);
				System.out.println("That ID corresponds with this shape:");
				System.out.println(sketch.getShapeMap().get(id));
				break;
			}
		}

		if (mode == Mode.DRAW) {

			if ((shapeType.equals("polyline"))) {
				int temp = ((Polyline) curr).getNumSegments();
				((Polyline) curr).getSegments().get(temp).setEnd(p.x, p.y);
				Segment seggy = new Segment(p.x, p.y, color);
				((Polyline) curr).addSegment(seggy);
			}

			else if (shapeType.equals("ellipse")) {
				//((Ellipse) curr).setCorners(drawFrom.x, drawFrom.y, p.x, p.y);
				if (movingId != -1){
				comm.send("SetCornersE " + movingId + " " + drawFrom.x + " " + drawFrom.y + " " + p.x + " " + p.y);
				}
			}

			else {
				//((Rectangle) curr).setCorners(drawFrom.x, drawFrom.y, p.x, p.y);
				if (movingId != -1) {
					comm.send("SetCornersR " + movingId + " " + drawFrom.x + " " + drawFrom.y + " " + p.x + " " + p.y);
				}
			}
		}

		else if (mode == Mode.MOVE && moveFrom != null) {
			curr.moveBy(p.x - moveFrom.x, p.y - moveFrom.y);
			moveFrom.setLocation(p.getX(), p.getY());
			int moveX = p.x - moveFrom.x;
			int moveY = p.y - moveFrom.y;
			comm.send("Move " + movingId + " " + moveX + " " + moveY);
		}

		repaint();

	}

	/**
	 * Helper method for release
	 * In drawing mode, pass the add new object request on to the server;
	 * in moving mode, release it		
	 */

	private void handleRelease() {
		// TODO: YOUR CODE HERE
		// In moving mode, stop dragging the object
		// Be sure to refresh the canvas (repaint) if the appearance has changed
		if (mode == Mode.MOVE) moveFrom = null;

		repaint();
	}

	public void clearCurr(){
		curr = null;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Editor();
			}
		});	
	}
}
