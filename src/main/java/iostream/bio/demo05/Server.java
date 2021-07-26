package iostream.bio.demo05;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname Server
 * @Description
 * 目标：服务端开发，可以接收客户端的任意类型文件，并保存到服务端的磁盘
 * @Date 2021-07-15 11:05 AM
 * @Created by eenheni
 */
public class Server {
    public static void main(String[] args) {

        try {
            ServerSocket ss = new ServerSocket(888);

            while (true){
                Socket socket = ss.accept();

                //交给一个线程进行处理
                new ServerReaderThread(socket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
