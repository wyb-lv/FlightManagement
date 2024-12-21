import java.util.Scanner;

public class Statistic {
    private Scanner scanner;
    public Statistic(){
        this.scanner = new Scanner(System.in);
    }
    protected void run(){
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Passenger Statistic");
            System.out.println("2. Member Statistic");
            System.out.println("3. Flight Statistic");
            System.out.println("4. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    statisticManagement management = new statisticManagement();
                    management.printPassengerStatistics();
                    break;
                case 2:
                    statisticManagement management1 = new statisticManagement();
                    management1.printMemberStatistic();
                    break;
                case 3:
                    statisticManagement management2 = new statisticManagement();
                    management2.printFlightStatistic();
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Please select a valid option.");
            }
        }
    }
}