import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MemberFile extends statisticFile{
    private static final String fileName = "employee_data.csv";
    private List<Member> memberList;
    public MemberFile(){
        this.memberList = new ArrayList<>();
        loadInfofromFile();
    }

    @Override
    protected void loadInfofromFile() {
        super.checkExistFile(fileName);
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    String id = data[0].trim();
                    String name = data[1].trim();
                    String gender = data[2].trim();
                    String role = data[3].trim();
                    int age = Integer.parseInt(data[4].trim());
                    Member member = new Member(id, name, gender, age, role);
                    memberList.add(member);
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("Error loading file: " + e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public List<Member> getMemberList() {
        return memberList;
    }
}
