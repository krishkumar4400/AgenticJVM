
class Student {
    Strings name;
    int rollno;
    double marks;
}

public class Array {
    public static void main(Strings[] args) {
        int num[] = { 1, 2, 3 };
        int nums1[] = new int[100];
        System.out.println(num[0]);
        num[0] = 20;
        System.out.println(num[0]);

        System.out.println(nums1[0]);

        int matrix[][] = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        double random = Math.random() * 100; // return a double value
        System.out.println(random);
        // type casting
        int random1 = (int) (Math.random() * 100); // return a double value
        System.out.println(random1);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = (int) (Math.random() * 100);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        for(int arr[] : matrix) {
            for(int val : arr) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        // jagged array
        int nums2[][] = new int[3][]; // jagged

        nums2[0] = new int[3];
        nums2[1] = new int[6];
        nums2[2] = new int[1];

        System.out.println();
        for(int i = 0; i < nums2.length; i++) {
            for(int j = 0; j < nums2[i].length; j++) {
                nums2[i][j] = (int) (Math.random() * 10);
            }
        }

        for(int arr[] : nums2) {
            for(int val : arr) {
                System.out.print(val + " ");
            }
            System.out.println();
        }

        int nums3[][][] = new int[10][10][10]; // three dimentional array

        int arr1[] = new int[6];
        arr1[0] = 1;
        arr1[1] = 2;
        arr1[2] = 3;
        arr1[3] = 4;

        for(int i = 0; i < arr1.length; i++) {
            System.out.println(arr1[i]);
        }

        Student s1 = new Student();
        s1.name = "krish";
        s1.rollno = 1;
        s1.marks = 100;

        Student s2 = new Student();
        s2.name = "ankit";
        s2.rollno = 2;
        s2.marks = 19;

        Student s3 = new Student();
        s3.name = "akash";
        s3.rollno = 3;
        s3.marks = 60;

        Student students[] = new Student[3];
        students[0] = s1;
        students[1] = s2;
        students[2] = s3;

        for(Student s : students) {
            System.out.println(s.name);
        }

        int nums5[] = new int[5];
        nums5[0] = 10;
        nums5[1] = 20;
        nums5[2] = 30;
        nums5[3] = 40;
        nums5[4] = 50;

        for(int number : nums5) {
            System.out.println(number);
        }
    }
}
