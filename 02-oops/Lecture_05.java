/**
 * Inheritance
 * 
 * - super method
 */

class Calc {
    public void add(int a, int b) {
        System.out.println(a + b);
    }

    public void sub(int a, int b) {
        System.out.println(a - b);
    }

    public void multi(int a, int b) {
        System.out.println(a * b);
    }

    public void div(int a, int b) {
        System.out.println(a / b);
    }
}

// Child class, Sub class, Derived class
class AdvCalc extends Calc {
    public void modulo(int a, int b) {
        System.out.println(a % b);
    }

    public void power(int a, int b) {
        System.out.println(Math.pow(a, b));
    }
}

public class Lecture_05 {
    public static void main(String[] args) {
        Calc calc = new Calc();
        int a = 10;
        int b = 20;
        calc.add(a, b);
        calc.sub(a, b);
        calc.multi(a, b);
        calc.div(a, b);

        AdvCalc advCalc = new AdvCalc();
        advCalc.modulo(a, b);
        advCalc.power(a, b);
        advCalc.add(a, b);
        advCalc.sub(a, b);
        advCalc.multi(a, b);
        advCalc.div(a, b);
    }
}
