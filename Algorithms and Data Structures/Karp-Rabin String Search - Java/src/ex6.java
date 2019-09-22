/*
Exercise 6 - Karp-Rabin String Search
Morten Aursland
Student login: ma919
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ex6 {
    public static int d = 256;
    public static int prime = 101;


    // Main function
    public static void main(String[] args) {
        String dictFile = "ex6.txt";
        Scanner file;

        try {
            file = new Scanner(new File(dictFile));
            String target = file.nextLine();
            String search = file.nextLine();
            file.close();
            int n = target.length();
            int m = search.length();

            int h = pow(d, m-1);

            int hash_s = hash(search, m);
            int hash_t = hash(target, m);

            for(int i = 0; i < n-m; i++){

                if(hash_s == hash_t){
                    if(search.equals(target.substring(i, i+m))){
                        System.out.println("String \"" + search + "\" found starting at location: " + i);
                    }

                }
                hash_t = roll(hash_t, target.charAt(i), target.charAt(i+m), h);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }
    }

    // Create rolling hash of next substring
    private static int roll(int ht, int c1, int c2, int h) {
        ht = (d * (ht - c1*h) + c2 ) % prime;
        if(ht<0){
            ht += prime;
        }
        return ht;
    }

    public static int pow(int d, int e){
        int p = 1;
        for(int i = 0; i < e; i++){
            p=(p*d) % prime;
        }
        return p;
    }
    public static int hash(String str, int m){
        int h=0;
        for(int i = 0; i < m; i++) {
            h = ((d * h) + str.charAt(i)) % prime;
        }
        return h;
    }

}