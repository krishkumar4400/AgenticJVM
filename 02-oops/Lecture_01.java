
/**
 * Classes and Objects
 * 
 * Object Oriented programming
 * Object - properties and behaviors
 */

// class
class Demo {

}

class Calculator {
    public int add(int num1, int num2) {
        int a;
        System.out.println("in add");;
        // return 0;

        int r = num1 + num2;
        return r;

    }
}

public class Lecture_01 {
    public static void main(String[] args) {
        int num1 = 4;
        int num2 = 5;

        Calculator calc = new Calculator();
        int result = calc.add(num1, num2);
        System.out.println(result);
    } 
}
