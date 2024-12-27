public class AircrewMember {
    private String id;
    private String name;
    private String gender;
    private int age;
    private String position;

    public AircrewMember(String id, String name, String gender, int age, String position) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.position = position;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Role: " + position + "\n";
    }
}