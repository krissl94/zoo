package gui;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 * A class to validate keyboard input.
 * 
 * @author R.Akkersdijk
 */
// package-private class
class InputChecker {

	// Some edit key ?
	public static boolean isEditKey(KeyEvent ev) {
		int n = ev.getKeyCode();
		switch (n) {
			case KeyEvent.VK_HOME:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_KP_LEFT:
			case KeyEvent.VK_BACK_SPACE:
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_KP_RIGHT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_END:
				return true;
		}
		return false;
	}

	// Is this a valid key event for a number
	public static boolean isNumberKey(JTextField caller, KeyEvent ev, boolean period) {
		char c = ev.getKeyChar();
		if (java.lang.Character.isDigit(c))
			return true;
		if (c == '-') { // only allowed a first position
			int caretPosition = caller.getCaretPosition();
			// System.out.println("NC: c="+c+" @ "+caretPosition);
			return (caretPosition == 0);
		}
		if (period) { // period allowed ?
			if (c == '.') {
				String tryText = caller.getText();
				// System.out.println("NC '"+tryText+"'");
				int index = tryText.indexOf('.'); // already a . ?
				return (index == -1);
			}
		}
		return false;
	}

	// Is this a valid key event for a word/name
	public static boolean isWordKey(JTextField caller, KeyEvent ev, boolean space) {
		char c = ev.getKeyChar();
		if (java.lang.Character.isDigit(c) || (space && c == ' ')) // space allowed?
			return true;
		return false;
	}

}
