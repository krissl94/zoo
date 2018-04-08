package zoo;

import java.util.TreeMap;
//import java.util.Vector;		// no longer needed
// for saveFile at the end
import java.io.PrintWriter;


/**
 * All information about a zoo.
 * 
 * @author R.Akkersdijk
 */
public class Zoo
{

	// the name of the zoo
	private final String the_name;

	// the cages containing animals ...
	// those cages will be made on demand
	// but they are kept around when they become empty
	private final TreeMap<String,Cage> the_cages;			// <kind,cage>

	// all employees
	private final TreeMap<Integer,Employee> the_employees; // <number,employee>

	public Zoo(String a_name) {
		assert a_name != null : "null name";			// M
		assert !a_name.isEmpty() : "empty name";		// M

		the_name = a_name;
		the_cages = new TreeMap<String,Cage>();
		the_employees = new TreeMap<Integer,Employee>();
	}

	public String getName() {
		return the_name;
	}

	// ---------------------
	// about animals
	// ---------------------

	// Add an animal
	public void addAnimal(Animal animal) {
		assert animal != null : "null animal";
		Cage cage = findCage(animal.getKind());	// find the cage for those animals
		if (cage == null)						// non existent yet?
			cage = makeCage(animal.getKind());	// then make a new cage
		// Second Assignment: This is should be done by the cage!
		cage.addAnimal(animal);
//		TreeMap<String,Animal> animals = cage.getAnimals(); // ask for the list of animals
//		animals.put(animal.getName(), animal);	// add new animal to the list
	} // addAnimal

	// Find the cage for some kind a animal (null=not found)
	public Cage findCage(String animalKind) {
		assert animalKind != null : "null animal kind";
		assert !animalKind.isEmpty() : "empty animal kind";
		return the_cages.get(animalKind);
	} // findCage

	// Make a cage for some kind of animal
	public Cage makeCage(String animalKind) {
		assert animalKind != null : "null animal kind";
		assert !animalKind.isEmpty() : "empty animal kind";		
		assert !the_cages.containsKey(animalKind)
				: "Cage for " + animalKind + " already exists";
		Cage cage = new Cage(animalKind); // make the cage
		the_cages.put(animalKind, cage); // register the new cage
		return cage;
	} // makeCage

	// Remove an animal
	public void removeAnimal(String animalKind, String animalName) {
		assert animalKind != null : "null animal kind";
		assert !animalKind.isEmpty() : "empty animal kind";
		assert animalName != null : "null animal name";
		assert !animalName.isEmpty() : "empty animal name";
		Cage cage = findCage(animalKind);
		assert cage != null : "no such cage";
		// Second Assignment: This is should be done by the cage!
		cage.removeAnimal(animalName);
		System.out.println(animalName + " removed");
//		// Ask for the list of animals
//		TreeMap<String,Animal> animals = cage.getAnimals();
//		// Find that animal
//		Animal animal = animals.get(animalName);
//		if (animal != null) {
//			// TODO: remove that animal from list [DONE]
//			System.out.println(animal + " removed");
//			return; // ready
//		}
//		// We should never get here, something went wrong!
//		assert false : "Animal " + animalKind + ":" + animalName + " not found";	// M
	} // removeAnimal

	// List all animals in all cages
	public void showAnimals() {
		System.out.println("List of all animals");
		if (the_cages.isEmpty())
			System.out.println("\tthere are no cages");
		else {
			// For all cages
			for (Cage cage : the_cages.values()) {
				cage.print();
				// Second Assignment: This is should be done by the cage!
				cage.printAnimals();
//				// Get list of animals
//				TreeMap<String,Animal> animals = cage.getAnimals();
//				if (animals.isEmpty())
//					System.out.println("\tis empty");
//				else { // Print the list of animals
//					System.out.println("kind\tname\tage");
//					for (Animal animal : animals.values())
//						animal.print();
//				}
			}
		}
	} // showAnimals

	// Any animals in the zoo ?
	public boolean hasAnimals() {
		if (the_cages.isEmpty())
			return false;
		for (Cage cage : the_cages.values()) {
			if (cage.hasAnimals()) return true;
//			if (cage.getAnimals().size() > 0) return true;
		}
		return false;
	} // hasAnimals

	// ---------------------
	// about employees
	// ---------------------

	// Add a new employee
	public void addEmployee(int employeeNumber, Employee employee) {
		assert Employee.isValidEmployeeNumber(employeeNumber) : "invalid number";
		assert employee != null : "null employee";
		assert employee.getNumber() == employeeNumber : "numbers do not match";
		// TODO: Is this a unique employee number? [DONE]
		assert !the_employees.containsKey(employeeNumber) : "duplicate number";
		the_employees.put(employeeNumber, employee);
	} // addEmployee

	// Assign an employee to a manager (and vice versa)
	public void assignManager(int managerNumber, Employee employee) {
		assert Employee.isValidEmployeeNumber(managerNumber) : "invalid manager number";
		assert employee != null : "null employee";	
		Employee manager = the_employees.get(managerNumber);
		assert manager != null : "no such manager";
		// Third assignment: Is that really a manager?
		assert manager instanceof Manager : "manager is not a Manager";
		((Manager)manager).addEmployee(employee);
//		employee.setManager(manager); // inform the employee ...
//		Vector<Employee> employees = manager.getEmployees();
//		employees.add(employee); // ... and his manager
	} // assignManager

	// Do we have any managers ?
	public boolean hasManagers() {
		for (Employee employee : the_employees.values()) {
			if (employee instanceof Manager)
//			if (Employee.MANAGER == employee.getType())
				return true;
		}
		return false;
	} // hasManagers

	// Do we have any employees ?
	public boolean hasEmployees() {
		return !the_employees.isEmpty();
	} // hasEmployees

	// List all managers
	public void showManagers() {
		System.out.println("List of all managers");
		for (Employee employee : the_employees.values()) {
			if (employee instanceof Manager) {
//			if (Employee.MANAGER == employee.getType()) {
				employee.print();
				System.out.println();
			}
		}
	} // showManagers

	// Remove some employee
	public void removeEmployee(int employeeNumber) {
		assert Employee.isValidEmployeeNumber(employeeNumber) : "invalid employee number";
		Employee employee = the_employees.get(employeeNumber);
		assert employee != null : "Employee " + employeeNumber + " not found";
		// TODO: employee should tell his manager he is leaving! [DONE]
		employee.isLeaving();
		the_employees.remove(employeeNumber); // and forget employee
		System.out.println(employee + " removed");
	} // removeEmployee

	// List all employees
	public void showEmployees() {
		System.out.println("List of all employees");
		if (the_employees.isEmpty())
			System.out.println("\tis empty");
		else {
			System.out.println("     employee\tname\tage\tsalary\t\tremarks");
			for (Employee employee : the_employees.values()) {
				employee.print();
				System.out.println();
			}
		}
	} // showEmployees

	// Calculate total salary costs
	public void showCosts() {
		System.out.print("Total salary costs are ");
		if (the_employees.isEmpty())
			System.out.println("nothing");
		else {
			int costs = 0;
			for (Employee employee : the_employees.values())
				costs += employee.getSalary();
			System.out.println(costs + " euro");
		}
	} // showCosts

	// ===========================================================
	// Save all data in a file
	public void saveFile(PrintWriter out) {
		assert out != null : "null PrintWriter";
		for (Cage cage : the_cages.values())
			cage.saveFile(out);
		for (Employee employee : the_employees.values())
			employee.saveFile(out);
	} // saveFile

}
