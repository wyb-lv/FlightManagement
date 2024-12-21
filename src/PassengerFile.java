import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerFile extends statisticFile{
    private static final String fileName = "passenger.csv";
    private List<Passenger>passengers;
    public PassengerFile(){
        this.passengers = new ArrayList<>();
        loadInfofromFile();
    }

    @Override
    protected void loadInfofromFile() {
        super.checkExistFile(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    int age = Integer.parseInt(parts[2].trim());
                    String gender = parts[3].trim();
                    String phone = parts[4].trim();
                    Passenger passenger = new Passenger(id, name, age, gender, phone);
                    passengers.add(passenger);
                }
            }
        }
        catch (IOException e){
            System.out.println("Error reading file: " + e.getMessage());
        }
        catch (NumberFormatException e){
            System.out.println("Error parsing age: " + e.getMessage());
        }
    }
    public List<Passenger> getPassengers() {
        return passengers;
    }
}
