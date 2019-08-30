/*
Exercise 4 - BST Sort
Morten Aursland
Student login: ma919
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex4{
    private static String[] list = new String[300];
    private static int count = 0;

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

    public static void visit(Node node){
        if(node.left != null){
            visit(node.left);
        }
        if(count++<10){
            System.out.println(count);
            System.out.println(node.key);
        }
        if(node.right != null){
            visit(node.right);
        }
    }
    public static Node insertFirst(int value) {
        Node newNode = new Node(value);
        newNode.left = null;
        newNode.right = null;
        return newNode;
    }
    public static void insert(int item, Node node) {

        Node next;

        boolean left;
        if(item == node.key){
            return;
        }else if (item < node.key){
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


    public static Node find(int item, Node node){

        if (item == 0){
            return null;
        }
        if(item == node.key){
            return node;
        }else if(item < node.key){
            find(item, node.left);
        }else{
            find(item, node.right);
        }
        return null;
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