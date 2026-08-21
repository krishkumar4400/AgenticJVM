/**
 * Loops
 * 
 * - while loop
 * - do while loop
 * - for loop
 */

/**
 * For loop:-
 * If you know how many iterations you want to go for loop
 * (intiliazing vaue, condition, increment or decrement)
 * 
 * While loop:-
 * When you need to read the file go for while loop, if the number of iterations
 * is not known.
 * 
 * - For loop can also be used as a while.
 * 
 * Do While Loop:-
 * If you condition get false but you want to exceute the code at least once.
 * 
 */

public class Lectun {

    public static void main(String[] args) {

        // while loop
        int i = 1;
        while(i < 6) {
            System.out.println("i = " + i);
            i++;
        }

        // do-while loop
        int j = 1;
        do {
            System.out.println("j = " + j);
        } while(j > 1);

        // for loop
        for(int n = 0; n < 5; n++) {
            System.out.println("n = " + n);
        }
    }
}