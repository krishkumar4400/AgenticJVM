import java.util.HashSet;

/**
 * Set interface
 * 
 * - HashSet
 */

import java.util.*;

public class Lecture_06 {

    public static void main(String[] args) {

        // Data structure that stores unique elements in sorted order
        HashSet<Integer> hs = new HashSet<>();
        hs.add(2);
        hs.add(1);
        hs.add(4);
        hs.add(4);
        hs.add(5);
        hs.add(3);
        System.out.println(hs);
        
        for(Integer num : hs) {
            System.out.println(num);
        }

        for(var num : hs) {
            System.out.println(num);
        }
    }
}