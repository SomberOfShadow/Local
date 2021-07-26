package instant;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class InstantDemo {
    public static void main(String[] args) throws InterruptedException {
        Instant start = Instant.now();
        System.out.println("start :" + start);


        TimeUnit.SECONDS.sleep(1);
        Instant end = Instant.now();
        System.out.println("end :" + end);
        System.out.println(Duration.between(start, end).toMillis());

    }
}
