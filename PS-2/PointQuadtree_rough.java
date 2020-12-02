import java.util.ArrayList;
import java.util.List;

/**
 * A point quadtree: stores an element at a 2D position,
 * with children at the subdivided quadrants
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2015
 * @author CBK, Spring 2016, explicit rectangle
 * @author CBK, Fall 2016, generic with Point2D interface
 *
 */
public class PointQuadtree_rough<E extends Point2D> {
    private E point;							// the point anchoring this node
    private int x1, y1;							// upper-left corner of the region
    private int x2, y2;							// bottom-right corner of the region
    private PointQuadtree_rough<E> c1, c2, c3, c4;	// children

    /**
     * Initializes a leaf quadtree, holding the point in the rectangle
     */
    public PointQuadtree_rough(E point, int x1, int y1, int x2, int y2) {
        this.point = point;
        this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2;
    }

    // Getters

    public E getPoint() {
        return point;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    /**
     * Returns the child (if any) at the given quadrant, 1-4
     * @param quadrant	1 through 4
     */
    public PointQuadtree_rough<E> getChild(int quadrant) {
        if (quadrant==1) return c1;
        if (quadrant==2) return c2;
        if (quadrant==3) return c3;
        if (quadrant==4) return c4;
        return null;
    }

    /**
     * Returns whether or not there is a child at the given quadrant, 1-4
     * @param quadrant	1 through 4
     */
    public boolean hasChild(int quadrant) {
        return (quadrant==1 && c1!=null) || (quadrant==2 && c2!=null) || (quadrant==3 && c3!=null) || (quadrant==4 && c4!=null);
    }

    /**
     * Inserts the point into the tree
     */
    public void insert(E p2) {
        // TODO: YOUR CODE HERE
        if (/*p2.getY() >= 0 && */p2.getY() < point.getY()){
            if (p2.getX() > point.getX()/* && p2.getX() <= getX2()*/){
                if (hasChild(1)){
                    getChild(1).insert(p2);
                }

                else{
                    c1 = new PointQuadtree_rough<>(p2, (int) point.getX(), getY1(), getX2(), (int) point.getY());
                }
            }

            else if (/*(p2.getX() >= 0 && */p2.getX() < point.getX()){
                if (hasChild(2)) {
                    getChild(2).insert(p2);
                }

                else{
                    c2 = new PointQuadtree_rough<>(p2, getX1(), getY1(), (int) point.getX(), (int) point.getY());
                }
            }
        }

        else if (p2.getY() > point.getY()/* && p2.getY() <= point.getY()*/){
            if (/*p2.getX() >= 0 && */p2.getX() < point.getX()){
                if (hasChild(3)){
                    getChild(3).insert(p2);
                }

                else{
                    c3 = new PointQuadtree_rough<>(p2, getX1(), (int) point.getY(), (int) point.getX(), getY2());
                }
            }

            else if (p2.getX() > point.getX()/* && p2.getX() <= getX2()*/){
                if (hasChild(4)){
                    getChild(4).insert(p2);
                }

                else{
                    c4 = new PointQuadtree_rough<>(p2, (int) point.getX(), (int) point.getY(), getX2(), getY2());
                }
            }
        }
    }

    /**
     * Finds the number of points in the quadtree (including its descendants)
     */
    public int size() {
        // TODO: YOUR CODE HERE
        int num = 1;
        for (int i = 1; i <= 4; i++){
            if (hasChild(i)){
                num += getChild(i).size();
            }
        }
        return  num;
    }

    /**
     * Builds a list of all the points in the quadtree (including its descendants)
     */
    public List<E> allPoints() {
        // TODO: YOUR CODE HERE
        List<E> list = new ArrayList<>();
        allPointsHelper(list);
        return list;
    }

    /**
     * Uses the quadtree to find all points within the circle
     * @param cx	circle center x
     * @param cy  	circle center y
     * @param cr  	circle radius
     * @return    	the points in the circle (and the qt's rectangle)
     */
    public List<E> findInCircle(double cx, double cy, double cr) {
        // TODO: YOUR CODE HERE
        List<E> list = new ArrayList<E>();
        findInCircleHelper(cx, cy, cr, list);
        return list;
    }

    // TODO: YOUR CODE HERE for any helper methods
    public void allPointsHelper(List<E> list){
        list.add(point);
        for (int i = 1; i <= 4; i++){
            if (hasChild(i)){
                getChild(i).allPointsHelper(list);
            }
        }
    }

    public void findInCircleHelper(double cx, double cy, double cr, List<E> list){
        if (Geometry.circleIntersectsRectangle(cx, cy, cr, getX1(), getY1(), getX2(), getY2())){
            if (Geometry.pointInCircle(point.getX(), point.getY(), cx, cy, cr)){
                list.add(point);
            }
            for (int i = 1; i <= 4; i++){
                if (hasChild(i)){
                    getChild(i).findInCircleHelper(cx, cy, cr, list);
                }
            }
        }
    }

    public static void main(String[]args) {

        Dot carlos = new Dot(50, 100);
        Dot bob = new Dot(70, 80);
        Dot karl = new Dot(20, 80);
        Dot marx = new Dot(20, 120);
        Dot stalin = new Dot(100, 150);
        Dot trump = new Dot(120, 50);
        Dot mao = new Dot(150, 20);
        Dot krushchev = new Dot(10, 150);

        PointQuadtree_rough<Dot> losers = new PointQuadtree_rough<Dot>(carlos, 0, 0, 199, 199);
        System.out.println("hello " + losers + "\n");

        losers.insert(bob);
        losers.insert(karl);
        losers.insert(marx);
        losers.insert(stalin);
        losers.insert(trump);
        losers.insert(mao);
        losers.insert(krushchev);

        System.out.println(losers.size());
        System.out.println(losers.allPoints());

        ArrayList<Dot> allPoints = (ArrayList<Dot>) losers.allPoints();
        for (Dot d : allPoints) {
            System.out.println(d);
        }

        System.out.println(losers.findInCircle(160, 10, 20));

    }
}
