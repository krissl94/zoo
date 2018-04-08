// so we dont have to type the fullnames all the time

import org.junit.Test;
import zoo.Animal;

import static org.junit.Assert.*;

// Note: By making this test-class member of the zoo package
// we could also gain access to package-private things.

/**
 * A class to test class Animal from the zoo package.
 */
public class AnimalTest {

	/**
	 * good weather test cases for:
	 *
	 * {@link Animal#Animal(String, String, int)},
	 * {@link Animal#getKind()},
	 * {@link Animal#isKind(String)},
	 * {@link Animal#getName()},
	 * {@link Animal#isAnimal(String)},
	 * {@link Animal#toString()}.
	 */

	@Test
	public void testAnimal() {
		// For the age, we need to check the two boundary values 0 and 200
		Animal animal = new Animal("monkey", "toto", 0); // lowest valid age
		assertNotNull(animal);	// NOTE: this check is a bit redundant
								// because either we will have an object
								// or the constructor will have thrown
								// an exception aborting this test method.
		animal = new Animal("monkey", "toto", 200); // highest valid age
		// Now check the return value of various methods
		assertEquals("Animal monkey toto", animal.toString());
		assertEquals("monkey", animal.getKind());
		assertTrue(animal.isKind("monkey"));
		assertFalse(animal.isKind("fish"));
		assertEquals("toto", animal.getName());
		assertTrue(animal.isAnimal("toto"));
		assertFalse(animal.isAnimal("bokito"));
	}

	// Some bad weather test cases:
	// Each test will be aborted with an
	// Assertion exception because we try to do
	// something forbidden.

	// Always make sure each "bad test" only contains a single "wrongness".
	// For instance, when making new Animals, all arguments
	// should be correct, and only one may be "wrong".

	/**
	 * Test method for
	 * {@link Animal#Animal(String, String, int)}.
	 */

	@Test(expected = AssertionError.class)
	public void testAnimalNullKind() {
		new Animal(null, "toto", 1); // null kind not allowed
		/* NOTREACHED */
	}
	@Test(expected = AssertionError.class)
	public void testAnimalEmptyKind() {
		new Animal("", "toto", 1); // empty kind not allowed
		/* NOTREACHED */
	}

	@Test(expected = AssertionError.class)
	public void testAnimalNullName() {
		new Animal("monkey", null, 1); // null name
		/* NOTREACHED */
	}
	@Test(expected = AssertionError.class)
	public void testAnimalEmptyName() {
		new Animal("monkey", "", 1); // empty name
		/* NOTREACHED */
	}

	@Test(expected = AssertionError.class)
	public void testAnimalToYoung() {
		new Animal("monkey", "toto", -1); // just to young
		/* NOTREACHED */
	}
	@Test(expected = AssertionError.class)
	public void testAnimalToOld() {
		new Animal("monkey", "toto", 201); // just to old
		/* NOTREACHED */
	}

	// Some more "bad weather test cases" for other methods.
	
	/**
	 * Test methods for {@link Animal#isKind(String)}.
	 */
	@Test(expected = AssertionError.class)
	public void testIsKindNull() {
		Animal d1 = new Animal("monkey", "toto", 1);
		d1.isKind(null); // null kind
		/* NOTREACHED */
	}
	@Test(expected = AssertionError.class)
	public void testIsKindEmpty() {
		Animal d1 = new Animal("monkey", "toto", 1);
		d1.isKind(""); // empty kind
		/* NOTREACHED */
	}	
	
	/**
	 * Test method for {@link Animal#isAnimal(String)}.
	 */
	@Test(expected = AssertionError.class)
	public void testIsAnimalNull() {
		Animal d1 = new Animal("monkey", "toto", 1);
		d1.isAnimal(null); // null name
		/* NOTREACHED */
	}
	@Test(expected = AssertionError.class)
	public void testIsAnimalEmpty() {
		Animal d1 = new Animal("monkey", "toto", 1);
		d1.isAnimal(""); // empty name
		/* NOTREACHED */
	}
	
	// ==========================================
	// Code to check the 'asserts-enabled' status
	// (Otherwise all the bad-weather tests will fail)
	static {
		boolean ea = false;
		assert ea = true; // mis-using a side-effect !
		if (!ea)
			System.err.println("** WARNING: ASSERTS ARE NOT ENABLED **");
	}
}
