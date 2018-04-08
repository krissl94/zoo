// ===============================================================
//			THERE IS NO NEED TO CHANGE THIS FILE
//		  UNLESS YOU ADD OR REMOVE EXISTING CLASSES
//		   WHICH SHOULD NOT HAPPEN BEFORE PART 3
//		             OF YOUR ASSIGNMENT
// ===============================================================
//
// Change  3.1:
// AKK 20160325 - removed redundant "validInput" code
// AKK 20160323 - added "new zoo" usecase
// AKK 20160310 - translated to english
// Change  2.1:
// AKK 20150618 - removed lots of safeguard code
// Change  1.4.4:
// AKK 20150602 - removed various use-case limitations
// Changes 1.4.3:
// AKK 20141110 - oops, was comparing some strings with ==
// Changes 1.4.2:
// AKK 20140317 - initialy disable "save zoo" use-case
// Changes 1.4.1:
// AKK 20130528 - load/save file got a dialog to select the file.
// AKK 20130516 - bug! werknemerToevoegen created a dummy employee when
//					the question dialog was cancelled.
// AKK 20130516 - removed 'werknemerCategorie' + code, because we can
//					now ask for the index of the selected item.
// AKK 20130515 - moved the 'assert check' message to the reporter window.
// AKK 20130515 - removed redundant 'dierentuin' parameter from the methods.
// AKK 20130515 - aligned the reporter window with the usecase menu.
// AKK 20130405 - removed the AssertionError try/catch code
//					(it is better to fail fast).
// AKK 20130404 - a GUI class added using the UseCaseSet package.
// Changes 1.3.4:
// AKK 20130309 - the scanner was never closed.
// AKK 20130309 - removed the ansi-escape strings.
// AKK 20120403 - AssertionError try/catch added.
// Changes 1.3.2:
// AKK 20120322 - eclipse cannot handle the ansi-escapes in the c_* strings
//					reduced them to empty strings
// AKK 20120322 - eclipse does not handle the ansi-escapes in the asserts
//					check strings
// AKK 20120322 - eclipse executes the program in the wrong directory,
//					adjusted the built-in save_file names.
// AKK 20120322 - when catching an IOException, saveFile() would always
//					complain about 'dierentuin.txt' rather than about
//					the current save_file.
// AKK 20120322 - added example asserts to the internal methods.

// For load/save zoo
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
//import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

import gui.Messenger;
import gui.Questioner;
import gui.Reporter;
import gui.UseCaseHandler;
import gui.UseCaseSet;
import zoo.Administrator;
import zoo.Animal;
import zoo.Keeper;
import zoo.Manager;
import zoo.Zoo;
import zoo.Cage;
import zoo.Employee;


/**
 * A simple graphical user-interface.
 * 
 * @author R.Akkersdijk
 */
class GUI implements UseCaseHandler
{

	// ======================================================================
	//
	// This is the GUI for the Zoo program.
	// Output sent to System.out will be diverted to a graphical window.
	// Output sent to System.err will go to your IDE or terminal.
	//
	// This program can load zoo-data from a file
	// and later store it again. This saves you some
	// typeing during the excercises.
	// - good.txt contains valid information
	//		but maybe the program will not handle it properly
	// - bad.txt contains bogus information
	//		but maybe the program will accept it anyhow
	// In case you accidentaly overwrite one of them
	// two extra copies: good-spare.txt and bad-spare.txt
	// are also provided.

	// The load/save methods at the end allow you to select
	// the version wanted.
	//
	// NOTE: Depending on your IDE and your OS settings, you
	// 	may have to add/remove a '../' prefix to the filenames.
	//
	// ======================================================================

	// The names of the files to load/save
	static final String[] files = {
			"good.txt",			"bad.txt",
			"good-spare.txt",	"bad-spare.txt"
		};


	// The Main entry point
	public static void main(String[] args) {

//		/* PLEASE IGNORE THIS CODE */
//		try {
//			String current = new java.io.File(".").getCanonicalPath();
//			System.out.println("Current dir:" + current);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Current dir using System:" + System.getProperty("user.dir"));
//		/* END IGNORED CODE */

		new GUI("Drienerlo"); // launch the gui
	}

	// The instance variables
	private Reporter reporter; // the window for System.out
	private UseCaseSet useCaseSet; // the use-cases menu
	private Zoo zoo; // the zoo being handled

	// -----------------------
	// The list of use case names, so we can compare
	// objects rather than strings later.
	static final String ucnNewZoo = "new zoo";
	static final String ucnLoadZoo = "load zoo";
	static final String ucnSaveZoo = "save zoo";
	static final String ucnAddAnimal = "add animal";
	static final String ucnRemoveAnimal = "remove animal";
	static final String ucnShowAnimals = "show animals";
	static final String ucnAddEmployee = "add employee";
	static final String ucnRemoveEmployee = "remove employee";
	static final String ucnShowEmployees = "show employees";
	static final String ucnShowCosts = "show costs";
	

