



// // final variable
// public class Final_keyword {
//     public static void main(String[] args) {
//         // int num = 8;
//         // num = 10;

//         final int num = 8; // the variable becomes constant
//         // num = 10; // error: cannot assign a value to final variable num


//     }    
// }




// final class

// final class Calc { 
//     public void show() {
//         System.out.println("in Calc show");
//     }

//     public void add(int a, int b) {
//         System.out.println(a + b);
//     }
// }

// // class AdvCalc extends Calc { // // error: cannot inherit from final Calc

// // }

// public class Final_keyword {
//     public static void main(String[] args) {

//         Calc obj = new Calc();
//         obj.show();
//         obj.add(10, 10);

//     }
// }


// final method

class Calc {
    final public void show() {
        System.out.println("in Calc show");
    }

    public void add(int a, int b) {
        System.out.println(a + b);
    }
}

class AdvCalc extends Calc { // problem - someone has created object and they will use your feature. so i don't want to anyone override my method. to stop method overriding we make the method as final.

    public void show() {
        System.out.println("in AdvCalc show");
    }
}

public class Final_keyword {
    public static void main(String[] args) {

        AdvCalc obj = new AdvCalc();
        obj.show();
        obj.add(10, 10);

    }
}