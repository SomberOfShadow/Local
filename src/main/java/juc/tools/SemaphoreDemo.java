package juc.tools;


import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {

        // 只有一个车位，10辆车
        Semaphore semaphore = new Semaphore(1);

        for (int i = 0; i < 10; i++) {
            new Thread(()-> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "拿到了车位！");
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName() + "离开了车位！");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }

            }).start();

        }
    }
}
