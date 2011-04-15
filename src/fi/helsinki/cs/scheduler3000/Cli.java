package fi.helsinki.cs.scheduler3000;

/**
 * @author Team TA's
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.awt.*;
import java.net.*;
import javax.swing.*;



import fi.helsinki.cs.scheduler3000.Weekday.Day;
import java.util.Collections;

public class Cli implements Runnable{

    private static Scanner input = new Scanner(System.in);
    private static Schedule schedule = null;
    private static final String endCommand = "/q";
    private static ObjectOutputStream objectOutput;
    private static ObjectInputStream objectInput;

    public static void main(String[] args) throws MalformedURLException {

        //new GIF
        URL url = new URL("http://www.cs.helsinki.fi/u/koheikki/nymanfaster.gif");
        Icon icon = new ImageIcon(url);
        JLabel label = new JLabel(icon);
 
        JFrame f = new JFrame("Processing...");
        f.getContentPane().add(label);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);


        System.out.println("\033[2J\n");

        System.out.println("***************************************");
        System.out.println("****    ****  ********  ***  **********");
        System.out.println("**** **  ***  ********  ***  **********");
        System.out.println("**** *** ****  **  **  ****  **********");
        System.out.println("**** *** ****  *    *  ****  **** *****");
        System.out.println("**** **  *****   **   *****  ***   ****");
        System.out.println("****    ******* **** ******  **** *****");
        System.out.println("***************************************");
        System.out.println();
        System.out.println("PUUKKO PUOLUKKAPUUROSSA 3001");
        System.out.println("(more friendlier known as PUPU 3001)");
        System.out.println();


        Character foo;

        do {
            System.out.println();
            printCommands();
            printPrompt();
            foo = sanitize(input.nextLine());

            switch (foo) {

                case 'p':
                    if (schedule == null) { // cannot do this if schedule is not existing
                        break;
                    }
                    printReportDialogToScreenDialog();
                    break;

                case 'a':
                    if (schedule == null) { // cannot do this if schedule is not existing
                        break;
                    }
                    newEventDialog();
                    break;

                case 's':
                    if (schedule == null) { // cannot do this if schedule is not existing
                        break;
                    }
                    saveScheduleDialog();
                    break;

                case 'f':
                    if (schedule == null) { // cannot do this if schedule is not existing
                        break;
                    }
                    printReportToFileDialog();
                    break;

                case 'n':
                    newScheduleDialog();
                    break;

                case 'o':
                    openScheduleDialog();
                    break;

                case 'q':
                    System.exit(0);
                    break;
                    
                case 'c':
                	new CSV().writeCSV(schedule);
                	break;

                default:
                    System.out.println("Don't know what that command is");
                    break;

            }
        } while (true);

    }

    

    private static boolean checkDate(String in) {
        Integer day = null;
        // try-catch makes sure that input is numeric
        try {

            day = Integer.parseInt(in);
            // check if day is indeed a valid number
            if (day > 0 && day < 8) {
                return true; // day ok, exit!
            }

            System.out.println("Sorry, but \"" + day + "\" is not a valid number for date");


        } catch (NumberFormatException e) {
            System.out.println("Sorry, cannot parse \"" + in + "\"");
        }

        return false;

    }

    private static boolean checkDate(String in, Schedule schedule) {
        // check if the date is valid at all
        if (!checkDate(in)) {
            return false;
        }
        // check if specified date is in the schedule
        return schedule.getSchedule().containsKey(Weekday.intToEnumMap.get(Integer.parseInt(in)));
    }

    private static HashMap<String, Object> getOptions(String key, Day value) {
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put(key, value);
        return ret;
    }

    private static HashMap<String, Object> getOptions(String key, ArrayList<Day> value) {
        HashMap<String, Object> ret = new HashMap<String, Object>();
        ret.put(key, value);
        return ret;
    }

    private static void newEventDialog() {
        String startTime = null, endTime = null, location = null, title = null, eventDayTemp;
        Event event = null;
        Day eventDay = null;

        do {
            try {
                event = new Event("08", "12", title, location);
                System.out.println("");

                System.out.println("Which day is the event? (Press \"/q\" to exit)");
                printDates();
                printPrompt();
                eventDayTemp = input.nextLine();
                if (eventDayTemp.equals(endCommand)) {
                    return;
                }
                if(!checkDate(eventDayTemp)){
                    continue;
                }
                

                System.out.println("What is the start time?");
                printPrompt();
                startTime = input.nextLine();
                event.setStartTime(startTime);

                System.out.println("What is the end time?");
                printPrompt();
                endTime = input.nextLine();
                event.setEndTime(endTime);

                System.out.println("What this event should be named as?");
                System.out.println("(just press enter to skip this)");
                printPrompt();
                title = input.nextLine();
                event.setTitle(title);

                System.out.println("Where this event is held?");
                System.out.println("(just press enter to skip this)");
                printPrompt();
                location = input.nextLine();
                event.setLocation(location);


                eventDay = Weekday.intToEnumMap.get(Integer.parseInt(eventDayTemp));
                //event = new Event(startTime, endTime, title, location);
                break; // success, get out of the do-while

            } catch (IllegalArgumentException e) {

                System.out.println("Sorry, but some mistakes were made:");
                System.out.println(e.getMessage());

            }
        } while (true);

        System.out.print("Adding event to schedule...");

        try {
            schedule.addEvent(eventDay, event);
        } catch (IllegalArgumentException e) {
            System.out.println("Something went wrong:");
            System.out.println(e.getMessage());
            System.out.println("Sorry, but once more");
            newEventDialog();
            return; // this is for when newEventDialog finally succeedes, we don't print out the last ok!'s
        }

        System.out.println("ok!");

    }

    private static void newScheduleDialog() {
        String in = null;
        HashSet<Integer> dates = new HashSet<Integer>();

        System.out.println("Enter the period this schedule is for:");
        printPrompt();
        String period = input.nextLine();

        /*System.out.println("Give dates you want to include in the scedule");
        System.out.println("Stop giving the dates by entering \""+endCommand+"\"");
        System.out.println("One at a time, please");

        do {
        printDates();
        printSelection(dates);
        printPrompt();
        in = input.nextLine().trim();

        if (in.toLowerCase().equals(endCommand)){
        break;
        }
        else {
        if(checkDate(in.trim())){
        dates.add(Integer.parseInt(in.trim()));
        }
        }

        } while (true);*/

        System.out.print("Creating schedule...");

        ArrayList<Day> days = new ArrayList<Day>();
        for (int d = 1; d < 8; d++) {
            days.add(Weekday.intToEnumMap.get(d));
        }

        schedule = new Schedule(days, period);

        System.out.println("ok!");

    }

    private static boolean open(String filename) {

        objectInput = null; // nullify in case something is wrong and it's open

        FileInputStream fos = null;

        try {
            fos = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            System.out.println("File \"" + filename + "\" couldn't be opened");
            return false;
        }

        try {
            objectInput = new ObjectInputStream(fos);
        } catch (IOException e) {
            System.out.println("Cannot read \"" + filename + "\" from FileInputStream");
            return false;
        }

        try {
            Schedule newSchedcule = (Schedule) objectInput.readObject();
            if (schedule != null) {
                schedule.setSchedule(newSchedcule); // have to cast the object
            } else {
                schedule = newSchedcule;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Cannot read \"" + filename + "\" from ObjectInputStream");
        } catch (ClassNotFoundException e) {
            System.out.println("Cannot find class for the object when reading \"" + filename + "\"");
        }

        return false;
    }

    private static void openScheduleDialog() {
        System.out.println("Give name of the file to be opened");
        printPrompt();
        String filename = input.nextLine().trim();
        while (true) {

            if (!filename.endsWith(".dat")) {
                filename += ".dat";
            }

            if (open(filename)) {
                break;
            } else {
                System.out.println("Please enter the name of the file again");
                System.out.println("You can exit with " + endCommand);

                filename = input.nextLine().trim();
                if (filename.equals(endCommand)) {
                    return;
                }

            }
        }


    }

    private static void printCommands() {
        System.out.println("Commands");
        System.out.println("--------");
        System.out.println("[N]ew schedule");
        System.out.println("[O]pen schedule from file");
        if (schedule != null) {
            System.out.println("[A]dd event to schedule");
            System.out.println("[S]ave schedule to file");
            System.out.println("[P]rint a report on screen");
            System.out.println("Print a report to [F]ile");
            System.out.println("Save schedule as [C]SV file");
        }
        System.out.println("[Q]uit");
    }

    private static void printDates() {
        System.out.print("Dates are: ");
        for (Day d : Day.values()) {
            System.out.print(Weekday.enumToIntMap.get(d));
            System.out.print(" - ");
            System.out.print(Weekday.longNameMap.get(d));
            System.out.print(" ");
        }
        System.out.println();
    }

    private static void printDates(Schedule schedule) {
        ArrayList<Weekday.Day> days = new ArrayList<Weekday.Day>(schedule.getSchedule().keySet());
        Collections.sort(days);
        System.out.print("Dates are: ");
        for (Day d : days) {
            System.out.print(Weekday.enumToIntMap.get(d));
            System.out.print(" - ");
            System.out.print(Weekday.longNameMap.get(d));
            System.out.print(" ");
        }
        System.out.println();
    }

    private static void printPrompt() {
        System.out.print("?>");
    }

    private static Report printReportDialog() {
        Character command = null;

        while (true) {
            System.out.print("Which type of report do you want to print? Options are: ");
            for (ReportFactory.ReportType type : ReportFactory.ReportType.values()) {
                // make types the way every other option is shown to user
                System.out.print("[" + type.toString().charAt(0) + "]" + type.toString().substring(1).toLowerCase());
                System.out.print(" ");
            }
            System.out.println("[N]one");
            printPrompt();
            command = sanitize(input.nextLine());
            String in = null;

            switch (command) {

                case 'd':

                    System.out.println("Which day you want to see your schedule for?");
                    printDates();
                    printPrompt();
                    in = input.nextLine();
                    if (!checkDate(in)) {
                        System.out.println("Unvalid date");
                        break;
                    }

                    Day day = Weekday.intToEnumMap.get(Integer.parseInt(in));
                    return ReportFactory.makeReport(ReportFactory.ReportType.DAY, schedule, getOptions("day", day));

                case 'f':
                    return ReportFactory.makeReport(ReportFactory.ReportType.FULL, schedule, null); // full report doesen't need options

                case 'w':
                    ArrayList<Day> days = new ArrayList<Day>();

                    System.out.println("Which days you want to include in this report? You can end with \"" + endCommand + "\"");
                    System.out.println("One at the time, please");

                    while (true) {
                        // print only available dates
                        printDates(schedule);
                        printPrompt();

                        in = input.nextLine();
                        if (in.equals(endCommand)) {
                            break;
                        } else if (!checkDate(in, schedule)) {
                            System.out.println("Unvalid date");
                        } else {
                            days.add(Weekday.intToEnumMap.get(Integer.parseInt(in)));
                        }
                    }

                    return ReportFactory.makeReport(ReportFactory.ReportType.WEEK, schedule, getOptions("days", days));


                case 'n':
                    System.out.println("Returning back to main menu");
                    return null;

                default:
                    System.out.println("Cannot parse " + command);
                    break;
            }

        }
    }

    private static void printReportToFileDialog() {
        Report report = printReportDialog();
        if (report != null) {
            PrintWriter out = null;
            String filename = null;

            System.out.println("Give full file name and path (if applicable)");

            while (true) {
                printPrompt();
                try {
                    filename = input.nextLine().trim();
                    out = new PrintWriter(filename);
                    break; // break out of the loop
                } catch (FileNotFoundException e) {
                    System.out.println("File " + filename + " was not found");
                }
            }

            System.out.print("Writing the file...");
            out.print(report);
            out.close();
            System.out.println("ok!");
        }
    }

    private static void printReportDialogToScreenDialog() {
        Report report = printReportDialog();
        if (report != null) {
            System.out.println(report);
        }

    }

    private static void printSelection(HashSet<Integer> dates) {
        if (dates.size() > 0) {
            System.out.print("You have selected: ");
            for (Integer d : dates) {
                System.out.print(Weekday.longNameMap.get(Weekday.intToEnumMap.get(d)));
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private static Character sanitize(String rawInput) {
        return new Character(rawInput.toLowerCase().charAt(0));
    }

    private static boolean save(String filename) {

        // nullify static variable to be sure
        objectOutput = null;

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot open \"" + filename + "\", something's wrong with it");
            return false;
        }

        try {
            //output is Cli's static variable
            objectOutput = new ObjectOutputStream(fos);
        } catch (IOException e) {
            System.out.println("Cannot write to \"" + filename + "\"");
            return false;
        }

        try {
            objectOutput.writeObject(schedule);
            objectOutput.close();
            return true;
        } catch (IOException e) {
            System.out.println("Writing to \"" + filename + "\" failed");
            return false;
        }

    }

    private static void saveScheduleDialog() {
        System.out.println("Give name of the file to open");
        System.out.println("Notice that file will be saved with .dat-extension, eg. \"myfile\" will be \"myfile.dat\" ");
        printPrompt();
        String filename = input.nextLine().trim() + ".dat";
        while (true) {
            if (save(filename)) {
                break;
            } else {
                System.out.println("Please enter the name of the file again");
                System.out.println("You can exit with " + endCommand);
                filename = input.nextLine().trim() + ".dat";

                if (filename.trim().toLowerCase().equals(endCommand)) {
                    return;
                }


            }
        }
        System.out.println("Schedule saved as \"" + filename + "\"");
    }

    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

