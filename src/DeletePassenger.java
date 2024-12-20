import java.io.*;
import java.util.*;

public class DeletePassenger {

    public String getIdToRemove() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhap ID khach hang can xoa: ");
        return scanner.nextLine();
    }

    public void runDeleteProcess(String filePath) {
        displayPassengers(filePath);
        String idToRemove = getIdToRemove();
        if (confirmDelete(idToRemove)) {
            deletePassengerById(filePath, idToRemove);
        } else {
            System.out.println("Huy thao tac xoa.");
        }
        displayPassengers(filePath);
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

    public boolean confirmDelete(String idToRemove) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ban co chac chan muon xoa khach hang voi ID: " + idToRemove + "? (yes/no)");
        String confirmation = scanner.nextLine();
        return confirmation.equalsIgnoreCase("yes");
    }

    public void deletePassengerById(String filePath, String idToRemove) {
        File file = new File(filePath);
        File tempFile = new File("tempfile.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean isDeleted = false;

            while ((currentLine = reader.readLine()) != null) {
                // Bo qua dong tieu de hoac dong co ID can xoa
                if (currentLine.startsWith(idToRemove + ",")) {
                    isDeleted = true;
                    continue;
                }
                writer.write(currentLine + System.lineSeparator());
            }

            if (isDeleted) {
                System.out.println("Thong tin khach hang voi ID " + idToRemove + " da duoc xoa.");
            } else {
                System.out.println("Khong tim thay khach hang voi ID " + idToRemove + ".");
            }

        } catch (IOException e) {
            System.out.println("Da xay ra loi: " + e.getMessage());
        }


        if (file.delete()) {
            if (!tempFile.renameTo(file)) {
                System.out.println("Khong the doi ten tep tam.");
            }
        } else {
            System.out.println("Khong the cap nhat tep.");
        }
    }


}

