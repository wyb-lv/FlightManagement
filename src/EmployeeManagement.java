import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class EmployeeManagement {
    private List<Employee> employees;
    private Scanner scanner;

    public EmployeeManagement() {
        employees = new ArrayList<>();
        loadEmployeesFromFile();
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("===== Employee Management Menu =====");
        System.out.println("1. Add employee");
        System.out.println("2. Remove employee");
        System.out.println("3. Update employee information");
        System.out.println("4. Search for employee");
        System.out.println("5. Change employee access");
        System.out.println("6. Print all employee information");
        System.out.println("Choose an option (1-6, or any other key to exit): ");
    }

    public void run() {
        while (true) {
            showMenu();
            String choice = scanner.nextLine(); // Read choice as a string
            switch (choice) {
                case "1" -> new AddEmployeeInformation().execute(employees);
                case "2" -> new DeleteEmployeeInformation().execute(employees);
                case "3" -> new ChangeEmployeeInformation().execute(employees);
                case "4" -> new FindEmployeeInformation().execute();
                case "5" -> new ChangeEmployeeAccess().execute(employees);
                case "6" -> {
                    new ShowEmployeeInformation().execute();
                    System.out.println("Press any key to return to the menu...");
                    scanner.nextLine(); // Wait for user to press any key
                }
                default -> {
                    System.out.println("Exiting program.");
                    return;
                }
            }
        }
    }

    private void loadEmployeesFromFile() {
        String filePath = "src/employee_data.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) { // Include access level
                    Employee employee = new Employee(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5], parts[6], Employee.AccessLevel.valueOf(parts[7]));
                    employees.add(employee);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while loading employees from file.");
        }
    }
}
