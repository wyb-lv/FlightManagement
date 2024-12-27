import java.io.*;
import java.util.*;
import java.util.function.Predicate;

public class EmployeeManagement {
    private final String filePath = "src/employee_data.csv";
    private final int MAX_ATTEMPTS = 3;
    private final List<Employee> employees = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public EmployeeManagement() {
        loadEmployeesFromFile();
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void run(Employee empAccess) {
        while (true) {
            showMenu();
            switch (scanner.nextLine()) {
                case "1" -> addEmployeeInformation();
                case "2" -> deleteEmployeeInformation();
                case "3" -> changeEmployeeInformation();
                case "4" -> findEmployeeInformation();
                case "5" -> changeEmployeeAccess(empAccess);
                case "6" -> {
                    displayEmployeeInformation();
                    System.out.println("Press any key to return to the menu...");
                    scanner.nextLine();
                }
                default -> {
                    System.out.println("Exiting employee management.");
                    return;
                }
            }
        }
    }

    private void loadEmployeesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    Employee employee = new Employee(parts[0], parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), parts[5], parts[6], Employee.AccessLevel.valueOf(parts[7]));
                    employees.add(employee);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
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
//==============================================================================show====================================================================================================
    public void addEmployeeInformation() {
        while (true) {
            String id = inputIDWithValidation("ID");
            if(id == null) return;

            String name = inputNotNullWithValidation("name");
            if(name == null) return;

            String gender = inputNotNullWithValidation("gender");
            if(gender == null) return;

            String position = inputNotNullWithValidation("position");
            if(position == null) return;

            int age = inputAgeWithValidation("age");
            if(age == -1) return;

            String username = inputUsernameWithValidation("username");
            if(username == null) return;

            String password = inputNotNullWithValidation("password");
            if(password == null) return;

            // Create and add the new employee with default access level NONE
            createAndAddToEmployees(id, name, gender, position, age, username, password, employees);

            // Append to CSV file
            updateCSV("Employee removed successfully!");

            // Ask user if they want to perform another action
            if(!performAnotherAct("Do you want to add another employee? (yes/no): ")) break;
        }
    }

    public void deleteEmployeeInformation() {
        while (true) {
            displayEmployeeInformation();
            String id = inputIDWithValidation("ID of the employee to remove");

            // Find employee need to remove
            Employee toRemove = findEmployeeById(id);

            // removing
            removeFromCSV(toRemove,id);

            // Ask user if they want to perform another action
            if(!performAnotherAct("Do you want to remove another employee? (yes/no): ")) break;
        }
    }

    public void changeEmployeeInformation() {
        while (true) {
            displayEmployeeInformation();

            String id;
            Employee toUpdate;
            while (true) {
                id = inputIDWithValidation("ID of the employee to update");
                if (id == null) {
                    System.out.println("Input cannot be null. Please try again.");
                    continue;
                }

                toUpdate = findEmployeeById(id);
                if (toUpdate == null) {
                    System.out.println("Employee with ID: " + id + " not found.");
                    if (!performAnotherAct("Do you want to try again? (yes/no): ")) {
                        return;
                    }
                } else {
                    break;
                }
            }

            System.out.println("Updating information for employee with ID: " + id);

            String newId = inputIDWithValidation("new ID");
            if(newId == null) return;

            String name = inputNotNullWithValidation("new name");
            if(name == null) return;

            String gender = inputNotNullWithValidation("new gender");
            if(gender == null) return;

            String position = inputNotNullWithValidation("new position");
            if(position == null) return;

            int age = inputAgeWithValidation("new age");
            if(age == -1) return;

            String username = inputUsernameWithValidation("new username");
            if(username == null) return;

            String password = inputNotNullWithValidation( "new password");
            if(password == null) return;

            updateToCSV(toUpdate, newId, name, gender, position, age, username, password);

            if (!performAnotherAct("Do you want to update another employee? (yes/no): ")) {
                break;
            }
        }
    }

    public void findEmployeeInformation() {
        while (true) {
            String searchValue = inputNotNullWithValidation("value to search for");
            if (searchValue == null) return;

            Set<String> foundLines = searchInCSV(searchValue);

            checkFoundLinesEmpty(foundLines);

            // Ask user if they want to perform another action
            if(!performAnotherAct("Do you want to search for another value? (yes/no): ")) break;
        }
    }

