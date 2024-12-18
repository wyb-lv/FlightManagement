import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class EmployeeUtils implements EmployeeOperations {
    private static final int MAX_ATTEMPTS = 3;
    private ShowEmployeeInformation s = new ShowEmployeeInformation();

    @Override
    public String inputWithValidation(String field, Scanner scanner, Predicate<String> validator) {
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

    @Override
    public Integer inputIntegerWithValidation(String field, Scanner scanner) {
        for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
            System.out.print("Enter " + field + ": ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                return input;
            } else {
                System.out.println("Invalid " + field + ". Enter a valid integer.");
                scanner.next(); // consume invalid input
            }
            if (attempts == MAX_ATTEMPTS - 1) {
                System.out.println("Max attempts reached. Returning to menu.");
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean isValidId(String id, List<Employee> employees) {
        return id != null && !id.trim().isEmpty();
    }

    @Override
    public boolean isValidUsername(String username, List<Employee> employees, Employee currentEmployee) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        for (Employee emp : employees) {
            if (!emp.equals(currentEmployee) && emp.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateCSV(List<Employee> employees, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getGender() + "," + employee.getPosition() + "," + employee.getAge() + "," + employee.getUsername() + "," + employee.getPassword() + "," + employee.getAccess() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error while writing to the file.");
        }
    }

    @Override
    public void checkStringIfNull(String value) {
        if(value == null) return;
    }

    @Override
    public void checkIntegerIfNull(Integer value) {
        if (value == null) return;
    }

    @Override
    public boolean performAnotherAction(String choice) {
        if (!choice.equalsIgnoreCase("yes")) {
            return false;
        } else return true;
    }

    @Override
    public void showEmployeeInformation() {
        s.execute();
    }
}