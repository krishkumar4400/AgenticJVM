// class A {
//     int marks = 66;
// }

// public class Access_Modifiers {
//     public static void main(String[] args) {
//         A obj = new A();
//         System.out.println(obj.marks);
//     }
// }

// class A {
//     private int marks = 66;
// }

// public class Access_Modifiers {
//     public static void main(String[] args) {
//         A obj = new A();
//         System.out.println(obj.marks); // marks has private access in A
//     }
// }

// class A {
//     protected int marks = 66;
// }


// public class Access_Modifiers {
//     public static void main(String[] args) {
//         A obj = new A();
//         System.out.println(obj.marks);
//     }
// }

import access_pkg.*;


public class Access_Modifiers {
    public static void main(String[] args) {
        B obj1 = new B();
        System.out.println(obj1.marks);
    }
}