	private GUI(String name) {
		// Create an output window ...
		reporter = new Reporter(name + " output", 24, 90);
		// ... next to the use-case menu
		reporter.setLocation(230, 30);

		// Send System.out to that output window
		System.setOut(new PrintStream(reporter.getOutputStream()));

		// Has java assert been enabled ?
		// Note: err goes to the IDE, out goes to the window
		if (!java_assert_enabled) {
			System.err.println("\n\t** WARNING: JAVA ASSERTS ARE NOT ENABLED **\n");
			reporter.setForeground(Color.RED.darker());
			System.out.println("\n\t** WARNING: JAVA ASSERTS ARE NOT ENABLED **\n");
		} else {
			System.err.println("OKE: java 'assert' is enabled\n");
			reporter.setForeground(Color.GREEN.darker().darker().darker());
			System.out.println("OKE: java 'assert' is enabled\n");
		}

		// Create the zoo instance
		zoo = new Zoo(name);
		System.out.println("Welcome to the " + zoo.getName() + "zoo");

		// Define the set of use cases
		useCaseSet = new UseCaseSet(name + " menu", this);
		{
			useCaseSet.addUseCase(ucnNewZoo);
			useCaseSet.addUseCase(ucnLoadZoo);

			useCaseSet.addUseCase(ucnAddAnimal);
			useCaseSet.addUseCase(ucnRemoveAnimal);
			{
				useCaseSet.disableUseCase(ucnRemoveAnimal);
			}
			useCaseSet.addUseCase(ucnShowAnimals);

			useCaseSet.addUseCase(ucnAddEmployee);
			useCaseSet.addUseCase(ucnRemoveEmployee);
			{
				useCaseSet.disableUseCase(ucnRemoveEmployee);
			}
			useCaseSet.addUseCase(ucnShowEmployees);
			useCaseSet.addUseCase(ucnShowCosts);

			useCaseSet.addUseCase(ucnSaveZoo);
			{
				useCaseSet.disableUseCase(ucnSaveZoo);
			}
		}
		// ... now show the menu
		useCaseSet.setVisible(true);
	}

	@Override
	// The use-case menu callback handler
	public void handleUseCase(String useCaseName) {
		try {
			// Just in case the user has dismissed the output window
			reporter.setVisible(true);

			if (useCaseName == ucnNewZoo)
				newZoo();
			else if (useCaseName == ucnLoadZoo)
				loadFile();
			else if (useCaseName == ucnAddAnimal)
				addAnimal();
			else if (useCaseName== ucnRemoveAnimal)
				removeAnimal();
			else if (useCaseName == ucnShowAnimals)
				zoo.showAnimals();
			else if (useCaseName == ucnAddEmployee)
				addEmployee();
			else if (useCaseName == ucnRemoveEmployee)
				removeEmployee();
			else if (useCaseName == ucnShowEmployees)
				zoo.showEmployees();
			else if (useCaseName == ucnShowCosts)
				zoo.showCosts();
			else if (useCaseName == ucnSaveZoo)
				saveFile();
			else
				// oops, forgot a usecase!
				assert false : "NOT REACHED";
		} catch (Throwable e) {
			// Select where the error message will appear (or not)
			printThrowable(e, System.err);	// ide output stream
			printThrowable(e, System.out);	// gui output window
		}
	}

	// Internal function to print a partial stacktrace on a printstream
	private void printThrowable(Throwable e, PrintStream dest) {
		dest.println("------------");
		dest.println(e+"!");
		StackTraceElement[] s = e.getStackTrace();
		for (int i = 0; (i < 3) && (i < s.length); ++i) {
			if (i > 0)	dest.print("  called from ");
			else		dest.print("detected in ");
			dest.println(s[i]);
		}
		if (s.length > 3)
			dest.println("  called from ...");
		dest.println("------------\nYour program continues ...\n------------");
	}

	// -------------------

	private void addAnimal() {
		assert zoo != null : "Cannot add an animal to a null zoo";

		Questioner question = new Questioner(ucnAddAnimal);
		question.addString("kind");
		question.addString("name");
		question.addInt("age");

		while (!question.isCancelled()) {
			question.setVisible(true);
			if (question.isOke()) {
				String animalKind = question.getString("kind");
				String animalName = question.getString("name");
				int animalAge = question.getInt("age");

				zoo.addAnimal( new Animal(animalKind, animalName, animalAge) );
				// did status changed?
				if (zoo.hasAnimals()) {
					useCaseSet.enableUseCase(ucnRemoveAnimal);
					useCaseSet.enableUseCase(ucnSaveZoo);
				}
				break;
			}
		}
	} // addAnimal

