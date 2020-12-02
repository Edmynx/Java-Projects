import java.awt.*;
import java.util.Arrays;
//@author by Edmund Aduse Poku (worked with Arjun)

public class StringDecomposer {

    public static Shape unpackString(String input) {

        //System.out.println("This is StringDecomposers input: " + input);

        Shape shape = null;
        String[] tokens = input.split("\\s+");

        if (tokens[0].charAt(1) == 'k'){
            if (tokens[0].contains("ll")){
                tokens[0] = "ellipse";
            }
            if (tokens[0].contains("ect")){
                tokens[0] = "rectangle";
            }
        }

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
                else if (tokens[0].equals("rectangle")){
                    Color color = new Color(Integer.parseInt(tokens[5]));
                    shape = new Rectangle(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                    shape.setColor(color);
                }
                else{
                    System.out.println("Unexpected input: the first token from String decomposition was: " + tokens[0]);
                }

            }
        }

        //System.out.println("This is the shape being returned by String Decomposer:");
        //System.out.println(shape);

        return shape;
    }


}
