

public class Inheritance {
    public static void main(String[] args) {
        Calc obj = new Calc();

        int r1 = obj.add(4, 10);
        int r2 = obj.sub(12, 2);

        System.out.println(r1 + " " + r2); // 14 10

        AdvCalc obj1 = new AdvCalc();
        System.out.println(obj1.multi(10, 2) + " " + obj1.div(10, 2) + " " + obj1.add(10, 1) + " " + obj1.sub(1, 2)); // 20 5 11 -1
        
        VeryAdvCalc obj2 = new VeryAdvCalc();
        double r5 = obj2.power(4, 2);
        System.out.println(obj1.multi(10, 2) + " " + obj1.div(10, 2) + " " + obj1.add(10, 1) + " " + obj1.sub(1, 2) + " " + r5); // 20 5 11 -1 16.0
        

    }
}
