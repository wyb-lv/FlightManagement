import java.io.*;
import java.util.*;

public class SearchPassenger {

    private final String filePath;

    public SearchPassenger(String filePath) {
        this.filePath = filePath;
    }

    public void runSearch() {
        Scanner scanner = new Scanner(System.in);

            while (true) {
            System.out.println("\nNhap  ID hanh khach can tim: ");
            String keyword = scanner.nextLine().toLowerCase();

            try {
                List<String> results = this.searchPassenger(keyword);
                if (results.isEmpty()) {
                    System.out.println("Khong tim thay hanh khach phu hop.");
                } else {
                    System.out.println("\nDanh sach hanh khach tim thay:");
                    for (String result : results) {
                        System.out.println(result);
                    }
                }
            } catch (IOException e) {
                System.err.println("Da xay ra loi khi doc file: " + e.getMessage());
            }

            System.out.println("\nBan co muon tiep tuc tim kiem? (Nhap 'Y' de tiep tuc, bat ky phim nao khac de thoat): ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (!choice.equals("y")) {
                System.out.println("Thoat tim kiem. Tam biet!");
                break;
            }
        }
    }

    private List<String> searchPassenger(String keyword) throws IOException {
        List<String> matchingPassengers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 0 && columns[0].equalsIgnoreCase(keyword)) { // Check if ID matches
                    matchingPassengers.add(line);
                }
            }
        }

        return matchingPassengers;
    }
}
