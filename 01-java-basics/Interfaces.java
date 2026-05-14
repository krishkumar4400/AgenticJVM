// interface A {
//     int age = 21; // final and static
//     String area = "Bhagalpur";

//      void show();
//      void config();
// }

// class B implements A { // if we do not defined both the methods we need to make this class abstract
//     public void show() {
//         System.out.println("in B show");
//     }

//     public void config() {
//        System.out.println("in B config");
//     }
// }

// public class Interfaces {
//     public static void main(String[] args) {
//     //  A obj = new A();  // A is abstract; cannot be instantiated
//     A obj;
//     obj = new B();
//     obj.show();
//     obj.config();

//     System.out.println(A.area); // Bhagalpur
//     System.out.println(A.age); // 21
//     System.out.println(A.class); // interface A

//     // A.age = 22; // error: cannot assign a value to static final variable age
//     }
// }


// interface A {
//     int age = 21; // final and static
//     String area = "Bhagalpur";

//     void show();

//     void config();
// }

// interface X {
//     void run();
// }

// interface Y {
//     void run();
// }

// interface Z {
//     void run();
// }

// class B implements A, X, Y {
//     public void show() {
//         System.out.println("in B show");
//     }

//     public void config() {
//         System.out.println("in B config");
//     }

//     public void run() {
//         System.out.println("running...");
//     }
// }

// public class Interfaces {
//     public static void main(String[] args) {
//         A obj;

//         obj = new B();

//         obj.show();
//         obj.config();
//         obj.run();
        
//     }
// }


// interface A {
//     int age = 21;
//     String area = "Bhagalpur";

//     void show();

//     void config();
// }

// interface X {
//     void run();
// }

// interface Y extends X {
// }


// class B implements A, Y{
//     public void show() {
//         System.out.println("in B show");
//     }

//     public void config() {
//         System.out.println("in B config");
//     }

//     public void run() {
//         System.out.println("running...");
//     }
// }

// public class Interfaces {
//     public static void main(String[] args) {
//         B obj =  new B();

//         obj.show();
//         obj.config();
//         obj.run();
//     }
// }


// interface A {
//     int age = 21;
//     String area = "Bhagalpur";

//     void show();
//     void config();
// }

// interface X {
//     void run();
// }


// class B implements A, X {
//     public void show() {
//         System.out.println("in B show");
//     }

//     public void config() {
//         System.out.println("in B config");
//     }

//     public void run() {
//         System.out.println("running...");
//     }
// }

// public class Interfaces {
//     public static void main(String[] args) {
//         B obj = new B();

//         obj.show();
//         obj.config();
//         obj.run();
//     }
// }

// Types
// 1. funnctional interface:
@FunctionalInterface
interface A {
    void show();
}

class B implements A {
    public void show() {
        System.out.println("in A show");
    }
}

public class Interfaces {
    public static void main(String[] args) {
        B obj = new B();
        obj.show();
    }
}