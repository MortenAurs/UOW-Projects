/*
** CSCI203 Assignment 2
* Morten Aursland
* Student login: ma919
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.Math.round;

public class ass2 {
    public static String dictFile = "ass2.txt";
    public static CustomerQueue cQueue = new CustomerQueue();
    public static EventQueue eventQueue = new EventQueue();
    public static ServerQueue serverQueue = new ServerQueue();
    public static Scanner file;
    public static Customer customer;
    public static Event event;
    public static int arrivalEvent = -1;
    public static double time = 0;
    public static boolean cash = false;
    public static double payment_time;
    public static Server server;
    public static int serverId = 0;
    public static double finishedTime;
    public static double customerArrival = 0;
    public static double counter = 0;
    public static int cntCustomer = 0;
    public static double firstArrival;
    public static double lastFinished;
    public static int cntServers = 0;

    // Main function
    public static void main(String[] args) {
        try {
            file = new Scanner(new File(dictFile));
            String line = "";
            boolean readFile = true;
            while(readFile){
                readFile = false;
                line = file.nextLine();
                String serverEfficiency = "";
                for(int i = 0; i < line.length(); i++){
                    if(line.charAt(i) != '\t'){
                        serverEfficiency += line.charAt(i);
                        readFile = true;
                    }else{
                        customerArrival = Double.parseDouble(serverEfficiency);
                        readFile = false;
                        break;
                    }
                }
                if(readFile){
                    cntServers++;
                    Server server = new Server(Double.parseDouble(serverEfficiency), serverId);
                    serverQueue.addServerToServerArray(server);
                    serverId++;
                }
            }

            String[] values = line.split("\\t");
            double customerArrival = Double.parseDouble(values[0]);
            double tallyTime = Double.parseDouble(values[1]);
            String paymentMethod = values[2];
            if(paymentMethod.equals("cash")){cash = true;}
            event = new Event(-1, customerArrival, tallyTime, cash);
            eventQueue.addEvent(event);
            firstArrival = customerArrival;
            while(!eventQueue.isEmpty()){
                event = eventQueue.removeRecord();
                time = event.eventTime;
                if(event.eventType == arrivalEvent){
                    if(serverQueue.availableServer()){
                        Server fastestServer = serverQueue.findFastestServer(time);
                        fastestServer.busy = true;
                        if(event.cash){
                            payment_time = 0.3;
                        }else{
                            payment_time = 0.7;
                        }
                        finishedTime = time + (event.tallyTime * fastestServer.efficiency) + payment_time;
                        event = new Event(fastestServer.serverId, finishedTime);
                        eventQueue.addEvent(event);
                    }else{
                        customer = new Customer(customerArrival, tallyTime, cash);
                        cQueue.addCustomer(customer, time);
                    }
                    if(file.hasNext()){
                        customerArrival = Double.parseDouble(file.next());
                        tallyTime = Double.parseDouble(file.next());
                        paymentMethod = file.next();
                        if(paymentMethod.equals("cash")){
                            cash = true;
                        }
                        event = new Event(arrivalEvent, customerArrival, tallyTime, cash);
                        eventQueue.addEvent(event);
                    }
                }else{
                    cntCustomer++;
                    lastFinished = event.eventTime;
                    int serverNr = event.eventType;
                    server = serverQueue.getServer(serverNr);
                    server.busy = false;
                    if(!cQueue.isEmpty()){
                        counter++;
                        Customer nextCustomer = cQueue.removeCustomer();
                        Server fastestServer = serverQueue.findFastestServer(time);
                        fastestServer.busy = true;
                        if(event.cash){
                            payment_time = 0.3;
                        }else{
                            payment_time = 0.7;
                        }
                        finishedTime = time + (event.tallyTime * server.efficiency) + payment_time;
                        event = new Event(fastestServer.serverId, finishedTime, nextCustomer.tallyTime, nextCustomer.cash);
                        eventQueue.addEvent(event);

                    }
                }
            }
            System.out.println("Amount of customers served: " + cntCustomer);
            double totalTime = lastFinished-firstArrival;
            System.out.println("Time to serve all customers: " + (totalTime));
            System.out.println("Greatest length reached by customer queue: " + round(cQueue.longestQueue));
            System.out.println("Average length of customer queue: " + String.format("%.2f", (cQueue.average)));
            System.out.println("Average time spent by customer in customer queue: " + String.format("%.2f", (cQueue.timeSpent/cQueue.totCustomersInQueue)));
            double notInQueue = cntCustomer - cQueue.totCustomersInQueue;
            double percentNotInCQueue = (notInQueue/cntCustomer) * 100;
            System.out.println("Percentage of customers who's waiting time in the customer queue was 0: " + String.format("%.2f", percentNotInCQueue) + "%");
            System.out.println();
            System.out.println("==================== Server stats ====================");
            serverQueue.showServerStats(totalTime);
            file.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

    }
}

class Customer {

    public double arrivalTime;
    public double tallyTime;
    public boolean cash;

    // Constructor for Customer
    public Customer(double arrivalTime, double tallyTime, boolean cash){
        this.arrivalTime = arrivalTime;
        this.tallyTime = tallyTime;
        this.cash = cash;
    }
}

class CustomerQueue {
    private Customer[] customerArray = new Customer[500];
    private int numCustomers = 0;
    private int inIndex = 0;
    private int outIndex = 0;
    private int cMaxCustomerQueue = 500;
    public int longestQueue = 0;
    public int length = 0;
    double totalLength = 0;
    public double prevTime = 0;
    public int totCustomersInQueue = 0;
    public double average = 0;
    public double timeSpent = 0;

    // Function for adding customer with circular queue
    public void addCustomer(Customer customer, double time){
        if(numCustomers == cMaxCustomerQueue) {
            System.out.println("Queue is full");
            System.exit(1);
        }
        customerArray[inIndex] = customer;
        length = inIndex - outIndex;
        timeSpent += time - prevTime;
        average += ((time - prevTime) * length)/time;
        prevTime = time;
        inIndex++;
        length = inIndex - outIndex;
        totalLength += length;

        if(longestQueue < length){
            longestQueue = length;
        }
        if(inIndex == cMaxCustomerQueue) inIndex = 0;
        numCustomers++;
        totCustomersInQueue++;
    }

    // Function for removing customer from customer queue
    public Customer removeCustomer(){
        Customer removed = customerArray[outIndex];
        outIndex++;
        if(outIndex== cMaxCustomerQueue) outIndex=0;
        numCustomers--;
        return removed;
    }

    public boolean isEmpty(){return numCustomers==0;}
}

class Event {
    public int eventType;
    public double eventTime, tallyTime;
    public boolean cash;

    // Constructor for customer arrival events
    public Event(int eventType, double eventTime, double tallyTime, boolean cash){
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.tallyTime = tallyTime;
        this.cash = cash;
    }

    // Constructor for serverFinish event
    public Event(int eventType, double eventTime){
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    // toString function to write info on the event if needed
    public String toString(){
        return "Eventtype: " + eventType + ", event time: " + eventTime + ", Tally time: " + tallyTime + ", cash: " + cash;
    }
}
class EventQueue {
    public Event[] eventArray = new Event[500];
    public int numEvents = 0;
    public int maxLength = 500;

    // Function for adding event into eventqueue (heap)
    public void addEvent(Event event) {
        if (numEvents > maxLength - 1) {
            System.out.println("Heap is full. Exiting..");
            System.exit(0);
        }
        eventArray[numEvents++] = event;
        siftUp(numEvents - 1);

    }

    // Function for removing from queue
    public Event removeRecord(){
        Event removed = eventArray[0];
        swap(numEvents-1, 0);
        numEvents--;
        siftDown(0);
        return removed;
    }

    // Function for sifting down events after being removed
    public void siftDown(int i){
        int parent = i;
        int leftChild = i*2+1;
        if(leftChild < numEvents){
            int smallestChild = leftChild;
            int rightChild = leftChild+1;
            if(rightChild < numEvents) {
                if (eventArray[leftChild].eventTime > eventArray[rightChild].eventTime) smallestChild = rightChild;
            }
            if (eventArray[parent].eventTime > eventArray[smallestChild].eventTime) {
                swap(parent, smallestChild);
                siftDown(smallestChild);
            }

        }

    }

    // Function for sifting up events when being added
    public void siftUp(int i){
        int child = i;
        if (child==0){
            return;
        }else{
            int parent = (child-1) / 2;
            if(eventArray[parent].eventTime > eventArray[child].eventTime) {
                swap(child, parent);
                siftUp(parent);
            }
        }
    }

    // Swap function for the heap
    public void swap(int i, int parent){
        Event temp = eventArray[parent];
        eventArray[parent] = eventArray[i];
        eventArray[i] = temp;
    }

    // Check if the event heap is empty
    public boolean isEmpty(){return numEvents==0;}

}

class Server {

    public boolean busy = false;
    public int serverId;
    public double efficiency;
    public int cntCustomer = 0;
    public double busyTime = 0;
    public double idleTime = 0;

    // Constructor for Server
    public Server(double efficiency, int serverId){
        this.efficiency = efficiency;
        this.serverId = serverId;
    }

    // toString function to print out information about server
    public String toString(){
        String spaces = "             ";
        return "   " + serverId + spaces +
                efficiency + spaces +
                cntCustomer + spaces +
                String.format("%.2f", idleTime);
    }
}

class ServerQueue {
    public Server[] serverArray = new Server[20];
    public int cntServer = 0;

    // Function for adding server to array
    public void addServerToServerArray(Server server){
        serverArray[cntServer] = server;
        cntServer++;
    }

    // Function for finding the fastest
    public Server findFastestServer(double time){
        double fastest = 9999;
        Server server = new Server(fastest, -1);
        for(int i = 0; i < cntServer; i++){

            if(fastest > serverArray[i].efficiency && !serverArray[i].busy){
                fastest = serverArray[i].efficiency;
                server = serverArray[i];
            }
        }
        server.busyTime += time - server.busyTime;
        server.cntCustomer++;
        return server;
    }

    // Function for finding available server
    public boolean availableServer(){
        for(int i = 0; i < cntServer; i++){
            if(serverArray[i].busy == false){return true;}
        }
        return false;
    }

    // Function to get specific server based on serverID
    public Server getServer(int index) {
        return serverArray[index];
    }

    // Function to show server stats
    public void showServerStats(double totalTime) {
        System.out.println("ServerID      " + "Efficiency   " + "CustomersServed    " + "IdleTime   ");
        for(int i = 0; i < cntServer; i++){
            serverArray[i].idleTime = totalTime - serverArray[i].busyTime;
            System.out.println(serverArray[i].toString());
        }
    }
}


/*
Customer queue:
Implemented circle queue on the customer queue.
Event queue:
Implemented heap on the event queue. Sifting down when removing event from queue. Sifting up when adding.
Server queue:
Decided not to implement heap queue as this assignment talks about up to 20 servers so there is no need,
although I see the advances with it if there are many more servers.
 */