/**
 * Stack
 * 
 */

import java.util.*;

public class Lecture_04 {

    public static void main(String[] args) {
        // LIFO - Last in First out
        Stack<Integer>st = new Stack<>();

        st.push(2);
        st.push(4);
        st.push(6);

        System.out.println(st); // [2, 4, 6]

        System.out.println(st.peek());

        st.pop();
        System.out.println(st);
        System.out.println(st.peek());

        System.out.println(st.size()); // 2

        st.clear();
        System.out.println(st);
    }
}