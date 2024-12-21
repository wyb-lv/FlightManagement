import java.io.*;

public abstract class statisticFile {
    protected abstract void loadInfofromFile();
    protected void checkExistFile(String fileName){
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist. Creating a new file.");
            try {
                if (file.createNewFile()) {
                    System.out.println("File created.");
                } else {
                    System.out.println("Failed to create file.");
                }
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }
    }
}