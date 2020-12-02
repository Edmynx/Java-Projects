/**
 * Some test cases for expressions
 */
public class ExpressionTest {
	public static void main(String[] args) {
		Expression five = new Number(5);
		System.out.println(five + " = " + five.evaluate());
		
		Expression threePlusFour = new Addition(new Number(3), new Number(4));
		System.out.println(threePlusFour + " = " + threePlusFour.evaluate());
		
		Expression test = times(plus(plus(c(3),c(4)),
									 times(c(4),c(2))),
								c(5));
		System.out.println(test + " = " + test.evaluate());
	}
	
	// Helper functions to make the expressions a little easier to read
	
	public static Expression c(double theNumber) {
		return new Number(theNumber);
	}

	public static Expression plus(Expression e1, Expression e2) {
		return new Addition(e1, e2);
	}

	public static Expression times(Expression e1, Expression e2) {
		return new Multiplication(e1, e2);
	}
}
