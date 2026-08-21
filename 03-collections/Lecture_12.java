
/**
 * Iterator
 *  - ListIterator
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Lecture_12
 */
public class Lecture_12 {

    public static void main(String[] args) {
        List<Integer>arrayList = new ArrayList<>();

        arrayList.add(1);
        arrayList.add(5);
        arrayList.add(4);
        arrayList.add(2);

        for(var num : arrayList) {
            System.out.println(num);
        }

        // using iterator
        Iterator<Integer>iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            Integer val = iterator.next();
            System.out.println("value = " + val);
        }
    }
}