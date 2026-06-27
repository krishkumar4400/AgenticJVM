import db.tools.*;
import java.lang.*;

public class Demo {
    public static void main(String[] args) {
        Calc obj = new Calc();
        AdvCalc obj1 = new AdvCalc();

        System.out.println(obj.add(3, 4));
        System.out.println(obj1.add(3, 4));
    }
}
