import java.time.LocalDateTime;

public class Flight {
    private String flightCode;
    private String airlineName;
    private String departureLocation;
    private String arrivalLocation;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int seatCount;

    public Flight(String flightCode, String airlineName, String departureLocation, String arrivalLocation, LocalDateTime departureTime, LocalDateTime arrivalTime, int seatCount) {
        this.flightCode = flightCode;
        this.airlineName = airlineName;
        this.departureLocation = departureLocation;
        this.arrivalLocation = arrivalLocation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatCount = seatCount;
    }

    public String getDepartureLocation() {
        return departureLocation;
    }

    public String getArrivalLocation() {
        return arrivalLocation;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
}
