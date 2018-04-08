package gui;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.*;

/**
 * Provides an alternative for the standard output.
 * 
 * You could also use it to send System.out to a window by using:
 * 
 * <pre>
 * System.out(reporter.getPrintStream());
 * </pre>
 * 
 * The user can copy &amp; paste the contents for further use.
 * 
 * @author E.Jannink
 * @author R.Akkersdijk
 * @version 10 november 2014
 */
@SuppressWarnings("serial")
public class Reporter extends JDialog {

	// The attributes

	private JTextArea textArea; // the actual contents

	private static int counter = 0; // to ensure multiple Reporter instances
									// don't overlap

	/**
	 * Constructs a small outputFrame of size(10, 30) and shows it.
	 * 
	 * The frame is closeable();
	 * 
	 * @param title
	 *            The title of the window
	 */
	public Reporter(String title) {
		this(title, 10, 30);
	}

	/**
	 * Constructs an outputFrame of given size and shows it.
	 * 
	 * The frame is closeable().
	 * 
	 * @param title
	 *            The title of the window
	 * @param rows
	 *            The number of text rows
	 * @param cols
	 *            The number of text columns
	 * 
	 * @exception AssertionError
	 *                title String can not be null
	 * @exception AssertionError
	 *                title String can not be empty
	 * @exception AssertionError
	 *                rows must at least be one
	 * @exception AssertionError
	 *                cols must at least be one
	 */
	public Reporter(String title, int rows, int cols) {
		super(new JFrame(), title, false);

		assert title != null : "title String can not be null";
		assert !title.isEmpty() : "title String can not be empty";
		assert rows > 0 : "rows must at least be one";
		assert cols > 0 : "cols must at least be one";

		textArea = new JTextArea(rows, cols);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// dispose(); // not used because of slow response
				remove();
			}
		});

		// add some default offset to stack multiple instances
		setLocation(20 + counter * 20, 20 + counter * 20);
		++counter;

		// contents
		textArea.setFont(new Font("Monospaced", Font.BOLD, 16));
		textArea.setEditable(false);
		textArea.setLineWrap(false);
		// add the textarea to the window
		JScrollPane jsp = new JScrollPane(textArea);
		add(jsp);
		pack();
		setVisible(true);
	}

	// ---------------------------

	/**
	 * Set the text color. This applies to the entire window!
	 * 
	 * @param color
	 *            The desired foreground color
	 * 
	 * @exception AssertionError
	 *                can not set foreground to null color
	 */
	@Override
	public void setForeground(Color color) {
		assert color != null : "can not set foreground to null color";
		if (textArea == null)
			super.setForeground(color);
		else
			textArea.setForeground(color);
	}

	/**
	 * Set the background color. This applies to the entire window!
	 * 
	 * @param color
	 *            The desired background color
	 * 
	 * @exception AssertionError
	 *                can not set background to null color
	 */
	@Override
	public void setBackground(Color color) {
		assert color != null : "can not set background to null color";
		if (textArea == null)
			super.setBackground(color);
		else
			textArea.setBackground(color);
	}

	/**
	 * Set the text font. This applies to the entire window!
	 * 
	 * @param font
	 *            the desired font
	 * @exception AssertionError
	 *                can not set font to null font
	 */
	@Override
	public void setFont(Font font) {
		assert font != null : "can not set font to null font";
		if (textArea == null)
			super.setFont(font);
		else
			textArea.setFont(font);
	}

	// ---------------------------

	/**
	 * Returns an OutputStream on the outputFrame.
	 * 
	 * @return the outputstream
	 */
	public OutputStream getOutputStream() {
		return new JTextAreaOutputStream(textArea);
	}

	/**
	 * Returns a PrintStream on the outputFrame which e.g. could be used for
	 * System.setout(...);
	 * 
	 * @return the printstream
	 */
	public PrintStream getPrintStream() {
		return new PrintStream(getOutputStream());
	}

	// ---------------------------

	/**
	 * Appends thisText to the contents of the outputFrame.
	 * 
	 * @param thisText
	 *            the text to be added
	 * 
	 * @exception AssertionError
	 *                can not append a null string
	 */
	public void print(String thisText) {
		assert thisText != null : "can not append a null string";
		textArea.append(thisText);
	}

	/**
	 * Appends thisText to the outputFrame, adding an EOL character.
	 * 
	 * @param thisText
	 *            the text to be added
	 * 
	 * @exception AssertionError
	 *                can not append a null string
	 */
	public void println(String thisText) {
		assert thisText != null : "can not append a null string";
		print(thisText + "\n");
	}

	/**
	 * Appends an EOL character to the outputFrame
	 */
	public void println() {
		println("");
	}

	// ---------------------------

	/**
	 * Removes this reporter, same as closing the window.
	 */
	public void remove() {
		// dispose(); // not used cause of slow response
		setVisible(false);
	}

}
