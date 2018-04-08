package gui;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

/**
 * A class to treat a JTextArea as an output stream.
 * 
 * Original code "stolen" from somewhere from the internet
 * then modified by me again. This version writes text
 * line by line and autoscrolls the textarea when text is added.
 * 
 * @author akkersdi
 * @version 2013-04-04
 * 
 */
class JTextAreaOutputStream extends OutputStream {

	// The area where the text will be shown
	private final JTextArea textArea;

	// A buffer to build a line of text
	private final StringBuilder sb = new StringBuilder();

	// ============================

	public JTextAreaOutputStream(final JTextArea textArea) {
		this.textArea = textArea;
		sb.ensureCapacity(132);
	}

	// ============================

	@Override
	public void flush() {
		if (sb.length() > 0) {
			final String text = sb.toString(); // get the resulting text
			sb.setLength(0); // reset the buffer contents
			textArea.append(text); // add the new text ...
			// ... and autoscroll to the bottom
			textArea.setCaretPosition(textArea.getDocument().getLength());
		}
	}

	@Override
	public void close() {
		flush();
	}

	@Override
	public void write(int b) throws IOException {
		if (b == '\r')
			return;
		else if (b == '\n') {
			sb.append('\n');
			flush();
		} else
			sb.append((char) b);
	}
}
