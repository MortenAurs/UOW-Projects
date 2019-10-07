import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ass3 {

    // Main function
    public static void main(String[] args) {
        Scanner file;
        String fileName = "ass3.txt";
        try {
            file = new Scanner(new File(fileName));
            String line = "";
            while (file.hasNextLine()) {
                line = file.nextLine();
                System.out.println(line);
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}