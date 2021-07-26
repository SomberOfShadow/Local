package thread.examples.pc.lock;

public class Consumer extends Thread{

    Container container;

    public Consumer(Container container) {
        this.container = container;
    }


    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            Product pop = container.pop();
            System.out.println("Consume--> " + pop.getId() + " product!");
        }
    }
}
