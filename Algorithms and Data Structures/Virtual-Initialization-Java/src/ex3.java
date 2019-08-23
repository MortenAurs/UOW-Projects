/*
Assignment 1 - Step 1
Morten Aursland
Student login: ma919
 */

import java.io.*;
import java.util.Scanner;

public class ex3 {

    private static String[] data = new String[100];
    private static String[] forward = new String[100];
    private static String[] backward = new String[100];

    public static void main(String[] args) {
        readFile();
    }

    // Reading through dictionary.txt and adding the contents to dictList-array.
    public static void readFile() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter filename: ");
        String fileName = input.nextLine();
        Scanner file;
        try {
            file = new Scanner(new File(fileName));
            while (file.hasNextLine()) {
                System.out.println(file.nextLine());
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}