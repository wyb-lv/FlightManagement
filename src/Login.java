import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Login {
    EmployeeManagement employeeManagement = new EmployeeManagement();
//    FlightManagement flightManagement = new FlightManagement();
//    AircrewManagement aircrewManagement = new AircrewManagement();
    PassengerManagement passengerManagement = new PassengerManagement();

    private List<Employee> employees = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void startLogin(String filePath) {
        loadEmployeesFromFile(filePath);
        checkAtemps();
    }

    public void checkAtemps() {
        int failedAttempts = 0;
        final int MAX_FAILED_ATTEMPTS = 5;
        Employee loggedInEmployee = null;

        while (loggedInEmployee == null && failedAttempts < MAX_FAILED_ATTEMPTS) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            loggedInEmployee = authenticate(username, password);

            if (loggedInEmployee != null) {
                System.out.println("Login successful! Welcome, " + username + "!");
                executeFunctionsBasedOnAccessLevel(loggedInEmployee);
            } else {
                failedAttempts++;
                if (failedAttempts < MAX_FAILED_ATTEMPTS) {
                    System.out.println("Login failed! Please try again. Attempts left: " + (MAX_FAILED_ATTEMPTS - failedAttempts));
                } else {
                    System.out.println("Login failed! Maximum attempts reached. Exiting program.");
                    return;
                }
            }
        }
    }

    private void loadEmployeesFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    Employee employee = new Employee(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5], parts[6], Employee.AccessLevel.valueOf(parts[7]));
                    employees.add(employee);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
        }
    }

    public Employee authenticate(String username, String password) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }

    private void executeFunctionsBasedOnAccessLevel(Employee employee) {
            switch (employee.getAccess()) {
            case ADMIN -> {
                while (true) {
                    adminMenu();
                    switch (scanner.nextLine()) {
                        case "1" -> employeeManagement.run(employees);
//                        case "2" -> flightManagement.run();
//                        case "3" -> aircrewManagement.run(employees);
                        case "4" -> passengerManagement.run();
                        default -> {
                            System.out.println("Exiting program.");
                        }
                    }
                }
            }
            case EMPLOYEE_MANAGER -> {
                while (true) {
                    employeeManagerMenu();
                    switch (scanner.nextLine()) {
                        case "1" -> employeeManagement.run(employees);
//                        case "2" -> aircrewManagement.run(employees);
                        default -> {
                            System.out.println("Exiting program.");
                        }
                    }
                }
            }
            case FLIGHT_MANAGER -> {
                while (true) {
                    flightManagerMenu();
                    switch (scanner.nextLine()) {
//                        case "1" -> flightManagement.run();
                        case "2" -> passengerManagement.run();
                        default -> {
                            System.out.println("Exiting program.");
                        }
                    }
                }
            }
            case NONE -> {
                while (true) {
                    noneMenu();
                    switch (scanner.nextLine()) {
                        case "1" -> System.out.println("Display flight list");
                        case "2" -> System.out.println("Search flight information");
                        case "3" -> System.out.println("Display aircrew information");
                        case "4" -> System.out.println("Search aircrew information");
                        default -> {
                            System.out.println("Exiting program.");
                        }
                    }
                }
            }
        }
    }

    public void adminMenu(){
        System.out.println("Access granted to admin functions.");
        System.out.println("1. Employee management");
        System.out.println("2. Flight management");
        System.out.println("3. Aircrew management");
        System.out.println("4. Passenger management");
        System.out.println("Choose an option (1-4, or any other key to exit): ");
    }

    public void employeeManagerMenu(){
        System.out.println("Access granted to employee manager functions.");
        System.out.println("1. Employee management");
        System.out.println("2. Aircrew management");
    }

    public void flightManagerMenu(){
        System.out.println("Access granted to flight manager functions.");
        System.out.println("1. Flight management");
        System.out.println("2. Passenger management");
    }

    public void noneMenu(){
        System.out.println("Access granted to general employee functions.");
        System.out.println("1. Display flight list");
        System.out.println("2. Search flight information");
        System.out.println("3. Display aircrew information");
        System.out.println("4. Search aircrew information");
    }


}


