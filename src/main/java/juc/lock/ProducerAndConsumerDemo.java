package juc.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *  @Description: ProducerAndConsumerDemo
 *  实际使用：多线程操作资源类
 */
public class ProducerAndConsumerDemo {

    public static void main(String[] args) {
        Data data = new Data();


        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }

        }, "A").start();


        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }

        }, "B").start();


        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                data.increment();
            }

        }, "C").start();


        new Thread(()-> {
            for (int i = 0; i < 10; i++) {
                data.decrement();
            }

        }, "D").start();

    }


}


// 通用逻辑：判断等待--> 业务逻辑--> 通知唤醒

class Data {// 资源类
    private int flag = 0;// 0 代表没有资源，1代表只有资源

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void increment() {
        lock.lock();

        //业务代码写在try()catch中，finally释放锁
        try {
            while(flag != 0){
                //wait
                condition.await();
            }
            // +1
            flag++;
            System.out.println(Thread.currentThread().getName() + "--> " + flag);
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {

        lock.lock();
        try {
            while (flag != 1){
                // wait
                condition.await();
            }

            //decr
            flag--;
            System.out.println(Thread.currentThread().getName() + "--> " + flag);
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
