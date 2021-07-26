package juc.pool;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 *  模拟银行柜台业务，联系起来理解
 *
 */
public class Deno02 {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());//相当于银行人满了,还有人进来，不处理，抛出异常

//            new ThreadPoolExecutor.CallerRunsPolicy());//哪来的回哪里去
//        new  ThreadPoolExecutor.DiscardPolicy());//队列满了丢掉任务，不会抛出异常
//        new ThreadPoolExecutor.DiscardOldestPolicy());//尝试和第一个竞争，竞争失败，丢掉,也不会抛出异常

        // CPU 密集型：多少核就设置最大线程数为多少
        // IO 密集型：线程数大于很消耗IO资源的线程数，一般设置为2倍
        System.out.printf("core number: %d\n", Runtime.getRuntime().availableProcessors());

        try {
            //最大承载=max+blockingQueue(本案例中是8)
            for (int i = 0; i < 8; i++) {
                threadPoolExecutor.execute(()-> System.out.println(Thread.currentThread().getName() + " ok"));
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            threadPoolExecutor.shutdown();
        }


    }
}
