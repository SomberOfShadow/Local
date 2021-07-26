package iostream.bio.demo04;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname Server
 * @Description
 * 开发实现伪异步通信架构，服务端使用线程池
 *
 * 优缺点：
 * 伪异步采用了线程池，避免了为每一个请求创建一个独立的线程造成线程资源耗尽的问题，
 * 但是由于底层依然采用BIO，无法从根本上解决问题
 *
 * 如果单个消息处理缓慢，或者线程池中全部线程都被塞满，后续的socket消息都将在队列中排队，
 * 新的socket请求无法处理，客户端户产生大量的连接超时
 *
 *
 *
 * @Date 2021-07-14 4:20 PM
 * @Created by eenheni
 */
public class Server {
    //初始化一个线程池对象
    private static HandlerSocketServerPool pool = new HandlerSocketServerPool(3, 10);

    public static void main(String[] args) {
        try {

            ServerSocket serverSocket = new ServerSocket(9999);

            while (true){
                Socket socket = serverSocket.accept();

                // 把socket对象封装成一个任务对象交给线程池处理
                ServerRunnableTarget target = new ServerRunnableTarget(socket);
                pool.execute(target);
            }

        } catch (IOException e) {
            pool.shutDown();//关闭线程池
            e.printStackTrace();
        }

    }
}
