/**
 * A constant expression
 */
public class Number implements Expression {
	private double theNumber;
	
	public Number(double theNumber) {
		this.theNumber = theNumber;
	}
	
	public double evaluate() {
		return theNumber;
	}
	
	@Override
	public String toString() {
		return Double.toString(theNumber);
	}
}
