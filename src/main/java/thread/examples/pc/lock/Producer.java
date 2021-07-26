package thread.examples.pc.lock;

public class Producer extends Thread {

    Container container;

    public Producer(Container container) {
        this.container = container;
    }


    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            container.push(new Product(i));
            System.out.println("Produce " + i + " product!");
        }
    }
}
