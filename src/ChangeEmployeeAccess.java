import java.util.List;
import java.util.Scanner;

public class ChangeEmployeeAccess {
    private final EmployeeOperations employeeOps = new EmployeeUtils();

    public void execute(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);

        Employee currentUser = null;
        for (int attempts = 0; attempts < 3; attempts++) {
            System.out.print("Enter your employee ID: ");
            String id = scanner.nextLine();
            currentUser = findEmployeeById(id, employees);

            if (currentUser != null) {
                break;
            } else {
                System.out.println("User not found. Try again.");
            }
        }

        if (currentUser == null) {
            System.out.println("Max attempts reached. Returning to menu.");
            return;
        }

        if (currentUser.getAccess() == Employee.AccessLevel.NONE || currentUser.getAccess() == Employee.AccessLevel.FLIGHT_MANAGER) {
            System.out.println("You do not have permission to change access levels.");
            return;
        }

        while (true) {
            System.out.print("Enter the ID of the employee to change access: ");
            String targetId = scanner.nextLine();
            Employee targetEmployee = findEmployeeById(targetId, employees);

            if (targetEmployee == null) {
                System.out.println("Target user not found.");
                continue; // Prompt again for a valid target employee ID
            }

            System.out.print("Enter new access level (ADMIN, EMPLOYEE_MANAGER, FLIGHT_MANAGER, NONE): ");
            String newAccessStr = scanner.nextLine();
            Employee.AccessLevel newAccess;
            try {
                newAccess = Employee.AccessLevel.valueOf(newAccessStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid access level.");
                continue; // Prompt again for a valid access level
            }

            if (canChangeAccess(currentUser.getAccess(), targetEmployee.getAccess(), newAccess)) {
                targetEmployee.setAccess(newAccess);
                System.out.println("Access level changed successfully.");
                updateCSV(employees);
            } else {
                System.out.println("You do not have permission to assign this access level.");
            }

            System.out.print("Do you want to change access for another employee? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break; // Return to the menu if the user does not want to continue
            }
        }
    }

    private boolean canChangeAccess(Employee.AccessLevel currentAccess, Employee.AccessLevel targetAccess, Employee.AccessLevel newAccess) {
        return switch (currentAccess) {
            case ADMIN -> true; // Admin can assign any access level
            case EMPLOYEE_MANAGER -> (targetAccess != Employee.AccessLevel.ADMIN) && (newAccess == Employee.AccessLevel.EMPLOYEE_MANAGER || newAccess == Employee.AccessLevel.FLIGHT_MANAGER || newAccess == Employee.AccessLevel.NONE);
            case NONE, FLIGHT_MANAGER -> false; // None and Flight Manager cannot assign any access level
        };
    }

    private Employee findEmployeeById(String id, List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    private void updateCSV(List<Employee> employees) {
        employeeOps.updateCSV(employees, "src/employee_data.csv");
    }
}
