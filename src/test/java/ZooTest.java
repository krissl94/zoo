import org.junit.Test;
import zoo.*;

import static org.junit.Assert.*;

/**
 * Test class for Zoo from zoo.
 *
 * Warning: Still unfinished.
 * 
 * @author akk
 *
 */
public class ZooTest
{

	/**
	 * Test method for {@link zoo.Zoo#Zoo(String)}.
	 * Test method for {@link zoo.Zoo#getName()}.
	 */
	@Test
	public final void testZoo() {
		Zoo zoo = new Zoo("artis");
		assertEquals("artis", zoo.getName());
	}
	@Test(expected=AssertionError.class)
	public final void testZooNull() {
		new Zoo(null);	// null name
	}
	@Test(expected=AssertionError.class)
	public final void testZooEmpty() {
		new Zoo("");	// empty name
	}

	/**
	 * Test method for {@link zoo.Zoo#hasAnimals()}.
	 * Test method for {@link zoo.Zoo#findCage(String)}.
	 * Test method for {@link zoo.Zoo#addAnimal(zoo.Animal)}.
	 * Test method for {@link zoo.Zoo#removeAnimal(String, String)}.
	 */
	@Test
	public final void testAnimals() {
		Zoo zoo = new Zoo("artis");
		assertFalse(zoo.hasAnimals());	// no animals yet
		Cage cage = zoo.findCage("monkey");
		assertNull(cage);				// should not yet exist
		Animal animal = new Animal("monkey", "toto", 0);
		zoo.addAnimal(animal);
		assertTrue(zoo.hasAnimals());	// should have animals
		cage = zoo.findCage("monkey");
		assertNotNull(cage);			// should now exist
		assertTrue(cage.hasAnimal("toto"));	// animal in the right cage?
		zoo.removeAnimal("monkey", "toto");
		assertFalse(zoo.hasAnimals());		// no more animals left
		assertFalse(cage.hasAnimal("toto")); // should be gone too
	}

	/**
	 * Test method for {@link zoo.Zoo#findCage(String)}.
	 */
	@Test(expected=AssertionError.class)
	public final void testFindCageNull() {
		Zoo zoo = new Zoo("artis");
		zoo.findCage(null);	// null name
	}
	@Test(expected=AssertionError.class)
	public final void testFindCageEmpty() {
		Zoo zoo = new Zoo("artis");
		zoo.findCage("");	// empty name
	}
	/**
	 * Test method for {@link zoo.Zoo#makeCage(String)}.
	 */
	@Test(expected=AssertionError.class)
	public final void testMakeCageNull() {
		Zoo zoo = new Zoo("artis");
		zoo.makeCage(null);	// null name
	}
	@Test(expected=AssertionError.class)
	public final void testMakeCageEmpty() {
		Zoo zoo = new Zoo("artis");
		zoo.makeCage("");	// empty name
	}
	@Test(expected=AssertionError.class)
	public final void testMakeCageDuplicate() {
		Zoo zoo = new Zoo("artis");
		zoo.makeCage("monkey");
		zoo.makeCage("monkey");	// duplicate animal type
	}

	/**
	 * Test method for {@link zoo.Zoo#removeAnimal(String, String)}.
	 */
	@Test(expected=AssertionError.class)
	public final void testRemoveAnimal() {
		Zoo zoo = new Zoo("artis");
		zoo.removeAnimal("monkey", "toto");	// no such animal
	}

	// --------------------------------------------------

	/**
	 * Test method for
	 * {@link Zoo#addEmployee(int, Employee)}
	 * {@link Zoo#hasManagers()},
	 * {@link Zoo#assignManager(int, Employee)}
	 * {@link Zoo#removeEmployee(int)}
	 */
	@Test
	public void testWerknemers() {
		Zoo zoo = new Zoo("artis");

		assertFalse( zoo.hasEmployees() );	// no employees yet
		assertFalse( zoo.hasManagers() );	// nor managers

		Manager manager = new Manager(100, "baas", 30);
		zoo.addEmployee(100, manager);

		assertTrue( zoo.hasEmployees() );	// should have employees
		assertTrue( zoo.hasManagers() );	// and manager(s)

		Administrator boekhouder = new Administrator(101, "pen", 20);
		zoo.addEmployee(101, boekhouder);
		zoo.assignManager(100, boekhouder);

		zoo.removeEmployee(101); // the administrator
		zoo.removeEmployee(100); // the manager

		// back to initial state ?
		assertFalse( zoo.hasEmployees() );	// no more employees now
		assertFalse( zoo.hasManagers() );	// nor managers
	}

	/**
	 * Test method for {@link zoo.Zoo#addEmployee(int, zoo.Employee)}.
	 * TODO: illegal number
	 * TODO: add null employee
	 * TODO: mismatch number <> employee
	 * TODO: duplicate number
	 */
	@Test
	public final void testAddEmployee() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link zoo.Zoo#assignManager(int, zoo.Employee)}.
	 * TODO: illegal number
	 * TODO: null employee
	 * TODO: number not an employee
	 * TODO: number not a manager
	 * TODO: do it twice
	 */
	@Test
	public final void testAssignManager() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link zoo.Zoo#removeEmployee(int)}.
	 * TODO: number illegal
	 * TODO: no such employee
	 */
	@Test
	public final void testRemoveEmployee() {
		fail("Not yet implemented");
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
