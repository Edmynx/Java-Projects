import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 * Load an image and display it
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 * modified by Edmund Aduse Poku, Dartmouth CS 10, Fall 2019
 */
public class ImageProcessingGUI0 extends DrawingGUI {
	private ImageProcessor0 proc;		// handles the image processing
	private Color color;				// color of effect

	private char action = 'a';			// what action to take when mouse is moved
	private int radius = 5;				// extent of drawing effects

	private boolean brushState = true;	// when should effects be applied

	/**
	 * Creates the GUI for the image processor, with the window scaled to the to-process image's size
	 */
	public ImageProcessingGUI0(ImageProcessor0 proc) {
		super("Image processing", proc.getImage().getWidth(), proc.getImage().getHeight());
		this.proc = proc;

		// Generate a new random color for effects
		color = new Color((int) (Math.random() * 16777216));
	}

	/**
	 * DrawingGUI method, here showing the current image
	 */
	@Override
	public void draw(Graphics g) {
		g.drawImage(proc.getImage(), 0, 0, null);
	}

	/**
	 * DrawingGUI method, here dispatching on image processing operations
	 */
	@Override
	public void handleKeyPress(char op) {
		System.out.println("Handling key '"+op+"'");
		if (op=='s') { // save a snapshot
			saveImage(proc.getImage(), "pictures/snapshot.png", "png");
		}

		else if (op=='g'){ // mouse motion => draw circles
			action = op;
		}

		else if (op=='u'){ // set effects to "off"
			brushState = false;
		}

		else if (op=='d'){ // set effects to "on"
			brushState = true;
		}

		else if (op=='c'){ // choose a new random color for effects
			color = new Color((int)(Math.random() * 16777216));
		}

		else if (op=='='){ // make radius bigger
			if (radius<Math.min(proc.getImage().getHeight(), proc.getImage().getWidth())){
				radius += 2;
			}
			else {System.out.println("radius cannot be increased further");
			}
		}

		else if (op=='-'){ // make radius smaller
			if (radius>0){
				radius -= 2;
			}
			else {
				System.out.println("radius cannot be decreased further");
			}
		}

		else {
			System.out.println("Unknown operation");
		}

		repaint(); // Re-draw, since image has changed
	}
	/**
	 * DrawingGUI superclass method,  here manipulating the image at/near the mouse location
	 */
	@Override
	public void handleMouseMotion(int x, int y){
		if (brushState==true && action=='a'){ // Drawing circles
			proc.drawCircle(x, y, radius, color);
		}

		repaint();	// Re-draw, since image has changed

	}

	public static void main(String[] args) { 
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Load the image to process
				BufferedImage baker = loadImage("pictures/baker.jpg");
				// Create a new processor, and a GUI to handle it
				new ImageProcessingGUI0(new ImageProcessor0(baker));
			}
		});
	}
}
