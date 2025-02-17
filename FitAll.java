//import scanner class so user input can be taken in
import java.util.Scanner;

class Session {
    //Creates private properties
    private static int idCounter = 1;
    private int sessionID;
    private String sessionName;
    private String fitnessLevel;
    private String day;
    private String startTime;
    private int duration;
    private int availableSpaces;

    public Session(String sessionName, String fitnessLevel, String day, String startTime, int duration, int availableSpaces) {
        //for every new instance of a session, the sessionID is incremented to ensure that it is unique
        sessionID = idCounter++;
        //"this" is used to refer to the properties of the Session class that were declared
        this.sessionName = sessionName;
        this.fitnessLevel = fitnessLevel;
        this.day = day;
        this.startTime = startTime;
        this.duration = duration;
        this.availableSpaces = availableSpaces;
    }
    //getter method for session ID
    public int getSessionID() {
        return sessionID;
    }

    //setter method to update available spaces
    public void updateSpaces(int change) {
        availableSpaces += change;
    }

    //getter method for available spaces
    public int getAvailableSpaces() {
        return availableSpaces;
    }

    //method to print a row in the timetable
    public void printSession() {
        System.out.printf("%-10d %-10s %-10s %-10s %-10s %-10d %-10d\n", sessionID, sessionName, fitnessLevel, day, startTime, duration, availableSpaces);
    }

    //method used to display registered sessions but without showing available spaces
    public void printRegisteredSession() {
        System.out.printf("%-10d %-10s %-10s %-10s %-10s %-10d\n", sessionID, sessionName, fitnessLevel, day, startTime, duration);
    }
}

public class FitAll {
    //array of length 7 created to store values of table
    private static Session[] sessions = new Session[7];
    private static int[] registeredSessions = new int[7];
    private static Scanner scanner = new Scanner(System.in);
    private static int registeredSessionsNumber = 0;

    //main method used to create a session
    public static void main(String[] args) {
        //creates new object of class type FitAll
        FitAll fitAll = new FitAll();
        //creates the sessions
        fitAll.initializeSessions();
        //initially prints timetable so user can see
        System.out.println("\nWelcome to the FitAll club!");
        fitAll.printTimetable();
        //runs menu
        fitAll.runMenu();
    }

    private void initializeSessions() {
        //adds values to esch index of the created array
        sessions[0] = new Session("Cycling", "High", "Thursday", "10:00", 45, 20);
        sessions[1] = new Session("Core", "Medium", "Monday", "14:00", 55, 15);
        sessions[2] = new Session("Yoga", "Low", "Saturday", "09:00", 55, 10);
        sessions[3] = new Session("Pump", "High", "Tuesday", "18:00", 45, 15);
        sessions[4] = new Session("Pilates", "Medium", "Wednesday", "12:00", 55, 20);
        sessions[5] = new Session("Core", "Low", "Friday", "17:00", 45, 10);
        sessions[6] = new Session("Yoga", "High", "Sunday", "11:00", 55, 15);

        //set every item in every index in registeredSessions to -1 so that it can be used to
        //check if there are no registered sessions in viewRegisteredSessions
        registeredSessions[0] = -1;
        registeredSessions[1] = -1;
        registeredSessions[2] = -1;
        registeredSessions[3] = -1;
        registeredSessions[4] = -1;
        registeredSessions[5] = -1;
        registeredSessions[6] = -1;
    }

    private void runMenu() {
        //while loop used for exception handling
        while (true) {
            System.out.println("\nMain menu:");
            System.out.println("\n1. View timetable\n2. Register for session\n3. Cancel registration\n4. View registered sessions\n5. Exit");
            System.out.print("Enter your choice: ");
            int choice = 0;
            try{
                choice = scanner.nextInt();
            }
            catch (Exception e) {
                System.out.println("\nInvalid input. Input should be a number from 1-4.");
                //scanner.nextLine() clears the scanner to prevent an infinite loop
                scanner.nextLine();
                continue;
            }
            //switch and case is used as there are 4 options for cleaner code, there would be too many else if statements otherwise
            switch (choice) {
                case 1:
                    printTimetable();
                    //breaks while loop if the input is valid
                    break;
                case 2:
                    registerSession();
                    break;
                case 3:
                    cancelRegistration();
                    break;
                case 4:
                    viewRegisteredSessions();
                    break;
                case 5:
                    //ends program by returning to main method which has finished itself
                    return;
                default:
                    System.out.println("\nInvalid number. Try again.");
                    scanner.nextLine();
            }
        }
    }

