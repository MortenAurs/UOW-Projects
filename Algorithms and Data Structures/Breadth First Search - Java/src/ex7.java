import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex7 {
    private static Stack stack = new Stack();

    public static void main(String args[]) {
        Scanner file;

        String fileName = "ex7.txt";

        try {
            file = new Scanner(new File(fileName));
            int starter = Integer.parseInt(file.next());
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

    public int bfs (starter, adj){
        int[] vList = new int[20];


        stack.push(starter);
        int i = 0;
        while (!stack.isEmpty()){
            int current = stack.pop();
            vList[i] = current;

        }
        current = q.dequeue()
        add current to v
        for each n in adj(current)
        if n is not in v then
        q.enqueue(n)
        fi
                rof
        elihw
        return v

    }
}



class Stack {
    private int inIndex = 0;
    private int outIndex = 0;
    private int maxLength = 100;
    private int cntItem = 0;
    public int[] stack = new int[maxLength];

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