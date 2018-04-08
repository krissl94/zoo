import org.junit.Test;
import zoo.Animal;
import zoo.Cage;

import static org.junit.Assert.*;

/**
 * Test for class Cage from the Zoo.
 * @author akk
 *
 */
public class CageTest
{

	/**
	 * Test method for {@link zoo.Cage#Cage(String)}.
	 * Test method for {@link zoo.Cage#getKind()}.
	 * Test method for {@link zoo.Cage#toString()}.
	 */
	@Test
	public final void testCage() {
		Cage cage = new Cage("monkey");
		// We have a cage, fine, and now first we check
		assertEquals("monkey", cage.getKind());
		assertEquals("Cage monkey", cage.toString());
		// other methods will be done later
	}

	// bad weather testcases for {@link Cage#Cage(java.lang.String)}
	@Test(expected = AssertionError.class)
	public void testCageNull() {
		new Cage(null); // null animal type
	}
	@Test(expected = AssertionError.class)
	public void testCageEmpty() {
		new Cage(""); // empty animal type
	}

	/**
	 * Test method for {@link zoo.Cage#hasAnimals()}.
	 * Test method for {@link zoo.Cage#size()}.
	 * Test method for {@link zoo.Cage#addAnimal(zoo.Animal)}.
	 * Test method for {@link zoo.Cage#hasAnimal(String)}.
	 * Test method for {@link zoo.Cage#getAnimal(String)}.
	 * Test method for {@link zoo.Cage#removeAnimal(String)}.
	 */
	@Test
	public final void testDoAnimals() {
		Cage cage = new Cage("monkey");
		assertFalse(cage.hasAnimals()); // still empty
		assertEquals(0, cage.size()); // should be 0

		Animal animal = new Animal("monkey", "toto", 0);

		cage.addAnimal(animal); // should be no problem
		assertTrue(cage.hasAnimals()); // should contain animals
		assertEquals(1, cage.size()); // exactly 1

		assertTrue(cage.hasAnimal("toto")); // should exist
		assertFalse(cage.hasAnimal("bokito")); // but not this one

		assertSame(animal, cage.getAnimal("toto"));	// identical?

		cage.removeAnimal("toto"); // should be no problem
		assertFalse(cage.hasAnimals()); // empty again?
		assertEquals(0, cage.size()); // zero again?

		assertFalse(cage.hasAnimal("toto")); // no longer there?
	}

	/**
	 * Test method for {@link zoo.Cage#addAnimal(zoo.Animal)}.
	 */
	@Test(expected=AssertionError.class)
	public final void testAddAnimalNull() {
		Cage cage = new Cage("monkey");
		cage.addAnimal(null);		// illegal
	}

	/**
	 * Test method for {@link zoo.Cage#hasAnimal(String)}.
	 */
	@Test(expected=AssertionError.class)
	public final void testHasAnimalNull() {
		Cage cage = new Cage("monkey");
		cage.hasAnimal(null);			// null name
	}
	@Test(expected=AssertionError.class)
	public final void testHasAnimalEmpty() {
		Cage cage = new Cage("monkey");
		cage.hasAnimal("");				// empty name
	}

	/**
	 * Test method for {@link zoo.Cage#getAnimal(String)}.
	 */
	@Test(expected=AssertionError.class)
	public final void testGetAnimalNull() {
		Cage cage = new Cage("monkey");
		cage.getAnimal(null);			// null name
	}
	@Test(expected=AssertionError.class)
	public final void testGetAnimalEmpty() {
		Cage cage = new Cage("monkey");
		cage.getAnimal("");			// empty name
	}

	/**
	 * Test method for {@link zoo.Cage#removeAnimal(String)}.
	 */
	@Test(expected=AssertionError.class)
	public final void testRemoveAnimalNull() {
		Cage cage = new Cage("monkey");
		cage.removeAnimal(null);			// null name
	}
	@Test(expected=AssertionError.class)
	public final void testRemoveAnimalEmpty() {
		Cage cage = new Cage("monkey");
		cage.removeAnimal("");			// empty name
	}
	
	// ==========================================
	// Code to check the 'asserts-enabled' status
	static {
		boolean ea = false;
		assert ea = true; // mis-using a side-effect !
		if (!ea)
			System.err.println("** WARNING: ASSERTS ARE NOT ENABLED **");
	}
}
