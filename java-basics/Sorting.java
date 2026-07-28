import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// public class Sorting {
//     public static void main(String[] args) {
//         List<Integer> nums = new ArrayList<>();
//         nums.add(4);
//         nums.add(3);
//         nums.add(7);
//         nums.add(9);

//         Collections.sort(nums);

//         System.out.println(nums);
//     }
// }

// sort values on custom login - eg: by last digit
// public class Sorting {
//     public static void main(String[] args) {
//         List<Integer> nums = new ArrayList<>();
//         nums.add(43);
//         nums.add(36);
//         nums.add(71);
//         nums.add(94);

//         List<String> str = new ArrayList<>();
//         str.add("krish");
//         str.add("ankit");
//         str.add("raj");
//         str.add("aman");
//         str.add("krishna");

//         Comparator<Integer> com = new Comparator<Integer>() {
//             // public int compare(Integer i, Integer j) {
//             // if (i%10 > j%10) {
//             // return 1;
//             // }
//             // return -1;
//             // }
//             public int compare(Integer i, Integer j) {
//                 if (j > i) {
//                     return 1;
//                 }
//                 return -1;
//             }
//         };

//         Comparator<String> com1 = new Comparator<String>() {
//             public int compare(String s1, String s2) {
//                 if (s1.length() > s2.length()) {
//                     return 1;
//                 }
//                 return -1;
//             }
//         };

//         // Collections.sort(nums, com); // [71, 43, 94, 36]
//         Collections.sort(nums, com); // [94, 71, 43, 36]
//         System.out.println(nums);

//         System.out.println(str); // [krish, ankit, raj, aman, krishna]
//         Collections.sort(str, com1);
//         System.out.println(str); // [raj, aman, ankit, krish, krishna]

//     }
// }

// class Student {
//     int age;
//     String name;

//     Student(int age, String name) {
//         this.name = name;
//         this.age = age;
//     }

//     @Override
//     public String toString() {
//         return "Student [age=" + age + ", name=" + name + "]";
//     }

//     public void show() {
//     }
// }

// public class Sorting {
//     public static void main(String[] args) {
//         List<Student> student = new ArrayList<>();
//         student.add(new Student(20, "krish"));
//         student.add(new Student(18, "ankit"));
//         student.add(new Student(42, "amit"));
//         student.add(new Student(26, "rahul"));
//         student.add(new Student(13, "ayush"));

//         Comparator<Student> com = new Comparator<Student>() {
//             public int compare(Student i, Student j) {
//                 if (i.age > j.age) {
//                     return 1;
//                 }
//                 return -1;
//             }
//         };

//         // System.out.println(student);

//         System.out.println("Before Sorting");
//         for (Student st : student) {
//             System.out.println(st);
//         }

//         Collections.sort(student, com);

//         System.out.println("After Sorting");
//         for (Student st : student) {
//             System.out.println(st);
//         }
//     }
// }

// class Student implements Comparable<Student> {
//     int age;
//     String name;

//     Student(int age, String name) {
//         this.name = name;
//         this.age = age;
//     }

//     @Override
//     public String toString() {
//         return "Student [age=" + age + ", name=" + name + "]";
//     }

//     @Override
//     public int compareTo(Student that) {
//         if (this.age > that.age) {
//             return 1;
//         }
//         return -1;
//     }
// }

// public class Sorting {
//     public static void main(String[] args) {
//         List<Student> student = new ArrayList<>();
//         student.add(new Student(20, "krish"));
//         student.add(new Student(18, "ankit"));
//         student.add(new Student(42, "amit"));
//         student.add(new Student(26, "rahul"));
//         student.add(new Student(13, "ayush"));

//         // Comparator<Student> com = new Comparator<Student>() {
//         // public int compare(Student i, Student j) {
//         // if (i.age > j.age) {
//         // return 1;
//         // }
//         // return -1;
//         // }
//         // };

//         Collections.sort(student);

//         for (Student s : student) {
//             System.out.println(s);
//         }

//     }
// }

class Student implements Comparable<Student> {
    int age;
    String name;

    Student(int age, String name) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student [age=" + age + ", name=" + name + "]";
    }

    @Override
    public int compareTo(Student that) {
        if (this.age > that.age) {
            return 1;
        }
        return -1;
    }
}

public class Sorting {
    public static void main(String[] args) {
        List<Student> student = new ArrayList<>();
        student.add(new Student(20, "krish"));
        student.add(new Student(18, "ankit"));
        student.add(new Student(42, "amit"));
        student.add(new Student(26, "rahul"));
        student.add(new Student(13, "ayush"));

        Comparator<Student> com = (i, j) -> i.age > j.age ? 1 : -1;

        // Collections.sort(student);
        Collections.sort(student, com);

        for (Student s : student) {
            System.out.println(s);
        }

    }
}