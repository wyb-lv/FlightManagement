import java.util.List;
import java.util.Scanner;

public class DeleteEmployeeInformation {
    private final EmployeeOperations employeeOps = new EmployeeUtils();

    public void execute(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        ShowEmployeeInformation showEmployeeInfo = new ShowEmployeeInformation();

        while (true) {
            showEmployeeInfo.execute();
            String id = employeeOps.inputWithValidation("ID of the employee to remove", scanner, input -> employeeOps.isValidId(input, employees));
            if (id == null) return;

            Employee toRemove = findEmployeeById(id, employees);
            if (toRemove != null) {
                employees.remove(toRemove);
                System.out.println("Employee removed successfully!");
                employeeOps.updateCSV(employees, "src/employee_data.csv");
            } else {
                System.out.println("Employee with ID: " + id + " not found.");
            }

            System.out.print("Do you want to remove another employee? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
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
