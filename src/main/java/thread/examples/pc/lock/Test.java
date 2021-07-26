package thread.examples.pc.lock;


/**
 *  @Description: Test
 *  测试生产者消费者问题
 *  使用lock锁的condition的await()和signalAll()代替wait和notifyAll方法
 */
public class Test {
    public static void main(String[] args) {
        Container container = new Container();

        for (int i = 0; i < 10; i++) {
            new Producer(container).start();
            new Consumer(container).start();
        }

    }
}
