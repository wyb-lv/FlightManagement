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
                        String position = (i == 2) ? "Pilot" : "Flight Attendant";
                        String memberId = teamId + "_" + i;
                        AircrewMember member = new AircrewMember(memberId, memberName, position);
                        membersMap.put(memberId, member);
                        if (position.equals("Pilot")) {
                            team.setPilot(member);
                        } else if (position.equals("Flight Attendant")) {
                            if (team.getFlightAttendant1() == null) {
                                team.setFlightAttendant1(member);
                            } else {
                                team.setFlightAttendant2(member);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading aircrew file: " + e.getMessage());
        }
    }

    public void loadEmployeesFromFile(Map<String, AircrewMember> pilots, Map<String, AircrewMember> attendants) {
        checkFileExist(EMPLOYEES_FILE);
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String position = data[3].trim();
                    AircrewMember member = new AircrewMember(id, name, position);
                    if (position.equalsIgnoreCase("Pilot")) {
                        pilots.put(id, member);
                    } else if (position.equalsIgnoreCase("Flight Attendant")) {
                        attendants.put(id, member);
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
                writer.write(team.getTeamId() + "," + team.getTeamName() + ",");
                if (team.getPilot() != null) {
                    writer.write(team.getPilot().getName() + ",");
                }
                if (team.getFlightAttendant1() != null) {
                    writer.write(team.getFlightAttendant1().getName() + ",");
                }
                if (team.getFlightAttendant2() != null) {
                    writer.write(team.getFlightAttendant2().getName());
                }
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