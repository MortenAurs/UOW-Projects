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
    private static int listLength = 100;
    private static Node[] hashTable = new Node[100];
    private static int collisions = 0;
    private static int longestChain = 0;
    private static int emptyEntries = listLength;

    // Main function
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
                int hashKey = hashFunction(Integer.parseInt(line));
                insert(hashKey, Integer.parseInt(line));
                line = file.readLine();
            }
            //printOut();
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

    // Inserting into hash table
    public static void insert(int key, int value){
        Node newNode = new Node(value);
        newNode.next = null;
        if(hashTable[key] == null){
            hashTable[key] = newNode;
            emptyEntries--;
        }else {
            collisions++;
            Node currentNode = hashTable[key];
            int cntChain = 2;
            while(currentNode.next != null){
                cntChain++;
                currentNode = currentNode.next;
            }
            if(cntChain>longestChain){
                longestChain = cntChain;
            }
           currentNode.next = newNode;
        }
    }

    // Prints out the content of the hashtable
    public static void printOut(){
        System.out.println("======= Printout =======");
        for(int i = 0; i < listLength; i++){
            if(hashTable[i] != null){
                Node node = hashTable[i];
                System.out.println("Index: " + i + " - Value: " + node.value);
                while (node.next != null){
                    node = node.next;
                }
            }
        }
    }

    // Calculating hash-key
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

}



