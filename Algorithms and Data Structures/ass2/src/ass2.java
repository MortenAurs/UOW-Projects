import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ass2 {
    private static int cMaxCustomerQueue = 500;
    public static void main(String[] args) {
        String dictFile = "ass2.txt";
        CustomerQueue cQueue = new CustomerQueue();
        EventQueue eventQueue = new EventQueue();
        ServerQueue serverQueue = new ServerQueue();
        Scanner file;
        Customer customer;
        Event event;
        boolean cash = false;
        double serverEfficiency;
        int counter = 0;
        double payment_time;
        Server server;

        String temp;
        try {
            file = new Scanner(new File(dictFile));
            for(int i = 0; i < 6; i++){
                server = new Server(Double.parseDouble(file.nextLine()), i);
                serverQueue.addServerToServerArray(server);
            }

            double customerArrival = Double.parseDouble(file.next());
            double tallyTime = Double.parseDouble(file.next());
            String paymentMethod = file.next();
            System.out.println(customerArrival);
            System.out.println(tallyTime);
            System.out.println(paymentMethod);
            if(paymentMethod.equals("cash")){
                cash = true;
            }
            event = new Event(-1, customerArrival, tallyTime, cash);
            eventQueue.addEvent(event);

            while(!eventQueue.isEmpty()){

                Event nextEvent = eventQueue.removeRecord();
                double currentTime = nextEvent.eventTime;
                System.out.println("Event");
                System.out.println(event.toString());
                System.out.println("NextEvent");
                System.out.println(nextEvent.toString());
                if(nextEvent.eventType == event.eventType){
                    if(serverQueue.availableServer()){
                        System.out.println("======= Available server =======");
                        Server fastestServer = serverQueue.findFastestServer();
                        fastestServer.busy = true;
                        event.eventType = fastestServer.serverId;
                        //System.out.println("fastestserver: " + fastestServer.toString());
                        if(nextEvent.cash){
                            payment_time = 0.3;
                        }else{
                            payment_time = 0.7;
                        }
                        System.out.println();
                        System.out.println("Event: " + event.eventType);
                        System.out.println();
                        eventQueue.addEvent(event);
                        double finishTime = (nextEvent.tallyTime * fastestServer.efficiency) + payment_time;
                    }else{
                        System.out.println("inne");
                        customer = new Customer(customerArrival, tallyTime, cash);
                        cQueue.addCustomer(customer);
                    }
                    if(file.hasNext()){
                        System.out.println("Inne i filhasnext");
                        customerArrival = Double.parseDouble(file.next());
                        tallyTime = Double.parseDouble(file.next());
                        paymentMethod = file.next();
//                        System.out.println(customerArrival);
//                        System.out.println(tallyTime);
//                        System.out.println(paymentMethod);
                        if(paymentMethod.equals("cash")){
                            cash = true;
                        }
                        event = new Event(-1, customerArrival, tallyTime, cash);
                        eventQueue.addEvent(event);
                    }
                    System.out.println(eventQueue.isEmpty());
                }else{
                    System.out.println("============================ INNE =====================================");
                    int serverNr = nextEvent.eventType;
                    server = serverQueue.getServer(serverNr);
                    server.busy = false;
                    if(!cQueue.isEmpty()){
                        Customer nextCustomer = cQueue.removeCustomer();
                        event = new Event(-1, nextCustomer.arrivalTime, nextCustomer.tallyTime, nextCustomer.cash);
                        Server fastestServer = serverQueue.findFastestServer();
                        fastestServer.busy = true;
                        eventQueue.addEvent(event);
                        if(nextEvent.cash){
                            payment_time = 0.3;
                        }else{
                            payment_time = 0.7;
                        }
                        double finishTime = (nextEvent.tallyTime * fastestServer.efficiency) + payment_time;

                    }


                }

            }
            cQueue.showCustomerQueue();


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

    public Customer(double arrivalTime, double tallyTime, boolean cash){
        this.arrivalTime = arrivalTime;
        this.tallyTime = tallyTime;
        this.cash = cash;
    }
    public Customer(){

    }

    public double getTallyTime(){
        return tallyTime;
    }

    public double getArrivalTime(){
        return arrivalTime;
    }
}

class CustomerQueue {
    private Customer[] customerArray = new Customer[500];
    private int numCustomers = 0;
    private int inIndex = 0;
    private int outIndex = 0;
    private int cMaxCustomerQueue = 500;

    // Function for adding customer
    public void addCustomer(Customer customer){
        if(numCustomers == cMaxCustomerQueue) {
            System.out.println("Queue is full");
            System.exit(1);
        }
        customerArray[inIndex] = customer;
        System.out.println("Added to customer array: " + customer.getArrivalTime());
        inIndex++;
        if(inIndex == cMaxCustomerQueue) inIndex = 0;
        numCustomers++;
    }

    public Customer removeCustomer(){
        Customer removed = customerArray[outIndex];
        System.out.println("Removed from customerArray: " + removed.getArrivalTime());
        outIndex++;
        if(outIndex== cMaxCustomerQueue) outIndex=0;
        numCustomers--;
        return removed;
    }

    public void showCustomerQueue(){
        for(int i = outIndex; i<inIndex; i++){
            System.out.println("I: " + i);
            System.out.println(customerArray[i].arrivalTime);
            System.out.println(customerArray[i].tallyTime);
            System.out.println(customerArray[i].cash);
        }
    }

    public Customer[] getCustomerArray(){
        return customerArray;
    }

    public boolean isEmpty(){return numCustomers==0;}
}

class Event {
/*
eventType:
    Suggestion: make eventType -1 to indicate arrival event.
    Give it a number from 0 to n-1 servers to indicate which server has triggered the event

 */
    public int eventType;
    public double eventTime, tallyTime;
    public boolean cash;

    public Event(int eventType, double eventTime, double tallyTime, boolean cash){
        this.eventType = eventType;
        this.eventTime = eventTime;
        this.tallyTime = tallyTime;
        this.cash = cash;
    }

    public double getEventTime(){
        return eventTime;
    }

    public int getEventType() {
        return eventType;
    }

    public String toString(){
        return "Eventtype: " + eventType + ", event time: " + eventTime + ", Tally time: " + tallyTime + ", cash: " + cash;
    }
}
class EventQueue {
    public Event[] eventArray = new Event[500];
    public int numEvents = 0;
    public int maxLength = 500;

    public void addEvent(Event event) {
        if (numEvents > maxLength - 1) {
            System.out.println("Heap is full. Exiting..");
            System.exit(0);
        }
        System.out.println("Added to eventArray: " + event.eventTime);
        eventArray[numEvents++] = event;
        siftUp(numEvents - 1);

    }

    public Event removeRecord(){
        Event removed = eventArray[0];
        swap(numEvents-1, 0);
        numEvents--;
        siftDown(0);
        //System.out.println("Removed from eventArray: " + removed.eventTime);
        return removed;
    }

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

    public void swap(int i, int parent){
        Event temp = eventArray[parent];
        eventArray[parent] = eventArray[i];
        eventArray[i] = temp;
    }

    public boolean isEmpty(){return numEvents==0;}

}

class Server {

    public boolean busy = false;
    public double efficiency;
    public int serverId;
    // double to count idle time

    public Server(double efficiency, int serverId){
        this.efficiency = efficiency;
        this.serverId = serverId;

    }
    public Server () {

    }
    public String toString(){
        return "Busy: " + busy + "\nEfficiency: " + efficiency + "\nserverID: " + serverId;
    }


}

class ServerQueue {
    public Server[] serverArray = new Server[20];
    public int cntCustomer = 0;


    public void addServerToServerArray(Server server){
        serverArray[cntCustomer] = server;
        cntCustomer++;
    }

    public void removeCustomerFromServer(){
        Server removed = serverArray[0];
        for(int i = 0; i < cntCustomer; i++){
            serverArray[i] = serverArray[i+1];
        }
    }

    public Server findFastestServer(){
        double fastest = 99;
        Server server = new Server(fastest, -1);
        for(int i = 0; i < cntCustomer; i++){
            if(fastest > serverArray[i].efficiency && !serverArray[i].busy){
                fastest = serverArray[i].efficiency;
                server = serverArray[i];
            }
        }
        System.out.println("SERVER: " + server.toString());
        return server;
    }

    public boolean availableServer(){
        for(int i = 0; i < cntCustomer; i++){
            if(serverArray[i].busy == false){
                return true;
            }
        }
        return false;
    }

    public Server getServer(int index) {


        return serverArray[index];
    }


}