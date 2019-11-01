/*
 ** CSCI203 Assignment 3
 * Morten Aursland
 * Student login: ma919
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ass3 {
    private static Node[] verticesList = new Node[20];
    public static void main(String args[]) {

        String vertices = "";
        String startEdge = "";
        String endEdge = "";
        int weight;
        boolean aStar = false;

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
                Node node = new Node(vertices, x, y);
                verticesList[i] = node;
            }

            for (int i = 0; i < edgesCnt; i++) {
                startEdge = file.next();
                endEdge = file.next();
                weight = Integer.parseInt(file.next());
                gph.addEdge(startEdge, endEdge, weight);
            }
            String startV = file.next();
            String endV = file.next();
            //System.out.println("BFS:");
            //gph.bfs(startV);
            //System.out.println(gph.toString());
            int[] parents = gph.dijkstra(false, startV, endV);
            System.out.println("Start and end vertex: " + startV + " " + endV);
            System.out.println();
            System.out.println("Dijkstras shortest path is:");
            gph.printDijkstra(gph.convertToInt(startV), parents, gph.convertToInt(endV));
            System.out.println();
            System.out.println("A* shortest path is: ");
            parents = gph.dijkstra(true, startV, endV);
            gph.printDijkstra(gph.convertToInt(startV), parents, gph.convertToInt(endV));
            gph.secondShortest(startV, endV, parents);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    public static Node[] getVerticesList(){
        return verticesList;
    }
}

class Node {
    public String label;
    public int x, y;

    public Node(String label, int x, int y){
        this.label = label;
        this.x = x;
        this.y = y;
    }
}

class Graph {
    private int ascii = 97;
    private int[][] adjMatrix;
    private int numVertices;
    private int pathCounter;
    private int visitedCounter;

    public Graph(int numVertices) {
        this.numVertices = numVertices;
        adjMatrix = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                adjMatrix[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public void addEdge(String i, String j, int weight) {
        int intSrc = convertToInt(i);
        int intDst = convertToInt(j);
        adjMatrix[intSrc][intDst] = weight;
    }

    public void removeEdge(String i, String j) {
        int intSrc = convertToInt(i);
        int intDst = convertToInt(j);
        adjMatrix[intSrc][intDst] = 0;
    }

    public int convertToInt(String i) {
        int integer = i.charAt(0) - 'a';
        return integer;
    }

    public String convertToString(int i) {
        char temp = (char)(i + ascii);
        String letter = String.valueOf(temp);
        return letter;
    }

    public boolean isEdge(int i, int j) {
        return adjMatrix[i][j] != 0;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numVertices; i++) {
            s.append(convertToString(i) + ": ");
            for (int j = 0; j < numVertices; j++) {
                //s.append((j ? 1 : 0) + " ");

                if (adjMatrix[i][j] != 0) {
                    s.append(convertToString(j) + "(" + adjMatrix[i][j] + ") ");
                }
            }
            s.append("\n");
        }
        return s.toString();
    }

    public void printDijkstra(int sourceVertex, int[] parents, int dstVertex) {
        int i, u;
        int[] path = new int[10];
        int shortDist = 0;
        int count = 0;

        while(dstVertex!=sourceVertex){
            count++;
            path[count] = dstVertex;
            u = parents[dstVertex];

            shortDist += adjMatrix[u][dstVertex];
            dstVertex = u;
        }
        count++;
        path[count]=sourceVertex;

        for(i=count; i>=1; i--) {
            System.out.printf("%s  ", convertToString(path[i]));
        }
        System.out.print("\nVertices visited: " + visitedCounter);
        System.out.print("\nLength of path: " + pathCounter);
        System.out.print("\nLength of final path: " + count);
        System.out.printf("\nShortest distance is : %d\n", shortDist);
    }

    public int getMinimumVertex(boolean[] candidate, double[] distance, boolean aStar, String endV) {
        double minValue = Integer.MAX_VALUE;
        int vertex = -1;
        for (int i = 0; i < numVertices; i++) {
            if (!candidate[i]) {
                double eDist;
                if(aStar) {
                    eDist = eculidDist(i, endV);
                } else {
                    eDist=0;
                }
                if(distance[i]+eDist < minValue){
                    minValue = distance[i] + eDist;
                    vertex = i;
                }
            }
        }
        visitedCounter++;
        return vertex;
    }

    public int[] dijkstra(boolean aStar, String startV, String endV) {
        pathCounter = 0;
        visitedCounter = 0;
        double[] distance = new double[numVertices];

        boolean[] candidate = new boolean[numVertices];
        int[] parents = new int[numVertices];

        int src = convertToInt(startV);
        int dst = convertToInt(endV);
        parents[src] = -1;

        for (int i = 0; i < numVertices; i++) {
            if(aStar) {
                distance[i] = Integer.MAX_VALUE;
            }else{
                distance[i] = adjMatrix[0][i];
            }
        }

        distance[src] = 0;

        for (int i = 0; i < numVertices; i++) {
            int vertex_Min = getMinimumVertex(candidate, distance, aStar, endV);
            candidate[vertex_Min] = true;

            for (int vertex_V = 0; vertex_V < numVertices; vertex_V++) {
                if (adjMatrix[vertex_Min][dst] > 0) {
                    if (!candidate[vertex_V] && adjMatrix[vertex_Min][vertex_V] != Integer.MAX_VALUE) {
                        double newKey;
                        if(aStar){
                            newKey = adjMatrix[vertex_Min][vertex_V] + distance[vertex_Min] + eculidDist(vertex_V, endV);
                        }else {
                            newKey = adjMatrix[vertex_Min][vertex_V] + distance[vertex_Min];
                        }
                        if (newKey < distance[vertex_V]) {
                            pathCounter++;

                            parents[vertex_V] = vertex_Min;
                            distance[vertex_V] = newKey;
                        }
                    }
                }
            }
        }
        return parents;
    }

    private double eculidDist(int vertex_v, String endV) {
        ass3 ass = new ass3();
        Node[] verticesList = ass.getVerticesList();
        double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
        for(int i = 0; i < 20; i++) {
            if (convertToString(vertex_v).equals(verticesList[i].label)) {
                x1 = verticesList[i].x;
                y1 = verticesList[i].y;
            }
            if (convertToString(vertex_v).equals(endV)) {
                x2 = verticesList[i].x;
                y2 = verticesList[i].y;
            }
        }
        double x = x1 - x2;
        double y = y1 - y2;
        x = x*x;
        y = y*y;
        return (Math.sqrt(x+y));
    }

    public void secondShortest(String startV, String endV, int[] parents) {
        int i, u;
        int[] path = new int[10];
        int shortDist = 0;
        int count = 0;
    }
}

/*

Reading the data into an adjacency matrix.
Pre initializing the distance list with the vertices that are
    connected to the first node in the matrix. If it's A* it will initialize
    the list with MAX_INT value.
Have not been able to implement second shortest path.


What if we require that the second shortest path be longer than the shortest path?
    - Then we must have a check if number of vertices visited are greater than the other
What if the graph contains cycles?
    - If we have a negative cycle we must use a different algorithm (Bellman Ford)
What if the graph is undirected?
    - We can still use Dijkstra's algorithm as long as we know all the nodes
    that can be reached from every given node.
 */