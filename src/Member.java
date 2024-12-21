public class Member {
    private String id;
    private String name;
    private String gender;
    private int age;
    private String role;

    public Member(String id, String name, String gender, int age, String role) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}