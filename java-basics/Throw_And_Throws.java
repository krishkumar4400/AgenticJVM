// throw
// public class Throw_And_Throws {
//     public static void main(String[] args) {
//         int i = 20;
//         int j = 0;

//         try {
//             j = 14 / i;
//             if(j == 0) {
//                 throw new ArithmeticException();
//             }

//         } catch (ArithmeticException e) {
//             j = 18/1;
//             System.out.println("That's the default output");
//         } catch (Exception e) {
//             System.out.println(e);
//         }

//         System.out.println(j);
//         System.out.println("Bye");
//     }
// }


// public class Throw_And_Throws {
//     public static void main(String[] args) {
//         int i = 20;
//         int j = 0;

//         try {
//             j = 14 / i;
//             if (j == 0) {
//                 throw new ArithmeticException("This is error message");
//             }

//         } catch (ArithmeticException e) {
//             j = 18 / 1;
//             System.out.println("That's the default output" + e);
//         } catch (Exception e) {
//             System.out.println(e);
//         }

//         System.out.println(j);
//         System.out.println("Bye");
//     }
// }


// class MyException extends Exception {
//     public MyException(String errorMessage) {
//         super(errorMessage);
//     }
// }

// public class Throw_And_Throws {
//     public static void main(String[] args) {
//         int i = 20;
//         int j = 0;

//         try {
//             j = 14 / i;
//             if (j == 0) {
//                 throw new MyException("This is custom exception");
//             }

//         } catch (ArithmeticException e) {
//             j = 18 / 1;
//             System.out.println("That's the default output");
//         } catch(MyException e) {
//             System.out.println("Error => " + e);
//         } catch (Exception e) {
//             System.out.println(e);
//         }

//         System.out.println(j);
//         System.out.println("Bye");
//     }
// }


// throws
