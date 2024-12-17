//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Scanner;
//import java.util.Set;
//
//public class FindEmployeeInformation {
//    public void execute() {
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.print("Enter a value to search for: ");
//            String searchValue = scanner.nextLine();
//
//            String filePath = "src/employee_data.csv";
//            Set<String> foundLines = new HashSet<>();
//
//            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    if (line.contains(searchValue)) {
//                        foundLines.add(line);
//                    }
//                }
//            } catch (IOException e) {
//                System.out.println("Error while reading the file.");
//            }
//
//            if (foundLines.isEmpty()) {
//                System.out.println("No matching records found.");
//            } else {
//                System.out.println("Matching records:");
//                for (String foundLine : foundLines) {
//                    System.out.println(foundLine);
//                }
//            }
//
//            // Ask user if they want to perform another action
//            System.out.print("Do you want to search for another value? (yes/no): ");
//            String choice = scanner.nextLine();
//            if (!choice.equalsIgnoreCase("yes")) {
//                break;
//            }
//        }
//    }
//}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FindEmployeeInformation {
    private static final int MAX_ATTEMPTS = 3;

    public void execute() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String searchValue = inputWithValidation("value to search for", scanner, input -> !input.trim().isEmpty());
            if (searchValue == null) return;

            String filePath = "src/employee_data.csv";
            Set<String> foundLines = searchInCSV(filePath, searchValue);

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
}
