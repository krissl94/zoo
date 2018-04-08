import org.junit.Before;
import org.junit.Test;
import zoo.Animal;
import zoo.Cage;
import zoo.Keeper;

import static org.junit.Assert.*;

/**
 * The test class for Keeper from zoo.
 * @author akkersdi
 */
public class KeeperTest
{
	Cage	cage;
	
	@Before
	public void Setup() {
		cage = new Cage("monkey");
	}
	
	/**
	 * Test method for {@link Keeper#Keeper(int, String, int, Cage)}
	 * {@link Keeper#getSalary()}, {@link Keeper#toString()},
	 * {@link Keeper#getNumber()}, {@link Keeper#isEmployee(int)},
	 */
	@Test
	public void testKeeper() {
		Keeper keeper = new Keeper(100, "slaaf", 45, cage);
		// let's do it
		assertEquals(100, keeper.getNumber());
		assertTrue(keeper.isEmployee(100));
		assertFalse(keeper.isEmployee(101));
		assertEquals("    keeper 100", keeper.toString());
		assertEquals(2000, keeper.getSalary(), 0);	// basic wages
		// Keeper specific
		Animal animal = new Animal("monkey", "toto", 10);
		cage.addAnimal(animal);
		assertEquals(2100, keeper.getSalary(), 0);	// higher now
	}

	// bad weather test cases

	/**
	 * Test method for {@link Keeper#Keeper(int, String, int, Cage)}
	 */
	@Test(expected=AssertionError.class)
	public void testKeeper0a() {
		new Keeper(99, "slaaf", 40, cage); // number to low
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testKeeper0b() {
		new Keeper(1000, "slaaf", 40, cage); // number to high
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testKeeper1() {
		new Keeper(100, null, 40, cage); // null name
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testKeeper2() {
		new Keeper(100, "", 40, cage); // empty name
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testKeeper3a() {
		new Keeper(100, "slaaf", -1, cage); // to young
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testKeeper3b() {
		new Keeper(100, "slaaf", 101, cage); // to old
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testKeeper4() {
		new Keeper(100, "slaaf", 45, null); // null cage
		/*NOT REACHED*/
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
