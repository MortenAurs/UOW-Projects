/*
Exercise 5 - Hashing
Morten Aursland
Student login: ma919
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ex5 {
    private static Node[] hashTable = new Node[300];
    private static int collisions = 0;
    private static int emptyEntries = 0;
    private static int longestChain = 0;

    public static void main(String[] args) {
        System.out.println("Please type filename: ");

        //Scanner input = new Scanner(System.in);
        String fileName = "ex5.txt"; //input.nextLine();
        BufferedReader file;
        try {
            file = new BufferedReader(new FileReader(fileName));
            String line = file.readLine();
            // Reading files as long as there is content
            while (line != null) {
//                get hash key from hash function
                int hashKey = hashFunction(Integer.parseInt(line));
//                insert value into hashTable at key
                insert(hashKey, Integer.parseInt(line));
                line = file.readLine();
            }
            // print nubmer of empty entries and collisions
            System.out.println("Number of empty entries: " + emptyEntries);
            System.out.println("Number of collisions: " + collisions);
            System.out.println("The longes chain is: " + longestChain + " long");
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Readline error");
        }
    }

    public static void insert(int key, int value){
        Node newNode = new Node(value);
        newNode.setNext(null);
        if(hashTable[key] == null){
            emptyEntries++;
            hashTable[key] = newNode;
        }else {
            collisions++;
            Node currentNode = hashTable[key];
            int longChain = 1;
            while(currentNode.next != null){
                longChain++;
                currentNode = currentNode.next;
            }
            if(longChain>longestChain){
                longestChain = longChain;
            }
           currentNode.next = newNode;
        }
    }

    public static int hashFunction(int value){
        int hashKey = value % 100;
        return hashKey;
    }
}

class Node {
    public int value;
    public Node next;

    public Node(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public Node getNext(){
        return next;
    }

    public void setNext(Node next){
        this.next = next;
    }


}



