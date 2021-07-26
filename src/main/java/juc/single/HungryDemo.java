package juc.single;

/**
 *  单例模式饿汉式
 *
 */
public class HungryDemo {

    private static HungryDemo instance = new HungryDemo();
    private HungryDemo(){
    }


    public static HungryDemo getInstance() {
        return instance;
    }
}
