import java.time.LocalDateTime;
import java.time.Month;
import java.util.Scanner;

public class statisticManagement {
    private PassengerFile passengerFile;
    private MemberFile memberFile;
    private FlightFile flightFile;
    private Scanner scanner;

    public statisticManagement() {
        this.passengerFile = new PassengerFile();
        this.memberFile = new MemberFile();
        this.flightFile = new FlightFile();
        this.scanner = new Scanner(System.in);
    }

    public void printPassengerStatistics() {
        int under18 = 0;
        int age18to30 = 0;
        int age30to40 = 0;
        int age40to60 = 0;
        int over60 = 0;
        if (passengerFile.getPassengers().isEmpty()) {
            System.out.println("No passenger available");
            return;
        }
        for (Passenger passenger : passengerFile.getPassengers()) {
            int age = passenger.getAge();
            if (age < 18)
                under18++;
            else if (age <= 30)
                age18to30++;
            else if (age <= 40)
                age30to40++;
            else if (age <= 60)
                age40to60++;
            else
                over60++;
        }
        System.out.println("Number of passengers under 18 years old: " + under18);
        System.out.println("Number of passengers from 18 to 30 years old: " + age18to30);
        System.out.println("Number of passengers from 30 to 40 years old: " + age30to40);
        System.out.println("Number of passengers from 40 to 60 years old: " + age40to60);
        System.out.println("Number of passengers over 60 years old: " + over60);
    }

    public void printMemberStatistic() {
        int[] roleCounts = new int[7];
        String[] roles = {"Developer", "Tester", "Pilot", "Flight Attendant", "Designer", "Analyst", "Manager"};
        if (memberFile.getMemberList().isEmpty()) {
            System.out.println("File is empty");
            return;
        }
        for (Member member : memberFile.getMemberList()) {
            String role = member.getRole();
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
        System.out.println("Pilot: " + roleCounts[1]);
        System.out.println("Flight Attendant: " + roleCounts[2]);
        System.out.println("Designer: " + roleCounts[3]);
        System.out.println("Analyst: " + roleCounts[4]);
        System.out.println("Manager: " + roleCounts[5]);
        System.out.println("Other Roles: " + roleCounts[6]);
    }

    public void printFlightStatistic() {
        int flightDepart = 0;
        int flightArrive = 0;
        System.out.print("Enter the year: ");
        int year = scanner.nextInt();
        System.out.print("Enter the month (1-12): ");
        int month = scanner.nextInt();
        if (month < 1 || month > 12)
            System.out.println("Invalid month. Please enter a month between 1 and 12");
        else {
            for (Flight flight : flightFile.getFlightList()) {
                LocalDateTime departTime = flight.getDepartureTime();
                LocalDateTime arriveTime = flight.getArrivalTime();
                String departLocation = flight.getDepartureLocation();
                String arriveLocation = flight.getArrivalLocation();
                if (departTime.getYear() == year && departTime.getMonthValue() == month && departLocation.equalsIgnoreCase("Ho Chi Minh City"))
                    flightDepart++;
                else if (arriveTime.getYear() == year && arriveTime.getMonthValue() == month && arriveLocation.equalsIgnoreCase("Ho Chi Minh City"))
                    flightArrive++;
            }
        }
        System.out.println("Number of Flight arrived to HCM city in " + Month.of(month) + "/" + year + ": " + flightDepart);
        System.out.println("Number of Flight depart from HCM city in " + Month.of(month) + "/" + year + ": " + flightArrive);
    }
}