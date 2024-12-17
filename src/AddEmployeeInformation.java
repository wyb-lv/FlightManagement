import java.util.List;
import java.util.Scanner;

public class AddEmployeeInformation {
    private final EmployeeOperations employeeOps = new EmployeeUtils();

    public void execute(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String id = employeeOps.inputWithValidation("ID", scanner, input -> employeeOps.isValidId(input, employees));
            if (id == null) return;

            String name = employeeOps.inputWithValidation("name", scanner, input -> !input.trim().isEmpty());
            if (name == null) return;

            String gender = employeeOps.inputWithValidation("gender", scanner, input -> !input.trim().isEmpty());
            if (gender == null) return;

            String position = employeeOps.inputWithValidation("position", scanner, input -> !input.trim().isEmpty());
            if (position == null) return;

            Integer age = employeeOps.inputIntegerWithValidation("age", scanner);
            if (age == null) return;

            String username = employeeOps.inputWithValidation("username", scanner, input -> employeeOps.isValidUsername(input, employees, null));
            if (username == null) return;

            String password = employeeOps.inputWithValidation("password", scanner, input -> !input.trim().isEmpty());
            if (password == null) return;

            // Create and add the new employee with default access level NONE
            Employee employee = new Employee(id, name, gender, position, age, username, password, Employee.AccessLevel.NONE);
            employees.add(employee);
            System.out.println("Employee added successfully with default access level NONE!");

            // Append to CSV file
            employeeOps.updateCSV(employees, "src/employee_data.csv");

            // Ask user if they want to perform another action
            System.out.print("Do you want to add another employee? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }
}
