package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Provides a simple messagebox, centered on the screen
 */
@SuppressWarnings("serial")
public class Messenger extends JDialog implements ActionListener {

	private JButton ok = new JButton("Ok");
	private JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

	/**
	 * Constructs the Message-dialog
	 * 
	 * @param message
	 *            The message to be displayed
	 * 
	 * @exception AssertionError
	 *                message String can not be null
	 * @exception AssertionError
	 *                message String can not be empty
	 */
	public Messenger(String message) {
		super(new JFrame(), true);
		assert message != null : "message String can not be null";
		assert !message.isEmpty() : "message String can not be empty";
		setTitle("Messenger");
		setLayout(new BorderLayout());
		add(new JLabel(message), BorderLayout.CENTER);
		bottomPanel.add(ok);
		ok.addActionListener(this);
		add(bottomPanel, BorderLayout.SOUTH);
		setVisible(true);
	}

	/**
	 * This static method generates a dialog containing a message<br>
	 * Usage: <tt>Messenger.say("An error has ocurred");</tt>
	 * 
	 * @param message
	 *            The message to be displayed
	 * 
	 * @exception AssertionError
	 *                message String can not be null
	 * @exception AssertionError
	 *                message String can not be empty
	 */
	public static void say(String message) {
		assert message != null : "message String can not be null";
		assert !message.isEmpty() : "message String can not be empty";
		new Messenger(" " + message + " ");
	}

	/**
	 * Handles the buttons, should not be called from outside this class
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		if (source == ok) {
			// dispose(); // not used cause of slow response
			setVisible(false);
		}
	}

	/**
	 * Makes the Messenger visible.
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

	/**
	 * @deprecated Makes the Messenger visible.
	 */
	public void show() {
		pack();
		setCenterLocation();
		setResizable(false);
		super.show();
	}

	/*
	 * private methods
	 */

	private void setCenterLocation() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		Dimension component = getSize();
		setLocation((screen.width - component.width) / 2, (screen.height - component.height) / 2);
	}
}
