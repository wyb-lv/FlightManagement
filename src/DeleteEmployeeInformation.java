//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//
//public class DeleteEmployeeInformation {
//    public void execute(List<Employee> employees) {
//        Scanner scanner = new Scanner(System.in);
//        ShowEmployeeInformation showEmployeeInfo = new ShowEmployeeInformation();
//        while (true) {
//            showEmployeeInfo.execute();
//            System.out.print("Enter the ID of the employee to remove: ");
//            String id = scanner.nextLine();
//            Employee toRemove = null;
//
//            for (Employee emp : employees) {
//                if (emp.getId().equals(id)) {
//                    toRemove = emp;
//                    break;
//                }
//            }
//
//            if (toRemove != null) {
//                employees.remove(toRemove);
//                System.out.println("Employee removed successfully!");
//                updateCSV(employees);
//            } else {
//                System.out.println("Employee with ID: " + id + " not found.");
//            }
//
//            // Ask user if they want to perform another action
//            System.out.print("Do you want to remove another employee? (yes/no): ");
//            String choice = scanner.nextLine();
//            if (!choice.equalsIgnoreCase("yes")) {
//                break;
//            }
//        }
//    }
//
//    private void updateCSV(List<Employee> employees) {
//        String filePath = "src/employee_data.csv";
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            for (Employee employee : employees) {
//                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getGender() + "," + employee.getPosition() + "," + employee.getAge() + "," + employee.getUsername() + "," + employee.getPassword() + "\n");
//            }
//        } catch (IOException e) {
//            System.out.println("Error while writing to the file.");
//        }
//    }
//}
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class DeleteEmployeeInformation {
    private static final int MAX_ATTEMPTS = 3;

    public void execute(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        ShowEmployeeInformation showEmployeeInfo = new ShowEmployeeInformation();

        while (true) {
            showEmployeeInfo.execute();
            String id = inputWithValidation("ID of the employee to remove", scanner, input -> isValidId(input, employees));
            if (id == null) return;

            Employee toRemove = null;
            for (Employee emp : employees) {
                if (emp.getId().equals(id)) {
                    toRemove = emp;
                    break;
                }
            }

            if (toRemove != null) {
                employees.remove(toRemove);
                System.out.println("Employee removed successfully!");
                updateCSV(employees);
            } else {
                System.out.println("Employee with ID: " + id + " not found.");
            }

            // Ask user if they want to perform another action
            System.out.print("Do you want to remove another employee? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

    private String inputWithValidation(String field, Scanner scanner, java.util.function.Predicate<String> validator) {
        for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
            System.out.print("Enter " + field + ": ");
            String input = scanner.nextLine();
            if (validator.test(input)) {
                return input;
            } else {
                System.out.println("Invalid " + field + ". Try again.");
            }
            if (attempts == MAX_ATTEMPTS - 1) {
                System.out.println("Max attempts reached. Returning to menu.");
                return null;
            }
        }
        return null;
    }

    private boolean isValidId(String id, List<Employee> employees) {
        return id != null && !id.trim().isEmpty();
    }

    private void updateCSV(List<Employee> employees) {
        String filePath = "src/employee_data.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getGender() + "," + employee.getPosition() + "," + employee.getAge() + "," + employee.getUsername() + "," + employee.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error while writing to the file.");
        }
    }
}
