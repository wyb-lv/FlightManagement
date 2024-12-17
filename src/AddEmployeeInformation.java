import java.util.List;
import java.util.Scanner;

public class AddEmployeeInformation {
    private final EmployeeOperations employeeOps = new EmployeeUtils();
    Scanner scanner = new Scanner(System.in);

    public void execute(List<Employee> employees) {
        while (true) {
            String id = inputIDWithValidation(employeeOps, employees);
            if(id == null) return;

            String name = inputNameWithValidation(employeeOps);
            if(name == null) return;

            String gender = inputGenderWithValidation(employeeOps);
            if(gender == null) return;

            String position = inputPositionWithValidation(employeeOps);
            if(position == null) return;

            int age = inputAgeWithValidation();
            if(age == -1) return;

            String username = inputUsernameWithValidation(employeeOps, employees);
            if(username == null) return;

            String password = inputPasswordWithValidation(employeeOps);
            if(password == null) return;

            // Create and add the new employee with default access level NONE
            createAndAddToEmployees(id, name, gender, position, age, username, password, employees);

            // Append to CSV file
            appendToCSV(employeeOps, employees);

            // Ask user if they want to perform another action
            if(!performAnotherAdd(employeeOps))
                break;
        }
    }

    public String inputIDWithValidation(EmployeeOperations employeeOps, List<Employee> employees){
        String id;
        employeeOps.checkStringIfNull(id = employeeOps.inputWithValidation("ID", scanner, input -> employeeOps.isValidId(input, employees)));
        return id;
    }
    public String inputNameWithValidation(EmployeeOperations employeeOps){
        String name;
        employeeOps.checkStringIfNull(name = employeeOps.inputWithValidation("name", scanner, input -> !input.trim().isEmpty()));
        return name;
    }
    public String inputGenderWithValidation(EmployeeOperations employeeOps){
        String gender;
        employeeOps.checkStringIfNull(gender = employeeOps.inputWithValidation("gender", scanner, input -> !input.trim().isEmpty()));
        return gender;
    }
    public String inputPositionWithValidation(EmployeeOperations employeeOps){
        String position;
        employeeOps.checkStringIfNull(position = employeeOps.inputWithValidation("position", scanner, input -> !input.trim().isEmpty()));
        return position;
    }
    public int inputAgeWithValidation(){
        Integer age;
        employeeOps.checkIntegerIfNull(age = employeeOps.inputIntegerWithValidation("age", scanner));
        return age;
    }
    public String inputUsernameWithValidation(EmployeeOperations employeeOps, List<Employee> employees){
        String username;
        employeeOps.checkStringIfNull(username = employeeOps.inputWithValidation("username", scanner, input -> employeeOps.isValidUsername(input, employees, null)));
        return username;
    }
    public String inputPasswordWithValidation(EmployeeOperations employeeOps){
        String password;
        employeeOps.checkStringIfNull(password = employeeOps.inputWithValidation("password", scanner, input -> !input.trim().isEmpty()));
        return password;
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
