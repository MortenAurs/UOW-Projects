/*
Assignment 1 - Step 1
Morten Aursland
Student login: ma919
 */

import java.io.*;
import java.util.Scanner;

public class ex3 {

    private static int[] data = new int[100];
    private static int[] forward = new int[100];
    private static int[] backward = new int[100];
    private static int cnt = 0;

    public static void main(String[] args) {
        readFile();
    }

    // Reading through dictionary.txt and adding the contents to dictList-array.
    public static void readFile() {
        Scanner input = new Scanner(System.in);
        //System.out.println("Enter filename: ");
        //String fileName = input.nextLine();
        String fileName = "ex3.txt";
        Scanner file;
        int what, where, probe;
        try {
            file = new Scanner(new File(fileName));
            while (file.hasNext()) {

                what = Integer.parseInt(file.next());
                where = Integer.parseInt(file.next());
                if(what == -1 && where == -1) {
                    break;
                }
                data[where] = what;

                forward[cnt] = where;
                backward[where] = cnt;
                cnt++;
            }
            while(file.hasNext()) {
                probe = Integer.parseInt(file.next());
                if(probe == -1) {
                    break;
                }
                if(backward[probe] > 0) {
                    System.out.println("Position " + probe + " has been initialized to value " + data[probe]);
                }else{
                    System.out.println("Position " + probe + " has not been initialized");
                }

            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}