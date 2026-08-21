
/**
 * Custom comparator
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Lecture_14
 */
public class Lecture_14 {

    public static void main(String[] args) {
        List<Integer> arrList = new ArrayList<>();

        arrList.add(1);
        arrList.add(5);
        arrList.add(4);

        System.out.println(arrList); // [1, 5, 4]
        Collections.sort(arrList);
        System.out.println(arrList); // [1, 4, 5]

        Collections.sort(arrList, new Comparator<Integer>() {
            @Override
            public int compare(Integer num1, Integer num2) {
                // order is wrong
                if (num1 < num2) {
                    return 1;
                } else if (num1 > num2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        System.out.println(arrList); // [5, 4, 1]

        // lambda
        // sort it the descending order
        // num1 < num2 -> wrong order, swap means return positive
        // num1 < num2 -> 
        List<Integer> arrList1 = new ArrayList<>();

        arrList1.add(11);
        arrList1.add(51);
        arrList1.add(41);
        System.out.println(arrList1);
        Collections.sort(arrList1, (num1, num2) -> num1 - num2); // [11, 41, 51]
        System.out.println(arrList1);
        Collections.sort(arrList1, (num1, num2) -> num2 - num1); // [51, 41, 11]
        System.out.println(arrList1);
    }
}