/**
 * functions
 */

class Demo {
    void printHello() {
        System.out.println("Hello");
    }
}

public class Lecture_05 {
    public static void main(String[] args) {
        Demo d1 = new Demo();
        Demo d2 = new Demo();
        Demo d3 = new Demo();
        d1.printHello();
        d2.printHello();
        d3.printHello();
    }
}
