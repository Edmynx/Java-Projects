/**
 * Class to hold purchases made on a credit card.  Each transaction has an ID, a type (e.g., Groceries), and an amount spent.
 * @author Tim Pierson, Dartmouth CS 10, Spring 2019
 *
 */
public class Transaction implements Comparable<Transaction>{
	protected Integer id;
	protected String type;
	protected Double amount;
	
	public Transaction(Integer id, String type, double amount) {
		this.id = id;
		this.type = type;
		this.amount = amount;
	}
	
	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}
	
	public Double getAmount() {
		return amount;
	}
	
	public int compareTo(Transaction t1) {
		return (int)Math.signum(amount-t1.getAmount());
	}
	
	public String toString() {
		return getId() + "," + getType() + "," + getAmount();
	}
}
