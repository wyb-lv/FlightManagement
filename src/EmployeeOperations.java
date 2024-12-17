import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public interface EmployeeOperations {
    String inputWithValidation(String field, Scanner scanner, Predicate<String> validator);
    Integer inputIntegerWithValidation(String field, Scanner scanner);
    void checkStringIfNull(String value);
    void checkIntegerIfNull(Integer value);

    boolean isValidId(String id, List<Employee> employees);
    boolean isValidUsername(String username, List<Employee> employees, Employee currentEmployee);

    void updateCSV(List<Employee> employees, String filePath);

    boolean performAnotherAction(String choice);
    void showEmployeeInformation();
}
