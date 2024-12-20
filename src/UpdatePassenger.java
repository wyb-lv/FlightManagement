import java.io.*;
import java.util.*;


public class UpdatePassenger {
    public void runUpdateProcess(String filePath) {

        while (true) {
            displayPassengers(filePath);

            Scanner scanner = new Scanner(System.in);
            System.out.println("Nhap ID cua hanh khach can cap nhat: ");
            String keyword = scanner.nextLine().toLowerCase();

            try {
                updatePassenger(filePath, keyword);
            } catch (IOException e) {
                System.out.println("Da xay ra loi: " + e.getMessage());
            }

            System.out.println("\nBan co muon tiep tuc tim kiem? (Nhap 'Y' de tiep tuc, bat ky phim nao khac de thoat): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("y")) {
                System.out.println("Thoat tim kiem. Tam biet!");
                break;
            }
        }
    }

    public void displayPassengers(String filePath) {
        System.out.println("Danh sach khach hang hien tai:");
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi doc tep: " + e.getMessage());
        }
    }

    public void updatePassenger(String filePath, String idToUpdate) throws IOException {
        File inputFile = new File(filePath);
        File tempFile = new File("temp.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            Scanner scanner = new Scanner(System.in);
            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");

                if (details[0].equals(idToUpdate)) {
                    System.out.println("Thong tin hien tai: " + line);
                    System.out.println("Nhap ten moi (hoac de trong de giu nguyen): ");
                    String name = scanner.nextLine();
                    System.out.println("Nhap tuoi moi (hoac de trong de giu nguyen): ");
                    String age = scanner.nextLine();
                    System.out.println("Nhap gioi tinh moi (hoac de trong de giu nguyen): ");
                    String gender = scanner.nextLine();
                    System.out.println("Nhap so dien thoai moi (hoac de trong de giu nguyen): ");
                    String phoneNumber = scanner.nextLine();

                    if (!name.isEmpty()) details[1] = name;
                    if (!age.isEmpty()) details[2] = age;
                    if (!gender.isEmpty()) details[3] = gender;
                    if (!phoneNumber.isEmpty()) details[4] = phoneNumber;

                    writer.write(String.join(",", details) + System.lineSeparator());
                    updated = true;
                } else {
                    writer.write(line + System.lineSeparator());
                }
            }

            if (!updated) {
                System.out.println("Khong tim thay hanh khach voi ID: " + idToUpdate);
            } else {
                System.out.println("Cap nhat thong tin hanh khach thanh cong.");
            }
        }

        if (inputFile.delete()) {
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Khong the doi ten tep tam.");
            }
        } else {
            System.out.println("Khong the cap nhat tep.");
        }
    }
}
