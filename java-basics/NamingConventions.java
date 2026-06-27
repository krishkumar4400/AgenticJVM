class A {
    public A() {
        System.out.println("Object Created");
    }

    public void show() {
        System.out.println("in A Show");
    }
}

public class NamingConventions {
    public static void main(String[] args) {
        A obj = new A(); // obj is a refrence variable because it is refering to an object.
        obj.show();

        new A(); // object creation -> called anonymous object -> you can't use same object again.

        new A().show(); // created new object
        // A obj1; // this is an refrence variable which stores refrence of the object in the stack.
    }
}
