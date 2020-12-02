import java.util.*;

/**
 * Identifies unique words in a document, and the positions of their occurrences, via a map
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author Tim Pierson, CS 10, Spring 2019 -- added comments
 */
public class UniqueWordsPositions {
	public static void main(String[] args) {
		String page = "Pretend that this string was loaded from a web page.  We won't go to all that trouble here.  This string contains multiple words. And multiple copies of multiple words.  And multiple words with multiple copies.  It is to be used as a test to demonstrate how sets work in removing redundancy by keeping only one copy of each thing. Is it very very redundant in having more than one copy of some words?";
		String[] allWords = page.split("[ .,?!]+");
		
		// Declare new Map, each entry in the Map is a List that will hold the index where each word occurs (might be multiple locations)
		Map<String,List<Integer>> wordPositions = new TreeMap<String,List<Integer>>(); // word -> [position1, position2, ...]

		// Loop over all the words split out of the string, adding their positions in the string to the map
		for (int i=0; i<allWords.length; i++) {
			String word = allWords[i].toLowerCase();
			
			// Check to see if we have seen this word before, update wordCounts appropriately
			if (wordPositions.containsKey(word)) {
				// Now each item in the Map is a List of Integers, add the position of the word to the List
				wordPositions.get(word).add(i);
			}
			else {
				// Add the new word with a new list containing just this position
				List<Integer> positions = new ArrayList<Integer>();
				positions.add(i);
				wordPositions.put(word, positions);
			}
		}
		System.out.println(wordPositions);
	}
}
