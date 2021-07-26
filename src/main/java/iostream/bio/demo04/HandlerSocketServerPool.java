package iostream.bio.demo04;

import java.util.concurrent.*;

/**
 * @Classname HandlerSocketServerPool
 * @Description TODO
 * @Date 2021-07-14 4:22 PM
 * @Created by eenheni
 */
public class HandlerSocketServerPool {

    //1. 创建一个线程池的成员变量，存储一个线程池对象
    private ExecutorService executorService;

    //2. 初始化线程池对象
    /**
     *    public ThreadPoolExecutor(int corePoolSize,
     *                               int maximumPoolSize,
     *                               long keepAliveTime,
     *                               TimeUnit unit,
     *                               BlockingQueue<Runnable> workQueue,
     *                               RejectedExecutionHandler handler)
     */
    public HandlerSocketServerPool(int maxThreadNum, int queueSize) {
        int core = Runtime.getRuntime().availableProcessors();//core is 8
        executorService= new ThreadPoolExecutor(3,
                maxThreadNum,
                20,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize));
    }

    /**
     * 3. 提供一个方法提交任务给线程池对象暂存，等着线程池处理
     */
    public void execute(Runnable target) {
        executorService.execute(target);
    }


    public void shutDown() {
        executorService.shutdown();
    }

}
