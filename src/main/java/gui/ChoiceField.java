package gui;

import javax.swing.*;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Provides a wrapper for use by the Questioner.
 * 
 * @author E.Jannink
 */
@SuppressWarnings({"serial", "rawtypes"})
// package private class
class ChoiceField extends JComboBox {

	@SuppressWarnings("unchecked")
	public ChoiceField(Enumeration choices) {
		while (choices.hasMoreElements()) {
			String choice = choices.nextElement().toString();
			addItem(choice);
		}
	}

	@SuppressWarnings("unchecked")
	public ChoiceField(Iterable<Object> the_choices) {
		Iterator<Object> choices = the_choices.iterator();
		while (choices.hasNext()) {
			String choice = choices.next().toString();
			addItem(choice);
		}
	}

	@SuppressWarnings("unchecked")
	public ChoiceField(Object[] the_choices) {
		for (Object object: the_choices) {
			String choice = object.toString();
			addItem(choice);
		}
	}

	@Override
	public String getSelectedItem() {
		return (String) super.getSelectedItem();
	}

}
