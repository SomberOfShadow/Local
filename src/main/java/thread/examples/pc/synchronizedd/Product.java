package thread.examples.pc.synchronizedd;



/**
 *  @Description: Product 模拟生产者消费者模型中的产品
 */

public class Product {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public Product(int id) {
        this.id = id;
    }
}
