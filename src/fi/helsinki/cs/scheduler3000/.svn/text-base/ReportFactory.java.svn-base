package fi.helsinki.cs.scheduler3000;

/**
 * @author Team TA's
 */


import java.util.HashMap;


public class ReportFactory {

	public static enum ReportType { DAY, WEEK, FULL }
	
	public static Report makeReport(ReportType type, Schedule schedule, HashMap<String, Object> options){
		switch (type) {
		case DAY:
			return new DayReport(schedule, options);
		case WEEK:
			return new WeekReport(schedule, options);
		case FULL:
			return new FullReport(schedule, options);
		default:
			return null;
		}
	}
	
}
