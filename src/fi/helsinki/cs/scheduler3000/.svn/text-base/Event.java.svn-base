package fi.helsinki.cs.scheduler3000;

/**
 * @author Team TA's
 */


public class Event {

	// FIXME
	public static final String[] VALID_START_TIMES = {"08", "10", "12", "14", "16", "18" };
	public static final String[] VALID_END_TIMES = {"10", "12", "14", "16", "18", "20" };
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
}
