import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;


public class Collections {
    public static void main(String[] args) {
        // Collection<Integer> nums = new ArrayList<Integer>();
        List<Integer> nums = new ArrayList<Integer>();
        nums.add(10);
        nums.add(11);
        nums.add(12);
        nums.add(6);

        System.out.println(nums.get(2));
        
        System.out.println(nums);

        for(int val : nums) {
            System.out.println(val);
        }
        
    }
}
