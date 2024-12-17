import java.util.List;
import java.util.Scanner;

public class ChangeEmployeeInformation {
    private final EmployeeOperations employeeOps = new EmployeeUtils();

    public void execute(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        ShowEmployeeInformation showEmployeeInfo = new ShowEmployeeInformation();

        while (true) {
            showEmployeeInfo.execute();
            String id = employeeOps.inputWithValidation("ID of the employee to update", scanner, input -> employeeOps.isValidId(input, employees));
            if (id == null) return; // Return to the menu if ID input fails

            final Employee toUpdate = findEmployeeById(id, employees);
            if (toUpdate == null) {
                System.out.println("Employee with ID: " + id + " not found.");
                System.out.print("Do you want to try again? (yes/no): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("yes")) {
                    break; // Return to the menu if the user does not want to try again
                }
                continue;
            }

            System.out.println("Updating information for employee with ID: " + id);

            String name = employeeOps.inputWithValidation("new name", scanner, input -> !input.trim().isEmpty());
            if (name != null) toUpdate.setName(name);
            else return;

            String gender = employeeOps.inputWithValidation("new gender", scanner, input -> !input.trim().isEmpty());
            if (gender != null) toUpdate.setGender(gender);
            else return;

            String position = employeeOps.inputWithValidation("new position", scanner, input -> !input.trim().isEmpty());
            if (position != null) toUpdate.setPosition(position);
            else return;

            Integer age = employeeOps.inputIntegerWithValidation("new age", scanner);
            if (age != null) toUpdate.setAge(age);
            else return;

            String username = employeeOps.inputWithValidation("new username", scanner, input -> employeeOps.isValidUsername(input, employees, toUpdate));
            if (username != null) toUpdate.setUsername(username);
            else return;

            String password = employeeOps.inputWithValidation("new password", scanner, input -> !input.trim().isEmpty());
            if (password != null) toUpdate.setPassword(password);
            else return;

            System.out.println("Employee information updated successfully!");
            employeeOps.updateCSV(employees, "src/employee_data.csv");

            System.out.print("Do you want to update another employee? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break; // Return to the menu if the user does not want to update another employee
            }
        }
    }

    private Employee findEmployeeById(String id, List<Employee> employees) {
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }
}
