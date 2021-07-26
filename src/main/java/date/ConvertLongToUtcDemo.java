package date;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class ConvertLongToUtcDemo {
    public static void main(String[] args) {

        //use Date package, not recommend
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss.SSS");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(format.format(new Date(time)));


        System.out.println("--------------------------");

        // use time package, Instant : default Zone is UTC
        Instant now = Instant.now();
        String s = now.toString();
        System.out.println(s);


        // use java.time to specify date format
        LocalDateTime localDateTime = LocalDateTime.now();

//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        DateTimeFormatter isoLocalDateTime = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        DateTimeFormatter dateTimeFormatter = isoLocalDateTime.withZone(ZoneId.systemDefault());

        System.out.println(isoLocalDateTime.format(localDateTime));
        System.out.println(dateTimeFormatter.format(localDateTime));

        System.out.println("-------------------");
        // use java.time to specify UTC Zone
        Instant instant = Instant.now();
        OffsetDateTime odt = instant.atOffset( ZoneOffset.UTC );
        // or skip instant
        OffsetDateTime now1 = OffsetDateTime.now(ZoneOffset.UTC);
        System.out.println(odt);

//        DateTimeFormatter formatter = DateTimeFormatter.ISO_ZONED_DATE_TIME;
//        System.out.println(formatter.format(odt));


        System.out.println("---------------------");
        Instant ins = Instant.now();
        ZonedDateTime zdt = ins.atZone(ZoneId.of("UTC"));
//        ZonedDateTime zdt = ins.atZone(ZoneId.of("Asia/Karachi"));
//        ZonedDateTime zdt = ins.atZone(TimeZone.getDefault().toZoneId());
        System.out.println(zdt);

    }


    // use date
    private String convertUnixTimeToUtcTime() {
        //Unix timestamp
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(new Date(time));
    }

}
