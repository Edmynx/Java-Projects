import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A class demonstrating manipulation of image pixels.
 * Version 0: just the core definition
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, Winter 2014, rewritten for BufferedImage
 * @author CBK, Spring 2015, refactored to separate GUI from operations
 */
public class ImageProcessor0 {
	private BufferedImage image;		// the current image being processed

	/**
	 * @param image		the original
	 */
	public ImageProcessor0(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Updates the image with an ellipse with center cx and cy at the location.
	 * Color of ellipse randomly selected at the start of program
	 * User can also choose random colors for the ellipses drawn
	 *
	 * @param cx		center x of ellipse
	 * @param cy		center y of ellipse
	 * @param r			radius of ellipse
	 * @param color 	color to fill ellipse
	 */

	public void drawCircle(int cx, int cy, int r, Color color){
		for (int y = Math.max(0, cy-r); y < Math.min(image.getHeight(), cy+r); y++) {
			for (int x = Math.max(0, cx-r); x < Math.min(image.getWidth(), cx+r); x++) {
				if (contains(cx, cy, x, y, r)){
					image.setRGB(x, y, color.getRGB());
				}
			}
		}
	}
	/**
	 * Checks to see if point lies in given ellipse
	 *
	 * @param cx		center x of ellipse
	 * @param cy		center y of ellipse
	 * @param x2        x-coordinate of point
	 * @param y2 		y-coordinate of point
	 * @param r			radius of ellipse
	 *
	 */

	public static boolean contains(double cx, double cy, double x2, double y2, double r) {
		double dx = cx - x2;
		double dy = cy - y2;
		return dx * dx + dy * dy <= r * r;
	}
}
