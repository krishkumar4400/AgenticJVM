// class Calculator {
//     public int add(int num1, int num2) {
//         return num1 + num2;
//     }
// }

// class Computer {
//     public void playMusic() {
//         System.out.println("Playing Music");
//     }

//     public String getMeAPen(int cost) {
//         if(cost > 10)
//             return "Pen";
//         return "Nothing";
//     }
// }

// class Calculator {
//     // method overloading
//         // - matters:
//             // - different number of parameters
//             // - different types of parameters

//     public int add(int n1, int n2) {
//         return n1 + n2;
//     }

//     public int add(int n1, int n2, int n3) {
//         return n1 + n2 + n3;
//     }

//     public double add(double n3, int n4) {
//         return n3 + n4;
//     }
// }

class Calculator {
    int num = 100; // instance variable

    public int add(int n1, int n2) { // local variable
        return n1 + n2;
    }
}

public class Demo {
    public static void main(Strings[] args) {
        // int num1 = 10;
        // int num2 = 20;

        // Calculator calc = new Calculator();
        // calc.add(num1, num2);

        // Computer comp = new Computer();

        // comp.playMusic();
        // String str = comp.getMeAPen(0);
        // System.out.println(str);

        // Calculator obj = new Calculator();

        int data = 10;
        Calculator c1 = new Calculator();
        Calculator c2 = new Calculator();

        System.out.println(c1.num);
        System.out.println(c2.num);
        c1.num = 200;
        System.out.println(c1.num);
        System.out.println(c2.num);
    }
}
