import java.awt.*;

import javax.swing.*;

import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.ArrayList;

/**
 * Using a quadtree for collision detection
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, updated for blobs
 * @author CBK, Fall 2016, using generic PointQuadtree
 */
public class CollisionGUIExtraCredit extends DrawingGUI {
    private static final int width=800, height=600;		// size of the universe

    private List<Blob> blobs;							// all the blobs
    private List<Blob> colliders;						// the blobs who collided at this step
    private List<Blob> blobsInDrag;
    private char blobType = 'b';						// what type of blob to create
    private char collisionHandler = 'c';				// when there's a collision, 'c'olor them, or 'd'estroy them
    private char dragShape = '2';                       // drag shape to use during dragging, 1 for rectangle and 2 for circle
    private int delay = 100;							// timer control
    private int clickedX, clickedY;						// current mouse location, after dragging
    private int minimumX, minimumY;                     // keep track of the minimum x and y betwenn the start of the drag and its release
    private int maximumX, maximumY;                     // keep track of the maximum x and y between the start of the drag and its release
    private int releasedX, releasedY;                   // keep track of where the drag was released
    private int radius;                                 // radius for drag circle
    private boolean shapeReady = false;                 // drag shape is not ready to be drawn at start of program

    // do you want to do a collision test or operate as normal
    // We don't want to do a collision test at the beginning
    // We wait until user tells us to
    private boolean collisionTest = false;

    public CollisionGUIExtraCredit() {
        super("super-collider", width, height);

        blobs = new ArrayList<Blob>();

        // Timer drives the animation.
        startTimer();
    }

    /**
     * Adds an blob of the current blobType at the location
     */
    private void add(int x, int y) {
        if (blobType=='b') {
            blobs.add(new Bouncer(x,y,width,height));
        }
        else if (blobType=='w') {
            blobs.add(new Wanderer(x,y));
        }
        else {
            System.err.println("Unknown blob type "+blobType);
        }
    }

    /**
     * DrawingGUI method, here creating a new blob
     */
    public void handleMousePress(int x, int y) {
        add(x,y);
        clickedX = x; clickedY = y;
        repaint();
    }

    @Override
    public void handleMouseDragged(int x, int y) {
        minimumX = Math.min(x, clickedX);
        minimumY = Math.min(y, clickedY);
        maximumX = Math.max(x, clickedX);
        maximumY = Math.max(y, clickedY);
        releasedX = x; releasedY = y;
        shapeReady = true;
        repaint();
    }

    /**
     * DrawingGUI method
     */
    public void handleKeyPress(char k) {
        if (k == 'f') { // faster
            if (delay>1) delay /= 2;
            setTimerDelay(delay);
            System.out.println("delay:"+delay);
        }
        else if (k == 's') { // slower
            delay *= 2;
            setTimerDelay(delay);
            System.out.println("delay:"+delay);
        }
        else if (k == 'r') { // add some new blobs at random positions
            for (int i=0; i<10; i++) {
                add((int)(width*Math.random()), (int)(height*Math.random()));
                repaint();
            }
        }
        else if (k == 'c' || k == 'd') { // control how collisions are handled
            collisionHandler = k;
            System.out.println("collision:"+k);
        }

        else if (k == '1' || k == '2') { // determine what shape to draw upon dragging
            dragShape = k;
            if (k == '2') System.out.println("Shape for dragging changed to a circle: " + k);
            else System.out.println("Shape for dragging changed to a rectangle: " + k);
        }

        else if (k == 'q') { // turn shape for drag off
            shapeReady = false;
            repaint();
        }

        else if(k == 't'){ // control operational mode
            collisionTest = !collisionTest;
            repaint();
        }

        else { // set the type for new blobs
            blobType = k;
        }
    }

