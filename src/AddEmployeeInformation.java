import java.util.List;
import java.util.Scanner;

public class AddEmployeeInformation {
    Scanner scanner = new Scanner(System.in);

    public void execute(List<Employee> employees,EmployeeOperations employeeOps) {
        while (true) {
            String id = inputIDWithValidation(employeeOps, employees);
            if(id == null) return;

            String name = inputNotNullWithValidation(employeeOps, "name");
            if(name == null) return;

            String gender = inputNotNullWithValidation(employeeOps, "gender");
            if(gender == null) return;

            String position = inputNotNullWithValidation(employeeOps, "position");
            if(position == null) return;

            int age = inputAgeWithValidation(employeeOps);
            if(age == -1) return;

            String username = inputUsernameWithValidation(employeeOps, employees);
            if(username == null) return;

            String password = inputNotNullWithValidation(employeeOps, "password");
            if(password == null) return;

            // Create and add the new employee with default access level NONE
            createAndAddToEmployees(id, name, gender, position, age, username, password, employees);

            // Append to CSV file
            appendToCSV(employeeOps, employees);

            // Ask user if they want to perform another action
            if(!performAnotherAdd(employeeOps)) break;
        }
    }

    public String inputIDWithValidation(EmployeeOperations employeeOps, List<Employee> employees){
        return employeeOps.inputWithValidation("ID", scanner, input -> employeeOps.isValidId(input, employees));
    }

    public String inputNotNullWithValidation(EmployeeOperations employeeOps, String fieldName){
        return employeeOps.inputWithValidation(fieldName, scanner, input -> !input.trim().isEmpty());
    }

    public int inputAgeWithValidation(EmployeeOperations employeeOps){
        return employeeOps.inputIntegerWithValidation("age", scanner);
    }

    public String inputUsernameWithValidation(EmployeeOperations employeeOps, List<Employee> employees){
        return employeeOps.inputWithValidation("username", scanner, input -> employeeOps.isValidUsername(input, employees));
    }

    public void createAndAddToEmployees(String id, String name, String gender, String position, int age, String username, String password, List<Employee> employees) {
        Employee employee = new Employee(id, name, gender, position, age, username, password, Employee.AccessLevel.NONE);
        employees.add(employee);
        System.out.println("Employee added successfully with default access level NONE!");
    }

    public void appendToCSV(EmployeeOperations employeeOps, List<Employee> employees) {
        employeeOps.updateCSV(employees, "src/employee_data.csv");
    }

    public boolean performAnotherAdd(EmployeeOperations employeeOps) {
        System.out.print("Do you want to add another employee? (yes/no): ");
        String choice = scanner.nextLine();
        return employeeOps.performAnotherAction(choice);
    }
}
