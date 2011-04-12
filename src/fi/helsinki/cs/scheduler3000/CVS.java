package fi.helsinki.cs.scheduler3000;

import java.io.*;
import java.util.*;
/**
 *
 * @author haeroe
 */
public class CVS
{
    private String[][] CVSgrid;
    private String[] keys = {"Time", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private int CVSrows = 54, CVScolumns = keys.length;

    public void writeCVS(Schedule _sched)
    {
	if(_sched == null)
	    return;

	CVSgrid = new String[CVSrows][CVScolumns];
	CVSgrid[0] = keys;

	ArrayList<Event> eventList;
	Event event;
	HashMap<Weekday.Day, ArrayList<Event>> schedule = _sched.getSchedule();

	for(int i=0;i<Weekday.Day.values().length;i++)
	{
	    eventList = schedule.get(i);

	    for(int j=0;j<eventList.size();j++)
	    {
		event = eventList.get(j);
	    }
	}
    }
}
