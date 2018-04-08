package gui;

import java.awt.event.*;
import javax.swing.*;

/**
 * A class to handle an int input field
 * 
 * @author R.Akkersdijk
 */
@SuppressWarnings("serial")
// package private class
class IntField extends JTextField { // implements TextListener
	private int value;
	private String text;
	private int caretPosition;

	public IntField(int value, int size) {
		super("" + value, size);
		this.value = value;
		text = "" + value;
		// addTextListener(this);
	}

	@Override
	// Intercept input
	public void processKeyEvent(KeyEvent ev) {
		if (InputChecker.isEditKey(ev) || InputChecker.isNumberKey(this, ev, false))
			super.processKeyEvent(ev); // accept input
		else
			ev.consume(); // discard input
	}

//	public void textValueChanged(TextEvent evt) {
//		try {
//			String tryText = getText();
//			caretPosition = getCaretPosition();
//			value = Integer.parseInt(tryText);
//			text = tryText;
//		} catch (Exception e) {
//			setText(text);
//			setCaretPosition(caretPosition);
//		}
//	}

	public int getInt() {
		try {
			String tryText = getText();
			caretPosition = getCaretPosition();
			value = Integer.parseInt(tryText);
			text = tryText;
		} catch (Exception e) {
			setText(text);
			setCaretPosition(caretPosition);
		}
		return value;
	}

}
