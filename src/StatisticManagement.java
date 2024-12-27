import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StatisticManagement {
    private StatisticFile statisticFile;
    private Scanner scanner;
    private List<Flight> flightList;
    private List<Employee> memberList;
    private List<Passenger>passengerList;

    public StatisticManagement(StatisticFile file, Scanner scanner, List<Employee> employees) {
        this.statisticFile = file;
        this.scanner = scanner;
//        this.flightList = new ArrayList<>();
        this.memberList = employees;
        this.passengerList = new ArrayList<>();
        statisticFile.loadFlightfromFile(flightList);
        // StatisticFile.loadMemberfromFile(memberList);
        statisticFile.loadPassengerfromFile(passengerList);
    }

    public void printPassengerStatistics() {
        int[] ageGroups = new int[5];
        if (passengerList == null || passengerList.isEmpty()) {
            System.out.println("No passengers available.");
            return;
        }
        for (Passenger passenger : passengerList) {
            int age = passenger.getAge();
            if (age < 18)
                ageGroups[0]++;
            else if (age <= 30)
                ageGroups[1]++;
            else if (age <= 40)
                ageGroups[2]++;
            else if (age <= 60)
                ageGroups[3]++;
            else
                ageGroups[4]++;
        }

        String[] ageGroup = {"under 18 years old: ", "from 18 to 30 years old: ","from 30 to 40 years old: ", "from 40 to 60 years old: ", "over 60 years old: "};
        for(int i = 0; i < ageGroup.length; i++) {
            System.out.println("Number of passengers "+ ageGroup[i] + ageGroups[i]);
        }
    }


    public void printMemberStatistic() {
        int[] roleCounts = new int[7];
        String[] roles = {"Developer", "Tester", "Pilot", "Flight Attendant", "Designer", "Analyst", "Manager"};
        if (memberList.isEmpty()) {
            System.out.println("File is empty");
            return;
        }
        for (Employee member : memberList) {
            String role = member.getPosition();
            if (role.equalsIgnoreCase(roles[0]) || role.equalsIgnoreCase(roles[1])) {
                roleCounts[0]++;
            } else if (role.equalsIgnoreCase(roles[2])) {
                roleCounts[1]++;
            } else if (role.equalsIgnoreCase(roles[3])) {
                roleCounts[2]++;
            } else if (role.equalsIgnoreCase(roles[4])) {
                roleCounts[3]++;
            } else if (role.equalsIgnoreCase(roles[5])) {
                roleCounts[4]++;
            } else if (role.equalsIgnoreCase(roles[6])) {
                roleCounts[5]++;
            } else {
                roleCounts[6]++;
            }
        }
        System.out.println("IT Team (Developer/Tester): " + roleCounts[0]);
        for (int i = 2; i < roles.length; i++) {
            System.out.println(roles[i] + ": " + roleCounts[i-1]);
        }
    }

    public void printFlightStatistic() {
        int flightDepart = 0;
        int flightArrive = 0;
        int year = readInput("Enter the year: ");
        int month = readInput("Enter the month (1-12): ");
        if (month < 1 || month > 12)
            System.out.println("Invalid month. Please enter a month between 1 and 12");
        else {
            for (Flight flight : flightList) {
                LocalDateTime departTime = flight.getDatetimeStart();
                LocalDateTime arriveTime = flight.getDatetimeEnd();
                String departLocation = flight.getLocateStart();
                String arriveLocation = flight.getLocateEnd();
                if (departTime.getYear() == year && departTime.getMonthValue() == month && departLocation.equalsIgnoreCase("Ho Chi Minh City"))
                    flightDepart++;
                else if (arriveTime.getYear() == year && arriveTime.getMonthValue() == month && arriveLocation.equalsIgnoreCase("Ho Chi Minh City"))
                    flightArrive++;
            }
        }
        System.out.println("Number of Flight arrived to HCM city in " + Month.of(month) + "/" + year + ": " + flightDepart);
        System.out.println("Number of Flight depart from HCM city in " + Month.of(month) + "/" + year + ": " + flightArrive);
    }
    private int readInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }
}