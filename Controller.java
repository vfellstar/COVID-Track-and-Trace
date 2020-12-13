import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/* Contains code from https://ncl.instructure.com/courses/24644/modules
* Original Author: Jordan Barnes
* Editing Author: Vincent Taylor */

public class Controller{
    //Constructors
    public Controller() {

    }
    public Controller(String establishmentCSVFileURI){
        File csv = new File(Controller.class.getResource(establishmentCSVFileURI).getFile());

    }



    //EVENTS CONTROLLER /////////////////////////////////////////////////////////////////////////////////
    private static ArrayList<Event> eventList = new ArrayList<>();

    public static ArrayList<Event> getEventList() {
        return eventList;
    }
    // creates a new Event - event ID is automatically created
    public static Event recordEvent(User user, int partyNumber, Establishment e){
        Event event = new Event(user, LocalDate.now(), LocalTime.now(), partyNumber, e);
        return event;
    }

    // searches for event - true if event is found, false if event does not exist
    public static boolean eventFinding(Event event) {
        String idToCompare = event.getEventID();
        boolean match = false;
        for (Event e : eventList) {
            String ID = e.getEventID();
            if (ID.equals(idToCompare)) {
                match = true;
            }
        }
        return match;
    }

    // adds an event if the event is not in the list.
    // ID is used as the same user can go into the same establishment multiple times.
    // ID guarantees that every event is unique
    public static boolean addIfNotInArray(Event event) {
        boolean add = eventFinding(event);
        boolean added;
        if (!add) {
            addEventToList(event);
            added = true;
        }
        else {
            added = false;
        }
        return added;
    }

    // simple method to add event to the ArrayList
    public static Event addEventToList(Event event1) {
        eventList.add(event1);
        return event1;
    }


    //ESTABLISHMENT CONTROLLER /////////////////////////////////////////////////////////////////////////////////
    private static ArrayList<Establishment> establishmentsList = new ArrayList<>();

    public static ArrayList<Establishment> getEstablishmentsList() {
        return establishmentsList;
    }

    // Grabs every line in the file and puts it in an ArrayList
    /* Returns the CSV file as an Arraylist of establishments!!
    Since its an ArrayList of Establishments you'll need to do stuff like...
  >>>>> ArrayList<Establishment> establishment = establishmentParser(records);
        for(Establishment es: establishment) {
            System.out.println(es.name);
            System.out.println(es.firstLineAddress);   }*/

    // reads contents of file into an ArrayList of Strings
    public static ArrayList<String> readCSV() throws FileNotFoundException {
        ArrayList<String> contents = new ArrayList<>();
        File file = new File(Controller.class.getResource("establishments.csv").getFile());
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            contents.add(s.nextLine());
        }
        contents.remove(0);
        return contents;
    }

    // turns contents of ArrayList into an ArrayList of Establishments
    public static ArrayList establishmentParser(ArrayList<String> contents) {
        for (String line : contents) {
            String[] elements = line.split(",");
            establishmentsList.add(new Establishment(elements[0], elements[1], elements[2], Integer.parseInt(elements[3])));
        }
        return establishmentsList;
    }
    //initiates the csv parser methods.
    public static void controllerEstablishmentSetUp() throws FileNotFoundException {
        establishmentParser(readCSV());
    }

    // simple method to add establishment to the ArrayList
    public static Establishment addEstablishmentToList(Establishment establishment) {
        establishmentsList.add(establishment);
        return establishment;
    }

    // searches for establishment - true if establishment is found, false if establishment does not exist
    public static boolean establishmentFinding(Establishment establishment) {
        String nameToCompare = establishment.getName();
        boolean match = false;
        for (Establishment e : establishmentsList) {
            String establishmentName = e.getName();
            if (e.getName().toLowerCase().equals(nameToCompare.toLowerCase())) {
                match = true;
            }
        }
        return match;
    }

    // adds an event if the event is not in the list.
    public static boolean addIfNotInArray(Establishment establishment) {
        boolean add = establishmentFinding(establishment);
        boolean added;
        if (!add) {
            addEstablishmentToList(establishment);
            added = true;
        }
        else {
            added = false;
        }
        return added;
    }

    // Searches for Establishments in the list using its name, returns the establishment
    public static Establishment establishmentFinder(String establishmentName) throws FileNotFoundException{
        Establishment eToFind = null;
        for (Establishment e: establishmentsList) {
            if (e.getName().equals(establishmentName)) {
                eToFind = e;
            }
        }
        return eToFind;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Sub menu - Functions
    ArrayList filterByEstablishment(String establishmentName) {
        ArrayList<String> filteredRecords = new ArrayList<>();
        for (Event e : getEventList()) {
            if (e.getEstablishment().getName().equals(establishmentName)) {
                filteredRecords.add(e.getUser().nameAndNumber());
            }
        }
        return filteredRecords;
    }

    ArrayList filterByDate(String date) {
        ArrayList<String> filteredRecords = new ArrayList<>();
        for(Event e : getEventList()) {
            if (e.getEventDate().equals(date)) {
                filteredRecords.add(e.getFullEvent());
            }
        }
        return filteredRecords;
    }

    ArrayList filterByUser(String userName, String email) {
        ArrayList<String> filteredRecords = new ArrayList<>();
        for(Event e : getEventList()) {
            if (e.getUser().getName().equals(userName) && e.getUser().getEmail().equals(email)) {
                filteredRecords.add(e.getFullEvent());
            }
        }
        return filteredRecords;
    }

}

