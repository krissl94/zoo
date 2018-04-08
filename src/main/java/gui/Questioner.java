package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * <p>
 * Provides two ways for dealing with questions.
 * </p>
 * 
 * <p>
 * The easy way to ask for a String, int or double is to use the static
 * <tt>askString</tt>, <tt>askInt</tt>, <tt>askDouble</tt> or <tt>askChoice</tt>
 * methods. These setup a Questioner and pose the question. When the ok-button
 * is clicked, the result is evaluated and returned.
 * </p>
 * 
 * <p>
 * You can also build a Questioner that contains more questions by creating a
 * Questioner instance yourself and then add multiple questions. When ready
 * adding questions, make the Questioner show itself.<br>
 * When the OK-button is applied, the window will disappear and you may retrieve
 * the replies to the various questions with <tt>getString</tt>, <tt>getInt</tt>,
 * <tt>getDouble</tt> or <tt>getChoice</tt>.<br>
 * Make sure to ask for the right type of reply value to avoid casting
 * exceptions.
 * </p>
 */
@SuppressWarnings("serial")
public class Questioner extends JDialog implements ActionListener {

	private Hashtable<String, Component> questions = new Hashtable<String, Component>();

	private JPanel labelPanel = new JPanel(new GridLayout(0, 1));
	private JPanel answerPanel = new JPanel(new GridLayout(0, 1));
	private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JButton ok = new JButton("Ok");
	private JButton cancel = new JButton("Cancel");

	private boolean cancelled = false;

	/**
	 * Constructs the Questioner-dialog
	 * 
	 * @param title
	 *            Title of the window
	 * 
	 * @exception AssertionError
	 *                title String can not be null
	 * @exception AssertionError
	 *                title String can not be empty
	 */
	public Questioner(String title) {
		super(new JFrame(), true);
		assert title != null : "title String can not be null";
		assert !title.isEmpty() : "title String can not be empty";

		setTitle(title);
		setLayout(new BorderLayout());
		buttonPanel.add(cancel);
		cancel.addActionListener(this);
		buttonPanel.add(ok);
		ok.addActionListener(this);
		add(buttonPanel, BorderLayout.SOUTH);
		add(labelPanel, BorderLayout.WEST);
		add(answerPanel, BorderLayout.CENTER);
	}

	// =============================================
	// Various static methods for one-shot questions
	// =============================================

	/**
	 * Returns a String value in reply to a question.
	 * 
	 * @param question
	 *            The question String
	 * @param value
	 *            The initial answer string for this question
	 * @return the confirmed answer or, if not confirmed the initial answer
	 * 
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                initial answer String can not be null
	 */
	public static String askString(String question, String value) {
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert value != null : "initial answer String can not be null";

		Questioner questioner = new Questioner("Ask for a String");
		questioner.addString(question, value);
		questioner.setVisible(true);
		return questioner.getString(question);
	}

	/**
	 * Returns a int value in reply to a question.
	 * 
	 * An input key is only accepted if the evaluation of the text would provide
	 * a legal int value, otherwise it is ignored.
	 * 
	 * @param question
	 *            The question String
	 * @param value
	 *            The initial answer value for this question
	 * @return the confirmed answer or, if not confirmed the initial answer
	 * 
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 */
	public static int askInt(String question, int value) {
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";

		Questioner questioner = new Questioner("Ask for an integer");
		questioner.addInt(question, value);
		questioner.setVisible(true);
		return questioner.getInt(question);
	}

	/**
	 * Returns a double value in reply to a question.
	 * 
	 * An input key is only accepted if the evaluation of the text provides a
	 * legal double value, otherwise it is ignored.
	 * 
	 * @param question
	 *            The question String
	 * @param value
	 *            The initial answer value for this question
	 * @return the confirmed answer or, if not confirmed the initial answer
	 * 
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 */
	public static double askDouble(String question, double value) {
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";

		Questioner questioner = new Questioner("Ask for a double");
		questioner.addDouble(question, value);
		questioner.setVisible(true);
		return questioner.getDouble(question);
	}

