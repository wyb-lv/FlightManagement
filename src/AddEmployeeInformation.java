import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AddEmployeeInformation {
    private static final int MAX_ATTEMPTS = 3;

    public void execute(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String id = null;
            String name = null;
            String gender = null;
            String position = null;
            int age = 0;
            String username = null;
            String password = null;

            // Check ID
            for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                System.out.print("Enter ID: ");
                id = scanner.nextLine();
                if (isValidId(id, employees)) {
                    break;
                } else {
                    System.out.println("Invalid or duplicate ID. Try again.");
                }
                if (attempts == MAX_ATTEMPTS - 1) {
                    System.out.println("Max attempts reached. Returning to menu.");
                    return;
                }
            }

            // Check Name
            for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                System.out.print("Enter name: ");
                name = scanner.nextLine();
                if (!name.isEmpty()) {
                    break;
                } else {
                    System.out.println("Name cannot be empty. Try again.");
                }
                if (attempts == MAX_ATTEMPTS - 1) {
                    System.out.println("Max attempts reached. Returning to menu.");
                    return;
                }
            }

            // Check Gender
            for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                System.out.print("Enter gender: ");
                gender = scanner.nextLine();
                if (!gender.isEmpty()) {
                    break;
                } else {
                    System.out.println("Gender cannot be empty. Try again.");
                }
                if (attempts == MAX_ATTEMPTS - 1) {
                    System.out.println("Max attempts reached. Returning to menu.");
                    return;
                }
            }

            // Check Position
            for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                System.out.print("Enter position: ");
                position = scanner.nextLine();
                if (!position.isEmpty()) {
                    break;
                } else {
                    System.out.println("Position cannot be empty. Try again.");
                }
                if (attempts == MAX_ATTEMPTS - 1) {
                    System.out.println("Max attempts reached. Returning to menu.");
                    return;
                }
            }

            // Check Age
            for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                System.out.print("Enter age: ");
                if (scanner.hasNextInt()) {
                    age = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    break;
                } else {
                    System.out.println("Invalid age. Enter a valid age.");
                    scanner.next(); // consume invalid input
                }
                if (attempts == MAX_ATTEMPTS - 1) {
                    System.out.println("Max attempts reached. Returning to menu.");
                    return;
                }
            }

            // Check Username
            for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
                System.out.print("Enter username: ");
                username = scanner.nextLine();
                if (isValidUsername(username, employees)) {
                    break;
                } else {
                    System.out.println("Duplicate or empty username. Try again.");
                }
                if (attempts == MAX_ATTEMPTS - 1) {
                    System.out.println("Max attempts reached. Returning to menu.");
                    return;
                }
            }

            // Check Password
            while (true) {
                System.out.print("Enter password: ");
                password = scanner.nextLine();
                if (!password.isEmpty()) {
                    break;
                } else {
                    System.out.println("Password cannot be empty. Try again.");
                }
            }

            // Create and add the new employee
            Employee employee = new Employee(id, name, gender, position, age, username, password);
            employees.add(employee);
            System.out.println("Employee added successfully!");

            // Append to CSV file
            appendToCSV(employee);

            // Ask user if they want to perform another action
            System.out.print("Do you want to add another employee? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

    private boolean isValidId(String id, List<Employee> employees) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidUsername(String username, List<Employee> employees) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        for (Employee emp : employees) {
            if (emp.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    private void appendToCSV(Employee employee) {
        String filePath = "src/employee_data.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(employee.getId() + "," + employee.getName() + "," + employee.getGender() + "," + employee.getPosition() + "," + employee.getAge() + "," + employee.getUsername() + "," + employee.getPassword() + "\n");
        } catch (IOException e) {
            System.out.println("Error while writing to the file.");
        }
    }
}
