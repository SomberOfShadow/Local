package thread.examples.pc.lock;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  模拟生产者消费者模型的缓存区，存入产品
 *
 *  缓冲区满, 生产者等待，消费者消费；
 *  缓冲区空，消费者等待，生产者生产
 *
 */
public class Container {
    Product[] products = new Product[10]; //假设缓冲区只能放十个产品

    private int count = 0;// 代表缓冲区剩余空间大小
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void push (Product product) {
        lock.lock();
        try {
            while (count == products.length) {//如果使用if,在多线程情况下会有虚假唤醒的问题
                // 消费者消费，生产者等待
                condition.await(); // wait
            }

            //not full, push
            products[count] = product;
            count++;
            // notify consumer to consume
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Product pop () {
        Product product = null;
        lock.lock();
        try {
            //no product
            while (count == 0) {//如果使用if,在多线程情况下会有虚假唤醒的问题
                // consumer wait , producer produce
                condition.await();
            }
            //consumer consume
            count--;
            product = products[count];
            // notify producer to produce
            condition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return product;
    }

}
