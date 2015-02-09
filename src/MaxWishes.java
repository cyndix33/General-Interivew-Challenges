import java.util.Scanner;

/**
 * The question was not clearly stated so I have made the following assumptions
 * 		1. Only accept capital T and P
 * 		2. Throw IllegalArugmentException when,
 * 				a. incorrect number of columns input as stated in the first line
 * 				b. incorrect number of rows input as stated in the first line
 * 				c. the dimension (the first line) is not two numbers separated by two spaces
 * 				d. Within the matrix, letters/numbers other than capital T or P were input
 * 		3. When M or N was out of bound
 * 
 * From Palantir
 * @author Cyndi Ai
 *
 */
public class MaxWishes {
	private static int[] dimension;		// keeps track of the dimension of the matrix
	private static int[] countWishes;	// countSuccess[i] keeps track 
										// of the number of wishes in flipping at i
	
	public static void main(String args[]) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		getDimension(scanner); 			// sets up the dimension
		processMatrix(scanner);			// count the success while processing the lines 
		
		scanner.close();
		
		outputInfo();					// generates and output the biggest success
	}
	
	/**
	 * generates the dimension array and get everything initialized
	 * 
	 * @param s the scanner that will be using reading from input
	 * @throws IllegalArgumentException when the input is not in the format of #  #
	 * 			or the numbers are out of bound
	 */
	private static void getDimension(Scanner s) {
		String[] temp = s.nextLine().split("  ");
		
		if (temp.length == 2) {
			dimension = new int[temp.length];
			for (int i = 0; i < 2; i++) {
				try {
					// parse the input dimension string to int and save it into dimension
					int dimen = Integer.parseInt(temp[i]);
					dimension[i] = dimen;
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException("you should only input numbers");
				}
			}
		} else {
			throw new IllegalArgumentException("the format of inputting dimension "
					+ "should be M  N");
		}
		
		if (dimension[0] > 100000 || dimension[0] < 1) {
			throw new IllegalArgumentException("M should be within the range of "
					+ "1 to 10,0000 inclusively");
		}
		
		if (dimension[1] > 500 || dimension[1] < 1) {
			throw new IllegalArgumentException("N should be within the range of "
					+ "1 to 500 inclusively");
		}
		
		countWishes = new int[dimension[1]];
	}
	
	/**
	 * Read through the matrix inputed and keeps track of the wishes gained as if we are
	 * flipping at i in countWishes
	 * 
	 * @param s	the scanner using the read from input
	 * @throws IllegalArgumentException when the size of the matrix input is not
	 * 			the same as the size required at the first line of input
	 */
	private static void processMatrix(Scanner s) {
		for (int i = 0; i < dimension[0]; i++) {
			// for each row
			if (s.hasNext()) {
				String line = s.nextLine();
				if (line.length() != dimension[1]) {
					throw new IllegalArgumentException("the size of each line should be "
							+ dimension[1]);
				} else {
					int countT = 0;			// keeps track of the number of Ts in the row
					int countP = 0;			// keeps track of the number of Ps in the row
					int indexT = -1;		// keeps track of the last/only index of T 
					int indexP = -1;		// keeps track of the last/only index of P
					
					// for each column inside the row
					for (int j = 0; j < dimension[1]; j++) {
						// when there is no way to gain wish in this row
						// i.e. more than 1 Ts and Ps 
						if (countT > 1 && countP > 1) {
							break;
						}
						
						// even though the indexT and indexP keeps changing its location
						// we only care when the P or T exists just one time
						// so it will keep track of the index of the T's or P's location
						if (line.charAt(j) == 'T') {
							countT++;
							indexT = j;
						} else if (line.charAt(j) == 'P'){
							countP++;
							indexP = j;
						} else {
							throw new IllegalArgumentException("you should only input T or P");
						}
					}
					
					if (countT == 1) {
						countWishes[indexT]++;
					}
					if (countP == 1) {
						countWishes[indexP]++;
					}
				}
			} else {
				throw new IllegalArgumentException("the number of lines should be "
						+ dimension[0]);
			}
		}
	}
	
	/**
	 * finds the max in the countWishes array and output as 
	 * the maximum number of wishes one can receive
	 */
	private static void outputInfo() {
		int bestColumn = 0;
		for (int i = 0; i < countWishes.length; i++) {
			if (countWishes[i] > bestColumn) {
				bestColumn = countWishes[i];
			}
		}
		System.out.println(bestColumn);
	}
}
