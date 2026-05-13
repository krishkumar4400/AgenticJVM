class Human {
    private String name;
    private int age;

    public Human() {
        name = "no name";
        age = 11;
    }
    // public Human(String n, int a) {
    //     name = n;
    //     age = a;
    // }

    public Human(String name, int age) {
        System.out.println("In Constructor");
        this.name = name;
        this.age = age;
    }
    public Human(String name) {
        System.out.println("In Constructor");
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}



public class Constructor {
    public static void main(String[] args) {
        // Human h1 = new Human("krishna", 22);
        // int age = h1.getAge();
        // String name = h1.getName();

        // System.out.println(age);
        // System.out.println(name);

        Human h1 = new Human();
        System.out.println(h1.getAge());
        System.out.println(h1.getName());
        
        Human h2 = new Human("this name", 23);
        System.out.println(h2.getAge());
        System.out.println(h2.getName());

        Human h3 = new Human("Pathak");
        System.out.println(h3.getAge());


    }
}