    //method prints the timetable by firstly printing the first row then iterating the printSession method which
    //prints an individual new row that contains the contents of the timetable
    private void printTimetable() {
        //shows number of registered sessions
        System.out.println("\nNumber of registered sessions:" + registeredSessionsNumber);
        //each entry has a space of 10 characters so it can be formatted nicely
        System.out.printf("\n%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", "SessionID", "Name", "Level", "Day", "Time", "Duration", "Spaces");
        for (int i = 0; i < sessions.length; i++) {
            sessions[i].printSession();
        }
    }

    private void registerSession() {

        //while loop used for exception handling
        while (true) {
        System.out.print("\nEnter Session ID to register: ");
        System.out.println("\nEnter -1 to return to main menu");
        //declares id variable so it can be modified for user input
        int id = 0;
        //try catch is used to check if input is an integer
        try{
            id = scanner.nextInt();
        }
        catch (Exception e){
            System.out.println("\nInvalid input. Input should be a number. ");
            //scanner.nextLine() clears the scanner to prevent an infinite loop
            scanner.nextLine();
            //continue used to repeat the while loop from start, so user can try again
            continue;
        }
        //if statement used so user can return to menu if needed
        if (id == -1) {
            return;
        }
        for (int i = 0; i < sessions.length; i++) {
            //checks if id matches and if there are any available spaces
            if (sessions[i].getSessionID() == id && sessions[i].getAvailableSpaces() > 0) {
                //checks if user has registered already for a session
                for (int x = 0; x < 7; x++) {
                    if (registeredSessions[x] == id) {
                        System.out.println("You are already registered for this session");
                        return;
                    }
                }
                //calls setter method to decrease the number of spaces in the table by 1, by passing in -1
                //as an argument to the updateSpaces class
                sessions[i].updateSpaces(-1);
                //add the id into the array into required index registeredSessions to show that user has registered for that session
                registeredSessions[i] = id;
                System.out.println("\nSuccessfully registered! ");
                //increment registeredSessionsNumber by 1
                registeredSessionsNumber++;
                return;
            }
        }
        //Displays message if user fails to register
        System.out.println("\nIncorrect ID or no more spaces left.\nCheck the table and try again. ");
    }
}

    private void cancelRegistration() {
        //while loop used for exception handling
        while (true) {
        System.out.println("Enter Session ID to cancel registration: ");
        System.out.println("\nEnter -1 to return to main menu if needed\n");
        int id = 0;
        try{
            id = scanner.nextInt();
        }
        catch (Exception e){
            System.out.println("\nInvalid input. Try again. ");
            //scanner.nextLine() clears the scanner to prevent an infinite loop
            scanner.nextLine();
            //continue used to repeat the while loop from start, so user can try again
            continue;
        }
        //if statement used so user can return to menu if needed
        if (id == -1) {
            return;
        }
        //use index = -1 to check if user has registered for session
        int index = -1;
        for (int i = 0; i < 7; i++) {
            if (registeredSessions[i] == id) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println("\nYou are not registered for this session.");
            System.out.println("Check the table and try again.\n");
            continue;
        }

        for (int i = 0; i < sessions.length; i++) {
            //checks if user enters a valid id
            if (sessions[i].getSessionID() == id) {
                //calls setter method to increase the number of spaces in the table by 1, by passing in 1
                //as an argument to the updateSpaces class
                sessions[i].updateSpaces(1);
                System.out.println("\nRegistration cancelled. ");
                registeredSessions[i] = -1;
                //decrement registeredSessionsNumber by 1
                registeredSessionsNumber--;
                return;
                }
            }
        }
    }

    //method used to show what sessions the user has registered for
    private void viewRegisteredSessions() {
       //boolean exists used to display message if all values in registeredSessions are set to -1
       boolean exists = false;
       System.out.println("\nThese are your registered sessions:\n");
       for (int i = 0; i < registeredSessions.length; i++) {
           //if value is not -1, it means the user has registered for that session
           //so session will be printed
           if (registeredSessions[i] != -1) {
               sessions[i].printRegisteredSession();
               exists = true;
           }
       }
       //displays message if user has not registered for any sessions
       if (!exists) {
            System.out.println("You have not registered for any sessions.");
       }
    }
}
