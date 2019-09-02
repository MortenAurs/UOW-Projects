/*
Assignment 1 - Step 1
Morten Aursland
Student login: ma919
 */

import java.io.*;
import java.util.Scanner;
import java.util.function.DoubleToIntFunction;

public class ass1 {

    private static String[] dictList = new String[400000];
    private static String[] sampleList = new String[40000];
    private static String[] validInDict = new String[300];
    private static int nWords = 0;
    private static int uniqueWords = 0;
    private static int validCnt = 0;
    private static int validDict = 0;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        readDictFile();
        //System.out.println("Number of words in the dictionary: " + nWords);
        //step1();
        System.out.println( "===== STEP 2 =====");
        step2();
        System.out.println();
        System.out.println( "===== STEP 3 =====");
        System.out.println();
        step3();
        System.out.println();
        System.out.println( "===== STEP 4 =====");
        System.out.println();
        step4();
        long endTime = System.currentTimeMillis();
        //System.out.println("Execution time is " + (endTime - startTime)  + " milliseconds");
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
    public static void step1() {
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

    public static void step2() {
        String reversedWord;
        int counter = 0;
        int length = 0;
        int result = 0;
        int validRes = 0;
        int listLength = nWords;
        String longestWord = null;
        System.out.println("First 10 emordnilap's (binary search):");
        // Iterating words in the dictionary list until it finds the ten first emordnilaps'
        for(int i = 0; i < listLength; i++){
            String word = dictList[i];
            if(word.length() > 1){
                reversedWord = reverse(word);
                result = binarySearch(reversedWord);
                if(result>=0){
                    validRes = result;
                    // Finding the longest word
                    if (reversedWord.length() > length) {
                        length = reversedWord.length();
                        longestWord = reversedWord;
                    }
                    if(counter < 10){
                        System.out.println(dictList[i] + " : " + dictList[result]);
                    }
                    counter++;
                }
            }
        }



        // Checking dictlist from same place it stopped searching for emornilaps
        /*for(int j = validRes; j < dictList.length; j++){
            // Finding the longest word
            if(dictList[j] != null){
                if (dictList[j].length() > length) {
                    length = dictList[j].length();
                    longestWord = dictList[j];
                }
            }
        }*/
        System.out.println("Longest emordnilap is " + longestWord + " which is " + longestWord.length() + " characters long.");
    }

    // Binary search the dictList
    public static int binarySearch(String word) {
        int listLength = nWords;
        int low = 0;
        int high = listLength-1;
        int mid;

        // Going through list
        while (low <= high) {
            mid = (low + high) / 2;
            if (dictList[mid].compareTo(word) < 0) {
                low = mid + 1;
            } else if (dictList[mid].compareTo(word) > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    // Function for step 3 of assignment
    public static void step3() {
        String sampleFile = "sample.txt";
        Node root = null;

        String[] notAllowed = new String[] {"." , "," , ":" , "'" , "!" , "?", "\"" , ";" , "-"};

        int index = 0;
        boolean firstWord = true;
        Scanner input = null;

        try {
            input = new Scanner(new File(sampleFile));
            String word = "";
            String line = input.nextLine().toLowerCase();
            // Going through line char by char to find first word
            for(int i = 0; i < line.length(); i++) {
                // Seperating words that are separated by space
                if (line.charAt(i) == ' ') {
                    if (word != "") {
                        validCnt++;
                        if(firstWord){
                            root = insertFirst(word);
                            word = "";
                            firstWord = false;
                        }else{
                            insert(word, root);
                            word = "";
                        }
                    }
                }else{
                    word += line.charAt(i);
                }
            }
            if(word != ""){
                validCnt++;
            }

            while (input.hasNext()) {
                line = input.nextLine().toLowerCase();
                word = "";
                // Going through line char by char
                for(int i = 0; i < line.length(); i++){
                    // Seperating words that are separated by space
                    if(line.charAt(i) == ' '){
                        // Only adding to list words that have content(no empty words)
                        if(word != "") {
                            validCnt++;
                            insert(word, root);
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
                    validCnt++;
                    insert(word, root);
                }
            }
            visit(root);

            int sampleLength = uniqueWords;
            int result;
            // Searching with binary search in dictionary list
            for(int i = 0; i < sampleLength; i++){
                result = binarySearch(sampleList[i]);
                if(result >= 0){
                    validInDict[validDict] = sampleList[i];
                    validDict++;
                }
            }

            System.out.println("Total number of valid words is : " + validCnt);
            System.out.println("Total number of unique words is : " + sampleLength);
            System.out.println("Total number of unique words found in dictionary is : " + validDict);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    // Inserting first word into
    public static Node insertFirst(String value) {
        Node newNode = new Node(value);
        sampleList[uniqueWords] = newNode.key;
        newNode.left = null;
        newNode.right = null;
        return newNode;
    }

    // Inserting into BST tree
    public static void insert(String item, Node node) {

        Node next;
        boolean left;
        if(item.equals(node.key)){
            return;
        }else if (item.compareTo(node.key) < 0){
            next = node.left;
            left = true;
        }else{
            next = node.right;
            left = false;
        }
        if (next != null) {
            insert(item, next);
        }else{
            next = new Node(item);
            next.key = item;

            if(left){
                node.left = next;
            }else {
                node.right = next;
            }
        }
    }

    // Going through BST tree and inserting into list
    public static void visit(Node node){
        int sampleLength = validCnt;
        if(node.left != null){
            visit(node.left);
        }
        if(uniqueWords++<validCnt){
            sampleList[uniqueWords-1] = node.key;
        }

        if(node.right != null){
            visit(node.right);
        }
    }

    public static void step4(){


        int listLength = nWords;
        String word1;
        String word2 = "";
        String mostAnagrams = "";
        String longestWord = "";
        int cntAnagrams = 0;
        int cntMostAnagrams = 0;
        int cntLongestFound = 0;
        int cntWordsWithAnagrams = 0;


        boolean found;
        for(int i = 0; i < validDict; i++){
            int cntFound = 0;

            String anagram = "";
            word1 = validInDict[i];
            if(word1.length() > 2){
                for(int j = 0; j < listLength; j++) {
                    word2 = dictList[j];
                    if (word2.length() > 2 && !word1.equals(word2)) {
                        found = anagram(word1, word2);
                        if (found) {
                            cntFound++;
                            cntAnagrams++;
                            if(cntLongestFound < word1.length()){
                                cntLongestFound = word1.length();
                                longestWord = word1;
                            }
                            if(cntFound > cntMostAnagrams){
                                cntMostAnagrams = cntFound;
                                mostAnagrams = word1;
                            }
                            anagram += word2 + " ";
                        }
                    }
                }

                // If word is two letters long we reverse it and binary search for it in dictionary
            }else if(word1.length() == 2){
                String reversedWord = reverse(word1);
                int result = binarySearch(reversedWord);
                if(result >= 0){
                    cntAnagrams++;
                    anagram += reversedWord + " ";
                }
            }
            if(!anagram.equals("")) {
                if(cntWordsWithAnagrams<10) {
                    System.out.println(word1 + ": " + anagram);
                }
                cntWordsWithAnagrams++;
            }

        }
        System.out.println();
        System.out.println("The word with the most anagrams: " + mostAnagrams + " with " + cntMostAnagrams + " anagrams.");
        System.out.println("The longest word with anagram(s): " + longestWord + " with " + cntLongestFound + " letters in the word.");
        System.out.println("Total number of words with anagrams: " + cntWordsWithAnagrams);
        System.out.println("Total number of anagrams found: " + cntAnagrams);

    }
    public static boolean anagram(String word1, String word2){
        int n1 = word1.length();
        int n2 = word2.length();

        if(n1 != n2){
            return false;
        }
        int[] counter = new int[256];

        for(char c : word1.toCharArray()) {
            counter[c]++;
        }
        for(char c : word2.toCharArray()){
            counter[c]--;
        }
        for(int i : counter){
            if(i != 0) return false;
        }
        return true;
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