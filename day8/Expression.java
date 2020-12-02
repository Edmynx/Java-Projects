/**
 * A mathematical expression, which evaluates to a double
 * 
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 * Inspired by a much richer set of expression classes (with variable substitution and differentiation) by Scot Drysdale
 * Ask me later this term if you want to discuss how that could be folded in
 */
public interface Expression {
	/**
	 * Evaluates the expression to return its value
	 * @return  the value
	 */
	public double evaluate();
}
