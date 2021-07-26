package thread.local;

/**
 *
 * ThreadLocal demo
 *
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        ThreadLocal<String> name = new ThreadLocal<>();

        for (int i = 0; i < 5; i++) {
            int temp = i;
            new Thread(()-> {
                name.set(String.valueOf(temp));
                System.out.println(Thread.currentThread().getName() + " name is " + name.get());
            }, String.valueOf(i)).start();
        }

    }
}
