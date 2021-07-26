package juc.single;

/**
 * 单例模式懒汉式，线程不安全写法
 *
 */
public class LazyDemo1 {
    private static LazyDemo1 instance;
    private LazyDemo1() {

    }

    public static LazyDemo1 getInstance() {
        if (instance == null) {
            instance = new LazyDemo1();
        }
        return instance;
    }

}
