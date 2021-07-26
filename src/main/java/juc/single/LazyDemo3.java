package juc.single;


/**
 *  @Description: 单例模式，懒汉式，DCL(double check locking)写法
 *
 */
public class LazyDemo3 {

    private volatile static LazyDemo3 instance;

    private LazyDemo3(){

    }

    public static LazyDemo3 getInstance() {
        if (instance == null) {
            synchronized (LazyDemo3.class){
                if (instance == null) {
                    instance = new LazyDemo3();//非原子性操作，所以要加volatile,防止指令重排
                }

            }
        }
        return instance;
    }
}
