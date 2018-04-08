package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JButton;

/**
 * A UseCaseSet is a simple window which can be used to test various use-cases.
 * It supports UML-Use-Cases bij creating a button-frame. Each button
 * represents a user-initiated use-case.<br>
 * Closing the UseCaseFrame ends the running application by calling
 * <tt>System.exit(0)</tt><br>
 * <br>
 * Example:
 * 
 * <pre>
 * import gui.UseCaseTest; // &lt;i&gt;adjust as needed&lt;/i&gt;
 * 
 * public class ApplicationInterface implements UseCaseHandler {
 * 
 * 	Application application; // &lt;i&gt;the application to be controlled&lt;/i&gt;
 * 
 * 	// &lt;i&gt;Execution starts here&lt;/i&gt;
 * 	public ApplicationInterface(Application application) {
 * 		this.application = application;
 * 		// &lt;i&gt;Define the various use-cases&lt;/i&gt;
 * 		UseCaseSet useCaseSet = new UseCaseSet(&quot;Using UseCases&quot;, this);
 * 		useCaseSet.addUseCase(&quot;Doing one thing&quot;);
 * 		useCaseSet.addUseCase(&quot;Doing yet another thing&quot;);
 * 		useCaseSet.show();
 * 	}
 * 
 * 	// &lt;i&gt;The use-case callback method&lt;/i&gt;
 * 	public void handleUseCase(String useCaseName) {	// &lt;i&gt;must be implemented&lt;/i&gt;
 *             if (useCaseName.equals(&quot;Doing one thing&quot;) {
 *                 application.doOneThing();	// &lt;i&gt;your method to be invoked&lt;/i&gt;
 *             } else
 *             if (useCaseName.equals(&quot;Doing yet another thing&quot;) {
 *                 application.doAnotherThing();	// &lt;i&gt;another method to be invoked&lt;/i&gt;
 *             } else
 *                assert false : &quot;NOT REACHED&quot;;
 *         }
 * }
 * </pre>
 * 
 * @author e.jannink for HE-ICT
 * @version 08 febr 1999
 * 
 * @author R.Akkersdijk - revised for java 5
 * @version 09 march 2012
 * @version 09 april 2013
 */
@SuppressWarnings("serial")
public class UseCaseSet extends JFrame implements ActionListener {
	private UseCaseHandler handler;
	private Hashtable<String, JButton> usecases = new Hashtable<String, JButton>();

	/**
	 * Constructs a titled window and assigns the usecasehandler. The Frame is
	 * closeable, which normally should close the application too<br>
	 * 
	 * @param title
	 *            the frame-title
	 * @param newHandler
	 *            this object will be handling all cases, must implement the
	 *            UseCaseHandler interface.
	 * 
	 * @exception AssertionError
	 *                The title cannot be null
	 * @exception AssertionError
	 *                The title cannot be empty
	 */
	public UseCaseSet(String title, UseCaseHandler newHandler) {
		super(title);
		assert title != null : "The title cannot be null";
		assert !title.isEmpty() : "The title cannot be empty";
		handler = newHandler;
		setLocation(30, 30);
		setLayout(new GridLayout(0, 1));
		enableWindowClosing();
	}

	/**
	 * Adds a useCase button to the useCaseFrame.
	 * 
	 * @param useCaseName
	 *            the name of the added usecase, which is also used to identify
	 *            the usecase.
	 * 
	 * @exception AssertionError
	 *                A useCaseName cannot be null
	 * @exception AssertionError
	 *                A useCaseName cannot be empty
	 */
	public void addUseCase(String useCaseName) {
		assert useCaseName != null : "A useCaseName cannot be null";
		assert !useCaseName.isEmpty() : "A useCaseName cannot be empty";
		JButton button = new JButton(useCaseName);
		usecases.put(useCaseName, button);
		button.addActionListener(this);
		this.add(button);
	}

	/**
	 * Disable the given usecase
	 * 
	 * @param useCaseName
	 *            the name of the usecase to disable.
	 * 
	 * @exception AssertionError
	 *                A useCaseName cannot be null
	 * @exception AssertionError
	 *                A useCaseName cannot be empty
	 */
	public void disableUseCase(String useCaseName) {
		assert useCaseName != null : "A useCaseName cannot be null";
		assert !useCaseName.isEmpty() : "A useCaseName cannot be empty";
		JButton button = usecases.get(useCaseName);
		assert button != null : "UseCase " + useCaseName + " does not exist";
		button.setEnabled(false);
	}

	/**
	 * Enable the given usecase
	 * 
	 * @param useCaseName
	 *            the name of the usecase to disable.
	 * 
	 * @exception AssertionError
	 *                A useCaseName cannot be null
	 * @exception AssertionError
	 *                A useCaseName cannot be empty
	 */
	public void enableUseCase(String useCaseName) {
		assert useCaseName != null : "A useCaseName cannot be null";
		assert !useCaseName.isEmpty() : "A useCaseName cannot be empty";
		JButton button = usecases.get(useCaseName);
		assert button != null : "UseCase " + useCaseName + " does not exist";
		button.setEnabled(true);
	}

	/** Handles the buttons, do not invoke it yourself. */
	public void actionPerformed(ActionEvent ae) {
		assert ae != null;
		String useCaseName = ae.getActionCommand();
		handler.handleUseCase(useCaseName);
	}

	/**
	 * Makes the UseCaseSet visible at it's preferred size.<br>
	 * Should be called after adding all UseCases.
	 * 
	 * @param show
	 *            the desired state
	 */
	@Override
	public void setVisible(boolean show) {
		if (show) {
			pack();
			setResizable(true);
		}
		super.setVisible(show);
	}

	/**
	 * @deprecated Makes the UseCase set visible at it's preferred size<br>
	 *             Should be called after adding all UseCases.
	 */
	@Override
	public void show() {
		pack();
		setResizable(false);
		super.show();
	}

	private void enableWindowClosing() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

}
