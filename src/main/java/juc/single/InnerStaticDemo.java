package juc.single;

/**
 *  @Description: InnerStaticDemo
 *  单例模式, 静态内部类的实现方式
 */
public class InnerStaticDemo {
    private static InnerStaticDemo instance;
    private InnerStaticDemo(){
    }

    public static InnerStaticDemo getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private final static InnerStaticDemo INSTANCE = new InnerStaticDemo();
    }
}
