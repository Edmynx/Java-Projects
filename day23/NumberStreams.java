
//import java.util.Arrays;
//import java.util.List;
import java.util.Scanner;
import java.util.stream.*;

/**
 * Fun with Java 8 pipelines/streams of numbers
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2016
 */

public class NumberStreams {
	public static void main(String[] args) throws Exception {		
		Scanner console = new Scanner(System.in);
		int ex = 1;
		System.out.println("example "+(ex++));
		
		// Numbers in a range
		IntStream.range(1, 10) //exclusive of 10, so 1..9 here
				 .forEach(System.out::println);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
	
		// Numbers in a range, filtered to evens, and tripled
		IntStream.range(1, 10)
				 .filter(i -> i%2 == 0)
				 .map(i -> i*3)
				 .forEach(System.out::println);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Reduction: sum
		int sum =
				IntStream.range(1, 10)
						 .reduce(0, (a,b)->a+b); //start at 0, take previous result (a,first time 0), add next number (b), return result to next stage (first time 0+1)
		System.out.println(sum);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Reduction: product
		int product =
				IntStream.range(1, 5)
						 .reduce(1, (a,b)->a*b);  //start at 1, multiply next number by prior result (initially 1)
		System.out.println(product);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// It's lazy!
		IntStream.range(0,10)
			     .map(i -> { System.out.println("mapping "+i+" to "+(i+1)); return i+1; })
			     .limit(3) //demand for 3 items, previous won't produce unless consumer asks it for, so only first three processed
		 		 .forEach(i -> { System.out.println("producing "+i); });
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// So no need to be finite
		IntStream.iterate(0, i -> i+1)
				 .map(i -> { System.out.println("mapping "+i+" to "+(i+1)); return i+1; })
	     		 .limit(10)
		 		 .forEach(i -> { System.out.println("producing "+i); });
		System.out.println("<enter> for example "+(ex++)); console.nextLine();

		// Take increasingly bigger steps
		IntStream.iterate(1, i -> i*2)
		 		 .limit(10)
		 		 .forEach(System.out::println);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Generate randoms and average them
		double ave =
				DoubleStream.generate(Math::random) //each element [0,1)
							.limit(1000)
							.average()
							.getAsDouble();
		System.out.println(ave);
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Parallel
		IntStream.range(1, 10)
				 .parallel()
				 .filter(i -> { System.out.println("f "+i); return i%2 == 0; })
				 .map(i -> { System.out.println("m "+i); return i*3; })
				 .sum();
		System.out.println("<enter> for example "+(ex++)); console.nextLine();
		
		// Timing test: sequential vs. parallel
		System.out.println("Wait for it");
		final int num = 1000000000;
		long seqStart = System.nanoTime();
		IntStream.range(1, num)
				 .filter(i -> i%2 == 0)
				 .map(i -> i*3)
				 .sum();
		long seqEnd = System.nanoTime();
		long parStart = System.nanoTime();
		IntStream.range(1, num)
				 .parallel()
				 .filter(i -> i%2 == 0)
				 .map(i -> i*3)
				 .sum();
		long parEnd = System.nanoTime();
		System.out.println("sequential:" + (seqEnd - seqStart)/1000000000.0);
		System.out.println("parallel:" + (parEnd - parStart)/1000000000.0);
		
		System.out.println("All done!");
	}
}












/*  example from slides
 		List<Integer>	numbers	=	Arrays.asList(1,	2,	3,	4,	5,	6,	7,	8);	
		List<Integer>	twoEvenSquares	=		
						numbers.stream()	
						.filter(n	->	{	
										System.out.println("filtering	"	+	n);		
										return	n	%	2	==	0;	
										})	
						.map(n	->	{	
										System.out.println("mapping	"	+	n);	
										return	n	*	n;	
									})	
						.limit(2)	
						.collect(Collectors.toList());
		
		System.out.println(twoEvenSquares);
		
		
		
		
		
			IntStream.range(1,10)
			.map(n->{System.out.println("m1 " + n); return n;})
			.map(n1->{System.out.println("m2 " + n1); return n1;})
			.map(n2->{System.out.println("m3 " + n2); return n2;})
			.forEach(System.out::println);
			
		System.out.println("moving on");
*/
