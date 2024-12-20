import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
    private final EmployeeOperations employeeOps = new EmployeeUtils();
    private Scanner scanner = new Scanner(System.in);

    public void run(List<Employee> employees) {
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
                    System.out.println("Press any key to return to the menu...");
                    inputOption();
                }
                default -> {
                    System.out.println("Exiting employee management.");
                    return;
                }
            }
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

