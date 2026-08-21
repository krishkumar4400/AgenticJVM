
/**
 * 
 * ArrayDeque
*/

import java.util.*;

public class Lecture_08 {

    public static void main(String[] args) {
        ArrayDeque<Integer> aDeque = new ArrayDeque<>();

        aDeque.offer(1); // add element
        aDeque.offer(3);
        aDeque.offer(4);
        aDeque.offer(2);
        aDeque.offer(1);
        aDeque.offer(5);
        aDeque.offer(5);

        System.out.println(aDeque);
        System.out.println(aDeque.peek()); // 1

        aDeque.poll();
        System.out.println(aDeque.peek()); // 3
        System.out.println(aDeque);

        aDeque.offerFirst(100);
        aDeque.offerLast(200);
        System.out.println(aDeque);

        aDeque.pollFirst();
        aDeque.pollFirst();
        System.out.println(aDeque);
        aDeque.pollLast();
        aDeque.pollLast();
        System.out.println(aDeque);

        System.out.println(aDeque.size());
        System.out.println(aDeque.peekLast());
        System.out.println(aDeque.peekFirst());
        System.out.println(aDeque.peek());
    }
}