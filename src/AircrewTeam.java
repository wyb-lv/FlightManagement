public class AircrewTeam {
    private String teamId;
    private String teamName;
    private AircrewMember pilot;
    private AircrewMember flightAttendant1;
    private AircrewMember flightAttendant2;
    public AircrewTeam(String teamId, String teamName) {
        this.teamId = teamId;
        this.teamName = teamName;
    }

    public String getTeamId() {return teamId;}
    public String getTeamName() {return teamName;}
    public AircrewMember getPilot() {return pilot;}
    public AircrewMember getFlightAttendant1() {return flightAttendant1;}
    public AircrewMember getFlightAttendant2() {return flightAttendant2;}
    public void setPilot(AircrewMember pilot) {this.pilot = pilot;}
    public void setFlightAttendant1(AircrewMember flightAttendant1) {this.flightAttendant1 = flightAttendant1;}
    public void setFlightAttendant2(AircrewMember flightAttendant2) {this.flightAttendant2 = flightAttendant2;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Team ID: ").append(teamId).append(", Team Name: ").append(teamName).append("\n");

        sb.append(pilot != null ? "Pilot: " + pilot + "\n" : "Pilot: Not Assigned\n");
        sb.append(flightAttendant1 != null ? "Flight Attendant 1: " + flightAttendant1 + "\n" : "Flight Attendant 1: Not Assigned\n");
        sb.append(flightAttendant2 != null ? "Flight Attendant 2: " + flightAttendant2 + "\n" : "Flight Attendant 2: Not Assigned\n");

        return sb.toString();
    }
}
