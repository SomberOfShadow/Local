package iostream.bio.chat.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname Server
 * @Description
 * BIO 模式下端口转发 服务端实现
 * 需求：
      1. 注册端口
      2. 接收客户端的请求，交给一个线程处理
      3. 当前连接socket存入一个所谓的在线socket集合集中保存
      4. 接收客户端的消息，推送给当前在线的所有socket
 * @Date 2021-07-15 2:18 PM
 * @Created by eenheni
 */
public class Server {
    //定义一个静态的集合
    public static List<Socket> allSocketsOnline = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(9999);

            while (true){
                Socket socket = ss.accept();

                //把当前的socket存入到一个在线集合中去
                allSocketsOnline.add(socket);

                // 为当前线程分配一个独立的线程处理与之通信
                new ServerReaderThread(socket).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
