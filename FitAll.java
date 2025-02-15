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
        //"this" is used to refer to the properties of the Session class that were declared
        this.sessionID = idCounter++;
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

    //setter method for available spaces
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
}

public class FitAll {
    //array of length 7 created to store values of table
    private static Session[] sessions = new Session[7];
    private static Scanner scanner = new Scanner(System.in);

    //main method used to create a session
    public static void main(String[] args) {
        //creates new object of class type FitAll
        FitAll fitAll = new FitAll();
        //creates the sessions
        fitAll.initializeSessions();
        //initially prints timetable so user can see
        fitAll.printTimetable();
        //runs menu
        fitAll.runMenu();
    }

    private void initializeSessions() {
        //adds values to esch index of the created array
        sessions[0] = new Session("Pilates", "Low", "Monday", "13:00", 55, 15);
        sessions[1] = new Session("Yoga", "Low", "Friday", "18:00", 55, 15);
        sessions[2] = new Session("Core", "Medium", "Tuesday", "19:00", 55, 20);
        sessions[3] = new Session("Pump", "Medium", "Tuesday", "10:00", 55, 20);
        sessions[4] = new Session("Yoga", "Medium", "Wednesday", "12:00", 55, 15);
        sessions[5] = new Session("Core", "High", "Thursday", "18:00", 45, 20);
        sessions[6] = new Session("Cycling", "High", "Wednesday", "09:00", 45, 10);
    }

    private void runMenu() {
        //while loop used for exception handling
        while (true) {
            System.out.println("1. View Timetable\n2. Register for Session\n3. Cancel Registration\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = 0;
            try{
                choice = scanner.nextInt();
            }
            catch (Exception e) {
                System.out.println("Invalid input. Input should be a number from 1-4.");
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
                    return;
                default:
                    System.out.println("Invalid number. Try again.");
                    scanner.nextLine();
            }
        }
    }

    //method prints the timetable by firstly printing the first row then iterating the printSession method which
    //prints an individual new row that contains the contents of the timetable
    private void printTimetable() {
        //each entry has a space of 10 characters so it can be formatted nicely
        System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n", "SessionID", "Name", "Level", "Day", "Time", "Duration", "Spaces");
        for (int i = 0; i < sessions.length; i++) {
            sessions[i].printSession();
        }
    }

    private void registerSession() {
        //while loop used for exception handling
        while (true) {
        System.out.print("Enter Session ID to register: ");
        //declares id variable so it can be modified for user input
        int id = 0;
        try{
            id = scanner.nextInt();
        }
        catch (Exception e){
            System.out.println("Invalid input. Input should be a number. ");
            //scanner.nextLine() clears the scanner to prevent an infinite loop
            scanner.nextLine();
            //continue used to repeat the while loop from start, so user can try again
            continue;
        }
        for (int i = 0; i < sessions.length; i++) {
            if (sessions[i].getSessionID() == id && sessions[i].getAvailableSpaces() > 0) {
                //calls setter method to change the number of spaces in the table
                sessions[i].updateSpaces(-1);
                System.out.println("Successfully registered!");
                return;
            }
        }
        //Displays message if user fails to register
        System.out.println("Incorrect ID or no more spaces left.\nCheck the table and try again. ");
    }
}

    private void cancelRegistration() {
        //while loop used for exception handling
        while (true) {
        System.out.print("Enter Session ID to cancel registration: ");
        int id = 0;
        try{
            id = scanner.nextInt();
        }
        catch (Exception e){
            System.out.println("Invalid input");
            //scanner.nextLine() clears the scanner to prevent an infinite loop
            scanner.nextLine();
            //continue used to repeat the while loop from start, so user can try again
            continue;
        }
        for (int i = 0; i < sessions.length; i++) {
            if (sessions[i].getSessionID() == id) {
                //calls setter method to change the number of spaces in the table
                sessions[i].updateSpaces(1);
                System.out.println("Registration cancelled.");
                return;
                }
            }
            //Displays message if user fails to cancel registration
            System.out.println("Incorrect ID.\nCheck the table and try again. ");
        }
    }
}
