import java.util.HashMap;
import java.util.Map;
/**
 * Boyer-Moore and Brute Force string matching algorithm from Data Structures and Algorithms in Java, 6th edition by Goodrich, Tamassa & Goldwasser.
 * @author Tim Pierson, Dartmouth CS 10, Spring 2019
 *
 */

public class BoyerMoore {
	
	/**
	 * Brute force search for pattern within string text.  
	 * @param text -- search this text to see if it contains pattern
	 * @param pattern -- look for this text inside text parameter
	 * @return -- return index of first match or -1 if not found
	 */
	public static int findBruteForce(char[] text, char[] pattern) {
		System.out.println("Brute force looking for " + String.valueOf(pattern) + " in " + String.valueOf(text));
		int n = text.length;
		int m = pattern.length;
		
		// Test for empty string
		if (m == 0) return 0;
		
		//brute force it -- loop over all characters in text O(n)
		for (int i=0;i<=n-m;i++) { //index into the text
			//loop over all characters in pattern while characters match O(m)
			int k = 0; //index into the pattern
			while (k<m && text[i+k] == pattern[k]) {
				k++;
			}
			//if at end of pattern, then found  match starting at index i in text
			if (k==m) {
				System.out.println("\tFound match at index " + i);
				return i;
			}
		}
		//match not found
		System.out.println("\tNo match found");
		return -1;
	}
	
	/**
	 * Boyer-Moore search for pattern within string text.  
	 * @param text -- search this text to see if it contains pattern
	 * @param pattern -- look for this text inside text parameter
	 * @return -- return index of first match or -1 if not found
	 */
	public static int findBoyerMoore(char[] text, char[] pattern) {
	  System.out.println("Boyer-Moore looking for " + String.valueOf(pattern) + " in " + String.valueOf(text));
	  int n = text.length;
	  int m = pattern.length;
	    
	  // Test for empty string
	  if (m == 0) return 0;
	    
	  // Initialization, create Map of last position of each character = O(n)
	  Map<Character, Integer> last = new HashMap<>();
	  for (int i = 0; i < n; i++) {
		  last.put(text[i], -1);   // set all chars, by default, to -1
	  }        
	  for (int i = 0; i < m; i++) {
		  last.put(pattern[i], i); // update last seen positions
	  }
	    
	  // Start with the end of the pattern aligned at index m-1 in the text. 
	  int i = m - 1;  // index into the text
	  int k = m - 1;  // index into the pattern
	  while (i < n) { 
		  if (text[i] == pattern[k]) { // match! return i if complete match; otherwise, keep checking.
			  if (k == 0) {
				  System.out.println("\tFound match at index " + i);
				  return i; // done!
			  }
			  i--; k--;
		  } 
		  else { // jump step + restart at end of pattern
			  i += m - Math.min(k, 1 + last.get(text[i]));  //move in text 
			  k = m - 1; //move to end of pattern
		  }
	  }
	  System.out.println("\tNo match found");
	  return -1; // not found
  }
	
  public static void main(String[] args) {
	  char[] text = "abcfefabddef".toCharArray();
	  char[] pattern = "abddef".toCharArray();
	  
	  findBruteForce(text,pattern);
	  findBoyerMoore(text,pattern);        
  }
}