    /*make sure that this doesnt do any printing!
     * make sure that it is removed after wards*/

    import java.time.LocalDate;
    import java.time.LocalTime;
    import java.time.format.DateTimeFormatter;
    import java.util.Random;

    /* Contains code from https://ncl.instructure.com/courses/24644/modules
     * Original Author: Jordan Barnes
     * Editing Author: Vincent Taylor */

    public class Event {
        // instance fields
        private int eventID;
        private User user;
        private LocalDate eventDate;
        private LocalTime eventTime;
        private int partyNumber = 1;
        private Establishment establishment;

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        // constructors methods
        Event() {

        }
        // Teacher did ___________________________________________________________________________
        Event(User user, LocalDate date, int partyNumber, Establishment establishment) {
            this.user = user;
            this.user.setName(user.getName());
            this.user.setBirthDay(user.getBirthDay());
            this.user.setBirthMonth(user.getBirthMonth());
            this.user.setBirthYear(user.getBirthYear());
            this.user.setEmail(user.getEmail());
            this.user.setPhoneNumber(user.getPhone());
            this.eventDate = date;
            this.partyNumber = partyNumber;
            this.establishment = establishment;
            this.eventTime = LocalTime.now();
            createEventID();
        }

        Event(User user, int partyNumber, Establishment establishment) {
            this.user = user;
            this.partyNumber = partyNumber;
            this.establishment = establishment;
            this.eventTime = LocalTime.now();
            this.eventDate = LocalDate.now();
            createEventID();
        }
        // Teacher did ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

        public Event(User user, LocalDate eventDate, LocalTime eventTime, int partyNumber, Establishment establishment) {
            this.user = user;
            this.eventDate = eventDate;
            this.eventTime = eventTime;
            this.partyNumber = partyNumber;
            this.establishment = establishment;
            createEventID();
        }

        // Getter methods
        public String getEventID() {return String.valueOf(eventID);}
        public User getUser() {
            return user;
        }
        public String getEventDate() {
            return eventDate.format(dateFormat);
        }
        public String getEventTime() {

            return eventTime.format(timeFormat);
        }
        public String getPartyNumber() {
            return String.valueOf(partyNumber);
        }
        public Establishment getEstablishment() {
            return establishment;
        }

        // Methods

        public void createEventID() {
            Random rng = new Random();
            int min = 100000000;
            int max = 999999999;
            this.eventID = rng.nextInt(((max-min)+min)+min);
        }

        /* Print of details - Section 1 */

        public String getFullEvent() {

            String eventID = "Event ID: " + this.getEventID() + " | " + "Recorded User\n";
            User user1 = this.getUser();
            String userDetails = user1.returnUser();

            String eventDetails =
                    "Date " + this.getEventDate() +
                            "\nTime " + this.getEventTime() +
                            "\nParty Size " + this.getPartyNumber() + "\n";
            String establishmentDetails = this.getEstablishment().toString();
            return eventID + userDetails + eventDetails + establishmentDetails +"\n";
        }

    }
