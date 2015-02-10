/**
 * @author Cyndi Ai
 * 
 * Converts the given string into a long
 * Assumptions
 * 		1. Return 0 when input is empty or with only spaces
 * 		2. There *can* be multiple spaces in front
 * 		3. There *can* be ONE positive or negative signs in front
 * 		4. If there are any other chars other than the ONE +/- or numbers
 * 		   NumberFormatException will be thrown
 * 		5. For anything greater than Long.MAX_VALUE or less than Long.MIN_VALUE
 * 			Min/Max will be returned.
 * 
 * JUnit Test code available in src.testStringToLong
 */
public class StringToLong {
	/**
	 * converts the string passed in into a long, if the string is empty
	 * or with all spaces 0 is returned
	 * 
	 * @param s the String to be converted
	 * @return a long according to the string passed in
	 * @throws NumberFormatException if the format of s passed in is 
	 * 			incorrect (including non number characters or more than one
	 * 			signs)
	 * @requires nothing greater than Long.MAX_VALUE or less than Long.MIN_VALUE
	 */
	public static long stringToLong(String s) {
		// deals with the spaces 
		s = s.trim();
		if (s.length() == 0) {
			return 0;
		}
		
		int sign = 1;		// without signs, it is postive
		int index = 0;		// current index working on

		// deal with the sign
		if (s.charAt(index) == '-') {
			sign = -1;
			index++;
		} else if (s.charAt(index) == '+') {
			sign = 1;
			index++;
		}
		
		long result = 0;
		long comparison2 = Long.MAX_VALUE % 10;		// to detect overflow/underflow, 1's digit
		long comparison1 = Long.MAX_VALUE / 10;		// to detect overflow/underflow, other digits
		
		// for each char inside the string
		while (index < s.length()) {
			int current = s.charAt(index) - '0'; 
			if (current >= 0 && current <= 9) {
				// detect any over/under flow
				if(result > comparison1 || (result == comparison1 && current > comparison2)) { 
					if (sign == -1) {
						return Long.MIN_VALUE;
					} else {
						return Long.MAX_VALUE;
					}
				}
				result = result * 10 + current;
			} else {
				throw new NumberFormatException("invalid string passed in");
			}
			index ++;
		}
	
		return sign * result;
	}
}
