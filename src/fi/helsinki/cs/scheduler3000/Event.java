package fi.helsinki.cs.scheduler3000;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Team TA's
 */


public class Event implements Serializable{

	// FIXME
	public static final String[] VALID_START_TIMES = generateValidTimes();
	public static final String[] VALID_END_TIMES = generateValidTimes();
	private String startTime;
	private String endTime;
	private String location;
	private String title;
	
	public Event(String startTime, String endTime){
		this.setStartTime(startTime);
		this.setEndTime(endTime);
	}
	
	public Event(String startTime, String endTime, String title){
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setTitle(title);
	}
	
	public Event(String startTime, String endTime, String title, String location){
		this.setStartTime(startTime);
		this.setEndTime(endTime);
		this.setTitle(title);
		this.setLocation(location);
	}
	
	public String getLocation() {
		return location;
	} 

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		if (checkIfValid(startTime, VALID_START_TIMES)){		
			this.startTime = startTime;
		}
		else {
			throw new IllegalArgumentException("Start time must be one of the following: " + getAllValidValues(VALID_START_TIMES));
		}
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		if (checkIfValid(endTime, VALID_END_TIMES)){
			this.endTime = endTime;
		}
		else {
			throw new IllegalArgumentException("End time must be one of the following: "+ getAllValidValues(VALID_END_TIMES));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// PRIVATES
	
	private String getAllValidValues(String[] listOfValids){
		String valids = "";
		for (String v : listOfValids){
			valids += "\""+v+"\", ";
		}
		return valids;
	}
		
	/**
	 * @param value 
	 * @param allValids - Array of Strings that contains all valid Strings
	 * @return true if value is valid, ie. 
	 * @return false if startTime didn't match a valid start time.
	 */
	private boolean checkIfValid(String value, String[] allValids){
		for (String valid : allValids){
			if (value.equalsIgnoreCase(valid)){
				return true;
			}
		}
		return false;
	}

        private static String[] generateValidTimes(){
            ArrayList<String> times = new ArrayList<String>();
            for (int i = 8; i < 21; i++){
                String hour = ""+i;
                String halfHour = i+":30";
                if(i < 10){
                    hour = "0"+hour;
                    halfHour = "0"+halfHour;
                }
                times.add(hour);
                times.add(halfHour);
            }
            return times.toArray(new String [1]);
        }
}
