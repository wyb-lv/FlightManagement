import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public interface EmployeeOperations {
    String inputWithValidation(String field, Scanner scanner, Predicate<String> validator);
    Integer inputIntegerWithValidation(String field, Scanner scanner);

    boolean isValidId(String id, List<Employee> employees);
    boolean isValidUsername(String username, List<Employee> employees);

    void updateCSV(List<Employee> employees, String filePath);

    boolean performAnotherAction(String choice);
    void showEmployeeInformation();
}
