import java.io.*;
import java.util.Scanner;

public class ass1 {

    private static String[] dictList = new String[400000];
    private static int nWords = 0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        readDictFile();
        System.out.println("Number of words in the dictionary: " + nWords);
        linearSearch();
        //binarySearch();
        //step3();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time is " + (endTime - startTime)  + " milliseconds");
    }

    // Reading through dictionary.txt and adding the contents to dictList-array.
    // Returning the length of the new dictlist
    public static void readDictFile() {
        String dictFile = "dictionary.txt";
        Scanner file;
        try {
            file = new Scanner(new File(dictFile));
            while (file.hasNextLine()) {
                dictList[nWords] = file.nextLine();
                nWords++;
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
    // Finding the first five emordnilaps and printing them out
    public static void linearSearch() {
        int listLength = nWords;
        String reversedWord;
        int counter = 0;
        int i = 0;
        System.out.println("Five first emordnilap words (linear search):");
        // Iterating every word in the dictionary list until it finds the five first emordnilaps'
        while (counter < 5) {
            reversedWord = reverse(dictList[i]);
            // Iterating through the dictionary list again to find a match with the reversed word
            for (int j = 0; j < listLength; j++){
                // Checking that the word is more than one letter and if the reversed word exists in the list
                if (dictList[j].length() > 1 && dictList[j].equals(reversedWord)) {
                    // Print out the original word and the reversed word while incrementing
                    // the emornilaps-found-counter by one
                    System.out.println(dictList[i] + " : " + reversedWord);
                    counter++;
                }
            }
            i++;
        }
    }

    // Reversing word and returning it
    public static String reverse(String word) {
        String reversedWord = "";
        for (int i = 0, j = (word.length()-1); i < word.length(); i++, j--) {
            reversedWord = reversedWord+word.charAt(j);
        }
        return reversedWord;
    }






    public static void binarySearch() {
        String reversedWord;
        int counter = 0;
        int length = 0;
        int i = 0;
        String longestWord = null;
        System.out.println("First 10 emordnilap's (binary search):");
        // Iterating every word in the dictionary list until it finds the ten first emordnilaps'
        while (counter < 10) {
            int result;
            // Reverse the word we will be searching for
            reversedWord = reverse(dictList[i]);
            // Checking if word has more than 1 letter
            if (dictList[i].length() > 1) {
                result = search(reversedWord);
                // Checking if result has a positive value
                if (result >= 0) {
                    // Finding the longest word
                    if (dictList[result].length() > length) {
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

    public static int search(String reversedWord) {
        int listLength = nWords;
        int low = 0;
        int high = listLength;
        int mid;
        // Going through list
        while (low <= high) {
            mid = (low + high) / 2;
            if (dictList[mid].compareTo(reversedWord) < 0) {
                low = mid + 1;
            } else if (dictList[mid].compareTo(reversedWord) > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


    public static void step3() {
        String sampleFile = "sample.txt";
        String[] sampleList = new String[700];
        String[] validWords = new String[700];
        int uniqueCounter = 0;
        int i = 0;
        boolean found;
        Scanner input = null;
        int validCounter = 0;
        try {
            input = new Scanner(new File(sampleFile));
            while (input.hasNext()) {
                found = false;

                sampleList[i] = input.next().toLowerCase().replaceAll("[,.!\"':?;-]", "");
                for (String str : validWords) {
                    if (str != null) {
                        //System.out.println("Str: " + str);
                        //System.out.println("Samplelist[i]: + " + sampleList[i]);
                        if (sampleList[i].equals(str)) {
                            //System.out.println("Funnet");
                            found = true;
                        }
                    }

                }
                for (int k = 0; k < dictList.length; k++) {
                    if (sampleList[i].equals(dictList[k])) {
                        if (!found) {
                            validWords[uniqueCounter] = sampleList[i];
                            uniqueCounter++;
                        }
                        validCounter++;
                    }
                }
                i++;
            }
            System.out.println("Valid words found in dictionary: " + validCounter);
            System.out.println("Unique words: " + uniqueCounter);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    public static void replace(){

    }
}