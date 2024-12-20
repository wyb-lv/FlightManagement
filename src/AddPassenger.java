import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class AddPassenger {
    private static final String FILE_NAME = "src/Passenger.csv";

    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean continueAdding = true;

        while (continueAdding) {
            addNewPassenger(scanner);

            System.out.println("Ban co muon tiep tuc them hanh khach? (Nhap 'Y' de tiep tuc, bat ky phim nao khac de thoat)");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("Y")) {
                continueAdding = false;
            }
        }


    }

    public void addNewPassenger(Scanner scanner) {
        try {
            System.out.println("Nhap thong tin hanh khach:");

            System.out.print("ID: ");
            String id = scanner.nextLine().trim();

            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Age: ");
            String age = scanner.nextLine().trim();

            System.out.print("Gender: ");
            String gender = scanner.nextLine().trim();

            System.out.print("Phone Number: ");
            String phoneNumber = scanner.nextLine().trim();

            if (id.isEmpty() || name.isEmpty() || age.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty()) {
                System.out.println("Tat ca cac thong tin phai duoc dien day du. Vui long thu lai.");
                return;
            }

            writeToFile(id, name, age, gender, phoneNumber);
            System.out.println("Hanh khach da duoc luu thanh cong.");

        } catch (Exception e) {
            System.out.println("Da xay ra loi: " + e.getMessage());
        }
    }

    private void writeToFile(String id, String name, String age, String gender, String phoneNumber) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(String.format("%s,%s,%s,%s,%s", id, name, age, gender, phoneNumber));
            writer.newLine();
        }
    }
}
