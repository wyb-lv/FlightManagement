public class AircrewTeam {
    private String teamID;
    private String teamName;

    public AircrewTeam(String teamID, String teamName) {
        this.teamID = teamID;
        this.teamName = teamName;
    }

    protected String toCsvString() {
        return teamID + "," + teamName;
    }

    @Override
    public String toString() {
        return "TeamID: " + teamID + ", Team Name: " + teamName;
    }
}