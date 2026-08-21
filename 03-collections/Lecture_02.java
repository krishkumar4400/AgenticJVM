/**
 * - Collection interface
 * - List interface
 * - ArrayList class
 */

import java.util.*;

class Data {
    private Integer num;
    private String name;

    Data(Integer _num, String _name) {
        this.num = _num;
        this.name = _name;
    }
}

public class Lecture_02 {
    public static void main(String[] args) {
        ArrayList<Integer> arrList = new ArrayList<>(); // dynamic in nature
        ArrayList<Data> custmArrList = new ArrayList<>();

        // int arr[] = new int[100];  // constant in size

        arrList.add(12);
        arrList.add(16);
        arrList.add(20);
        arrList.add(10 );

        System.out.println(arrList);

        System.out.println(arrList.size());

        System.out.println(arrList.get(3));

        arrList.add(1, 1001);

        System.out.println(arrList);

        Integer val = arrList.remove(2);
        System.out.println(val);

        System.out.println(arrList);

        System.out.println(arrList.contains(20)); // true
        System.out.println(arrList.contains(200)); // false

        System.out.println(arrList.indexOf(11)); // -1
        System.out.println(arrList.indexOf(12)); // 0

        arrList.clear();
        System.out.println(arrList); // []
        
    }
}