	/**
	 * Returns the item selected from an array of choices.
	 * 
	 * Their toString() method is used to make the actual list.
	 * 
	 * @param question
	 *            The question String
	 * @param choices
	 *            The array of choices
	 * @return the String for the selected choice
	 * 
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                choices should have at least one element
	 */
	public static String askChoice(String question, Object[] choices) {
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert choices.length > 0 : "choices should have at least one element";

		Questioner questioner = new Questioner("Make a choice");
		questioner.addChoice(question, choices);
		questioner.setVisible(true);
		return questioner.getChoice(question);
	}

	/**
	 * Returns the item selected out of an Enumeration of choices.
	 * 
	 * Their toString() method is used to make the actual list.
	 * 
	 * @param question
	 *            The question String
	 * @param choices
	 *            The Enumeration of choices
	 * @return the selected choice
	 * 
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                choices should have at least one element
	 */
	@SuppressWarnings("rawtypes")
	public static String askChoice(String question, Enumeration choices) {
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert choices.hasMoreElements() : "choices should have at least one element";

		Questioner questioner = new Questioner("Make a choice");
		questioner.addChoice(question, choices);
		questioner.setVisible(true);
		return questioner.getChoice(question);
	}

	/**
	 * Returns the item selected out of an Iterable of choices.
	 * 
	 * Their toString() method is used to make the actual list.
	 * 
	 * @param question
	 *            The question String
	 * @param choices
	 *            The Iterable of choices
	 * @return the selected choice
	 * 
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                choices should have at least one element
	 */
	public static String askChoice(String question, Iterable<Object> choices) {
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert choices.iterator().hasNext() : "choices should have at least one element";

		Questioner questioner = new Questioner("Make a choice");
		questioner.addChoice(question, choices);
		questioner.setVisible(true);
		return questioner.getChoice(question);
	}

	// ===== STRING question =====

	/**
	 * Adds a String question to the question list.
	 * 
	 * The room for the answer is 40 characters wide.
	 * 
	 * @param question
	 *            The question String
	 * @param initAnswer
	 *            The initial answer String for the question
	 * 
	 * @exception AssertionError
	 *                Questioner may not be visible
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                Question already present
	 * @exception AssertionError
	 *                Initial answer String can not be null
	 */
	public void addString(String question, String initAnswer) {
		assert !isVisible() : "Questioner may not be visible";
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert !questions.containsKey(question) : "Question already present";
		assert initAnswer != null : "Initial answer String can not be null";

		JLabel label = new JLabel(question);
		labelPanel.add(label);
		JTextField textField = new JTextField(initAnswer, 40);
		questions.put(question, textField);
		answerPanel.add(textField);
	}

	public void addString(String question) {
		addString(question, "");
	}

	/**
	 * Returns the current answer as a String.
	 * 
	 * All types of questions can return their String value.
	 * 
	 * @param question
	 *            The question String
	 * @return the answer string
	 * 
	 * @exception AssertionError
	 *                question should be present in this Questioner
	 */
	public String getString(String question) {
		assert questions.containsKey(question) : "question should be present in this Questioner";

		return ((JTextField) questions.get(question)).getText();
	}

	// ===== INT question =====

	/**
	 * Adds a question for an integer value to the question list
	 * 
	 * An input key is only accepted if the evaluation of the text provides a
	 * legal int value, otherwise it is ignored.
	 * 
	 * @param question
	 *            The question String
	 * @param initAnswer
	 *            The initial answer to the question
	 * 
	 * @exception AssertionError
	 *                Questioner may not be visible
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                question is already present
	 */
	public void addInt(String question, int initAnswer) {
		assert !isVisible() : "Questioner may not be visible";
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert !questions.containsKey(question) : "question already present";

		JLabel label = new JLabel(question);
		labelPanel.add(label);
		IntField intField = new IntField(initAnswer, 10);
		questions.put(question, intField);
		answerPanel.add(intField);
	}

	public void addInt(String question) {
		addInt(question, 0);
	}

	/**
	 * Returns the int value for the question. The caller must be sure the
	 * question was about an int value.
	 * 
	 * @param question
	 *            The question String
	 * @return the int entered
	 * 
	 * @exception AssertionError
	 *                Question should be present in this Questioner
	 * @exception AssertionError
	 *                Wrong type for this question
	 */
	public int getInt(String question) {
		assert questions.containsKey(question) : "Question should be present in this Questioner";
		Object reply = questions.get(question);
		assert (reply instanceof IntField) : "Wrong type for this question";

		return ((IntField) questions.get(question)).getInt();
	}

