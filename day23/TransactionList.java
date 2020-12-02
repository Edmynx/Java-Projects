import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/** 
 * Extract Grocery purchases from list of transactions, sort by amount spent descending, return transaction IDs
 * @author Tim Pierson, Dartmouth CS10, Spring 2019
 *
 */
public class TransactionList {
	/**
	 * Given an ArrayList of Transaction items, filter items with type = type parameter, sort by purchase amount descending,
	 * return transaction IDs.
	 * 
	 * This method uses the typical Java pattern of looping over the transactions ArrayList two times.
	 *  
	 * @param transactions -- ArrayList of Transaction Objects
	 * @param type -- String with the type of transactions to filter (e.g., Groceries)
	 * @return -- ArrayList of Transaction IDs sorted by amount spent for transactions of type given in parameter type
	 */
	public static ArrayList<Integer> getTransactionIDTraditional(ArrayList<Transaction> transactions, String type) {
		ArrayList<Transaction> list = new ArrayList<Transaction>(); //list told IDs
		
		//extract transactions of type matching parameter type
		for (Transaction t : transactions) {
			if (t.getType() == type) {
				list.add(t);
			}
		}
		
		//sort by amount descending
		list.sort((t1,t2) -> t2.getAmount().compareTo(t1.getAmount()));
		
		//get transaction IDs
		ArrayList<Integer> ids = new ArrayList<Integer>();
		for (Transaction t: list) {
			ids.add(t.getId());
		}
		return ids;
	}
	
	/**
	 * Given an ArrayList of Transaction items, filter items with type = type parameter, sort by purchase amount descending,
	 * return transaction IDs.
	 * 
	 * This method uses the Java Streams.
	 *  
	 * @param transactions -- ArrayList of Transaction Objects
	 * @param type -- String with the type of transactions to filter (e.g., Groceries)
	 * @return -- ArrayList of Transaction IDs sorted by amount spent for transactions of type given in parameter type
	 */
	public static List<Integer> getTransactionIDStream(ArrayList<Transaction> transactions, String type) {
		List<Integer> list = transactions.stream() //user transactions ArrayList as source
            .filter(t -> t.getType() == type) //filter on type
            .sorted(Comparator.reverseOrder()) //sort using compareTo, but reversed
            .map(Transaction::getId) //extract IDs
            .collect(Collectors.toList()); //save in List
		return list;
	}

	public static void main(String[] args) {
		//create transaction list and add some transactions of vaying types and amnounts
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(new Transaction(123,"Fuel",33.33));
		transactions.add(new Transaction(124,"Groceries",120.12));
		transactions.add(new Transaction(125,"Beer",175.75));
		transactions.add(new Transaction(126,"Groceries",152.52));
		transactions.add(new Transaction(127,"Groceries",12.12));
		//extract transactions of Groceries, sort by amount spent, and get transaction IDs
		System.out.println("Traditional method: " + getTransactionIDTraditional(transactions,"Groceries"));
		System.out.println("Stream method: " + getTransactionIDStream(transactions,"Groceries"));
	}
}
