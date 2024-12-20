import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DisplayPassenger {
    private String filePath;

    public DisplayPassenger(String filePath) {
        this.filePath = filePath;
    }

    public void readData() {
        System.out.println("Danh sach khach hang hien tai:");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Da xay ra loi khi doc tep: " + e.getMessage());
        }
    }
}



