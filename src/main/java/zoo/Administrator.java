package zoo;

import java.io.PrintWriter;


/**
 * The Administrator class.
 * 
 * @author R.Akkersdijk
 */
public class Administrator extends Employee
{

	public Administrator(int a_number, String a_name, int an_age) {
		super(a_number, a_name, an_age);
		// The superclass will take care of the employee assertions
	}

	@Override
	public float getSalary() {
		return 1000 + the_age * 50;
	}

	@Override
	public String toString() {
		return String.format("%10s %3d", "administrator", the_number);
	}
	
	// NB: Because Administrator has no other special features
	//		we can simply "borrow" other Employee methods.

	// =====================================
	@Override
	public void saveFile(PrintWriter out) {
		assert out != null : "null printwriter";
		out.println("2\t2\t" // action 2=employee, type 2=administrator
				+ the_number + "\t" + the_name + "\t" + the_age
				+ "\t" + the_boss.the_number);
	}
}
