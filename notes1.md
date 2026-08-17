# Java Core Concepts — Complete Learning Handbook

### Topics: Anonymous Objects · Inheritance · Method Overriding · Packages · Access Modifiers · Polymorphism · Dynamic Method Dispatch · final · equals() · Upcasting & Downcasting · abstract · Object class · Inner Classes · Anonymous Inner Classes

> **Target Audience:** SDE Interviews · Senior Backend Engineering · System Design · Production Development · JVM Deep Dive  
> **Java Versions Covered:** Java 8, 11, 17, 21+

---

# TABLE OF CONTENTS

| Part | Topic |
| ------ | ------- |
| 1 | Anonymous Objects |
| 2 | Inheritance and Types |
| 3 | Method Overriding |
| 4 | Packages |
| 5 | Access Modifiers (public, private, protected) |
| 6 | Polymorphism |
| 7 | Dynamic Method Dispatch |
| 8 | final Keyword |
| 9 | equals() and hashCode() |
| 10 | Upcasting & Downcasting |
| 11 | abstract Keyword |
| 12 | Object Class |
| 13 | Inner Classes |
| 14 | Anonymous Inner Classes |
| X | Cross-Cutting: Interview Section, Cheat Sheet, Mind Map |

---

---

# PART 1: ANONYMOUS OBJECTS

---

## 1. Overview

### What is it?

An **anonymous object** is an object that is created **without assigning it to any reference variable**. It is used once and immediately discarded.

```java
// Normal object (has reference)
Car myCar = new Car();
myCar.start();

// Anonymous object (no reference)
new Car().start();
```

### Why was it introduced?

To support **one-time use** object creation without polluting the namespace with unnecessary variables. Also enables chained API calls and cleaner functional-style code.

### What problem does it solve?

- Eliminates the need to declare a variable when the object is used only once
- Keeps code concise
- Allows passing objects directly as arguments
- Useful in builder patterns, callbacks, factory method results

### History

Anonymous objects have been part of Java since its inception (Java 1.0). They became more useful with:

- Java 5: Enhanced for loops, autoboxing
- Java 8: Lambdas replaced many anonymous object use-cases
- Java 16+: Record classes reduced boilerplate

### Real-world importance

- Used extensively in test frameworks (JUnit, Mockito)
- Used in event handlers, callbacks
- Used in builder pattern chains
- Common in Spring Boot configuration

### Where it is used in industry

- **Android**: `new Intent(this, NextActivity.class)` passed directly
- **Spring**: `new HttpHeaders()` passed inline to `RequestEntity`
- **JUnit**: `assertThrows(Exception.class, () -> new MyClass(null))`

---

## 2. Intuition

Think of it like **ordering food at a vending machine** — you insert a coin (call `new`), get a snack (create object), eat it immediately (use it), and throw the wrapper (object becomes eligible for GC). You never assigned the snack a name. You just used it once.

Another analogy: **a taxi you flag, ride, and never see again** vs a car you own (referenced object). The taxi is the anonymous object.

---

## 3. Core Concept

### How does it work?

```java
new ClassName().methodName();
//  ↑                ↑
//  Object creation  Immediately call method
//  No variable      No re-use possible
```

### Key terminology

| Term | Explanation |
| ------ | ------------- |
| Reference variable | A named variable holding object address |
| Anonymous object | Object without reference variable |
| Heap | Where the object lives in memory |
| GC eligible | When no reference points to object |

### Rules

1. Can call only one method directly (though you can chain if methods return `this`)
2. Cannot be reused after the statement ends
3. Can be passed as an argument to methods
4. Can be used in constructor calls

---

## 4. Internal Working

### Step-by-Step

```
1. JVM sees: new Car()
2. Allocates memory on the HEAP for Car object
3. Calls the constructor (initializes fields)
4. Returns the object reference (a memory address)
5. .start() is called on that address immediately
6. The statement ends
7. No variable holds the reference
8. GC marks object as eligible for collection
9. GC eventually frees the heap memory
```

### Memory Behavior

```
Stack Frame:                 Heap:
+-----------------+          +------------------+
| current method  |          | Car Object       |
| local vars: []  |    →     | speed: 0         |
| No reference!   |  (temp)  | color: null      |
+-----------------+          +------------------+
                                      ↑
                              Created, used, then
                              no reference → GC eligible
```

### JVM Bytecode

For `new Car().start()`, the bytecode would be:

```
NEW Car           ; allocate heap memory
DUP               ; duplicate reference (for constructor call)
INVOKESPECIAL     ; call Car.<init>()
INVOKEVIRTUAL     ; call Car.start()
POP               ; discard the return value (if any)
```

Notice: the reference is never stored in a local variable slot.

---

## 5. Visual Flow

```
Source Code: new Car().start();
                   |
                   ↓
             Compiler creates
             bytecode: NEW + INVOKESPECIAL + INVOKEVIRTUAL
                   |
                   ↓
             JVM allocates heap memory
                   |
                   ↓
             Constructor called → object initialized
                   |
                   ↓
             start() called on temp reference
                   |
                   ↓
             Method returns → temp reference disappears
                   |
                   ↓
             Object on heap has zero references
                   |
                   ↓
             GC marks as eligible for collection
                   |
                   ↓
             Next GC cycle frees heap memory
```

---

## 6. Syntax

```java
// Basic anonymous object
new ClassName();

// Anonymous object with method call
new ClassName().methodName();

// Anonymous object passed as argument
someMethod(new ClassName());

// Anonymous object with constructor arguments
new ClassName(arg1, arg2).methodName();

// Chaining (if method returns 'this')
new Builder().setName("A").setAge(25).build();
```

---

## 7. Examples

### Basic Example

```java
class Greeter {
    void sayHello() {
        System.out.println("Hello!");
    }
}

public class Main {
    public static void main(String[] args) {
        // Anonymous object — no variable, just use and discard
        new Greeter().sayHello(); // prints: Hello!
    }
}
```

Line by line:

- `new Greeter()` — creates object on heap, no reference stored
- `.sayHello()` — method called, prints output
- End of statement — object eligible for GC

### Intermediate Example

```java
class Car {
    private String model;

    Car(String model) {
        this.model = model;
    }

    void display() {
        System.out.println("Car: " + model);
    }
}

public class Main {
    static void showCar(Car c) {
        c.display();
    }

    public static void main(String[] args) {
        // Passing anonymous object as argument
        showCar(new Car("Tesla")); // Car: Tesla
    }
}
```

### Advanced Example — Builder Pattern

```java
// Anonymous object in builder pattern
String result = new StringBuilder()
    .append("Hello")
    .append(", ")
    .append("World")
    .toString();
System.out.println(result); // Hello, World
```

Here `new StringBuilder()` creates an anonymous object, then we chain methods. This is a very common real-world pattern.

### Production Example — Spring Boot

```java
// In Spring Boot controllers
ResponseEntity<String> response = ResponseEntity
    .ok()
    .header("X-Custom-Header", "value")
    .body("Success");

// HttpHeaders as anonymous object
restTemplate.exchange(
    url,
    HttpMethod.GET,
    new HttpEntity<>(new HttpHeaders()),  // anonymous HttpEntity
    String.class
);
```

---

## 8. Real World Usage

| Company | Usage |
| --------- | ------- |
| **Android** | `startActivity(new Intent(this, Target.class))` |
| **Spring Boot** | `new HttpHeaders()`, `new RestTemplate()` in one-time calls |
| **Netflix** | Builder chains without storing intermediate objects |
| **Google Guava** | `ImmutableList.of(new SomeConfig(...))` |
| **JUnit/Mockito** | `assertThrows(Ex.class, () -> new Foo(null))` |

---

## 9. Internal JVM Perspective

| Aspect | Detail |
| -------- | -------- |
| **Heap** | Object allocated here, same as referenced objects |
| **Stack** | No local variable slot consumed |
| **GC Impact** | Becomes eligible for GC immediately after use — short-lived, collected in Minor GC (Young Generation) |
| **Object Lifecycle** | Create → Use → Abandon → GC |
| **Performance** | Same as regular object; no overhead. If used in tight loop, may trigger frequent Minor GC |

### JVM Optimization

The JVM's **Escape Analysis** can detect that anonymous objects don't escape the method scope and may allocate them on the **stack** instead of the heap (JIT optimization — "scalar replacement"). This eliminates GC pressure entirely.

---

## 10. Advantages

| Advantage | Reason |
| ----------- | -------- |
| Concise code | No unnecessary variable declaration |
| GC friendly | Short-lived objects collected quickly |
| Clean API design | Enables fluent/builder patterns |
| Test-friendly | Easy to create throwaway test objects |
| Thread safe in isolation | No shared reference means no contention |

---

## 11. Disadvantages

| Disadvantage | Reason |
| -------------- | -------- |
| Cannot reuse | No reference = cannot call another method |
| Debugging hard | No variable name to inspect in debugger |
| Null risk | If constructor returns null (can't happen in Java, but factory methods can) |
| GC pressure in loops | Creating many anonymous objects in loops → frequent minor GC |

---

## 12. Tradeoffs

**Use anonymous objects when:**

- Object is used exactly once
- Passing as argument to a method
- Building method chains (builder pattern)
- One-time event handling

**Do NOT use when:**

- You need the object multiple times
- You need to inspect the object later
- Debugging is critical (hard to watch anonymous objects)
- Inside tight performance loops (prefer object pooling instead)

**Alternatives:**

- Store in a variable if reuse needed
- Use lambdas (Java 8+) for functional callbacks
- Use static factory methods for complex one-time objects

---

## 13. Common Mistakes

```java
// MISTAKE 1: Trying to reuse anonymous object
new Car().start(); // used
new Car().stop();  // new Car created! NOT the same object

// MISTAKE 2: Expecting return value to persist
// Wrong assumption: "I'll use this object twice"
// new Car().start();  <-- used
// .stop();            <-- COMPILE ERROR: cannot chain like this unless start() returns Car

// MISTAKE 3: Creating in a loop (GC pressure)
for (int i = 0; i < 1_000_000; i++) {
    new HeavyObject().process(); // Creates 1M objects → heavy GC
}
// Better: create once, reuse
HeavyObject obj = new HeavyObject();
for (int i = 0; i < 1_000_000; i++) {
    obj.process();
}
```

---

## 14. Interview Questions

**Easy:**

1. What is an anonymous object in Java?
2. Can an anonymous object be reused?

**Medium:**
3. How is an anonymous object different from a regular object in terms of memory?
4. Where does an anonymous object live — stack or heap?
5. What happens to an anonymous object after the statement ends?

**Hard:**
6. How does JVM Escape Analysis optimize anonymous object allocation?
7. Can you use anonymous objects in multi-threaded code safely?

**Expected Key Answers:**

- Anonymous objects live on the **heap**, not stack (unless escape analysis applies)
- They become GC eligible immediately after the statement
- They cannot be reused
- JIT can eliminate them via scalar replacement if they don't escape

---

---

# PART 2: INHERITANCE AND TYPES

---

## 1. Overview

### What is it?

**Inheritance** is a mechanism by which one class (child/subclass/derived) acquires the properties (fields) and behaviors (methods) of another class (parent/superclass/base). It represents an **IS-A relationship**.

```java
class Animal {          // Parent class
    void eat() { ... }
}

class Dog extends Animal { // Dog IS-A Animal
    void bark() { ... }
}
```

### Why was it introduced?

- **Code Reuse**: Write once in parent, use in all children
- **Extensibility**: Add behavior without modifying parent
- **Polymorphism**: Enables runtime method dispatch
- **Abstraction**: Model real-world hierarchies

### What problem does it solve?

Without inheritance, every class must redefine common behavior. With inheritance:

- Eliminates code duplication
- Enables a common interface for related types
- Supports the Open/Closed Principle (open for extension, closed for modification)

### History

- Java 1.0: Basic inheritance with `extends`
- Java 5: Covariant return types in overriding
- Java 8: Default methods in interfaces (changed inheritance landscape)
- Java 9+: Private methods in interfaces
- Java 17: Sealed classes (restrict inheritance hierarchy)
- Java 21: Pattern matching with sealed hierarchies

### Real-world importance

Foundation of OOP. Every Java framework (Spring, Hibernate, etc.) uses it extensively.

---

## 2. Intuition

Think of **biological inheritance**: a child inherits genes from parents. A dog has Animal properties (eats, sleeps, breathes) PLUS its own properties (barks, fetches). The dog doesn't redefine "eating" — it inherits it.

Another analogy: A **corporate org chart**. A Manager IS-A Employee. All Employee behavior (getting paid, having an ID) is inherited. Manager adds extra behavior (approves leaves).

---

## 3. Core Concept

### Types of Inheritance in Java

| Type | Description | Java Support |
| ------ | ------------- | ------------- |
| **Single** | One parent, one child | ✅ Yes |
| **Multilevel** | Chain: A → B → C | ✅ Yes |
| **Hierarchical** | One parent, many children | ✅ Yes |
| **Multiple** | One child, many parents (classes) | ❌ Not via class |
| **Hybrid** | Combination of types | ✅ Via interfaces |

### Keywords

| Keyword | Purpose |
| --------- | --------- |
| `extends` | Inherit from a class |
| `implements` | Inherit from an interface |
| `super` | Reference to parent class |
| `@Override` | Mark overriding methods |

### What is inherited?

| Inherited | Not Inherited |
| ----------- | --------------- |
| Public fields and methods | Private fields and methods |
| Protected fields and methods | Constructors |
| Default (package-private) if same package | Static members (inherited but not overridden) |
| Nested classes | |

> **Critical:** Private members are NOT inherited. They exist in the parent object but are inaccessible to the child.

---

## 4. Internal Working

### Single Inheritance

```
     Animal
    /       
  Dog      
```

```java
class Animal {
    String name = "Animal";
    void eat() { System.out.println("Eating"); }
}

class Dog extends Animal {
    void bark() { System.out.println("Barking"); }
}
```

When `Dog d = new Dog()` is created:

1. JVM allocates memory for BOTH Animal and Dog fields on heap
2. `super()` is automatically called first → Animal constructor runs
3. Then Dog constructor runs
4. The Dog object contains all Animal fields + Dog fields

### Memory Layout of Dog object on heap

```
+------------------------+
| Object Header          |  (mark word, class pointer)
+------------------------+
| name (from Animal)     |  ← Animal part
+------------------------+
| (other Animal fields)  |
+------------------------+
| (Dog-specific fields)  |  ← Dog part
+------------------------+
```

### Multilevel Inheritance

```
    Animal → Dog → GoldenRetriever
```

```java
class Animal {
    void breathe() { System.out.println("Breathing"); }
}

class Dog extends Animal {
    void bark() { System.out.println("Barking"); }
}

class GoldenRetriever extends Dog {
    void fetch() { System.out.println("Fetching"); }
}
```

Constructor call chain: `GoldenRetriever()` → `Dog()` → `Animal()` → `Object()`

### Hierarchical Inheritance

```
         Animal
        /      \
      Dog      Cat
```

Multiple children share one parent. Each child has its own specialization.

### Why Multiple Inheritance via Classes is NOT Allowed

The **Diamond Problem**:

```
        A (method m())
       / \
      B   C   (both override m())
       \ /
        D   (which m() to call??)
```

Java avoids this ambiguity. Instead, Java allows:

- Multiple interface implementation (interfaces solved this with explicit rules in Java 8)
- Hierarchical composition

### Multiple Inheritance via Interfaces (Java 8+)

```java
interface Flyable { default void move() { System.out.println("Flying"); } }
interface Swimmable { default void move() { System.out.println("Swimming"); } }

class Duck implements Flyable, Swimmable {
    @Override
    public void move() {
        Flyable.super.move(); // Must explicitly resolve diamond
    }
}
```

### Constructor Chaining in Inheritance

```java
class A {
    A() {
        super(); // Object() called
        System.out.println("A constructor");
    }
}

class B extends A {
    B() {
        super(); // A() called automatically if not explicitly written
        System.out.println("B constructor");
    }
}

class C extends B {
    C() {
        super(); // B() called
        System.out.println("C constructor");
    }
}

// new C() prints:
// A constructor
// B constructor
// C constructor
```

---

## 5. Visual Flow

```
Single Inheritance:
Animal
  └── Dog

Multilevel Inheritance:
Animal
  └── Dog
        └── GoldenRetriever

Hierarchical Inheritance:
Animal
  ├── Dog
  ├── Cat
  └── Bird

Multiple via Interfaces:
Flyable  Swimmable
   \       /
    Duck (implements both)

Hybrid Inheritance:
Animal
  ├── FlyingAnimal (Flyable)
  └── AquaticAnimal (Swimmable)
        └── AquaticFlyingAnimal
              (implements both interfaces)

Constructor Call Stack (new C()):
main()
  → C()
    → super() → B()
      → super() → A()
        → super() → Object()
        ← Object() done
      ← A() done
    ← B() done
  ← C() done
```

---

## 6. Syntax

```java
// Single inheritance
class Child extends Parent { }

// Implementing interface
class MyClass implements MyInterface { }

// Multiple interfaces
class MyClass implements Interface1, Interface2, Interface3 { }

// Inheritance + interface
class Child extends Parent implements Interface1, Interface2 { }

// super keyword — call parent method
class Dog extends Animal {
    @Override
    void eat() {
        super.eat();         // call Animal's eat()
        System.out.println("Dog also chews bone");
    }
}

// super keyword — call parent constructor
class Dog extends Animal {
    Dog(String name) {
        super(name);         // call Animal(String name)
    }
}

// super keyword — access parent field
class Dog extends Animal {
    void display() {
        System.out.println(super.name); // parent field
    }
}
```

---

## 7. Examples

### Basic — Single Inheritance

```java
class Vehicle {
    int speed;

    Vehicle(int speed) {
        this.speed = speed;   // Initialize speed in parent
    }

    void accelerate() {
        speed += 10;          // Common behavior in parent
    }
}

class Car extends Vehicle {

    String brand;

    Car(int speed, String brand) {
        super(speed);         // Call parent constructor — REQUIRED
        this.brand = brand;   // Initialize child-specific field
    }

    void honk() {
        System.out.println(brand + " beeps!"); // Child-specific method
    }
}

public class Main {
    public static void main(String[] args) {
        Car c = new Car(60, "Toyota");
        c.accelerate();       // inherited from Vehicle
        System.out.println(c.speed); // 70 — inherited field
        c.honk();             // Toyota beeps! — own method
    }
}
```

### Intermediate — Multilevel + super

```java
class Shape {
    String color;

    Shape(String color) {
        this.color = color;
    }

    double area() {
        return 0.0;  // Default — meant to be overridden
    }

    void display() {
        System.out.println("Shape color: " + color);
    }
}

class Rectangle extends Shape {
    double width, height;

    Rectangle(String color, double w, double h) {
        super(color);   // Shape(color) called
        this.width = w;
        this.height = h;
    }

    @Override
    double area() {
        return width * height;
    }
}

class Square extends Rectangle {
    Square(String color, double side) {
        super(color, side, side);  // Rectangle(color, side, side)
    }

    @Override
    void display() {
        super.display();           // prints color from Shape
        System.out.println("Side: " + width);
    }
}

// new Square("Red", 5).display() prints:
// Shape color: Red
// Side: 5.0
```

### Advanced — Sealed Classes (Java 17)

```java
// Restrict who can extend Shape
sealed class Shape permits Circle, Rectangle, Triangle { }

final class Circle extends Shape {
    double radius;
    Circle(double r) { this.radius = r; }
}

final class Rectangle extends Shape {
    double w, h;
    Rectangle(double w, double h) { this.w = w; this.h = h; }
}

non-sealed class Triangle extends Shape { } // Can be extended further

// Pattern matching with switch (Java 21)
double area(Shape s) {
    return switch (s) {
        case Circle c    -> Math.PI * c.radius * c.radius;
        case Rectangle r -> r.w * r.h;
        case Triangle t  -> 0; // simplified
    };
}
```

### Production — Spring Boot Service Hierarchy

```java
// Base service class
public abstract class BaseService<T, ID> {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    public abstract T findById(ID id);
    public abstract List<T> findAll();
    public abstract T save(T entity);

    // Common behavior inherited by all services
    protected void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }
}

// Child inherits all common behavior
@Service
public class UserService extends BaseService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        validateNotNull(id, "userId"); // inherited from BaseService
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        validateNotNull(user, "user");
        return userRepository.save(user);
    }
}
```

---

## 8. Internal JVM Perspective

### vtable (Virtual Method Table)

The JVM maintains a **vtable** for each class — a table of pointers to the actual method implementations. Inheritance affects the vtable:

```
Animal vtable:
+------------------+
| eat → Animal.eat |
| toString → Obj   |
+------------------+

Dog vtable (extends Animal):
+------------------+
| eat → Animal.eat | ← inherited (not overridden)
| bark → Dog.bark  | ← new method
| toString → Obj   |
+------------------+

GuideDog vtable (overrides eat):
+------------------+
| eat → Guide.eat  | ← overridden (points to GuideDog.eat)
| bark → Dog.bark  | ← inherited from Dog
| guide → Guide    | ← new
| toString → Obj   |
+------------------+
```

### Heap Memory During Inheritance

```
Dog object on heap:
+---------------------------+
| Object Header (16 bytes)  |  ← mark word + class pointer
| name (String ref, Animal) |  ← inherited from Animal
| age (int, Animal)         |  ← inherited from Animal
| breed (String, Dog)       |  ← Dog's own field
+---------------------------+
```

The child object literally contains the parent's fields in memory.

### Metaspace

Class hierarchy information, method tables (vtables, itable for interfaces), field layouts are stored in **Metaspace** (Java 8+, previously PermGen).

### GC Impact

- Objects with deep inheritance hierarchies have more fields → larger memory footprint
- Parent and child share no memory (each instance is independent)
- No GC impact from inheritance itself, but object size affects GC pressure

---

## 9. Advantages

| Advantage | Reason |
| ----------- | -------- |
| Code reuse | Common logic in parent, shared by all children |
| Polymorphism | Enables runtime method dispatch |
| Extensibility | Add new types without modifying existing code |
| Maintainability | Fix bug in parent → all children benefit |
| Abstraction | Model real-world IS-A relationships |
| Framework support | Spring, Hibernate built entirely on inheritance |

---

## 10. Disadvantages

| Disadvantage | Reason |
| -------------- | -------- |
| Tight coupling | Child depends on parent implementation |
| Fragile Base Class problem | Changing parent can break children |
| Deep hierarchies | Hard to understand, navigate, and debug |
| Limited flexibility | Java's single inheritance restricts design |
| Cannot un-inherit | Cannot selectively exclude parent behavior |
| Liskov violations | Poorly designed hierarchies violate substitutability |

---

## 11. Common Mistakes

```java
// MISTAKE 1: Calling super() not as first statement
class Dog extends Animal {
    Dog() {
        System.out.println("Dog"); // ← COMPILE ERROR!
        super();                   // super() must be FIRST
    }
}

// MISTAKE 2: Thinking private members are inherited
class Animal {
    private String dna = "ATCG"; // private
}

class Dog extends Animal {
    void print() {
        System.out.println(dna); // COMPILE ERROR — not accessible
    }
}

// MISTAKE 3: Confusing IS-A with HAS-A
// Wrong: class Car extends Engine (Car IS-A Engine?)
// Right: class Car { Engine engine; } (Car HAS-A Engine)

// MISTAKE 4: Multiple class inheritance attempt
class A { }
class B { }
class C extends A, B { } // COMPILE ERROR in Java

// MISTAKE 5: Overriding with weaker access
class Animal {
    public void eat() { }
}
class Dog extends Animal {
    private void eat() { } // COMPILE ERROR — cannot reduce visibility
}
```

---

## 12. Interview Questions

**Easy:**

1. What is inheritance? What keyword is used?
2. What are the types of inheritance in Java?
3. Does Java support multiple inheritance with classes?
4. What is the difference between `extends` and `implements`?

**Medium:**
5. What is the Diamond Problem? How does Java solve it?
6. What happens when you don't call `super()` explicitly?
7. Are constructors inherited in Java?
8. Can we override static methods?
9. What is the Fragile Base Class Problem?

**Hard:**
10. How does the JVM vtable work with inheritance?
11. Explain how sealed classes (Java 17) change the inheritance model.
12. What is the Liskov Substitution Principle and how does it relate to inheritance?
13. Compare inheritance vs composition — when would you choose each?

**Trick Questions:**

- "Can a child class have a constructor if the parent class has no default constructor?"
  - Answer: Yes, but the child MUST explicitly call the parent's parameterized constructor with `super(args)`
- "Is `String` inheritable?"
  - Answer: No. `String` is `final` — cannot be extended.

---

---

# PART 3: METHOD OVERRIDING

---

## 1. Overview

### What is it?

**Method overriding** is when a subclass provides a specific implementation of a method that is already defined in its parent class. The method in the child class has the **same name, same parameters, same (or covariant) return type**.

```java
class Animal {
    void sound() { System.out.println("Some sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Woof"); } // overrides Animal.sound()
}
```

### Why was it introduced?

To enable **runtime polymorphism** — the ability to call the same method name on different objects and get behavior appropriate to the actual object type.

### Problem it solves

Without overriding, you can't customize inherited behavior. With it:

- Dogs bark, Cats meow — same method name `sound()`, different behavior
- Framework calls `draw()` on any Shape — each shape draws itself correctly

---

## 2. Intuition

Think of an **employee performance review form**. The base form says "Describe your contribution." A Software Engineer fills it one way; a Manager fills it differently. Same question, different tailored answer. That's overriding — same method signature, different implementation per subclass.

---

## 3. Core Concept

### Rules for Method Overriding

| Rule | Detail |
| ------ | -------- |
| **Same method name** | Exact same name, case-sensitive |
| **Same parameter list** | Type, count, and order must match |
| **Same or covariant return type** | Child can return a subtype (Java 5+) |
| **Same or wider access modifier** | public can stay public, but private cannot become public in override context |
| **Same or narrower checked exceptions** | Can throw fewer/narrower checked exceptions |
| **Cannot override static methods** | Static → method hiding, not overriding |
| **Cannot override final methods** | final = sealed, no override allowed |
| **Cannot override private methods** | Private methods not visible to child |
| **Cannot override constructors** | Constructors are not methods |

### @Override Annotation

```java
@Override  // ← Always use this!
void sound() { ... }
```

`@Override` tells the compiler: "I intend to override a parent method." If the signature doesn't match any parent method, the compiler throws an error. This prevents accidental typos from creating a new method instead of overriding.

### Covariant Return Types (Java 5+)

```java
class Animal {
    Animal create() { return new Animal(); }
}

class Dog extends Animal {
    @Override
    Dog create() { return new Dog(); } // return type is subtype — valid!
}
```

### Access Modifier Rules

```
Parent: private  → Child: Cannot override (not visible)
Parent: default  → Child: default, protected, or public (widen only)
Parent: protected → Child: protected or public
Parent: public   → Child: must be public (cannot narrow)
```

---

## 4. Internal Working

### How the JVM decides which method to call

The JVM uses **virtual method dispatch** (explained in detail in Part 7). At runtime:

1. JVM looks at the **actual object type** (not the reference type)
2. Checks the vtable for that class
3. Finds the correct method implementation
4. Executes it

```java
Animal a = new Dog(); // Reference type: Animal, Actual type: Dog
a.sound();            // JVM checks Dog's vtable → calls Dog.sound()
```

### vtable for overriding

```
Animal vtable:
+-------------------------+
| sound → Animal.sound()  |
+-------------------------+

Dog vtable (overrides sound):
+-------------------------+
| sound → Dog.sound()     | ← points to Dog's implementation
+-------------------------+
```

When you call `a.sound()` where `a` references a Dog, the JVM uses Dog's vtable entry, which points to `Dog.sound()`.

### Bytecode — INVOKEVIRTUAL

The `sound()` call compiles to `INVOKEVIRTUAL`, which does dynamic dispatch at runtime:

```bytecode
INVOKEVIRTUAL Animal.sound()V
```

This instruction says "call the sound() method, resolving at runtime based on actual object type."

---

## 5. Visual Flow

```
Parent class: Animal
Method: void sound() → "Some sound"

Child class: Dog overrides sound()
Method: void sound() → "Woof"

At runtime:
Animal ref = new Dog();
ref.sound();
    |
    ↓
JVM inspects actual type: Dog
    |
    ↓
Looks up Dog's vtable
    |
    ↓
Finds Dog.sound() → executes it
    |
    ↓
Output: "Woof"  ← NOT "Some sound"
```

---

## 6. Syntax

```java
class Parent {
    // Method to be overridden
    ReturnType methodName(ParamType param) throws CheckedException {
        // parent implementation
    }
}

class Child extends Parent {
    @Override                     // annotation — recommended, not required
    ReturnType methodName(ParamType param) {  // same signature
        // child-specific implementation
        // optionally: super.methodName(param) to include parent behavior
    }
}
```

---

## 7. Examples

### Basic

```java
class Shape {
    double area() {
        return 0.0; // default — meaningless for abstract shape
    }
}

class Circle extends Shape {
    double radius;

    Circle(double r) { this.radius = r; }

    @Override
    double area() {
        return Math.PI * radius * radius; // concrete implementation
    }
}

class Rectangle extends Shape {
    double width, height;

    Rectangle(double w, double h) { this.width = w; this.height = h; }

    @Override
    double area() {
        return width * height; // different implementation, same method name
    }
}

Shape s1 = new Circle(5);
Shape s2 = new Rectangle(4, 6);
System.out.println(s1.area()); // 78.53... — Circle's area()
System.out.println(s2.area()); // 24.0 — Rectangle's area()
```

### Intermediate — super in Override

```java
class Logger {
    void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

class TimedLogger extends Logger {
    @Override
    void log(String message) {
        System.out.print("[" + System.currentTimeMillis() + "] "); // add timestamp
        super.log(message); // call parent's log, don't duplicate the [LOG] logic
    }
}
// Output: [1701234567890] [LOG] Server started
```

### Advanced — Covariant Return Types

```java
class Connection {
    Connection getConnection() {
        System.out.println("Creating base connection");
        return new Connection();
    }
}

class DatabaseConnection extends Connection {
    @Override
    DatabaseConnection getConnection() {   // covariant return type — valid!
        System.out.println("Creating DB connection");
        return new DatabaseConnection();
    }
}

// Client code can use the more specific type
DatabaseConnection conn = new DatabaseConnection().getConnection();
```

### Production — Strategy/Template Method in Spring

```java
// Abstract base handler
public abstract class BaseRequestHandler {

    // Template method — defines the algorithm skeleton
    public final ResponseEntity<Object> handle(HttpServletRequest request) {
        validate(request);          // common step
        Object result = process(request); // polymorphic step — overridden by child
        return ResponseEntity.ok(result);
    }

    protected void validate(HttpServletRequest request) {
        // common validation
    }

    // Subclasses override this
    protected abstract Object process(HttpServletRequest request);
}

@Component
public class UserRequestHandler extends BaseRequestHandler {
    @Override
    protected Object process(HttpServletRequest request) {
        // user-specific processing
        return userService.findById(extractId(request));
    }
}

@Component
public class OrderRequestHandler extends BaseRequestHandler {
    @Override
    protected Object process(HttpServletRequest request) {
        // order-specific processing
        return orderService.findById(extractId(request));
    }
}
```

---

## 8. Common Mistakes

```java
// MISTAKE 1: Wrong parameter type — creates OVERLOAD, not OVERRIDE
class Animal {
    void sound(String type) { }
}
class Dog extends Animal {
    void sound(int times) { } // ← Different param! This is OVERLOADING, not OVERRIDING
    // @Override here would cause compile error
}

// MISTAKE 2: Forgetting @Override — typo creates new method
class Animal {
    void sound() { }
}
class Dog extends Animal {
    void Sound() { } // ← Capital S! New method, not override. @Override would catch this.
}

// MISTAKE 3: Overriding static method — it's method HIDING
class Animal {
    static void breathe() { System.out.println("Animal breathes"); }
}
class Dog extends Animal {
    static void breathe() { System.out.println("Dog breathes"); } // hiding, not overriding!
}
// Animal a = new Dog(); a.breathe() → calls Animal.breathe()! (static dispatch)

// MISTAKE 4: Throwing broader checked exception
class Animal {
    void eat() throws IOException { }
}
class Dog extends Animal {
    @Override
    void eat() throws Exception { } // COMPILE ERROR! Exception is broader than IOException
}

// MISTAKE 5: Narrowing access modifier
class Animal {
    public void eat() { }
}
class Dog extends Animal {
    @Override
    protected void eat() { } // COMPILE ERROR! Cannot narrow public to protected
}
```

---

## 9. Interview Questions

**Easy:**

1. What is method overriding?
2. What is the `@Override` annotation for?
3. Can we override `static` and `private` methods?

**Medium:**
4. What are the rules of method overriding?
5. What is covariant return type?
6. Difference between method overriding and method hiding?
7. Can we override a `final` method?

**Hard:**
8. How does the JVM implement method overriding at the bytecode level?
9. What is INVOKEVIRTUAL vs INVOKESTATIC?
10. Can we override a method and make it throw fewer exceptions? More exceptions?

**Trick:**

- "Can we override a method in the same class?"
  - No! Overriding requires inheritance. Same class = overloading or redefining.
- "What happens if we override `hashCode()` but not `equals()`?"
  - HashMap/HashSet behavior becomes inconsistent (covered in equals() section)

---

---

# PART 4: PACKAGES

---

## 1. Overview

### What is it?

A **package** is a namespace that organizes classes and interfaces. It's a folder-like grouping mechanism for Java source files.

```
com.company.project.service.UserService
    ↑           ↑       ↑       ↑
 reverse       project  layer  class
 domain
```

### Why was it introduced?

- **Namespace management**: Prevent class name collisions
- **Access control**: Package-level access modifier
- **Modularity**: Group related classes
- **Security**: Restrict access across packages

### Problems it solves

- Without packages: If you have two `Date` classes from different libraries, they conflict. With packages: `java.util.Date` vs `java.sql.Date` — no conflict.

### History

- Java 1.0: Basic package system
- Java 9: Java Module System (JPMS) — modules contain packages
- Naming convention: reverse domain name

---

## 2. Intuition

Think of packages like **folders in a filing cabinet**. Instead of dumping all documents in one place, you organize them: Finance → Q1 → Reports. In Java: `com.company.finance.reports`. Just as you can restrict who accesses the Finance cabinet, packages support access control.

---

## 3. Core Concept

### Package Declaration

```java
package com.company.project.service; // MUST be the first statement
```

### Import Statement

```java
import java.util.ArrayList;       // specific class import
import java.util.*;               // wildcard import (all classes in package)
import static java.lang.Math.PI; // static import (Java 5+)
```

### Default Package

If no `package` statement → class belongs to the **default (unnamed) package**.

- Bad practice for production code
- Cannot be imported by classes in named packages

### Built-in Java Packages

| Package | Contents |
| --------- | ---------- |
| `java.lang` | String, Object, System, Math, Integer — **auto-imported** |
| `java.util` | ArrayList, HashMap, Scanner, Collections, Date |
| `java.io` | File, InputStream, OutputStream |
| `java.util.concurrent` | ExecutorService, ConcurrentHashMap, AtomicInteger |
| `java.net` | Socket, URL, HttpURLConnection |
| `java.time` | LocalDate, LocalDateTime, ZonedDateTime (Java 8+) |
| `java.math` | BigDecimal, BigInteger |
| `java.sql` | Connection, PreparedStatement, ResultSet |
| `java.nio` | ByteBuffer, Path, Files |

### Package Naming Conventions

```
com.company.project.module.layer

Examples:
com.google.guava.collect
org.springframework.boot.autoconfigure
com.netflix.hystrix.core
io.micronaut.http.client
```

---

## 4. Internal Working

### Package → Folder Mapping

The package name maps exactly to the directory structure:

```
Package: com.company.project.service.UserService
Folder:  src/main/java/com/company/project/service/UserService.java
```

### Compilation and Classpath

```
javac -d . com/company/project/service/UserService.java
  ↓
Creates: com/company/project/service/UserService.class
  ↓
java com.company.project.service.UserService
```

The JVM uses the **classpath** to find package directories. It maps the package name to folder paths on the classpath.

### Access Within Packages

```
Package A:  Class A1, A2 — can access each other's package-private (default) members
Package B:  Class B1 — CANNOT access A1's default members (different package)
```

### Java 9 Module System

In Java 9+, packages live inside **modules** (defined by `module-info.java`):

```java
// module-info.java
module com.company.myapp {
    requires java.sql;               // depend on this module
    exports com.company.myapp.api;   // expose this package
}
```

---

## 5. Visual Flow

```
Source Directory Layout:
src/
 └── main/
      └── java/
           └── com/
                └── company/
                     └── project/
                          ├── model/
                          │    ├── User.java        → package com.company.project.model;
                          │    └── Order.java
                          ├── service/
                          │    ├── UserService.java  → package com.company.project.service;
                          │    └── OrderService.java
                          ├── repository/
                          │    └── UserRepository.java
                          └── controller/
                               └── UserController.java

Import chain:
UserController
  └── imports UserService (package com.company.project.service)
       └── imports UserRepository (package com.company.project.repository)
            └── imports User (package com.company.project.model)
```

---

## 6. Syntax

```java
// 1. Package declaration (first line of file)
package com.company.project.service;

// 2. Import specific class
import java.util.ArrayList;

// 3. Import all classes from package (wildcard)
import java.util.*;   // Note: does NOT import sub-packages

// 4. Static import
import static java.lang.Math.PI;
import static java.util.Collections.*;

// 5. Fully Qualified Name (no import needed)
java.util.Date today = new java.util.Date();

// 6. Handling name collision
import java.util.Date;
// java.sql.Date used via FQN:
java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
```

---

## 7. Examples

### Basic — Creating a Package

```java
// File: src/com/company/greet/Greeter.java
package com.company.greet;  // declare package

public class Greeter {
    public void greet(String name) {
        System.out.println("Hello, " + name + "!");
    }
}

// File: src/Main.java
import com.company.greet.Greeter; // import the package class

public class Main {
    public static void main(String[] args) {
        Greeter g = new Greeter();
        g.greet("World"); // Hello, World!
    }
}
```

### Intermediate — Resolving Name Conflicts

```java
import java.util.Date;  // import one

public class Main {
    public static void main(String[] args) {
        Date utilDate = new Date();              // java.util.Date
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis()); // FQN
        System.out.println(utilDate);
        System.out.println(sqlDate);
    }
}
```

### Advanced — Package with Access Modifiers

```java
// Package: com.company.security

// File: EncryptionKey.java
package com.company.security;

class EncryptionKey {     // package-private — NOT public!
    String key = "SECRET123"; // only accessible within com.company.security
}

// File: Encryptor.java
package com.company.security;

public class Encryptor {  // public — accessible anywhere
    private EncryptionKey key = new EncryptionKey(); // Can access — same package!

    public String encrypt(String data) {
        return data + key.key; // uses package-private class
    }
}

// From outside the package:
// EncryptionKey k = new EncryptionKey(); // COMPILE ERROR — package-private
// Encryptor e = new Encryptor();         // OK — public
```

### Production — Standard Maven Project Structure

```
my-spring-app/
├── src/main/java/com/mycompany/myapp/
│   ├── MyAppApplication.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── DatabaseConfig.java
│   ├── controller/
│   │   ├── UserController.java
│   │   └── OrderController.java
│   ├── service/
│   │   ├── UserService.java
│   │   └── OrderService.java
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── OrderRepository.java
│   ├── model/
│   │   ├── User.java
│   │   └── Order.java
│   └── dto/
│       ├── UserDTO.java
│       └── OrderDTO.java
└── src/test/java/com/mycompany/myapp/
    └── service/
        └── UserServiceTest.java
```

---

## 8. Common Mistakes

```java
// MISTAKE 1: Package not first statement
import java.util.ArrayList; // ← import before package!
package com.company;        // COMPILE ERROR

// MISTAKE 2: Package name doesn't match directory
package com.company.service;
// File is in: com/company/controller/  → COMPILE ERROR at runtime

// MISTAKE 3: Wildcard import imports sub-packages
import java.util.*;  // Does NOT import java.util.concurrent.*
// You still need: import java.util.concurrent.ExecutorService;

// MISTAKE 4: Thinking static import changes the class
import static java.lang.Math.sqrt;
// You can now write sqrt(4) instead of Math.sqrt(4)
// BUT Math is still Math — you didn't import a different thing

// MISTAKE 5: Using default package in production
// No package declaration → default package
// Cannot be imported by any named package
// Never do this in real projects
```

---

## 9. Interview Questions

**Easy:**

1. What is a package in Java?
2. Which package is automatically imported in Java?
3. What is the naming convention for packages?

**Medium:**
4. What is the difference between `import java.util.*` and `import java.util.ArrayList`?
5. How do you handle two classes with the same name from different packages?
6. What is a static import?
7. Can a class be in the default package and import from named packages?

**Hard:**
8. Explain the Java 9 Module System and how it enhances packages.
9. What is the relationship between package access modifier and inheritance?
10. How does the JVM find classes using the classpath/module path?

---

---

# PART 5: ACCESS MODIFIERS (public, private, protected)

---

## 1. Overview

### What are they?

Access modifiers are keywords that control the **visibility/accessibility** of classes, methods, and fields.

| Modifier | Keyword | Scope |
| ---------- | --------- | ------- |
| Public | `public` | Everywhere |
| Private | `private` | Same class only |
| Protected | `protected` | Same package + subclasses |
| Default | _(none)_ | Same package only |

### Why were they introduced?

To implement **encapsulation** — one of the pillars of OOP. Hide internal implementation, expose only what's necessary.

### Problem they solve

Without access control:

- Any class can modify any field → data corruption
- Internal implementation is exposed → cannot change without breaking clients
- Security vulnerabilities in sensitive systems

---

## 2. Intuition

Think of **concentric security zones** at a company:

```
public:    Anyone from the street can enter (no badge needed)
default:   Only company employees (same building) can enter
protected: Employees + their dependents (family/subclasses) can enter
private:   Only you (same class) can access — locked personal safe
```

---

## 3. Core Concept

### Accessibility Table

| Context | private | default | protected | public |
| --------- | --------- | --------- | ----------- | -------- |
| Same class | ✅ | ✅ | ✅ | ✅ |
| Same package (subclass) | ❌ | ✅ | ✅ | ✅ |
| Same package (non-subclass) | ❌ | ✅ | ✅ | ✅ |
| Different package (subclass) | ❌ | ❌ | ✅ | ✅ |
| Different package (non-subclass) | ❌ | ❌ | ❌ | ✅ |

### Class-Level Access

- Classes can be `public` or **default** only (not private or protected)
  - Exception: Inner/nested classes can have all modifiers
- `public` class: accessible from any package
- default class: accessible only within the same package

---

## 4. Internal Working

### private

```java
class BankAccount {
    private double balance;  // ONLY this class can access

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;  // controlled access via public method
        }
    }

    public double getBalance() {
        return balance;
    }
}
```

At bytecode level: private access is enforced by the JVM. Attempting to access a private field from outside the class results in `IllegalAccessError` at runtime (after compiler already catches it at compile time).

### protected

```java
// Package: com.company.vehicles

class Vehicle {
    protected int speed;         // Accessible in package + subclasses
    protected void maintain() { } // Accessible in package + subclasses
}

// In SAME PACKAGE — can access protected
class Garage {  // not a subclass
    void service(Vehicle v) {
        v.speed = 0;       // ✅ same package
        v.maintain();      // ✅ same package
    }
}
```

```java
// Package: com.company.sports (DIFFERENT package)
import com.company.vehicles.Vehicle;

class SportsCar extends Vehicle {  // subclass
    void turboBoost() {
        speed += 100;   // ✅ accessible — subclass
        maintain();     // ✅ accessible — subclass
    }
}

class Mechanic {  // NOT a subclass, different package
    void fix(Vehicle v) {
        v.speed = 0;    // ❌ COMPILE ERROR — not subclass, different package
    }
}
```

### Default (Package-Private)

No keyword — accessible only within the same package.

```java
package com.company.internal;

class InternalHelper {  // default class — not public
    int helperField = 42;  // default field

    void helperMethod() { } // default method
}
```

Only classes in `com.company.internal` can use `InternalHelper`.

---

## 5. Visual Flow

```
Access Modifier Decision Flow:

Who needs to access this member?
         |
         ↓
Only within THIS class?
  → private
         |
         ↓
Also within the same PACKAGE?
  → default (no keyword)
         |
         ↓
Also within SUBCLASSES (even in different packages)?
  → protected
         |
         ↓
Anywhere in the entire application?
  → public

Encapsulation Rule:
Use the MOST RESTRICTIVE modifier that still allows the needed access
private > default > protected > public
```

---

## 6. Examples

### Basic — Encapsulation with private

```java
class Person {
    private String name;   // private — no direct access from outside
    private int age;

    // Public constructor — controlled creation
    public Person(String name, int age) {
        this.name = name;
        setAge(age);       // use setter for validation
    }

    // Public getter — read access
    public String getName() { return name; }

    // Public setter with validation
    public void setAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Invalid age: " + age);
        }
        this.age = age;
    }

    public int getAge() { return age; }
}
```

### Intermediate — protected in inheritance

```java
package com.animals;

public class Animal {
    protected String name;          // child classes can access directly

    protected Animal(String name) { // protected constructor
        this.name = name;
    }

    protected void breathe() {      // common behavior for subclasses
        System.out.println(name + " is breathing");
    }
}

// Different package
package com.pets;

import com.animals.Animal;

public class Dog extends Animal {
    public Dog(String name) {
        super(name);      // ✅ protected constructor accessible via super
    }

    public void greet() {
        System.out.println("Hi! I'm " + name); // ✅ protected field
        breathe();                               // ✅ protected method
    }
}
```

### Advanced — Access Modifiers in Spring Boot

```java
@Service
public class UserService {

    // private — implementation detail, not exposed
    private final UserRepository userRepository;

    // private — internal helper, not API
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    // public constructor — Spring needs this for dependency injection
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // public — this is the API contract
    public User getUserById(Long id) {
        log.info("Fetching user: {}", id);
        return findOrThrow(id); // delegates to private method
    }

    // private — internal logic, implementation detail
    private User findOrThrow(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }

    // protected — available for subclasses (e.g., AdminUserService)
    protected boolean isAdmin(User user) {
        return user.getRoles().contains("ADMIN");
    }
}
```

---

## 7. Common Mistakes

```java
// MISTAKE 1: Making everything public (no encapsulation)
public class User {
    public String password; // HUGE security risk
    public double salary;   // anyone can change it
}

// MISTAKE 2: Thinking protected = private in same class
class A {
    protected int x = 10;
}
class B {
    A a = new A();
    void test() {
        // From a DIFFERENT PACKAGE, accessing protected field on instance:
        System.out.println(a.x); // COMPILE ERROR if B is in different package
        // Protected allows subclass access, not arbitrary access in different package
    }
}

// MISTAKE 3: Private constructor edge case
class Singleton {
    private static Singleton instance;
    private Singleton() { } // private constructor
    public static Singleton getInstance() {
        if (instance == null) instance = new Singleton();
        return instance;
    }
}
// Cannot do: new Singleton() from outside — that's the point!

// MISTAKE 4: Narrowing protected to private in override
class Parent {
    protected void method() { }
}
class Child extends Parent {
    @Override
    private void method() { } // COMPILE ERROR — cannot narrow
}
```

---

## 8. Interview Questions

**Easy:**

1. List all access modifiers in Java.
2. What is the default access modifier?
3. Can a class be private?

**Medium:**
4. What is the difference between `protected` and default access?
5. Can you access a `protected` member of a different package's class without extending it?
6. When would you use `protected` vs `private`?

**Hard:**
7. Can reflection bypass access modifiers? (Answer: Yes — `field.setAccessible(true)`)
8. How do access modifiers interact with the Java Module System (Java 9+)?
9. Explain the relationship between access modifiers and encapsulation as a security feature.

**Trick:**

- "Can a class have `private` as its access modifier?"
  - Top-level class: No. Inner/nested class: Yes.

---

# Java Core Engineering Handbook

## Topics: User Input · BufferedReader · Threads · Runnable · Collection API · Comparator & Comparable · Wrapper Classes

> **Production-Grade Revision Notes** — SDE Interviews · Senior Backend Engineering · System Design · JVM Internals

---

# TABLE OF CONTENTS

1. [User Input in Java](#topic-1-user-input-in-java)
2. [BufferedReader](#topic-2-bufferedreader)
3. [Threads](#topic-3-threads)
4. [Runnable](#topic-4-runnable)
5. [Collection API](#topic-5-collection-api)
6. [Comparator and Comparable](#topic-6-comparator-and-comparable)
7. [Wrapper Classes](#topic-7-wrapper-classes)

---

# TOPIC 1: User Input in Java

---

## 1. Overview

| Attribute | Details |
| --- | --- |
| **What is it?** | Mechanism to read data from standard input (keyboard, file, network) at runtime |
| **Why introduced?** | Programs need to interact with users and external systems dynamically |
| **Problem it solves** | Allows dynamic data entry rather than hardcoded values |
| **Core Classes** | `Scanner`, `BufferedReader`, `Console`, `DataInputStream` |
| **Since** | Java 1.0 (`InputStream`), Java 1.1 (`InputStreamReader`), Java 5 (`Scanner`) |
| **Industry Use** | CLI tools, build systems, test utilities, competitive programming |

**Real-World Importance:**

- Every backend application reads config, request body, or user commands
- Competitive programming heavily uses `BufferedReader` for fast input
- Spring Boot reads properties and environment variables using similar streams
- AWS Lambda reads event input via `InputStream`

---

## 2. Intuition

Imagine your computer is a cashier at a counter.

- **Standard Input (`System.in`)** = The speaking tube through which a customer talks
- **Scanner** = A smart assistant who translates what the customer says into useful info (int, String, double)
- **BufferedReader** = A faster assistant who collects an entire sentence at once before processing

When you type on a keyboard, keystrokes are stored in an **input buffer** (temporary memory). Java reads from this buffer using various classes.

---

## 3. Core Concepts

### System.in

- Type: `java.io.InputStream`
- Represents: Standard input stream (keyboard by default)
- Raw byte stream — reads one byte at a time
- Not human-friendly (returns raw bytes)

### Scanner

- Type: `java.util.Scanner`
- Wraps `InputStream` or `Reader`
- Tokenizes input using delimiter (default: whitespace)
- Provides `nextInt()`, `nextDouble()`, `nextLine()`, `next()` etc.
- **Thread-UNSAFE**
- Internally uses regex — **slower** for large inputs

### BufferedReader

- Type: `java.io.BufferedReader`
- Wraps a `Reader` (like `InputStreamReader`)
- Reads entire lines efficiently
- **Faster** than Scanner for large inputs
- Only provides `readLine()` — you parse manually

### Console

- Type: `java.io.Console`
- Available via `System.console()`
- Best for password input (doesn't echo characters)
- **Returns null in IDEs** (no console attached)

### DataInputStream (Legacy)

- Reads primitive types from binary stream
- Mostly replaced by Scanner/BufferedReader in modern code

---

## 4. Internal Working

### Scanner Internal Flow

```
User types: "42 hello"
              ↓
      Keyboard Buffer (OS)
              ↓
        System.in (InputStream)
              ↓
      Scanner wraps System.in
              ↓
  Scanner reads tokens using Pattern/Regex
              ↓
   nextInt() → parses "42" → returns 42
   next()    → parses "hello" → returns "hello"
```

### Scanner Token Engine (Internals)

1. Scanner maintains an internal `CharBuffer`
2. Uses a `Pattern` (default `\p{javaWhitespace}+`) as delimiter
3. `hasNextXxx()` — peeks ahead without consuming
4. `nextXxx()` — consumes and parses next token
5. Internally uses `java.util.regex.Matcher`

### BufferedReader Internal Flow

```
User types: "Hello World\n"
              ↓
      Keyboard Buffer (OS)
              ↓
        System.in (InputStream)      ← raw bytes
              ↓
    InputStreamReader                ← bytes → chars (UTF-8 decoding)
              ↓
      BufferedReader                 ← chars buffered (default 8192 chars)
              ↓
       readLine()                    ← returns "Hello World" (strips \n)
```

### Memory Behavior

| Class | Buffer Size | Memory |
| --- | --- | --- |
| `Scanner` | No explicit buffer | Regex engine objects allocated per parse |
| `BufferedReader` | 8192 chars (default) | One `char[]` allocated upfront |
| `System.in` | OS-managed | No Java heap allocation |

---

## 5. Visual Flow

```
[Keyboard]
    │
    ▼
[OS Input Buffer]
    │
    ▼
[System.in]  ────────────────────────────────────────────
    │                                                     │
    ▼                                                     ▼
[Scanner(System.in)]              [InputStreamReader(System.in)]
    │                                                     │
    │  tokenizes by whitespace                            ▼
    │  regex-based parsing                   [BufferedReader(isr)]
    │                                                     │
    ▼                                                     ▼
nextInt() / nextLine() / next()                    readLine()
    │                                                     │
    ▼                                                     ▼
  int / String / double                            String (or null)
```

---

## 6. Syntax

```java
// ── Scanner ──────────────────────────────────────
Scanner sc = new Scanner(System.in);   // wrap System.in
int n        = sc.nextInt();           // read integer token
long l       = sc.nextLong();          // read long token
double d     = sc.nextDouble();        // read double token
String word  = sc.next();             // read single whitespace-delimited word
String line  = sc.nextLine();         // read entire line including spaces
boolean more = sc.hasNext();          // check if more input exists
sc.close();                           // close (releases OS resources)

// ── BufferedReader ───────────────────────────────
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String line2 = br.readLine();          // reads one line; returns null at EOF
int n2 = Integer.parseInt(br.readLine()); // parse line to int
br.close();                            // always close

// ── Console (password input) ─────────────────────
Console console = System.console();
if (console != null) {
    char[] pwd = console.readPassword("Enter password: "); // input not echoed
    String name = console.readLine("Enter name: ");
}
```

**Keyword-by-keyword:**

| Symbol/Keyword | Meaning |
| --- | --- |
| `System.in` | Static field of `System` class, type `InputStream` |
| `new Scanner(System.in)` | Creates Scanner wrapping the InputStream |
| `nextInt()` | Reads next whitespace-delimited token, parses as int |
| `nextLine()` | Reads until newline (`\n`), returns String without `\n` |
| `new InputStreamReader(System.in)` | Converts byte stream to character stream |
| `new BufferedReader(isr)` | Adds 8192-char buffer on top of char stream |
| `readLine()` | Reads a full line; returns `null` at end of stream |
| `Integer.parseInt()` | Converts String to primitive int |

---

## 7. Examples

### Basic — Reading a Single Integer

```java
import java.util.Scanner;

public class BasicInput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);  // create scanner wrapping stdin
        System.out.print("Enter a number: ");
        int num = sc.nextInt();               // read integer from stdin
        System.out.println("You entered: " + num);
        sc.close();                           // release file descriptor
    }
}
```

### Intermediate — Reading Multiple Values

```java
import java.util.Scanner;

public class MultiInput {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();                 // read array size
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();            // read each element
        }
        sc.close();
    }
}
```

### Advanced — Fast Input with BufferedReader + StringTokenizer

```java
import java.io.*;
import java.util.StringTokenizer;

public class FastInput {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim()); // read n, trim whitespace
        int[] arr = new int[n];
        // StringTokenizer is faster than String.split() — no regex
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken()); // parse each space-separated token
        }
        br.close();
    }
}
```

### Production — Reading Config from InputStream

```java
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class ConfigReader {
    public static Properties loadConfig(InputStream inputStream) throws IOException {
        Properties props = new Properties();
        // InputStreamReader converts bytes to chars using UTF-8
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            props.load(reader);  // reads key=value pairs line by line
        }
        return props;
    }
}
```

---

## 8. Real-World Usage

| Company/Framework | Use Case |
| --- | --- |
| **Amazon AWS Lambda** | `InputStream` as handler parameter to read event JSON |
| **Spring Boot** | `application.properties` loaded via `InputStream` + `Properties` |
| **Netflix** | Log file analysis using `BufferedReader` for line-by-line processing |
| **Competitive Programming** | `BufferedReader` + `StringTokenizer` for sub-millisecond I/O |
| **Build Tools (Maven/Gradle)** | Reading `pom.xml`, `build.gradle` via buffered streams |
| **Kafka Consumer** | Reading serialized byte streams from topics |

---

## 9. Internal JVM Perspective

| Aspect | Detail |
| --- | --- |
| **System.in** | Stored in `java.lang.System` as `public static final InputStream` |
| **Stream Wrapper Chain** | Each wrapper is an object on the **Heap** holding a reference to the next |
| **char[] buffer** | `BufferedReader`'s 8192-char buffer is a `char[]` allocated on Heap |
| **GC Impact** | Scanner creates many short-lived String/Pattern/Matcher objects → GC pressure |
| **Stack** | `readLine()` method frame on stack; result String stored on Heap |
| **Native Call** | `System.in.read()` eventually calls `FileInputStream.read0()` — a JNI native method |

---

## 10. Time Complexity

| Operation | Scanner | BufferedReader |
| --- | --- | --- |
| Read one int | O(k) where k = digits | O(k) with amortized O(1) buffer |
| Read n integers | O(n × k) with regex overhead | O(n × k) minimal overhead |
| Memory | Higher (regex objects per token) | Lower (one `char[]` reused) |
| Throughput (large input) | ~10× slower | Baseline fast |

---

## 11. Advantages

| Class | Advantage |
| --- | --- |
| `Scanner` | Easy API, built-in type-safe methods, pattern matching |
| `Scanner` | Handles whitespace and newlines gracefully |
| `BufferedReader` | Very fast (no regex overhead per token) |
| `BufferedReader` | Low memory footprint (one buffer reused) |
| `Console` | Secure password input (characters masked) |

---

## 12. Disadvantages

| Class | Disadvantage |
| --- | --- |
| `Scanner` | Slow for large inputs (regex per token) |
| `Scanner` | `nextLine()` after `nextInt()` bug (notorious interview topic) |
| `Scanner` | Not thread-safe |
| `BufferedReader` | Manual parsing required (no `readInt()`) |
| `Console` | Returns `null` in IDE — breaks programs silently |
| `System.in` | Cannot be easily mocked in unit tests |

### The Infamous nextLine() Bug

```java
Scanner sc = new Scanner(System.in);
int n = sc.nextInt();      // reads "5", leaves "\n" in buffer
String s = sc.nextLine();  // READS THE LEFTOVER "\n" — s = "" !!

// FIX: consume the leftover newline first
int n2 = sc.nextInt();
sc.nextLine();             // flush the leftover \n
String s2 = sc.nextLine(); // now reads the actual next line correctly
```

---

## 13. Tradeoffs

| Scenario | Recommended Class |
| --- | --- |
| Simple programs, few inputs | `Scanner` |
| Competitive programming, large input | `BufferedReader` + `StringTokenizer` |
| Password/secure input | `Console` |
| Reading files line by line | `BufferedReader` |
| Spring Boot config reading | `Properties.load()` or `@Value` |
| Binary data reading | `DataInputStream` |

---

## 14. Comparison

| Feature | Scanner | BufferedReader | Console |
| --- | --- | --- | --- |
| Package | `java.util` | `java.io` | `java.io` |
| Input granularity | Tokens | Lines | Lines |
| Speed | Slow | Fast | Medium |
| Built-in parsing | Yes | No (manual) | No (manual) |
| Thread-safe | No | No | No |
| Password input | No | No | Yes |
| Null in IDE | No | No | Yes |
| Introduced | Java 5 | Java 1.1 | Java 6 |

---

## 15. Common Mistakes

1. **Not closing** Scanner/BufferedReader → OS file descriptor leak
2. **`nextLine()` after `nextInt()`** → reads empty string (leftover `\n`)
3. **Using `System.console()` in IDE** → NullPointerException
4. **Not handling `IOException`** with BufferedReader
5. **Using Scanner for large inputs** → TLE in competitive programming
6. **Not specifying charset** in `InputStreamReader` → encoding corruption in production

---

## 16. Best Practices

- Always use **try-with-resources** to auto-close streams
- Prefer `BufferedReader` for performance-critical code
- Always specify charset explicitly: `new InputStreamReader(System.in, StandardCharsets.UTF_8)`
- Use `.trim()` on `readLine()` to remove trailing whitespace/carriage returns
- Validate input before parsing (catch `NumberFormatException`)

```java
// Production-grade input reading pattern
try (BufferedReader br = new BufferedReader(
        new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
    String line;
    while ((line = br.readLine()) != null) {
        // process each line
    }
} catch (IOException e) {
    throw new RuntimeException("Failed to read input", e);
}
```

---

## 17. Interview Section

**Easy:**

1. What is `System.in`?
2. How is `Scanner` different from `BufferedReader`?
3. What does `nextLine()` return when it encounters end of input?

**Medium:**
4. Why is `Scanner` slow for large inputs?
5. Explain the `nextInt()` then `nextLine()` bug with a code example.
6. What happens if `System.console()` returns null?

**Hard:**
7. How does `Scanner` internally tokenize input using regex?
8. How does `BufferedReader` buffer data internally — walk through the `readLine()` flow.
9. How would you unit-test code that reads from `System.in`?

**Very Hard:**
10. Explain the native call chain from `Scanner.nextInt()` down to OS level.
11. How would you implement a custom fast reader for competitive programming?
12. How does charset encoding affect `InputStreamReader` behavior?

---

## 18. Coding Questions

**Easy:**

1. Read N integers and print their sum.
2. Read a full line and count the number of words.
3. Read lines until the user types "END".
4. Read a matrix of N×M integers.
5. Read name and age on separate lines.

**Medium:**
6. Read a CSV line and parse its values into an array.
7. Read alternating int and String pairs from input.
8. Implement buffered console input without using Scanner.
9. Read a file line-by-line using BufferedReader.
10. Handle EOF gracefully for both Scanner and BufferedReader.

**Hard:**
11. Build a generic custom input reader faster than Scanner.
12. Read input and build a graph as an adjacency list.
13. Multi-threaded input processing pipeline.
14. Read binary data from `System.in`.
15. Build a mock `InputStream` for unit testing.

---

## 19. Production Scenarios

**Scenario 1: TLE in competitive programming**

- Problem: Scanner too slow for 10^6 inputs
- Fix: Replace with `BufferedReader` + `StringTokenizer`

**Scenario 2: Encoding bug in production logs**

- Problem: Special characters garbled from user input on Windows servers
- Fix: Explicitly specify `StandardCharsets.UTF_8` in `InputStreamReader`

**Scenario 3: Resource leak in long-running service**

- Problem: `Scanner` created inside a loop but never closed
- Fix: Use try-with-resources, keep scanner outside the loop

---

## 20. Internal Deep Dive

`Scanner` internally uses:

- `java.util.regex.Pattern.compile(delimiter)` — compiled once at construction
- `java.nio.CharBuffer` — holds unprocessed characters read so far
- `java.io.StreamDecoder` — embedded inside `InputStreamReader` for byte-to-char conversion

`BufferedReader` internally:

- `char cb[]` — 8192-char default buffer (`char[8192]`)
- `int nChars` — how many chars currently in buffer
- `int nextChar` — position of next char to deliver
- On `readLine()`: scans `cb[]` for `\n` or `\r\n`, builds and returns substring

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| --- | --- |
| `next()` vs `nextLine()` | `next()` reads one whitespace-delimited word; `nextLine()` reads entire line including spaces |
| `Scanner(System.in)` vs `Scanner(file)` | Same class, different source — API is identical |
| `read()` vs `readLine()` | `read()` returns single char as int (-1 at EOF); `readLine()` returns full String (null at EOF) |
| EOF handling | `readLine()` returns `null`; `Scanner.hasNext()` returns `false` |

---

## 22. Cheat Sheet

```
User Input Quick Reference
══════════════════════════════════════════════════════
Scanner sc = new Scanner(System.in);
  int     → sc.nextInt()
  long    → sc.nextLong()
  double  → sc.nextDouble()
  word    → sc.next()
  line    → sc.nextLine()
  check   → sc.hasNext() / sc.hasNextInt()
  TRAP    → nextInt() leaves \n; call nextLine() after!

BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  line    → br.readLine()           // null at EOF
  int     → Integer.parseInt(br.readLine())
  tokens  → new StringTokenizer(br.readLine())
  FAST    → always use for large input

Always close with try-with-resources!
══════════════════════════════════════════════════════
```

---

## 23. Mind Map

```
User Input
├── System.in (InputStream — raw bytes from keyboard)
│   ├── InputStreamReader (bytes → chars with charset)
│   │   └── BufferedReader (buffers 8192 chars)
│   │       └── readLine() → String / null
│   └── Scanner (regex tokenizer)
│       ├── nextInt(), nextLong(), nextDouble()
│       ├── next(), nextLine()
│       └── hasNext(), hasNextInt()
├── System.console()
│   ├── readLine()
│   └── readPassword() (masked)
└── Common Issues
    ├── nextLine() after nextInt() bug
    ├── Console null in IDE
    ├── Scanner slow on large input
    └── Charset encoding issues
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --- | --- |
| `System.in` | Static `InputStream` representing keyboard; raw bytes |
| `Scanner` | Tokenizes input using regex; convenient but slow |
| `BufferedReader` | Line-by-line fast reader; 8192-char buffer |
| `InputStreamReader` | Bridge converting byte stream to char stream |
| `readLine()` | Returns next line as String; null at EOF |
| `nextLine()` bug | Reads leftover `\n` after `nextInt()` — add extra `nextLine()` to flush |
| `Console` | Secure input with password masking; null inside IDE |
| `StringTokenizer` | Fast token parser; faster than `String.split()` for I/O |

---

## 25. Memory Tricks

- **S**canner = **S**low (regex **S**peed penalty on every token)
- **B**ufferedReader = **B**lindingly fast (**B**uffer = batch reads)
- `nextLine()` after `nextInt()` → think **"flush the toilet after use"** (flush the `\n`)
- **C**onsole = **C**onfidential (passwords, **C**haracters masked)
- `readLine()` returns **null** at EOF → "null = nothing left to read"

---

## 26. Important Keywords

| Term | Meaning |
| --- | --- |
| `InputStream` | Abstract class for reading raw bytes |
| `Reader` | Abstract class for reading characters |
| `InputStreamReader` | Converts InputStream to Reader using charset |
| `BufferedReader` | Adds buffering and `readLine()` to a Reader |
| `Scanner` | Utility class for tokenized input parsing with regex |
| `StringTokenizer` | Fast string tokenizer (no regex overhead) |
| `delimiter` | Pattern Scanner uses to split tokens (default: whitespace) |
| `EOF` | End of stream — `readLine()` returns null, `read()` returns -1 |
| `charset` | Character encoding (UTF-8, ISO-8859-1, etc.) |

---

## 27. Interview One-Liners

- "Scanner uses regex for tokenization making it slower; BufferedReader reads raw lines directly."
- "System.in is a static InputStream representing standard input from keyboard."
- "readLine() returns null at EOF — use this as the while-loop termination condition."
- "nextLine() after nextInt() is a classic Java trap — the newline character is left in the buffer."
- "System.console() returns null when running inside an IDE."
- "BufferedReader's default buffer is 8192 characters allocated as a char[] on the heap."

---

## 28. Summary

Java provides multiple ways to read user input. `System.in` is the raw byte stream from the keyboard. `Scanner` wraps it with a convenient API but is slow due to regex-based tokenization. `BufferedReader` wraps `InputStreamReader` around `System.in` for fast, buffered, line-based reading. `Console` adds password masking but fails in IDEs. The infamous `nextLine()`-after-`nextInt()` bug catches most beginners. In production, always use try-with-resources, specify charset explicitly, and choose `BufferedReader` for performance-critical paths.

---

## 29. Further Learning

| Topic | Why |
| --- | --- |
| `java.nio` (NIO) | Modern non-blocking I/O; used in high-performance servers |
| `Files.readAllLines()` | Java 7+ convenience for small file reading |
| `InputStream` class hierarchy | Understanding the full I/O class hierarchy |
| Java Serialization | Reading objects from binary streams |
| Spring `@RequestBody` | How Spring reads HTTP request bodies (same stream concepts) |

---
---

# TOPIC 2: BufferedReader

---

## 1. Overview

| Attribute | Details |
| --- | --- |
| **What is it?** | A buffering character-input stream that reads text efficiently |
| **Package** | `java.io` |
| **Introduced** | Java 1.1 |
| **Superclass** | `Reader` |
| **Why introduced?** | `FileReader`/`InputStreamReader` reads one char at a time — too many OS syscalls |
| **Problem solved** | Reduces OS system calls by buffering many chars in memory at once |
| **Key method** | `readLine()` — reads a full line at once |

**Industry Usage:**

- Log processing pipelines (Netflix, Twitter)
- Large file parsing (Hadoop, Spark pre-processing stages)
- Competitive programming fast I/O
- HTTP response body reading (pre-NIO era)

---

## 2. Intuition

Imagine moving water from a lake to a tank.

**Without buffering:** You carry one cup at a time. Each trip is costly (one OS syscall per char).

**With buffering:** You use a large bucket (8192 chars). You fill the bucket once, then pour from the bucket — far fewer trips to the lake.

`BufferedReader` is that large bucket for character input.

---

## 3. Core Concepts

### Character Buffer

- `char[] cb` — internal array of 8192 characters (default)
- Filled by calling the underlying `Reader.read(char[], off, len)`
- Program reads from this array without touching the OS each time

### readLine()

- Scans the buffer for `\n`, `\r`, or `\r\n`
- Returns characters up to (but not including) the line terminator
- Calls the underlying reader only when the buffer is exhausted

### mark() and reset()

- `mark(readAheadLimit)` — saves current read position
- `reset()` — returns to the saved position
- Useful for lookahead parsing scenarios

### Underlying Reader

- Can wrap: `FileReader`, `InputStreamReader`, `StringReader`, `CharArrayReader`
- BufferedReader delegates actual disk/network reads to the wrapped Reader

---

## 4. Internal Working

### Initialization

```
new BufferedReader(reader, 8192)
  → allocates char[] cb = new char[8192]
  → nChars = 0   (buffer starts empty)
  → nextChar = 0 (read position at start)
```

### readLine() Flow

```
readLine() called
    │
    ▼
Is buffer empty? (nextChar >= nChars)
    │
    YES → fill(): reader.read(cb, 0, cb.length)
    │     → OS syscall: reads up to 8192 chars
    │     → nChars updated to chars actually read
    │
    ▼
Scan cb[] from nextChar looking for '\n' or '\r'
    │
    ├── Found at index i
    │     → return new String(cb, nextChar, i - nextChar)
    │       nextChar = i + 1
    │
    └── Not found (buffer exhausted without \n)
          → copy remaining buffer to StringBuilder
          → fill buffer again (loop until \n or EOF)
```

### Memory Layout

```
Heap:
┌──────────────────────────────────────────┐
│  BufferedReader object                    │
│  ├── Reader in        (reference)         │
│  ├── char[] cb        = new char[8192]    │  ← 8192×2 bytes = 16 KB
│  ├── int nChars       = 1024              │
│  ├── int nextChar     = 0                 │
│  └── int markedChar   = -1                │
└──────────────────────────────────────────┘
```

---

## 5. Visual Flow

```
[OS / File System / Keyboard]
          │
          │  (syscall fills up to 8192 chars at once)
          ▼
┌──────────────────────────────┐
│  char[] cb  (8192 chars)     │  ← BufferedReader's internal buffer
│  H e l l o \n W o r l d \n  │
└──────────────────────────────┘
          │
   readLine() scans for \n
          │
          ▼
  returns "Hello" (new String on Heap)
          │
   nextChar advances past \n
          │
   next readLine() returns "World"
          │
   next readLine() returns null (EOF)
```

---

## 6. Syntax

```java
// ── Wrapping InputStreamReader (stdin) ─────────────────
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

// ── Wrapping FileReader (file) ──────────────────────────
BufferedReader br2 = new BufferedReader(new FileReader("file.txt"));

// ── Custom buffer size (for large files) ────────────────
BufferedReader br3 = new BufferedReader(new FileReader("big.txt"), 65536);

// ── Reading methods ─────────────────────────────────────
String line   = br.readLine();          // one line; null at EOF
int ch        = br.read();              // one char as int; -1 at EOF
int count     = br.read(buf, 0, 10);   // up to 10 chars into buf[]

// ── Mark / Reset ────────────────────────────────────────
br.mark(100);    // mark current position; 100 = max chars to read before reset
br.reset();      // go back to marked position

// ── Utility ─────────────────────────────────────────────
boolean ready = br.ready(); // true if buffer not empty (non-blocking hint)
br.skip(5);                 // skip 5 characters

// ── Always use try-with-resources ───────────────────────
try (BufferedReader br4 = new BufferedReader(new FileReader("data.txt"))) {
    String line2;
    while ((line2 = br4.readLine()) != null) {
        System.out.println(line2);
    }
}   // auto-closes br4 even on exception
```

---

## 7. Examples

### Basic — Read File Line by Line

```java
import java.io.*;

public class ReadFile {
    public static void main(String[] args) throws IOException {
        // FileReader opens file; BufferedReader adds 8192-char buffer
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            // Assignment inside condition is idiomatic Java I/O pattern
            while ((line = br.readLine()) != null) {  // null signals EOF
                System.out.println(line);
            }
        }   // try-with-resources auto-closes br
    }
}
```

### Intermediate — Parse CSV File

```java
import java.io.*;
import java.util.*;

public class CSVParser {
    public static List<String[]> parse(String filename) throws IOException {
        List<String[]> result = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");  // split by comma delimiter
                result.add(parts);
            }
        }
        return result;
    }
}
```

### Advanced — Large File with Custom Buffer Size

```java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class LargeFileProcessor {
    private static final int BUFFER_SIZE = 1 << 16; // 65536 chars = 64KB

    public static long countLines(String path) throws IOException {
        long lines = 0;
        // 64KB buffer reduces syscalls for large files
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8),
                BUFFER_SIZE)) {
            while (br.readLine() != null) lines++;
        }
        return lines;
    }
}
```

### Production — HTTP Response Reading

```java
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class SimpleHttpClient {
    public static String get(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder sb = new StringBuilder();
        // Same wrapper chain: InputStream → InputStreamReader → BufferedReader
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        }
        return sb.toString();
    }
}
```

---

## 8. Real-World Usage

| Context | Detail |
| --- | --- |
| **Apache Kafka** | Buffered streams for reading topic partition data |
| **Hadoop MapReduce** | `LineRecordReader` wraps `BufferedReader` internally |
| **Spring MVC** | `HttpServletRequest.getReader()` returns a `BufferedReader` |
| **Elasticsearch** | Bulk API reads NDJSON using `BufferedReader` |
| **Log4j/Logback** | Rolling file appender reads old files via buffered streams |
| **JUnit** | Test output capture piped through `BufferedReader` |

**Spring Boot Connection:**

```java
// In a Spring MVC controller
@PostMapping("/upload")
public ResponseEntity<String> handleUpload(HttpServletRequest request) throws IOException {
    BufferedReader reader = request.getReader(); // returns BufferedReader!
    StringBuilder body = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null) {
        body.append(line);
    }
    return ResponseEntity.ok(body.toString());
}
```

---

## 9. Internal JVM Perspective

| JVM Area | BufferedReader Detail |
| --- | --- |
| **Heap** | `char[] cb` (8–64KB), the `BufferedReader` object, each `String` from `readLine()` |
| **Stack** | `readLine()` stack frame; local variables `nextChar`, `i`, `startChar` |
| **GC** | Each `readLine()` creates a new `String` — can cause GC pressure in tight loops |
| **Native** | `FileInputStream.read0()` → native OS `read()` syscall |
| **Method Area** | `BufferedReader.class` bytecode loaded once into Metaspace |

**GC Optimization — avoid String per line:**

```java
// Instead of creating a String per line when you only need raw chars:
char[] buf = new char[8192];
int charsRead;
while ((charsRead = br.read(buf, 0, buf.length)) != -1) {
    // process buf[0..charsRead-1] without any String allocation
}
```

---

## 10. Time Complexity

| Operation | Complexity | Notes |
| --- | --- | --- |
| `readLine()` (chars in buffer) | O(L) where L = line length | Scans buffer array for `\n` |
| `readLine()` (needs buffer refill) | O(L + B) where B = fill overhead | Amortized: ~1 syscall per 8192 chars |
| `read()` single char | O(1) amortized | Buffer refill cost amortized over 8192 reads |
| Full file of N chars | O(N) | ~N/8192 syscalls total |

---

## 11. Advantages

- Drastically reduces OS system calls (batches reads into 8192-char chunks)
- Simple and well-known API (`readLine()`)
- Supports `mark()`/`reset()` for lookahead parsing
- Works with any `Reader` source (file, network, string, etc.)
- Memory-efficient: reuses the same `char[]` buffer

---

## 12. Disadvantages

- No built-in type parsing (must convert manually with `Integer.parseInt()`, etc.)
- `readLine()` strips line terminators — you lose `\r\n` information
- Not thread-safe
- `ready()` is unreliable for blocking streams (false ≠ EOF)
- Each `readLine()` creates a new `String` on heap → GC pressure in tight loops

---

## 13. Tradeoffs

| Use BufferedReader | Don't Use BufferedReader |
| --- | --- |
| Large file processing | Simple single-line reads |
| Performance-critical code | When Scanner's convenience is sufficient |
| Line-by-line text parsing | Binary data (use `DataInputStream`) |
| Network I/O response reading | Structured object data (use `ObjectInputStream`) |

---

## 14. Comparison

| Feature | BufferedReader | FileReader | Scanner | Files.readAllLines() |
| --- | --- | --- | --- | --- |
| Buffered | Yes (8192 chars) | No | Internal | Yes |
| Returns | String (lines) | int (chars) | Typed tokens | `List<String>` |
| EOF signal | null | -1 | false | end of list |
| Speed | Fast | Slow | Slowest | Fast (Java 8+) |
| Memory | Low (streaming) | Low | Medium | HIGH (all in RAM) |
| Use case | Streaming large files | Rarely used alone | Easy small input | Small files only |

---

## 15. Common Mistakes

1. **Not closing** → OS file descriptor leak (use try-with-resources)
2. **Ignoring null from readLine()** → NullPointerException when file ends
3. **Wrong charset** → garbled output for non-ASCII characters on some platforms
4. **Using `ready()` to detect EOF** → wrong (`ready()` checks buffer, not stream end)
5. **Creating `BufferedReader` inside a loop** → buffer created and discarded per iteration
6. **Assuming `\n` is preserved** → `readLine()` strips the line terminator

---

## 16. Best Practices

```java
// 1. Always try-with-resources
try (BufferedReader br = new BufferedReader(new FileReader("file.txt"))) { }

// 2. Always specify charset
new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))

// 3. Increase buffer for large files (64KB)
new BufferedReader(reader, 65536)

// 4. Java 8 streams alternative for small files
Files.lines(Paths.get("file.txt"), StandardCharsets.UTF_8)
     .forEach(System.out::println);

// 5. Never assume non-null — always check in loop condition
String line;
while ((line = br.readLine()) != null) {
    // process line
}
```

---

## 17. Interview Section

**Easy:**

1. What is the default buffer size of `BufferedReader`?
2. What does `readLine()` return at the end of the stream?
3. How do you wrap `System.in` with `BufferedReader`?

**Medium:**
4. Why is `BufferedReader` faster than reading char-by-char with `FileReader`?
5. How does `mark()`/`reset()` work in `BufferedReader`?
6. How does `BufferedReader` differ from `BufferedInputStream`?

**Hard:**
7. Explain the internal buffer refill mechanism step by step.
8. How does `BufferedReader` handle `\r\n` Windows line endings?
9. What happens if you call `read()` after the buffer is exhausted?

**Very Hard:**
10. How would you implement a thread-safe `BufferedReader` wrapper?
11. What is the performance difference between `BufferedReader` and `java.nio.Files.lines()`?
12. Explain how Hadoop's `LineRecordReader` uses `BufferedReader` internally.

---

## 18. Coding Questions

**Easy:**

1. Read and print all lines from a file.
2. Count the number of lines in a file.
3. Find the longest line in a file.
4. Read only the first 5 lines of a file.
5. Check if a file contains a specific word.

**Medium:**
6. Parse a TSV file into a 2D String array.
7. Find duplicate lines in a file.
8. Filter lines that match a regex pattern.
9. Read a properties-style file into a Map.
10. Count word frequency across all lines.

**Hard:**
11. Implement a custom `BufferedReader` with a `peek()` method.
12. Process a 10GB file with minimal memory usage (streaming).
13. Multi-threaded file line processing with work-stealing.
14. Benchmark: `BufferedReader` vs `java.nio.Files.lines()` for 1M lines.
15. Build a streaming CSV parser using `BufferedReader`.

---

## 19. Production Scenarios

**Scenario 1: Memory spike reading large files**

- Problem: `Files.readAllLines()` loads entire 5GB file into RAM → OutOfMemoryError
- Fix: Use `BufferedReader` + streaming processing, never materialise full list

**Scenario 2: Encoding issues in production logs**

- Problem: Log files with mixed encoding crash parser on non-ASCII characters
- Fix: Use `CharsetDecoder` with `CodingErrorAction.REPLACE` to handle malformed sequences

**Scenario 3: Slow log analysis job**

- Problem: Reading 1M-line log file takes 30 seconds
- Fix: Increase buffer from default 8192 to 65536 chars; consider parallel stream processing

---

## 20. Internal Deep Dive (OpenJDK)

```java
// OpenJDK BufferedReader.readLine() simplified pseudocode:
public String readLine() throws IOException {
    StringBuilder s = null;
    int startChar;

    synchronized (lock) {
        for (;;) {
            if (nextChar >= nChars) fill(); // refill buffer from underlying Reader
            if (nextChar >= nChars) {       // still empty = EOF
                return (s == null) ? null : s.toString();
            }

            // scan buffer for line terminator
            for (int i = nextChar; i < nChars; i++) {
                char c = cb[i];
                if (c == '\n' || c == '\r') {
                    String str = new String(cb, startChar, i - startChar);
                    nextChar = i + 1;
                    // handle \r\n
                    return str;
                }
            }
            // buffer exhausted without \n — append to StringBuilder and refill
            if (s == null) s = new StringBuilder();
            s.append(cb, startChar, nChars - startChar);
        }
    }
}
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| --- | --- |
| `BufferedReader` vs `BufferedInputStream` | `BufferedReader` = chars; `BufferedInputStream` = bytes |
| `read()` returns -1 vs `readLine()` returns null | Different EOF signals for different methods |
| `ready()` means data is available | `ready()` is a non-blocking hint; `false` does NOT mean EOF |
| `mark()` always works | `mark()` only works if `markSupported()` returns true |

---

## 22. Cheat Sheet

```
BufferedReader Quick Reference
══════════════════════════════════════════════════════
CREATION:
  new BufferedReader(new InputStreamReader(System.in))
  new BufferedReader(new FileReader("file.txt"))
  new BufferedReader(reader, 65536)      // custom buffer size

READING:
  String line = br.readLine()   // null at EOF
  int ch = br.read()            // -1 at EOF
  br.read(char[], off, len)     // fills char array

CONTROL:
  br.ready()        // is buffer non-empty? (NOT EOF check)
  br.mark(n)        // mark position; n = readAheadLimit
  br.reset()        // go back to mark
  br.skip(n)        // skip n chars
  br.close()        // ALWAYS CLOSE (use try-with-resources)

CANONICAL LOOP:
  while ((line = br.readLine()) != null) { ... }
══════════════════════════════════════════════════════
```

---

## 23. Mind Map

```
BufferedReader
├── Wraps
│   ├── InputStreamReader (stdin, network)
│   ├── FileReader (files)
│   └── StringReader (in-memory / testing)
├── Internal State
│   ├── char[] cb       (8192 chars default)
│   ├── int nChars      (chars currently in buffer)
│   ├── int nextChar    (next read position)
│   └── int markedChar  (mark position, -1 if not set)
├── Key Methods
│   ├── readLine() → String / null at EOF
│   ├── read() → int / -1 at EOF
│   ├── read(char[], off, len)
│   ├── mark(readAheadLimit) / reset()
│   └── ready() / close()
└── Use Cases
    ├── File processing (large files)
    ├── Network I/O (HTTP response)
    ├── Competitive programming
    └── Log and data pipeline parsing
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --- | --- |
| Buffer (`char[]`) | 8192-char array holding pre-read chars to reduce syscalls |
| `readLine()` | Reads until `\n`, `\r`, or `\r\n`; returns null at EOF |
| `fill()` | Internal method that calls underlying reader to refill buffer |
| `mark()` | Saves current read position for later `reset()` |
| `ready()` | Returns true if buffer has chars that can be read without blocking |
| Charset | Must specify when wrapping `InputStreamReader` for non-ASCII safety |
| Close | Closing `BufferedReader` also closes the underlying reader chain |

---

## 25. Memory Tricks

- **B**ufferedReader = **B**ucket of chars (8192 per trip)
- **readLine** → "reading a **LINE** in a book"
- **null** = "book is finished — no more lines"
- **-1** = "no more characters" (for `read()` method)
- **8192** = 8KB = **"8K bucket"**
- `ready()` = "Is the bucket already filled?" (non-blocking check, NOT EOF)

---

## 26. Important Keywords

| Term | Meaning |
| --- | --- |
| `Reader` | Abstract character-based input superclass |
| `char[] cb` | Internal character buffer array |
| `nChars` | Number of valid characters currently in buffer |
| `nextChar` | Index of next character to deliver |
| `fill()` | Internal: replenishes buffer from underlying Reader |
| `readLine()` | Reads until line terminator; returns String or null |
| `mark()` | Save read position for future reset |
| `reset()` | Return to marked position |
| `lock` | Object used for internal synchronization |

---

## 27. Interview One-Liners

- "BufferedReader reduces OS system calls by batch-reading up to 8192 chars into a char[] buffer."
- "readLine() returns null at EOF — always check for null in the while-loop condition."
- "BufferedReader is internally synchronized (uses a lock object) but not safe for concurrent reads."
- "Use BufferedReader over Scanner when reading large files or doing competitive programming."
- "Default buffer size is 8192 characters; can be customized in the constructor."
- "`BufferedReader` wraps a `Reader`; `BufferedInputStream` wraps an `InputStream`."

---

## 28. Summary

`BufferedReader` is Java's workhorse for efficient character-based input. It wraps any `Reader` and adds an 8192-char internal buffer (`char[]`) to minimize OS syscalls. Its key method `readLine()` returns complete lines as Strings and returns null at EOF. Unlike Scanner, it has no parsing helpers — but its speed advantage (no regex overhead) makes it essential for large files and competitive programming. Always use try-with-resources, specify charset explicitly, and check for null in your loop. In Spring MVC, `HttpServletRequest.getReader()` returns a `BufferedReader` directly — the same concept applies throughout web development.

---

## 29. Further Learning

| Topic | Why |
| --- | --- |
| `java.nio.Files.lines()` | Java 8 stream-based line reading (lazy, closeable) |
| `java.nio.ByteBuffer` | NIO buffer model (lower-level, non-blocking) |
| `FileChannel` | High-performance file I/O via NIO |
| `StreamTokenizer` | Faster tokenizer than Scanner for numeric-heavy data |
| `MappedByteBuffer` | Memory-mapped files for ultra-fast file reading |

---

# Java Complete Learning Handbook

## Core Java Fundamentals — Production-Grade Revision Notes

> **Covers:** Data Types · Operators · Classes & Objects · Strings · Static · Encapsulation · Getters & Setters · `this` Keyword · Constructors · `super` Keyword
>
> **Audience:** SDE Interviews | Senior Backend Engineering | System Design | Production Dev | JVM Internals
>
> **Java Versions Referenced:** Java 8 · 11 · 17 · 21

---

# TABLE OF CONTENTS

| # | Topic |
| --- | ------- |
| 1 | Data Types |
| 2 | Operators |
| 3 | Classes and Objects |
| 4 | Strings |
| 5 | Static Variable, Method, Block |
| 6 | Encapsulation |
| 7 | Getter and Setter |
| 8 | `this` Keyword |
| 9 | Constructors and Types |
| 10 | `super` Keyword |

---

---

# TOPIC 1: DATA TYPES

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | A data type defines the kind of value a variable can hold, the size of memory allocated, and the operations permitted on that value. |
| **Why introduced?** | To allow the JVM to allocate correct memory, enforce type safety at compile-time, and enable hardware-level optimizations. |
| **Problem solved** | Without types, the compiler cannot know how much memory to allocate or which operations are valid — leading to runtime crashes and undefined behaviour (as seen in C without strict typing). |
| **Introduced in** | Java 1.0 (1996) — Java was designed as a strongly, statically typed language from birth. |
| **Industry importance** | Type safety is Java's biggest selling point for enterprise software. All of Spring Framework, Hibernate, JPA, and every enterprise system depend on Java's type system for correctness and performance. |

### Where it is used

- Primitive types power mathematical computations in trading systems (HFT, banking)
- `long` is used for transaction IDs, timestamps (System.currentTimeMillis())
- `double` / `float` for scientific computing, ML models in Java
- `boolean` for feature flags in A/B testing systems
- `byte[]` for network payloads, serialization
- `char` for text processing pipelines

---

## 2. Intuition

Think of data types like **containers of different sizes and shapes**:

- A `byte` is a tiny shot glass — holds 8 bits, perfect for small numbers
- An `int` is a standard mug — holds 32 bits, most common everyday use
- A `long` is a bucket — holds 64 bits, for very large numbers
- A `double` is a graduated cylinder — for precise decimal measurements
- A `boolean` is a light switch — only ON or OFF (true/false)
- A `String` (reference type) is like a label pointing to text stored in a warehouse (heap memory)

**Key insight:** Primitive types live on the **stack** (fast, fixed-size). Reference types point to objects on the **heap** (flexible, GC-managed).

---

## 3. Core Concepts

### 3.1 Two Categories

```
Java Data Types
├── Primitive (8 types) — stored by VALUE
│   ├── Numeric Integer: byte, short, int, long
│   ├── Numeric Float:   float, double
│   ├── Character:       char
│   └── Boolean:         boolean
│
└── Reference (Non-Primitive) — stored by REFERENCE
    ├── String
    ├── Arrays
    ├── Classes (user-defined)
    └── Interfaces
```

### 3.2 Primitive Types — Complete Reference

| Type | Size | Min Value | Max Value | Default | Wrapper | Literal Example |
| ------ | ------ | ----------- | ----------- | --------- | --------- | ----------------- |
| `byte` | 8 bits (1 byte) | -128 | 127 | 0 | `Byte` | `byte b = 100;` |
| `short` | 16 bits (2 bytes) | -32,768 | 32,767 | 0 | `Short` | `short s = 300;` |
| `int` | 32 bits (4 bytes) | -2,147,483,648 | 2,147,483,647 | 0 | `Integer` | `int i = 42;` |
| `long` | 64 bits (8 bytes) | -9.2 × 10¹⁸ | 9.2 × 10¹⁸ | 0L | `Long` | `long l = 9999L;` |
| `float` | 32 bits (4 bytes) | ~1.4E-45 | ~3.4E+38 | 0.0f | `Float` | `float f = 3.14f;` |
| `double` | 64 bits (8 bytes) | ~4.9E-324 | ~1.7E+308 | 0.0d | `Double` | `double d = 3.14;` |
| `char` | 16 bits (2 bytes) | '\u0000' (0) | '\uffff' (65535) | '\u0000' | `Character` | `char c = 'A';` |
| `boolean` | JVM-specific (conceptually 1 bit) | false | true | false | `Boolean` | `boolean ok = true;` |

> **Interview Trap:** `boolean` size is NOT 1 bit in practice. JVM spec says it's implementation-defined — in HotSpot JVM it uses 1 byte when stored as a field, but 4 bytes (int) in arrays and on the stack.

### 3.3 Why These Specific Sizes?

- Based on **Two's Complement** for integer representation
- `int` = 32 bits matches most hardware registers (historically)
- `long` = 64 bits for modern 64-bit CPUs
- `float` and `double` follow **IEEE 754** standard:
  - `float`: 1 sign bit + 8 exponent bits + 23 mantissa bits
  - `double`: 1 sign bit + 11 exponent bits + 52 mantissa bits

### 3.4 Type Casting

#### Widening (Implicit) — safe, no data loss

```
byte → short → int → long → float → double
                char → int
```

#### Narrowing (Explicit) — may lose data

```
double → float → long → int → short → char → byte
```

```java
// Widening — automatic
int i = 100;
long l = i;        // OK, no cast needed
double d = i;      // OK

// Narrowing — explicit cast required
double pi = 3.14159;
int truncated = (int) pi;   // truncated = 3, decimal part LOST
byte b = (byte) 200;        // b = -56 (overflow/wrap-around!)
```

> **Interview Trap:** `(byte) 200` does NOT throw an exception. It wraps around silently. 200 in binary = 11001000. As signed byte = -56.

### 3.5 Autoboxing and Unboxing (Java 5+)

**Autoboxing:** Automatic conversion from primitive → Wrapper object
**Unboxing:** Automatic conversion from Wrapper object → primitive

```java
// Autoboxing
Integer obj = 42;           // compiler converts: Integer.valueOf(42)
List<Integer> list = new ArrayList<>();
list.add(5);               // autoboxing: Integer.valueOf(5)

// Unboxing
int val = obj;              // compiler converts: obj.intValue()
int sum = obj + 10;         // unboxing happens here

// DANGER: NullPointerException on unboxing null
Integer x = null;
int y = x;                  // throws NullPointerException at runtime!
```

### 3.6 Integer Cache (Java 5+) — Critical Interview Topic

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b);    // true  — same cached object

Integer c = 128;
Integer d = 128;
System.out.println(c == d);    // false — different objects (beyond cache range)
System.out.println(c.equals(d)); // true
```

**Why?** `Integer.valueOf()` caches instances from -128 to 127 (configurable via `java.lang.Integer.IntegerCache.high`). This saves memory for the most commonly used integer values.

### 3.7 var Keyword (Java 10+ — Local Variable Type Inference)

```java
var name = "Krish";          // inferred as String
var list = new ArrayList<String>(); // inferred as ArrayList<String>
var age = 21;                // inferred as int

// NOT allowed:
var x;                       // ERROR — must have initializer
var nothing = null;          // ERROR — cannot infer from null
// var is NOT dynamic typing — type is fixed at compile time
```

### 3.8 Reference Types

```java
// All reference types:
String name = "Krish";           // points to String object in heap (or pool)
int[] arr = {1, 2, 3};          // arr points to array object in heap
Person p = new Person("Krish");  // p points to Person object in heap
```

**Key difference from primitives:**

- Variable stores a memory address (reference), not the actual value
- Default value is `null` (not 0 or false)
- Passed by value of the reference (the address), NOT by object reference

---

## 4. Internal Working

### 4.1 Memory Layout

```
JVM Memory
├── Stack (Thread-private)
│   ├── Primitive local variables stored HERE (actual value)
│   └── Reference variables stored HERE (memory address)
│
├── Heap (Shared)
│   ├── All objects live here (new Person(), new int[10])
│   └── String Pool (part of Heap since Java 7+)
│
└── Method Area / Metaspace
    ├── Class definitions
    └── Static variables
```

### 4.2 Stack vs Heap for Data Types

```java
void method() {
    int x = 42;           // x stored on stack (4 bytes, value = 42)
    String s = "hello";   // s on stack (reference), "hello" in String Pool (heap)
    Person p = new Person(); // p on stack (reference), Person object on heap
}
// when method() returns: stack frame popped, x and s reference destroyed
// heap objects survive until GC collects them
```

### 4.3 Bytecode Representation

```java
int i = 42;
```

Bytecode:

```
bipush 42      // push byte 42 onto operand stack
istore_1       // store int value into local variable slot 1
```

```java
long l = 9999L;
```

Bytecode:

```
ldc2_w #long 9999  // push long constant
lstore_1           // store long (takes 2 slots)
```

### 4.4 IEEE 754 Floating Point — Internal Representation

```
double 3.14 in memory (64 bits):
0 10000000000 1001000111101011100001010001111010111000010100011111

Sign: 0 (positive)
Exponent: 10000000000 = 1024, actual exponent = 1024 - 1023 = 1
Mantissa: represents 1.5700000000000000...

CONSEQUENCE: 0.1 + 0.2 ≠ 0.3 in floating point!
System.out.println(0.1 + 0.2);  // prints 0.30000000000000004
```

> **Production Rule:** Never use `float` or `double` for money calculations. Use `BigDecimal`.

---

## 5. Visual Flow

```
Source Code: int x = 42;
         |
         ↓
   Compiler (javac)
         |
         ↓
   Bytecode: bipush 42, istore_1
         |
         ↓
   JVM Class Loader
         |
         ↓
   JIT Compiler (HotSpot)
         |
         ↓
   Stack Frame Allocation
   [method frame]
   ┌─────────────────┐
   │ local var 0: x  │ → 42 (4 bytes, int value stored directly)
   └─────────────────┘
         |
         ↓
   CPU Registers / Memory
```

---

## 6. Syntax

```java
// Declaration
<type> <identifier>;

// Declaration with initialization
<type> <identifier> = <value>;

// Multiple declarations (same type)
int a = 1, b = 2, c = 3;

// Final (constant)
final int MAX_SIZE = 100;

// Casting syntax
(targetType) expression

// Numeric literals (Java 7+)
int million = 1_000_000;         // underscore for readability
int hex = 0xFF;                  // hexadecimal
int binary = 0b1010;             // binary prefix 0b
long bigNum = 9_999_999_999L;    // L suffix for long
float pi = 3.14f;                // f suffix for float
double d = 3.14d;                // d suffix (optional, default)
```

---

## 7. Examples

### Basic

```java
public class DataTypesBasic {
    public static void main(String[] args) {
        byte age = 25;           // small numbers
        short year = 2024;       // medium range
        int salary = 100000;     // standard integer
        long population = 8_000_000_000L; // large number, L required!
        float gpa = 9.5f;        // f required for float
        double pi = 3.141592653589793; // default decimal is double
        char grade = 'A';        // single quotes for char
        boolean isPass = true;   // only true or false
        
        System.out.println("Age: " + age);       // 25
        System.out.println("Year: " + year);     // 2024
        System.out.println("Salary: " + salary); // 100000
        System.out.println("Pi: " + pi);         // 3.141592653589793
    }
}
```

### Intermediate — Overflow and Casting

```java
public class TypeCasting {
    public static void main(String[] args) {
        // Overflow — wraps around silently!
        int maxInt = Integer.MAX_VALUE;   // 2147483647
        int overflow = maxInt + 1;        // -2147483648 (wraps to MIN_VALUE)
        System.out.println(overflow);     // -2147483648 — no exception!
        
        // Narrowing cast — data loss
        double d = 9.99;
        int i = (int) d;                  // i = 9, .99 truncated
        System.out.println(i);            // 9
        
        // Char and int — char IS a numeric type
        char c = 'A';
        int ascii = c;                    // widening: 65
        char next = (char)(c + 1);       // 'B'
        System.out.println(ascii);        // 65
        System.out.println(next);         // B
        
        // Autoboxing trap
        Integer x = null;
        try {
            int val = x;                  // NullPointerException!
        } catch (NullPointerException e) {
            System.out.println("NPE on unboxing null!");
        }
    }
}
```

### Advanced — Integer Cache, BigDecimal

```java
import java.math.BigDecimal;

public class AdvancedTypes {
    public static void main(String[] args) {
        // Integer cache trap
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        System.out.println(a == b);         // true  (cached)
        System.out.println(c == d);         // false (not cached)
        System.out.println(c.equals(d));    // true  (always use equals!)
        
        // Floating point precision issue
        double d1 = 0.1 + 0.2;
        System.out.println(d1);             // 0.30000000000000004
        System.out.println(d1 == 0.3);      // false!
        
        // Fix with BigDecimal for money
        BigDecimal bd1 = new BigDecimal("0.1");  // use String constructor!
        BigDecimal bd2 = new BigDecimal("0.2");
        BigDecimal sum = bd1.add(bd2);
        System.out.println(sum);             // 0.3 — exact!
        System.out.println(sum.compareTo(new BigDecimal("0.3")) == 0); // true
    }
}
```

### Production — Type Safety in APIs

```java
// Bad practice — using wrong types
public class OrderService {
    private double amount;        // WRONG for money
    private int orderId;          // WRONG — can overflow for large systems
    private String status;        // FRAGILE — magic strings
    
    // Better practice
    private BigDecimal amount2;   // CORRECT for money
    private long orderId2;        // CORRECT — 64-bit for large IDs  
    private OrderStatus status2;  // CORRECT — enum for status
}

enum OrderStatus { PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED }
```

---

## 8. Real World Usage

| Company/Framework | Data Type Usage |
| ------------------ | ----------------- |
| **Amazon** | `long` for order IDs (billions of orders), `BigDecimal` for pricing, `byte[]` for S3 object data |
| **Google** | `long` for timestamps (nanosecond precision), Protocol Buffers uses Java primitives |
| **Netflix** | `boolean` feature flags in Zuul gateway, `int` for HTTP status codes |
| **Spring Boot** | `@Value` injects config as `int`, `boolean`, `String`; `@Column` maps to SQL types |
| **Hibernate/JPA** | Maps Java types to SQL: `int` → INTEGER, `String` → VARCHAR, `boolean` → BIT/BOOLEAN |
| **Kafka** | `byte[]` for message keys and values — everything serialized to bytes |
| **Redis** | All Java types serialized to byte[] for storage |

---

## 9. Internal JVM Perspective

### Memory Allocation for Primitives

```
Stack Frame (one per method call):
┌──────────────────────────────┐
│ Local Variable Table         │
│ Slot 0: int x = 42          │ ← 4 bytes, value stored directly
│ Slot 1: long y = 100L       │ ← 8 bytes (occupies 2 slots!)
│ Slot 2: double z = 3.14     │ ← 8 bytes (occupies 2 slots!)
│ Slot 3: boolean b = true    │ ← 4 bytes (JVM uses int for booleans)
└──────────────────────────────┘
```

### GC Impact

- Primitives on stack: **No GC involvement** — freed when method returns
- Wrapper objects on heap: **Subject to GC** — more pressure on GC
- This is why primitives are preferred in hot paths / tight loops

### Metaspace

- Class metadata for wrapper types (Integer, Long, etc.) stored in Metaspace
- Static constants (`Integer.MAX_VALUE`, etc.) also in Metaspace

---

## 10. Time and Space Complexity

| Type | Read | Write | Space |
| ------ | ------ | ------- | ------- |
| Primitive | O(1) | O(1) | Fixed (1–8 bytes) |
| Reference (object access) | O(1) | O(1) | Pointer + object overhead (12–16 bytes minimum per object) |
| Autoboxing/Unboxing | O(1) amortized | O(1) amortized | Cache hit vs new object |
| BigDecimal operations | O(n) where n = precision digits | O(n) | O(n) |

---

## 11. Advantages of Java's Type System

- **Compile-time safety** — type errors caught before runtime
- **Predictable memory** — primitive sizes are fixed across platforms
- **Performance** — primitives avoid object overhead
- **Interoperability** — types map predictably to SQL, JSON, binary formats
- **Optimization** — JIT can aggressively optimize operations on primitives
- **Tooling** — IDEs provide type-aware auto-complete, refactoring

---

## 12. Disadvantages

- **Verbosity** — `long orderId` vs Kotlin's `val orderId: Long`
- **Primitive/wrapper duality** — two worlds (`int` vs `Integer`) causes confusion and NPE bugs
- **No unsigned types** — Java has no `uint`, `ulong` (unlike C/C++/Kotlin); must use `>>>` for unsigned right shift and manual masking
- **float/double imprecision** — IEEE 754 causes subtle money-calculation bugs in production
- **int overflow silently** — no ArithmeticException for overflow (only for `/ 0`)
- **var limitations** (Java 10) — only for local variables, not fields or method parameters

---

## 13. Tradeoffs

| Decision | Use | Don't Use |
| ---------- | ----- | ----------- |
| `int` vs `long` | Default for integers | Use `long` when values can exceed 2.1 billion |
| `float` vs `double` | Almost never prefer float | Use `double` (more precision, same speed on 64-bit CPUs) |
| `double` vs `BigDecimal` | Scientific, non-exact | Use `BigDecimal` for money, banking, taxes |
| `int[]` vs `Integer[]` | Performance-critical, primitives | Collections require `Integer[]` |
| `var` | Code is clear, local scope | Avoid when type is not obvious from initializer |

---

## 14. Comparison

### int vs Integer

| Aspect | `int` (primitive) | `Integer` (wrapper) |
| -------- | ------------------ | --------------------- |
| Storage | Stack (4 bytes) | Heap object (~16 bytes) |
| Default value | 0 | null |
| Null possible | No | Yes |
| Used in Collections | No (directly) | Yes |
| Autoboxing needed | No | Yes |
| Performance | Faster | Slower (object overhead, GC) |
| Methods available | None | `parseInt()`, `valueOf()`, `compareTo()`, etc. |

### float vs double

| Aspect | `float` | `double` |
| -------- | --------- | ---------- |
| Size | 32 bits | 64 bits |
| Precision (decimal digits) | ~7 | ~15-16 |
| Literal suffix | `f` required | Optional (`d`) |
| Default for decimal | No | Yes |
| Use case | Memory-constrained, graphics | Scientific computing, general |

### var vs explicit type (Java 10+)

| Aspect | `var` | Explicit Type |
| -------- | ------- | --------------- |
| Verbosity | Less | More |
| Readability | Depends on initializer | Always clear |
| Scope | Local variables only | Fields, params, returns |
| Type safety | Same (compile-time inferred) | Same |

---

## 15. Common Mistakes

### Beginner

```java
// Mistake 1: Missing L suffix for long
long pop = 8000000000;    // COMPILE ERROR — exceeds int range, needs L
long pop = 8000000000L;   // CORRECT

// Mistake 2: Missing f suffix for float
float pi = 3.14;          // COMPILE ERROR — 3.14 is double by default, can't auto-narrow
float pi = 3.14f;         // CORRECT

// Mistake 3: Integer division
int a = 5, b = 2;
double result = a / b;    // result = 2.0, NOT 2.5! Integer division happens first
double result2 = (double) a / b; // result2 = 2.5 CORRECT
```

### Intermediate

```java
// Mistake 4: == for Integer comparison
Integer x = 200, y = 200;
if (x == y) { }           // FALSE — different objects (outside cache)
if (x.equals(y)) { }      // TRUE — correct value comparison

// Mistake 5: float/double for money
double price = 19.99 + 5.01;
System.out.println(price); // NOT 25.0, might be 25.000000000000004!
```

### Experienced

```java
// Mistake 6: int overflow in calculations
int a = 2_000_000;
int b = 2_000_000;
int product = a * b;       // OVERFLOW — result exceeds int range, no exception
long product = (long) a * b; // CORRECT — cast BEFORE multiplication

// Mistake 7: Unboxing null
Integer count = getCountFromDB(); // might return null
int total = count + 1;    // NPE if count is null!
int total = (count == null ? 0 : count) + 1; // Safe
```

---

## 16. Best Practices

1. **Prefer primitives over wrappers** in performance-critical code (avoid autoboxing overhead)
2. **Use `long` by default** for IDs, timestamps — future-proof against overflow
3. **Never use `float`/`double` for monetary values** — use `BigDecimal` with `String` constructor
4. **Always use `.equals()` for wrapper comparison**, never `==`
5. **Use `final`** for constants: `static final int MAX_CONNECTIONS = 100;`
6. **Use numeric literals with underscores** (Java 7+): `1_000_000` not `1000000`
7. **Declare variables with the minimum required scope** — prefer local over field when possible
8. **Use `Math.addExact()`, `Math.multiplyExact()`** (Java 8+) for overflow-safe arithmetic
9. **Avoid raw `char` arithmetic** — use `Character.isDigit()`, `Character.toUpperCase()` instead

---

## 17. Interview Section

### Easy

1. What are the 8 primitive types in Java and their sizes?
2. What is the default value of `int`, `boolean`, `String`, `Integer`?
3. What is the difference between `float` and `double`?
4. What is autoboxing and unboxing?
5. Why can't you use `==` to compare two `Integer` objects reliably?

### Medium

1. What is the Integer cache? What is its range? How can you change the upper bound?
2. Why does `0.1 + 0.2 != 0.3` in Java? How do you fix it?
3. What is the difference between `int` and `Integer` in terms of memory?
4. What happens when you do `(byte) 200`? Explain the result.
5. What is `var` in Java 10? Is it dynamic typing?

### Hard

1. Explain Two's Complement representation and why `Integer.MAX_VALUE + 1 = Integer.MIN_VALUE`.
2. Explain IEEE 754 floating point format and why `float` has ~7 decimal digits of precision.
3. How does the JVM represent `boolean` in arrays vs. as fields?
4. Why does `long` take 2 slots in the local variable table?
5. Explain the behavior: `new Integer(127) == new Integer(127)` → what is the result?

### Very Hard / Trick Questions

1. `Integer.MAX_VALUE + 1` vs `(long) Integer.MAX_VALUE + 1` — explain both results.
2. What is the value of `(byte) -1`? What about `(byte) 256`? Explain.
3. How does `Math.addExact(Integer.MAX_VALUE, 1)` differ from `Integer.MAX_VALUE + 1`?
4. Why does autoboxing of `Long` not cache the same range as `Integer` by default?

### Expected Key Answers

- `new Integer(127) == new Integer(127)` → **false** — `new` always creates a new object, bypasses cache
- `Integer.valueOf(127) == Integer.valueOf(127)` → **true** — uses cache
- `(byte) 256` → **0** — 256 = 0x100, byte takes last 8 bits = 0x00 = 0
- `(byte) -1` → **-1** — -1 in two's complement is all 1s (0xFF), as byte = -1

---

## 18. Coding Questions

### Easy

1. Write a method that checks if an `int` is within `byte` range without casting.
2. Write a method that safely adds two `int` values without overflow (throw exception if overflow).
3. Convert a `char` to uppercase without using `Character.toUpperCase()`.
4. Write a method that counts the number of 1-bits in an `int` (bit counting).
5. Given a `double`, round it to 2 decimal places using `BigDecimal`.

### Medium

1. Implement an `IntRange` class with `min`, `max`, `contains(int val)` — use appropriate types.
2. Write a currency formatter that takes a `BigDecimal` amount and returns "₹1,23,456.78" format.
3. Find all `int` values that when cast to `byte` produce the same value.
4. Implement type-safe temperature conversion: Celsius ↔ Fahrenheit ↔ Kelvin using `double`.
5. Write a method that detects autoboxing-related performance issues in a loop (micro-benchmark).

### Hard

1. Implement a `LongCounter` that detects and reports overflow on every increment.
2. Given a `float[]` of stock prices, convert accurately to `BigDecimal[]` — handle precision loss.
3. Implement a custom `UnsignedInt` class that wraps `int` but treats it as unsigned (0 to 2^32-1).
4. Write a bit manipulation suite: `setBit`, `clearBit`, `toggleBit`, `checkBit` using `int`.
5. Implement efficient `byte[]` to `int` and `int` to `byte[]` conversion (big-endian and little-endian).

### Company Level

1. **Google:** Implement a `BigInteger` addition for arbitrarily large numbers represented as `byte[]`.
2. **Amazon:** Design a `MoneyAmount` class backed by `BigDecimal` that supports +, -, *, / with currency codes.
3. **Uber:** Implement a thread-safe counter using primitive `long` vs `AtomicLong` — explain the difference.
4. **Netflix:** Build a feature flag system where flags can be `boolean`, `int`, `String`, or `double`.
5. **HFT/Trading:** Implement a fixed-point arithmetic class using `long` to represent decimal values with 6 decimal places.

---

## 19. Production Scenarios

### Scenario 1: Silent Overflow in Production

```
Problem: An e-commerce system calculated total order value using int.
After 2 billion rupees in a day, the total went negative.
Cause: int overflow — silently wraps to negative.
Fix: Use long for monetary aggregations, or BigDecimal.
```

### Scenario 2: Float Precision in Banking

```
Problem: Bank calculated interest using double. After millions of transactions,
1 paisa discrepancies accumulated to thousands of rupees.
Cause: IEEE 754 floating point precision error.
Fix: ALWAYS use BigDecimal with ROUND_HALF_EVEN (banker's rounding) for money.
```

### Scenario 3: NPE from Unboxing in Stream

```java
// Production bug
Map<String, Integer> counts = getFromCache(); // may return null values
int total = counts.values().stream()
                  .mapToInt(Integer::intValue)  // NPE if any value is null!
                  .sum();

// Fix
int total = counts.values().stream()
                  .filter(Objects::nonNull)
                  .mapToInt(Integer::intValue)
                  .sum();
```

### Scenario 4: Autoboxing Performance in Hot Path

```java
// Bad — millions of autoboxing operations in a tight loop
List<Integer> results = new ArrayList<>();
for (int i = 0; i < 10_000_000; i++) {
    results.add(i);   // autoboxing 10M times — massive GC pressure!
}

// Better — use primitive array or specialized library
int[] results = new int[10_000_000];
// Or use Eclipse Collections / Trove for primitive collections
```

---

## 20. Internal Deep Dive

### Integer.valueOf() Source (OpenJDK)

```java
// From OpenJDK source — Integer.java
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high) // -128 to 127
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}

// IntegerCache is a private static inner class
// cache is an Integer[] pre-populated at class loading time
// High bound can be configured via JVM flag:
// -XX:AutoBoxCacheMax=<size>
```

### Math.addExact() (Java 8+)

```java
// OpenJDK source
public static int addExact(int x, int y) {
    int r = x + y;
    // HD 2-12 Overflow iff both arguments have the opposite sign of the result
    if (((x ^ r) & (y ^ r)) < 0) {
        throw new ArithmeticException("integer overflow");
    }
    return r;
}
// Uses XOR trick to detect overflow without branching on individual signs
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| `int` default is 0 vs `Integer` default is null | Only **fields** have defaults. Local variables MUST be initialized before use — no default. |
| `float` is more precise than `double` | Wrong — `double` has MORE precision (64-bit vs 32-bit). |
| `char` is the same as `String` | `char` is a primitive, 16-bit Unicode unit. `String` is an object, a sequence of chars. |
| `var` is dynamic typing | Wrong — `var` is static type inference. Type is fixed at compile time. |
| `byte` can hold 0-255 | Wrong — `byte` is SIGNED: -128 to 127. Use `& 0xFF` to treat as unsigned 0-255. |
| `boolean` is 1 bit | JVM spec doesn't mandate 1 bit — HotSpot uses at least 1 byte for fields, 4 bytes on stack. |

---

## 22. Cheat Sheet

```
PRIMITIVE TYPES QUICK REFERENCE:
byte   → 8-bit signed  → -128 to 127           → Byte
short  → 16-bit signed → -32768 to 32767        → Short
int    → 32-bit signed → ~-2B to 2B             → Integer
long   → 64-bit signed → ~-9.2E18 to 9.2E18    → Long (add L suffix)
float  → 32-bit IEEE754→ ~7 decimal digits      → Float (add f suffix)
double → 64-bit IEEE754→ ~15-16 decimal digits  → Double (default decimal)
char   → 16-bit Unicode→ '\u0000' to '\uffff'   → Character (single quotes)
boolean→ true/false   → JVM-specific size       → Boolean

DEFAULT VALUES (class fields only):
int/short/byte/long → 0
float/double → 0.0
char → '\u0000'
boolean → false
Reference types → null

WIDENING ORDER: byte→short→int→long→float→double
                char→int

CRITICAL RULES:
• Money → BigDecimal (String constructor)
• IDs → long
• Comparisons → .equals() not ==
• Integer cache → -128 to 127
• Long literal → L suffix
• Float literal → f suffix
```

---

## 23. Mind Map

```
DATA TYPES
│
├── PRIMITIVE (8)
│   ├── Integer Family
│   │   ├── byte (8-bit, -128 to 127)
│   │   ├── short (16-bit)
│   │   ├── int (32-bit) ← DEFAULT INTEGER
│   │   └── long (64-bit) ← for IDs/timestamps
│   │
│   ├── Float Family
│   │   ├── float (32-bit, ~7 digits) ← rarely used
│   │   └── double (64-bit, ~15 digits) ← DEFAULT DECIMAL
│   │
│   ├── char (16-bit Unicode)
│   └── boolean (true/false)
│
├── REFERENCE
│   ├── String (special — String Pool)
│   ├── Arrays
│   ├── Classes
│   └── Interfaces
│
├── WRAPPER CLASSES
│   ├── Integer Cache (-128 to 127)
│   ├── Autoboxing (primitive → wrapper)
│   └── Unboxing (wrapper → primitive, NPE risk!)
│
└── SPECIAL TYPES
    ├── BigDecimal (exact decimal math)
    ├── var (Java 10, type inference)
    └── Numeric Literals (underscores, hex, binary)
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| Primitive type | Stores actual value directly on stack; 8 types in Java |
| Reference type | Stores memory address pointing to heap object |
| Widening | Automatic conversion to a larger/more capable type |
| Narrowing | Manual cast to smaller type; may lose data silently |
| Autoboxing | Auto-conversion from primitive to wrapper (Java 5+) |
| Unboxing | Auto-conversion from wrapper to primitive; NPE risk if null |
| Integer cache | JVM caches Integer -128 to 127; use .equals() for comparison |
| IEEE 754 | Floating point standard causing 0.1+0.2≠0.3 |
| BigDecimal | Arbitrary precision decimal; use for money |
| var | Local type inference (Java 10+); still statically typed |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"Bytes Short, Ints Long, Floats Double"** | Size progression of types |
| **"1-2-4-8-4-8-2-1"** | Byte sizes: byte(1), short(2), int(4), long(8), float(4), double(8), char(2), boolean(1) |
| **"127 is the magic number"** | Integer cache boundary — use == safely only within -128 to 127 |
| **"L for Long, f for float"** | Suffixes needed for literals |
| **"BIG money needs DECIMAL precision"** | Use BigDecimal for financial calculations |
| **"null + unbox = NPE explosion"** | Always null-check before unboxing |

---

## 26. Important Keywords

| Keyword/Term | Explanation |
| ------------- | ------------- |
| `byte` | 8-bit signed integer primitive |
| `int` | 32-bit signed integer, most common |
| `long` | 64-bit signed integer, for large numbers |
| `double` | 64-bit IEEE 754 floating point |
| `boolean` | Logical type, only true/false |
| `char` | 16-bit Unicode character |
| `var` | Java 10 local variable type inference |
| `final` | Makes variable a constant (cannot reassign) |
| Widening | Implicit safe conversion to larger type |
| Narrowing | Explicit cast to smaller type |
| Autoboxing | Automatic primitive → wrapper conversion |
| Unboxing | Automatic wrapper → primitive conversion |
| Integer cache | JVM optimization caching Integer -128 to 127 |
| IEEE 754 | International floating point standard |
| Two's Complement | How Java represents negative integers in binary |
| BigDecimal | Exact arbitrary-precision decimal arithmetic class |

---

## 27. Interview One-Liners

- "`int` is 32-bit signed; `long` is 64-bit signed; both use Two's Complement."
- "Java has no unsigned integer types — use `& 0xFF` to treat byte as unsigned."
- "Autoboxing calls `Integer.valueOf()` which uses a cache for -128 to 127."
- "Always use `.equals()` for wrapper comparison, never `==`."
- "Use `BigDecimal` for money — `double` has IEEE 754 precision errors."
- "`float` needs `f` suffix; `long` needs `L` suffix in literals."
- "`var` is compile-time type inference, NOT dynamic typing."
- "Integer overflow wraps silently — use `Math.addExact()` for safe addition."
- "Default value for primitives is 0/false; for references is null (only for fields, not local variables)."
- "Unboxing a null wrapper throws NullPointerException."

---

## 28. Summary

Java's type system is the foundation of everything. **8 primitive types** (byte, short, int, long, float, double, char, boolean) are stored on the stack as actual values. **Reference types** (String, arrays, objects) store a memory address pointing to the heap. The type system ensures compile-time safety, predictable memory allocation, and JVM-level optimizations. Key pitfalls: integer overflow is silent, floating-point math is imprecise (use BigDecimal for money), and unboxing null wrappers causes NPE. The Integer cache (-128 to 127) is a hidden interview trap — always use `.equals()` for object comparison. Java 10 introduced `var` for local type inference without sacrificing type safety.

---

## 29. Further Learning

| Topic | Why Study Next |
| ------- | --------------- |
| Wrapper Classes (in depth) | Deep dive into Integer, Long, Double — their API, parseXxx, valueOf, compareTo |
| String interning | String pool is the reference-type equivalent of Integer cache |
| BigDecimal & BigInteger | Production-grade arbitrary precision math |
| BitWise Operators | Build on primitive `int`/`long` bit representation |
| Arrays | Reference types built on primitives — memory layout, multidimensional |
| Collections Framework | Requires understanding of wrapper types and autoboxing |
| Generics | Type system evolution — type safety without primitives in generics |

---

---

---

# TOPIC 2: OPERATORS

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | Operators are special symbols that perform operations on operands (variables, literals, expressions) and produce a result. |
| **Why introduced?** | To enable computation, comparison, logical decision-making, bit manipulation, and assignment — the building blocks of any algorithm. |
| **Problem solved** | Without operators, you cannot write any computational logic — no arithmetic, no comparisons, no boolean logic. |
| **History** | Java 1.0 (1996) — inherited operator set from C/C++ with deliberate exclusions (no `++` on pointers, no operator overloading). Java 14+ added pattern matching in `instanceof`. |
| **Java version changes** | Java 14: `instanceof` pattern matching preview → Java 16: finalized. Java 21: pattern matching in `switch`. |
| **Industry importance** | Every algorithm, every business rule, every condition in production code uses operators. Understanding operator precedence prevents subtle bugs. |

---

## 2. Intuition

Think of operators as **verbs** in a sentence. They describe actions:

- **Arithmetic operators** are like a calculator: `+`, `-`, `*`, `/`
- **Relational operators** are like a judge: Is A greater than B? Yes/No
- **Logical operators** are like a bouncer: You must be 18+ AND have ID
- **Bitwise operators** are like a light panel: toggle individual bits (switches)
- **Assignment operator** is like a label gun: attach value to variable
- **Ternary operator** is like a quick if/else: "Rain? Umbrella : Sunglasses"

---

## 3. Core Concepts

### 3.1 Complete Operator Classification

```
Java Operators
├── 1. Arithmetic:    + - * / % ++ --
├── 2. Relational:    == != > < >= <=
├── 3. Logical:       && || !
├── 4. Bitwise:       & | ^ ~ << >> >>>
├── 5. Assignment:    = += -= *= /= %= &= |= ^= <<= >>= >>>=
├── 6. Unary:         + - ++ -- ~ !
├── 7. Conditional:   ?: (ternary)
└── 8. Special:       instanceof, . (member access), [] (array), (cast)
```

### 3.2 Operator Precedence Table (Highest to Lowest)

| Priority | Category | Operators | Associativity |
| ---------- | ---------- | ----------- | --------------- |
| 1 | Postfix | `expr++` `expr--` | Left to Right |
| 2 | Unary | `++expr` `--expr` `+expr` `-expr` `~` `!` | Right to Left |
| 3 | Multiplicative | `*` `/` `%` | Left to Right |
| 4 | Additive | `+` `-` | Left to Right |
| 5 | Shift | `<<` `>>` `>>>` | Left to Right |
| 6 | Relational | `<` `>` `<=` `>=` `instanceof` | Left to Right |
| 7 | Equality | `==` `!=` | Left to Right |
| 8 | Bitwise AND | `&` | Left to Right |
| 9 | Bitwise XOR | `^` | Left to Right |
| 10 | Bitwise OR | `\|` | Left to Right |
| 11 | Logical AND | `&&` | Left to Right |
| 12 | Logical OR | `\|\|` | Left to Right |
| 13 | Ternary | `?:` | Right to Left |
| 14 | Assignment | `=` `+=` `-=` etc. | Right to Left |

> **Memory trick:** "PLMRSE-BXOL-TAS" — Postfix, (pre)Unary, Mult, Rel, Shift, Eq, Bitwise(AND/XOR/OR), Logical(&&/||), Ternary, Assignment

---

## 4. Internal Working

### 4.1 Arithmetic Operators

```java
// Integer arithmetic
int a = 10, b = 3;
int div = a / b;     // 3 (integer division — truncates)
int mod = a % b;     // 1 (remainder)
int mul = a * b;     // 30
int add = a + b;     // 13
int sub = a - b;     // 7

// Floating point arithmetic
double x = 10.0, y = 3.0;
double fdiv = x / y;  // 3.3333333333333335 (IEEE 754)

// Division by zero behaviour
int bad = 10 / 0;          // throws ArithmeticException: / by zero
double inf = 10.0 / 0.0;   // Double.POSITIVE_INFINITY (no exception!)
double nan = 0.0 / 0.0;    // Double.NaN (Not a Number)
System.out.println(Double.isNaN(nan));    // true
System.out.println(Double.isInfinite(inf)); // true
```

### 4.2 Increment / Decrement — Internal Mechanism

```java
int x = 5;

// Post-increment: return THEN increment
int a = x++;   // a = 5, then x becomes 6
// Equivalent bytecode:
// iload x      → push 5
// iinc x 1     → x = 6
// istore a     → a = 5

// Pre-increment: increment THEN return
int b = ++x;   // x becomes 7, then b = 7
// Equivalent bytecode:
// iinc x 1     → x = 7
// iload x      → push 7
// istore b     → b = 7
```

### 4.3 Short-Circuit Evaluation — Critical Concept

```java
// && (AND) — stops if LEFT is false (no need to check right)
boolean result = (x != 0) && (100 / x > 5); // Safe: avoids /0 if x==0

// || (OR) — stops if LEFT is true (no need to check right)
boolean found = (cache.contains(key)) || (db.search(key) != null);
// If cache hits, db is never queried — HUGE performance optimization!

// NON-short-circuit versions (evaluate BOTH sides always)
boolean r1 = condition1 & condition2;   // always evaluates both
boolean r2 = condition1 | condition2;   // always evaluates both
// Use case: when right-side has necessary side effects
```

### 4.4 Bitwise Operators — Internal Mechanism

```java
int a = 5;  // binary: 0000...00000101
int b = 3;  // binary: 0000...00000011

// AND: both bits must be 1
a & b  → 0000...00000001  = 1

// OR: at least one bit is 1
a | b  → 0000...00000111  = 7

// XOR: exactly one bit is 1 (toggle)
a ^ b  → 0000...00000110  = 6

// NOT (bitwise complement): flip all bits
~a     → 1111...11111010  = -6  (Two's complement: -(a+1))

// Left shift: multiply by 2^n (fast!)
a << 1 → 0000...00001010  = 10  (5 × 2)
a << 2 → 0000...00010100  = 20  (5 × 4)

// Signed right shift: divide by 2^n (preserves sign bit)
-8 >> 1  → -4   (arithmetic shift, copies sign bit)
 8 >> 1  →  4

// Unsigned right shift: always fills with 0
-1 >>> 1  → Integer.MAX_VALUE (2147483647)
// -1 in binary = 1111...1111
// >>> fills MSB with 0 = 0111...1111 = Integer.MAX_VALUE
```

### 4.5 instanceof Operator

```java
// Classic usage
Object obj = "hello";
if (obj instanceof String) {
    String s = (String) obj;    // explicit cast needed
    System.out.println(s.length());
}

// Pattern matching (Java 16+)
if (obj instanceof String s) {  // declares AND casts in one step
    System.out.println(s.length()); // s is String in this scope
}

// Pattern matching in switch (Java 21+)
switch (obj) {
    case String s  -> System.out.println("String: " + s.length());
    case Integer i -> System.out.println("Int: " + i);
    case null      -> System.out.println("null!");
    default        -> System.out.println("Other: " + obj);
}

// null-safe: instanceof always returns false for null
String str = null;
System.out.println(str instanceof String); // false (no NPE)
```

### 4.6 String Concatenation with `+`

```java
// String + anything = String concatenation
String s = "Hello" + " " + "World";  // "Hello World"
String n = "Value: " + 42;           // "Value: 42"
String mix = 1 + 2 + " hello";       // "3 hello" (1+2=3 first, then concat)
String mix2 = "hello" + 1 + 2;       // "hello12" (left-to-right, both concatenated)

// Internal: Java 9+ uses invokedynamic with StringConcatFactory
// (replaces StringBuilder-based compilation from Java 8-)
// This is optimized at JVM level

// Performance trap in loops:
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i;   // Creates 1000 new String objects! O(n²) 
}

// Fix: use StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i);  // O(n) amortized
}
String result2 = sb.toString();
```

### 4.7 Ternary Operator

```java
// Syntax: condition ? valueIfTrue : valueIfFalse
int max = (a > b) ? a : b;

// Can be nested (avoid — readability suffers)
String grade = (score >= 90) ? "A" : (score >= 80) ? "B" : "C";

// Type rules — both branches must be compatible
int x = 1;
// int result = condition ? 1 : 1.0;  // COMPILE ERROR — int vs double
double d = true ? 1 : 1.0;           // OK — int widened to double = 1.0
```

---

## 5. Visual Flow

```
EXPRESSION EVALUATION: a + b * c - d / 2

Step 1: Apply precedence rules
   a + (b * c) - (d / 2)    ← * and / before + and -

Step 2: Left-to-right for same precedence
   (a + (b * c)) - (d / 2)

Step 3: Bytecode execution (operand stack)
   push a
   push b
   push c
   imul           ← b * c
   iadd           ← a + (b*c)
   push d
   push 2
   idiv           ← d / 2
   isub           ← (a+b*c) - (d/2)

SHORT-CIRCUIT FLOW for (A && B):
   Evaluate A
   ├── A is false → STOP, return false (B never evaluated)
   └── A is true  → Evaluate B, return B
```

---

## 6. Syntax

```java
// Arithmetic
int r = a + b;    // addition
int r = a - b;    // subtraction
int r = a * b;    // multiplication
int r = a / b;    // division (integer truncation for int/int)
int r = a % b;    // modulo (remainder)
a++;              // post-increment
++a;              // pre-increment
a--;              // post-decrement
--a;              // pre-decrement

// Relational
boolean r = a == b;   // equal to
boolean r = a != b;   // not equal to
boolean r = a > b;    // greater than
boolean r = a < b;    // less than
boolean r = a >= b;   // greater than or equal
boolean r = a <= b;   // less than or equal

// Logical
boolean r = a && b;   // logical AND (short-circuit)
boolean r = a || b;   // logical OR  (short-circuit)
boolean r = !a;       // logical NOT

// Bitwise
int r = a & b;    // bitwise AND
int r = a | b;    // bitwise OR
int r = a ^ b;    // bitwise XOR
int r = ~a;       // bitwise NOT (ones complement)
int r = a << n;   // left shift by n bits (× 2^n)
int r = a >> n;   // signed right shift (÷ 2^n, preserves sign)
int r = a >>> n;  // unsigned right shift (fills 0)

// Assignment
a = b;     // assign
a += b;    // a = a + b
a -= b;    // a = a - b
a *= b;    // a = a * b
a /= b;    // a = a / b
a %= b;    // a = a % b
a &= b;    // a = a & b
a |= b;    // a = a | b
a ^= b;    // a = a ^ b
a <<= n;   // a = a << n
a >>= n;   // a = a >> n
a >>>= n;  // a = a >>> n

// Ternary
result = condition ? trueValue : falseValue;

// instanceof
boolean b = obj instanceof ClassName;
if (obj instanceof ClassName varName) { /* varName auto-cast */ } // Java 16+
```

---

## 7. Examples

### Basic

```java
public class OperatorsBasic {
    public static void main(String[] args) {
        int a = 15, b = 4;
        
        System.out.println(a + b);   // 19
        System.out.println(a - b);   // 11
        System.out.println(a * b);   // 60
        System.out.println(a / b);   // 3   ← integer division!
        System.out.println(a % b);   // 3   ← 15 = 4*3 + 3
        
        System.out.println(a > b);   // true
        System.out.println(a == b);  // false
        
        boolean x = true, y = false;
        System.out.println(x && y);  // false
        System.out.println(x || y);  // true
        System.out.println(!x);      // false
        
        // Ternary
        String result = (a > b) ? "a is bigger" : "b is bigger";
        System.out.println(result);  // a is bigger
    }
}
```

### Intermediate — Bit Manipulation

```java
public class BitOps {
    public static void main(String[] args) {
        int flags = 0;              // 00000000
        
        // Set bit 2 (third bit from right)
        flags = flags | (1 << 2);  // 00000100 = 4
        
        // Check bit 2
        boolean isSet = (flags & (1 << 2)) != 0; // true
        
        // Clear bit 2
        flags = flags & ~(1 << 2); // 00000000 = 0
        
        // Toggle bit 1
        flags = flags ^ (1 << 1);  // 00000010 = 2
        
        // Fast multiply/divide by power of 2
        int n = 100;
        int doubled = n << 1;    // 200 (faster than n * 2)
        int halved  = n >> 1;    // 50  (faster than n / 2)
        
        // Check if n is even/odd using bitmask
        System.out.println((n & 1) == 0 ? "even" : "odd"); // even
        
        // Swap without temp (XOR trick)
        int x = 5, y = 9;
        x = x ^ y;   // x = 5 XOR 9 = 12
        y = x ^ y;   // y = 12 XOR 9 = 5
        x = x ^ y;   // x = 12 XOR 5 = 9
        System.out.println(x + ", " + y);  // 9, 5
    }
}
```

### Advanced — Operator Precedence Traps

```java
public class PrecedenceTraps {
    public static void main(String[] args) {
        // Trap 1: + vs string concatenation order
        System.out.println(1 + 2 + " items");   // "3 items"  (1+2 first)
        System.out.println("items: " + 1 + 2);  // "items: 12" (concat left-to-right)
        System.out.println("items: " + (1 + 2)); // "items: 3" (parentheses)
        
        // Trap 2: Post vs pre increment in expression
        int a = 5;
        int b = a++ + ++a;  // a++ = 5 (a→6), ++a = 7 (a→7), b = 5+7 = 12
        System.out.println(a + " " + b);  // 7 12
        
        // Trap 3: Ternary precedence
        int x = 1, y = 2, z = 3;
        int r = x > y ? x : y > z ? y : z; // right-to-left: x>(y>z?y:z)?x:(y>z?y:z)
        System.out.println(r);  // 3
        
        // Trap 4: Bitwise vs logical precedence
        // & has higher precedence than &&
        // | has higher precedence than ||
        boolean t = true, f = false;
        boolean r1 = f & t || t;   // (f & t) || t = false || true = true
        boolean r2 = f && t | t;   // f && (t | t) = f && true = false
        // Use parentheses to be explicit!
        
        // Trap 5: Compound assignment and type
        byte by = 10;
        by += 5;     // OK — compound assignment includes implicit cast
        // by = by + 5; // COMPILE ERROR — by+5 is int, can't assign to byte without cast
    }
}
```

### Production — Defensive Coding with Operators

```java
// Production pattern: safe navigation with operators
public class SafeOps {
    // Pattern 1: Null-safe check with short-circuit
    public static String getUserName(User user) {
        return (user != null && user.getProfile() != null)
            ? user.getProfile().getName()
            : "Anonymous";
    }
    
    // Pattern 2: Bitwise flags for permission system
    static final int READ    = 1 << 0;  // 001
    static final int WRITE   = 1 << 1;  // 010
    static final int EXECUTE = 1 << 2;  // 100
    
    static boolean hasPermission(int userPerms, int required) {
        return (userPerms & required) == required;
    }
    
    // Pattern 3: Efficient modulo for circular buffers (power-of-2 size)
    static final int BUFFER_SIZE = 1024; // must be power of 2
    int head = 0;
    void advance() {
        head = (head + 1) & (BUFFER_SIZE - 1); // faster than head % BUFFER_SIZE
    }
    
    // Pattern 4: Unsigned byte to int conversion
    static int unsignedByte(byte b) {
        return b & 0xFF;  // clears sign-extended bits
    }
}
```

---

## 8. Real World Usage

| Company/Context | Operator Usage |
| ---------------- | ---------------- |
| **Amazon** | Bitwise flags for order feature flags, `%` for round-robin load balancing across shards |
| **Google** | Shift operators in Guava's `Hashing`, bitwise ops in Protocol Buffers encoding |
| **Netflix** | Short-circuit `&&`/` | | ` in Hystrix circuit breaker condition evaluation |
| **Kafka** | `&` with power-of-2 partition count for fast modulo: `hash & (numPartitions-1)` |
| **Redis/Jedis** | Bitwise ops for Redis BITFIELD commands, `>>>` for unsigned handling |
| **Spring Security** | Bitwise OR to combine permissions: `ROLE_ADMIN | ROLE_USER` |
| **JDK Collections** | `HashMap` uses `(n-1) & hash` for O(1) bucket index (n is always power-of-2) |
| **HFT Trading** | Shift operators for fast price encoding; bitwise ops to pack multiple flags in one `long` |

---

## 9. Internal JVM Perspective

### Bytecode Instructions per Operator (int type)

| Java Operator | JVM Bytecode |
| -------------- | ------------- |
| `a + b` | `iadd` |
| `a - b` | `isub` |
| `a * b` | `imul` |
| `a / b` | `idiv` |
| `a % b` | `irem` |
| `-a` | `ineg` |
| `a << b` | `ishl` |
| `a >> b` | `ishr` |
| `a >>> b` | `iushr` |
| `a & b` | `iand` |
| `a \| b` | `ior` |
| `a ^ b` | `ixor` |
| `~a` | `iconst_m1` + `ixor` |

> Each type has its own bytecode prefix: `i`=int, `l`=long, `f`=float, `d`=double, `b`=byte (uses int instructions)

### JIT Optimization

- JIT compiles hot arithmetic loops to native CPU instructions
- `a << 1` → CPU SHL instruction (single cycle)
- `a * 2` → JIT often optimizes to SHL anyway
- Short-circuit `&&`/`||` → branch prediction friendly code

---

## 10. Time & Space Complexity

| Operator Category | Time Complexity | Notes |
| ------------------ | ----------------- | ------- |
| Arithmetic (+,-,*,/) | O(1) | CPU-level operations |
| Bitwise (&, | ,^,~,<<,>>,>>>) | O(1) | Single CPU instruction |
| Comparison (==,!=,<,>) | O(1) | For primitives |
| String `+` in loop | O(n²) | Creates new String each time |
| `instanceof` | O(1) | Type check against class hierarchy |
| Pattern matching `instanceof` | O(1) | Same as above + cast |

---

## 11. Advantages

- **Type-safe operations** — compiler enforces operand type compatibility
- **Short-circuit evaluation** — enables null-safe guards and performance optimization
- **Bitwise operations** — enable compact flag storage, fast power-of-2 math
- **Compound assignment** — `+=`, `-=` include implicit cast for byte/short
- **Ternary operator** — concise conditional expression (functional-style)
- **Pattern `instanceof`** (Java 16+) — eliminates boilerplate cast-after-check pattern

---

## 12. Disadvantages

- **No operator overloading** — can't define custom behaviour for `+` on your class (unlike Kotlin/C++)
- **Silent integer overflow** — `int overflow` wraps around without ArithmeticException
- **Floating-point imprecision** — `0.1 + 0.2 != 0.3` due to IEEE 754
- **String concatenation with `+` in loops** — quadratic performance if misused
- **Precedence complexity** — `&` vs `&&`, `|` vs `||` confusion causes bugs
- **Ternary nesting** — deeply nested ternaries are unreadable
- **`++` in expressions** — post/pre increment in complex expressions is error-prone

---

## 13. Tradeoffs

| Decision | Use When | Avoid When |
| ---------- | ---------- | ------------ |
| `&&` vs `&` | Normal boolean logic (short-circuit preferred) | Right side must always execute (side effects needed) |
| `%` vs `& (n-1)` | General modulo | Only use `&` when divisor is guaranteed power-of-2 |
| Bitwise flags vs booleans | Many boolean flags in memory-critical code | Readability matters more |
| Ternary vs if-else | Simple single-expression condition | Complex logic, multiple lines |
| `<<`, `>>` vs `*`, `/` | Performance-critical loops | Regular business code (clarity > micro-opt) |

---

## 14. Comparison

### `&` vs `&&`

| Aspect | `&` (Bitwise AND / Non-short-circuit) | `&&` (Logical AND / Short-circuit) |
| -------- | --------------------------------------- | ------------------------------------- |
| Operand types | `int`, `long`, `boolean` | `boolean` only |
| Short-circuit | No — always evaluates both | Yes — stops if left is false |
| Use case | Bit manipulation, forced both-eval | Boolean logic, null-safe guards |
| Performance | Evaluates both sides always | Can skip right side |

### `>>` vs `>>>`

| Aspect | `>>` (Signed right shift) | `>>>` (Unsigned right shift) |
| -------- | -------------------------- | ------------------------------ |
| Sign bit | Preserved (fills with sign bit) | Always fills with 0 |
| Effect on positive | Same as `>>>` | Same as `>>` |
| Effect on negative | Stays negative | Becomes positive large number |
| Use case | Arithmetic divide by 2 | Unsigned byte/hash operations |

### `==` vs `.equals()`

| Aspect | `==` | `.equals()` |
| -------- | ------ | ------------ |
| For primitives | Value comparison | N/A (primitives have no methods) |
| For objects | Reference comparison (same object?) | Value/content comparison |
| For null | Safe (`null == null` is true) | NPE if called on null |
| String | May give wrong answer (pool trick) | Always correct for content |

---

## 15. Common Mistakes

```java
// Mistake 1: Integer division expecting decimal
int a = 5, b = 2;
double result = a / b;      // 2.0, NOT 2.5!
double result = (double)a / b; // 2.5 — correct

// Mistake 2: Confusing & and &&
if (user != null & user.isActive()) { }  // NPE if user is null! & always evaluates right
if (user != null && user.isActive()) { } // Safe — short-circuits

// Mistake 3: Post-increment in return
int getAndIncrement(int x) {
    return x++;  // returns ORIGINAL x, increment is lost!
    return ++x;  // returns incremented value
}

// Mistake 4: Assuming == works for String
String a = "hello", b = "hello";
if (a == b) { }  // may be true (string pool) but WRONG approach
if (a.equals(b)) { } // CORRECT

// Mistake 5: % returning negative for negative numbers
System.out.println(-7 % 3);   // -1, NOT 2!
// Fix: use Math.floorMod(-7, 3) → 2 (always non-negative)

// Mistake 6: Ternary with incompatible types
Object obj = true ? new Integer(1) : "hello";  // OK
int val = true ? 1 : 2L;  // 1 widened to long, then narrowed? Careful!

// Mistake 7: Compound assignment on byte/short
byte b = 10;
b = b + 5;   // COMPILE ERROR — b+5 is int
b += 5;      // OK — compound assignment has implicit narrowing cast
```

---

## 16. Best Practices

1. **Always use `&&` and `||`** for boolean logic — `&` and `|` only when bitwise intent is explicit
2. **Use parentheses** to make complex expressions unambiguous
3. **Never use `==` to compare String objects** or any non-primitive types
4. **Use `Math.floorMod()`** instead of `%` when working with negative numbers
5. **Use `Math.addExact()`, `Math.multiplyExact()`** for overflow-safe arithmetic (Java 8+)
6. **Avoid `++`/`--` inside complex expressions** — use on own line for clarity
7. **Prefer bit shifting to multiplication/division** only in documented, performance-critical code
8. **Use named constants for bitwise flags**: `static final int READ = 1 << 0;`
9. **Avoid string concatenation with `+` inside loops** — use `StringBuilder.append()`
10. **Pattern `instanceof`** (Java 16+) — prefer over cast-after-check pattern

---

## 17. Interview Section

### Easy

1. What is the difference between `==` and `.equals()` in Java?
2. What is the result of `5 / 2` in Java? What about `5.0 / 2`?
3. What is the difference between `++i` and `i++`?
4. What is short-circuit evaluation in Java?
5. What does the `%` operator return for negative numbers?

### Medium

1. Explain operator precedence: What is the result of `2 + 3 * 4 - 1`?
2. What is the difference between `&` and `&&`?
3. What is `>>>` and when would you use it over `>>`?
4. What is the result of `"a" + 1 + 2` and `1 + 2 + "a"`? Explain.
5. What does `b += 5` compile to when `b` is a `byte`?

### Hard

1. Explain why `Math.floorMod(-7, 3)` returns `2` but `-7 % 3` returns `-1`.
2. How does Java's `HashMap` use bitwise AND for bucket indexing?
3. What is the XOR swap trick? Explain with binary.
4. Why can't `==` reliably compare Integer objects outside the cache range?
5. What is the behavior of `(int) (char) (byte) -1`? Explain step by step.

### Very Hard / Trick Questions

1. What is the result of `i+++i` where `i=5`? (post-increment + addition)
2. What is `Integer.MAX_VALUE >> 1`? What about `-1 >> 1`?
3. Explain how `(n & (n-1)) == 0` checks if `n` is a power of 2.
4. What is the difference in behavior between `1/0` and `1.0/0.0`?

### Expected Key Answers

- `i+++i` with i=5: parsed as `(i++) + i` → `5 + 6` = 11, i = 6
- `-1 >> 1` → `-1` (sign bit preserved, -1 in two's complement is all 1s)
- `Integer.MAX_VALUE >> 1` → `1073741823`
- `(n & (n-1)) == 0`: if n is power of 2, its binary has exactly one 1. n-1 flips that and all lower bits. AND gives 0.

---

## 18. Coding Questions

### Easy

1. Check if a given `int` is even or odd using bitwise operator.
2. Find the absolute value of an `int` without using `Math.abs()`.
3. Swap two integers without using a temporary variable.
4. Check if a number is a power of 2 using bitwise operators.
5. Given `int x = 5`, compute `x` squared using only bit shifting and addition.

### Medium

1. Count the number of 1-bits in an integer (`Integer.bitCount()` style).
2. Implement `setPower(int n, int k)`: return `n` rounded up to the next power of 2 ≥ k.
3. Given a permissions system with READ=1, WRITE=2, EXECUTE=4, implement `grant`, `revoke`, `check`.
4. Implement a circular buffer index increment using bitwise AND (assume power-of-2 capacity).
5. Find the single non-duplicate number in an array where every other element appears twice (XOR trick).

### Hard

1. Implement a BigInteger adder for arbitrarily long binary strings.
2. Reverse the bits of a 32-bit unsigned integer.
3. Determine if a signed 32-bit integer is a palindrome without converting to string.
4. Implement division of two integers using only subtraction and bit shifts.
5. Find the maximum XOR of two numbers in an array.

### Company Level

1. **Google:** Implement a `BitSet` class with `set`, `clear`, `get`, `toggle`, `and`, `or`, `xor`.
2. **Amazon:** Design a fast in-memory permission cache using bitwise flags.
3. **Uber:** Use bit shifting to implement a fixed-point arithmetic price comparison (no floating point).
4. **Netflix:** Implement a bloom filter using bitwise operations on a `long[]`.
5. **Trading Systems:** Implement a packed `int` that stores bid/ask/volume in different bit ranges.

---

## 19. Production Scenarios

### Scenario 1: Silent Arithmetic Overflow in Counter

```
Problem: A metrics system counted page views using int.
After ~2.1 billion views, counter wrapped to negative.
Alarm: "Negative page views" alert fired in production.
Cause: int overflow, no exception thrown.
Fix: Use long, or AtomicLong for concurrent counters.
Lesson: Always consider value range before choosing int vs long.
```

### Scenario 2: Wrong Use of `&` vs `&&`

```java
// Production bug found in review
if (user != null & user.getRole().equals("ADMIN")) { // NPE if user=null!
// Should be:
if (user != null && user.getRole().equals("ADMIN")) { // short-circuits safely
```

### Scenario 3: String Concatenation Performance

```
Problem: Log aggregation service concatenated 10K log lines with += in a loop.
Result: 10K String allocations, quadratic memory usage, GC pauses every few seconds.
Fix: StringBuilder.append() → 10x throughput improvement.
```

### Scenario 4: Unsigned Byte Processing

```java
// Reading bytes from network socket
byte[] data = socket.read();
// Bug: treating signed byte as unsigned
int value = data[0];           // -128 to 127 (wrong for protocol values 0-255)
// Fix:
int value = data[0] & 0xFF;   // 0 to 255 (correct unsigned interpretation)
```

---

## 20. Internal Deep Dive

### How `&&` is compiled

```java
boolean result = a && b;
// Bytecode:
iload a
ifeq FALSE_BRANCH   // if a == 0 (false), jump to FALSE_BRANCH
iload b
goto END
FALSE_BRANCH:
iconst_0            // push false
END:
istore result
// Right operand (b) is NEVER loaded if a is false — true short-circuit
```

### HashMap Bucket Index (OpenJDK)

```java
// From HashMap.java source:
tab[i = (n - 1) & hash]
// n = table length (always power of 2)
// (n-1) = bit mask (e.g., n=16 → 0b1111)
// & hash = last log2(n) bits of hash → O(1) bucket index
// Equivalent to hash % n but 5-10x faster
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| `&` vs `&&` | `&` is bitwise AND (int) or non-short-circuit boolean AND. `&&` is ONLY for boolean, short-circuits. |
| `>>` vs `>>>` | `>>` is arithmetic (sign-preserving). `>>>` is logical (always zero-fill). |
| `++i` vs `i++` | Both increment. Difference is WHEN the new value is returned in an expression. |
| `-7 % 3 == -1` not 2 | Java `%` returns a result with the sign of the dividend. Use `Math.floorMod` for always-positive. |
| `a = b = c = 5` | Right-to-left: c=5, b=5, a=5. Assignment chains from right. |
| `!` vs `~` | `!` is logical NOT (boolean). `~` is bitwise NOT (integer). |

---

## 22. Cheat Sheet

```
ARITHMETIC: + - * / %
  int/int → int (/ truncates!), double/anything → double

RELATIONAL: == != > < >= <=
  Always use .equals() for objects, not ==

LOGICAL:  && || !   (short-circuit)
BITWISE:  &  |  ^  ~ << >> >>>   (no short-circuit)

INCREMENT: i++ (post: return then increment) | ++i (pre: increment then return)

SHIFT:
  << n  = × 2^n
  >> n  = ÷ 2^n (signed)
  >>> n = ÷ 2^n (unsigned, fills 0)

COMMON TRICKS:
  n & 1 == 0       → n is even
  n & (n-1) == 0   → n is power of 2
  a ^ b ^ a == b   → XOR self-cancels
  x & 0xFF         → unsigned byte value
  (n & (n-1))      → clear lowest set bit

PRECEDENCE (high→low):
  postfix++ / --
  unary ++/ -- / + / - / ~ / !
  * / %
  + -
  << >> >>>
  < > <= >= instanceof
  == !=
  & ^ |
  && ||
  ?:
  = += -= etc.
```

---

## 23. Mind Map

```
OPERATORS
│
├── ARITHMETIC (+, -, *, /, %)
│   ├── Integer division truncates
│   ├── % sign follows dividend
│   └── Overflow wraps silently
│
├── INCREMENT/DECREMENT (++, --)
│   ├── Post: return THEN change
│   └── Pre: change THEN return
│
├── RELATIONAL (==, !=, <, >, <=, >=)
│   └── == is reference for objects → use .equals()
│
├── LOGICAL (&&, ||, !)
│   └── SHORT-CIRCUIT ← performance & safety
│
├── BITWISE (&, |, ^, ~, <<, >>, >>>)
│   ├── No short-circuit
│   ├── >> preserves sign
│   └── >>> fills 0
│
├── ASSIGNMENT (=, +=, etc.)
│   └── Compound includes implicit cast
│
├── TERNARY (?:)
│   └── Right-to-left associative
│
└── SPECIAL
    ├── instanceof (null-safe, Java 16 pattern)
    └── String + (concat, left-to-right type matters)
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| Short-circuit | `&&` stops if left is false; ` | | ` stops if left is true |
| `>>` vs `>>>` | `>>` arithmetic (preserves sign); `>>>` logical (fills 0) |
| Integer division | `5/2 = 2` in Java; cast to double for decimal result |
| `%` on negatives | Sign follows dividend: `-7%3 = -1`; use `Math.floorMod` for positive result |
| Post vs pre `++` | `i++` returns old then increments; `++i` increments then returns |
| `&` vs `&&` | `&` always evaluates both; `&&` short-circuits |
| Compound assignment | `b += 5` includes implicit narrowing cast (byte/short safe) |
| `(n-1) & hash` | Fast modulo when n is power-of-2 (used in HashMap) |
| XOR swap | `a^=b; b^=a; a^=b` swaps without temp variable |
| `b & 0xFF` | Convert signed byte to unsigned int value |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"AMP then OR"** | Bitwise AND (`&`) has higher precedence than bitwise OR (` | `) |
| **"Post first, Pre later"** | Post-increment: expression evaluated first, then increment |
| **"Short STOPS at FALSE for &&"** | `&&` stops (short-circuits) when it finds a false |
| **"String + is left-to-right"** | Type of left operand determines + behaviour |
| **"Power of 2? (n & n-1) == 0"** | Single bit set; n-1 flips it and all below; AND = 0 |
| **">>> always safe for unsigned"** | Three-arrow: fills three zeros (logically zero) |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| Operand | The value on which an operator acts (a, b in a+b) |
| Unary | Operates on a single operand (!, ~, ++, --) |
| Binary | Operates on two operands (a+b, a&&b) |
| Ternary | Operates on three operands (?:) |
| Short-circuit | Skip evaluating right operand if result determined from left |
| Operator precedence | Order in which operators are evaluated in an expression |
| Associativity | Direction of evaluation for same-precedence operators (L-to-R or R-to-L) |
| Bitwise | Operations on individual bits of integer values |
| Logical shift | Fill with zeros (>>>) |
| Arithmetic shift | Fill with sign bit (>>) |
| Compound assignment | `+=`, `-=`, etc. — shorthand for `a = a op b` with implicit cast |

---

## 27. Interview One-Liners

- "`5/2` gives `2` in Java because both operands are `int` — integer division truncates."
- "`&&` short-circuits on false; `||` short-circuits on true — prevents NPE in guard conditions."
- "`&` evaluates both sides always; use `&&` for null-safe boolean logic."
- "`>>` fills with sign bit; `>>>` fills with zero — use `>>>` for unsigned operations."
- "`-7 % 3` is `-1` in Java; use `Math.floorMod(-7, 3)` to get positive `2`."
- "`i++` returns old value; `++i` returns new value — difference only matters in expressions."
- "`HashMap` uses `(n-1) & hash` instead of `hash % n` for O(1) bucket — requires power-of-2 capacity."
- "Compound assignment (`b += 5`) includes implicit narrowing cast — `byte b += 5` works without explicit cast."
- "`instanceof` returns `false` for null — never throws NPE."
- "String `+` is left-to-right: `1 + 2 + \"a\"` = `\"3a\"`, `\"a\" + 1 + 2` = `\"a12\"`."

---

## 28. Summary

Java operators cover 8 categories from arithmetic to bitwise. The most critical production knowledge: **short-circuit evaluation** (`&&`/`||`) prevents NPE and enables performance optimization; **integer division truncates** (cast to `double` when needed); **`%` on negatives** follows the dividend sign (use `Math.floorMod` for always-positive); **bitwise operators** are the backbone of HashMap, permission systems, and network byte handling; **string `+` concatenation** in loops is O(n²) (use StringBuilder); **operator precedence** is a common source of subtle bugs (use parentheses). Java deliberately omits operator overloading (unlike C++/Kotlin) — no custom `+` on user types.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| Bitwise Algorithms (LeetCode Bit manipulation) | Most interview bit problems need operator mastery |
| BigDecimal arithmetic | When `+`, `-`, `*`, `/` aren't enough for precision |
| StringBuilder/StringJoiner | Replace string `+` loops in production |
| Math class (Java 8+) | `Math.addExact`, `Math.floorMod`, `Math.max`, etc. |
| JVM bytecode (`javap -c`) | See exactly how operators compile to bytecode |
| Pattern matching (Java 16-21) | `instanceof` evolution into switch pattern matching |

---

---

# TOPIC 3: CLASSES AND OBJECTS

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | A **class** is a blueprint/template defining data (fields) and behaviour (methods). An **object** is a runtime instance of a class — a concrete entity with its own state in memory. |
| **Why introduced?** | To model real-world entities and organise code around data + behaviour, enabling reuse, encapsulation, and polymorphism. |
| **Problem solved** | Procedural code (C-style) bundles data and functions separately — hard to maintain at scale. OOP groups related data and behaviour into cohesive units. |
| **History** | OOP concept from Simula (1967) → Smalltalk (1970s) → C++ (1983) → Java (1996). Java mandates everything lives inside a class. |
| **Industry importance** | Every Java program is a collection of interacting objects. Spring Beans, JPA Entities, REST Controllers, Services — all classes and objects. |

---

## 2. Intuition

**Class is a blueprint; Object is the house built from it.**

Think of a class `Car` as the architect's drawing. It describes:

- What properties a car has (colour, speed, fuel)
- What a car can do (accelerate, brake, honk)

An object (`Car myCar = new Car()`) is an actual specific car parked in memory with its own state.

You can create thousands of objects from one class, just like thousands of houses from one blueprint — each house has its own address (memory location) and can have different paint colors (field values), but the structure is defined once.

---

## 3. Core Concepts

### 3.1 Class Anatomy

```java
[access_modifier] [modifier] class ClassName [extends ParentClass] [implements Interface] {
    // 1. Fields (state)
    // 2. Static fields (class-level state)
    // 3. Constructors (object creation)
    // 4. Methods (behaviour)
    // 5. Nested classes
    // 6. Static initializer blocks
    // 7. Instance initializer blocks
}
```

### 3.2 Complete Class Structure

```java
public class BankAccount {                       // class declaration
    
    // === FIELDS (instance state) ===
    private String accountNumber;                // private field
    private double balance;                      // private field
    private static int totalAccounts = 0;        // class-level shared state
    
    // === STATIC INITIALIZER ===
    static {
        System.out.println("BankAccount class loaded");
        // Runs ONCE when class is first loaded by JVM
    }
    
    // === INSTANCE INITIALIZER ===
    {
        totalAccounts++;                         // runs for EVERY new object
    }
    
    // === CONSTRUCTORS ===
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;      // 'this' disambiguates field from param
        this.balance = initialBalance;
    }
    
    // === METHODS (behaviour) ===
    public void deposit(double amount) {         // instance method
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.balance += amount;
    }
    
    public double getBalance() { return balance; }
    
    public static int getTotalAccounts() {       // static method
        return totalAccounts;
    }
    
    // === toString (from Object class) ===
    @Override
    public String toString() {
        return "BankAccount{account=" + accountNumber + ", balance=" + balance + "}";
    }
}
```

### 3.3 Object Creation — The `new` Keyword

```java
BankAccount acc = new BankAccount("ACC001", 5000.0);
//              ↑                 ↑
//          new allocates      constructor called
//          heap memory        to initialise
```

Step-by-step:

1. `new` → JVM allocates memory on the **heap**
2. Object fields are set to default values (0, null, false)
3. Instance initializer blocks run (top-to-bottom)
4. Constructor executes
5. Reference to the object returned and stored in `acc` (on stack)

### 3.4 Access Modifiers

| Modifier | Class | Package | Subclass | World |
| ---------- | ------- | --------- | ---------- | ------- |
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| (default/package-private) | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

### 3.5 Object vs Class Members

| Aspect | Instance (Object) | Static (Class) |
| -------- | ----------------- | ---------------- |
| Belongs to | Each object independently | The class (shared by all objects) |
| Memory | Object on heap | Method Area / Metaspace |
| Access | Through object reference | Through class name or object |
| Lifecycle | Created with object, GC'd when unreachable | Lives as long as class is loaded |
| Example | `balance`, `accountNumber` | `totalAccounts`, `MAX_LIMIT` |

### 3.6 The Object Class — Root of All

Every class in Java implicitly extends `java.lang.Object`. It provides:

| Method | Purpose | Notes |
| -------- | --------- | ------- |
| `equals(Object obj)` | Compare objects | Default: reference equality (==) |
| `hashCode()` | Hash for collections | Must be consistent with equals |
| `toString()` | String representation | Default: `ClassName@hex(hashCode)` |
| `getClass()` | Get runtime class | Returns `Class<?>` object |
| `clone()` | Shallow copy | Requires `Cloneable` interface |
| `finalize()` | Before GC (deprecated Java 9+) | Never use in production |
| `wait()`, `notify()`, `notifyAll()` | Thread synchronisation | Used with `synchronized` blocks |

### 3.7 equals() and hashCode() Contract

**The Contract:**

1. If `a.equals(b)` → `a.hashCode() == b.hashCode()` (mandatory)
2. If `a.hashCode() == b.hashCode()` → NOT necessarily `a.equals(b)` (hash collision OK)
3. `a.equals(a)` → true (reflexive)
4. `a.equals(b)` → `b.equals(a)` (symmetric)
5. Consistent across calls (stable)

```java
// WRONG — violates contract: equals says equal, hashCode says different
@Override public boolean equals(Object o) {
    if (!(o instanceof Person)) return false;
    return ((Person)o).name.equals(this.name);
}
// Missing hashCode override → HashMap/HashSet will BREAK!

// CORRECT — both together
@Override public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Person)) return false;
    Person p = (Person) o;
    return Objects.equals(name, p.name) && age == p.age;
}
@Override public int hashCode() {
    return Objects.hash(name, age);  // Java 7+ utility
}
```

---

## 4. Internal Working

### 4.1 Object Memory Layout in JVM (HotSpot)

```
Heap Memory — Object Layout:
┌────────────────────────────────┐
│ Mark Word (8 bytes)            │ ← hash code, lock state, GC age
│ Class Pointer (4-8 bytes)      │ ← points to class metadata in Metaspace
│─────────────────────────────── │
│ Instance Fields                │
│   field1: int (4 bytes)        │
│   field2: long (8 bytes)       │
│   field3: reference (4 bytes)  │ ← pointer to another heap object
│   ...                          │
│ (Padding to 8-byte alignment)  │
└────────────────────────────────┘
Minimum object overhead: 12-16 bytes (before any fields!)
```

### 4.2 Reference Variable vs Object

```
Stack                          Heap
┌──────────────┐              ┌──────────────────────┐
│ acc (ref)    │ ──────────► │ BankAccount object    │
│ 0x7F3A2B1C  │              │ accountNumber: "ACC1" │
└──────────────┘              │ balance: 5000.0       │
                              └──────────────────────┘

BankAccount acc2 = acc;  // two references to SAME object
┌──────────────┐              ┌──────────────────────┐
│ acc  (ref)   │ ─────┐       │ BankAccount object    │
│ acc2 (ref)   │ ─────┴────► │ accountNumber: "ACC1" │
└──────────────┘              │ balance: 5000.0       │
                              └──────────────────────┘
```

### 4.3 Class Loading Process

```
Source: BankAccount.java
         ↓
Compilation: BankAccount.class (bytecode)
         ↓
JVM Class Loading (ClassLoader):
  1. Bootstrap ClassLoader  → loads JDK classes (java.lang.*)
  2. Extension ClassLoader  → loads ext/ libraries (Java 8 only)
  3. Application ClassLoader → loads YOUR classes from classpath
         ↓
Linking:
  1. Verification  → bytecode valid and safe
  2. Preparation   → static fields allocated, set to defaults
  3. Resolution    → symbolic references → direct references
         ↓
Initialization:
  1. Static initializer blocks run (top-to-bottom)
  2. Static fields assigned their declared values
         ↓
Class ready for object creation (new)
```

### 4.4 Object Creation Bytecode

```java
BankAccount acc = new BankAccount("ACC001", 5000.0);
```

Bytecode:

```
new           #2    // allocate memory for BankAccount on heap
dup                 // duplicate reference on stack (one for constructor, one to store)
ldc           #3    // push "ACC001"
ldc2_w        #4    // push 5000.0 (double)
invokespecial #5    // call BankAccount.<init>(String,double)
astore_1            // store reference in local variable slot 1
```

---

## 5. Visual Flow

```
OBJECT CREATION FLOW:

BankAccount acc = new BankAccount("ACC001", 5000.0);

1. JVM checks if BankAccount class is loaded
   └── If not: ClassLoader loads BankAccount.class
       └── Static initializer runs
       └── Static fields initialized

2. 'new' keyword: JVM allocates heap memory
   ┌──────────────────┐
   │  BankAccount     │
   │  Mark Word: 0x..  │  (default values: all 0/null/false)
   │  accountNumber: null │
   │  balance: 0.0    │
   └──────────────────┘

3. Instance initializer block runs
   └── totalAccounts++ → 1

4. Constructor runs: BankAccount("ACC001", 5000.0)
   └── this.accountNumber = "ACC001"
   └── this.balance = 5000.0

5. Reference stored in 'acc' on stack
   ┌───────┐           ┌──────────────────┐
   │  acc  │ ────────► │  BankAccount@1a2b│
   │(stack)│           │  account="ACC001"│
   └───────┘           │  balance=5000.0  │
                       └──────────────────┘

GARBAGE COLLECTION:
acc = null;    → object has no more references
              → GC marks it eligible for collection
              → Memory reclaimed in next GC cycle
```

---

## 6. Syntax

```java
// Class declaration
[public|protected|private|package-private] [abstract|final] class ClassName
    [extends ParentClass]
    [implements Interface1, Interface2] {
    
    // Field declaration
    [access_modifier] [static] [final] type fieldName [= defaultValue];
    
    // Method declaration
    [access_modifier] [static|abstract|final|synchronized|native] returnType methodName(
        [type param1, type param2, ...]) 
        [throws ExceptionType] {
        // body
    }
}

// Object creation
ClassName varName = new ClassName(args);

// Anonymous class (inline subclass)
Runnable r = new Runnable() {
    @Override public void run() { System.out.println("running"); }
};

// Record (Java 16+) — immutable data class
record Point(int x, int y) {}  // auto-generates: constructor, getters, equals, hashCode, toString
```

---

## 7. Examples

### Basic

```java
public class Student {
    // Fields
    String name;
    int age;
    double gpa;
    
    // Constructor
    Student(String name, int age, double gpa) {
        this.name = name;
        this.age = age;
        this.gpa = gpa;
    }
    
    // Method
    void introduce() {
        System.out.println("Hi, I'm " + name + ", age " + age + ", GPA: " + gpa);
    }
    
    public static void main(String[] args) {
        Student s1 = new Student("Krish", 21, 9.2);
        Student s2 = new Student("Rahul", 22, 8.7);
        
        s1.introduce();   // Hi, I'm Krish, age 21, GPA: 9.2
        s2.introduce();   // Hi, I'm Rahul, age 22, GPA: 8.7
        
        // s1 and s2 are separate objects — different memory locations
        System.out.println(s1 == s2);        // false (different objects)
        System.out.println(s1.name == s2.name); // false (different strings)
    }
}
```

### Intermediate — Object Identity vs Equality

```java
import java.util.Objects;

public class Employee {
    private final String employeeId;  // final: can't change after construction
    private String name;
    private double salary;
    
    public Employee(String employeeId, String name, double salary) {
        this.employeeId = Objects.requireNonNull(employeeId, "ID cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.salary = salary;
    }
    
    // Two employees are "equal" if same employeeId (business key)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                    // same object reference
        if (!(o instanceof Employee)) return false;    // null or wrong type
        Employee emp = (Employee) o;
        return Objects.equals(employeeId, emp.employeeId); // business key comparison
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(employeeId);  // consistent with equals
    }
    
    @Override
    public String toString() {
        return String.format("Employee{id='%s', name='%s', salary=%.2f}",
            employeeId, name, salary);
    }
    
    // Getters/setters...
    public String getEmployeeId() { return employeeId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name); }
    public double getSalary() { return salary; }
    public void setSalary(double salary) {
        if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative");
        this.salary = salary;
    }
}
```

### Advanced — Object Creation Patterns

```java
// Singleton pattern — only one instance
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private final String connectionString;
    
    private DatabaseConnection(String connectionString) {
        this.connectionString = connectionString;
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection("jdbc:mysql://localhost:3306/mydb");
        }
        return instance;
    }
}

// Builder pattern — for complex object construction
public class Order {
    private final String orderId;
    private final String customerId;
    private final List<String> items;
    private final double totalAmount;
    private final String shippingAddress;
    
    private Order(Builder builder) {
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.items = Collections.unmodifiableList(builder.items);
        this.totalAmount = builder.totalAmount;
        this.shippingAddress = builder.shippingAddress;
    }
    
    public static class Builder {
        private String orderId;
        private String customerId;
        private List<String> items = new ArrayList<>();
        private double totalAmount;
        private String shippingAddress;
        
        public Builder orderId(String id) { this.orderId = id; return this; }
        public Builder customerId(String id) { this.customerId = id; return this; }
        public Builder addItem(String item) { this.items.add(item); return this; }
        public Builder totalAmount(double amount) { this.totalAmount = amount; return this; }
        public Builder shippingAddress(String addr) { this.shippingAddress = addr; return this; }
        
        public Order build() {
            if (orderId == null || customerId == null) {
                throw new IllegalStateException("orderId and customerId are required");
            }
            return new Order(this);
        }
    }
}

// Usage:
Order order = new Order.Builder()
    .orderId("ORD-12345")
    .customerId("CUST-789")
    .addItem("Laptop")
    .addItem("Mouse")
    .totalAmount(75000.00)
    .shippingAddress("Patna, Bihar")
    .build();
```

### Production — Records (Java 16+)

```java
// Record: immutable data class with auto-generated boilerplate
public record UserDTO(String userId, String username, String email) {
    // Compact constructor for validation
    public UserDTO {
        if (userId == null || userId.isBlank())
            throw new IllegalArgumentException("userId cannot be blank");
        if (!email.contains("@"))
            throw new IllegalArgumentException("Invalid email");
        // fields are auto-assigned after this block
    }
    
    // Custom methods are allowed
    public String maskedEmail() {
        int at = email.indexOf('@');
        return email.substring(0, 2) + "***" + email.substring(at);
    }
}

// JPA Entity in Spring Boot
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String email;
    
    // JPA requires a no-arg constructor
    protected User() {}
    
    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
    // getters, setters, equals, hashCode...
}
```

---

## 8. Real World Usage

| Company/Framework | Class/Object Usage |
| ------------------- | -------------------- |
| **Spring Boot** | Every `@Service`, `@Controller`, `@Repository` is a singleton object (bean) managed by IoC container |
| **JPA/Hibernate** | `@Entity` classes map to DB tables; each row becomes a Java object in Persistence Context |
| **Amazon AWS SDK** | S3Client, DynamoDbClient — immutable objects built via builder pattern |
| **Google Guava** | ImmutableList, ImmutableMap — final class objects, no modification |
| **Netflix OSS** | Hystrix Command objects — one object per circuit breaker |
| **Kafka** | ProducerRecord, ConsumerRecord — POJO objects wrapping message data |
| **gRPC** | Protocol Buffer generated classes — data objects for RPC |
| **Lombok** | `@Data`, `@Builder`, `@Value` — generates boilerplate for Java classes |

---

## 9. Internal JVM Perspective

### Heap Regions (Java 17+ G1GC default)

```
Heap:
┌─────────────────────────────────────────────┐
│ Young Generation                            │
│  ├── Eden: new objects created here first   │
│  ├── Survivor 0                             │
│  └── Survivor 1                             │
│ Old Generation (Tenured)                    │
│  └── Long-lived objects promoted here       │
│ Large Object Regions (humongous objects)    │
└─────────────────────────────────────────────┘
```

### Object Lifecycle

```
new Object()
→ Allocated in Eden
→ Minor GC: live objects move to Survivor
→ Multiple GCs survived → promoted to Old Gen
→ All references nulled/out of scope → eligible for GC
→ Major/Full GC → memory reclaimed
```

### Object Header Size Impact

- Every object has 12-16 bytes overhead (mark word + class pointer)
- 1 million small objects = 12-16 MB just for headers!
- **Production tip:** Use primitive arrays or value types where possible for large-scale data

### Metaspace (Class Metadata)

- Class definition, method bytecode, static fields all in Metaspace
- Does NOT have a fixed size by default (grows as needed)
- `-XX:MaxMetaspaceSize=256m` to cap it
- Class unloading (when ClassLoader is GC'd) frees Metaspace

---

## 10. Time & Space Complexity

| Operation | Complexity | Notes |
| ----------- | ----------- | ------- |
| Object creation (`new`) | O(1) amortized | Memory allocation + constructor call |
| Field access | O(1) | Direct memory read |
| Method invocation (non-virtual) | O(1) | Direct call (static, final, private) |
| Method invocation (virtual) | O(1) | vtable lookup — still O(1), tiny constant |
| `instanceof` check | O(depth of hierarchy) | Checks class hierarchy; practically O(1) |
| `equals()` (default) | O(1) | Reference comparison only |
| `equals()` (String) | O(n) | Compares character by character |
| `hashCode()` (Objects.hash) | O(n) | n = number of fields |
| Object per-instance memory | Object header (12-16B) + fields + padding | Minimum 16 bytes even for empty class |

---

## 11. Advantages

- **Encapsulation** — data and behaviour together, controlled access
- **Reusability** — one class, many objects; class hierarchy via inheritance
- **Abstraction** — hide implementation, expose interface
- **Polymorphism** — one interface, multiple implementations
- **Maintainability** — changes in one class don't cascade
- **Natural modelling** — maps to real-world entities (Order, User, Product)
- **Framework support** — Spring, JPA, Hibernate all built on Java's class model

---

## 12. Disadvantages

- **Object overhead** — every object has 12-16 byte header (GC pressure in high-frequency scenarios)
- **Verbosity** — Java requires explicit class declarations for everything
- **Mutable state** — objects with setters can be accidentally mutated across threads (concurrency bugs)
- **God class antipattern** — easy to put too much responsibility in one class
- **Deep inheritance** — can make code hard to follow and maintain
- **Java lacks value types** (until Project Valhalla — Java 23+ preview) — small objects always heap-allocated

---

## 13. Tradeoffs

| Decision | Prefer | When |
| ---------- | -------- | ------ |
| Class vs Record | Record | Immutable data carriers (DTOs, value objects) |
| Mutable vs Immutable objects | Immutable | Thread-safe, predictable; Mutable for complex state |
| Inheritance vs Composition | Composition | Default — prefer "has-a" over "is-a" |
| Single responsibility | Small focused classes | Always — easier to test, maintain |
| Builder vs constructor | Builder | When >3-4 params or optional params |
| Anonymous class vs Lambda | Lambda | For functional interfaces (Java 8+) |

---

## 14. Comparison

### Class vs Record vs Interface vs Abstract Class

| Aspect | Class | Record (Java 16+) | Abstract Class | Interface |
| -------- | ------- | ------------------ | ---------------- | ----------- |
| Instantiable | Yes | Yes | No | No |
| Mutable | Yes | No (fields are final) | Yes | N/A |
| Fields | Any | Private final only | Any | Static final only |
| Constructors | Yes | Canonical constructor | Yes | No |
| Inheritance | Extends one | Cannot extend other records | Extends one | Implements multiple |
| Use case | General purpose | Immutable data | Partial implementation | Contracts |

### Object Creation Patterns

| Pattern | Use When | Drawback |
| --------- | ---------- | ---------- |
| Direct constructor | Simple objects | Too many params → messy |
| Builder | Complex, optional params | Verbose builder class |
| Factory method | Logic to choose subtype | Static, not polymorphic |
| Singleton | One instance needed | Hard to test, hidden coupling |
| Prototype (clone) | Create copy of existing | Shallow vs deep clone complexity |

---

## 15. Common Mistakes

```java
// Mistake 1: Forgetting to override hashCode with equals
@Override public boolean equals(Object o) { ... }
// Missing hashCode → HashSet/HashMap will not work correctly!

// Mistake 2: Calling overridable methods in constructor
class Parent {
    Parent() { init(); }           // DANGEROUS
    void init() {}
}
class Child extends Parent {
    private int value = 10;
    @Override void init() { System.out.println(value); } // prints 0! value not set yet
}

// Mistake 3: Mutable object in equals/hashCode
class Bad {
    List<String> items;                         // mutable!
    @Override public int hashCode() {
        return Objects.hash(items);             // hashCode changes if list changes!
        // If used as HashMap key: object "lost" after mutation
    }
}

// Mistake 4: == instead of .equals()
String a = new String("hello");
String b = new String("hello");
System.out.println(a == b);       // false (different objects)
System.out.println(a.equals(b));  // true (same content)

// Mistake 5: Forgetting no-arg constructor for JPA
@Entity
public class User {
    // JPA requires no-arg constructor (can be protected/private)
    // Without it: InstantiationException at runtime!
}
```

---

## 16. Best Practices

1. **Follow Single Responsibility Principle** — one class, one job
2. **Override equals AND hashCode together** — never one without the other
3. **Use `Objects.equals()` and `Objects.hash()`** — null-safe utilities (Java 7+)
4. **Prefer immutability** — use `final` fields and no setters where possible
5. **Use Records** (Java 16+) for immutable data carriers
6. **Use Builder pattern** for classes with >3 parameters
7. **Validate in constructor** — fail fast with `Objects.requireNonNull()`
8. **Never call overridable methods from constructor** — polymorphic dispatch with uninitialized state
9. **Mark utility classes `final`** and provide only static methods
10. **Follow JavaBeans naming**: `getX()`, `setX()`, `isX()` for boolean

---

## 17. Interview Section

### Easy

1. What is the difference between a class and an object?
2. What is the default value of an object reference field?
3. What is the `Object` class in Java?
4. What is the difference between `==` and `.equals()` for objects?
5. What is a constructor? What is its return type?

### Medium

1. Explain the contract between `equals()` and `hashCode()`. What happens if you only override `equals()`?
2. What is the difference between shallow copy and deep copy? How do you implement each?
3. What is the object creation process in Java? (new, memory allocation, init, constructor)
4. What is a `record` in Java 16? How is it different from a regular class?
5. What happens when you call `toString()` on an object without overriding it?

### Hard

1. Explain HotSpot JVM object memory layout (Mark Word, Class Pointer, fields, padding).
2. What is the difference between `new Integer(127)` and `Integer.valueOf(127)`?
3. How does `instanceof` work with null? Explain.
4. What is the difference between a static and instance initializer block? In what order do they run?
5. What is object identity vs object equality? Give examples where they differ.

### Very Hard

1. If `a.equals(b)` is true, must `a.hashCode() == b.hashCode()` be true? And vice versa?
2. Explain why calling `list.contains(key)` may fail after mutating an object used as a key.
3. How does HotSpot JVM perform method dispatch for instance methods (vtable lookup)?
4. What is escape analysis and how does it affect object allocation?

---

## 18. Coding Questions

### Easy

1. Create a `Rectangle` class with fields `width`, `height` and methods `area()`, `perimeter()`.
2. Create a `Circle` class with `radius` field, override `toString()`, `equals()`, `hashCode()`.
3. Write a `Counter` class with `increment()`, `decrement()`, `reset()`, and `getValue()` methods.
4. Create a `Point` class and implement `distanceTo(Point other)` method.
5. Create a `Stack` class using an `int[]` array.

### Medium

1. Implement the Builder pattern for a `Person` class with 6 fields (some optional).
2. Implement a generic `Pair<A,B>` class with `equals`, `hashCode`, `toString`.
3. Create an immutable `Money` class with currency and amount supporting `add()` and `subtract()`.
4. Implement a `LRUCache` class with `get` and `put` using LinkedHashMap.
5. Create a `Graph` class with nodes and edges, and implement BFS/DFS methods.

### Hard

1. Implement a deep clone of a complex object graph without using serialization.
2. Create a thread-safe `ObjectPool<T>` with `acquire()` and `release()` methods.
3. Implement `equals()` and `hashCode()` for a binary tree class.
4. Design a `VersionedObject<T>` that tracks mutation history.
5. Implement a generic `Result<T>` class (like Optional) with `success`, `failure` states.

### Company Level

1. **Amazon:** Design an `OrderItem` hierarchy with Shippable, Digital, and Subscription subclasses.
2. **Google:** Implement a type-safe `EventBus` using class objects and reflection.
3. **Netflix:** Design a `CircuitBreaker` class with states (CLOSED, OPEN, HALF_OPEN).
4. **Spring:** Implement a simplified `@Autowired` dependency injection container.
5. **Uber:** Design a `DriverLocation` class with history tracking and proximity queries.

---

## 19. Production Scenarios

### Scenario 1: equals/hashCode Missing

```
Problem: Team added Order objects to a HashSet. After fixing a bug, Orders
with same orderId appeared multiple times in the set.
Root cause: equals() was overridden but hashCode() was not.
HashSet used default hashCode (identity-based) → same logical order
stored in different buckets → set allowed duplicates.
Fix: Always override both equals and hashCode together.
```

### Scenario 2: Mutable Object as Map Key

```java
// Production bug:
List<String> key = new ArrayList<>(Arrays.asList("a", "b"));
Map<List<String>, Integer> map = new HashMap<>();
map.put(key, 42);
key.add("c");                // mutate the key!
System.out.println(map.get(key)); // null — hashCode changed, can't find!
// Fix: Use immutable objects as map keys (String, Integer, record)
```

### Scenario 3: God Class

```
Problem: A legacy e-commerce system had an "OrderProcessor" class with 50+ methods:
payment, shipping, inventory, notifications, analytics — all in one class.
Result: Any change required understanding the entire class.
Merge conflicts daily. Impossible to unit test.
Fix: Decompose using SRP: PaymentService, ShippingService, InventoryService, etc.
```

---

## 20. Internal Deep Dive

### Escape Analysis & Stack Allocation (JIT Optimization)

```
If JIT determines an object does NOT escape the method scope
(not passed to other methods, not stored in heap), it can allocate
the object ON THE STACK instead of the heap.

Result: Object is freed instantly when method returns → Zero GC pressure.

Example: new StringBuilder() inside a method for building a return String
is typically stack-allocated by JIT — never touches GC!
```

### vtable — Virtual Method Dispatch

```
Every class has a vtable (virtual method table) in Metaspace.
vtable is an array of method pointers.
When you call instance method: JVM follows object reference → class pointer → vtable → method code.
For final/static/private methods: no vtable lookup — direct invocation (faster).
JIT inlines frequently-called virtual methods — eliminates dispatch overhead.
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| Class is an object | In Java, class definitions are objects of type `Class<?>` (Class object in Metaspace). But this is metaclass concept, not the class itself. |
| `null` is an object | `null` is the absence of an object reference — it's not an object. Calling any method on null throws NPE. |
| Passing object = passing by reference | Java passes the REFERENCE VALUE by value. You can modify the object the reference points to, but you cannot make the caller's variable point to a different object. |
| `clone()` makes a deep copy | Default `clone()` is SHALLOW — creates new object but copies field values (references still point to same sub-objects). |
| All objects live on heap | JIT escape analysis can place non-escaping objects on the stack (optimization). |
| Constructor creates the object | `new` allocates memory; constructor INITIALIZES it. The object exists (with defaults) before the constructor body runs. |

---

## 22. Cheat Sheet

```
CLASS STRUCTURE:
  access_modifier class Name extends Parent implements Iface {
    fields → state
    static block → runs once at class load
    instance block → runs every new
    constructor → initializes new object
    methods → behaviour
  }

OBJECT CREATION ORDER:
  1. Class loaded + static init (once)
  2. Memory allocated (heap)
  3. Fields set to defaults (0, null, false)
  4. Instance init blocks run
  5. Constructor runs
  6. Reference returned

equals + hashCode RULES:
  Always override BOTH together
  equals: reflexive, symmetric, transitive, consistent
  If a.equals(b) → a.hashCode() == b.hashCode()
  Use Objects.equals() and Objects.hash()

OBJECT HEADER: 12-16 bytes overhead per object

ACCESS MODIFIERS:
  public   → everywhere
  protected → package + subclasses
  default  → package only
  private  → class only

RECORDS (Java 16+):
  record Point(int x, int y) {}
  Auto-generates: constructor, getters, equals, hashCode, toString
```

---

## 23. Mind Map

```
CLASSES & OBJECTS
│
├── CLASS (Blueprint)
│   ├── Fields (state)
│   ├── Methods (behaviour)
│   ├── Constructors
│   ├── Static members
│   └── Nested classes
│
├── OBJECT (Instance)
│   ├── Created with 'new'
│   ├── Stored on HEAP
│   ├── Reference on STACK
│   └── GC eligible when no refs
│
├── OBJECT CLASS (Root)
│   ├── equals() ← override with hashCode!
│   ├── hashCode()
│   ├── toString()
│   ├── getClass()
│   └── wait/notify (threading)
│
├── MEMORY LAYOUT
│   ├── Mark Word (8 bytes)
│   ├── Class Pointer
│   └── Fields + Padding
│
├── OBJECT PATTERNS
│   ├── Singleton
│   ├── Builder
│   ├── Factory
│   └── Prototype
│
└── MODERN (Java 16+)
    └── Records (immutable data class)
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| Class | Blueprint defining fields and methods |
| Object | Runtime instance of a class, allocated on heap |
| `new` | Allocates heap memory and triggers constructor |
| Reference | Stack variable holding the memory address of a heap object |
| `equals()` | Object value comparison (override from Object) |
| `hashCode()` | Integer hash for collections (must match equals) |
| Access modifiers | public > protected > default > private |
| Object header | 12-16 byte JVM overhead per object (Mark Word + Class Pointer) |
| Static initializer | Runs once when class is first loaded |
| Instance initializer | Runs before every constructor call |
| Record | Java 16+ immutable data class with auto-generated boilerplate |
| Escape analysis | JIT optimization: non-escaping objects stack-allocated |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"Blueprint → House"** | Class is the blueprint; Object is the actual house (instance) |
| **"Both or Neither"** | Override equals AND hashCode — never one without the other |
| **"HEAP for objects, STACK for refs"** | Objects always on heap (by default); references on stack |
| **"new = Allocate + Initialize"** | `new` allocates, constructor initializes |
| **"12 bytes minimum"** | Every Java object has at least 12-16 bytes overhead |
| **"null is not an object"** | null is absence of reference; methods on null → NPE |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| `class` | Keyword to declare a class definition |
| `new` | Allocates heap memory and creates a new object |
| `null` | Absence of a reference; default for reference-type fields |
| `this` | Reference to current object inside instance methods/constructors |
| `final` | Field: constant; Method: cannot override; Class: cannot subclass |
| `static` | Belongs to class, not object |
| `instanceof` | Checks if object is instance of a type (null-safe) |
| `extends` | Subclass inherits from superclass |
| `implements` | Class implements interface(s) |
| Object header | JVM internal bytes for GC metadata, lock state, hash |
| vtable | Virtual method table for dynamic dispatch |
| Escape analysis | JIT technique to stack-allocate non-escaping objects |
| Mark Word | 8-byte header field for hash, lock state, GC age |
| Record | Java 16 immutable data class construct |

---

## 27. Interview One-Liners

- "A class is a compile-time construct; an object is a runtime entity on the heap."
- "Every object has a 12-16 byte header (Mark Word + Class Pointer) before fields."
- "Override both `equals()` and `hashCode()` — violating this breaks HashMap and HashSet."
- "Java passes object references by value — you can modify the object but can't change what the caller's variable points to."
- "`instanceof null` always returns false — it's null-safe."
- "Static initializer runs once at class load; instance initializer runs before every constructor."
- "Records (Java 16+) are immutable data classes — final fields, auto-generated equals/hashCode/toString."
- "Escape analysis lets JIT allocate non-escaping objects on the stack instead of heap."
- "Default `Object.equals()` compares references; always override for value-based equality."
- "The constructor doesn't return anything — the `new` keyword returns the reference."

---

## 28. Summary

Classes and objects are the core of Java OOP. A **class** is a compile-time blueprint; an **object** is a runtime heap-allocated instance. Every object creation involves memory allocation, field initialization to defaults, instance initializer execution, then constructor invocation. All classes extend `Object`, inheriting `equals()`, `hashCode()`, and `toString()` — always override `equals()` and `hashCode()` together. Object memory has a 12-16 byte overhead (Mark Word + Class Pointer) before fields — important for large-scale systems. Java 16 Records provide concise immutable data classes. Key production wisdom: never use mutable objects as Map keys, avoid calling overridable methods from constructors, and prefer composition over deep inheritance.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| Inheritance | How classes extend each other; method overriding; `super` |
| Interfaces | Contract definition; default methods (Java 8+); sealed interfaces (Java 17+) |
| Abstract classes | Partial implementation; template method pattern |
| Design Patterns | Singleton, Factory, Builder, Decorator — object creation and structure patterns |
| Java Memory Model | How objects live in heap regions; GC algorithms |
| Records & Sealed Classes | Modern Java (16-21) immutable and restricted type hierarchies |
| Reflection | Runtime class inspection; used by Spring, JPA |

---

---

# TOPIC 4: STRINGS

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | `String` is a class in `java.lang` that represents an immutable sequence of Unicode characters. It is NOT a primitive type. |
| **Why introduced?** | Text handling is fundamental to programming. Java made String immutable and interned for security, thread-safety, and memory efficiency. |
| **Problem solved** | Mutable strings in multi-threaded environments are dangerous (race conditions). Immutability makes Strings inherently thread-safe and cacheable. |
| **History** | Java 1.0 (1996) — `String` class. Java 5 — `StringBuilder`. Java 8 — `String.join()`, `StringJoiner`. Java 11 — `strip()`, `isBlank()`, `lines()`, `repeat()`. Java 15 — Text Blocks (standard). Java 21 — String Templates (preview). |
| **Industry importance** | Strings are the most heavily used class in any Java application — HTTP request parsing, JSON processing, database queries, logging, user input. Misuse causes the most common performance and security issues. |

---

## 2. Intuition

Think of String like a **printed book** — once printed, you cannot change the letters inside. If you want a modified version, you print a **new book** with the changes.

- Immutability: String "hello" in memory cannot become "HELLO" — you get a new String "HELLO"
- String Pool: Like a library that keeps ONE copy of each unique book — if you need "hello" again, you get the same copy from the library (no new printing)
- StringBuilder: Like a **whiteboard** — you can erase and write freely, efficiently. When done, you take a "photo" (toString()) to get the final String.

---

## 3. Core Concepts

### 3.1 String Immutability

```java
String s = "hello";
s.toUpperCase();             // creates NEW String "HELLO" — s unchanged!
System.out.println(s);       // still "hello"

s = s.toUpperCase();         // NOW s points to new "HELLO" object
System.out.println(s);       // "HELLO"
// The old "hello" object still exists in pool (may not be GC'd)
```

**Why immutable?**

1. **Thread safety** — multiple threads can share String without synchronization
2. **Security** — file paths, class names, network addresses can't be tampered with mid-execution
3. **Caching** — hashCode computed once and cached (Java stores it in the String object)
4. **String pool** — safe to reuse the same object for equal strings

### 3.2 String Pool (String Intern Pool)

```java
// String literals → go to String Pool
String a = "hello";     // "hello" created in pool (Heap since Java 7+)
String b = "hello";     // reuses SAME "hello" from pool
System.out.println(a == b);  // true — same object!

// new String() → bypasses pool, always new heap object
String c = new String("hello");  // creates NEW object outside pool
String d = new String("hello");  // creates ANOTHER NEW object
System.out.println(c == d);       // false — different objects
System.out.println(c.equals(d)); // true — same content

// intern() — explicitly put in pool / retrieve from pool
String e = c.intern();            // returns pool reference
System.out.println(a == e);       // true — e is from pool!
```

**Pool location:**

- Java 6 and earlier: PermGen (fixed size → OutOfMemoryError risk)
- Java 7+: **Heap** (subject to GC — much safer)

### 3.3 String vs StringBuilder vs StringBuffer

| Aspect | `String` | `StringBuilder` | `StringBuffer` |
| -------- | ---------- | ----------------- | ---------------- |
| Mutability | Immutable | Mutable | Mutable |
| Thread safety | Thread-safe (immutable) | NOT thread-safe | Thread-safe (synchronized) |
| Performance | Slow in loops | Fast | Slower than StringBuilder |
| Use case | Final text | Single-thread string building | Multi-thread string building |
| Introduced | Java 1.0 | Java 5 | Java 1.0 |

### 3.4 String Internal Representation

```
Java 8 and before:
String object → char[] value (UTF-16 encoded, 2 bytes per char)

Java 9+ (Compact Strings):
String object → byte[] value + byte coder
  coder = LATIN1 (0): each char stored in 1 byte (if all chars ≤ U+00FF)
  coder = UTF16  (1): each char stored in 2 bytes (if any char > U+00FF)

Result: Memory savings of ~50% for ASCII-only strings (most strings in practice)
```

### 3.5 Key String Methods

```java
String s = "Hello, World!";

// Length and access
s.length()                  // 13
s.charAt(0)                 // 'H'
s.indexOf("World")          // 7
s.lastIndexOf('l')          // 10
s.isEmpty()                 // false
s.isBlank()                 // false (Java 11+) — also checks whitespace-only

// Substrings
s.substring(7)              // "World!"
s.substring(7, 12)          // "World"

// Comparison
s.equals("Hello, World!")   // true
s.equalsIgnoreCase("hello, world!")  // true
s.compareTo("Hello")        // positive (lexicographic)
s.startsWith("Hello")       // true
s.endsWith("!")             // true
s.contains("World")         // true

// Transformation
s.toLowerCase()             // "hello, world!"
s.toUpperCase()             // "HELLO, WORLD!"
s.trim()                    // removes leading/trailing whitespace
s.strip()                   // Java 11+: Unicode-aware trim
s.replace("World", "Java")  // "Hello, Java!"
s.replaceAll("\\s+", "_")   // regex replace
s.split(", ")               // ["Hello", "World!"]

// Building
String.join("-", "a", "b", "c")       // "a-b-c"
String.format("Hi %s, you are %d", "Krish", 21)  // "Hi Krish, you are 21"

// Java 11+ additions
"  hello  ".stripLeading()   // "hello  "
"  hello  ".stripTrailing()  // "  hello"
"abc".repeat(3)              // "abcabcabc"
"line1\nline2".lines()       // Stream<String>: ["line1", "line2"]
"  ".isBlank()               // true (only whitespace)
```

---

## 4. Internal Working

### 4.1 String Immutability Implementation (OpenJDK)

```java
// java.lang.String (simplified)
public final class String {           // final — cannot be subclassed
    private final byte[] value;       // Java 9+: compact byte array
    private final byte coder;         // LATIN1 or UTF16
    private int hash;                 // cached hashCode (lazy, 0 = not computed)
    private boolean hashIsZero;       // Java 13+: true if hash was actually 0
    
    public int hashCode() {
        int h = hash;
        if (h == 0 && !hashIsZero) {
            // compute hash ONCE, cache for future calls
            h = computeHash();
            if (h == 0) hashIsZero = true;
            else hash = h;
        }
        return h;
    }
}
```

### 4.2 String Concatenation Evolution

```java
// Java 8 and before — compiled to StringBuilder
String result = a + " " + b;
// becomes: new StringBuilder().append(a).append(" ").append(b).toString()

// Java 9+ — uses invokedynamic with StringConcatFactory
// JVM generates optimized concatenation at runtime
// No intermediate StringBuilder object in simple cases!
```

### 4.3 String.intern() Internal

```java
// When you call s.intern():
// 1. JVM checks if an equal string exists in the pool
// 2. If YES: return the pool reference
// 3. If NO: add this string to the pool, return its reference
// Pool is implemented as a native hash table in JVM
```

### 4.4 + Operator in Loops (Critical Performance)

```java
// PERFORMANCE DISASTER:
String result = "";
for (String s : largeList) {
    result += s;   // Each iteration: new String object created!
}
// n iterations → O(n²) time, O(n²) memory allocated (mostly garbage)

// CORRECT:
StringBuilder sb = new StringBuilder(estimatedCapacity);
for (String s : largeList) {
    sb.append(s);  // O(1) amortized per append
}
String result = sb.toString();  // one final String
// n iterations → O(n) time, O(n) memory
```

---

## 5. Visual Flow

```
STRING POOL MECHANISM:

String a = "hello";
    ┌───────────────┐      String Pool (Heap)
    │  a (stack)    │ ─────────► ┌─────────┐
    └───────────────┘            │ "hello" │
                                 └─────────┘

String b = "hello";    // reuses from pool
    ┌───────────────┐      String Pool
    │  a (stack)    │ ─┐         ┌─────────┐
    │  b (stack)    │ ─┴────────► │ "hello" │
    └───────────────┘            └─────────┘
    a == b → TRUE

String c = new String("hello");   // bypasses pool
    ┌───────────────┐      String Pool        Heap
    │  a (stack)    │ ─┐         ┌─────────┐  ┌─────────┐
    │  b (stack)    │ ─┴────────► │ "hello" │  │ "hello" │◄─ c
    │  c (stack)    │ ──────────────────────►  └─────────┘
    └───────────────┘
    a == c → FALSE
    a.equals(c) → TRUE

COMPACT STRINGS (Java 9+):
"Hello"              "Héllo"
byte[] value         byte[] value
[72,101,108,108,111] [72,-61,-87,108,108,111] (UTF-8? No, UTF-16LE)
coder = LATIN1       coder = UTF16
1 byte per char      2 bytes per char
→ 5 bytes            → 12 bytes
```

---

## 6. Syntax

```java
// String creation
String s1 = "literal";             // String pool
String s2 = new String("value");   // heap (not pool)
String s3 = String.valueOf(42);    // from int: "42"
String s4 = String.valueOf(true);  // "true"
String s5 = String.format("Name: %s, Age: %d", "Krish", 21);
String s6 = "Hello".intern();      // explicitly pool

// Text Blocks (Java 15+)
String json = """
    {
        "name": "Krish",
        "age": 21
    }
    """;

// StringBuilder usage
StringBuilder sb = new StringBuilder();    // default capacity 16
sb.append("Hello");
sb.append(", ");
sb.append("World");
sb.insert(5, " Beautiful");   // insert at index
sb.delete(5, 15);             // delete range
sb.reverse();                 // reverse contents
sb.replace(0, 5, "Hi");       // replace range
String result = sb.toString();

// String.join (Java 8+)
String joined = String.join(", ", "a", "b", "c");     // "a, b, c"
String fromList = String.join("-", list);              // join Iterable

// StringJoiner (Java 8+)
StringJoiner sj = new StringJoiner(", ", "[", "]");  // delimiter, prefix, suffix
sj.add("one"); sj.add("two");
String out = sj.toString();  // "[one, two]"
```

---

## 7. Examples

### Basic

```java
public class StringBasics {
    public static void main(String[] args) {
        String name = "Krish";
        String greeting = "Hello, " + name + "!";   // "Hello, Krish!"
        
        System.out.println(greeting.length());        // 13
        System.out.println(greeting.toUpperCase());   // "HELLO, KRISH!"
        System.out.println(greeting.contains("Krish")); // true
        System.out.println(greeting.replace("Krish", "World")); // "Hello, World!"
        
        // Splitting
        String csv = "a,b,c,d";
        String[] parts = csv.split(",");  // ["a","b","c","d"]
        for (String p : parts) System.out.print(p + " "); // a b c d
    }
}
```

### Intermediate — Pool and Comparison Traps

```java
public class StringComparison {
    public static void main(String[] args) {
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        String s4 = s3.intern();
        
        System.out.println(s1 == s2);         // true  (same pool object)
        System.out.println(s1 == s3);         // false (s3 is new heap object)
        System.out.println(s1 == s4);         // true  (intern returns pool object)
        System.out.println(s1.equals(s3));    // true  (value comparison)
        
        // Concatenation with literals — constant folding at compile time
        String s5 = "Ja" + "va";    // compiler resolves to "Java" → pool
        System.out.println(s1 == s5); // true — same pool object!
        
        // Concatenation with variable — NOT constant-folded
        String part = "Ja";
        String s6 = part + "va";    // runtime concatenation → new object
        System.out.println(s1 == s6); // false
        System.out.println(s1.equals(s6)); // true
    }
}
```

### Advanced — Performance Comparison

```java
import java.util.*;

public class StringPerformance {
    public static void main(String[] args) {
        int N = 100_000;
        
        // Method 1: String += (BAD)
        long start = System.nanoTime();
        String r1 = "";
        for (int i = 0; i < N; i++) r1 += "x";
        System.out.println("String +=: " + (System.nanoTime() - start) / 1_000_000 + "ms");
        // Typically: 3000-5000ms for N=100K
        
        // Method 2: StringBuilder (GOOD)
        start = System.nanoTime();
        StringBuilder sb = new StringBuilder(N);
        for (int i = 0; i < N; i++) sb.append("x");
        String r2 = sb.toString();
        System.out.println("StringBuilder: " + (System.nanoTime() - start) / 1_000_000 + "ms");
        // Typically: 5-10ms — 500-1000x faster!
        
        // Method 3: String.join with array
        start = System.nanoTime();
        char[] chars = new char[N];
        Arrays.fill(chars, 'x');
        String r3 = new String(chars);
        System.out.println("char[] + new String: " + (System.nanoTime() - start) / 1_000_000 + "ms");
    }
}
```

### Production — Text Blocks, Security

```java
// Text Blocks (Java 15+) — clean multiline strings
public class TextBlockExample {
    // Before Java 15:
    static String oldJson = "{\n" +
        "  \"name\": \"Krish\",\n" +
        "  \"role\": \"Developer\"\n" +
        "}";
    
    // Java 15+: Text Block
    static String newJson = """
        {
          "name": "Krish",
          "role": "Developer"
        }
        """;
    
    // SQL queries — much more readable
    static String sql = """
        SELECT u.id, u.name, o.total
        FROM users u
        JOIN orders o ON u.id = o.user_id
        WHERE u.active = true
        AND o.created_at > :startDate
        ORDER BY o.total DESC
        """;
    
    // Security: NEVER use String for passwords!
    // String stays in pool → memory dump can reveal password
    // Use char[] instead:
    char[] password = getPassword();  // process it
    Arrays.fill(password, '\0');      // clear immediately after use
}

// String formatting in logging (avoid + in logger)
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    
    void processOrder(String orderId, double amount) {
        // BAD: String concatenation even if log level is DEBUG (disabled)
        log.debug("Processing order: " + orderId + " for ₹" + amount);
        
        // GOOD: SLF4J lazy evaluation — string only built if DEBUG enabled
        log.debug("Processing order: {} for ₹{}", orderId, amount);
    }
}
```

---

## 8. Real World Usage

| Company/Context | String Usage |
| ---------------- | ------------- |
| **Spring MVC** | `@RequestParam String name` — request parameters are Strings |
| **Jackson/JSON** | Serialization/deserialization converts object fields to/from String |
| **Amazon S3** | Bucket names, object keys are Strings |
| **Netflix Zuul** | HTTP headers, routes processed as Strings |
| **Google Guava** | `Splitter`, `Joiner` — production-grade String utilities |
| **Log4j/SLF4J** | Log messages are Strings; use `{}` placeholders for lazy concat |
| **JPA/Hibernate** | JPQL queries as Strings; entity String fields → VARCHAR |
| **Kafka** | Topic names, consumer group IDs are Strings |
| **Spring Security** | Authentication tokens, roles stored as Strings |

---

## 9. Internal JVM Perspective

### String.hashCode() Caching

```java
// String caches its hashCode internally:
private int hash;  // starts at 0

public int hashCode() {
    if (hash == 0) {
        // Polynomial rolling hash: s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
        hash = computeHash();
    }
    return hash;
}
// Why 31? It's prime, and (31 * n) == (n << 5) - n → JIT can optimize to shift+subtract
```

### Memory in String Pool (JVM Native Hash Table)

```
String Pool is a native (C++) hash table in HotSpot JVM.
- Managed separately from heap GC (historically)
- Java 7+: Pool references objects in heap (GC can collect unreferenced pool strings)
- Default pool size: 60013 buckets (Java 8) → configurable with -XX:StringTableSize
- Java 11: default 65536 → better distribution
- Large applications: increase with -XX:StringTableSize=1000003
```

### Compact Strings Memory Impact (Java 9+)

```
Before Java 9:
  String "Hello" = char[] {H,e,l,l,o} = 10 bytes (2 per char) + object overhead

After Java 9 (Compact Strings):
  String "Hello" = byte[] {72,101,108,108,111} = 5 bytes (1 per char) + coder byte

~50% memory reduction for ASCII strings → huge GC improvement in string-heavy apps
```

---

## 10. Time & Space Complexity

| Operation | Time | Space | Notes |
| ----------- | ------ | ------- | ------- |
| `s.length()` | O(1) | O(1) | Field access |
| `s.charAt(i)` | O(1) | O(1) | Array index |
| `s.equals(t)` | O(min(m,n)) | O(1) | Character comparison |
| `s.compareTo(t)` | O(min(m,n)) | O(1) | Lexicographic |
| `s.indexOf(c)` | O(n) | O(1) | Linear scan |
| `s.contains(sub)` | O(n×m) | O(1) | Naive; optimized internally |
| `s.substring(i,j)` | O(j-i) | O(j-i) | Creates new String |
| `s.split(regex)` | O(n) | O(n) | Creates array |
| `s.replaceAll(regex)` | O(n) | O(n) | Regex compile overhead |
| `sb.append(s)` | O(s.length()) amortized | O(1) amortized | Doubling strategy |
| `String +=` in loop | O(n²) | O(n²) | Creates new String each time |

---

## 11. Advantages

- **Immutability** — thread-safe, hashCode cacheable, usable as Map keys safely
- **String pool** — memory efficient for repeated strings
- **Rich API** — 60+ methods for all text manipulation needs
- **Security** — immutability prevents malicious modification of class names, file paths
- **Compact Strings** (Java 9+) — 50% memory reduction for ASCII strings
- **Text Blocks** (Java 15+) — multiline strings without escape hell

---

## 12. Disadvantages

- **Immutability overhead** — every modification creates a new object
- **String concatenation in loops** — O(n²) pitfall for beginners
- **`new String("literal")` is wasteful** — bypasses pool, creates redundant object
- **String pool is not infinite** — excessive `intern()` can cause memory pressure
- **Regex overhead** — `replaceAll()`, `matches()`, `split()` compile regex every call; cache `Pattern`
- **Not for passwords** — String stays in memory pool even after done; use `char[]`
- **Null handling** — `String.valueOf(null)` gives "null"; method call on null → NPE

---

## 13. Tradeoffs

| Decision | Use | When |
| ---------- | ----- | ------ |
| `String` | Final text value | Not modifying, sharing across threads |
| `StringBuilder` | Mutable building | Single thread, loop concatenation |
| `StringBuffer` | Mutable building | Multi-thread shared builder (rare) |
| `String.intern()` | Save memory | High-frequency duplicate strings (enum-like values) |
| `char[]` for passwords | Security | Always — clear with `Arrays.fill(pwd, '\0')` |
| Text Block | Multiline string | SQL, JSON, HTML templates (Java 15+) |
| `String.format()` | Readable formatting | Debug/display; avoid in hot paths (slow) |
| `MessageFormat` | Locale-sensitive | Internationalization (i18n) |

---

## 14. Comparison

### String vs StringBuilder vs StringBuffer

| Aspect | String | StringBuilder | StringBuffer |
| -------- | -------- | --------------- | -------------- |
| Mutability | Immutable | Mutable | Mutable |
| Thread-safe | Yes (by immutability) | No | Yes (synchronized) |
| Performance | Slowest for building | Fastest | Slower than SB |
| API | Rich (60+ methods) | Subset | Same as SB |
| Use in Java streams | As result | For building | Rarely |

### String.format() vs +, StringBuilder, MessageFormat

| Method | Speed | Readability | Use Case |
| -------- | ------- | ------------- | ---------- |
| `+` (few concatenations) | Fast | Good | Simple, <3 parts |
| `String.format()` | Slow | Excellent | Debug output, formatted display |
| `StringBuilder.append()` | Fastest | OK | Loop building, complex logic |
| `MessageFormat` | Slow | Excellent | i18n, locale-sensitive messages |

---

## 15. Common Mistakes

```java
// Mistake 1: Using == for string comparison
String a = new String("hello");
String b = new String("hello");
if (a == b) { }              // WRONG — false even though equal content
if (a.equals(b)) { }        // CORRECT

// Mistake 2: NullPointerException from null check order
String s = null;
if (s.equals("hello")) { }  // NPE!
if ("hello".equals(s)) { }  // Safe — literal on left can't be null

// Mistake 3: String += in loop
String result = "";
for (int i = 0; i < 10000; i++) {
    result += "x";           // 10000 String objects created!
}
// Fix: StringBuilder

// Mistake 4: Regex compiled every time
for (String email : emails) {
    if (email.matches("[^@]+@[^@]+")) { } // Pattern compiled 10000 times!
}
// Fix: Precompile the Pattern
Pattern emailPattern = Pattern.compile("[^@]+@[^@]+");
for (String email : emails) {
    if (emailPattern.matcher(email).matches()) { }
}

// Mistake 5: new String("literal")
String s = new String("hello");  // wasteful! Creates new object when pool one exists
String s = "hello";             // Use literal; gets/creates pool entry

// Mistake 6: substring memory leak (Java 6 and earlier only)
// Java 7+ fixed this — substring creates new backing array

// Mistake 7: String.valueOf(null) and ("" + null)
System.out.println(String.valueOf(null));  // "null" (String) — no NPE
System.out.println("" + null);            // "null" (String) — no NPE
System.out.println((String) null);        // null (null reference — no NPE just null)
null.toString();                          // NPE!
```

---

## 16. Best Practices

1. **Always use `.equals()` for String comparison**, never `==`
2. **Put the literal on the left**: `"constant".equals(variable)` — prevents NPE
3. **Use `StringBuilder`** for string building in loops, not `+=`
4. **Precompile regex** with `Pattern.compile()` when used repeatedly
5. **Use `String.isEmpty()`** for empty check; **`String.isBlank()`** (Java 11+) for whitespace-only
6. **Use `char[]` for passwords**, not `String` — zero it out after use
7. **Use Text Blocks** (Java 15+) for multiline strings (SQL, JSON, HTML)
8. **Use `Objects.toString(obj, "default")`** for null-safe toString
9. **Avoid unnecessary `intern()`** — can cause StringTable memory pressure
10. **Use `String.join()` or `StringJoiner`** instead of manual delimiter logic
11. **Log with `{}` placeholders** (SLF4J), not `+` concatenation — lazy evaluation
12. **Initialize `StringBuilder` with expected capacity**: `new StringBuilder(expectedSize)`

---

## 17. Interview Section

### Easy

1. Is `String` a primitive type in Java?
2. What is String immutability? Why is String immutable?
3. What is the difference between `==` and `.equals()` for Strings?
4. What is the String pool?
5. What is the difference between `String`, `StringBuilder`, and `StringBuffer`?

### Medium

1. What is the output of: `"ab" + "cd" == "abcd"`? Explain.
2. Why is `new String("hello")` considered wasteful?
3. Explain String.intern(). When would you use it?
4. What changed in Java 9 with Compact Strings?
5. What is the time complexity of `String +=` in a loop?

### Hard

1. Explain how `String.hashCode()` is computed and why it uses the multiplier 31.
2. Why was the String pool moved from PermGen to Heap in Java 7?
3. Explain the memory leak with `String.substring()` in Java 6 and how it was fixed in Java 7.
4. What is the difference between `String.format()` and `MessageFormat`?
5. Why should you use `char[]` instead of `String` for passwords?

### Very Hard

1. How does `invokedynamic` with `StringConcatFactory` work in Java 9+ vs StringBuilder in Java 8?
2. Explain how Compact Strings work internally with the `coder` byte.
3. What is the default `StringTable` size in different JVM versions, and how does it affect performance?
4. How does JIT handle String deduplication with `-XX:+UseStringDeduplication`?

---

## 18. Coding Questions

### Easy

1. Reverse a String without using `StringBuilder.reverse()`.
2. Check if a String is a palindrome (case-insensitive).
3. Count the occurrences of each character in a String.
4. Remove all whitespace from a String.
5. Check if two Strings are anagrams of each other.

### Medium

1. Implement `String.indexOf(String target)` from scratch.
2. Find the longest common prefix of an array of Strings.
3. Given a sentence, reverse only the words (not characters): "Hello World" → "World Hello".
4. Implement a simple CSV parser that handles quoted fields.
5. Find the first non-repeating character in a String.

### Hard

1. Implement the Rabin-Karp rolling hash algorithm for substring search.
2. Implement LZW string compression and decompression.
3. Given a pattern and a string, implement wildcard pattern matching (`*` and `?`).
4. Find the longest palindromic substring (Manacher's algorithm).
5. Implement a `Trie` for autocomplete using String operations.

### Company Level

1. **Google:** Implement `String.split()` from scratch (handle edge cases: empty parts, trailing delimiter).
2. **Amazon:** Design a log parser that extracts structured data (timestamp, level, message) from log strings.
3. **LinkedIn:** Implement a search-highlight function: given text and keywords, wrap matches in `<b></b>` tags.
4. **Uber:** Given a template like "Order #{orderId} by {userName}", implement a variable-substitution engine.
5. **Netflix:** Build a multi-pattern string searcher (Aho-Corasick algorithm).

---

## 19. Production Scenarios

### Scenario 1: SQL Injection via String Concatenation

```java
// DANGEROUS — Production security bug
String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
// If userInput = "'; DROP TABLE users; --" → SQL INJECTION!

// CORRECT — Use PreparedStatement
PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE name = ?");
ps.setString(1, userInput);  // parameterized query — safe
```

### Scenario 2: Log String Concatenation Performance

```java
// BAD — String built even if DEBUG logging is disabled
log.debug("User " + userId + " request: " + requestBody + " response: " + responseBody);

// GOOD — String only built if DEBUG is enabled
log.debug("User {} request: {} response: {}", userId, requestBody, responseBody);
```

### Scenario 3: String Pool OutOfMemory (Java 6 era)

```
Problem: Legacy app loaded 10M unique product codes and called intern() on each.
PermGen (default 256MB) filled up with interned strings → OutOfMemoryError.
Fix (Java 6): Increase PermGen. Fix (Java 7+): Strings moved to heap; GC handles it.
```

### Scenario 4: Regex Performance in Parsing

```
Problem: An API validation framework used String.matches() inside a hot request loop.
String.matches() compiles the regex EVERY call.
Result: 200ms latency per request just for validation.
Fix: Precompile Pattern → 1ms per request. 200x improvement.
```

---

## 20. Internal Deep Dive

### String.hashCode() Algorithm

```java
// s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
// Why 31? It's prime. Also: 31 * x = (x << 5) - x → one shift + subtract (CPU efficient)
// Optimized bytecode:
int h = 0;
for (char c : value) {
    h = 31 * h + c;  // JIT: (h << 5) - h + c
}

// PROBLEM: "Aa" and "BB" have the same hashCode! (Hash collision exists)
// String HashMap bucket collision → falls back to equals() comparison
```

### String Deduplication (G1GC with -XX:+UseStringDeduplication, Java 8+)

```
When two different String objects have the same char[]/byte[] content,
G1GC can detect duplicates and make them share the same backing array.
The String objects themselves remain separate, but backing arrays are unified.
Memory savings: 10-20% in string-heavy applications.
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| `String` is a primitive | String is a `class` in `java.lang`, a reference type. NOT primitive. |
| String pool is PermGen | Java 6-: PermGen. Java 7+: **Heap**. |
| `==` works for Strings from pool | Only if both come from pool. `new String()` bypasses pool. Always use `.equals()`. |
| `substring()` causes memory leaks | Java 6 only — `substring` shared the backing array. Fixed in Java 7 with new array copy. |
| `String.format()` is fast | It's slow — uses `Formatter` internally with regex parsing. Avoid in hot loops. |
| `StringBuffer` is better | `StringBuilder` is FASTER for single-thread use (no sync overhead). Use StringBuffer ONLY for multi-thread. |
| Compact Strings use UTF-8 | Java 9 Compact Strings use **LATIN-1** (single byte, 0-255) or **UTF-16** — NOT UTF-8. |

---

## 22. Cheat Sheet

```
CREATION:
  "literal"          → String Pool (preferred)
  new String("x")   → Always new heap object (avoid)
  String.valueOf(x)  → Safe conversion from any type

COMPARISON:
  .equals()          → value comparison (use this!)
  .equalsIgnoreCase() → case-insensitive
  ==                 → reference comparison (DON'T use for content)

BUILDING:
  Small: String + (OK for <3 parts)
  Loops: StringBuilder.append() → O(n)
  Multi-thread: StringBuffer

KEY METHODS:
  length(), charAt(), indexOf(), lastIndexOf()
  substring(), split(), contains(), startsWith(), endsWith()
  toLowerCase(), toUpperCase(), trim(), strip() [Java11]
  replace(), replaceAll() [regex], replaceFirst()
  isEmpty(), isBlank() [Java11]
  repeat() [Java11], lines() [Java11]

TEXT BLOCK (Java 15+):
  """
  multiline content
  """

PERFORMANCE:
  ✓ StringBuilder in loops
  ✓ Precompile Pattern for regex
  ✓ SLF4J {} placeholders in logs
  ✗ += in loops (O(n²))
  ✗ matches() in loops (recompiles regex)

SECURITY:
  ✗ Passwords as String (stays in pool/memory)
  ✓ Passwords as char[] (zero with Arrays.fill)
  ✓ PreparedStatement for SQL (no string concat)
```

---

## 23. Mind Map

```
STRING
│
├── IMMUTABILITY
│   ├── Thread-safe
│   ├── Hashcode cacheable
│   ├── Security (paths, class names)
│   └── Every modification → new object
│
├── STRING POOL
│   ├── Literals → pool (heap, Java 7+)
│   ├── new String() → bypasses pool
│   ├── intern() → add/get from pool
│   └── == works ONLY within pool
│
├── INTERNAL (Java 9+)
│   ├── byte[] + coder (LATIN1 or UTF16)
│   ├── Compact Strings: 50% memory saving
│   └── hashCode cached (lazy compute)
│
├── BUILDING
│   ├── String +  → O(n²) in loops
│   ├── StringBuilder → O(n) amortized, not thread-safe
│   ├── StringBuffer → O(n), thread-safe
│   ├── String.join() → simple joining
│   └── StringJoiner → delimiter + prefix/suffix
│
├── KEY METHODS
│   ├── Java 11: strip, isBlank, lines, repeat
│   └── Java 15: Text Blocks
│
└── GOTCHAS
    ├── Always use .equals()
    ├── "constant".equals(var) → NPE-safe
    ├── Regex: precompile Pattern
    └── Passwords: use char[], zero it after
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| String immutability | String content cannot be changed; every "modification" returns a new String |
| String pool | JVM-maintained cache of string literals in the heap (Java 7+) |
| `intern()` | Forces string into pool and returns the pool reference |
| `==` vs `equals()` | `==` compares references; `.equals()` compares content — always use `.equals()` |
| StringBuilder | Mutable char sequence; O(n) append; NOT thread-safe |
| StringBuffer | Mutable char sequence; synchronized; thread-safe but slower than StringBuilder |
| Compact Strings | Java 9+ uses byte[] instead of char[]; 50% memory saving for ASCII |
| `String +=` in loop | Creates new String each iteration; O(n²) — always use StringBuilder instead |
| Text Blocks | Java 15+ multiline string literals using `"""` |
| `char[]` for passwords | char[] can be zeroed after use; String stays in pool exposing passwords |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"Pool = Library"** | String pool reuses books (strings); new String() prints a new copy |
| **"Immutable = New Book"** | Every modification prints a new book; original unchanged |
| **"SB = Whiteboard"** | StringBuilder is like a whiteboard — write, erase, modify freely |
| **"== for pool, .equals() always"** | `==` only reliable for pool strings; `.equals()` always correct |
| **"N squared for +="** | n string concatenations with += = n² operations |
| **"Regex costs → Precompile"** | Pattern.compile() once, matcher().matches() many times |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| Immutability | String's state cannot change after creation |
| String Pool / Intern Pool | JVM cache of string literals (on heap, Java 7+) |
| `intern()` | Moves string to pool; returns canonical reference |
| Compact Strings | Java 9+ byte[]-backed strings (LATIN1/UTF16) instead of char[] |
| `StringBuilder` | Non-synchronized mutable string builder |
| `StringBuffer` | Synchronized mutable string builder (legacy) |
| `String.join()` | Joins multiple strings with a delimiter |
| `StringJoiner` | Joins strings with delimiter + prefix + suffix |
| Text Block | Java 15+ multiline string with `"""` delimiters |
| String deduplication | G1GC option to share identical backing arrays across String objects |

---

## 27. Interview One-Liners

- "String is immutable because `final class` + `private final byte[] value` — no way to change internal bytes."
- "String pool is in the heap (Java 7+), not PermGen — GC can collect pool strings when unreferenced."
- "Always use `.equals()` for String comparison — `==` only works by coincidence within the pool."
- "`new String(\"hello\")` is wasteful — creates a new heap object bypassing the pool."
- "`StringBuilder` is for single-thread string building; `StringBuffer` is synchronized (slower, rarely needed)."
- "`String +=` in a loop is O(n²) — creates a new String object every iteration."
- "Compact Strings (Java 9+) use 1 byte per char for ASCII strings — 50% memory reduction."
- "Precompile regex patterns with `Pattern.compile()` — `String.matches()` recompiles every call."
- "Use `char[]` for passwords, not `String` — you can zero out `char[]`; String stays in memory pool."
- "`\"constant\".equals(variable)` is preferred over `variable.equals(\"constant\")` — avoids NPE."

---

## 28. Summary

`String` in Java is an immutable, final class backed by a `byte[]` (Java 9+ Compact Strings). Its immutability enables thread safety, secure key usage in HashMaps, and hashCode caching. The **String pool** in the heap (Java 7+) ensures memory efficiency for repeated literals. The most common pitfall is using `+=` for string building in loops — this is O(n²); always use `StringBuilder`. Regex operations via `matches()` / `replaceAll()` in loops compile the pattern every time — precompile with `Pattern.compile()`. For production security, never store passwords as `String` — use `char[]` and zero it immediately after use. Java 15+ Text Blocks solve multiline string readability; Java 11 added `strip()`, `isBlank()`, `repeat()`, `lines()`.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| Regular Expressions (java.util.regex) | Pattern, Matcher — essential for text validation, parsing |
| `java.text.MessageFormat` | Locale-sensitive formatting |
| `java.nio.charset` (Charset, CharsetEncoder) | Encoding strings to bytes for I/O |
| `StringTemplate` (Java 21 preview) | Modern string interpolation |
| `Collator` | Unicode-aware string comparison and sorting |
| Aho-Corasick algorithm | Multi-pattern string search used in production parsers |
| Guava's `Splitter`, `Joiner` | Production-grade String utilities beyond JDK |

---

---

# TOPIC 5: STATIC VARIABLE, METHOD, AND BLOCK

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | `static` is a modifier that makes a member (variable, method, block, or nested class) belong to the **class itself** rather than to any individual instance/object. |
| **Why introduced?** | To support class-level shared state and behaviour that doesn't depend on any instance — utilities, constants, factory methods, counters, singletons. |
| **Problem solved** | Without static, you'd need to create an object just to call a utility method or maintain a shared counter — wasteful and semantically wrong. |
| **History** | Java 1.0 (1996). Static import introduced in Java 5. |
| **Industry importance** | Every Spring `@Bean` factory method, every `Math.sqrt()`, every `System.out.println()`, `Logger.getLogger()`, `Collections.sort()` — all static. |

---

## 2. Intuition

Think of a class as a country, and objects as individual citizens.

- **Static variable** = a national currency — shared by ALL citizens. If you change it, everyone sees the change.
- **Static method** = a national law — applies regardless of which specific citizen you're talking about. You don't need a citizen to invoke national law.
- **Static block** = the constitution signing ceremony — happens ONCE when the country is founded (class loaded), never again.
- **Instance variable** = a person's wallet — each citizen has their own wallet with different amounts.
- **Instance method** = a personal action — "John eats", "Mary runs" — specific to the person (object).

---

## 3. Core Concepts

### 3.1 Static Variable

```java
public class Counter {
    static int count = 0;     // Shared by ALL instances
    int id;                   // Each instance has its own id
    
    Counter() {
        count++;              // increments shared counter
        this.id = count;      // each object gets unique id
    }
}

Counter c1 = new Counter();   // count = 1, c1.id = 1
Counter c2 = new Counter();   // count = 2, c2.id = 2
Counter c3 = new Counter();   // count = 3, c3.id = 3

// Accessing static via class name (preferred)
System.out.println(Counter.count);  // 3

// Accessing via instance (allowed but NOT recommended — misleading)
System.out.println(c1.count);       // 3 (same value — it's class-level!)
```

**Key properties of static variables:**

- Stored in **Metaspace** (class data area), NOT on the heap per-object
- Only ONE copy per class, shared by all instances
- Default-initialized like instance fields (0, null, false)
- Can be `public`, `private`, `protected`, or `final`
- Static final = compile-time constant: `static final int MAX = 100;`

### 3.2 Static Method

```java
public class MathUtils {
    // Static method — no instance needed
    public static int add(int a, int b) {
        return a + b;
    }
    
    // Static method calling another static method — OK
    public static double circleArea(double radius) {
        return Math.PI * square(radius);   // Math.PI is static field
    }
    
    private static double square(double n) {
        return n * n;
    }
}

// Call without creating an object
int sum = MathUtils.add(3, 4);           // 7
double area = MathUtils.circleArea(5.0); // 78.53...
```

**Rules for static methods:**

- Can ONLY access: static variables, other static methods (directly)
- CANNOT access: instance variables, instance methods, `this`, `super`
- Can be called from instance methods (instance has access to class)
- Cannot be overridden (no dynamic dispatch) — can be hidden in subclass

### 3.3 Static Block

```java
public class DatabaseConfig {
    static final String URL;
    static final int PORT;
    static final Properties config = new Properties();
    
    // Static initializer block
    static {
        System.out.println("Loading DB config...");
        try {
            config.load(DatabaseConfig.class.getResourceAsStream("/db.properties"));
            URL = config.getProperty("db.url");
            PORT = Integer.parseInt(config.getProperty("db.port"));
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);  // wraps checked exceptions
        }
        System.out.println("DB config loaded!");
    }
}
// static block runs ONCE when the class is first loaded by JVM
```

**Key properties:**

- Runs once at class loading time — before any object creation or static method call
- Runs in the ORDER they appear if multiple static blocks exist
- Can access only static members
- Can throw only unchecked exceptions (or wrap in `ExceptionInInitializerError`)
- If it throws, class fails to load → `ExceptionInInitializerError`

### 3.4 Static Nested Class

```java
public class Outer {
    private static int outerStatic = 100;
    private int outerInstance = 200;
    
    // Static nested class — does NOT hold reference to Outer instance
    static class StaticNested {
        void display() {
            System.out.println(outerStatic);     // OK — can access outer static
            // System.out.println(outerInstance); // ERROR — cannot access instance
        }
    }
    
    // Inner class (non-static) — holds implicit reference to Outer instance
    class Inner {
        void display() {
            System.out.println(outerStatic);     // OK
            System.out.println(outerInstance);   // OK — has outer instance reference
        }
    }
}

// Static nested class: no Outer instance needed
Outer.StaticNested nested = new Outer.StaticNested();

// Inner class: NEEDS Outer instance
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

### 3.5 Static Import (Java 5+)

```java
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.util.Collections.sort;

// Now use without class qualifier
double area = PI * r * r;          // instead of Math.PI
double root = sqrt(25);            // instead of Math.sqrt(25)
sort(list);                        // instead of Collections.sort(list)

// Avoid overusing — reduces clarity of where methods come from
// Best use: for constants (Math.PI → PI, Assert.assertEquals → assertEquals in JUnit)
```

---

## 4. Internal Working

### 4.1 Memory Location — Metaspace

```
JVM Memory:
├── Heap (GC-managed)
│   ├── Object instances (fields, arrays)
│   └── String Pool (Java 7+)
│
├── Metaspace (NOT GC-managed by default; grows as needed)
│   ├── Class metadata (bytecode, method info)
│   ├── Static variables ← HERE
│   └── Static final constants (may be inlined at compile time)
│
└── Stack (per-thread)
    └── Local variables, references to heap objects
```

### 4.2 Class Loading and Static Initialization Order

```java
class Example {
    static int a = 10;             // Step 1: Prepare (default 0), then assign 10
    static int b;
    
    static {
        b = a * 2;                 // Step 2: Static block runs (b = 20)
        System.out.println("static block 1");
    }
    
    static int c = b + 5;          // Step 3: c = 25
    
    static {
        System.out.println("static block 2, c = " + c); // Step 4
    }
}
```

**Exact initialization order:**

1. JVM loads the class
2. **Prepare phase:** All static variables set to defaults (0, null, false)
3. **Initialize phase** (top-to-bottom):
   - `a = 10`
   - static block 1: `b = 20`, prints "static block 1"
   - `c = 25`
   - static block 2: prints "static block 2, c = 25"

### 4.3 Object vs Class — Complete Initialization Order

```java
class Parent {
    static { System.out.println("1: Parent static block"); }
    { System.out.println("3: Parent instance block"); }
    Parent() { System.out.println("4: Parent constructor"); }
}

class Child extends Parent {
    static { System.out.println("2: Child static block"); }
    { System.out.println("5: Child instance block"); }
    Child() { System.out.println("6: Child constructor"); }
}

// new Child() output:
// 1: Parent static block         ← parent class loaded first
// 2: Child static block          ← child class loaded next
// 3: Parent instance block       ← instance init order: parent first
// 4: Parent constructor
// 5: Child instance block
// 6: Child constructor
```

### 4.4 Static vs Instance Method Dispatch

```java
class Animal {
    static void staticMethod() { System.out.println("Animal.staticMethod"); }
    void instanceMethod() { System.out.println("Animal.instanceMethod"); }
}

class Dog extends Animal {
    static void staticMethod() { System.out.println("Dog.staticMethod"); }
    @Override void instanceMethod() { System.out.println("Dog.instanceMethod"); }
}

Animal a = new Dog();
a.instanceMethod(); // "Dog.instanceMethod" — dynamic dispatch (runtime)
a.staticMethod();   // "Animal.staticMethod" — STATIC BINDING (compile-time)
// Static methods are HIDDEN not OVERRIDDEN!
```

---

## 5. Visual Flow

```
CLASS LOADING SEQUENCE (for 'new MyClass()' first time):

JVM starts
     ↓
Is MyClass loaded? NO
     ↓
ClassLoader reads MyClass.class
     ↓
Linking:
  Verify  → bytecode valid
  Prepare → static vars set to defaults (int→0, ref→null)
  Resolve → symbolic references resolved
     ↓
Initialize:
  Execute static initializers TOP → BOTTOM
  (static field assignments + static blocks interleaved)
     ↓
Class ready in Metaspace
     ↓
'new': allocate heap memory
     ↓
Instance initializers (top → bottom)
     ↓
Constructor runs
     ↓
Object reference returned

MEMORY PICTURE:
                Metaspace             Heap
               ┌──────────┐        ┌──────────┐
               │ MyClass  │        │ obj1     │
               │ count: 3 │◄───────│ id: 1    │
               │ MAX: 100 │        └──────────┘
               └──────────┘        ┌──────────┐
                                   │ obj2     │
               (shared by ALL)     │ id: 2    │
                                   └──────────┘
```

---

## 6. Syntax

```java
// Static variable
[access] static [final] type varName [= value];
static int count = 0;
static final int MAX = 100;     // constant: UPPER_SNAKE_CASE

// Static method
[access] static [final] returnType methodName(params) { }
public static int add(int a, int b) { return a + b; }

// Static block
static {
    // initialization code
}

// Multiple static blocks (run in order)
static { /* first */ }
static { /* second */ }

// Static nested class
static class Nested { }

// Static import
import static java.lang.Math.PI;
import static java.lang.Math.*;   // import all static members

// Accessing static members
ClassName.staticField
ClassName.staticMethod(args)
// Via instance reference (NOT recommended — misleading)
instanceRef.staticField   // works but avoid
```

---

## 7. Examples

### Basic

```java
public class Student {
    static int totalStudents = 0;   // class-level counter
    String name;
    int rollNo;
    
    Student(String name) {
        totalStudents++;             // shared counter
        this.name = name;
        this.rollNo = totalStudents;
    }
    
    static void displayTotal() {    // static method — no instance needed
        System.out.println("Total students: " + totalStudents);
    }
    
    public static void main(String[] args) {
        new Student("Krish");
        new Student("Rahul");
        new Student("Priya");
        
        Student.displayTotal();     // Total students: 3
        System.out.println(Student.totalStudents); // 3
    }
}
```

### Intermediate — Singleton with Static

```java
public class AppConfig {
    private static AppConfig instance;
    private static final Properties props = new Properties();
    
    // Static block for initialization
    static {
        try (InputStream is = AppConfig.class.getResourceAsStream("/app.properties")) {
            props.load(is);
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Cannot load app.properties: " + e);
        }
    }
    
    // Private constructor — no outside instantiation
    private AppConfig() {}
    
    // Thread-safe lazy singleton (double-checked locking)
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
    
    public String getProperty(String key) {
        return props.getProperty(key);
    }
}
```

### Advanced — Static Factory Method Pattern

```java
public class LocalDateTime {
    private final int year, month, day, hour, minute, second;
    
    // Private constructor
    private LocalDateTime(int year, int month, int day, int hour, int minute, int second) {
        // validate...
        this.year = year; this.month = month; this.day = day;
        this.hour = hour; this.minute = minute; this.second = second;
    }
    
    // Static factory methods — more expressive than constructors
    public static LocalDateTime now() {
        Calendar cal = Calendar.getInstance();
        return new LocalDateTime(
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
            cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
    }
    
    public static LocalDateTime of(int year, int month, int day) {
        return new LocalDateTime(year, month, day, 0, 0, 0);
    }
    
    public static LocalDateTime midnight(int year, int month, int day) {
        return new LocalDateTime(year, month, day, 0, 0, 0);
    }
}
// Usage: clear, self-documenting
LocalDateTime now = LocalDateTime.now();
LocalDateTime birthday = LocalDateTime.of(2003, 5, 15);
```

### Production — Spring Boot Static Usage

```java
// Utility class — static only, no instantiation
public final class ValidationUtils {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    
    private ValidationUtils() {  // private: prevent instantiation
        throw new UnsupportedOperationException("Utility class");
    }
    
    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("^[6-9]\\d{9}$");
    }
}

// Spring @Configuration with static inner class (for BeanFactoryPostProcessor)
@Configuration
public class AppConfig {
    // Must be static to be processed early in Spring lifecycle
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
```

---

## 8. Real World Usage

| Company/Framework | Static Usage |
| ------------------ | ------------- |
| **Java Standard Library** | `Math.PI`, `Math.sqrt()`, `System.out`, `Collections.sort()`, `Arrays.asList()` |
| **Spring Boot** | `@Bean` static factory methods, `BeanFactoryPostProcessor`, `ApplicationContextHolder` |
| **Log4j/SLF4J** | `static final Logger log = LoggerFactory.getLogger(MyClass.class)` — per-class singleton |
| **JUnit 5** | `@BeforeAll` annotated methods must be `static` — run once before all tests |
| **Singleton** | Spring beans default to Singleton scope — essentially one static-like instance |
| **Google Guava** | `ImmutableList.of()`, `Lists.newArrayList()` — all static factory methods |
| **Jackson** | `ObjectMapper` often stored as `static final` field |
| **JPA** | Named queries via static constants, `EntityManagerFactory` as static field |
| **Kafka** | `AdminClient.create()` static factory; topic names as static constants |

---

## 9. Internal JVM Perspective

### Static Variables in Metaspace

```
Java 8-:
  Static variables stored in PermGen (part of class metadata area)
  
Java 8+:
  Static variables stored in Metaspace alongside class metadata
  BUT: the VALUES of static reference types still point to heap objects
  
Example:
  static String name = "hello";
  → "name" field slot: stored in Metaspace (contains a reference)
  → "hello" string object: stored on heap (String Pool area)
```

### Class Initialization — Thread Safety

```
JVM guarantees class initialization is thread-safe:
- First thread to trigger class init acquires a lock on the class
- Other threads wait until initialization is complete
- This is why the "initialization-on-demand holder idiom" works:

class Singleton {
    private static class Holder {           // not loaded until getInstance() called
        static final Singleton INSTANCE = new Singleton();
    }
    public static Singleton getInstance() { return Holder.INSTANCE; }
}
// Thread-safe: JVM's class initialization lock ensures single creation
```

### static final and Constant Folding

```java
// static final primitives and Strings are inlined at compile time!
class Config {
    static final int MAX = 100;
    static final String PREFIX = "USER_";
}

// When you write:
if (x > Config.MAX) { }

// Compiler generates bytecode as if you wrote:
if (x > 100) { }
// Config.MAX is NOT read at runtime — it's inlined!
// This is why Gradle/Maven recommend 'api' vs 'implementation' dependencies
// for constants — changing a constant REQUIRES recompilation of all dependents
```

---

## 10. Time & Space Complexity

| Aspect | Complexity | Notes |
| -------- | ----------- | ------- |
| Static variable access | O(1) | Direct memory reference in Metaspace |
| Static method call | O(1) | No vtable lookup — direct invocation |
| Static block execution | O(cost of block code) | Runs once; amortized O(0) per use |
| Static variable memory | O(1) per class | Regardless of object count |
| Instance variable memory | O(n) | n copies for n objects |

**Memory savings example:**

```
10,000 User objects with:
  instance int age       → 10,000 × 4 bytes = 40,000 bytes
  static String APP_NAME → 1 reference (8 bytes) regardless of instance count
```

---

## 11. Advantages

- **Memory efficiency** — one copy regardless of how many objects
- **No object creation required** — utility methods callable without instantiation
- **Early initialization** — static blocks run at class load for expensive setup
- **Thread-safe initialization** — JVM guarantees single initialization
- **Constant inlining** — `static final` values inlined at compile time (performance)
- **Factory methods** — more expressive than constructors; can return subtypes, cache instances

---

## 12. Disadvantages

- **Cannot use `this` or `super`** — no instance context
- **Testing difficulty** — static methods hard to mock (need PowerMock or refactoring)
- **Hidden global state** — static mutable fields are global state → concurrency bugs
- **Memory not freed** — static variables live as long as the class is loaded (as long as JVM runs)
- **Static state breaks multithreading** — mutable static without synchronization = race conditions
- **Cannot override** — static methods are hidden, not overridden → polymorphism broken
- **Class coupling** — callers are tightly coupled to the concrete class (no interface)

---

## 13. Tradeoffs

| Decision | Use Static | Use Instance |
| ---------- | ----------- | ------------- |
| Utility methods | ✅ (no state needed) | Unnecessary object creation |
| Shared counter/state | ✅ (if intentionally shared) | Each object has own copy |
| Constants | ✅ `static final` | Never as instance variable |
| Business logic with dependencies | ❌ (hard to test/mock) | ✅ (inject dependencies, easier to test) |
| Singleton | ✅ `private static instance` | Not applicable |
| Factory method | ✅ | ❌ (would need an object to call it) |

---

## 14. Comparison

### static vs instance

| Aspect | Static | Instance |
| -------- | -------- | ---------- |
| Belongs to | Class | Object |
| Memory | Metaspace (one copy) | Heap (per object) |
| Access | Via class name (preferred) | Via object reference |
| `this` available | ❌ No | ✅ Yes |
| Overridable | ❌ (hidden, not overridden) | ✅ Yes (for non-final) |
| GC | Lives with class | GC'd when object unreachable |
| Thread safety | ⚠️ Must synchronize mutable static | Each object has own copy |

### Static Block vs Instance Block vs Constructor

| Aspect | Static Block | Instance Block | Constructor |
| -------- | ------------- | ---------------- | ------------- |
| Runs when | Class loaded (once) | Before every constructor | At object creation |
| Access | Static only | Instance + Static | Instance + Static |
| Can have parameters | No | No | Yes |
| Count | Multiple (in order) | Multiple (in order) | Multiple (overloaded) |
| `this()` call | No | No | Yes |

---

## 15. Common Mistakes

```java
// Mistake 1: Accessing instance member from static context
class MyClass {
    int x = 10;
    static void show() {
        System.out.println(x); // COMPILE ERROR: 'x' not static
    }
}

// Mistake 2: Mutable static = global state → race condition
class RequestTracker {
    static List<String> requests = new ArrayList<>(); // DANGER in multi-threaded server!
    // Fix: use thread-local or concurrent collection
    static List<String> safeRequests = Collections.synchronizedList(new ArrayList<>());
    // Better: ThreadLocal<List<String>>
}

// Mistake 3: Thinking static method is overridden
class Animal {
    static void speak() { System.out.println("..."); }
}
class Dog extends Animal {
    static void speak() { System.out.println("Woof"); }
}
Animal a = new Dog();
a.speak(); // "..." NOT "Woof"! Static method is HIDDEN, not overridden.

// Mistake 4: Forgetting final on static constants
class Config {
    static int MAX = 100;    // BAD: can be mutated from anywhere!
    static final int MAX = 100; // CORRECT: true constant
}

// Mistake 5: Heavy work in static initializer — delays class loading
static {
    // Don't make HTTP calls, DB connections in static blocks
    // They delay ALL usages of this class, impossible to handle failures gracefully
}

// Mistake 6: Static import overuse
import static com.myapp.utils.StringUtils.*;
import static com.myapp.utils.MathUtils.*;
// Now calling abs() and trim() — impossible to know which class they're from!
```

---

## 16. Best Practices

1. **Use `static final` for constants** — always UPPER_SNAKE_CASE
2. **Make static utility classes `final` with private constructor** — prevent instantiation and extension
3. **Prefer static factory methods over public constructors** (Effective Java Item 1)
4. **Avoid mutable static state** — it's global state and thread-unsafe
5. **Use `private static final Logger`** for loggers — class-level, one per class
6. **Keep static blocks minimal** — no I/O, no network calls; only simple initialization
7. **Use Initialization-on-demand Holder idiom** for lazy, thread-safe singletons
8. **Access static members via class name** — `ClassName.field`, not `instance.field`
9. **Use static import sparingly** — only for frequently used constants (JUnit assertions, Math constants)
10. **Synchronize access to mutable static fields** or use `AtomicInteger`, `AtomicReference`

---

## 17. Interview Section

### Easy

1. What is a static variable? How is it different from an instance variable?
2. Can a static method access instance variables? Why or why not?
3. When does a static block execute?
4. What is the difference between a static nested class and an inner class?
5. Can we have multiple static blocks in a class?

### Medium

1. What is the order of execution: static block, instance block, constructor?
2. Can we override a static method in Java? Explain with example.
3. What is static import? When should you use it?
4. Why does Spring recommend `private static final Logger` for loggers?
5. What happens if a static block throws an exception?

### Hard

1. Where are static variables stored in memory? (PermGen vs Metaspace)
2. Explain the Initialization-on-demand Holder idiom for Singleton and why it's thread-safe.
3. Why are `static final` primitives inlined at compile time? What's the implication for binary compatibility?
4. What is the difference between `static void method()` and `void method()`? How does JVM dispatch each?
5. Can a class be loaded and NOT have its static blocks run? (Hint: lazy vs eager loading)

### Very Hard

1. Explain how JVM guarantees thread-safe class initialization (class init lock).
2. What is static field shadowing vs instance field hiding vs method overriding? Compare all three.
3. How does `ExceptionInInitializerError` propagate, and what happens to the class state after it?
4. Describe memory implications of a class with many `static final String` constants that are never used.

---

## 18. Coding Questions

### Easy

1. Write a class `Counter` with a static count that increments with each instantiation.
2. Create a utility class `MathHelper` with `static isPrime(int n)` and `static factorial(int n)`.
3. Write a class that uses a static block to load a Map of country codes.
4. Demonstrate that static methods are not polymorphic with a Parent/Child class example.
5. Create a class with a `static final int[] FIBONACCI` array initialized in a static block.

### Medium

1. Implement a thread-safe Singleton using the Initialization-on-demand Holder idiom.
2. Implement a `static factory method` pattern for creating database connections with pooling.
3. Build a `Registry` class where objects register themselves using a static map on creation.
4. Demonstrate the difference between static method hiding and instance method overriding.
5. Write a class initialization order demonstration for: Parent static, Child static, Parent instance, Constructor.

### Hard

1. Implement a thread-safe in-memory cache as a static singleton with expiry.
2. Create a `StaticImportDemo` that uses static imports from your custom utility class safely.
3. Implement an event bus using static subscriber registration.
4. Simulate class loading order with a circular dependency scenario.
5. Implement a `StaticPool<T>` for reusable object instances (object pooling).

### Company Level

1. **Spring Boot:** Implement a simplified `ApplicationContext` with static bean registry.
2. **Kafka:** Design a static `TopicRegistry` for managing topic metadata.
3. **Amazon:** Build a static `FeatureFlagService` with hot-reload (atomic reference swap).
4. **Netflix:** Implement static circuit breaker state tracking across all service calls.
5. **Google:** Design a static `MetricsCollector` that aggregates stats per class.

---

## 19. Production Scenarios

### Scenario 1: Race Condition on Static Counter

```java
// Bug in multi-threaded server:
class RequestHandler {
    static int requestCount = 0;  // NOT thread-safe!
    
    void handleRequest() {
        requestCount++;  // Not atomic: read, increment, write — three operations!
        // Two threads can both read 100, both write 101 → count should be 102 but is 101
    }
}

// Fix 1: AtomicInteger
static AtomicInteger requestCount = new AtomicInteger(0);
requestCount.incrementAndGet();  // atomic

// Fix 2: synchronized
static synchronized void incrementCount() { requestCount++; }
```

### Scenario 2: Static Initializer Failure

```java
// Class fails to load if static block throws
class Config {
    static final String DB_URL;
    static {
        String url = System.getenv("DATABASE_URL");
        if (url == null) throw new RuntimeException("DATABASE_URL not set");
        DB_URL = url;
    }
}
// If DATABASE_URL not set: ExceptionInInitializerError wraps RuntimeException
// Every subsequent attempt to use Config throws NoClassDefFoundError!
// Fix: validate at startup in main(), not in static blocks
```

### Scenario 3: Memory Leak via Static Collection

```java
class CacheManager {
    static Map<String, Object> cache = new HashMap<>();  // STATIC — never GC'd!
    
    static void put(String key, Object value) {
        cache.put(key, value);   // keeps growing — MEMORY LEAK!
    }
}
// Fix: Use WeakHashMap, Guava Cache, or Caffeine with expiry
static Map<String, Object> cache = new WeakHashMap<>();  // allows GC of values
```

---

## 20. Internal Deep Dive

### Bytecode for Static vs Instance Method Call

```java
// Instance method call
obj.instanceMethod();
// Bytecode: invokevirtual → looks up vtable → dynamic dispatch

// Static method call
MyClass.staticMethod();
// Bytecode: invokestatic → direct call, no vtable → faster
```

### ExceptionInInitializerError Propagation

```
First access to class → static block runs → RuntimeException thrown
→ JVM wraps it in ExceptionInInitializerError
→ Class is marked as FAILED TO INITIALIZE
→ All subsequent accesses throw NoClassDefFoundError
→ Even if the root problem is fixed, you must restart the JVM!
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| Static method is "overridden" | Static methods are HIDDEN in subclasses, not overridden. Method hiding = compile-time; overriding = runtime. |
| Static variable is thread-safe | Static variables are shared — mutable statics need explicit synchronization |
| `static final` can't change | `static final` reference can't be reassigned, but the object it points to CAN be mutated (e.g., `static final List`!) |
| Static block runs on first method call | Static block runs when class is LOADED — which happens on first use of ANY static/instance member, or first `new` |
| Inner class = Static nested class | Inner class has implicit reference to outer instance; static nested class does NOT |
| Static import imports the class | `import static` imports specific static MEMBERS, not the whole class |

---

## 22. Cheat Sheet

```
STATIC KEYWORD:
  static field   → shared by all instances, stored in Metaspace
  static method  → class-level, no 'this', invokestatic bytecode
  static block   → runs ONCE at class load, in order
  static nested  → no reference to outer instance

INITIALIZATION ORDER:
  1. Parent static fields + blocks (top→bottom)
  2. Child static fields + blocks (top→bottom)
  3. [for each new object]:
     a. Parent instance fields + blocks
     b. Parent constructor
     c. Child instance fields + blocks
     d. Child constructor

STATIC METHOD RULES:
  ✓ Can call other static methods/fields directly
  ✗ Cannot use 'this' or 'super'
  ✗ Cannot access instance fields/methods directly
  ✗ Cannot be overridden (only hidden)

STATIC FINAL CONSTANTS:
  static final int MAX = 100;         // inlined at compile time
  static final List<String> LIST = new ArrayList<>();  // reference final, content mutable!

THREAD SAFETY:
  Mutable static → use AtomicXxx or synchronized
  static final + immutable → thread-safe
  Class initialization itself → JVM-guaranteed thread-safe
```

---

## 23. Mind Map

```
STATIC
│
├── STATIC VARIABLE
│   ├── Shared by ALL instances
│   ├── Stored in Metaspace
│   ├── Default initialized (0/null/false)
│   └── static final → constant (UPPER_SNAKE_CASE)
│
├── STATIC METHOD
│   ├── No 'this', no 'super'
│   ├── Can only access static members
│   ├── invokestatic bytecode (no vtable)
│   └── NOT overridden — HIDDEN in subclass
│
├── STATIC BLOCK
│   ├── Runs ONCE at class load
│   ├── Multiple: run in order
│   ├── Exception → ExceptionInInitializerError
│   └── Use for complex static field init
│
├── STATIC NESTED CLASS
│   ├── No outer instance reference
│   └── Can access outer static members
│
├── STATIC IMPORT
│   └── import static pkg.Class.member
│
└── MEMORY
    ├── Variables: Metaspace
    ├── static final primitives: inlined at compile
    └── Lives as long as class is loaded
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| Static variable | One copy per class, shared by all instances, stored in Metaspace |
| Static method | Class-level method; no `this`; cannot access instance members |
| Static block | Initialization code that runs once when class is loaded |
| Static final | Constant: value can't be changed after initialization; inlined at compile time |
| Static nested class | Nested class without reference to outer instance |
| Static import | Makes static members usable without class qualifier |
| Class hiding | Static method in subclass hides parent's static method (not overriding) |
| ExceptionInInitializerError | Thrown when static block fails; class marked broken until JVM restart |
| Initialization-on-demand Holder | Lazy, thread-safe singleton using nested static class |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"Static = Shared"** | One copy, all objects share it |
| **"Static goes to School (Metaspace)"** | Static data is class-level, stored in Metaspace |
| **"Block runs Once, Constructor runs n times"** | Static block: once; constructor: per new object |
| **"Hidden not Overridden"** | Static methods are HIDDEN in subclasses — compile-time binding |
| **"UPPER_SNAKE for Constants"** | `static final` constants always in UPPER_SNAKE_CASE |
| **"No this in Static"** | Static context has no object, so no `this` or `super` |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| `static` | Modifier indicating class-level (not instance-level) member |
| `static final` | Class-level constant; value fixed after initialization |
| Static initializer | `static { }` block; runs once at class load |
| Instance initializer | `{ }` block without static; runs before every constructor |
| `ExceptionInInitializerError` | Error thrown when static initializer fails |
| `invokestatic` | JVM bytecode instruction for calling static methods |
| `invokevirtual` | JVM bytecode for instance method calls (with vtable lookup) |
| Method hiding | Subclass static method with same signature replaces parent's (compile-time) |
| Metaspace | JVM memory region (Java 8+) storing class metadata and static fields |
| Singleton | Design pattern using private constructor + static instance |

---

## 27. Interview One-Liners

- "Static members belong to the class, not to any object — one copy shared by all instances."
- "Static methods cannot use `this` or `super` — no instance context."
- "Static blocks run once when the class is loaded, before any constructors."
- "Static methods are HIDDEN in subclasses, not OVERRIDDEN — compile-time binding, not runtime dispatch."
- "`static final` primitive constants are inlined at compile time — changing them requires recompilation of all callers."
- "Mutable static state is dangerous in multi-threaded environments — use `AtomicInteger`, `ConcurrentHashMap`."
- "If a static block throws, `ExceptionInInitializerError` is thrown — the class is permanently broken until JVM restart."
- "Static variables outlive all objects — they live as long as the class is loaded (JVM lifetime)."
- "JVM guarantees thread-safe class initialization — static blocks can't race."
- "Initialization-on-demand Holder idiom is the best way to implement lazy thread-safe Singleton."

---

## 28. Summary

The `static` keyword makes a member belong to the **class itself** rather than any object instance. Static variables are stored in Metaspace (Java 8+), shared by all instances, and live for the JVM lifetime. Static methods execute without object context — no `this`, no `super`, and they're NOT overridden (only hidden in subclasses). Static blocks run **once** at class loading time and are ideal for complex static initialization. The JVM guarantees thread-safe class initialization. Key production risks: mutable static state causes race conditions (use `AtomicXxx` or `synchronized`), and failed static initializers permanently corrupt the class (`ExceptionInInitializerError` → `NoClassDefFoundError` on subsequent access). Best use cases: constants, utility methods, singletons, loggers, factory methods, counters.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| Singleton pattern (all 5 ways) | Double-checked locking, Enum, Holder idiom |
| Class Loading and ClassLoader | Deep dive into when and how classes are loaded |
| Thread Safety fundamentals | How to safely work with static mutable state |
| `java.util.concurrent` | AtomicInteger, AtomicReference — thread-safe static alternatives |
| Spring singleton scope | Spring's default bean scope = one instance per ApplicationContext |
| Effective Java Item 1-5 | Static factory methods, builder — leverages static heavily |

---

---

# TOPIC 6: ENCAPSULATION

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | Encapsulation is the OOP principle of bundling data (fields) and the methods that operate on that data into a single unit (class), while **restricting direct access** to the internal state through access modifiers. |
| **Why introduced?** | To enforce information hiding — the internal implementation of a class should be private; only a well-defined interface should be public. This prevents unauthorized/accidental data corruption. |
| **Problem solved** | Without encapsulation, any code anywhere can directly modify an object's internal state (`account.balance = -99999`) — leading to data corruption, invalid states, and unmaintainable systems. |
| **History** | Core OOP concept since Simula (1967). Java enforced it from day one (1996) via access modifiers. JavaBeans specification (1996) formalized getters/setters pattern. |
| **4 pillars of OOP** | Encapsulation · Inheritance · Polymorphism · Abstraction |
| **Industry importance** | Every production-grade class uses encapsulation. Spring Beans, JPA Entities, DTOs, Value Objects — all encapsulated. It enables API evolution without breaking callers. |

---

## 2. Intuition

Think of a **vending machine**:

- You don't have direct access to the money inside or the mechanics
- You interact only through a defined interface: insert coin, press button, get item
- The internal state (stock levels, money count) is hidden and managed internally
- Invalid operations (pressing button without inserting coin) are rejected by the machine's own logic

This is exactly what encapsulation does for your class:

- **Fields are private** (you can't put your hand in the machine)
- **Methods are public** (buttons and coin slot — the allowed interface)
- **Internal validation** happens inside (the machine refuses invalid input)
- **State remains consistent** (you can't get items without paying)

---

## 3. Core Concepts

### 3.1 The Four Access Modifiers

```java
public class AccessDemo {
    public    int publicField    = 1;  // visible everywhere
    protected int protectedField = 2;  // visible to package + subclasses
              int packageField   = 3;  // visible to package only (default)
    private   int privateField   = 4;  // visible ONLY within this class
}
```

| Modifier | Same Class | Same Package | Subclass (other pkg) | Other Package |
| ---------- | ----------- | -------------- | ---------------------- | --------------- |
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| default (none) | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

### 3.2 What to Encapsulate

```java
public class BankAccount {
    // ENCAPSULATED — private state
    private String accountId;
    private double balance;
    private List<Transaction> history;
    private AccountStatus status;
    
    // PUBLIC INTERFACE — controlled access
    public void deposit(double amount) {
        validateAmount(amount);          // internal validation
        balance += amount;
        history.add(new Transaction(amount, "CREDIT"));
    }
    
    public void withdraw(double amount) {
        validateAmount(amount);
        if (amount > balance) throw new InsufficientFundsException();
        if (status == AccountStatus.FROZEN) throw new AccountFrozenException();
        balance -= amount;
        history.add(new Transaction(amount, "DEBIT"));
    }
    
    public double getBalance() { return balance; }
    
    // PRIVATE — internal implementation detail
    private void validateAmount(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
    }
}
```

### 3.3 Levels of Encapsulation

```
Strict Encapsulation Hierarchy:
┌─────────────────────────────────────────────┐
│ private  → best encapsulation               │ most restrictive
│ package-private → reasonable for internals  │
│ protected → for intended subclassers        │
│ public → part of the API contract           │ least restrictive
└─────────────────────────────────────────────┘
```

**Design principle:** Make everything as private as possible. Only promote visibility when genuinely needed. Every public member is a contract you must honour forever.

### 3.4 Encapsulation vs Information Hiding

| Concept | Meaning |
| --------- | --------- |
| **Encapsulation** | Bundling data + methods together in a class |
| **Information Hiding** | Restricting access to internal implementation details |
| **Relationship** | Information hiding is achieved THROUGH encapsulation. You encapsulate to hide. |

### 3.5 Tight Encapsulation Checklist

- [ ] All fields are `private`
- [ ] Mutable fields exposed only through validated setters
- [ ] No direct field access from outside the class
- [ ] Internal helper methods are `private`
- [ ] Defensive copies returned from getters that expose mutable objects
- [ ] Class invariants maintained by all public methods

---

## 4. Internal Working

### 4.1 Compiler Enforcement

```java
class Wallet {
    private double amount = 1000.0;
}

// In another class:
Wallet w = new Wallet();
w.amount = 5000.0;   // COMPILE ERROR: amount has private access in Wallet
double d = w.amount;  // COMPILE ERROR: amount has private access in Wallet
```

The Java compiler enforces access modifiers at **compile time** — not runtime. The JVM bytecode also checks (`IllegalAccessError` at runtime if someone uses reflection without permission).

### 4.2 Reflection Bypass (and How to Prevent)

```java
// Access private fields via reflection (bypass encapsulation!)
Field f = BankAccount.class.getDeclaredField("balance");
f.setAccessible(true);           // breaks encapsulation!
f.set(account, 999999.0);        // set private field

// Java 9+ Module System protection:
// If module-info.java declares the package as NOT open,
// even reflection setAccessible() fails with InaccessibleObjectException
// → Strong encapsulation at module level
```

### 4.3 Bytecode Level Access Checking

```java
// Java bytecode has access flags on fields:
// public  → ACC_PUBLIC
// private → ACC_PRIVATE
// protected → ACC_PROTECTED

// JVM verifier checks at class loading
// JVM also checks at invocation (getfield, putfield instructions)
// private members: only accessible from the declaring class
```

### 4.4 Package as Encapsulation Unit

```
src/
├── com.myapp.service/
│   ├── OrderService.java        (public class)
│   └── OrderValidator.java      (package-private — internal helper!)
├── com.myapp.repository/
│   ├── OrderRepository.java     (public interface)
│   └── CacheHelper.java         (package-private — not part of API)
└── com.myapp.model/
    ├── Order.java               (public — domain model)
    └── OrderLineItem.java       (package-private — internal to model package)
```

---

## 5. Visual Flow

```
WITHOUT ENCAPSULATION (Public fields — dangerous):

External Code          BankAccount
┌──────────────┐      ┌─────────────────────┐
│              │ ──►  │ balance = -99999     │ ← Anyone can set!
│ hacker.java  │ ──►  │ status = "INVALID"   │ ← No validation!
│              │      │ history = null       │ ← Can null out!
└──────────────┘      └─────────────────────┘
Result: CORRUPTED STATE

WITH ENCAPSULATION (Private fields + methods):

External Code          BankAccount (Encapsulated)
┌──────────────┐      ┌─────────────────────┐
│              │ ──►  │ + deposit(amount)    │ ← Validated entry
│ client.java  │ ──►  │ + withdraw(amount)   │ ← Rules enforced
│              │ ──►  │ + getBalance()       │ ← Read-only balance
└──────────────┘      │─────────────────────│
                      │ - balance            │ ← Hidden state
                      │ - history            │ ← Protected internals
                      │ - validateAmount()   │ ← Private implementation
                      └─────────────────────┘
Result: CONSISTENT, VALID STATE ALWAYS
```

---

## 6. Syntax

```java
// Encapsulated class template
public class EncapsulatedClass {
    
    // Fields: private (always)
    private type fieldName;
    private final type immutableField;  // final = set once in constructor
    
    // Constructor: initialize & validate
    public EncapsulatedClass(type param) {
        this.fieldName = validate(param);
    }
    
    // Public getter: expose data safely
    public type getFieldName() {
        return fieldName;               // for immutables
        // return new ArrayList<>(list); // defensive copy for mutables!
    }
    
    // Public setter: validate before changing
    public void setFieldName(type value) {
        if (value == null) throw new IllegalArgumentException("...");
        this.fieldName = value;
    }
    
    // Private helper: internal logic hidden
    private type validate(type input) {
        // validation logic
        return input;
    }
}
```

---

## 7. Examples

### Basic

```java
// BAD: no encapsulation
class PersonBad {
    public String name;     // anyone can set anything
    public int age;         // negative age allowed!
    public String email;    // invalid email allowed!
}

// GOOD: encapsulated
public class Person {
    private String name;
    private int age;
    private String email;
    
    public Person(String name, int age, String email) {
        setName(name);    // use setters even in constructor — single validation point
        setAge(age);
        setEmail(email);
    }
    
    public String getName() { return name; }
    
    public void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        this.name = name.trim();
    }
    
    public int getAge() { return age; }
    
    public void setAge(int age) {
        if (age < 0 || age > 150) throw new IllegalArgumentException("Invalid age: " + age);
        this.age = age;
    }
    
    public String getEmail() { return email; }
    
    public void setEmail(String email) {
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("Invalid email: " + email);
        this.email = email.toLowerCase().trim();
    }
}
```

### Intermediate — Defensive Copies

```java
import java.util.*;

public class Team {
    private String teamName;
    private final List<String> members;
    private final Date foundedDate;  // Date is mutable — dangerous!
    
    public Team(String name, List<String> members, Date foundedDate) {
        this.teamName = name;
        this.members = new ArrayList<>(members);  // defensive copy in constructor!
        this.foundedDate = new Date(foundedDate.getTime()); // defensive copy
    }
    
    // Returns defensive copy — caller cannot modify internal list
    public List<String> getMembers() {
        return Collections.unmodifiableList(members);  // immutable view
        // OR: return new ArrayList<>(members);        // actual copy
    }
    
    // Returns defensive copy — caller cannot mutate our date
    public Date getFoundedDate() {
        return new Date(foundedDate.getTime());  // copy, not original
    }
    
    // Add through controlled method — can validate/log
    public void addMember(String member) {
        if (member == null || member.isBlank()) throw new IllegalArgumentException();
        if (members.contains(member)) throw new IllegalStateException("Already a member");
        members.add(member);
    }
}
```

### Advanced — Immutable Class

```java
// Fully immutable, perfectly encapsulated
public final class Money {                    // final: cannot be subclassed
    private final BigDecimal amount;          // final: cannot be reassigned
    private final Currency currency;          // Currency is itself immutable
    
    public Money(BigDecimal amount, Currency currency) {
        if (amount == null || currency == null)
            throw new NullPointerException("amount and currency required");
        if (amount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Negative money not allowed");
        // BigDecimal is immutable — no defensive copy needed
        this.amount = amount.setScale(2, RoundingMode.HALF_EVEN); // normalize scale
        this.currency = currency;
    }
    
    // No setters — immutable! Return new object instead
    public Money add(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException("Currency mismatch");
        return new Money(this.amount.add(other.amount), this.currency);
    }
    
    public Money multiply(int factor) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)), this.currency);
    }
    
    public BigDecimal getAmount() { return amount; }     // BigDecimal is immutable — safe to return
    public Currency getCurrency() { return currency; }   // Currency is immutable — safe to return
    
    @Override public boolean equals(Object o) {
        if (!(o instanceof Money)) return false;
        Money m = (Money) o;
        return amount.compareTo(m.amount) == 0 && currency.equals(m.currency);
    }
    
    @Override public int hashCode() { return Objects.hash(amount, currency); }
    
    @Override public String toString() {
        return currency.getSymbol() + amount.toPlainString();
    }
}
```

### Production — JPA Entity with Encapsulation

```java
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String customerId;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderItem> items = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    
    protected Order() {}  // JPA requires no-arg; protected prevents misuse
    
    public Order(String customerId) {
        this.customerId = Objects.requireNonNull(customerId);
    }
    
    // Items managed through controlled methods — not direct list access
    public void addItem(OrderItem item) {
        if (status != OrderStatus.PENDING)
            throw new IllegalStateException("Cannot modify a confirmed order");
        items.add(Objects.requireNonNull(item));
    }
    
    public void confirm() {
        if (items.isEmpty()) throw new IllegalStateException("Cannot confirm empty order");
        this.status = OrderStatus.CONFIRMED;
    }
    
    // Unmodifiable view — JPA can still read, caller cannot mutate
    public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
    
    public OrderStatus getStatus() { return status; }
    public Long getId() { return id; }
    public String getCustomerId() { return customerId; }
    // No setStatus() — state transitions via domain methods only!
}
```

---

## 8. Real World Usage

| Company/Framework | Encapsulation |
| ------------------ | -------------- |
| **Spring Boot** | All `@Service`, `@Repository` classes have private fields injected via constructor/setter |
| **JPA Entities** | Fields private; lazy-loaded collections exposed via getters; state transitions via methods |
| **Project Lombok** | `@Data`, `@Getter`, `@Setter` — auto-generates encapsulation boilerplate |
| **Jackson** | Deserializes into private fields via getters/setters (JavaBeans convention) |
| **Google Guava** | `ImmutableList`, `ImmutableMap` — maximum encapsulation (final class, no mutators) |
| **Java standard library** | `String` — final class, private `byte[]` value — perfect encapsulation |
| **Domain-Driven Design** | Aggregate roots (like Order) control all mutations through domain methods |
| **Spring Security** | `UserDetails` implementation with private credential fields |

---

## 9. Internal JVM Perspective

### Access Check in JVM Bytecode

```
getfield  #2   // reads instance field
putfield  #2   // writes instance field

JVM verifier during class loading:
  For each getfield/putfield instruction:
  → Check calling class vs field's declaring class and access flags
  → private: only accessible from declaring class (same class in bytecode)
  → Violation: IllegalAccessError (runtime) or compile error (at source level)
```

### Java 9 Module System — Encapsulation at Scale

```
module com.myapp.core {
    exports com.myapp.core.api;      // only this package is accessible
    // com.myapp.core.internal is hidden — cannot be accessed by other modules
    // Even with reflection! (unless opens ... to ...)
}

// Strong encapsulation: modules enforce package-level privacy
// Used in JDK itself — sun.misc.Unsafe was previously accessible,
// now requires --add-opens JVM flag
```

---

## 10. Time & Space Complexity

| Aspect | Without Encapsulation | With Encapsulation |
| -------- | ---------------------- | ------------------- |
| Field access | O(1) direct | O(1) via getter |
| Setter validation | N/A | O(validation cost) |
| Defensive copy | N/A | O(n) for collections |
| Space overhead | None | Defensive copies use extra space |
| Maintenance cost | High (callers depend on internals) | Low (change internals freely) |

---

## 11. Advantages

- **Data integrity** — validation in setters prevents invalid states
- **Maintainability** — change internal implementation without breaking callers
- **Security** — private fields can't be tampered with externally
- **Flexibility** — add logging, caching, lazy loading to getters/setters later
- **Thread safety** — synchronized getters/setters easier to implement
- **Testability** — clear boundaries make unit testing easier
- **API evolution** — add fields, change storage format without affecting callers

---

## 12. Disadvantages

- **Boilerplate** — getters, setters, constructors for every field (mitigated by Lombok/Records)
- **Performance overhead** — method call vs direct field access (micro-optimization; JIT inlines anyway)
- **Over-engineering risk** — simple data transfer objects (DTOs) may not need full encapsulation
- **Anemic Domain Model antipattern** — all private fields + all public getters/setters = no real encapsulation (just field wrappers)
- **Defensive copies** — for mutable fields, returning copies has space/time cost

---

## 13. Tradeoffs

| Scenario | Approach |
| ---------- | --------- |
| Domain model (Order, Account) | Full encapsulation — state transitions via domain methods |
| DTO / Value Object | Immutable record or read-only class |
| Configuration class | Package-private fields OK within module |
| Utility class | All static, no fields at all |
| Test-only data class | Relaxed encapsulation acceptable |

---

## 14. Comparison

### Encapsulated vs Anemic Model

| Aspect | Encapsulated (Rich Domain Model) | Anemic Domain Model |
| -------- | ---------------------------------- | --------------------- |
| Fields | private | private |
| Setters | Validated, selective | Public setter for every field |
| Behaviour | Business logic in the class | No business logic — just data |
| State transitions | Through domain methods (confirm(), ship()) | External services call setters |
| Invariant protection | Enforced by class | Left to service layer |
| DDD alignment | ✅ Yes | ❌ Anti-pattern |

### Access Modifier Choices for Different Contexts

| Context | Recommended |
| --------- | ------------ |
| Fields (always) | `private` |
| Internal helper methods | `private` |
| Methods for subclassers | `protected` |
| Package-internal collaborators | default (package-private) |
| Public API | `public` (carefully!) |

---

## 15. Common Mistakes

```java
// Mistake 1: Anemic model — all setters, no behaviour
// Looks encapsulated but isn't
public class Order {
    private OrderStatus status;
    public void setStatus(OrderStatus status) { this.status = status; } // Anyone can do anything!
}
// Fix: use domain methods: confirm(), ship(), cancel() with business rules

// Mistake 2: Exposing mutable internals via getter
public class Team {
    private List<String> members = new ArrayList<>();
    public List<String> getMembers() { return members; } // Caller can: members.clear()!
    // Fix: return Collections.unmodifiableList(members)
}

// Mistake 3: Defensive copy only in getter, not constructor
public class Config {
    private final List<String> allowedHosts;
    public Config(List<String> hosts) {
        this.allowedHosts = hosts; // BUG! Caller can mutate original list after construction
    }
    // Fix: this.allowedHosts = new ArrayList<>(hosts);
}

// Mistake 4: Breaking encapsulation with public fields
public class Point {
    public int x, y; // Anyone can set x = -999999
    // Fix: private int x, y; with getters/setters or record
}

// Mistake 5: Protected field (common in inheritance abuse)
protected int balance; // Any subclass can directly access — weakens encapsulation
// Fix: private with protected getter
```

---

## 16. Best Practices

1. **All fields `private`** — no exceptions for production code
2. **Return defensive copies** for mutable fields (Date, List, arrays) in getters
3. **Accept defensive copies** of mutable parameters in constructors and setters
4. **Validate in setters/constructors** — fail fast with descriptive exception messages
5. **Prefer immutable classes** — no setters; all state set in constructor
6. **Use domain methods** for state transitions, not public setters
7. **Minimize public API surface** — every public member is a permanent contract
8. **Use `Collections.unmodifiableList()`** or return copies from collection getters
9. **Prefer Records** (Java 16+) for simple immutable data classes
10. **Use `Objects.requireNonNull()`** for null-checking constructor arguments
11. **Make classes `final`** if not designed for inheritance
12. **Use Lombok's `@Value`** for immutable POJOs to reduce boilerplate

---

## 17. Interview Section

### Easy

1. What is encapsulation in Java?
2. What are the four access modifiers in Java?
3. Why should fields be `private`?
4. What is the difference between `private` and `protected`?
5. What is a JavaBean? What conventions does it follow?

### Medium

1. What is the difference between encapsulation and information hiding?
2. What is an anemic domain model and why is it considered an antipattern?
3. Why should getters return defensive copies for mutable objects?
4. What is the difference between `protected` access and package-private access?
5. How does Java 9 module system improve encapsulation over packages?

### Hard

1. How does the JVM enforce access modifiers? (bytecode-level)
2. Can you break encapsulation using Java Reflection? How can you prevent it in Java 9+?
3. What is the difference between returning `Collections.unmodifiableList()` vs a new copy?
4. Explain why calling an overridable setter in a constructor can lead to bugs.
5. What is the difference between `private` fields in a class vs a Record?

### Very Hard

1. How does the Java module system (`module-info.java`) enforce stronger encapsulation than packages?
2. Explain how encapsulation enables binary compatibility across API versions.
3. How does JPA/Hibernate access private fields for ORM mapping?
4. What is the "published interface" concept and how does it relate to encapsulation?

---

## 18. Coding Questions

### Easy

1. Refactor a `public` field `Person.age` into a fully encapsulated version with validation.
2. Create an encapsulated `Rectangle` class that maintains the invariant `width > 0 && height > 0`.
3. Demonstrate the defensive copy pattern for a class with a `Date` field.
4. Create a `Stack<T>` class that encapsulates an internal `List<T>`.
5. Write a `Temperature` class that stores Celsius internally but exposes Fahrenheit via getter.

### Medium

1. Implement an immutable `Range` class (min, max) with `contains()`, `overlap()`, `union()` methods.
2. Implement a `BankAccount` with deposit, withdraw, and transaction history — fully encapsulated.
3. Create a `Configuration` class that loads from Properties file in a static block and exposes read-only access.
4. Design an encapsulated `Matrix` class with row/column bounds checking.
5. Implement an `ImmutableList<T>` wrapper that delegates to `ArrayList` but blocks all mutations.

### Hard

1. Design a fully immutable, thread-safe `CacheEntry<K,V>` with TTL expiry.
2. Implement a `SecurePassword` class that stores hashed password and exposes only `verify(raw)` method.
3. Design an encapsulated event sourcing `Account` class where all state changes are recorded as events.
4. Build a `Builder` for a complex `ReportConfig` object with 10+ fields, some required, some optional.
5. Implement a `Graph<T>` class that encapsulates adjacency list while exposing safe read-only traversal.

### Company Level

1. **Amazon:** Design an `Order` aggregate with full DDD encapsulation — items, status transitions, pricing.
2. **Google:** Build an immutable `JsonNode` class hierarchy.
3. **Spring:** Implement a `@ConfigurationProperties` style class that binds from properties file securely.
4. **Netflix:** Design a `CircuitBreakerState` that encapsulates state machine transitions safely.
5. **Banking:** Implement a `LedgerEntry` system where balance is derived (never stored directly).

---

## 19. Production Scenarios

### Scenario 1: Missing Validation Causes Production Bug

```
Problem: A User class had a public email field. A backend service was accidentally
setting email = null for batch users. Thousands of accounts had null email.
Cannot send password reset, cannot verify accounts.
Root cause: No private + validated setter. Anyone could set anything.
Fix: private String email with setEmail() that validates format.
Lesson: Production data integrity requires encapsulation.
```

### Scenario 2: Mutable Field Returned — External Mutation Bug

```java
// Produced in production
class ReportConfig {
    private List<String> columns = Arrays.asList("name", "email", "date");
    public List<String> getColumns() { return columns; } // returns internal list!
}

// Called from reporting engine:
List<String> cols = config.getColumns();
cols.add("password");   // OOPS — modified the internal list!
cols.add("ssn");        // sensitive fields added accidentally

// Fix:
public List<String> getColumns() {
    return Collections.unmodifiableList(columns);
}
```

### Scenario 3: Breaking Encapsulation with Reflection in Tests

```
Problem: Tests used reflection to set private fields directly (bypassing constructors).
When the field name was renamed in a refactoring, all tests broke silently (NoSuchFieldException).
Fix: Test via public API only. If testing private logic is needed, extract it to package-private method.
Lesson: Tests should respect encapsulation — they verify behaviour, not implementation.
```

---

## 20. Internal Deep Dive

### How Jackson/Spring Access Private Fields

```java
// Jackson uses reflection to access private fields:
Field field = MyClass.class.getDeclaredField("privateField");
field.setAccessible(true);   // bypasses access check
Object value = field.get(instance);

// Java 9+ modules block this unless explicitly opened:
// module-info.java:
//   opens com.myapp.model to com.fasterxml.jackson.databind;
// OR use @JsonProperty on getters/setters instead
```

### Record Encapsulation (Java 16+)

```java
record Point(int x, int y) {}
// Generated by compiler:
// private final int x;
// private final int y;
// public int x() { return x; }   ← accessor, not getter
// public int y() { return y; }
// public boolean equals(Object o) { ... }
// public int hashCode() { ... }
// public String toString() { ... }
// public Point(int x, int y) { this.x=x; this.y=y; } ← canonical constructor
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| Encapsulation = getters/setters | Getters/setters are ONE tool. True encapsulation means protecting invariants, not just wrapping fields in methods. |
| `protected` is safer than package-private | `protected` exposes to ALL subclasses anywhere in the codebase. Package-private is often BETTER (narrows exposure). |
| Encapsulation = immutability | Related but different. Encapsulation restricts access; immutability means no state change after creation. |
| All fields should have getters AND setters | Only expose what's needed. Many fields should be read-only (getter only) or write-once (set in constructor). |
| Encapsulation harms performance | JIT inlines trivial getters to direct field access — no runtime cost in practice. |

---

## 22. Cheat Sheet

```
ENCAPSULATION RULES:
  ✅ All fields: private
  ✅ Getters: return defensive copies for mutable types
  ✅ Setters: validate before assigning
  ✅ Constructors: copy mutable inputs, validate all
  ✅ Domain transitions: via named methods (confirm(), cancel())
  ❌ Never public/protected fields in production
  ❌ Never return internal mutable references
  ❌ Never set state directly without validation

ACCESS MODIFIERS:
  private   → class only        (MOST restrictive — use for fields)
  default   → package only      (good for package-internal helpers)
  protected → package + subs    (for intended extension points)
  public    → everywhere        (use sparingly — it's a contract)

DEFENSIVE COPIES:
  Constructor input: new ArrayList<>(input)
  Getter output: Collections.unmodifiableList(field)
              OR: new ArrayList<>(field)
  Date: new Date(date.getTime())

IMMUTABILITY (strongest encapsulation):
  - final class
  - private final fields
  - No setters
  - Defensive copies in constructor
  - Operations return new objects
```

---

## 23. Mind Map

```
ENCAPSULATION
│
├── ACCESS MODIFIERS
│   ├── private  → class only (fields always here)
│   ├── default  → package only
│   ├── protected → package + subclasses
│   └── public   → everywhere (minimize!)
│
├── TOOLS
│   ├── private fields
│   ├── Getters (controlled read)
│   ├── Setters (validated write)
│   ├── Domain methods (state transitions)
│   └── Defensive copies
│
├── LEVELS
│   ├── Class level (access modifiers)
│   ├── Package level (package-private)
│   └── Module level (Java 9+ module-info)
│
├── IMMUTABILITY (strongest form)
│   ├── final class
│   ├── private final fields
│   ├── No setters
│   └── Records (Java 16+)
│
└── VIOLATIONS
    ├── Public fields
    ├── Returning mutable refs
    ├── Anemic model (setters for everything)
    └── Reflection setAccessible()
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| Encapsulation | Bundling data + methods + restricting access to protect internal state |
| `private` | Most restrictive; accessible only within the declaring class |
| `protected` | Accessible within package AND all subclasses (anywhere in project) |
| Default (package-private) | No modifier; accessible only within the same package |
| `public` | Accessible everywhere; represents the API contract |
| Defensive copy | Create a new copy of mutable objects to prevent external mutation |
| Anemic model | Classes with only getters/setters and no behaviour — encapsulation antipattern |
| Immutable class | Class whose state cannot change after construction — maximum encapsulation |
| JavaBeans | Convention: `private` fields, `getXxx()` / `setXxx()` / `isXxx()` methods |
| Module encapsulation | Java 9+ `module-info.java` controls package-level visibility across modules |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"PPPD — Private, Protected, Package, Default"** | Access modifiers from most to least restrictive |
| **"Vending Machine"** | Encapsulation = machine hides internals, exposes only interface |
| **"Validate then Assign"** | In setters: validate first, modify field only if valid |
| **"Defensive Copy In and Out"** | Constructor: copy inputs; Getter: copy outputs (for mutables) |
| **"Public = Contract Forever"** | Every public member is a backward-compatibility promise |
| **"Private field + no setter = read-only"** | Immutable fields: private final + getter only |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| `private` | Most restrictive access — class only |
| `protected` | Package + subclass access |
| `public` | Universal access — part of API contract |
| Defensive copy | New copy of mutable object to prevent external mutation |
| Immutable class | Class that cannot be changed after creation |
| Invariant | Condition that must always be true about an object's state |
| Anemic domain model | Anti-pattern: classes with data but no behavior |
| JavaBeans | Convention for encapsulated Java classes (get/set/is methods) |
| Information hiding | Hiding implementation details behind an interface |
| Module system | Java 9+ feature for package-level encapsulation across JARs |

---

## 27. Interview One-Liners

- "Encapsulation bundles data and behaviour into a class while restricting direct external access via access modifiers."
- "All fields should be `private` — expose state only through validated getters and setters."
- "Return defensive copies from getters that expose mutable objects — otherwise callers can corrupt internal state."
- "`protected` exposes to all subclasses anywhere; package-private is often more conservative and better."
- "An anemic domain model has private fields with public getters/setters for everything — it's encapsulation in name only."
- "True encapsulation means state transitions happen through domain methods with business rules enforced."
- "Java 9 modules provide stronger encapsulation than packages — even reflection can be blocked."
- "Immutable classes achieve maximum encapsulation — no setters, all state set in constructor, copy on operation."
- "Calling overridable methods in constructors breaks encapsulation — child class method runs before child fields init."
- "Every `public` member is a contract — minimize public API surface."

---

## 28. Summary

Encapsulation is the OOP principle of **hiding internal state and requiring all interactions to go through a controlled interface**. In Java, this is achieved primarily through `private` fields combined with public methods (getters, setters, domain methods). The key insight beyond simple getters/setters is **protecting object invariants** — ensuring the object is always in a valid state. Defensive copies prevent external code from corrupting encapsulated mutable fields. Immutable classes (final class, private final fields, no setters) represent the strongest form of encapsulation. The anemic domain model antipattern — getters and setters for every field with no domain logic — achieves syntax of encapsulation without its benefits. Java 9 modules extend encapsulation to the package level across JARs. Encapsulation is what makes APIs maintainable — internal implementation can change freely without breaking callers.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| Getter and Setter (detailed) | Implementation patterns, Lombok, validation strategies |
| Immutable Objects (Effective Java Ch.4) | Immutability as the gold standard of encapsulation |
| Domain-Driven Design | Rich domain model vs anemic model |
| Java 9 Module System | Package-level encapsulation; `module-info.java` |
| Design by Contract | Preconditions, postconditions, invariants |
| Lombok | `@Data`, `@Value`, `@Builder` — encapsulation with less boilerplate |

---

---

# TOPIC 7: GETTER AND SETTER

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | Getters and setters are public methods that provide controlled read (`getXxx()`) and write (`setXxx()`) access to private fields of a class. Together with private fields they form the **JavaBeans** pattern. |
| **Why introduced?** | To enforce encapsulation while still allowing external code to read/write field values — but through a controlled interface that can include validation, logging, lazy loading, and access control. |
| **Problem solved** | Public fields allow uncontrolled, unvalidated direct access. Private fields with no accessors are inaccessible. Getters/setters provide the middle ground. |
| **History** | JavaBeans specification (Sun, 1996) formalized the naming convention. Spring, JPA, Jackson, and virtually every Java framework relies on this convention. |
| **Industry importance** | Jackson uses getters to serialize; JPA uses getters/setters for entity mapping; Spring binds request params to setters; Lombok generates them automatically. |

---

## 2. Intuition

Think of a **hotel room**:

- The room (object) has contents: a safe, minibar, thermostat (private fields)
- You (external code) can't directly reach in and grab things
- Instead: call the front desk (getter) to know the temperature: `getTemperature()`
- Call the front desk (setter) to change it: `setTemperature(22)` — they validate you're not setting 99°C!
- The hotel controls what you can access and what values are valid

**Getter** = front desk telling you current state (read-only, safe)
**Setter** = front desk accepting your request and validating it before making the change

---

## 3. Core Concepts

### 3.1 JavaBeans Naming Convention

```java
// Standard pattern for field 'name':
private String name;
public String getName()          { return name; }         // getter
public void   setName(String n)  { this.name = n; }      // setter

// For boolean field 'active':
private boolean active;
public boolean isActive()                { return active; }   // is-getter (not get!)
public void    setActive(boolean active) { this.active = active; }

// For Boolean (wrapper) field — can use either get or is:
private Boolean verified;
public Boolean getVerified()                { return verified; }  // preferred for wrapper
public void    setVerified(Boolean v)       { this.verified = v; }
```

> **Interview Trap:** For `boolean` primitives → `isXxx()`. For `Boolean` wrapper → either `isXxx()` or `getXxx()`. Jackson and Spring treat them differently — stick to `isXxx()` for primitive boolean.

### 3.2 Getter Types and Patterns

```java
// 1. Simple getter
public String getName() { return name; }

// 2. Derived/computed getter
public String getFullName() { return firstName + " " + lastName; }

// 3. Defensive copy getter (mutable fields)
public List<String> getTags() { return Collections.unmodifiableList(tags); }
public Date getCreatedAt()    { return new Date(createdAt.getTime()); }

// 4. Lazy initialization getter
private List<Permission> permissions;
public List<Permission> getPermissions() {
    if (permissions == null) {
        permissions = loadFromDB();   // load only when first requested
    }
    return Collections.unmodifiableList(permissions);
}

// 5. Optional getter (Java 8+) — signals field may be absent
private String middleName;
public Optional<String> getMiddleName() {
    return Optional.ofNullable(middleName);
}
```

### 3.3 Setter Types and Patterns

```java
// 1. Simple setter
public void setName(String name) { this.name = name; }

// 2. Validated setter
public void setAge(int age) {
    if (age < 0 || age > 150) throw new IllegalArgumentException("Invalid age: " + age);
    this.age = age;
}

// 3. Normalising setter
public void setEmail(String email) {
    if (email == null) throw new NullPointerException("email required");
    this.email = email.toLowerCase().trim();  // normalise on input
}

// 4. Fluent setter (builder-style — returns this)
public Person setName(String name) { this.name = name; return this; }
public Person setAge(int age)      { this.age = age;   return this; }
// Usage: person.setName("Krish").setAge(21).setEmail("k@example.com");

// 5. Copy-on-write setter (mutable fields)
public void setTags(List<String> tags) {
    this.tags = new ArrayList<>(tags);  // defensive copy — don't hold caller's list
}
```

### 3.4 When NOT to have Setters

```java
// Immutable value object — NO setters
public final class Coordinate {
    private final double lat;
    private final double lon;
    
    public Coordinate(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
    
    public double getLat() { return lat; }
    public double getLon() { return lon; }
    
    // Instead of setters, return new objects
    public Coordinate withLat(double lat) { return new Coordinate(lat, this.lon); }
    public Coordinate withLon(double lon) { return new Coordinate(this.lat, lon); }
}
```

### 3.5 Lombok — Eliminating Boilerplate

```java
import lombok.*;

// Generate all getters + setters + equals + hashCode + toString + required constructor
@Data
public class User {
    private Long id;
    private String username;
    private String email;
    private boolean active;
}

// Generate only getters (immutable-style)
@Getter
@AllArgsConstructor
public class ProductDTO {
    private final Long id;
    private final String name;
    private final BigDecimal price;
}

// @Value = @Getter + @AllArgsConstructor + final fields + equals/hashCode/toString
@Value
public class OrderId {
    String value;  // generates getValue(), all-arg constructor, immutable
}

// Fluent builder
@Builder
public class Order {
    private String customerId;
    private List<String> items;
    private BigDecimal total;
}
// Usage: Order.builder().customerId("C1").items(items).total(total).build();
```

---

## 4. Internal Working

### 4.1 How JVM Calls Getters/Setters

```
Source:   String name = person.getName();
Bytecode: aload_1              // push person reference
          invokevirtual #5     // call Person.getName()
          astore_2             // store result

Source:   person.setName("Krish");
Bytecode: aload_1              // push person reference
          ldc #3               // push "Krish"
          invokevirtual #6     // call Person.setName(String)
```

### 4.2 JIT Inlining of Trivial Getters

```java
// Source code:
int age = person.getAge();
// After JIT inlining (if getAge() is trivial and hot):
// Equivalent to: int age = person.age; (direct field read!)
// Performance impact: ZERO for simple getters — JIT eliminates the method call overhead
```

### 4.3 How Jackson Serializes Using Getters

```java
// Jackson ObjectMapper:
ObjectMapper mapper = new ObjectMapper();
String json = mapper.writeValueAsString(person);
// Jackson does:
// 1. Reflect on Person class
// 2. Find all getXxx() / isXxx() methods
// 3. Derive field names: getName() → "name", isActive() → "active"
// 4. Call each getter to get value
// 5. Write to JSON

// For deserialization (JSON → object):
// 1. Find all setXxx() methods
// 2. Call setters with JSON field values
// OR: @JsonProperty on fields + allow field access in mapper
```

### 4.4 How Spring Data JPA Uses Getters/Setters

```java
// JPA specification: entity state access can be field-based or property-based
// Property-based (getter access): @Id on getter
@Entity
public class User {
    private Long id;
    
    @Id  // annotation on GETTER → property-based access
    @GeneratedValue
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}
// Hibernate uses reflection to call getters for reading and setters for hydrating

// Field-based (more common, faster): @Id on field
@Entity
public class User {
    @Id  // annotation on FIELD → field-based access (Hibernate reads fields directly)
    @GeneratedValue
    private Long id;
    
    public Long getId() { return id; }  // getter still needed for other code
}
```

---

## 5. Visual Flow

```
GETTER FLOW:
External Code                    Object (Heap)
┌────────────────┐               ┌──────────────────┐
│ String n =     │ ─ getName() ─►│ return name      │
│ user.getName() │◄── "Krish" ──│ ← private String  │
└────────────────┘               │    name = "Krish" │
                                 └──────────────────┘

SETTER FLOW WITH VALIDATION:
External Code                    Object (Heap)
┌────────────────┐               ┌──────────────────┐
│ user.setAge(-5)│ ─ setAge(-5) ►│ if(age < 0)      │
│                │               │   throw Exception │
│ EXCEPTION! ◄──│◄── thrown ────│                  │
└────────────────┘               └──────────────────┘

┌────────────────┐               ┌──────────────────┐
│ user.setAge(21)│ ─ setAge(21) ►│ if(21 < 0) false │
│                │               │ this.age = 21    │
│ OK ◄──────────│◄── returns ───│                  │
└────────────────┘               └──────────────────┘

LAZY GETTER FLOW:
First call:  getPermissions() → permissions == null → loadFromDB() → cache → return
Second call: getPermissions() → permissions != null → return cached
```

---

## 6. Syntax

```java
// Standard getter naming
public ReturnType getFieldName() { return fieldName; }
public boolean isFieldName() { return fieldName; }   // boolean only

// Standard setter naming
public void setFieldName(Type fieldName) { this.fieldName = fieldName; }

// Validated setter template
public void setFieldName(Type value) {
    // Pre-condition checks
    Objects.requireNonNull(value, "fieldName cannot be null");
    if (/* invalid condition */) throw new IllegalArgumentException("...");
    // Normalisation (optional)
    value = normalise(value);
    // Assign
    this.fieldName = value;
}

// Fluent setter (returns this for chaining)
public ClassName setFieldName(Type value) {
    this.fieldName = value;
    return this;
}

// Lombok annotations
@Getter              // generates all getters
@Setter              // generates all setters
@Data                // @Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor
@Value               // @Getter + @AllArgsConstructor + final fields + @ToString + @EqualsAndHashCode
@Getter(AccessLevel.PROTECTED) // specific access level
@Setter(AccessLevel.NONE)      // suppress setter for one field with @Data
```

---

## 7. Examples

### Basic

```java
public class Employee {
    private String name;
    private double salary;
    private boolean permanent;
    
    // Getters
    public String getName()      { return name; }
    public double getSalary()    { return salary; }
    public boolean isPermanent() { return permanent; }  // 'is' for boolean
    
    // Setters
    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be blank");
        this.name = name.trim();
    }
    
    public void setSalary(double salary) {
        if (salary < 0) throw new IllegalArgumentException("Salary cannot be negative");
        this.salary = salary;
    }
    
    public void setPermanent(boolean permanent) { this.permanent = permanent; }
}
```

### Intermediate — Computed Getters, Optional, Defensive Copy

```java
import java.util.*;

public class Customer {
    private String firstName;
    private String lastName;
    private String phoneNumber;      // may be null
    private List<String> orderIds = new ArrayList<>();
    private Date registrationDate;
    
    // Computed getter — derived value, no backing field
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    // Optional getter — signals field may be absent
    public Optional<String> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }
    
    // Defensive copy — don't expose internal list
    public List<String> getOrderIds() {
        return Collections.unmodifiableList(orderIds);
    }
    
    // Defensive copy for mutable Date
    public Date getRegistrationDate() {
        return registrationDate == null ? null : new Date(registrationDate.getTime());
    }
    
    // Setter with defensive copy for mutable input
    public void setRegistrationDate(Date date) {
        this.registrationDate = (date == null ? null : new Date(date.getTime()));
    }
    
    // Controlled modification — not exposing the list
    public void addOrderId(String orderId) {
        if (orderId == null || orderId.isBlank()) throw new IllegalArgumentException();
        orderIds.add(orderId);
    }
}
```

### Advanced — Lombok + Validation + Builder

```java
import lombok.*;
import javax.validation.constraints.*;

@Getter
@EqualsAndHashCode(of = "userId")
@ToString(exclude = "passwordHash")
public class UserProfile {
    
    @NonNull
    private final String userId;
    
    @Setter(AccessLevel.NONE)  // read-only after creation
    private final String username;
    
    @Email
    @Setter  // settable with implicit @NonNull from Lombok if field annotated
    private String email;
    
    @Min(0) @Max(150)
    private int age;
    
    private String passwordHash;  // excluded from toString
    
    @Builder
    public UserProfile(String userId, String username, String email, int age) {
        this.userId   = Objects.requireNonNull(userId, "userId required");
        this.username = Objects.requireNonNull(username, "username required");
        setEmail(email);    // use setter for validation
        setAge(age);
    }
    
    // Custom validated setter for email
    public void setEmail(String email) {
        if (email != null && !email.contains("@"))
            throw new IllegalArgumentException("Invalid email: " + email);
        this.email = email == null ? null : email.toLowerCase().trim();
    }
    
    // Custom validated setter for age
    public void setAge(int age) {
        if (age < 0 || age > 150)
            throw new IllegalArgumentException("Invalid age: " + age);
        this.age = age;
    }
}

// Usage:
UserProfile u = UserProfile.builder()
    .userId("USR-001")
    .username("krish_dev")
    .email("krish@example.com")
    .age(21)
    .build();
```

### Production — Spring Boot REST Controller + JPA Entity

```java
// JPA Entity
@Entity
@Table(name = "products")
@Getter @Setter    // Lombok generates all getters/setters
@NoArgsConstructor // JPA required
@EqualsAndHashCode(of = "id")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)  // productCode is immutable after creation
    private String productCode;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(nullable = false)
    private boolean available = true;
    
    public Product(String productCode, String name, BigDecimal price) {
        this.productCode = Objects.requireNonNull(productCode);
        this.name        = Objects.requireNonNull(name);
        setPrice(price);   // validated setter
    }
    
    // Custom price setter with validation
    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Price must be non-negative");
        this.price = price.setScale(2, RoundingMode.HALF_EVEN);
    }
}

// DTO with Jackson
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    
    @JsonProperty("isAvailable")   // explicit JSON key name
    private boolean available;
}
```

---

## 8. Real World Usage

| Framework/Context | Getter/Setter Usage |
| ------------------ | --------------------- |
| **Jackson** | Serializes via `getXxx()` / `isXxx()`; deserializes via `setXxx()` |
| **Spring MVC** | `@ModelAttribute` binds HTTP params to setters; `@RequestBody` uses Jackson |
| **JPA/Hibernate** | Hydrates entities via setters (property access) or field access directly |
| **Spring Data** | Repository returns entities; service maps to DTOs using getters |
| **Lombok** | `@Data`, `@Getter`, `@Setter`, `@Builder`, `@Value` — industry standard |
| **MapStruct** | Generates mapping code using getters/setters between entities and DTOs |
| **Bean Validation** | `@NotNull`, `@Email` on getters trigger validation |
| **Spring Boot `@ConfigurationProperties`** | Binds YAML/properties to setters |

---

## 9. Internal JVM Perspective

### Getter/Setter Method Resolution

```
Person.getName() bytecode: invokevirtual
→ JVM looks up vtable for Person.getName
→ If trivial (one load + return), JIT inlines to direct field read
→ HotSpot: after ~10,000 calls, JIT compiles and inlines trivial accessors
→ Result: zero overhead compared to direct field access
```

### Memory Implications

```
Fields accessed via getters/setters:
→ No extra memory per accessor method (bytecode in Metaspace, shared)
→ Defensive copies in getters DO create extra heap objects
→ Strategy:
   - unmodifiableList() → no copy, just wrapper (tiny overhead)
   - new ArrayList<>()   → full copy (proportional to list size)
```

---

## 10. Time & Space Complexity

| Operation | Time | Notes |
| ----------- | ------ | ------- |
| Simple getter/setter | O(1) | JIT-inlined to field read/write |
| Validated setter | O(validation cost) | Usually O(1) for numeric checks |
| Defensive copy getter (List) | O(n) | n = list size |
| `unmodifiableList()` wrapper | O(1) | No copy — just wraps |
| Lazy getter (first call) | O(load cost) | DB read, file parse, etc. |
| Lazy getter (subsequent) | O(1) | Cached value returned |

---

## 11. Advantages

- **Controlled access** — validation, logging, lazy loading centralized in one place
- **Framework compatibility** — Jackson, JPA, Spring all need JavaBeans convention
- **Backward compatibility** — change internal storage without breaking callers
- **Lazy evaluation** — getter can compute/load value only when first requested
- **Thread safety** — getter/setter can be synchronized if needed
- **Interception** — AOP (Spring) can intercept getter/setter calls for transactions, auditing

---

## 12. Disadvantages

- **Boilerplate** — 2-3 lines per field × many fields = verbosity (solved by Lombok/Records)
- **Anemic model trap** — setters for everything → no business logic enforcement
- **Security illusion** — getter/setter for every private field is NOT encapsulation — it's just wrapping
- **Thread safety not automatic** — getter/setter pair is not atomic (read-modify-write race)
- **Serialization coupling** — Jackson, JAXB depend on naming conventions — renaming breaks serialization

---

## 13. Tradeoffs

| Decision | Use Getter/Setter | Use Alternative |
| ---------- | ------------------ | ----------------- |
| Mutable entity (JPA) | ✅ Standard getters + setters | — |
| Immutable value object | ✅ Getter only, no setters | Or use Records |
| Framework integration | ✅ Required (Jackson, JPA) | — |
| Domain state transitions | ❌ Use domain methods instead | `confirm()`, `cancel()` |
| Performance-critical | Use field access directly (if access modifier allows) | — |

---

## 14. Comparison

### Getter/Setter vs Public Field vs Record

| Aspect | Public Field | Getter/Setter | Record (Java 16+) |
| -------- | ------------- | --------------- | ------------------- |
| Validation | ❌ None | ✅ In setter | ✅ In compact constructor |
| Immutable possible | ❌ | ✅ (getter-only) | ✅ (by design) |
| Boilerplate | None | High (without Lombok) | None |
| Framework compat | Limited | ✅ Full | ✅ (with config) |
| Encapsulation | ❌ None | ✅ Full | ✅ Full |
| Inheritance | ✅ | ✅ | ❌ Records final |

### @Data vs @Value vs @Getter/@Setter (Lombok)

| Annotation | What it generates | Mutable? |
| ------------ | ------------------ | --------- |
| `@Data` | All getters, all setters, `toString`, `equals`, `hashCode`, required constructor | Yes |
| `@Value` | All getters, all-args constructor, `toString`, `equals`, `hashCode`, `final` fields | No (immutable) |
| `@Getter` | Only getters | Depends |
| `@Setter` | Only setters | Yes |
| `@Builder` | Builder inner class | Depends on fields |

---

## 15. Common Mistakes

```java
// Mistake 1: Calling mutable getter and modifying result
List<String> tags = product.getTags();  // returns internal list reference!
tags.add("HACKED");                     // modifies internal state — bug!
// Fix: return unmodifiableList or a new copy

// Mistake 2: Boolean getter with wrong name
private boolean active;
public boolean getActive() { return active; }  // WRONG — should be isActive()
// Jackson may not recognise 'getActive' for a boolean field in some configs

// Mistake 3: Setter without 'this.' causing field NOT to be set
public void setName(String name) {
    name = name.trim();  // sets local param, NOT the field!
    // this.name = name.trim();  // CORRECT
}

// Mistake 4: Not copying mutable parameters in setter
public void setTags(List<String> tags) {
    this.tags = tags;  // BUG! Caller mutates their list → internal state changes!
    // Fix: this.tags = new ArrayList<>(tags);
}

// Mistake 5: ThreadSafety illusion
// Even with synchronized getter AND setter, compound operations are not safe:
if (account.getBalance() > 0) {        // check
    account.withdraw(account.getBalance()); // act — race condition between check and act!
}
// Fix: make compound operations atomic at the domain method level

// Mistake 6: Renaming getter breaks JSON API
public String getUserName() { return username; }  // was getName() before
// Jackson now serializes as "userName" instead of "name" → API breaking change!
// Fix: @JsonProperty("name") public String getUserName() { return username; }
```

---

## 16. Best Practices

1. **Follow JavaBeans naming strictly**: `getXxx()`, `setXxx()`, `isXxx()` (boolean)
2. **Validate in setters, not in getters** — setters are the gatekeepers
3. **Return defensive copies** for mutable fields in getters
4. **Accept defensive copies** of mutable parameters in setters
5. **Use `Optional<T>` return type** for nullable fields (Java 8+)
6. **Call setters from constructors** — single validation point
7. **Use Lombok `@Data`/`@Builder`** to eliminate boilerplate; exclude sensitive fields from `@ToString`
8. **Use `@Setter(AccessLevel.NONE)`** in Lombok to make specific fields read-only
9. **Use domain methods** for state transitions instead of raw setters
10. **Add `@JsonProperty`** when getter name doesn't match desired JSON key
11. **Synchronize getters/setters** only when truly needed — prefer `AtomicXxx` or immutability
12. **Never expose internal mutable collections** directly — always wrap or copy

---

## 17. Interview Section

### Easy

1. What is a getter and a setter?
2. What is the naming convention for getters? For boolean fields?
3. Why use getters/setters instead of public fields?
4. What does Lombok's `@Data` annotation generate?
5. What is the difference between `getName()` and `isName()`?

### Medium

1. Why should setters make defensive copies of mutable parameters?
2. What is the difference between `Collections.unmodifiableList()` and returning `new ArrayList<>()`?
3. How does Jackson use getters and setters for JSON serialization?
4. What is a fluent setter? When would you use it?
5. What is the problem with calling overridable setter methods from a constructor?

### Hard

1. Explain how JPA/Hibernate uses getters vs field access. What determines which one?
2. Why is having public setters for every field considered an anti-pattern (anemic model)?
3. How do you implement a thread-safe getter for a lazily initialized field?
4. What happens to serialization if you rename a getter? How do you maintain backward compatibility?
5. How does `Optional` as a getter return type change the API design? What are its tradeoffs?

### Very Hard

1. Explain how JIT inlines trivial getters and why this makes getters zero-overhead in hot paths.
2. Design a getter/setter architecture for a class that needs change tracking for dirty checking (like Hibernate).
3. How would you implement copy-on-write semantics in a getter for a large shared collection?
4. Explain why `@Transactional` in Spring works on public methods accessed via getters but not via direct field access.

---

## 18. Coding Questions

### Easy

1. Write a `Rectangle` class with validated getters/setters ensuring `width > 0 && height > 0`.
2. Create a `Temperature` class with a Celsius field; add getters that return Fahrenheit and Kelvin too.
3. Write a `BooleanField` demo class showing difference between `isXxx()` and `getXxx()`.
4. Add lazy initialization to a getter that computes a Fibonacci sequence list.
5. Demonstrate the defensive copy pattern with a class containing a `Date` field.

### Medium

1. Build a `UserProfile` using Lombok `@Builder` with custom validated setters for email and age.
2. Implement a `ReadOnlyList<T>` wrapper class that exposes only getters and read methods.
3. Create an `AuditableEntity` base class where every setter records the old value and timestamp.
4. Implement a `Config` class with typed getters (`getInt()`, `getString()`, `getBoolean()`) backed by a `Properties` map.
5. Build a fluent API `QueryBuilder` class using fluent setters (returns `this`).

### Hard

1. Implement a dirty-checking mechanism: track which fields were changed via setters since last save.
2. Design a `SecureField<T>` generic class where `get()` requires passing a capability token.
3. Implement a copy-on-write `CowList<T>` that returns a new copy on write operations via getters.
4. Build a `VersionedBean` where each setter creates a new immutable version, keeping history.
5. Implement thread-safe lazy initialization using double-checked locking in a getter.

### Company Level

1. **Spring Boot:** Build a `@ConfigurationProperties`-style class that maps YAML to typed fields.
2. **JPA/Hibernate:** Implement a base `Auditable` entity with `createdAt`, `updatedAt` auto-set via setters.
3. **Jackson:** Design a DTO with `@JsonProperty`, `@JsonIgnore`, and custom serializers for sensitive fields.
4. **MapStruct:** Design source and target classes so MapStruct can auto-generate mappings using getters/setters.
5. **Netflix:** Implement a `FeatureConfig` class where getters A/B test: return value A or B based on user cohort.

---

## 19. Production Scenarios

### Scenario 1: Getter Returns Internal List — Caller Clears It

```
Problem: ProductService.getTopProducts() returned the internal cached list.
A developer accidentally called .clear() on the result.
The cache was cleared — next 10,000 requests went to the database.
Fix: return Collections.unmodifiableList(cache) or a new copy.
```

### Scenario 2: Renamed Getter Breaks JSON API

```java
// Before refactoring:
public String getUserName() { return username; } // JSON: "userName"

// After renaming:
public String getUsername() { return username; }  // JSON: "username" (lowercase u!)
// Mobile apps using "userName" key stopped working — API contract broken!

// Fix:
@JsonProperty("userName")
public String getUsername() { return username; }  // JSON still "userName"
```

### Scenario 3: Setter Param Shadowing — Silent Bug

```java
// Bug in production code
public void setDiscount(double discount) {
    discount = Math.min(discount, 1.0);  // modifies LOCAL param, NOT the field!
    // this.discount still unchanged → discounts never capped at 100%
}
// Fix: this.discount = Math.min(discount, 1.0);
```

### Scenario 4: Thread Safety — Get-Then-Set Race Condition

```java
// Cart service bug:
if (cart.getItemCount() < 10) {
    cart.setItemCount(cart.getItemCount() + 1);  // not atomic!
    // Two threads both read 9, both add 1, count = 10 but should be 11 (or throw)
}
// Fix: use AtomicInteger or synchronized block
private final AtomicInteger itemCount = new AtomicInteger(0);
```

---

## 20. Internal Deep Dive

### How Hibernate Hydrates Entities via Setters

```
Hibernate query executes → ResultSet returned
→ For each row:
   1. Instantiate entity via no-arg constructor (reflection)
   2. For each mapped column:
      a. Field access mode: field.set(entity, value) directly
      b. Property access mode: setXxx(value) via reflection
   3. Register entity in Persistence Context (1st level cache)
   4. Return populated entity to caller
```

### How MapStruct Generates Mapper Code

```java
// You write:
@Mapper
public interface ProductMapper {
    ProductDTO toDTO(Product product);
}

// MapStruct generates at compile time:
public class ProductMapperImpl implements ProductMapper {
    public ProductDTO toDTO(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());          // uses getter
        dto.setName(product.getName());      // uses getter
        dto.setPrice(product.getPrice());    // uses getter
        return dto;
    }
}
// Zero reflection — compile-time generated, type-safe, fast
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| `isActive()` vs `getActive()` | For `boolean` primitive → always `isActive()`. For `Boolean` wrapper → either works; prefer `isActive()` for consistency. |
| Getters/setters = encapsulation | Wrong — they're a TOOL for encapsulation. Having a setter for every private field with no validation is an anemic model — not real encapsulation. |
| Lombok `@Data` is always safe | `@Data` generates a setter for EVERY field. For immutable fields use `@Value` or add `@Setter(AccessLevel.NONE)`. |
| `unmodifiableList()` returns a copy | Wrong — it returns a VIEW (wrapper) around the original list. If the original changes, the view reflects it! For a true copy, use `new ArrayList<>()`. |
| Renaming a getter is safe refactoring | It changes the JSON key name → breaking API change. Always add `@JsonProperty`. |
| Calling setters in constructor is redundant | Calling setters in constructor centralizes validation — if validation logic changes, you update only the setter. |

---

## 22. Cheat Sheet

```
NAMING CONVENTION:
  private String name;
  public String getName()           // getter
  public void setName(String name)  // setter

  private boolean active;
  public boolean isActive()         // is-getter (NOT getActive)
  public void setActive(boolean v)  // setter

LOMBOK QUICK REFERENCE:
  @Getter          → all getters
  @Setter          → all setters
  @Data            → @Getter+@Setter+@ToString+@EqualsAndHashCode+@RequiredArgsConstructor
  @Value           → immutable @Data
  @Builder         → fluent builder
  @Setter(AccessLevel.NONE) → suppress setter for one field

VALIDATION TEMPLATE:
  public void setFieldName(Type value) {
    Objects.requireNonNull(value, "fieldName cannot be null");
    if (invalid) throw new IllegalArgumentException("reason");
    this.fieldName = normalise(value);
  }

DEFENSIVE COPY:
  Getter: return Collections.unmodifiableList(list); // wrap (no copy)
          return new ArrayList<>(list);               // actual copy
  Setter: this.list = new ArrayList<>(incomingList);  // own copy

GOTCHAS:
  ✗ 'this.' in setter body → shadow bug
  ✗ Exposing mutable internals in getter
  ✗ Renaming getter → breaks JSON API (@JsonProperty to fix)
  ✗ unmodifiableList != defensive copy
```

---

## 23. Mind Map

```
GETTER AND SETTER
│
├── GETTER
│   ├── getXxx() for all types
│   ├── isXxx() for boolean primitive
│   ├── Computed (derived) values
│   ├── Optional<T> for nullable fields
│   ├── Lazy initialization
│   └── Defensive copy (mutable types)
│
├── SETTER
│   ├── Validate first
│   ├── Normalise (trim, lowercase)
│   ├── Defensive copy of mutable inputs
│   ├── Fluent (returns this)
│   └── NONE for immutable fields
│
├── FRAMEWORKS
│   ├── Jackson: getXxx()/isXxx() → JSON key
│   ├── JPA: setter for hydration
│   ├── Spring: setXxx() for @ConfigurationProperties
│   └── MapStruct: compile-time getter/setter mapping
│
├── LOMBOK
│   ├── @Getter, @Setter
│   ├── @Data (full mutable bean)
│   ├── @Value (immutable bean)
│   └── @Builder
│
└── ANTI-PATTERNS
    ├── Missing 'this.' in setter
    ├── Returning mutable internals
    ├── Setter for everything (anemic)
    └── Renaming getter (breaks JSON)
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| Getter | Public method `getXxx()` / `isXxx()` providing read access to private field |
| Setter | Public method `setXxx()` providing validated write access to private field |
| JavaBeans | Convention: private fields + getXxx/setXxx/isXxx + no-arg constructor |
| `isXxx()` | Getter naming convention for `boolean` primitives (not `Boolean` wrapper) |
| Defensive copy (getter) | Return copy or unmodifiable view instead of internal mutable reference |
| Defensive copy (setter) | Store a copy of mutable parameter, not the caller's original object |
| Fluent setter | Setter that returns `this`, enabling method chaining |
| Lombok `@Data` | Generates all getters, setters, toString, equals, hashCode |
| Lombok `@Value` | Generates immutable bean: final fields, all getters, no setters |
| Anemic model | Anti-pattern: all getters + all setters, no domain logic — encapsulation in name only |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"is for boolean, get for rest"** | `isActive()` for primitive boolean; `getXxx()` for everything else |
| **"Validate then Assign"** | Setter always validates BEFORE touching `this.field` |
| **"this dot or it's lost"** | Always `this.fieldName = value` in setter — shadow bug without it |
| **"unmodifiable ≠ copy"** | `unmodifiableList` wraps; caller sees changes to original. `new ArrayList()` copies. |
| **"Rename getter = broken API"** | Jackson derives JSON key from getter name — renaming is a breaking change |
| **"Lazy = null-check inside getter"** | `if (field == null) { field = initialize(); } return field;` |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| JavaBeans | Java component standard requiring `private` fields + public `getXxx`/`setXxx`/`isXxx` |
| Accessor | General term for getter and setter methods |
| Mutator | Alternative term for setter method |
| Fluent setter | Setter returning `this` for method chaining |
| Defensive copy | New instance of mutable object to prevent external mutation |
| `Optional<T>` | Return type for getters that may return null, forcing callers to handle absence |
| `@JsonProperty` | Jackson annotation to override getter-derived JSON field name |
| Lazy initialization | Loading/computing field value only when first accessed via getter |
| Anemic domain model | Anti-pattern: POJOs with only data, no business logic |
| Lombok | Java annotation processor that auto-generates boilerplate code |

---

## 27. Interview One-Liners

- "Getters and setters follow JavaBeans convention: `getXxx()` / `isXxx()` for read, `setXxx()` for write."
- "Use `isXxx()` for `boolean` primitives — Jackson, Spring, and JPA expect this convention."
- "Setters should validate and normalise input before assigning to the field — they're the gatekeepers."
- "Always return defensive copies from getters that expose mutable fields — otherwise callers can corrupt internal state."
- "Lomboks `@Data` generates all boilerplate; `@Value` generates an immutable version — no setters."
- "Calling an overridable setter in a constructor is dangerous — child method runs before child fields are initialized."
- "`Collections.unmodifiableList()` wraps without copying — the original can still change. Use `new ArrayList<>()` for true isolation."
- "Renaming a getter changes the Jackson-derived JSON key — always add `@JsonProperty` to maintain backward compatibility."
- "JPA can access entity state via fields (field-based) or getters (property-based) — annotation position on `@Id` determines which."
- "A class with getters and setters for every field but no domain logic is an anemic model — not real encapsulation."

---

## 28. Summary

Getters and setters are the standard Java mechanism for providing controlled read and write access to private fields following the **JavaBeans convention** (`getXxx()`/`isXxx()`/`setXxx()`). They are the foundation upon which Jackson, JPA, Spring, and MapStruct all depend. The key value of setters is **validation and normalization** — they are the gatekeepers of object state. Getters must return **defensive copies** for mutable types to prevent external corruption. The biggest antipattern is the **anemic domain model** — public getters and setters for every field with no business logic provides the syntax of encapsulation without its benefits. Lombok eliminates boilerplate entirely with `@Getter`, `@Setter`, `@Data`, `@Value`, and `@Builder`. Java 16 Records provide an even cleaner alternative for immutable data classes. Common production bugs: missing `this.` in setter body, returning internal mutable collections, and renaming getters breaking JSON serialization.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| Lombok (deep dive) | `@Data`, `@Builder`, `@Value`, `@Slf4j` — essential for productivity |
| Records (Java 16+) | Zero-boilerplate immutable data classes — successor to getter-only JavaBeans |
| MapStruct | Compile-time getter/setter-based object mapping |
| Jackson advanced | `@JsonProperty`, `@JsonIgnore`, custom serializers, `@JsonCreator` |
| Bean Validation (JSR-380) | `@NotNull`, `@Email`, `@Min` on getters — declarative validation |
| Domain-Driven Design | Rich domain model — when to use domain methods vs setters |

---

---

# TOPIC 8: THE `this` KEYWORD

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | `this` is a reference variable in Java that always refers to the **current object** — the instance on which the method or constructor is currently executing. |
| **Why introduced?** | To disambiguate between instance fields and local variables/parameters with the same name, and to enable objects to refer to themselves, pass themselves as arguments, and chain constructors. |
| **Problem solved** | Without `this`, there's no way to distinguish `name` (field) from `name` (parameter) inside a method. Also needed for constructor chaining to reduce duplication. |
| **History** | Java 1.0 (1996) — inherited concept from C++ (`this` pointer). Java's `this` is a reference, not a raw pointer. |
| **Industry importance** | Used in virtually every setter, builder, constructor chain, and fluent API. It's a subtle keyword with multiple distinct uses. |

---

## 2. Intuition

**`this` = "me" in plain English.**

When you write instance methods, each method runs _on behalf of a specific object_. `this` is how that object refers to itself.

Imagine you're a chef (object). When a customer calls `chef.cook("pasta")`, inside `cook()`:

- `this` = you, the specific chef executing the method
- `this.speciality` = your own speciality (instance field), not someone else's
- If the restaurant calls `chef.cook(chef)`, passing `this` as an argument = "cook by yourself"

In a builder:

- Each method `setIngredient("tomato")` returns `this` = returns the same chef = you can keep calling methods on the same object: `chef.setIngredient("tomato").setIngredient("cheese").serve()`

---

## 3. Core Concepts

### 3.1 The Five Uses of `this`

```
this keyword uses:
1. Disambiguate field vs parameter (same name)
2. Refer to current object (pass as argument)
3. Constructor chaining — this(args)
4. Return current object from method (fluent APIs)
5. Call a method on current object (optional but explicit)
```

### 3.2 Use 1 — Field vs Parameter Disambiguation

```java
public class Person {
    private String name;  // field
    private int age;      // field
    
    public void setName(String name) {     // parameter also named 'name'
        // WITHOUT this: name = name;  → assigns param to itself! Field unchanged!
        this.name = name;               // this.name = field, name = parameter
    }
    
    public void setAge(int age) {
        this.age = age;                 // field = parameter
    }
    
    // When NO naming conflict: 'this.' is optional (but helps clarity)
    public String getName() {
        return this.name;   // same as just 'name' — both refer to field
    }
}
```

### 3.3 Use 2 — Passing Current Object as Argument

```java
public class Node {
    private int value;
    private NodeRegistry registry;
    
    public Node(int value, NodeRegistry registry) {
        this.value = value;
        this.registry = registry;
        registry.register(this);  // passing this node to the registry
    }
    
    public void addToList(List<Node> list) {
        list.add(this);  // adding current object to a list
    }
}

// Observer pattern — registering self as listener
public class EventHandler implements Listener {
    public EventHandler(EventBus bus) {
        bus.subscribe(this);  // 'this' EventHandler subscribes to bus
    }
    
    @Override
    public void onEvent(Event e) { /* handle */ }
}
```

### 3.4 Use 3 — Constructor Chaining with `this()`

```java
public class Connection {
    private String host;
    private int port;
    private int timeout;
    private boolean ssl;
    
    // Most specific constructor
    public Connection(String host, int port, int timeout, boolean ssl) {
        this.host    = host;
        this.port    = port;
        this.timeout = timeout;
        this.ssl     = ssl;
    }
    
    // Delegates to 4-arg constructor (defaults timeout=30, ssl=false)
    public Connection(String host, int port) {
        this(host, port, 30, false);  // MUST be first statement!
    }
    
    // Delegates to 2-arg constructor (default port=3306)
    public Connection(String host) {
        this(host, 3306);  // chains to Connection(host, port)
    }
    
    // No-arg: defaults everything
    public Connection() {
        this("localhost");  // chains to Connection(host)
    }
}
// Chain: Connection() → Connection("localhost") → Connection("localhost", 3306)
//                     → Connection("localhost", 3306, 30, false)
```

> **Critical Rule:** `this()` must be the **very first statement** in a constructor body. You cannot call it after any other statement.

### 3.5 Use 4 — Return `this` for Fluent APIs (Builder Pattern)

```java
public class QueryBuilder {
    private String table;
    private String condition;
    private int limit;
    private List<String> columns = new ArrayList<>();
    
    // Each method modifies state and returns 'this' — enables chaining
    public QueryBuilder from(String table) {
        this.table = table;
        return this;   // return current object
    }
    
    public QueryBuilder where(String condition) {
        this.condition = condition;
        return this;
    }
    
    public QueryBuilder select(String... cols) {
        this.columns.addAll(Arrays.asList(cols));
        return this;
    }
    
    public QueryBuilder limit(int n) {
        this.limit = n;
        return this;
    }
    
    public String build() {
        return "SELECT " + String.join(",", columns) +
               " FROM " + table +
               (condition != null ? " WHERE " + condition : "") +
               (limit > 0 ? " LIMIT " + limit : "");
    }
}

// Usage — clean, readable:
String sql = new QueryBuilder()
    .from("users")
    .select("id", "name", "email")
    .where("active = true")
    .limit(10)
    .build();
// "SELECT id,name,email FROM users WHERE active = true LIMIT 10"
```

### 3.6 Use 5 — Explicit Method Call on Current Object

```java
public class Account {
    private double balance;
    
    public void deposit(double amount) {
        validate(amount);     // implicit this.validate(amount) — same object
        this.balance += amount;
        this.notifyListeners("deposit", amount);  // explicit this — same effect
    }
    
    private void validate(double amount) {
        if (amount <= 0) throw new IllegalArgumentException();
    }
    
    private void notifyListeners(String type, double amount) {
        // notification logic
    }
}
```

### 3.7 `this` Cannot Be Used in Static Context

```java
public class Demo {
    private int value = 42;
    
    public void instanceMethod() {
        System.out.println(this.value);   // OK — 'this' refers to current object
        System.out.println(this);         // prints result of toString()
    }
    
    public static void staticMethod() {
        // System.out.println(this.value); // COMPILE ERROR — no 'this' in static!
        // Static context has NO associated object → no 'this'
    }
}
```

---

## 4. Internal Working

### 4.1 How JVM Implements `this`

```
When a method is called on an object:
  account.deposit(500);
  
JVM pushes the arguments onto the operand stack:
  Slot 0 (implicit): reference to 'account' object → this
  Slot 1: argument 500

Inside deposit(double amount):
  Local variable table:
  ┌────┬─────────────────────────────────────────┐
  │ 0  │ this (reference to account on heap)     │
  │ 1  │ amount (500.0 — the parameter)          │
  └────┴─────────────────────────────────────────┘
  
'this.balance' compiles to:
  aload_0        // load 'this' from slot 0
  getfield #2    // read 'balance' field from the object
```

### 4.2 `this()` Compiler Rules

```java
// this() MUST be first statement:
public MyClass(int a) {
    System.out.println("before");  // COMPILE ERROR if this() follows
    this(a, 0);                    // ERROR: must be first!
}

// CORRECT:
public MyClass(int a) {
    this(a, 0);                    // first statement
    System.out.println("after");   // OK — can be here
}

// Cannot call both this() and super() — only one allowed, first:
public Child(int a) {
    this(a, 0);    // calls another Child constructor
    super();       // COMPILE ERROR — already called this()
}
```

### 4.3 `this` Reference Value

```java
public class Counter {
    private int count;
    
    public Counter getThis() {
        return this;  // returns the object reference itself
    }
    
    public static void main(String[] args) {
        Counter c = new Counter();
        Counter ref = c.getThis();
        System.out.println(c == ref);   // true — same object!
        System.out.println(c.getThis() == c.getThis()); // true — same object each time
    }
}
```

### 4.4 Bytecode for `this`

```java
// Source:
public void setName(String name) {
    this.name = name;
}

// Bytecode (javap -c):
public void setName(java.lang.String);
  Code:
    0: aload_0        // load 'this' (slot 0 = always 'this' in instance methods)
    1: aload_1        // load 'name' parameter (slot 1)
    2: putfield #2    // set field 'name' on the object loaded in step 0
    5: return
```

---

## 5. Visual Flow

```
INSTANCE METHOD EXECUTION — HOW 'this' EXISTS:

Java code: account.deposit(500.0);

JVM Stack Frame for deposit():
┌────────────────────────────────────────────┐
│ Local Variable Table                       │
│ Slot 0: this = (ref) ────────────────────►│ BankAccount object (Heap)
│ Slot 1: amount = 500.0                    ││ ┌─────────────────────┐
│                                           │└►│ balance: 1000.0     │
│ Operand Stack                             │  │ owner: "Krish"      │
│ [ ]                                       │  └─────────────────────┘
└────────────────────────────────────────────┘

'this.balance += amount':
  aload_0  → push Slot 0 (the account object reference)
  dup      → duplicate (one for getfield, one for putfield)
  getfield balance → reads 1000.0 from object
  dload_1  → push 500.0
  dadd     → 1500.0
  putfield balance → stores 1500.0 back into object

CONSTRUCTOR CHAINING FLOW:
Connection()
  → this("localhost")       ← this() call = redirect to another constructor
      → this("localhost", 3306)
          → this("localhost", 3306, 30, false)
              → [actual initialization of all 4 fields]
          ← returns
      ← returns
  ← returns
← instance created with host="localhost", port=3306, timeout=30, ssl=false
```

---

## 6. Syntax

```java
// 1. Field disambiguation
this.fieldName = parameter;

// 2. Pass current object as argument
method(this);
list.add(this);
return this;

// 3. Constructor chaining — MUST be first statement
this();                    // calls no-arg constructor of same class
this(arg1);               // calls single-arg constructor
this(arg1, arg2, ...);    // calls matching constructor

// 4. Return this (fluent API)
public ClassName methodName(Type param) {
    this.field = param;
    return this;
}

// 5. Explicit method call (optional)
this.methodName(args);

// 6. Print this (calls toString())
System.out.println(this);

// INVALID USES:
// static void method() { this.field; }  // ERROR: no 'this' in static
// this = new Object();                   // ERROR: 'this' is not reassignable
```

---

## 7. Examples

### Basic — Disambiguation

```java
public class Box {
    private double length;
    private double width;
    private double height;
    
    public Box(double length, double width, double height) {
        // All three params shadow the fields — must use 'this.'
        this.length = length;
        this.width  = width;
        this.height = height;
    }
    
    public double getVolume() {
        return this.length * this.width * this.height;  // 'this.' optional here
        // 'return length * width * height;' is equivalent — no local var shadows fields
    }
    
    @Override
    public String toString() {
        return "Box[" + length + "x" + width + "x" + height + "]"; // 'this.' not needed
    }
}
```

### Intermediate — Constructor Chaining

```java
public class HttpRequest {
    private final String method;
    private final String url;
    private final Map<String, String> headers;
    private final String body;
    private final int timeoutMs;
    
    // Primary constructor — all fields
    public HttpRequest(String method, String url,
                       Map<String, String> headers, String body, int timeoutMs) {
        this.method    = Objects.requireNonNull(method, "method required");
        this.url       = Objects.requireNonNull(url, "url required");
        this.headers   = headers != null ? new HashMap<>(headers) : new HashMap<>();
        this.body      = body;
        this.timeoutMs = timeoutMs;
    }
    
    // Convenience constructors — chain to primary
    public HttpRequest(String method, String url) {
        this(method, url, null, null, 5000);  // default: no headers, no body, 5s timeout
    }
    
    public HttpRequest(String method, String url, String body) {
        this(method, url, null, body, 5000);
    }
    
    // Static factory alternatives (more readable than constructor chaining)
    public static HttpRequest get(String url) {
        return new HttpRequest("GET", url);
    }
    
    public static HttpRequest post(String url, String body) {
        return new HttpRequest("POST", url, body);
    }
    
    // Getters
    public String getMethod()        { return method; }
    public String getUrl()           { return url; }
    public Map<String, String> getHeaders() { return Collections.unmodifiableMap(headers); }
    public String getBody()          { return body; }
    public int getTimeoutMs()        { return timeoutMs; }
}
```

### Advanced — Returning `this` in Builder

```java
public class Email {
    private final String from;
    private final List<String> to;
    private final List<String> cc;
    private final String subject;
    private final String body;
    private final boolean htmlFormat;
    
    private Email(Builder b) {
        this.from       = b.from;
        this.to         = Collections.unmodifiableList(b.to);
        this.cc         = Collections.unmodifiableList(b.cc);
        this.subject    = b.subject;
        this.body       = b.body;
        this.htmlFormat = b.htmlFormat;
    }
    
    public static class Builder {
        private String from;
        private List<String> to = new ArrayList<>();
        private List<String> cc = new ArrayList<>();
        private String subject;
        private String body;
        private boolean htmlFormat = false;
        
        public Builder from(String from) { this.from = from; return this; }        // return this!
        public Builder to(String... recipients) { to.addAll(Arrays.asList(recipients)); return this; }
        public Builder cc(String... recipients) { cc.addAll(Arrays.asList(recipients)); return this; }
        public Builder subject(String s) { this.subject = s; return this; }
        public Builder body(String b) { this.body = b; return this; }
        public Builder html() { this.htmlFormat = true; return this; }
        
        public Email build() {
            Objects.requireNonNull(from, "from is required");
            if (to.isEmpty()) throw new IllegalStateException("at least one recipient required");
            return new Email(this);
        }
    }
}

// Elegant usage via method chaining (all return 'this' from Builder):
Email email = new Email.Builder()
    .from("krish@dev.com")
    .to("recruiter@google.com", "hr@amazon.com")
    .cc("mentor@iit.ac.in")
    .subject("SDE Internship Application")
    .body("Dear Hiring Team,\n\nPlease find my resume attached...")
    .html()
    .build();
```

### Production — Observer Registration with `this`

```java
// Spring: passing 'this' to event bus
@Component
public class OrderEventHandler implements ApplicationListener<OrderEvent> {
    
    private final ApplicationEventPublisher publisher;
    
    // Constructor injection — 'this' not needed here (Spring injects)
    public OrderEventHandler(ApplicationEventPublisher publisher) {
        this.publisher = publisher;  // disambiguate field from param
    }
    
    // Publishing an event about THIS handler
    public void notifySelfReady() {
        publisher.publishEvent(new HandlerReadyEvent(this));  // 'this' = the handler
    }
    
    @Override
    public void onApplicationEvent(OrderEvent event) {
        // handle
    }
}
```

---

## 8. Real World Usage

| Framework/Context | `this` Usage |
| ------------------ | ------------- |
| **Lombok `@Builder`** | Generated builder methods return `this` for chaining |
| **JPA entities** | `this.field = param` in setters and constructors |
| **Spring Security** | `this` passed to security context in custom `UserDetails` |
| **Observer pattern** | `eventBus.subscribe(this)` — object subscribes itself |
| **Fluent builders** (Guava, AssertJ) | Every method returns `this` for readable chaining |
| **JUnit 5 `@Test`** | AssertJ: `assertThat(x).isNotNull().isEqualTo(y)` — all return `this` |
| **Java streams** | Stream is its own builder — methods like `filter()`, `map()` return the stream (analogous to `this`) |
| **Android SDK** | `AlertDialog.Builder` — `setTitle().setMessage().setPositiveButton()` all return `this` |

---

## 9. Internal JVM Perspective

### `this` in Local Variable Table

```
Every non-static method call receives 'this' as hidden slot 0:

Slot 0: this reference     ← always here for instance methods
Slot 1: first parameter
Slot 2: second parameter
...

For static methods: NO slot 0 for this!
Slot 0: first parameter
Slot 1: second parameter
...

This is why you can't use 'this' in static methods:
there simply IS no slot 0 containing an object reference.
```

### `this()` Call — Bytecode

```java
// this(a, 0) in constructor:
// bytecode:
aload_0         // push 'this' onto operand stack
iload_1         // push argument 'a'
iconst_0        // push 0
invokespecial   // call the chained constructor <init>(int, int)
```

### Memory — `this` is a Reference, Not a Copy

```
account.deposit(500);

Stack:
┌─────────────────────────────┐
│ deposit() frame             │
│ slot 0: 0x7F3A → account   │ ← this is just an address
│ slot 1: 500.0              │
└─────────────────────────────┘
          │
          ▼ (follows pointer)
Heap:    ┌──────────────────┐
         │ BankAccount obj  │
         │ balance: 1000.0  │
         └──────────────────┘

this is NOT a copy of the object — it's the same object accessed via reference.
Changes through 'this' affect the actual heap object.
```

---

## 10. Time & Space Complexity

| Use | Time | Space |
| ----- | ------ | ------- |
| `this.field` access | O(1) | O(0) — no extra memory |
| `this()` constructor chain | O(total init cost) | O(call stack depth) |
| Returning `this` | O(1) | O(0) — just returns existing reference |
| `this` as method argument | O(1) | O(0) — passes existing reference |

`this` itself consumes no extra memory — it's just a reference (pointer) to the existing object.

---

## 11. Advantages

- **Disambiguation** — cleanly separates fields from same-named parameters
- **Constructor chaining** — eliminates constructor duplication via `this()`
- **Fluent API** — returning `this` enables clean method chaining
- **Self-reference** — allows objects to register themselves, pass to callbacks
- **Readability** — explicit `this.field` clarifies intent when reading code
- **Single validation point** — `this(primaryConstructor)` centralizes initialization

---

## 12. Disadvantages

- **Verbosity** — repetitive `this.` in classes with many fields
- **`this()` first-statement rule** — cannot execute any code before delegating constructor
- **Cannot be used in static** — causes compile error; easy to confuse new developers
- **Anonymous class `this`** — in anonymous classes, `this` refers to the anonymous class, not outer (use `OuterClass.this` for outer)
- **Circular reference risk** — passing `this` in constructor to an external system before construction is complete can expose a partially initialized object

---

## 13. Tradeoffs

| Use Case | Use `this.` | Skip `this.` |
| ---------- | ------------ | -------------- |
| Parameter shadows field | **Always** | Never |
| No naming conflict | Optional (clarity) | Usually OK |
| Method chaining builder | **Always return this** | — |
| Pass self as argument | **Use `this`** | — |
| Constructor chaining | **Use `this()`** | Multiple constructors with duplicate logic |

---

## 14. Comparison

### `this` vs `super`

| Aspect | `this` | `super` |
| -------- | -------- | --------- |
| Refers to | Current object (same class) | Parent class portion of current object |
| Constructor call | `this()` — same class constructor | `super()` — parent class constructor |
| Field access | `this.field` — current class field | `super.field` — parent class field |
| Method call | `this.method()` — dynamic dispatch | `super.method()` — bypasses override, calls parent |
| First statement? | `this()` must be first | `super()` must be first |
| Both at once? | ❌ Cannot use `this()` AND `super()` in same constructor | — |

### `this()` vs `super()` in Constructor

| Rule | `this()` | `super()` |
| ------ | ---------- | ----------- |
| Must be first line | ✅ | ✅ |
| Can only one per constructor | ✅ | ✅ |
| Can combine in same constructor | ❌ | ❌ |
| Implicit if omitted | ❌ (not added) | ✅ `super()` added implicitly |
| Purpose | Chain to another constructor in SAME class | Call parent class constructor |

---

## 15. Common Mistakes

```java
// Mistake 1: Missing this. in setter → shadow bug
public void setName(String name) {
    name = name.trim();  // sets local param, NOT the field! Field unchanged!
    // Fix: this.name = name.trim();
}

// Mistake 2: this() not first statement
public MyClass(int a) {
    System.out.println("initializing");  // COMPILE ERROR: this() must be first
    this(a, 0);
}

// Mistake 3: Passing 'this' before construction complete (unsafe publication)
public EventSource(EventBus bus) {
    this.bus = bus;
    bus.register(this);  // DANGER! Partially constructed object exposed
    // If another thread calls a method on this via bus, fields below not set yet:
    this.id = generateId();  // not set yet when register() was called!
}
// Fix: register in a factory method or @PostConstruct, after full construction

// Mistake 4: Thinking 'this' in anonymous class = outer class
button.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        System.out.println(this);              // 'this' = the anonymous ActionListener
        System.out.println(OuterClass.this);   // explicit outer reference
    }
});

// Mistake 5: Calling this() after other statements
public Connection(String host) {
    this.host = host;   // Fine, but then:
    this("localhost");  // COMPILE ERROR — this() must be VERY FIRST
}

// Mistake 6: Using this in static method
public static void helper() {
    System.out.println(this.name); // COMPILE ERROR — no 'this' in static
}
```

---

## 16. Best Practices

1. **Always use `this.` in setters and constructors** when parameter names match field names — even if not strictly required, it's explicit and safe
2. **Use `this()` to reduce constructor duplication** — one primary constructor, others delegate
3. **Design the primary constructor to receive ALL fields** and validate there; others delegate to it
4. **Never pass `this` to external system from within a constructor** — may expose partially initialized object
5. **In anonymous/inner classes**, use `OuterClass.this` to refer to the outer instance
6. **Use `return this` in builder-style methods** for fluent API design
7. **Avoid unnecessary `this.` in getters** where no naming conflict exists — reduces noise
8. **`this()` chain should have a single primary constructor** with all validation — avoids validation scattering

---

## 17. Interview Section

### Easy

1. What does the `this` keyword refer to in Java?
2. When is `this.fieldName` necessary vs optional?
3. Can `this` be used in a static method? Why?
4. What is `this()` in a constructor?
5. What is the rule about where `this()` must appear?

### Medium

1. Explain how constructor chaining with `this()` works. What problem does it solve?
2. How do you refer to the outer class instance from within an anonymous inner class?
3. What is the risk of passing `this` in a constructor to an external system?
4. How is returning `this` from a method useful? Give an example.
5. What is the difference between `this.method()` and just `method()` inside an instance method?

### Hard

1. How does the JVM represent `this` in the local variable table?
2. Why is `this()` required to be the first statement in a constructor?
3. Can `this()` and `super()` both be called in the same constructor? Why?
4. What happens to `this` after the constructor chain completes? Is it safe to use externally?
5. How does `this` behave differently in lambda expressions vs anonymous inner classes?

### Very Hard

1. Explain how `this` in a lambda is lexically scoped (refers to enclosing class), unlike anonymous classes.
2. What is "unsafe publication" and how does it relate to passing `this` in constructors?
3. Explain the JVM bytecode difference between `invokevirtual` (instance method) and `invokestatic` (static method) in terms of `this` slot.
4. How does the JIT compiler handle methods that `return this` — does it change optimization behaviour?

---

## 18. Coding Questions

### Easy

1. Write a class `Circle` with a constructor using `this.radius = radius` — explain why `this.` is needed.
2. Demonstrate constructor chaining: `Rectangle()`, `Rectangle(double side)`, `Rectangle(double w, double h)`.
3. Write a method that adds the current object to a given list using `this`.
4. Create a class where `getThis()` returns `this` — verify two variables point to same object.
5. Show the `this()` chain for a `Person` class with 5 overloaded constructors.

### Medium

1. Implement a fluent `StringBuilder`-style `HtmlTag` class: `tag.open().addText("Hi").close()`.
2. Implement a `LinkedList<T>` `Node` that adds itself to the list in its constructor via `this`.
3. Create an `EventEmitter` class that uses `this` to register itself with an event bus.
4. Write `this()` constructor chaining for an HTTP client with optional headers, body, timeout.
5. Implement `EqualsChecker` class where `chain().add("a","a").add("b","b").allEqual()` uses `this`.

### Hard

1. Implement a `CopyBuilder<T>` that creates a copy of an object and modifies it — return `this` for chaining.
2. Implement safe construction: use a private constructor + static factory to prevent unsafe `this` exposure.
3. Build a `FluentValidator<T>` where `validate(obj).notNull().maxLength(50).matches(regex).check()` uses `this`.
4. Implement a recursive data structure where each node stores a reference to the parent using `this`.
5. Create a thread-safe event emitter where `subscribe(this)` is safe even after partial construction.

### Company Level

1. **Spring-style:** Implement a simplified `@Component` registration where beans register themselves via `this`.
2. **Builder pattern (Google Guava style):** Build an `ImmutableConfig.Builder` with 8 fields, fluent API using `this`.
3. **Fluent test API (AssertJ style):** Implement `assertThat(value).isNotNull().isGreaterThan(0).isLessThan(100)`.
4. **Chain of Responsibility:** Implement `RequestHandler.next(this)` pattern where handlers chain themselves.
5. **Observer pattern:** Implement a full pub-sub system where subscribers register with `bus.subscribe(this)`.

---

## 19. Production Scenarios

### Scenario 1: Unsafe Publication via `this` in Constructor

```java
// BUG — dangerous in multi-threaded environment
public class Service {
    private final Cache cache;
    private final Config config;  // initialized AFTER registration
    
    public Service(ServiceRegistry registry) {
        registry.register("myService", this); // 'this' exposed!
        this.cache  = new Cache();
        this.config = Config.load();  // NOT YET DONE when registered!
    }
}
// Another thread calls service.process() before config is initialized → NPE

// FIX — use factory method or @PostConstruct
public class Service {
    private final Cache cache;
    private final Config config;
    
    private Service() {  // private constructor
        this.cache  = new Cache();
        this.config = Config.load();
    }
    
    public static Service create(ServiceRegistry registry) {
        Service s = new Service();  // fully constructed
        registry.register("myService", s);  // THEN expose
        return s;
    }
}
```

### Scenario 2: `this` Shadow Bug Found in Code Review

```java
// Buggy setter found in production code base:
public void setConnectTimeout(int connectTimeout) {
    connectTimeout = Math.max(connectTimeout, 1000);  // local param capped, NOT field!
    // Actual field still has old value — timeouts never capped!
}

// Discovered after users complained about 1ms timeout failures.
// Fix: this.connectTimeout = Math.max(connectTimeout, 1000);
```

### Scenario 3: Anonymous Class `this` Confusion

```java
// Common bug in pre-Java 8 code
public class ButtonHandler {
    private String label = "Submit";
    
    public JButton createButton() {
        return new JButton() {{
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // BUG: 'this' is the ActionListener, NOT ButtonHandler!
                    System.out.println(this.label);           // compile error or wrong
                    System.out.println(ButtonHandler.this.label); // correct: "Submit"
                    
                    // Lambda fix: lambdas DO NOT have their own 'this'
                    // addActionListener(e -> System.out.println(label)); // works!
                }
            });
        }};
    }
}
```

---

## 20. Internal Deep Dive

### Lambda vs Anonymous Class — `this` Scope

```java
public class Demo {
    private String name = "Demo";
    
    public void show() {
        // Anonymous class: 'this' = anonymous class instance
        Runnable anon = new Runnable() {
            public void run() {
                System.out.println(this);           // anonymous Runnable object
                System.out.println(Demo.this.name); // outer class (explicit)
            }
        };
        
        // Lambda: 'this' = enclosing class instance (lexical scoping)
        Runnable lambda = () -> {
            System.out.println(this);           // Demo instance! (not the lambda itself)
            System.out.println(this.name);      // "Demo" — works directly
        };
    }
}
// Key insight: lambdas don't create a new 'this' scope — they capture the enclosing 'this'.
// This is why lambdas can access outer 'this' directly, while anonymous classes need OuterClass.this.
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| `this` = a copy of the object | `this` is a REFERENCE to the object — same heap memory, not a copy |
| `this` can be used in static | Static methods have no object context — NO `this`. Compile error. |
| `this()` can go anywhere in constructor | `this()` MUST be the very FIRST statement. No exceptions. |
| `this` and `super` both callable | You can use EITHER `this()` OR `super()` in one constructor, NEVER both |
| `this` in lambda = lambda itself | Lambdas do NOT have their own `this`. `this` in a lambda = enclosing class instance |
| `this.method()` is different from `method()` | For non-static methods, they are IDENTICAL. `this.method()` is explicit, `method()` is implicit `this.method()` |

---

## 22. Cheat Sheet

```
THIS KEYWORD — 5 USES:

1. DISAMBIGUATION: this.field = param;   // when param shadows field
2. PASS SELF:      method(this);         // pass current object
3. CHAIN CONSTRUCTOR: this(args);        // MUST be first statement
4. FLUENT RETURN:  return this;          // enable method chaining
5. EXPLICIT CALL:  this.method(args);    // same as method(args)

RULES:
  ✅ Available in ALL instance methods and constructors
  ❌ NOT available in static methods/blocks
  ✅ this() must be FIRST statement in constructor
  ❌ Cannot call both this() and super() in same constructor
  ✅ Lambda: 'this' = enclosing class (lexically scoped)
  ⚠️  Anonymous class: 'this' = anonymous class (use OuterClass.this for outer)

COMMON BUG — SHADOW:
  public void setX(int x) {
    x = x;       // WRONG: assigns param to itself
    this.x = x;  // CORRECT: assigns param to field
  }

SAFE CONSTRUCTION:
  Never pass 'this' to external system inside constructor!
  Use @PostConstruct or static factory method instead.
```

---

## 23. Mind Map

```
THIS KEYWORD
│
├── USES
│   ├── 1. Disambiguate field vs parameter
│   ├── 2. Pass self as argument
│   ├── 3. this() → constructor chaining
│   ├── 4. return this → fluent API
│   └── 5. this.method() → explicit call
│
├── RULES
│   ├── NOT in static context (no object)
│   ├── this() must be first statement
│   ├── Cannot combine this() and super()
│   └── super() implicit if this() not used
│
├── SCOPING
│   ├── Instance method → current object
│   ├── Anonymous class → the anonymous class
│   └── Lambda → enclosing class (lexical)
│
├── JVM INTERNALS
│   ├── Slot 0 in local variable table
│   ├── Passed implicitly on invokevirtual
│   └── Not present for invokestatic
│
└── COMMON BUGS
    ├── Missing 'this.' in setter → shadow
    ├── this() not first → compile error
    └── Unsafe publication in constructor
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| `this` reference | Refers to the current object on which the method is executing |
| Field disambiguation | `this.name = name` → left side is field, right side is parameter |
| `this()` | Calls another constructor of the same class; must be first statement |
| Return `this` | Enables method chaining / fluent API by returning the current object |
| `this` in static | Compile error — no current object in static context |
| `this` in lambda | Refers to the enclosing class instance (not the lambda itself) |
| `this` in anonymous class | Refers to the anonymous class (use `OuterClass.this` for outer) |
| Unsafe publication | Passing `this` in constructor before fully initialized — concurrency bug |
| Slot 0 | JVM always puts `this` reference at local variable slot 0 in instance methods |
| `this` vs `super` | `this` = current class; `super` = parent class portion of same object |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"this = Me"** | `this` always refers to "me" — the current executing object |
| **"First or Nothing"** | `this()` must be the FIRST line — no exceptions |
| **"Static = No this"** | Static methods are class-level — no object, no `this` |
| **"Anon = own this; Lambda = outer this"** | Lambda doesn't create new `this` scope |
| **"Unsafe Publishing = Exposed Before Built"** | Never pass `this` in constructor to external code |
| **"Fluent = Return this"** | Builder-style APIs return `this` from every setter |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| `this` | Reference to the current object in instance methods and constructors |
| `this()` | Constructor call to another constructor in the same class |
| Field shadowing | Local variable or parameter hides a field with the same name |
| Constructor chaining | Calling one constructor from another to avoid code duplication |
| Fluent API | Design where each method returns `this` to allow method chaining |
| Unsafe publication | Exposing `this` before object construction is complete |
| Lexical scoping | Lambda's `this` is determined by where the lambda is written, not where it runs |
| `OuterClass.this` | Syntax to access the outer class instance from within an inner/anonymous class |

---

## 27. Interview One-Liners

- "`this` is a reference to the current object; it occupies slot 0 in the local variable table of every instance method."
- "`this.field = parameter` is needed when a parameter shadows a field with the same name."
- "`this()` calls another constructor of the same class and must be the first statement — cannot combine with `super()`."
- "`this` cannot be used in static methods — static context has no associated object."
- "Returning `this` from a method enables fluent/method-chaining APIs like builders."
- "In a lambda, `this` refers to the enclosing class; in an anonymous class, `this` refers to the anonymous class itself."
- "Never pass `this` to an external system from within a constructor — the object may be partially initialized."
- "`this.method()` and `method()` are identical in instance methods — both invoke the same dynamic dispatch."
- "Constructor chaining with `this()` centralizes validation in one constructor and eliminates duplication."
- "`OuterClass.this` is needed to access the outer class instance from an inner or anonymous class."

---

## 28. Summary

`this` is one of Java's most versatile keywords with five distinct uses: (1) **field disambiguation** — `this.name = name` when parameter shadows field; (2) **passing current object** — `bus.subscribe(this)`; (3) **constructor chaining** — `this(args)` must be the first statement, delegates to another same-class constructor to eliminate duplication; (4) **fluent API** — `return this` enables method chaining in builders; (5) **explicit method call** — `this.method()` (optional clarity). Critically, `this` is unavailable in static contexts, must always be the first call in constructors when used as `this()`, cannot be combined with `super()`, and is lexically scoped in lambdas (referring to enclosing class) unlike anonymous classes. The most common bugs are: missing `this.` in setters (shadow bug), passing `this` before construction completes (unsafe publication), and forgetting `OuterClass.this` in anonymous classes.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| `super` keyword | Complement to `this` — accessing parent class members |
| Constructors (all types) | `this()` chaining is a constructor concept |
| Builder pattern | `return this` used extensively in production builders |
| Inner classes | `OuterClass.this` — how inner classes reference outer |
| Lambda expressions | Why lambdas' `this` is different from anonymous classes |
| Concurrency (unsafe publication) | Why passing `this` in constructors is dangerous in multi-threaded code |

---

---

# TOPIC 9: CONSTRUCTORS AND TYPES OF CONSTRUCTORS

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | A constructor is a special method invoked automatically when an object is created with `new`. It initializes the object's state and has the same name as the class with no return type. |
| **Why introduced?** | To guarantee every object starts life in a valid, initialized state. Without constructors, fields would have unpredictable default values and no validation could happen at creation time. |
| **Problem solved** | Forces the caller to provide required data at creation time. Ensures invariants are met before the object is used. |
| **History** | Java 1.0 (1996). Java 5: varargs in constructors. Java 8: no change. Java 9: `private` constructors enabled for interfaces (default methods). Java 14+: Records introduced implicit canonical constructor. |
| **Industry importance** | Every object creation in every Java application invokes a constructor. Spring beans, JPA entities, value objects, DTOs — all initialized via constructors. |

---

## 2. Intuition

Think of a constructor as the **birth certificate and hospital admission form** for an object:

- You can't exist (be created) without it being filled out
- It happens exactly once at birth (creation)
- It records your initial state (name, date of birth = fields)
- If information is missing or wrong, the birth is rejected (exception thrown)

Different constructor types are like different hospital forms:

- **Default constructor** = standard admission form with blanks
- **No-arg constructor** = blank form with sensible defaults pre-filled
- **Parameterized constructor** = form requiring all critical info upfront
- **Copy constructor** = duplicate form from an existing patient's record
- **Constructor chaining** = one form delegates to another for certain sections

---

## 3. Core Concepts

### 3.1 Constructor Rules (Invariants)

```
MUST:
  ✅ Same name as the class (exact match, case-sensitive)
  ✅ No return type (not even void)
  ✅ Called automatically by 'new'
  ✅ Can throw checked exceptions (must declare)
  ✅ Can be overloaded (multiple constructors with different params)

CANNOT:
  ❌ Have a return type
  ❌ Be static, abstract, final, synchronized (modifier-wise)
  ❌ Be inherited (constructors are NOT inherited)
  ❌ Be called explicitly like regular methods (only via new or this()/super())
  ❌ Use 'return value' — only 'return;' is allowed

SPECIAL:
  ⚡ If no constructor defined → compiler adds default no-arg constructor
  ⚡ If ANY constructor defined → compiler does NOT add default
  ⚡ super() is implicitly added as first statement if not explicitly provided
```

### 3.2 Types of Constructors

```
Constructor Types:
├── 1. Default Constructor        (compiler-generated, no-arg, empty body)
├── 2. No-Argument Constructor    (explicitly written, no params)
├── 3. Parameterized Constructor  (takes arguments to initialize fields)
├── 4. Copy Constructor           (takes same-class object, copies its state)
└── 5. Private Constructor        (prevents instantiation — Singleton, Utility, Factory)
```

---

## 4. Internal Working

### 4.1 Type 1 — Default Constructor (Compiler-Generated)

```java
// If you write this:
public class Cat {
    String name;
    int age;
    // No constructor defined
}

// Compiler AUTOMATICALLY inserts this:
public class Cat {
    String name;
    int age;
    
    public Cat() {    // synthetic default constructor
        super();      // calls Object() — always implicit if not written
    }
}

Cat c = new Cat();   // works! name = null, age = 0 (default values)
```

**CRITICAL RULE:** The moment you define **ANY** constructor, the compiler stops generating the default:

```java
public class Cat {
    String name;
    
    public Cat(String name) {   // defined one constructor
        this.name = name;
    }
}

Cat c = new Cat();       // COMPILE ERROR: no suitable constructor found
Cat c = new Cat("Kitty"); // OK
```

### 4.2 Type 2 — No-Argument Constructor (Explicit)

```java
public class Configuration {
    private String host;
    private int port;
    private boolean ssl;
    
    // Explicitly written no-arg constructor with defaults
    public Configuration() {
        this.host = "localhost";
        this.port = 8080;
        this.ssl  = false;
    }
    
    public Configuration(String host, int port, boolean ssl) {
        this.host = host;
        this.port = port;
        this.ssl  = ssl;
    }
}

// JPA requires no-arg constructor — must be explicit if parameterized exists:
@Entity
public class User {
    protected User() {}  // required by JPA; protected prevents direct use
    public User(String name) { this.name = name; }
}
```

### 4.3 Type 3 — Parameterized Constructor

```java
public class Rectangle {
    private final double width;
    private final double height;
    
    // Parameterized — requires width and height at creation
    public Rectangle(double width, double height) {
        // Validate first — fail fast
        if (width <= 0)  throw new IllegalArgumentException("Width must be positive: " + width);
        if (height <= 0) throw new IllegalArgumentException("Height must be positive: " + height);
        this.width  = width;
        this.height = height;
    }
    
    // Overloaded — creates a square
    public Rectangle(double side) {
        this(side, side);  // delegates via this()
    }
    
    public double area()      { return width * height; }
    public double perimeter() { return 2 * (width + height); }
}
```

### 4.4 Type 4 — Copy Constructor

```java
public class Student {
    private String name;
    private int age;
    private List<String> courses;  // mutable — needs deep copy
    
    // Primary constructor
    public Student(String name, int age, List<String> courses) {
        this.name    = name;
        this.age     = age;
        this.courses = new ArrayList<>(courses);  // defensive copy
    }
    
    // Copy constructor — creates an independent copy of another Student
    public Student(Student other) {
        this.name    = other.name;
        this.age     = other.age;
        this.courses = new ArrayList<>(other.courses);  // deep copy of list
    }
}

Student original = new Student("Krish", 21, Arrays.asList("Java", "DSA"));
Student copy     = new Student(original);  // new independent object

copy.courses.add("System Design");
System.out.println(original.courses.size()); // still 2 — deep copy, independent!
```

**Copy Constructor vs `clone()`:**

| Aspect | Copy Constructor | `clone()` |
| -------- | ----------------- | ----------- |
| Type safety | ✅ Strongly typed | ❌ Returns `Object` (needs cast) |
| Interface required | ❌ None | ✅ Must implement `Cloneable` |
| Exception | ❌ None | ✅ Must handle `CloneNotSupportedException` |
| Deep copy control | ✅ Full control | ❌ Default is shallow |
| Industry preference | ✅ Preferred (Effective Java) | ❌ Avoid (fragile, confusing) |

### 4.5 Type 5 — Private Constructor

**Use Case A: Singleton Pattern**

```java
public class DatabasePool {
    private static DatabasePool instance;
    private final List<Connection> connections;
    
    // Private: no external instantiation!
    private DatabasePool() {
        this.connections = initializePool();
    }
    
    // Controlled single-instance access
    public static synchronized DatabasePool getInstance() {
        if (instance == null) {
            instance = new DatabasePool();
        }
        return instance;
    }
}
```

**Use Case B: Utility Class (All Static Methods)**

```java
public final class StringUtils {
    // Private constructor: cannot instantiate
    private StringUtils() {
        throw new UnsupportedOperationException("Utility class — do not instantiate");
    }
    
    public static boolean isPalindrome(String s) { /* ... */ }
    public static String reverse(String s) { /* ... */ }
}
```

**Use Case C: Factory Method Pattern**

```java
public class Point {
    private final double x;
    private final double y;
    
    private Point(double x, double y) {  // private — use factory methods
        this.x = x;
        this.y = y;
    }
    
    // Factory methods with clear naming (unlike overloaded constructors)
    public static Point fromCartesian(double x, double y) {
        return new Point(x, y);
    }
    
    public static Point fromPolar(double radius, double angle) {
        return new Point(radius * Math.cos(angle), radius * Math.sin(angle));
    }
    
    public static Point origin() {
        return new Point(0, 0);
    }
}
```

**Use Case D: Enum (Implicit Private)**

```java
// Enum constructors are implicitly private:
public enum Status {
    PENDING("Waiting"), ACTIVE("Running"), CLOSED("Done");
    
    private final String description;
    
    Status(String desc) { this.description = desc; }  // private by default
    
    public String getDescription() { return description; }
}
```

---

## 5. Visual Flow

```
OBJECT CREATION SEQUENCE:
new Person("Krish", 21)

Step 1: JVM allocates heap memory
        ┌─────────────────────────┐
        │ Person object           │
        │ name: null (default)    │
        │ age:  0    (default)    │
        └─────────────────────────┘

Step 2: Instance initializer blocks run (top-to-bottom)
        { log.debug("Creating person"); } ← if defined

Step 3: Constructor invoked
        Person("Krish", 21) {
            this.name = "Krish";   ← name set
            this.age = 21;         ← age set
        }

Step 4: Reference returned to caller
        Person p = [reference to heap object]

CONSTRUCTOR CHAINING SEQUENCE:
Rectangle(5.0) → this(5.0, 5.0) → Rectangle(5.0, 5.0)
                                    → validate width
                                    → validate height
                                    → this.width = 5.0
                                    → this.height = 5.0
                                    ← returns
                ← returns
← p has 5×5 rectangle

INHERITANCE CONSTRUCTOR ORDER:
class Animal { Animal() { super(); ... } }
class Dog extends Animal { Dog() { super(); ... } }

new Dog():
  → Dog() called
    → super() called (first statement, implicit or explicit)
      → Animal() called
        → super() → Object() called
          ← Object() returns
        ← Animal() returns
      ← super() returns
    → Dog fields initialized
  ← Dog() returns
← Dog object ready
```

---

## 6. Syntax

```java
// Basic constructor
[access] ClassName() {
    // no-arg constructor body
}

// Parameterized constructor
[access] ClassName(Type param1, Type param2, ...) [throws Exception] {
    // body
}

// Constructor chaining to same class
ClassName(Type param) {
    this(defaultArg1, param, defaultArg2);  // MUST be first statement
}

// Delegating to parent class constructor
ClassName(Type param) {
    super(param);   // MUST be first statement
}

// Implicit super() — added by compiler if no this() or super() present:
ClassName() {
    // compiler adds: super(); as first line
    // your code here
}

// Private constructor (utility / singleton / factory)
private ClassName() {
    throw new UnsupportedOperationException("...");
}

// Copy constructor
ClassName(ClassName other) {
    this.field1 = other.field1;
    this.field2 = new MutableType(other.field2);  // deep copy mutable fields
}
```

---

## 7. Examples

### Basic — All Constructor Types in One Class

```java
public class Vehicle {
    private String make;
    private String model;
    private int year;
    private double price;
    
    // 1. No-arg constructor with defaults
    public Vehicle() {
        this("Unknown", "Unknown", 2024, 0.0);
    }
    
    // 2. Partial parameterized — delegates to full
    public Vehicle(String make, String model) {
        this(make, model, 2024, 0.0);
    }
    
    // 3. Full parameterized — primary constructor
    public Vehicle(String make, String model, int year, double price) {
        if (make == null || make.isBlank()) throw new IllegalArgumentException("Make required");
        if (year < 1886 || year > 2100)    throw new IllegalArgumentException("Invalid year");
        if (price < 0)                     throw new IllegalArgumentException("Price negative");
        this.make  = make;
        this.model = model;
        this.year  = year;
        this.price = price;
    }
    
    // 4. Copy constructor
    public Vehicle(Vehicle other) {
        this(other.make, other.model, other.year, other.price);
    }
    
    @Override
    public String toString() {
        return year + " " + make + " " + model + " @ ₹" + price;
    }
}
```

### Intermediate — Constructor with Exception Handling

```java
public class FileProcessor {
    private final Path filePath;
    private final Charset charset;
    private List<String> lines;
    
    // Constructor that reads file — can throw IOException
    public FileProcessor(String path) throws IOException {
        this(path, StandardCharsets.UTF_8);
    }
    
    public FileProcessor(String path, Charset charset) throws IOException {
        Objects.requireNonNull(path, "path required");
        this.filePath = Paths.get(path);
        this.charset  = charset;
        
        if (!Files.exists(filePath)) {
            throw new IOException("File not found: " + path);
        }
        
        this.lines = Files.readAllLines(filePath, charset);
    }
    
    public List<String> getLines() { return Collections.unmodifiableList(lines); }
    public int getLineCount() { return lines.size(); }
}

// Usage — must handle IOException
try {
    FileProcessor fp = new FileProcessor("/data/log.txt");
    System.out.println(fp.getLineCount());
} catch (IOException e) {
    System.err.println("Failed: " + e.getMessage());
}
```

### Advanced — Immutable Class + All Types

```java
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public final class Order {
    private final String orderId;
    private final String customerId;
    private final List<OrderItem> items;
    private final BigDecimal totalAmount;
    private final LocalDateTime createdAt;
    private final String currency;
    
    // Primary constructor — all validation here
    public Order(String orderId, String customerId,
                 List<OrderItem> items, BigDecimal totalAmount, String currency) {
        this.orderId      = Objects.requireNonNull(orderId, "orderId required");
        this.customerId   = Objects.requireNonNull(customerId, "customerId required");
        this.items        = List.copyOf(Objects.requireNonNull(items, "items required")); // Java 10
        this.totalAmount  = Objects.requireNonNull(totalAmount);
        this.currency     = Objects.requireNonNull(currency);
        this.createdAt    = LocalDateTime.now();
        
        if (items.isEmpty()) throw new IllegalArgumentException("Order must have items");
        if (totalAmount.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Total cannot be negative");
    }
    
    // Convenience constructor — single item order
    public Order(String orderId, String customerId, OrderItem item, String currency) {
        this(orderId, customerId, Collections.singletonList(item),
             item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())), currency);
    }
    
    // Copy constructor (creates a new order with same items — different ID)
    public Order copyWithNewId(String newOrderId) {
        return new Order(newOrderId, this.customerId, this.items, this.totalAmount, this.currency);
    }
    
    // Private factory — used internally
    private static Order empty(String customerId, String currency) {
        return new Order(UUID.randomUUID().toString(), customerId,
                        Collections.emptyList(), BigDecimal.ZERO, currency);
    }
    
    public String getOrderId()          { return orderId; }
    public String getCustomerId()       { return customerId; }
    public List<OrderItem> getItems()   { return items; }  // already unmodifiable (List.copyOf)
    public BigDecimal getTotalAmount()  { return totalAmount; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
```

### Production — Record with Compact Constructor (Java 16+)

```java
// Record: compiler generates canonical constructor
public record UserRegistrationRequest(
    String username,
    String email,
    String password,
    LocalDate birthDate
) {
    // Compact constructor: runs before field assignment
    // Fields are implicitly assigned after this block
    public UserRegistrationRequest {
        // Validation (canonical constructor, compact form)
        Objects.requireNonNull(username, "username required");
        Objects.requireNonNull(email, "email required");
        Objects.requireNonNull(password, "password required");
        Objects.requireNonNull(birthDate, "birthDate required");
        
        if (username.length() < 3 || username.length() > 50)
            throw new IllegalArgumentException("Username must be 3-50 chars");
        if (!email.contains("@"))
            throw new IllegalArgumentException("Invalid email");
        if (password.length() < 8)
            throw new IllegalArgumentException("Password min 8 chars");
        if (birthDate.isAfter(LocalDate.now().minusYears(13)))
            throw new IllegalArgumentException("Must be 13+ years old");
        
        // Normalise (modification is allowed in compact constructor)
        username = username.toLowerCase().trim();
        email    = email.toLowerCase().trim();
    }
}

// Usage
UserRegistrationRequest req = new UserRegistrationRequest(
    "Krish_Dev", "KRISH@gmail.com", "securePass123", LocalDate.of(2003, 5, 15));
System.out.println(req.username()); // "krish_dev" — normalised
System.out.println(req.email());    // "krish@gmail.com" — normalised
```

---

## 8. Real World Usage

| Framework/Context | Constructor Usage |
| ------------------ | ----------------- |
| **JPA/Hibernate** | Requires `protected` or `public` no-arg constructor for entity instantiation |
| **Spring (DI)** | Constructor injection (preferred): `@Autowired` or auto-detected |
| **Lombok** | `@AllArgsConstructor`, `@NoArgsConstructor`, `@RequiredArgsConstructor` |
| **Jackson** | `@JsonCreator` marks a constructor for JSON deserialization |
| **Google Guava** | `ImmutableList.of()` — private constructor, factory methods |
| **Java Records** | Canonical constructor auto-generated; compact constructor for validation |
| **Enum** | Enum constructors are always private |
| **Singleton** | Private constructor + static factory method |
| **Builder pattern** | Private constructor; only `Builder.build()` can create instance |

---

## 9. Internal JVM Perspective

### Constructor as `<init>` Method

```
In bytecode, every constructor becomes a special method named '<init>':

javap -c Person.class:
  public Person(java.lang.String, int);
    descriptor: (Ljava/lang/String;I)V
    Code:
       0: aload_0              // push 'this'
       1: invokespecial #1     // Method java/lang/Object."<init>":()V  ← super()
       4: aload_0              // push 'this'
       5: aload_1              // push 'name' param
       6: putfield #2          // set Person.name field
       9: aload_0              // push 'this'
      10: iload_2              // push 'age' param
      11: putfield #3          // set Person.age field
      14: return

Note: invokespecial is used for constructors, private methods, and super calls
      (NOT invokevirtual — no dynamic dispatch for constructors)
```

### Object Creation — Full JVM Sequence

```
new Person("Krish", 21):
  1. new #2      → allocate memory for Person on Eden (heap)
                   set all fields to defaults (null, 0, false)
  2. dup         → duplicate reference (one for init, one to keep)
  3. ldc "Krish" → push string literal
  4. bipush 21   → push int 21
  5. invokespecial Person.<init>(String,int) → call constructor
     → inside <init>: super() → Object.<init>() runs first
     → then: this.name = "Krish", this.age = 21
  6. astore_1    → store reference in local variable
```

### Constructor Injection in Spring (Preferred Pattern)

```java
@Service
public class OrderService {
    private final OrderRepository repo;
    private final EmailService email;
    
    // Spring auto-detects single constructor — no @Autowired needed (Spring 4.3+)
    public OrderService(OrderRepository repo, EmailService email) {
        this.repo  = Objects.requireNonNull(repo);
        this.email = Objects.requireNonNull(email);
    }
}
```

---

## 10. Time & Space Complexity

| Constructor Type | Time | Space | Notes |
| ----------------- | ------ | ------- | ------- |
| No-arg (empty) | O(1) | O(1) | Just calls super(), returns |
| Parameterized | O(validation + init) | O(fields) | Linear in number of fields |
| Copy constructor | O(n) shallow | O(n) | n = number of fields |
| Copy constructor (deep) | O(total nested size) | O(full graph) | Copies all nested objects |
| Chained `this()` | O(called chain) | O(stack depth) | Stack frames per hop |

---

## 11. Advantages

- **Forced initialization** — cannot create an object without providing required data
- **Validation at creation** — invalid state objects never exist after construction
- **Overloading** — multiple constructors for different initialization scenarios
- **`this()` chaining** — eliminates initialization duplication
- **Fail-fast** — throw exceptions at creation time, not randomly later
- **Immutability support** — constructors + final fields = immutable objects
- **Injection support** — constructor injection is the most testable DI style

---

## 12. Disadvantages

- **Verbosity** — many fields → long constructor signatures (use Builder pattern)
- **No return value** — cannot signal alternative outcomes (use factory methods)
- **Cannot be inherited** — each subclass must define or delegate its own
- **`this()` must be first** — cannot execute code before delegating
- **JPA no-arg requirement** — JPA entities need a no-arg constructor, which conflicts with forcing required fields
- **Too many params** — more than 4-5 params is a code smell (use Builder)
- **Copy constructors need updating** — add a field → must update copy constructor manually

---

## 13. Tradeoffs

| Scenario | Use Constructor | Use Builder | Use Factory |
| ---------- | --------------- | ------------- | ------------- |
| Few required fields (≤3) | ✅ Direct | Overkill | Sometimes |
| Many optional fields | Too many overloads | ✅ Builder | — |
| Subtype selection needed | ❌ Can't return subtypes | ❌ | ✅ Factory |
| Caching/singleton needed | ❌ | ❌ | ✅ Factory |
| Framework compat (JPA) | ✅ No-arg required | — | — |
| Immutable object | ✅ Final fields | ✅ With private ctor | — |

---

## 14. Comparison

### Constructor vs Static Factory Method

| Aspect | Constructor | Static Factory Method |
| -------- | ------------- | ---------------------- |
| Name | Always class name (not descriptive) | Descriptive: `of()`, `from()`, `create()`, `getInstance()` |
| Return type | Always current class | Can return subtype |
| New object | Always creates new | Can return cached/existing |
| Overloading with same params | ❌ Impossible | ✅ Different names |
| Hiding | ❌ Must be public for new | ✅ Can be private |
| Industry examples | `new ArrayList<>()` | `List.of()`, `Optional.of()` |

### Types of Constructors — Quick Comparison

| Type | Params | Body | Purpose |
| ------ | -------- | ------ | --------- |
| Default (compiler) | None | Empty (calls super()) | Created when no constructor defined |
| No-arg (explicit) | None | Sets defaults | Framework compat (JPA), default state |
| Parameterized | 1+ | Validates + assigns | Force required fields at creation |
| Copy | Same class | Copies fields (deep) | Create independent clone |
| Private | Any | `throw` or initialize | Singleton, utility, factory |
| Canonical (Record) | All record fields | Validates | Auto-generated + customizable |

---

## 15. Common Mistakes

```java
// Mistake 1: Forgetting no-arg when parameterized exists
@Entity
class Product {
    Product(String name) { this.name = name; }
    // Missing: Product() {}   → JPA fails at runtime!
}

// Mistake 2: this() not first statement
class Box {
    Box(double side) {
        System.out.println("Creating box");  // COMPILE ERROR: this() must be first!
        this(side, side);
    }
}

// Mistake 3: Mutable fields not defensively copied in constructor
class Wrapper {
    private final List<String> items;
    Wrapper(List<String> items) {
        this.items = items;  // WRONG: caller can modify their list → our state changes!
        // Fix: this.items = new ArrayList<>(items);
    }
}

// Mistake 4: Infinite constructor chain
class A {
    A()    { this(0); }   // → A(0)
    A(int x) { this(); }  // → A() → INFINITE LOOP → StackOverflowError!
}

// Mistake 5: Calling overridable methods from constructor
class Parent {
    Parent() { initialize(); }   // calls overridable method
    void initialize() {}
}
class Child extends Parent {
    private List<String> data = new ArrayList<>();
    @Override void initialize() {
        data.add("item");  // data is NULL at this point! NPE!
        // Child's 'data' field is not yet initialized when Parent constructor calls this
    }
}

// Mistake 6: Shallow copy in copy constructor for mutable fields
class Config {
    List<String> servers;
    Config(Config other) {
        this.servers = other.servers;  // WRONG: same list reference!
        // Fix: this.servers = new ArrayList<>(other.servers);
    }
}
```

---

## 16. Best Practices

1. **Validate all inputs in the primary constructor** — fail fast with clear messages
2. **Use `Objects.requireNonNull(param, "message")`** for null checks
3. **Make defensive copies** of mutable constructor parameters
4. **Chain constructors with `this()`** — keep validation in one place
5. **Use Builder pattern** for classes with >4 fields or many optional fields
6. **Keep constructors lean** — no heavy I/O, no external service calls
7. **Use static factory methods** over constructors when name clarity matters
8. **Never call overridable methods** from a constructor
9. **JPA entities**: provide `protected` no-arg constructor to discourage direct use
10. **Private constructors + static factory** for singletons and utility classes
11. **Use Records** (Java 16+) for simple immutable data — canonical constructor auto-generated
12. **Spring boot**: prefer **constructor injection** over field injection — facilitates immutability and testing

---

## 17. Interview Section

### Easy

1. What is a constructor? How is it different from a method?
2. What happens if you don't define any constructor in a class?
3. Can a constructor have a return type?
4. What is the difference between a no-arg and a default constructor?
5. Can constructors be overloaded?

### Medium

1. What happens when a parameterized constructor is defined but no no-arg constructor?
2. Explain constructor chaining with `this()`. What is the rule about its position?
3. What is a copy constructor? How is it different from `clone()`?
4. Why is private constructor used? Give 3 use cases.
5. What is the order of execution: static block, instance block, constructor?

### Hard

1. Why is calling an overridable method from a constructor dangerous?
2. Explain how `super()` is implicitly added. What happens in a multi-level inheritance hierarchy?
3. How does JPA/Hibernate use the no-arg constructor? Why does it need one?
4. What is the canonical constructor in a Java Record? What is the compact constructor?
5. Why is constructor injection preferred over field injection in Spring?

### Very Hard

1. Explain how constructors are represented in JVM bytecode (`<init>` method).
2. What is the difference between `invokespecial` (constructor) and `invokevirtual` (method)?
3. Explain unsafe publication of partially initialized objects in constructors (Java Memory Model).
4. Why can't constructors be `abstract`, `final`, `static`, or `synchronized`? Explain each.

---

## 18. Coding Questions

### Easy

1. Create a `Circle` class with 3 constructors: no-arg (r=1), single-param (radius), copy.
2. Demonstrate that if you define a parameterized constructor, the default is not generated.
3. Create a `Date` class (day, month, year) with validated parameterized constructor.
4. Show constructor chaining: `Triangle()` → `Triangle(double base)` → `Triangle(double base, double height)`.
5. Write a utility class `MathUtil` with a private constructor that throws `UnsupportedOperationException`.

### Medium

1. Implement a `Matrix` class where the constructor takes `int[][]` with deep copy validation.
2. Create an immutable `Address` class using constructor injection with all-field validation.
3. Implement the Singleton pattern using a private constructor and thread-safe getInstance().
4. Build a `Product` class with no-arg (JPA-compatible), full parameterized, and copy constructor.
5. Implement a `Point3D` with constructors: `(x,y,z)`, `(x,y)` (z=0), `(Point3D other)`.

### Hard

1. Implement a `Graph<T>` constructor that takes an adjacency matrix and validates it.
2. Design a `Transaction` class whose constructor logs to an audit trail — using constructor injection.
3. Implement a `PooledObject<T>` with a private constructor and public static `acquire()` factory.
4. Create a safe copy of an object graph with circular references in the copy constructor.
5. Build a `VersionedSnapshot<T>` that captures the state of an object at construction time.

### Company Level

1. **Spring Boot:** Implement a `ServiceConfig` class with constructor injection of multiple dependencies.
2. **JPA Entity:** Create a `Payment` entity with proper no-arg (JPA) and parameterized (domain) constructors.
3. **Amazon:** Build an `S3ObjectKey` immutable value class with private constructor and factory methods.
4. **Google:** Implement `Optional<T>` from scratch — private constructor, `of()`, `ofNullable()`, `empty()`.
5. **Netflix:** Design a `CircuitBreakerConfig` builder that validates all parameters in `build()` constructor.

---

## 19. Production Scenarios

### Scenario 1: JPA No-Arg Constructor Missing

```
Problem: A new developer added a required-field parameterized constructor to User entity.
Spring Boot app deployed successfully (compile OK) but failed on first DB query.
Error: "No default constructor for entity: com.app.User" — Hibernate needs no-arg to instantiate.
Fix: Add protected User() {} (protected to prevent misuse, satisfy JPA).
Lesson: JPA entities ALWAYS need a no-arg constructor.
```

### Scenario 2: Mutable Input Not Copied in Constructor

```java
// Order constructed with external list
List<String> items = new ArrayList<>(Arrays.asList("Laptop"));
Order order = new Order("ORD-1", items);

items.add("Keyboard");  // mutating external list
items.clear();          // clearing it!

// If constructor did: this.items = items; → order now has empty items list!
// Fix: this.items = new ArrayList<>(items); in constructor
```

### Scenario 3: Overridable Method in Constructor

```java
// Found during debugging of initialization bug in production
abstract class DataProcessor {
    DataProcessor() {
        process();   // calls overridable method!
    }
    abstract void process();
}

class CsvProcessor extends DataProcessor {
    private List<String[]> data = new ArrayList<>();
    
    @Override void process() {
        // BUG: 'data' is null here! Parent constructor runs before child field init.
        data.add(parseCsv());  // NullPointerException!
    }
}
// Fix: don't call overridable methods in constructors
// Use a factory method or @PostConstruct for post-construction initialization
```

---

## 20. Internal Deep Dive

### Constructor Overloading Resolution

```java
class Foo {
    Foo(Object o)  { System.out.println("Object"); }
    Foo(String s)  { System.out.println("String"); }
    Foo(Integer i) { System.out.println("Integer"); }
}

new Foo("hello");   // "String" — most specific type wins
new Foo(42);        // "Integer" — int autoboxed to Integer
new Foo(null);      // AMBIGUOUS: compile error — String and Integer both match null!
new Foo((Object)null); // "Object" — explicit cast resolves ambiguity
```

### Record Canonical Constructor (Java 16+)

```java
record Point(int x, int y) {}
// Compiler generates:
public final class Point implements Record {
    private final int x;
    private final int y;
    
    // Canonical constructor (all components as params)
    public Point(int x, int y) {
        this.x = x;  // these assignments happen AFTER compact constructor body
        this.y = y;
    }
    
    public int x() { return x; }
    public int y() { return y; }
    @Override public boolean equals(Object o) { ... }
    @Override public int hashCode() { ... }
    @Override public String toString() { return "Point[x=" + x + ", y=" + y + "]"; }
}
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| Default constructor = no-arg constructor | Default = compiler-generated no-arg. No-arg = explicitly written no-arg. Different! |
| Constructors are inherited | NOT inherited. Each class must define its own. Subclass calls parent via `super()`. |
| `super()` is optional | If you don't write `super()` or `this()`, compiler adds `super()` as first line automatically. |
| Constructor has void return type | Constructor has NO return type at all — not even void. |
| Calling `this()` and `super()` together | Cannot do both in same constructor. Only one first-line constructor call allowed. |
| Private constructor prevents subclassing | `private` constructor does prevent subclassing (can't call `super()`). `final` class also prevents it. |
| Copy constructor = clone | Copy constructor is a design pattern; `clone()` is a Java method from Object. Prefer copy constructor. |

---

## 22. Cheat Sheet

```
CONSTRUCTOR RULES:
  ✅ Same name as class
  ✅ No return type (not even void)
  ✅ Called by 'new'
  ✅ Can be overloaded
  ✅ Can throw checked exceptions
  ❌ Not static, final, abstract, synchronized

TYPES:
  1. Default    → compiler generates if NO constructor exists
  2. No-arg     → explicitly written, no params
  3. Parameterized → takes args, validates, initializes
  4. Copy       → takes same-class object, deep copies state
  5. Private    → Singleton, Utility, Factory pattern

CHAINING RULES:
  this()  → chain to same class constructor (must be FIRST)
  super() → chain to parent constructor (must be FIRST)
  Cannot use BOTH in one constructor
  super() added IMPLICITLY if neither this() nor super() written

EXECUTION ORDER:
  1. Parent static block (once)
  2. Child static block (once)
  3. Parent instance block (each new)
  4. Parent constructor (each new)
  5. Child instance block (each new)
  6. Child constructor (each new)

JPA REQUIREMENT:
  Always provide protected or public no-arg constructor for @Entity classes

FACTORY vs CONSTRUCTOR:
  Constructor: always returns same type, no caching
  Factory: descriptive name, can return subtype, can cache
```

---

## 23. Mind Map

```
CONSTRUCTORS
│
├── TYPES
│   ├── Default (compiler-generated)
│   ├── No-arg (explicit)
│   ├── Parameterized
│   ├── Copy constructor
│   └── Private (Singleton/Utility/Factory)
│
├── RULES
│   ├── Same name as class
│   ├── No return type
│   ├── Overloadable
│   ├── Not inheritable
│   └── this() OR super() first — not both
│
├── CHAINING
│   ├── this() → same class
│   └── super() → parent class
│
├── JVM
│   ├── Compiled as <init> method
│   ├── invokespecial bytecode
│   ├── super() always called (implicit)
│   └── Slot 0 = this reference
│
├── EXECUTION ORDER
│   ├── Static blocks (parent → child, once)
│   └── Instance blocks → constructors (parent → child, each new)
│
└── MODERN
    ├── Records: canonical constructor
    ├── Compact constructor (Records)
    └── Constructor injection (Spring)
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| Constructor | Special method with class name, no return type, invoked by `new` |
| Default constructor | Compiler-generated no-arg constructor; only added when no constructor exists |
| No-arg constructor | Explicitly written constructor with no parameters |
| Parameterized constructor | Constructor accepting arguments to initialize fields |
| Copy constructor | Constructor taking same-class object, creating independent copy |
| Private constructor | Prevents instantiation; used in Singleton, Utility, Factory patterns |
| Constructor chaining | `this()` or `super()` call to another constructor; must be first statement |
| `super()` implicit | Compiler adds `super()` as first line if neither `this()` nor `super()` written |
| `<init>` | JVM bytecode name for constructors |
| Canonical constructor | Auto-generated constructor for Java Records (all components as params) |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"No return = Constructor"** | If it has a return type (even void), it's a method, not a constructor |
| **"No constructor? Free default"** | Compiler gives you a no-arg for free — but only if you define ZERO constructors |
| **"One defined → Free one gone"** | Define any constructor → compiler's free default disappears |
| **"First or fail"** | `this()` and `super()` must be FIRST statement — no exceptions |
| **"this() OR super() — never both"** | One first-line constructor call per constructor |
| **"Copy deep, not shallow"** | Copy constructor must deep-copy mutable fields |
| **"Private + static method = Singleton"** | Private constructor + `getInstance()` = Singleton pattern |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| Constructor | Initialization method with class name, no return type, called by `new` |
| Default constructor | Compiler-generated no-arg constructor; absent if any constructor defined |
| `this()` | Constructor chaining to another constructor of the same class |
| `super()` | Call to parent class constructor; implicit if not written |
| `<init>` | JVM internal name for constructor bytecode |
| Overloading | Multiple constructors with different parameter lists |
| Canonical constructor | Record's auto-generated constructor with all components as parameters |
| Compact constructor | Record constructor without explicit parameter list; validates, then fields auto-assigned |
| Constructor injection | DI pattern where dependencies passed via constructor (preferred in Spring) |
| Copy constructor | Constructor creating an independent copy of another object of the same class |

---

## 27. Interview One-Liners

- "A constructor has the same name as the class, no return type, and is called automatically by `new`."
- "If you define ANY constructor, the compiler stops generating the default no-arg constructor."
- "`super()` is implicitly added as the first statement in every constructor that doesn't have `this()` or `super()`."
- "`this()` and `super()` must be the FIRST statement in a constructor — they cannot coexist in one constructor."
- "Constructors are NOT inherited — every class must define its own or delegate to parent via `super()`."
- "Copy constructors deep-copy mutable fields; `clone()` is shallow by default and requires `Cloneable` — prefer copy constructors."
- "Private constructors prevent instantiation — used in Singleton, Utility classes, and Factory patterns."
- "JPA requires a no-arg constructor (can be `protected`) to instantiate entities via reflection."
- "In JVM bytecode, constructors are compiled as `<init>` methods invoked with `invokespecial`."
- "Never call overridable methods from a constructor — child method runs before child fields are initialized."

---

## 28. Summary

Constructors are the **mandatory initialization mechanism** for Java objects — called exactly once at creation via `new`, they ensure objects start in a valid state. Java has five constructor types: **default** (compiler-generated when no constructor exists), **no-arg** (explicit version, required for JPA), **parameterized** (forces required fields at creation), **copy** (independent duplication — prefer over `clone()`), and **private** (Singleton, Utility, Factory patterns). Constructor **chaining** via `this()` eliminates duplication by delegating to a primary constructor that contains all validation. `super()` is implicitly added if not explicitly written, triggering the parent constructor chain. Key production rules: always validate in constructors, defensively copy mutable params, never call overridable methods, provide `protected` no-arg for JPA entities, and prefer constructor injection in Spring. Java 16 Records introduce **canonical** and **compact** constructors for elegant immutable data classes.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| `super` keyword | Complement to `this()` — parent constructor delegation |
| Builder pattern | Handle many optional constructor parameters elegantly |
| Static factory methods | `List.of()`, `Optional.of()` — alternative to constructors |
| Java Records | Canonical + compact constructors — modern immutable objects |
| Spring constructor injection | `@Autowired` on constructor; `@RequiredArgsConstructor` (Lombok) |
| Java Memory Model (unsafe publication) | Why passing `this` in constructor can be unsafe in multi-threaded code |
| Object cloning | `Cloneable`, `clone()` — compared to copy constructors |

---

---

# TOPIC 10: THE `super` KEYWORD

---

## 1. Overview

| Attribute | Detail |
| ----------- | -------- |
| **What is it?** | `super` is a reference keyword in Java that refers to the **parent (superclass) portion** of the current object. It enables subclasses to access overridden methods, hidden fields, and parent constructors. |
| **Why introduced?** | Without `super`, once a method is overridden, the parent's original implementation is permanently inaccessible from the child class. `super` provides a controlled window into the parent layer. |
| **Problem solved** | Enables subclasses to extend (not completely replace) parent behaviour — the "extend" in inheritance. Also mandates parent constructor call to ensure the parent part is always initialized. |
| **History** | Java 1.0 (1996). Core concept of OOP inheritance. No major changes across Java versions. |
| **Industry importance** | Every Spring controller that extends a base class, every JUnit `@BeforeEach` that calls `super.setUp()`, every JPA entity inheriting from a mapped superclass — all use `super`. |

---

## 2. Intuition

Think of inheritance like a **phone with apps installed on top of the OS**:

- The parent class = operating system (built-in functionality)
- The child class = your custom app layer on top
- `super` = calling an OS function from within your app

When you override `toString()` in your Dog class, you're replacing the default behaviour. But what if you want the Dog's name AND the Object's hash code? Use `super.toString()` to call the parent's version.

`super()` in a constructor = filling out the OS registration form before configuring your app — the OS (parent) must be set up first before the app (child) can run.

**Key insight:** `super` doesn't refer to a different object — it refers to the **same object** (`this`) but accesses the **parent class's version** of a method or field.

---

## 3. Core Concepts

### 3.1 Three Uses of `super`

```
super uses:
1. super()        → call parent constructor (MUST be first statement in child constructor)
2. super.method() → call parent class's version of an overridden method
3. super.field    → access parent class's field (when child hides it with same name)
```

### 3.2 Use 1 — `super()` Constructor Call

```java
class Animal {
    private String species;
    private int lifespan;
    
    public Animal(String species, int lifespan) {
        this.species  = species;
        this.lifespan = lifespan;
        System.out.println("Animal created: " + species);
    }
    
    public String getSpecies() { return species; }
    public int getLifespan()   { return lifespan; }
}

class Dog extends Animal {
    private String breed;
    
    public Dog(String breed) {
        super("Canis lupus familiaris", 13);  // MUST be first statement!
        this.breed = breed;
        System.out.println("Dog created: " + breed);
    }
    
    // Output for new Dog("Labrador"):
    // "Animal created: Canis lupus familiaris"
    // "Dog created: Labrador"
}
```

**Rules for `super()`:**

- Must be the **very first statement** in the constructor
- Mutually exclusive with `this()` — cannot have both as first statement
- If neither `super()` nor `this()` is written, compiler inserts `super()` automatically
- If parent has no no-arg constructor, `super(args)` with matching args MUST be explicit

### 3.3 Use 2 — `super.method()` — Calling Parent's Overridden Method

```java
class Shape {
    public double area() {
        return 0.0;
    }
    
    public String describe() {
        return "I am a Shape with area: " + area();
    }
}

class Circle extends Shape {
    private double radius;
    
    public Circle(double radius) { this.radius = radius; }
    
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
    
    @Override
    public String describe() {
        // Call parent's describe() first, then add own info
        return super.describe() + " [Circle with radius " + radius + "]";
    }
    
    // Output for new Circle(5).describe():
    // "I am a Shape with area: 78.539... [Circle with radius 5.0]"
    // Note: super.describe() → calls Shape.describe()
    //       Shape.describe() calls area() → DYNAMIC DISPATCH → Circle.area()!
}
```

### 3.4 Use 3 — `super.field` — Accessing Hidden Parent Field

```java
class Vehicle {
    String type = "Vehicle";    // package-private field
}

class Car extends Vehicle {
    String type = "Car";        // HIDES parent's 'type' field (not overriding!)
    
    public void showTypes() {
        System.out.println(type);        // "Car"     — child's field
        System.out.println(super.type);  // "Vehicle" — parent's field
        System.out.println(this.type);   // "Car"     — same as just 'type'
    }
}
```

> **Critical interview point:** Fields are **HIDDEN**, not overridden. `super.field` accesses the parent's hidden field. Method overriding uses dynamic dispatch; field access is compile-time (static binding). This is why you should NEVER expose fields publicly and rely on hiding — it's fragile.

### 3.5 Implicit `super()` — Compiler Behaviour

```java
class Parent {
    Parent() {
        System.out.println("Parent no-arg");
    }
    Parent(int x) {
        System.out.println("Parent int: " + x);
    }
}

class Child extends Parent {
    Child() {
        // Compiler inserts: super(); — calls Parent() no-arg
        System.out.println("Child no-arg");
    }
    
    Child(int x) {
        // Compiler inserts: super(); — calls Parent() no-arg (NOT Parent(x)!)
        System.out.println("Child int: " + x);
    }
    
    // Problem: if Parent has NO no-arg constructor, compiler's implicit super() FAILS:
}

class BadParent {
    BadParent(int x) { } // ONLY parameterized constructor
}

class BadChild extends BadParent {
    BadChild() {
        // Compiler tries to add super(); but BadParent() doesn't exist!
        // COMPILE ERROR: implicit super() undefined for BadParent
        super(42);  // must explicitly provide args
    }
}
```

---

## 4. Internal Working

### 4.1 `super` is NOT a Separate Object

```
Common misconception: super creates a new parent object.
REALITY: There is ONE object in memory. 'super' is just a VIEW into
the parent layer of that same object.

Memory (one Dog object):
┌────────────────────────────────────────┐
│ Dog object (heap)                      │
│                                        │
│ [Animal portion:]                      │
│   species:  "Canis lupus familiaris"  │◄── super.getSpecies() accesses here
│   lifespan: 13                        │◄── super.getLifespan() accesses here
│                                        │
│ [Dog portion:]                         │
│   breed: "Labrador"                   │◄── this.breed accesses here
└────────────────────────────────────────┘

Both 'this' and 'super' reference the SAME heap address.
'super' just tells the JVM to use the parent class's method table.
```

### 4.2 Method Resolution with `super`

```java
class A {
    void hello() { System.out.println("A.hello"); }
}
class B extends A {
    @Override void hello() { System.out.println("B.hello"); }
    void test() {
        hello();        // B.hello — dynamic dispatch (this.hello())
        super.hello();  // A.hello — bypasses vtable, direct call to A.hello()
    }
}
class C extends B {
    @Override void hello() { System.out.println("C.hello"); }
    void test2() {
        hello();        // C.hello — dynamic dispatch
        super.hello();  // B.hello — one level up
        // super.super.hello(); // COMPILE ERROR — cannot skip levels!
    }
}
```

> **Interview Trap:** You cannot do `super.super.method()` in Java — each `super` call can only go one level up in the hierarchy. There's no way to skip a middle class's implementation.

### 4.3 Bytecode for `super` calls

```java
// super.describe()
// Bytecode:
aload_0          // push 'this'
invokespecial #5 // Shape.describe() — NOT invokevirtual!
// 'invokespecial' bypasses vtable → always calls the specified class's method
// This is different from 'invokevirtual' which does dynamic dispatch
```

### 4.4 Constructor Chain — Full Stack

```java
class A { A() { System.out.println("A"); } }
class B extends A { B() { System.out.println("B"); } }
class C extends B { C() { System.out.println("C"); } }

new C():
Call Stack:
  C() invoked
    → implicit super() → B() invoked
        → implicit super() → A() invoked
            → implicit super() → Object() invoked
                ← Object() returns
            ← A() continues, prints "A", returns
        ← B() continues, prints "B", returns
    ← C() continues, prints "C", returns

Output: A, B, C (top-down constructor execution)
```

---

## 5. Visual Flow

```
INHERITANCE HIERARCHY AND super CALLS:

Object
  └── Animal (species, lifespan)
        └── Mammal (bloodTemp)
              └── Dog (breed)

new Dog("Labrador"):

Stack:
Dog()
  → super()  → Mammal()
      → super()  → Animal()
          → super()  → Object()
          ← Object returns
          ← Animal: species="Canis", lifespan=13
      ← Mammal: bloodTemp="warm"
  ← Dog: breed="Labrador"

Memory (one object, all layers):
┌───────────────────────────────┐
│ Object layer                  │
│ Animal layer: species, life   │◄── super.getSpecies() from Dog
│ Mammal layer: bloodTemp       │◄── super.getBloodTemp() from Dog
│ Dog layer: breed              │◄── this.breed from Dog
└───────────────────────────────┘

METHOD CALL RESOLUTION:
dog.speak() called from outside:
  → JVM vtable lookup → Dog.speak() (most derived)

super.speak() called from inside Dog:
  → invokespecial → Mammal.speak() (one level up, bypasses vtable)

super.super.speak() from inside Dog:
  → COMPILE ERROR — cannot skip levels
```

---

## 6. Syntax

```java
// 1. super() — parent constructor call (MUST be first)
class Child extends Parent {
    Child() {
        super();              // call parent no-arg constructor
    }
    Child(int x) {
        super(x);             // call parent's int-param constructor
    }
    Child(String s, int x) {
        super(s, x);          // call parent's matching constructor
    }
}

// 2. super.method() — call parent's overridden method
@Override
public String toString() {
    return super.toString() + " [extended info]";
}

@Override
public void processOrder(Order order) {
    super.processOrder(order);  // execute parent logic
    audit(order);               // then add child-specific logic
}

// 3. super.field — access parent's hidden field
void show() {
    System.out.println(super.fieldName);  // parent's field
}

// Combined — typical subclass constructor
class SubClass extends BaseClass {
    private Type additionalField;
    
    SubClass(Type baseParam, Type childParam) {
        super(baseParam);                    // parent init first
        this.additionalField = childParam;   // then child init
    }
}
```

---

## 7. Examples

### Basic — super() in Constructor

```java
class Person {
    private String name;
    private int age;
    
    Person(String name, int age) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name required");
        if (age < 0 || age > 150) throw new IllegalArgumentException("Invalid age");
        this.name = name;
        this.age  = age;
    }
    
    public String getName() { return name; }
    public int    getAge()  { return age; }
    
    @Override public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}

class Employee extends Person {
    private String department;
    private double salary;
    
    Employee(String name, int age, String department, double salary) {
        super(name, age);    // must be FIRST — delegate Person validation
        if (department == null || department.isBlank()) throw new IllegalArgumentException("Dept required");
        if (salary < 0) throw new IllegalArgumentException("Salary negative");
        this.department = department;
        this.salary     = salary;
    }
    
    public String getDepartment() { return department; }
    public double getSalary()     { return salary; }
    
    @Override public String toString() {
        return super.toString() +   // "Person{name='Krish', age=21}"
               ", dept=" + department + ", salary=" + salary;
        // Full: "Person{name='Krish', age=21}, dept=Engineering, salary=80000.0"
    }
}

// Full chain
class Manager extends Employee {
    private int teamSize;
    
    Manager(String name, int age, String dept, double salary, int teamSize) {
        super(name, age, dept, salary);  // calls Employee → calls Person
        this.teamSize = teamSize;
    }
    
    @Override public String toString() {
        return super.toString() + ", manages=" + teamSize + " people";
    }
}
```

### Intermediate — super.method() Extending Behaviour

```java
abstract class DataValidator {
    // Base validation — common to all validators
    public ValidationResult validate(Object data) {
        if (data == null) return ValidationResult.failure("Data cannot be null");
        return ValidationResult.success();
    }
}

class StringValidator extends DataValidator {
    private int maxLength;
    
    StringValidator(int maxLength) { this.maxLength = maxLength; }
    
    @Override
    public ValidationResult validate(Object data) {
        // First run parent's null check
        ValidationResult base = super.validate(data);
        if (!base.isValid()) return base;    // propagate parent failure
        
        // Then add our own specific validation
        String s = (String) data;
        if (s.length() > maxLength)
            return ValidationResult.failure("Max length " + maxLength + " exceeded");
        if (s.isBlank())
            return ValidationResult.failure("String cannot be blank");
        
        return ValidationResult.success();
    }
}

class EmailValidator extends StringValidator {
    EmailValidator() { super(254); }  // RFC 5321: max email = 254 chars
    
    @Override
    public ValidationResult validate(Object data) {
        // Chain: EmailValidator → StringValidator → DataValidator
        ValidationResult base = super.validate(data);   // runs StringValidator checks first
        if (!base.isValid()) return base;
        
        String email = (String) data;
        if (!email.contains("@")) return ValidationResult.failure("Invalid email: missing @");
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[1].isEmpty())
            return ValidationResult.failure("Invalid email domain");
        
        return ValidationResult.success();
    }
}
```

### Advanced — Template Method Pattern with `super`

```java
// Template method pattern: parent defines skeleton, children fill details
abstract class ReportGenerator {
    // Template method — defines the algorithm skeleton
    public final String generateReport(String title, List<String> data) {
        StringBuilder sb = new StringBuilder();
        sb.append(generateHeader(title));
        sb.append(generateBody(data));
        sb.append(generateFooter());
        return sb.toString();
    }
    
    // Common implementation in parent
    protected String generateHeader(String title) {
        return "=== " + title + " ===\n" +
               "Generated: " + LocalDateTime.now() + "\n\n";
    }
    
    // Abstract — subclasses MUST implement
    protected abstract String generateBody(List<String> data);
    
    // Default footer — subclasses CAN override
    protected String generateFooter() {
        return "\n--- End of Report ---";
    }
}

class CSVReport extends ReportGenerator {
    @Override
    protected String generateBody(List<String> data) {
        return String.join(",", data) + "\n";
    }
}

class HTMLReport extends ReportGenerator {
    @Override
    protected String generateHeader(String title) {
        // Extend parent's header with HTML wrapper
        return "<html><body><h1>" + title + "</h1>\n" +
               super.generateHeader(title);   // include parent's timestamp/info
    }
    
    @Override
    protected String generateBody(List<String> data) {
        StringBuilder sb = new StringBuilder("<ul>\n");
        data.forEach(item -> sb.append("<li>").append(item).append("</li>\n"));
        return sb.append("</ul>\n").toString();
    }
    
    @Override
    protected String generateFooter() {
        return super.generateFooter() + "\n</body></html>";  // extend parent footer
    }
}
```

### Production — Spring Boot Inheritance

```java
// Base controller with common behaviour
@RestController
public abstract class BaseController<T, ID> {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    
    public abstract BaseService<T, ID> getService();
    
    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable ID id) {
        log.debug("GET request for id: {}", id);
        return getService().findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable ID id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

// Concrete controller — extends base, adds specific endpoints
@RestController
@RequestMapping("/api/users")
public class UserController extends BaseController<User, Long> {
    private final UserService userService;
    
    // Constructor injection — calls super implicitly (BaseController has no-arg)
    public UserController(UserService userService) {
        // super() added implicitly by compiler
        this.userService = userService;
    }
    
    @Override
    public BaseService<User, Long> getService() { return userService; }
    
    // Add user-specific endpoints
    @GetMapping("/search")
    public List<User> search(@RequestParam String query) {
        log.debug("Search: {}", query);  // uses parent's log field
        return userService.search(query);
    }
    
    // Override parent's findById to add custom response mapping
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        log.debug("User-specific findById: {}", id);
        ResponseEntity<User> result = super.findById(id);  // delegate to parent logic
        // Add user-specific headers, transformations, etc.
        return result;
    }
}
```

---

## 8. Real World Usage

| Framework/Context | `super` Usage |
| ------------------ | -------------- |
| **Spring MVC** | `super.handleRequest()` in custom `DispatcherServlet` extensions |
| **JUnit 5** | `super.setUp()` / `super.tearDown()` in base test class |
| **JPA `@MappedSuperclass`** | `BaseEntity` with `id`, `createdAt`; entity classes call `super.prePersist()` |
| **Android** | `super.onCreate(savedInstanceState)` — MUST call in every Activity lifecycle method |
| **Servlet** | `super.service(request, response)` in `HttpServlet` extensions |
| **Template Method** | Parent's template method calls abstract hooks; child hooks call `super` for base |
| **Lombok `@SuperBuilder`** | Generates builder that respects parent class fields via `super` |
| **Exception classes** | `public MyException(String msg) { super(msg); }` — passes to Throwable |

---

## 9. Internal JVM Perspective

### `invokespecial` — How `super` Bypasses vtable

```
Normal instance method call (invokevirtual):
  dog.speak() → JVM reads vtable of actual class (Dog) → Dog.speak()
  
super.speak() inside Dog (invokespecial):
  → JVM does NOT use vtable
  → Instead: directly calls Animal.speak() from Dog's perspective
  → Compile-time resolved class (Animal) is used, not runtime class
  → This is why super.method() ALWAYS calls the immediate parent's version,
    regardless of further overrides in the hierarchy
```

### Memory — One Object, Multiple Layers

```
new Manager("Krish", 21, "Engineering", 80000.0, 5):

One heap object:
┌───────────────────────────────────────────────┐
│ Object.class pointer → Object metadata        │
│ Person layer:                                 │
│   name: "Krish"                              │
│   age: 21                                    │
│ Employee layer:                               │
│   department: "Engineering"                  │
│   salary: 80000.0                            │
│ Manager layer:                                │
│   teamSize: 5                                │
└───────────────────────────────────────────────┘

super.getName() from Manager:
  → same heap object, resolved to Person's getName() via invokespecial
  → returns "Krish"

super.getDepartment() from Manager:
  → same heap object, resolved to Employee's getDepartment()
  → returns "Engineering"
```

### `super()` JVM Requirement

```
JVM specification requires: before an object can be used, 
Object's <init> must complete (transitively).

Every constructor chain must eventually reach Object.<init>().
If parent has no no-arg constructor and you don't call super(args),
the compiler catches it → compile error.
This guarantees: by the time any constructor body code executes,
ALL parent layers are fully initialized.
```

---

## 10. Time & Space Complexity

| Operation | Time | Notes |
| ----------- | ------ | ------- |
| `super()` call | O(parent init cost) | Runs parent constructor chain |
| `super.method()` | O(parent method cost) | Direct invocation, no vtable lookup |
| `super.field` | O(1) | Direct field read |
| Constructor chain (depth n) | O(sum of all constructors) | Each level runs once |
| `super` reference | O(0) memory | No extra object — same object, different view |

---

## 11. Advantages

- **Reuse parent logic** — `super.method()` extends rather than replaces
- **Mandatory parent init** — `super()` ensures parent portion always initialized
- **Template method** — parent defines skeleton using abstract methods + `super` calls
- **Validation chaining** — child validates → calls `super.validate()` → propagates up
- **Clean `toString()`** — `super.toString() + ", extraField"` composites representations
- **DRY principle** — child adds only delta to parent, shares common logic

---

## 12. Disadvantages

- **Cannot skip levels** — `super.super.method()` is illegal — can't skip intermediate class
- **Fragile super calls** — if parent method changes signature, super call breaks
- **Breaks encapsulation slightly** — child depends on parent's internal implementation
- **Tight coupling** — deep inheritance with heavy `super` usage creates fragile hierarchies
- **Wrong super() call** — calling wrong parent constructor leads to incorrect initialization
- **Testing difficulty** — need to mock parent behaviour when testing child's super calls

---

## 13. Tradeoffs

| Decision | Use `super` | Prefer Composition |
| ---------- | ------------- | ------------------- |
| Extending existing logic | ✅ `super.method()` before/after own logic | — |
| Completely replacing parent | ❌ Don't call super | — |
| Deep inheritance (>3 levels) | ⚠️ Risky — fragile hierarchy | ✅ Prefer composition |
| Parent class you don't control | ⚠️ Risky — parent can change | ✅ Wrap instead |
| Exception classes | ✅ `super(message, cause)` is standard | — |
| JPA base entity fields | ✅ `@MappedSuperclass` + `super()` | — |

---

## 14. Comparison

### `super` vs `this`

| Aspect | `this` | `super` |
| -------- | -------- | --------- |
| Refers to | Current class portion of current object | Parent class portion of current object |
| `this()` | Calls same-class constructor | N/A |
| `super()` | N/A | Calls parent class constructor |
| Field | `this.field` — current class field | `super.field` — parent class field (if hidden) |
| Method | `this.method()` — dynamic dispatch | `super.method()` — static dispatch (parent) |
| First statement? | `this()` must be first | `super()` must be first |
| Exclusive? | `this()` or `super()` — not both | Same |

### `super.method()` vs Override (calling vs replacing)

| Approach | Effect | Use When |
| ---------- | -------- | --------- |
| Override WITHOUT `super.method()` | Completely replaces parent logic | Parent logic not needed/wanted |
| Override WITH `super.method()` first | Runs parent logic, then adds child logic | Extending (parent first) |
| Override WITH `super.method()` last | Adds child logic, then parent logic | Decorating (child first) |
| No override | Inherits parent as-is | No change needed |

### `@Override` + `super` vs Template Method

| Pattern | Mechanism | Best For |
| --------- | ----------- | --------- |
| `@Override` + `super.method()` | Child extends parent by calling super | Simple extension |
| Template method | Parent calls abstract hook methods | Complex algorithm with fixed structure |
| Decorator pattern | Object wraps another (composition) | Deep extension without inheritance |

---

## 15. Common Mistakes

```java
// Mistake 1: super() not first statement
class Child extends Parent {
    Child(int x) {
        System.out.println("before");  // COMPILE ERROR
        super(x);                      // must be first!
    }
}

// Mistake 2: Forgetting super() when parent has no no-arg
class Parent {
    Parent(String name) { this.name = name; }  // no no-arg!
}
class Child extends Parent {
    Child() {
        // COMPILE ERROR: super() implicit but Parent() doesn't exist!
        super("defaultName");  // must explicitly call with args
    }
}

// Mistake 3: super.super.method() — illegal
class Grandchild extends Child {
    void test() {
        super.test();            // OK — Child.test()
        // super.super.test();   // COMPILE ERROR — cannot skip levels
    }
}

// Mistake 4: Calling super() in a class with no parent
class Root {
    Root() {
        super();  // OK — all classes extend Object; calls Object()
    }
}

// Mistake 5: Overriding and forgetting super.method() when needed
class LoggingService extends BaseService {
    @Override
    public void doWork() {
        // Forgot super.doWork()!
        // All base class logic (DB transactions, error handling) LOST!
        log.info("Working...");
    }
    // Fix: add super.doWork() + call at right position
}

// Mistake 6: Field hiding confusion
class A { int x = 10; }
class B extends A {
    int x = 20;  // HIDES A.x (not overrides!)
    void show() {
        System.out.println(x);       // 20 (B.x)
        System.out.println(super.x); // 10 (A.x)
    }
}
A ref = new B();
System.out.println(ref.x); // 10! Field access is compile-time → A.x
// This is why public fields are dangerous
```

---

## 16. Best Practices

1. **Always call `super()` explicitly** when parent lacks a no-arg constructor — don't rely on implicit calls
2. **Chain validation** using `super.validate()` — parent checks first, child adds specifics
3. **Augment `toString()`** with `super.toString()` — "Parent{...}, Child field=value"
4. **Prefer `super.method()` at start of override** for "extend" semantics (decoration at end is rarer)
5. **Document `super` call expectations** — `/** Subclasses MUST call super.init() first */`
6. **Avoid deep inheritance (>3 levels)** — each level adds `super` chain complexity
7. **Prefer composition over inheritance** when `super` calls become tangled
8. **Use `@MappedSuperclass`** (JPA) for shared entity fields (id, timestamps) — use `super()` appropriately
9. **Exception constructors** — always `super(message)`, `super(message, cause)` — chain to parent
10. **Test parent initialization** — verify `super()` is called correctly via integration tests

---

## 17. Interview Section

### Easy

1. What does the `super` keyword refer to in Java?
2. What are the three uses of `super`?
3. When is `super()` automatically inserted by the compiler?
4. Can you call both `this()` and `super()` in the same constructor?
5. What is the output order when `new Child()` is called in a two-level hierarchy?

### Medium

1. What is the difference between method overriding and field hiding? How does `super` interact with each?
2. Explain what `super.toString()` does in a custom class. Why is it useful?
3. What happens if you call `super()` in a class that directly extends Object?
4. What is `invokespecial` bytecode and when is it used vs `invokevirtual`?
5. Why is `super.super.method()` illegal in Java? How do you achieve similar functionality?

### Hard

1. Explain how `super.method()` bypasses virtual dispatch (vtable). What are the implications?
2. How does JVM ensure Object's `<init>` always completes before the object is used?
3. Describe a scenario where calling `super.method()` at the WRONG position in an override causes a bug.
4. What is the Template Method pattern and how does it use `super`?
5. How does `@MappedSuperclass` in JPA use the `super` concept for entity inheritance?

### Very Hard

1. Explain why field access in Java is compile-time (static) while method invocation is runtime (dynamic). What does this mean for `super.field`?
2. How does `super()` work with Java's multi-level inheritance to guarantee the full constructor chain?
3. What is the diamond problem in Java's interface default methods (Java 8+)? How does `super` syntax work there?
4. Explain how `super` interacts with generics and type erasure in a generic superclass.

---

## 18. Coding Questions

### Easy

1. Create `Shape → Circle` hierarchy; `Circle.toString()` calls `super.toString()`.
2. Write a `Pet → Dog` hierarchy where `Dog` must call `super("Dog")` and cannot compile without it.
3. Show the output order for `new Grandchild()` in a 3-level hierarchy with print in each constructor.
4. Demonstrate field hiding: `A.x = 10`, `B extends A { x = 20 }` — show `super.x` vs `x`.
5. Create an exception class `AppException extends RuntimeException` with proper `super(message, cause)`.

### Medium

1. Implement `Animal → Mammal → Dog` where `describe()` composes using `super.describe()` at each level.
2. Build a `Validator` chain where `EmailValidator extends StringValidator extends DataValidator`, each calling `super.validate()`.
3. Implement a base `BaseDao<T>` with `save()` and `findById()` — `UserDao extends BaseDao<User>` adds custom queries.
4. Create `Vehicle → ElectricVehicle` where `ElectricVehicle.fuelCost()` = `super.fuelCost() * 0.3` (electric is cheaper).
5. Implement a `LoggingProxy` style class where `doWork()` logs before calling `super.doWork()`.

### Hard

1. Implement the Template Method pattern: `ReportGenerator` with `CSVReport`, `HTMLReport`, `PDFReport` using `super`.
2. Design a deep 4-level hierarchy with `super()` chaining and verify correct initialization order.
3. Implement `super` correctly in a generic `BaseController<T,ID>` → `UserController` (Spring-style).
4. Build a validation framework with 5 levels of validators, each delegating to `super.validate()`.
5. Create a `MappedSuperclass`-style base entity with `id`, `createdAt`, `updatedAt` and two extending entities.

### Company Level

1. **Android-style:** Implement `Activity → BaseActivity → MainActivity` where each level has a `lifecycle()` method calling `super.lifecycle()`.
2. **Spring:** Design `BaseService → AbstractCrudService → UserService` with `super` calls for common CRUD logic.
3. **JPA:** Implement full `@MappedSuperclass BaseEntity` + 3 entity subclasses with `@PrePersist` calling `super`.
4. **Exception hierarchy:** Design `AppException → ServiceException → ValidationException → FieldValidationException` with proper `super(message, cause)` chains.
5. **Template Method:** Build a multi-format data exporter using `super` calls for shared metadata generation.

---

## 19. Production Scenarios

### Scenario 1: Forgetting `super()` — Partial Initialization

```
Problem: TransactionalService extended BaseService. Developers added a new
field `connectionPool` to BaseService but forgot to update the super() call
in TransactionalService's constructor.
Result: connectionPool was null in TransactionalService → NPE in production
when first transaction attempted.
Fix: Parameterized super() updated; IDE inspection and constructor review process added.
```

### Scenario 2: `super.method()` Position Bug

```java
// ReportService overrides sendReport()
@Override
public void sendReport(Report report) {
    emailService.send(report);        // send email
    super.sendReport(report);          // parent updates status to SENT
    // BUG: if email fails, super still runs → status marked SENT despite failure!
    
    // Fix: parent logic FIRST, then child:
    super.sendReport(report);          // validate + prepare
    emailService.send(report);         // then send
}
```

### Scenario 3: Android `super.onCreate()` Not Called

```
Problem: Developer overrode onCreate() without calling super.onCreate() in Android Activity.
Result: Activity crashed with RuntimeException: "super.onCreate was not called".
Android requires super.onCreate() to initialize the Activity's lifecycle correctly.
Lesson: Some frameworks REQUIRE super.method() — check documentation before omitting it.
```

### Scenario 4: Diamond Problem in Java 8+ Interfaces

```java
// Java 8+ default methods can cause diamond problem
interface A { default void hello() { System.out.println("A"); } }
interface B extends A { default void hello() { System.out.println("B"); } }
interface C extends A { default void hello() { System.out.println("C"); } }

// Class D inherits conflicting defaults from B and C
class D implements B, C {
    // COMPILE ERROR: must override to resolve conflict
    @Override
    public void hello() {
        B.super.hello();  // explicit interface.super syntax!
        C.super.hello();
    }
}
// Special syntax: InterfaceName.super.method() for interface default method resolution
```

---

## 20. Internal Deep Dive

### JVM Specification — Constructor Chain Guarantee

```
JVM spec §2.9: Every instance initialisation method (<init>) of class C
must call either another <init> of C (this()) or an <init> of C's superclass.

The chain MUST eventually call Object.<init>().
The JVM verifier checks this at class-loading time.
If violated, VerifyError is thrown at class load.
This guarantees: no object can exist in a partially-initialized parent state.
```

### Interface Default Method `super` (Java 8+)

```java
// Unlike class super (one parent), interfaces can have multiple parents with defaults.
// Syntax: InterfaceName.super.methodName()

interface Flyable { default void move() { System.out.println("Fly"); } }
interface Swimmable { default void move() { System.out.println("Swim"); } }

class Duck implements Flyable, Swimmable {
    @Override
    public void move() {
        Flyable.super.move();    // "Fly"
        Swimmable.super.move();  // "Swim"
        System.out.println("Waddle");
    }
}
// Three-way diamond resolution using interface.super syntax
```

### Covariant Return Types with `super`

```java
class Animal {
    public Animal create() { return new Animal(); }
}

class Dog extends Animal {
    @Override
    public Dog create() {        // covariant return: Dog is subtype of Animal → valid override
        return new Dog();
    }
    
    public Animal createParent() {
        return super.create();   // calls Animal.create() → returns Animal object
    }
}
```

---

## 21. Frequently Confused Concepts

| Confusion | Clarification |
| ----------- | -------------- |
| `super` creates a new parent object | `super` accesses the SAME object — just the parent portion. ONE object in memory. |
| `super.field` = overriding a field | Fields are HIDDEN, not overridden. `super.field` accesses the hidden field. Field access is compile-time. |
| `super.super.method()` works | ILLEGAL in Java. Each `super` call only goes ONE level up. No skipping. |
| `super()` is optional always | Optional only if parent has a no-arg constructor (implicit). If parent only has parameterized constructors, `super(args)` is mandatory. |
| `super` and `this` refer to different objects | Same object. Different views/scopes of the same object. |
| `super.method()` always calls direct parent | Only if the direct parent has that method. If not, JVM walks up the hierarchy to find it. |
| `super` can be used in static methods | NO. `super` (like `this`) requires an object context. NOT available in static methods. |

---

## 22. Cheat Sheet

```
SUPER KEYWORD — 3 USES:

1. super()  → call parent constructor  (MUST be first statement)
   super(args) → parent's matching constructor
   Implicit super() added if no this() or super() written

2. super.method() → call parent's version of overridden method
   → uses invokespecial (bypasses vtable)
   → always calls DIRECT parent's version, not grandparent

3. super.field → access parent's HIDDEN field
   (rarely needed — avoid public/hidden fields)

RULES:
  ✅ Available in instance methods and constructors
  ❌ NOT in static context (no object)
  ✅ super() must be FIRST statement
  ❌ Cannot call both this() and super()
  ❌ super.super.method() ILLEGAL
  ✅ Interface default: InterfaceName.super.method()

CONSTRUCTOR CHAIN ALWAYS:
  new Child() → Child() → super() → Parent() → super() → Object()
  Object.<init> ALWAYS runs (JVM enforces)

EXECUTION ORDER:
  Static blocks: grandparent → parent → child (each class once)
  Constructors: grandparent → parent → child (each new object)

COMMON PATTERNS:
  toString(): return super.toString() + ", childField=" + childField;
  validate(): super.validate(data) + child-specific checks
  Exception: super(message) or super(message, cause)
```

---

## 23. Mind Map

```
super KEYWORD
│
├── USES
│   ├── super()        → parent constructor (first statement)
│   ├── super.method() → parent's overridden version
│   └── super.field    → parent's hidden field
│
├── RULES
│   ├── Not in static context
│   ├── super() must be FIRST
│   ├── Cannot combine with this()
│   ├── super.super.method() ILLEGAL
│   └── One level up only
│
├── JVM INTERNALS
│   ├── invokespecial (bypasses vtable)
│   ├── Same object, different view
│   └── Object.<init> always runs
│
├── PATTERNS
│   ├── Constructor chaining
│   ├── Extending behaviour (not replacing)
│   ├── Template method
│   └── Validation chaining
│
├── JAVA 8+ INTERFACE
│   └── InterfaceName.super.method() for diamond resolution
│
└── GOTCHAS
    ├── super.super illegal
    ├── Field access static (not dynamic)
    ├── super() mandatory if no parent no-arg
    └── Cannot use in static
```

---

## 24. Revision Table

| Concept | One-line Explanation |
| --------- | --------------------- |
| `super` reference | Refers to parent class portion of current object (same object, different view) |
| `super()` | Calls parent's constructor; must be first statement in child constructor |
| `super.method()` | Calls parent class's overridden version via `invokespecial` (no vtable) |
| `super.field` | Accesses parent's field when child hides it with same name |
| Implicit `super()` | Compiler adds `super()` if no explicit `this()` or `super()` in constructor |
| `super.super` | ILLEGAL — cannot skip inheritance levels |
| `invokespecial` | JVM instruction for `super.method()` — bypasses virtual dispatch table |
| Field hiding | Child field with same name as parent — `super.field` accesses parent's version |
| Method overriding | Child replaces parent's method — `super.method()` accesses parent's original |
| `Interface.super` | Java 8+ syntax for calling specific interface's default method in diamond scenario |

---

## 25. Memory Tricks

| Trick | What to Remember |
| ------- | ----------------- |
| **"super = Parent Layer"** | `super` accesses the parent portion of THE SAME object |
| **"First or Fail"** | `super()` must be the FIRST statement — same rule as `this()` |
| **"One Level Only"** | `super.super` = illegal; super only goes one step up |
| **"Static = No super"** | No object = no super (same rule as `this`) |
| **"Extend not Replace"** | `super.method()` + your code = extend; no `super.method()` = replace |
| **"Object always runs"** | No matter what, `Object.<init>()` always executes — JVM guarantees it |

---

## 26. Important Keywords

| Term | Explanation |
| ------ | ------------- |
| `super` | Reference to parent class portion of current object |
| `super()` | Call to parent class constructor (must be first statement) |
| `super.method()` | Calls parent class's specific implementation of overridden method |
| `super.field` | Accesses parent class field when same-name field exists in child |
| `invokespecial` | JVM bytecode for super method calls — no virtual dispatch |
| Method overriding | Child provides new implementation; `super.method()` can call parent's |
| Field hiding | Child declares field with same name as parent — different from overriding |
| Implicit `super()` | Compiler-inserted call to parent no-arg constructor when nothing explicit |
| Diamond problem | Multiple interface paths to same method — `Interface.super.method()` resolves |
| Template method | Pattern where parent defines algorithm skeleton with `super` calls |

---

## 27. Interview One-Liners

- "`super` refers to the parent class portion of the current object — there is only ONE object in memory."
- "`super()` must be the first statement in a constructor and is added implicitly by the compiler if omitted."
- "`this()` and `super()` cannot both appear in the same constructor — only one first-line call allowed."
- "`super.method()` uses `invokespecial` bytecode — bypasses the vtable, always calls parent's version directly."
- "`super.super.method()` is illegal in Java — can only go one level up per call."
- "`super` cannot be used in static methods — no object context means no parent reference."
- "Fields are hidden (compile-time), not overridden (runtime) — `super.field` accesses the hidden parent field."
- "If the parent class has no no-arg constructor, child constructors MUST explicitly call `super(args)`."
- "Java 8+ interface default methods use `InterfaceName.super.method()` to resolve diamond conflicts."
- "The JVM guarantees `Object.<init>()` always runs — constructor chain must reach Object."

---

## 28. Summary

`super` is Java's mechanism for accessing the **parent class layer** of the current object — one object, multiple layers. Its three uses are: **`super(args)`** to call the parent constructor (must be first statement, mandatory when parent lacks no-arg), **`super.method()`** to access the parent's overridden implementation (uses `invokespecial` — bypasses vtable), and **`super.field`** to access a hidden parent field. The compiler inserts `super()` automatically when no explicit `this()` or `super()` is written, guaranteeing the entire constructor chain from child to `Object` always completes. Critically, `super` is NOT allowed in static methods, `super.super.method()` is illegal (only one level at a time), and field access via `super` resolves at compile time (unlike method overriding which is runtime). Java 8+ added `InterfaceName.super.method()` syntax for resolving diamond conflicts with default interface methods. Prefer using `super.method()` to extend (not replace) parent behaviour — the template method pattern formalises this approach.

---

## 29. Further Learning

| Topic | Why |
| ------- | ----- |
| Inheritance (deep dive) | IS-A relationships, when to inherit vs compose |
| Polymorphism | How dynamic dispatch works; `super` bypasses it |
| Abstract classes | Often used with `super` for template method pattern |
| Interface default methods | Java 8+ `Interface.super` for diamond problem |
| Design Patterns | Template Method, Decorator, Strategy — super-related patterns |
| Java Memory Model | Constructor chain guarantees and thread safety implications |
| Sealed classes (Java 17+) | Restricting class hierarchies — reduces need for complex super chains |

---

---

# MASTER APPENDIX

---

## A. CROSS-TOPIC INTERVIEW RAPID-FIRE CHEAT SHEET

### Data Types + Operators

| Question | Answer |
| ---------- | -------- |
| `5 / 2` result? | `2` (int division truncates) |
| `5.0 / 2` result? | `2.5` (double) |
| `(byte) 200` result? | `-56` (overflow wraps) |
| `Integer.MAX_VALUE + 1` result? | `Integer.MIN_VALUE` (wraps silently) |
| `0.1 + 0.2 == 0.3`? | `false` (IEEE 754 imprecision) |
| `Integer x = 127; Integer y = 127; x == y`? | `true` (Integer cache) |
| `Integer x = 128; Integer y = 128; x == y`? | `false` (outside cache) |
| `-7 % 3` result? | `-1` (sign follows dividend) |
| `Math.floorMod(-7, 3)` result? | `2` (always non-negative) |
| `-1 >>> 1` result? | `Integer.MAX_VALUE` |

### Classes, Objects + Strings

| Question | Answer |
| ---------- | -------- |
| Default value of object reference field? | `null` |
| Default value of `int` field? | `0` |
| `"ab" + "cd" == "abcd"`? | `true` (compile-time constant folding) |
| `new String("hi") == new String("hi")`? | `false` (different heap objects) |
| `"hi" == "hi"`? | `true` (same pool object) |
| Min object overhead in JVM? | 12-16 bytes (Mark Word + Class Pointer) |
| `s.equals(null)` throws? | No — returns `false` |
| `null instanceof String`? | `false` (null-safe) |
| If `a.equals(b)` then `a.hashCode() == b.hashCode()`? | Must be `true` (contract) |

### Static + Encapsulation + Getter/Setter

| Question | Answer |
| ---------- | -------- |
| Where are static variables stored? | Metaspace (Java 8+) |
| When does static block run? | Once, at class load time |
| Can static method access instance fields? | No — no `this` in static context |
| Can static method be overridden? | No — hidden in subclass, not overridden |
| `static final int MAX = 100` — when is value read? | Inlined at compile time (not at runtime) |
| If static block throws, what error? | `ExceptionInInitializerError`; subsequent → `NoClassDefFoundError` |
| Getter for boolean `active`? | `isActive()` not `getActive()` |
| `Collections.unmodifiableList(x)` makes a copy? | No — wraps, original changes reflect |

### this + Constructors + super

| Question | Answer |
| ---------- | -------- |
| `this()` must be at? | First statement in constructor |
| Can `this()` and `super()` coexist in one constructor? | No — mutually exclusive |
| What compiler adds if no `this()` or `super()` in constructor? | `super()` as first line |
| `super.super.method()` legal? | No — compile error |
| Constructor bytecode name? | `<init>` |
| Constructor bytecode instruction? | `invokespecial` |
| `super.method()` uses which bytecode? | `invokespecial` (bypasses vtable) |
| Regular method call uses which bytecode? | `invokevirtual` |
| Static method call uses which bytecode? | `invokestatic` |
| If ANY constructor defined, does compiler add default? | No — default disappears |

---

## B. COMPLETE KEYWORD GLOSSARY

| Keyword/Term | Topic | One-line Explanation |
| ------------- | ------- | --------------------- |
| `byte` | Data Types | 8-bit signed integer (-128 to 127) |
| `int` | Data Types | 32-bit signed integer (most common) |
| `long` | Data Types | 64-bit signed integer (use for IDs, timestamps) |
| `double` | Data Types | 64-bit IEEE 754 floating point (default decimal) |
| `boolean` | Data Types | Logical type: true or false only |
| `char` | Data Types | 16-bit Unicode character |
| `var` | Data Types | Java 10+ local type inference — still statically typed |
| `final` | Data Types | Variable cannot be reassigned after initialization |
| Autoboxing | Data Types | Automatic primitive → wrapper class conversion |
| Unboxing | Data Types | Automatic wrapper class → primitive conversion |
| Integer Cache | Data Types | JVM caches Integer -128 to 127; use `.equals()` beyond |
| Short-circuit | Operators | `&&`/` | | ` stops evaluating if result determined from left |
| `>>>` | Operators | Unsigned right shift — fills 0 regardless of sign |
| `instanceof` | Operators | Null-safe type check; Java 16+ with pattern variable |
| `>>` | Operators | Signed right shift — fills with sign bit |
| Compound assignment | Operators | `+=`, `-=` etc. — includes implicit narrowing cast |
| `class` | Classes/Objects | Keyword to define a class blueprint |
| `new` | Classes/Objects | Allocates heap memory and triggers constructor |
| `null` | Classes/Objects | Absence of object reference; default for reference fields |
| `instanceof` | Classes/Objects | Runtime type check (null-safe) |
| `extends` | Classes/Objects | Subclass relationship declaration |
| `implements` | Classes/Objects | Class implements one or more interfaces |
| `equals()` | Classes/Objects | Value comparison (override from Object) |
| `hashCode()` | Classes/Objects | Integer hash; must be consistent with equals |
| `toString()` | Classes/Objects | String representation of object |
| String Pool | Strings | JVM cache of string literals in heap (Java 7+) |
| `intern()` | Strings | Force string into pool; return canonical reference |
| Immutability | Strings | String content cannot change — every modification = new object |
| `StringBuilder` | Strings | Mutable string builder — NOT thread-safe |
| `StringBuffer` | Strings | Mutable string builder — thread-safe (synchronized) |
| Compact Strings | Strings | Java 9+ byte[] backing — 50% memory saving for ASCII |
| Text Block | Strings | Java 15+ multiline string with `"""` delimiters |
| `static` | Static | Modifier making member belong to class, not instance |
| `static {}` | Static | Static initializer block — runs once at class load |
| Metaspace | Static | JVM memory region (Java 8+) for class metadata + static fields |
| `ExceptionInInitializerError` | Static | Thrown when static initializer block fails |
| `invokestatic` | Static | JVM bytecode for static method calls — no vtable |
| `private` | Encapsulation | Class-only access — most restrictive |
| `protected` | Encapsulation | Package + subclass access |
| `public` | Encapsulation | Accessible everywhere — API contract |
| Defensive copy | Encapsulation/Getters | New copy of mutable to prevent external mutation |
| Anemic model | Encapsulation/Getters | Anti-pattern: getters+setters for all, no domain logic |
| JavaBeans | Getters/Setters | Convention: `getXxx()`/`setXxx()`/`isXxx()` + no-arg ctor |
| Fluent setter | Getters/Setters | Setter returning `this` for method chaining |
| `this` | this keyword | Reference to current object in instance context |
| `this()` | this keyword | Constructor chaining to another same-class constructor |
| Unsafe publication | this keyword | Exposing `this` before construction complete — concurrency bug |
| Constructor | Constructors | Initialization method: class name, no return type, called by `new` |
| Default constructor | Constructors | Compiler-generated no-arg; disappears when ANY ctor defined |
| `<init>` | Constructors | JVM bytecode name for constructor methods |
| Copy constructor | Constructors | Constructor creating independent copy of same-class object |
| `super` | super keyword | Reference to parent class portion of current object |
| `super()` | super keyword | Call to parent constructor; must be first statement |
| `invokespecial` | super keyword | JVM bytecode for `super.method()`, constructors, private methods |
| Field hiding | super keyword | Child field with same name as parent — compile-time binding |
| Diamond problem | super keyword | Multiple interface default method conflict — `Interface.super` resolves |

---

## C. JAVA VERSION QUICK REFERENCE

| Feature | Java Version | Topic |
| --------- | ------------- | ------- |
| Generics | Java 5 | Data Types (wrapper classes) |
| Autoboxing/Unboxing | Java 5 | Data Types |
| Integer cache upper bound configurable | Java 5+ | Data Types |
| `StringBuilder` | Java 5 | Strings |
| Static import | Java 5 | Static |
| Diamond operator `<>` | Java 7 | Classes |
| String pool moved to Heap | Java 7 | Strings |
| Numeric literal underscores `1_000_000` | Java 7 | Data Types |
| Binary literals `0b1010` | Java 7 | Data Types |
| `Objects.requireNonNull()` | Java 7 | Encapsulation |
| `Objects.hash()`, `Objects.equals()` | Java 7 | Classes |
| `String.join()` | Java 8 | Strings |
| `Optional<T>` | Java 8 | Getters |
| Interface default methods | Java 8 | super (diamond) |
| `Math.addExact()`, `Math.floorMod()` | Java 8 | Operators |
| `StringJoiner` | Java 8 | Strings |
| Compact Strings (byte[] backing) | Java 9 | Strings |
| `String.strip()`, `isBlank()`, `lines()`, `repeat()` | Java 11 | Strings |
| `var` local type inference | Java 10 | Data Types |
| `List.copyOf()`, `Map.copyOf()` | Java 10 | Classes |
| `instanceof` pattern matching (preview) | Java 14 | Operators |
| Records (preview) | Java 14 | Classes, Constructors |
| Text Blocks (standard) | Java 15 | Strings |
| Records (standard) | Java 16 | Classes, Constructors |
| `instanceof` pattern matching (standard) | Java 16 | Operators, Classes |
| Sealed classes (standard) | Java 17 | Classes |
| Pattern matching in `switch` (preview) | Java 21 | Operators |
| Virtual threads | Java 21 | (threading topic) |

---

## D. PRODUCTION RULES — THE NON-NEGOTIABLES

```
DATA TYPES:
  ✅ Use long for IDs and timestamps (not int — overflow risk)
  ✅ Use BigDecimal for money (not double — IEEE 754 imprecision)
  ✅ Use char[] for passwords (not String — stays in pool)
  ✅ Use .equals() for all object comparison (not ==)

OPERATORS:
  ✅ Use && and || (short-circuit) for boolean guards
  ✅ Precompile Pattern.compile() for repeated regex
  ✅ Use Math.addExact() for overflow-safe arithmetic
  ✅ Use StringBuilder in loops (not String +=)

STRINGS:
  ✅ Always .equals() — never == for content comparison
  ✅ Put literal on left: "constant".equals(variable) (NPE-safe)
  ✅ Use SLF4J {} placeholders in logs (not + concatenation)
  ✅ Never String.matches() in loops — precompile Pattern

CLASSES:
  ✅ Override equals() AND hashCode() together — never one without other
  ✅ Never use mutable objects as Map/Set keys
  ✅ Prefer composition over inheritance (>3 level hierarchies)
  ✅ Use Records for immutable data classes (Java 16+)

STATIC:
  ✅ All constants: static final UPPER_SNAKE_CASE
  ✅ Utility classes: final + private constructor
  ✅ Mutable static: use AtomicXxx or synchronized
  ✅ static final Logger log = ... per class
  ✅ Never I/O or network calls in static blocks

ENCAPSULATION:
  ✅ All fields private
  ✅ Return defensive copies for mutable fields
  ✅ Accept defensive copies of mutable parameters
  ✅ State transitions via domain methods, not raw setters

GETTERS / SETTERS:
  ✅ Validate in setters before assigning
  ✅ boolean → isXxx(), all others → getXxx()
  ✅ Use @JsonProperty when renaming getters
  ✅ Use Lombok to eliminate boilerplate

THIS:
  ✅ Always this. in setters when param shadows field
  ✅ Never pass this in constructor to external systems
  ✅ Use OuterClass.this for outer reference in anonymous class

CONSTRUCTORS:
  ✅ Validate all params in primary constructor
  ✅ JPA entities: protected no-arg constructor
  ✅ Never call overridable methods in constructors
  ✅ Use Builder for >4 fields or optional params
  ✅ Prefer constructor injection in Spring

SUPER:
  ✅ Explicit super(args) when parent has no no-arg constructor
  ✅ super.validate() pattern for chained validation
  ✅ super.toString() for composed string representations
  ✅ Document when subclasses must call super.method()
```

---

## E. MOST CRITICAL INTERVIEW TRAPS

```
🔴 TRAP 1: Integer Cache Boundary
   Integer a = 127; Integer b = 127; a == b → TRUE
   Integer a = 128; Integer b = 128; a == b → FALSE
   Rule: ALWAYS use .equals() for Integer comparison

🔴 TRAP 2: String Comparison
   new String("hi") == new String("hi") → FALSE
   "hi" == "hi" → TRUE (pool)
   "h" + "i" == "hi" → TRUE (compile-time fold for literals)
   String s = "h"; s + "i" == "hi" → FALSE (runtime concat)

🔴 TRAP 3: Integer Division
   5 / 2 → 2 (NOT 2.5!)
   5.0 / 2 → 2.5
   double r = 5 / 2 → 2.0 (division happens first as int!)

🔴 TRAP 4: Byte Overflow
   (byte) 200 → -56 (no exception! wraps around)
   (byte) 256 → 0 (256 = 0x100, byte = last 8 bits)

🔴 TRAP 5: Compound Assignment Cast
   byte b = 10; b = b + 5; → COMPILE ERROR
   byte b = 10; b += 5; → OK (implicit cast in compound assignment)

🔴 TRAP 6: Static Method "Overriding"
   Animal a = new Dog(); a.staticMethod() → calls Animal.staticMethod()!
   Static methods are HIDDEN, not overridden (compile-time binding)

🔴 TRAP 7: this() Position
   Constructor() { doSomething(); this(1); } → COMPILE ERROR
   this() must be FIRST — no exceptions

🔴 TRAP 8: equals() without hashCode()
   Override equals but not hashCode → HashSet/HashMap BREAKS silently

🔴 TRAP 9: Unboxing null
   Integer x = null; int y = x; → NullPointerException!

🔴 TRAP 10: super.super
   super.super.method() → COMPILE ERROR — cannot skip levels
```

---

## F. COMPLETE EXAM MIND MAP

```
JAVA CORE FUNDAMENTALS
│
├── DATA TYPES
│   ├── Primitives: byte short int long float double char boolean
│   ├── Reference: String, Arrays, Classes, Interfaces
│   ├── Wrappers: Byte Short Integer Long Float Double Character Boolean
│   ├── Integer Cache: -128 to 127 (use .equals()!)
│   ├── Autoboxing/Unboxing (NPE risk on null unbox)
│   ├── BigDecimal for money (not double)
│   └── var (Java 10): local type inference, still static
│
├── OPERATORS
│   ├── Arithmetic: + - * / % (int/int → int, truncates!)
│   ├── Relational: == != > < >= <=
│   ├── Logical: && || ! (SHORT-CIRCUIT ← key)
│   ├── Bitwise: & | ^ ~ << >> >>> (no short-circuit)
│   ├── Assignment: = += -= (compound has implicit cast)
│   ├── Ternary: ?:
│   └── instanceof (null-safe; Java 16 pattern matching)
│
├── CLASSES & OBJECTS
│   ├── Class = blueprint; Object = heap instance
│   ├── new: allocate → defaults → init blocks → constructor
│   ├── Object class: equals() hashCode() toString() getClass()
│   ├── equals() + hashCode() CONTRACT — always together!
│   ├── Object header: 12-16 bytes overhead
│   └── Records (Java 16+): immutable data class
│
├── STRINGS
│   ├── Immutable, final class, byte[] backed (Java 9+)
│   ├── String Pool (heap since Java 7)
│   ├── == vs .equals() (always .equals()!)
│   ├── StringBuilder (not thread-safe, fast)
│   ├── StringBuffer (thread-safe, slower)
│   ├── String +=  loop = O(n²) → use StringBuilder
│   ├── Compact Strings Java 9: LATIN1/UTF16
│   └── Text Blocks Java 15: """..."""
│
├── STATIC
│   ├── Belongs to class (not instance), stored in Metaspace
│   ├── static block: runs once at class load
│   ├── static final: constant, inlined at compile time
│   ├── Cannot use this/super
│   ├── NOT overridden (hidden in subclass)
│   └── Thread safety: use AtomicXxx for mutable statics
│
├── ENCAPSULATION
│   ├── private fields → controlled access via methods
│   ├── Access: private < default < protected < public
│   ├── Defensive copies: getter return + constructor input
│   ├── Immutable = final class + final fields + no setters
│   └── Module system (Java 9+): package-level encapsulation
│
├── GETTERS / SETTERS
│   ├── getXxx() / isXxx() (boolean) / setXxx()
│   ├── Validate in setter, defensive copy in getter
│   ├── Lombok: @Getter @Setter @Data @Value @Builder
│   └── Jackson, JPA, Spring depend on this convention
│
├── THIS KEYWORD
│   ├── Current object reference (slot 0 in frame)
│   ├── Disambiguation: this.field = param
│   ├── this() → constructor chain (FIRST statement)
│   ├── return this → fluent API
│   └── Lambdas: 'this' = enclosing class (not lambda)
│
├── CONSTRUCTORS
│   ├── Same name, no return type, called by new
│   ├── Default: compiler adds only if ZERO ctors defined
│   ├── Parameterized: validate, defensively copy, assign
│   ├── Copy: deep-copy mutable fields
│   ├── Private: Singleton, Utility, Factory
│   ├── this() / super() → FIRST statement only
│   └── Records: canonical + compact constructor
│
└── SUPER KEYWORD
    ├── Parent portion of SAME object
    ├── super(): call parent ctor (first statement, mandatory if no parent no-arg)
    ├── super.method(): invokespecial, bypasses vtable
    ├── super.field: access hidden parent field
    ├── super.super: ILLEGAL
    ├── Static: NO super available
    └── Interface diamond: InterfaceName.super.method()
```

---

## G. FINAL SUMMARY TABLE

| Topic | Core Rule | Critical Trap | Production Tip |
| ------- | ----------- | --------------- | ---------------- |
| Data Types | 8 primitives + reference types | `(byte)200 = -56` silently | `BigDecimal` for money; `long` for IDs |
| Operators | `&&`/` | | ` short-circuit; `&`/` | ` don't | `String +` left-to-right type matters | `StringBuilder` in loops; precompile `Pattern` |
| Classes | Override `equals()` AND `hashCode()` | Objects have 12-16 byte overhead | Use `Records` for immutable data |
| Strings | Immutable; use `.equals()` | `String +=` loop = O(n²) | `StringBuilder`; SLF4J `{}` placeholders |
| Static | Belongs to class; stored in Metaspace | Static not overridden — hidden | Mutable static needs `AtomicXxx` |
| Encapsulation | All fields `private`; validate in setters | Returning mutable internals | Defensive copies in/out; immutable = best |
| Getters/Setters | `getXxx()`/`isXxx()` for boolean | Renaming getter breaks JSON API | Lombok `@Data`/`@Value` eliminates boilerplate |
| `this` | Current object; `this()` must be first | Shadow bug: `name = name` (missing `this.`) | Never pass `this` in ctor to external |
| Constructors | Same name, no return type, called by `new` | Defining one removes compiler default | JPA needs `protected` no-arg; use Builder |
| `super` | Parent layer of same object | `super.super` = illegal | `super()` mandatory when parent has no no-arg |

---

_End of Java Core Fundamentals Handbook_
_Topics: Data Types | Operators | Classes & Objects | Strings | Static | Encapsulation | Getters/Setters | this | Constructors | super_
_Java 8 through Java 21 | Production-grade | Interview-ready | SDE & Senior Backend Engineering_
