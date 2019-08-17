import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class ass1 {


    private static String[] list = new String[370103];

    public static void main(String[] args){
        int counter = 0;
        String dictFile = "dictionary.txt";
        long startTime = System.currentTimeMillis();
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(dictFile));
            String line = file.readLine();
            // Reading files as long as there is content
            while (line != null) {
                list[counter] = line;
                line = file.readLine();
                counter ++;
            }
            file.close();
            System.out.println("Number of words in the dictionary: " + counter);
            linearSearch();
            long endTime = System.currentTimeMillis();
            System.out.println("Execution time is " + (endTime - startTime) + " milliseconds");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Readline error");
        }
    }
    public static void linearSearch() {
        StringBuilder reversedWord;
        int counter = 0;
        int i = 0;
        System.out.println("Five first emordnilap words:");
        // Iterating every word in the dictionary list until it finds the five first emordnilaps'
        while(counter < 5){
            int j = 0;
            // Reverse the word in the list and save it in reversedWord
            reversedWord = new StringBuilder(list[i]).reverse();
            // Iterating through the dictionary list again to find a match with the reversed word
            while(j < list.length-1){
                // Checking that the word is more than one letter and if the reversed word exists in the list
                if(list[j].length() > 1 && list[j].equals(String.valueOf(reversedWord))){
                    // Print out the original word and the reversed word
                    System.out.println(list[i] + " : " + reversedWord);
                    counter++;
                }
                j++;
            }
            i++;
        }
    }
}