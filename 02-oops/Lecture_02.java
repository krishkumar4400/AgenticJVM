/**
 * encapsulation
 * getters and setters
 */

class Human {
    // int age;
    // String name;

    private int age;
    private String name;

    // private int age = 11;
    // private String name = "krish";

    public int getAge() {
        return age;
    }

    // public void setAge(int a) {
    //     age = a;
    // }

    public String getName() {
        return name;
    }

    // public void setName(String n) {
    //     name = n;
    // }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

}

public class Lecture_02 {
    public static void main(String[] args) {
        Human obj = new Human();

        obj.setAge(22);
        obj.setName("ankit");

        // obj.age = 11;
        // obj.name = "Navin";

        System.out.println(obj.getName() + " " + obj.getAge());
    }
}
