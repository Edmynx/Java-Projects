import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Webcam-based drawing 
 * Scaffold for PS-1, Dartmouth CS 10, Fall 2016
 * @author Chris Bailey-Kellogg, Spring 2015 (based on a different webcam app from previous terms)
 * @author Edmund Aduse Poku, Fall 2019, filled in code
 */
public class CamPaint_ExtraCredit extends Webcam {
	private char displayMode = 'w';			// what to display: 'w': live webcam, 'r': recolored image, 'p': painting
	private RegionFinder finder;			// handles the finding
	private Color targetColor;          	// color of regions of interest (set by mouse press)
	private Color paintColor = Color.blue;	// the color to put into the painting from the "brush"
	private BufferedImage painting;			// the resulting masterpiece
	private int brushNumber = 1;
	ArrayList<Point> targetForBrushes;
	private boolean imageColor = false;
	private boolean paintInAction = true;

	/**
	 * Initializes the region finder and the drawing
	 */
	public CamPaint_ExtraCredit() {
		finder = new RegionFinder();
		clearPainting();
	}

	/**
	 * Resets the painting to a blank image
	 */
	protected void clearPainting() {
		painting = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	/**
	 * DrawingGUI method, here drawing one of live webcam, recolored image, or painting, 
	 * depending on display variable ('w', 'r', or 'p')
	 */
	@Override
	public void draw(Graphics g) {

			if (displayMode == 'p'){
				g.drawImage(painting, 0, 0, null);
			}

			else if (displayMode == 'r'){
				g.drawImage(finder.getRecoloredImage(), 0, 0, null);
			}

			else{
				super.draw(g);
			}
	}

	/**
	 * Webcam method, here finding regions and updating the painting.
	 */
	@Override
	public void processImage() {
		if (targetColor != null && image != null){
			finder.setImage(image);
			finder.findRegions(targetColor);
			finder.recolorImage();

			ArrayList<Point> largestRegion = finder.largestRegion();
			if (largestRegion != null && paintInAction){
				for (Point point : largestRegion){
					if (imageColor){
						painting.setRGB((int) point.getX(), (int) point.getY(), image.getRGB((int) point.getX(), (int) point.getY()));
					}

					else{
						painting.setRGB((int) point.getX(), (int) point.getY(), paintColor.getRGB());
					}

				}
			}
		}
	}

	/**
	 * Overrides the DrawingGUI method to set the track color.
	 */
	@Override
	public void handleMousePress(int x, int y) {
		if (image != null) {
			if (brushNumber == 1) {
				targetColor = new Color(image.getRGB(x, y));
			}

			else{
				if (targetForBrushes.size() < brushNumber){
					targetForBrushes.add(new Point(x, y));
				}
			}
		}
	}

	/**
	 * DrawingGUI method, here doing various drawing commands
	 */
	@Override
	public void handleKeyPress(char k) {
		if (k == 'p' || k == 'r' || k == 'w') { // display: painting, recolored image, or webcam
			displayMode = k;
		}
		else if (k == 'c') { // clear
			clearPainting();
		}
		else if (k == 'o') { // save the recolored image
			saveImage(finder.getRecoloredImage(), "pictures/recolored.png", "png");
		}
		else if (k == 's') { // save the painting
			saveImage(painting, "pictures/painting.png", "png");
		}

		else if (k == 'z'){
			imageColor = !imageColor;
		}

		else if (k == ' '){
			paintInAction = !paintInAction;
		}

		else if (k == 'n'){
			paintColor = new Color((int) (Math.random() * (16777216 + 1)));
		}

		else if (k == '='){
			if (brushNumber < 20) {
				brushNumber++;
			}
		}

		else if (k == '-'){
			if (brushNumber > 1) {
				brushNumber--;
			}
		}

		else {
			System.out.println("unexpected key "+k);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CamPaint_ExtraCredit();
			}
		});
	}
}
