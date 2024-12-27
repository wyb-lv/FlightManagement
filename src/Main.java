import java.util.Scanner;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeManagement employeeManagement = new EmployeeManagement();
        FlightManagement flightManagement = new FlightManagement();
        AircrewMenu aircrewManagement = new AircrewMenu();
        PassengerMenu passengerManagement = new PassengerMenu();
        StatisticMenu statistic = new StatisticMenu();
        LowestLevelAccessManagement lowestLevelAccessManagement = new LowestLevelAccessManagement();
        Scanner scanner = new Scanner(System.in);

        int failedAttempts = 0;
        final int MAX_FAILED_ATTEMPTS = 5;
        Employee loggedInEmployee = null;

        while (loggedInEmployee == null && failedAttempts < MAX_FAILED_ATTEMPTS) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            loggedInEmployee = authenticate(username, password, employeeManagement.getEmployees());

            if (loggedInEmployee != null) {
                System.out.println("Login successful! Welcome, " + username + "!");
                executeFunctionsBasedOnAccessLevel(loggedInEmployee, employeeManagement, flightManagement, aircrewManagement,passengerManagement, statistic, lowestLevelAccessManagement);
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

    public static  Employee authenticate(String username, String password, List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }

    public static void executeFunctionsBasedOnAccessLevel(Employee employee, EmployeeManagement employeeManagement, FlightManagement flightManagement, AircrewMenu aircrewManagement, PassengerMenu passengerManagement, StatisticMenu statistic, LowestLevelAccessManagement lowestLevelAccessManagement) {
        Scanner scanner = new Scanner(System.in);
        switch (employee.getAccess()) {
            case ADMIN -> {
                while (true) {
                    adminMenu();
                    switch (scanner.nextLine()) {
                        case "1" -> employeeManagement.run(employee);
                        case "2" -> flightManagement.run();
                        case "3" -> aircrewManagement.run();
                        case "4" -> passengerManagement.run();
                        case "5" -> statistic.run(employeeManagement.getEmployees());
                        default -> {
                            System.out.println("Exiting program.");
                            return;
                        }
                    }
                }
            }
            case EMPLOYEE_MANAGER -> {
                while (true) {
                    employeeManagerMenu();
                    switch (scanner.nextLine()) {
                        case "1" -> employeeManagement.run(employee);
                        case "2" -> aircrewManagement.run();
                        default -> {
                            System.out.println("Exiting program.");
                            return;
                        }
                    }
                }
            }
            case FLIGHT_MANAGER -> {
                while (true) {
                    flightManagerMenu();
                    switch (scanner.nextLine()) {
                        case "1" -> flightManagement.run();
                        case "2" -> passengerManagement.run();
                        default -> {
                            System.out.println("Exiting program.");
                            return;
                        }
                    }
                }
            }
            case NONE -> {
                lowestLevelAccessManagement.run();
            }
        }
    }

    public static void adminMenu(){
        System.out.println("Access granted to admin functions.");
        System.out.println("1. Employee management");
        System.out.println("2. Flight management");
        System.out.println("3. Aircrew management");
        System.out.println("4. Passenger management");
        System.out.println("5. Statistic");
        System.out.println("Choose an option (1-4, or any other key to exit): ");
    }

    public static void employeeManagerMenu(){
        System.out.println("Access granted to employee manager functions.");
        System.out.println("1. Employee management");
        System.out.println("2. Aircrew management");
    }

    public static void flightManagerMenu(){
        System.out.println("Access granted to flight manager functions.");
        System.out.println("1. Flight management");
        System.out.println("2. Passenger management");
    }
}

