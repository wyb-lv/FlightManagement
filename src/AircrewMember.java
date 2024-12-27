public class AircrewMember {
    private String id;
    private String name;
    private String position;

    public AircrewMember(String id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public String getId() {
        return id;
    }
    public String getName() {return name;}

    @Override
    public String toString() {return "ID: "+ id + "Name: " + name + ", Role: " + position + "\n";
    }
}