import org.junit.Test;
import zoo.Administrator;
import zoo.Employee;
import zoo.Manager;

import static org.junit.Assert.*;

/**
 * The test class for Manager from zoo
 * @author akkersdi
 */
public class ManagerTest
{

	/**
	 * Test method for {@link Manager#Manager(int, String, int)},
	 * {@link Manager#getSalary()}, {@link Manager#toString()},
	 * {@link Manager#getNumber()}, {@link Manager#isEmployee(int)},
	 */
	@Test
	public void testManager() {
		Manager manager = new Manager(100, "baas", 40);
		//
		assertEquals(100, manager.getNumber());
		assertTrue(manager.isEmployee(100));
		assertFalse(manager.isEmployee(101));
		assertEquals("   manager 100", manager.toString());
		assertEquals(5500, manager.getSalary(), 0);	// basic salaris
	}

	// bad weather test cases

	/**
	 * Test method for {@link Manager#Manager(int, String, int)}.
	 */
	@Test(expected=AssertionError.class)
	public void testManager0a() {
		new Manager(99, "baas", 40); // number to low
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testManager0b() {
		new Manager(1000, "baas", 40); // number to high
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testManager1() {
		new Manager(100, null, 40); // null name
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testManager2() {
		new Manager(100, "", 40); // lege name
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testManager3a() {
		new Manager(100, "baas", -1); // to young
		/*NOT REACHED*/
	}
	@Test(expected=AssertionError.class)
	public void testManager3b() {
		new Manager(100, "baas", 101); // to old
		/*NOT REACHED*/
	}

	
	// And now the special Manager methods

	/**
	 * Test method for {@link Manager#addEmployee(Employee)}
	 * {@link Manager#setManager(Manager)} and
	 * {@link Manager#isLeaving()}
	 */
    @Test
	public void testAddEmployee() {
		Manager manager = new Manager(100, "baas", 40);
		assertEquals(5500, manager.getSalary(), 0);	// basic salaris
		Administrator administrator = new Administrator(101, "pen", 50);
		manager.addEmployee(administrator);			// uses setManager!
		assertEquals(6000, manager.getSalary(), 0);	// salary now higher?
		administrator.isLeaving();					// should inform the manager
		assertEquals(5500, manager.getSalary(), 0);	// salary now back to basic?
	}
		
	/**
	 * Test method for {@link Manager#addEmployee(Employee)}
	 */
	@Test(expected=AssertionError.class)
	public void testAddEmployee1() {
		Manager manager = new Manager(100, "baas", 40);
		manager.addEmployee(manager);	// target already has a manager (himself)
		/*NOT REACHED*/
	}
	/**
	 * Test method for {@link Manager#addEmployee(Employee)}.
	 */
	@Test(expected=AssertionError.class)
	public void testAddEmployee2() {
		Manager manager = new Manager(100, "baas", 40);
		Administrator administrator = new Administrator(101, "pen", 50);
		manager.addEmployee(administrator);	// oke, normal
		manager.addEmployee(administrator);	// target already has a manager
		/*NOT REACHED*/
	}
	/**
	 * Test method for {@link Manager#isLeaving()}.
	 */
	@Test(expected=AssertionError.class)
	public void testIsLeaving() {
		Manager manager = new Manager(100, "baas", 40);
		Administrator administrator = new Administrator(101, "pen", 50);
		manager.addEmployee(administrator);
		manager.isLeaving(); // forbidden, still other employees around!
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
