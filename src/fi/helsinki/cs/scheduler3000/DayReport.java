package fi.helsinki.cs.scheduler3000;

/**
 * @author Team TA's
 */


import java.util.ArrayList;
import java.util.HashMap;

import fi.helsinki.cs.scheduler3000.Weekday.Day;

public class DayReport extends Report {

	public DayReport(Schedule schedule, HashMap<String, Object> options) {
		super(schedule, options);
	}
	
	
	@Override
	public String toString() {
		if (this.options.containsKey("day")){
			String res = "";
			Weekday.Day weekday = (Day)this.options.get("day");
			ArrayList<Event> events = this.schedule.getSchedule().get(weekday);
			
			if (events == null) {
				return null;
			}
			
			res += Weekday.longNameMap.get(weekday) + ":\n------\n";
			
			for (Event e : events){
				if(e.getTitle().equals("")){
					res += "<no title for this event>";
				}
				else{
					res += e.getTitle();	
				}
				
				//event must always have start time and end time
				res += "\nat " + e.getStartTime() + " to " + e.getEndTime(); 
				
				if (e.getLocation().equals("")){
					res += "\n<no location specified>";
				}
				else {
					res += "\nin " + e.getLocation();
				}
				res += "\n\n";
			}
			return res;
		}

		return null;
	}
	
}
