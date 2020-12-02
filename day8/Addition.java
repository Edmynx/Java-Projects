/**
 * The addition of two expression children
 */
public class Addition implements Expression {
	private Expression left, right;
	
	public Addition(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}
	
	public double evaluate() {
		return left.evaluate() + right.evaluate();
	}
	
	@Override
	public String toString() {
		return "("+left+"+"+right+")";
	}
}
