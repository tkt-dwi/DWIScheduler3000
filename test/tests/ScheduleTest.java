package tests;

/**
 * @author Team TA's
 */

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;
import org.junit.*;

import fi.helsinki.cs.scheduler3000.*;
import fi.helsinki.cs.scheduler3000.Weekday.Day;

public class ScheduleTest extends TestCase {

	Schedule schedule;
	ArrayList<Day> week;
	ArrayList<Event> events;
	HashMap<Day, ArrayList<Event>> map;
	
	@Before
	public void setUp() throws Exception {
		week = new ArrayList<Day>();
		week.add(Day.MON);
		week.add(Day.TUE);
		week.add(Day.WED);
		week.add(Day.THU);
		week.add(Day.FRI);
		
		events = new ArrayList<Event>();
		events.add(new Event("08", "12", "first", "firstLocation"));
		events.add(new Event("18", "20", "second", "secondLocation"));
		events.add(new Event("12", "14", "third", "thirdLocation"));
		events.add(new Event("10", "16", "fourth", "fourthLocation"));
		events.add(new Event("14", "18", "fifth", "fifthLocation"));
		events.add(new Event("08", "20", "sixth", "sixthLocation"));
		
		map = new HashMap<Day, ArrayList<Event>>();
		for (Day d : week){
			map.put(d, events);
		}
		
	}

	@After
	public void tearDown() throws Exception {
		map = null;
		
		events = null;
		
		week = null;		
		
		// if test case forgots to set schedule to null...
		if (schedule != null){
			schedule = null;
		}
	}
	

	@Test
	public void testAllConstructors(){
		schedule = new Schedule(week);
		
		assertNotNull(schedule);
		
		assertEquals(week.size(), schedule.getSchedule().size());
		
		for (Day d : week){
			assertNotNull(schedule.getSchedule().get(d));
		}
		schedule = null;
		
		assertNull(schedule);
		
		String testperiod = "testperiod";
		schedule = new Schedule(week, testperiod);
		
		assertNotNull(schedule);
		
		for (Day d : week){
			assertNotNull(schedule.getSchedule().get(d));
		}
		
		assertNotNull(schedule.getPeriod());
		assertEquals(testperiod, schedule.getPeriod());
		schedule = null;
	}
	
	@Test
	public void testAllSetSchedules(){
		schedule = new Schedule(week, "first period");

		schedule.setSchedule(map);
		assertEquals(map, schedule.getSchedule());
		
		Schedule anotherSchedule = new Schedule(week);
		anotherSchedule.setSchedule(schedule);
		
	}
	
	@Test
	public void testSetPeriod(){
		String period = "first period";
		schedule = new Schedule(week, period);
	
		assertEquals(period, schedule.getPeriod());
		
		period = "totally another period";
		schedule.setPeriod(period);
		assertEquals(period, schedule.getPeriod());
	}
	
	@Test
	public void testAddEvent(){
		schedule = new Schedule(week, "period");
		Event e = new Event("16", "18", "testAddEvent", "testAddEventLocation");
		schedule.addEvent(Day.FRI, e);
		
		assertEquals(1, schedule.getSchedule().get(Day.FRI).size());
		assertEquals(e, schedule.getSchedule().get(Day.FRI).get(0));
	}
	
	@Test
	public void testAddEventWhenWrongDay(){
		schedule = new Schedule(week, "period");
		
		try {
			schedule.addEvent(Day.SUN, events.get(0));
			fail("Expecting IllegalArgumentException when no such day in schedule");
		} catch (IllegalArgumentException e) {
			// Everything went better than expected
		}
		
	}
	
}
