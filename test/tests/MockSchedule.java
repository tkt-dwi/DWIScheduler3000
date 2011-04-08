package tests;

/**
 * @author Team TA's
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fi.helsinki.cs.scheduler3000.*;
import fi.helsinki.cs.scheduler3000.Weekday.Day;

public class MockSchedule extends Schedule {

	// CONSTRUCTORS
	String period;
	HashMap<Weekday.Day, ArrayList<Event>> schedule;

	public MockSchedule(ArrayList<Weekday.Day> week){
		this.schedule = new HashMap<Day, ArrayList<Event>>();
		for (Weekday.Day d : week){
			this.schedule.put(d, new ArrayList<Event>());
		}
    }
    
    // OTHER
	
	@Override
	public void addEvent(Day eventDay, Event event) {
		schedule.get(eventDay).add(event);
	}

	@Override
	public String getPeriod() {
		return this.period;
	}

	@Override
	public HashMap<Day, ArrayList<Event>> getSchedule() {
		return this.schedule;
	}

	@Override
	public void setPeriod(String period) {
		this.period = period;
	}

	@Override
	public void setSchedule(ArrayList<Day> newSchedule) {
		this.schedule = new HashMap<Day, ArrayList<Event>>();
		for (Day d : newSchedule){
			this.schedule.put(d, new ArrayList<Event>());
		}
	}

	@Override
	public void setSchedule(HashMap<Day, ArrayList<Event>> newSchedule) {
		this.schedule = schedule;
	}

	@Override
	public void setSchedule(Schedule newSchedule) {
		this.schedule = newSchedule.getSchedule();
	}

}
