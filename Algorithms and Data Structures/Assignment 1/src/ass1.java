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
            //linearSearch();
            binarySearch();
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

    public static void binarySearch() {
        StringBuilder reversedWord;
        int counter = 0;
        int length = 0;
        String longestWord = null;
        System.out.println("First 10 emordnilap's:");
        // Iterating every word in the dictionary list until it finds the ten first emordnilaps'
        for (int i = 0; i < list.length; i++) {
            int result;
            // Reverse the word we will be searching for
            reversedWord = new StringBuilder(list[i]).reverse();
            // Checking if word has more than 1 letter
            if(list[i].length() > 1) {
                result = search(reversedWord);
                // Checking if result has a positive value
                if(result > 0){
                    // Finding the longest word
                    if(list[result].length() > length){
                        length = list[result].length();
                        longestWord = list[result];
                    }
                    counter++;
                    System.out.println(list[i] + " : " + list[result]);
                }
            }
        }
        System.out.println("Longest emordnilap is " + longestWord + " which is " + longestWord.length() + " characters long.");
    }

    public static int search(StringBuilder reversedWord){
        int low = 0;
        int high = list.length-1;
        int mid;
        // Going through list
        while (low <= high){
            mid = (low+high) / 2;
            if(list[mid].compareTo(String.valueOf(reversedWord)) < 0) {
                low = mid + 1;
            }else if (list[mid].compareTo(String.valueOf(reversedWord)) > 0) {
                high = mid -1;
            }else{
                return mid;
            }
        }
        return -1;
    }
}