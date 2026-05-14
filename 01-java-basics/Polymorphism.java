
// public class Polymorphism {
//     public static void main(String[] args) {

//     }
// }

//  implementation of runtime polymorphism using Dynamic Method Dispatch.

// class Computer {

// }

// class Laptop extends Computer {

// }

// class A {
//     public void show() {
//         System.out.println("In A Show");
//     }
// }

// class B extends A {

// }

// class C extends A {

// }
// public class Polymorphism {
//     public static void main(String[] args) {
//         // B obj = new B();
//         // obj.show();
//         A obj = new B();
//         obj.show();

//         Computer obj1 = new Laptop();

//     }
// }

class A {
    public void show() {
        System.out.println("In A Show");
    }
}

class B extends A {
    public void show() {
        System.out.println("In B Show");
    }
}

class C extends A {
    public void show() {
        System.out.println("In C Show");
    }
}

class D {
    public void show() {
        System.out.println("In C Show");
    }
}

public class Polymorphism {
    public static void main(String[] args) {
        A obj = new A();
        obj.show();
        obj = new B();
        obj.show();

        obj = new C();
        obj.show();

        obj = new D();
        obj.show(); //error: incompatible types: D cannot be converted to A

    }
}
