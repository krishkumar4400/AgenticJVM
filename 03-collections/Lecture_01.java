/**
 * 1. Custom classes
 */

class Data {
    private Integer num;
    private String name;
    private InternalData internalData;

    Data(int _num, String _name, int _revenue) {
        this.num = _num;
        this.name = _name;
        this.internalData = new InternalData(_revenue);
    }

    public void setNum(Integer _num) {
        this.num = _num;
    }
    public void setName(String _name) {
        this.name = _name;
    }
    public Integer getNum() {
        return num;
    }
    public String getName() {
        return name;
    }
    public Integer getRevenue() {
        return internalData.revenue;
    }
}

class InternalData {
    public Integer revenue;
    InternalData(Integer _revenue) {
        this.revenue = _revenue;
    }
}

public class Lecture_01 {

    public static void main(String[] args) {
        Data dataObj1 = new Data(10, "krish", 1000);
        Data dataObj2 = new Data(20, "raj", 2000);

        // dataObj1.name = "Striver";
        // System.out.println(dataObj2.name);
        
        System.out.println(dataObj1.getName() + " : " + dataObj1.getNum() + " : " + dataObj1.getRevenue());
    }
}