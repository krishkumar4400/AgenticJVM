// class A {
//     public void show() {
//         System.out.println("in A show");
//     }

//     public void config() {
//         System.out.println("In A Config");
//     }
// }
// class B extends A{
//     public void show() {
//         System.out.println("in B show");
//     }
// }

// public class Method_Overriding {
//     public static void main(String[] args) {
//         A obj = new A();
//         obj.show();

//         B obj1 = new B();
//         obj1.show();
//         obj1.config();
//     }
// }


class Calc {
    public int add(int a, int b) {
        return a + b;
    }

    public void config() {
        System.out.println("In A Config");
    }
}

class AdvCalc extends Calc {
    public int add(int a, int b) {
        return a + b + 1;
    }
}

public class Method_Overriding {
    public static void main(String[] args) {
        
        AdvCalc obj1 = new AdvCalc();
        int r1 = obj1.add(3, 4);
        System.out.println(r1);
    }
}
