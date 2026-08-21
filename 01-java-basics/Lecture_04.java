/**
 * methods
 * 
 */

class Calculator {
    public int add(int num1, int num2) {
        return num1 + num2;
    }

    public int subtract(int num1, int num2) {
        return num1 - num2;
    }
}

class Computer {
    public void playMusic() {
        System.out.println("Music Playing...");
    }
}

public class Lecture_04 {

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        int sum = calc.add(2, 9);
        int difference = calc.subtract(2, 9);
        System.out.println("Sum = " + sum); // 11
        System.out.println("Difference = " + difference); // -7

        Computer c = new Computer();
        c.playMusic();
    }
}
