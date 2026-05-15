# ☕ Java — Complete Learning Notes

---

> **How to use these notes:**
>
> - Read top to bottom for learning
> - Use headings to jump to a topic while revising
> - Interview Questions sections for mock prep
> - Code examples are runnable — try them in JShell or your IDE

---

# Table of Contents

1. [Java Basics — Expressions, Literals & Operators](#1-java-basics--expressions-literals--operators)
2. [JShell — Java REPL](#2-jshell--java-repl)
3. [How Java Works — JVM, JRE, JDK](#3-how-java-works--jvm-jre-jdk)
4. [The Main Method](#4-the-main-method)
5. [Object-Oriented Programming (OOP) — Overview](#5-object-oriented-programming-oop--overview)
6. [Memory Management — Stack vs Heap](#6-memory-management--stack-vs-heap)
7. [Arrays](#7-arrays)
8. [Strings](#8-strings)
9. [Encapsulation](#9-encapsulation)
10. [Constructors](#10-constructors)
11. [The `static` Keyword](#11-the-static-keyword)
12. [Naming Conventions](#12-naming-conventions)
13. [Inheritance](#13-inheritance)
14. [`super` and `this` Keywords](#14-super-and-this-keywords)
15. [Method Overriding](#15-method-overriding)
16. [Access Modifiers](#16-access-modifiers)
17. [Polymorphism](#17-polymorphism)
18. [The `final` Keyword](#18-the-final-keyword)
19. [The Object Class](#19-the-object-class)
20. [Type Casting — Upcasting & Downcasting](#20-type-casting--upcasting--downcasting)
21. [Wrapper Classes](#21-wrapper-classes)
22. [Abstract Classes](#22-abstract-classes)
23. [Inner Classes](#23-inner-classes)
24. [Interfaces](#24-interfaces)
25. [Exception Handling](#25-exception-handling)
26. [Threads & Concurrency](#26-threads--concurrency)
27. [Collections Framework](#27-collections-framework)

---

---

# 1. Java Basics — Expressions, Literals & Operators

## Introduction

Before writing any Java program, you need to understand the **building blocks** of every statement you write. Every line of code is made up of three fundamental things: **expressions**, **operators**, and **operands (literals/variables)**.

## Why This Concept Exists

Computers ultimately execute arithmetic and logical operations. Expressions give us a human-readable way to express those computations. The Java compiler translates these expressions into machine-level instructions.

---

## 1.1 Literals

> **Definition:** A literal is a **fixed value that you write directly in the code** — it does not change.

Think of a literal as a **hardcoded value** — the exact value you typed, not a result of computation.

### Types of Literals in Java

| Literal Type | Example | Description |
|---|---|---|
| Integer literal | `42`, `-7`, `0` | Whole numbers (type: `int`) |
| Long literal | `100L`, `9999999999L` | Large whole numbers (type: `long`) |
| Float literal | `3.14f`, `2.5F` | Decimal numbers (type: `float`) |
| Double literal | `3.14159`, `2.71828` | Precise decimal numbers (type: `double`) — default for decimals |
| Char literal | `'A'`, `'z'`, `'9'` | Single character in single quotes (type: `char`) |
| String literal | `"Hello"`, `"Java"` | Text in double quotes (type: `String`) |
| Boolean literal | `true`, `false` | Only two possible values (type: `boolean`) |
| Null literal | `null` | Represents absence of object |

```java
public class LiteralsDemo {
    public static void main(String[] args) {
        int age = 25;           // 25 is an integer literal
        double pi = 3.14159;    // 3.14159 is a double literal
        char grade = 'A';       // 'A' is a char literal
        String name = "Alice";  // "Alice" is a String literal
        boolean isJavaFun = true; // true is a boolean literal
        long population = 8000000000L; // L suffix required for long
        float temp = 36.6f;     // f suffix required for float

        System.out.println(name + " is " + age + " years old");
        // Output: Alice is 25 years old
    }
}
```

### Common Mistake with Literals

```java
// ❌ WRONG — Missing suffix
long bigNumber = 9999999999;   // Compile error: integer too large
float price = 99.99;           // Compile error: possible lossy conversion

// ✅ CORRECT
long bigNumber = 9999999999L;  // L tells the compiler it's a long
float price = 99.99f;          // f tells the compiler it's a float
```

---

## 1.2 Operators

> **Definition:** An operator is a **symbol that performs an operation** on one or more values.

The values an operator works on are called **operands**.

```
operand  operator  operand
  5    +    3    = 8
       ↑
   this is an expression
```

### Categories of Operators

#### Arithmetic Operators

| Operator | Name | Example | Result |
|---|---|---|---|
| `+` | Addition | `5 + 3` | `8` |
| `-` | Subtraction | `5 - 3` | `2` |
| `*` | Multiplication | `5 * 3` | `15` |
| `/` | Division | `10 / 3` | `3` (integer division!) |
| `%` | Modulo (remainder) | `10 % 3` | `1` |

```java
int a = 10, b = 3;
System.out.println(a / b);  // Output: 3 (NOT 3.33!)
// Integer division drops the decimal part
// To get decimal result: (double) a / b → 3.333...
```

#### Relational (Comparison) Operators

| Operator | Meaning | Example |
|---|---|---|
| `==` | Equal to | `5 == 5` → `true` |
| `!=` | Not equal | `5 != 3` → `true` |
| `>` | Greater than | `5 > 3` → `true` |
| `<` | Less than | `5 < 3` → `false` |
| `>=` | Greater or equal | `5 >= 5` → `true` |
| `<=` | Less or equal | `3 <= 5` → `true` |

#### Logical Operators

| Operator | Meaning | Example |
|---|---|---|
| `&&` | AND (both true) | `true && false` → `false` |
| `\|\|` | OR (at least one true) | `true \|\| false` → `true` |
| `!` | NOT (reverses) | `!true` → `false` |

#### Assignment Operators

```java
int x = 10;
x += 5;   // same as x = x + 5  → x is now 15
x -= 3;   // same as x = x - 3  → x is now 12
x *= 2;   // same as x = x * 2  → x is now 24
x /= 4;   // same as x = x / 4  → x is now 6
x %= 4;   // same as x = x % 4  → x is now 2
```

#### Unary Operators (Pre/Post Increment & Decrement)

```java
int n = 5;

// Post-increment: use first, then increment
System.out.println(n++);  // prints 5, then n becomes 6
System.out.println(n);    // prints 6

// Pre-increment: increment first, then use
n = 5; // reset
System.out.println(++n);  // n becomes 6 first, then prints 6
```

---

## 1.3 Expressions

> **Definition:** An expression is **any valid combination of literals, variables, operators, and method calls** that produces a value.

```java
// All of these are expressions:
5 + 3           // evaluates to 8
age > 18        // evaluates to true or false
"Hello" + name  // evaluates to a String
Math.sqrt(16)   // evaluates to 4.0
```

---

## Interview Questions — Literals & Operators

1. **What is the difference between `==` and `.equals()` in Java?**
   - `==` compares **references** (memory addresses) for objects. For primitives, it compares values.
   - `.equals()` compares **content/value** of objects.
   - `"hello" == "hello"` may be `true` (string pool), but `new String("hello") == new String("hello")` is `false`.

2. **What is the result of `10 / 3` in Java?**
   - `3` — integer division truncates the decimal. Use `10.0 / 3` or `(double) 10 / 3` for `3.333...`

3. **What is the modulo operator used for?**
   - Finding remainders: checking even/odd (`n % 2 == 0`), cycling through values, etc.

4. **What is the difference between `i++` and `++i`?**
   - `i++` (post-increment): returns current value, then increments.
   - `++i` (pre-increment): increments first, then returns new value.

---

## Summary

- A **literal** is a raw value directly written in code (`42`, `"hello"`, `true`).
- An **operator** performs an action on operands (`+`, `-`, `&&`).
- An **expression** is a combination that evaluates to a value.
- Watch out for integer division (drop the decimal).
- Use `L` for `long` literals and `f` for `float` literals.

---

---

# 2. JShell — Java REPL

## Introduction

**JShell** is Java's **Read-Eval-Print Loop (REPL)** — an interactive command-line tool introduced in **Java 9** that lets you write and execute Java code **line by line** without needing to create a full class or compile a file.

## Why This Concept Exists

Before JShell, to test even a single line of Java code you had to:

1. Create a `.java` file
2. Write a class with a `main` method
3. Compile with `javac`
4. Run with `java`

That's a lot of boilerplate just to see what `5 + 3` evaluates to. JShell solves this — it's a **scratchpad for Java**.

## Real World Usage

- Quick experimentation with Java APIs
- Learning and teaching Java interactively
- Testing small code snippets without a full project
- Exploring library behavior

## How to Use JShell

```bash
# Open terminal and type:
jshell

# You'll see:
# |  Welcome to JShell -- Version 17
# |  For an introduction type: /help intro

jshell> 5 + 3
$1 ==> 8

jshell> String name = "Alice"
name ==> "Alice"

jshell> System.out.println("Hello " + name)
Hello Alice

jshell> int square(int n) { return n * n; }
|  created method square(int)

jshell> square(5)
$5 ==> 25

jshell> /exit   // to quit JShell
```

## Useful JShell Commands

| Command | Purpose |
|---|---|
| `/list` | Shows all snippets entered |
| `/vars` | Lists all declared variables |
| `/methods` | Lists all declared methods |
| `/edit` | Opens an editor for a snippet |
| `/exit` | Exits JShell |
| `/help` | Shows help |

## Interview Questions — JShell

1. **What is JShell and when was it introduced?** — Java REPL tool, introduced in Java 9 (JEP 222).
2. **What does REPL stand for?** — Read, Eval, Print, Loop.
3. **Can you use JShell in production?** — No, it's a development/exploration tool.

---

---

# 3. How Java Works — JVM, JRE, JDK

## Introduction

This is one of the most important foundational concepts in Java. Understanding how Java runs under the hood will make you a significantly better developer and help you in every interview.

## The Core Philosophy — WORA

> **"Write Once, Run Anywhere"** — Java's most celebrated promise.

You write Java code **once** on your machine. That same code runs on Windows, Linux, macOS, Android — **anywhere** — without modification.

How? Let's understand the entire pipeline.

---

## 3.1 The Java Execution Pipeline

```
Your Java Source Code (.java)
           │
           ▼
     [ Java Compiler — javac ]
           │
           ▼
      Bytecode (.class)
           │
     ┌─────┴──────────────────────────┐
     ▼                                ▼
  JVM on Windows           JVM on Linux / macOS
     │                                │
     ▼                                ▼
  Output ✅                        Output ✅
```

### Step-by-step Breakdown

**Step 1: You write Java code**

```java
// Hello.java
public class Hello {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

**Step 2: Compile it**

```bash
javac Hello.java
# This creates: Hello.class (bytecode file)
```

**Step 3: Run it**

```bash
java Hello
# Output: Hello, World!
```

**Step 4: JVM executes the bytecode**

- JVM reads `Hello.class`
- Converts bytecode to machine instructions (via JIT compiler)
- Executes on the current platform

---

## 3.2 What is Bytecode?

> **Bytecode** is an **intermediate, platform-neutral** set of instructions. It's not machine code (binary for a specific CPU), and it's not your original Java source code. It lives in between.

You **cannot** write bytecode directly. You write Java; the compiler generates bytecode.

Think of it like this:

- 🇬🇧 English (your Java source) → translator → 🌐 Universal language (bytecode) → JVM → local machine language
- The "universal language" is understood by JVMs everywhere. Each JVM then converts it to its own machine's language.

```
Bytecode is portable. Machine code is not.
That's Java's secret to "Write Once, Run Anywhere."
```

---

## 3.3 JVM — Java Virtual Machine

> **JVM is a virtual computer that runs inside your real computer.** It understands and executes bytecode.

### Key Facts About JVM

- JVM is **platform-dependent** (there is a different JVM for Windows, Linux, macOS)
- But your **bytecode** is **platform-independent** (same `.class` file runs on all JVMs)
- JVM is **responsible for running** your Java application
- JVM also handles **memory management**, **garbage collection**, **security**

```
╔═════════════════════════════════════════╗
║           JVM Architecture              ║
╠═════════════════════════════════════════╣
║  Class Loader Subsystem                 ║
║  ┌─────────────────────────────────┐   ║
║  │ Bootstrap → Extension → App     │   ║
║  └─────────────────────────────────┘   ║
║                                         ║
║  Runtime Data Areas                     ║
║  ┌──────────┐ ┌──────┐ ┌───────────┐  ║
║  │  Heap    │ │Stack │ │Method Area│  ║
║  └──────────┘ └──────┘ └───────────┘  ║
║                                         ║
║  Execution Engine                       ║
║  ┌──────────────┐ ┌───────────────┐    ║
║  │ Interpreter  │ │ JIT Compiler  │    ║
║  └──────────────┘ └───────────────┘    ║
║                                         ║
║  Garbage Collector                      ║
╚═════════════════════════════════════════╝
```

### JVM's Main Components

| Component | Role |
|---|---|
| **Class Loader** | Loads `.class` files into memory |
| **Bytecode Verifier** | Checks bytecode for security issues |
| **Interpreter** | Executes bytecode line by line |
| **JIT Compiler** | Compiles hot (frequently used) bytecode to native machine code for speed |
| **Garbage Collector** | Automatically frees unused memory |
| **Heap** | Where objects are stored |
| **Stack** | Where method calls and local variables live |

---

## 3.4 JRE — Java Runtime Environment

> **JRE = JVM + Class Libraries + Supporting Files**

JRE is what you need to **run** a Java application. It includes:

- JVM (the executor)
- Core Java class libraries (`java.lang`, `java.util`, etc.)
- Supporting configuration files

```
JRE
 ├── JVM
 ├── java.lang (String, Math, System, ...)
 ├── java.util (ArrayList, HashMap, ...)
 ├── java.io   (File, InputStream, ...)
 └── ... other libraries
```

**Who needs JRE?** → Anyone who wants to **run** a Java app (end users, servers).

---

## 3.5 JDK — Java Development Kit

> **JDK = JRE + Development Tools**

JDK is what **developers** install. It includes everything in JRE plus:

- `javac` — the Java compiler
- `javadoc` — generates documentation
- `jdb` — Java debugger
- `jshell` — REPL tool
- `jar` — packaging tool

```
JDK
 ├── JRE
 │    ├── JVM
 │    └── Libraries
 ├── javac (compiler)
 ├── javadoc
 ├── jshell
 ├── jar
 └── ... other dev tools
```

---

## 3.6 The Big Picture

```
╔══════════════════════════════════════╗
║                JDK                   ║
║  ╔════════════════════════════════╗  ║
║  ║             JRE                ║  ║
║  ║  ╔══════════════════════════╗  ║  ║
║  ║  ║          JVM             ║  ║  ║
║  ║  ╚══════════════════════════╝  ║  ║
║  ║  + Java Libraries              ║  ║
║  ╚════════════════════════════════╝  ║
║  + javac, jshell, jar, javadoc ...  ║
╚══════════════════════════════════════╝
```

| Tool | Contains | Who Needs It |
|---|---|---|
| **JVM** | Execution engine only | Part of JRE |
| **JRE** | JVM + Libraries | End users running Java apps |
| **JDK** | JRE + Dev tools (javac, etc.) | Developers |

---

## 3.7 Platform Independence — The Full Picture

| | Java Source Code | Bytecode | JVM |
|---|---|---|---|
| Platform Independent? | ✅ Yes | ✅ Yes | ❌ No |
| Same across OS? | ✅ Yes | ✅ Yes | ❌ Different per OS |

The **paradox**: Java apps are platform-independent *because* the JVM is platform-dependent. The JVM does the heavy lifting of adapting to each OS, so your code doesn't have to.

---

## 3.8 Java is Strongly Typed

> Java is a **strongly typed** (also called **statically typed**) language.

This means:

- Every variable **must** have a declared type
- The type **cannot change** after declaration
- The compiler enforces types at **compile time**

```java
int age = 25;       // ✅ OK
age = "twenty";     // ❌ Compile error: incompatible types

String name = "Bob";
name = 42;          // ❌ Compile error
```

**Why strong typing?**

- Catches bugs early (at compile time, not runtime)
- Makes code more readable and self-documenting
- Enables better IDE support and refactoring

---

## Interview Questions — JVM / JRE / JDK

1. **Why is Java platform independent?**
   - Java compiles to bytecode (not machine code). Bytecode is platform-neutral. Any JVM on any OS can run it. The JVM handles OS-specific execution.

2. **Is JVM platform independent?**
   - ❌ No. JVM itself is platform-dependent. There's a different JVM for Windows, Linux, macOS. This is intentional — the JVM takes on all the platform-specific work.

3. **What is bytecode?**
   - An intermediate, platform-neutral instruction set produced by `javac`. It lives in `.class` files and is executed by the JVM.

4. **What is the difference between JDK, JRE, and JVM?**
   - JVM: Runs bytecode. Part of JRE.
   - JRE: JVM + libraries. Needed to run Java apps.
   - JDK: JRE + dev tools (javac, etc.). Needed to write and compile Java.

5. **What is WORA?**
   - Write Once, Run Anywhere. You write and compile code once; the resulting bytecode runs on any machine with a JVM.

6. **What is the JIT compiler?**
   - Just-In-Time compiler inside the JVM. It identifies "hot" (frequently executed) bytecode and compiles it directly to native machine code for performance, rather than interpreting it repeatedly.

7. **What is the difference between interpreted and compiled languages?**
   - Java is **both**: source code is compiled to bytecode (compiled), then the JVM interprets or JIT-compiles bytecode at runtime.

---

## Summary

- Java → `javac` → Bytecode (`.class`) → JVM → Output
- Bytecode is **platform independent**; JVM is **platform dependent**
- JVM ⊂ JRE ⊂ JDK
- Developers install **JDK**; end users only need **JRE**
- Java is **strongly typed** — types are enforced at compile time
- WORA = Write Once, Run Anywhere

---

---

# 4. The Main Method

## Introduction

When you run a Java program, the JVM needs a **starting point** — a specific method to begin execution from. That starting point is the `main` method. It has a very specific, rigid signature that the JVM looks for.

## Why This Concept Exists

Your application might have 1,000 files with thousands of methods. The JVM can't guess where to start. You tell it: *"Start here."* The `main` method is that designated entry point.

---

## The Required Signature

```java
public static void main(String[] args) {
    // Your program starts here
}
```

Every word in this signature has a specific purpose:

| Part | What it Means |
|---|---|
| `public` | JVM needs to call this from outside — must be publicly accessible |
| `static` | JVM calls this without creating an object first (explained below) |
| `void` | Main doesn't return anything to the JVM |
| `main` | The specific name the JVM looks for |
| `String[] args` | Command-line arguments passed when running the program |

### Alternative valid signatures

```java
// All of these are valid:
public static void main(String[] args) { }
public static void main(String args[]) { }   // array brackets can go after type or name
static public void main(String[] args) { }   // order of modifiers doesn't matter
```

---

## Why is `main` Static?

This is one of the most common interview questions.

**The problem without `static`:**

- Execution starts from `main`
- If `main` is not static, you'd need an **object** to call it
- But to create an object, you need `main` to have already started
- **Deadlock!** You can't start without an object, and you can't create an object without starting.

**The solution with `static`:**

- Static methods belong to the **class**, not to any object
- JVM can call `ClassName.main(args)` **without creating an object first**
- No chicken-and-egg problem!

```
Without static:             With static:
JVM wants to run main        JVM finds Main.class
     ↓                            ↓
Needs object of Main         Calls Main.main() directly ✅
     ↓                       No object needed!
Needs main to run first
     ↓
DEADLOCK ❌
```

---

## Command-Line Arguments (`String[] args`)

The `args` parameter lets users pass data to the program when running it.

```java
public class Greet {
    public static void main(String[] args) {
        if (args.length > 0) {
            System.out.println("Hello, " + args[0] + "!");
        } else {
            System.out.println("Hello, World!");
        }
    }
}
```

```bash
javac Greet.java
java Greet Alice
# Output: Hello, Alice!

java Greet
# Output: Hello, World!
```

---

## Execution Flow — Multi-file Projects

Real applications have many files. Here's how the JVM handles this:

```
Project with 500 .java files
         │
         ▼
Developer compiles all: javac *.java
         │
         ▼
500 .class files generated
         │
         ▼
Developer specifies entry: java MainApp
         │
         ▼
JVM loads MainApp.class
         │
         ▼
JVM calls MainApp.main(args)
         │
         ▼
main() calls other classes → JVM loads them on demand
```

---

## Interview Questions — Main Method

1. **Why is the `main` method `public`?**
   - The JVM needs to call it from outside the class. `public` ensures it's accessible.

2. **Why is `main` `static`?**
   - To allow JVM to call it without creating an instance of the class — avoiding the circular dependency of needing an object before execution starts.

3. **Can you overload the `main` method?**
   - Yes! You can have multiple `main` methods with different parameters. But only `public static void main(String[] args)` is the entry point the JVM uses.

4. **Can `main` be `private`?**
   - It will compile, but the JVM cannot call it — you'll get a runtime error: `Main method not found`.

5. **What happens if there's no `main` method?**
   - Compile succeeds, but running the class throws: `Error: Main method not found in class`.

---

---

# 5. Object-Oriented Programming (OOP) — Overview

## Introduction

**OOP** is a programming paradigm that organizes code around **objects** — entities that have **properties** (data) and **behaviors** (actions).

Java is fundamentally an OOP language. Almost everything in Java is an object.

## Why OOP Exists

Before OOP, code was written **procedurally** — a long list of instructions from top to bottom. As programs grew larger, this became:

- Hard to maintain
- Hard to understand
- Hard to reuse
- Prone to bugs from uncontrolled data access

OOP solves this by **modeling software like the real world** — in terms of objects that interact.

---

## The Real-World Analogy

Everything around you is an object:

- A **Car** has properties (color, speed, fuel) and behaviors (start, stop, accelerate)
- A **BankAccount** has properties (balance, accountNumber) and behaviors (deposit, withdraw)
- A **Student** has properties (name, age, marks) and behaviors (study, takeExam)

---

## Four Pillars of OOP

```
┌─────────────────────────────────────────────────────┐
│                  4 Pillars of OOP                   │
├──────────────┬──────────────┬──────────────┬────────┤
│ Encapsulation│  Inheritance │ Polymorphism │Abstrac-│
│              │              │              │  tion  │
│ Wrap data +  │ Child class  │ Same name,   │ Hide   │
│ methods in a │ inherits     │ different    │ complex│
│ class; hide  │ from parent  │ behavior     │ details│
│ internals    │ class        │              │        │
└──────────────┴──────────────┴──────────────┴────────┘
```

---

## Classes and Objects

> **Class** = Blueprint / Template
> **Object** = Instance created from the blueprint

```java
// CLASS — the blueprint
class Car {
    // Properties (instance variables)
    String color;
    String brand;
    int speed;

    // Behavior (methods)
    void start() {
        System.out.println(brand + " started!");
    }

    void accelerate(int amount) {
        speed += amount;
        System.out.println("Speed: " + speed + " km/h");
    }
}

// OBJECTS — instances of the blueprint
public class Main {
    public static void main(String[] args) {
        Car car1 = new Car();     // Object 1
        car1.brand = "Toyota";
        car1.color = "Red";
        car1.start();             // Toyota started!
        car1.accelerate(60);      // Speed: 60 km/h

        Car car2 = new Car();     // Object 2 — separate instance
        car2.brand = "BMW";
        car2.color = "Blue";
        car2.start();             // BMW started!
    }
}
```

**Key point:** Each object has its **own copy** of instance variables. `car1.color` and `car2.color` are completely independent.

---

## Interview Questions — OOP Basics

1. **What are the four pillars of OOP?** — Encapsulation, Inheritance, Polymorphism, Abstraction.
2. **What is the difference between a class and an object?** — Class is the blueprint; object is a concrete instance created from it.
3. **Is Java 100% object-oriented?** — No. Java has primitive types (`int`, `char`, etc.) which are not objects. That's why some say Java is "primarily" OOP.

---

---

# 6. Memory Management — Stack vs Heap

## Introduction

Understanding where Java stores data is crucial for understanding bugs, performance, and the behavior of your programs. Java uses two main memory regions: **Stack** and **Heap**.

## Stack Memory

> The **Stack** stores **method call frames** and **local variables** (primitive types and object references).

**Key characteristics:**

- **LIFO (Last In, First Out)** structure
- Each thread has its **own** stack
- Memory is allocated and freed **automatically** when methods are called/returned
- **Fast** to access (contiguous memory)
- **Limited size** — deep recursion causes `StackOverflowError`

```java
public void greet() {
    String message = "Hello"; // 'message' reference is in Stack
                               // "Hello" String object is in Heap
    System.out.println(message);
} // When greet() returns, 'message' is popped off the stack
```

## Heap Memory

> The **Heap** stores all **objects** (including arrays and Strings).

**Key characteristics:**

- **Shared** across all threads
- Objects live here until **Garbage Collector** removes them
- Larger than stack, but slower to access
- Instance variables (fields of objects) live here

---

## Visual Memory Model

```
  STACK                        HEAP
┌─────────────┐            ┌──────────────────────────┐
│ main()      │            │                          │
│  ┌────────┐ │            │  ┌──────────────────┐   │
│  │ car1   │─┼────────────┼─►│ Car Object       │   │
│  │ (ref)  │ │            │  │  color = "Red"   │   │
│  └────────┘ │            │  │  speed = 60      │   │
│  ┌────────┐ │            │  └──────────────────┘   │
│  │ car2   │─┼────────────┼─►│ Car Object       │   │
│  │ (ref)  │ │            │  │  color = "Blue"  │   │
│  └────────┘ │            │  │  speed = 0       │   │
│             │            │  └──────────────────┘   │
│  ┌────────┐ │            │                          │
│  │ age=25 │ │            │  (int lives in stack,    │
│  │ (prim) │ │            │   not here)              │
│  └────────┘ │            └──────────────────────────┘
└─────────────┘
```

---

## Instance Variables vs Local Variables

| Feature | Instance Variable | Local Variable |
|---|---|---|
| **Where declared** | Inside class, outside methods | Inside a method |
| **Stored in** | Heap (inside object) | Stack |
| **Default value** | Yes (`0`, `false`, `null`) | ❌ No — must initialize |
| **Lifetime** | As long as object exists | Until method returns |
| **Access** | Via object (`obj.field`) | Directly by name |

```java
class Student {
    int age;        // instance variable — stored in HEAP, default = 0
    String name;    // instance variable — stored in HEAP, default = null

    void study() {
        int hours = 5; // local variable — stored in STACK, no default!
        // int hours;
        // System.out.println(hours); // ❌ Error: variable might not have been initialized
    }
}
```

---

## Garbage Collection

> **Garbage Collector (GC)** automatically frees heap memory when objects are no longer referenced.

```java
Car car = new Car();  // Object created in Heap
car = null;           // Reference removed — object is now eligible for GC
// GC will eventually reclaim that memory
```

You **don't** manually free memory in Java (unlike C/C++). This prevents memory leaks but means you don't control exactly when memory is freed.

---

## Interview Questions — Memory Management

1. **Where are local variables stored?** — Stack.
2. **Where are objects stored?** — Heap.
3. **What is the difference between Stack and Heap memory?** — Stack: thread-local, LIFO, stores local vars/method frames, auto-freed. Heap: shared, stores objects, GC-managed.
4. **What is a `StackOverflowError`?** — Stack is full, usually due to infinite/very deep recursion.
5. **What is `OutOfMemoryError`?** — Heap is full — too many objects and GC can't free enough.
6. **What is Garbage Collection?** — JVM's automatic memory management that removes unreferenced objects from the heap.
7. **Can you force garbage collection?** — You can suggest it with `System.gc()`, but the JVM decides if/when it runs.

---

---

# 7. Arrays

## Introduction

An **array** is a data structure that stores a **fixed-size, ordered collection of elements of the same type**.

In Java, **arrays are objects** — they live in the heap.

## Why Arrays Exist

Before arrays, if you wanted to store 100 student marks, you'd need 100 separate variables (`mark1`, `mark2`, ...). Arrays let you store multiple values under a single name and access them via an index.

## Syntax

```java
// Declaration
int[] marks;         // preferred style
int marks[];         // also valid (C-style, avoid)

// Declaration + Initialization
int[] marks = new int[5];          // 5 elements, all initialized to 0
String[] names = new String[3];    // 3 elements, all initialized to null

// Declaration + Initialization + Values
int[] scores = {90, 85, 78, 92, 88};       // shorthand (only at declaration)
int[] scores = new int[]{90, 85, 78};      // explicit form (usable anywhere)
```

## Basic Examples

```java
public class ArrayDemo {
    public static void main(String[] args) {
        int[] marks = {85, 90, 78, 92, 88};

        // Access by index (0-based)
        System.out.println(marks[0]);   // 85 (first element)
        System.out.println(marks[4]);   // 88 (last element)

        // Length
        System.out.println(marks.length);  // 5 (note: no parentheses — it's a field, not method)

        // Modify
        marks[2] = 95;
        System.out.println(marks[2]);   // 95

        // Traverse with for loop
        for (int i = 0; i < marks.length; i++) {
            System.out.println("marks[" + i + "] = " + marks[i]);
        }

        // Enhanced for-each loop (cleaner for read-only traversal)
        for (int mark : marks) {
            System.out.print(mark + " ");  // 85 90 95 92 88
        }
    }
}
```

---

## Arrays as Objects (Heap)

```java
int[] arr = new int[3];
// 'arr' is a reference variable on the STACK
// The actual array {0, 0, 0} is an object on the HEAP
// arr points to that object

int[] arr2 = arr;  // arr2 points to the SAME array!
arr2[0] = 99;
System.out.println(arr[0]);  // 99 ← arr is affected too!
```

This is **reference semantics** — a critical concept. When you assign an array to another variable, you copy the **reference**, not the array.

---

## 2D Arrays

```java
int[][] matrix = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

System.out.println(matrix[1][2]); // 6 (row 1, col 2)

// Traverse 2D array
for (int i = 0; i < matrix.length; i++) {          // rows
    for (int j = 0; j < matrix[i].length; j++) {   // columns
        System.out.print(matrix[i][j] + " ");
    }
    System.out.println();
}
```

---

## Drawbacks of Arrays

| Drawback | Details |
|---|---|
| **Fixed size** | Size is set at creation and cannot grow or shrink |
| **Homogeneous** | All elements must be the same type |
| **No built-in methods** | No `sort()`, `add()`, `remove()` without `Arrays` utility class |
| **Slow search** | Must traverse all elements — O(n) linear search |
| **Memory waste** | If you allocate 100 slots but only use 10, 90 slots are wasted |
| **No type safety for mixed types** | `Object[]` is dangerous |

These drawbacks led to the **Collections Framework**.

---

## Common Mistakes

```java
// 1. ArrayIndexOutOfBoundsException
int[] arr = new int[5];
arr[5] = 10; // ❌ Error! Valid indices: 0-4

// 2. Forgetting that length is a field, not method
arr.length();  // ❌ Error
arr.length;    // ✅ Correct

// 3. Null reference
int[] arr2;
arr2[0] = 5;   // ❌ NullPointerException — not initialized!

// 4. Printing array directly
int[] nums = {1,2,3};
System.out.println(nums);   // ❌ Prints something like [I@6d06d69c
System.out.println(java.util.Arrays.toString(nums)); // ✅ [1, 2, 3]
```

---

## Useful Arrays Utility Class

```java
import java.util.Arrays;

int[] nums = {5, 3, 1, 4, 2};
Arrays.sort(nums);                          // Sort in place: [1, 2, 3, 4, 5]
System.out.println(Arrays.toString(nums));  // [1, 2, 3, 4, 5]
int idx = Arrays.binarySearch(nums, 3);    // 2 (index of 3 after sorting)
int[] copy = Arrays.copyOf(nums, nums.length); // Deep copy
```

---

## Performance

| Operation | Time Complexity |
|---|---|
| Access by index | O(1) — constant time |
| Linear search | O(n) |
| Insertion at end | O(1) if space available |
| Insertion at middle | O(n) — shift elements |
| Deletion | O(n) — shift elements |
| Sorting (Arrays.sort) | O(n log n) |

---

## Interview Questions — Arrays

1. **Are arrays objects in Java?** — Yes. They extend `Object` and are created on the heap.
2. **What is the default value of array elements?** — `0` for numeric, `false` for boolean, `null` for objects/Strings.
3. **Can an array store different types?** — Only if declared as `Object[]`. Normally arrays are homogeneous.
4. **What is `ArrayIndexOutOfBoundsException`?** — Thrown when accessing an index < 0 or ≥ length.
5. **What is the difference between `length` and `length()` in Java?** — Arrays use `length` (field). Strings use `length()` (method). `ArrayList` uses `size()` (method).
6. **How do you copy an array?** — `Arrays.copyOf()`, `System.arraycopy()`, or clone().

---

---

# 8. Strings

## Introduction

`String` is arguably the most used class in Java. Understanding how Strings work internally will save you from bugs and help you write efficient code.

## What is a String?

In Java, a `String` is a **sequence of characters**. Unlike in C, Java Strings are **objects** (instances of `java.lang.String`), not arrays of chars (though internally they use a char array or byte array).

```java
String name = "Alice";
// OR
String name = new String("Alice");
```

---

## 8.1 Immutability — The Most Critical String Concept

> **Java Strings are immutable** — once created, their value **cannot be changed**.

When you "modify" a String, you're actually creating a **brand new String** in memory.

```java
String s = "Hello";
s = s + " World"; // Does NOT modify "Hello"
                   // Creates a NEW String "Hello World"
                   // 's' now points to the new String
                   // "Hello" is now orphaned (eligible for GC)
```

**Dry Run:**

```
Step 1: String s = "Hello"
    Heap: ["Hello"] ← s points here

Step 2: s = s + " World"
    Heap: ["Hello"] (orphaned)
           ["Hello World"] ← s now points here

The original "Hello" was NOT changed.
```

### Why are Strings Immutable?

1. **Security** — Strings are used for passwords, file paths, network connections. Immutability prevents malicious changes.
2. **String Pool efficiency** — Multiple variables can safely share the same String object.
3. **Thread safety** — Immutable objects are inherently thread-safe.
4. **HashCode caching** — Hash is computed once and cached, enabling efficient use in `HashMap`/`HashSet`.

---

## 8.2 The String Pool (String Intern Pool)

> The **String Pool** is a special area in the **Heap** (since Java 7; was in PermGen before) where **String literals** are stored and **reused**.

```java
String a = "Hello";   // "Hello" stored in String Pool
String b = "Hello";   // "Hello" already exists — reuses same object!

System.out.println(a == b);      // true (same reference!)
System.out.println(a.equals(b)); // true (same content)

String c = new String("Hello"); // Forces NEW object in heap (outside pool)
System.out.println(a == c);      // false (different objects!)
System.out.println(a.equals(c)); // true (same content)
```

```
String Pool (part of Heap)        Regular Heap
┌────────────────────────────┐   ┌─────────────────┐
│ "Hello" ← a, b point here │   │ "Hello" ← c     │
└────────────────────────────┘   └─────────────────┘
```

**Golden Rule:** Always use `.equals()` to compare String content, never `==`.

---

## 8.3 Common String Methods

```java
String s = "  Hello, World!  ";

s.length()            // 18
s.trim()              // "Hello, World!" (removes leading/trailing whitespace)
s.toLowerCase()       // "  hello, world!  "
s.toUpperCase()       // "  HELLO, WORLD!  "
s.charAt(2)           // 'H' (index 2 of trimmed... wait, 2 is space here)
s.indexOf("World")    // 9
s.contains("Hello")   // true
s.startsWith("  He")  // true
s.endsWith("  ")      // true
s.replace("Hello", "Hi") // "  Hi, World!  "
s.substring(2, 7)     // "Hello"
s.split(", ")         // ["  Hello", "World!  "]
s.isEmpty()           // false
s.isBlank()           // false (Java 11+) — checks whitespace only strings too
```

---

## 8.4 String Concatenation Pitfall

Concatenating Strings with `+` in a loop is **very slow** because each `+` creates a new String object.

```java
// ❌ BAD — O(n²) performance
String result = "";
for (int i = 0; i < 10000; i++) {
    result += "a"; // Creates 10000 String objects!
}

// ✅ GOOD — Use StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 10000; i++) {
    sb.append("a"); // Modifies one object
}
String result = sb.toString();
```

---

## 8.5 Mutable Strings — StringBuilder and StringBuffer

Since `String` is immutable, Java provides **mutable alternatives**:

| Feature | String | StringBuilder | StringBuffer |
|---|---|---|---|
| **Mutable?** | ❌ No | ✅ Yes | ✅ Yes |
| **Thread-safe?** | ✅ Yes (immutable) | ❌ No | ✅ Yes (synchronized) |
| **Performance** | Slowest for concatenation | Fastest | Slower than SB (sync overhead) |
| **When to use** | Fixed strings, constants | Single-thread string building | Multi-thread string building |

```java
// StringBuilder
StringBuilder sb = new StringBuilder("Hello");
sb.append(", World");    // Hello, World
sb.insert(5, "!");       // Hello!, World
sb.delete(5, 6);         // Hello, World
sb.reverse();            // dlroW ,olleH
sb.replace(0, 4, "Hi");  // Hi W ,olleH → wait, positions change with reverse
System.out.println(sb.toString()); // final result

// StringBuffer — same API, but synchronized
StringBuffer sbf = new StringBuffer("Hello");
sbf.append(" World");   // Thread-safe append
```

### Internal Working

`StringBuilder` internally uses a **resizable char array** (`char[]`). When it runs out of space, it creates a **new larger array** (typically doubles in size) and copies contents. This is why it's efficient for concatenation — far fewer object creations than `String +`.

---

## 8.6 String Comparison

```java
String s1 = "Java";
String s2 = "Java";
String s3 = new String("Java");

s1 == s2          // true  — same pool reference
s1 == s3          // false — different object
s1.equals(s3)     // true  — same content
s1.equalsIgnoreCase("java") // true
s1.compareTo(s2)  // 0 (equal), negative (s1 before s2), positive (s1 after s2)
```

---

## Common Mistakes

```java
// ❌ Using == for content comparison
if (input == "yes") { } // May fail even when input is "yes"!

// ✅ Correct
if ("yes".equals(input)) { } // Note: literal first to avoid NPE if input is null

// ❌ NullPointerException
String s = null;
s.equals("hello"); // NPE!

// ✅ Safe comparison
"hello".equals(s); // false, no NPE
```

---

## Interview Questions — Strings

1. **Why are Strings immutable in Java?** — Security, thread safety, String pool reuse, hashcode caching.
2. **What is the String Pool?** — A heap area where String literals are stored and reused to save memory.
3. **Difference between `==` and `.equals()` for Strings?** — `==` compares references, `.equals()` compares content.
4. **Difference between `String`, `StringBuilder`, and `StringBuffer`?** — String: immutable. StringBuilder: mutable, not thread-safe. StringBuffer: mutable, thread-safe.
5. **When would you use `StringBuilder` over `String`?** — When building Strings in loops or when many concatenations are needed.
6. **What is `String.intern()`?** — Forces a String to be placed in the String pool. `new String("hello").intern()` returns the pool reference.
7. **Can a String be null?** — Yes. `String s = null;` is valid, but calling methods on it throws `NullPointerException`.

---

---

# 9. Encapsulation

## Introduction

**Encapsulation** is the practice of **wrapping data (variables) and the methods that operate on that data together** in a single unit (class), and **restricting direct access** to the internal data from outside.

Think of it as putting data in a **protective capsule** — outside code can only interact with data through controlled gates (methods).

## Why Encapsulation Exists

Imagine a bank account where anyone can directly change the balance:

```java
// Without encapsulation — DANGEROUS
account.balance = -1000000; // Anyone can set any value!
```

With encapsulation:

```java
// With encapsulation — SAFE
account.withdraw(500); // Must go through controlled method with validation
```

Encapsulation gives you:

- **Data protection** — prevent invalid states
- **Flexibility** — change internal implementation without affecting external code
- **Maintainability** — clear interface between components
- **Testability** — easier to write unit tests

---

## Implementation in Java

The recipe for encapsulation:

1. Make instance variables **`private`**
2. Provide **`public` getter** methods to read values
3. Provide **`public` setter** methods to modify values (with validation)

```java
public class BankAccount {
    // 1. Private instance variables — hidden from outside
    private String accountNumber;
    private double balance;
    private String owner;

    // 2. Constructor to initialize
    public BankAccount(String accountNumber, String owner, double initialBalance) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        // Validation even in constructor
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }

    // 3. Public getter — read-only access
    public double getBalance() {
        return balance;
    }

    public String getOwner() {
        return owner;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // 4. Controlled modification through methods
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive.");
            return;
        }
        balance += amount;
        System.out.println("Deposited: " + amount + ". New balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds!");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn: " + amount + ". New balance: " + balance);
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("ACC123", "Alice", 1000.0);

        // account.balance = -500; // ❌ Compile error — private!

        account.deposit(500);    // ✅ Deposited: 500.0. New balance: 1500.0
        account.withdraw(200);   // ✅ Withdrawn: 200.0. New balance: 1300.0
        account.withdraw(5000);  // ✅ Insufficient funds!

        System.out.println(account.getBalance()); // 1300.0
    }
}
```

---

## The `this` Keyword

`this` is a **reference to the current object** — the object that called the current method.

```java
public class Student {
    private String name;  // instance variable
    private int age;

    public Student(String name, int age) {
        // Without this: 'name' would refer to the parameter, not the field
        this.name = name; // 'this.name' = instance variable, 'name' = parameter
        this.age = age;
    }

    public void printInfo() {
        System.out.println(this.name + " is " + this.age + " years old");
        // 'this' refers to whichever object called printInfo()
    }
}

Student s1 = new Student("Bob", 20);
Student s2 = new Student("Carol", 22);

s1.printInfo(); // 'this' = s1 → "Bob is 20 years old"
s2.printInfo(); // 'this' = s2 → "Carol is 22 years old"
```

---

## Best Practices for Encapsulation

1. **Always make instance variables `private`** — no exceptions
2. **Provide getters/setters only when needed** — don't blindly create both
3. **Add validation in setters** — protect data integrity
4. **Return defensive copies** for mutable objects (like arrays/collections)
5. **Make the class `public`** so it's accessible; control access at the field level

```java
// ✅ Defensive copy — protects internal array
public int[] getScores() {
    return scores.clone(); // Return copy, not the actual array
}
```

---

## Interview Questions — Encapsulation

1. **What is encapsulation?** — Binding data and methods together in a class and restricting direct access to data.
2. **How is encapsulation achieved in Java?** — Private fields + public getters/setters.
3. **What is the benefit of encapsulation?** — Data protection, flexibility, maintainability.
4. **What is the `this` keyword?** — A reference to the current object.
5. **Can getters modify state?** — They shouldn't. A getter that modifies state violates convention and confuses users.
6. **Is it mandatory to have both getter and setter?** — No. If a field is read-only, have only a getter. No setter prevents modification.

---

---

# 10. Constructors

## Introduction

A **constructor** is a special method that is **called automatically when an object is created**. Its purpose is to **initialize the object's state**.

## Key Facts

- Constructor name **must match the class name** exactly
- Constructor has **no return type** (not even `void`)
- Called automatically with the `new` keyword
- A class can have **multiple constructors** (constructor overloading)
- If you don't write a constructor, Java provides a **default constructor**

## Syntax

```java
class ClassName {
    ClassName() {  // Default constructor
        // initialization
    }

    ClassName(int x, String s) {  // Parameterized constructor
        // initialization using x and s
    }
}
```

---

## Types of Constructors

### 1. Default Constructor (No-arg)

```java
public class Dog {
    String name;
    String breed;

    // Default constructor
    public Dog() {
        name = "Unknown";
        breed = "Mixed";
        System.out.println("Dog object created (default)");
    }
}

Dog d = new Dog(); // Calls default constructor
// Output: Dog object created (default)
System.out.println(d.name); // Unknown
```

### 2. Parameterized Constructor

```java
public class Dog {
    String name;
    String breed;
    int age;

    public Dog(String name, String breed, int age) {
        this.name = name;
        this.breed = breed;
        this.age = age;
    }
}

Dog d1 = new Dog("Buddy", "Labrador", 3);
Dog d2 = new Dog("Max", "Poodle", 5);
```

### 3. Constructor Overloading (Multiple Constructors)

```java
public class Rectangle {
    double width;
    double height;

    // No-arg: creates 1x1 rectangle
    public Rectangle() {
        this(1.0, 1.0); // Calls the two-arg constructor (using this())
    }

    // Square (only one size needed)
    public Rectangle(double side) {
        this(side, side);
    }

    // Full constructor
    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    double area() { return width * height; }
}

Rectangle r1 = new Rectangle();        // 1x1
Rectangle r2 = new Rectangle(5.0);     // 5x5 square
Rectangle r3 = new Rectangle(4.0, 6.0); // 4x6
```

---

## Automatic Default Constructor

If you **don't write any constructor**, Java silently adds:

```java
public Dog() {
    super(); // calls Object's constructor
}
```

But if you write **any** constructor, Java **stops providing** the default one:

```java
class Cat {
    public Cat(String name) { /* ... */ }
    // Java does NOT add Cat() anymore!
}

Cat c = new Cat();       // ❌ Compile error: no suitable constructor
Cat c = new Cat("Whiskers"); // ✅ OK
```

---

## Constructor vs Method

| Feature | Constructor | Method |
|---|---|---|
| Name | Same as class name | Any valid identifier |
| Return type | None (not even void) | Must have return type |
| Called automatically? | ✅ Yes, on `new` | ❌ No, must be called explicitly |
| Purpose | Initialize object | Perform operation |
| Inherited? | ❌ No | ✅ Yes |

---

## Interview Questions — Constructors

1. **What is a constructor?** — A special method that initializes an object when it's created. Same name as class, no return type.
2. **Can a constructor return a value?** — No. It has no return type.
3. **What is constructor overloading?** — Multiple constructors with different parameter lists.
4. **What happens if you don't write a constructor?** — Java provides a default no-arg constructor.
5. **Can a constructor call another constructor?** — Yes, using `this()` (same class) or `super()` (parent class). Must be the first statement.
6. **Can constructors be private?** — Yes! Used in Singleton pattern to prevent outside instantiation.
7. **What is the difference between a constructor and a method?** — Constructor: no return type, same name as class, auto-called on object creation. Method: has return type, any name, called explicitly.

---

---

# 11. The `static` Keyword

## Introduction

The `static` keyword makes a member (variable or method) belong to the **class itself** rather than to any specific object (instance). This is one of Java's most important keywords — deeply connected to memory, the JVM, and design.

## Why `static` Exists

Without `static`, every variable is tied to an object — every object gets its own copy. But sometimes you want data or behavior that is **shared across all objects** or that doesn't need an object at all.

---

## 11.1 Static Variables (Class Variables)

> A static variable is **shared by all objects** of that class. There is only **one copy** in memory, no matter how many objects exist.

```java
public class Employee {
    // Instance variable — each object gets its own copy
    String name;
    double salary;

    // Static variable — ONE copy, shared by all Employee objects
    static String companyName = "TechCorp";
    static int employeeCount = 0;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        employeeCount++; // Counts total employees created
    }
}

Employee e1 = new Employee("Alice", 70000);
Employee e2 = new Employee("Bob", 65000);
Employee e3 = new Employee("Carol", 80000);

System.out.println(Employee.employeeCount); // 3 — access via class name
System.out.println(e1.companyName);         // TechCorp (accessible via object too, but not recommended)
```

**Memory diagram:**

```
STACK              HEAP                     METHOD AREA (Static Area)
┌──────────┐      ┌──────────────────────┐  ┌─────────────────────────┐
│ e1 (ref) │─────►│ Employee Object 1    │  │ companyName = "TechCorp"│
│ e2 (ref) │─┐   │  name = "Alice"      │  │ employeeCount = 3       │
│ e3 (ref) │─┼─┐ │  salary = 70000      │  └─────────────────────────┘
└──────────┘ │ │ └──────────────────────┘   ↑ All objects share this
             │ │ ┌──────────────────────┐   │
             └►│ │ Employee Object 2    │───┘
               │ │  name = "Bob"        │
               │ └──────────────────────┘
               └►┌──────────────────────┐
                 │ Employee Object 3    │
                 │  name = "Carol"      │
                 └──────────────────────┘
```

---

## 11.2 Static Methods

> Static methods **belong to the class** and can be called **without creating an object**.

```java
public class MathUtils {
    // Static method — no object needed
    public static int add(int a, int b) {
        return a + b;
    }

    public static double circleArea(double radius) {
        return Math.PI * radius * radius;
    }
}

// Call without creating an object
int sum = MathUtils.add(5, 3);           // 8
double area = MathUtils.circleArea(7.0); // ~153.93
```

---

## 11.3 Why Can't Static Methods Access Non-Static Variables?

This is a crucial interview question.

```java
class Counter {
    int count = 0;          // instance variable
    static int total = 0;   // static variable

    static void show() {
        System.out.println(count);  // ❌ ERROR!
        System.out.println(total);  // ✅ OK
    }
}
```

**Reason:** When a static method runs, **no object may exist yet**. The JVM doesn't know which object's `count` to use — there could be 0, 1, or many objects. It's **ambiguous**. Static members don't have a "this" reference.

**Workaround — use object reference:**

```java
static void show(Counter c) {
    System.out.println(c.count); // ✅ OK — explicitly told which object
}
```

---

## 11.4 Static Block

> A **static block** runs **once** when the class is first loaded into the JVM — before any objects are created or methods are called.

```java
public class Database {
    static String connectionString;

    // Runs once when class loads
    static {
        System.out.println("Static block: Loading class...");
        connectionString = "jdbc:mysql://localhost:3306/mydb";
        System.out.println("Connection configured: " + connectionString);
    }

    public Database() {
        System.out.println("Constructor: Creating Database object");
    }
}

// Test
Database db1 = new Database();
Database db2 = new Database();

// Output:
// Static block: Loading class...        ← Only once!
// Connection configured: jdbc:mysql://...
// Constructor: Creating Database object  ← Twice (once per object)
// Constructor: Creating Database object
```

---

## 11.5 Class Loading — The Complete Picture

This is advanced JVM knowledge that impresses interviewers.

```
Flow when you do: Database db1 = new Database();

1. JVM checks Class Loader: "Is Database already loaded?"
   └─ No → Load from .class file → Store in Method Area

2. Run static block(s) → only once per class lifetime

3. Allocate object memory in Heap

4. Run constructor → initializes instance variables

5. Return reference → assign to 'db1'

Second object:
   Database db2 = new Database();

1. JVM checks Class Loader: "Is Database already loaded?"
   └─ YES → skip loading, skip static block

2. Allocate object → run constructor → done
```

---

## 11.6 Static Variables in Constructors

You can use and initialize static variables inside a constructor — but remember it's called once per **object**, not once per class:

```java
class Widget {
    static int count = 0;
    int id;

    Widget() {
        count++;         // Update shared counter
        id = count;      // Each object gets unique id
    }
}

Widget w1 = new Widget(); // count=1, w1.id=1
Widget w2 = new Widget(); // count=2, w2.id=2
Widget w3 = new Widget(); // count=3, w3.id=3
```

---

## 11.7 Why is `main` Static? (Full Explanation)

```
The JVM wants to run your program.
It looks for:  Main.main(String[] args)

Without static:
  - main() is an instance method
  - To call main(), JVM needs a Main object
  - To create a Main object, a constructor must run
  - But the program hasn't started yet!
  - DEADLOCK ❌

With static:
  - main() is a class method
  - JVM calls Main.main(args) directly — no object needed
  - Program starts, now you can create objects inside main
  - PROBLEM SOLVED ✅
```

---

## Common Mistakes

```java
// ❌ Accessing static via object reference (works but misleading)
Employee e = new Employee();
e.companyName = "NewCorp"; // Works, but confusing — looks like instance-level change

// ✅ Always access static via class name
Employee.companyName = "NewCorp";

// ❌ Trying to use 'this' in static context
static void show() {
    System.out.println(this); // ❌ Cannot use 'this' in static context
}
```

---

## Best Practices

- Access static members via **class name**, not object reference
- Use static for **utility methods** that don't need object state (`Math.sqrt()`)
- Use static for **constants** (`static final`)
- Avoid overusing static — it creates global state, which is hard to test and can cause bugs in multithreading

---

## Interview Questions — static

1. **What is a static variable?** — A class-level variable shared by all instances. One copy in memory.
2. **What is a static method?** — A method that belongs to the class, callable without an object.
3. **Why can't you use `this` in a static method?** — `this` refers to the current object, but static methods have no associated object.
4. **Why can't static methods directly access instance variables?** — Ambiguity — no way to know which object's variable to access.
5. **What is a static block?** — Code that runs once when the class is first loaded, before any objects are created.
6. **What is the order of execution?** — Static block → Constructor (in order of object creation).
7. **Can static methods be overridden?** — No. Static methods are resolved at compile time (method hiding, not overriding).

---

---

# 12. Naming Conventions

## Introduction

Java has **standard naming conventions** that every Java developer follows. These aren't enforced by the compiler, but they are **industry standards** — violating them makes your code harder to read and will raise flags in code reviews.

## Java Uses Camel Case

**Camel Case** means the first letter of each word (except potentially the first word) is capitalized.

```
camelCase → "camel" + "Case"
PascalCase → "Pascal" + "Case" (also called UpperCamelCase)
```

---

## Convention Rules

| Type | Convention | Example |
|---|---|---|
| **Class** | PascalCase (UpperCamelCase) | `Student`, `BankAccount`, `OrderService` |
| **Interface** | PascalCase (often adjective) | `Runnable`, `Serializable`, `Comparable` |
| **Method** | camelCase, verb | `getName()`, `calculateTax()`, `sendEmail()` |
| **Variable** | camelCase, noun | `firstName`, `totalAmount`, `studentList` |
| **Constant** | ALL_CAPS with underscores | `MAX_SIZE`, `PI`, `DEFAULT_TIMEOUT` |
| **Package** | all lowercase | `com.company.project`, `java.util` |
| **Enum** | PascalCase (values ALL_CAPS) | `enum Day { MONDAY, TUESDAY }` |

---

## Examples

```java
// Package — all lowercase
package com.techcorp.banking;

// Class — PascalCase
public class BankAccount {

    // Constants — ALL_CAPS
    public static final double INTEREST_RATE = 0.05;
    public static final int MAX_WITHDRAWAL_LIMIT = 10000;

    // Instance variables — camelCase
    private String accountNumber;
    private String holderName;
    private double currentBalance;

    // Methods — camelCase, descriptive verbs
    public double getCurrentBalance() {
        return currentBalance;
    }

    public void depositAmount(double amount) {
        currentBalance += amount;
    }

    private boolean isValidAmount(double amount) {
        return amount > 0 && amount <= MAX_WITHDRAWAL_LIMIT;
    }
}

// Interface — PascalCase, usually adjective
interface Printable {
    void print();
}

// Enum — PascalCase, values ALL_CAPS
enum AccountType {
    SAVINGS, CHECKING, FIXED_DEPOSIT;
}
```

---

## Why Naming Conventions Matter

1. **Readability** — Anyone can understand your code at a glance
2. **Professionalism** — Required in real-world companies
3. **IDE support** — IDEs use conventions for code generation
4. **Collaboration** — Team members expect conventions to be followed
5. **Interviews** — Interviewers notice when you follow (or ignore) conventions

---

---

# 13. Inheritance

## Introduction

**Inheritance** allows a class (child/subclass) to **acquire properties and behaviors** from another class (parent/superclass). It enables **code reuse** and models the "is-a" relationship.

```
Animal (parent)
  ├── Dog (child) — "A Dog IS-AN Animal"
  ├── Cat (child) — "A Cat IS-AN Animal"
  └── Bird (child) — "A Bird IS-AN Animal"
```

## Why Inheritance Exists

Without inheritance, you'd repeat the same code in every class. If `Dog`, `Cat`, and `Bird` all need `name`, `age`, and `eat()`, you'd write those three times. With inheritance, write once in `Animal`, inherit everywhere.

## Syntax

```java
class ChildClass extends ParentClass {
    // Child gets everything from Parent
    // Can add new features
    // Can override parent's behavior
}
```

---

## Basic Example

```java
// Parent class
class Animal {
    String name;
    int age;

    public void eat() {
        System.out.println(name + " is eating.");
    }

    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
}

// Child class — inherits from Animal
class Dog extends Animal {
    String breed;

    public void bark() {
        System.out.println(name + " says: Woof!");
    }
}

class Cat extends Animal {
    boolean isIndoor;

    public void purr() {
        System.out.println(name + " is purring.");
    }
}

// Usage
public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        dog.name = "Buddy";    // Inherited from Animal
        dog.age = 3;           // Inherited from Animal
        dog.breed = "Labrador"; // Dog's own field
        dog.eat();             // Inherited method: "Buddy is eating."
        dog.bark();            // Dog's own method: "Buddy says: Woof!"

        Cat cat = new Cat();
        cat.name = "Whiskers";
        cat.sleep();           // Inherited: "Whiskers is sleeping."
        cat.purr();            // Cat's own: "Whiskers is purring."
    }
}
```

---

## Multi-level Inheritance

```java
class Animal { void breathe() { System.out.println("Breathing"); } }

class Mammal extends Animal { void nurseYoung() { System.out.println("Nursing"); } }

class Dog extends Mammal { void bark() { System.out.println("Woof!"); } }

Dog d = new Dog();
d.breathe();    // From Animal (grandparent)
d.nurseYoung(); // From Mammal (parent)
d.bark();       // Dog's own
```

---

## Why Multiple Inheritance is NOT Allowed in Java

Java **does not allow a class to extend more than one class**. This is intentional.

The reason is the **Diamond Problem**:

```
           A
          / \
         B   C
          \ /
           D  ← Which method does D inherit if both B and C override A's method?
```

```java
class A { void show() { System.out.println("A"); } }
class B extends A { void show() { System.out.println("B"); } }
class C extends A { void show() { System.out.println("C"); } }

// If this were allowed:
class D extends B, C { } // ❌ Java doesn't allow this

D d = new D();
d.show(); // Which show()? B's or C's? AMBIGUOUS!
```

Java avoids this ambiguity by forbidding multiple inheritance of classes. However, multiple inheritance **through interfaces** is allowed (and is solved differently).

---

## What is NOT Inherited

| Not Inherited |
|---|
| `private` members of the parent |
| Constructors |

```java
class Parent {
    private int secret = 42;        // NOT inherited
    public int visible = 10;        // Inherited

    public Parent() { }             // NOT inherited
    public void show() { }          // Inherited
}

class Child extends Parent {
    void test() {
        System.out.println(visible); // ✅ 10
        System.out.println(secret);  // ❌ Compile error — private!
    }
}
```

---

## Interview Questions — Inheritance

1. **What is inheritance?** — Mechanism where a child class acquires properties and behaviors of a parent class.
2. **What does `extends` mean in Java?** — That the child class inherits from the parent class.
3. **Does Java support multiple inheritance?** — Not for classes (to avoid the Diamond Problem). Yes for interfaces.
4. **What is the Diamond Problem?** — Ambiguity when a class inherits from two classes that have the same method.
5. **Are constructors inherited?** — No. But they're called via `super()`.
6. **Can a class extend itself?** — No. That would cause infinite recursion.
7. **What is the difference between IS-A and HAS-A?** — IS-A = inheritance (`Dog extends Animal`). HAS-A = composition (`Car has Engine`).

---

---

# 14. `super` and `this` Keywords

## Introduction

- **`this`** — refers to the **current object**
- **`super`** — refers to the **parent (super) class**

Both are used to avoid ambiguity and to explicitly call constructors or methods.

---

## `this` — Complete Usage

```java
class Employee {
    String name;
    int salary;

    // Use 1: Resolve naming conflict in constructor
    Employee(String name, int salary) {
        this.name = name;     // 'this.name' = field, 'name' = parameter
        this.salary = salary;
    }

    // Use 2: Call another constructor in same class
    Employee(String name) {
        this(name, 30000); // Calls Employee(String, int)
        // MUST be first statement!
    }

    // Use 3: Return current object (method chaining)
    Employee setName(String name) {
        this.name = name;
        return this; // Returns the current object
    }

    // Use 4: Pass current object to a method
    void register() {
        EmployeeDatabase.add(this); // Passes current object
    }
}
```

---

## `super` — Complete Usage

```java
class Animal {
    String name;
    int age;

    Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    void describe() {
        System.out.println("Animal: " + name + ", Age: " + age);
    }
}

class Dog extends Animal {
    String breed;

    Dog(String name, int age, String breed) {
        super(name, age); // Use 1: Call parent constructor — MUST be first statement!
        this.breed = breed;
    }

    void describe() {
        super.describe(); // Use 2: Call parent's version of the same method
        System.out.println("Breed: " + breed); // Add dog-specific info
    }

    void showParentField() {
        System.out.println(super.name); // Use 3: Access parent's field (if shadowed)
    }
}

Dog d = new Dog("Rex", 5, "German Shepherd");
d.describe();
// Output:
// Animal: Rex, Age: 5
// Breed: German Shepherd
```

---

## The Invisible `super()` Call

**Every constructor in Java has an implicit `super()` as its first statement**, unless you explicitly write `super(...)` or `this(...)`.

```java
class A {
    A() {
        // super() here — calls Object's constructor
        System.out.println("A constructor");
    }
}

class B extends A {
    B() {
        // super() is automatically here, calling A()!
        System.out.println("B constructor");
    }
}

class C extends B {
    C() {
        // super() → B() → A() → Object()
        System.out.println("C constructor");
    }
}

new C();
// Output:
// A constructor  ← called first (via super chain)
// B constructor
// C constructor
```

This chain goes all the way up to `Object` — the root of all Java classes.

---

## Key Rules

| Rule | Details |
|---|---|
| `super()` and `this()` must be **first statement** | Cannot have both in same constructor |
| `super()` calls **parent constructor** | Implicitly added if you don't write it |
| `this()` calls **current class constructor** | Used for constructor chaining |
| `super.method()` calls **parent's method** | Useful in overriding |

---

## Interview Questions — super & this

1. **What is `this` in Java?** — A reference to the current object.
2. **What is `super` in Java?** — A reference to the parent class's members.
3. **Can you have both `super()` and `this()` in the same constructor?** — No. Both must be the first statement, so they can't coexist.
4. **Is `super()` always called?** — Yes. If not explicitly written, Java adds it implicitly as the first statement.
5. **What happens if a parent class has no no-arg constructor but the child doesn't explicitly call `super(...)`?** — Compile error. Java tries to call `super()` implicitly, but if it doesn't exist, compilation fails.

---

---

# 15. Method Overriding

## Introduction

**Method Overriding** occurs when a **child class provides its own implementation** of a method already defined in the parent class.

This is the mechanism behind **Runtime Polymorphism**.

## Rules for Overriding

1. **Same method name** as parent
2. **Same parameter list** (same signature)
3. **Return type** must be same or a subtype (covariant return)
4. Access modifier must be **same or more accessible** (can't reduce visibility)
5. Can't override `static`, `final`, or `private` methods
6. `@Override` annotation is strongly recommended

---

## Basic Example

```java
class Shape {
    public double area() {
        return 0.0; // Default
    }

    public void describe() {
        System.out.println("I am a shape.");
    }
}

class Circle extends Shape {
    double radius;

    Circle(double radius) { this.radius = radius; }

    @Override
    public double area() {
        return Math.PI * radius * radius; // Overrides parent's area()
    }

    @Override
    public void describe() {
        System.out.println("I am a circle with radius " + radius);
    }
}

class Rectangle extends Shape {
    double width, height;

    Rectangle(double w, double h) { width = w; height = h; }

    @Override
    public double area() {
        return width * height;
    }
}
```

---

## Why `@Override` is Important

```java
class Parent {
    public void display() { System.out.println("Parent"); }
}

class Child extends Parent {
    // WITHOUT @Override — is this overriding or a new method?
    public void dispaly() { // Typo! 'dispaly' not 'display'
        System.out.println("Child");
    }
    // Java thinks it's a new method. No error. But override doesn't happen!

    @Override
    public void dispaly() { // ❌ Compile error: method does not override
        // @Override catches the bug immediately
    }
}
```

`@Override` is a **compile-time safety net**. Always use it.

---

## What's in `java.lang.Object`?

Every class extends `Object` by default. The most commonly overridden `Object` methods:

```java
class Student {
    String name;
    int id;

    Student(String name, int id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', id=" + id + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return id == s.id && name.equals(s.name);
    }

    @Override
    public int hashCode() {
        return 31 * id + (name != null ? name.hashCode() : 0);
    }
}

Student s1 = new Student("Alice", 101);
System.out.println(s1);            // Calls toString(): Student{name='Alice', id=101}
System.out.println(s1.equals(s2)); // Uses custom equals logic
```

**Note:** `java.lang.*` is imported automatically in every Java file.

---

## Method Overriding vs Method Overloading

| Feature | Overriding | Overloading |
|---|---|---|
| **Where** | Child class overrides parent | Same class, different parameters |
| **Signature** | Same name, same params | Same name, different params |
| **Binding** | Runtime (dynamic) | Compile time (static) |
| **Return type** | Same or covariant | Can differ |
| **Polymorphism type** | Runtime polymorphism | Compile-time polymorphism |

```java
// Overloading — same class, different params
class Calculator {
    int add(int a, int b) { return a + b; }
    double add(double a, double b) { return a + b; }       // ✅ Overloaded
    int add(int a, int b, int c) { return a + b + c; }    // ✅ Overloaded
}
```

---

## Interview Questions — Method Overriding

1. **What is method overriding?** — Child class providing its own implementation of a method inherited from the parent.
2. **What is the `@Override` annotation for?** — Tells the compiler to verify this method actually overrides a parent method. Catches typos.
3. **Can you override a `private` method?** — No. Private methods are not visible to child classes.
4. **Can you override a `static` method?** — No. Static methods can be hidden (method hiding), not overridden.
5. **Can you override a `final` method?** — No. `final` methods cannot be overridden.
6. **What is covariant return type?** — Overriding method can return a subtype. e.g., parent returns `Animal`, child returns `Dog`.

---

---

# 16. Access Modifiers

## Introduction

**Access modifiers** control the **visibility** and **accessibility** of classes, methods, and fields. Java has four levels:

| Modifier | Keyword | Scope |
|---|---|---|
| Public | `public` | Accessible **everywhere** |
| Protected | `protected` | Same class + same package + subclasses (other packages) |
| Default | *(no keyword)* | Same package only |
| Private | `private` | Same class only |

---

## Visual Comparison

```
                    Same    Same     Subclass  Other
                    Class   Package  (other    Package
                            package)
──────────────────────────────────────────────────────
private             ✅      ❌        ❌          ❌
(default/package)   ✅      ✅        ❌          ❌
protected           ✅      ✅        ✅          ❌
public              ✅      ✅        ✅          ✅
```

---

## Detailed Examples

```java
// File: com/example/Parent.java
package com.example;

public class Parent {
    private int secret = 1;      // Only Parent class
    int packageVar = 2;          // Same package (default)
    protected int protectedVar = 3; // Package + subclasses
    public int publicVar = 4;    // Everywhere

    private void privateMethod() { }
    void packageMethod() { }
    protected void protectedMethod() { }
    public void publicMethod() { }
}
```

```java
// Same package — com/example/Child.java
package com.example;

public class Child extends Parent {
    void test() {
        // secret        ❌ Cannot access private
        System.out.println(packageVar);    // ✅ Same package
        System.out.println(protectedVar);  // ✅ Same package + subclass
        System.out.println(publicVar);     // ✅ Always
    }
}
```

```java
// Different package — com/other/ExternalChild.java
package com.other;
import com.example.Parent;

public class ExternalChild extends Parent {
    void test() {
        // secret        ❌ Cannot access private
        // packageVar    ❌ Different package, not subclass access
        System.out.println(protectedVar);  // ✅ Subclass in different package
        System.out.println(publicVar);     // ✅ Always
    }
}
```

---

## Best Practices for Access Modifiers

```
✅ Classes → public (one public class per file)
✅ Instance variables → private (ALWAYS)
✅ Methods → public (most of the time)
✅ Helper/internal methods → private
✅ Methods for subclasses only → protected
❌ Avoid default — be explicit about intent
```

```java
public class Customer {
    // ✅ Private fields — encapsulation
    private String name;
    private String email;
    private String password; // Never expose this!

    // ✅ Public methods — interface to outside world
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // ✅ Private helper — internal use only
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    // ✅ Protected — subclasses may need this
    protected void logActivity(String action) {
        System.out.println("[LOG] " + name + ": " + action);
    }
}
```

---

## One Public Class Per File Rule

Java allows only **one public class per `.java` file**, and the filename must match the public class name.

```java
// File: MyClass.java
public class MyClass { }  // ✅ — filename matches
class Helper { }          // ✅ — non-public, allowed in same file

// class AnotherPublic { } // ❌ Can't have two public classes in one file
```

---

## Interview Questions — Access Modifiers

1. **What are the four access modifiers in Java?** — `private`, default (no keyword), `protected`, `public`.
2. **What is the default access modifier?** — Package-private — accessible within the same package only.
3. **Where can a `protected` member be accessed?** — Same class, same package, and subclasses in other packages.
4. **Can a `private` member be inherited?** — No. Private members are not inherited (but they exist in the child object's memory).
5. **Can a class be `private`?** — Top-level classes cannot be private. Inner classes can.
6. **Why should instance variables be private?** — Encapsulation — to control access and prevent invalid state.

---

---

# 17. Polymorphism

## Introduction

**Polymorphism** comes from Greek: *"poly"* (many) + *"morphe"* (form). In OOP, it means **one entity behaving in multiple ways depending on the context**.

In Java, polymorphism allows a single variable/method to work with multiple types, and behavior changes based on the actual object.

---

## Two Types of Polymorphism

```
Polymorphism
├── Compile-time (Static) Polymorphism
│    └── Method Overloading
│         - Decided at compile time
│         - Same name, different parameters
│
└── Runtime (Dynamic) Polymorphism
     └── Method Overriding + Dynamic Method Dispatch
          - Decided at runtime
          - Same name, same parameters, different objects
```

---

## 17.1 Compile-time Polymorphism — Method Overloading

The compiler determines which method to call at **compile time** based on the **number and types of arguments**.

```java
class Printer {
    // Same name, different parameters
    void print(int n) {
        System.out.println("Printing int: " + n);
    }

    void print(double d) {
        System.out.println("Printing double: " + d);
    }

    void print(String s) {
        System.out.println("Printing String: " + s);
    }

    void print(int a, int b) {
        System.out.println("Printing two ints: " + a + ", " + b);
    }
}

Printer p = new Printer();
p.print(42);          // Printing int: 42
p.print(3.14);        // Printing double: 3.14
p.print("Hello");     // Printing String: Hello
p.print(1, 2);        // Printing two ints: 1, 2
```

**What makes overloading different (NOT overloading):**

```java
// ❌ NOT overloading — only return type differs
int getValue() { return 1; }
double getValue() { return 1.0; } // Compile error — same signature!
```

---

## 17.2 Runtime Polymorphism — Dynamic Method Dispatch

### The Key Concept

> In Java, you can create a **parent class reference** pointing to a **child class object**.

```java
Animal a = new Dog(); // Parent reference, Child object
```

When you call an overridden method through this reference, Java decides **at runtime** (not compile time) which version to call, based on the **actual object type** (Dog), not the reference type (Animal).

This decision-making at runtime is called **Dynamic Method Dispatch**.

---

### Complete Example

```java
class A {
    public void show() {
        System.out.println("In A show");
    }
}

class B extends A {
    @Override
    public void show() {
        System.out.println("In B show");
    }
}

class C extends A {
    @Override
    public void show() {
        System.out.println("In C show");
    }
}

public class PolymorphismDemo {
    public static void main(String[] args) {
        A obj;  // Parent reference — can hold A, B, or C objects

        obj = new A();
        obj.show();  // In A show — obj is actually an A object

        obj = new B();
        obj.show();  // In B show — obj is actually a B object, B's show() called

        obj = new C();
        obj.show();  // In C show — obj is actually a C object, C's show() called

        // The REFERENCE type is A, but behavior depends on OBJECT type
    }
}
```

---

### Real-World Example — Payment Processing

```java
abstract class PaymentMethod {
    abstract void processPayment(double amount);
}

class CreditCard extends PaymentMethod {
    @Override
    void processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Credit Card");
    }
}

class PayPal extends PaymentMethod {
    @Override
    void processPayment(double amount) {
        System.out.println("Processing $" + amount + " via PayPal");
    }
}

class UPI extends PaymentMethod {
    @Override
    void processPayment(double amount) {
        System.out.println("Processing $" + amount + " via UPI");
    }
}

// One method handles all payment types — polymorphism!
public class Checkout {
    public static void checkout(PaymentMethod payment, double amount) {
        payment.processPayment(amount); // Runtime decides which processPayment()
    }

    public static void main(String[] args) {
        checkout(new CreditCard(), 199.99); // Processing $199.99 via Credit Card
        checkout(new PayPal(), 49.50);       // Processing $49.50 via PayPal
        checkout(new UPI(), 10.00);          // Processing $10.0 via UPI
    }
}
```

This is how real systems work — payment gateways, notification systems, rendering engines all use this pattern.

---

### Why Can't Unrelated Classes Use This?

```java
class D {} // Does NOT extend A

A obj = new D(); // ❌ Compile error: incompatible types
```

Dynamic Method Dispatch only works through the **inheritance chain**. `D` has no relationship with `A`, so the assignment is illegal.

---

## How Dynamic Method Dispatch Works Internally (JVM)

```
Compile time:
  obj.show() is compiled. Compiler sees 'obj' is type A.
  It just records: "call some show() on obj"

Runtime:
  JVM looks at what obj ACTUALLY points to.
  obj → B object?
  JVM looks in B's method table for show()
  Found! Calls B's show().

This lookup happens via the "vtable" (virtual method table).
Each class has a vtable mapping method names to implementations.
```

---

## Compile-time vs Runtime Polymorphism — Summary

| Aspect | Compile-time | Runtime |
|---|---|---|
| Also called | Static / Early binding | Dynamic / Late binding |
| Mechanism | Method Overloading | Method Overriding |
| Decision time | Compile time | Runtime |
| Based on | Method signature | Actual object type |
| Speed | Faster | Slightly slower (dynamic lookup) |
| Flexibility | Less flexible | Highly flexible |

---

## Interview Questions — Polymorphism

1. **What is polymorphism?** — One entity behaving in multiple ways depending on context.
2. **What is method overloading?** — Multiple methods with same name but different parameters in the same class.
3. **What is method overriding?** — Child class providing its own version of an inherited method.
4. **What is Dynamic Method Dispatch?** — The JVM determining which overridden method to call at runtime based on actual object type.
5. **Can you assign a parent reference to a child object?** — Yes: `Animal a = new Dog();`
6. **What is the difference between static and dynamic binding?** — Static: resolved at compile time (overloading). Dynamic: resolved at runtime (overriding).
7. **Can method overloading change the return type only?** — No. Overloading requires different parameter lists. Return type alone is not enough.

---

---

# 18. The `final` Keyword

## Introduction

The `final` keyword in Java means "**this cannot be changed**." It can be applied to **variables**, **methods**, and **classes**, each with a different meaning.

---

## 18.1 `final` Variable — Constant Value

```java
public class Circle {
    final double PI = 3.14159;   // Constant — cannot be reassigned

    void calculate(double r) {
        // PI = 3.14; // ❌ Compile error: cannot assign a value to final variable
        System.out.println("Area: " + PI * r * r);
    }
}

// Common pattern for global constants:
public class Constants {
    public static final double TAX_RATE = 0.18;
    public static final int MAX_RETRY = 3;
    public static final String APP_NAME = "MyApp";
}
```

**Important note for object references:**

```java
final int[] arr = {1, 2, 3};
arr[0] = 99;        // ✅ OK — modifying contents, not the reference
arr = new int[]{};  // ❌ Cannot reassign the reference
```

`final` on an object reference means the **reference cannot change** — but the object's internal state can still be modified (unless the class itself is designed to be immutable).

---

## 18.2 `final` Method — Prevent Overriding

```java
class TemplatePrinter {
    // This template must not be changed by subclasses
    public final void printHeader() {
        System.out.println("=== COMPANY REPORT ===");
    }

    public void printBody() {
        // Subclasses can override this
    }
}

class ReportPrinter extends TemplatePrinter {
    // @Override
    // public void printHeader() { } // ❌ Compile error: cannot override final method

    @Override
    public void printBody() {
        System.out.println("Custom body content"); // ✅ Allowed
    }
}
```

**Use case:** When you want the base implementation to be guaranteed — like security checks, logging, or templates.

---

## 18.3 `final` Class — Prevent Inheritance

```java
final class ImmutablePoint {
    final int x;
    final int y;

    ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

// class Subclass extends ImmutablePoint { } // ❌ Compile error: cannot inherit from final class
```

**Real-world examples:** `String`, `Integer`, `Math` — all are `final` classes in Java's standard library. No one can extend `String`, which protects its immutability guarantee.

---

## Summary Table

| Applied To | Effect |
|---|---|
| `final` variable | Value cannot be changed after assignment |
| `final` method | Cannot be overridden by subclasses |
| `final` class | Cannot be extended (no subclasses) |

---

## Interview Questions — final

1. **What does the `final` keyword do?** — Prevents change: final variable = constant, final method = cannot override, final class = cannot extend.
2. **Can a `final` variable be initialized in a constructor?** — Yes! It just can't be reassigned after initialization.
3. **What is a blank final variable?** — A `final` variable declared but not initialized at declaration — must be initialized in every constructor.
4. **Why is `String` a `final` class?** — To guarantee immutability. If someone could subclass String, they might make a mutable version, breaking assumptions.
5. **Can you override a `final` method?** — No.
6. **Difference between `final`, `finally`, and `finalize`?** — `final`: modifier. `finally`: try-catch block that always runs. `finalize()`: deprecated GC method called before object is collected.

---

---

# 19. The Object Class

## Introduction

`java.lang.Object` is the **root of all Java class hierarchy**. Every class in Java — whether you write it or it's a standard library class — **implicitly extends `Object`** if no other superclass is specified.

## What This Means

```java
class Dog { }
// This is actually:
class Dog extends Object { } // Java adds this implicitly!
```

This means every Java object automatically has methods inherited from `Object`.

---

## Key Methods of Object Class

| Method | Description |
|---|---|
| `toString()` | Returns a String representation of the object |
| `equals(Object o)` | Checks if two objects are "equal" |
| `hashCode()` | Returns integer hash code for the object |
| `getClass()` | Returns the runtime class of the object |
| `clone()` | Creates and returns a copy of the object |
| `finalize()` | Called by GC before object is collected (deprecated since Java 9) |
| `wait()`, `notify()`, `notifyAll()` | Thread synchronization methods |

---

## Overriding Object Methods

The most important ones to override are `toString()`, `equals()`, and `hashCode()`.

```java
class Point {
    int x, y;

    Point(int x, int y) { this.x = x; this.y = y; }

    @Override
    public String toString() {
        return "Point(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}

Point p1 = new Point(3, 4);
Point p2 = new Point(3, 4);

System.out.println(p1);            // Point(3, 4) — uses toString()
System.out.println(p1.equals(p2)); // true — custom equals
System.out.println(p1 == p2);      // false — different objects
```

### The `equals` and `hashCode` Contract

> **CRITICAL:** If you override `equals()`, you **must** also override `hashCode()`. If two objects are equal (by `equals()`), they **must** have the same `hashCode()`.

Violating this breaks `HashMap`, `HashSet`, and any other hash-based collection.

---

## Interview Questions — Object Class

1. **What is the `Object` class?** — The root of Java's class hierarchy. Every class implicitly extends it.
2. **What methods does `Object` provide?** — `toString()`, `equals()`, `hashCode()`, `getClass()`, `clone()`, `wait()`, `notify()`, etc.
3. **Why override `equals()` and `hashCode()` together?** — If equal objects have different hash codes, they break hash-based collections.
4. **What does `toString()` return by default?** — Class name + "@" + hex hashcode: `ClassName@1a2b3c`.

---

---

# 20. Type Casting — Upcasting & Downcasting

## Introduction

**Type casting** in Java means converting one type to another. In the context of OOP, it refers to converting between parent and child class references.

---

## Upcasting (Widening) — Child to Parent

> **Upcasting** = assigning a child object to a parent reference. **Implicit** — no explicit cast needed.

```java
class Animal { void sound() { System.out.println("..."); } }
class Dog extends Animal {
    @Override void sound() { System.out.println("Woof!"); }
    void fetch() { System.out.println("Fetching!"); }
}

// UPCASTING — implicit, safe
Animal a = new Dog(); // Dog upcasted to Animal reference
a.sound();   // ✅ "Woof!" — runtime dispatch (Dog's version called!)
a.fetch();   // ❌ Compile error — Animal reference doesn't know about fetch()
```

**After upcasting:**

- You can only call methods defined in the **reference type (Animal)**
- But overridden methods use the **actual object's (Dog's) implementation**

---

## Downcasting (Narrowing) — Parent to Child

> **Downcasting** = converting a parent reference back to a child type. **Explicit** — must cast manually.

```java
Animal a = new Dog(); // Upcasted

Dog d = (Dog) a;   // Downcasting — explicit cast
d.fetch();         // ✅ Now we can call Dog-specific methods!
```

**Danger — ClassCastException:**

```java
Animal a = new Cat(); // a actually holds a Cat

Dog d = (Dog) a;  // ❌ Runtime ClassCastException!
                   // Can't cast Cat to Dog — they're not related!
```

**Safe downcasting with `instanceof`:**

```java
Animal a = ...; // Unknown actual type

if (a instanceof Dog) {
    Dog d = (Dog) a; // Safe — we verified first
    d.fetch();
}

// Java 16+ Pattern Matching (cleaner):
if (a instanceof Dog d) {  // Combines check and cast
    d.fetch();
}
```

---

## Comparison Table

| Aspect | Upcasting | Downcasting |
|---|---|---|
| Direction | Child → Parent | Parent → Child |
| Explicit cast needed? | ❌ No (implicit) | ✅ Yes |
| Safe? | Always safe ✅ | Risky — verify with instanceof |
| Methods accessible | Only parent's methods | All child's methods |
| Example | `Animal a = new Dog()` | `Dog d = (Dog) a` |

---

## Interview Questions — Type Casting

1. **What is upcasting?** — Assigning a child object to a parent reference. Implicit and safe.
2. **What is downcasting?** — Converting a parent reference back to a child type. Must be explicit, can throw ClassCastException.
3. **What is `ClassCastException`?** — Runtime exception thrown when an invalid downcast is attempted.
4. **How do you safely downcast?** — Use `instanceof` to verify the type before casting.
5. **What is the purpose of upcasting?** — To use polymorphism — handle multiple types through a single parent reference.

---

---

# 21. Wrapper Classes

## Introduction

Java has 8 **primitive types** (`int`, `byte`, `short`, `long`, `float`, `double`, `char`, `boolean`). These are not objects — they can't be stored in collections, have no methods, and can't be null.

**Wrapper classes** are object equivalents of each primitive:

| Primitive | Wrapper Class |
|---|---|
| `int` | `Integer` |
| `long` | `Long` |
| `double` | `Double` |
| `float` | `Float` |
| `char` | `Character` |
| `boolean` | `Boolean` |
| `byte` | `Byte` |
| `short` | `Short` |

---

## Autoboxing and Unboxing

Java automatically converts between primitives and wrapper objects:

```java
// AUTOBOXING — primitive to object (automatic)
int x = 42;
Integer obj = x;         // Auto: Integer.valueOf(42)
Integer obj2 = 100;      // Also autoboxing

// UNBOXING — object to primitive (automatic)
Integer n = Integer.valueOf(99);
int val = n;             // Auto: n.intValue()

// Used in collections (which require objects):
List<Integer> list = new ArrayList<>();
list.add(5);   // 5 autoboxed to Integer(5)
int first = list.get(0); // Integer unboxed to int
```

---

## Useful Wrapper Methods

```java
// Parsing strings to primitives
int n = Integer.parseInt("42");
double d = Double.parseDouble("3.14");
boolean b = Boolean.parseBoolean("true");

// Converting primitives to strings
String s = Integer.toString(42);
String s2 = String.valueOf(42);  // works for all types

// Min, Max, constants
int max = Integer.MAX_VALUE;   // 2147483647
int min = Integer.MIN_VALUE;   // -2147483648

// Comparing
Integer.compare(5, 10);       // negative (5 < 10)
Integer.max(5, 10);           // 10
Integer.sum(5, 10);           // 15
```

---

## Autoboxing Trap — The `==` Problem

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b); // true ← (cached range: -128 to 127)

Integer c = 128;
Integer d = 128;
System.out.println(c == d); // false ← different objects!
System.out.println(c.equals(d)); // true ← compare content
```

Java caches Integer values from -128 to 127. Above that range, new objects are created. **Always use `.equals()` to compare Integer objects.**

---

## Interview Questions — Wrapper Classes

1. **What are wrapper classes?** — Object representations of Java primitives.
2. **What is autoboxing?** — Automatic conversion from primitive to wrapper object.
3. **What is unboxing?** — Automatic conversion from wrapper object to primitive.
4. **Why do we need wrapper classes?** — Collections require objects (not primitives). Wrapper classes provide utility methods. Support null.
5. **What is the Integer cache?** — Java caches Integer objects for values -128 to 127. Values outside this range create new objects each time.
6. **Can a wrapper class be null?** — Yes. `Integer n = null;`. Unboxing a null wrapper throws `NullPointerException`.

---

---

# 22. Abstract Classes

## Introduction

An **abstract class** is a class that **cannot be instantiated** (you cannot create objects of it directly). It serves as a **template** for subclasses, potentially containing:

- Abstract methods (declared but not implemented)
- Concrete methods (fully implemented)
- Instance variables
- Constructors

## The `abstract` Keyword

```java
abstract class Shape {
    // Abstract method — NO implementation, MUST be overridden by subclasses
    public abstract double area();

    // Concrete method — has implementation, inherited as-is
    public void describe() {
        System.out.println("I am a shape with area: " + area());
    }
}
```

---

## Rules for Abstract Classes

1. Declared with the `abstract` keyword
2. **Cannot be instantiated** directly
3. Can have **abstract methods** (no body) — subclasses must implement them
4. Can have **concrete methods** (with body) — optionally overridden
5. Can have **constructors** (called by subclass via `super()`)
6. A subclass **must implement all abstract methods** or itself be declared abstract
7. A class with even **one abstract method** must be declared abstract

---

## Complete Example

```java
abstract class Vehicle {
    String brand;
    int year;

    // Constructor (called by subclasses)
    Vehicle(String brand, int year) {
        this.brand = brand;
        this.year = year;
    }

    // Abstract method — every vehicle starts differently
    public abstract void start();

    // Abstract method — every vehicle has different fuel capacity
    public abstract double fuelCapacity();

    // Concrete method — same for all vehicles
    public void displayInfo() {
        System.out.println(brand + " (" + year + ") - Fuel: " + fuelCapacity() + "L");
    }
}

class Car extends Vehicle {
    Car(String brand, int year) {
        super(brand, year);
    }

    @Override
    public void start() {
        System.out.println("Car starts with a key turn.");
    }

    @Override
    public double fuelCapacity() { return 50.0; }
}

class Motorcycle extends Vehicle {
    Motorcycle(String brand, int year) {
        super(brand, year);
    }

    @Override
    public void start() {
        System.out.println("Motorcycle starts with a kick/button.");
    }

    @Override
    public double fuelCapacity() { return 15.0; }
}

// Usage
// Vehicle v = new Vehicle("Toyota", 2023); // ❌ Cannot instantiate abstract class

Vehicle car = new Car("Toyota", 2023); // ✅ Upcasting
car.start();          // Car starts with a key turn.
car.displayInfo();    // Toyota (2023) - Fuel: 50.0L

Vehicle moto = new Motorcycle("Honda", 2022);
moto.start();         // Motorcycle starts with a kick/button.
moto.displayInfo();   // Honda (2022) - Fuel: 15.0L
```

---

## Abstract Class vs Interface

| Feature | Abstract Class | Interface |
|---|---|---|
| Instantiation | ❌ Cannot | ❌ Cannot |
| Methods | Abstract + Concrete | Abstract (default: all), can have `default`/`static` |
| Variables | Instance + static + constants | Only `public static final` constants |
| Constructors | ✅ Yes | ❌ No |
| Multiple inheritance | ❌ Only one | ✅ Implement multiple |
| Keyword | `extends` | `implements` |
| "IS-A" relationship | Strong | Loose (capability) |
| When to use | Shared base code + partial implementation | Define a contract/capability |

---

## Interview Questions — Abstract Class

1. **What is an abstract class?** — A class that cannot be instantiated, serving as a blueprint with optional abstract methods.
2. **Can an abstract class have a constructor?** — Yes. Called by subclasses via `super()`.
3. **Must an abstract class have abstract methods?** — No! An abstract class can have zero abstract methods.
4. **Can a concrete class have abstract methods?** — No. If a class has an abstract method, it must be abstract.
5. **What happens if a subclass doesn't implement all abstract methods?** — The subclass must also be declared abstract.
6. **Abstract class vs interface?** — Abstract class: partial implementation, single inheritance, can have state. Interface: full contract, multiple "inheritance", no state.

---

---

# 23. Inner Classes

## Introduction

Java allows you to define a **class inside another class**. These are called **inner classes** (or nested classes). They are useful for logically grouping classes that are only used in one place.

---

## Types of Inner Classes

```
Nested Classes
├── Static Nested Class
└── Inner Classes (non-static)
     ├── Regular Inner Class
     ├── Local Inner Class (inside a method)
     └── Anonymous Inner Class
```

---

## Regular Inner Class

```java
class Outer {
    private int x = 10;

    class Inner {
        void display() {
            System.out.println("x = " + x); // Inner can access Outer's private members!
        }
    }
}

// Creating inner class object:
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner(); // Need outer object first!
inner.display(); // x = 10
```

---

## Static Nested Class

```java
class Outer {
    static int staticVar = 100;
    int instanceVar = 200;

    static class StaticNested {
        void display() {
            System.out.println(staticVar);    // ✅ Can access static members
            // System.out.println(instanceVar); // ❌ Cannot access instance members
        }
    }
}

// No outer instance needed for static nested class:
Outer.StaticNested nested = new Outer.StaticNested();
nested.display();
```

---

## Anonymous Inner Class

An anonymous class is a class **without a name** created on-the-fly, usually to override a method of an interface or class for a specific use.

```java
abstract class Greeter {
    abstract void greet();
}

// Anonymous class — defined and instantiated in one expression
Greeter g = new Greeter() {
    @Override
    void greet() {
        System.out.println("Hello from anonymous class!");
    }
};

g.greet(); // Hello from anonymous class!
```

Anonymous classes are the predecessor to Lambda expressions (Java 8+).

---

## Interview Questions — Inner Classes

1. **What is an inner class?** — A class defined within another class.
2. **Can an inner class access private members of the outer class?** — Yes, regular inner classes can.
3. **What is an anonymous inner class?** — A class without a name, defined and instantiated in one expression.
4. **What is a static nested class?** — A static class inside another class. Doesn't need an outer class instance. Can only access static members.

---

---

# 24. Interfaces

## Introduction

An **interface** in Java is a **pure contract** — it specifies what a class must do, but not how. It's like a blueprint of behavior without any implementation (before Java 8).

> Think of an interface as a **job description**: it tells you the required skills, not how you've learned them.

---

## Key Characteristics

1. **All methods are `public abstract` by default** (pre-Java 8)
2. **All variables are `public static final` by default** (constants)
3. Cannot be instantiated
4. A class **implements** an interface (not extends)
5. A class can implement **multiple interfaces**
6. Interfaces can extend multiple interfaces

---

## Syntax

```java
// Define interface
public interface Printable {
    void print();          // public abstract by default
    void preview();        // public abstract by default
}

// Implement interface
public class Document implements Printable {
    @Override
    public void print() {
        System.out.println("Printing document...");
    }

    @Override
    public void preview() {
        System.out.println("Previewing document...");
    }
}
```

---

## Why Interfaces Exist

**Problem solved:** Java doesn't allow multiple class inheritance (Diamond Problem). But sometimes you need a class to have **multiple capabilities**. Interfaces solve this.

```java
interface Flyable {
    void fly();
}

interface Swimmable {
    void swim();
}

// Duck can both fly AND swim!
class Duck extends Animal implements Flyable, Swimmable {
    @Override
    public void fly() { System.out.println("Duck flying!"); }

    @Override
    public void swim() { System.out.println("Duck swimming!"); }
}
```

---

## Interface Variables — Why Static and Final?

```java
interface Config {
    int MAX_CONNECTIONS = 100;  // Implicitly: public static final int MAX_CONNECTIONS = 100;
}
```

- **`final`** — Interface has no object of its own in the heap. It can't have mutable state. All values must be constants.
- **`static`** — Since there's no object, you access them via interface name: `Config.MAX_CONNECTIONS`

```java
System.out.println(Config.MAX_CONNECTIONS); // 100
// Config.MAX_CONNECTIONS = 50;             // ❌ Cannot assign to final variable
```

---

## Multiple Interface Implementation

```java
interface Drawable {
    void draw();
}

interface Resizable {
    void resize(double factor);
}

interface Colorable {
    void setColor(String color);
}

// One class implementing multiple interfaces
class GraphicElement implements Drawable, Resizable, Colorable {
    private String color = "black";
    private double size = 1.0;

    @Override
    public void draw() {
        System.out.println("Drawing " + color + " element of size " + size);
    }

    @Override
    public void resize(double factor) {
        size *= factor;
    }

    @Override
    public void setColor(String color) {
        this.color = color;
    }
}
```

---

## Inheritance Relationships

| Relationship | Keyword |
|---|---|
| Class → Class | `extends` |
| Class → Interface | `implements` |
| Interface → Interface | `extends` |

```java
interface A { void methodA(); }
interface B extends A { void methodB(); } // Interface extends interface

class C implements B { // Must implement both methodA() AND methodB()
    public void methodA() { }
    public void methodB() { }
}
```

---

## Types of Interfaces

### 1. Normal Interface

Has 2 or more abstract methods:

```java
interface DataProcessor {
    void readData();
    void processData();
    void writeData();
}
```

### 2. Functional Interface (SAM — Single Abstract Method)

Has **exactly one** abstract method. Designed to work with **Lambda expressions**:

```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);  // Only ONE abstract method
}

// With anonymous class:
Calculator add = new Calculator() {
    public int calculate(int a, int b) { return a + b; }
};

// With Lambda (much cleaner):
Calculator add = (a, b) -> a + b;
Calculator multiply = (a, b) -> a * b;

System.out.println(add.calculate(5, 3));      // 8
System.out.println(multiply.calculate(5, 3)); // 15
```

Common built-in functional interfaces: `Runnable`, `Comparator`, `Callable`, `Predicate<T>`, `Function<T,R>`.

### 3. Marker Interface

Has **no methods** at all. Used to signal/mark a class for special treatment:

```java
// Example from Java standard library:
interface Serializable { }  // No methods — just marks the class as serializable
interface Cloneable { }     // No methods — marks class as cloneable
```

```java
class Employee implements Serializable {
    // Now this class can be serialized (converted to bytes for storage/transmission)
}
```

---

## Java 8+ Interface Features

Java 8 added `default` and `static` methods to interfaces:

```java
interface Greeter {
    void greet(String name); // Abstract (must implement)

    // Default method — has implementation, can be overridden
    default void greetAll(String[] names) {
        for (String name : names) {
            greet(name);
        }
    }

    // Static method — utility, called on interface name
    static String format(String name) {
        return "Dear " + name;
    }
}

class FormalGreeter implements Greeter {
    @Override
    public void greet(String name) {
        System.out.println("Good day, " + Greeter.format(name));
    }
    // Gets greetAll() for free from default method!
}
```

---

## Abstract Class vs Interface — When to Use Which?

**Use Abstract Class when:**

- You want to share code among several closely related classes
- Classes need common state (instance variables)
- You need non-public members (private/protected)
- You need constructors

**Use Interface when:**

- Unrelated classes should share behavior (e.g., `Comparable` works for `String`, `Integer`, custom classes)
- You want to specify behavior without regard to who implements it
- You need multiple "inheritance" of type
- Defining a capability (`Flyable`, `Serializable`)

---

## Interview Questions — Interfaces

1. **What is an interface?** — A contract specifying what a class must do, without implementation.
2. **Can an interface have variables?** — Yes, but they're automatically `public static final` (constants).
3. **Why are interface methods `public abstract` by default?** — Because interface methods must be available to all implementing classes and must be overridden.
4. **Can a class implement multiple interfaces?** — Yes.
5. **What is a functional interface?** — An interface with exactly one abstract method. Used with lambda expressions.
6. **What is a marker interface?** — An interface with no methods, used to "mark" a class for special treatment.
7. **What is the difference between abstract class and interface?** — See table above.
8. **Can an interface extend another interface?** — Yes, using `extends`.
9. **What is a default method in an interface?** — A method with a body in an interface (Java 8+). Implementing classes inherit it but can override.
10. **Can interfaces have constructors?** — No.

---

---

# 25. Exception Handling

## Introduction

**Exceptions** are **unexpected events** that disrupt the normal flow of program execution. Exception handling is Java's mechanism to **detect, report, and recover** from these events gracefully.

Without exception handling, your program crashes. With it, you can handle errors and keep running.

---

## Types of Errors

| Type | When Detected | Example |
|---|---|---|
| **Compile-time error** (Syntax error) | At compile time | Missing semicolon, wrong type |
| **Runtime error** (Exception) | During execution | NullPointerException, ArrayIndexOutOfBounds |
| **Logical error** | Never (silent bug) | Wrong algorithm producing wrong results |

Runtime errors are called **Exceptions** in Java — and these are what we handle.

---

## Exception Hierarchy

```
Throwable
├── Error (serious system issues — don't catch these)
│    ├── OutOfMemoryError
│    ├── StackOverflowError
│    └── VirtualMachineError
│
└── Exception
     ├── Checked Exceptions (must handle or declare)
     │    ├── IOException
     │    ├── SQLException
     │    ├── FileNotFoundException
     │    └── ClassNotFoundException
     │
     └── RuntimeException (Unchecked — optional to handle)
          ├── NullPointerException
          ├── ArrayIndexOutOfBoundsException
          ├── ClassCastException
          ├── NumberFormatException
          └── ArithmeticException
```

### Checked vs Unchecked Exceptions

| Type | Must Handle? | Examples | When |
|---|---|---|---|
| **Checked** | ✅ Yes | `IOException`, `SQLException` | External resources: files, DB, network |
| **Unchecked** (RuntimeException) | ❌ Optional | `NullPointerException`, `ClassCastException` | Programming bugs |
| **Error** | ❌ Never | `OutOfMemoryError` | JVM-level issues |

---

## 25.1 try-catch-finally

```java
try {
    // Code that might throw an exception
} catch (ExceptionType1 e) {
    // Handle ExceptionType1
} catch (ExceptionType2 e) {
    // Handle ExceptionType2
} finally {
    // ALWAYS runs — whether exception happened or not
    // Used for cleanup (close files, DB connections, etc.)
}
```

### Complete Example

```java
public class ExceptionDemo {
    public static void main(String[] args) {
        int[] arr = {10, 20, 30};

        try {
            System.out.println("Trying to access element...");
            System.out.println(arr[5]); // ❌ Index out of bounds!
            System.out.println("This line is SKIPPED if exception occurs");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Caught! " + e.getMessage());
        } finally {
            System.out.println("Finally always runs — cleanup here");
        }

        System.out.println("Program continues after exception handling");
    }
}

// Output:
// Trying to access element...
// Caught! Index 5 out of bounds for length 3
// Finally always runs — cleanup here
// Program continues after exception handling
```

---

### Multiple catch Blocks

```java
try {
    String s = null;
    int[] arr = new int[5];

    System.out.println(s.length()); // NullPointerException
    System.out.println(arr[10]);    // ArrayIndexOutOfBoundsException
} catch (NullPointerException e) {
    System.out.println("Null pointer: " + e.getMessage());
} catch (ArrayIndexOutOfBoundsException e) {
    System.out.println("Array out of bounds: " + e.getMessage());
} catch (Exception e) {
    // Catch-all — must be LAST (most general)
    System.out.println("Generic exception: " + e.getMessage());
}
```

**Rule:** More specific exceptions must come before more general ones. `Exception` must be last.

### Multi-catch (Java 7+)

```java
catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
    System.out.println("Either null pointer or array issue: " + e.getMessage());
}
```

---

## 25.2 throw and throws

### `throw` — Manually throw an exception

```java
void setAge(int age) {
    if (age < 0 || age > 150) {
        throw new IllegalArgumentException("Invalid age: " + age);
    }
    this.age = age;
}

// Usage:
setAge(-5); // Throws IllegalArgumentException
```

### `throws` — Declare that a method might throw an exception

```java
// Method declares it can throw IOException — callers must handle it
public void readFile(String path) throws IOException {
    FileReader reader = new FileReader(path); // Can throw FileNotFoundException (subclass of IOException)
    // read file...
}

// Caller MUST handle it:
try {
    readFile("data.txt");
} catch (IOException e) {
    System.out.println("File error: " + e.getMessage());
}
```

| | `throw` | `throws` |
|---|---|---|
| Used in | Method body | Method signature |
| Purpose | Actually throws an exception | Declares potential exceptions |
| Number | One exception at a time | Multiple exceptions (comma-separated) |

---

## 25.3 Custom Exceptions

```java
// Custom checked exception
public class InsufficientFundsException extends Exception {
    private double deficit;

    public InsufficientFundsException(double deficit) {
        super("Insufficient funds. Need: " + deficit + " more.");
        this.deficit = deficit;
    }

    public double getDeficit() {
        return deficit;
    }
}

// Custom unchecked exception
public class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Using custom exceptions:
class BankAccount {
    private double balance;

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(amount - balance);
        }
        balance -= amount;
    }
}

// Main:
BankAccount acc = new BankAccount(/* balance=100 */);
try {
    acc.withdraw(500);
} catch (InsufficientFundsException e) {
    System.out.println(e.getMessage()); // Insufficient funds. Need: 400.0 more.
    System.out.println("Deficit: " + e.getDeficit());
}
```

---

## 25.4 `finally` Block — Resource Cleanup

```java
FileReader reader = null;
try {
    reader = new FileReader("data.txt");
    // read file
} catch (IOException e) {
    System.out.println("Error reading file");
} finally {
    // Always runs — even if exception thrown or caught
    if (reader != null) {
        try {
            reader.close(); // Close resource
        } catch (IOException e) {
            System.out.println("Error closing file");
        }
    }
}
```

**Simpler with try-with-resources (Java 7+):**

```java
try (FileReader reader = new FileReader("data.txt")) {
    // reader is auto-closed when block exits, even if exception occurs
} catch (IOException e) {
    System.out.println("Error: " + e.getMessage());
}
```

---

## Interview Questions — Exception Handling

1. **What is an exception?** — A runtime event that disrupts normal program flow.
2. **Difference between checked and unchecked exceptions?** — Checked: must handle (IOException). Unchecked: optional (RuntimeException subclasses).
3. **What is the difference between `throw` and `throws`?** — `throw`: actually throws an exception. `throws`: declares that a method can throw an exception.
4. **What does `finally` do?** — Runs always, even if exception occurs, used for cleanup.
5. **Can `finally` be skipped?** — Yes: `System.exit()` call, or JVM crash.
6. **What is try-with-resources?** — Java 7+ feature that auto-closes resources implementing `AutoCloseable`.
7. **Can you catch an `Error`?** — Technically yes, but you shouldn't. Errors indicate unrecoverable JVM conditions.
8. **What is a custom exception?** — A user-defined exception class extending `Exception` or `RuntimeException`.
9. **What is the exception propagation?** — If a method doesn't handle an exception, it propagates (is passed) to the calling method up the call stack.

---

---

# 26. Threads & Concurrency

## Introduction

A **thread** is the smallest unit of program execution. Java supports **multithreading** — running multiple threads simultaneously, allowing programs to do multiple things at the same time.

## Why Threads?

- **Performance** — Use multiple CPU cores
- **Responsiveness** — Keep UI responsive while doing background work
- **Resource utilization** — Don't idle while waiting for I/O

---

## Thread Lifecycle (States)

```
NEW → RUNNABLE → RUNNING → (BLOCKED/WAITING/TIMED_WAITING) → DEAD
 │                               ↑         ↓
 │                               └─────────┘
 └── thread not started yet

NEW:          Thread created but start() not called
RUNNABLE:     Ready to run, waiting for CPU
RUNNING:      Actually executing (CPU assigned)
BLOCKED:      Waiting for a monitor lock (synchronized)
WAITING:      Waiting indefinitely (wait(), join())
TIMED_WAITING: Waiting for specified time (sleep(ms))
DEAD:         Execution completed
```

---

## Creating Threads — Two Ways

### Way 1: Extending `Thread`

```java
class MyThread extends Thread {
    private String name;

    MyThread(String name) { this.name = name; }

    @Override
    public void run() {  // The task this thread will perform
        for (int i = 1; i <= 5; i++) {
            System.out.println(name + ": count = " + i);
            try {
                Thread.sleep(500); // Sleep 500ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

// Usage:
MyThread t1 = new MyThread("Thread-1");
MyThread t2 = new MyThread("Thread-2");

t1.start(); // Don't call run()! Call start() — JVM creates a new thread
t2.start(); // Both run concurrently — output will be interleaved
```

### Way 2: Implementing `Runnable` (Preferred)

```java
class MyTask implements Runnable {
    private String taskName;

    MyTask(String taskName) { this.taskName = taskName; }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(taskName + " executing: " + i);
        }
    }
}

// Usage:
Thread t1 = new Thread(new MyTask("Task-A"));
Thread t2 = new Thread(new MyTask("Task-B"));

// Lambda (since Runnable is @FunctionalInterface):
Thread t3 = new Thread(() -> System.out.println("Lambda task!"));

t1.start();
t2.start();
t3.start();
```

**Why `Runnable` is preferred over `Thread`:**

- Java single inheritance: extending `Thread` prevents extending any other class
- Separates task (what to do) from execution mechanism (how to run it)
- Better for thread pools (ExecutorService)

---

## Race Condition

A **race condition** occurs when **multiple threads access and modify shared data concurrently**, leading to **inconsistent/incorrect results**.

```java
class Counter {
    int count = 0; // Shared resource

    void increment() {
        count++; // NOT atomic! Three steps: read, increment, write
    }
}

// Race condition demo:
Counter counter = new Counter();

Runnable task = () -> {
    for (int i = 0; i < 1000; i++) {
        counter.increment(); // Multiple threads doing this simultaneously
    }
};

Thread t1 = new Thread(task);
Thread t2 = new Thread(task);
t1.start();
t2.start();
t1.join(); // Wait for t1 to finish
t2.join(); // Wait for t2 to finish

System.out.println(counter.count); // Expected: 2000, Actual: something less!
```

---

## Solving Race Conditions — `synchronized`

```java
class SafeCounter {
    int count = 0;

    synchronized void increment() { // Only one thread at a time
        count++;
    }
}
```

Or using `java.util.concurrent.atomic`:

```java
import java.util.concurrent.atomic.AtomicInteger;

AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet(); // Thread-safe, no synchronization needed
```

---

## Interview Questions — Threads

1. **What is a thread?** — The smallest unit of execution. Java supports multiple threads running simultaneously.
2. **Two ways to create a thread?** — Extend `Thread`, or implement `Runnable`. `Runnable` is preferred.
3. **Why call `start()` and not `run()`?** — `run()` executes in the current thread (no new thread created). `start()` creates a new thread and calls `run()` in it.
4. **What is a race condition?** — Multiple threads accessing shared mutable data concurrently, causing unexpected results.
5. **What is `synchronized`?** — Keyword that ensures only one thread executes a block/method at a time, preventing race conditions.
6. **What is `Thread.sleep()`?** — Makes the current thread pause for a specified number of milliseconds.
7. **Thread states?** — NEW, RUNNABLE, RUNNING, BLOCKED, WAITING, TIMED_WAITING, DEAD.
8. **What is `join()`?** — Makes the calling thread wait until the specified thread finishes.

---

---

# 27. Collections Framework

## Introduction

The **Collections Framework** is Java's built-in library for storing, retrieving, and manipulating groups of objects. It provides ready-made, optimized data structures and algorithms.

---

## The Naming Confusion — Clarified

| Term | Type | What it is |
|---|---|---|
| **Collection** (capital C) | Interface | The root interface of the framework |
| **Collections** (with 's') | Utility Class | `java.util.Collections` — has static methods like `sort()`, `shuffle()` |
| **collection** (lowercase) | Concept | The general concept of storing groups of objects |

---

## Collections Framework Hierarchy

```
Iterable
 └── Collection (interface)
      ├── List (interface)          — Ordered, allows duplicates
      │    ├── ArrayList
      │    ├── LinkedList
      │    └── Vector (legacy)
      │
      ├── Set (interface)           — Unordered, NO duplicates
      │    ├── HashSet
      │    ├── LinkedHashSet
      │    └── TreeSet
      │
      └── Queue (interface)         — FIFO processing
           ├── LinkedList
           ├── PriorityQueue
           └── Deque (interface)
                └── ArrayDeque

Map (interface) — NOT part of Collection, but part of framework
 ├── HashMap
 ├── LinkedHashMap
 ├── TreeMap
 └── Hashtable (legacy)
```

---

## 27.1 List — Ordered, Duplicates Allowed

### ArrayList

```java
import java.util.ArrayList;
import java.util.List;

List<String> fruits = new ArrayList<>();

// Adding
fruits.add("Apple");
fruits.add("Banana");
fruits.add("Cherry");
fruits.add("Apple"); // Duplicates allowed!

// Accessing
System.out.println(fruits.get(0));  // Apple
System.out.println(fruits.size());  // 4

// Iterating
for (String fruit : fruits) {
    System.out.println(fruit);
}

// Removing
fruits.remove("Banana");      // Remove by value
fruits.remove(0);             // Remove by index

// Searching
System.out.println(fruits.contains("Apple")); // true
System.out.println(fruits.indexOf("Cherry")); // index or -1
```

**Internal Working of ArrayList:**

- Backed by a `Object[]` array
- Default initial capacity: **10**
- When full: creates new array of size **(current × 1.5)**, copies elements
- This resizing is **O(n)** but amortized is still **O(1)** for add

```
Initial: [_, _, _, _, _, _, _, _, _, _]  (10 slots)
After 10 adds: Full!
New array: 15 slots, elements copied → O(n) operation
But this happens rarely, so average add is O(1)
```

### ArrayList Performance

| Operation | Time Complexity |
|---|---|
| Add to end | O(1) amortized |
| Add at index | O(n) — shift right |
| Get by index | O(1) |
| Remove by index | O(n) — shift left |
| Search (contains) | O(n) |

### LinkedList

```java
import java.util.LinkedList;

LinkedList<Integer> list = new LinkedList<>();
list.add(10);
list.add(20);
list.addFirst(5);  // O(1) — add at beginning!
list.addLast(30);  // O(1) — add at end!

System.out.println(list); // [5, 10, 20, 30]
```

**Internal Working:** Doubly-linked list. Each element (node) stores data + reference to next and previous node.

### ArrayList vs LinkedList

| Operation | ArrayList | LinkedList |
|---|---|---|
| Get by index | O(1) ✅ | O(n) ❌ |
| Add at end | O(1) amortized ✅ | O(1) ✅ |
| Add at beginning/middle | O(n) ❌ | O(1) if node reference available ✅ |
| Memory | Less (compact array) | More (each node has 2 extra references) |
| **Best for** | Random access, iterations | Frequent insertions/deletions at ends |

---

## 27.2 Set — No Duplicates

```java
import java.util.HashSet;
import java.util.Set;

Set<String> set = new HashSet<>();
set.add("Apple");
set.add("Banana");
set.add("Apple"); // Duplicate — silently ignored!

System.out.println(set.size()); // 2, not 3
System.out.println(set);        // [Banana, Apple] — no guaranteed order

// Common operations
set.contains("Apple"); // true — O(1)
set.remove("Banana");  // O(1)
```

**`LinkedHashSet`** — maintains insertion order.
**`TreeSet`** — sorted order, O(log n) operations.

---

## 27.3 Map — Key-Value Pairs

```java
import java.util.HashMap;
import java.util.Map;

Map<String, Integer> scores = new HashMap<>();

// Putting entries
scores.put("Alice", 95);
scores.put("Bob", 82);
scores.put("Carol", 91);
scores.put("Alice", 98); // Updates Alice's score (overwrites)

// Getting
System.out.println(scores.get("Bob"));        // 82
System.out.println(scores.getOrDefault("Dave", 0)); // 0 (Dave not in map)

// Checking
System.out.println(scores.containsKey("Carol")); // true
System.out.println(scores.containsValue(82));    // true

// Iterating
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    System.out.println(entry.getKey() + ": " + entry.getValue());
}

// Size and removal
System.out.println(scores.size()); // 3
scores.remove("Bob");
```

---

## Choosing the Right Collection

| Need | Use |
|---|---|
| Ordered list, random access | `ArrayList` |
| Frequent insertion/deletion at ends | `LinkedList` |
| No duplicates, no order matters | `HashSet` |
| No duplicates, maintain insertion order | `LinkedHashSet` |
| No duplicates, sorted | `TreeSet` |
| Key-value pairs, fast lookup | `HashMap` |
| Key-value pairs, maintain insertion order | `LinkedHashMap` |
| Key-value pairs, sorted by key | `TreeMap` |
| Thread-safe list | `CopyOnWriteArrayList` |
| Thread-safe map | `ConcurrentHashMap` |

---

## Fail-Fast Behavior

> **Fail-fast iterators** throw `ConcurrentModificationException` if the collection is **modified while being iterated**.

```java
List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));

for (String s : list) {
    if (s.equals("B")) {
        list.remove(s); // ❌ ConcurrentModificationException!
    }
}

// ✅ Use Iterator's remove instead:
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    if (it.next().equals("B")) {
        it.remove(); // Safe removal during iteration
    }
}

// ✅ Or use removeIf (Java 8+):
list.removeIf(s -> s.equals("B"));
```

---

## Thread Safety in Collections

Standard collections (`ArrayList`, `HashMap`, etc.) are **NOT thread-safe**.

```java
// Thread-safe options:
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Better — java.util.concurrent:
import java.util.concurrent.*;
List<String> safeList = new CopyOnWriteArrayList<>();
Map<String, Integer> safeMap = new ConcurrentHashMap<>();
```

---

## The `Collections` Utility Class

```java
import java.util.Collections;

List<Integer> nums = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6));

Collections.sort(nums);          // [1, 1, 2, 3, 4, 5, 6, 9]
Collections.reverse(nums);       // [9, 6, 5, 4, 3, 2, 1, 1]
Collections.shuffle(nums);       // Random order
Collections.min(nums);           // Minimum element
Collections.max(nums);           // Maximum element
Collections.frequency(nums, 1);  // Count occurrences of 1 → 2
Collections.unmodifiableList(nums); // Read-only view
```

---

## Interview Questions — Collections

1. **What is the difference between Collection, Collections, and collection?** — Interface, utility class, concept.
2. **What is the difference between ArrayList and LinkedList?** — ArrayList: array-backed, fast random access. LinkedList: node-based, fast insertions/deletions.
3. **What is fail-fast iteration?** — Iterator throws `ConcurrentModificationException` if collection is modified during iteration.
4. **What is the difference between `HashMap` and `Hashtable`?** — `HashMap`: not synchronized, allows null keys/values, faster. `Hashtable`: synchronized, no nulls, legacy.
5. **How does `HashMap` work internally?** — Uses array of buckets + linked lists (or trees in Java 8+). Key's `hashCode()` determines bucket; `equals()` resolves collisions.
6. **What is the load factor in HashMap?** — Default 0.75. When 75% full, HashMap doubles in size (rehashing).
7. **When to use `Set` vs `List`?** — `List` when order/duplicates matter. `Set` when uniqueness is required.
8. **What is `ConcurrentHashMap`?** — Thread-safe HashMap that allows concurrent reads and segment-level writes without locking the whole map.

---

---

# 📚 Master Revision Sheet

## Quick Reference — All Key Concepts

### Java Basics

- Literal = raw value (`42`, `"hello"`, `true`)
- `javac` compiles `.java` → `.class` (bytecode)
- JVM runs bytecode; JRE = JVM + libs; JDK = JRE + tools
- WORA = Write Once, Run Anywhere
- `main` is `static` so JVM can call it without an object

### OOP Pillars

| Pillar | Keyword | One line |
|---|---|---|
| Encapsulation | `private` + getters/setters | Hide data, expose interface |
| Inheritance | `extends` | Child acquires parent's features |
| Polymorphism | `@Override` + parent refs | Same name, different behavior |
| Abstraction | `abstract` / `interface` | Hide complexity, show essentials |

### Access Modifiers

```
private < default < protected < public
```

- `private` → same class only
- `default` → same package
- `protected` → package + subclasses
- `public` → everywhere

### static

- Static = class-level, shared, no object needed
- Static method: cannot use non-static (ambiguity)
- `main` is static: JVM calls without object
- Static block: runs once on class load

### Collections Quick Pick

- Random access list → `ArrayList`
- Unique values → `HashSet`
- Key-value → `HashMap`
- Sorted set → `TreeSet`
- Thread-safe → `ConcurrentHashMap`, `CopyOnWriteArrayList`

---

## Top 20 Interview Questions at a Glance

| # | Question | Key Answer |
|---|---|---|
| 1 | Why is Java platform independent? | Bytecode + JVM |
| 2 | Is JVM platform independent? | No! JVM is platform-dependent |
| 3 | Why is `main` static? | JVM calls without object — no chicken-and-egg |
| 4 | Why are Strings immutable? | Security, thread safety, String pool, caching |
| 5 | `==` vs `.equals()` | References vs content |
| 6 | Stack vs Heap | Stack: local vars, methods. Heap: objects |
| 7 | Overloading vs Overriding | Compile-time vs Runtime polymorphism |
| 8 | Abstract class vs Interface | Partial impl vs pure contract; state vs no state |
| 9 | Why no multiple class inheritance? | Diamond Problem / Ambiguity |
| 10 | `final`, `finally`, `finalize` | modifier / try-catch block / deprecated GC method |
| 11 | Checked vs Unchecked exceptions | Must handle vs optional |
| 12 | `throw` vs `throws` | Actual throw vs declaration |
| 13 | ArrayList vs LinkedList | Array-backed vs node-based |
| 14 | Fail-fast iterators | `ConcurrentModificationException` |
| 15 | HashMap internal working | Buckets + hashCode + equals |
| 16 | `this` vs `super` | Current object vs parent class |
| 17 | Static variable vs instance variable | Shared (class) vs per-object |
| 18 | Autoboxing vs Unboxing | primitive↔wrapper conversion |
| 19 | Functional interface | Single abstract method; lambda-compatible |
| 20 | Thread `run()` vs `start()` | `start()` creates new thread; `run()` does not |

---

> ✅ **End of Notes**
> These notes cover everything from your raw input — expanded, structured, and made interview-ready.
> Keep practicing the code examples. The best way to internalize Java is to type the code yourself!