    public void changeEmployeeAccess(Employee empAccess) {
        if (empAccess.getAccess() == Employee.AccessLevel.NONE || empAccess.getAccess() == Employee.AccessLevel.FLIGHT_MANAGER) {
            System.out.println("You do not have permission to change access levels.");
            return;
        }

        while (true) {
            System.out.print("Enter the ID of the employee to change access: ");
            String targetId = scanner.nextLine();
            Employee targetEmployee = findEmployeeById(targetId);

            if (targetEmployee == null) {
                System.out.println("Target user not found.");
                continue; // Prompt again for a valid target employee ID
            }

            System.out.print("Enter new access level (ADMIN, EMPLOYEE_MANAGER, FLIGHT_MANAGER, NONE): ");
            String newAccessStr = scanner.nextLine();
            Employee.AccessLevel newAccess;

            try {
                newAccess = Employee.AccessLevel.valueOf(newAccessStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid access level.");
                continue; // Prompt again for a valid access level
            }

            if (canChangeAccess(empAccess.getAccess(), targetEmployee.getAccess(), newAccess)) {
                targetEmployee.setAccess(newAccess);
                System.out.println("Access level changed successfully.");
                updateCSV("Employee level access updated successfully!");
            } else {
                System.out.println("You do not have permission to assign this access level.");
            }

            if (!performAnotherAct("Do you want to change access for another employee? (yes/no): ")) break;
        }
    }
//==============================================================================GENERAL====================================================================================================
    public String inputWithValidation(String field, Predicate<String> validator) {
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

    public boolean isValidId(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        for (Employee emp : employees) {
            if (!emp.equals(id) && emp.getId().equals(id)) {
                return false;
            }
        }
        return true;
    }

    public String inputIDWithValidation(String field){
        String id = inputWithValidation(field, input -> isValidId(input));
        return id;
    }

    public String inputNotNullWithValidation(String field){
        String value = inputWithValidation(field, input -> !input.trim().isEmpty());
        return value;
    }

    public Integer inputIntegerWithValidation(String field) {
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

    public int inputAgeWithValidation(String field){
        int age = inputIntegerWithValidation(field);
        return age;
    }

    public boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        for (Employee emp : employees) {
            if (!emp.equals(username) && emp.getUsername().equals(username)) {
                return false;
            }
        }
        return true;
    }

    public String inputUsernameWithValidation(String field){
        String username = inputWithValidation(field, this::isValidUsername);
        return username;
    }

    public boolean performAnotherAct(String content) {
        System.out.print(content);
        String choice = scanner.nextLine();
        return choice.equalsIgnoreCase("yes");
    }

    // Stream API
    private Employee findEmployeeById(String id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public void displayEmployeeInformation() {
        System.out.println("All employees information:");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) { // Ensure the line has exactly 8 parts
                    System.out.println("ID: " + parts[0] + ", Name: " + parts[1] + ", Gender: " + parts[2] + ", Position: " + parts[3] + ", Age: " + parts[4] + ", Username: " + parts[5] + ", Password: " + parts[6]+ ", Access: " + parts[7]);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error while reading the file.");
        }
    }

    public void updateCSV(String content) {
        System.out.println(content);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Employee employee : employees) {
                writer.write(employee.getId() + "," + employee.getName() + "," + employee.getGender() + "," + employee.getPosition() + "," + employee.getAge() + "," + employee.getUsername() + "," + employee.getPassword() + "," + employee.getAccess() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error while writing to the file.");
        }
    }
//==============================================================================add====================================================================================================
    public void createAndAddToEmployees(String id, String name, String gender, String position, int age, String username, String password, List<Employee> employees) {
        Employee employee = new Employee(id, name, gender, position, age, username, password, Employee.AccessLevel.NONE);
        employees.add(employee);
        System.out.println("Employee added successfully with default access level NONE!");
    }
//==============================================================================remove====================================================================================================
    public void removeFromCSV(Employee toRemove, String id) {
        if (toRemove != null) {
            employees.remove(toRemove);
            updateCSV("Employee removed successfully!");
        } else {
            System.out.println("Employee with ID: " + id + " not found.");
        }
    }
//==============================================================================change====================================================================================================
    public void updateToCSV(Employee toUpdate, String id, String name, String gender, String position, int age, String username, String password) {
        toUpdate.setId(id);
        toUpdate.setName(name);
        toUpdate.setGender(gender);
        toUpdate.setPosition(position);
        toUpdate.setAge(age);
        toUpdate.setUsername(username);
        toUpdate.setPassword(password);
        updateCSV("Employee information updated successfully!");
    }
//==============================================================================find====================================================================================================
    private Set<String> searchInCSV(String searchValue) {
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
            System.out.println("Invalid line format: " + line);
        }
    }
//==============================================================================change access====================================================================================================
    private boolean canChangeAccess(Employee.AccessLevel currentAccess, Employee.AccessLevel targetAccess, Employee.AccessLevel newAccess) {
        return switch (currentAccess) {
            case ADMIN -> true; // Admin can assign any access level
            case EMPLOYEE_MANAGER -> (targetAccess != Employee.AccessLevel.ADMIN) && (newAccess == Employee.AccessLevel.EMPLOYEE_MANAGER || newAccess == Employee.AccessLevel.FLIGHT_MANAGER || newAccess == Employee.AccessLevel.NONE);
            case NONE, FLIGHT_MANAGER -> false; // None and Flight Manager cannot assign any access level
        };
    }
}

