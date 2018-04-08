package zoo;

import java.io.PrintWriter;


/**
 * The Zoo Keeper class.
 * 
 * @author R.Akkersdijk
 */
public class Keeper extends Employee
{

	// For: Keepers only
	private Cage the_cage; // takes care of the animals in this cage

	public Keeper(int a_number, String a_name, int an_age, Cage a_cage) {
		super(a_number, a_name, an_age);
		// The superclass will take care of the employee assertions
		assert a_cage != null : "null kooi";
		the_cage = a_cage; // the cage taken care of
	}

	// This method is no longer needed
	@Deprecated
	public void setCage(Cage cage) {
		assert cage != null : "null cage";
		the_cage = cage;
	}

	@Override
	public float getSalary() {
		return 2000 + the_cage.size() * 100; // number of animals
	}

	@Override
	public String toString() {
		return String.format("%10s %3d", "keeper", the_number);
	}

	@Override
	void print() {
		super.print(); // handles employee aspects
		System.out.print("\tcares for " + the_cage.getKind());
	}

	// =====================================
	@Override
	public void saveFile(PrintWriter out) {
		assert out != null : "null printwriter";
		out.println("2\t3\t" // action 2=employee, type 3=keeper
				+ the_number + "\t" + the_name + "\t" + the_age
				+ "\t" + the_cage.getKind() + "\t" + the_boss.the_number);
	}

}
