import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/* Contains code from https://ncl.instructure.com/courses/24644/modules
 * Original Author: Jordan Barnes
 * Editing Author: Vincent Taylor */

public class IO {
    private final Controller c;

    private IO() throws FileNotFoundException {
        this.c = new Controller();
    }

    String menuPrompt = "Please enter a number:\n 1. Record an Event" +
            "\n 2. Add an establishment" +
            "\n 3. Filter records" +
            "\n 4. Print Events" +
            "\n 5. Print establishments" +
            "\n 6. Exit the program";

    // Menu function that allows the user to use the other functions
    private static void menu() throws FileNotFoundException {
        IO io = new IO();
        String prompt = "Please enter a number:\n 1. Record an Event" +
                "\n 2. Add an establishment" +
                "\n 3. Filter records" +
                "\n 4. Print Events" +
                "\n 5. Print establishments" +
                "\n 6. Exit the program";
        System.out.println(prompt);
        Scanner input = new Scanner(System.in);
        boolean run = true;
        while(run) {
            int choice = input.nextInt();
            switch (choice) {
                case 1: //Done
                    io.case1();
                    break;
                case 2: //Done
                    io.case2();
                    break;
                case 3:
                    io.case3subMenu();
                    break;
                case 4://Done
                    io.case4();
                    break;

                case 5: //Done
                    io.case5();
                    break;
                case 6: //Done
                    System.out.println("Exiting application... ");
                    run = false;
                    break;
                default:
                    System.out.println("Not a valid option.\n ");

            }
        }
    }

    /* Record an Event Function - Users are asked to input details to create a user, input establishment name, the program
    then searches for an establishment with the matching name and using those establishment details to create an event.
    If an event is not found the program asks the user to create an event.*/
    void case1() throws FileNotFoundException {
        IO io = new IO();

        System.out.println("Adding an event... Creating user details. " +
                "\nPlease enter the following." +
                "\nName of user... ");
        String uName = new Scanner(System.in).nextLine();

        String dob = null;

        boolean dobCorrect = false;
        while(!dobCorrect) {
            String dobCheck;
            System.out.println("Please enter a valid date of birth in the correct format (DD/MM/YYYY)");
            dobCheck = new Scanner(System.in).nextLine();
            if (dobCheck.length()==10) {
                if (dateFormat(dobCheck)) {
                    if (validDate(dobCheck)) {
                        dobCorrect = true;
                        dob = dobCheck;
                    }
                }
            }

        }

        String em = null;
        boolean emailValid = false;
        while(!emailValid) {
            System.out.println("Please enter a valid email address... ");
            String tempEmail = new Scanner(System.in).nextLine();
            if (validEmail(tempEmail)) {
                emailValid = true;
                em = tempEmail;
            }
        }


        String pn = null;
        boolean pnValid = false;
        while(!pnValid) {
            System.out.println("Enter a valid phone number... ");
            String TempPn = new Scanner(System.in).nextLine();
            if (validNumber(TempPn)) {
                pnValid = true;
                pn = TempPn;
            }
        }

        User user = new User(uName, dob,em,pn);

        System.out.println("Enter the party number: ");
        int partyNumber = new Scanner(System.in).nextInt();


        Establishment esta = null;
        String estaToSearch;
        String findValidEstablishment;
        boolean validEstablishment = false;
        while (!validEstablishment) {
            System.out.println("Enter the name of a valid establishment: ");
            estaToSearch = new Scanner(System.in).nextLine();
            boolean validEsta = validEstablishment(estaToSearch);
            if(validEsta) {
                findValidEstablishment = estaToSearch;
                esta = c.establishmentFinder(findValidEstablishment);
                validEstablishment = true;
            } else {
                System.out.println("Establishment Doesn't appear to exist...\n" +
                        "Please create establishment.");
                esta = case2();
                break;
            }
        }

        // Event(User user, LocalDate date, int partyNumber, Establishment establishment)

        Event event = c.recordEvent(user, partyNumber, esta);
        try {
            System.out.println("Added to the list of events: ");
            System.out.println(c.addIfNotInArray(event));
        } catch (Exception e){
            System.out.println("Error. Establishment entered doesn't appear to exist.");
        }

        System.out.println("Returned to Main Menu.\n" + menuPrompt);
    }

    // Add Establishment to ArrayList
    Establishment case2(){
        System.out.println("Enter the name of the establishment");
        Scanner scanName = new Scanner(System.in);
        String esName = scanName.nextLine();

        System.out.println("Enter the first line of the establishment's address");
        Scanner scanFirstLineAddress = new Scanner(System.in);
        String esLine = scanFirstLineAddress.nextLine();

        System.out.println("Enter the establishment's post code");
        Scanner scanPostcode = new Scanner(System.in);
        String esPostCode = scanPostcode.nextLine();

        System.out.println("Enter the max occupancy of the establishment");
        Scanner scanOccupancy = new Scanner(System.in);
        int esMaxOcc = scanOccupancy.nextInt();

        Establishment establishment = new Establishment(esName, esLine, esPostCode, esMaxOcc);

        System.out.println(c.addIfNotInArray(establishment));

        System.out.println("Request complete. Returning\n");
        return establishment;
    }

