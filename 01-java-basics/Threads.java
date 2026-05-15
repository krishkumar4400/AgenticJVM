// class A {
//     public void show() {
//         for(int i = 0; i < 10; i++) {
//             System.out.println("HI");
//         }
//     }
// }
// class B {
//     public void show() {
//         for(int i = 0; i < 10; i++) {
//             System.out.println("Hello");
//         }
//     }
// }

// public class Threads {
//     public static void main(String[] args) {
//         A obj1 = new A();
//         B obj2 = new B();
//         obj1.show();
//         obj2.show();
//     }
// }


// class A extends Thread {
//     public void run() {
//         for(int i = 0; i < 100; i++) {
//             System.out.println("HI");
//         }
//     }
// }
// class B extends Thread {
//     public void run() {
//         for(int i = 0; i < 100; i++) {
//             System.out.println("Hello");
//         }
//     }
// }

// public class Threads {
//     public static void main(String[] args) {
//         A obj1 = new A();
//         B obj2 = new B();
//         obj1.start();
//         obj2.start();
//     }
// }


// class A extends Thread {
//     public void run() {
//         for (int i = 0; i < 100; i++) {
//             System.out.println("HI");
//             try {
//                 Thread.sleep(10);
//             } catch (InterruptedException e) {
//                 System.out.println(e);
//             }
//         }
//     }
// }

// class B extends Thread {
//     public void run() {
//         for (int i = 0; i < 100; i++) {
//             System.out.println("Hello");
//             try {
//                 Thread.sleep(10);
//             } catch (InterruptedException e) {
//                 System.out.println(e);
//             }
//         }
//     }
// }

// public class Threads {
//     public static void main(String[] args) {
//         A obj1 = new A();
//         B obj2 = new B();
//         obj1.start();
//         try {
//             Thread.sleep(2);
//         } catch (InterruptedException e) {
//             System.out.println(e);
//         }
//         obj2.start();
//     }
// }


// Another way to implement Thread - using Runnable
class A implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("HI");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

class B implements Runnable {
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Hello");
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}

public class Threads {
    public static void main(String[] args) {
        A obj1 = new A();
        B obj2 = new B();

        Thread t1 = new Thread(obj1);
        Thread t2 = new Thread(obj2);
        t1.start();
        t2.start();
    }
}
