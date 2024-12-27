import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Flight {
    protected ArrayList<Flight> FL = new ArrayList<>();
    protected String ID, LocateStart, LocateEnd, Status, Airline_Name;
    protected LocalDateTime datetimeStart = null, datetimeEnd = null;
    protected int Chairs = 0;
    protected final String FILE_PATH = "Flight.csv";

    public final DateTimeFormatter dt_format = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

    public Flight() {}

    public Flight(String ID, String LocateStart, String LocateEnd, String Status, String Airline_Name, int Chairs,
                  LocalDateTime datetimeStart, LocalDateTime datetimeEnd) {
        this.ID = ID;
        this.LocateStart = LocateStart;
        this.LocateEnd = LocateEnd;
        this.Status = Status;
        this.Airline_Name = Airline_Name;
        this.Chairs = Chairs;
        this.datetimeStart = datetimeStart;
        this.datetimeEnd = datetimeEnd;
    }

    public String getID() {
        return ID;
    }

    public String getLocateStart() {
        return LocateStart;
    }

    public String getLocateEnd() {
        return LocateEnd;
    }

    public String getStatus() {
        return Status;
    }

    public String getAirline_Name() {
        return Airline_Name;
    }

    public LocalDateTime getDatetimeStart() {
        return datetimeStart;
    }

    public LocalDateTime getDatetimeEnd() {
        return datetimeEnd;
    }

    public int getChairs() {
        return Chairs;
    }

    private ArrayList<Flight> readFromFile() {
        ArrayList<Flight> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) continue;
                Flight flight = new Flight(
                        fields[0],
                        fields[2],
                        fields[3],
                        fields[4],
                        fields[1],
                        Integer.parseInt(fields[5]),
                        LocalDateTime.parse(fields[6], dt_format),
                        LocalDateTime.parse(fields[7], dt_format)
                );
                flights.add(flight);
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
        ArrayList<Flight> result = new ArrayList<>(flights);
        flights.clear();
        return result;
    }

    private boolean isDuplicateID(String id) {
        ArrayList<Flight> flights = readFromFile();
        for (Flight flight : flights) {
            if (flight.ID.equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private void writeToFile(ArrayList<Flight> flights, boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, append))) {
            for (Flight flight : flights) {
                String line = String.format(
                        "%s,%s,%s,%s,%s,%d,%s,%s%n",
                        flight.ID,
                        flight.Airline_Name,
                        flight.LocateStart,
                        flight.LocateEnd,
                        flight.Status,
                        flight.Chairs,
                        flight.datetimeStart.format(dt_format),
                        flight.datetimeEnd.format(dt_format)
                );
                writer.write(line);
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void Add_Flight() {
        Scanner sc = new Scanner(System.in);
        boolean addmore;

        do{
            do {
                System.out.println("Enter Flight ID:");
                ID = sc.nextLine().trim();
                if (ID.isEmpty()) {
                    System.out.println("Flight ID cannot be empty.");
                } else if (isDuplicateID(ID)) {
                    System.out.println("Flight ID already exists in the file. Please enter a different ID.");
                    ID = "";
                }
            } while (ID.isEmpty());

            do {
                System.out.println("Enter Airline Name:");
                Airline_Name = sc.nextLine().trim();
                if (Airline_Name.isEmpty()) {
                    System.out.println("Airline Name cannot be empty.");
                }
            } while (Airline_Name.isEmpty());

            do {
                System.out.println("Enter Start Location:");
                LocateStart = sc.nextLine().trim();
                if (LocateStart.isEmpty()) {
                    System.out.println("Start Location cannot be empty.");
                }
            } while (LocateStart.isEmpty());

            do {
                System.out.println("Enter End Location:");
                LocateEnd = sc.nextLine().trim();
                if (LocateEnd.isEmpty()) {
                    System.out.println("End Location cannot be empty.");
                }
            } while (LocateEnd.isEmpty());

            boolean validChairs = false;
            do {
                System.out.println("Enter number of chairs:");
                try {
                    Chairs = Integer.parseInt(sc.nextLine().trim());
                    if (Chairs <= 0) {
                        System.out.println("Chairs must be greater than 0.");
                    } else {
                        validChairs = true;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid number format. Please enter a valid integer.");
                }
            } while (!validChairs);

            do {
                System.out.println("Enter Start Date & Time (HH:mm dd/MM/yyyy):");
                try {
                    datetimeStart = LocalDateTime.parse(sc.nextLine().trim(), dt_format);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid format. Please use HH:mm dd/MM/yyyy.");
                    datetimeStart = null;
                }
            } while (datetimeStart == null);

            do {
                System.out.println("Enter End Date & Time (HH:mm dd/MM/yyyy):");
                try {
                    datetimeEnd = LocalDateTime.parse(sc.nextLine().trim(), dt_format);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid format. Please use HH:mm dd/MM/yyyy.");
                    datetimeEnd = null;
                }
            } while (datetimeEnd == null);

            do {
                System.out.println("Enter Flight Status:");
                Status = sc.nextLine().trim();
                if (Status.isEmpty()) {
                    System.out.println("Flight Status cannot be empty.");
                }
            } while (Status.isEmpty());

            Flight newFlight = new Flight(ID, LocateStart, LocateEnd, Status, Airline_Name, Chairs, datetimeStart, datetimeEnd);
            FL.add(newFlight);

            ArrayList<Flight> temp = new ArrayList<>();
            temp.add(newFlight);
            writeToFile(temp, true);

            System.out.println("Flight added successfully!");

            System.out.println("Do you want to add another flight? (Y/N): ");
            String choice = sc.nextLine().trim().toUpperCase();
            addmore = choice.equals("Y");
        }while(addmore);
    }

    public void Display_Flight() {
        System.out.println("Loading data from file...");
        FL = readFromFile();

        if (FL.isEmpty()) {
            System.out.println("No flights available to display.");
            return;
        }

        System.out.printf("%-10s %-20s %-20s %-20s %-10s %-10s %-20s %-20s%n",
                "ID", "Airline", "Start Location", "End Location", "Status", "Chairs", "Start Time", "End Time");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------");

        for (Flight flight : FL) {
            System.out.printf("%-10s %-20s %-20s %-20s %-10s %-10d %-20s %-20s%n",
                    flight.ID,
                    flight.Airline_Name,
                    flight.LocateStart,
                    flight.LocateEnd,
                    flight.Status,
                    flight.Chairs,
                    flight.datetimeStart.format(dt_format),
                    flight.datetimeEnd.format(dt_format));
        }
    }

    public void Remove_Flight() {
        Display_Flight();

        Scanner sc = new Scanner(System.in);
        boolean rm_more;

        do {
            System.out.println("Enter the Flight ID to remove:");
            String idToRemove = sc.nextLine().trim();

            boolean found = false;
            for (int i = 0; i < FL.size(); i++) {
                if (FL.get(i).ID.equalsIgnoreCase(idToRemove)) {
                    Flight FR = FL.get(i);
                    System.out.println("Flight found:");
                    System.out.printf(
                            "%-10s %-20s %-20s %-20s %-10s %-10d %-20s %-20s%n",
                            FR.ID,
                            FR.Airline_Name,
                            FR.LocateStart,
                            FR.LocateEnd,
                            FR.Status,
                            FR.Chairs,
                            FR.datetimeStart.format(dt_format),
                            FR.datetimeEnd.format(dt_format)
                    );

                    System.out.println("Are you sure you want to remove this flight? (Y/N): ");
                    String confirmation = sc.nextLine().trim().toUpperCase();
                    if (confirmation.equals("Y")) {
                        FL.remove(i);
                        System.out.println("Flight with ID: " + idToRemove + " has been removed.");
                        writeToFile(FL, false); // Ghi đè file sau khi xóa
                    } else {
                        System.out.println("Flight removal canceled.");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Flight ID: " + idToRemove + " not found in the list.");
            }

            System.out.println("Do you want to remove another flight? (Y/N): ");
            String choice = sc.nextLine().trim().toUpperCase();
            rm_more = choice.equals("Y");
        } while (rm_more);
    }

    public void Update_Flight() {
        Display_Flight();

        Menu m = new Menu(8);
        Scanner sc = new Scanner(System.in);
        boolean up_more;

        do {
            System.out.println("Enter the Flight ID to Update:");
            String idToUpdate = sc.nextLine().trim();
            boolean found = false;

            for (int i = 0; i < FL.size(); i++) {
                if (FL.get(i).ID.equalsIgnoreCase(idToUpdate)) {
                    Flight FU = FL.get(i);
                    found = true;

                    System.out.println("Flight Found:");
                    System.out.printf(
                            "%-10s %-20s %-20s %-20s %-10s %-10d %-20s %-20s%n",
                            FU.ID,
                            FU.Airline_Name,
                            FU.LocateStart,
                            FU.LocateEnd,
                            FU.Status,
                            FU.Chairs,
                            FU.datetimeStart.format(dt_format),
                            FU.datetimeEnd.format(dt_format)
                    );

                    m.add("Airline Name");
                    m.add("Start Location");
                    m.add("End Location");
                    m.add("Number of Chairs");
                    m.add("Start DateTime");
                    m.add("End DateTime");
                    m.add("Flight Status");
                    m.add("Back to Main Menu");

                    int slt;
                    do {
                        System.out.println("\nUpdate Menu:");
                        slt = m.getChoice();
                        switch (slt) {
                            case 1:
                                System.out.println("Enter new Airline Name:");
                                FU.Airline_Name = sc.nextLine().trim();
                                confirmAndWriteToFile(FU, FL);
                                break;

                            case 2:
                                System.out.println("Enter new Start Location:");
                                FU.LocateStart = sc.nextLine().trim();
                                confirmAndWriteToFile(FU, FL);
                                break;

                            case 3:
                                System.out.println("Enter new End Location:");
                                FU.LocateEnd = sc.nextLine().trim();
                                confirmAndWriteToFile(FU, FL);
                                break;

                            case 4:
                                boolean validChairs = false;
                                do {
                                    System.out.println("Enter new number of chairs:");
                                    try {
                                        FU.Chairs = Integer.parseInt(sc.nextLine().trim());
                                        validChairs = true;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Invalid number format. Please enter a valid integer.");
                                    }
                                } while (!validChairs);
                                confirmAndWriteToFile(FU, FL);
                                break;

                            case 5:
                                LocalDateTime newStartTime = null;
                                do {
                                    System.out.println("Enter new Start DateTime (HH:mm dd/MM/yyyy):");
                                    try {
                                        newStartTime = LocalDateTime.parse(sc.nextLine().trim(), dt_format);
                                        FU.datetimeStart = newStartTime;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Invalid format. Please use HH:mm dd/MM/yyyy.");
                                    }
                                } while (newStartTime == null);
                                confirmAndWriteToFile(FU, FL);
                                break;

                            case 6:
                                LocalDateTime newEndTime = null;
                                do {
                                    System.out.println("Enter new End DateTime (HH:mm dd/MM/yyyy):");
                                    try {
                                        newEndTime = LocalDateTime.parse(sc.nextLine().trim(), dt_format);
                                        FU.datetimeEnd = newEndTime;
                                    } catch (DateTimeParseException e) {
                                        System.out.println("Invalid format. Please use HH:mm dd/MM/yyyy.");
                                    }
                                } while (newEndTime == null);
                                confirmAndWriteToFile(FU, FL);
                                break;

                            case 7:
                                System.out.println("Enter new Flight Status:");
                                FU.Status = sc.nextLine().trim();
                                confirmAndWriteToFile(FU, FL);
                                break;

                            case 8:
                                System.out.println("Returning to Main Menu...");
                                return; // Quay về menu chính

                            default:
                                System.out.println("Invalid choice. Please select a valid option.");
                        }
                    } while (slt >= 1 && slt <= 8);
                }
            }

            if (!found) {
                System.out.println("Flight ID: " + idToUpdate + " not found in the list.");
            }

            System.out.println("Do you want to update another flight? (Y/N): ");
            String choice = sc.nextLine().trim().toUpperCase();
            up_more = choice.equals("Y");

        } while (up_more);
    }

    private void confirmAndWriteToFile(Flight updatedFlight, ArrayList<Flight> flights) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to save this change to the file? (Y/N): ");
        String choice = sc.nextLine().trim().toUpperCase();

        if (choice.equals("Y")) {
            writeToFile(flights, false); // Ghi toàn bộ danh sách
            System.out.println("Changes for flight ID " + updatedFlight.ID + " have been saved.");
        } else {
            System.out.println("Changes were not saved.");
        }
    }

    public void Search_Flight() {
        Scanner sc = new Scanner(System.in);
        boolean searchAgain;

        do {
            System.out.println("Enter a keyword to search for flights:");
            String keyword = sc.nextLine().trim().toLowerCase();

            ArrayList<Flight> fileFlights = readFromFile();
            boolean found = false;

            System.out.printf(
                    "%-10s %-20s %-20s %-20s %-10s %-10s %-20s %-20s%n",
                    "ID", "Airline Name", "Start Location", "End Location",
                    "Status", "Chairs", "Start DateTime", "End DateTime"
            );
            System.out.println("-------------------------------------------------------------------------------------------");

            for (Flight flight : fileFlights) {
                if (flight.ID.toLowerCase().contains(keyword) ||
                        flight.Airline_Name.toLowerCase().contains(keyword) ||
                        flight.LocateStart.toLowerCase().contains(keyword) ||
                        flight.LocateEnd.toLowerCase().contains(keyword) ||
                        flight.Status.toLowerCase().contains(keyword) ||
                        String.valueOf(flight.Chairs).contains(keyword) ||
                        flight.datetimeStart.format(dt_format).toLowerCase().contains(keyword) ||
                        flight.datetimeEnd.format(dt_format).toLowerCase().contains(keyword)) {

                    System.out.printf(
                            "%-10s %-20s %-20s %-20s %-10s %-10d %-20s %-20s%n",
                            flight.ID,
                            flight.Airline_Name,
                            flight.LocateStart,
                            flight.LocateEnd,
                            flight.Status,
                            flight.Chairs,
                            flight.datetimeStart.format(dt_format),
                            flight.datetimeEnd.format(dt_format)
                    );
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No flights matched the keyword: " + keyword);
            }

            System.out.println("Do you want to search for another flight? (Y/N): ");
            String choice = sc.nextLine().trim().toUpperCase();
            searchAgain = choice.equals("Y");
        } while (searchAgain);

        System.out.println("Exiting search. Thank you!");
    }

}