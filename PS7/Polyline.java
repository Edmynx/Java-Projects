import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 */
public class Polyline implements Shape {
	// TODO: YOUR CODE HERE
	//private Color color = Color.black;
	private Color color;
	private ArrayList<Segment> segments;


	public Polyline(Segment start){
		segments = new ArrayList<>();
		segments.add(start);
		color = start.getColor();
	}

	public int getNumSegments(){
		return segments.size();
	}

	public ArrayList<Segment> getSegments(){
		return segments;
	}

	@Override
	public void moveBy(int dx, int dy) {
		for (Segment s: segments){
			s.moveBy(dx, dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
		for (Segment segment : segments){
			segment.setColor(color);
		}
	}
	
	@Override
	public boolean contains(int x, int y) {
		for (Segment segment: segments){
			if (segment.contains(x, y)){
				return true;
			}
		}

		return false;
	}

	@Override
	public void draw(Graphics g) {
		for (Segment s: segments){
			s.draw(g);
		}
	}

	public void addSegment(Segment s){
		segments.add(s);
	}

	@Override
	public String toString() {
		return "Polyline" + segments.toString();
	}
}
