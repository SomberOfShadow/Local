package iostream.bio.demo01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Classname Server
 * @Description Server sends single data and Client receives single data
 * @Date 2021-07-12 3:46 PM
 * @Created by eenheni
 */
public class Server {

    public static void main(String[] args) {
        try {
            System.out.println("=======Start Server======");
            //1. 定义一个ServerSocket的对象
            ServerSocket ss = new ServerSocket(999);

            //2. 监听客户端socket的请求
            Socket socket = ss.accept();

            //3. 从socket管道中接收到字节流
            InputStream is = socket.getInputStream();

            //4. 将字节流转成字符流
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String msg;

            if ((msg = bufferedReader.readLine()) != null) {
                System.out.println("Server receive : " + msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
