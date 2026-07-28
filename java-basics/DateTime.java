// import java.util.Date;

// public class DateTime {
//     public static void main(String[] args) {
//         Date date = new Date();
//         System.out.println(date);

//         long time = date.getTime();
//         System.out.println(time);

//         java.sql.Date sqlDate = new java.sql.Date(time);
//         System.out.println(sqlDate);
//     }
// }

import java.time.*;

public class DateTime {
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        System.out.println(date);

        System.out.println(date.getMonth());

        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalTime lt = LocalTime.now();
        System.out.println(lt);

    }
}
