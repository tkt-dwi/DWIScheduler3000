package fi.helsinki.cs.scheduler3000;

import java.io.*;
import java.util.*;
/**
 *
 * @author haeroe
 */
public class CSV
{
    private String[][] CSVgrid;
    private String[] keys = {"Time", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
    private int CSVrows = 26, CSVcolumns = keys.length;
    private final int START_LIMIT=8, END_LIMIT=21, HOUR_PARTS=2;
    private FileWriter filestream;
    private BufferedWriter writer;

    public void writeCSV(Schedule _sched)
    {
		if(_sched == null)
		    return;
	
		CSVgrid = new String[CSVrows][CSVcolumns];
		CSVgrid[0] = keys;
		for(int i=1;i<CSVrows;i++)
		{
			CSVgrid[i][0] = "" + (START_LIMIT + (i-1)/2) + "-" + ((i-1)%HOUR_PARTS * 60/HOUR_PARTS);
		}
	
		ArrayList<Event> eventList;
		Event event;
		HashMap<Weekday.Day, ArrayList<Event>> schedule = _sched.getSchedule();
	
		String[] startArray, endArray;
		int startH=0, startM=0, endH=0, endM=0;
		for(int i=0;i<Weekday.Day.values().length;i++)
		{
		    eventList = schedule.get(Weekday.Day.values()[i]);
		    if(eventList == null)
		    	continue;
	
		    for(int j=0;j<eventList.size();j++)
		    {
		    	event = eventList.get(j);
		    	if(event == null || event.getStartTime() == null || event.getEndTime() == null)
		    		continue;
	
		    	startArray = event.getStartTime().split(":");
		    	endArray = event.getEndTime().split(":");
		    	
		    	try
		    	{
		    		startH = Integer.parseInt(startArray[0]);
		    		if(startArray.length > 1)
		    			startM = Integer.parseInt(startArray[1]);
		    		
		    		endH = Integer.parseInt(endArray[0]);
		    		if(endArray.length > 1)
		    			endM = Integer.parseInt(endArray[1]);
		    	} catch(NumberFormatException ex)
		    	{
		    		continue;
		    	}
		    	
		    	if(startH < START_LIMIT || endH < startH || endH > END_LIMIT || (endH == END_LIMIT && endM != 0) || startM < 0 || startM > 59 || endM < 0 || endM > 59 || (startH == endH && startM > endM))
		    		continue;
		    	
		    	int startY = 2*(startH - 8) + startM/30 + 1;
		    	int endY = 2*(endH - 8) + endM/30 + 1;
		    	
		    	for(int foo=startY;foo<endY;foo++)
		    	{
		    		CSVgrid[foo][1+i] = event.getTitle();
		    	}
		    }
		}
		
		try
		{
			filestream = new FileWriter("csv.csv");
			writer = new BufferedWriter(filestream);
			
			for(int i=0;i<CSVrows;i++)
			{
				if(CSVgrid[i][0] != null)
					writer.write(CSVgrid[i][0].replace("\n", "\\n"));
				for(int j=1;j<CSVcolumns;j++)
				{
					writer.write(',');
					if(CSVgrid[i][j] != null)
						writer.write(CSVgrid[i][j].replace("\n", "\\n"));
				}
				writer.write('\n');
			}
		} catch (Exception exc)
		{
			System.err.println("Could not write CSV data");
			return;
		} finally
		{
			try
			{
				filestream.close();
			} catch (IOException iox)
			{
				filestream = null;
			}
		}
		
		System.out.println("Successfully wrote to csv.csv");
    }
}

