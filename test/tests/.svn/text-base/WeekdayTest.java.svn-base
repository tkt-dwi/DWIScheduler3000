package tests;

/**
 * @author Team TA's
 */


import junit.framework.TestCase;
import org.junit.*;

import fi.helsinki.cs.scheduler3000.Weekday;

public class WeekdayTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testIntToEnumMap(){
		// is initiated
		assertNotNull(Weekday.intToEnumMap);
		
		// has the same number of values
		assertEquals(Weekday.Day.values().length, Weekday.intToEnumMap.size());
		
		// all values match
		int counter = 0;
		for (Weekday.Day mapVal : Weekday.intToEnumMap.values()){
			for (Weekday.Day realVal : Weekday.Day.values()){
				if (mapVal.compareTo(realVal) == 0){
					counter++;
				}
			}
		}	
		assertEquals(Weekday.Day.values().length, counter);
	}
	
	@Test
	public void testEnumToIntMap(){
		assertNotNull(Weekday.enumToIntMap);
		
		assertEquals(Weekday.Day.values().length, Weekday.enumToIntMap.size());
		
		int counter = 0;
		for (Weekday.Day mapVal : Weekday.enumToIntMap.keySet()){
			for (Weekday.Day realVal : Weekday.Day.values()){
				if (mapVal.compareTo(realVal) == 0){
					counter++;
				}
			}
		}
		assertEquals(Weekday.Day.values().length, counter);
	}
	
	@Test
	public void testLongNameMap(){
		assertNotNull(Weekday.longNameMap);
		
		assertEquals(Weekday.Day.values().length, Weekday.longNameMap.size());
	}

}
