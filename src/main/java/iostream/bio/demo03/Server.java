package iostream.bio.demo03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname Server
 * @Description 服务端可以接受多个客户端的通信请求
 * 思路：服务端每接收到一个客户端的请求，交给一个独立的线程处理
 *
 * BIO模式的缺点：
 * 一个socket连接建立一个线程进行处理，线程上下文切换开销很大
 * 多个线程占用栈资源和CPU资源
 * 不是每一个socket都进行IO操作
 * 客户端和服务端是1:1的线程开销，对服务器的压力很大
 *
 *
 * @Date 2021-07-12 3:46 PM
 * @Created by eenheni
 */
public class Server {

    public static void main(String[] args) {
        try {
            System.out.println("=======Start Server======");
            //1. 定义一个ServerSocket的对象
            ServerSocket ss = new ServerSocket(9999);

            //2. 定义一个死循环不断接收客户端的Socket请求
            while (true){
                Socket socket = ss.accept();
                //3. 创建一个线程处理
                new ServerThreadReader(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
