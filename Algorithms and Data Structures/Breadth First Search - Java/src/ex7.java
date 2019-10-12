import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex7 {

    public static void main(String args[]) {
        Scanner file;
        String fileName = "ex7.txt";
        try {
            file = new Scanner(new File(fileName));
            while (file.hasNextLine()){
                System.out.println(file.nextLine());
            }
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}