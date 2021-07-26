package juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *  @Description: ProducerAndConsumerDemo
 *  实际使用：多线程操作资源类
 */
public class ProducerAndConsumerDemo2 {

    public static void main(String[] args) {
        Data2 data = new Data2();


        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                data.printA();
            }

        }, "A").start();


        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                data.PrintB();
            }

        }, "B").start();


        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                data.PrintC();
            }

        }, "C").start();


}

}



// 通用逻辑：判断等待--> 业务逻辑--> 通知唤醒

class Data2 {// 资源类
    private int flag = 1;// 指定线程执行顺序的标志位 1--> A, 2--> B, 3--> C

    private Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void printA() {
        lock.lock();

        //业务代码写在try()catch中，finally释放锁
        try {
            while (flag != 1) {
                //wait
                condition1.await();
            }
            // +1
            flag = 2;
            System.out.println(Thread.currentThread().getName() + "--> AAAA");
            condition2.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void PrintB() {

        lock.lock();
        try {
            while (flag != 2) {
                // wait
                condition2.await();
            }

            //decr
            flag = 3;
            System.out.println(Thread.currentThread().getName() + "--> BBBB");
            condition3.signal();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void PrintC() {

        lock.lock();
        try {
            while (flag != 3) {
                // wait
                condition3.await();
            }

            //decr
            flag = 1;
            System.out.println(Thread.currentThread().getName() + "--> CCCC");
            condition1.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}