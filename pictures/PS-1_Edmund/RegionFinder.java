import java.awt.*;
import java.awt.image.*;
import java.util.*;

/**
 * Region growing algorithm: finds and holds regions in an image.
 * Each region is a list of contiguous points with colors similar to a target color.
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * 
 * @author Chris Bailey-Kellogg, Winter 2014 (based on a very different structure from Fall 2012)
 * @author Travis W. Peters, Dartmouth CS 10, Updated Winter 2015
 * @author CBK, Spring 2015, updated for CamPaint_ExtraCredit
 * @author Edmund Aduse Poku, Fall 2019, filled in code
 */
public class RegionFinder {
	private static final int maxColorDiff = 50;				// how similar a pixel color must be to the target color, to belong to a region
	private static final int minRegion = 50; 				// how many points in a region to be worth considering

	private BufferedImage image;                            // the image in which to find regions
	private BufferedImage recoloredImage;                   // the image with identified regions recolored

	private ArrayList<ArrayList<Point>> regions;			// a region is a list of points
															// so the identified regions are in a list of lists of points
	public RegionFinder() {
		image = null;
	}

	public RegionFinder(BufferedImage image) {
		this.image = image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage getImage() {
		return image;
	}

	public BufferedImage getRecoloredImage() {
		return recoloredImage;
	}

	/**
	 * Sets regions to the flood-fill regions in the image, similar enough to the trackColor.
	 * @param targetColor
	 */
	public void findRegions(Color targetColor) {
		regions = new ArrayList<ArrayList<Point>>();
		BufferedImage visited = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		/**
		 * Loop over all the pixels
		 * If a pixel is unvisited and of the correct color, start a new region
		 * keeping track of which pixels need to be visited including their neighbors
		 */
		for (int column = 0; column < image.getHeight(); column++){
			for (int row = 0; row < image.getWidth(); row++){
				if (visited.getRGB(row, column) == 0){
					ArrayList<Point> region = new ArrayList<Point>();
					Color color1 = new Color(image.getRGB(row, column));
					if (colorMatch(color1, targetColor)){
						ArrayList<Point> toVisit = new ArrayList<>();
						toVisit.add(new Point(row, column));

						/**
						 * As long as there's some pixel that needs to be visited, get one to visit,
						 * add it to the region and mark it as visited
						 */

						while (toVisit.size() !=0){
							Point visit = toVisit.remove(toVisit.size() - 1);

							int posX = (int) visit.getX();
							int posY = (int) visit.getY();

							region.add(visit);
							visited.setRGB(posX, posY, 1);

							for (int y = Math.max(posY - 1, 0); y < Math.min(posY + 2, image.getHeight()); y++){
								for (int x = Math.max(posX - 1, 0); x < Math.min(posX + 2, image.getWidth()); x++){
									if (visited.getRGB(x, y) == 0){
										Color color2 = new Color(image.getRGB(x, y));
										if (colorMatch(color2, targetColor)) {
											toVisit.add(new Point(x, y));
										}
									}
								}
							}
						}

						/**
						 * If the region is big enough (ie greater than min region size),
						 * keep it
						 */

						if (region.size() >= minRegion){
							regions.add(region);
						}
					}
				}
			}
		}
	}

	/**
	 * Tests whether the two colors are "similar enough" (your definition, subject to the maxColorDiff threshold, which you can vary).
	 */
	private static boolean colorMatch(Color c1, Color c2) {
		int difference = Math.abs(c2.getRed() - c1.getRed())
							+ Math.abs(c2.getGreen() - c1.getGreen())
								+ Math.abs(c2.getBlue() - c1.getBlue());

		return difference <= maxColorDiff;
	}

	/**
	 * Returns the largest region detected (if any region has been detected)
	 */
	public ArrayList<Point> largestRegion() {

		/**
		 * Pick a random region and make it the current largest region
		 * Loop over all the regions in the list,
		 * comparing their sizes to the current largest region
		 * Store whichever region that has the largest size in largestRegion
		 */
		ArrayList<Point> largestRegion = null;
		if (regions.size() !=0){
			largestRegion = regions.get((int) Math.random() * regions.size());
			for (ArrayList<Point> region : regions){
				if (region.size() > largestRegion.size()){
					largestRegion = region;
				}
			}
		}
		return largestRegion;
	}

	/**
	 * Sets recoloredImage to be a copy of image, 
	 * but with each region a uniform random color, 
	 * so we can see where they are
	 */
	public void recolorImage() {
		// First copy the original
		recoloredImage = new BufferedImage(image.getColorModel(), image.copyData(null), image.getColorModel().isAlphaPremultiplied(), null);
		// Now recolor the regions in it
		if (regions.size() != 0){
			for (ArrayList<Point> region : regions){
				Color color = new Color((int) (Math.random() * (16777216 + 1)));
				for (Point point : region){
					recoloredImage.setRGB((int) point.getX(), (int) point.getY(), color.getRGB());
				}
			}
		}
	}
}
