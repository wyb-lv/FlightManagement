//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Scanner;
//
//public class ShowEmployeeInformation {
//    public void execute() {
//        String filePath = "src/employee_data.csv";
//        Scanner scanner = new Scanner(System.in);
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            System.out.println("All employees information:");
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split(",");
//                System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Gender: " + parts[2] + ", Position: " + parts[3] + ", Age: " + parts[4] + ", Username: " + parts[5] + ", Password: " + parts[6]);
//            }
//        } catch (IOException e) {
//            System.out.println("Error while reading the file.");
//        }
//    }
//}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ShowEmployeeInformation {
    public void execute() {
        String filePath = "src/employee_data.csv";
        Scanner scanner = new Scanner(System.in);

        System.out.println("All employees information:");
        displayEmployeeInformation(filePath);
    }

    private void displayEmployeeInformation(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) { // Ensure the line has exactly 7 parts
                    System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Gender: " + parts[2] + ", Position: " + parts[3] + ", Age: " + parts[4] + ", Username: " + parts[5] + ", Password: " + parts[6]);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
        }
    }
}

