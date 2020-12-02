/**
 * The multiplication of two expression children
 */
public class Multiplication implements Expression {
	private Expression left, right;
	
	public Multiplication(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	public double evaluate() {
		return left.evaluate() * right.evaluate();
	}
	
	@Override
	public String toString() {
		return "("+left+"*"+right+")";
	}
}
