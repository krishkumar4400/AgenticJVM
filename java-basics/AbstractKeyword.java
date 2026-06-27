
// abstract class Car {
//     public abstract void drive();

//     public void playMusic() {
//         System.out.println("play music");
//     }
// }

// class WagonR extends Car {
//     public void drive() {
//         System.out.println("Driving...");
//     }
// }

// public class AbstractKeyword {
//     public static void main(String[] args) {
//         // Car obj = new Car(); // Car is abstract; cannot be instantiated
//         // obj.drive();
//         // obj.playMusic();

//         Car car = new WagonR();
//         car.drive();
//         car.playMusic();

//     }
// }

// abstract class Car {
//     // public abstract void drive(); // no problem here

//     public void playMusic() {
//         System.out.println("play music");
//     }
// }

// class WagonR extends Car {
//     public void drive() {
//         System.out.println("Driving...");
//     }
// }

// public class AbstractKeyword {
//     public static void main(String[] args) {

//         Car car = new WagonR();
//         car.drive();
//         car.playMusic();

//     }
// }

// abstract class Car {
//     public abstract void drive();

//     public abstract void fly();

//     public abstract void break();

//     public void playMusic() {
//         System.out.println("play music");
//     }
// }

// class WagonR extends Car {
//     public void drive() {
//         System.out.println("Driving...");
//     }
//     public void fly(){
//         System.out.println("");
//     }
//     public void break(){
//         System.out.println("");
//     }

// }

// public class AbstractKeyword {
//     public static void main(String[] args) {

//         Car car = new WagonR();
//         car.drive();
//         car.playMusic();

//     }
// }

abstract class Car {
    public abstract void drive();

    public abstract void fly();

    public abstract void break();

    public void playMusic() {
        System.out.println("play music");
    }
}

abstract class WagonR extends Car {
    public void drive() {
        System.out.println("Driving...");
    }

    public void fly();
}

class UpdatedWagonR extends WagonR { // concrete class.
    public void fly() {
        System.out.println("fly");
    }
}

public class AbstractKeyword { 
    public static void main(String[] args) {

        Car car = new UpdatedWagonR();
        car.drive();
        car.playMusic();

    }
}