	// ===== DOUBLE question =====

	/**
	 * Adds a question for a double value to the questionlist.
	 * 
	 * An input key is only accepted if the evaluation of the text provides a
	 * legal double value, otherwise it is ignored.
	 * 
	 * @param question
	 *            The question String
	 * @param value
	 *            The initial answer to the question
	 * 
	 * @exception AssertionError
	 *                Questioner may not be visible
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                question is already present
	 */
	public void addDouble(String question, double value) {
		assert !isVisible() : "questioner may not be visible";
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert !questions.containsKey(question) : "question is already present";

		JLabel label = new JLabel(question);
		labelPanel.add(label);
		DoubleField doubleField = new DoubleField(value, 15);
		questions.put(question, doubleField);
		answerPanel.add(doubleField);
	}

	public void addDouble(String question) {
		addDouble(question, 0);
	}

	/**
	 * Returns the double value for the question. The caller must be sure the
	 * question was about a double value.
	 * 
	 * @param question
	 *            The question String
	 * @return the double vaue entered
	 * 
	 * @exception AssertionError
	 *                question should be present in this Questioner
	 * @exception AssertionError
	 *                Wrong type for this question
	 */
	public double getDouble(String question) {
		assert questions.containsKey(question) : "question should be present in this Questioner";
		assert (questions.get(question) instanceof DoubleField) : "Wrong type for this question";

		return ((DoubleField) questions.get(question)).getDouble();
	}

	// ===== CHOICE question =====

	/**
	 * Adds a choice question to the questionlist.
	 * 
	 * The choices are taken from a array of Objects, who's toString() method is
	 * invoked to get their String representation.
	 * 
	 * Of the chosen item, the string representation is returned.
	 * 
	 * @param question
	 *            The question String
	 * @param choices
	 *            All the choices as an array of Objects.
	 * 
	 * @exception AssertionError
	 *                Questioner may not be visible
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                question is already present
	 * @exception AssertionError
	 *                choices should have at least one element
	 */
	public void addChoice(String question, Object[] choices) {
		assert !isVisible() : "Questioner may not be visible";
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert !questions.containsKey(question) : "question already present";
		assert choices.length > 0 : "choices should have at least one element";

		JLabel label = new JLabel(question);
		labelPanel.add(label);
		ChoiceField choiceField = new ChoiceField(choices);
		questions.put(question, choiceField);
		answerPanel.add(choiceField);
	}

	/**
	 * Adds a choice question to the questionlist.
	 * 
	 * The choices are taken from a Enumeration of Objects, who's toString()
	 * method is invoked to get their String representation.
	 * 
	 * Of the chosen item, the string representation is returned.
	 * 
	 * @param question
	 *            The question String
	 * @param choices
	 *            All the choices as an Enumeration
	 * 
	 * @exception AssertionError
	 *                Questioner may not be visible
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                question is already present
	 * @exception AssertionError
	 *                choices should have at least one element
	 */
	@SuppressWarnings("rawtypes")
	public void addChoice(String question, Enumeration choices) {
		assert !isVisible() : "Questioner may not be visible";
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert !questions.containsKey(question) : "question already present";
		assert choices.hasMoreElements() : "choices should have at least one element";

		JLabel label = new JLabel(question);
		labelPanel.add(label);
		ChoiceField choiceField = new ChoiceField(choices);
		questions.put(question, choiceField);
		answerPanel.add(choiceField);
	}

