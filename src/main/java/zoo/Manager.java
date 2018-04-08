package zoo;

import java.io.PrintWriter;
import java.util.Vector;


/**
 * The Manager class.
 * 
 * @author R.Akkersdijk
 */
public class Manager extends Employee
{

	// Voor managers
	private final Vector<Employee> the_employees; // all employees (including self)

	public Manager(int a_number, String a_name, int an_age) {
		super(a_number, a_name, an_age);
		// The superclass will take care of the employee assertions
		the_boss = this;			// Manages himself
		the_employees = new Vector<Employee>();
		the_employees.add(this);	// Managed by himself
	}

	// This method is no longer wanted
	@Deprecated
	public Vector<Employee> getEmployees() {
		return the_employees;
	}

	public void addEmployee(Employee employee) {
		assert employee != null : "null employee";
		assert !the_employees.contains(employee) : "employee already in list";
		employee.setManager(this); // inform employee ...
		the_employees.add(employee); // ... and register here
	}
	public void forgetEmployee(Employee employee) {
		assert employee != null : "null employee";
		// When this manager leaves ...
		if (employee == this) // .. he should be the last one
			assert the_employees.size() == 1 : "There are still some other employees";
		the_employees.remove(employee);
	}

	@Override
	public float getSalary() {
		return 5000 + the_employees.size() * 500; // number of employees
	}

	@Override
	public String toString() {
		return String.format("%10s %3d", "manager", the_number);
	}

	@Override
	void print() {
		super.print(); // handles all employee aspects
		System.out.print("\thas " + the_employees.size() + " employees");
	}

	// =====================================
	@Override
	public void saveFile(PrintWriter out) {
		assert out != null : "null printwriter";
		out.println("2\t1\t" // actie 2=employee, type 1=manager
				+ the_number + "\t" + the_name + "\t" + the_age);
	}

}
