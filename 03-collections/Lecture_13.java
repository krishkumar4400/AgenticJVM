
/**
 * Common algorithms
 * 
 * - sort
 * - min
 * - max
 * - pow
 * - reverse
 * - binary search
 * - frequency
 */

import java.util.*;

/**
 * Lecture_13
 */
public class Lecture_13 {

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();

        arrayList.add(1);
        arrayList.add(5);
        arrayList.add(4);
        arrayList.add(2);

        System.out.println(arrayList); // [1, 5, 4, 2]
        Collections.sort(arrayList);
        System.out.println(arrayList); // [1, 2, 4, 5]

        System.out.println(Collections.min(arrayList)); // 1
        System.out.println(Collections.max(arrayList)); // 5
        Collections.reverse(arrayList);
        System.out.println(arrayList); // [5, 4, 2, 1]
        System.out.println("Freq = " + Collections.frequency(arrayList, 5)); // 1
        Collections.sort(arrayList);
        System.out.println(arrayList);
        Integer elem = Collections.binarySearch(arrayList, 4);

        System.out.println("element = " + elem);

        int arr[] = new int[4];
        arr[0] = 1;
        arr[1] = 21;
        arr[2] = 3;
        arr[3] = 33;
        for (int i : arr) {
            System.out.println(i);
        }
        Arrays.sort(arr);
        for (int i : arr) {
            System.out.println(i);
        }

        double num = Math.pow(2, 4);
        System.out.println(num); // 16.0

        int num1 = (int) Math.pow(2, 5);
        System.out.println(num1); // 32
    }
}