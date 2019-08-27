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
    private static int validCount = 0;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter filename: ");
        String fileName = input.nextLine();
        Scanner file;
        try {
            file = new Scanner(new File(fileName));
            // Reading contents of textfile
            while (file.hasNext()) {
                boolean firstWord = true;
                String strWhat = "";
                String strWhere = "";
                String line = file.nextLine();
                // Checking contents of the line in the text file
                for(int i = 0; i < line.length(); i++){
                    if(firstWord) {
                        // Seperating numbers that are seperated with tab
                        if (line.charAt(i) != '\t') {
                            strWhat += String.valueOf(line.charAt(i));
                        } else {
                            firstWord = false;
                        }
                    }else{
                        strWhere += String.valueOf(line.charAt(i));
                    }
                }
                int what = Integer.parseInt(strWhat);
                int where = Integer.parseInt(strWhere);
                // Breaking if what and where is -1
                if(what == -1 && where == -1) {
                    break;
                }

                data[where] = what;
                forward[validCount] = where;
                backward[where] = validCount;
                validCount++;
            }
            while(file.hasNext()) {
                String strProbe = "";
                String line = file.nextLine();
                // Getting content of file and removing tabs.
                for(int i = 0; i < line.length(); i++){
                    if(line.charAt(i) != '\t'){
                        strProbe += String.valueOf(line.charAt(i));
                    }
                }
                int probe = Integer.parseInt(strProbe);
                if(probe == -1) {
                    break;
                }
                // Checking if position has been initialized
                if(backward[probe] > 0 && backward[probe] < validCount && forward[backward[probe]] == probe) {
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