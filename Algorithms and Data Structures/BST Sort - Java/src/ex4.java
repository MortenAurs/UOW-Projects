/*
Exercise 4 - BST Sort
Morten Aursland
Student login: ma919
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex4{
    private static int count = 0;

    // Running main functino
    public static void main(String[] args) {
        Node root = null;
        String dictFile = "ex4.txt";

        Scanner file;
        try {
            file = new Scanner(new File(dictFile));
            int line = Integer.parseInt(file.nextLine());
            root = insertFirst(line);
            while (file.hasNextLine()) {
                line = Integer.parseInt(file.nextLine());
                insert(line, root);
            }
            visit(root);
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    // Function to list out the output
    public static void visit(Node node){
        if(node.left != null){
            visit(node.left);
        }

        count++;
        System.out.format("%5d", node.key);
        if(count % 10 == 0){
            System.out.println();
        }

        if(node.right != null){
            visit(node.right);
        }
        return;
    }

    // Function to insert the first number of the file
    public static Node insertFirst(int value) {
        Node newNode = new Node(value);

        return newNode;
    }

    // Function to insert the rest of the numbers in the file
    public static void insert(int item, Node node) {
        Node next;
        boolean left;
        if (item <= node.key){
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

            if(left){
                node.left = next;
            }else {
                node.right = next;
            }
        }
    }
}

class Node {
    int key;
    Node left;
    Node right;

    public Node(int item){
        key = item;
        left = null;
        right = null;
    }
}