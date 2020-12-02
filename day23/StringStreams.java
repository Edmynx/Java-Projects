import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

/**
 * Fun with Java 8 pipelines/streams of strings
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 */
public class StringStreams {
	/**
	 * Returns an igPay atinLay version of the string
	 * Go to first vowel, return rest of word, followed by start of word to first vowel
	 * followed by "ay"
	 */
	static String toPigLatin(String s) {
		int i = 0;
		while (i<s.length()) {
			char c = s.charAt(i);
			if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') break;
			i++;
		}
		return s.substring(i) + s.substring(0, i) + "ay";
	}
	
	public static void main(String[] args) throws Exception {
		Scanner console = new Scanner(System.in);
		int ex = 1;
		System.out.println("example "+(ex++));
		
		List<String>strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
		List<String> filtered = strings.stream()
				.filter(string -> !string.isEmpty())
				.collect(Collectors.toList());
		System.out.println(filtered);

		//NOTE: Java's syntax of :: means pass defined method on right part to left part
		// Start with Stream composed of fixed list of Strings
		// Just print them out by passing them as a parameter to println
		Stream.of("hi", "there")
		 	  .forEach(System.out::println);
		
		//pause before going on to next example
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// Print out their lengths
		// first determine length of each string, then pass length to println
		Stream.of("hi", "there")
			  .map(String::length)
		 	  .forEach(System.out::println);
		
		//pause
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// Translate each to pig Latin by calling our function above, then passing to print
		// so map string to pig Latin version, then print each
		Stream.of("mary", "had", "a", "little", "lamb", "it's", "fleece", "was", "white", "as", "snow")
			  .map(StringStreams::toPigLatin) //our static function above
			  .forEach(System.out::println);
		
		
		//pause
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// Keep only three-letter words
		// first create list of words
		// then create a stream from list of words, filter and print those meeting filter
		List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog");		
		words.stream()
			 .filter(w -> w.length() == 3) //some words discarded and not passed to next operation (print)
			 .forEach(System.out::println);
		
		//pause
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// Keep only three-letter words, removing duplicates and sorting
		words.stream()
			 .filter(w -> w.length() == 3)
        	 .distinct()
        	 .sorted()
			 .forEach(System.out::println);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// Let's get word counts this way!
		String page = "Pretend that this string was loaded from a web page.  We won't go to all that trouble here.  This string contains multiple words. And multiple copies of multiple words.  And multiple words with multiple copies.  It is to be used as a test to demonstrate how sets work in removing redundancy by keeping only one copy of each thing. Is it very very redundant in having more than one copy of some words?";
		String[] pageWords = page.split("[ .,?!]+"); // split on punctuation and white space
		Map<String, Long> wordCounts =
				Stream.of(pageWords) //loop over all words
					  .map(String::toLowerCase) //convert each to lower case
					  .collect(Collectors.groupingBy((String w) -> w, Collectors.counting())); //collect by grouping on W and counting
		System.out.println(wordCounts);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Or how many words start with each letter
		Map<Character, Long> firstLetterCounts =
				Stream.of(pageWords)
					  .map(String::toLowerCase)
					  .collect(Collectors.groupingBy((String w) -> w.charAt(0), Collectors.counting()));
		System.out.println(firstLetterCounts);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// Or letter counts (using single-character Strings for simplicity)
		Map<String, Long> charCounts =
				Stream.of(pageWords)
					  .map(String::toLowerCase) //lower case
					  .map(w -> w.split("")) //split out each letter, now have stream of steam of characters, want single stream of chars
					  .flatMap(Arrays::stream) //flatten letters into one array of characters
					  .collect(Collectors.groupingBy((String c) -> c, Collectors.counting()));
		System.out.println(charCounts);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Can process a file, here Romeo & Juliet, limiting output
		Files.lines(Paths.get("inputs/shakespeare/romeoAndJuliet.txt")) //read line into stream
        	 	 .map(line -> line.split("\\W+")) //split out words (this returns a stream of string arrays, not a stream of strings)
        	 	 .flatMap(Arrays::stream) //map words into single stream
        	 	 .map(String::toLowerCase) //lower case
        	 	 .filter(w -> w.length() == 4) //keep only words of length 4
        	 	 .distinct() //distinct
        	 	 .sorted() //sorted
        	 	 .limit(25) //keep first 25 then move on
        	 	 .forEach(System.out::println); //print
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// How many 4-letter words?  Same as before, count as last step
		long count = Files.lines(Paths.get("inputs/shakespeare/romeoAndJuliet.txt"))
    			  .map(line -> line.split("\\W+"))
    			  .flatMap(Arrays::stream)
				  .map(String::toLowerCase)
				  .filter(w -> w.length() == 4)
				  .distinct()
				  .count();
		System.out.println("# 4-letter words:"+count);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// By length
		Files.lines(Paths.get("inputs/shakespeare/romeoAndJuliet.txt"))
			 .map(line -> line.split("\\W+"))
			 .flatMap(Arrays::stream)
			 .map(String::toLowerCase)
        	 	 .distinct()
        	 	 .sorted((w1,w2) -> w2.length() - w1.length())  //provide a comparator for sorting
        	 	 .limit(25) //keep top 25
        	 	 .forEach(System.out::println);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Average word length
		double len = Files.lines(Paths.get("inputs/shakespeare/romeoAndJuliet.txt"))
  			  		      .map(line -> line.split("\\W+"))
  			  		      .flatMap(Arrays::stream)
						  .mapToDouble(String::length)
						  .average()
						  .getAsDouble();
		System.out.println("average length over all words: "+len);
		
		System.out.println("All done!");
	}
}
