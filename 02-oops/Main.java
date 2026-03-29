/**
 * Object Oriented Programming
 * - thinking about objects
 * - class
 * - data(state)
 * - actions(behavior)
 * - encapsulation
 * - abstraction
 * 
 * 
 * Airplane
 * name, address, type, position // data
 * takeoff(), land(), cruise() // actions
 * 
 * AirHostess
 * name, address, // data
 * wish(), serve(), // actions
 * 
 * Passenger
 * takeCab(), check-in(), walk(), smile(), // actions
 * 
 * Procedural / structured programming - thinking in terms of functions and
 * procedures
 * // Global data
 * 
 * fly() {
 * travelToAirport();
 * findCheckInCounter();
 * checkIn();
 * boardTheFlight();
 * takeOff();
 * landing();
 * }
 * 
 * -> Thinking about objects
 * -> Data(state)
 * ->Actions (behavior)
 * 
 * Aeroplane
 * airline, make, type, position, //data
 * takeoff(), land(), cruise() // actions
 * 
 * 
 * 
 */

// object oriented terminology
class Planet {
    // data/state/fields
    String name;
    String location;
    String distanceFromSun;

    // actions/behavior/methods
    void revoke() {
    }

    void rotate() {
    }
}

public class Main {
    public static void main(String[] args) {
        Planet earth = new Planet();
        Planet venus = new Planet();
    }
}
/**
 * Exercises
 * Online shopping system:
 * 
 * -Customer
 * name, address
 * login(), logout(), selectProduct(product)
 * 
 * -Shopping Cart
 * items
 * add(item), remove(item)
 * 
 * -Product
 * name, price, quantityAvailable
 * order(), changePrice()
 * 
 * 
 * Person
 * name, address, hobbies, work
 * walk(), run(), sleep(), eat(), drink().
 * 
 */

