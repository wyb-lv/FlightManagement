import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FlightFile extends statisticFile{
   private final String fileName = "Flight.csv";
   private List<Flight> flightList;
   public FlightFile(){
       this.flightList = new ArrayList<>();
        loadInfofromFile();
   }

    @Override
    protected void loadInfofromFile() {
        super.checkExistFile(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    try {
                        String code = data[0].trim();
                        String airlineName = data[1].trim();
                        String departLocation = data[2].trim();
                        String arriveLocation = data[3].trim();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        LocalDateTime departTime = LocalDateTime.parse(data[4].trim(), formatter);
                        LocalDateTime arriveTime = LocalDateTime.parse(data[5].trim(), formatter);

                        int seat = Integer.parseInt(data[6].trim());
                        Flight flight = new Flight(code, airlineName, departLocation, arriveLocation, departTime, arriveTime, seat);
                        flightList.add(flight);
                    } catch (Exception e) {
                        System.out.println("Error parsing flight data: " + e.getMessage() + " in line: " + line);
                    }
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error loading file: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public List<Flight> getFlightList() {
        return flightList;
    }
}