	// ----------

	private void removeAnimal() {
		assert zoo != null : "Cannot remove an animal from a null zoo";

		// sanity check (maybe this usecase should have been disabled)
		if (!zoo.hasAnimals()) {
			Messenger.say("Sorry, there are no animals to remove");
			return;
		}

		Questioner question = new Questioner(ucnRemoveAnimal);
		question.addString("kind");
		question.addString("name");

		while (!question.isCancelled()) {
			question.setVisible(true);
			if (question.isOke()) {
				String animalKind = question.getString("kind");
				String animalName = question.getString("name");
				zoo.removeAnimal(animalKind, animalName);
				// did status changed?
				if (!zoo.hasAnimals()) {
					useCaseSet.disableUseCase(ucnRemoveAnimal);
					if (!zoo.hasEmployees())
						useCaseSet.disableUseCase(ucnSaveZoo);
				}
				break;
			}
		}
	} // removeAnimal

	// ====================

	private void addEmployee() {
		assert zoo != null : "Cannot add an employee to a null zoo";

		// to collect employee data
		int employeeNumber = 0;
		int employeeType = 0;
		String employeeName = null;
		int employeeAge = 0;

		// What kind of employee ?
		String[] categories = { "--type--", "manager", "administrator", "zookeeper" };

		Questioner question = new Questioner(ucnAddEmployee);
		question.addChoice("type", categories);
		question.addInt("number");
		question.addString("name");
		question.addInt("age");

		while (!question.isCancelled()) {
			question.setVisible(true);
			if (question.isOke()) {
				employeeType = question.getSelectedIndex("type");
				employeeNumber = question.getInt("number");
				employeeName = question.getString("name");
				employeeAge = question.getInt("age");
				break;
			}
		}

		if (question.isCancelled())
			return;

		// Make the right kind of employee
		Employee employee = null;
		switch (employeeType)
		{
		case 1: // Manager
			employee = new Manager(employeeNumber, employeeName, employeeAge);
//			employee = new Employee(Employee.MANAGER, employeeNumber, employeeName, employeeAge);
			break;

		case 2: // Administrator
			employee = new Administrator(employeeNumber, employeeName, employeeAge);
//			employee = new Employee(Employee.ADMINISTRATOR, employeeNumber, employeeName, employeeAge);
			// and we also have to do ...
			assignManager(employee);
			break;

		case 3: // Zookeeper
			// need more information
			String animalKind = null;
			animalKind = Questioner.askString("what kind of animal", "");
			Cage cage = zoo.findCage(animalKind);
			if (cage == null) cage = zoo.makeCage(animalKind);
			employee = new Keeper(employeeNumber, employeeName, employeeAge, cage);
//			employee = new Employee(Employee.ZOOKEEPER, employeeNumber, employeeName, employeeAge);
//			employee.setCage(cage);
			assignManager(employee);
			break;
		}
		zoo.addEmployee(employeeNumber, employee);
		// did status changed?
		if (zoo.hasEmployees()) {
			useCaseSet.enableUseCase(ucnRemoveEmployee);
			useCaseSet.enableUseCase(ucnSaveZoo);
		}
	} // addEmployee

	// internal assist function
	private void assignManager(Employee employee) {
		assert zoo != null : "Trying to attach an employee to a manager in a null zoo";
		assert employee != null : "Cannot attach a null employee to a manager";

		zoo.showManagers();

		int managerNumber = Questioner.askInt("manager", 0);
		zoo.assignManager(managerNumber, employee);
	} // assignManager

	public void removeEmployee() {
		assert zoo != null : "Cannot remove an employee from a null zoo";

		// sanity check (maybe usecase should have been disabled)
		if (!zoo.hasEmployees()) {
			Messenger.say("Sorry, can not remove an employee when there are none");
			return;
		}

		Questioner question = new Questioner(ucnRemoveEmployee);
		question.addInt("number");

		while (!question.isCancelled()) {
			question.setVisible(true);
			if (question.isOke()) {
				int employeeNumber = question.getInt("number");
				zoo.removeEmployee(employeeNumber);
				// did status changed?
				if (!zoo.hasEmployees()) {
					useCaseSet.disableUseCase(ucnRemoveEmployee);
					if (!zoo.hasAnimals())
						useCaseSet.disableUseCase(ucnSaveZoo);
				}
				break;
			}
		}
	} // removeEmployee

	// ======================================================================
	// The code to save or load zoo data or create a new one

