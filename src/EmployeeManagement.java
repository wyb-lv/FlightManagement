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
//        if(!loadEmployeesFromFile())
//            throw new NullPointerException("Must provide an engine to our car");;
        scanner = new Scanner(System.in);
    }

    public void showMenu() {
        System.out.println("===== Employee Management Menu =====");
        System.out.println("1. Add employee");
        System.out.println("2. Remove employee");
        System.out.println("3. Update employee information");
        System.out.println("4. Search for employee");
        System.out.println("5. Print all employee information");
        System.out.println("Choose an option (1-5, or any other key to exit): ");
    }

    public void run() {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    new AddEmployeeInformation().execute(employees);
                    break;
                case 2:
                    new DeleteEmployeeInformation().execute(employees);
                    break;
                case 3:
                    new ChangeEmployeeInformation().execute(employees);
                    break;
                case 4:
                    new FindEmployeeInformation().execute();
                    break;
                case 5:
                    new ShowEmployeeInformation().execute();
                    System.out.println("Press any key to return to the menu...");
                    scanner.nextLine(); // Wait for user to press any key
                    break;
                default:
                    System.out.println("Exiting program.");
                    return;
            }
        }
    }

    private void loadEmployeesFromFile() {
        String filePath = "src/employee_data.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((reader.readLine()) != null) {
                String line = reader.readLine();
                String[] parts = line.split(",");
                Employee employee = new Employee(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5], parts[6]);
                employees.add(employee);
            }
        } catch (IOException e) {
            System.out.println("Error while loading employees from file.");
        }
    }
}
