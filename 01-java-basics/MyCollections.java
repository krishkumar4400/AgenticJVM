import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;


// public class Collections {
//     public static void main(String[] args) {
//         // Collection<Integer> nums = new ArrayList<Integer>();
//         List<Integer> nums = new ArrayList<Integer>();
//         nums.add(10);
//         nums.add(11);
//         nums.add(12);
//         nums.add(6);

//         System.out.println(nums.get(2));
        
//         System.out.println(nums);

//         for(int val : nums) {
//             System.out.println(val);
//         }

//         Set <Integer> s = new HashSet<Integer>();
//         s.add(10);
//         s.add(8);
//         s.add(10);
//         s.add(6);
//         System.out.println(s);

//         Set<Integer> ts = new TreeSet<Integer>();
//         ts.add(10);
//         ts.add(1);
//         ts.add(12);
//         ts.add(45);
//         ts.add(6);
//         ts.add(61);
//         System.out.println(ts);
        
//     }
// }

public class MyCollections {
    public static void main(String[] args) {
        Map<String,Integer>students = new HashMap<>();
        students.put("krish", 96);
        students.put("ankit", 80);
        students.put("raj", 66);
        students.put("sami", 89);
        students.put("ashish", 90);

        System.out.println(students);
        System.out.println(students.get("krish"));
        students.put("krish", 100);
        System.out.println(students.get("krish"));

        System.out.println(students.keySet());
        for(String name : students.keySet()) {
            System.out.println(students.get(name));
        }
        System.out.println(students.get("aman")); // null
        System.out.println(students.values());

        List<Integer>nums = new ArrayList<>();
        nums.add(4);
        nums.add(3);
        nums.add(7);
        nums.add(9);
        nums.add(1);

        System.out.println(nums);
        System.out.println(nums.size());

        Collections.sort(nums);
        System.out.println(nums);

    }
}
