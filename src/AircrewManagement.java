import java.util.*;

public class AircrewManagement {
    private File fileHandler;
    private Scanner scanner;
    private Map<String, AircrewTeam> aircrewMap;
    private Map<String, AircrewMember> membersMap;
    private List<AircrewMember> pilots;
    private List<AircrewMember> attendants;

    public AircrewManagement(File fileHandler, Scanner scanner) {
        this.fileHandler = fileHandler;
        this.scanner = scanner;
        this.aircrewMap = new HashMap<>();
        this.membersMap = new HashMap<>();
        this.pilots = new ArrayList<>();
        this.attendants = new ArrayList<>();
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
        System.out.println("Available Pilots:");
        for (AircrewMember pilot : pilots) {
            System.out.println(pilot.getId() + " - " + pilot.getName());
        }
        System.out.println("Available Flight Attendants:");
        for (AircrewMember attendant : attendants) {
            System.out.println(attendant.getId() + " - " + attendant.getName());
        }
        addMemberToTeam("Pilot");
        for (int i = 1; i <= 2; i++) {
            addMemberToTeam("Flight Attendant " + i);
        }
        System.out.println("Successfully added members to the team!");
        fileHandler.saveAircrewToFile(aircrewMap);
    }


    private void addMemberToTeam(String role) {
        String memberId = readInput("Enter " + role + " ID: ").trim();
        AircrewMember member = membersMap.get(memberId);
        if (member != null) {
            membersMap.put(role, member);
        } else {
            System.out.println("Invalid " + role + " ID");
        }
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