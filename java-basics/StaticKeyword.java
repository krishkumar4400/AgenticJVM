// static variables

// class Mobile {
//     String brand;
//     int price;
//     String network;
//     static String name;

//     public void show() {
//         System.out.println(brand + " " + price + " " + network + " " + name);
//     }
// }

// public class StaticKeyword {
//     public static void main(String[] args) {

//         Mobile.name = "SmartPhone";
//         Mobile obj1 = new Mobile();
//         obj1.brand = "Apple";
//         obj1.price = 1500;
//         obj1.network = "5G";

//         Mobile obj2 = new Mobile();
//         obj2.brand = "Samsung";
//         obj2.price = 1700;
//         obj2.network = "4G";

//         // obj1.name = "phone"; // change the value for every object

//         obj1.show();
//         obj2.show();

//         System.out.println(Mobile.name);

//     }
// }

// static methods

// class Mobile {
//     String brand; // instance variables
//     int price;
//     static String name; // static variables

//     public void show() {
//         System.out.println(brand + " " + price + " " + name);
//     }

//     // public static void show1() {
//     //     System.out.println("In static method");
//     // }
//     // public static void show1() {
//     //     System.out.println(brand + " " + price + " " + name);
//     // }
//     // public static void show1() {
//     //     System.out.println(name);
//     // }
//     public static void show1(Mobile obj) {
//         System.out.println(obj.brand + " " + obj.price + " " + name);
//     }

// }

// public class StaticKeyword {
//     public static void main(String[] args) {

//         Mobile.name = "SmartPhone";

//         Mobile obj1 = new Mobile();
//         obj1.brand = "Apple";
//         obj1.price = 1500;

//         Mobile obj2 = new Mobile();
//         obj2.brand = "Samsung";
//         obj2.price = 1700;

//         obj1.show();
//         obj2.show();

//         System.out.println(Mobile.name);

//         // Mobile.show(); // error: non-static method show() cannot be referenced from a static context
//         // Mobile.show1();

//         Mobile.show1(obj2);
//         Mobile.show1(obj1);

//     }
// }

// class Mobile {
//     String brand;
//     int price;
//     static String name;

//     // public Mobile() {
//     //     brand = "";
//     //     price = 200;
//     //     name = "Phone"; // everytime the constructor will be called the name which is static and common we also be re initialized everytime.
//     // }

//     static {
//         name = "SmartyPhony"; // the good thing about static this is this static block will be called only once irrespective of how many object you are creating. it will call static block only once
//         System.out.println("Static block called");
//     }

//     public Mobile() {
//         brand = "";
//         price = 200;
//         System.out.println("Constructor called");
//     }

//     public void show() {
//         System.out.println(brand + " " + price + " " + name);
//     }
// }

// public class StaticKeyword {
//     public static void main(String[] args) {

//         // Mobile obj1 = new Mobile();
//         // Mobile obj2 = new Mobile();
//         // Mobile obj3 = new Mobile();

//     }
// }
// /** Output
//  * Static block called
//  * Constructor called
//  * Constructor called
//  * Constructor called
//  */


class Mobile {
    String brand;
    int price;
    static String name;

    static {
        name = "SmartyPhony";
        System.out.println("Static block called");
    }

    public Mobile() {
        brand = "";
        price = 200;
        System.out.println("Constructor called");
    }

    public void show() {
        System.out.println(brand + " " + price + " " + name);
    }
}

public class StaticKeyword {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("Mobile"); // forname is a method which loads our class. 
        // so now if we write this line it will not create an object it will just instantiated.
    }
}
/**
 * Output
 * Static block called
 */