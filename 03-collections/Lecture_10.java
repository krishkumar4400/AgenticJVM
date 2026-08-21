/**
 * HashMap
 */

/**
 * Lecture_10
 */

import java.util.*;

public class Lecture_10 {

    public static void main(String[] args) {

        // doesn't stores keys in the sorted order
        HashMap<String, Integer>hm = new HashMap<>();

        // key, value
        hm.put("krish", 98); // O(1)
        hm.put("raj", 92);
        hm.put("ankit", 87);
        hm.put("ashish", 95);
        hm.put("sami", 77);
        hm.put("ankit", 78);
        hm.put("krish", 78);

        System.out.println(hm); // {ankit=78, ashish=95, raj=92, krish=78, sami=77}

        System.out.println(hm.get("krish")); // 78 // O(1)
        System.out.println(hm.get("raj")); // 92
        System.out.println(hm.size()); // 5

        hm.remove("krish");
        System.out.println(hm); // {ankit=78, ashish=95, raj=92, sami=77}

        hm.remove("x"); // O(1)

        System.out.println(hm.get("chandan")); // null

    }
}