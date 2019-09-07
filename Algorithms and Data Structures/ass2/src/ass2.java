import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class ass2 {

    public static void main(String[] args) {
        String dictFile = "ass2.txt";
        Customer customerQueue = new Customer();
        Event eventQueue = new Event();
        Random r = new Random();
        System.out.println("===== Add to customer list =====");
        System.out.println();
        for(int i = 0; i < 10; i++){
            double randNr = Math.round(100 * r.nextDouble() * 100d) / 100d;;
            customerQueue.addItem(randNr);
        }
        System.out.println();
        System.out.println("===== Remove from customer list and add to event list =====");
        System.out.println();
        while(!customerQueue.isEmpty()){
            double removed = customerQueue.removeItem();
            eventQueue.addItem(removed);
        }
        System.out.println();
        System.out.println("===== Remove from eventlist =====");
        System.out.println();
        while(!eventQueue.isEmpty()){
            eventQueue.removeRecord();
        }

        Scanner file;
        int counter = 0;
        String temp;
        try {
            file = new Scanner(new File(dictFile));
            int cntCheckouts = Integer.parseInt(file.nextLine());
            double serverEfficiency;
            while (file.hasNextLine()) {
                temp = file.nextLine();
                /*if(counter++ < cntCheckouts){
                    serverEfficiency = Double.parseDouble(file.nextLine());
                    System.out.println(serverEfficiency);
                }else{
                    System.out.println(file.nextLine());
                }*/

            }
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }
}

class Customer{

    public Customer(){

    }

    private double[] customerArray = new double[500];
    private int count = 0;
    private double arrivalTime;
    private double tallyTime;
    private boolean cash;

    public void addItem(double item){
        System.out.println("Added to customer array: " + item);
        customerArray[count] = item;
        count++;
    }

    public double removeItem(){
        double removed = customerArray[0];
        count--;
        for(int i = 0; i < count; i++){
            customerArray[i] = customerArray[i+1];
        }
        System.out.println("Removed from customerArray: " + removed);
        return removed;
    }

    public boolean isEmpty(){return count==0;}
}

class Event {
    // Add, remove, isempty
    private int eventType;
    private double tallyTime;
    private boolean cash;
    private double[] eventArray = new double[500];
    private int count = 0;

    public void addItem(double item) {
        eventArray[count] = item;
        System.out.println("Added to event array: " + item);
        count++;
        sort();

    }

    public void sort(){


        for(int i = 0; i < count; i++){
            double key = eventArray[i];
            int j = i-1;
            while ((j > -1) && eventArray[j] > key){
                eventArray[j+1] = eventArray[j];
                j--;
            }
            eventArray[j+1] = key;
        }
    }


    public void removeRecord(){
        double removed = eventArray[0];
        count--;
        for(int i = 0; i < count; i++){
            eventArray[i] = eventArray[i+1];
        }
        System.out.println("Removed from eventArray: " + removed);
    }

    public boolean isEmpty(){return count==0;}

}

class Server {

}