	/**
	 * Adds a choice question to the questionlist.
	 * 
	 * The choices are taken from an Iterable of Objects, who's toString()
	 * method is invoked to get their String representation.
	 * 
	 * Of the chosen item, the string representation is returned.
	 * 
	 * @param question
	 *            The question String
	 * @param choices
	 *            All the choices as an Iterable
	 * 
	 * @exception AssertionError
	 *                Questioner may not be visible
	 * @exception AssertionError
	 *                question String can not be null
	 * @exception AssertionError
	 *                question String can not be empty
	 * @exception AssertionError
	 *                question is already present
	 * @exception AssertionError
	 *                choices should have at least one element
	 */
	public void addChoice(String question, Iterable<Object> choices) {
		assert !isVisible() : "Questioner may not be visible";
		assert question != null : "question String can not be null";
		assert !question.isEmpty() : "question String can not be empty";
		assert !questions.containsKey(question) : "question already present";
		assert choices.iterator().hasNext() : "choices should have at least one element";

		JLabel label = new JLabel(question);
		labelPanel.add(label);
		ChoiceField choiceField = new ChoiceField(choices);
		questions.put(question, choiceField);
		answerPanel.add(choiceField);
	}

	/**
	 * Returns the selected string from the given choices.
	 * 
	 * The caller must be sure the question was a choice.
	 * 
	 * @param question
	 *            The question string
	 * @return the selected choice string
	 * 
	 * @exception AssertionError
	 *                question should be present in this Questioner
	 * @exception AssertionError
	 *                wrong type for this question
	 */
	public String getChoice(String question) {
		assert questions.containsKey(question) : "question should be present in this Questioner";
		assert (questions.get(question) instanceof ChoiceField) : "wrong type for this question";

		return ((ChoiceField) questions.get(question)).getSelectedItem();
	}

	/**
	 * Returns the index of the selected item from the given choices.
	 * 
	 * The caller must be sure the question was a choice.
	 * 
	 * @param question
	 *            The question String
	 * @return the index
	 * 
	 * @exception AssertionError
	 *                question should be present in this Questioner
	 * @exception AssertionError
	 *                wrong type for this question
	 */
	public int getSelectedIndex(String question) {
		assert questions.containsKey(question) : "question should be present in this Questioner";
		assert (questions.get(question) instanceof ChoiceField) : "wrong type for this question";

		return ((ChoiceField) questions.get(question)).getSelectedIndex();
	}

	// ===== ENUM question (one day in the distant feature) =====

	// public void addEnum(String question, Enum<?>[] choices) {
	// assert !isVisible() : "Questioner may not be visible";
	// assert question != null : "question String can not be null";
	// assert !question.isEmpty() : "question String can not be empty";
	// assert !questions.containsKey(question) : "question already present";
	// assert choices.length > 0 : "choices should have at least one element";
	// JLabel label = new JLabel(question);
	// labelPanel.add(label);
	// ChoiceField choiceField = new ChoiceField(choices);
	// questions.put(question, choiceField);
	// answerPanel.add(choiceField);
	// }

	// public Enum<?> getEnum(String question) {
	// assert questions.containsKey(question) :
	// "question should be present in this Questioner";
	// assert (questions.get(question) instanceof ChoiceField) :
	// "wrong type for this question";
	// String x = ((ChoiceField) questions.get(question)).getItem();
	// if (x == null) return null;
	// return null;
	// }

	// ---------------------------

	/**
	 * @return true when the Questioner was cancelled
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * @return true when the OKE button was clicked
	 */
	public boolean isOke() {
		return !cancelled;
	}

	// ---------------------------

	/**
	 * Handles the buttons, should not be invoked from outside this class!
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source == ok) { // ok button
			cancelled = false;
			// dispose(); // not used because of slow response
			setVisible(false);
		} else if (source == cancel) { // cancel button
			cancelled = true;
			// dispose(); // not used because of slow response
			setVisible(false);
		}
	}

	/**
	 * Makes the Questioner visible at it's preferred size
	 * 
	 * @param show
	 *            the desired state
	 */
	public void setVisible(boolean show) {
		if (show) {
			pack();
			setResizable(false);
		}
		super.setVisible(show);
	}

	// /**
	// * @deprecated Makes the Questioner visible at it's preferred size
	// */
	// public void show() { // AKK: deprecated
	// pack();
	// //setCenterLocation();
	// setResizable(false);
	// super.show(); // AKK: deprecated
	// }
	//
	// /*
	// * private methods
	// */
	//
	// private void setCenterLocation() {
	// Toolkit toolkit = Toolkit.getDefaultToolkit();
	// Dimension screen = toolkit.getScreenSize();
	// Dimension component = getSize();
	// setLocation((screen.width - component.width) / 2, (screen.height -
	// component.height) / 2);
	// }

}
