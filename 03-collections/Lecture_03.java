
/**
 * LinkedList
 * 
 */

import java.util.*;

public class Lecture_03 {
    public static void main(String[] args) {
        LinkedList<Integer> ll = new LinkedList<>();

        ll.add(10);
        ll.add(20);
        ll.add(30);

        ll.add(1, 40);

        ll.addFirst(999);
        ll.addLast(111);

        System.out.println(ll);

        ll.remove(); // removes first element
        System.out.println(ll);
        ll.remove(1);
        System.out.println(ll);

        Integer first = ll.removeFirst();
        Integer last = ll.removeLast();
        System.out.println(ll); // [20, 30]

        System.out.println(ll.contains(30));

        Integer val = ll.get(1);
        System.out.println(val);

        System.out.println(ll.isEmpty()); // false

        System.out.println(ll.getFirst());
        System.out.println(ll.getLast());

        System.out.println(ll.size());

        ll.clear();
        System.out.println(ll); // []
    }
}