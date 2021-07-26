package juc.tools;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *  CyclicBarrier 是可以重复利用的, CountDownLatch 只能用一次
 *
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //集齐七颗龙珠召唤神龙

        CyclicBarrier cyclicBarrier = new CyclicBarrier(7, ()->{
            System.out.println("召唤神龙成功！");
        });
        for (int i = 1; i <= 7; i++) {
            int finalI = i;
            new Thread(()-> {
                System.out.println(Thread.currentThread().getName() + "收集了" + finalI + "颗龙珠！");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }


    }
}
