import java.io.*;
import java.util.*;

public class AircrewAndEmployeeFile {
    private static final String AIRCREW_FILE = "src/aircrew.csv";
    private static final String EMPLOYEES_FILE = "src/employee_data.csv";

    public void loadAircrewFromFile(Map<String, AircrewTeam> aircrewMap, Map<String, AircrewMember> membersMap) {
        checkFileExist(AIRCREW_FILE);
        try (BufferedReader reader = new BufferedReader(new FileReader(AIRCREW_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String teamId = parts[0].trim();
                    String teamName = parts[1].trim();
                    AircrewTeam team = new AircrewTeam(teamId, teamName);
                    aircrewMap.put(teamId, team);

                    for (int i = 2; i < parts.length; i++) {
                        String memberName = parts[i].trim();
                        String role = (i == 2) ? "Pilot" : "Flight Attendant";
                        AircrewMember member = new AircrewMember("", memberName, "", 0, role);
                        membersMap.put(member.getId(), member);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading aircrew file: " + e.getMessage());
        }
    }

    public void loadEmployeesFromFile(List<AircrewMember> pilots, List<AircrewMember> attendants) {
        checkFileExist(EMPLOYEES_FILE);
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String gender = data[2].trim();
                    String position = data[3].trim();
                    int age = Integer.parseInt(data[4].trim());
                    String username = data[5].trim();
                    String password = data[6].trim();
                    String access = data[7].trim();

                    AircrewMember member = new AircrewMember(id, name, gender, age, position);
                    if (position.equalsIgnoreCase("Pilot")) {
                        pilots.add(member);
                    } else if (position.equalsIgnoreCase("Flight Attendant")) {
                        attendants.add(member);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void saveAircrewToFile(Map<String, AircrewTeam> aircrewMap) {
        checkFileExist(AIRCREW_FILE);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AIRCREW_FILE))) {
            for (AircrewTeam team : aircrewMap.values()) {
                writer.write(team.toCsvString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private void checkFileExist(String fileName) {
        java.io.File file = new java.io.File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist. Creating a new file.");
            try {
                if (file.createNewFile()) {
                    System.out.println("File created.");
                } else {
                    System.out.println("Failed to create file.");
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }
}