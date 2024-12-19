import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FindEmployeeInformation {
    private final EmployeeOperations employeeOps = new EmployeeUtils();
    Scanner scanner = new Scanner(System.in);

    public void execute() {
        while (true) {
            String searchValue = inputValueWithValidation(employeeOps);
            if (searchValue == null) return;

            String filePath = "src/employee_data.csv";
            Set<String> foundLines = searchInCSV(filePath, searchValue);

            checkFoundLinesEmpty(foundLines);

            // Ask user if they want to perform another action
            if(!performAnotherFind(employeeOps)) break;
        }
    }

    public String inputValueWithValidation(EmployeeOperations employeeOps) {
        return employeeOps.inputWithValidation("value to search for", scanner, input -> !input.trim().isEmpty());
    }

    private Set<String> searchInCSV(String filePath, String searchValue) {
        Set<String> foundLines = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchValue)) {
                    foundLines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
        }
        return foundLines;
    }

    public void checkFoundLinesEmpty(Set<String> foundLines) {
        if (foundLines.isEmpty()) {
            System.out.println("No matching records found.");
        } else {
            System.out.println("Matching records:");
            for (String foundLine : foundLines) {
                displayFormattedLine(foundLine);
            }
        }
    }

    private void displayFormattedLine(String line) {
        String[] parts = line.split(",");
        if (parts.length == 8) { // Ensure the line has exactly 7 parts
            System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Gender: " + parts[2] + ", Position: " + parts[3] + ", Age: " + parts[4] + ", Username: " + parts[5] + ", Password: " + parts[6] + ", Access: " + parts[7]);
        } else {
            System.out.println("Invalid line    format: " + line);
        }
    }

    public boolean performAnotherFind(EmployeeOperations employeeOps) {
        System.out.print("Do you want to search for another value? (yes/no): ");
        String choice = scanner.nextLine();
        return employeeOps.performAnotherAction(choice);
    }
}
