import java.util.List;
import java.util.Scanner;

public class ChangeEmployeeInformation {
    private final EmployeeOperations employeeOps = new EmployeeUtils();
    Scanner scanner = new Scanner(System.in);

    public void execute(List<Employee> employees) {
        while (true) {
            showInfo(employeeOps);

            String id;
            Employee toUpdate;
            while (true) {
                id = inputIDWithValidation(employeeOps);
                if (id == null) {
                    System.out.println("Input cannot be null. Please try again.");
                    continue;
                }

                toUpdate = findEmployeeById(id, employees);
                if (toUpdate == null) {
                    System.out.println("Employee with ID: " + id + " not found.");
                    if (!performAnotherTry(employeeOps)) {
                        return;
                    }
                } else {
                    break;
                }
            }

            System.out.println("Updating information for employee with ID: " + id);

            String newId = updateIDWithValidation(employeeOps, employees);
            if(newId == null) return;

            String name = updateNotNullWithValidation(employeeOps, "name");
            if(name == null) return;

            String gender = updateNotNullWithValidation(employeeOps, "gender");
            if(gender == null) return;

            String position = updateNotNullWithValidation(employeeOps, "position");
            if(position == null) return;

            int age = updateAgeWithValidation();
            if(age == -1) return;

            String username = updateUsernameWithValidation(employeeOps, employees);
            if(username == null) return;

            String password = updateNotNullWithValidation(employeeOps, "password");
            if(password == null) return;

            updateToCSV(employeeOps, employees, toUpdate, newId, name, gender, position, age, username, password);

            if (!performAnotherChange(employeeOps)) {
                break;
            }
        }
    }

    public void showInfo(EmployeeOperations employeeOps) {
        employeeOps.showEmployeeInformation();
    }

    public String inputIDWithValidation(EmployeeOperations employeeOps) {
        return employeeOps.inputWithValidation("ID of the employee to update", scanner, input -> input != null && !input.trim().isEmpty());
    }

    private Employee findEmployeeById(String id, List<Employee> employees) {
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    public boolean performAnotherTry(EmployeeOperations employeeOps) {
        System.out.print("Do you want to try again? (yes/no): ");
        String choice = scanner.nextLine();
        return employeeOps.performAnotherAction(choice);
    }

    public String updateIDWithValidation(EmployeeOperations employeeOps, List<Employee> employees) {
        return employeeOps.inputWithValidation("new ID", scanner, input -> employeeOps.isValidId(input, employees));
    }

    public String updateNotNullWithValidation(EmployeeOperations employeeOps, String fieldName) {
        return employeeOps.inputWithValidation(fieldName, scanner, input -> !input.trim().isEmpty());
    }

    public int updateAgeWithValidation() {
        return employeeOps.inputIntegerWithValidation("new age", scanner);
    }

    public String updateUsernameWithValidation(EmployeeOperations employeeOps, List<Employee> employees) {
        return employeeOps.inputWithValidation("new username", scanner, input -> employeeOps.isValidUsername(input, employees));
    }

    public void updateToCSV(EmployeeOperations employeeOps, List<Employee> employees, Employee toUpdate, String id, String name, String gender, String position, int age, String username, String password) {
        toUpdate.setId(id);
        toUpdate.setName(name);
        toUpdate.setGender(gender);
        toUpdate.setPosition(position);
        toUpdate.setAge(age);
        toUpdate.setUsername(username);
        toUpdate.setPassword(password);
        System.out.println("Employee information updated successfully!");
        employeeOps.updateCSV(employees, "src/employee_data.csv");
    }

    public boolean performAnotherChange(EmployeeOperations employeeOps) {
        System.out.print("Do you want to update another employee? (yes/no): ");
        String choice = scanner.nextLine();
        return employeeOps.performAnotherAction(choice);
    }
}
