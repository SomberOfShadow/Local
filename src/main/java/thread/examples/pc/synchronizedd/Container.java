package thread.examples.pc.synchronizedd;


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

    public synchronized void push (Product product) {
        if (count == products.length) {
            // 消费者消费，生产者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //not full, push
        products[count] = product;
        count++;

        // notify consumer to consume
        this.notifyAll();
    }

    public synchronized Product pop () {
        //no product
        if (count == 0) {
            // consumer wait , producer produce
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        //consumer consume
        count--;
        Product product = products[count];

        // notify producer to produce
        this.notifyAll();
        return product;
    }

}
