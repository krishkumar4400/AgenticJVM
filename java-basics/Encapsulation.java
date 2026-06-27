class Human {
    int age;
    String name;
    public String branch = "CSE";
    private String password;
    private double balance;

    // void setPasword(String p) {
    //     password = p;
    // }

    // void setBalance(double b) {
    //     balance = b;
    // }

    // public String getPassword() {
    //     return password;
    // }

    // public double getBalance() {
    //     return balance;
    // }

    // void setPasword(String password) {
    //     password = password;
    // }

    // void setBalance(double balance) {
    //     balance = balance;
    // }

    // public String getPassword() { // null
    //     return password;
    // }

    // public double getBalance() {  // 0.0
    //     return balance;
    // }

    // void setPasword(String password) {
    //     Human h = new Human();
    //     h.password = password;
    // }

    // void setBalance(double balance) {
    //     Human h = new Human();
    //     h.balance = balance;
    // }

    // public String getPassword() {
    //     return password;
    // }

    // public double getBalance() {
    //     return balance;
    // }

    void setPasword(String password) {
        this.password = password;
    }

    void setBalance(double balance) {
        this.balance = balance;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }


}

public class Encapsulation {
    public static void main(String[] args) {
        Human obj = new Human();

        obj.name = "krish";
        obj.age = 21;

        System.out.println(obj.name);
        System.out.println(obj.branch);

        obj.setPasword("Krish@9661");
        obj.setBalance(9000.30);

        String password = obj.getPassword();
        double balance = obj.getBalance();

        System.out.println(password);
        System.out.println(balance);
    }
}
