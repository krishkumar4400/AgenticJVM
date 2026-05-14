//// type casting

// public class Casting {
//     public static void main(String[] args) {

//         double d = 4.5;

//         System.out.println(d);

//         // int i = d; // error: incompatible types: possible lossy conversion from double to int
//         // System.out.println(i);
        
//         double d1 = 4;
//         System.out.println(d1); // 4.0

//         int i = (int)d1;
//         System.out.println(i);

//         int i1 = (int)(d);
//         System.out.println(i1);
//     }
// }


// // up casting
// class A {
//     public void show1() {
//         System.out.println("in A show");
//     }
// }

// class B extends A{
//     public void show2() {
//         System.out.println("in B show");
//     }
// }

// public class Casting {
//     public static void main(String[] args) {
//         A obj = (A)new B(); // we are going up which is if you try to commpare A and B, A is super class B is subclass so we are trying to say object of B but refer to A.so we are going up which is up casting.

//         obj.show1();
//     }
// }


// down casting
class A {
    public void show1() {
        System.out.println("in A show");
    }
}

class B extends A {
    public void show2() {
        System.out.println("in B show");
    }
}

public class Casting {
    public static void main(String[] args) {
        A obj = new B();

        obj.show1();
        // obj.show2(); // error: cannot find symbol

        B obj1 = (B) obj; // this obj is refrence of parent object or parent refrence, we are down casting it to the child refrence.
        
        obj1.show2();
    }
}
