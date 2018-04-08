package gui;

import java.awt.event.*;
import javax.swing.*;

/**
 * A class to handle an double input field
 * 
 * @author R.Akkersdijk
 */
@SuppressWarnings("serial")
// package private class
class DoubleField extends JTextField { // implements TextListener
	private double value;
	private String text;
	private int caretPosition;

	public DoubleField(double value, int size) {
		super("" + value, size);
		this.value = value;
		text = "" + value;
		// addTextListener(this);
	}

	@Override
	// Intercept input
	public void processKeyEvent(KeyEvent ev) {
		if (InputChecker.isEditKey(ev) || InputChecker.isNumberKey(this, ev, true))
			super.processKeyEvent(ev); // accept input
		else
			ev.consume(); // discard input
	}

//	public void textValueChanged(TextEvent evt) {
//		try {
//			String tryText = getText();
//			caretPosition = getCaretPosition();
//			value = Double.valueOf(tryText).doubleValue();
//			text = tryText;
//		} catch (Exception e) {
//			setText(text);
//			setCaretPosition(caretPosition);
//		}
//	}

	public double getDouble() {
		try {
			String tryText = getText();
			caretPosition = getCaretPosition();
			value = Double.valueOf(tryText).doubleValue();
			text = tryText;
		} catch (Exception e) {
			setText(text);
			setCaretPosition(caretPosition);
		}
		return value;
	}
}
