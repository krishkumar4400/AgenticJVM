/**
 * Operators:
 * - assignment operator
 * - relational operator
 * - logical operator
 */

public class Lecture_07 {
    public static void main(String[] args) {

        // assignment operator
        int num1 = 7;
        int num2 = 5;
        int result = num1 + num2;
        System.out.println(result);

        /**
         * int num1=7;
         * int num2=5;
         * int result=num1-num2;
         * System.out.println(result);
         **/

        /**
         * int num1=7;
         * int num2=5;
         * int result=num1*num2;
         * System.out.println(result);
         **/

        /**
         * int num1=7;
         * int num2=5;
         * int result=num1/num2;
         * System.out.println(result);
         **/

        /**
         * int num1=7;
         * int num2=5;
         * int result=num1%num2;
         * System.out.println(result);
         **/

        int num = 7;
        num = num + 2;
        num += 2;
        num *= 2;
        System.out.println(num);

        num++; // post increment
        ++num; // pre increment
        num--; // post decrement
        --num; // pre decrement
        System.out.println(num);

        int result1 = num++; // fetch the value and then increment
        System.out.println(result);

        // relational operator
        int x = 6;
        int y = 5;
        // boolean result2= x<y; // false
        // boolean result2= x>y; // true
        // boolean result2= x>=y; // true
        // boolean result2= x<=y; // false
        // boolean result2= x!=y; // true
        boolean result2 = x == y; // false
        System.out.println(result2);

        double a = 8.8;
        double b = 9.8;
        // boolean res = a<=b; // true
        boolean res = a >= b; // false

        System.out.println(res);

        // logical operator
        int x1 = 7;
        int y1 = 5;
        int a1 = 5;
        int b1 = 9;

        // boolean result3= x1>y1 && a1<b1 ; // true
        // boolean result3= x1>y1 || a1<b1 ; // true
        // boolean result3= x1>y1 && a1>b1 ; // false
        // boolean result3= x1>y1 || a1>b1 ; // true
        // boolean result3= x1<y1 && a1<b1 ; // false
        // boolean result3= x1<y1 || a1<b1 ; // true
        // boolean result3= x1<y1 || a1<b1 || a1>1 ; // true

        // System.out.println("result3 = " + result3);
        boolean result3 = a1 > b1; // false
        System.out.println("result3 = " + !result3); // true
    }
}
