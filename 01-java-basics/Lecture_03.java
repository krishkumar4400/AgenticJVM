/**
 * conditional statements
 * 
 * - if
 * - if else
 * - if else if else
 * - ternary
 */

public class Lecture_03 {
    public static void main(String[] args) {

        // if statement
        int x = 20;
        if (x > 10) {
            System.out.println("x is greator than 10");
        }

        if (true) {
            System.out.println("Hello");
        }

        // if(1) { // not valid
        // System.out.println("hello again");
        // }

        // if-alse statement
        int x1 = 2;
        if ((x1 % 2) == 0) {
            System.out.println("x1 is even");
        } else {
            System.out.println("x1 is odd");
        }

        // if-else if statement
        int a = 10, b = 20, c = 30;
        if (a > b && a > c) {
            System.out.println("A is greator");
        } else if (b > a & b > c) {
            System.out.println("B is greator");
        } else {
            System.out.println("C is greator");
        }

        // ternary
        int n = 4;
        int result = 0;
        if (n % 2 == 0) {
            result = 10;
        } else {
            result = 20;
        }

        System.out.println("Result = " + result);

        result = n % 2 == 0 ? 10 : 20;
        System.out.println("Result = " + result);

        int day = 2;
        switch (day) {
            case 1:
                System.out.println("Monday");
                break;
            case 2:
                System.out.println("Tuesday");
                break;
            case 3:
                System.out.println("Wednesday");
                break;
            case 4:
                System.out.println("Thursday");
                break;
            case 5:
                System.out.println("Friday");
                break;
            case 6:
                System.out.println("Saturday");
                break;
            case 7:
                System.out.println("Sunday");
                break;
            default:
                System.out.println("Enter a valid day");
                break;
        }
    }
}
