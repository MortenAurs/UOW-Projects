/*
Exercise 1 - Implementing a Stack
Morten Aursland
Student login: ma919
 */

import java.io.*;
import java.util.Scanner;

public class ex1 {
    private static int index = 0;
    private final static int length = 51;
    private static String[] stack = new String[length];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter filename:");
        String fileName = input.nextLine();
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(fileName));
            String line = file.readLine();
            while(line!=null) {
                push(line);
                line = file.readLine();
            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Readline error");
        }
        while(!isEmpty()){
            System.out.println("Top: " + top());
            pop();
        }

    }

    public static void push(String line){
        if(index==length-1){
            System.out.println("Stack is full");
            exit();
        }
        stack[index] = line;
        index++;
    }

    public static void pop(){
        if(!isEmpty()){
            index--;
        }else{
            System.out.println("Stack is empty");
        }
    }

    public static boolean isEmpty(){
        if(index==0){
            return true;
        }else{
            return false;
        }
    }

    public static String top(){
        if(isEmpty()){
            System.out.println("Stack is empty");
            exit();
        }
        return stack[index-1];
    }
    public static void exit() {
        System.exit(0);
    }
}
