import java.awt.*;
import java.util.Map;

public class StringInterpreter {

    public static String[] unpackString(String input) {
        String[] tokens = input.split("[ ,]+");
        return tokens;
    }

    public static void performAction(String[] tokens, Map<Integer, Shape> map) {
        if (tokens[0].equals("draw") || tokens[0].equals("move")) {
            int id = Integer.parseInt(tokens[1]);
            int x1 = Integer.parseInt(tokens[3]);
            int y1 = Integer.parseInt(tokens[4]);
            int x2 = Integer.parseInt(tokens[5]);
            int y2 = Integer.parseInt(tokens[6]);

            if (tokens[0].equals("draw")) {
                Color color = new Color(Integer.parseInt(tokens[7]));

                if (tokens[2].equals("ellipse")) {
                    ((Ellipse) map.get(id)).setCorners(x1, y1, x2, y2);
                    System.out.println("\t\tHas edited the size of said ellipse in editor's sketch");
                }

                else if (tokens[2].equals("rectangle")) {
                    ((Rectangle) map.get(id)).setCorners(x1, y1, x2, y2);
                    System.out.println("\t\tHas edited the size of said rectangle in editor's sketch");
                }

                else if (tokens[2].equals("segment")) {
                    ((Segment) map.get(id)).setEnd(x2, y2);
                    System.out.println("\t\tHas edited the size of said segment in editor's sketch");
                }

                else {
                    int length = ((Polyline) map.get(id)).getNumSegments();
                    ((Polyline) map.get(id)).getSegments().get(length - 1).setEnd(x2, y2);
                    ((Polyline) map.get(id)).addSegment(new Segment(x2, y2, color));
                    System.out.println("\t\tHas edited the size of said polyline in editor's sketch");
                }
            }

            else {
                if (tokens[2].equals("ellipse")) {
                    map.get(id).moveBy(x2 - x1, y2 - y1);
                    System.out.println("\t\tHas changed the location of said ellipse in editor's sketch");
                }

                else if (tokens[2].equals("rectangle")) {
                    map.get(id).moveBy(x2 - x1, y2 - y1);
                    System.out.println("\t\tHas changed the location of said rectangle in editor's sketch");
                }

                else if (tokens[2].equals("segment")) {
                    map.get(id).moveBy(x2 - x1, y2 - y1);
                    System.out.println("\t\tHas changed the location of said segment in editor's sketch");
                }

                else {
                    map.get(id).moveBy(x2 - x1, y2 - y1);
                    System.out.println("\t\tHas changed the location of said polyline in editor's sketch");
                }
            }
        }

        else if (tokens[0].equals("recolor")) {
            map.get(Integer.parseInt(tokens[1])).setColor(new Color(Integer.parseInt(tokens[2])));
            System.out.println("\t\tHas changed the color of said shape in editor's sketch");
        }

        else if (tokens[0].equals("delete")) {
            map.remove(Integer.parseInt(tokens[1]));
            System.out.println("\t\tHas deleted said shape from editor's sketch");
        }
    }

    public static Shape createShape(String[] tokens) {
        String colorToken = tokens[6];
        if (colorToken.charAt(colorToken.length() - 1) == ']') {
            colorToken = "";
            for (int i = 0; i < tokens[6].length() - 1; i++) {
                colorToken += tokens[6].charAt(i);
            }
        }

        Shape shape = null;
        Color color = new Color(Integer.parseInt(colorToken));

        if (tokens[1].equals("ellipse")) {
            shape = new Ellipse(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), color);
            System.out.println("\t\tHas added an ellipse to editor's sketch");
        }

        else if (tokens[1].equals("rectangle")) {
            shape = new Rectangle(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), color);
            System.out.println("\t\tHas added a rectangle to editor's sketch");
        }

        else if (tokens[1].equals("segment")) {
            shape = new Segment(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), color);
            System.out.println("\t\tHas added a segment to editor's sketch");
        }

        else {
            shape = new Polyline(new Segment(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), color));
        }

        //else {
            // shape = new Polyline(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), color);
        //}

        return shape;
    }
}