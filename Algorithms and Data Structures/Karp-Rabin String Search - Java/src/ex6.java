import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex6 {

    public static void main(String[] args) {
        String dictFile = "ass6.txt";
        Scanner file;

        try {
            file = new Scanner(new File(dictFile));
            String target = file.nextLine();
            String search = file.nextLine();
            // Reading files as long as there is content
            System.out.println(target);
            System.out.println(search);
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}