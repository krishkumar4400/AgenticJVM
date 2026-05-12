public class Strings {
    public static void main(String[] args) {
        String name = new String("Krish");
        System.out.println(name);
        System.out.println(name.hashCode());

        // concatenation
        System.out.println("Hello " + name);

        // string methods
        System.out.println(name.charAt(1));
        System.out.println(name.concat("kumar"));
        System.out.println(name);

        name = name + " Kumar";
        System.out.println(name);

        String s1 = "krish";
        String s2 = "krish";

        // string buffer
        StringBuffer sb = new StringBuffer();
        System.out.println(sb.capacity());
        System.out.println(sb.length());

        StringBuffer sb2 = new StringBuffer("krishna");
        System.out.println(sb2.capacity());
        System.out.println(sb2.length());

        sb2.append(" Kumar");
        System.out.println(sb2);

        // String st1 = sb2; // StringBuffer cannot be converted to String
        // System.out.println(st1);

        // methods
        String s3 = sb2.toString();
        System.out.println(s3);

        System.out.println(sb2);
        sb2.delete(5, 7);
        System.out.println(sb2);
        
        sb2.insert(0, "Java ");
        System.out.println(sb2);

        sb2.insert(11, "Java ");
        System.out.println(sb2);


    }
}
