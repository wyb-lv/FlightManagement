import java.util.*;

public class AircrewManagement {
    private AircrewAndEmployeeFile fileHandler;
    private Scanner scanner;
    private Map<String, AircrewTeam> aircrewMap;
    private Map<String, AircrewMember> membersMap;
    private Map<String, AircrewMember> pilots;
    private Map<String, AircrewMember> attendants;

    public AircrewManagement(AircrewAndEmployeeFile fileHandler, Scanner scanner) {
        this.fileHandler = fileHandler;
        this.scanner = scanner;
        this.aircrewMap = new HashMap<>();
        this.membersMap = new HashMap<>();
        this.pilots = new HashMap<>();
        this.attendants = new HashMap<>();
        fileHandler.loadAircrewFromFile(aircrewMap, membersMap);
        fileHandler.loadEmployeesFromFile(pilots, attendants);
    }

    public void createAircrewTeam() {
        String aircrewID = readInput("Enter the Team ID: ").trim();
        if (aircrewMap.containsKey(aircrewID)) {
            System.out.println("Team ID already exists. Please choose a different Team ID.");
            return;
        }
        if (!aircrewID.matches("[A-Za-z0-9]{3,}") || aircrewID.isEmpty()) {
            System.out.println("Invalid ID");
            return;
        }
        String teamName = readInput("Enter the Team Name: ").trim();
        if (teamName.isEmpty()) {
            System.out.println("Team name cannot be empty");
            return;
        }
        AircrewTeam newTeam = new AircrewTeam(aircrewID, teamName);
        aircrewMap.put(aircrewID, newTeam);
        fileHandler.saveAircrewToFile(aircrewMap);
    }

    public void addMemberToAircrew() {
        String teamId = readInput("Enter the Team ID: ").trim();
        if (!aircrewMap.containsKey(teamId)) {
            System.out.println("No team found with the given ID.");
            return;
        }
        AircrewTeam team = aircrewMap.get(teamId);
        System.out.println("Available Pilots:");
        for (AircrewMember pilot : pilots.values()) {
            System.out.println(pilot.getId() + " - " + pilot.getName());
        }
        System.out.println("Available Flight Attendants:");
        for (AircrewMember attendant : attendants.values()) {
            System.out.println(attendant.getId() + " - " + attendant.getName());
        }
        addMemberToTeam(team, "Pilot");
        addMemberToTeam(team, "Flight Attendant 1");
        addMemberToTeam(team, "Flight Attendant 2");
        System.out.println("Successfully added members to the team!");
        fileHandler.saveAircrewToFile(aircrewMap);
    }

    private void addMemberToTeam(AircrewTeam team, String role) {
        String memberId = readInput("Enter " + role + " ID: ").trim();
        AircrewMember member = null;
        if (role.equals("Pilot")) {
            member = pilots.get(memberId);
        } else if (role.equals("Flight Attendant 1") || role.equals("Flight Attendant 2")) {
            member = attendants.get(memberId);
        }
        if (member == null) {
            System.out.println("Invalid " + role + " ID. No such member found.");
            return;
        }
        if (role.equals("Pilot")) {
            team.setPilot(member);
        } else if (role.equals("Flight Attendant 1")) {
            team.setFlightAttendant1(member);
        } else if (role.equals("Flight Attendant 2")) {
            team.setFlightAttendant2(member);
        }
        System.out.println(role + " assigned to the team successfully.");
    }

    public void displayAllAircrewTeams() {
        if (aircrewMap.isEmpty()) {
            System.out.println("No teams available.");
            return;
        }
        for (AircrewTeam team : aircrewMap.values()) {
            System.out.println(team);
            for (AircrewMember member : membersMap.values()) {
                System.out.println(member);
            }
        }
    }

    public void deleteAircrewTeam() {
        String teamId = readInput("Enter the Team ID to delete: ").trim();
        if (aircrewMap.remove(teamId) != null) {
            fileHandler.saveAircrewToFile(aircrewMap);
        } else {
            System.out.println("No team found with the given ID.");
        }
    }

    public void searchAircrew() {
        String teamId = readInput("Enter the Team ID to search: ").trim();
        AircrewTeam team = aircrewMap.get(teamId);
        if (team != null) {
            System.out.println("Team Found: " + team);
            for (AircrewMember member : membersMap.values()) {
                System.out.println(member);
            }
        } else {
            System.out.println("No team found with the given ID.");
        }
    }

    private String readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
