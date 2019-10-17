import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex7 {
    private static int starterVertices = 0;

    public static void main(String args[]) {
        Scanner file;
        String fileName = "ex7.txt";
        try {
            file = new Scanner(new File(fileName));
            int vertices = Integer.parseInt(file.next());
            Graph graph = new Graph(vertices);
            while (file.hasNext()){
                int item = Integer.parseInt(file.next());
                int neighbour = Integer.parseInt(file.next());
                graph.addEdge(item, neighbour);
            }
            graph.bfs(starterVertices);
            //System.out.println(graph.toString());
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}

class Graph {
    private boolean adjMatrix[][];
    private int numVertices;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new boolean[numVertices][numVertices];
    }

    public void addEdge(int i, int j) {
        adjMatrix[i][j] = true;
        adjMatrix[j][i] = true;
    }

    public void removeEdge(int i, int j) {
        adjMatrix[i][j] = false;
        adjMatrix[j][i] = false;
    }

    public boolean isEdge(int i, int j) {
        return adjMatrix[i][j];
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            s.append(i + ": ");
            for (boolean j : adjMatrix[i]) {
                s.append((j ? 1 : 0) + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void bfs(int starter) {

        Queue queue = new Queue(numVertices);
        boolean[] vList = new boolean[numVertices];
        queue.add(starter);

        while (!queue.isEmpty()) {
            int current = queue.remove();
            vList[current] = true;
            for (int i = 0; i < adjMatrix[current].length; i++) {
                if(adjMatrix[current][i] == true){
                    if(!vList[i]){
                        vList[i] = true;
                        System.out.println(current + " " + i);
                        queue.add(i);
                    }

                }
            }
        }
    }
}

class Queue {
    private int[] queue;
    private int length;
    private int rear = 0;

    // adding a queue with a determined length
    public Queue (int length){
        this.length = length;
        queue = new int [length];
    }

    // adding item to the queue
    public void add (int item){
        if (rear > length) {
            System.out.println("Queue is full");
        }else {
            queue[rear] = item;
            rear++;
        }
    }

    // removin item from queue
    public int remove (){
        int remove = queue[0];
        if (rear != 0){
            for(int i = 1; i <= rear; i++) {
                queue[i-1] = queue[i];
            }
        }
        rear--;
        return remove;
    }

    // check if queue is empty
    public boolean isEmpty(){return(rear == 0);}
}