//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.List;
//import java.util.Scanner;
//
//public class ChangeEmployeeInformation {
//    private static final int MAX_ATTEMPTS = 3;
//
//    public void execute(List<Employee> employees) {
//        Scanner scanner = new Scanner(System.in);
//        ShowEmployeeInformation showEmployeeInfo = new ShowEmployeeInformation();
//        while (true) {
//            showEmployeeInfo.execute();
//            System.out.print("Enter the ID of the employee to update: ");
//            String id = scanner.nextLine();
//            Employee toUpdate = null;
//
//            for (Employee emp : employees) {
//                if (emp.getId().equals(id)) {
//                    toUpdate = emp;
//                    break;
//                }
//            }
//
//            if (toUpdate != null) {
//                System.out.println("Updating information for employee with ID: " + id);
//
//                // Update name
//                for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
//                    System.out.print("Enter new name: ");
//                    String name = scanner.nextLine();
//                    if (!name.isEmpty()) {
//                        toUpdate.setName(name);
//                        break;
//                    } else {
//                        System.out.println("Name cannot be empty. Try again.");
//                    }
//                    if (attempts == MAX_ATTEMPTS - 1) {
//                        System.out.println("Max attempts reached. Returning to menu.");
//                        return;
//                    }
//                }
//
//                // Update gender
//                for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
//                    System.out.print("Enter new gender: ");
//                    String gender = scanner.nextLine();
//                    if (!gender.isEmpty()) {
//                        toUpdate.setGender(gender);
//                        break;
//                    } else {
//                        System.out.println("Gender cannot be empty. Try again.");
//                    }
//                    if (attempts == MAX_ATTEMPTS - 1) {
//                        System.out.println("Max attempts reached. Returning to menu.");
//                        return;
//                    }
//                }
//
//                // Update position
//                for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
//                    System.out.print("Enter new position: ");
//                    String position = scanner.nextLine();
//                    if (!position.isEmpty()) {
//                        toUpdate.setPosition(position);
//                        break;
//                    } else {
//                        System.out.println("Position cannot be empty. Try again.");
//                    }
//                    if (attempts == MAX_ATTEMPTS - 1) {
//                        System.out.println("Max attempts reached. Returning to menu.");
//                        return;
//                    }
//                }
//
//                // Update age
//                for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
//                    System.out.print("Enter new age: ");
//                    if (scanner.hasNextInt()) {
//                        int age = scanner.nextInt();
//                        scanner.nextLine(); // Consume newline
//                        toUpdate.setAge(age);
//                        break;
//                    } else {
//                        System.out.println("Invalid age. Enter a valid age.");
//                        scanner.next(); // consume invalid input
//                    }
//                    if (attempts == MAX_ATTEMPTS - 1) {
//                        System.out.println("Max attempts reached. Returning to menu.");
//                        return;
//                    }
//                }
//
//                // Update username
//                for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
//                    System.out.print("Enter new username: ");
//                    String username = scanner.nextLine();
//                    if (isValidUsername(username, employees, toUpdate)) {
//                        toUpdate.setUsername(username);
//                        break;
//                    } else {
//                        System.out.println("Duplicate or empty username. Try again.");
//                    }
//                    if (attempts == MAX_ATTEMPTS - 1) {
//                        System.out.println("Max attempts reached. Returning to menu.");
//                        return;
//                    }
//                }
//
//                // Update password
//                for (int attempts = 0; attempts < MAX_ATTEMPTS; attempts++) {
//                    System.out.print("Enter new password: ");
//                    String password = scanner.nextLine();
//                    if (!password.isEmpty()) {
//                        toUpdate.setPassword(password);
//                        break;
//                    } else {
//                        System.out.println("Password cannot be empty. Try again.");
//                    }
//                    if (attempts == MAX_ATTEMPTS - 1) {
//                        System.out.println("Max attempts reached. Returning to menu.");
//                        return;
//                    }
//                }
//
//                System.out.println("Employee information updated successfully!");
//                updateCSV(employees);
//
//                // Ask user if they want to perform another action
//                System.out.print("Do you want to update another employee? (yes/no): ");
//                String choice = scanner.nextLine();
//
//                if (!choice.equalsIgnoreCase("yes"))
//                    break;
//            }
//            else {
//                System.out.println("Employee with ID: " + id + " not found.");
//                System.out.print("Do you want to try again? (yes/no): ");
//                String choice = scanner.nextLine();
//
//                if (!choice.equalsIgnoreCase("yes"))
//                    break;
//            }
//        }
//    }
//
//    private boolean isValidUsername(String username, List<Employee> employees, Employee currentEmployee) {
//        if (username == null || username.trim().isEmpty()) {
//            return false;
//        }
//        for (Employee emp : employees) {
//            if (!emp.equals(currentEmployee) && emp.getUsername().equals(username)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    private void updateCSV(List<Employee> employees) {
//        String filePath = "employee_data.csv";
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

public class ChangeEmployeeInformation {
    private static final int MAX_ATTEMPTS = 3;

    public void execute(List<Employee> employees) {
        Scanner scanner = new Scanner(System.in);
        ShowEmployeeInformation showEmployeeInfo = new ShowEmployeeInformation();

        while (true) {
            showEmployeeInfo.execute();
            String id = inputWithValidation("ID of the employee to update", scanner, input -> isValidId(input, employees));
            if (id == null) return;

            final Employee toUpdate = findEmployeeById(id, employees);
            if (toUpdate == null) {
                System.out.println("Employee with ID: " + id + " not found.");
                System.out.print("Do you want to try again? (yes/no): ");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("yes")) {
                    break;
                }
                continue;
            }

            System.out.println("Updating information for employee with ID: " + id);

            String name = inputWithValidation("new name", scanner, input -> !input.trim().isEmpty());
            if (name != null) toUpdate.setName(name);

            String gender = inputWithValidation("new gender", scanner, input -> !input.trim().isEmpty());
            if (gender != null) toUpdate.setGender(gender);

            String position = inputWithValidation("new position", scanner, input -> !input.trim().isEmpty());
            if (position != null) toUpdate.setPosition(position);

            Integer age = inputIntegerWithValidation("new age", scanner);
            if (age != null) toUpdate.setAge(age);

            String username = inputWithValidation("new username", scanner, input -> isValidUsername(input, employees, toUpdate));
            if (username != null) toUpdate.setUsername(username);

            String password = inputWithValidation("new password", scanner, input -> !input.trim().isEmpty());
            if (password != null) toUpdate.setPassword(password);

            System.out.println("Employee information updated successfully!");
            updateCSV(employees);

            System.out.print("Do you want to update another employee? (yes/no): ");
            String choice = scanner.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                break;
            }
        }
    }

    private Employee findEmployeeById(String id, List<Employee> employees) {
        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
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

    private Integer inputIntegerWithValidation(String field, Scanner scanner) {
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

    private boolean isValidId(String id, List<Employee> employees) {
        return id != null && !id.trim().isEmpty();
    }

    private boolean isValidUsername(String username, List<Employee> employees, Employee currentEmployee) {
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

    private void updateCSV(List<Employee> employees) {
        String filePath = "employee_data.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getGender() + "," + employee.getPosition() + "," + employee.getAge() + "," + employee.getUsername() + "," + employee.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error while writing to the file.");
        }
    }
}

