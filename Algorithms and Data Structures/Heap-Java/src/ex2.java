/*
Exercise 2 - Implementing a Heap
Morten Aursland
Student login: ma919
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ex2 {
    private static int index = 0;

    public static void main(String[] args) {
        Heap heap = new Heap(100);
        System.out.println("Please type filename: ");

        Scanner input = new Scanner(System.in);
        String fileName = "../" + input.nextLine();
        BufferedReader file;
        try{
            file = new BufferedReader(new FileReader(fileName));
            String line = file.readLine();
            while(line!=null){ 
                heap.insert(Integer.parseInt(line));
                line = file.readLine();
            }
            file.close();
            // Printing out the min-heap
            System.out.println("Min-Heap:");
            heap.printHeap();
            // Making the heap a max-heap
            heap.makeHeap();
            //Printing out the max-heap
            System.out.println("Max-Heap");
            heap.printHeap();
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Readline error");
        }
    }
}
class Heap {
    private static int maxLength = 100;
    private static int[] heap = new int[maxLength];
    private static int n = 0;
    private static int printLength = 5;

    public Heap(int maxLength){
        this.maxLength = maxLength;
    }

    public void siftUp(int i){
        int child = i;
        if (child==0){
            return;
        }else{
            int parent = (child-1) / 2;
            if(heap[parent] > heap[child]) {

                swap(child, parent);
                siftUp(parent);
            }
        }
    }

    public void siftDown(int i){
        int parent = i;
        int leftChild = i*2+1;
        if(leftChild < n){
            int biggestChild = leftChild;
            int rightChild = leftChild+1;
            if(rightChild < n) {
                if (heap[leftChild] < heap[rightChild]) biggestChild = rightChild;
            }
            if (heap[parent] < heap[biggestChild]) {
                swap(parent, biggestChild);
                siftDown(biggestChild);
            }

        }
    }


    public void makeHeap(){
        for(int i = (n-1)/2; i >= 0; i--){
            siftDown(i);
        }
    }

    public void swap(int i, int parent){
        int temp = heap[parent];
        heap[parent] = heap[i];
        heap[i] = temp;
    }

    public void insert(int item){
        if(n > maxLength-1){
            System.out.println("Heap is full. Exiting..");
            exit();
        }
        heap[n++] = item;
        siftUp(n-1);
    }

    public void printHeap(){
        for(int i = 0; i < printLength; i++){
            System.out.println(heap[i]);
        }
    }

    public void exit(){
        System.exit(0);
    }

}

