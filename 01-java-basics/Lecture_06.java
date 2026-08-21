/**
 * Datatypes
 * Literals
 * type conversion
 */

class Demo {

}

public class Lecture_06 {
    public static void main(String[] args) {

        // datatypes
        byte b = 127;
        short sh = 558;
        int num = 9;
        long l = 5854l;

        float f = 5.8f;
        double d = 5.8;

        char c = 'k';

        boolean bool = true;

        // literals
        // binary
        int num1 = 0b101;
        System.out.println(num1);

        // hexadecimal
        int num2 = 0x7E;
        System.out.println(num2);

        // integer
        int num3 = 1_00_00_00_000;
        System.out.println(num3);

        float num4 = 56;
        System.out.println(num4);

        double num5 = 12e10;
        System.out.println(num5);

        double num6 = 56;
        System.out.println(num6);

        boolean num7 = true;
        System.out.println(num7);

        char c1 = 'a';
        System.out.println(c1); // 'a'
        c1++;
        System.out.println(c1); // 'b'

        char c2 = 'a';
        c2++;
        System.out.println(c2); // 'b'

        // type conversion
        byte b1 = 127;
        int a1 = b1;
        System.out.println(b1);
        System.out.println(a1);

        int a2 = 257;
        byte b2 = (byte) a2;
        System.out.println(a2);
        System.out.println(b2);

        float f1 = 5.6f;
        int a3 = (int) f1;
        System.out.println(f1);
        System.out.println(a3);

        // type promotion
        byte bt1 = 10;
        byte bt2 = 20;
        System.out.println(bt1 + bt2); // 30

    }
}
