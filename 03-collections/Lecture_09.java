
/**
 * 
 * PriorityQueue
*/

import java.util.*;

public class Lecture_09 {

    public static void main(String[] args) {

        // stores elements
        // and whenever you ask for peak, it gives you the smallest element
        PriorityQueue<Integer> pQueue = new PriorityQueue<>();

        pQueue.offer(10);
        pQueue.offer(100);
        pQueue.offer(112);
        pQueue.offer(101);

        System.out.println(pQueue.peek()); // 10

        pQueue.poll();
        System.out.println(pQueue.peek());

        System.out.println(pQueue);

        System.out.println(pQueue.size());

        for (var num : pQueue) {
            System.out.println(num);
        }

        System.out.println(pQueue.isEmpty());

        while (pQueue.isEmpty() == false) {
            System.out.println(pQueue.peek());
            pQueue.poll();
        }
    }
}