import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Login {
    EmployeeManagement management = new EmployeeManagement();
    private List<Employee> employees;

    public Login(String filePath) {
        employees = new ArrayList<>();
        loadEmployeesFromFile(filePath);
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

    public void startLogin() {
        Scanner scanner = new Scanner(System.in);
        Employee loggedInEmployee = null;
        int failedAttempts = 0;
        final int MAX_FAILED_ATTEMPTS = 5;

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

    private void executeFunctionsBasedOnAccessLevel(Employee employee) {
        switch (employee.getAccess()) {
            case ADMIN -> {
                System.out.println("Access granted to admin functions.");
            }
            case EMPLOYEE_MANAGER -> {
                System.out.println("Access granted to employee manager functions.");
                management.run();
            }
            case FLIGHT_MANAGER -> {
                System.out.println("Access granted to flight manager functions.");
            }
            default -> {
                System.out.println("Access granted to general employee functions.");
            }
        }
    }
}
