
// class A {
//     public A() {
//         super();
//         System.out.println("in A");
//     }
    
//     public A(int n) {
//         super();
//         System.out.println("In A Int");
//     }
// }

// class B extends A {

//     public B() {
//         super();
//         System.out.println("In B");
//     }

//     public B(int n) {
//         super();
//         System.out.println("In B Int");
//     }
// }

// public class Super_This {
//     public static void main(String[] args) {
        
//         B obj = new B();
//         B obj1 = new B(2);
//     }
// }


// class A {
//     public A() {
//         super();
//         System.out.println("in A");
//     }

//     public A(int n) {
//         super();
//         System.out.println("In A Int");
//     }
// }

// class B extends A {

//     public B() {
//         super();
//         System.out.println("In B");
//     }

//     public B(int n) {
//         super(n);
//         System.out.println("In B Int");
//     }
// }

// public class Super_This {
//     public static void main(String[] args) {

//         B obj1 = new B(2);
//     }
// }
/**
 * In A Int
 * In B Int
 */

// class A {
//     public A() {
//         super();
//         System.out.println("in A");
//     }

//     public A(int n) {
//         super();
//         System.out.println("In A Int");
//     }
// }

// class B extends A {

//     public B() {
//         super(2);
//         System.out.println("In B");
//     }

//     public B(int n) {
//         super(n);
//         System.out.println("In B Int");
//     }
// }

// public class Super_This {
//     public static void main(String[] args) {

//         B obj1 = new B();
//     }
// }
/**
 * In A Int
 * In B
 */

// class A extends Object { // every class in java extends the Object class by default even if you don't mention so here super means we are calling the constructor of Object class.
//     public A() {
//         super();
//         System.out.println("in A");
//     }

//     public A(int n) {
//         super();
//         System.out.println("In A Int");
//     }
// }

// class B extends A {

//     public B() {
//         super();
//         System.out.println("In B");
//     }

//     public B(int n) {
//         super();
//         System.out.println("In B Int");
//     }
// }

// public class Super_This {
//     public static void main(String[] args) {

//         B obj1 = new B();
//     }
// }


class A extends Object { // every class in java extends the Object class by default even if you don't
                         // mention so here super means we are calling the constructor of Object class.
    public A() {
        super();
        System.out.println("in A");
    }

    public A(int n) {
        super();
        System.out.println("In A Int");
    }
}

class B extends A {

    public B() {
        super();
        System.out.println("In B");
    }

    public B(int n) {
        this();
        System.out.println("In B Int");
    }
}

public class Super_This {
    public static void main(String[] args) {

        B obj1 = new B(10);
    }
}
/**
 * in A
 * In B
 * In B Int
 */