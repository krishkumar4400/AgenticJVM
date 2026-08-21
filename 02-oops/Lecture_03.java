/**
 * this keyword
 */

class Human {
    private int age;
    private String name;

    // public void SetAge(int a)
    // {
    // age=a;
    // }

    // public void setAge(int age, Human obj) {
    //     // Human obj = new Human();
    //     // obj.age = age;
    //     this.age = age;
    // }
    public void setAge(int age) {
        this.age = age;
    }


    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    // public void setName(String n)
    // {
    // name=n;
    // }
    public void setName(String name) {
        this.name = name;
    }
}

public class Lecture_03 {
    public static void main(String[] args) {
        Human obj = new Human();

        // obj.setName("krish");

        // obj.setAge(10, obj);
        obj.setAge(10);
        obj.setName("krish");
        System.out.println(obj.getAge() + " : " + obj.getName());
    }
}
