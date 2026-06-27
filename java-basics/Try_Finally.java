// public class Try_Finally {
//     public static void main(String[] args) {
//         int i = 0;
//         int j = 0;
//         try {
//             j = 18 / i;
//         } catch(Exception e) {
//             System.out.println(e.getMessage());
//         }
//         finally {
//             System.out.println("Bye");
//         }
//         System.out.println(j);
//     }
// }

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;

// public class Try_Finally {
//     public static void main(String[] args) throws NumberFormatException, IOException {
//         int num = 0;
//         BufferedReader br = null;
//         try {
//             br = new BufferedReader(new InputStreamReader(System.in));
//             System.out.println("Enter the number");
//             num = Integer.parseInt(br.readLine());
//             System.out.println(num);
//         } catch (Exception e) {
//             System.out.println(e.getMessage());
//         } finally {
//             br.close(); // closing the resource
//         }
//     }
// }

// optimized - for auto closing the resources.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Try_Finally {
    public static void main(String[] args) throws NumberFormatException, IOException {
        int num = 0;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the number");
            num = Integer.parseInt(br.readLine());
            System.out.println(num);
        }
    }
}