    // Submenu Function - Allows users to filter through events using User name & email, date or establishment name.
    void case3subMenu() {
        String subMenu = "How would you like to filter through events?\n" +
                "1. Establishments \n" +
                "2. Date \n" +
        "3. Name of user \n" +
        "4. Return to the menu screen";
        System.out.println(subMenu);

        boolean runSubMenu = true;
        while(runSubMenu) {

            Scanner case3Options = new Scanner(System.in);
            switch(case3Options.nextInt()) {
                case 1:
                    // filter through events by establishment function
                    System.out.println("Please enter the name of the establishment: ");
                    String establishmentName = new Scanner(System.in).nextLine();

                    System.out.println("Here are all the users that visited the given establishment:\n");
                    printFilteredRecords(c.filterByEstablishment(establishmentName));
                    System.out.println("\nReturned to sub menu.\n" + subMenu);
                    break;
                case 2:
                    // filter through events by date of event function
                    System.out.println("Please enter the date of the event (DD/MM/YYYY): ");
                    String input = new Scanner(System.in).nextLine();

                    System.out.println("Printing all events that occurred at the given date:\n");
                    printFilteredRecords(c.filterByDate(input));
                    System.out.println("\nReturned to sub menu.\n" + subMenu);
                    break;
                case 3:
                    // filter through events by user function
                    System.out.println("Please enter the name of the user: ");
                    String userName = new Scanner(System.in).nextLine();

                    System.out.println("Enter the user's email: ");
                    String email = new Scanner(System.in).nextLine();

                    System.out.println("Printing all events that inputted user is linked to:\n");
                    printFilteredRecords(c.filterByUser(userName, email));
                    System.out.println("\nReturned to sub menu.\n" + subMenu);
                    break;
                case 4:
                    System.out.println("Returned to Main Menu.\n" + menuPrompt);
                    runSubMenu = false;
                    break;
            }
        }
    }

    // Returns all Events
    void case4() {
        System.out.println("Printing all events.\n");
        ArrayList<Event> eventList = c.getEventList();

        for(Event e: eventList) {
            System.out.println(e.getFullEvent());
            System.out.println();
        }
        System.out.println("Done!\n");
        System.out.println("Returned to Main Menu.\n"+ menuPrompt);
    }

    // Returns all establishments
    void case5() throws FileNotFoundException {
        System.out.println("Printing all establishments.\n");
        ArrayList<Establishment> establishmentList = c.getEstablishmentsList();

        for(Establishment es: establishmentList) {
            System.out.println(es.toString());
            System.out.println();
        }

        System.out.println("Done!\n");
        System.out.println("Returned to Main Menu.\n" + menuPrompt);
    }

    // allows the printing of ArrayLists after they have been filtered in the controller.
    void printFilteredRecords(ArrayList<String> list) {
        for (String e : list) {
            System.out.println(e);
        }
    }

    // Functions that check the validity of input
    private static boolean dateFormat(String date) {
        boolean isValid = false;
        if (date.contains("/")) {
            isValid = true;
        }
        return isValid;
    }
    private static boolean validDate(String date) {
        boolean isBefore = false;
        if (LocalDate.parse(date, new Event().dateFormat).isBefore(LocalDate.now())) {
            isBefore = true;
        }
        return isBefore;
    }
    private static boolean validEmail(String email) {
        boolean isValid = false;
        if (email.contains("@")) {
            isValid = true;
        }
        return isValid;
    }
    private static boolean validNumber(String number) {
        boolean isValid = false;
        if (number.length() == 11 && number.startsWith("0")) {
            isValid = true;
        }
        return isValid;
    }
    private static boolean validEstablishment(String establishmentName) {
        boolean isValid = false;
        ArrayList<Establishment> searchThis = Controller.getEstablishmentsList();
            for (Establishment e: searchThis) {
                if (e.getName().toLowerCase().contains(establishmentName.toLowerCase())) {
                    isValid = true;
                }
            }

        return isValid;
    }

    // Hard coded several user objects and several event objects.
    private static void debug() {
        Controller c = new Controller();

        // Keep adding objects and testing functions here
        //  User Objects

        User user1 = new User("Vincent Taylor", "20/05/1996", "vjmartinezt@outlook.com", "07305577887");

        User user2 = new User("Mary Jane", "03/11/2020", "mjneedssavinggmail.com", "07722456755");

        User user3 = new User("John Cena", "23/04/1977", "icantseeme@wwe.com", "This is my number");

/*      erroneous
        User user4 = new User("Catness Evergreen", 23/04/2001, "strokeme@catman.com", 073455678865);*/

        //  Establishment Objects
        Establishment establishment1 = new Establishment("Some Sweet Crib", "2 Sweet Road", "SG5 AK76", 73);

        Establishment establishment2 = new Establishment("Rasta Shop", "1 Jamaican Road, B0b PU3", 90);

        Establishment establishment3 = new Establishment("Some Cake Shop", "1 Sweet Road", "Post C0de", -99);

        /* erroneous
        Establishment establishment4 = new Establishment("District Cat", "11 District Crescent", DR11 SUS, "73");*/

        //  Adding Establishment Objects to ArrayList
        c.addEstablishmentToList(establishment1);
        c.addEstablishmentToList(establishment2);
        c.addEstablishmentToList(establishment3);

        //  Event Objects
        Event event1 = new Event(user1, LocalDate.now(), LocalTime.now(), 2, establishment1);
        Event event2 = new Event(user2, LocalDate.now(), LocalTime.now(), 2, establishment2);
        Event event3 = new Event(user3, LocalDate.now(), LocalTime.now(), 8, establishment3);

        //  Adding Event Object to ArrayList
        c.addEventToList(event1);
        c.addEventToList(event2);
        c.addEventToList(event3);
    }

    public static void main(String[] args) throws FileNotFoundException {
        IO io = new IO();
        debug();

        // Read CSV and loads establishments into the program.
        Controller.controllerEstablishmentSetUp();

        //Menu
        try {
            menu();
        } catch (InputMismatchException e) {
            System.out.println("Input error. Ending application");
        }
    }

    // remove IO objects and make everything refer to "c" in the IO constructor
    // make sure Controller methods referred in this way is not static

}