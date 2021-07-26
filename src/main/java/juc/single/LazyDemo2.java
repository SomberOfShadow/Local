package juc.single;

/**
 *  单例模式，懒汉式，线程安全写法-synchronized
 *
 */
public class LazyDemo2 {

    private static LazyDemo2 instance;

    private LazyDemo2(){

    }

    public synchronized static LazyDemo2 getInstance() {
        if (instance != null) {
            instance = new LazyDemo2();
        }
        return instance;
    }
}
