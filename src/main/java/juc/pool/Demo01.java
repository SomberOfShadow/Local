package juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  Java自带的线程池，阿里巴巴开发手册不推荐使用，建议使用ThreadPoolExecutor手动创建线程池
 *
 */
public class Demo01 {
    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
        ExecutorService executorService = Executors.newCachedThreadPool();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(()->{
                    System.out.println("Scheduled Executor Service Test!");
                },
                2,
                TimeUnit.SECONDS
                );
        scheduledExecutorService.shutdown();

        try {
            for (int i = 0; i < 10; i++) {
                executorService.execute(()-> System.out.println(Thread.currentThread().getName() + " ok"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }


    }
}