    /**
     * DrawingGUI method, here drawing all the blobs and then re-drawing the colliders in red
     */
    public void draw(Graphics g) {
        // TODO: YOUR CODE HERE
        // Ask all the blobs to draw themselves.
        // Ask the colliders to draw themselves in red.

        if (shapeReady) {
            if (dragShape == '1'){
                g.drawRect(minimumX, minimumY, maximumX - minimumX, maximumY - minimumY);
            }

            else  {
                // Calculate the radius of the circle
                radius = (int) (Math.sqrt((clickedX - releasedX) * (clickedX - releasedX) + (clickedY - releasedY) * (clickedY - releasedY)));
                g.drawOval(clickedX - radius, clickedY - radius, 2 * radius, 2 * radius);
            }
        }

        for (Blob blob : blobs){
            // Set color of blobs in drag to green
            if (blobsInDrag != null && blobsInDrag.contains(blob)){
                g.setColor(Color.GREEN);
            }

            // If blob is in colliders, set its color to red
            else if (colliders != null && colliders.contains(blob)){
                g.setColor(Color.RED);
                System.out.println(colliders);
            }

            // Otherwise, blob should stay black
            else{
                g.setColor(Color.BLACK);
            }

            // Draw blob
            blob.draw(g);
        }
    }

    /**
     * Sets colliders to include all blobs in contact with another blob
     */
    private void findColliders() {
        // TODO: YOUR CODE HERE
        // Create the tree
        // For each blob, see if anybody else collided with it
        colliders = new ArrayList<>();
        blobsInDrag = new ArrayList<>();
        PointQuadtree<Blob> tree = new PointQuadtree<Blob>(blobs.get(0), 0, 0, width, height);
        for (int i = 1; i < blobs.size(); i++){
            tree.insert(blobs.get(i));
        }

        List<Blob> collisionList;
        List<Blob> dragList = new ArrayList<>();
        for (Blob blob : blobs){
            if (shapeReady) {
                if (dragShape == '1') dragList = tree.findInRectangle(minimumX, minimumY, maximumX, maximumY);
                else dragList = tree.findInCircle(clickedX, clickedY, radius);
            }


            for (Blob b : dragList) {
                blobsInDrag.add(b);
            }

            collisionList = tree.findInCircle(blob.getX(), blob.getY(), 2 * blob.getR());
            collisionList.remove(blob);
            for (Blob collidingBlob: collisionList){
                colliders.add(collidingBlob);
            }
        }
    }

    public void testCollision(){
        blobs = new ArrayList<>();

        // Add four wandering blobs that are more likely to collide among themselves
        blobs.add(new Wanderer(400, 300));
        blobs.add(new Wanderer(392, 302));
        blobs.add(new Wanderer(395, 307));
        blobs.add(new Wanderer(408, 301));

        // Add four more wandering blobs that would most likely not collide among themselves
        blobs.add(new Wanderer(195, 145));
        blobs.add(new Wanderer(205, 155));
        blobs.add(new Wanderer(595, 445));
        blobs.add(new Wanderer(605, 455));

        // Add two free bouncers that can collide with any of the 8 blobs above
        blobs.add(new Blob(Math.random() * width, Math.random() * height));
        blobs.add(new Blob(Math.random() * width, Math.random() * height));

        for (Blob blob : blobs) {
            blob.step();
        }

        // Check for collisions
        findColliders();
        if (collisionHandler=='d') {
            blobs.removeAll(colliders);
            colliders = null;
        }
    }

    /**
     * DrawingGUI method, here moving all the blobs and checking for collisions
     */
    public void handleTimer() {
        // Ask all the blobs to move themselves.

        // If test collision is true, test collision
        if (collisionTest){
            testCollision();
        }

        else{

            for (Blob blob : blobs) {
                blob.step();
            }
            // Check for collisions
            if (blobs.size() > 0) {
                findColliders();
                if (collisionHandler=='d') {
                    blobs.removeAll(colliders);
                    colliders = null;
                }
            }
        }

        // Now update the drawing
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CollisionGUIExtraCredit();
            }
        });
    }
}

