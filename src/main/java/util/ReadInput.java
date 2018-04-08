package util;
//
//	====================================================================
//	Deze class is alleen een hulpje en valt buiten de praktikum opdracht
//	====================================================================
//
// Changes 1.3.2:
// AKK  20120322 - catch EOF when reading input
//
import java.io.*;

/**
 * A class to get input from the user.
 */
public class ReadInput {

	/**
	 * Prompts the user to enter a line of text
	 * 
	 * @param prompt
	 *        the text to be printed first
	 * @return the input line as a string
	 */
	public static String getString(String prompt) {
		assert prompt != null : "ReadInput.getString null prompt";
		// Prompt the user to enter some input
		System.out.print(prompt + ": ");
		// Open up standard input
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// Read the input from the command-line;
		// Have to use try/catch with the readLine() method
		try {
			String temp = br.readLine();
			if (temp == null) { // AKK 20120322 added - catch EOF
				System.out.println("Unexpected EOF seen");
				System.exit(1);
			}
			return temp;
		} catch (IOException ioe) {
			System.err.println("Oops, fatal IO error trying to read your input!");
			System.exit(1);
		}
		return null; // make the compiler happy
	}

	/**
	 * Prompts the user to enter an integer
	 * 
	 * @param prompt
	 *        the text to be printed first
	 * @return the integer entered
	 */
	public static int getInt(String prompt) {
		assert prompt != null : "ReadInput.getInt null prompt";
		for (;;) {
			String temp = getString(prompt);
			try {
				return Integer.parseInt(temp);
			} catch (NumberFormatException nfe) {
				System.out.println("Illegal character in your input! Try again.");
			}
		}
	}

} // end of ReadInput class
