import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
    private EmployeeOperations employeeOps = new EmployeeUtils();
    private List<Employee> employees = new ArrayList<>();

    public EmployeeManagement() {
        loadEmployeesFromFile("src/employee_data.csv");
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            switch (scanner.nextLine()) {
                case "1" -> addEmployeeInformation(employees,employeeOps);
                case "2" -> deleteEmployeeInformation(employees,employeeOps);
                case "3" -> changeEmployeeInformation(employees,employeeOps);
                case "4" -> findEmployeeInformation(employeeOps);
                case "5" -> changeEmployeeAccess(employees,employeeOps);
                case "6" -> {
                    showEmployeeInfo(employeeOps);
                    System.out.println("Press any key to return to the menu...");
                    scanner.nextLine();
                }
                default -> {
                    System.out.println("Exiting employee management.");
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
            return;
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

    public void addEmployeeInformation(List<Employee> employees, EmployeeOperations employeeOps) {
        new AddEmployeeInformation().execute(employees, this.employeeOps);
    }
    public void deleteEmployeeInformation(List<Employee> employees, EmployeeOperations employeeOps) {
        new DeleteEmployeeInformation().execute(employees, this.employeeOps);
    }
    public void changeEmployeeInformation(List<Employee> employees, EmployeeOperations employeeOps) {
        new ChangeEmployeeInformation().execute(employees, this.employeeOps);
    }
    public void findEmployeeInformation(EmployeeOperations employeeOps) {
        new FindEmployeeInformation().execute(this.employeeOps);
    }
    public void changeEmployeeAccess(List<Employee> employees, EmployeeOperations employeeOps) {
        new ChangeEmployeeAccess().execute(employees,this.employeeOps);
    }
    public void showEmployeeInfo(EmployeeOperations employeeOps) {
        employeeOps.showEmployeeInformation();
    }
}

