
import java.util.Scanner;

public class PassengerMenu {
    private Scanner scanner;

    public PassengerMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        PassengerFile file = new PassengerFile();
        PassengerManagement passengerManagement = new PassengerManagement(file, scanner);

        while (true) {
            System.out.println("==== Passenger Management System ====");
            System.out.println("1. Add Passenger");
            System.out.println("2. Display All Passengers");
            System.out.println("3. Search Passenger by ID");
            System.out.println("4. Update Passenger Information");
            System.out.println("5. Delete Passenger");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Enter number of passengers to add: ");
                    int numberOfPassengers = Integer.parseInt(scanner.nextLine());
                    for (int i = 0; i < numberOfPassengers; i++) {
                        System.out.println("Adding Passenger " + (i + 1) + ":");
                        passengerManagement.addPassenger();
                    }
                    break;
                case 2:
                    passengerManagement.displayPassengers();
                    break;
                case 3:
                    passengerManagement.searchPassenger();
                    break;
                case 4:
                    passengerManagement.updatePassenger();
                    break;
                case 5:
                    passengerManagement.deletePassenger();
                    break;
                case 6:
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
