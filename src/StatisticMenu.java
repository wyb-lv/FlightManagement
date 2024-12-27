import java.util.List;
import java.util.Scanner;

public class StatisticMenu {
    private Scanner scanner;
    public StatisticMenu(){
        this.scanner = new Scanner(System.in);
    }
    protected void run(List<Employee> employees){
        StatisticFile file = new StatisticFile();
        StatisticManagement management = new StatisticManagement(file, scanner, employees);
        while (true) {
            System.out.println("==== Statistic Management ====");
            System.out.println("1. Passenger Statistic");
            System.out.println("2. Member Statistic");
            System.out.println("3. Flight Statistic");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    management.printPassengerStatistics();
                    break;
                case 2:
                    management.printMemberStatistic();
                    break;
                case 3:
                    management.printFlightStatistic();
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }
}