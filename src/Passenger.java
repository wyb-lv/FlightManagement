public class Passenger {
    private String ID;
    private String name;
    private String gender;
    private int age;
    private String phoneNumber;

    public Passenger(String ID, String name, int age, String gender, String phoneNumber){
        this.ID = ID;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }
    public int getAge() {
        return age;
    }
}