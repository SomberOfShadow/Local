package juc.lock;



import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 10; i++) {
            int temp = i;
            new Thread(()-> {
                myCache.put(String.valueOf(temp), temp);
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 10; i++) {
            int temp = i;
            new Thread(()-> {
                myCache.get(String.valueOf(temp));
            }, String.valueOf(i)).start();
        }
    }

}


class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void put (String key, Object value) {
        reentrantReadWriteLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + " write ...");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " write ok!");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }

    }

    public void get (String key) {
        reentrantReadWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + " read ... ");
            map.get(key);
            System.out.println(Thread.currentThread().getName() + " write ok!");
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }

    }
}
