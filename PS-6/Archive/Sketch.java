import java.util.TreeMap;

public class Sketch {

// going to be mapping IDs (which refer to editors) and shapes
    // or are we mapping ID #s that refer to shapes and shapes?

    /*

    To add a new shape (once finished), an editor client sends a request to the server to add it,
    giving the info needed for a new instance. The server will then determine a unique id for that shape,
     and broadcast to everyone an indication to add the shape (again, with all the info needed for the constructor)
     with the given id. Thus all editors have the same id for the same shape.

     */

    // Delete, recolor, and move operations use the determined id number.

    /*
    The editor and sketch will need to work together to see which shape contains the mouse press, as well as to draw the shapes.
    Be careful with front-to-back order — look for the topmost containing shape, but draw the bottommost shapes first
    so that the later ones cover them. If you use a TreeMap for the id-to-shape mapping, with an increasing id number
    for newer shapes, then a traversal of the tree will yield them in order of newness.
    That's what TreeMap.descendingKeySet and TreeMap.navigableKeySet do (high-to-low and low-to-high, respectively).
     */

    TreeMap<Integer, Shape> shapeMap = new TreeMap<>();
    int id = 0; // seed, can start wherever

    public int generateID(){
        id ++;
        return id;
    }

    public TreeMap<Integer, Shape> getShapeMap(){
        return this.shapeMap;
    }

    public synchronized void addShape(Shape shape){
        int id = generateID();
        this.shapeMap.put(id, shape);
    }

    public synchronized void deleteShape(int id){
        this.shapeMap.remove(id);
    }

    public static void main(String[] args) {

    }
}
