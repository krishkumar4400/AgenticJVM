/**
 * TreeMap
 */

import java.util.*;

/**
 * Lecture_11
 */
public class Lecture_11 {

    public static void main(String[] args) {
        
        // it allows you to store sorted stuff - always stores sorted order of keys
        TreeMap<Integer, String> tMap = new TreeMap<>();

        tMap.put(3, "ankit"); // O(log N)
        tMap.put(2, "raj");
        tMap.put(4, "ashish");
        tMap.put(1, "krish");
        tMap.put(12, "abc");
        tMap.put(7, "xyz");
        tMap.put(8, "xyz"); // {1=krish, 2=raj, 3=ankit, 4=ashish, 7=xyz, 8=xyz, 12=abc}

        System.out.println(tMap); // {1=krish, 2=raj, 3=ankit, 4=ashish}

        System.out.println(tMap.ceilingKey(2)); // 2
        System.out.println(tMap.ceilingKey(6)); // 7
        System.out.println(tMap.floorKey(6)); // 4

        System.out.println(tMap.size());
        System.out.println(tMap.get(1));
        System.out.println(tMap.containsValue("abc")); // true
        System.out.println(tMap.containsValue(33)); // false
        System.out.println(tMap.containsValue("ankit")); // true

        Set<Integer>s = tMap.keySet();
        System.out.println(s); // [1, 2, 3, 4, 7, 12]

        Collection<String> c = tMap.values();
        System.out.println(c); // [krish, raj, ankit, ashish, xyz, abc]



    }
}