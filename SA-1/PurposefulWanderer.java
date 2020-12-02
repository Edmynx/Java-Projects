import java.awt.*;
import java.util.Random;

/**
 * A blob that moves randomly but purposefully
 *
 * @author Edmund Aduse Poku, Dartmouth CS 10, Fall 2019
 */

public class PurposefulWanderer extends Blob {
    private Color color;    // wanderer's color

    protected double maxStep;   // number of steps between velocities
    protected double coveredSteps;  // number of steps covered

    public PurposefulWanderer(double x, double y, double r, Color color){
        super(x, y, r);
        this.color = color;

        // Choose a number randomly between 10 and 20 (inclusive) and store it in maxStep
        maxStep = (10 * (Math.random() - 0.5)) + 15;
    }

    // retrieve wanderer's color
    public Color getMyColor(){
        return color;
    }


    /*
    updates wanderer's position
    random new values are assigned to dx and dy each time
    the required number of steps has been taken
     */
    @Override
    public void step(){
        if (maxStep < coveredSteps || coveredSteps == 0){
            dx = 2 * (Math.random() - 0.5);
            dy = 2 * (Math.random() - 0.5);
            coveredSteps = 0;
        }
        x += dx;
        y += dy;
        coveredSteps += 1;    // update the value of number of steps covered after each step
    }
}
