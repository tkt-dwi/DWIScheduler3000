package fi.helsinki.cs.scheduler3000;

/**
 * @author Team TA's
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Report {

	// visible to subclasses
	protected Schedule schedule;
	protected HashMap<String, Object> options;
	
	public Report(Schedule schedule, HashMap<String, Object> options) {
		this.schedule = schedule;
		this.options = options;
	}
	
	// FIXME: should this be elsewhere?
	public String toString(){
		String res = "";
                ArrayList<Weekday.Day> days = new ArrayList<Weekday.Day>(schedule.getSchedule().keySet());
		Collections.sort(days);
		for (Weekday.Day day : days){
			res += day + ":\n";
			res += "----\n";
			for (Event event : schedule.getSchedule().get(day)){
				res += event.getTitle();
				res += "\nat " + event.getStartTime() + "-" + event.getEndTime();
				res += "\nin " + event.getLocation();
			}
			res += "\n\n";
		}
		
		return res;
	}
	
}
