import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FindEmployeeInformation {
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter a value to search for: ");
            String searchValue = scanner.nextLine();

            String filePath = "src/employee_data.csv";
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

            if (foundLines.isEmpty()) {
                System.out.println("No matching records found.");
            } else {
                System.out.println("Matching records:");
                for (String foundLine : foundLines) {
                    System.out.println(foundLine);
                }
            }

            // Ask user if they want to perform another action
            System.out.print("Do you want to search for another value? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }
}
