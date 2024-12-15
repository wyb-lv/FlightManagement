public class Employee {
    private String id;
    private String name;
    private String gender;
    private String position;
    private int age;
    private String username;
    private String password;

    // Constructor
    public Employee(String id, String name, String gender, String position, int age, String username, String password) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.position = position;
        this.age = age;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", position=" + position + ", age=" + age + ", username=" + username + ", password=" + password + "]";
    }
}
