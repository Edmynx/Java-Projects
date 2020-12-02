import java.awt.*;
import java.util.Arrays;

public class StringDecomposer {

    public static Shape unpackString(String input) {

        //System.out.println("This is StringDecomposers input: " + input);

        Shape shape = null;
        String[] tokens = input.split("\\s+");

        if (!tokens[0].equals("Delete")) {

            if (tokens.length >= 5){

                if (tokens[0].equals("Polyline")) {
                    // do something special
                    System.out.println("placeholder");
                }
                else if (tokens[0].equals("ellipse")) {
                    //System.out.println("This is the color integer as read by String Decomposer:");
                    //System.out.println(tokens[4]);
                    //System.out.println("\n\n");
                    Color color = new Color(Integer.parseInt(tokens[5])); // so we're asking it for a new color for int 201
                    shape = new Ellipse(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), color);
                }

                else if (tokens[0].equals("segment")) {
                    Color color = new Color(Integer.parseInt(tokens[5])); // so we're asking it for a new color for int 201
                    shape = new Segment(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), color);
                }

                else {
                    Color color = new Color(Integer.parseInt(tokens[5]));
                    shape = new Rectangle(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), color);
                }

            }
        }

        //System.out.println("This is the shape being returned by String Decomposer:");
        //System.out.println(shape);

        return shape;
    }


}
