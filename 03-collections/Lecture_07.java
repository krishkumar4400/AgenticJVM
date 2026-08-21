

/**
 * TreeSet
 */

import java.util.*;

public class Lecture_07 {
    public static void main(String [] args) {

        // Data structure that stores unqiue elements in sorted order
        TreeSet<Integer>ts = new TreeSet<>();
        ts.add(1);
        ts.add(3);
        ts.add(0);
        ts.add(1);
        ts.add(2);
        ts.add(22);
        System.out.println(ts); // [0, 1, 2, 3, 22]
        
        TreeSet<Integer>ts1 = new TreeSet<>();
        ts1.add(12);
        ts1.add(9);
        ts1.add(1);
        ts1.add(4);
        System.out.println(ts1);
        
        System.out.println(ts1.floor(8)); // <= 8 -> 4
        System.out.println(ts1.ceiling(8)); // => 8 -> 9

        System.out.println(ts1.floor(4)); // <= 8 -> 4
        System.out.println(ts1.ceiling(9)); // => 8 -> 9
        
        for(var num : ts1) {
            System.out.println(num);
        }
    }
}