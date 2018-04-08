import org.junit.Test;
import zoo.Administrator;

import static org.junit.Assert.*;

/**
 * Test class for Administrator from Zoo.
 * @author akkersdi
 */
public class AdministratorTest
{

	/**
	 * Test method for {@link Administrator#Administrator(int, String, int)},
	 * {@link Administrator#getSalary()}, {@link Administrator#toString()},
	 * {@link Administrator#getNumber()}, {@link Administrator#isEmployee(int)},
	 */
	@Test
	public void testAdministrator() {
		Administrator administrator = new Administrator(100, "pen", 50);
		// we have an administrator ...
		assertEquals(100, administrator.getNumber());
		assertTrue(administrator.isEmployee(100));
		assertFalse(administrator.isEmployee(999));
		assertEquals("administrator 100", administrator.toString());
		assertEquals(3500, administrator.getSalary(), 0);
	}

	// bad weather

	/**
	 * Test method for {@link Administrator#Administrator(int, String, int)}.
	 */
	@Test(expected=AssertionError.class)
	public void testAdministrator0() {
		new Administrator(99, "pen", 40); // number to low
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testAdministrator0b() {
		new Administrator(1000, "pen", 40); // number to high
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testAdministrator1() {
		new Administrator(100, null, 40); // null name
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testAdministrator2() {
		new Administrator(100, "", 40); // empty name
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testAdministrator3() {
		new Administrator(100, "pen", -1); // to young
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testAdministrator4() {
		new Administrator(100, "pen", 101); // to old
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
