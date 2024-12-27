
import java.util.*;

public class PassengerManagement {
    private PassengerFile fileHandler;
    private Scanner scanner;
    private Map<String, Passenger> passengerMap;

    // Constructor
    public PassengerManagement(PassengerFile fileHandler, Scanner scanner) {
        this.fileHandler = fileHandler;
        this.scanner = scanner;
        this.passengerMap = new HashMap<>();


        fileHandler.loadPassengerfromFile(passengerMap);
    }




    public void addPassenger() {
        String id = readInput("Enter Passenger ID: ").trim();
        if (passengerMap.containsKey(id)) {
            System.out.println("Passenger ID already exists. Please use a different ID.");
            return;
        }

        String name = readInput("Enter Passenger Name: ").trim();
        int age = Integer.parseInt(readInput("Enter Passenger Age: ").trim());
        String gender = readInput("Enter Passenger Gender (Male/Female): ").trim();
        String phoneNumber = readInput("Enter Passenger Phone Number: ").trim();


        Passenger newPassenger = new Passenger(id, name, age, gender, phoneNumber);
        passengerMap.put(id, newPassenger);


        fileHandler.savePassengerToFile(passengerMap);
        System.out.println("Passenger added successfully!");
    }


    public void displayPassengers() {
        if (passengerMap.isEmpty()) {
            System.out.println("No passengers found.");
            return;
        }

        System.out.println("Passenger List:");
        for (Passenger passenger : passengerMap.values()) {
            System.out.println(passenger);
        }
    }


    public void searchPassenger() {
        String id = readInput("Enter Passenger ID to search: ").trim();
        Passenger passenger = passengerMap.get(id);

        if (passenger != null) {
            System.out.println("Passenger Found:");
            System.out.println(passenger);
        } else {
            System.out.println("No passenger found with the given ID.");
        }
    }


    public void deletePassenger() {
        String id = readInput("Enter Passenger ID to delete: ").trim();
        if (passengerMap.remove(id) != null) {
            fileHandler.savePassengerToFile(passengerMap);
            System.out.println("Passenger deleted successfully!");
        } else {
            System.out.println("No passenger found with the given ID.");
        }
    }


    public void updatePassenger() {
        String id = readInput("Enter Passenger ID to update: ").trim();
        Passenger passenger = passengerMap.get(id);

        if (passenger != null) {
            System.out.println("Current Information: " + passenger);

            String name = readInput("Enter new Name (leave blank to keep current): ").trim();
            String ageInput = readInput("Enter new Age (leave blank to keep current): ").trim();
            String gender = readInput("Enter new Gender (leave blank to keep current): ").trim();
            String phoneNumber = readInput("Enter new Phone Number (leave blank to keep current): ").trim();

            if (!name.isEmpty()) {
                passenger.setName(name);
            }
            if (!ageInput.isEmpty()) {
                try {
                    int age = Integer.parseInt(ageInput);
                    passenger.setAge(age);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age input. Keeping current age.");
                }
            }
            if (!gender.isEmpty()) {
                passenger.setGender(gender);
            }
            if (!phoneNumber.isEmpty()) {
                passenger.setPhoneNumber(phoneNumber);
            }


            fileHandler.savePassengerToFile(passengerMap);
            System.out.println("Passenger updated successfully!");
        } else {
            System.out.println("No passenger found with the given ID.");
        }
    }


    private String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}