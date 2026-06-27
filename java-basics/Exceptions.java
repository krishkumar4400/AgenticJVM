



// public class Exceptions {
//     public static void main(String[] args) {
//         int i = 4;
//         int j = 0;
//         int nums[] = new int[5];

//         try
//         {
//             j = 18 / i;
//             System.out.println(nums[1]);
//             System.out.println(nums[5]);
//         }
//         catch(Exception e) {
//             System.out.println("Something went wrong. " + e);
//             // e => java.lang.ArithmeticException: / by zero
//             // e => java.lang.ArrayIndexOutOfBoundsException
//         }

//         System.out.println(j);

//         System.out.println("Bye");
//     }
// }


public class Exceptions {
    public static void main(String[] args) {
        int i = 2;
        int j = 0;
        int nums[] = new int[51];
        String str = null;

        try {
            j = 18 / i;
            System.out.println(nums[1]);
            System.out.println(nums[5]);
            System.out.println(str.length());
        } catch (ArithmeticException e) {
            System.out.println("cannot divide by zero.");
        } catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index is invalid");
        } catch(Exception e) {
            System.out.println("Something went wrong" + e);
        }
    
        System.out.println(j);

        System.out.println("Bye");
    }
}
