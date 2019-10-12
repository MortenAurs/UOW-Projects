import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ass3 {

    public static void main(String args[]) {
        String[] verticesList = new String[20];
        String vertices = "";
        String startEdge = "";
        String endEdge = "";
        int weight;
        int x, y;

        Scanner file;
        String fileName = "ass3.txt";
        try {
            file = new Scanner(new File(fileName));
            int verticesCnt = Integer.parseInt(file.next());
            int edgesCnt = Integer.parseInt(file.next());
            Graph gph = new Graph(verticesCnt);

            for (int i = 0; i < verticesCnt; i++) {
                vertices = file.next();
                x = Integer.parseInt(file.next());
                y = Integer.parseInt(file.next());
                verticesList[i] = vertices;
            }
            
            for (int i = 0; i < edgesCnt; i++) {
                startEdge = file.next();
                endEdge = file.next();
                weight = Integer.parseInt(file.next());
                gph.addEdge(startEdge, endEdge, weight);
            }
            gph.printGraph(gph);

        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}

class Node {
    String source;
    String destination;
    int weight;
    Node next;

    public Node(String source, String destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        next = null;
    }
}

class adjList {
    Node head;
}

class Graph {
    int V;
    adjList String[];

    public Graph(int V) {
        this.V = V;
        String = new adjList[V];
        for (int i = 0; i < V; i++) {
            String[i] = new adjList();
            String[i].head = null;
        }
    }

    // Adding edges to the array
    public void addEdge(String source, String destination, int weight) {

        Node adn = new Node(source, destination, weight);

        // add this node to the source adj List
        int intSrc = source.charAt(0) -'a';
        int intDst = source.charAt(0) -'a';
        adn.next = String[intSrc].head;
        String[intDst].head = adn;
    }

    // Going through the array and printing out 5 first vertices and the connections with weight
    public void printGraph(Graph gph) {
        int listLength = 5;
        Node ad;
        for (int i = 0; i < listLength; i++) {
            ad = gph.String[i].head;
            if(ad!=null){
                System.out.print(ad.source + ":  ");
                while (ad != null) {
                    System.out.print("  " + ad.destination + "(" + ad.weight + ")");
                    ad = ad.next;
                }
            }
            System.out.println();
        }
    }
}