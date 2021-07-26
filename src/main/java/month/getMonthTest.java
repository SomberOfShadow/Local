package month;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class getMonthTest {
    public static void main(String[] args) {

        // get year and month by Calendar
//        int year = Calendar.getInstance().get(Calendar.YEAR);
//        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
//        System.out.println("year is " + year);
//        System.out.println("month is " + month);

        // get year and month by localDate

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("-yyyy-MM");
        String format = localDate.format(dateTimeFormatter);
        System.out.println(format);

    }
}
