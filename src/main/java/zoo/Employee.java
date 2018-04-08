package zoo;

//import java.util.Vector;	// No longer needed
// for saveFile at the end
import java.io.PrintWriter;


/**
 * The class representing various kinds of employees.
 * 
 * @author R.Akkersdijk
 */
public abstract class Employee // superclass, no instances wanted, therefor abstract
{
	// Utility function
	/** Test that a number can be a valid employee number (i.e. 3 digits) */
	public static final boolean isValidEmployeeNumber(int a_number) {
		return (100 <= a_number) && (a_number <= 999);
	}
	
	// ---------------------------------------------------------
	// Notes:
	// 1.
	// When some value should be between two boundaries, by preference say:
	//		min <= x && x <= max
	// and not
	//		x >= min && x <= max
	// This first version depicts that 'x' is *between* boundaries.
	// The second version can be confusing.
	// Also, by preference use the actual boundary values, and not:
	//		tolow < x && x < tobig
	//
	// 2.
	// Focus on what is VALID, not on what is wrong!
	// So don't use:
	//		! (x < min || max < x)
	// nor:
	//		! (x < min || x > max)
	// ---------------------------------------------------------
	
	// ===========================

	// Employee function codes	// No longer needed
//	@Deprecated
//	public static final int MANAGER = 1; // department manager
//	public static final int ADMINISTRATOR = 2; // administrator
//	public static final int ZOOKEEPER = 4; // animal keeper

	// ===========================

	// Common data for all types of employees
//	@Deprecated
//	protected final int the_function; 	// m.a.w. ADMINISTRATOR, ZOOKEEPER, MANAGER	// No longer needed
	protected final int the_number; 	// unique employee number (3 digits)
	protected final String the_name;	// his/her name
	protected int the_age; 				// his/her age
	protected Manager the_boss; 		// for whom he/she works		// type changed!

	// Only for zoo keepers
//	@Deprecated
//	private Cage the_cage;			// takes care of the animals in this cage	// No longer needed

	// Only for managers
//	@Deprecated
//	private Vector<Employee> the_employees;	// all people being managed (include himself)	// No longer needed


	/**
	 * Employee constructor
//	 * @param function The employee function code
	 * @param number The unique employee number (3 digits)
	 * @param name His/Her name
	 * @param age His/Her age (between 0 and 100 inclusive)
	 */
	protected Employee( /*int function,*/ int number, String name, int age) {
		// WARNING: The constructor has now become 'protected', not 'public' !
		
//		assert (function == MANAGER) || (function == ADMINISTRATOR) || (function == ZOOKEEPER)
//								: "wrong employee function";								// M
		// Because the values for 'function' are "codes" rather then ordinary numbers,
		// always use the NAMES of those "magic values".
		// Because codes are arbitrary, treat them as separate values, not as ranges.
		// Don't use: A <= x <= D, but: x is A OR x is B OR x is C OR x is D.
		
		assert isValidEmployeeNumber(number) : "bad employee number";
		
		assert name != null : "null name";
		assert !name.isEmpty() : "empty name";
		
		assert 0 <= age : "to young";
		assert age <= 100 : "to old";
		
//		the_function = function; // should be: MANAGER, ADMINISTRATOR or ZOOKEEPER	// No longer needed
		the_number = number;
		the_name = name;
		the_age = age;
		the_boss = null;
//		the_cage = null;	// No longer needed
//		the_employees = null;	// No longer needed
//		if (the_type == MANAGER) {	// No longer needed
//			the_boss = this; // A manager manages himself !
//			the_employees = new Vector<Employee>();
//			the_employees.add(this); // add self to list
//		}
	}

	public int getNumber() {
		return the_number;
	}

	public boolean isEmployee(int a_number) {
		assert isValidEmployeeNumber(a_number) : "bad employee number";			// S
		return the_number == a_number;
	}

//	@Deprecated
//	public int getFunction() {	// No longer needed
//		return the_type;
//	}

	// for Managers only		// No longer needed
//	@Deprecated
//	public Vector<Employee> getEmployees() {
//		return the_employees;
//	}

	// for Keepers only	// No longer needed
//	@Deprecated
//	public void setCage(Cage cage) {
//		the_cage = cage;
//	}

	public void setManager(Manager boss) {
		assert boss != null : "null boss";
		assert the_boss == null : "already has a boss";
		the_boss = boss;
	}
	
	public void isLeaving() {			// ADDED, tell boss you are leaving
		the_boss.forgetEmployee(this);
	}
	
	public abstract float getSalary();

//	@Deprecated
//	public float getSalary() {
//		float salary = 0;
//		switch (the_type)	// No longer needed
//		{
//		case ADMINISTRATOR:
//			return 1000 + the_age * 50;
//		case MANAGER:
//			return 5000 + the_employees.size() * 500; // number of employees
//		case ZOOKEEPER:
//			return 2000 + the_cage.size() * 100; // number of animals
//		}
//		return salary;
//	}

	// Because 'toString' is often used automatically
	// it is a bad idea to make it 'abstract'
	public String toString() {
		return String.format("%10s %3d", "employee", the_number);
	}

	// Let this version of print handle all the common employee attributes
	void print() {
		System.out.print(this + "\t" + the_name + "\t" + the_age + "\t" + getSalary() + " euro");
		if (the_boss != null)
			System.out.print("\tworking for " + the_boss.getNumber());
		else
			System.out.print("\thas gone away");
//		if (the_type == MANAGER)		// No longer needed
//			System.out.print("\thas " + the_employees.size() + " employees");
//		if (the_type == ZOOKEEPER)		// No longer needed
//			System.out.print("\ttaking care of " + the_cage.getKind());
	}

	// =====================================
	public abstract void saveFile(PrintWriter out);
	
//	public void saveFile(PrintWriter out) {
//		assert out != null : "null printwriter";
//		switch (the_type)	// No longer needed
//		{
//		case ADMINISTRATOR:
//			out.println("2\t2\t" // action 2=employee, function 2=administrator
//					+ the_number + "\t" + the_name + "\t" + the_age + "\t" + the_boss.the_number);
//			break;
//		case MANAGER:
//			out.println("2\t1\t" // action 2=employee, function 1=manager
//					+ the_number + "\t" + the_name + "\t" + the_age);
//			break;
//		case KEEPER:
//			out.println("2\t4\t" // action 2=employee, function 4=keeper
//					+ the_number + "\t" + the_name + "\t" + the_age + "\t" + the_cage.getKind() + "\t" + the_boss.the_number);
//			break;
//		}
//	}

}
