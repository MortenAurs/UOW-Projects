import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex7 {

    public static void main(String args[]) {
        Scanner file;
        Stack stack = new Stack();
        String fileName = "ex7.txt";

        try {
            file = new Scanner(new File(fileName));
            int counter = Integer.parseInt(file.next());
            while (file.hasNext()){
                int item = Integer.parseInt(file.next());
                stack.push(item);
            }
            System.out.println("==============================");
            System.out.println("TOP: " + stack.top());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
            System.out.println(stack.pop());
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}



class Stack {
    private int inIndex = 0;
    private int outIndex = 0;
    private int maxLength = 201;
    private int cntItem = 0;
    private int[] stack = new int[maxLength];

    public void push(int item){
        if(cntItem==maxLength){
            System.out.println("Stack is full");
            System.exit(0);
        }
        stack[inIndex] = item;
        inIndex++;
        if(inIndex == maxLength) inIndex = 0;

        cntItem++;
    }

    public int pop(){
        int removed = stack[outIndex];
        outIndex++;
        if(outIndex == maxLength) outIndex = 0;
        cntItem--;
        return removed;
    }

    public boolean isEmpty(){return cntItem == 0;}

    public int top(){
        if(isEmpty()){
            System.out.println("Stack is empty");
            System.exit(0);
        }
        System.out.println("outindex: " + outIndex);
        return stack[outIndex];
    }
    public void printList(){
        for (int i = 0; i < maxLength; i++){
            System.out.println(stack[i]);
        }
    }

}