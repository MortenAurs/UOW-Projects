import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ass3 {

    // Main function
    public static void main(String[] args) {
        String[] verticesList = new String[20];
        Edgelist edgelist = new Edgelist();
        Scanner file;
        String fileName = "ass3.txt";
        try {
            file = new Scanner(new File(fileName));
            int verticesCnt = Integer.parseInt(file.next());
            int edgesCnt = Integer.parseInt(file.next());
            /*System.out.println(verticesCnt);
            System.out.println(edgesCnt);*/
            String vertices = "";
            String startEdge = "";
            String endEdge = "";
            int weight;
            String startVertices = "";
            String endVertices = "";
            int x, y;
            for(int i = 0; i < verticesCnt; i++){
                vertices = file.next();
                x = Integer.parseInt(file.next());
                y = Integer.parseInt(file.next());
                verticesList[i] = vertices;
                /*System.out.println("Vertices: " + vertices);
                System.out.println("X: " + x);
                System.out.println("Y: " + y);*/
            }
            for(int i = 0; i < edgesCnt; i++){
                startEdge = file.next();
                //System.out.println(startEdge);
                endEdge = file.next();
                //System.out.println(endEdge);
                weight = Integer.parseInt(file.next());
                //System.out.println(weight);
                Edge edge = new Edge(startEdge, endEdge, weight);
                edgelist.addEdge(edge);
            }
            for(int i = 0; i < 5; i++){
                System.out.print(verticesList[i] + ":  ");
                for(int j = 0; j < 99; j++){
                    if(edgelist.edgeList[j].startEdge.equals(verticesList[i])) {
                        System.out.print(edgelist.edgeList[j].endEdge + "(" + edgelist.edgeList[j].weight + ")  ");
                    }
                }
                System.out.println();
            }
            startVertices = file.next();
            endVertices = file.next();
        }catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}


class Edge {
    public String startEdge;
    public String endEdge;
    int weight;

    public Edge(String startEdge, String endEdge, int weight){
        this.startEdge = startEdge;
        this.endEdge = endEdge;
        this.weight = weight;
    }
}
class Edgelist{
    public Edge[] edgeList = new Edge[99];
    public int cntEdge = 0;

    public void addEdge(Edge edge){
        edgeList[cntEdge] = edge;
        cntEdge++;

    }
}