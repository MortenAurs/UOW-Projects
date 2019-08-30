/*
Assignment 1 - Step 1
Morten Aursland
Student login: ma919
 */

import java.io.*;
import java.util.Scanner;

public class ass1 {

    private static String[] dictList = new String[400000];
    private static String[] sampleList = new String[40000];
    private static int nWords = 0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        readDictFile();
        System.out.println("Number of words in the dictionary: " + nWords);
        //linearSearch();
        //binarySearch();
        step3();
        long endTime = System.currentTimeMillis();
        System.out.println("Execution time is " + (endTime - startTime)  + " milliseconds");
    }

    // Reading through dictionary.txt and adding the contents to dictList-array.
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
        int result = 0;
        String longestWord = null;
        System.out.println("First 10 emordnilap's (binary search):");
        // Iterating words in the dictionary list until it finds the ten first emordnilaps'
        while (counter < 10) {
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
        // Checking dictlist from same place it stopped searching for emornilaps
        for(int j = result; j < dictList.length; j++){
            // Finding the longest word
            if(dictList[j] != null){
                if (dictList[j].length() > length) {
                    length = dictList[j].length();
                    longestWord = dictList[j];
                }
            }
        }
        System.out.println("Longest emordnilap is " + longestWord + " which is " + longestWord.length() + " characters long.");
    }

    // Binary search the dictList
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
        Node root = null;


        String[] validWords = new String[40000];
        String[] notAllowed = new String[] {"." , "," , ":" , "'" , "!" , "?", "\"" , ";" , "-"};

        int uniqueCounter = 0;
        int index = 0;
        Scanner input = null;
        int validCounter = 0;
        try {
            input = new Scanner(new File(sampleFile));
            while (input.hasNext()) {
                String line = input.nextLine().toLowerCase();
                String word = "";
                // Going through line by line
                for(int i = 0; i < line.length(); i++){
                    // Seperating words that are separated by space
                    if(line.charAt(i) == ' '){
                        // Only adding to list words that have content(no empty words)
                        if(word != "") {
                            sampleList[index] = word;
                            //insert(word, root);

                            index++;
                            validCounter++;
                            word = "";
                        }


                    }else {
                        boolean found = false;
                        for(int j = 0; j < notAllowed.length; j++){
                            if(notAllowed[j].equals(String.valueOf(line.charAt(i)))){
                                found = true;
                            }
                        }
                        if(!found){
                            word += line.charAt(i);
                        }
                    }
                }
                if(word != "") {

                    sampleList[index] = word;
                    index++;

                    validCounter++;
                }
            }

            int sampleLength = index;
            int dictLength = nWords;
            int validCount = 0;
            for(int i = 0; i < sampleLength; i++){
                for(int j = 0; j < dictLength; j++){
                    if(sampleList[i].equals(dictList[j])){
                        validWords[validCount] = sampleList[i];
                        validCount++;
                    }
                }
            }
            System.out.println("Total number of valid words is : " + validCounter);
            System.out.println("Total number of unique words is : " + index);
            System.out.println("Total number of unique words found in dictionary is : " + validCount);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

    }
    public static Node insert_first(String value){
        Node newNode = new Node(value);
        newNode.key = value;
        return newNode;
    }
    public static void insert(String item, Node node){
        Node next; boolean left;
        if(item == node.key) return;
        else if (item.compareTo(node.key) < 0){
            next = node.left; left = true;
        }else{
            next = node.right; left = false;
        }
        if (next != null){
            insert (item, next);
        }else{
            next = new Node(null);
            next.key = item;
            next.left = null;
            next.right = null;
            if(left){
                node.left = next;
            }else{
                node.right=next;
            }
        }
    }

}

class Node {
    String key;
    Node left, right;

    public Node(String item){
        key = item;
        this.left = this.right = null;
    }
}