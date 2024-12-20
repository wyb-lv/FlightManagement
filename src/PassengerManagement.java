import java.util.*;

class PassengerManagement {
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        String filePath = "src/Passenger.csv";

        while (true) {
            System.out.println("\nChon chuc nang:");
            System.out.println("1. Tim kiem hanh khach");
            System.out.println("2. Xoa hanh khach");
            System.out.println("3. sua thong tin hanh khach");
            System.out.println("4. hien thi thong tin hanh khach");
            System.out.println("5. them hanh khach");
            System.out.println("6. Thoat");
            System.out.print("Lua chon cua ban: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so tu 1 den 3.");
                continue;
            }

            switch (choice) {
                case 1:
                    SearchPassenger searchPassenger = new SearchPassenger(filePath);
                    searchPassenger.runSearch();
                    break;
                case 2:
                    DeletePassenger fileDelete = new DeletePassenger();
                    fileDelete.runDeleteProcess(filePath);
                    break;
                case 3:
                    UpdatePassenger updatePassenger = new UpdatePassenger();
                    updatePassenger.runUpdateProcess(filePath);
                    break;
                case 4:
                    DisplayPassenger displayPassenger = new DisplayPassenger(filePath);
                    displayPassenger.readData();
                    break;
                case 5:
                    AddPassenger addPassenger = new AddPassenger();
                    addPassenger.run();
                    break;
                case 6:
                    System.out.println("Thoat chuong trinh. Tam biet!");
                    return;
                default:
                    System.out.println("Lua chon khong hop le. Vui long nhap lai.");
            }
        }
    }

}