	private void newZoo() {
		String name = zoo.getName();
		zoo = new Zoo(name);
		useCaseSet.disableUseCase(ucnRemoveAnimal);
		useCaseSet.disableUseCase(ucnRemoveEmployee);
		useCaseSet.disableUseCase(ucnSaveZoo);
		System.out.println("-----------------\nCreated a new zoo");
	}

	// Load saved zoo-data from a file:
	// The first number tells an "action".
	// 1=add animal, 2=add employee, 0=done.
	private void loadFile() {
		assert zoo != null : "Cannot load to a null zoo";

		// Load from which file ? This time we also offer the backup verions.
		String[] options = { "--choose--", files[0], files[2], files[1], files[3] };
		String the_loadfile = Questioner.askChoice("load file ", options);
		if (the_loadfile == "--choose--")
			return;

		try {
			Scanner scanner = new Scanner(new File(the_loadfile));
			Pattern alfanum = Pattern.compile("\\w+"); // i.e "word"

			System.out.println("Loading from " + the_loadfile);

			int animals = 0, employees = 0; // to count how many we have added

			// Until we run out of lines ...
			while (scanner.hasNextLine()) {

				// Read action code
				int action = scanner.nextInt();
				switch (action)
				{
				case 0: // Ready
					scanner.close();
					System.out.println(animals + " animals and " + employees + " employees loaded\n");
					// did status changed?
					if (zoo.hasAnimals()) {
						useCaseSet.enableUseCase(ucnRemoveAnimal);
						useCaseSet.enableUseCase(ucnSaveZoo);
					}
					if (zoo.hasEmployees()) {
						useCaseSet.enableUseCase(ucnRemoveEmployee);
						useCaseSet.enableUseCase(ucnSaveZoo);
					}
					return;

				case 1: { // Add animal
					// Get animal data
					String kind = scanner.next(alfanum);
					String name = scanner.next(alfanum);
					int age = scanner.nextInt();
					zoo.addAnimal(new Animal(kind, name, age));
					++animals;
					}
					break; // add animal

				case 2: { // Add employee
					// Get employee data
					int category = scanner.nextInt();
					int number = scanner.nextInt();
					String name = scanner.next(alfanum);
					int age = scanner.nextInt();
					Employee employee = null;
					switch (category) // what type of employee?
					{
					case 1: { // Manager
						employee = new Manager(number, name, age);
//						employee = new Employee(Employee.MANAGER, number, name, age);
						}
						break;

					case 2: { // Administrator
						employee = new Administrator(number, name, age);
//						employee = new Employee(Employee.ADMINISTRATOR, number, name, age);
						// read number of his boss
						int boss = scanner.nextInt();
						zoo.assignManager(boss, employee);
						}
						break;

					case 4: { // Zoo keeper
						// read cage type
						String kind = scanner.next(alfanum);
						Cage cage = zoo.findCage(kind);
						if (cage == null) cage = zoo.makeCage(kind);
						employee = new Keeper(number, name, age, cage);
//						employee = new Employee(Employee.ZOOKEEPER, number, name, age);
//						employee.setCage(cage);
						// read number of his boss
						int boss = scanner.nextInt();
						zoo.assignManager(boss, employee);
						}
						break;

					default: // no such employee type
						System.err.println("Found bogus category " + category);
						scanner.close();
						return;

					} // end category
					zoo.addEmployee(number, employee);
					++employees;
					}
					break; // add employee

				default: // no such action code
					System.err.println("Found bogus action " + action);
					scanner.close();
					return;

				} // end actions
			} // end input
			scanner.close();
			System.err.println("Unexpected EOF!");
		} catch (java.io.FileNotFoundException fnfe) {
			System.err.println("File " + the_loadfile + " not found");
		} catch (java.util.NoSuchElementException nsee) {
			System.err.println("Found bad input");
		}
	} // loadFile

	// Save all zoo data to a file.
	private void saveFile() {
		assert zoo != null : "Cannot save a null zoo";

		// into which file ? only offer the "working" versions!
		String[] options = { "--choose--", files[0], files[1] };
		String the_savefile = Questioner.askChoice("to file ", options);
		if (the_savefile == "--choose--")
			return;

		try {
			PrintWriter out = new PrintWriter(new FileWriter(the_savefile));
			System.out.println("Saving to " + the_savefile);
			zoo.saveFile(out);
			out.println("0");
			out.close();
		} catch (java.io.IOException ioe) {
			System.err.println("Oops, can not make " + the_savefile);
		}
	} // saveFile


	// ==========================================
	// Code to check the 'asserts-enabled' status
	public static boolean java_assert_enabled = false;

	static {
		assert java_assert_enabled = true; // mis-using a side-effect here !
	}

}
