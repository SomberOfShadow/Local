package stopwatch;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

public class StopWatchTest {
    public static void main(String[] args) throws InterruptedException {

        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(2000);
        stopwatch.stop();
        long elapsed = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        System.out.println(elapsed);
//        long time = stopWatch.getTime(TimeUnit.MICROSECONDS);
//        long nanoTime = stopWatch.getNanoTime();
//
//        System.out.println(nanoTime);
//        System.out.println(time);


    }
}
