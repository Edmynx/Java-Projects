import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Painting random colors with wanderers
 * Template for SA-2, Dartmouth CS 10, Spring 2016
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * modified by Edmund Aduse Poku, Dartmouth CS 10, Fall 2019
 */
public class PollockStreak extends DrawingGUI {
    private static final int width = 800, height = 600; // setup: window size
    private static final int numBlobs = 20000;			// setup: how many blobs
    private static final int numToMove = 5000;			// setup: how many blobs to animate each frame

    private static final int radius = 1;				// radius of blobs

    private BufferedImage result;						// the picture being painted
    private ArrayList<PurposefulWanderer> wanderers;						// the blobs representing the picture

    public PollockStreak() {
        super("PollockStreak", width, height);

        result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Create a bunch of random blobs.
        wanderers = new ArrayList<PurposefulWanderer>();
        for (int i=0; i<numBlobs; i++) {
            int x = (int)(width*Math.random());
            int y = (int)(height*Math.random());
            // Create a blob with a random color
            Color color = new Color((int)(16777216 * Math.random()));
            wanderers.add(new PurposefulWanderer(x, y, radius, color));
        }

        // Timer drives the animation.
        startTimer();
    }

    /**
     * DrawingGUI method, here just drawing all the blobs
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(result, 0, 0, null);
        for (PurposefulWanderer wanderer : wanderers) {
            wanderer.draw(g);
        }
    }

    /**
     * DrawingGUI method, here moving some of the blobs
     */
    @Override
    public void handleTimer() {
        for (int b = 0; b < numToMove; b++) {
            // Pick a random blob, leave a trail where it is, and ask it to move.
            PurposefulWanderer wanderer = wanderers.get((int)(Math.random()*wanderers.size()));
            int x = (int)wanderer.getX(), y = (int)wanderer.getY();
            // Careful to stay within the image
            if (x>=0 && x<width && y>=0 && y<height) {
                // Leave a trail of the wanderer's color
                result.setRGB(x, y, wanderer.getMyColor().getRGB());
            }
            wanderer.step();
        }
        // Now update the drawing
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PollockStreak();
            }
        });
    }
}
