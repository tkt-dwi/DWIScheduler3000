package tests;

/**
 * @author Team TA's
 */

import junit.framework.TestCase;

import org.junit.* ;

import fi.helsinki.cs.scheduler3000.*;

public class EventTest extends TestCase{
	
	Event event;
	
	@Before
	public void setUp() {
		event = new Event("08","10","title", "testlaboratory");
	}
	
	@After
	public void tearDown() {
		event = null;
	}

	@Test
	public void test_constructor() {
		
	    Event E = new Event("10","12") ;
	    assertTrue(E.getStartTime() != null);
	    assertTrue(E.getEndTime() != null ) ;
	}

	@Test
	public void test_constructor2() {
	    Event E2 = new Event("10","12", "title") ;
	    assertTrue(E2.getStartTime() != null);
	    assertTrue(E2.getEndTime() != null ) ;
	    assertTrue(E2.getTitle() != null);
	}
	
	@Test
	public void test_constructor3() {
	    Event E3 = new Event("12","14", "Ohtu", "Auditory A111") ;
	    assertTrue(E3.getStartTime() != null);
	    assertTrue(E3.getEndTime() != null ) ;
	    assertTrue(E3.getTitle() != null);
	    assertTrue(E3.getLocation() != null);
	}
	
	@Test
	public void test_setStartTime(){
		event.setStartTime("10");
		assertEquals("10", event.getStartTime());
		try{
			event.setStartTime("09");	
			fail("Expecting IllegalArgumentException");
		}catch(IllegalArgumentException r){
			// Everything went better than expected
		}
	}
	
	@Test
	public void test_setEndTime(){
		event.setEndTime("12");
		assertEquals("12", event.getEndTime());
		try{
			event.setEndTime("25");
			fail("Expecting IllegalArgumentException");
		}catch(IllegalArgumentException r){
			// Everything went better than expected
		}
		
		
	}
	 
}
