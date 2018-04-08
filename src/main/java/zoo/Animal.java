package zoo;

// for saveFile() at the end
import java.io.PrintWriter;


/**
 * Animal class.
 * 
 * @author R.Akkersdijk
 */
public class Animal
{
	// Animal data
	private final String the_kind;
	private final String the_name;
	private int the_age;

	/**
	 * Animal constructor
	 * 
	 * @param kind What kind of animal this is
	 * @param name The name of the animal
	 * @param age The age of the animal (between 0 and 200 inclusive)
	 */
	public Animal(String kind, String name, int age) {
		// First the "pre-condities" (i.e. what we expect to
		// be true when the program works correctly)
		// which say something about the parameters.

		// The kind and the name are strings, which should at least be ...
		assert kind != null		: "null kind";					// M
		assert !kind.isEmpty()	: "empty kind";					// M
		assert name != null		: "null name";					// M
		assert !name.isEmpty()	: "empty name";					// M
		// ... being more specific about "names" usually is a waste of time.

		// About the age we expect something like ...
		assert 0 <= age : "negative age";						// M
		assert age <= 200 : "unlikely age";						// M
		// ... but the upper boundary is a bit arbitrary.

		// Oke, those parameters make sense
		the_kind = kind;
		the_name = name;
		the_age = age;

		// At the end of a method we can also specify
		// post-conditions (i.e. "we now guaranty that ...")
		// but in most programs this is a bit of overkill.
		// Note: Post-conditions always say something about "this" object only.
		assert the_kind != null;				// W
		assert the_kind.equals(kind);			// W
		assert the_name != null;				// W
		assert the_name.equals(name);			// W
		assert the_age == age;					// W
	}

	// used by: Zoo.addAnimal
	public String getKind() {
		return the_kind;
	}

	// Is this animal a ... ?
	public boolean isKind(String kind) {
		// The next asserts are not about THIS object,
		// but detect errors made elsewhere.
		assert kind != null : "null animal kind question";		// S
		assert !kind.isEmpty() : "empty animal kind question";	// S
		return kind.equals(the_kind);
	}

	public String getName() {
		return the_name;
	}

	// Is this animal with name ... ?
	public boolean isAnimal(String name) {
		// See: isKind remark
		assert name != null : "null name question";		// S
		assert !name.isEmpty() : "lege name question";	// S
		return name.equals(the_name);
	}

	public void print() {
		System.out.println(the_kind + "\t" + the_name + "\t" + the_age);
	}

	public String toString() {
		return String.format("Animal %s %s", the_kind, the_name);
	}

	// =====================================
	public void saveFile(PrintWriter out) {
		assert out != null : "null PrintWriter";
		out.println("1\t" // action code 1=add animal
				+ the_kind + "\t" + the_name + "\t" + the_age);
	}

}
