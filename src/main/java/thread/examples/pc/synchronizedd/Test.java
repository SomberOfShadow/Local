package thread.examples.pc.synchronizedd;


/**
 *  @Description: Test
 *  测试生产者消费者问题，使用synchronized关键字，使用wait和notifyAll方法
 *  管程法，也可以使用信号灯法，即使用一boolean变量
 */
public class Test {
    public static void main(String[] args) {
        Container container = new Container();

        new Producer(container).start();
        new Consumer(container).start();
    }
}
