import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class ass2 {
    private static int cMaxCustomerQueue = 500;
    public static void main(String[] args) {
        String dictFile = "ass2.txt";
        CustomerQueue cQueue = new CustomerQueue();
        Customer customer;

        Random r = new Random();
        System.out.println("===== Add to customer list =====");
        System.out.println();
        for(int i = 0; i < 10; i++){
            double randNr = Math.round(100 * r.nextDouble() * 100d) / 100d;
            customer = new Customer(randNr, 0, false);
            cQueue.addCustomer(customer);
        }
        System.out.println();
        System.out.println("===== Remove from customer list and add to event list =====");
        System.out.println();
        EventQueue eventQueue = new EventQueue();
        while(!cQueue.isEmpty()){
            Customer removed = cQueue.removeItem();
            Event event = new Event(0, removed.getArrivalTime(), 0, false);
            eventQueue.addEvent(event);
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

class Customer {

    private double arrivalTime;
    private double tallyTime;
    private boolean cash;

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

    public Customer removeItem(){
        if(numCustomers==0){
            System.out.println("Customer queue is empty");
            System.exit(1);
        }
        Customer removed = customerArray[0];

        for(int i = 0; i < inIndex; i++){
            customerArray[i] = customerArray[i+1];
        }
        System.out.println("Removed from customerArray: " + removed.getArrivalTime());
        outIndex++;
        if(outIndex== cMaxCustomerQueue) outIndex=0;
        numCustomers--;
        return removed;
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
    private int eventType;
    private double eventTime, tallyTime;
    private boolean cash;

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
}
class EventQueue{
    private Event[] eventArray = new Event[500];
    private int numEvents = 0;

    public void addEvent(Event event) {
        int i = numEvents;

        while(i>0 && event.getEventTime() < eventArray[i-1].getEventTime()){
            eventArray[i] = eventArray[i-1];
            i--;
        }
        eventArray[i] = event;
        System.out.println("Added to event array:       " + event.getEventTime());
        System.out.println();
        numEvents++;
    }

    public void removeRecord(){

        Event removed = eventArray[0];
        numEvents--;
        for(int i = 0; i < numEvents; i++){
            eventArray[i] = eventArray[i+1];
        }
        
        System.out.println("Removed from eventArray: " + removed.getEventTime());
    }

    public boolean isEmpty(){return numEvents==0;}

}

class Server {

}