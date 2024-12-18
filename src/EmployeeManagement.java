import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;

public class EmployeeManagement {
    private final EmployeeOperations employeeOps = new EmployeeUtils();
    private List<Employee> employees = new ArrayList<>();
    private Scanner scanner;

    public void run() {
        setUp();
        while (true) {
            showMenu();
            switch (inputOption()) {
                case "1" -> addEmployeeInformation(employees);
                case "2" -> deleteEmployeeInformation(employees);
                case "3" -> changeEmployeeInformation(employees);
                case "4" -> findEmployeeInformation();
                case "5" -> changeEmployeeAccess(employees);
                case "6" -> {
                    showEmployeeInfo(employeeOps);
                    inputExitOpion();
                }
                default -> {
                    System.out.println("Exiting program.");
                    return;
                }
            }
        }
    }

    public void setUp() {
        loadEmployeesFromFile();
        scanner = new Scanner(System.in);
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

    public String inputOption() {
        return scanner.nextLine(); // Read choice as a string
    }

    public String inputExitOpion() {
        System.out.println("Press any key to return to the menu...");
        return scanner.nextLine();
    }

    public void addEmployeeInformation(List<Employee> employees) {
        new AddEmployeeInformation().execute(employees);
    }
    public void deleteEmployeeInformation(List<Employee> employees) {
        new DeleteEmployeeInformation().execute(employees);
    }
    public void changeEmployeeInformation(List<Employee> employees) {
        new ChangeEmployeeInformation().execute(employees);
    }
    public void findEmployeeInformation() {
        new FindEmployeeInformation().execute();
    }
    public void changeEmployeeAccess(List<Employee> employees) {
        new ChangeEmployeeAccess().execute(employees);
    }
    public void showEmployeeInfo(EmployeeOperations employeeOps) {
        employeeOps.showEmployeeInformation();
    }
}

