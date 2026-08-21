/**
 * Constructor
 * 
 * - default and parameterized constructor
 */


class Human {
    private int age;
    private String name;

//  default constructor
    public Human() {
        age = 12;
        name = "krish";
        System.out.println("in default constructor");
    }

    //Parameterized constructor
    public Human(int age, String name) {
        this.age = age;
        this.name = name;
         System.out.println("in parameterized constructor");
    }

    public Human(String name) {
		this.name=name;
	}
    public Human(int age) {
		this.age=age;
	}

    public int getAge() {
        return age;
    }
    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class Lecture_04 {
    public static void main(String[] args) {
     Human obj = new Human();
     Human obj1 = new Human(2, "xyz");

     System.out.println(obj.getAge() + " : " + obj.getName());
     
     obj.setName("ankit");
     obj.setAge(20);
     System.out.println(obj.getAge() + " : " + obj.getName());
     System.out.println(obj1.getAge() + " : " + obj1.getName());
    }
}
