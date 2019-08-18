import java.io.*;
import java.util.Scanner;

public class ass1 {

    private static String[] dictList = new String[370103];

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();

        readDictFile();
        //linearSearch();
        //binarySearch();
        SpellCheck sc = new SpellCheck();
        readSampleFile();
        sc.spellCheck();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time is " + (endTime - startTime) + " milliseconds");

    }

    public static void readSampleFile(){
        String sampleFile = "sample.txt";
        String[] sampleList = new String[700];
        String[] validWords = new String[500];
        int validIndex = 0;
        int i = 0;
        boolean found;
        Scanner input = null;
        int counter = 0;
        try {
            input = new Scanner(new File(sampleFile));
            while(input.hasNext()){
                found = false;
                sampleList[i] = input.next().toLowerCase().replaceAll("[,.!\"':?;-]", "");
                for(String str : validWords){
                    if(sampleList[i].equals(str)){
                        found = true;
                    }
                }
                if(!found) {
                    for (int k = 0; k < dictList.length; k++) {
                        if (sampleList[i].equals(dictList[k])) {
                            System.out.println("Sample: " + sampleList[i]);
                            System.out.println("Dictlist: " + dictList[k]);
                            System.out.println("Funnet");
                            validWords[validIndex] = sampleList[i];
                            validIndex++;
                        }
                    }
                }

                System.out.println();
                i++;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public static void readDictFile(){
        int counter = 0;
        String dictFile = "dictionary.txt";

        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(dictFile));
            String line = file.readLine();
            // Reading files as long as there is content
            while (line != null) {
                dictList[counter] = line;
                line = file.readLine();
                counter ++;
            }
            file.close();
            System.out.println("Number of words in the dictionary: " + counter);
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
        System.out.println("Five first emordnilap words (linear search:");
        // Iterating every word in the dictionary list until it finds the five first emordnilaps'
        while(counter < 5){
            int j = 0;
            // Reverse the word in the list and save it in reversedWord
            reversedWord = new StringBuilder(dictList[i]).reverse();
            // Iterating through the dictionary list again to find a match with the reversed word
            while(j < dictList.length-1){
                // Checking that the word is more than one letter and if the reversed word exists in the list
                if(dictList[j].length() > 1 && dictList[j].equals(String.valueOf(reversedWord))){
                    // Print out the original word and the reversed word
                    System.out.println(dictList[i] + " : " + reversedWord);
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
        int i = 0;
        String longestWord = null;
        System.out.println("First 10 emordnilap's (binary search):");
        // Iterating every word in the dictionary list until it finds the ten first emordnilaps'
        while(counter < 10){
            int result;
            // Reverse the word we will be searching for
            reversedWord = new StringBuilder(dictList[i]).reverse();
            // Checking if word has more than 1 letter
            if(dictList[i].length() > 1) {
                result = search(reversedWord);
                // Checking if result has a positive value
                if(result > 0){
                    // Finding the longest word
                    if(dictList[result].length() > length){
                        length = dictList[result].length();
                        longestWord = dictList[result];
                    }
                    counter++;
                    System.out.println(dictList[i] + " : " + dictList[result]);
                }
            }
            i++;
        }
        System.out.println("Longest emordnilap is " + longestWord + " which is " + longestWord.length() + " characters long.");
    }

    public static int search(StringBuilder reversedWord){
        int low = 0;
        int high = dictList.length-1;
        int mid;
        // Going through list
        while (low <= high){
            mid = (low+high) / 2;
            if(dictList[mid].compareTo(String.valueOf(reversedWord)) < 0) {
                low = mid + 1;
            }else if (dictList[mid].compareTo(String.valueOf(reversedWord)) > 0) {
                high = mid -1;
            }else{
                return mid;
            }
        }
        return -1;
    }
}

class SpellCheck{


    public SpellCheck(){

    }

    public void spellCheck(){
    }
}